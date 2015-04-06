package org.diorite.material.blocks.plants;

import java.util.Map;

import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.utils.collections.SimpleStringHashMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

public class WaterLily extends Plant
{
    public static final byte  USED_DATA_VALUES = 1;
    public static final float BLAST_RESISTANCE = MagicNumbers.MATERIAL__WATER_LILY__BLAST_RESISTANCE;
    public static final float HARDNESS         = MagicNumbers.MATERIAL__WATER_LILY__HARDNESS;

    public static final WaterLily WATER_LILY = new WaterLily();

    private static final Map<String, WaterLily>    byName = new SimpleStringHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<WaterLily> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    @SuppressWarnings("MagicNumber")
    protected WaterLily()
    {
        super("WATER_LILY", 111, "minecraft:waterlily", "WATER_LILY", (byte) 0x00);
    }

    public WaterLily(final String enumName, final int type)
    {
        super(WATER_LILY.name(), WATER_LILY.getId(), WATER_LILY.getMinecraftId(), enumName, (byte) type);
    }

    public WaterLily(final int maxStack, final String typeName, final byte type)
    {
        super(WATER_LILY.name(), WATER_LILY.getId(), WATER_LILY.getMinecraftId(), maxStack, typeName, type);
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
    public WaterLily getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public WaterLily getType(final int id)
    {
        return getByID(id);
    }

    public static WaterLily getByID(final int id)
    {
        return byID.get((byte) id);
    }

    public static WaterLily getByEnumName(final String name)
    {
        return byName.get(name);
    }

    public static void register(final WaterLily element)
    {
        byID.put(element.getType(), element);
        byName.put(element.name(), element);
    }

    static
    {
        WaterLily.register(WATER_LILY);
    }
}