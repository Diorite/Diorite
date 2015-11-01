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
 * Class representing 'Stone Brick' block material in minecraft. <br>
 * ID of block: 98 <br>
 * String ID of block: minecraft:stonebrick <br>
 * Hardness: 1,5 <br>
 * Blast Resistance 30 <br>
 * <br>
 * Subtypes: <br>
 * <ol>
 * <li>
 * CHISELED:
 * Type name: 'Chiseled' <br>
 * SubID: 3 <br>
 * Hardness: 1,5 <br>
 * Blast Resistance 30 <br>
 * </li>
 * <li>
 * CRACKED:
 * Type name: 'Cracked' <br>
 * SubID: 2 <br>
 * Hardness: 1,5 <br>
 * Blast Resistance 30 <br>
 * </li>
 * <li>
 * MOSSY:
 * Type name: 'Mossy' <br>
 * SubID: 1 <br>
 * Hardness: 1,5 <br>
 * Blast Resistance 30 <br>
 * </li>
 * <li>
 * CLASSIC:
 * Type name: 'Classic' <br>
 * SubID: 0 <br>
 * Hardness: 1,5 <br>
 * Blast Resistance 30 <br>
 * </li>
 * </ol>
 */
@SuppressWarnings("JavaDoc")
public class StoneBrickMat extends StonyMat implements VariantableMat
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final int USED_DATA_VALUES = 4;

    public static final StoneBrickMat STONE_BRICK          = new StoneBrickMat();
    public static final StoneBrickMat STONE_BRICK_MOSSY    = new StoneBrickMat(0x1, VariantMat.MOSSY);
    public static final StoneBrickMat STONE_BRICK_CRACKED  = new StoneBrickMat(0x2, VariantMat.CRACKED);
    public static final StoneBrickMat STONE_BRICK_CHISELED = new StoneBrickMat(0x3, VariantMat.CHISELED);

    private static final Map<String, StoneBrickMat>    byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<StoneBrickMat> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR, Byte.MIN_VALUE);

    protected final VariantMat variant;

    @SuppressWarnings("MagicNumber")
    protected StoneBrickMat()
    {
        super("STONE_BRICK", 98, "minecraft:stonebrick", "CLASSIC", (byte) 0x00, 1.5f, 30);
        this.variant = VariantMat.CLASSIC;
    }

    protected StoneBrickMat(final int type, final VariantMat varaint)
    {
        super(STONE_BRICK.name(), STONE_BRICK.ordinal(), STONE_BRICK.getMinecraftId(), varaint.name(), (byte) type, STONE_BRICK.getHardness(), STONE_BRICK.getBlastResistance());
        this.variant = varaint;
    }

    protected StoneBrickMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type, final VariantMat variant, final float hardness, final float blastResistance)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, hardness, blastResistance);
        this.variant = variant;
    }

    @Override
    public StoneBrickMat getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public StoneBrickMat getType(final int id)
    {
        return getByID(id);
    }

    @Override
    public VariantMat getVariant()
    {
        return this.variant;
    }

    @Override
    public StoneBrickMat getVariant(final VariantMat variant)
    {
        for (final StoneBrickMat mat : byName.values())
        {
            if (mat.variant == variant)
            {
                return mat;
            }
        }
        return STONE_BRICK;
    }

    /**
     * Returns one of StoneBrick sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of StoneBrick or null
     */
    public static StoneBrickMat getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of StoneBrick sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of StoneBrick or null
     */
    public static StoneBrickMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final StoneBrickMat element)
    {
        byID.put((byte) element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    @Override
    public StoneBrickMat[] types()
    {
        return StoneBrickMat.stoneBrickTypes();
    }

    /**
     * @return array that contains all sub-types of this block.
     */
    public static StoneBrickMat[] stoneBrickTypes()
    {
        return byID.values(new StoneBrickMat[byID.size()]);
    }

    static
    {
        StoneBrickMat.register(STONE_BRICK);
        StoneBrickMat.register(STONE_BRICK_MOSSY);
        StoneBrickMat.register(STONE_BRICK_CRACKED);
        StoneBrickMat.register(STONE_BRICK_CHISELED);
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("variant", this.variant).toString();
    }
}
