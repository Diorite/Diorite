package org.diorite;

import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.utils.SimpleEnum;
import org.diorite.utils.collections.SimpleStringHashMap;

import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.hash.TIntObjectHashMap;

public class Particle implements SimpleEnum<Particle>
{
    public static final Particle EXPLOSION_NORMAL       = new Particle("EXPLOSION_NORMAL", "explode", 0);
    public static final Particle EXPLOSION_LARGE        = new Particle("EXPLOSION_LARGE", "largeexplode", 1);
    public static final Particle EXPLOSION_HUGE         = new Particle("EXPLOSION_HUGE", "hugeexplosion", 2);
    public static final Particle FIREWORKS_SPARK        = new Particle("FIREWORKS_SPARK", "fireworksSpark", 3);
    public static final Particle WATER_BUBBLE           = new Particle("WATER_BUBBLE", "bubble", 4);
    public static final Particle WATER_SPLASH           = new Particle("WATER_SPLASH", "splash", 5);
    public static final Particle WATER_WAKE             = new Particle("WATER_WAKE", "wake", 6);
    public static final Particle SUSPENDED              = new Particle("SUSPENDED", "suspended", 7);
    public static final Particle SUSPENDED_DEPTH        = new Particle("SUSPENDED_DEPTH", "depthsuspend", 8);
    public static final Particle CRIT                   = new Particle("CRIT", "crit", 9);
    public static final Particle CRIT_MAGIC             = new Particle("CRIT_MAGIC", "magicCrit", 10);
    public static final Particle SMOKE_NORMAL           = new Particle("SMOKE_NORMAL", "smoke", 11);
    public static final Particle SMOKE_LARGE            = new Particle("SMOKE_LARGE", "largesmoke", 12);
    public static final Particle SPELL                  = new Particle("SPELL", "spell", 13);
    public static final Particle SPELL_INSTANT          = new Particle("SPELL_INSTANT", "instantSpell", 14);
    public static final Particle SPELL_MOB              = new Particle("SPELL_MOB", "mobSpell", 15);
    public static final Particle SPELL_MOB_AMBIENT      = new Particle("SPELL_MOB_AMBIENT", "mobSpellAmbient", 16);
    public static final Particle SPELL_WITCH            = new Particle("SPELL_WITCH", "witchMagic", 17);
    public static final Particle DRIP_WATER             = new Particle("DRIP_WATER", "dripWater", 18);
    public static final Particle DRIP_LAVA              = new Particle("DRIP_LAVA", "dripLava", 19);
    public static final Particle VILLAGER_ANGRY         = new Particle("VILLAGER_ANGRY", "angryVillager", 20);
    public static final Particle VILLAGER_HAPPY         = new Particle("VILLAGER_HAPPY", "happyVillager", 21);
    public static final Particle TOWN_AURA              = new Particle("TOWN_AURA", "townaura", 22);
    public static final Particle NOTE                   = new Particle("NOTE", "note", 23);
    public static final Particle PORTAL                 = new Particle("PORTAL", "portal", 24);
    public static final Particle ENCHANTMENT_TABLE      = new Particle("ENCHANTMENT_TABLE", "enchantmenttable", 25);
    public static final Particle FLAME                  = new Particle("FLAME", "flame", 26);
    public static final Particle LAVA                   = new Particle("LAVA", "lava", 27);
    public static final Particle FOOTSTEP               = new Particle("FOOTSTEP", "footstep", 28);
    public static final Particle CLOUD                  = new Particle("CLOUD", "cloud", 29);
    public static final Particle REDSTONE               = new Particle("REDSTONE", "reddust", 30);
    public static final Particle SNOWBALL               = new Particle("SNOWBALL", "snowballpoof", 31);
    public static final Particle SNOW_SHOVEL            = new Particle("SNOW_SHOVEL", "snowshovel", 32);
    public static final Particle SLIME                  = new Particle("SLIME", "slime", 33);
    public static final Particle HEART                  = new Particle("HEART", "heart", 34);
    public static final Particle BARRIER                = new Particle("BARRIER", "barrier", 35);
    public static final Particle ITEM_CRACK             = new Particle("ITEM_CRACK", "iconcrack_", 36, 2);
    public static final Particle BLOCK_CRACK            = new Particle("BLOCK_CRACK", "blockcrack_", 37, 1);
    public static final Particle BLOCK_DUST             = new Particle("BLOCK_DUST", "blockdust_", 38, 1);
    public static final Particle WATER_DROP             = new Particle("WATER_DROP", "droplet", 39);
    public static final Particle ITEM_TAKE              = new Particle("ITEM_TAKE", "take", 40);
    public static final Particle MOB_APPEARANCE         = new Particle("MOB_APPEARANCE", "mobappearance", 41);

