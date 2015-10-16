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

package org.diorite.world;

import java.util.Map;
import java.util.Random;

import org.diorite.utils.SimpleEnum;
import org.diorite.utils.SimpleEnum.ASimpleEnum;
import org.diorite.utils.collections.maps.CaseInsensitiveMap;
import org.diorite.utils.math.noise.SimplexOctaveGenerator;

import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.hash.TIntObjectHashMap;

@SuppressWarnings({"MagicNumber", "ClassHasNoToStringMethod"})
public class Biome extends ASimpleEnum<Biome>
{
    private static final SimplexOctaveGenerator noiseGen = new SimplexOctaveGenerator(new Random(1234), 1);

    static
    {
        init(Biome.class, 61);
        noiseGen.setScale(0.125); // 1/8
    }

    public static final Biome BEACH                         = new Biome("BEACH", "Beach", 16, 0.8F, 0.4F, true);
    public static final Biome BIRCH_FOREST                  = new Biome("BIRCH_FOREST", "Birch Forest", 27, 0.6F, 0.6F, true);
    public static final Biome BIRCH_FOREST_HILLS            = new Biome("BIRCH_FOREST_HILLS", "Birch Forest Hills", 28, 0.5F, 0.5F, true);
    public static final Biome BIRCH_FOREST_HILLS_MOUNTAINS  = new Biome("BIRCH_FOREST_HILLS_MOUNTAINS", "Birch Forest Hills M", 156, 0.5F, 0.5F, true);
    public static final Biome BIRCH_FOREST_MOUNTAINS        = new Biome("BIRCH_FOREST_MOUNTAINS", "Birch Forest M", 155, 0.5F, 0.5F, true);
    public static final Biome COLD_BEACH                    = new Biome("COLD_BEACH", "Cold Beach", 26, 0.5F, 0.5F, true);
    public static final Biome COLD_TAIGA                    = new Biome("COLD_TAIGA", "Cold Taiga", 30, 0.5F, 0.5F, true);
    public static final Biome COLD_TAIGA_HILLS              = new Biome("COLD_TAIGA_HILLS", "Cold Taiga Hills", 31, 0.5F, 0.5F, true);
    public static final Biome COLD_TAIGA_MOUNTAINS          = new Biome("COLD_TAIGA_MOUNTAINS", "Cold Taiga M", 158, 0.5F, 0.5F, true);
    public static final Biome DEEP_OCEAN                    = new Biome("DEEP_OCEAN", "Deep Ocean", 24, 0.5F, 0.5F, true);
    public static final Biome DESERT                        = new Biome("DESERT", "Desert", 2, 2.0F, 0.0F, false);
    public static final Biome DESERT_HILLS                  = new Biome("DESERT_HILLS", "DesertHills", 17, 2.0F, 0.0F, true);
    public static final Biome DESERT_MOUNTAINS              = new Biome("DESERT_MOUNTAINS", "Desert M", 130, 2.0F, 0.0F, true);
    public static final Biome EXTREME_HILLS                 = new Biome("EXTREME_HILLS", "Extreme Hills", 3, 0.2F, 0.3F, true);
    public static final Biome EXTREME_HILLS_EDGE            = new Biome("EXTREME_HILLS_EDGE", "Extreme Hills Edge", 20, 0.2F, 0.3F, true);
    public static final Biome EXTREME_HILLS_MOUNTAINS       = new Biome("EXTREME_HILLS_MOUNTAINS", "Extreme Hills M", 131, 0.2F, 0.3F, true);
    public static final Biome EXTREME_HILLS_PLUS            = new Biome("EXTREME_HILLS_PLUS", "Extreme Hills+", 34, 0.2F, 0.3F, true);
    public static final Biome EXTREME_HILLS_PLUS_MOUNTAINS  = new Biome("EXTREME_HILLS_PLUS_MOUNTAINS", "Extreme Hills+ M", 162, 0.2F, 0.3F, true);
    public static final Biome FLOWER_FOREST                 = new Biome("FLOWER_FOREST", "Flower Forest", 132, 0.5F, 0.5F, true);
    public static final Biome FOREST                        = new Biome("FOREST", "Forest", 4, 0.7F, 0.8F, true);
    public static final Biome FOREST_HILLS                  = new Biome("FOREST_HILLS", "ForestHills", 120, 0.5F, 0.5F, true);
    public static final Biome FROZEN_OCEAN                  = new Biome("FROZEN_OCEAN", "FrozenOcean", 10, 0.5F, 0.5F, true);
    public static final Biome FROZEN_RIVER                  = new Biome("FROZEN_RIVER", "FrozenRiver", 11, 0.5F, 0.5F, true);
    public static final Biome HELL                          = new Biome("HELL", "Hell", 8, 2.0F, 0.0F, true);
    public static final Biome ICE_MOUNTAINS                 = new Biome("ICE_MOUNTAINS", "Ice Mountains", 13, 0.5F, 0.5F, true);
    public static final Biome ICE_PLAINS                    = new Biome("ICE_PLAINS", "Ice Plains", 12, 0.0F, 0.5F, true);
    public static final Biome ICE_PLAINS_SPIKES             = new Biome("ICE_PLAINS_SPIKES", "Ice Plains Spikes", 140, 0.5F, 0.5F, true);
    public static final Biome JUNGLE                        = new Biome("JUNGLE", "Jungle", 21, 0.5F, 0.5F, true);
    public static final Biome JUNGLE_EDGE                   = new Biome("JUNGLE_EDGE", "JungleEdge", 23, 0.5F, 0.5F, true);
    public static final Biome JUNGLE_EDGE_MOUNTAINS         = new Biome("JUNGLE_EDGE_MOUNTAINS", "JungleEdge M", 151, 0.5F, 0.5F, true);
    public static final Biome JUNGLE_HILLS                  = new Biome("JUNGLE_HILLS", "JungleHills", 22, 0.5F, 0.5F, true);
    public static final Biome JUNGLE_MOUNTAINS              = new Biome("JUNGLE_MOUNTAINS", "Jungle M", 149, 0.5F, 0.5F, true);
    public static final Biome MEGA_SPRUCE_TAIGA             = new Biome("MEGA_SPRUCE_TAIGA", "Mega Spruce Taiga", 160, 0.5F, 0.5F, true);
    public static final Biome MEGA_TAIGA                    = new Biome("MEGA_TAIGA", "Mega Taiga", 32, 0.5F, 0.5F, true);
    public static final Biome MEGA_TAIGA_HILLS              = new Biome("MEGA_TAIGA_HILLS", "Mega Taiga Hills", 33, 0.5F, 0.5F, true);
    public static final Biome MESA                          = new Biome("MESA", "Mesa", 37, 2.0F, 0.0F, true);
    public static final Biome MESA_BRYCE                    = new Biome("MESA_BRYCE", "Mesa (Bryce)", 165, 2.0F, 0.0F, true);
    public static final Biome MESA_PLATEAU                  = new Biome("MESA_PLATEAU", "Mesa Plateau", 39, 2.0F, 0.0F, true);
    public static final Biome MESA_PLATEAU_FOREST           = new Biome("MESA_PLATEAU_FOREST", "Mesa Plateau F", 38, 2.0F, 0.0F, true);
    public static final Biome MESA_PLATEAU_FOREST_MOUNTAINS = new Biome("MESA_PLATEAU_FOREST_MOUNTAINS", "Mesa Plateau F M", 166, 2.0F, 0.0F, true);
    public static final Biome MESA_PLATEAU_MOUNTAINS        = new Biome("MESA_PLATEAU_MOUNTAINS", "Mesa Plateau M", 167, 2.0F, 0.0F, true);
    public static final Biome MUSHROOM_ISLAND               = new Biome("MUSHROOM_ISLAND", "MushroomIsland", 14, 0.9F, 1.0F, true);
    public static final Biome MUSHROOM_SHORE                = new Biome("MUSHROOM_SHORE", "MushroomIslandShore", 15, 0.9F, 1.0F, true);
    public static final Biome OCEAN                         = new Biome("OCEAN", "Ocean", 0, 0.5F, 0.5F, true);
    public static final Biome PLAINS                        = new Biome("PLAINS", "Plains", 1, 0.8F, 0.4F, true);
    public static final Biome REDWOOD_TAIGA_HILLS_MOUNTAINS = new Biome("REDWOOD_TAIGA_HILLS_MOUNTAINS", "Redwood Taiga Hills M", 161, 0.5F, 0.5F, true);
    public static final Biome RIVER                         = new Biome("RIVER", "River", 7, 0.5F, 0.5F, true);
    public static final Biome ROOFED_FOREST                 = new Biome("ROOFED_FOREST", "Roofed Forest", 29, 0.5F, 0.5F, true);
    public static final Biome ROOFED_FOREST_MOUNTAINS       = new Biome("ROOFED_FOREST_MOUNTAINS", "Roofed Forest M", 157, 0.5F, 0.5F, true);
    public static final Biome SAVANNA                       = new Biome("SAVANNA", "Savanna", 35, 0.5F, 0.5F, true);
    public static final Biome SAVANNA_MOUNTAINS             = new Biome("SAVANNA_MOUNTAINS", "Savanna M", 163, 0.5F, 0.5F, true);
    public static final Biome SAVANNA_PLATEAU               = new Biome("SAVANNA_PLATEAU", "Savanna Plateau", 36, 0.5F, 0.5F, true);
    public static final Biome SAVANNA_PLATEAU_MOUNTAINS     = new Biome("SAVANNA_PLATEAU_MOUNTAINS", "Savanna Plateau M", 164, 0.5F, 0.5F, true);
    public static final Biome STONE_BEACH                   = new Biome("STONE_BEACH", "Stone Beach", 25, 0.2F, 0.3F, true);
    public static final Biome SUNFLOWER_PLAINS              = new Biome("SUNFLOWER_PLAINS", "Sunflower Plains", 129, 0.8F, 0.4F, true);
    public static final Biome SWAMPLAND                     = new Biome("SWAMPLAND", "Swampland", 6, 0.8F, 0.9F, true);
    public static final Biome SWAMPLAND_MOUNTAINS           = new Biome("SWAMPLAND_MOUNTAINS", "Swampland M", 134, 0.5F, 0.5F, true);
    public static final Biome TAIGA                         = new Biome("TAIGA", "Taiga", 5, 0.25F, 0.8F, true);
    public static final Biome TAIGA_HILLS                   = new Biome("TAIGA_HILLS", "TaigaHills", 19, 0.5F, 0.5F, true);
    public static final Biome TAIGA_MOUNTAINS               = new Biome("TAIGA_MOUNTAINS", "Taiga M", 133, 0.5F, 0.5F, true);
    public static final Biome THE_END                       = new Biome("THE_END", "The End", 9, 0.5F, 0.5F, true);

