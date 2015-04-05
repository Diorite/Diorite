package org.diorite.material.blocks.cold;

import java.util.Map;

import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.material.BlockMaterialData;
import org.diorite.utils.collections.SimpleStringHashMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

public class PackedIce extends BlockMaterialData
{
    public static final byte  USED_DATA_VALUES = 1;
    public static final float BLAST_RESISTANCE = MagicNumbers.MATERIAL__PACKED_ICE__BLAST_RESISTANCE;
    public static final float HARDNESS         = MagicNumbers.MATERIAL__PACKED_ICE__HARDNESS;

    public static final PackedIce PACKED_ICE = new PackedIce();

    private static final Map<String, PackedIce>    byName = new SimpleStringHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<PackedIce> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    @SuppressWarnings("MagicNumber")
    protected PackedIce()
    {
        super("PACKED_ICE", 174, "minecraft:packet_ice", "PACKED_ICE", (byte) 0x00);
    }

    public PackedIce(final String enumName, final int type)
    {
        super(PACKED_ICE.name(), PACKED_ICE.getId(), PACKED_ICE.getMinecraftId(), enumName, (byte) type);
    }

    public PackedIce(final int maxStack, final String typeName, final byte type)
    {
        super(PACKED_ICE.name(), PACKED_ICE.getId(), PACKED_ICE.getMinecraftId(), maxStack, typeName, type);
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