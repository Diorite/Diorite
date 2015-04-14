package org.diorite.material.blocks.end;

import java.util.Map;

import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.material.BlockMaterialData;
import org.diorite.utils.collections.SimpleStringHashMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing block "EndPortalFrame" and all its subtypes.
 */
public class EndPortalFrame extends BlockMaterialData
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
    public static final float BLAST_RESISTANCE = MagicNumbers.MATERIAL__END_PORTAL_FRAME__BLAST_RESISTANCE;	
    /**
     * Hardness of block, can be changed only before server start.
     * Final copy of hardness from {@link MagicNumbers} class.
     */
    public static final float HARDNESS         = MagicNumbers.MATERIAL__END_PORTAL_FRAME__HARDNESS;

    public static final EndPortalFrame END_PORTAL_FRAME = new EndPortalFrame();

    private static final Map<String, EndPortalFrame>    byName = new SimpleStringHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<EndPortalFrame> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    @SuppressWarnings("MagicNumber")
    protected EndPortalFrame()
    {
        super("END_PORTAL_FRAME", 120, "minecraft:end_portal_frame", "END_PORTAL_FRAME", (byte) 0x00);
    }

    public EndPortalFrame(final String enumName, final int type)
    {
        super(END_PORTAL_FRAME.name(), END_PORTAL_FRAME.getId(), END_PORTAL_FRAME.getMinecraftId(), enumName, (byte) type);
    }

    public EndPortalFrame(final int maxStack, final String typeName, final byte type)
    {
        super(END_PORTAL_FRAME.name(), END_PORTAL_FRAME.getId(), END_PORTAL_FRAME.getMinecraftId(), maxStack, typeName, type);
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
    public EndPortalFrame getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public EndPortalFrame getType(final int id)
    {
        return getByID(id);
    }

    public static EndPortalFrame getByID(final int id)
    {
        return byID.get((byte) id);
    }

    public static EndPortalFrame getByEnumName(final String name)
    {
        return byName.get(name);
    }

    public static void register(final EndPortalFrame element)
    {
        byID.put(element.getType(), element);
        byName.put(element.name(), element);
    }

    static
    {
        EndPortalFrame.register(END_PORTAL_FRAME);
    }
}
