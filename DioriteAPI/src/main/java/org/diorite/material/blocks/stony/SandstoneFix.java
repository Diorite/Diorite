package org.diorite.material.blocks.stony;

import java.util.Map;

import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.utils.collections.SimpleStringHashMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

public class SandstoneFix extends Stony
{
    public static final byte  USED_DATA_VALUES = 3;
    public static final float BLAST_RESISTANCE = MagicNumbers.MATERIAL__SAND_STONE__BLAST_RESISTANCE;
    public static final float HARDNESS         = MagicNumbers.MATERIAL__SAND_STONE__HARDNESS;

    public static final SandstoneFix SANDSTONE          = new SandstoneFix();
    public static final SandstoneFix SANDSTONE_CHISELED = new SandstoneFix("CHISELED", 0x01);
    public static final SandstoneFix SANDSTONE_SMOOTH   = new SandstoneFix("SMOOTH", 0x02);

    private static final Map<String, SandstoneFix>    byName = new SimpleStringHashMap<>(USED_DATA_VALUES, SLOW_GROW);
    private static final TByteObjectMap<SandstoneFix> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SLOW_GROW);

    @SuppressWarnings("MagicNumber")
    protected SandstoneFix()
    {
        super("SANDSTONE", 24, "minecraft:sandstone", "SANDSTONE", (byte) 0x00);
    }

    public SandstoneFix(final String enumName, final int type)
    {
        super(SANDSTONE.name(), SANDSTONE.getId(), SANDSTONE.getMinecraftId(), enumName, (byte) type);
    }

    public SandstoneFix(final int maxStack, final String typeName, final byte type)
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
    public SandstoneFix getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public SandstoneFix getType(final int id)
    {
        return getByID(id);
    }

    public static SandstoneFix getByID(final int id)
    {
        return byID.get((byte) id);
    }

    public static SandstoneFix getByEnumName(final String name)
    {
        return byName.get(name);
    }

    public static void register(final SandstoneFix element)
    {
        byID.put(element.getType(), element);
        byName.put(element.name(), element);
    }

    static
    {
        SandstoneFix.register(SANDSTONE);
        SandstoneFix.register(SANDSTONE_CHISELED);
        SandstoneFix.register(SANDSTONE_SMOOTH);
    }
}
