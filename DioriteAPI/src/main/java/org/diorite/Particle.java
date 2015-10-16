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

package org.diorite;

import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.utils.SimpleEnum;
import org.diorite.utils.SimpleEnum.ASimpleEnum;
import org.diorite.utils.collections.maps.CaseInsensitiveMap;

import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.hash.TIntObjectHashMap;

@SuppressWarnings("MagicNumber")
public class Particle extends ASimpleEnum<Particle>
{
    static
    {
        init(Particle.class, 42);
    }

    public static final Particle EXPLOSION_NORMAL  = new Particle("EXPLOSION_NORMAL", "explode", 0);
    public static final Particle EXPLOSION_LARGE   = new Particle("EXPLOSION_LARGE", "largeexplode", 1);
    public static final Particle EXPLOSION_HUGE    = new Particle("EXPLOSION_HUGE", "hugeexplosion", 2);
    public static final Particle FIREWORKS_SPARK   = new Particle("FIREWORKS_SPARK", "fireworksSpark", 3);
    public static final Particle WATER_BUBBLE      = new Particle("WATER_BUBBLE", "bubble", 4);
    public static final Particle WATER_SPLASH      = new Particle("WATER_SPLASH", "splash", 5);
    public static final Particle WATER_WAKE        = new Particle("WATER_WAKE", "wake", 6);
    public static final Particle SUSPENDED         = new Particle("SUSPENDED", "suspended", 7);
    public static final Particle SUSPENDED_DEPTH   = new Particle("SUSPENDED_DEPTH", "depthsuspend", 8);
    public static final Particle CRIT              = new Particle("CRIT", "crit", 9);
    public static final Particle CRIT_MAGIC        = new Particle("CRIT_MAGIC", "magicCrit", 10);
    public static final Particle SMOKE_NORMAL      = new Particle("SMOKE_NORMAL", "smoke", 11);
    public static final Particle SMOKE_LARGE       = new Particle("SMOKE_LARGE", "largesmoke", 12);
    public static final Particle SPELL             = new Particle("SPELL", "spell", 13);
    public static final Particle SPELL_INSTANT     = new Particle("SPELL_INSTANT", "instantSpell", 14);
    public static final Particle SPELL_MOB         = new Particle("SPELL_MOB", "mobSpell", 15);
    public static final Particle SPELL_MOB_AMBIENT = new Particle("SPELL_MOB_AMBIENT", "mobSpellAmbient", 16);
    public static final Particle SPELL_WITCH       = new Particle("SPELL_WITCH", "witchMagic", 17);
    public static final Particle DRIP_WATER        = new Particle("DRIP_WATER", "dripWater", 18);
    public static final Particle DRIP_LAVA         = new Particle("DRIP_LAVA", "dripLava", 19);
    public static final Particle VILLAGER_ANGRY    = new Particle("VILLAGER_ANGRY", "angryVillager", 20);
    public static final Particle VILLAGER_HAPPY    = new Particle("VILLAGER_HAPPY", "happyVillager", 21);
    public static final Particle TOWN_AURA         = new Particle("TOWN_AURA", "townaura", 22);
    public static final Particle NOTE              = new Particle("NOTE", "note", 23);
    public static final Particle PORTAL            = new Particle("PORTAL", "portal", 24);
    public static final Particle ENCHANTMENT_TABLE = new Particle("ENCHANTMENT_TABLE", "enchantmenttable", 25);
    public static final Particle FLAME             = new Particle("FLAME", "flame", 26);
    public static final Particle LAVA              = new Particle("LAVA", "lava", 27);
    public static final Particle FOOTSTEP          = new Particle("FOOTSTEP", "footstep", 28);
    public static final Particle CLOUD             = new Particle("CLOUD", "cloud", 29);
    public static final Particle REDSTONE          = new Particle("REDSTONE", "reddust", 30);
    public static final Particle SNOWBALL          = new Particle("SNOWBALL", "snowballpoof", 31);
    public static final Particle SNOW_SHOVEL       = new Particle("SNOW_SHOVEL", "snowshovel", 32);
    public static final Particle SLIME             = new Particle("SLIME", "slime", 33);
    public static final Particle HEART             = new Particle("HEART", "heart", 34);
    public static final Particle BARRIER           = new Particle("BARRIER", "barrier", 35);
    public static final Particle ITEM_CRACK        = new Particle("ITEM_CRACK", "iconcrack_", 36, 2);
    public static final Particle BLOCK_CRACK       = new Particle("BLOCK_CRACK", "blockcrack_", 37, 1);
    public static final Particle BLOCK_DUST        = new Particle("BLOCK_DUST", "blockdust_", 38, 1);
    public static final Particle WATER_DROP        = new Particle("WATER_DROP", "droplet", 39);
    public static final Particle ITEM_TAKE         = new Particle("ITEM_TAKE", "take", 40);
    public static final Particle MOB_APPEARANCE    = new Particle("MOB_APPEARANCE", "mobappearance", 41);

