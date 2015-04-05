package org.diorite.material.blocks.stony.oreblocks;

import java.util.Map;

import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.material.BlockMaterialData;
import org.diorite.utils.collections.SimpleStringHashMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

public class IronBlock extends BlockMaterialData
{
    public static final byte  USED_DATA_VALUES = 1;
    public static final float BLAST_RESISTANCE = MagicNumbers.MATERIAL__IRON_BLOCK__BLAST_RESISTANCE;
    public static final float HARDNESS         = MagicNumbers.MATERIAL__IRON_BLOCK__HARDNESS;

    public static final IronBlock IRON_BLOCK = new IronBlock();

    private static final Map<String, IronBlock>    byName = new SimpleStringHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<IronBlock> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    @SuppressWarnings("MagicNumber")
    protected IronBlock()
    {
        super("IRON_BLOCK", 42, "minecraft:iron_block", "IRON_BLOCK", (byte) 0x00);
    }

    public IronBlock(final String enumName, final int type)
    {
        super(IRON_BLOCK.name(), IRON_BLOCK.getId(), IRON_BLOCK.getMinecraftId(), enumName, (byte) type);
    }

    public IronBlock(final int maxStack, final String typeName, final byte type)
    {
        super(IRON_BLOCK.name(), IRON_BLOCK.getId(), IRON_BLOCK.getMinecraftId(), maxStack, typeName, type);
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
    public IronBlock getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public IronBlock getType(final int id)
    {
        return getByID(id);
    }

    public static IronBlock getByID(final int id)
    {
        return byID.get((byte) id);
    }

    public static IronBlock getByEnumName(final String name)
    {
        return byName.get(name);
    }

    public static void register(final IronBlock element)
    {
        byID.put(element.getType(), element);
        byName.put(element.name(), element);
    }

    static
    {
        IronBlock.register(IRON_BLOCK);
    }
}
