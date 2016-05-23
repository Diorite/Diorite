/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2016. Diorite (by Bartłomiej Mazur (aka GotoFinal))
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

package org.diorite;

import org.diorite.utils.SimpleEnum.ASimpleEnum;
import org.diorite.utils.collections.maps.CaseInsensitiveMap;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;

public class Art extends ASimpleEnum<Art>
{
    static
    {
        init(Art.class, 26);
    }

    public static final Art KEBAB           = new Art("KEBAB", "Kebab", 16, 16);
    public static final Art AZTEC           = new Art("AZTEC", "Aztec", 16, 16);
    public static final Art ALBAN           = new Art("ALBAN", "Alban", 16, 16);
    public static final Art AZTEC_2         = new Art("AZTEC_2", "Aztec2", 16, 16);
    public static final Art BOMB            = new Art("BOMB", "Bomb", 16, 16);
    public static final Art PLANT           = new Art("PLANT", "Plant", 16, 16);
    public static final Art WASTELAND       = new Art("WASTELAND", "Wasteland", 16, 16);
    public static final Art POOL            = new Art("POOL", "Pool", 32, 16);
    public static final Art COURBET         = new Art("COURBET", "Courbet", 32, 16);
    public static final Art SEA             = new Art("SEA", "Sea", 32, 16);
    public static final Art SUNSET          = new Art("SUNSET", "Sunset", 32, 16);
    public static final Art CREEBET         = new Art("CREEBET", "Creebet", 32, 16);
    public static final Art WANDERER        = new Art("WANDERER", "Wanderer", 16, 32);
    public static final Art GRAHAM          = new Art("GRAHAM", "Graham", 16, 32);
    public static final Art MATCH           = new Art("MATCH", "Match", 32, 32);
    public static final Art BUST            = new Art("BUST", "Bust", 32, 16);
    public static final Art STAGE           = new Art("STAGE", "Stage", 32, 32);
    public static final Art VOID            = new Art("VOID", "Void", 32, 32);
    public static final Art SKULL_AND_ROSES = new Art("SKULL_AND_ROSES", "SkullAndRoses", 32, 32);
    public static final Art WITHER          = new Art("WITHER", "Wither", 32, 32);
    public static final Art FIGHTERS        = new Art("FIGHTERS", "Fighters", 64, 32);
    public static final Art POINTER         = new Art("POINTER", "Pointer", 64, 64);
    public static final Art PIGSCENE        = new Art("PIGSCENE", "Pigscene", 64, 64);
    public static final Art BURNING_SKULL   = new Art("BURNING_SKULL", "BurningSkull", 64, 64);
    public static final Art SKELETON        = new Art("SKELETON", "Skeleton", 64, 48);
    public static final Art DONKEY_KONG     = new Art("DONKEY_KONG", "DonkeyKong", 64, 48);

    private static final CaseInsensitiveMap<Art> byArtName = new CaseInsensitiveMap<>(26, SMALL_LOAD_FACTOR);
    private static       int                     maxArtNameLength;

    private final String artName;
    private final int    width;
    private final int    height;

    public Art(final String enumName, final String artName, final int width, final int height)
    {
        super(enumName);
        this.artName = artName;
        this.width = width;
        this.height = height;
    }

    public String getArtName()
    {
        return artName;
    }

    public int getWidth()
    {
        return width;
    }

    public int getHeight()
    {
        return height;
    }

    /**
     * Register new {@link Art} entry in this enum.
     *
     * @param element new element to register.
     */
    public static void register(final Art element)
    {
        ASimpleEnum.register(Art.class, element);
        byArtName.put(element.getArtName(), element);
        final int artNameLength = element.getArtName().length();
        if (maxArtNameLength < artNameLength)
        {
            maxArtNameLength = artNameLength;
        }
    }

    /**
     * Get one of {@link Art} entry by its ordinal id.
     *
     * @param ordinal ordinal id of entry.
     *
     * @return one of entry or null.
     */
    public static Art getByEnumOrdinal(final int ordinal)
    {
        return getByEnumOrdinal(Art.class, ordinal);
    }

    /**
     * Get one of {@link Art} entry by its name.
     *
     * @param name name of entry.
     *
     * @return one of entry or null.
     */
    public static Art getByEnumName(final String name)
    {
        return getByEnumName(Art.class, name);
    }

    /**
     * Get one of {@link Art} entry by its minecraft id.
     *
     * @param artName minecraft name of entry.
     *
     * @return one of entry or null.
     */
    public static Art getByArtName(final String artName)
    {
        return byArtName.get(artName);
    }

    /**
     * @return all values in array.
     */
    public static Art[] values()
    {
        final Int2ObjectMap<Art> map = getByEnumOrdinal(Art.class);
        return map.values().toArray(new Art[map.size()]);
    }

    /**
     * @return length of the longest art name.
     */
    public static int getMaxArtNameLength()
    {
        return maxArtNameLength;
    }

    static
    {
        register(KEBAB);
        register(AZTEC);
        register(ALBAN);
        register(AZTEC_2);
        register(BOMB);
        register(PLANT);
        register(WASTELAND);
        register(POOL);
        register(COURBET);
        register(SEA);
        register(SUNSET);
        register(CREEBET);
        register(WANDERER);
        register(GRAHAM);
        register(MATCH);
        register(BUST);
        register(STAGE);
        register(VOID);
        register(SKULL_AND_ROSES);
        register(WITHER);
        register(FIGHTERS);
        register(POINTER);
        register(PIGSCENE);
        register(BURNING_SKULL);
        register(SKELETON);
        register(DONKEY_KONG);
    }
}
