package org.diorite.material.blocks.others;

import java.util.Map;

import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.material.BlockMaterialData;
import org.diorite.utils.collections.SimpleStringHashMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

public class SkullBlock extends BlockMaterialData
{
    // TODO: auto-generated class, implement other types (sub-ids).
    public static final byte  USED_DATA_VALUES = 1;
    public static final float BLAST_RESISTANCE = MagicNumbers.MATERIAL__SKULL_BLOCK__BLAST_RESISTANCE;
    public static final float HARDNESS         = MagicNumbers.MATERIAL__SKULL_BLOCK__HARDNESS;

    public static final SkullBlock SKULL_BLOCK = new SkullBlock();

    private static final Map<String, SkullBlock>    byName = new SimpleStringHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<SkullBlock> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    @SuppressWarnings("MagicNumber")
    protected SkullBlock()
    {
        super("SKULL_BLOCK", 144, "minecraft:skull", "SKULL_BLOCK", (byte) 0x00);
    }

    public SkullBlock(final String enumName, final int type)
    {
        super(SKULL_BLOCK.name(), SKULL_BLOCK.getId(), SKULL_BLOCK.getMinecraftId(), enumName, (byte) type);
    }

    public SkullBlock(final int maxStack, final String typeName, final byte type)
    {
        super(SKULL_BLOCK.name(), SKULL_BLOCK.getId(), SKULL_BLOCK.getMinecraftId(), maxStack, typeName, type);
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
    public SkullBlock getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public SkullBlock getType(final int id)
    {
        return getByID(id);
    }

    public static SkullBlock getByID(final int id)
    {
        return byID.get((byte) id);
    }

    public static SkullBlock getByEnumName(final String name)
    {
        return byName.get(name);
    }

    public static void register(final SkullBlock element)
    {
        byID.put(element.getType(), element);
        byName.put(element.name(), element);
    }

    static
    {
        SkullBlock.register(SKULL_BLOCK);
    }
}