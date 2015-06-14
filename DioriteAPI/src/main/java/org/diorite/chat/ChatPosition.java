package org.diorite.chat;

import org.diorite.utils.SimpleEnum;
import org.diorite.utils.SimpleEnum.ASimpleEnum;

import gnu.trove.map.TIntObjectMap;

public class ChatPosition extends ASimpleEnum<ChatPosition>
{
    static
    {
        init(ChatPosition.class, 3);
    }

    public static final ChatPosition CHAT   = new ChatPosition("CHAT");
    public static final ChatPosition SYSTEM = new ChatPosition("SYSTEM");
    public static final ChatPosition ACTION = new ChatPosition("ACTION");

    public ChatPosition(final String enumName, final int enumId)
    {
        super(enumName, enumId);
    }

    public ChatPosition(final String enumName)
    {
        super(enumName);
    }

    /**
     * Register new {@link ChatPosition} entry in this enum.
     *
     * @param element new element to register.
     */
    public static void register(final ChatPosition element)
    {
        ASimpleEnum.register(ChatPosition.class, element);
    }

    /**
     * Get one of {@link ChatPosition} entry by its ordinal id.
     *
     * @param ordinal ordinal id of entry.
     *
     * @return one of entry or null.
     */
    public static ChatPosition getByEnumOrdinal(final int ordinal)
    {
        return getByEnumOrdinal(ChatPosition.class, ordinal);
    }

    /**
     * Get one of {@link ChatPosition} entry by its name.
     *
     * @param name name of entry.
     *
     * @return one of entry or null.
     */
    public static ChatPosition getByEnumName(final String name)
    {
        return getByEnumName(ChatPosition.class, name);
    }

    /**
     * @return all values in array.
     */
    public static ChatPosition[] values()
    {
        final TIntObjectMap<SimpleEnum<?>> map = getByEnumOrdinal(ChatPosition.class);
        return (ChatPosition[]) map.values(new ChatPosition[map.size()]);
    }

    static
    {
        ChatPosition.register(CHAT);
        ChatPosition.register(SYSTEM);
        ChatPosition.register(ACTION);
    }
}