    private static final TIntObjectMap<Biome> byBiomeID      = new TIntObjectHashMap<>(3, SMALL_LOAD_FACTOR);
    private static final Map<String, Biome>   byBiomeName    = new CaseInsensitiveMap<>(3, SMALL_LOAD_FACTOR);
    /**
     * all biomes with humidity above tihs value are wet.
     */
    public static final  double               WET_THRESHOLD  = 0.85D;
    /**
     * all biomes with temperature below tihs value are cold.
     */
    public static final  double               COLD_THRESHOLD = 0.15D;

    private final String  biomeName;
    private final int     biomeId;
    private final float   temperature;
    private final float   humidity;
    private final boolean rainy;

    public Biome(final String enumName, final int enumId, final String biomeName, final int biomeId, final float temperature, final float humidity, final boolean rainy)
    {
        super(enumName, enumId);
        this.biomeName = biomeName;
        this.biomeId = biomeId;
        this.temperature = temperature;
        this.humidity = humidity;
        this.rainy = rainy;
    }

    public Biome(final String enumName, final String biomeName, final int biomeId, final float temperature, final float humidity, final boolean rainy)
    {
        super(enumName);
        this.biomeName = biomeName;
        this.biomeId = biomeId;
        this.temperature = temperature;
        this.humidity = humidity;
        this.rainy = rainy;
    }

