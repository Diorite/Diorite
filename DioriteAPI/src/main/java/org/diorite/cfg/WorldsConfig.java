package org.diorite.cfg;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.Difficulty;
import org.diorite.GameMode;
import org.diorite.cfg.annotations.CfgClass;
import org.diorite.cfg.annotations.CfgComment;
import org.diorite.cfg.annotations.CfgFooterComment;
import org.diorite.cfg.annotations.defaults.CfgBooleanDefault;
import org.diorite.cfg.annotations.defaults.CfgByteDefault;
import org.diorite.cfg.annotations.defaults.CfgCustomDefault;
import org.diorite.cfg.annotations.defaults.CfgDelegateDefault;
import org.diorite.cfg.annotations.defaults.CfgIntDefault;
import org.diorite.cfg.annotations.defaults.CfgStringDefault;
import org.diorite.utils.math.DioriteRandomUtils;
import org.diorite.world.Dimension;
import org.diorite.world.HardcoreSettings.HardcoreAction;

@SuppressWarnings("HardcodedFileSeparator")
@CfgClass(name = "WorldsConfig")
@CfgComment("Welcome in Diorite worlds configuration file.")
@CfgFooterComment("End of configuration!")
@CfgFooterComment("=====================")
public class WorldsConfig
{
    private static final List<WorldGroupConfig> def1 = Collections.singletonList(new WorldGroupConfig());
    private static final List<WorldConfig>      def2 = new ArrayList<>(3);

    static
    {
        final WorldGroupConfig g = def1.get(0);
        g.name = "default";
        {
            final WorldConfig w = new WorldConfig();
            w.seed = defaultSeed();
            def2.add(w);
        }
        {
            final WorldConfig w = new WorldConfig();
            w.name = "world_nether";
            w.seed = defaultSeed();
            w.dimension = Dimension.NETHER;
            def2.add(w);
        }
        {
            final WorldConfig w = new WorldConfig();
            w.name = "world_end";
            w.seed = defaultSeed();
            w.dimension = Dimension.END;
            def2.add(w);
        }
        g.worlds = new ArrayList<>(def2);
    }

    private static List<WorldGroupConfig> defaultGroupConfig()
    {
        return def1;
    }

    private static List<WorldConfig> defaultWorldsConfig()
    {
        return def2;
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
    private String defaultWorld = "world";

    @CfgComment("All groups")
    @CfgDelegateDefault("org.diorite.cfg.WorldsConfig#defaultGroupConfig")
    private List<WorldGroupConfig> groups = new ArrayList<>(def1);

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
    }

    @CfgClass(name = "WorldConfig")
    @CfgComment("Single world configuration")
    public static class WorldConfig
    {
        @CfgComment("Name of world, must be unique.")
        @CfgStringDefault("world")
        private String name = "world";

        @CfgComment("Default gamemode for new players.")
        @CfgDelegateDefault("org.diorite.cfg.WorldsConfig#defaultGamemode")
        private GameMode gamemode = GameMode.SURVIVAL;

        @CfgComment("If true, then gamemode will be always changed on join to default one.")
        @CfgBooleanDefault(false)
        private boolean forceGamemode = false;

        @CfgComment("Difficulty for world.")
        @CfgDelegateDefault("org.diorite.cfg.WorldsConfig#defaultDifficulty")
        private Difficulty difficulty = Difficulty.NORMAL;

        @CfgComment("Enable PvP on the server. Players shooting themselves with arrows will only receive damage if PvP is enabled.")
        @CfgBooleanDefault(true)
        private boolean pvp = true;

        @CfgComment("If world is in hardcore mode.")
        @CfgBooleanDefault(false)
        private boolean hardcore = false;

        @CfgComment("Action when player die.")
        @CfgHardcoreActionDefault(HardcoreAction.BAN_PLAYER)
        private HardcoreAction hardcoreAction = HardcoreAction.BAN_PLAYER;

        @CfgComment("This part of world are always loaded. (radius of chunks from spawn point)")
        @CfgByteDefault(10)
        private byte forceLoadedRadius = 10;

        @CfgComment("X coordinates of spawn location.")
        private int spawnX;

        @CfgComment("Y coordinates of spawn location, -1 means that diorite should find highest block.")
        @CfgIntDefault(- 1)
        private int spawnY = - 1;

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
        private Dimension dimension = Dimension.OVERWORLD;

        @CfgComment("Generator for world.")
        @CfgStringDefault("default")
        private String generator = "diorite:default";

        @CfgComment("Generator settings, every generator may have own options here.")
        @CfgDelegateDefault("{emptyMap}")
        private Map<String, Object> generatorSettings = new HashMap<>(1);

        public String getName()
        {
            return this.name;
        }

        public void setName(final String name)
        {
            this.name = name;
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
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("defaultWorld", this.defaultWorld).append("groups", this.groups).toString();
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    @CfgCustomDefault(HardcoreAction.class)
    public @interface CfgHardcoreActionDefault
    {
        HardcoreAction value();
    }
}
