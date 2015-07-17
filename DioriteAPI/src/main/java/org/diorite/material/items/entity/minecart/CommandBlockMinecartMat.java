package org.diorite.material.items.entity.minecart;

import java.util.Map;

import org.diorite.utils.collections.maps.CaseInsensitiveMap;

import gnu.trove.map.TShortObjectMap;
import gnu.trove.map.hash.TShortObjectHashMap;

@SuppressWarnings("MagicNumber")
public class CommandBlockMinecartMat extends AbstractMinecartMat
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final byte USED_DATA_VALUES = 1;

    public static final CommandBlockMinecartMat COMMAND_BLOCK_MINECART = new CommandBlockMinecartMat();

    private static final Map<String, CommandBlockMinecartMat>     byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TShortObjectMap<CommandBlockMinecartMat> byID   = new TShortObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    protected CommandBlockMinecartMat()
    {
        super("COMMAND_BLOCK_MINECART", 422, "minecraft:command_block_minecart", "COMMAND_BLOCK_MINECART", (short) 0x00);
    }

    protected CommandBlockMinecartMat(final String enumName, final int id, final String minecraftId, final String typeName, final short type)
    {
        super(enumName, id, minecraftId, typeName, type);
    }

    protected CommandBlockMinecartMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final short type)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type);
    }

    @Override
    public CommandBlockMinecartMat getType(final int type)
    {
        return getByID(type);
    }

    @Override
    public CommandBlockMinecartMat getType(final String type)
    {
        return getByEnumName(type);
    }

    /**
     * Returns one of CommandBlockMinecart sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of CommandBlockMinecart or null
     */
    public static CommandBlockMinecartMat getByID(final int id)
    {
        return byID.get((short) id);
    }

    /**
     * Returns one of CommandBlockMinecart sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of CommandBlockMinecart or null
     */
    public static CommandBlockMinecartMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final CommandBlockMinecartMat element)
    {
        byID.put(element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    @Override
    public CommandBlockMinecartMat[] types()
    {
        return CommandBlockMinecartMat.commandBlockMinecartTypes();
    }

    /**
     * @return array that contains all sub-types of this block.
     */
    public static CommandBlockMinecartMat[] commandBlockMinecartTypes()
    {
        return byID.values(new CommandBlockMinecartMat[byID.size()]);
    }

    static
    {
        CommandBlockMinecartMat.register(COMMAND_BLOCK_MINECART);
    }
}

