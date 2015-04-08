package org.diorite.material.blocks.redstone;

import java.util.Map;

import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.utils.collections.SimpleStringHashMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

public class StonePressurePlate extends PressurePlate
{
    // TODO: auto-generated class, implement other types (sub-ids).
    public static final byte  USED_DATA_VALUES = 1;
    public static final float BLAST_RESISTANCE = MagicNumbers.MATERIAL__STONE_PRESSURE_PLATE__BLAST_RESISTANCE;
    public static final float HARDNESS         = MagicNumbers.MATERIAL__STONE_PRESSURE_PLATE__HARDNESS;

    public static final StonePressurePlate STONE_PRESSURE_PLATE = new StonePressurePlate();

    private static final Map<String, StonePressurePlate>    byName = new SimpleStringHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<StonePressurePlate> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    @SuppressWarnings("MagicNumber")
    protected StonePressurePlate()
    {
        super("STONE_PRESSURE_PLATE", 70, "minecraft:stone_pressure_plate", "STONE_PRESSURE_PLATE", (byte) 0x00);
    }

    public StonePressurePlate(final String enumName, final int type)
    {
        super(STONE_PRESSURE_PLATE.name(), STONE_PRESSURE_PLATE.getId(), STONE_PRESSURE_PLATE.getMinecraftId(), enumName, (byte) type);
    }

    public StonePressurePlate(final int maxStack, final String typeName, final byte type)
    {
        super(STONE_PRESSURE_PLATE.name(), STONE_PRESSURE_PLATE.getId(), STONE_PRESSURE_PLATE.getMinecraftId(), maxStack, typeName, type);
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
    public StonePressurePlate getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public StonePressurePlate getType(final int id)
    {
        return getByID(id);
    }

    @Override
    public boolean isActivated()
    {
        return false; // TODO: implement
    }

    public static StonePressurePlate getByID(final int id)
    {
        return byID.get((byte) id);
    }

    public static StonePressurePlate getByEnumName(final String name)
    {
        return byName.get(name);
    }

    public static void register(final StonePressurePlate element)
    {
        byID.put(element.getType(), element);
        byName.put(element.name(), element);
    }

    static
    {
        StonePressurePlate.register(STONE_PRESSURE_PLATE);
    }
}