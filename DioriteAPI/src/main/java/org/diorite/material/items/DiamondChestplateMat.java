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
 * Class representing 'Diamond Chestplate' item material in minecraft. <br>
 * ID of material: 311 <br>
 * String ID of material: minecraft:diamond_chestplate <br>
 * Max item stack size: 1
 */
@SuppressWarnings({"JavaDoc", "ClassHasNoToStringMethod"})
public class DiamondChestplateMat extends ChestplateMat
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final int USED_DATA_VALUES = 1;

    public static final DiamondChestplateMat DIAMOND_CHESTPLATE = new DiamondChestplateMat();

    private static final Map<String, DiamondChestplateMat>     byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TShortObjectMap<DiamondChestplateMat> byID   = new TShortObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR, Short.MIN_VALUE);

    protected final LazyValue<DiamondChestplateMat> next = new LazyValue<>(() -> (this.haveValidDurability()) ? getByDurability(this.getDurability() + 1) : null);
    protected final LazyValue<DiamondChestplateMat> prev = new LazyValue<>(() -> (this.haveValidDurability()) ? getByDurability(this.getDurability() - 1) : null);

    @SuppressWarnings("MagicNumber")
    protected DiamondChestplateMat()
    {
        super("DIAMOND_CHESTPLATE", 311, "minecraft:diamond_chestplate", "NEW", (short) 0, ArmorMaterial.DIAMOND);
    }

    protected DiamondChestplateMat(final int durability)
    {
        super(DIAMOND_CHESTPLATE.name(), DIAMOND_CHESTPLATE.getId(), DIAMOND_CHESTPLATE.getMinecraftId(), Integer.toString(durability), (short) durability, ArmorMaterial.DIAMOND);
    }

    protected DiamondChestplateMat(final String enumName, final int id, final String minecraftId, final String typeName, final short type, final ArmorMaterial armorMaterial, final ArmorType armorType)
    {
        super(enumName, id, minecraftId, typeName, type, armorMaterial, armorType);
    }

    protected DiamondChestplateMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final short type, final ArmorMaterial armorMaterial, final ArmorType armorType)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, armorMaterial, armorType);
    }

    @Override
    public DiamondChestplateMat[] types()
    {
        return new DiamondChestplateMat[]{DIAMOND_CHESTPLATE};
    }

    @Override
    public DiamondChestplateMat getType(final String type)
    {
        return getByEnumName(type);
    }

    @Override
    public DiamondChestplateMat getType(final int type)
    {
        return getByID(type);
    }

    @Override
    public DiamondChestplateMat increaseDurability()
    {
        return this.next.get();
    }

    @Override
    public DiamondChestplateMat decreaseDurability()
    {
        return this.prev.get();
    }

    @Override
    public DiamondChestplateMat setDurability(final int durability)
    {
        return this.getType(durability);
    }

    /**
     * Returns one of DiamondChestplate sub-type based on sub-id.
     *
     * @param id sub-type id
     *
     * @return sub-type of DiamondChestplate.
     */
    public static DiamondChestplateMat getByID(final int id)
    {
        DiamondChestplateMat mat = byID.get((short) id);
        if (mat == null)
        {
            mat = new DiamondChestplateMat(id);
            if ((id > 0) && (id < DIAMOND_CHESTPLATE.getBaseDurability()))
            {
                DiamondChestplateMat.register(mat);
            }
        }
        return mat;
    }

    /**
     * Returns one of DiamondChestplate sub-type based on durability.
     *
     * @param id durability of type.
     *
     * @return sub-type of DiamondChestplate.
     */
    public static DiamondChestplateMat getByDurability(final int id)
    {
        return getByID(id);
    }

    /**
     * Returns one of DiamondChestplate-type based on name (selected by diorite team).
     * If item contains only one type, sub-name of it will be this same as name of material.<br>
     * Returns null if name can't be parsed to int and it isn't "NEW" one.
     *
     * @param name name of sub-type
     *
     * @return sub-type of DiamondChestplate.
     */
    public static DiamondChestplateMat getByEnumName(final String name)
    {
        final DiamondChestplateMat mat = byName.get(name);
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
    public static void register(final DiamondChestplateMat element)
    {
        byID.put(element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    /**
     * @return array that contains all sub-types of this item.
     */
    public static DiamondChestplateMat[] diamondChestplateTypes()
    {
        return new DiamondChestplateMat[]{DIAMOND_CHESTPLATE};
    }

    static
    {
        DiamondChestplateMat.register(DIAMOND_CHESTPLATE);
    }
}
