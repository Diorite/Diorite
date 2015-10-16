/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2015 Diorite (by Bartłomiej Mazur (aka GotoFinal))
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package org.diorite.impl.world;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;

import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.impl.DioriteCore;
import org.diorite.impl.cfg.DioriteConfigImpl;
import org.diorite.impl.cfg.WorldsConfigImpl;
import org.diorite.impl.world.io.anvil.serial.AnvilSerialIOService;
import org.diorite.impl.world.tick.TickGroupImpl;
import org.diorite.impl.world.tick.WorldTickGroup;
import org.diorite.cfg.WorldsConfig.WorldConfig;
import org.diorite.cfg.WorldsConfig.WorldGroupConfig;
import org.diorite.entity.Player;
import org.diorite.nbt.NbtInputStream;
import org.diorite.nbt.NbtLimiter;
import org.diorite.nbt.NbtNamedTagContainer;
import org.diorite.nbt.NbtOutputStream;
import org.diorite.nbt.NbtTagCompound;
import org.diorite.utils.DioriteUtils;
import org.diorite.world.World;
import org.diorite.world.WorldsManager;

public class WorldsManagerImpl implements WorldsManager
{
    private WorldImpl        defaultWorld;
    private WorldsConfigImpl config;
    private final Map<String, WorldGroupImpl> groups = new ConcurrentHashMap<>(5, .1f, 4);
    private final Map<String, WorldImpl>      worlds = new ConcurrentHashMap<>(5, .1f, 4);

    public void setDefaultWorld(final WorldImpl defaultWorld)
    {
        Validate.notNull(defaultWorld, "Default world can't be null!");
        this.defaultWorld = defaultWorld;
    }

    public void addWorld(final WorldImpl world)
    {
        this.worlds.put(world.getName(), world);

        DioriteCore.getInstance().getTicker().getGroups().add(new WorldTickGroup(world)); // TODO: something better?
    }

    public void removeWorld(final String worldName)
    {
        final WorldImpl world = this.worlds.remove(worldName);
        if (world == null)
        {
            return;
        }
        for (final TickGroupImpl group : DioriteCore.getInstance().getTicker().getGroups())
        {
            group.removeWorld(world);
        }
    }

    public void removeWorld(final World world)
    {
        this.worlds.remove(world.getName());
        for (final TickGroupImpl group : DioriteCore.getInstance().getTicker().getGroups())
        {
            group.removeWorld(world);
        }
    }

    @Override
    public WorldsConfigImpl getConfig()
    {
        return this.config;
    }

    @Override
    public Map<String, WorldImpl> getWorldsMap()
    {
        return ImmutableMap.copyOf(this.worlds);
    }

    @Override
    public Map<String, WorldGroupImpl> getGroupsMap()
    {
        return ImmutableMap.copyOf(this.groups);
    }

    @Override
    public Collection<WorldImpl> getWorlds()
    {
        return ImmutableSet.copyOf(this.worlds.values());
    }

    @Override
    public Collection<WorldGroupImpl> getGroups()
    {
        return ImmutableSet.copyOf(this.groups.values());
    }

    @Override
    public WorldImpl getDefaultWorld()
    {
        return this.defaultWorld;
    }

    @Override
    public WorldImpl getWorld(final UUID uuid)
    {
        return this.worlds.get(uuid);
    }

    @Override
    public WorldImpl getWorld(final String name)
    {
        for (final WorldImpl world : this.worlds.values())
        {
            if (world.getName().equalsIgnoreCase(name))
            {
                return world;
            }
        }
        return null;
    }

    @Override
    public Collection<Player> getPlayersInWorld(final World world)
    {
        return world.getPlayersInWorld();
    }

    public void init(final DioriteConfigImpl cfg, final File worldsFile)
    {
        Validate.notNull(worldsFile, "File can't be null");
        if (! worldsFile.exists())
        {
            worldsFile.mkdirs();
        }
        this.config = cfg.getWorlds();
        if (! worldsFile.isDirectory())
        {
            throw new IllegalArgumentException("Worlds can be only loaded from directory. Not from file: " + worldsFile.getPath());
        }

        final Collection<Runnable> loaders = new LinkedHashSet<>(10);
        for (final WorldGroupConfig wgc : this.config.getGroups())
        {
            final WorldGroupImpl wgImpl = new WorldGroupImpl(wgc.getName(), new File(worldsFile, wgc.getName()));
            this.groups.put(wgc.getName(), wgImpl);
            loaders.addAll(wgc.getWorlds().stream().filter(WorldConfig::isEnabled).map(wc -> (Runnable) () -> {
                final File wFile = new File(wgImpl.getDataFolder(), wc.getName());
                final WorldImpl wImpl = new WorldImpl(new AnvilSerialIOService(wFile, wc.getName()), wc.getName(), wgImpl, wc.getDimension(), wc.getWorldType(), wc.getGenerator(), wc.getGeneratorSettings());
                this.loadWorld(wImpl, wc);
                wgImpl.addWorld(wImpl);
            }).collect(Collectors.toList()));
        }
        System.out.println("[WorldLoader] Loading " + loaders.size() + " worlds...");
        loaders.stream().forEach(Runnable::run);
        this.setDefaultWorld(this.getWorld(this.config.getDefaultWorld()));
        System.out.println("[WorldLoader] Loaded all " + loaders.size() + " worlds!");
    }

    private void loadWorld(final WorldImpl world, final WorldConfig worldConfig)
    {
        world.setNoUpdateMode(true);
        final File file = new File(world.getWorldFile(), "level.dat");
        boolean isNew = true;
        if (file.exists())
        {
            try
            {
                isNew = false;
                final NbtTagCompound tag = ((NbtTagCompound) NbtInputStream.readTagCompressed(file, NbtLimiter.getUnlimited()));
                world.loadNBT(tag.getCompound(""), worldConfig);
            } catch (final IOException e)
            {
                System.err.println("Can't read world in: " + world.getWorldFile().getPath());
                e.printStackTrace();
            }
        }
        else
        {
            try
            {
                DioriteUtils.createFile(file);
            } catch (final IOException e)
            {
                throw new RuntimeException("Can't create file: " + file, e);
            }
            world.loadNBT(new NbtTagCompound(), worldConfig);
            { // write
                try (final NbtOutputStream os = NbtOutputStream.getCompressed(file))
                {
                    final NbtTagCompound nbt = new NbtTagCompound();
                    world.writeTo(nbt);
                    final NbtNamedTagContainer nbtData = new NbtTagCompound();
                    nbtData.setTag("data", nbt);
                    os.write(nbtData);
                    os.flush();
                    os.close();
                } catch (final IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
        this.addWorld(world);
        if (isNew)
        {
            world.initSpawn();
        }
        world.loadBase(world.getForceLoadedRadius(), world.getSpawn().toBlockLocation());
        world.setNoUpdateMode(false);
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("worlds", this.worlds).toString();
    }
}
