package org.diorite.material.blocks.nether;

import java.util.Map;

import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.material.blocks.Portal;
import org.diorite.utils.collections.SimpleStringHashMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

public class NetherPortal extends Portal
{
    // TODO: auto-generated class, implement other types (sub-ids).
    public static final byte  USED_DATA_VALUES = 1;
    public static final float BLAST_RESISTANCE = MagicNumbers.MATERIAL__NETHER_PORTAL__BLAST_RESISTANCE;
    public static final float HARDNESS         = MagicNumbers.MATERIAL__NETHER_PORTAL__HARDNESS;

    public static final NetherPortal NETHER_PORTAL = new NetherPortal();

    private static final Map<String, NetherPortal>    byName = new SimpleStringHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<NetherPortal> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    @SuppressWarnings("MagicNumber")
    protected NetherPortal()
    {
        super("NETHER_PORTAL", 90, "minecraft:portal", "NETHER_PORTAL", (byte) 0x00);
    }

    public NetherPortal(final String enumName, final int type)
    {
        super(NETHER_PORTAL.name(), NETHER_PORTAL.getId(), NETHER_PORTAL.getMinecraftId(), enumName, (byte) type);
    }

    public NetherPortal(final int maxStack, final String typeName, final byte type)
    {
        super(NETHER_PORTAL.name(), NETHER_PORTAL.getId(), NETHER_PORTAL.getMinecraftId(), maxStack, typeName, type);
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
    public NetherPortal getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public NetherPortal getType(final int id)
    {
        return getByID(id);
    }

    public static NetherPortal getByID(final int id)
    {
        return byID.get((byte) id);
    }

    public static NetherPortal getByEnumName(final String name)
    {
        return byName.get(name);
    }

    public static void register(final NetherPortal element)
    {
        byID.put(element.getType(), element);
        byName.put(element.name(), element);
    }

    static
    {
        NetherPortal.register(NETHER_PORTAL);
    }
}