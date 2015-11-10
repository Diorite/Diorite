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

package org.diorite.material.blocks;

import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.material.BlockMaterialData;
import org.diorite.utils.collections.maps.CaseInsensitiveMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing 'Water Lily' block material in minecraft. <br>
 * ID of block: 111 <br>
 * String ID of block: minecraft:waterlily <br>
 * Hardness: 0,6 <br>
 * Blast Resistance 0
 */
@SuppressWarnings("JavaDoc")
public class WaterLilyMat extends PlantMat
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final int USED_DATA_VALUES = 1;

    public static final WaterLilyMat WATER_LILY = new WaterLilyMat();

    private static final Map<String, WaterLilyMat>    byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<WaterLilyMat> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR, Byte.MIN_VALUE);

    @SuppressWarnings("MagicNumber")
    protected WaterLilyMat()
    {
        super("WATER_LILY", 111, "minecraft:waterlily", "WATER_LILY", (byte) 0x00, 0.6f, 0);
    }

    protected WaterLilyMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type, final float hardness, final float blastResistance)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, hardness, blastResistance);
    }

    @Override
    public WaterLilyMat getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public WaterLilyMat getType(final int id)
    {
        return getByID(id);
    }

    /**
     * Returns one of WaterLily sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of WaterLily or null
     */
    public static WaterLilyMat getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of WaterLily sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of WaterLily or null
     */
    public static WaterLilyMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final WaterLilyMat element)
    {
        byID.put((byte) element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    @Override
    public WaterLilyMat[] types()
    {
        return WaterLilyMat.waterLilyTypes();
    }

    /**
     * @return array that contains all sub-types of this block.
     */
    public static WaterLilyMat[] waterLilyTypes()
    {
        return byID.values(new WaterLilyMat[byID.size()]);
    }

    static
    {
        WaterLilyMat.register(WATER_LILY);
    }

    public abstract static class RailsMat extends BlockMaterialData
    {
        protected final RailTypeMat railType;

        protected RailsMat(final String enumName, final int id, final String minecraftId, final String typeName, final RailTypeMat railType, final byte flags, final float hardness, final float blastResistance)
        {
            super(enumName, id, minecraftId, typeName, (byte) (railType.getFlag() | flags), hardness, blastResistance);
            this.railType = railType;
        }

        protected RailsMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type, final RailTypeMat railType, final float hardness, final float blastResistance)
        {
            super(enumName, id, minecraftId, maxStack, typeName, type, hardness, blastResistance);
            this.railType = railType;
        }

        /**
         * @return type of Rails.
         */
        public RailTypeMat getRailType()
        {
            return this.railType;
        }

        /**
         * Returns sub-type of Rails based on {@link RailTypeMat} state.
         *
         * @param railType {@link RailTypeMat} of Rails,
         *
         * @return sub-type of Rails
         */
        public abstract RailsMat getRailType(RailTypeMat railType);

        @Override
        public abstract RailsMat getType(final int type);

        @Override
        public abstract RailsMat getType(final String type);

        @Override
        public abstract RailsMat[] types();

        @Override
        public String toString()
        {
            return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("railType", this.railType).toString();
        }
    }

    public static enum RailTypeMat
    {
        FLAT_NORTH_SOUTH(0x00),
        FLAT_WEST_EAST(0x01),
        ASCENDING_EAST(0x02),
        ASCENDING_WEST(0x03),
        ASCENDING_NORTH(0x04),
        ASCENDING_SOUTH(0x05),
        CURVED_SOUTH_EAST(0x06),
        CURVED_SOUTH_WEST(0x07),
        CURVED_NORTH_WEST(0x08),
        CURVED_NORTH_EAST(0x09);

        private final byte flag;

        RailTypeMat(final int flag)
        {
            this.flag = (byte) flag;
        }

        public byte getFlag()
        {
            return this.flag;
        }
    }
}
