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

package org.diorite.material.items;

import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.DyeColor;
import org.diorite.material.ColorableMat;
import org.diorite.material.ItemMaterialData;
import org.diorite.material.OreItemMat;
import org.diorite.material.blocks.LapisBlockMat;
import org.diorite.material.blocks.LapisOreMat;
import org.diorite.material.blocks.OreBlockMat;
import org.diorite.material.blocks.OreMat;
import org.diorite.utils.collections.maps.CaseInsensitiveMap;

import gnu.trove.map.TShortObjectMap;
import gnu.trove.map.hash.TShortObjectHashMap;

/**
 * Class representing 'Dye' item material in minecraft. <br>
 * ID of material: 351 <br>
 * String ID of material: minecraft:dye <br>
 * Max item stack size: 64 <br>
 * <br>
 * Subtypes: <br>
 * <ol>
 * <li>
 * BONE_MEAL:
 * Type name: 'Bone Meal' <br>
 * SubID: 15 <br>
 * Max item stack size: 64
 * </li>
 * <li>
 * ORANGE:
 * Type name: 'Orange' <br>
 * SubID: 14 <br>
 * Max item stack size: 64
 * </li>
 * <li>
 * MAGENTA:
 * Type name: 'Magenta' <br>
 * SubID: 13 <br>
 * Max item stack size: 64
 * </li>
 * <li>
 * LIGHT_BLUE:
 * Type name: 'Light Blue' <br>
 * SubID: 12 <br>
 * Max item stack size: 64
 * </li>
 * <li>
 * YELLOW:
 * Type name: 'Yellow' <br>
 * SubID: 11 <br>
 * Max item stack size: 64
 * </li>
 * <li>
 * LIME:
 * Type name: 'Lime' <br>
 * SubID: 10 <br>
 * Max item stack size: 64
 * </li>
 * <li>
 * PINK:
 * Type name: 'Pink' <br>
 * SubID: 9 <br>
 * Max item stack size: 64
 * </li>
 * <li>
 * GRAY:
 * Type name: 'Gray' <br>
 * SubID: 8 <br>
 * Max item stack size: 64
 * </li>
 * <li>
 * LIGHT_GRAY:
 * Type name: 'Light Gray' <br>
 * SubID: 7 <br>
 * Max item stack size: 64
 * </li>
 * <li>
 * CYAN:
 * Type name: 'Cyan' <br>
 * SubID: 6 <br>
 * Max item stack size: 64
 * </li>
 * <li>
 * PURPLE:
 * Type name: 'Purple' <br>
 * SubID: 5 <br>
 * Max item stack size: 64
 * </li>
 * <li>
 * LAPIS_LAZULI:
 * Type name: 'Lapis Lazuli' <br>
 * SubID: 4 <br>
 * Max item stack size: 64
 * </li>
 * <li>
 * COCOA_BEANS:
 * Type name: 'Cocoa Beans' <br>
 * SubID: 3 <br>
 * Max item stack size: 64
 * </li>
 * <li>
 * GREEN:
 * Type name: 'Green' <br>
 * SubID: 2 <br>
 * Max item stack size: 64
 * </li>
 * <li>
 * RED:
 * Type name: 'Red' <br>
 * SubID: 1 <br>
 * Max item stack size: 64
 * </li>
 * <li>
 * INK_SAC:
 * Type name: 'Ink Sac' <br>
 * SubID: 0 <br>
 * Max item stack size: 64
 * </li>
 * </ol>
 */
