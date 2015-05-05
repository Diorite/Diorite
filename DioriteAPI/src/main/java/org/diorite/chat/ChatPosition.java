package org.diorite.chat;

import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.utils.SimpleEnum;
import org.diorite.utils.collections.SimpleStringHashMap;

import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.hash.TIntObjectHashMap;

public class ChatPosition implements SimpleEnum<ChatPosition>
{
    public static final ChatPosition CHAT   = new ChatPosition("CHAT", 0);
    public static final ChatPosition SYSTEM = new ChatPosition("SYSTEM", 1);
    public static final ChatPosition ACTION = new ChatPosition("ACTION", 2);

    private static final Map<String, ChatPosition>   byName = new SimpleStringHashMap<>(3, SMALL_LOAD_FACTOR);
    private static final TIntObjectMap<ChatPosition> byID   = new TIntObjectHashMap<>(3, SMALL_LOAD_FACTOR);

    private final String enumName;
    private final int    id;

    public ChatPosition(final String enumName, final int id)
    {
        this.enumName = enumName;
        this.id = id;
    }

    @Override
    public String name()
    {
        return this.enumName;
    }

    @Override
    public int getId()
    {
        return this.id;
    }

    @Override
    public ChatPosition byId(final int id)
    {
        return byID.get(id);
    }

    @Override
    public ChatPosition byName(final String name)
    {
        return byName.get(name);
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("enumName", this.enumName).append("id", this.id).toString();
    }

    public static ChatPosition getByID(final int id)
    {
        return byID.get(id);
    }

    public static ChatPosition getByEnumName(final String name)
    {
        return byName.get(name);
    }

    public static void register(final ChatPosition element)
    {
        byID.put(element.getId(), element);
        byName.put(element.name(), element);
    }

    /**
     * @return all values in array.
     */
    public static ChatPosition[] values()
    {
        return byID.values(new ChatPosition[byID.size()]);
    }

    static
    {
        register(CHAT);
        register(SYSTEM);
        register(ACTION);
    }
}
