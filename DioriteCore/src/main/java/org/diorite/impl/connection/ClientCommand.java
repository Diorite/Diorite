package org.diorite.impl.connection;

import org.diorite.utils.SimpleEnum;
import org.diorite.utils.SimpleEnum.ASimpleEnum;

import gnu.trove.map.TIntObjectMap;

public class ClientCommand extends ASimpleEnum<ClientCommand>
{
    static
    {
        init(ClientCommand.class, 3);
    }

    public static final ClientCommand PERFORM_RESPAWN            = new ClientCommand("PERFORM_RESPAWN");
    public static final ClientCommand REQUEST_STATS              = new ClientCommand("REQUEST_STATS");
    public static final ClientCommand OPEN_INVENTORY_ACHIEVEMENT = new ClientCommand("OPEN_INVENTORY_ACHIEVEMENT");

    public ClientCommand(final String enumName, final int enumId)
    {
        super(enumName, enumId);
    }

    public ClientCommand(final String enumName)
    {
        super(enumName);
    }

    /**
     * Register new {@link ClientCommand} entry in this enum.
     *
     * @param element new element to register.
     */
    public static void register(final ClientCommand element)
    {
        ASimpleEnum.register(ClientCommand.class, element);
    }

    /**
     * Get one of {@link ClientCommand} entry by its ordinal id.
     *
     * @param ordinal ordinal id of entry.
     *
     * @return one of entry or null.
     */
    public static ClientCommand getByEnumOrdinal(final int ordinal)
    {
        return getByEnumOrdinal(ClientCommand.class, ordinal);
    }

    /**
     * Get one of ClientCommand entry by its name.
     *
     * @param name name of entry.
     *
     * @return one of entry or null.
     */
    public static ClientCommand getByEnumName(final String name)
    {
        return getByEnumName(ClientCommand.class, name);
    }

    /**
     * @return all values in array.
     */
    public static ClientCommand[] values()
    {
        final TIntObjectMap<SimpleEnum<?>> map = getByEnumOrdinal(ClientCommand.class);
        return (ClientCommand[]) map.values(new ClientCommand[map.size()]);
    }

    static
    {
        ClientCommand.register(PERFORM_RESPAWN);
        ClientCommand.register(REQUEST_STATS);
        ClientCommand.register(OPEN_INVENTORY_ACHIEVEMENT);
    }
}