@SuppressWarnings("JavaDoc")
public class DyeMat extends ItemMaterialData implements ColorableMat
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final int USED_DATA_VALUES = 16;

    public static final DyeMat            DYE_INK_SAC      = new DyeMat();
    public static final DyeMat            DYE_RED          = new DyeMat("RED", DyeColor.RED);
    public static final DyeMat            DYE_GREEN        = new DyeMat("GREEN", DyeColor.GREEN);
    public static final DyeMat            DYE_COCOA_BEANS  = new DyeMat("COCOA_BEANS", DyeColor.BROWN);
    public static final DyeLapisLazuliMat DYE_LAPIS_LAZULI = new DyeLapisLazuliMat();
    public static final DyeMat            DYE_PURPLE       = new DyeMat("PURPLE", DyeColor.PURPLE);
    public static final DyeMat            DYE_CYAN         = new DyeMat("CYAN", DyeColor.CYAN);
    public static final DyeMat            DYE_LIGHT_GRAY   = new DyeMat("LIGHT_GRAY", DyeColor.LIGHT_GRAY);
    public static final DyeMat            DYE_GRAY         = new DyeMat("GRAY", DyeColor.GRAY);
    public static final DyeMat            DYE_PINK         = new DyeMat("PINK", DyeColor.PINK);
    public static final DyeMat            DYE_LIME         = new DyeMat("LIME", DyeColor.LIME);
    public static final DyeMat            DYE_YELLOW       = new DyeMat("YELLOW", DyeColor.YELLOW);
    public static final DyeMat            DYE_LIGHT_BLUE   = new DyeMat("LIGHT_BLUE", DyeColor.LIGHT_BLUE);
    public static final DyeMat            DYE_MAGENTA      = new DyeMat("MAGENTA", DyeColor.MAGENTA);
    public static final DyeMat            DYE_ORANGE       = new DyeMat("ORANGE", DyeColor.ORANGE);
    public static final DyeMat            DYE_BONE_MEAL    = new DyeMat("BONE_MEAL", DyeColor.WHITE);

    private static final Map<String, DyeMat>     byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TShortObjectMap<DyeMat> byID   = new TShortObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR, Short.MIN_VALUE);

    protected final DyeColor color;

    @SuppressWarnings("MagicNumber")
    protected DyeMat()
    {
        super("DYE", 351, "minecraft:dye", "INK_SAC", (short) 0x00);
        this.color = DyeColor.BLACK;
    }

    protected DyeMat(final String typeName, final DyeColor color)
    {
        super(DYE_INK_SAC.name(), DYE_INK_SAC.getId(), DYE_INK_SAC.getMinecraftId(), typeName, (short) color.getItemFlag());
        this.color = color;
    }

    protected DyeMat(final String enumName, final int id, final String minecraftId, final String typeName, final short type, final DyeColor color)
    {
        super(enumName, id, minecraftId, typeName, type);
        this.color = color;
    }

    protected DyeMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final short type, final DyeColor color)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type);
        this.color = color;
    }

    @Override
    public DyeMat getType(final int type)
    {
        return getByID(type);
    }

    @Override
    public DyeMat getType(final String type)
    {
        return getByEnumName(type);
    }

    @Override
    public DyeColor getColor()
    {
        return this.color;
    }

    @Override
    public DyeMat getColor(final DyeColor color)
    {
        return getByID(color.getItemFlag());
    }

    /**
     * Returns one of Dye sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of Dye or null
     */
    public static DyeMat getByID(final int id)
    {
        return byID.get((short) id);
    }

    /**
     * Returns one of Dye sub-type based on name (selected by diorite team), may return null
     * If item contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of Dye or null
     */
    public static DyeMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final DyeMat element)
    {
        byID.put(element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    @Override
    public DyeMat[] types()
    {
        return DyeMat.dyeTypes();
    }

    /**
     * @return array that contains all sub-types of this item.
     */
    public static DyeMat[] dyeTypes()
    {
        return byID.values(new DyeMat[byID.size()]);
    }

    static
    {
        DyeMat.register(DYE_INK_SAC);
        DyeMat.register(DYE_RED);
        DyeMat.register(DYE_GREEN);
        DyeMat.register(DYE_COCOA_BEANS);
        DyeMat.register(DYE_LAPIS_LAZULI);
        DyeMat.register(DYE_PURPLE);
        DyeMat.register(DYE_CYAN);
        DyeMat.register(DYE_LIGHT_GRAY);
        DyeMat.register(DYE_GRAY);
        DyeMat.register(DYE_PINK);
        DyeMat.register(DYE_LIME);
        DyeMat.register(DYE_YELLOW);
        DyeMat.register(DYE_LIGHT_BLUE);
        DyeMat.register(DYE_MAGENTA);
        DyeMat.register(DYE_ORANGE);
        DyeMat.register(DYE_BONE_MEAL);
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("color", this.color).toString();
    }

    protected static class DyeLapisLazuliMat extends DyeMat implements OreItemMat
    {
        protected final OreMat      blockOreType;
        protected final OreBlockMat blockType;

        protected DyeLapisLazuliMat()
        {
            super("LAPIS_LAZULI", DyeColor.BLUE);
            this.blockOreType = LapisOreMat.LAPIS_ORE;
            this.blockType = LapisBlockMat.LAPIS_BLOCK;
        }

        protected DyeLapisLazuliMat(final String enumName, final int id, final String minecraftId, final String typeName, final short type, final DyeColor dyeColor, final OreMat oreType, final OreBlockMat blockType)
        {
            super(enumName, id, minecraftId, typeName, type, dyeColor);
            this.blockOreType = oreType;
            this.blockType = blockType;
        }

        protected DyeLapisLazuliMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final short type, final DyeColor dyeColor, final OreMat oreType, final OreBlockMat blockType)
        {
            super(enumName, id, minecraftId, maxStack, typeName, type, dyeColor);
            this.blockOreType = oreType;
            this.blockType = blockType;
        }

        /**
         * Returns related {@link OreMat} for this item, may return null.
         *
         * @return related {@link OreMat} for this item.
         */
        @Override
        public OreMat getBlockOreType()
        {
            return this.blockOreType;
        }

        /**
         * Returns related {@link OreBlockMat} for this item, may return null.
         *
         * @return related {@link OreBlockMat} for this item.
         */
        @Override
        public OreBlockMat getBlockType()
        {
            return this.blockType;
        }

        @Override
        public String toString()
        {
            return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("blockOreType", this.blockOreType).append("blockType", this.blockType).toString();
        }
    }
}

