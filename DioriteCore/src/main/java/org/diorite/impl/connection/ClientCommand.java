package org.diorite.impl.connection;

import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.utils.SimpleEnum;
import org.diorite.utils.collections.SimpleStringHashMap;

import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.hash.TIntObjectHashMap;

public class ClientCommand implements SimpleEnum<ClientCommand>
{
    public static final  ClientCommand                PERFORM_RESPAWN            = new ClientCommand("PERFORM_RESPAWN", 0);
    public static final  ClientCommand                REQUEST_STATS              = new ClientCommand("REQUEST_STATS", 1);
    public static final  ClientCommand                OPEN_INVENTORY_ACHIEVEMENT = new ClientCommand("OPEN_INVENTORY_ACHIEVEMENT", 2);
    private static final Map<String, ClientCommand>   byName                     = new SimpleStringHashMap<>(3, .1f);
    @SuppressWarnings("MagicNumber")
    private static final TIntObjectMap<ClientCommand> byID                       = new TIntObjectHashMap<>(3, .1f);
    private final String enumName;
    private final int    id;

    public ClientCommand(final String enumName, final int id)
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
    public ClientCommand byId(final int id)
    {
        return byID.get(id);
    }

    @Override
    public ClientCommand byName(final String name)
    {
        return byName.get(name);
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("enumName", this.enumName).append("id", this.id).toString();
    }

    public static ClientCommand getByID(final int id)
    {
        return byID.get(id);
    }

    public static ClientCommand getByEnumName(final String name)
    {
        return byName.get(name);
    }

    public static void register(final ClientCommand element)
    {
        byID.put(element.getId(), element);
        byName.put(element.name(), element);
    }

    static
    {
        register(PERFORM_RESPAWN);
        register(REQUEST_STATS);
        register(OPEN_INVENTORY_ACHIEVEMENT);
    }
}
