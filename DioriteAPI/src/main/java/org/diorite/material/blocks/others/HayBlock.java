package org.diorite.material.blocks.others;

import java.util.Map;

import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.material.BlockMaterialData;
import org.diorite.utils.collections.SimpleStringHashMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing block "HayBlock" and all its subtypes.
 */
public class HayBlock extends BlockMaterialData
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
    public static final float BLAST_RESISTANCE = MagicNumbers.MATERIAL__HAY_BLOCK__BLAST_RESISTANCE;	
    /**
     * Hardness of block, can be changed only before server start.
     * Final copy of hardness from {@link MagicNumbers} class.
     */
    public static final float HARDNESS         = MagicNumbers.MATERIAL__HAY_BLOCK__HARDNESS;

    public static final HayBlock HAY_BLOCK = new HayBlock();

    private static final Map<String, HayBlock>    byName = new SimpleStringHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<HayBlock> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    @SuppressWarnings("MagicNumber")
    protected HayBlock()
    {
        super("HAY_BLOCK", 170, "minecraft:hay_block", "HAY_BLOCK", (byte) 0x00);
    }

    public HayBlock(final String enumName, final int type)
    {
        super(HAY_BLOCK.name(), HAY_BLOCK.getId(), HAY_BLOCK.getMinecraftId(), enumName, (byte) type);
    }

    public HayBlock(final int maxStack, final String typeName, final byte type)
    {
        super(HAY_BLOCK.name(), HAY_BLOCK.getId(), HAY_BLOCK.getMinecraftId(), maxStack, typeName, type);
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
    public HayBlock getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public HayBlock getType(final int id)
    {
        return getByID(id);
    }

    public static HayBlock getByID(final int id)
    {
        return byID.get((byte) id);
    }

    public static HayBlock getByEnumName(final String name)
    {
        return byName.get(name);
    }

    public static void register(final HayBlock element)
    {
        byID.put(element.getType(), element);
        byName.put(element.name(), element);
    }

    static
    {
        HayBlock.register(HAY_BLOCK);
    }
}
