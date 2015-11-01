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
import org.diorite.material.Material;
import org.diorite.material.blocks.MushroomBlockMat.Type;
import org.diorite.utils.collections.maps.CaseInsensitiveMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing 'Cake Block' block material in minecraft. <br>
 * ID of block: 92 <br>
 * String ID of block: minecraft:cake <br>
 * This block can't be used in inventory, valid material for this block: 'Cake' (minecraft:cake(354):0) <br>
 * Hardness: 0,5 <br>
 * Blast Resistance 2,5 <br>
 * <br>
 * Subtypes: <br>
 * <ol>
 * <li>
 * 6:
 * Type name: '6' <br>
 * SubID: 6 <br>
 * Hardness: 0,5 <br>
 * Blast Resistance 2,5 <br>
 * </li>
 * <li>
 * 5:
 * Type name: '5' <br>
 * SubID: 5 <br>
 * Hardness: 0,5 <br>
 * Blast Resistance 2,5 <br>
 * </li>
 * <li>
 * 4:
 * Type name: '4' <br>
 * SubID: 4 <br>
 * Hardness: 0,5 <br>
 * Blast Resistance 2,5 <br>
 * </li>
 * <li>
 * 3:
 * Type name: '3' <br>
 * SubID: 3 <br>
 * Hardness: 0,5 <br>
 * Blast Resistance 2,5 <br>
 * </li>
 * <li>
 * 2:
 * Type name: '2' <br>
 * SubID: 2 <br>
 * Hardness: 0,5 <br>
 * Blast Resistance 2,5 <br>
 * </li>
 * <li>
 * 1:
 * Type name: '1' <br>
 * SubID: 1 <br>
 * Hardness: 0,5 <br>
 * Blast Resistance 2,5 <br>
 * </li>
 * <li>
 * 0:
 * Type name: '0' <br>
 * SubID: 0 <br>
 * Hardness: 0,5 <br>
 * Blast Resistance 2,5 <br>
 * </li>
 * </ol>
 */
@SuppressWarnings("JavaDoc")
public class CakeBlockMat extends BlockMaterialData
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final int USED_DATA_VALUES = 6;

    public static final CakeBlockMat CAKE_BLOCK_0 = new CakeBlockMat();
    public static final CakeBlockMat CAKE_BLOCK_1 = new CakeBlockMat(0x1);
    public static final CakeBlockMat CAKE_BLOCK_2 = new CakeBlockMat(0x2);
    public static final CakeBlockMat CAKE_BLOCK_3 = new CakeBlockMat(0x3);
    public static final CakeBlockMat CAKE_BLOCK_4 = new CakeBlockMat(0x4);
    public static final CakeBlockMat CAKE_BLOCK_5 = new CakeBlockMat(0x5);
    public static final CakeBlockMat CAKE_BLOCK_6 = new CakeBlockMat(0x6);

    private static final Map<String, CakeBlockMat>    byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<CakeBlockMat> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR, Byte.MIN_VALUE);

    protected final byte piecesEaten;

    @SuppressWarnings("MagicNumber")
    protected CakeBlockMat()
    {
        super("CAKE_BLOCK", 92, "minecraft:cake", 1, "0", (byte) 0x00, 0.5f, 2.5f);
        this.piecesEaten = 0x0;
    }

    protected CakeBlockMat(final int piecesEaten)
    {
        super(CAKE_BLOCK_0.name(), CAKE_BLOCK_0.ordinal(), CAKE_BLOCK_0.getMinecraftId(), CAKE_BLOCK_0.getMaxStack(), Integer.toString(piecesEaten), (byte) piecesEaten, CAKE_BLOCK_0.getHardness(), CAKE_BLOCK_0.getBlastResistance());
        this.piecesEaten = (byte) piecesEaten;
    }

    protected CakeBlockMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type, final byte piecesEaten, final float hardness, final float blastResistance)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, hardness, blastResistance);
        this.piecesEaten = piecesEaten;
    }

    @Override
    public Material ensureValidInventoryItem()
    {
        return Material.CAKE;
    }

    @Override
    public CakeBlockMat getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public CakeBlockMat getType(final int id)
    {
        return getByID(id);
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("piecesEaten", this.piecesEaten).toString();
    }

    /**
     * For vanilla cake blocks should return values from 0 to 6.
     *
     * @return amount of eated pieces of cake {@literal <3}
     */
    public byte getPiecesEaten()
    {
        return this.piecesEaten;
    }

    /**
     * Return cake with selected amount of eaten pieces.
     * Vanilla server will return null for all values above 6.
     *
     * @param piecesEaten amount of eated pieces of cake.
     *
     * @return cake with selected amount of eaten pieces or null.
     */
    public CakeBlockMat getPiecesEaten(final byte piecesEaten)
    {
        return getByID(piecesEaten);
    }

    /**
     * Returns one of Cake sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of Cake or null
     */
    public static CakeBlockMat getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of Cake sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of Cake or null
     */
    public static CakeBlockMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Returns one of Cake sub-type based on amount of eaten pieces.
     * It will never return null. (full cake if number is out of range)
     *
     * @param type amount of eaten pieces.
     *
     * @return sub-type of Cake
     */
    public static CakeBlockMat getCake(final Type type)
    {
        final CakeBlockMat cake = getByID(type.getFlag());
        if (cake == null)
        {
            return CAKE_BLOCK_0;
        }
        return cake;
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final CakeBlockMat element)
    {
        byID.put((byte) element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    @Override
    public CakeBlockMat[] types()
    {
        return CakeBlockMat.cakeTypes();
    }

    /**
     * @return array that contains all sub-types of this block.
     */
    public static CakeBlockMat[] cakeTypes()
    {
        return byID.values(new CakeBlockMat[byID.size()]);
    }

    static
    {
        CakeBlockMat.register(CAKE_BLOCK_0);
        CakeBlockMat.register(CAKE_BLOCK_1);
        CakeBlockMat.register(CAKE_BLOCK_2);
        CakeBlockMat.register(CAKE_BLOCK_3);
        CakeBlockMat.register(CAKE_BLOCK_4);
        CakeBlockMat.register(CAKE_BLOCK_5);
        CakeBlockMat.register(CAKE_BLOCK_6);
    }
}
