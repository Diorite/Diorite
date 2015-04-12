package org.diorite.material.blocks.end;

import java.util.Map;

import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.material.blocks.Portal;
import org.diorite.utils.collections.SimpleStringHashMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

public class EndPortal extends Portal
{
    // TODO: auto-generated class, implement other types (sub-ids).	
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final byte  USED_DATA_VALUES = 1;	
    /**
     * Blast resistance of block, can be changed only before server start.
     * Final copy of blast resistance from {@link MagicNumbers} class.
     */
    public static final float BLAST_RESISTANCE = MagicNumbers.MATERIAL__END_PORTAL__BLAST_RESISTANCE;	
    /**
     * Hardness of block, can be changed only before server start.
     * Final copy of hardness from {@link MagicNumbers} class.
     */
    public static final float HARDNESS         = MagicNumbers.MATERIAL__END_PORTAL__HARDNESS;

    public static final EndPortal END_PORTAL = new EndPortal();

    private static final Map<String, EndPortal>    byName = new SimpleStringHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<EndPortal> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    @SuppressWarnings("MagicNumber")
    protected EndPortal()
    {
        super("END_PORTAL", 119, "minecraft:end_portal", "END_PORTAL", (byte) 0x00);
    }

    public EndPortal(final String enumName, final int type)
    {
        super(END_PORTAL.name(), END_PORTAL.getId(), END_PORTAL.getMinecraftId(), enumName, (byte) type);
    }

    public EndPortal(final int maxStack, final String typeName, final byte type)
    {
        super(END_PORTAL.name(), END_PORTAL.getId(), END_PORTAL.getMinecraftId(), maxStack, typeName, type);
    }

    @Override
    public float getBlastResistance()
    {
        return BLAST_RESISTANCE;
    }

    @Override
    public float getHardness()
    {
        return HARDNESS;
    }

    @Override
    public EndPortal getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public EndPortal getType(final int id)
    {
        return getByID(id);
    }

    public static EndPortal getByID(final int id)
    {
        return byID.get((byte) id);
    }

    public static EndPortal getByEnumName(final String name)
    {
        return byName.get(name);
    }

    public static void register(final EndPortal element)
    {
        byID.put(element.getType(), element);
        byName.put(element.name(), element);
    }

    static
    {
        EndPortal.register(END_PORTAL);
    }
}
