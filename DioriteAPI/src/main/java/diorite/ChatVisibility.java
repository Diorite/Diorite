package diorite;

import diorite.utils.DioriteMathUtils;
import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

public enum ChatVisibility
{
    FULL(0, "options.chat.visibility.full"),
    SYSTEM(1, "options.chat.visibility.system"),
    HIDDEN(2, "options.chat.visibility.hidden");

    private final int    id;
    private final String option;
    private static final TByteObjectMap<ChatVisibility> elements = new TByteObjectHashMap<>();

    ChatVisibility(final int id, final String option)
    {
        this.id = id;
        this.option = option;
    }

    public int getId()
    {
        return this.id;
    }

    public String getOption()
    {
        return this.option;
    }

    public static ChatVisibility getByID(final int id)
    {
        if (! DioriteMathUtils.canBeByte(id))
        {
            return null;
        }
        return elements.get((byte) id);
    }

    static
    {
        for (final ChatVisibility diff : ChatVisibility.values())
        {
            elements.put((byte) diff.id, diff);
        }
    }
}
