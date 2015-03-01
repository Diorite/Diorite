package diorite;

import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import diorite.utils.DioriteMathUtils;
import diorite.utils.collections.SimpleStringHashMap;
import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.hash.TIntObjectHashMap;

public class ChatVisibility
{
    public static final ChatVisibility FULL   = new ChatVisibility("FULL", 0, "options.chat.visibility.full");
    public static final ChatVisibility SYSTEM = new ChatVisibility("SYSTEM", 1, "options.chat.visibility.system");
    public static final ChatVisibility HIDDEN = new ChatVisibility("HIDDEN", 2, "options.chat.visibility.hidden");

    private final String enumName;
    private final int    id;
    private final String option;
    private static final Map<String, ChatVisibility>   byName = new SimpleStringHashMap<>(3, .1f);
    private static final TIntObjectMap<ChatVisibility> byID   = new TIntObjectHashMap<>(3, .1f);

    public ChatVisibility(final String enumName, final int id, final String option)
    {
        this.enumName = enumName;
        this.id = id;
        this.option = option;
    }

    public String name()
    {
        return this.enumName;
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
        return byID.get((byte) id);
    }

    public static ChatVisibility getByEnumName(final String name)
    {
        return byName.get(name);
    }

    public static void register(final ChatVisibility element)
    {
        byID.put(element.getId(), element);
        byName.put(element.name(), element);
    }

    static
    {
        register(FULL);
        register(SYSTEM);
        register(HIDDEN);
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("enumName", this.enumName).append("id", this.id).append("option", this.option).toString();
    }
}
