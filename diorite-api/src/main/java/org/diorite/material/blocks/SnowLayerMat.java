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

import org.diorite.material.BlockMaterialData;
import org.diorite.material.Material;
import org.diorite.utils.collections.maps.CaseInsensitiveMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing 'Snow Layer 1' block material in minecraft. <br>
 * ID of block: 78 <br>
 * String ID of block: minecraft:snow_layer <br>
 * Hardness: 0,1 <br>
 * Blast Resistance 0,5 <br>
 * <br>
 * Subtypes: <br>
 * <ol>
 * <li>
 * 8:
 * Type name: '8' <br>
 * SubID: 7 <br>
 * Hardness: 0,1 <br>
 * Blast Resistance 0,5 <br>
 * </li>
 * <li>
 * 7:
 * Type name: '7' <br>
 * SubID: 6 <br>
 * Hardness: 0,1 <br>
 * Blast Resistance 0,5 <br>
 * </li>
 * <li>
 * 6:
 * Type name: '6' <br>
 * SubID: 5 <br>
 * Hardness: 0,1 <br>
 * Blast Resistance 0,5 <br>
 * </li>
 * <li>
 * 5:
 * Type name: '5' <br>
 * SubID: 4 <br>
 * Hardness: 0,1 <br>
 * Blast Resistance 0,5 <br>
 * </li>
 * <li>
 * 4:
 * Type name: '4' <br>
 * SubID: 3 <br>
 * Hardness: 0,1 <br>
 * Blast Resistance 0,5 <br>
 * </li>
 * <li>
 * 3:
 * Type name: '3' <br>
 * SubID: 2 <br>
 * Hardness: 0,1 <br>
 * Blast Resistance 0,5 <br>
 * </li>
 * <li>
 * 2:
 * Type name: '2' <br>
 * SubID: 1 <br>
 * Hardness: 0,1 <br>
 * Blast Resistance 0,5 <br>
 * </li>
 * <li>
 * 1:
 * Type name: '1' <br>
 * SubID: 0 <br>
 * Hardness: 0,1 <br>
 * Blast Resistance 0,5 <br>
 * </li>
 * </ol>
 */
@SuppressWarnings("JavaDoc")
public class SnowLayerMat extends BlockMaterialData
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final int USED_DATA_VALUES = 8;

    public static final SnowLayerMat SNOW_LAYER_1 = new SnowLayerMat();
    public static final SnowLayerMat SNOW_LAYER_2 = new SnowLayerMat(0x01);
    public static final SnowLayerMat SNOW_LAYER_3 = new SnowLayerMat(0x02);
    public static final SnowLayerMat SNOW_LAYER_4 = new SnowLayerMat(0x03);
    public static final SnowLayerMat SNOW_LAYER_5 = new SnowLayerMat(0x04);
    public static final SnowLayerMat SNOW_LAYER_6 = new SnowLayerMat(0x05);
    public static final SnowLayerMat SNOW_LAYER_7 = new SnowLayerMat(0x06);
    public static final SnowLayerMat SNOW_LAYER_8 = new SnowLayerMat(0x07);

    private static final Map<String, SnowLayerMat>    byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<SnowLayerMat> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR, Byte.MIN_VALUE);

    @SuppressWarnings("MagicNumber")
    protected SnowLayerMat()
    {
        super("SNOW_LAYER_1", 78, "minecraft:snow_layer", "1", (byte) 0x00, 0.1f, 0.5f);
    }

    protected SnowLayerMat(final int type)
    {
        super(SNOW_LAYER_1.name(), SNOW_LAYER_1.ordinal(), SNOW_LAYER_1.getMinecraftId(), Integer.toString(type + 1), (byte) type, SNOW_LAYER_1.getHardness(), SNOW_LAYER_1.getBlastResistance());
    }

    protected SnowLayerMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type, final float hardness, final float blastResistance)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, hardness, blastResistance);
    }

    @Override
    public Material ensureValidInventoryItem()
    {
        return Material.SNOW_LAYER;
    }

    @Override
    public SnowLayerMat getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public SnowLayerMat getType(final int id)
    {
        return getByID(id);
    }

    /**
     * Returns one of SnowLayer sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of SnowLayer or null
     */
    public static SnowLayerMat getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of SnowLayer sub-type based on how many layers it should have.
     * From 1 to 8.
     *
     * @param layers layers of snow.
     *
     * @return sub-type of SnowLayer or null
     */
    public static SnowLayerMat getSnowLayer(final int layers)
    {
        return getByID(layers + 1);
    }

    /**
     * Returns one of SnowLayer sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of SnowLayer or null
     */
    public static SnowLayerMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final SnowLayerMat element)
    {
        byID.put((byte) element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    @Override
    public SnowLayerMat[] types()
    {
        return SnowLayerMat.snowLayerTypes();
    }

    /**
     * @return array that contains all sub-types of this block.
     */
    public static SnowLayerMat[] snowLayerTypes()
    {
        return byID.values(new SnowLayerMat[byID.size()]);
    }

    static
    {
        SnowLayerMat.register(SNOW_LAYER_1);
        SnowLayerMat.register(SNOW_LAYER_2);
        SnowLayerMat.register(SNOW_LAYER_3);
        SnowLayerMat.register(SNOW_LAYER_4);
        SnowLayerMat.register(SNOW_LAYER_5);
        SnowLayerMat.register(SNOW_LAYER_6);
        SnowLayerMat.register(SNOW_LAYER_7);
        SnowLayerMat.register(SNOW_LAYER_8);
    }
}