    /**
     * @return vanilla biome name.
     */
    public String getBiomeName()
    {
        return this.biomeName;
    }

    /**
     * @return biome id used in maps and packets.
     */
    public int getBiomeId()
    {
        return this.biomeId;
    }

    /**
     * @return basic temperature of biome.
     */
    public float getTemperature()
    {
        return this.temperature;
    }

    /**
     * @return basic humidity of biome.
     */
    public float getHumidity()
    {
        return this.humidity;
    }

    /**
     * @return true if biome is rainy or snowy.
     *
     * @see #isRainy(int, int, int)
     * @see #isSnowy(int, int, int)
     */
    public boolean isRainy()
    {
        return this.rainy;
    }

    /**
     * Check if biome is wet on selected block. (cords are ignroed by all default biomes) <br>
     * Location is wet when humidity is {@literal <} {@link #WET_THRESHOLD}
     *
     * @param x x coordinate of location. (ignored by all default biomes.)
     * @param y y coordinate of location. (ignored by all default biomes.)
     * @param z z coordinate of location. (ignored by all default biomes.)
     *
     * @return true if location is wet.
     */
    public boolean isWet(final int x, final int y, final int z)
    {
        return this.humidity > WET_THRESHOLD;
    }

    /**
     * Check if biome is cold on selected block. (x/z cords are ignroed by all default biomes) <br>
     * Location is cold when temperature is {@literal <} {@link #COLD_THRESHOLD}
     *
     * @param x x coordinate of location. (ignored by all default biomes.)
     * @param y y coordinate of location.
     * @param z z coordinate of location. (ignored by all default biomes.)
     *
     * @return true if location is cold.
     */
    public boolean isCold(final int x, final int y, final int z)
    {
        return this.getVariatedTemperature(x, y, z) < COLD_THRESHOLD;
    }

