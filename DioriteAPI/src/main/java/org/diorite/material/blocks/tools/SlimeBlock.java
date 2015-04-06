package org.diorite.material.blocks.tools;

import java.util.Map;

import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.material.BlockMaterialData;
import org.diorite.utils.collections.SimpleStringHashMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

public class SlimeBlock extends BlockMaterialData
{
    public static final byte  USED_DATA_VALUES = 1;
    public static final float BLAST_RESISTANCE = MagicNumbers.MATERIAL__SLIME_BLOCK__BLAST_RESISTANCE;
    public static final float HARDNESS         = MagicNumbers.MATERIAL__SLIME_BLOCK__HARDNESS;

    public static final SlimeBlock SLIME_BLOCK = new SlimeBlock();

    private static final Map<String, SlimeBlock>    byName = new SimpleStringHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<SlimeBlock> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    @SuppressWarnings("MagicNumber")
    protected SlimeBlock()
    {
        super("SLIME_BLOCK", 165, "minecraft:slime", "SLIME_BLOCK", (byte) 0x00);
    }

    public SlimeBlock(final String enumName, final int type)
    {
        super(SLIME_BLOCK.name(), SLIME_BLOCK.getId(), SLIME_BLOCK.getMinecraftId(), enumName, (byte) type);
    }

    public SlimeBlock(final int maxStack, final String typeName, final byte type)
    {
        super(SLIME_BLOCK.name(), SLIME_BLOCK.getId(), SLIME_BLOCK.getMinecraftId(), maxStack, typeName, type);
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
    public SlimeBlock getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public SlimeBlock getType(final int id)
    {
        return getByID(id);
    }

    public static SlimeBlock getByID(final int id)
    {
        return byID.get((byte) id);
    }

    public static SlimeBlock getByEnumName(final String name)
    {
        return byName.get(name);
    }

    public static void register(final SlimeBlock element)
    {
        byID.put(element.getType(), element);
        byName.put(element.name(), element);
    }

    static
    {
        SlimeBlock.register(SLIME_BLOCK);
    }
}