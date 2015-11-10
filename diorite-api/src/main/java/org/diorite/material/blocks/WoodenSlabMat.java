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

import org.diorite.material.FuelMat;
import org.diorite.material.Material;
import org.diorite.material.SlabTypeMat;
import org.diorite.material.WoodType;
import org.diorite.utils.collections.maps.CaseInsensitiveMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing 'Wooden Slab' block material in minecraft. <br>
 * ID of block: 126 <br>
 * String ID of block: minecraft:wooden_slab <br>
 * Hardness: 2 <br>
 * Blast Resistance 15 <br>
 * <br>
 * Subtypes: <br>
 * <ol>
 * <li>
 * DARK_OAKUPPER:
 * Type name: 'Dark Oakupper' <br>
 * SubID: 13 <br>
 * Hardness: 2 <br>
 * Blast Resistance 15 <br>
 * </li>
 * <li>
 * ACACIAUPPER:
 * Type name: 'Acaciaupper' <br>
 * SubID: 12 <br>
 * Hardness: 2 <br>
 * Blast Resistance 15 <br>
 * </li>
 * <li>
 * JUNGLEUPPER:
 * Type name: 'Jungleupper' <br>
 * SubID: 11 <br>
 * Hardness: 2 <br>
 * Blast Resistance 15 <br>
 * </li>
 * <li>
 * BIRCHUPPER:
 * Type name: 'Birchupper' <br>
 * SubID: 10 <br>
 * Hardness: 2 <br>
 * Blast Resistance 15 <br>
 * </li>
 * <li>
 * SPRUCEUPPER:
 * Type name: 'Spruceupper' <br>
 * SubID: 9 <br>
 * Hardness: 2 <br>
 * Blast Resistance 15 <br>
 * </li>
 * <li>
 * OAKUPPER:
 * Type name: 'Oakupper' <br>
 * SubID: 8 <br>
 * Hardness: 2 <br>
 * Blast Resistance 15 <br>
 * </li>
 * <li>
 * DARK_OAK:
 * Type name: 'Dark Oak' <br>
 * SubID: 5 <br>
 * Hardness: 2 <br>
 * Blast Resistance 15 <br>
 * </li>
 * <li>
 * ACACIA:
 * Type name: 'Acacia' <br>
 * SubID: 4 <br>
 * Hardness: 2 <br>
 * Blast Resistance 15 <br>
 * </li>
 * <li>
 * JUNGLE:
 * Type name: 'Jungle' <br>
 * SubID: 3 <br>
 * Hardness: 2 <br>
 * Blast Resistance 15 <br>
 * </li>
 * <li>
 * BIRCH:
 * Type name: 'Birch' <br>
 * SubID: 2 <br>
 * Hardness: 2 <br>
 * Blast Resistance 15 <br>
 * </li>
 * <li>
 * SPRUCE:
 * Type name: 'Spruce' <br>
 * SubID: 1 <br>
 * Hardness: 2 <br>
 * Blast Resistance 15 <br>
 * </li>
 * <li>
 * OAK:
 * Type name: 'Oak' <br>
 * SubID: 0 <br>
 * Hardness: 2 <br>
 * Blast Resistance 15 <br>
 * </li>
 * </ol>
 */
