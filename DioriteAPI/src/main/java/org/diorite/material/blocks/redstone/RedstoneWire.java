package org.diorite.material.blocks.redstone;

import java.util.Map;

import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.material.BlockMaterialData;
import org.diorite.utils.collections.SimpleStringHashMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

@SuppressWarnings("MagicNumber")
public class RedstoneWire extends BlockMaterialData
{
    public static final byte  USED_DATA_VALUES = 16;
    public static final float BLAST_RESISTANCE = MagicNumbers.MATERIAL__REDSTONE_WIRE__BLAST_RESISTANCE;
    public static final float HARDNESS         = MagicNumbers.MATERIAL__REDSTONE_WIRE__HARDNESS;

    public static final RedstoneWire REDSTONE_WIRE_OFF   = new RedstoneWire();
    public static final RedstoneWire REDSTONE_WIRE_ON_1  = new RedstoneWire("ON_1", 0x1);
    public static final RedstoneWire REDSTONE_WIRE_ON_2  = new RedstoneWire("ON_2", 0x2);
    public static final RedstoneWire REDSTONE_WIRE_ON_3  = new RedstoneWire("ON_3", 0x3);
    public static final RedstoneWire REDSTONE_WIRE_ON_4  = new RedstoneWire("ON_4", 0x4);
    public static final RedstoneWire REDSTONE_WIRE_ON_5  = new RedstoneWire("ON_5", 0x5);
    public static final RedstoneWire REDSTONE_WIRE_ON_6  = new RedstoneWire("ON_6", 0x6);
    public static final RedstoneWire REDSTONE_WIRE_ON_7  = new RedstoneWire("ON_7", 0x7);
    public static final RedstoneWire REDSTONE_WIRE_ON_8  = new RedstoneWire("ON_8", 0x8);
    public static final RedstoneWire REDSTONE_WIRE_ON_9  = new RedstoneWire("ON_9", 0x9);
    public static final RedstoneWire REDSTONE_WIRE_ON_10 = new RedstoneWire("ON_10", 0xA);
    public static final RedstoneWire REDSTONE_WIRE_ON_11 = new RedstoneWire("ON_11", 0xB);
    public static final RedstoneWire REDSTONE_WIRE_ON_12 = new RedstoneWire("ON_12", 0xC);
    public static final RedstoneWire REDSTONE_WIRE_ON_13 = new RedstoneWire("ON_13", 0xD);
    public static final RedstoneWire REDSTONE_WIRE_ON_14 = new RedstoneWire("ON_14", 0xE);
    public static final RedstoneWire REDSTONE_WIRE_ON_15 = new RedstoneWire("ON_15", 0xF);

    private static final Map<String, RedstoneWire>    byName = new SimpleStringHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<RedstoneWire> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    @SuppressWarnings("MagicNumber")
    protected RedstoneWire()
    {
        super("REDSTONE_WIRE", 55, "minecraft:redstone_wire", "OFF", (byte) 0x00);
    }

    public RedstoneWire(final String enumName, final int type)
    {
        super(REDSTONE_WIRE_OFF.name(), REDSTONE_WIRE_OFF.getId(), REDSTONE_WIRE_OFF.getMinecraftId(), enumName, (byte) type);
    }

    public RedstoneWire(final int maxStack, final String typeName, final byte type)
    {
        super(REDSTONE_WIRE_OFF.name(), REDSTONE_WIRE_OFF.getId(), REDSTONE_WIRE_OFF.getMinecraftId(), maxStack, typeName, type);
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

    public boolean isPowered()
    {
        return this.type != 0;
    }

    public byte getPower()
    {
        return this.type;
    }

    public RedstoneWire getPower(final int power)
    {
        return getByID(power);
    }

    public static RedstoneWire getByID(final int id)
    {
        return byID.get((byte) id);
    }

    public static RedstoneWire getByEnumName(final String name)
    {
        return byName.get(name);
    }

    public static RedstoneWire getRedstoneWire(final int power)
    {
        return getByID(power);
    }

    public static void register(final RedstoneWire element)
    {
        byID.put(element.getType(), element);
        byName.put(element.name(), element);
    }

    static
    {
        RedstoneWire.register(REDSTONE_WIRE_OFF);
        RedstoneWire.register(REDSTONE_WIRE_ON_1);
        RedstoneWire.register(REDSTONE_WIRE_ON_2);
        RedstoneWire.register(REDSTONE_WIRE_ON_3);
        RedstoneWire.register(REDSTONE_WIRE_ON_4);
        RedstoneWire.register(REDSTONE_WIRE_ON_5);
        RedstoneWire.register(REDSTONE_WIRE_ON_6);
        RedstoneWire.register(REDSTONE_WIRE_ON_7);
        RedstoneWire.register(REDSTONE_WIRE_ON_8);
        RedstoneWire.register(REDSTONE_WIRE_ON_9);
        RedstoneWire.register(REDSTONE_WIRE_ON_10);
        RedstoneWire.register(REDSTONE_WIRE_ON_11);
        RedstoneWire.register(REDSTONE_WIRE_ON_12);
        RedstoneWire.register(REDSTONE_WIRE_ON_13);
        RedstoneWire.register(REDSTONE_WIRE_ON_14);
        RedstoneWire.register(REDSTONE_WIRE_ON_15);
    }
}