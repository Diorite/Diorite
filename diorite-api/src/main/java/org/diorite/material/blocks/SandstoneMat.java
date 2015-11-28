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

import it.unimi.dsi.fastutil.bytes.Byte2ObjectMap;
import it.unimi.dsi.fastutil.bytes.Byte2ObjectOpenHashMap;

/**
 * Class representing 'Sandstone' block material in minecraft. <br>
 * ID of block: 24 <br>
 * String ID of block: minecraft:sandstone <br>
 * Hardness: 0,8 <br>
 * Blast Resistance 4 <br>
 * <br>
 * Subtypes: <br>
 * <ol>
 * <li>
 * SMOOTH:
 * Type name: 'Smooth' <br>
 * SubID: 2 <br>
 * Hardness: 0,8 <br>
 * Blast Resistance 4 <br>
 * </li>
 * <li>
 * CHISELED:
 * Type name: 'Chiseled' <br>
 * SubID: 1 <br>
 * Hardness: 0,8 <br>
 * Blast Resistance 4 <br>
 * </li>
 * <li>
 * CLASSIC:
 * Type name: 'Classic' <br>
 * SubID: 0 <br>
 * Hardness: 0,8 <br>
 * Blast Resistance 4 <br>
 * </li>
 * </ol>
 */
@SuppressWarnings("JavaDoc")
public class SandstoneMat extends StonyMat implements VariantableMat
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final int USED_DATA_VALUES = 3;

    public static final SandstoneMat SANDSTONE          = new SandstoneMat();
    public static final SandstoneMat SANDSTONE_CHISELED = new SandstoneMat(0x1, VariantMat.CHISELED);
    public static final SandstoneMat SANDSTONE_SMOOTH   = new SandstoneMat(0x2, VariantMat.SMOOTH);

    private static final Map<String, SandstoneMat>    byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final Byte2ObjectMap<SandstoneMat> byID   = new Byte2ObjectOpenHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    protected final VariantMat variant;

    @SuppressWarnings("MagicNumber")
    protected SandstoneMat()
    {
        super("SANDSTONE", 24, "minecraft:sandstone", "CLASSIC", (byte) 0x00, 0.8f, 4);
        this.variant = VariantMat.CLASSIC;
    }

    protected SandstoneMat(final int type, final VariantMat variant)
    {
        super(SANDSTONE.name(), SANDSTONE.ordinal(), SANDSTONE.getMinecraftId(), variant.name(), (byte) type, SANDSTONE.getHardness(), SANDSTONE.getBlastResistance());
        this.variant = variant;
    }

    protected SandstoneMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type, final VariantMat variant, final float hardness, final float blastResistance)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, hardness, blastResistance);
        this.variant = variant;
    }

    @Override
    public SandstoneMat getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public SandstoneMat getType(final int id)
    {
        return getByID(id);
    }

    @Override
    public VariantMat getVariant()
    {
        return this.variant;
    }

    @Override
    public SandstoneMat getVariant(final VariantMat variant)
    {
        for (final SandstoneMat mat : byName.values())
        {
            if (mat.variant == variant)
            {
                return mat;
            }
        }
        return SANDSTONE;
    }

    /**
     * Returns one of Sandstone sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of Sandstone or null
     */
    public static SandstoneMat getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of Sandstone sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of Sandstone or null
     */
    public static SandstoneMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final SandstoneMat element)
    {
        byID.put((byte) element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    @Override
    public SandstoneMat[] types()
    {
        return SandstoneMat.sandstoneTypes();
    }

    /**
     * @return array that contains all sub-types of this block.
     */
    public static SandstoneMat[] sandstoneTypes()
    {
        return byID.values().toArray(new SandstoneMat[byID.size()]);
    }

    static
    {
        SandstoneMat.register(SANDSTONE);
        SandstoneMat.register(SANDSTONE_CHISELED);
        SandstoneMat.register(SANDSTONE_SMOOTH);
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("variant", this.variant).toString();
    }
}
