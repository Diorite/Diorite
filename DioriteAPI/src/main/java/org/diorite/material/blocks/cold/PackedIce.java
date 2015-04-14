package org.diorite.material.blocks.cold;

import java.util.Map;

import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.material.BlockMaterialData;
import org.diorite.utils.collections.SimpleStringHashMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing block "PackedIce" and all its subtypes.
 */
public class PackedIce extends BlockMaterialData
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final byte  USED_DATA_VALUES = 1;
    /**
     * Blast resistance of block, can be changed only before server start.
     * Final copy of blast resistance from {@link MagicNumbers} class.
     */
    public static final float BLAST_RESISTANCE = MagicNumbers.MATERIAL__PACKED_ICE__BLAST_RESISTANCE;
    /**
     * Hardness of block, can be changed only before server start.
     * Final copy of hardness from {@link MagicNumbers} class.
     */
    public static final float HARDNESS         = MagicNumbers.MATERIAL__PACKED_ICE__HARDNESS;

    public static final PackedIce PACKED_ICE = new PackedIce();

    private static final Map<String, PackedIce>    byName = new SimpleStringHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<PackedIce> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    @SuppressWarnings("MagicNumber")
    protected PackedIce()
    {
        super("PACKED_ICE", 174, "minecraft:packet_ice", "PACKED_ICE", (byte) 0x00);
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
    public PackedIce getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public PackedIce getType(final int id)
    {
        return getByID(id);
    }

    public static PackedIce getByID(final int id)
    {
        return byID.get((byte) id);
    }

    public static PackedIce getByEnumName(final String name)
    {
        return byName.get(name);
    }

    public static void register(final PackedIce element)
    {
        byID.put(element.getType(), element);
        byName.put(element.name(), element);
    }

    static
    {
        PackedIce.register(PACKED_ICE);
    }
}
