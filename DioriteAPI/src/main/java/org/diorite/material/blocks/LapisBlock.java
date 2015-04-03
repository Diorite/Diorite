package org.diorite.material.blocks;

import java.util.Map;

import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.material.BlockMaterialData;
import org.diorite.utils.collections.SimpleStringHashMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

public class LapisBlock extends BlockMaterialData
{
    public static final byte  USED_DATA_VALUES = 1;
    public static final float BLAST_RESISTANCE = MagicNumbers.MATERIAL__LAPIS_BLOCK__BLAST_RESISTANCE;
    public static final float HARDNESS         = MagicNumbers.MATERIAL__LAPIS_BLOCK__HARDNESS;

    public static final LapisBlock LAPIS_BLOCK = new LapisBlock();

    private static final Map<String, LapisBlock>    byName = new SimpleStringHashMap<>(USED_DATA_VALUES, SLOW_GROW);
    private static final TByteObjectMap<LapisBlock> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SLOW_GROW);

    @SuppressWarnings("MagicNumber")
    protected LapisBlock()
    {
        super("LAPIS_BLOCK", 22, "minecraft:lapis_block", "LAPIS_BLOCK", (byte) 0x00);
    }

    public LapisBlock(final String enumName, final int type)
    {
        super(LAPIS_BLOCK.name(), LAPIS_BLOCK.getId(), LAPIS_BLOCK.getMinecraftId(), enumName, (byte) type);
    }

    public LapisBlock(final int maxStack, final String typeName, final byte type)
    {
        super(LAPIS_BLOCK.name(), LAPIS_BLOCK.getId(), LAPIS_BLOCK.getMinecraftId(), maxStack, typeName, type);
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
    public LapisBlock getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public LapisBlock getType(final int id)
    {
        return getByID(id);
    }

    public static LapisBlock getByID(final int id)
    {
        return byID.get((byte) id);
    }

    public static LapisBlock getByEnumName(final String name)
    {
        return byName.get(name);
    }

    public static void register(final LapisBlock element)
    {
        byID.put(element.getType(), element);
        byName.put(element.name(), element);
    }

    static
    {
        LapisBlock.register(LAPIS_BLOCK);
    }
}
