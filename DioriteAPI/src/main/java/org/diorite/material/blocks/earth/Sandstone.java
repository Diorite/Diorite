package org.diorite.material.blocks.earth;

import java.util.Map;

import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.material.blocks.stony.Stony;
import org.diorite.utils.collections.SimpleStringHashMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

public class Sandstone extends Stony
{
    public static final byte  USED_DATA_VALUES = 3;
    public static final float BLAST_RESISTANCE = MagicNumbers.MATERIAL__SAND_STONE__BLAST_RESISTANCE;
    public static final float HARDNESS         = MagicNumbers.MATERIAL__SAND_STONE__HARDNESS;

    public static final Sandstone SANDSTONE          = new Sandstone();
    public static final Sandstone SANDSTONE_CHISELED = new Sandstone("CHISELED", 0x01);
    public static final Sandstone SANDSTONE_SMOOTH   = new Sandstone("SMOOTH", 0x02);

    private static final Map<String, Sandstone>    byName = new SimpleStringHashMap<>(USED_DATA_VALUES, SLOW_GROW);
    private static final TByteObjectMap<Sandstone> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SLOW_GROW);

    @SuppressWarnings("MagicNumber")
    protected Sandstone()
    {
        super("SANDSTONE", 24, "minecraft:sandstone", "SANDSTONE", (byte) 0x00);
    }

    public Sandstone(final String enumName, final int type)
    {
        super(SANDSTONE.name(), SANDSTONE.getId(), SANDSTONE.getMinecraftId(), enumName, (byte) type);
    }

    public Sandstone(final int maxStack, final String typeName, final byte type)
    {
        super(SANDSTONE.name(), SANDSTONE.getId(), SANDSTONE.getMinecraftId(), maxStack, typeName, type);
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
    public Sandstone getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public Sandstone getType(final int id)
    {
        return getByID(id);
    }

    public static Sandstone getByID(final int id)
    {
        return byID.get((byte) id);
    }

    public static Sandstone getByEnumName(final String name)
    {
        return byName.get(name);
    }

    public static void register(final Sandstone element)
    {
        byID.put(element.getType(), element);
        byName.put(element.name(), element);
    }

    static
    {
        Sandstone.register(SANDSTONE);
        Sandstone.register(SANDSTONE_CHISELED);
        Sandstone.register(SANDSTONE_SMOOTH);
    }
}
