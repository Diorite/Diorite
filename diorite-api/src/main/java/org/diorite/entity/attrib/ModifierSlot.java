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

package org.diorite.entity.attrib;

import java.util.Map;

import org.diorite.utils.SimpleEnum;
import org.diorite.utils.SimpleEnum.ASimpleEnum;
import org.diorite.utils.collections.maps.CaseInsensitiveMap;

import gnu.trove.map.TIntObjectMap;

/**
 * Represent slot where item needs to be holded to apply modifiers.
 */
@SuppressWarnings("ClassHasNoToStringMethod")
public class ModifierSlot extends ASimpleEnum<ModifierSlot>
{
    static
    {
        init(ModifierSlot.class, 7);
    }

    /**
     * Item needs to be holded in main hand. (right by default)
     */
    public static final ModifierSlot MAIN_HAND = new ModifierSlot("MAIN_HAND", "mainhand");
    /**
     * Item needs to be holded in second hand. (left by default)
     */
    public static final ModifierSlot OFF_HAND = new ModifierSlot("OFF_HAND", "offhand");
    /**
     * Item needs to be worn as boots.
     */
    public static final ModifierSlot FEET     = new ModifierSlot("FEET", "feet");
    /**
     * Item needs to be worn as leggings.
     */
    public static final ModifierSlot LEGS     = new ModifierSlot("LEGS", "legs");
    /**
     * Item needs to be worn as chestplate.
     */
    public static final ModifierSlot TORSO    = new ModifierSlot("TORSO", "torso");
    /**
     * Item needs to be worn as helmet.
     */
    public static final ModifierSlot HEAD     = new ModifierSlot("HEAD", "head");
    /**
     * Modifier isn't from item or other unknown state.
     */
    public static final ModifierSlot NOT_SET = new ModifierSlot("NOT_SET", "");

    private static final Map<String, ModifierSlot> byTypeName = new CaseInsensitiveMap<>(7, SMALL_LOAD_FACTOR);

    /**
     * Type name of modifier slot.
     */
    protected final String typeName;

    /**
     * Construct new ModifierSlot.
     *
     * @param enumName enum name of slot type.
     * @param enumId   enum id of slot type.
     * @param typeName type name of slot type.
     */
    public ModifierSlot(final String enumName, final int enumId, final String typeName)
    {
        super(enumName, enumId);
        this.typeName = typeName;
    }

    /**
     * Construct new ModifierSlot.
     *
     * @param enumName enum name of slot type.
     * @param typeName type name of slot type.
     */
    public ModifierSlot(final String enumName, final String typeName)
    {
        super(enumName);
        this.typeName = typeName;
    }

    /**
     * Returns type name of this modifier slot.
     *
     * @return type name of this modifier slot.
     */
    public String getTypeName()
    {
        return this.typeName;
    }

    /**
     * Register new {@link ModifierSlot} entry in this enum.
     *
     * @param element new element to register.
     */
    public static void register(final ModifierSlot element)
    {
        ASimpleEnum.register(ModifierSlot.class, element);
        byTypeName.put(element.typeName, element);
    }

    /**
     * Get one of {@link ModifierSlot} entry by its ordinal id.
     *
     * @param ordinal ordinal id of entry.
     *
     * @return one of entry or null.
     */
    public static ModifierSlot getByEnumOrdinal(final int ordinal)
    {
        return getByEnumOrdinal(ModifierSlot.class, ordinal);
    }

    /**
     * Get one of {@link ModifierSlot} entry by its type name.
     *
     * @param type type name of entry.
     *
     * @return one of entry or null.
     */
    public static ModifierSlot getByTypeName(final String type)
    {
        return byTypeName.get(type);
    }

    /**
     * Get one of ModifierSlot entry by its name.
     *
     * @param name name of entry.
     *
     * @return one of entry or null.
     */
    public static ModifierSlot getByEnumName(final String name)
    {
        return getByEnumName(ModifierSlot.class, name);
    }

    /**
     * @return all values in array.
     */
    public static ModifierSlot[] values()
    {
        final TIntObjectMap<SimpleEnum<?>> map = getByEnumOrdinal(ModifierSlot.class);
        return (ModifierSlot[]) map.values(new ModifierSlot[map.size()]);
    }

    static
    {
        ModifierSlot.register(MAIN_HAND);
        ModifierSlot.register(OFF_HAND);
        ModifierSlot.register(FEET);
        ModifierSlot.register(LEGS);
        ModifierSlot.register(TORSO);
        ModifierSlot.register(HEAD);
        ModifierSlot.register(NOT_SET);
    }
}