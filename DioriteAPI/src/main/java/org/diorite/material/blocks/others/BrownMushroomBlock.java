package org.diorite.material.blocks.others;

import java.util.Map;

import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.utils.collections.SimpleStringHashMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing block "BrownMushroomBlock" and all its subtypes.
 */
public class BrownMushroomBlock extends MushroomBlock
{
    // TODO: auto-generated class, implement other types (sub-ids).	
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final byte  USED_DATA_VALUES = 1;	
    /**
     * Blast resistance of block, can be changed only before server start.
     * Final copy of blast resistance from {@link MagicNumbers} class.
     */
    public static final float BLAST_RESISTANCE = MagicNumbers.MATERIAL__BROWN_MUSHROOM_BLOCK__BLAST_RESISTANCE;	
    /**
     * Hardness of block, can be changed only before server start.
     * Final copy of hardness from {@link MagicNumbers} class.
     */
    public static final float HARDNESS         = MagicNumbers.MATERIAL__BROWN_MUSHROOM_BLOCK__HARDNESS;

    public static final BrownMushroomBlock BROWN_MUSHROOM_BLOCK = new BrownMushroomBlock();

    private static final Map<String, BrownMushroomBlock>    byName = new SimpleStringHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<BrownMushroomBlock> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    @SuppressWarnings("MagicNumber")
    protected BrownMushroomBlock()
    {
        super("BROWN_MUSHROOM_BLOCK", 99, "minecraft:brown_mushroom_block", "BROWN_MUSHROOM_BLOCK", (byte) 0x00);
    }

    public BrownMushroomBlock(final String enumName, final int type)
    {
        super(BROWN_MUSHROOM_BLOCK.name(), BROWN_MUSHROOM_BLOCK.getId(), BROWN_MUSHROOM_BLOCK.getMinecraftId(), enumName, (byte) type);
    }

    public BrownMushroomBlock(final int maxStack, final String typeName, final byte type)
    {
        super(BROWN_MUSHROOM_BLOCK.name(), BROWN_MUSHROOM_BLOCK.getId(), BROWN_MUSHROOM_BLOCK.getMinecraftId(), maxStack, typeName, type);
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
    public BrownMushroomBlock getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public BrownMushroomBlock getType(final int id)
    {
        return getByID(id);
    }

    public static BrownMushroomBlock getByID(final int id)
    {
        return byID.get((byte) id);
    }

    public static BrownMushroomBlock getByEnumName(final String name)
    {
        return byName.get(name);
    }

    public static void register(final BrownMushroomBlock element)
    {
        byID.put(element.getType(), element);
        byName.put(element.name(), element);
    }

    static
    {
        BrownMushroomBlock.register(BROWN_MUSHROOM_BLOCK);
    }
}
