package org.diorite.material.blocks.stony.oreblocks;

import java.util.Map;

import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.material.Material;
import org.diorite.material.blocks.stony.ore.Ore;
import org.diorite.utils.collections.SimpleStringHashMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

public class DiamondBlock extends OreBlock
{
    public static final byte  USED_DATA_VALUES = 1;
    public static final float BLAST_RESISTANCE = MagicNumbers.MATERIAL__DIAMOND_BLOCK__BLAST_RESISTANCE;
    public static final float HARDNESS         = MagicNumbers.MATERIAL__DIAMOND_BLOCK__HARDNESS;

    public static final DiamondBlock DIAMOND_BLOCK = new DiamondBlock(Material.DIAMOND_ORE);

    private static final Map<String, DiamondBlock>    byName = new SimpleStringHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<DiamondBlock> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    @SuppressWarnings("MagicNumber")
    protected DiamondBlock(final Ore ore)
    {
        super("DIAMOND_BLOCK", 57, "minecraft:diamond_block", "DIAMOND_BLOCK", (byte) 0x00, ore);
    }

    public DiamondBlock(final String enumName, final int type, final Ore ore)
    {
        super(DIAMOND_BLOCK.name(), DIAMOND_BLOCK.getId(), DIAMOND_BLOCK.getMinecraftId(), enumName, (byte) type, ore);
    }

    public DiamondBlock(final int maxStack, final String typeName, final byte type, final Ore ore)
    {
        super(DIAMOND_BLOCK.name(), DIAMOND_BLOCK.getId(), DIAMOND_BLOCK.getMinecraftId(), maxStack, typeName, type, ore);
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
    public DiamondBlock getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public DiamondBlock getType(final int id)
    {
        return getByID(id);
    }

    public static DiamondBlock getByID(final int id)
    {
        return byID.get((byte) id);
    }

    public static DiamondBlock getByEnumName(final String name)
    {
        return byName.get(name);
    }

    public static void register(final DiamondBlock element)
    {
        byID.put(element.getType(), element);
        byName.put(element.name(), element);
    }

    static
    {
        DiamondBlock.register(DIAMOND_BLOCK);
    }
}