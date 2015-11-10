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
 * Class representing 'Leather Boots' item material in minecraft. <br>
 * ID of material: 301 <br>
 * String ID of material: minecraft:leather_boots <br>
 * Max item stack size: 1
 */
@SuppressWarnings({"JavaDoc", "ClassHasNoToStringMethod"})
public class LeatherBootsMat extends BootsMat
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final int USED_DATA_VALUES = 1;

    public static final LeatherBootsMat LEATHER_BOOTS = new LeatherBootsMat();

    private static final Map<String, LeatherBootsMat>     byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TShortObjectMap<LeatherBootsMat> byID   = new TShortObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR, Short.MIN_VALUE);

    protected final LazyValue<LeatherBootsMat> next = new LazyValue<>(() -> (this.haveValidDurability()) ? getByDurability(this.getDurability() + 1) : null);
    protected final LazyValue<LeatherBootsMat> prev = new LazyValue<>(() -> (this.haveValidDurability()) ? getByDurability(this.getDurability() - 1) : null);

    @SuppressWarnings("MagicNumber")
    protected LeatherBootsMat()
    {
        super("LEATHER_BOOTS", 301, "minecraft:leather_boots", "NEW", (short) 0, ArmorMaterial.LEATHER);
    }

    protected LeatherBootsMat(final int durability)
    {
        super(LEATHER_BOOTS.name(), LEATHER_BOOTS.getId(), LEATHER_BOOTS.getMinecraftId(), Integer.toString(durability), (short) durability, ArmorMaterial.LEATHER);
    }

    protected LeatherBootsMat(final String enumName, final int id, final String minecraftId, final String typeName, final short type, final ArmorMaterial armorMaterial, final ArmorType armorType)
    {
        super(enumName, id, minecraftId, typeName, type, armorMaterial, armorType);
    }

    protected LeatherBootsMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final short type, final ArmorMaterial armorMaterial, final ArmorType armorType)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, armorMaterial, armorType);
    }

    {
        this.metaType = LeatherArmorMeta.class;
    }

    @Override
    public LeatherBootsMat[] types()
    {
        return new LeatherBootsMat[]{LEATHER_BOOTS};
    }

    @Override
    public LeatherBootsMat getType(final String type)
    {
        return getByEnumName(type);
    }

    @Override
    public LeatherBootsMat getType(final int type)
    {
        return getByID(type);
    }

    @Override
    public LeatherBootsMat increaseDurability()
    {
        return this.next.get();
    }

    @Override
    public LeatherBootsMat decreaseDurability()
    {
        return this.prev.get();
    }

    @Override
    public LeatherBootsMat setDurability(final int durability)
    {
        return this.getType(durability);
    }

    /**
     * Returns one of LeatherBoots sub-type based on sub-id.
     *
     * @param id sub-type id
     *
     * @return sub-type of LeatherBoots.
     */
    public static LeatherBootsMat getByID(final int id)
    {
        LeatherBootsMat mat = byID.get((short) id);
        if (mat == null)
        {
            mat = new LeatherBootsMat(id);
            if ((id > 0) && (id < LEATHER_BOOTS.getBaseDurability()))
            {
                LeatherBootsMat.register(mat);
            }
        }
        return mat;
    }

    /**
     * Returns one of LeatherBoots sub-type based on durability.
     *
     * @param id durability of type.
     *
     * @return sub-type of LeatherBoots.
     */
    public static LeatherBootsMat getByDurability(final int id)
    {
        return getByID(id);
    }

    /**
     * Returns one of LeatherBoots-type based on name (selected by diorite team).
     * If item contains only one type, sub-name of it will be this same as name of material.<br>
     * Returns null if name can't be parsed to int and it isn't "NEW" one.
     *
     * @param name name of sub-type
     *
     * @return sub-type of LeatherBoots.
     */
    public static LeatherBootsMat getByEnumName(final String name)
    {
        final LeatherBootsMat mat = byName.get(name);
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
    public static void register(final LeatherBootsMat element)
    {
        byID.put(element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    /**
     * @return array that contains all sub-types of this item.
     */
    public static LeatherBootsMat[] leatherBootsTypes()
    {
        return new LeatherBootsMat[]{LEATHER_BOOTS};
    }

    static
    {
        LeatherBootsMat.register(LEATHER_BOOTS);
    }
}
