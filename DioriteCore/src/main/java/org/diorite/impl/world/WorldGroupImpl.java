package org.diorite.impl.world;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.impl.Tickable;
import org.diorite.world.WorldGroup;

public class WorldGroupImpl implements WorldGroup, Tickable
{
    private final String name;
    private final File   dir;
    private final File   playersDir;
    private final HashSet<WorldImpl> worlds = new HashSet<>(5);

    public WorldGroupImpl(final String name, final File dir)
    {
        this.name = name;
        this.dir = dir;
        this.playersDir = new File(dir, "_PlayerData_");
        this.playersDir.mkdirs();
    }

    public void addWorld(final WorldImpl world)
    {
        this.worlds.add(world);
    }

    @Override
    public Set<WorldImpl> getWorlds()
    {
        return this.worlds;
    }

    @Override
    public String getName()
    {
        return this.name;
    }

    @Override
    public File getDataFolder()
    {
        return this.dir;
    }

    @Override
    public File getPlayerDataFolder()
    {
        return this.playersDir;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("name", this.name).append("dir", this.dir).append("playersDir", this.playersDir).append("worlds", this.worlds).toString();
    }

    @Override
    public void doTick(final int tps)
    {
        this.worlds.forEach(w -> w.doTick(tps));
    }
}
