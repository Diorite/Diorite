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
    // TODO

    private static final Map<String, Particle>   byName = new SimpleStringHashMap<>(40, SMALL_LOAD_FACTOR);
    private static final TIntObjectMap<Particle> byID   = new TIntObjectHashMap<>(40, SMALL_LOAD_FACTOR);

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
    }
}