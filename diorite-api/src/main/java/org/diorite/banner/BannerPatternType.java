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

package org.diorite.banner;

import java.util.Map;

import org.diorite.utils.SimpleEnum;
import org.diorite.utils.SimpleEnum.ASimpleEnum;
import org.diorite.utils.collections.maps.CaseInsensitiveMap;

import gnu.trove.map.TIntObjectMap;

@SuppressWarnings("ClassHasNoToStringMethod")
public class BannerPatternType extends ASimpleEnum<BannerPatternType>
{
    static
    {
        //noinspection MagicNumber
        init(BannerPatternType.class, 39);
    }

    public static final BannerPatternType BASE                   = new BannerPatternType("BASE", "b");
    public static final BannerPatternType SQUARE_BOTTOM_LEFT     = new BannerPatternType("SQUARE_BOTTOM_LEFT", "bl");
    public static final BannerPatternType SQUARE_BOTTOM_RIGHT    = new BannerPatternType("SQUARE_BOTTOM_RIGHT", "br");
    public static final BannerPatternType SQUARE_TOP_LEFT        = new BannerPatternType("SQUARE_TOP_LEFT", "tl");
    public static final BannerPatternType SQUARE_TOP_RIGHT       = new BannerPatternType("SQUARE_TOP_RIGHT", "tr");
    public static final BannerPatternType STRIPE_BOTTOM          = new BannerPatternType("STRIPE_BOTTOM", "bs");
    public static final BannerPatternType STRIPE_TOP             = new BannerPatternType("STRIPE_TOP", "ts");
    public static final BannerPatternType STRIPE_LEFT            = new BannerPatternType("STRIPE_LEFT", "ls");
    public static final BannerPatternType STRIPE_RIGHT           = new BannerPatternType("STRIPE_RIGHT", "rs");
    public static final BannerPatternType STRIPE_CENTER          = new BannerPatternType("STRIPE_CENTER", "cs");
    public static final BannerPatternType STRIPE_MIDDLE          = new BannerPatternType("STRIPE_MIDDLE", "ms");
    public static final BannerPatternType STRIPE_DOWNRIGHT       = new BannerPatternType("STRIPE_DOWNRIGHT", "drs");
    public static final BannerPatternType STRIPE_DOWNLEFT        = new BannerPatternType("STRIPE_DOWNLEFT", "dls");
    public static final BannerPatternType STRIPE_SMALL           = new BannerPatternType("STRIPE_SMALL", "ss");
    public static final BannerPatternType CROSS                  = new BannerPatternType("CROSS", "cr");
    public static final BannerPatternType STRAIGHT_CROSS         = new BannerPatternType("STRAIGHT_CROSS", "sc");
    public static final BannerPatternType TRIANGLE_BOTTOM        = new BannerPatternType("TRIANGLE_BOTTOM", "bt");
    public static final BannerPatternType TRIANGLE_TOP           = new BannerPatternType("TRIANGLE_TOP", "tt");
    public static final BannerPatternType TRIANGLES_BOTTOM       = new BannerPatternType("TRIANGLES_BOTTOM", "bts");
    public static final BannerPatternType TRIANGLES_TOP          = new BannerPatternType("TRIANGLES_TOP", "tts");
    public static final BannerPatternType DIAGONAL_LEFT          = new BannerPatternType("DIAGONAL_LEFT", "ld");
    public static final BannerPatternType DIAGONAL_RIGHT         = new BannerPatternType("DIAGONAL_RIGHT", "rd");
    public static final BannerPatternType DIAGONAL_LEFT_MIRROR   = new BannerPatternType("DIAGONAL_LEFT_MIRROR", "lud");
    public static final BannerPatternType DIAGONAL_RIGHT_MIRROR  = new BannerPatternType("DIAGONAL_RIGHT_MIRROR", "rud");
    public static final BannerPatternType CIRCLE_MIDDLE          = new BannerPatternType("CIRCLE_MIDDLE", "mc");
    public static final BannerPatternType RHOMBUS_MIDDLE         = new BannerPatternType("RHOMBUS_MIDDLE", "mr");
    public static final BannerPatternType HALF_VERTICAL          = new BannerPatternType("HALF_VERTICAL", "vh");
    public static final BannerPatternType HALF_HORIZONTAL        = new BannerPatternType("HALF_HORIZONTAL", "hh");
    public static final BannerPatternType HALF_VERTICAL_MIRROR   = new BannerPatternType("HALF_VERTICAL_MIRROR", "vhr");
    public static final BannerPatternType HALF_HORIZONTAL_MIRROR = new BannerPatternType("HALF_HORIZONTAL_MIRROR", "hhb");
    public static final BannerPatternType BORDER                 = new BannerPatternType("BORDER", "bo");
    public static final BannerPatternType CURLY_BORDER           = new BannerPatternType("CURLY_BORDER", "cbo");
    public static final BannerPatternType CREEPER                = new BannerPatternType("CREEPER", "cre");
    public static final BannerPatternType GRADIENT               = new BannerPatternType("GRADIENT", "gra");
    public static final BannerPatternType GRADIENT_UP            = new BannerPatternType("GRADIENT_UP", "gru");
    public static final BannerPatternType BRICKS                 = new BannerPatternType("BRICKS", "bri");
    public static final BannerPatternType SKULL                  = new BannerPatternType("SKULL", "sku");
    public static final BannerPatternType FLOWER                 = new BannerPatternType("FLOWER", "flo");
    public static final BannerPatternType MOJANG                 = new BannerPatternType("MOJANG", "moj");