    private static final TIntObjectMap<Particle> byParticleID   = new TIntObjectHashMap<>(42, SMALL_LOAD_FACTOR);
    private static final Map<String, Particle>   byParticleName = new CaseInsensitiveMap<>(42, SMALL_LOAD_FACTOR);
    private final String particleName;
    private final int    particleId;
    private final int    dataSize;

    public Particle(final String enumName, final String name, final int particleId, final int dataSize)
    {
        super(enumName);
        this.particleName = name;
        this.particleId = particleId;
        this.dataSize = dataSize;
    }

    public Particle(final String enumName, final String name, final int particleId)
    {
        this(enumName, name, particleId, 0);
    }

    public int getParticleId()
    {
        return this.particleId;
    }

    public int getDataSize()
    {
        return this.dataSize;
    }

    public String getParticleName()
    {
        return this.particleName;
    }

    public static Particle getByParticleId(final int id)
    {
        return byParticleID.get(id);
    }

    public static Particle getByParticleName(final String name)
    {
        return byParticleName.get(name);
    }

    /**
     * Register new {@link Particle} entry in this enum.
     *
     * @param element new element to register.
     */
    public static void register(final Particle element)
    {
        ASimpleEnum.register(Particle.class, element);
        byParticleID.put(element.getParticleId(), element);
        byParticleName.put(element.getParticleName(), element);
    }

    /**
     * Get one of {@link Particle} entry by its ordinal id.
     *
     * @param ordinal ordinal id of entry.
     *
     * @return one of entry or null.
     */
    public static Particle getByEnumOrdinal(final int ordinal)
    {
        return getByEnumOrdinal(Particle.class, ordinal);
    }

    /**
     * Get one of Particle entry by its name.
     *
     * @param name name of entry.
     *
     * @return one of entry or null.
     */
    public static Particle getByEnumName(final String name)
    {
        return getByEnumName(Particle.class, name);
    }

    /**
     * @return all values in array.
     */
    public static Particle[] values()
    {
        final TIntObjectMap<SimpleEnum<?>> map = getByEnumOrdinal(Particle.class);
        return (Particle[]) map.values(new Particle[map.size()]);
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("enumName", this.enumName).append("particleName", this.particleName).append("id", this.particleId).append("dataSize", this.dataSize).toString();
    }

    static
    {
        Particle.register(EXPLOSION_NORMAL);
        Particle.register(EXPLOSION_HUGE);
        Particle.register(EXPLOSION_LARGE);
        Particle.register(FIREWORKS_SPARK);
        Particle.register(WATER_BUBBLE);
        Particle.register(WATER_SPLASH);
        Particle.register(WATER_WAKE);
        Particle.register(SUSPENDED);
        Particle.register(SUSPENDED_DEPTH);
        Particle.register(CRIT);
        Particle.register(CRIT_MAGIC);
        Particle.register(SMOKE_NORMAL);
        Particle.register(SMOKE_LARGE);
        Particle.register(SPELL);
        Particle.register(SPELL_INSTANT);
        Particle.register(SPELL_MOB);
        Particle.register(SPELL_MOB_AMBIENT);
        Particle.register(SPELL_WITCH);
        Particle.register(DRIP_LAVA);
        Particle.register(DRIP_WATER);
        Particle.register(VILLAGER_ANGRY);
        Particle.register(VILLAGER_HAPPY);
        Particle.register(TOWN_AURA);
        Particle.register(PORTAL);
        Particle.register(NOTE);
        Particle.register(ENCHANTMENT_TABLE);
        Particle.register(FLAME);
        Particle.register(LAVA);
        Particle.register(FOOTSTEP);
        Particle.register(CLOUD);
        Particle.register(REDSTONE);
        Particle.register(SNOWBALL);
        Particle.register(SNOW_SHOVEL);
        Particle.register(SLIME);
        Particle.register(HEART);
        Particle.register(BARRIER);
        Particle.register(ITEM_CRACK);
        Particle.register(BLOCK_CRACK);
        Particle.register(BLOCK_DUST);
        Particle.register(WATER_DROP);
        Particle.register(ITEM_TAKE);
        Particle.register(MOB_APPEARANCE);
    }
}