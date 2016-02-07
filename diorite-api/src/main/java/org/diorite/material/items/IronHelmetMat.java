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

package org.diorite.material.items;

import java.util.Map;

import org.diorite.material.ArmorMaterial;
import org.diorite.material.ArmorType;
import org.diorite.utils.collections.maps.CaseInsensitiveMap;
import org.diorite.utils.lazy.LazyValue;
import org.diorite.utils.math.DioriteMathUtils;

import it.unimi.dsi.fastutil.shorts.Short2ObjectMap;
import it.unimi.dsi.fastutil.shorts.Short2ObjectOpenHashMap;

/**
 * Class representing 'Iron Helmet' item material in minecraft. <br>
 * ID of material: 306 <br>
 * String ID of material: minecraft:iron_helmet <br>
 * Max item stack size: 1
 */
@SuppressWarnings({"JavaDoc", "ClassHasNoToStringMethod"})
public class IronHelmetMat extends HelmetMat
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final int USED_DATA_VALUES = 1;

    public static final IronHelmetMat IRON_HELMET = new IronHelmetMat();

    private static final Map<String, IronHelmetMat>     byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final Short2ObjectMap<IronHelmetMat> byID   = new Short2ObjectOpenHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    protected final LazyValue<IronHelmetMat> next = new LazyValue<>(() -> (this.haveValidDurability()) ? getByDurability(this.getDurability() + 1) : null);
    protected final LazyValue<IronHelmetMat> prev = new LazyValue<>(() -> (this.haveValidDurability()) ? getByDurability(this.getDurability() - 1) : null);

    @SuppressWarnings("MagicNumber")
    public IronHelmetMat()
    {
        super("IRON_HELMET", 306, "minecraft:iron_helmet", "NEW", (short) 0, ArmorMaterial.IRON);
    }

    public IronHelmetMat(final int durability)
    {
        super(IRON_HELMET.name(), IRON_HELMET.getId(), IRON_HELMET.getMinecraftId(), Integer.toString(durability), (short) durability, ArmorMaterial.IRON);
    }

    public IronHelmetMat(final String enumName, final int id, final String minecraftId, final String typeName, final short type, final ArmorMaterial armorMaterial, final ArmorType armorType)
    {
        super(enumName, id, minecraftId, typeName, type, armorMaterial, armorType);
    }

    public IronHelmetMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final short type, final ArmorMaterial armorMaterial, final ArmorType armorType)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, armorMaterial, armorType);
    }

    @Override
    public IronHelmetMat[] types()
    {
        return new IronHelmetMat[]{IRON_HELMET};
    }

    @Override
    public IronHelmetMat getType(final String type)
    {
        return getByEnumName(type);
    }

    @Override
    public IronHelmetMat getType(final int type)
    {
        return getByID(type);
    }

    @Override
    public IronHelmetMat increaseDurability()
    {
        return this.next.get();
    }

    @Override
    public IronHelmetMat decreaseDurability()
    {
        return this.prev.get();
    }

    @Override
    public IronHelmetMat setDurability(final int durability)
    {
        return this.getType(durability);
    }

    /**
     * Returns one of IronHelmet sub-type based on sub-id.
     *
     * @param id sub-type id
     *
     * @return sub-type of IronHelmet.
     */
    public static IronHelmetMat getByID(final int id)
    {
        IronHelmetMat mat = byID.get((short) id);
        if (mat == null)
        {
            mat = new IronHelmetMat(id);
            if ((id > 0) && (id < IRON_HELMET.getBaseDurability()))
            {
                IronHelmetMat.register(mat);
            }
        }
        return mat;
    }

    /**
     * Returns one of IronHelmet sub-type based on durability.
     *
     * @param id durability of type.
     *
     * @return sub-type of IronHelmet.
     */
    public static IronHelmetMat getByDurability(final int id)
    {
        return getByID(id);
    }

    /**
     * Returns one of IronHelmet-type based on name (selected by diorite team).
     * If item contains only one type, sub-name of it will be this same as name of material.<br>
     * Returns null if name can't be parsed to int and it isn't "NEW" one.
     *
     * @param name name of sub-type
     *
     * @return sub-type of IronHelmet.
     */
    public static IronHelmetMat getByEnumName(final String name)
    {
        final IronHelmetMat mat = byName.get(name);
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
    public static void register(final IronHelmetMat element)
    {
        allItems.incrementAndGet();
        byID.put((short) element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    /**
     * @return array that contains all sub-types of this item.
     */
    public static IronHelmetMat[] ironHelmetTypes()
    {
        return new IronHelmetMat[]{IRON_HELMET};
    }

    static
    {
        IronHelmetMat.register(IRON_HELMET);
    }
}
