package org.diorite.cfg;

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
import org.diorite.cfg.annotations.CfgClass;
import org.diorite.cfg.annotations.CfgComment;
import org.diorite.cfg.annotations.defaults.CfgBooleanDefault;
import org.diorite.cfg.annotations.defaults.CfgByteDefault;
import org.diorite.cfg.annotations.defaults.CfgCustomDefault;
import org.diorite.cfg.annotations.defaults.CfgDelegateDefault;
import org.diorite.cfg.annotations.defaults.CfgIntDefault;
import org.diorite.cfg.annotations.defaults.CfgStringDefault;
import org.diorite.cfg.system.Template;
import org.diorite.cfg.system.TemplateCreator;
import org.diorite.utils.math.DioriteRandomUtils;
import org.diorite.world.Dimension;
import org.diorite.world.HardcoreSettings.HardcoreAction;

@SuppressWarnings({"HardcodedFileSeparator", "SimplifiableIfStatement"})
public class WorldsConfig
{
    private static final List<WorldConfig>      def2;
    private static final List<WorldGroupConfig> def1;

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("defaultWorld", this.defaultWorld).append("groups", this.groups).toString();
    }

    static
    {
        final Template<WorldConfig> template = TemplateCreator.getTemplate(WorldConfig.class, true);
        def2 = new ArrayList<>(3);
        {
            final WorldConfig w = template.fillDefaults(new WorldConfig());
            w.seed = defaultSeed();
            def2.add(w);
        }
        {
            final WorldConfig w = template.fillDefaults(new WorldConfig());
            w.name = "world_nether";
            w.seed = defaultSeed();
            w.dimension = Dimension.NETHER;
            def2.add(w);
        }
        {
            final WorldConfig w = template.fillDefaults(new WorldConfig());
            w.name = "world_end";
            w.seed = defaultSeed();
            w.dimension = Dimension.END;
            def2.add(w);
        }
        def1 = Collections.singletonList(new WorldGroupConfig());
        final WorldGroupConfig g = def1.get(0);
        g.name = "default";
        g.worlds = new ArrayList<>(def2);
    }


    private static List<WorldGroupConfig> defaultGroupConfig()
    {
        return def1;
    }

    private static List<WorldConfig> defaultWorldsConfig()
    {
        return new ArrayList<>(def2);
    }

    private static GameMode defaultGamemode()
    {
        return GameMode.SURVIVAL;
    }

    private static Dimension defaultDimension()
    {
        return Dimension.OVERWORLD;
    }

    private static Difficulty defaultDifficulty()
    {
        return Difficulty.NORMAL;
    }

    private static long defaultSeed()
    {
        return DioriteRandomUtils.getRandom().nextLong();
    }

    @CfgComment("Default world where players are logged in.")
    @CfgStringDefault("world")
    private String defaultWorld;

    @CfgComment("All groups, every group have separate players data. (EQ, level etc..)")
    @CfgDelegateDefault("org.diorite.cfg.WorldsConfig#defaultGroupConfig")
    private List<WorldGroupConfig> groups;

    public String getDefaultWorld()
    {
        return this.defaultWorld;
    }

    public void setDefaultWorld(final String defaultWorld)
    {
        this.defaultWorld = defaultWorld;
    }

    public List<WorldGroupConfig> getGroups()
    {
        return this.groups;
    }

    public void setGroups(final List<WorldGroupConfig> groups)
    {
        this.groups = groups;
    }

    @CfgClass(name = "WorldGroupConfig")
    @CfgComment("World group, every group have own separate player data.")
    public static class WorldGroupConfig
    {

        @CfgComment("Name of world group.")
        @CfgStringDefault("default")
        private String name = "default";

        @CfgComment("All worlds for this group.")
        @CfgDelegateDefault("org.diorite.cfg.WorldsConfig#defaultWorldsConfig")
        private List<WorldConfig> worlds = new ArrayList<>(def2);

        public String getName()
        {
            return this.name;
        }

        public void setName(final String name)
        {
            this.name = name;
        }

        public List<WorldConfig> getWorlds()
        {
            return this.worlds;
        }

        public void setWorlds(final List<WorldConfig> worlds)
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
            if (! (o instanceof WorldGroupConfig))
            {
                return false;
            }

            final WorldGroupConfig that = (WorldGroupConfig) o;

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

    @CfgClass(name = "WorldConfig")
    @CfgComment("Single world configuration")
    public static class WorldConfig
    {
        @CfgComment("Name of world, must be unique.")
        @CfgStringDefault("world")
        private String name;

        @CfgComment("If world should be loaded on start.")
        @CfgBooleanDefault(true)
        private boolean enabled;

        @CfgComment("Default gamemode for new players.")
        @CfgDelegateDefault("org.diorite.cfg.WorldsConfig#defaultGamemode")
        private GameMode gamemode;

        @CfgComment("If true, then gamemode will be always changed on join to default one.")
        @CfgBooleanDefault(false)
        private boolean forceGamemode;

        @CfgComment("Difficulty for world.")
        @CfgDelegateDefault("org.diorite.cfg.WorldsConfig#defaultDifficulty")
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
        private int spawnX;

        @CfgComment("Y coordinates of spawn location, -1 means that diorite should find highest block.")
        @CfgIntDefault(- 1)
        private int spawnY;

        @CfgComment("Z coordinates of spawn location.")
        private int spawnZ;

        @CfgComment("Yaw roation coordinates of spawn location.")
        private float spawnYaw;

        @CfgComment("Pitch roation coordinates of spawn location.")
        private float spawnPitch;

        @CfgComment("Seed of world.")
        @CfgDelegateDefault("org.diorite.cfg.WorldsConfig#defaultSeed")
        private long seed;

        @CfgComment("Type/Dimension of world.")
        @CfgDelegateDefault("org.diorite.cfg.WorldsConfig#defaultDimension")
        private Dimension dimension;

        @CfgComment("Generator for world.")
        @CfgStringDefault("diorite:default")
        private String generator;

        @CfgComment("Generator settings, every generator may have own options here.")
        @CfgDelegateDefault("{emptyMap}")
        private Map<String, Object> generatorSettings;

        public String getName()
        {
            return this.name;
        }

        public void setName(final String name)
        {
            this.name = name;
        }

        public boolean isEnabled()
        {
            return this.enabled;
        }

        public void setEnabled(final boolean enabled)
        {
            this.enabled = enabled;
        }

        public GameMode getGamemode()
        {
            return this.gamemode;
        }

        public void setGamemode(final GameMode gamemode)
        {
            this.gamemode = gamemode;
        }

        public boolean isForceGamemode()
        {
            return this.forceGamemode;
        }

        public void setForceGamemode(final boolean forceGamemode)
        {
            this.forceGamemode = forceGamemode;
        }

        public Difficulty getDifficulty()
        {
            return this.difficulty;
        }

        public void setDifficulty(final Difficulty difficulty)
        {
            this.difficulty = difficulty;
        }

        public boolean isPvp()
        {
            return this.pvp;
        }

        public void setPvp(final boolean pvp)
        {
            this.pvp = pvp;
        }

        public long getSeed()
        {
            return this.seed;
        }

        public void setSeed(final long seed)
        {
            this.seed = seed;
        }

        public Dimension getDimension()
        {
            return this.dimension;
        }

        public void setDimension(final Dimension dimension)
        {
            this.dimension = dimension;
        }

        public String getGenerator()
        {
            return this.generator;
        }

        public void setGenerator(final String generator)
        {
            this.generator = generator;
        }

        public Map<String, Object> getGeneratorSettings()
        {
            return this.generatorSettings;
        }

        public void setGeneratorSettings(final Map<String, Object> generatorSettings)
        {
            this.generatorSettings = generatorSettings;
        }

        public boolean isHardcore()
        {
            return this.hardcore;
        }

        public void setHardcore(final boolean hardcore)
        {
            this.hardcore = hardcore;
        }

        public HardcoreAction getHardcoreAction()
        {
            return this.hardcoreAction;
        }

        public void setHardcoreAction(final HardcoreAction hardcoreAction)
        {
            this.hardcoreAction = hardcoreAction;
        }

        public byte getForceLoadedRadius()
        {
            return this.forceLoadedRadius;
        }

        public void setForceLoadedRadius(final byte forceLoadedRadius)
        {
            this.forceLoadedRadius = forceLoadedRadius;
        }

        public int getSpawnX()
        {
            return this.spawnX;
        }

        public void setSpawnX(final int spawnX)
        {
            this.spawnX = spawnX;
        }

        public int getSpawnY()
        {
            return this.spawnY;
        }

        public void setSpawnY(final int spawnY)
        {
            this.spawnY = spawnY;
        }

        public int getSpawnZ()
        {
            return this.spawnZ;
        }

        public void setSpawnZ(final int spawnZ)
        {
            this.spawnZ = spawnZ;
        }

        public float getSpawnYaw()
        {
            return this.spawnYaw;
        }

        public void setSpawnYaw(final float spawnYaw)
        {
            this.spawnYaw = spawnYaw;
        }

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
            if (! (o instanceof WorldConfig))
            {
                return false;
            }

            final WorldConfig that = (WorldConfig) o;

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
            int result = (this.name != null) ? this.name.hashCode() : 0;
            result = (31 * result) + (this.enabled ? 1 : 0);
            result = (31 * result) + ((this.gamemode != null) ? this.gamemode.hashCode() : 0);
            result = (31 * result) + (this.forceGamemode ? 1 : 0);
            result = (31 * result) + ((this.difficulty != null) ? this.difficulty.hashCode() : 0);
            result = (31 * result) + (this.pvp ? 1 : 0);
            result = (31 * result) + (this.hardcore ? 1 : 0);
            result = (31 * result) + ((this.hardcoreAction != null) ? this.hardcoreAction.hashCode() : 0);
            result = (31 * result) + (int) this.forceLoadedRadius;
            result = (31 * result) + this.spawnX;
            result = (31 * result) + this.spawnY;
            result = (31 * result) + this.spawnZ;
            result = (31 * result) + ((this.spawnYaw != + 0.0f) ? Float.floatToIntBits(this.spawnYaw) : 0);
            result = (31 * result) + ((this.spawnPitch != + 0.0f) ? Float.floatToIntBits(this.spawnPitch) : 0);
            result = (31 * result) + (int) (this.seed ^ (this.seed >>> 32));
            result = (31 * result) + ((this.dimension != null) ? this.dimension.hashCode() : 0);
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
        if (! (o instanceof WorldsConfig))
        {
            return false;
        }

        final WorldsConfig that = (WorldsConfig) o;

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
