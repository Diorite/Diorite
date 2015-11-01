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
 * Class representing 'Flower Pot Block' block material in minecraft. <br>
 * ID of block: 140 <br>
 * String ID of block: minecraft:flower_pot <br>
 * This block can't be used in inventory, valid material for this block: 'Flower Pot' (minecraft:flower_pot(390):0) <br>
 * Hardness: 0 <br>
 * Blast Resistance 0 <br>
 * <br>
 * Subtypes: <br>
 * <ol>
 * <li>
 * LEGACY_DARK_OAK_SAPLING:
 * Type name: 'Legacy Dark Oak Sapling' <br>
 * SubID: 13 <br>
 * Hardness: 0 <br>
 * Blast Resistance 0 <br>
 * </li>
 * <li>
 * LEGACY_ACACIA_SAPLING:
 * Type name: 'Legacy Acacia Sapling' <br>
 * SubID: 12 <br>
 * Hardness: 0 <br>
 * Blast Resistance 0 <br>
 * </li>
 * <li>
 * LEGACY_FERN:
 * Type name: 'Legacy Fern' <br>
 * SubID: 11 <br>
 * Hardness: 0 <br>
 * Blast Resistance 0 <br>
 * </li>
 * <li>
 * LEGACY_DEAD_BUSH:
 * Type name: 'Legacy Dead Bush' <br>
 * SubID: 10 <br>
 * Hardness: 0 <br>
 * Blast Resistance 0 <br>
 * </li>
 * <li>
 * LEGACY_CACTUS:
 * Type name: 'Legacy Cactus' <br>
 * SubID: 9 <br>
 * Hardness: 0 <br>
 * Blast Resistance 0 <br>
 * </li>
 * <li>
 * LEGACY_BROWN_MUSHROOM:
 * Type name: 'Legacy Brown Mushroom' <br>
 * SubID: 8 <br>
 * Hardness: 0 <br>
 * Blast Resistance 0 <br>
 * </li>
 * <li>
 * LEGACY_RED_MUSHROOM:
 * Type name: 'Legacy Red Mushroom' <br>
 * SubID: 7 <br>
 * Hardness: 0 <br>
 * Blast Resistance 0 <br>
 * </li>
 * <li>
 * LEGACY_JUNGLE_SAPLING:
 * Type name: 'Legacy Jungle Sapling' <br>
 * SubID: 6 <br>
 * Hardness: 0 <br>
 * Blast Resistance 0 <br>
 * </li>
 * <li>
 * LEGACY_BIRCH_SAPLING:
 * Type name: 'Legacy Birch Sapling' <br>
 * SubID: 5 <br>
 * Hardness: 0 <br>
 * Blast Resistance 0 <br>
 * </li>
 * <li>
 * LEGACY_SPRUCE_SAPLING:
 * Type name: 'Legacy Spruce Sapling' <br>
 * SubID: 4 <br>
 * Hardness: 0 <br>
 * Blast Resistance 0 <br>
 * </li>
 * <li>
 * LEGACY_OAK_SAPLING:
 * Type name: 'Legacy Oak Sapling' <br>
 * SubID: 3 <br>
 * Hardness: 0 <br>
 * Blast Resistance 0 <br>
 * </li>
 * <li>
 * LEGACY_DANDELION:
 * Type name: 'Legacy Dandelion' <br>
 * SubID: 2 <br>
 * Hardness: 0 <br>
 * Blast Resistance 0 <br>
 * </li>
 * <li>
 * LEGACY_POPPY:
 * Type name: 'Legacy Poppy' <br>
 * SubID: 1 <br>
 * Hardness: 0 <br>
 * Blast Resistance 0 <br>
 * </li>
 * <li>
 * EMPTY:
 * Type name: 'Empty' <br>
 * SubID: 0 <br>
 * Hardness: 0 <br>
 * Blast Resistance 0 <br>
 * </li>
 * </ol>
 */
