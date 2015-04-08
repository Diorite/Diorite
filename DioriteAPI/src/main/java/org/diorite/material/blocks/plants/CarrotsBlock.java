package org.diorite.material.blocks.plants;

import java.util.Map;

import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.utils.collections.SimpleStringHashMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

public class CarrotsBlock extends Plant
{
    // TODO: auto-generated class, implement other types (sub-ids).
    public static final byte  USED_DATA_VALUES = 1;
    public static final float BLAST_RESISTANCE = MagicNumbers.MATERIAL__CARROTS_BLOCK__BLAST_RESISTANCE;
    public static final float HARDNESS         = MagicNumbers.MATERIAL__CARROTS_BLOCK__HARDNESS;

    public static final CarrotsBlock CARROTS_BLOCK = new CarrotsBlock();

    private static final Map<String, CarrotsBlock>    byName = new SimpleStringHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<CarrotsBlock> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    @SuppressWarnings("MagicNumber")
    protected CarrotsBlock()
    {
        super("CARROTS_BLOCK", 141, "minecraft:carrots", "CARROTS_BLOCK", (byte) 0x00);
    }

    public CarrotsBlock(final String enumName, final int type)
    {
        super(CARROTS_BLOCK.name(), CARROTS_BLOCK.getId(), CARROTS_BLOCK.getMinecraftId(), enumName, (byte) type);
    }

    public CarrotsBlock(final int maxStack, final String typeName, final byte type)
    {
        super(CARROTS_BLOCK.name(), CARROTS_BLOCK.getId(), CARROTS_BLOCK.getMinecraftId(), maxStack, typeName, type);
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
    public CarrotsBlock getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public CarrotsBlock getType(final int id)
    {
        return getByID(id);
    }

    public static CarrotsBlock getByID(final int id)
    {
        return byID.get((byte) id);
    }

    public static CarrotsBlock getByEnumName(final String name)
    {
        return byName.get(name);
    }

    public static void register(final CarrotsBlock element)
    {
        byID.put(element.getType(), element);
        byName.put(element.name(), element);
    }

    static
    {
        CarrotsBlock.register(CARROTS_BLOCK);
    }
}