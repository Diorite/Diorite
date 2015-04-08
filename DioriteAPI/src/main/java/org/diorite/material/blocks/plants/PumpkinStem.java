package org.diorite.material.blocks.plants;

import java.util.Map;

import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.utils.collections.SimpleStringHashMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

public class PumpkinStem extends PlantStem
{
    // TODO: auto-generated class, implement other types (sub-ids).
    public static final byte  USED_DATA_VALUES = 1;
    public static final float BLAST_RESISTANCE = MagicNumbers.MATERIAL__PUMPKIN_STEM__BLAST_RESISTANCE;
    public static final float HARDNESS         = MagicNumbers.MATERIAL__PUMPKIN_STEM__HARDNESS;

    public static final PumpkinStem PUMPKIN_STEM = new PumpkinStem();

    private static final Map<String, PumpkinStem>    byName = new SimpleStringHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<PumpkinStem> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    @SuppressWarnings("MagicNumber")
    protected PumpkinStem()
    {
        super("PUMPKIN_STEM", 104, "minecraft:pumpkin_stem", "PUMPKIN_STEM", (byte) 0x00);
    }

    public PumpkinStem(final String enumName, final int type)
    {
        super(PUMPKIN_STEM.name(), PUMPKIN_STEM.getId(), PUMPKIN_STEM.getMinecraftId(), enumName, (byte) type);
    }

    public PumpkinStem(final int maxStack, final String typeName, final byte type)
    {
        super(PUMPKIN_STEM.name(), PUMPKIN_STEM.getId(), PUMPKIN_STEM.getMinecraftId(), maxStack, typeName, type);
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
    public PumpkinStem getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public PumpkinStem getType(final int id)
    {
        return getByID(id);
    }

    public static PumpkinStem getByID(final int id)
    {
        return byID.get((byte) id);
    }

    public static PumpkinStem getByEnumName(final String name)
    {
        return byName.get(name);
    }

    public static void register(final PumpkinStem element)
    {
        byID.put(element.getType(), element);
        byName.put(element.name(), element);
    }

    static
    {
        PumpkinStem.register(PUMPKIN_STEM);
    }
}