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

import org.diorite.DyeColor;
import org.diorite.inventory.item.meta.BannerMeta;
import org.diorite.material.FuelMat;
import org.diorite.material.ItemMaterialData;
import org.diorite.material.PlaceableMat;
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
 * WHITE:
 * Type name: 'White' <br>
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
 * BLUE:
 * Type name: 'Blue' <br>
 * SubID: 4 <br>
 * Max item stack size: 64
 * </li>
 * <li>
 * BROWN:
 * Type name: 'Brown' <br>
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
 * BLACK:
 * Type name: 'Black' <br>
 * SubID: 0 <br>
 * Max item stack size: 64
 * </li>
 * </ol>
 */
@SuppressWarnings("JavaDoc")
public class BannerMat extends ItemMaterialData implements PlaceableMat, FuelMat
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final int USED_DATA_VALUES = 16;

    public static final BannerMat BANNER_BLACK      = new BannerMat();
    public static final BannerMat BANNER_RED        = new BannerMat(DyeColor.RED);
    public static final BannerMat BANNER_GREEN      = new BannerMat(DyeColor.GREEN);
    public static final BannerMat BANNER_BROWN      = new BannerMat(DyeColor.BROWN);
    public static final BannerMat BANNER_BLUE       = new BannerMat(DyeColor.BLUE);
    public static final BannerMat BANNER_PURPLE     = new BannerMat(DyeColor.PURPLE);
    public static final BannerMat BANNER_CYAN       = new BannerMat(DyeColor.CYAN);
    public static final BannerMat BANNER_LIGHT_GRAY = new BannerMat(DyeColor.LIGHT_GRAY);
    public static final BannerMat BANNER_GRAY       = new BannerMat(DyeColor.GRAY);
    public static final BannerMat BANNER_PINK       = new BannerMat(DyeColor.PINK);
    public static final BannerMat BANNER_LIME       = new BannerMat(DyeColor.LIME);
    public static final BannerMat BANNER_YELLOW     = new BannerMat(DyeColor.YELLOW);
    public static final BannerMat BANNER_LIGHT_BLUE = new BannerMat(DyeColor.LIGHT_BLUE);
    public static final BannerMat BANNER_MAGENTA    = new BannerMat(DyeColor.MAGENTA);
    public static final BannerMat BANNER_ORANGE     = new BannerMat(DyeColor.ORANGE);
    public static final BannerMat BANNER_WHITE      = new BannerMat(DyeColor.WHITE);

    private static final Map<String, BannerMat>     byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TShortObjectMap<BannerMat> byID   = new TShortObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR, Short.MIN_VALUE);

    protected final DyeColor color;

    @SuppressWarnings("MagicNumber")
    public BannerMat()
    {
        super("BANNER", 425, "minecraft:banner", 16, "BLACK", (short) 0x00);
        this.color = DyeColor.BLACK;
    }

    public BannerMat(final DyeColor color)
    {
        super(BANNER_BLACK.name(), BANNER_BLACK.ordinal(), BANNER_BLACK.getMinecraftId(), color.name(), (short) color.getItemFlag());
        this.color = color;
    }

    public BannerMat(final String enumName, final int id, final String minecraftId, final String typeName, final short type, final DyeColor color)
    {
        super(enumName, id, minecraftId, BANNER_BLACK.getMaxStack(), typeName, type);
        this.color = color;
    }

    public BannerMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final short type, final DyeColor color)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type);
        this.color = color;
    }

    {
        this.metaType = BannerMeta.class;
    }

    @Override
    public BannerMat getType(final int type)
    {
        return getByID(type);
    }

    @Override
    public BannerMat getType(final String type)
    {
        return getByEnumName(type);
    }

    @SuppressWarnings("MagicNumber")
    @Override
    public int getFuelPower()
    {
        return 1500;
    }

    /**
     * Returns one of Banner sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of Banner or null
     */
    public static BannerMat getByID(final int id)
    {
        return byID.get((short) id);
    }

    /**
     * Returns one of Banner sub-type based on name (selected by diorite team), may return null
     * If item contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of Banner or null
     */
    public static BannerMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final BannerMat element)
    {
        allItems.incrementAndGet();
        byID.put((short) element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    @Override
    public BannerMat[] types()
    {
        return BannerMat.bannerTypes();
    }

    /**
     * @return array that contains all sub-types of this item.
     */
    public static BannerMat[] bannerTypes()
    {
        return byID.values(new BannerMat[byID.size()]);
    }

    static
    {
        BannerMat.register(BANNER_BLACK);
        BannerMat.register(BANNER_RED);
        BannerMat.register(BANNER_GREEN);
        BannerMat.register(BANNER_BROWN);
        BannerMat.register(BANNER_BLUE);
        BannerMat.register(BANNER_PURPLE);
        BannerMat.register(BANNER_CYAN);
        BannerMat.register(BANNER_LIGHT_GRAY);
        BannerMat.register(BANNER_GRAY);
        BannerMat.register(BANNER_PINK);
        BannerMat.register(BANNER_LIME);
        BannerMat.register(BANNER_YELLOW);
        BannerMat.register(BANNER_LIGHT_BLUE);
        BannerMat.register(BANNER_MAGENTA);
        BannerMat.register(BANNER_ORANGE);
        BannerMat.register(BANNER_WHITE);
    }
}