    private static final Map<String, BannerPatternType> byIdentifier = new CaseInsensitiveMap<>(39, SMALL_LOAD_FACTOR);

    /**
     * Identifier of this banner pattern.
     */
    protected final String identifier;

    /**
     * Construct new BannerPatternType with given identifier.
     *
     * @param enumName   enum name of type.
     * @param enumId     enum id of type.
     * @param identifier identifier of this banner pattern.
     */
    public BannerPatternType(final String enumName, final int enumId, final String identifier)
    {
        super(enumName, enumId);
        this.identifier = identifier;
    }

    /**
     * Construct new BannerPatternType with given identifier.
     *
     * @param enumName   enum name of type.
     * @param identifier identifier of this banner pattern.
     */
    public BannerPatternType(final String enumName, final String identifier)
    {
        super(enumName);
        this.identifier = identifier;
    }

    /**
     * Returns identifier of this banner pattern.
     *
     * @return identifier of this banner pattern.
     */
    public String getIdentifier()
    {
        return this.identifier;
    }

    /**
     * Register new {@link BannerPatternType} entry in this enum.
     *
     * @param element new element to register.
     */
    public static void register(final BannerPatternType element)
    {
        ASimpleEnum.register(BannerPatternType.class, element);
        byIdentifier.put(element.identifier, element);
    }

    /**
     * Get one of {@link BannerPatternType} entry by its ordinal id.
     *
     * @param ordinal ordinal id of entry.
     *
     * @return one of entry or null.
     */
    public static BannerPatternType getByEnumOrdinal(final int ordinal)
    {
        return getByEnumOrdinal(BannerPatternType.class, ordinal);
    }

    /**
     * Get one of BannerPatternType entry by its name.
     *
     * @param name name of entry.
     *
     * @return one of entry or null.
     */
    public static BannerPatternType getByEnumName(final String name)
    {
        return getByEnumName(BannerPatternType.class, name);
    }

    /**
     * Get one of BannerPatternType entry by its identifier.
     *
     * @param identifier identifier of entry.
     *
     * @return one of entry or null.
     */
    public static BannerPatternType getByIdentifier(final String identifier)
    {
        return byIdentifier.get(identifier);
    }

    /**
     * @return all values in array.
     */
    public static BannerPatternType[] values()
    {
        final TIntObjectMap<SimpleEnum<?>> map = getByEnumOrdinal(BannerPatternType.class);
        return (BannerPatternType[]) map.values(new BannerPatternType[map.size()]);
    }

    static
    {
        BannerPatternType.register(BASE);
        BannerPatternType.register(SQUARE_BOTTOM_LEFT);
        BannerPatternType.register(SQUARE_BOTTOM_RIGHT);
        BannerPatternType.register(SQUARE_TOP_LEFT);
        BannerPatternType.register(SQUARE_TOP_RIGHT);
        BannerPatternType.register(STRIPE_BOTTOM);
        BannerPatternType.register(STRIPE_TOP);
        BannerPatternType.register(STRIPE_LEFT);
        BannerPatternType.register(STRIPE_RIGHT);
        BannerPatternType.register(STRIPE_CENTER);
        BannerPatternType.register(STRIPE_MIDDLE);
        BannerPatternType.register(STRIPE_DOWNRIGHT);
        BannerPatternType.register(STRIPE_DOWNLEFT);
        BannerPatternType.register(STRIPE_SMALL);
        BannerPatternType.register(CROSS);
        BannerPatternType.register(STRAIGHT_CROSS);
        BannerPatternType.register(TRIANGLE_BOTTOM);
        BannerPatternType.register(TRIANGLE_TOP);
        BannerPatternType.register(TRIANGLES_BOTTOM);
        BannerPatternType.register(TRIANGLES_TOP);
        BannerPatternType.register(DIAGONAL_LEFT);
        BannerPatternType.register(DIAGONAL_RIGHT);
        BannerPatternType.register(DIAGONAL_LEFT_MIRROR);
        BannerPatternType.register(DIAGONAL_RIGHT_MIRROR);
        BannerPatternType.register(CIRCLE_MIDDLE);
        BannerPatternType.register(RHOMBUS_MIDDLE);
        BannerPatternType.register(HALF_VERTICAL);
        BannerPatternType.register(HALF_HORIZONTAL);
        BannerPatternType.register(HALF_VERTICAL_MIRROR);
        BannerPatternType.register(HALF_HORIZONTAL_MIRROR);
        BannerPatternType.register(BORDER);
        BannerPatternType.register(CURLY_BORDER);
        BannerPatternType.register(CREEPER);
        BannerPatternType.register(GRADIENT);
        BannerPatternType.register(GRADIENT_UP);
        BannerPatternType.register(BRICKS);
        BannerPatternType.register(SKULL);
        BannerPatternType.register(FLOWER);
        BannerPatternType.register(MOJANG);
    }
}