package org.diorite.material.blocks.others;

import java.util.Map;

import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.utils.collections.SimpleStringHashMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

public class RedMushroomBlock extends MushroomBlock
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
    public static final float BLAST_RESISTANCE = MagicNumbers.MATERIAL__RED_MUSHROOM_BLOCK__BLAST_RESISTANCE;	
    /**
     * Hardness of block, can be changed only before server start.
     * Final copy of hardness from {@link MagicNumbers} class.
     */
    public static final float HARDNESS         = MagicNumbers.MATERIAL__RED_MUSHROOM_BLOCK__HARDNESS;

    public static final RedMushroomBlock RED_MUSHROOM_BLOCK = new RedMushroomBlock();

    private static final Map<String, RedMushroomBlock>    byName = new SimpleStringHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<RedMushroomBlock> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    @SuppressWarnings("MagicNumber")
    protected RedMushroomBlock()
    {
        super("RED_MUSHROOM_BLOCK", 100, "minecraft:red_mushroom_block", "RED_MUSHROOM_BLOCK", (byte) 0x00);
    }

    public RedMushroomBlock(final String enumName, final int type)
    {
        super(RED_MUSHROOM_BLOCK.name(), RED_MUSHROOM_BLOCK.getId(), RED_MUSHROOM_BLOCK.getMinecraftId(), enumName, (byte) type);
    }

    public RedMushroomBlock(final int maxStack, final String typeName, final byte type)
    {
        super(RED_MUSHROOM_BLOCK.name(), RED_MUSHROOM_BLOCK.getId(), RED_MUSHROOM_BLOCK.getMinecraftId(), maxStack, typeName, type);
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
    public RedMushroomBlock getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public RedMushroomBlock getType(final int id)
    {
        return getByID(id);
    }

    public static RedMushroomBlock getByID(final int id)
    {
        return byID.get((byte) id);
    }

    public static RedMushroomBlock getByEnumName(final String name)
    {
        return byName.get(name);
    }

    public static void register(final RedMushroomBlock element)
    {
        byID.put(element.getType(), element);
        byName.put(element.name(), element);
    }

    static
    {
        RedMushroomBlock.register(RED_MUSHROOM_BLOCK);
    }
}
