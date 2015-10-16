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

package org.diorite.impl.cfg;

import java.io.File;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.Difficulty;
import org.diorite.GameMode;
import org.diorite.cfg.WorldsConfig;
import org.diorite.cfg.annotations.CfgComment;
import org.diorite.cfg.annotations.defaults.CfgBooleanDefault;
import org.diorite.cfg.annotations.defaults.CfgByteDefault;
import org.diorite.cfg.annotations.defaults.CfgCustomDefault;
import org.diorite.cfg.annotations.defaults.CfgDelegateDefault;
import org.diorite.cfg.annotations.defaults.CfgDelegateImport;
import org.diorite.cfg.annotations.defaults.CfgIntDefault;
import org.diorite.cfg.annotations.defaults.CfgStringDefault;
import org.diorite.cfg.system.Template;
import org.diorite.cfg.system.TemplateCreator;
import org.diorite.utils.math.DioriteRandomUtils;
import org.diorite.world.Dimension;
import org.diorite.world.HardcoreSettings.HardcoreAction;
import org.diorite.world.WorldType;

@SuppressWarnings({"SimplifiableIfStatement"})
public class WorldsConfigImpl implements WorldsConfig
{
    static final List<WorldConfigImpl>      def2;
    static final List<WorldGroupConfigImpl> def1;

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("defaultWorld", this.defaultWorld).append("groups", this.groups).toString();
    }

    static
    {
        final Template<WorldConfigImpl> template = TemplateCreator.getTemplate(WorldConfigImpl.class, true);
        def2 = new ArrayList<>(3);
        {
            final WorldConfigImpl w = template.fillDefaults(new WorldConfigImpl());
            w.seed = DioriteRandomUtils.nextLong();
            def2.add(w);
        }
        {
            final WorldConfigImpl w = template.fillDefaults(new WorldConfigImpl());
            w.name = "world_nether";
            w.enabled = false;
            w.seed = DioriteRandomUtils.nextLong();
            w.dimension = Dimension.NETHER;
            def2.add(w);
        }
        {
            final WorldConfigImpl w = template.fillDefaults(new WorldConfigImpl());
            w.name = "world_end";
            w.enabled = false;
            w.seed = DioriteRandomUtils.nextLong();
            w.dimension = Dimension.END;
            def2.add(w);
        }
        def1 = Collections.singletonList(new WorldGroupConfigImpl());
        final WorldGroupConfigImpl g = def1.get(0);
        g.name = "default";
        g.worlds = new ArrayList<>(def2);
    }

    @CfgComment("Default world where players are logged in.")
    @CfgStringDefault("world")
    private String defaultWorld;

    @CfgComment("In this folder all world groups (and worlds) will be stored.")
    @CfgStringDefault("worlds")
    private File worldsDir;

    @CfgComment("All groups, every group have separate players data. (EQ, level etc..)")
    @CfgDelegateDefault("adv| return org.diorite.impl.cfg.WorldsConfigImpl.def1;")
    private List<WorldGroupConfigImpl> groups;

    @Override
    public File getWorldsDir()
    {
        return this.worldsDir;
    }

    public void setWorldsDir(final File worldsDir)
    {
        this.worldsDir = worldsDir;
    }

    @Override
    public String getDefaultWorld()
    {
        return this.defaultWorld;
    }

    public void setDefaultWorld(final String defaultWorld)
    {
        this.defaultWorld = defaultWorld;
    }

    @Override
    public List<WorldGroupConfigImpl> getGroups()
    {
        return this.groups;
    }

    public void setGroups(final List<WorldGroupConfigImpl> groups)
    {
        this.groups = groups;
    }

    @CfgComment("World group, every group have own separate player data.")
    public static class WorldGroupConfigImpl implements WorldGroupConfig
    {

        @CfgComment("Name of world group.")
        @CfgStringDefault("default")
        private String name = "default";

        @CfgComment("All worlds for this group.")
        @CfgDelegateDefault("new ArrayList(org.diorite.impl.cfg.WorldsConfigImpl.def2)")
        private List<WorldConfigImpl> worlds = new ArrayList<>(def2);

        @Override
        public String getName()
        {
            return this.name;
        }

        public void setName(final String name)
        {
            this.name = name;
        }

        @Override
        public List<WorldConfigImpl> getWorlds()
        {
            return this.worlds;
        }

        public void setWorlds(final List<WorldConfigImpl> worlds)
        {
            this.worlds = worlds;
        }

        @Override
        public String toString()
        {
            return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("name", this.name).append("worlds", this.worlds).toString();
        }

        @Override
        public boolean equals(final Object o)
        {
            if (this == o)
            {
                return true;
            }
            if (! (o instanceof WorldGroupConfigImpl))
            {
                return false;
            }

            final WorldGroupConfigImpl that = (WorldGroupConfigImpl) o;

            if ((this.name != null) ? ! this.name.equals(that.name) : (that.name != null))
            {
                return false;
            }
            return ! ((this.worlds != null) ? ! this.worlds.equals(that.worlds) : (that.worlds != null));

        }

        @Override
        public int hashCode()
        {
            int result = (this.name != null) ? this.name.hashCode() : 0;
            result = (31 * result) + ((this.worlds != null) ? this.worlds.hashCode() : 0);
            return result;
        }
    }

    @CfgComment("Single world configuration")
    public static class WorldConfigImpl implements WorldConfig
    {
        @CfgComment("Name of world, must be unique.")
        @CfgStringDefault("world")
        private String name;

        @CfgComment("If world should be loaded on start.")
        @CfgBooleanDefault(true)
        private boolean enabled;

        @CfgComment("If world should vanilla compatible, if false, world may not load on vanilla clients/servers")
        @CfgBooleanDefault(false)
        private boolean vanillaCompatible;

        @CfgComment("Default gamemode for new players.")
        @CfgDelegateImport("org.diorite")
        @CfgDelegateDefault("GameMode.SURVIVAL")
        private GameMode gamemode;

        @CfgComment("If true, then gamemode will be always changed on join to default one.")
        @CfgBooleanDefault(false)
        private boolean forceGamemode;

        @CfgComment("Difficulty for world.")
        @CfgDelegateImport("org.diorite")
        @CfgDelegateDefault("Difficulty.NORMAL")
        private Difficulty difficulty;

        @CfgComment("Enable PvP on the server. Players shooting themselves with arrows will only receive damage if PvP is enabled.")
        @CfgBooleanDefault(true)
        private boolean pvp;

        @CfgComment("If world is in hardcore mode.")
        @CfgBooleanDefault(false)
        private boolean hardcore;

        @CfgComment("Action when player die.")
        @CfgHardcoreActionDefault(HardcoreAction.BAN_PLAYER)
        private HardcoreAction hardcoreAction;

        @CfgComment("This part of world are always loaded. (radius of chunks from spawn point)")
        @CfgByteDefault(10)
        private byte forceLoadedRadius;

        @CfgComment("X coordinates of spawn location.")
        private double spawnX;

        @CfgComment("Y coordinates of spawn location, -1 means that diorite should find highest block.")
        @CfgIntDefault(- 1)
        private double spawnY;

        @CfgComment("Z coordinates of spawn location.")
        private double spawnZ;

        @CfgComment("Yaw roation coordinates of spawn location.")
        private float spawnYaw;

        @CfgComment("Pitch roation coordinates of spawn location.")
        private float spawnPitch;

        @CfgComment("Seed of world.")
        @CfgDelegateImport("org.diorite.utils.math")
        @CfgDelegateDefault("DioriteRandomUtils.nextLong()")
        private long seed;

        @CfgComment("Dimension of world.")
        @CfgDelegateImport("org.diorite.world")
        @CfgDelegateDefault("Dimension.OVERWORLD")
        private Dimension dimension;

        @CfgComment("Type of world.")
        @CfgDelegateImport("org.diorite.world")
        @CfgDelegateDefault("WorldType.NORMAL")
        private WorldType worldType;

        @CfgComment("Generator for world.")
        @CfgStringDefault("diorite:default")
        private String generator;

        @CfgComment("Generator settings, every generator may have own options here.")
        @CfgDelegateDefault("{emptyMap}")
        private Map<String, Object> generatorSettings;

        @Override
        public String getName()
        {
            return this.name;
        }

        public void setName(final String name)
        {
            this.name = name;
        }

        @Override
        public boolean isEnabled()
        {
            return this.enabled;
        }

        public void setEnabled(final boolean enabled)
        {
            this.enabled = enabled;
        }

        @Override
        public boolean isVanillaCompatible()
        {
            return this.vanillaCompatible;
        }

        @Override
        public GameMode getGamemode()
        {
            return this.gamemode;
        }

        public void setGamemode(final GameMode gamemode)
        {
            this.gamemode = gamemode;
        }

        @Override
        public boolean isForceGamemode()
        {
            return this.forceGamemode;
        }

        public void setForceGamemode(final boolean forceGamemode)
        {
            this.forceGamemode = forceGamemode;
        }

        @Override
        public Difficulty getDifficulty()
        {
            return this.difficulty;
        }

        public void setDifficulty(final Difficulty difficulty)
        {
            this.difficulty = difficulty;
        }

        @Override
        public boolean isPvp()
        {
            return this.pvp;
        }

        public void setPvp(final boolean pvp)
        {
            this.pvp = pvp;
        }

        @Override
        public long getSeed()
        {
            return this.seed;
        }

        public void setSeed(final long seed)
        {
            this.seed = seed;
        }

        @Override
        public Dimension getDimension()
        {
            return this.dimension;
        }

        public void setDimension(final Dimension dimension)
        {
            this.dimension = dimension;
        }

        @Override
        public WorldType getWorldType()
        {
            return this.worldType;
        }

        public void setWorldType(final WorldType worldType)
        {
            this.worldType = worldType;
        }

        @Override
        public String getGenerator()
        {
            return this.generator;
        }

        public void setGenerator(final String generator)
        {
            this.generator = generator;
        }

        @Override
        public Map<String, Object> getGeneratorSettings()
        {
            return this.generatorSettings;
        }

        public void setGeneratorSettings(final Map<String, Object> generatorSettings)
        {
            this.generatorSettings = generatorSettings;
        }

        @Override
        public boolean isHardcore()
        {
            return this.hardcore;
        }

        public void setHardcore(final boolean hardcore)
        {
            this.hardcore = hardcore;
        }

        @Override
        public HardcoreAction getHardcoreAction()
        {
            return this.hardcoreAction;
        }

        public void setHardcoreAction(final HardcoreAction hardcoreAction)
        {
            this.hardcoreAction = hardcoreAction;
        }

        @Override
        public byte getForceLoadedRadius()
        {
            return this.forceLoadedRadius;
        }

        public void setForceLoadedRadius(final byte forceLoadedRadius)
        {
            this.forceLoadedRadius = forceLoadedRadius;
        }

        @Override
        public double getSpawnX()
        {
            return this.spawnX;
        }

        public void setSpawnX(final double spawnX)
        {
            this.spawnX = spawnX;
        }

        @Override
        public double getSpawnY()
        {
            return this.spawnY;
        }

        public void setSpawnY(final double spawnY)
        {
            this.spawnY = spawnY;
        }

        @Override
        public double getSpawnZ()
        {
            return this.spawnZ;
        }

        public void setSpawnZ(final double spawnZ)
        {
            this.spawnZ = spawnZ;
        }

        @Override
        public float getSpawnYaw()
        {
            return this.spawnYaw;
        }

        public void setSpawnYaw(final float spawnYaw)
        {
            this.spawnYaw = spawnYaw;
        }

        @Override
        public float getSpawnPitch()
        {
            return this.spawnPitch;
        }

        public void setSpawnPitch(final float spawnPitch)
        {
            this.spawnPitch = spawnPitch;
        }

        @Override
        public String toString()
        {
            return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("name", this.name).append("gamemode", this.gamemode).append("forceGamemode", this.forceGamemode).append("difficulty", this.difficulty).append("pvp", this.pvp).append("seed", this.seed).append("dimension", this.dimension).toString();
        }

        @Override
        public boolean equals(final Object o)
        {
            if (this == o)
            {
                return true;
            }
            if (! (o instanceof WorldConfigImpl))
            {
                return false;
            }

            final WorldConfigImpl that = (WorldConfigImpl) o;

            if (this.enabled != that.enabled)
            {
                return false;
            }
            if (this.forceGamemode != that.forceGamemode)
            {
                return false;
            }
            if (this.pvp != that.pvp)
            {
                return false;
            }
            if (this.hardcore != that.hardcore)
            {
                return false;
            }
            if (this.forceLoadedRadius != that.forceLoadedRadius)
            {
                return false;
            }
            if (this.spawnX != that.spawnX)
            {
                return false;
            }
            if (this.spawnY != that.spawnY)
            {
                return false;
            }
            if (this.spawnZ != that.spawnZ)
            {
                return false;
            }
            if (Float.compare(that.spawnYaw, this.spawnYaw) != 0)
            {
                return false;
            }
            if (Float.compare(that.spawnPitch, this.spawnPitch) != 0)
            {
                return false;
            }
            if (this.seed != that.seed)
            {
                return false;
            }
            if ((this.name != null) ? ! this.name.equals(that.name) : (that.name != null))
            {
                return false;
            }
            if ((this.gamemode != null) ? ! this.gamemode.equals(that.gamemode) : (that.gamemode != null))
            {
                return false;
            }
            if ((this.difficulty != null) ? ! this.difficulty.equals(that.difficulty) : (that.difficulty != null))
            {
                return false;
            }
            if (this.hardcoreAction != that.hardcoreAction)
            {
                return false;
            }
            if ((this.dimension != null) ? ! this.dimension.equals(that.dimension) : (that.dimension != null))
            {
                return false;
            }
            if ((this.generator != null) ? ! this.generator.equals(that.generator) : (that.generator != null))
            {
                return false;
            }
            return ! ((this.generatorSettings != null) ? ! this.generatorSettings.equals(that.generatorSettings) : (that.generatorSettings != null));

        }

        @Override
        public int hashCode()
        {
            int result;
            long temp;
            result = this.name.hashCode();
            result = (31 * result) + (this.enabled ? 1 : 0);
            result = (31 * result) + (this.vanillaCompatible ? 1 : 0);
            result = (31 * result) + this.gamemode.hashCode();
            result = (31 * result) + (this.forceGamemode ? 1 : 0);
            result = (31 * result) + this.difficulty.hashCode();
            result = (31 * result) + (this.pvp ? 1 : 0);
            result = (31 * result) + (this.hardcore ? 1 : 0);
            result = (31 * result) + this.hardcoreAction.hashCode();
            result = (31 * result) + (int) this.forceLoadedRadius;
            temp = Double.doubleToLongBits(this.spawnX);
            result = (31 * result) + (int) (temp ^ (temp >>> 32));
            temp = Double.doubleToLongBits(this.spawnY);
            result = (31 * result) + (int) (temp ^ (temp >>> 32));
            temp = Double.doubleToLongBits(this.spawnZ);
            result = (31 * result) + (int) (temp ^ (temp >>> 32));
            result = (31 * result) + ((this.spawnYaw != + 0.0f) ? Float.floatToIntBits(this.spawnYaw) : 0);
            result = (31 * result) + ((this.spawnPitch != + 0.0f) ? Float.floatToIntBits(this.spawnPitch) : 0);
            result = (31 * result) + (int) (this.seed ^ (this.seed >>> 32));
            result = (31 * result) + this.dimension.hashCode();
            result = (31 * result) + this.worldType.hashCode();
            result = (31 * result) + ((this.generator != null) ? this.generator.hashCode() : 0);
            result = (31 * result) + ((this.generatorSettings != null) ? this.generatorSettings.hashCode() : 0);
            return result;
        }
    }

    @Override
    public boolean equals(final Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (! (o instanceof WorldsConfigImpl))
        {
            return false;
        }

        final WorldsConfigImpl that = (WorldsConfigImpl) o;

        if ((this.defaultWorld != null) ? ! this.defaultWorld.equals(that.defaultWorld) : (that.defaultWorld != null))
        {
            return false;
        }
        return ! ((this.groups != null) ? ! this.groups.equals(that.groups) : (that.groups != null));

    }

    @Override
    public int hashCode()
    {
        int result = (this.defaultWorld != null) ? this.defaultWorld.hashCode() : 0;
        result = (31 * result) + ((this.groups != null) ? this.groups.hashCode() : 0);
        return result;
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    @CfgCustomDefault(HardcoreAction.class)
    public @interface CfgHardcoreActionDefault
    {
        HardcoreAction value();
    }
}
