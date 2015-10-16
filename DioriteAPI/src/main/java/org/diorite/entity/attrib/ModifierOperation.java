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

import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.function.UnaryOperator;

import org.diorite.utils.SimpleEnum;
import org.diorite.utils.SimpleEnum.ASimpleEnum;

import gnu.trove.map.TIntObjectMap;

@SuppressWarnings("ClassHasNoToStringMethod")
public class ModifierOperation extends ASimpleEnum<ModifierOperation>
{
    static
    {
        init(ModifierOperation.class, 3);
    }

    public static final ModifierOperation ADD_NUMBER          = new ModifierOperation("ADD_NUMBER", ModifierValue::addX, mod -> mod.setY(mod.getX()));
    public static final ModifierOperation MULTIPLY_PERCENTAGE = new ModifierOperation("MULTIPLY_PERCENTAGE", (value, d) -> value.addY(value.getX() * d));
    public static final ModifierOperation ADD_PERCENTAGE      = new ModifierOperation("ADD_PERCENTAGE", (value, d) -> value.multipleY(1 + d));

    private static final SortedSet<ModifierOperation> sortedByID = new TreeSet<>((e1, e2) -> Integer.compare(e1.ordinal, e2.ordinal));
    private final ModifierOperationAction      action;
    private final UnaryOperator<ModifierValue> onEnd;

    public ModifierOperation(final String enumName, final int enumId, final ModifierOperationAction action, final UnaryOperator<ModifierValue> onEnd)
    {
        super(enumName, enumId);
        this.action = action;
        this.onEnd = onEnd;
    }

    public ModifierOperation(final String enumName, final ModifierOperationAction action, final UnaryOperator<ModifierValue> onEnd)
    {
        super(enumName);
        this.action = action;
        this.onEnd = onEnd;
    }

    public ModifierOperation(final String enumName, final ModifierOperationAction action)
    {
        super(enumName);
        this.action = action;
        this.onEnd = m -> m;
    }

    public ModifierOperationAction getAction()
    {
        return this.action;
    }

    public UnaryOperator<ModifierValue> getOnEnd()
    {
        return this.onEnd;
    }

    public ModifierValue use(final ModifierValue value, final double modValue)
    {
        return this.action.use(value, modValue);
    }

    public ModifierValue onEnd(final ModifierValue value)
    {
        return this.onEnd.apply(value);
    }

    @FunctionalInterface
    public interface ModifierOperationAction
    {
        ModifierValue use(ModifierValue value, double modValue);
    }

    public static List<ModifierOperation> getSortedByID()
    {
        return new ArrayList<>(sortedByID);
    }

    /**
     * Register new {@link ModifierOperation} entry in this enum.
     *
     * @param element new element to register.
     */
    public static void register(final ModifierOperation element)
    {
        ASimpleEnum.register(ModifierOperation.class, element);
        sortedByID.add(element);
    }

    /**
     * Get one of {@link ModifierOperation} entry by its ordinal id.
     *
     * @param ordinal ordinal id of entry.
     *
     * @return one of entry or null.
     */
    public static ModifierOperation getByEnumOrdinal(final int ordinal)
    {
        return getByEnumOrdinal(ModifierOperation.class, ordinal);
    }

    /**
     * Get one of {@link ModifierOperation} entry by its name.
     *
     * @param name name of entry.
     *
     * @return one of entry or null.
     */
    public static ModifierOperation getByEnumName(final String name)
    {
        return getByEnumName(ModifierOperation.class, name);
    }

    /**
     * @return all values in array.
     */
    public static ModifierOperation[] values()
    {
        final TIntObjectMap<SimpleEnum<?>> map = getByEnumOrdinal(ModifierOperation.class);
        return (ModifierOperation[]) map.values(new ModifierOperation[map.size()]);
    }


    static
    {
        ModifierOperation.register(ADD_NUMBER);
        ModifierOperation.register(MULTIPLY_PERCENTAGE);
        ModifierOperation.register(ADD_PERCENTAGE);
    }
}
