package org.diorite.chat;

import org.diorite.utils.SimpleEnum;
import org.diorite.utils.SimpleEnum.ASimpleEnum;

import gnu.trove.map.TIntObjectMap;

@SuppressWarnings("ClassHasNoToStringMethod")
public class ChatVisibility extends ASimpleEnum<ChatVisibility>
{
    static
    {
        init(ChatVisibility.class, 3);
    }

    public static final ChatVisibility FULL   = new ChatVisibility("FULL", "options.chat.visibility.full");
    public static final ChatVisibility SYSTEM = new ChatVisibility("SYSTEM", "options.chat.visibility.system");
    public static final ChatVisibility HIDDEN = new ChatVisibility("HIDDEN", "options.chat.visibility.hidden");

    private final String option;

    public ChatVisibility(final String enumName, final int enumId, final String option)
    {
        super(enumName, enumId);
        this.option = option;
    }

    public ChatVisibility(final String enumName, final String option)
    {
        super(enumName);
        this.option = option;
    }

    public String getOption()
    {
        return this.option;
    }

    /**
     * Register new {@link ChatVisibility} entry in this enum.
     *
     * @param element new element to register.
     */
    public static void register(final ChatVisibility element)
    {
        ASimpleEnum.register(ChatVisibility.class, element);
    }

    /**
     * Get one of {@link ChatVisibility} entry by its ordinal id.
     *
     * @param ordinal ordinal id of entry.
     *
     * @return one of entry or null.
     */
    public static ChatVisibility getByEnumOrdinal(final int ordinal)
    {
        return getByEnumOrdinal(ChatVisibility.class, ordinal);
    }

    /**
     * Get one of {@link ChatVisibility} entry by its name.
     *
     * @param name name of entry.
     *
     * @return one of entry or null.
     */
    public static ChatVisibility getByEnumName(final String name)
    {
        return getByEnumName(ChatVisibility.class, name);
    }

    /**
     * @return all values in array.
     */
    public static ChatVisibility[] values()
    {
        final TIntObjectMap<SimpleEnum<?>> map = getByEnumOrdinal(ChatVisibility.class);
        return (ChatVisibility[]) map.values(new ChatVisibility[map.size()]);
    }

    static
    {
        ChatVisibility.register(FULL);
        ChatVisibility.register(SYSTEM);
        ChatVisibility.register(HIDDEN);
    }
}
