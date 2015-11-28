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

import it.unimi.dsi.fastutil.shorts.Short2ObjectMap;
import it.unimi.dsi.fastutil.shorts.Short2ObjectOpenHashMap;

/**
 * Class representing 'Flint And Steel' item material in minecraft. <br>
 * ID of material: 259 <br>
 * String ID of material: minecraft:flint_and_steel <br>
 * Max item stack size: 1
 */
@SuppressWarnings({"JavaDoc", "ClassHasNoToStringMethod"})
public class FlintAndSteelMat extends BasicToolMat
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final int USED_DATA_VALUES = 1;

    public static final FlintAndSteelMat FLINT_AND_STEEL = new FlintAndSteelMat();

    private static final Map<String, FlintAndSteelMat>     byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final Short2ObjectMap<FlintAndSteelMat> byID   = new Short2ObjectOpenHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    protected final LazyValue<FlintAndSteelMat> next = new LazyValue<>(() -> (this.haveValidDurability()) ? getByDurability(this.getDurability() + 1) : null);
    protected final LazyValue<FlintAndSteelMat> prev = new LazyValue<>(() -> (this.haveValidDurability()) ? getByDurability(this.getDurability() - 1) : null);

    @SuppressWarnings("MagicNumber")
    protected FlintAndSteelMat()
    {
        super("FLINT_AND_STEEL", 259, "minecraft:flint_and_steel", "NEW", (short) 0x00, new BasicToolData(64));
    }

    protected FlintAndSteelMat(final int durability)
    {
        super(FLINT_AND_STEEL.name(), FLINT_AND_STEEL.getId(), FLINT_AND_STEEL.getMinecraftId(), Integer.toString(durability), (short) durability, new BasicToolData(FLINT_AND_STEEL.getToolData()));
    }

    public FlintAndSteelMat(final String enumName, final int id, final String minecraftId, final String typeName, final short type, final BasicToolData toolData)
    {
        super(enumName, id, minecraftId, typeName, type, toolData);
    }

    public FlintAndSteelMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final short type, final BasicToolData toolData)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, toolData);
    }

    @Override
    public FlintAndSteelMat getType(final String type)
    {
        return getByEnumName(type);
    }

    @Override
    public FlintAndSteelMat getType(final int type)
    {
        return getByID(type);
    }

    @Override
    public FlintAndSteelMat increaseDurability()
    {
        return this.next.get();
    }

    @Override
    public FlintAndSteelMat decreaseDurability()
    {
        return this.prev.get();
    }

    @Override
    public FlintAndSteelMat setDurability(final int durability)
    {
        return this.getType(durability);
    }

    @Override
    public FlintAndSteelMat[] types()
    {
        return flintAndSteelTypes();
    }

    /**
     * Returns one of FlintAndSteel sub-type based on sub-id.
     *
     * @param id sub-type id
     *
     * @return sub-type of FlintAndSteel.
     */
    public static FlintAndSteelMat getByID(final int id)
    {
        FlintAndSteelMat mat = byID.get((short) id);
        if (mat == null)
        {
            mat = new FlintAndSteelMat(id);
            if ((id > 0) && (id < FLINT_AND_STEEL.getBaseDurability()))
            {
                FlintAndSteelMat.register(mat);
            }
        }
        return mat;
    }

    /**
     * Returns one of FlintAndSteel sub-type based on durability.
     *
     * @param id durability of type.
     *
     * @return sub-type of FlintAndSteel.
     */
    public static FlintAndSteelMat getByDurability(final int id)
    {
        return getByID(id);
    }

    /**
     * Returns one of FlintAndSteel-type based on name (selected by diorite team).
     * If item contains only one type, sub-name of it will be this same as name of material.<br>
     * Returns null if name can't be parsed to int and it isn't "NEW" one.
     *
     * @param name name of sub-type
     *
     * @return sub-type of FlintAndSteel.
     */
    public static FlintAndSteelMat getByEnumName(final String name)
    {
        final FlintAndSteelMat mat = byName.get(name);
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
    public static void register(final FlintAndSteelMat element)
    {
        byID.put((short) element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    /**
     * @return array that contains all sub-types of this item.
     */
    public static FlintAndSteelMat[] flintAndSteelTypes()
    {
        return new FlintAndSteelMat[]{FLINT_AND_STEEL};
    }

    static
    {
        FlintAndSteelMat.register(FLINT_AND_STEEL);
    }
}

