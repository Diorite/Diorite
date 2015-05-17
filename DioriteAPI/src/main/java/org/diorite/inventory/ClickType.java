package org.diorite.inventory;

import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.utils.SimpleEnum;
import org.diorite.utils.collections.maps.CaseInsensitiveMap;

import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.hash.TIntObjectHashMap;

@SuppressWarnings("MagicNumber")
public class ClickType implements SimpleEnum<ClickType>
{
    public static final ClickType MOUSE_LEFT                = new ClickType("MOUSE_LEFT", 0, 0, 0, true);
    public static final ClickType MOUSE_RIGHT               = new ClickType("MOUSE_RIGHT", 1, 0, 1, true);
    public static final ClickType SHIFT_MOUSE_LEFT          = new ClickType("SHIFT_MOUSE_LEFT", 2, 1, 0, true);
    public static final ClickType SHIFT_MOUSE_RIGHT         = new ClickType("SHIFT_MOUSE_RIGHT", 3, 1, 1, true);
    public static final ClickType NUM_KEY_1                 = new ClickType("NUM_KEY_1", 4, 2, 0, true);
    public static final ClickType NUM_KEY_2                 = new ClickType("NUM_KEY_2", 5, 2, 1, true);
    public static final ClickType NUM_KEY_3                 = new ClickType("NUM_KEY_3", 6, 2, 2, true);
    public static final ClickType NUM_KEY_4                 = new ClickType("NUM_KEY_4", 7, 2, 3, true);
    public static final ClickType NUM_KEY_5                 = new ClickType("NUM_KEY_5", 8, 2, 4, true);
    public static final ClickType NUM_KEY_6                 = new ClickType("NUM_KEY_6", 9, 2, 5, true);
    public static final ClickType NUM_KEY_7                 = new ClickType("NUM_KEY_7", 10, 2, 6, true);
    public static final ClickType NUM_KEY_8                 = new ClickType("NUM_KEY_8", 11, 2, 7, true);
    public static final ClickType NUM_KEY_9                 = new ClickType("NUM_KEY_9", 12, 2, 8, true);
    public static final ClickType MOUSE_MIDDLE              = new ClickType("MOUSE_MIDDLE", 13, 3, 2, true);
    public static final ClickType DROP_KEY                  = new ClickType("DROP_KEY", 14, 4, 0, true);
    public static final ClickType CTRL_DROP_KEY             = new ClickType("CTRL_DROP_KEY", 15, 4, 1, true);
    public static final ClickType MOUSE_LEFT_OUTSIDE_EMPTY  = new ClickType("MOUSE_LEFT_OUTSIDE_EMPTY", 16, 4, 0, false);
    public static final ClickType MOUSE_RIGHT_OUTSIDE_EMPTY = new ClickType("MOUSE_RIGHT_OUTSIDE_EMPTY", 17, 4, 1, false);
    public static final ClickType MOUSE_LEFT_DRAG_START     = new ClickType("MOUSE_LEFT_DRAG_START", 18, 5, 0, false);
    public static final ClickType MOUSE_LEFT_DRAG_ADD       = new ClickType("MOUSE_LEFT_DRAG_ADD", 20, 5, 1, true);
    public static final ClickType MOUSE_LEFT_DRAG_END       = new ClickType("MOUSE_LEFT_DRAG_END", 22, 5, 2, false);
    public static final ClickType MOUSE_RIGHT_DRAG_START    = new ClickType("MOUSE_RIGHT_DRAG_START", 19, 5, 4, false);
    public static final ClickType MOUSE_RIGHT_DRAG_ADD      = new ClickType("MOUSE_RIGHT_DRAG_ADD", 21, 5, 5, true);
    public static final ClickType MOUSE_RIGHT_DRAG_END      = new ClickType("MOUSE_RIGHT_DRAG_END", 23, 5, 6, false);
    public static final ClickType DOUBLE_CLICK              = new ClickType("DOUBLE_CLICK", 24, 6, 0, true);

    private static final Map<String, ClickType>   byName = new CaseInsensitiveMap<>(25, SMALL_LOAD_FACTOR);
    private static final TIntObjectMap<ClickType> byID   = new TIntObjectHashMap<>(25, SMALL_LOAD_FACTOR);

    private final String  enumName;
    private final int     level;
    private final int     mode;
    private final int     button;
    private final boolean normalSlotData;

    public ClickType(final String enumName, final int level, final int mode, final int button, final boolean normalSlotData)
    {
        this.enumName = enumName;
        this.level = level;
        this.mode = mode;
        this.button = button;
        this.normalSlotData = normalSlotData;
    }

    @Override
    public String name()
    {
        return this.enumName;
    }

    @Override
    public int getId()
    {
        return this.level;
    }

    @Override
    public ClickType byId(final int id)
    {
        return byID.get(id);
    }

    @Override
    public ClickType byName(final String name)
    {
        return byName.get(name);
    }

    public int getLevel()
    {
        return this.level;
    }

    public int getMode()
    {
        return this.mode;
    }

    public int getButton()
    {
        return this.button;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("enumName", this.enumName).append("level", this.level).append("mode", this.mode).append("button", this.button).append("normalSlotData", this.normalSlotData).toString();
    }

    public static ClickType get(final int mode, final int button, final boolean normalSlotData)
    {
        for (final ClickType type : byName.values())
        {
            if ((type.mode == mode) && (type.button == button) && (type.normalSlotData == normalSlotData))
            {
                return type;
            }
        }
        return null;
    }

    public static ClickType getByLevel(final int level)
    {
        return byID.get(level);
    }

    public static ClickType getByEnumName(final String name)
    {
        return byName.get(name);
    }

    public static void register(final ClickType element)
    {
        byID.put(element.getLevel(), element);
        byName.put(element.name(), element);
    }

    /**
     * @return all values in array.
     */
    public static ClickType[] values()
    {
        return byID.values(new ClickType[byID.size()]);
    }

    static
    {
        register(MOUSE_LEFT);
        register(MOUSE_RIGHT);
        register(SHIFT_MOUSE_LEFT);
        register(SHIFT_MOUSE_RIGHT);
        register(NUM_KEY_1);
        register(NUM_KEY_2);
        register(NUM_KEY_3);
        register(NUM_KEY_4);
        register(NUM_KEY_5);
        register(NUM_KEY_6);
        register(NUM_KEY_7);
        register(NUM_KEY_8);
        register(NUM_KEY_9);
        register(MOUSE_MIDDLE);
        register(DROP_KEY);
        register(CTRL_DROP_KEY);
        register(MOUSE_LEFT_OUTSIDE_EMPTY);
        register(MOUSE_RIGHT_OUTSIDE_EMPTY);
        register(MOUSE_LEFT_DRAG_START);
        register(MOUSE_LEFT_DRAG_ADD);
        register(MOUSE_LEFT_DRAG_END);
        register(MOUSE_RIGHT_DRAG_START);
        register(MOUSE_RIGHT_DRAG_ADD);
        register(MOUSE_RIGHT_DRAG_END);
        register(DOUBLE_CLICK);
    }
}
