package org.diorite.material.blocks.plants;

import java.util.Map;

import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.utils.collections.SimpleStringHashMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

public class SugarCane extends Plant
{
    // TODO: auto-generated class, implement other types (sub-ids).
    public static final byte  USED_DATA_VALUES = 1;
    public static final float BLAST_RESISTANCE = MagicNumbers.MATERIAL__SUGAR_CANE__BLAST_RESISTANCE;
    public static final float HARDNESS         = MagicNumbers.MATERIAL__SUGAR_CANE__HARDNESS;

    public static final SugarCane SUGAR_CANE = new SugarCane();

    private static final Map<String, SugarCane>    byName = new SimpleStringHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<SugarCane> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    @SuppressWarnings("MagicNumber")
    protected SugarCane()
    {
        super("SUGAR_CANE", 83, "minecraft:reeds", "SUGAR_CANE", (byte) 0x00);
    }

    public SugarCane(final String enumName, final int type)
    {
        super(SUGAR_CANE.name(), SUGAR_CANE.getId(), SUGAR_CANE.getMinecraftId(), enumName, (byte) type);
    }

    public SugarCane(final int maxStack, final String typeName, final byte type)
    {
        super(SUGAR_CANE.name(), SUGAR_CANE.getId(), SUGAR_CANE.getMinecraftId(), maxStack, typeName, type);
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
    public SugarCane getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public SugarCane getType(final int id)
    {
        return getByID(id);
    }

    public static SugarCane getByID(final int id)
    {
        return byID.get((byte) id);
    }

    public static SugarCane getByEnumName(final String name)
    {
        return byName.get(name);
    }

    public static void register(final SugarCane element)
    {
        byID.put(element.getType(), element);
        byName.put(element.name(), element);
    }

    static
    {
        SugarCane.register(SUGAR_CANE);
    }
}