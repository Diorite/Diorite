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

import org.diorite.material.ArmorMaterial;
import org.diorite.material.ArmorType;
import org.diorite.utils.collections.maps.CaseInsensitiveMap;
import org.diorite.utils.lazy.LazyValue;
import org.diorite.utils.math.DioriteMathUtils;

import gnu.trove.map.TShortObjectMap;
import gnu.trove.map.hash.TShortObjectHashMap;

/**
 * Class representing 'Diamond Helmet' item material in minecraft. <br>
 * ID of material: 310 <br>
 * String ID of material: minecraft:diamond_helmet <br>
 * Max item stack size: 1
 */
@SuppressWarnings({"JavaDoc", "ClassHasNoToStringMethod"})
public class DiamondHelmetMat extends HelmetMat
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final int USED_DATA_VALUES = 1;

    public static final DiamondHelmetMat DIAMOND_HELMET = new DiamondHelmetMat();

    private static final Map<String, DiamondHelmetMat>     byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TShortObjectMap<DiamondHelmetMat> byID   = new TShortObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR, Short.MIN_VALUE);

    protected final LazyValue<DiamondHelmetMat> next = new LazyValue<>(() -> (this.haveValidDurability()) ? getByDurability(this.getDurability() + 1) : null);
    protected final LazyValue<DiamondHelmetMat> prev = new LazyValue<>(() -> (this.haveValidDurability()) ? getByDurability(this.getDurability() - 1) : null);

    @SuppressWarnings("MagicNumber")
    protected DiamondHelmetMat()
    {
        super("DIAMOND_HELMET", 310, "minecraft:diamond_helmet", "NEW", (short) 0, ArmorMaterial.DIAMOND);
    }

    protected DiamondHelmetMat(final int durability)
    {
        super(DIAMOND_HELMET.name(), DIAMOND_HELMET.getId(), DIAMOND_HELMET.getMinecraftId(), Integer.toString(durability), (short) durability, ArmorMaterial.DIAMOND);
    }

    protected DiamondHelmetMat(final String enumName, final int id, final String minecraftId, final String typeName, final short type, final ArmorMaterial armorMaterial, final ArmorType armorType)
    {
        super(enumName, id, minecraftId, typeName, type, armorMaterial, armorType);
    }

    protected DiamondHelmetMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final short type, final ArmorMaterial armorMaterial, final ArmorType armorType)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, armorMaterial, armorType);
    }

    @Override
    public DiamondHelmetMat[] types()
    {
        return new DiamondHelmetMat[]{DIAMOND_HELMET};
    }

    @Override
    public DiamondHelmetMat getType(final String type)
    {
        return getByEnumName(type);
    }

    @Override
    public DiamondHelmetMat getType(final int type)
    {
        return getByID(type);
    }

    @Override
    public DiamondHelmetMat increaseDurability()
    {
        return this.next.get();
    }

    @Override
    public DiamondHelmetMat decreaseDurability()
    {
        return this.prev.get();
    }

    @Override
    public DiamondHelmetMat setDurability(final int durability)
    {
        return this.getType(durability);
    }

    /**
     * Returns one of DiamondHelmet sub-type based on sub-id.
     *
     * @param id sub-type id
     *
     * @return sub-type of DiamondHelmet.
     */
    public static DiamondHelmetMat getByID(final int id)
    {
        DiamondHelmetMat mat = byID.get((short) id);
        if (mat == null)
        {
            mat = new DiamondHelmetMat(id);
            if ((id > 0) && (id < DIAMOND_HELMET.getBaseDurability()))
            {
                DiamondHelmetMat.register(mat);
            }
        }
        return mat;
    }

    /**
     * Returns one of DiamondHelmet sub-type based on durability.
     *
     * @param id durability of type.
     *
     * @return sub-type of DiamondHelmet.
     */
    public static DiamondHelmetMat getByDurability(final int id)
    {
        return getByID(id);
    }

    /**
     * Returns one of DiamondHelmet-type based on name (selected by diorite team).
     * If item contains only one type, sub-name of it will be this same as name of material.<br>
     * Returns null if name can't be parsed to int and it isn't "NEW" one.
     *
     * @param name name of sub-type
     *
     * @return sub-type of DiamondHelmet.
     */
    public static DiamondHelmetMat getByEnumName(final String name)
    {
        final DiamondHelmetMat mat = byName.get(name);
        if (mat == null)
        {
            final Integer idType = DioriteMathUtils.asInt(name);
            if (idType == null)
            {
                return null;
            }
            return getByID(idType);
        }
        return mat;
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final DiamondHelmetMat element)
    {
        byID.put(element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    /**
     * @return array that contains all sub-types of this item.
     */
    public static DiamondHelmetMat[] diamondHelmetTypes()
    {
        return new DiamondHelmetMat[]{DIAMOND_HELMET};
    }

    static
    {
        DiamondHelmetMat.register(DIAMOND_HELMET);
    }
}