@SuppressWarnings("JavaDoc")
public class WoodenSlabMat extends WoodSlabMat implements FuelMat
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final int USED_DATA_VALUES = 12;

    public static final WoodenSlabMat WOODEN_SLAB_OAK      = new WoodenSlabMat();
    public static final WoodenSlabMat WOODEN_SLAB_SPRUCE   = new WoodenSlabMat(WoodType.SPRUCE, SlabTypeMat.BOTTOM);
    public static final WoodenSlabMat WOODEN_SLAB_BIRCH    = new WoodenSlabMat(WoodType.BIRCH, SlabTypeMat.BOTTOM);
    public static final WoodenSlabMat WOODEN_SLAB_JUNGLE   = new WoodenSlabMat(WoodType.JUNGLE, SlabTypeMat.BOTTOM);
    public static final WoodenSlabMat WOODEN_SLAB_DARK_OAK = new WoodenSlabMat(WoodType.DARK_OAK, SlabTypeMat.BOTTOM);
    public static final WoodenSlabMat WOODEN_SLAB_ACACIA   = new WoodenSlabMat(WoodType.ACACIA, SlabTypeMat.BOTTOM);

    public static final WoodenSlabMat WOODEN_SLAB_OAK_UPPER      = new WoodenSlabMat(WoodType.OAK, SlabTypeMat.UPPER);
    public static final WoodenSlabMat WOODEN_SLAB_SPRUCE_UPPER   = new WoodenSlabMat(WoodType.SPRUCE, SlabTypeMat.UPPER);
    public static final WoodenSlabMat WOODEN_SLAB_BIRCH_UPPER    = new WoodenSlabMat(WoodType.BIRCH, SlabTypeMat.UPPER);
    public static final WoodenSlabMat WOODEN_SLAB_JUNGLE_UPPER   = new WoodenSlabMat(WoodType.JUNGLE, SlabTypeMat.UPPER);
    public static final WoodenSlabMat WOODEN_SLAB_DARK_OAK_UPPER = new WoodenSlabMat(WoodType.DARK_OAK, SlabTypeMat.UPPER);
    public static final WoodenSlabMat WOODEN_SLAB_ACACIA_UPPER   = new WoodenSlabMat(WoodType.ACACIA, SlabTypeMat.UPPER);

    private static final Map<String, WoodenSlabMat>    byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<WoodenSlabMat> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR, Byte.MIN_VALUE);

    @SuppressWarnings("MagicNumber")
    protected WoodenSlabMat()
    {
        super("WOODEN_SLAB", 126, "minecraft:wooden_slab", "OAK", WoodType.OAK, SlabTypeMat.BOTTOM, 2, 15);
    }

    public WoodenSlabMat(final WoodType woodType, final SlabTypeMat slabType)
    {
        super(WOODEN_SLAB_OAK.name(), WOODEN_SLAB_OAK.ordinal(), WOODEN_SLAB_OAK.getMinecraftId(), woodType.name() + (slabType.getFlag() == 0 ? "" : slabType.name()), woodType, slabType, WOODEN_SLAB_OAK.getHardness(), WOODEN_SLAB_OAK.getBlastResistance());
    }

    @Override
    public Material ensureValidInventoryItem()
    {
        return this.getType(this.woodType, SlabTypeMat.BOTTOM);
    }

    @Override
    public WoodenSlabMat getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public WoodenSlabMat getType(final int id)
    {
        return getByID(id);
    }

    @Override
    public WoodenSlabMat getWoodType(final WoodType woodType)
    {
        return getByID(combine(woodType, this.slabType));
    }

    @SuppressWarnings("MagicNumber")
    @Override
    public int getFuelPower()
    {
        return 750;
    }

    /**
     * Returns one of WoodenSlab sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of WoodenSlab or null
     */
    public static WoodenSlabMat getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of WoodenSlab sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of WoodenSlab or null
     */
    public static WoodenSlabMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Returns one of WoodenSlab sub-type based on {@link WoodType} and {@link SlabTypeMat}
     * It will never return null.
     *
     * @param woodType type of wood.
     * @param slabType type of slab.
     *
     * @return sub-type of WoodenSlab
     */
    public static WoodenSlabMat getWoodenSlab(final WoodType woodType, final SlabTypeMat slabType)
    {
        return getByID(combine(woodType, slabType));
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final WoodenSlabMat element)
    {
        byID.put((byte) element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    @Override
    public WoodenSlabMat[] types()
    {
        return WoodenSlabMat.woodenSlabTypes();
    }

    /**
     * @return array that contains all sub-types of this block.
     */
    public static WoodenSlabMat[] woodenSlabTypes()
    {
        return byID.values(new WoodenSlabMat[byID.size()]);
    }

    static
    {
        WoodenSlabMat.register(WOODEN_SLAB_OAK);
        WoodenSlabMat.register(WOODEN_SLAB_SPRUCE);
        WoodenSlabMat.register(WOODEN_SLAB_BIRCH);
        WoodenSlabMat.register(WOODEN_SLAB_JUNGLE);
        WoodenSlabMat.register(WOODEN_SLAB_DARK_OAK);
        WoodenSlabMat.register(WOODEN_SLAB_ACACIA);
        WoodenSlabMat.register(WOODEN_SLAB_OAK_UPPER);
        WoodenSlabMat.register(WOODEN_SLAB_SPRUCE_UPPER);
        WoodenSlabMat.register(WOODEN_SLAB_BIRCH_UPPER);
        WoodenSlabMat.register(WOODEN_SLAB_JUNGLE_UPPER);
        WoodenSlabMat.register(WOODEN_SLAB_DARK_OAK_UPPER);
        WoodenSlabMat.register(WOODEN_SLAB_ACACIA_UPPER);
    }
}
