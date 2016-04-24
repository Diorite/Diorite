/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2016. Diorite (by Bartłomiej Mazur (aka GotoFinal))
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

import org.diorite.utils.SimpleEnum.ASimpleEnum;
import org.diorite.utils.collections.maps.CaseInsensitiveMap;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;

public class Particle extends ASimpleEnum<Particle>
{
    static
    {
        init(Particle.class, 46);
    }

    public static final Particle EXPLOSION_NORMAL  = new Particle("EXPLOSION_NORMAL", "explode");
    public static final Particle EXPLOSION_LARGE   = new Particle("EXPLOSION_LARGE", "largeexplode");
    public static final Particle EXPLOSION_HUGE    = new Particle("EXPLOSION_HUGE", "hugeexplosion");
    public static final Particle FIREWORKS_SPARK   = new Particle("FIREWORKS_SPARK", "fireworksSpark");
    public static final Particle WATER_BUBBLE      = new Particle("WATER_BUBBLE", "bubble");
    public static final Particle WATER_SPLASH      = new Particle("WATER_SPLASH", "splash");
    public static final Particle WATER_WAKE        = new Particle("WATER_WAKE", "wake");
    public static final Particle SUSPENDED         = new Particle("SUSPENDED", "suspended");
    public static final Particle SUSPENDED_DEPTH   = new Particle("SUSPENDED_DEPTH", "depthsuspend");
    public static final Particle CRIT              = new Particle("CRIT", "crit");
    public static final Particle CRIT_MAGIC        = new Particle("CRIT_MAGIC", "magicCrit");
    public static final Particle SMOKE_NORMAL      = new Particle("SMOKE_NORMAL", "smoke");
    public static final Particle SMOKE_LARGE       = new Particle("SMOKE_LARGE", "largesmoke");
    public static final Particle SPELL             = new Particle("SPELL", "spell");
    public static final Particle SPELL_INSTANT     = new Particle("SPELL_INSTANT", "instantSpell");
    public static final Particle SPELL_MOB         = new Particle("SPELL_MOB", "mobSpell");
    public static final Particle SPELL_MOB_AMBIENT = new Particle("SPELL_MOB_AMBIENT", "mobSpellAmbient");
    public static final Particle SPELL_WITCH       = new Particle("SPELL_WITCH", "witchMagic");
    public static final Particle DRIP_WATER        = new Particle("DRIP_WATER", "dripWater");
    public static final Particle DRIP_LAVA         = new Particle("DRIP_LAVA", "dripLava");
    public static final Particle VILLAGER_ANGRY    = new Particle("VILLAGER_ANGRY", "angryVillager");
    public static final Particle VILLAGER_HAPPY    = new Particle("VILLAGER_HAPPY", "happyVillager");
    public static final Particle TOWN_AURA         = new Particle("TOWN_AURA", "townaura");
    public static final Particle NOTE              = new Particle("NOTE", "note");
    public static final Particle PORTAL            = new Particle("PORTAL", "portal");
    public static final Particle ENCHANTMENT_TABLE = new Particle("ENCHANTMENT_TABLE", "enchantmenttable");
    public static final Particle FLAME             = new Particle("FLAME", "flame");
    public static final Particle LAVA              = new Particle("LAVA", "lava");
    public static final Particle FOOTSTEP          = new Particle("FOOTSTEP", "footstep");
    public static final Particle CLOUD             = new Particle("CLOUD", "cloud");
    public static final Particle REDSTONE          = new Particle("REDSTONE", "reddust");
    public static final Particle SNOWBALL          = new Particle("SNOWBALL", "snowballpoof");
    public static final Particle SNOW_SHOVEL       = new Particle("SNOW_SHOVEL", "snowshovel");
    public static final Particle SLIME             = new Particle("SLIME", "slime");
    public static final Particle HEART             = new Particle("HEART", "heart");
    public static final Particle BARRIER           = new Particle("BARRIER", "barrier");
    public static final Particle ITEM_CRACK        = new Particle("ITEM_CRACK", "iconcrack_", 2);
    public static final Particle BLOCK_CRACK       = new Particle("BLOCK_CRACK", "blockcrack_", 1);
    public static final Particle BLOCK_DUST        = new Particle("BLOCK_DUST", "blockdust_", 1);
    public static final Particle WATER_DROP        = new Particle("WATER_DROP", "droplet");
    public static final Particle ITEM_TAKE         = new Particle("ITEM_TAKE", "take");
    public static final Particle MOB_APPEARANCE    = new Particle("MOB_APPEARANCE", "mobappearance");
    public static final Particle DRAGON_BREATH     = new Particle("DRAGON_BREATH", "dragonbreath");
    public static final Particle END_ROD           = new Particle("END_ROD", "endRod");
    public static final Particle DAMAGE_INDICATOR  = new Particle("DAMAGE_INDICATOR", "damageIndicator");
    public static final Particle SWEEP_ATTACK      = new Particle("SWEEP_ATTACK", "sweepAttack");

    private static final Map<String, Particle>   byParticleName = new CaseInsensitiveMap<>(46, SMALL_LOAD_FACTOR);
    private final String particleName;
    private final int    dataSize;

    public Particle(final String enumName, final String name, final int dataSize)
    {
        super(enumName);
        this.particleName = name;
        this.dataSize = dataSize;
    }

    public Particle(final String enumName, final String name)
    {
        this(enumName, name, 0);
    }

    public int getDataSize()
    {
        return this.dataSize;
    }

    public String getParticleName()
    {
        return this.particleName;
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
        final Int2ObjectMap<Particle> map = getByEnumOrdinal(Particle.class);
        return map.values().toArray(new Particle[map.size()]);
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("enumName", this.enumName).append("particleName", this.particleName).append("dataSize", this.dataSize).toString();
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
        Particle.register(DRAGON_BREATH);
        Particle.register(END_ROD);
        Particle.register(DAMAGE_INDICATOR);
        Particle.register(SWEEP_ATTACK);
    }
}