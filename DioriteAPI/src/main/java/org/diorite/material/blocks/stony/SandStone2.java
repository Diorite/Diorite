package org.diorite.material.blocks.stony;

import java.util.Map;

import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.utils.collections.SimpleStringHashMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

public class SandStone2 extends Stony
{
    public static final byte  USED_DATA_VALUES = 3;
    public static final float BLAST_RESISTANCE = MagicNumbers.MATERIAL__SAND_STONE__BLAST_RESISTANCE;
    public static final float HARDNESS         = MagicNumbers.MATERIAL__SAND_STONE__HARDNESS;

    public static final SandStone2 SANDSTONE          = new SandStone2();
    public static final SandStone2 SANDSTONE_CHISELED = new SandStone2("CHISELED", 0x01);
    public static final SandStone2 SANDSTONE_SMOOTH   = new SandStone2("SMOOTH", 0x02);

    private static final Map<String, SandStone2>    byName = new SimpleStringHashMap<>(USED_DATA_VALUES, SLOW_GROW);
    private static final TByteObjectMap<SandStone2> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SLOW_GROW);

    @SuppressWarnings("MagicNumber")
    protected SandStone2()
    {
        super("SANDSTONE", 24, "minecraft:sandstone", "SANDSTONE", (byte) 0x00);
    }

    public SandStone2(final String enumName, final int type)
    {
        super(SANDSTONE.name(), SANDSTONE.getId(), SANDSTONE.getMinecraftId(), enumName, (byte) type);
    }

    public SandStone2(final int maxStack, final String typeName, final byte type)
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
    public SandStone2 getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public SandStone2 getType(final int id)
    {
        return getByID(id);
    }

    public static SandStone2 getByID(final int id)
    {
        return byID.get((byte) id);
    }

    public static SandStone2 getByEnumName(final String name)
    {
        return byName.get(name);
    }

    public static void register(final SandStone2 element)
    {
        byID.put(element.getType(), element);
        byName.put(element.name(), element);
    }

    static
    {
        SandStone2.register(SANDSTONE);
        SandStone2.register(SANDSTONE_CHISELED);
        SandStone2.register(SANDSTONE_SMOOTH);
    }
}
