package org.diorite.material.blocks.others;

import java.util.Map;

import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.material.BlockMaterialData;
import org.diorite.utils.collections.SimpleStringHashMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing block "MelonBlock" and all its subtypes.
 */
public class MelonBlock extends BlockMaterialData
{	
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final byte  USED_DATA_VALUES = 1;	
    /**
     * Blast resistance of block, can be changed only before server start.
     * Final copy of blast resistance from {@link MagicNumbers} class.
     */
    public static final float BLAST_RESISTANCE = MagicNumbers.MATERIAL__MELON_BLOCK__BLAST_RESISTANCE;	
    /**
     * Hardness of block, can be changed only before server start.
     * Final copy of hardness from {@link MagicNumbers} class.
     */
    public static final float HARDNESS         = MagicNumbers.MATERIAL__MELON_BLOCK__HARDNESS;

    public static final MelonBlock MELON_BLOCK = new MelonBlock();

    private static final Map<String, MelonBlock>    byName = new SimpleStringHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<MelonBlock> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    @SuppressWarnings("MagicNumber")
    protected MelonBlock()
    {
        super("MELON_BLOCK", 103, "minecraft:melon_block", "MELON_BLOCK", (byte) 0x00);
    }

    public MelonBlock(final String enumName, final int type)
    {
        super(MELON_BLOCK.name(), MELON_BLOCK.getId(), MELON_BLOCK.getMinecraftId(), enumName, (byte) type);
    }

    public MelonBlock(final int maxStack, final String typeName, final byte type)
    {
        super(MELON_BLOCK.name(), MELON_BLOCK.getId(), MELON_BLOCK.getMinecraftId(), maxStack, typeName, type);
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
    public MelonBlock getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public MelonBlock getType(final int id)
    {
        return getByID(id);
    }

    public static MelonBlock getByID(final int id)
    {
        return byID.get((byte) id);
    }

    public static MelonBlock getByEnumName(final String name)
    {
        return byName.get(name);
    }

    public static void register(final MelonBlock element)
    {
        byID.put(element.getType(), element);
        byName.put(element.name(), element);
    }

    static
    {
        MelonBlock.register(MELON_BLOCK);
    }
}
