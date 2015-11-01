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

import org.diorite.material.VariantMat;
import org.diorite.material.VariantableMat;
import org.diorite.utils.collections.maps.CaseInsensitiveMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing 'Dirt' block material in minecraft. <br>
 * ID of block: 3 <br>
 * String ID of block: minecraft:dirt <br>
 * Hardness: 0,5 <br>
 * Blast Resistance 2,5 <br>
 * <br>
 * Subtypes: <br>
 * <ol>
 * <li>
 * PODZOL:
 * Type name: 'Podzol' <br>
 * SubID: 2 <br>
 * Hardness: 0,5 <br>
 * Blast Resistance 2,5 <br>
 * </li>
 * <li>
 * COARSE:
 * Type name: 'Coarse' <br>
 * SubID: 1 <br>
 * Hardness: 0,5 <br>
 * Blast Resistance 2,5 <br>
 * </li>
 * <li>
 * CLASSIC:
 * Type name: 'Classic' <br>
 * SubID: 0 <br>
 * Hardness: 0,5 <br>
 * Blast Resistance 2,5 <br>
 * </li>
 * </ol>
 */
@SuppressWarnings("JavaDoc")
public class DirtMat extends EarthMat implements VariantableMat
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final int USED_DATA_VALUES = 3;

    public static final DirtMat DIRT        = new DirtMat();
    public static final DirtMat DIRT_COARSE = new DirtMat(0x1, VariantMat.COARSE);
    public static final DirtMat DIRT_PODZOL = new DirtMat(0x2, VariantMat.PODZOL);

    private static final Map<String, DirtMat>    byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<DirtMat> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR, Byte.MIN_VALUE);

    protected final VariantMat variant;

    @SuppressWarnings("MagicNumber")
    protected DirtMat()
    {
        super("DIRT", 3, "minecraft:dirt", "CLASSIC", (byte) 0x00, 0.5f, 2.5f);
        this.variant = VariantMat.CLASSIC;
    }

    protected DirtMat(final int type, final VariantMat variant)
    {
        super(DIRT.name(), DIRT.ordinal(), DIRT.getMinecraftId(), variant.name(), (byte) type, DIRT.getHardness(), DIRT.getBlastResistance());
        this.variant = variant;
    }

    protected DirtMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type, final VariantMat variant, final float hardness, final float blastResistance)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, hardness, blastResistance);
        this.variant = variant;
    }

    @Override
    public DirtMat getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public DirtMat getType(final int id)
    {
        return getByID(id);
    }

    @Override
    public VariantMat getVariant()
    {
        return this.variant;
    }

    @Override
    public DirtMat getVariant(final VariantMat variant)
    {
        for (final DirtMat mat : byName.values())
        {
            if (mat.variant == variant)
            {
                return mat;
            }
        }
        return DIRT;
    }

    /**
     * Returns one of Dirt sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of Dirt or null
     */
    public static DirtMat getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of Dirt sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of Dirt or null
     */
    public static DirtMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final DirtMat element)
    {
        byID.put((byte) element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    @Override
    public DirtMat[] types()
    {
        return DirtMat.dirtTypes();
    }

    /**
     * @return array that contains all sub-types of this block.
     */
    public static DirtMat[] dirtTypes()
    {
        return byID.values(new DirtMat[byID.size()]);
    }

    static
    {
        DirtMat.register(DIRT);
        DirtMat.register(DIRT_COARSE);
        DirtMat.register(DIRT_PODZOL);
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("variant", this.variant).toString();
    }
}
