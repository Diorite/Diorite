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

import org.diorite.inventory.item.meta.LeatherArmorMeta;
import org.diorite.material.ArmorMaterial;
import org.diorite.material.ArmorType;
import org.diorite.utils.collections.maps.CaseInsensitiveMap;
import org.diorite.utils.lazy.LazyValue;
import org.diorite.utils.math.DioriteMathUtils;

import gnu.trove.map.TShortObjectMap;
import gnu.trove.map.hash.TShortObjectHashMap;

/**
 * Class representing 'Leather Helmet' item material in minecraft. <br>
 * ID of material: 298 <br>
 * String ID of material: minecraft:leather_helmet <br>
 * Max item stack size: 1
 */
@SuppressWarnings({"JavaDoc", "ClassHasNoToStringMethod"})
public class LeatherHelmetMat extends HelmetMat
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final int USED_DATA_VALUES = 1;

    public static final LeatherHelmetMat LEATHER_HELMET = new LeatherHelmetMat();

    private static final Map<String, LeatherHelmetMat>     byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TShortObjectMap<LeatherHelmetMat> byID   = new TShortObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR, Short.MIN_VALUE);

    protected final LazyValue<LeatherHelmetMat> next = new LazyValue<>(() -> (this.haveValidDurability()) ? getByDurability(this.getDurability() + 1) : null);
    protected final LazyValue<LeatherHelmetMat> prev = new LazyValue<>(() -> (this.haveValidDurability()) ? getByDurability(this.getDurability() - 1) : null);

    @SuppressWarnings("MagicNumber")
    public LeatherHelmetMat()
    {
        super("LEATHER_HELMET", 298, "minecraft:leather_helmet", "NEW", (short) 0, ArmorMaterial.LEATHER);
    }

    public LeatherHelmetMat(final int durability)
    {
        super(LEATHER_HELMET.name(), LEATHER_HELMET.getId(), LEATHER_HELMET.getMinecraftId(), Integer.toString(durability), (short) durability, ArmorMaterial.LEATHER);
    }

    public LeatherHelmetMat(final String enumName, final int id, final String minecraftId, final String typeName, final short type, final ArmorMaterial armorMaterial, final ArmorType armorType)
    {
        super(enumName, id, minecraftId, typeName, type, armorMaterial, armorType);
    }

    public LeatherHelmetMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final short type, final ArmorMaterial armorMaterial, final ArmorType armorType)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, armorMaterial, armorType);
    }

    {
        this.metaType = LeatherArmorMeta.class;
    }

    @Override
    public LeatherHelmetMat[] types()
    {
        return new LeatherHelmetMat[]{LEATHER_HELMET};
    }

    @Override
    public LeatherHelmetMat getType(final String type)
    {
        return getByEnumName(type);
    }

    @Override
    public LeatherHelmetMat getType(final int type)
    {
        return getByID(type);
    }

    @Override
    public LeatherHelmetMat increaseDurability()
    {
        return this.next.get();
    }

    @Override
    public LeatherHelmetMat decreaseDurability()
    {
        return this.prev.get();
    }

    @Override
    public LeatherHelmetMat setDurability(final int durability)
    {
        return this.getType(durability);
    }

    /**
     * Returns one of LeatherHelmet sub-type based on sub-id.
     *
     * @param id sub-type id
     *
     * @return sub-type of LeatherHelmet.
     */
    public static LeatherHelmetMat getByID(final int id)
    {
        LeatherHelmetMat mat = byID.get((short) id);
        if (mat == null)
        {
            mat = new LeatherHelmetMat(id);
            if ((id > 0) && (id < LEATHER_HELMET.getBaseDurability()))
            {
                LeatherHelmetMat.register(mat);
            }
        }
        return mat;
    }

    /**
     * Returns one of LeatherHelmet sub-type based on durability.
     *
     * @param id durability of type.
     *
     * @return sub-type of LeatherHelmet.
     */
    public static LeatherHelmetMat getByDurability(final int id)
    {
        return getByID(id);
    }

    /**
     * Returns one of LeatherHelmet-type based on name (selected by diorite team).
     * If item contains only one type, sub-name of it will be this same as name of material.<br>
     * Returns null if name can't be parsed to int and it isn't "NEW" one.
     *
     * @param name name of sub-type
     *
     * @return sub-type of LeatherHelmet.
     */
    public static LeatherHelmetMat getByEnumName(final String name)
    {
        final LeatherHelmetMat mat = byName.get(name);
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
    public static void register(final LeatherHelmetMat element)
    {
        allItems.incrementAndGet();
        byID.put((short) element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    /**
     * @return array that contains all sub-types of this item.
     */
    public static LeatherHelmetMat[] leatherHelmetTypes()
    {
        return new LeatherHelmetMat[]{LEATHER_HELMET};
    }

    static
    {
        LeatherHelmetMat.register(LEATHER_HELMET);
    }
}