    private static final Map<String, Particle>   byName = new SimpleStringHashMap<>(42, SMALL_LOAD_FACTOR);
    private static final TIntObjectMap<Particle> byID   = new TIntObjectHashMap<>(42, SMALL_LOAD_FACTOR);

    private final String enumName;
    private final String particleName;
    private final int    id;
    private final int    dataSize;

    public Particle(final String enumName, final String name, final int id, final int dataSize)
    {
        this.enumName = enumName;
        this.particleName = name;
        this.id = id;
        this.dataSize = dataSize;
    }

    public Particle(final String enumName, final String name, final int id)
    {
        this(enumName, name, id, 0);
    }

    @Override
    public String name()
    {
        return this.enumName;
    }

    @Override
    public int getId()
    {
        return this.id;
    }

    public int getDataSize()
    {
        return this.dataSize;
    }

    public String getMinecraftParticleName()
    {
        return this.particleName;
    }

    @Override
    public Particle byId(final int id)
    {
        return byID.get(id);
    }

    @Override
    public Particle byName(final String name)
    {
        return byName.get(name);
    }

    public static Particle getParticleById(final int id)
    {
        return byID.get(id);
    }

    public static Particle getParticleByName(final String name)
    {
        return byName.get(name);
    }

    public static void register(final Particle particle)
    {
        byID.put(particle.getId(), particle);
        byName.put(particle.name(), particle);
    }

    /**
     * @return all values in array.
     */
    public static Particle[] values()
    {
        return byID.values(new Particle[byID.size()]);
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("enumName", this.enumName).append("particleName", this.particleName).append("id", this.id).append("dataSize", this.dataSize).toString();
    }

    static
    {
        register(EXPLOSION_NORMAL);
        register(EXPLOSION_HUGE);
        register(EXPLOSION_LARGE);
        register(FIREWORKS_SPARK);
        register(WATER_BUBBLE);
        register(WATER_SPLASH);
        register(WATER_WAKE);
        register(SUSPENDED);
        register(SUSPENDED_DEPTH);
        register(CRIT);
        register(CRIT_MAGIC);
        register(SMOKE_NORMAL);
        register(SMOKE_LARGE);
        register(SPELL);
        register(SPELL_INSTANT);
        register(SPELL_MOB);
        register(SPELL_MOB_AMBIENT);
        register(SPELL_WITCH);
        register(DRIP_LAVA);
        register(DRIP_WATER);
        register(VILLAGER_ANGRY);
        register(VILLAGER_HAPPY);
        register(TOWN_AURA);
        register(PORTAL);
        register(NOTE);
        register(ENCHANTMENT_TABLE);
        register(FLAME);
        register(LAVA);
        register(FOOTSTEP);
        register(CLOUD);
        register(REDSTONE);
        register(SNOWBALL);
        register(SNOW_SHOVEL);
        register(SLIME);
        register(HEART);
        register(BARRIER);
        register(ITEM_CRACK);
        register(BLOCK_CRACK);
        register(BLOCK_DUST);
        register(WATER_DROP);
        register(ITEM_TAKE);
        register(MOB_APPEARANCE);
    }
}