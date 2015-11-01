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

import org.diorite.material.Material;
import org.diorite.utils.collections.maps.CaseInsensitiveMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing 'Nether Wart Block' block material in minecraft. <br>
 * ID of block: 115 <br>
 * String ID of block: minecraft:nether_wart <br>
 * This block can't be used in inventory, valid material for this block: 'Nether Wart' (minecraft:nether_wart(372):0) <br>
 * Hardness: 0 <br>
 * Blast Resistance 0 <br>
 * <br>
 * Subtypes: <br>
 * <ol>
 * <li>
 * RIPE:
 * Type name: 'Ripe' <br>
 * SubID: 3 <br>
 * Hardness: 0 <br>
 * Blast Resistance 0 <br>
 * </li>
 * <li>
 * 2:
 * Type name: '2' <br>
 * SubID: 2 <br>
 * Hardness: 0 <br>
 * Blast Resistance 0 <br>
 * </li>
 * <li>
 * 1:
 * Type name: '1' <br>
 * SubID: 1 <br>
 * Hardness: 0 <br>
 * Blast Resistance 0 <br>
 * </li>
 * <li>
 * 0:
 * Type name: '0' <br>
 * SubID: 0 <br>
 * Hardness: 0 <br>
 * Blast Resistance 0 <br>
 * </li>
 * </ol>
 */
@SuppressWarnings("JavaDoc")
public class NetherWartBlockMat extends CropsMat
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final int USED_DATA_VALUES = 4;

    public static final NetherWartBlockMat NETHER_WART_BLOCK_0    = new NetherWartBlockMat();
    public static final NetherWartBlockMat NETHER_WART_BLOCK_1    = new NetherWartBlockMat("1", 0x1);
    public static final NetherWartBlockMat NETHER_WART_BLOCK_2    = new NetherWartBlockMat("2", 0x2);
    public static final NetherWartBlockMat NETHER_WART_BLOCK_RIPE = new NetherWartBlockMat("RIPE", 0x3);

    private static final Map<String, NetherWartBlockMat>    byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<NetherWartBlockMat> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR, Byte.MIN_VALUE);

    protected final int age;

    @SuppressWarnings("MagicNumber")
    protected NetherWartBlockMat()
    {
        super("NETHER_WART_BLOCK", 115, "minecraft:nether_wart", "0", (byte) 0x00, 0, 0);
        this.age = 0;
    }

    protected NetherWartBlockMat(final String enumName, final int age)
    {
        super(NETHER_WART_BLOCK_0.name(), NETHER_WART_BLOCK_0.ordinal(), NETHER_WART_BLOCK_0.getMinecraftId(), enumName, (byte) age, NETHER_WART_BLOCK_0.getHardness(), NETHER_WART_BLOCK_0.getBlastResistance());
        this.age = age;
    }

    protected NetherWartBlockMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type, final int age, final float hardness, final float blastResistance)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, hardness, blastResistance);
        this.age = age;
    }

    @Override
    public Material ensureValidInventoryItem()
    {
        return NETHER_WART;
    }

    @Override
    public int getAge()
    {
        return this.age;
    }

    @Override
    public NetherWartBlockMat getAge(final int age)
    {
        return getByID(age);
    }

    @Override
    public NetherWartBlockMat getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public NetherWartBlockMat getType(final int id)
    {
        return getByID(id);
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("age", this.age).toString();
    }

    /**
     * Returns one of NetherWartBlock sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of NetherWartBlock or null
     */
    public static NetherWartBlockMat getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of NetherWartBlock sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of NetherWartBlock or null
     */
    public static NetherWartBlockMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Returns one of NetherWartBlock sub-type based on age.
     * It will never return null.
     *
     * @param age age of NetherWartBlock.
     *
     * @return sub-type of NetherWartBlock
     */
    public static NetherWartBlockMat getNetherWartBlock(final int age)
    {
        final NetherWartBlockMat netherWartBlock = getByID(age);
        if (netherWartBlock == null)
        {
            return NETHER_WART_BLOCK_0;
        }
        return netherWartBlock;
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final NetherWartBlockMat element)
    {
        byID.put((byte) element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    @Override
    public NetherWartBlockMat[] types()
    {
        return NetherWartBlockMat.netherWartBlockTypes();
    }

    /**
     * @return array that contains all sub-types of this block.
     */
    public static NetherWartBlockMat[] netherWartBlockTypes()
    {
        return byID.values(new NetherWartBlockMat[byID.size()]);
    }

    static
    {
        NetherWartBlockMat.register(NETHER_WART_BLOCK_0);
        NetherWartBlockMat.register(NETHER_WART_BLOCK_1);
        NetherWartBlockMat.register(NETHER_WART_BLOCK_2);
        NetherWartBlockMat.register(NETHER_WART_BLOCK_RIPE);
    }
}
