package org.diorite.material.blocks.redstone;

import java.util.Map;

import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.material.BlockMaterialData;
import org.diorite.utils.collections.SimpleStringHashMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

public class RedstoneWire extends BlockMaterialData
{
    // TODO: auto-generated class, implement other types (sub-ids).
    public static final byte  USED_DATA_VALUES = 1;
    public static final float BLAST_RESISTANCE = MagicNumbers.MATERIAL__REDSTONE_WIRE__BLAST_RESISTANCE;
    public static final float HARDNESS         = MagicNumbers.MATERIAL__REDSTONE_WIRE__HARDNESS;

    public static final RedstoneWire REDSTONE_WIRE = new RedstoneWire();

    private static final Map<String, RedstoneWire>    byName = new SimpleStringHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<RedstoneWire> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    @SuppressWarnings("MagicNumber")
    protected RedstoneWire()
    {
        super("REDSTONE_WIRE", 55, "minecraft:redstone_wire", "REDSTONE_WIRE", (byte) 0x00);
    }

    public RedstoneWire(final String enumName, final int type)
    {
        super(REDSTONE_WIRE.name(), REDSTONE_WIRE.getId(), REDSTONE_WIRE.getMinecraftId(), enumName, (byte) type);
    }

    public RedstoneWire(final int maxStack, final String typeName, final byte type)
    {
        super(REDSTONE_WIRE.name(), REDSTONE_WIRE.getId(), REDSTONE_WIRE.getMinecraftId(), maxStack, typeName, type);
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
    public RedstoneWire getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public RedstoneWire getType(final int id)
    {
        return getByID(id);
    }

    public static RedstoneWire getByID(final int id)
    {
        return byID.get((byte) id);
    }

    public static RedstoneWire getByEnumName(final String name)
    {
        return byName.get(name);
    }

    public static void register(final RedstoneWire element)
    {
        byID.put(element.getType(), element);
        byName.put(element.name(), element);
    }

    static
    {
        RedstoneWire.register(REDSTONE_WIRE);
    }
}