package org.diorite.material.blocks.cold;

import java.util.Map;

import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.material.BlockMaterialData;
import org.diorite.utils.collections.SimpleStringHashMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

public class SnowBlock extends BlockMaterialData
{
    public static final byte  USED_DATA_VALUES = 1;
    public static final float BLAST_RESISTANCE = MagicNumbers.MATERIAL__SNOW_BLOCK__BLAST_RESISTANCE;
    public static final float HARDNESS         = MagicNumbers.MATERIAL__SNOW_BLOCK__HARDNESS;

    public static final SnowBlock SNOW_BLOCK = new SnowBlock();

    private static final Map<String, SnowBlock>    byName = new SimpleStringHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<SnowBlock> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    @SuppressWarnings("MagicNumber")
    protected SnowBlock()
    {
        super("SNOW_BLOCK", 80, "minecraft:snow", "SNOW_BLOCK", (byte) 0x00);
    }

    public SnowBlock(final String enumName, final int type)
    {
        super(SNOW_BLOCK.name(), SNOW_BLOCK.getId(), SNOW_BLOCK.getMinecraftId(), enumName, (byte) type);
    }

    public SnowBlock(final int maxStack, final String typeName, final byte type)
    {
        super(SNOW_BLOCK.name(), SNOW_BLOCK.getId(), SNOW_BLOCK.getMinecraftId(), maxStack, typeName, type);
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
    public SnowBlock getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public SnowBlock getType(final int id)
    {
        return getByID(id);
    }

    public static SnowBlock getByID(final int id)
    {
        return byID.get((byte) id);
    }

    public static SnowBlock getByEnumName(final String name)
    {
        return byName.get(name);
    }

    public static void register(final SnowBlock element)
    {
        byID.put(element.getType(), element);
        byName.put(element.name(), element);
    }

    static
    {
        SnowBlock.register(SNOW_BLOCK);
    }
}