@SuppressWarnings("JavaDoc")
public class FlowerPotBlockMat extends BlockMaterialData
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final int USED_DATA_VALUES = 14;

    public static final FlowerPotBlockMat FLOWER_POT_BLOCK_EMPTY                   = new FlowerPotBlockMat();
    @Deprecated
    public static final FlowerPotBlockMat FLOWER_POT_BLOCK_LEGACY_POPPY            = new FlowerPotBlockMat("LEGACY_POPPY", 0x1);
    @Deprecated
    public static final FlowerPotBlockMat FLOWER_POT_BLOCK_LEGACY_DANDELION        = new FlowerPotBlockMat("LEGACY_DANDELION", 0x2);
    @Deprecated
    public static final FlowerPotBlockMat FLOWER_POT_BLOCK_LEGACY_OAK_SAPLING      = new FlowerPotBlockMat("LEGACY_OAK_SAPLING", 0x3);
    @Deprecated
    public static final FlowerPotBlockMat FLOWER_POT_BLOCK_LEGACY_SPRUCE_SAPLING   = new FlowerPotBlockMat("LEGACY_SPRUCE_SAPLING", 0x4);
    @Deprecated
    public static final FlowerPotBlockMat FLOWER_POT_BLOCK_LEGACY_BIRCH_SAPLING    = new FlowerPotBlockMat("LEGACY_BIRCH_SAPLING", 0x5);
    @Deprecated
    public static final FlowerPotBlockMat FLOWER_POT_BLOCK_LEGACY_JUNGLE_SAPLING   = new FlowerPotBlockMat("LEGACY_JUNGLE_SAPLING", 0x6);
    @Deprecated
    public static final FlowerPotBlockMat FLOWER_POT_BLOCK_LEGACY_RED_MUSHROOM     = new FlowerPotBlockMat("LEGACY_RED_MUSHROOM", 0x7);
    @Deprecated
    public static final FlowerPotBlockMat FLOWER_POT_BLOCK_LEGACY_BROWN_MUSHROOM   = new FlowerPotBlockMat("LEGACY_BROWN_MUSHROOM", 0x8);
    @Deprecated
    public static final FlowerPotBlockMat FLOWER_POT_BLOCK_LEGACY_CACTUS           = new FlowerPotBlockMat("LEGACY_CACTUS", 0x9);
    @Deprecated
    public static final FlowerPotBlockMat FLOWER_POT_BLOCK_LEGACY_DEAD_BUSH        = new FlowerPotBlockMat("LEGACY_DEAD_BUSH", 0xA);
    @SuppressWarnings("MagicNumber")
    @Deprecated
    public static final FlowerPotBlockMat FLOWER_POT_BLOCK_LEGACY_FERN             = new FlowerPotBlockMat("LEGACY_FERN", 0xB);
    @SuppressWarnings("MagicNumber")
    @Deprecated
    public static final FlowerPotBlockMat FLOWER_POT_BLOCK_LEGACY_ACACIA_SAPLING   = new FlowerPotBlockMat("LEGACY_ACACIA_SAPLING", 0xC);
    @SuppressWarnings("MagicNumber")
    @Deprecated
    public static final FlowerPotBlockMat FLOWER_POT_BLOCK_LEGACY_DARK_OAK_SAPLING = new FlowerPotBlockMat("LEGACY_DARK_OAK_SAPLING", 0xD);

    private static final Map<String, FlowerPotBlockMat>    byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<FlowerPotBlockMat> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR, Byte.MIN_VALUE);

    @SuppressWarnings("MagicNumber")
    protected FlowerPotBlockMat()
    {
        super("FLOWER_POT_BLOCK", 140, "minecraft:flower_pot", "EMPTY", (byte) 0x00, 0, 0);
    }

    protected FlowerPotBlockMat(final String enumName, final int type)
    {
        super(FLOWER_POT_BLOCK_EMPTY.name(), FLOWER_POT_BLOCK_EMPTY.ordinal(), FLOWER_POT_BLOCK_EMPTY.getMinecraftId(), enumName, (byte) type, FLOWER_POT_BLOCK_EMPTY.getHardness(), FLOWER_POT_BLOCK_EMPTY.getBlastResistance());
    }

    protected FlowerPotBlockMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type, final float hardness, final float blastResistance)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, hardness, blastResistance);
    }

    @Override
    public Material ensureValidInventoryItem()
    {
        return Material.FLOWER_POT;
    }

    @Override
    public FlowerPotBlockMat getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public FlowerPotBlockMat getType(final int id)
    {
        return getByID(id);
    }

    /**
     * Returns one of FlowerPot sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of FlowerPot or null
     */
    public static FlowerPotBlockMat getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of FlowerPot sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of FlowerPot or null
     */
    public static FlowerPotBlockMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final FlowerPotBlockMat element)
    {
        byID.put((byte) element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    @Override
    public FlowerPotBlockMat[] types()
    {
        return FlowerPotBlockMat.flowerPotTypes();
    }

    /**
     * @return array that contains all sub-types of this block.
     */
    public static FlowerPotBlockMat[] flowerPotTypes()
    {
        return byID.values(new FlowerPotBlockMat[byID.size()]);
    }

    static
    {
        FlowerPotBlockMat.register(FLOWER_POT_BLOCK_EMPTY);
        FlowerPotBlockMat.register(FLOWER_POT_BLOCK_LEGACY_POPPY);
        FlowerPotBlockMat.register(FLOWER_POT_BLOCK_LEGACY_DANDELION);
        FlowerPotBlockMat.register(FLOWER_POT_BLOCK_LEGACY_OAK_SAPLING);
        FlowerPotBlockMat.register(FLOWER_POT_BLOCK_LEGACY_SPRUCE_SAPLING);
        FlowerPotBlockMat.register(FLOWER_POT_BLOCK_LEGACY_BIRCH_SAPLING);
        FlowerPotBlockMat.register(FLOWER_POT_BLOCK_LEGACY_JUNGLE_SAPLING);
        FlowerPotBlockMat.register(FLOWER_POT_BLOCK_LEGACY_RED_MUSHROOM);
        FlowerPotBlockMat.register(FLOWER_POT_BLOCK_LEGACY_BROWN_MUSHROOM);
        FlowerPotBlockMat.register(FLOWER_POT_BLOCK_LEGACY_CACTUS);
        FlowerPotBlockMat.register(FLOWER_POT_BLOCK_LEGACY_DEAD_BUSH);
        FlowerPotBlockMat.register(FLOWER_POT_BLOCK_LEGACY_FERN);
        FlowerPotBlockMat.register(FLOWER_POT_BLOCK_LEGACY_ACACIA_SAPLING);
        FlowerPotBlockMat.register(FLOWER_POT_BLOCK_LEGACY_DARK_OAK_SAPLING);
    }
}
