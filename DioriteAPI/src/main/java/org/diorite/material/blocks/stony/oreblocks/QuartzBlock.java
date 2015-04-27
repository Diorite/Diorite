package org.diorite.material.blocks.stony.oreblocks;

import java.util.Map;

import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.material.Material;
import org.diorite.material.blocks.stony.ore.Ore;
import org.diorite.utils.collections.SimpleStringHashMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing block "QuartzBlock" and all its subtypes.
 */
public class QuartzBlock extends OreBlock
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final byte  USED_DATA_VALUES = 5;
    /**
     * Blast resistance of block, can be changed only before server start.
     * Final copy of blast resistance from {@link MagicNumbers} class.
     */
    public static final float BLAST_RESISTANCE = MagicNumbers.MATERIAL__QUARTZ_BLOCK__BLAST_RESISTANCE;
    /**
     * Hardness of block, can be changed only before server start.
     * Final copy of hardness from {@link MagicNumbers} class.
     */
    public static final float HARDNESS         = MagicNumbers.MATERIAL__QUARTZ_BLOCK__HARDNESS;

    public static final QuartzBlock QUARTZ_BLOCK                    = new QuartzBlock(Material.QUARTZ_ORE);
    public static final QuartzBlock QUARTZ_BLOCK_CHISELED           = new QuartzBlock("CHISELED", 0x1, Material.QUARTZ_ORE);
    public static final QuartzBlock QUARTZ_BLOCK_PILLAR_VERTICAL    = new QuartzBlock("PILLAR_VERTICAL", 0x2, Material.QUARTZ_ORE);
    public static final QuartzBlock QUARTZ_BLOCK_PILLAR_NORTH_SOUTH = new QuartzBlock("PILLAR_NORTH_SOUTH", 0x3, Material.QUARTZ_ORE);
    public static final QuartzBlock QUARTZ_BLOCK_PILLAR_EAST_WEST   = new QuartzBlock("PILLAR_EAST_WEST", 0x4, Material.QUARTZ_ORE);

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

    /**
     * Returns one of QuartzBlock sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of QuartzBlock or null
     */
    public static QuartzBlock getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of QuartzBlock sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of QuartzBlock or null
     */
    public static QuartzBlock getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final QuartzBlock element)
    {
        byID.put(element.getType(), element);
        byName.put(element.name(), element);
    }

    static
    {
        QuartzBlock.register(QUARTZ_BLOCK);
        QuartzBlock.register(QUARTZ_BLOCK_CHISELED);
        QuartzBlock.register(QUARTZ_BLOCK_PILLAR_VERTICAL);
        QuartzBlock.register(QUARTZ_BLOCK_PILLAR_NORTH_SOUTH);
        QuartzBlock.register(QUARTZ_BLOCK_PILLAR_EAST_WEST);
    }
}
