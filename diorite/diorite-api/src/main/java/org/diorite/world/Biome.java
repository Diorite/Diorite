/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2017. Diorite (by Bartłomiej Mazur (aka GotoFinal))
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

package org.diorite.world;

import java.util.Map;

import org.diorite.commons.enums.DynamicEnum;
import org.diorite.commons.maps.CaseInsensitiveMap;

/**
 * Dynamic enum of biomes.
 */
public class Biome extends DynamicEnum<Biome>
{
    public static final Biome OCEAN                         = $("Ocean");
    public static final Biome PLAINS                        = $("Plains");
    public static final Biome DESERT                        = $("Desert");
    public static final Biome EXTREME_HILLS                 = $("Extreme Hills");
    public static final Biome FOREST                        = $("Forest");
    public static final Biome TAIGA                         = $("Taiga");
    public static final Biome SWAMPLAND                     = $("Swampland");
    public static final Biome RIVER                         = $("River");
    public static final Biome HELL                          = $("Hell");
    public static final Biome THE_END                       = $("The End");
    public static final Biome FROZEN_OCEAN                  = $("FrozenOcean");
    public static final Biome FROZEN_RIVER                  = $("FrozenRiver");
    public static final Biome ICE_PLAINS                    = $("Ice Plains");
    public static final Biome ICE_MOUNTAINS                 = $("Ice Mountains");
    public static final Biome MUSHROOM_ISLAND               = $("MushroomIsland");
    public static final Biome MUSHROOM_ISLAND_SHORE         = $("MushroomIslandShore");
    public static final Biome BEACH                         = $("Beach");
    public static final Biome DESERT_HILLS                  = $("DesertHills");
    public static final Biome FOREST_HILLS                  = $("ForestHills");
    public static final Biome TAIGA_HILLS                   = $("TaigaHills");
    public static final Biome EXTREME_HILLS_EDGE            = $("Extreme Hills Edge");
    public static final Biome JUNGLE                        = $("Jungle");
    public static final Biome JUNGLE_HILLS                  = $("JungleHills");
    public static final Biome JUNGLE_EDGE                   = $("JungleEdge");
    public static final Biome DEEP_OCEAN                    = $("Deep Ocean");
    public static final Biome STONE_BEACH                   = $("Stone Beach");
    public static final Biome COLD_BEACH                    = $("Cold Beach");
    public static final Biome BIRCH_FOREST                  = $("Birch Forest");
    public static final Biome BIRCH_FOREST_HILLS            = $("Birch Forest Hills");
    public static final Biome ROOFED_FOREST                 = $("Roofed Forest");
    public static final Biome COLD_TAIGA                    = $("Cold Taiga");
    public static final Biome COLD_TAIGA_HILLS              = $("Cold Taiga Hills");
    public static final Biome MEGA_TAIGA                    = $("Mega Taiga");
    public static final Biome MEGA_TAIGA_HILLS              = $("Mega Taiga Hills");
    public static final Biome EXTREME_HILLS_PLUS            = $("Extreme Hills+");
    public static final Biome SAVANNA                       = $("Savanna");
    public static final Biome SAVANNA_PLATEAU               = $("Savanna Plateau");
    public static final Biome MESA                          = $("Mesa");
    public static final Biome MESA_PLATEAU_FOREST           = $("Mesa Plateau F");
    public static final Biome MESA_PLATEAU                  = $("Mesa Plateau");
    public static final Biome THE_VOID                      = $("The Void");
    public static final Biome SUNFLOWER_PLAINS              = $("Sunflower Plains");
    public static final Biome DESERT_MOUNTAINS              = $("Desert M");
    public static final Biome EXTREME_HILLS_MOUNTAINS       = $("Extreme Hills M");
    public static final Biome FLOWER_FOREST                 = $("Flower Forest");
    public static final Biome TAIGA_MOUNTAINS               = $("Taiga M");
    public static final Biome SWAMPLAND_MOUNTAINS           = $("Swampland M");
    public static final Biome ICE_PLAINS_SPIKES             = $("Ice Plains Spikes");
    public static final Biome JUNGLE_MOUNTAINS              = $("Jungle M");
    public static final Biome JUNGLE_EDGE_MOUNTAINS         = $("JungleEdge M");
    public static final Biome BIRCH_FOREST_MOUNTAINS        = $("Birch Forest M");
    public static final Biome BIRCH_FOREST_HILLS_MOUNTAINS  = $("Birch Forest Hills M");
    public static final Biome ROOFED_FOREST_MOUNTAINS       = $("Roofed Forest M");
    public static final Biome COLD_TAIGA_MOUNTAINS          = $("Cold Taiga M");
    public static final Biome MEGA_SPRUCE_TAIGA             = $("Mega Spruce Taiga");
    public static final Biome REDWOOD_TAIGA_HILLS_MOUNTAINS = $("Redwood Taiga Hills M");
    public static final Biome EXTREME_HILLS_PLUS_MOUNTAINS  = $("Extreme Hills+ M");
    public static final Biome SAVANNA_MOUNTAINS             = $("Savanna M");
    public static final Biome SAVANNA_PLATEAU_MOUNTAINS     = $("Savanna Plateau M");
    public static final Biome MESA_BRYCE                    = $("Mesa (Bryce)");
    public static final Biome MESA_PLATEAU_FOREST_MOUNTAINS = $("Mesa Plateau F M");
    public static final Biome MESA_PLATEAU_MOUNTAINS        = $("Mesa Plateau M");

    private static final Map<String, Biome> byBiomeName = new CaseInsensitiveMap<>(62, .1f);

    private final String biomeName;

    public Biome(String biomeName)
    {
        super();
        this.biomeName = biomeName;
        byBiomeName.putIfAbsent(biomeName, this);
        byBiomeName.putIfAbsent(this.name(), this);
    }

    /**
     * Returns biome by name.
     *
     * @param name
     *         name of biome.
     *
     * @return biome with given name or null.
     */
    public static Biome getByName(String name)
    {
        return byBiomeName.get(name);
    }

    /**
     * Returns all enum values.
     *
     * @return all enum values.
     */
    public static Biome[] values() {return DynamicEnum.values(Biome.class);}

    /**
     * Returns enum value of given name, case-sensitive.
     *
     * @param name
     *         name of element.
     *
     * @return value of given name, or exception is thrown.
     */
    public static Biome valueOf(String name) {return DynamicEnum.valueOf(Biome.class, name);}
}