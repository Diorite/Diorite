package org.diorite.material.blocks.stony.oreblocks;

import java.util.Map;

import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.material.Material;
import org.diorite.material.blocks.stony.ore.Ore;
import org.diorite.utils.collections.SimpleStringHashMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing block "RedstoneBlock" and all its subtypes.
 */
public class RedstoneBlock extends OreBlock
{	
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final byte  USED_DATA_VALUES = 1;	
    /**
     * Blast resistance of block, can be changed only before server start.
     * Final copy of blast resistance from {@link MagicNumbers} class.
     */
    public static final float BLAST_RESISTANCE = MagicNumbers.MATERIAL__REDSTONE_BLOCK__BLAST_RESISTANCE;	
    /**
     * Hardness of block, can be changed only before server start.
     * Final copy of hardness from {@link MagicNumbers} class.
     */
    public static final float HARDNESS         = MagicNumbers.MATERIAL__REDSTONE_BLOCK__HARDNESS;

    public static final RedstoneBlock REDSTONE_BLOCK = new RedstoneBlock(Material.REDSTONE_ORE);

    private static final Map<String, RedstoneBlock>    byName = new SimpleStringHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<RedstoneBlock> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    @SuppressWarnings("MagicNumber")
    protected RedstoneBlock(final Ore ore)
    {
        super("REDSTONE_BLOCK", 152, "minecraft:redstone_block", "REDSTONE_BLOCK", (byte) 0x00, ore);
    }

    public RedstoneBlock(final String enumName, final int type, final Ore ore)
    {
        super(REDSTONE_BLOCK.name(), REDSTONE_BLOCK.getId(), REDSTONE_BLOCK.getMinecraftId(), enumName, (byte) type, ore);
    }

    public RedstoneBlock(final int maxStack, final String typeName, final byte type, final Ore ore)
    {
        super(REDSTONE_BLOCK.name(), REDSTONE_BLOCK.getId(), REDSTONE_BLOCK.getMinecraftId(), maxStack, typeName, type, ore);
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
    public RedstoneBlock getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public RedstoneBlock getType(final int id)
    {
        return getByID(id);
    }

    public static RedstoneBlock getByID(final int id)
    {
        return byID.get((byte) id);
    }

    public static RedstoneBlock getByEnumName(final String name)
    {
        return byName.get(name);
    }

    public static void register(final RedstoneBlock element)
    {
        byID.put(element.getType(), element);
        byName.put(element.name(), element);
    }

    static
    {
        RedstoneBlock.register(REDSTONE_BLOCK);
    }
}