    /**
     * Check if biome is rainy (not snowy) on selected block. (x/z cords are ignroed by all default biomes) <br>
     * Location is rainy when it have rainy flag and it isn't cold. {@link #isCold(int, int, int)}
     *
     * @param x x coordinate of location. (ignored by all default biomes.)
     * @param y y coordinate of location.
     * @param z z coordinate of location. (ignored by all default biomes.)
     *
     * @return true if location is rainy.
     */
    public boolean isRainy(final int x, final int y, final int z)
    {
        return this.rainy && ! this.isCold(x, y, z);
    }

    /**
     * Check if biome is snowy (not rainy) on selected block. (x/z cords are ignroed by all default biomes) <br>
     * Location is snowy when it have rainy flag and it is cold. {@link #isCold(int, int, int)}
     *
     * @param x x coordinate of location. (ignored by all default biomes.)
     * @param y y coordinate of location.
     * @param z z coordinate of location. (ignored by all default biomes.)
     *
     * @return true if location is snowy.
     */
    public boolean isSnowy(final int x, final int y, final int z)
    {
        return this.rainy && this.isCold(x, y, z);
    }

    private double getVariatedTemperature(final int x, final int y, final int z)
    {
        if (y > 64)
        {
            return this.temperature - ((((noiseGen.noise(x, z, 0.5D, 2.0D) * 4.0D) + (double) (y - 64)) * 0.05D) / 30.0D);
        }
        return this.temperature;
    }


    /**
     * Get other Biome by biome ID.
     *
     * @param id id of biome.
     *
     * @return other biome or null.
     */
    public Biome byBiomeId(final int id)
    {
        return byBiomeID.get(id);
    }

    /**
     * Get other Biome by biome name.
     *
     * @param name name of biome.
     *
     * @return other biome or null.
     */
    public Biome byBiomeName(final String name)
    {
        return byBiomeName.get(name);
    }

    /**
     * Get other Biome by biome ID.
     *
     * @param id id of biome.
     *
     * @return other biome or null.
     */
    public static Biome getByBiomeId(final int id)
    {
        return byBiomeID.get(id);
    }

    /**
     * Get other Biome by biome name.
     *
     * @param name name of biome.
     *
     * @return other biome or null.
     */
    public static Biome getByBiomeName(final String name)
    {
        return byBiomeName.get(name);
    }

    /**
     * Register new {@link Biome} entry in this enum.
     *
     * @param element new element to register.
     */
    public static void register(final Biome element)
    {
        ASimpleEnum.register(Biome.class, element);
        byBiomeName.put(element.getBiomeName(), element);
        byBiomeID.put(element.getBiomeId(), element);
    }

