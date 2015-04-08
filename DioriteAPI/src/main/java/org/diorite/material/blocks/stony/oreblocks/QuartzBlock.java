package org.diorite.material.blocks.stony.oreblocks;

import java.util.Map;

import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.material.Material;
import org.diorite.material.blocks.stony.ore.Ore;
import org.diorite.utils.collections.SimpleStringHashMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

public class QuartzBlock extends OreBlock
{
    // TODO: auto-generated class, implement other types (sub-ids).
    public static final byte  USED_DATA_VALUES = 1;
    public static final float BLAST_RESISTANCE = MagicNumbers.MATERIAL__QUARTZ_BLOCK__BLAST_RESISTANCE;
    public static final float HARDNESS         = MagicNumbers.MATERIAL__QUARTZ_BLOCK__HARDNESS;

    public static final QuartzBlock QUARTZ_BLOCK = new QuartzBlock(Material.QUARTZ_ORE);

    private static final Map<String, QuartzBlock>    byName = new SimpleStringHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<QuartzBlock> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    @SuppressWarnings("MagicNumber")
    protected QuartzBlock(final Ore ore)
    {
        super("QUARTZ_BLOCK", 155, "minecraft:quartz_block", "QUARTZ_BLOCK", (byte) 0x00, ore);
    }

    public QuartzBlock(final String enumName, final int type, final Ore ore)
    {
        super(QUARTZ_BLOCK.name(), QUARTZ_BLOCK.getId(), QUARTZ_BLOCK.getMinecraftId(), enumName, (byte) type, ore);
    }

    public QuartzBlock(final int maxStack, final String typeName, final byte type, final Ore ore)
    {
        super(QUARTZ_BLOCK.name(), QUARTZ_BLOCK.getId(), QUARTZ_BLOCK.getMinecraftId(), maxStack, typeName, type, ore);
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
    public QuartzBlock getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public QuartzBlock getType(final int id)
    {
        return getByID(id);
    }

    public static QuartzBlock getByID(final int id)
    {
        return byID.get((byte) id);
    }

    public static QuartzBlock getByEnumName(final String name)
    {
        return byName.get(name);
    }

    public static void register(final QuartzBlock element)
    {
        byID.put(element.getType(), element);
        byName.put(element.name(), element);
    }

    static
    {
        QuartzBlock.register(QUARTZ_BLOCK);
    }
}