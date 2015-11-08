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

package org.diorite.inventory;

import org.diorite.utils.SimpleEnum;
import org.diorite.utils.SimpleEnum.ASimpleEnum;

import gnu.trove.map.TIntObjectMap;

@SuppressWarnings({"MagicNumber", "ClassHasNoToStringMethod"})
public class ClickType extends ASimpleEnum<ClickType>
{
    static
    {
        init(ClickType.class, 25);
    }

    public static final ClickType MOUSE_LEFT                = new ClickType("MOUSE_LEFT", 0, 0, true);
    public static final ClickType MOUSE_RIGHT               = new ClickType("MOUSE_RIGHT", 0, 1, true);
    public static final ClickType MOUSE_LEFT_OUTSIDE        = new ClickType("MOUSE_LEFT_OUTSIDE", 0, 0, false);
    public static final ClickType MOUSE_RIGHT_OUTSIDE       = new ClickType("MOUSE_RIGHT_OUTSIDE", 0, 1, false);
    public static final ClickType SHIFT_MOUSE_LEFT          = new ClickType("SHIFT_MOUSE_LEFT", 1, 0, true);
    public static final ClickType SHIFT_MOUSE_RIGHT         = new ClickType("SHIFT_MOUSE_RIGHT", 1, 1, true);
    public static final ClickType NUM_KEY_1                 = new ClickType("NUM_KEY_1", 2, 0, true);
    public static final ClickType NUM_KEY_2                 = new ClickType("NUM_KEY_2", 2, 1, true);
    public static final ClickType NUM_KEY_3                 = new ClickType("NUM_KEY_3", 2, 2, true);
    public static final ClickType NUM_KEY_4                 = new ClickType("NUM_KEY_4", 2, 3, true);
    public static final ClickType NUM_KEY_5                 = new ClickType("NUM_KEY_5", 2, 4, true);
    public static final ClickType NUM_KEY_6                 = new ClickType("NUM_KEY_6", 2, 5, true);
    public static final ClickType NUM_KEY_7                 = new ClickType("NUM_KEY_7", 2, 6, true);
    public static final ClickType NUM_KEY_8                 = new ClickType("NUM_KEY_8", 2, 7, true);
    public static final ClickType NUM_KEY_9                 = new ClickType("NUM_KEY_9", 2, 8, true);
    public static final ClickType MOUSE_MIDDLE              = new ClickType("MOUSE_MIDDLE", 3, 2, true);
    public static final ClickType DROP_KEY                  = new ClickType("DROP_KEY", 4, 0, true);
    public static final ClickType CTRL_DROP_KEY             = new ClickType("CTRL_DROP_KEY", 4, 1, true);
    public static final ClickType MOUSE_LEFT_OUTSIDE_EMPTY  = new ClickType("MOUSE_LEFT_OUTSIDE_EMPTY", 4, 0, false);
    public static final ClickType MOUSE_RIGHT_OUTSIDE_EMPTY = new ClickType("MOUSE_RIGHT_OUTSIDE_EMPTY", 4, 1, false);
    public static final ClickType MOUSE_LEFT_DRAG_START     = new ClickType("MOUSE_LEFT_DRAG_START", 5, 0, false);
    public static final ClickType MOUSE_LEFT_DRAG_ADD       = new ClickType("MOUSE_LEFT_DRAG_ADD", 5, 1, true);
    public static final ClickType MOUSE_LEFT_DRAG_END       = new ClickType("MOUSE_LEFT_DRAG_END", 5, 2, false);
    public static final ClickType MOUSE_RIGHT_DRAG_START    = new ClickType("MOUSE_RIGHT_DRAG_START", 5, 4, false);
    public static final ClickType MOUSE_RIGHT_DRAG_ADD      = new ClickType("MOUSE_RIGHT_DRAG_ADD", 5, 5, true);
    public static final ClickType MOUSE_RIGHT_DRAG_END      = new ClickType("MOUSE_RIGHT_DRAG_END", 5, 6, false);
    public static final ClickType DOUBLE_CLICK              = new ClickType("DOUBLE_CLICK", 6, 0, true);

    private final int     mode;
    private final int     button;
    private final boolean normalSlotData;

    public ClickType(final String enumName, final int enumId, final int mode, final int button, final boolean normalSlotData)
    {
        super(enumName, enumId);
        this.mode = mode;
        this.button = button;
        this.normalSlotData = normalSlotData;
    }

    public ClickType(final String enumName, final int mode, final int button, final boolean normalSlotData)
    {
        super(enumName);
        this.mode = mode;
        this.button = button;
        this.normalSlotData = normalSlotData;
    }

    public int getMode()
    {
        return this.mode;
    }

    public int getButton()
    {
        return this.button;
    }

    public static ClickType get(final int mode, final int button, final boolean normalSlotData)
    {
        for (final ClickType type : values())
        {
            if ((type.mode == mode) && (type.button == button) && (type.normalSlotData == normalSlotData))
            {
                return type;
            }
        }
        return null;
    }

    /**
     * Register new {@link ClickType} entry in this enum.
     *
     * @param element new element to register.
     */
    public static void register(final ClickType element)
    {
        ASimpleEnum.register(ClickType.class, element);
    }

    /**
     * Get one of {@link ClickType} entry by its ordinal id.
     *
     * @param ordinal ordinal id of entry.
     *
     * @return one of entry or null.
     */
    public static ClickType getByEnumOrdinal(final int ordinal)
    {
        return getByEnumOrdinal(ClickType.class, ordinal);
    }

    /**
     * Get one of {@link ClickType} entry by its name.
     *
     * @param name name of entry.
     *
     * @return one of entry or null.
     */
    public static ClickType getByEnumName(final String name)
    {
        return getByEnumName(ClickType.class, name);
    }

    /**
     * @return all values in array.
     */
    public static ClickType[] values()
    {
        final TIntObjectMap<SimpleEnum<?>> map = getByEnumOrdinal(ClickType.class);
        return (ClickType[]) map.values(new ClickType[map.size()]);
    }

    static
    {
        ClickType.register(MOUSE_LEFT);
        ClickType.register(MOUSE_RIGHT);
        ClickType.register(MOUSE_LEFT_OUTSIDE);
        ClickType.register(MOUSE_RIGHT_OUTSIDE);
        ClickType.register(SHIFT_MOUSE_LEFT);
        ClickType.register(SHIFT_MOUSE_RIGHT);
        ClickType.register(NUM_KEY_1);
        ClickType.register(NUM_KEY_2);
        ClickType.register(NUM_KEY_3);
        ClickType.register(NUM_KEY_4);
        ClickType.register(NUM_KEY_5);
        ClickType.register(NUM_KEY_6);
        ClickType.register(NUM_KEY_7);
        ClickType.register(NUM_KEY_8);
        ClickType.register(NUM_KEY_9);
        ClickType.register(MOUSE_MIDDLE);
        ClickType.register(DROP_KEY);
        ClickType.register(CTRL_DROP_KEY);
        ClickType.register(MOUSE_LEFT_OUTSIDE_EMPTY);
        ClickType.register(MOUSE_RIGHT_OUTSIDE_EMPTY);
        ClickType.register(MOUSE_LEFT_DRAG_START);
        ClickType.register(MOUSE_LEFT_DRAG_ADD);
        ClickType.register(MOUSE_LEFT_DRAG_END);
        ClickType.register(MOUSE_RIGHT_DRAG_START);
        ClickType.register(MOUSE_RIGHT_DRAG_ADD);
        ClickType.register(MOUSE_RIGHT_DRAG_END);
        ClickType.register(DOUBLE_CLICK);
    }
}