    /**
     * Get one of {@link Biome} entry by its ordinal id.
     *
     * @param ordinal ordinal id of entry.
     *
     * @return one of entry or null.
     */
    public static Biome getByEnumOrdinal(final int ordinal)
    {
        return getByEnumOrdinal(Biome.class, ordinal);
    }

    /**
     * Get one of Biome entry by its name.
     *
     * @param name name of entry.
     *
     * @return one of entry or null.
     */
    public static Biome getByEnumName(final String name)
    {
        return getByEnumName(Biome.class, name);
    }

    /**
     * @return all values in array.
     */
    public static Biome[] values()
    {
        final TIntObjectMap<SimpleEnum<?>> map = getByEnumOrdinal(Biome.class);
        return (Biome[]) map.values(new Biome[map.size()]);
    }

    static
    {
        Biome.register(BEACH);
        Biome.register(BIRCH_FOREST);
        Biome.register(BIRCH_FOREST_HILLS);
        Biome.register(BIRCH_FOREST_HILLS_MOUNTAINS);
        Biome.register(BIRCH_FOREST_MOUNTAINS);
        Biome.register(COLD_BEACH);
        Biome.register(COLD_TAIGA);
        Biome.register(COLD_TAIGA_HILLS);
        Biome.register(COLD_TAIGA_MOUNTAINS);
        Biome.register(DEEP_OCEAN);
        Biome.register(DESERT);
        Biome.register(DESERT_HILLS);
        Biome.register(DESERT_MOUNTAINS);
        Biome.register(EXTREME_HILLS);
        Biome.register(EXTREME_HILLS_MOUNTAINS);
        Biome.register(EXTREME_HILLS_PLUS);
        Biome.register(EXTREME_HILLS_PLUS_MOUNTAINS);
        Biome.register(FLOWER_FOREST);
        Biome.register(FOREST);
        Biome.register(FOREST_HILLS);
        Biome.register(FROZEN_OCEAN);
        Biome.register(FROZEN_RIVER);
        Biome.register(HELL);
        Biome.register(ICE_MOUNTAINS);
        Biome.register(ICE_PLAINS);
        Biome.register(ICE_PLAINS_SPIKES);
        Biome.register(JUNGLE);
        Biome.register(JUNGLE_EDGE);
        Biome.register(JUNGLE_EDGE_MOUNTAINS);
        Biome.register(JUNGLE_HILLS);
        Biome.register(JUNGLE_MOUNTAINS);
        Biome.register(MEGA_SPRUCE_TAIGA);
        Biome.register(MEGA_TAIGA);
        Biome.register(MEGA_TAIGA_HILLS);
        Biome.register(MESA);
        Biome.register(MESA_BRYCE);
        Biome.register(MESA_PLATEAU);
        Biome.register(MESA_PLATEAU_FOREST);
        Biome.register(MESA_PLATEAU_FOREST_MOUNTAINS);
        Biome.register(MESA_PLATEAU_MOUNTAINS);
        Biome.register(MUSHROOM_ISLAND);
        Biome.register(MUSHROOM_SHORE);
        Biome.register(OCEAN);
        Biome.register(PLAINS);
        Biome.register(REDWOOD_TAIGA_HILLS_MOUNTAINS);
        Biome.register(RIVER);
        Biome.register(ROOFED_FOREST);
        Biome.register(ROOFED_FOREST_MOUNTAINS);
        Biome.register(SAVANNA);
        Biome.register(SAVANNA_MOUNTAINS);
        Biome.register(SAVANNA_PLATEAU);
        Biome.register(SAVANNA_PLATEAU_MOUNTAINS);
        Biome.register(EXTREME_HILLS_EDGE);
        Biome.register(STONE_BEACH);
        Biome.register(SUNFLOWER_PLAINS);
        Biome.register(SWAMPLAND);
        Biome.register(SWAMPLAND_MOUNTAINS);
        Biome.register(TAIGA);
        Biome.register(TAIGA_HILLS);
        Biome.register(TAIGA_MOUNTAINS);
        Biome.register(THE_END);
    }
}
