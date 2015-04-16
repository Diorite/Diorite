package org.diorite.material.blocks.stony;

import java.util.Map;

import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.utils.collections.SimpleStringHashMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing block "BrickBlock" and all its subtypes.
 */
public class BrickBlock extends Stony
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final byte  USED_DATA_VALUES = 1;
    /**
     * Blast resistance of block, can be changed only before server start.
     * Final copy of blast resistance from {@link MagicNumbers} class.
     */
    public static final float BLAST_RESISTANCE = MagicNumbers.MATERIAL__BRICK_BLOCK__BLAST_RESISTANCE;
    /**
     * Hardness of block, can be changed only before server start.
     * Final copy of hardness from {@link MagicNumbers} class.
     */
    public static final float HARDNESS         = MagicNumbers.MATERIAL__BRICK_BLOCK__HARDNESS;

    public static final BrickBlock BRICK_BLOCK = new BrickBlock();

    private static final Map<String, BrickBlock>    byName = new SimpleStringHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<BrickBlock> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    @SuppressWarnings("MagicNumber")
    protected BrickBlock()
    {
        super("BRICK_BLOCK", 45, "minecraft:brick_block", "BRICK_BLOCK", (byte) 0x00);
    }

    public BrickBlock(final String enumName, final int type)
    {
        super(BRICK_BLOCK.name(), BRICK_BLOCK.getId(), BRICK_BLOCK.getMinecraftId(), enumName, (byte) type);
    }

    public BrickBlock(final int maxStack, final String typeName, final byte type)
    {
        super(BRICK_BLOCK.name(), BRICK_BLOCK.getId(), BRICK_BLOCK.getMinecraftId(), maxStack, typeName, type);
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
    public BrickBlock getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public BrickBlock getType(final int id)
    {
        return getByID(id);
    }

    /**
     * Returns one of BrickBlock sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of BrickBlock or null
     */
    public static BrickBlock getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of BrickBlock sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of BrickBlock or null
     */
    public static BrickBlock getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final BrickBlock element)
    {
        byID.put(element.getType(), element);
        byName.put(element.name(), element);
    }

    static
    {
        BrickBlock.register(BRICK_BLOCK);
    }
}
