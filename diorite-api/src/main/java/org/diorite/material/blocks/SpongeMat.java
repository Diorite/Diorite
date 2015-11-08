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
 * Class representing 'Sponge' block material in minecraft. <br>
 * ID of block: 19 <br>
 * String ID of block: minecraft:sponge <br>
 * Hardness: 0,6 <br>
 * Blast Resistance 3 <br>
 * <br>
 * Subtypes: <br>
 * <ol>
 * <li>
 * WEY:
 * Type name: 'Wey' <br>
 * SubID: 1 <br>
 * Hardness: 0,6 <br>
 * Blast Resistance 3 <br>
 * </li>
 * <li>
 * DRY:
 * Type name: 'Dry' <br>
 * SubID: 0 <br>
 * Hardness: 0,6 <br>
 * Blast Resistance 3 <br>
 * </li>
 * </ol>
 */
@SuppressWarnings("JavaDoc")
public class SpongeMat extends BlockMaterialData
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final int USED_DATA_VALUES = 2;

    public static final SpongeMat SPONGE     = new SpongeMat();
    public static final SpongeMat SPONGE_WET = new SpongeMat(true);

    private static final Map<String, SpongeMat>    byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<SpongeMat> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR, Byte.MIN_VALUE);

    protected final boolean isWet;

    @SuppressWarnings("MagicNumber")
    protected SpongeMat()
    {
        super("SPONGE", 19, "minecraft:sponge", "DRY", (byte) 0x00, 0.6f, 3);
        this.isWet = false;
    }

    protected SpongeMat(final boolean isWet)
    {
        super(SPONGE.name(), SPONGE.ordinal(), SPONGE.getMinecraftId(), isWet ? "WEY" : "DRY", (byte) (isWet ? 1 : 0), SPONGE.getHardness(), SPONGE.getBlastResistance());
        this.isWet = isWet;
    }

    protected SpongeMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type, final boolean isWet, final float hardness, final float blastResistance)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, hardness, blastResistance);
        this.isWet = isWet;
    }

    @Override
    public SpongeMat getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public SpongeMat getType(final int id)
    {
        return getByID(id);
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("isWet", this.isWet).toString();
    }

    public boolean isWet()
    {
        return this.isWet;
    }

    public SpongeMat getWet(final boolean wet)
    {
        return getByID(wet ? 1 : 0);
    }

    /**
     * Returns one of Sponge sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of Sponge or null
     */
    public static SpongeMat getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of Sponge sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of Sponge or null
     */
    public static SpongeMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final SpongeMat element)
    {
        byID.put((byte) element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    @Override
    public SpongeMat[] types()
    {
        return SpongeMat.spongeTypes();
    }

    /**
     * @return array that contains all sub-types of this block.
     */
    public static SpongeMat[] spongeTypes()
    {
        return byID.values(new SpongeMat[byID.size()]);
    }

    static
    {
        SpongeMat.register(SPONGE);
        SpongeMat.register(SPONGE_WET);
    }
}
