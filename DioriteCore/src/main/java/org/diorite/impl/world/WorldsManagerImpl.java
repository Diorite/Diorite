package org.diorite.impl.world;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.impl.Main;
import org.diorite.nbt.NbtInputStream;
import org.diorite.nbt.NbtTagCompound;
import org.diorite.nbt.NbtTagString;
import org.diorite.nbt.NbtTagType;
import org.diorite.world.Dimension;
import org.diorite.world.World;
import org.diorite.world.WorldsManager;

public class WorldsManagerImpl implements WorldsManager
{
    private final String defaultWorld;
    private final Map<String, World> worlds = new ConcurrentHashMap<>(5, .1f, 4);

    public WorldsManagerImpl(final String defaultWorld)
    {
        this.defaultWorld = defaultWorld;
    }

    public void addWorld(final World world)
    {
        this.worlds.put(world.getName(), world);
    }

    public Collection<World> getWorlds()
    {
        return new HashSet<>(this.worlds.values());
    }

    public void init(final File worldsFile)
    {
        Validate.notNull(worldsFile, "File can't be null");
        if (! worldsFile.exists())
        {
            worldsFile.mkdirs();
            this.createDefaultWorlds();
            return;
        }
        if (worldsFile.isDirectory())
        {
            final File file = new File(worldsFile, "level.dat");
            if (file.exists())
            {
                this.loadWorld(worldsFile);
            }
            else
            {
                Arrays.stream(worldsFile.listFiles(File::isDirectory)).forEach(this::loadWorld);
            }
            if (this.worlds.isEmpty())
            {
                this.createDefaultWorlds();
            }
        }
        else
        {
            throw new IllegalArgumentException("Worlds can be only loaded from directory. Not from file: " + worldsFile.getPath());
        }
    }

    private void loadWorld(final File worldDir)
    {
        // TODO: load world from file
        final File file = new File(worldDir, "level.dat");
        if (file.exists())
        {
            try
            {
                final NbtTagCompound tag = ((NbtTagCompound)NbtInputStream.readTagCompressed(file)).getCompound("Data");
                String worldName = tag.getString("LevelName");
                if (worldName == null)
                {
                    tag.addTag(new NbtTagString("LevelName", worldName = worldDir.getName()));
                }
                String gen = tag.getString("generatorName");
                if (gen == null)
                {
                    tag.addTag(new NbtTagString("generatorName", gen = "default"));
                }
                String genOpt = tag.getString("generatorOptions");
                if (genOpt == null)
                {
                    tag.addTag(new NbtTagString("generatorOptions", genOpt = ""));
                }
                final WorldImpl world = new WorldImpl(worldName, Dimension.getByID(tag.getInt("Diorite.Dimension", 0)), worldDir, gen, genOpt);
                world.setNoUpdateMode(true);
                world.loadNBT(tag);
                { // write
//                    try (final NbtOutputStream os = new NbtOutputStream(new GZIPOutputStream(new FileOutputStream(file, false))))
//                    {
//                        // TODO: update file
//                    }catch (final IOException e)
//                    {
//                        e.printStackTrace();
//                    }
                }
                this.addWorld(world);
                world.getChunkManager().loadBase(world.getForceLoadedRadius(), world.getSpawn().toBlockLocation());
                world.setNoUpdateMode(false);
            } catch (final IOException e)
            {
                System.err.println("Can't read world in: " + worldDir.getPath());
                e.printStackTrace();
            }
        }
        else
        {
            System.err.println("World dir don't contains level.dat file! (" + worldDir.getPath() + ")");
        }
    }

    private void createDefaultWorlds()
    {
        this.addWorld(new WorldImpl(this.defaultWorld, Dimension.OVERWORLD, null, "default")); // TODO: default generator
        // TODO
    }

    @Override
    public World getDefaultWorld()
    {
        World defWorld = this.getWorld(this.defaultWorld);
        if (defWorld == null)
        {
            this.createDefaultWorlds();
            defWorld = this.getWorld(this.defaultWorld);
        }
        return defWorld;
    }

    @Override
    public World getWorld(final UUID uuid)
    {
        return this.worlds.get(uuid);
    }

    @Override
    public World getWorld(final String name)
    {
        for (final World world : this.worlds.values())
        {
            if (world.getName().equalsIgnoreCase(name))
            {
                return world;
            }
        }
        return null;
    }
    // TODO: create world and others
    // TODO: maybe some multi-world support by default, separate data between selected worlds etc...

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("worlds", this.worlds).toString();
    }
}
