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

package org.diorite.world.generator.maplayer;

import java.util.Objects;
import java.util.Random;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.world.Biome;
import org.diorite.world.Dimension;
import org.diorite.world.WorldType;
import org.diorite.world.generator.maplayer.WhittakerMapLayer.ClimateType;
import org.diorite.world.generator.maplayer.ZoomMapLayer.ZoomType;


@SuppressWarnings("MagicNumber")
public abstract class MapLayer
{
    protected final Random random = new Random();
    protected final long seed;

    public MapLayer(final long seed)
    {
        this.seed = seed;
    }

    public void setCoordsSeed(final int x, final int z)
    {
        this.random.setSeed(this.seed);
        this.random.setSeed(((x * this.random.nextLong()) + (z * this.random.nextLong())) ^ this.seed);
    }

    public int nextInt(final int max)
    {
        return this.random.nextInt(max);
    }

    public abstract int[] generateValues(int x, int z, int sizeX, int sizeZ);

    public static MapLayer[] initialize(final long seed, final Dimension dimension, final WorldType worldType)
    {
        if (Objects.equals(dimension, Dimension.NETHER))
        {
            return new MapLayer[]{new ConstantBiomeMapLayer(seed, Biome.HELL), null};
        }
        if (Objects.equals(dimension, Dimension.END))
        {
            return new MapLayer[]{new ConstantBiomeMapLayer(seed, Biome.THE_END), null};
        }

        int zoom = 2;
        if (Objects.equals(worldType, WorldType.LARGE_BIOMES))
        {
            zoom = 4;
        }

        MapLayer layer = new NoiseMapLayer(seed); // this is initial land spread layer
        layer = new WhittakerMapLayer(seed + 1, layer, ClimateType.WARM_WET);
        layer = new WhittakerMapLayer(seed + 1, layer, ClimateType.COLD_DRY);
        layer = new WhittakerMapLayer(seed + 2, layer, ClimateType.LARGER_BIOMES);
        for (int i = 0; i < 2; i++)
        {
            layer = new ZoomMapLayer(seed + 100 + i, layer, ZoomType.BLURRY);
        }
        for (int i = 0; i < 2; i++)
        {
            layer = new ErosionMapLayer(seed + 3 + i, layer);
        }
        layer = new DeepOceanMapLayer(seed + 4, layer);

        MapLayer layerMountains = new BiomeVariationMapLayer(seed + 200, layer);
        for (int i = 0; i < 2; i++)
        {
            layerMountains = new ZoomMapLayer(seed + 200 + i, layerMountains);
        }

        layer = new BiomeMapLayer(seed + 5, layer);
        for (int i = 0; i < 2; i++)
        {
            layer = new ZoomMapLayer(seed + 200 + i, layer);
        }
        layer = new BiomeEdgeMapLayer(seed + 200, layer);
        layer = new BiomeVariationMapLayer(seed + 200, layer, layerMountains);
        layer = new RarePlainsMapLayer(seed + 201, layer);
        layer = new ZoomMapLayer(seed + 300, layer);
        layer = new ErosionMapLayer(seed + 6, layer);
        layer = new ZoomMapLayer(seed + 400, layer);
        layer = new BiomeThinEdgeMapLayer(seed + 400, layer);
        layer = new ShoreMapLayer(seed + 7, layer);
        for (int i = 0; i < zoom; i++)
        {
            layer = new ZoomMapLayer(seed + 500 + i, layer);
        }

        MapLayer layerRiver = layerMountains;
        layerRiver = new ZoomMapLayer(seed + 300, layerRiver);
        layerRiver = new ZoomMapLayer(seed + 400, layerRiver);
        for (int i = 0; i < zoom; i++)
        {
            layerRiver = new ZoomMapLayer(seed + 500 + i, layerRiver);
        }
        layerRiver = new RiverMapLayer(seed + 10, layerRiver);
        layer = new RiverMapLayer(seed + 1000, layerRiver, layer);

        final MapLayer layerLowerRes = layer;
        for (int i = 0; i < 2; i++)
        {
            layer = new ZoomMapLayer(seed + 2000 + i, layer);
        }

        layer = new SmoothMapLayer(seed + 1001, layer);

        return new MapLayer[]{layer, layerLowerRes};
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("random", this.random).append("seed", this.seed).toString();
    }
}
