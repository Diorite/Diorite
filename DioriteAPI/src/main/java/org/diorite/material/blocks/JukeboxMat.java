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
import org.diorite.material.FuelMat;
import org.diorite.material.Material;
import org.diorite.utils.collections.maps.CaseInsensitiveMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing 'Jukebox' block material in minecraft. <br>
 * ID of block: 84 <br>
 * String ID of block: minecraft:jukebox <br>
 * Hardness: 2 <br>
 * Blast Resistance 30 <br>
 * <br>
 * Subtypes: <br>
 * <ol>
 * <li>
 * WITH_DISC:
 * Type name: 'With Disc' <br>
 * SubID: 1 <br>
 * Hardness: 2 <br>
 * Blast Resistance 30 <br>
 * </li>
 * <li>
 * EMPTY:
 * Type name: 'Empty' <br>
 * SubID: 0 <br>
 * Hardness: 2 <br>
 * Blast Resistance 30 <br>
 * </li>
 * </ol>
 */
@SuppressWarnings("JavaDoc")
public class JukeboxMat extends BlockMaterialData implements FuelMat
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final int USED_DATA_VALUES = 2;

    public static final JukeboxMat JUKEBOX           = new JukeboxMat();
    public static final JukeboxMat JUKEBOX_WITH_DISC = new JukeboxMat("WITH_DISC", 0x1, true);

    private static final Map<String, JukeboxMat>    byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<JukeboxMat> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR, Byte.MIN_VALUE);

    protected final boolean withDisc;

    @SuppressWarnings("MagicNumber")
    protected JukeboxMat()
    {
        super("JUKEBOX", 84, "minecraft:jukebox", "EMPTY", (byte) 0x00, 2, 30);
        this.withDisc = false;
    }

    protected JukeboxMat(final String enumName, final int type, final boolean withDisc)
    {
        super(JUKEBOX.name(), JUKEBOX.ordinal(), JUKEBOX.getMinecraftId(), enumName, (byte) type, JUKEBOX.getHardness(), JUKEBOX.getBlastResistance());
        this.withDisc = withDisc;
    }

    protected JukeboxMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type, final boolean withDisc, final float hardness, final float blastResistance)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, hardness, blastResistance);
        this.withDisc = withDisc;
    }

    @Override
    public Material ensureValidInventoryItem()
    {
        return Material.JUKEBOX;
    }

    public boolean isWithDisc()
    {
        return this.withDisc;
    }

    public JukeboxMat getWithDisc(final boolean withDisc)
    {
        return getByID(withDisc ? 1 : 0);
    }

    @Override
    public JukeboxMat getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public JukeboxMat getType(final int id)
    {
        return getByID(id);
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("withDisc", this.withDisc).toString();
    }

    @SuppressWarnings("MagicNumber")
    @Override
    public int getFuelPower()
    {
        return 1500;
    }

    /**
     * Returns one of Jukebox sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of Jukebox or null
     */
    public static JukeboxMat getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of Jukebox sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of Jukebox or null
     */
    public static JukeboxMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Returns one of Jukebox sub-type with or without disc.
     * It will never return null.
     *
     * @param withDisc if it should contains disc.
     *
     * @return sub-type of Jukebox
     */
    public static JukeboxMat getJukebox(final boolean withDisc)
    {
        return getByID(withDisc ? 1 : 0);
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final JukeboxMat element)
    {
        byID.put((byte) element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    @Override
    public JukeboxMat[] types()
    {
        return JukeboxMat.jukeboxTypes();
    }

    /**
     * @return array that contains all sub-types of this block.
     */
    public static JukeboxMat[] jukeboxTypes()
    {
        return byID.values(new JukeboxMat[byID.size()]);
    }

    static
    {
        JukeboxMat.register(JUKEBOX);
        JukeboxMat.register(JUKEBOX_WITH_DISC);
    }
}
