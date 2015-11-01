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

import org.diorite.material.Material;
import org.diorite.material.SlabTypeMat;
import org.diorite.material.WoodType;
import org.diorite.utils.collections.maps.CaseInsensitiveMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing 'Double Wooden Slab' block material in minecraft. <br>
 * ID of block: 125 <br>
 * String ID of block: minecraft:double_wooden_slab <br>
 * This block can't be used in inventory, valid material for this block: 'Wooden Slab'/'Oak' (minecraft:wooden_slab(126):0) <br>
 * Hardness: 2 <br>
 * Blast Resistance 30 <br>
 * <br>
 * Subtypes: <br>
 * <ol>
 * <li>
 * DARK_OAK:
 * Type name: 'Dark Oak' <br>
 * SubID: 5 <br>
 * Hardness: 2 <br>
 * Blast Resistance 30 <br>
 * </li>
 * <li>
 * ACACIA:
 * Type name: 'Acacia' <br>
 * SubID: 4 <br>
 * Hardness: 2 <br>
 * Blast Resistance 30 <br>
 * </li>
 * <li>
 * JUNGLE:
 * Type name: 'Jungle' <br>
 * SubID: 3 <br>
 * Hardness: 2 <br>
 * Blast Resistance 30 <br>
 * </li>
 * <li>
 * BIRCH:
 * Type name: 'Birch' <br>
 * SubID: 2 <br>
 * Hardness: 2 <br>
 * Blast Resistance 30 <br>
 * </li>
 * <li>
 * SPRUCE:
 * Type name: 'Spruce' <br>
 * SubID: 1 <br>
 * Hardness: 2 <br>
 * Blast Resistance 30 <br>
 * </li>
 * <li>
 * OAK:
 * Type name: 'Oak' <br>
 * SubID: 0 <br>
 * Hardness: 2 <br>
 * Blast Resistance 30 <br>
 * </li>
 * </ol>
 */
@SuppressWarnings("JavaDoc")
public class DoubleWoodenSlabMat extends WoodSlabMat
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final int USED_DATA_VALUES = 6;

    public static final DoubleWoodenSlabMat DOUBLE_WOODEN_SLAB_OAK      = new DoubleWoodenSlabMat();
    public static final DoubleWoodenSlabMat DOUBLE_WOODEN_SLAB_SPRUCE   = new DoubleWoodenSlabMat(WoodType.SPRUCE, SlabTypeMat.BOTTOM);
    public static final DoubleWoodenSlabMat DOUBLE_WOODEN_SLAB_BIRCH    = new DoubleWoodenSlabMat(WoodType.BIRCH, SlabTypeMat.BOTTOM);
    public static final DoubleWoodenSlabMat DOUBLE_WOODEN_SLAB_JUNGLE   = new DoubleWoodenSlabMat(WoodType.JUNGLE, SlabTypeMat.BOTTOM);
    public static final DoubleWoodenSlabMat DOUBLE_WOODEN_SLAB_DARK_OAK = new DoubleWoodenSlabMat(WoodType.DARK_OAK, SlabTypeMat.BOTTOM);
    public static final DoubleWoodenSlabMat DOUBLE_WOODEN_SLAB_ACACIA   = new DoubleWoodenSlabMat(WoodType.ACACIA, SlabTypeMat.BOTTOM);

    private static final Map<String, DoubleWoodenSlabMat>    byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<DoubleWoodenSlabMat> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR, Byte.MIN_VALUE);

    @SuppressWarnings("MagicNumber")
    protected DoubleWoodenSlabMat()
    {
        super("DOUBLE_WOODEN_SLAB", 125, "minecraft:double_wooden_slab", "OAK", WoodType.OAK, SlabTypeMat.BOTTOM, 2, 30);
    }

    protected DoubleWoodenSlabMat(final WoodType woodType, final SlabTypeMat slabType)
    {
        super(DOUBLE_WOODEN_SLAB_OAK.name(), DOUBLE_WOODEN_SLAB_OAK.ordinal(), DOUBLE_WOODEN_SLAB_OAK.getMinecraftId(), woodType.name() + (slabType.getFlag() == 0 ? "" : slabType.name()), woodType, slabType, DOUBLE_WOODEN_SLAB_OAK.getHardness(), DOUBLE_WOODEN_SLAB_OAK.getBlastResistance());
    }

    protected DoubleWoodenSlabMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type, final WoodType woodType, final SlabTypeMat slabType, final float hardness, final float blastResistance)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, woodType, slabType, hardness, blastResistance);
    }

    @Override
    public Material ensureValidInventoryItem()
    {
        return WOODEN_SLAB.getWoodType(this.woodType);
    }

    @Override
    public DoubleWoodenSlabMat getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public DoubleWoodenSlabMat getType(final int id)
    {
        return getByID(id);
    }

    @Override
    public DoubleWoodenSlabMat getWoodType(final WoodType woodType)
    {
        return getByID(combine(woodType, this.slabType));
    }

    /**
     * Returns one of DoubleWoodenSlab sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of DoubleWoodenSlab or null
     */
    public static DoubleWoodenSlabMat getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of DoubleWoodenSlab sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of DoubleWoodenSlab or null
     */
    public static DoubleWoodenSlabMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Returns one of DoubleWoodenSlab sub-type based on {@link WoodType} and {@link SlabTypeMat}
     * It will never return null.
     *
     * @param woodType type of wood.
     * @param slabType type of slab.
     *
     * @return sub-type of DoubleWoodenSlab
     */
    public static DoubleWoodenSlabMat getDoubleWoodenSlab(final WoodType woodType, final SlabTypeMat slabType)
    {
        return getByID(combine(woodType, slabType));
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final DoubleWoodenSlabMat element)
    {
        byID.put((byte) element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    @Override
    public DoubleWoodenSlabMat[] types()
    {
        return DoubleWoodenSlabMat.doubleWoodenSlabTypes();
    }

    /**
     * @return array that contains all sub-types of this block.
     */
    public static DoubleWoodenSlabMat[] doubleWoodenSlabTypes()
    {
        return byID.values(new DoubleWoodenSlabMat[byID.size()]);
    }

    static
    {
        DoubleWoodenSlabMat.register(DOUBLE_WOODEN_SLAB_OAK);
        DoubleWoodenSlabMat.register(DOUBLE_WOODEN_SLAB_SPRUCE);
        DoubleWoodenSlabMat.register(DOUBLE_WOODEN_SLAB_BIRCH);
        DoubleWoodenSlabMat.register(DOUBLE_WOODEN_SLAB_JUNGLE);
        DoubleWoodenSlabMat.register(DOUBLE_WOODEN_SLAB_DARK_OAK);
        DoubleWoodenSlabMat.register(DOUBLE_WOODEN_SLAB_ACACIA);
    }
}
