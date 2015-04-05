package org.diorite.material.blocks.stony.oreblocks;

import java.util.Map;

import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.material.BlockMaterialData;
import org.diorite.utils.collections.SimpleStringHashMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

public class GoldBlock extends BlockMaterialData
{
    public static final byte  USED_DATA_VALUES = 1;
    public static final float BLAST_RESISTANCE = MagicNumbers.MATERIAL__GOLD_BLOCK__BLAST_RESISTANCE;
    public static final float HARDNESS         = MagicNumbers.MATERIAL__GOLD_BLOCK__HARDNESS;

    public static final GoldBlock GOLD_BLOCK = new GoldBlock();

    private static final Map<String, GoldBlock>    byName = new SimpleStringHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<GoldBlock> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    @SuppressWarnings("MagicNumber")
    protected GoldBlock()
    {
        super("GOLD_BLOCK", 41, "minecraft:gold_block", "GOLD_BLOCK", (byte) 0x00);
    }

    public GoldBlock(final String enumName, final int type)
    {
        super(GOLD_BLOCK.name(), GOLD_BLOCK.getId(), GOLD_BLOCK.getMinecraftId(), enumName, (byte) type);
    }

    public GoldBlock(final int maxStack, final String typeName, final byte type)
    {
        super(GOLD_BLOCK.name(), GOLD_BLOCK.getId(), GOLD_BLOCK.getMinecraftId(), maxStack, typeName, type);
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
    public GoldBlock getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public GoldBlock getType(final int id)
    {
        return getByID(id);
    }

    public static GoldBlock getByID(final int id)
    {
        return byID.get((byte) id);
    }

    public static GoldBlock getByEnumName(final String name)
    {
        return byName.get(name);
    }

    public static void register(final GoldBlock element)
    {
        byID.put(element.getType(), element);
        byName.put(element.name(), element);
    }

    static
    {
        GoldBlock.register(GOLD_BLOCK);
    }
}
