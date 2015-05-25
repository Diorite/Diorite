package org.diorite.impl.world;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.impl.Tickable;
import org.diorite.impl.world.io.mca.McaChunkIO;
import org.diorite.cfg.WorldsConfig;
import org.diorite.cfg.WorldsConfig.WorldConfig;
import org.diorite.cfg.WorldsConfig.WorldGroupConfig;
import org.diorite.cfg.system.Template;
import org.diorite.cfg.system.TemplateCreator;
import org.diorite.cfg.yaml.DioriteYaml;
import org.diorite.entity.Player;
import org.diorite.nbt.NbtInputStream;
import org.diorite.nbt.NbtLimiter;
import org.diorite.nbt.NbtOutputStream;
import org.diorite.nbt.NbtTagCompound;
import org.diorite.utils.DioriteUtils;
import org.diorite.world.World;
import org.diorite.world.WorldsManager;

public class WorldsManagerImpl implements WorldsManager, Tickable
{
    private WorldImpl    defaultWorld;
    private WorldsConfig config;
    private final Map<String, WorldGroupImpl> groups = new ConcurrentHashMap<>(5, .1f, 4);
    private final Map<String, WorldImpl>      worlds = new ConcurrentHashMap<>(5, .1f, 4);

    public WorldsManagerImpl()
    {
    }

    public void setDefaultWorld(final WorldImpl defaultWorld)
    {
        this.defaultWorld = defaultWorld;
    }

    public void addWorld(final WorldImpl world)
    {
        this.worlds.put(world.getName(), world);
    }

    @Override
    public WorldsConfig getConfig()
    {
        return this.config;
    }

    @Override
    public Map<String, WorldImpl> getWorldsMap()
    {
        return new HashMap<>(this.worlds);
    }

    @Override
    public Map<String, WorldGroupImpl> getGroupsMap()
    {
        return new HashMap<>(this.groups);
    }

    @Override
    public Collection<WorldImpl> getWorlds()
    {
        return new HashSet<>(this.worlds.values());
    }

    @Override
    public Collection<WorldGroupImpl> getGroups()
    {
        return new HashSet<>(this.groups.values());
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

    public void init(final File worldsFile)
    {
        Validate.notNull(worldsFile, "File can't be null");
        if (! worldsFile.exists())
        {
            worldsFile.mkdirs();
            return;
        }
        if (worldsFile.isDirectory())
        {
            final Template<WorldsConfig> cfgTemp = TemplateCreator.getTemplate(WorldsConfig.class);
            final File f = new File(worldsFile, "worlds.yml");
            boolean needWrite = true;
            if (f.exists())
            {
                final DioriteYaml dy = new DioriteYaml();
                try
                {
                    this.config = dy.loadAs(new FileInputStream(f), WorldsConfig.class);
                    if (this.config == null)
                    {
                        this.config = new WorldsConfig();
                    }
                    else
                    {
                        needWrite = false;
                    }
                } catch (final FileNotFoundException ignored) // should be never thrown.
                {
                    throw new AssertionError("Config file not found...", ignored);
                }
            }
            else
            {
                this.config = new WorldsConfig();
                try
                {
                    DioriteUtils.createFile(f);
                } catch (final IOException e)
                {
                    throw new RuntimeException("Can't create configuration file!", e);
                }
            }
            if (needWrite)
            {
                try
                {
                    cfgTemp.dump(f, this.config);
                } catch (final IOException e)
                {
                    throw new RuntimeException("Can't dump configuration file!", e);
                }
            }

            for (final WorldGroupConfig wgc : this.config.getGroups())
            {
                final WorldGroupImpl wgImpl = new WorldGroupImpl(wgc.getName(), new File(worldsFile, wgc.getName()));
                this.groups.put(wgc.getName(), wgImpl);
                for (final WorldConfig wc : wgc.getWorlds())
                {
                    final File wFile = new File(wgImpl.getDataFolder(), wc.getName());
                    final WorldImpl wImpl = new WorldImpl(new McaChunkIO(wFile), wc.getName(), wgImpl, wc.getDimension(), wc.getGenerator(), wc.getGeneratorSettings());
                    this.loadWorld(wImpl, wc);
                    wgImpl.addWorld(wImpl);
                }
            }
        }
        else
        {
            throw new IllegalArgumentException("Worlds can be only loaded from directory. Not from file: " + worldsFile.getPath());
        }
    }

    private void loadWorld(final WorldImpl world, final WorldConfig worldConfig)
    {
        world.setNoUpdateMode(true);
        final File file = new File(world.getWorldFile().getWorldDir(), "level.dat");
        if (file.exists())
        {
            try
            {
                final NbtTagCompound tag = ((NbtTagCompound) NbtInputStream.readTagCompressed(file, NbtLimiter.getUnlimited())).getCompound("Data");
                world.loadNBT(tag, worldConfig);
            } catch (final IOException e)
            {
                System.err.println("Can't read world in: " + world.getWorldFile().getWorldDir().getPath());
                e.printStackTrace();
            }
        }
        else
        {
            world.loadNBT(new NbtTagCompound(), worldConfig);
            { // write
                try (final NbtOutputStream os = NbtOutputStream.getCompressed(file))
                {
                    os.write(world.writeTo(new NbtTagCompound("Data")));
                    os.flush();
                    os.close();
                } catch (final IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
        this.addWorld(world);
        world.getChunkManager().loadBase(world.getForceLoadedRadius(), world.getSpawn().toBlockLocation());
        world.setNoUpdateMode(false);
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("worlds", this.worlds).toString();
    }

    @Override
    public void doTick()
    {
        this.worlds.values().forEach(WorldImpl::doTick);
    }
}
