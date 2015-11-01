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

import org.diorite.material.BasicToolData;
import org.diorite.utils.collections.maps.CaseInsensitiveMap;
import org.diorite.utils.lazy.LazyValue;
import org.diorite.utils.math.DioriteMathUtils;

import gnu.trove.map.TShortObjectMap;
import gnu.trove.map.hash.TShortObjectHashMap;

/**
 * Class representing 'Carrot On A Stick' item material in minecraft. <br>
 * ID of material: 398 <br>
 * String ID of material: minecraft:carrot_on_a_stick <br>
 * Max item stack size: 1
 */
@SuppressWarnings({"JavaDoc", "ClassHasNoToStringMethod"})
public class CarrotOnAStickMat extends BasicToolMat
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final int USED_DATA_VALUES = 1;

    public static final CarrotOnAStickMat CARROT_ON_A_STICK = new CarrotOnAStickMat();

    private static final Map<String, CarrotOnAStickMat>     byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TShortObjectMap<CarrotOnAStickMat> byID   = new TShortObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR, Short.MIN_VALUE);

    protected final LazyValue<CarrotOnAStickMat> next = new LazyValue<>(() -> (this.haveValidDurability()) ? getByDurability(this.getDurability() + 1) : null);
    protected final LazyValue<CarrotOnAStickMat> prev = new LazyValue<>(() -> (this.haveValidDurability()) ? getByDurability(this.getDurability() - 1) : null);

    @SuppressWarnings("MagicNumber")
    protected CarrotOnAStickMat()
    {
        super("CARROT_ON_A_STICK", 398, "minecraft:carrot_on_a_stick", "NEW", (short) 0x00, new BasicToolData(26));
    }

    protected CarrotOnAStickMat(final int durability)
    {
        super(CARROT_ON_A_STICK.name(), CARROT_ON_A_STICK.getId(), CARROT_ON_A_STICK.getMinecraftId(), Integer.toString(durability), (short) durability, new BasicToolData(CARROT_ON_A_STICK.getToolData()));
    }

    public CarrotOnAStickMat(final String enumName, final int id, final String minecraftId, final String typeName, final short type, final BasicToolData toolData)
    {
        super(enumName, id, minecraftId, typeName, type, toolData);
    }

    public CarrotOnAStickMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final short type, final BasicToolData toolData)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, toolData);
    }

    @Override
    public CarrotOnAStickMat getType(final String type)
    {
        return getByEnumName(type);
    }

    @Override
    public CarrotOnAStickMat getType(final int type)
    {
        return getByID(type);
    }

    @Override
    public CarrotOnAStickMat increaseDurability()
    {
        return this.next.get();
    }

    @Override
    public CarrotOnAStickMat decreaseDurability()
    {
        return this.prev.get();
    }

    @Override
    public CarrotOnAStickMat setDurability(final int durability)
    {
        return this.getType(durability);
    }

    @Override
    public CarrotOnAStickMat[] types()
    {
        return carrotOnAStickTypes();
    }

    /**
     * Returns one of CarrotOnAStick sub-type based on sub-id.
     *
     * @param id sub-type id
     *
     * @return sub-type of CarrotOnAStick.
     */
    public static CarrotOnAStickMat getByID(final int id)
    {
        CarrotOnAStickMat mat = byID.get((short) id);
        if (mat == null)
        {
            mat = new CarrotOnAStickMat(id);
            if ((id > 0) && (id < CARROT_ON_A_STICK.getBaseDurability()))
            {
                CarrotOnAStickMat.register(mat);
            }
        }
        return mat;
    }

    /**
     * Returns one of CarrotOnAStick sub-type based on durability.
     *
     * @param id durability of type.
     *
     * @return sub-type of CarrotOnAStick.
     */
    public static CarrotOnAStickMat getByDurability(final int id)
    {
        return getByID(id);
    }

    /**
     * Returns one of CarrotOnAStick-type based on name (selected by diorite team).
     * If item contains only one type, sub-name of it will be this same as name of material.<br>
     * Returns null if name can't be parsed to int and it isn't "NEW" one.
     *
     * @param name name of sub-type
     *
     * @return sub-type of CarrotOnAStick.
     */
    public static CarrotOnAStickMat getByEnumName(final String name)
    {
        final CarrotOnAStickMat mat = byName.get(name);
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
    public static void register(final CarrotOnAStickMat element)
    {
        byID.put(element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    /**
     * @return array that contains all sub-types of this item.
     */
    public static CarrotOnAStickMat[] carrotOnAStickTypes()
    {
        return new CarrotOnAStickMat[]{CARROT_ON_A_STICK};
    }

    static
    {
        CarrotOnAStickMat.register(CARROT_ON_A_STICK);
    }
}

