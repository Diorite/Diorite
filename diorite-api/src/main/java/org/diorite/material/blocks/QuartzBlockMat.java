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

import org.diorite.material.OreItemMat;
import org.diorite.material.VariantMat;
import org.diorite.material.VariantableMat;
import org.diorite.utils.collections.maps.CaseInsensitiveMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing 'Quartz Block' block material in minecraft. <br>
 * ID of block: 155 <br>
 * String ID of block: minecraft:quartz_block <br>
 * Hardness: 0,8 <br>
 * Blast Resistance 4 <br>
 * <br>
 * Subtypes: <br>
 * <ol>
 * <li>
 * PILLAR_EAST_WEST:
 * Type name: 'Pillar East West' <br>
 * SubID: 4 <br>
 * Hardness: 0,8 <br>
 * Blast Resistance 4 <br>
 * </li>
 * <li>
 * PILLAR_NORTH_SOUTH:
 * Type name: 'Pillar North South' <br>
 * SubID: 3 <br>
 * Hardness: 0,8 <br>
 * Blast Resistance 4 <br>
 * </li>
 * <li>
 * PILLAR_VERTICAL:
 * Type name: 'Pillar Vertical' <br>
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
 * QUARTZ_BLOCK:
 * Type name: 'Quartz Block' <br>
 * SubID: 0 <br>
 * Hardness: 0,8 <br>
 * Blast Resistance 4 <br>
 * </li>
 * </ol>
 */
@SuppressWarnings("JavaDoc")
public class QuartzBlockMat extends OreBlockMat implements VariantableMat
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final int USED_DATA_VALUES = 5;

    public static final QuartzBlockMat QUARTZ_BLOCK                    = new QuartzBlockMat();
    public static final QuartzBlockMat QUARTZ_BLOCK_CHISELED           = new QuartzBlockMat(0x1, VariantMat.CHISELED);
    public static final QuartzBlockMat QUARTZ_BLOCK_PILLAR_VERTICAL    = new QuartzBlockMat(0x2, VariantMat.PILLAR_VERTICAL);
    public static final QuartzBlockMat QUARTZ_BLOCK_PILLAR_NORTH_SOUTH = new QuartzBlockMat(0x3, VariantMat.PILLAR_NORTH_SOUTH);
    public static final QuartzBlockMat QUARTZ_BLOCK_PILLAR_EAST_WEST   = new QuartzBlockMat(0x4, VariantMat.PILLAR_EAST_WEST);

    private static final Map<String, QuartzBlockMat>    byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<QuartzBlockMat> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR, Byte.MIN_VALUE);

    protected final VariantMat variant;

    @SuppressWarnings("MagicNumber")
    protected QuartzBlockMat()
    {
        super("QUARTZ_BLOCK", 155, "minecraft:quartz_block", "QUARTZ_BLOCK", (byte) 0x00, QUARTZ_ORE, QUARTZ, 0.8f, 4);
        this.variant = VariantMat.CLASSIC;
    }

    protected QuartzBlockMat(final int type, final VariantMat variant)
    {
        super(QUARTZ_BLOCK.name(), QUARTZ_BLOCK.ordinal(), QUARTZ_BLOCK.getMinecraftId(), variant.name(), (byte) type, QUARTZ_BLOCK.getOre(), QUARTZ_BLOCK.getOreItem(), QUARTZ_BLOCK.getHardness(), QUARTZ_BLOCK.getBlastResistance());
        this.variant = variant;
    }

    protected QuartzBlockMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type, final OreMat ore, final OreItemMat item, final VariantMat variant, final float hardness, final float blastResistance)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, ore, item, hardness, blastResistance);
        this.variant = variant;
    }

    @Override
    public QuartzBlockMat getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public QuartzBlockMat getType(final int id)
    {
        return getByID(id);
    }

    @Override
    public VariantMat getVariant()
    {
        return this.variant;
    }

    @Override
    public QuartzBlockMat getVariant(final VariantMat variant)
    {
        for (final QuartzBlockMat mat : byName.values())
        {
            if (mat.variant == variant)
            {
                return mat;
            }
        }
        return QUARTZ_BLOCK;
    }

    /**
     * Returns one of QuartzBlock sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of QuartzBlock or null
     */
    public static QuartzBlockMat getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of QuartzBlock sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of QuartzBlock or null
     */
    public static QuartzBlockMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final QuartzBlockMat element)
    {
        byID.put((byte) element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    @Override
    public QuartzBlockMat[] types()
    {
        return QuartzBlockMat.quartzBlockTypes();
    }

    /**
     * @return array that contains all sub-types of this block.
     */
    public static QuartzBlockMat[] quartzBlockTypes()
    {
        return byID.values(new QuartzBlockMat[byID.size()]);
    }

    static
    {
        QuartzBlockMat.register(QUARTZ_BLOCK);
        QuartzBlockMat.register(QUARTZ_BLOCK_CHISELED);
        QuartzBlockMat.register(QUARTZ_BLOCK_PILLAR_VERTICAL);
        QuartzBlockMat.register(QUARTZ_BLOCK_PILLAR_NORTH_SOUTH);
        QuartzBlockMat.register(QUARTZ_BLOCK_PILLAR_EAST_WEST);
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("variant", this.variant).toString();
    }
}
