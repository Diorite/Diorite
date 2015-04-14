package org.diorite.material.blocks.earth;

import java.util.Map;

import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.utils.collections.SimpleStringHashMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing block "ClayBlock" and all its subtypes.
 */
public class ClayBlock extends Earth
{	
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final byte  USED_DATA_VALUES = 1;	
    /**
     * Blast resistance of block, can be changed only before server start.
     * Final copy of blast resistance from {@link MagicNumbers} class.
     */
    public static final float BLAST_RESISTANCE = MagicNumbers.MATERIAL__CLAY_BLOCK__BLAST_RESISTANCE;	
    /**
     * Hardness of block, can be changed only before server start.
     * Final copy of hardness from {@link MagicNumbers} class.
     */
    public static final float HARDNESS         = MagicNumbers.MATERIAL__CLAY_BLOCK__HARDNESS;

    public static final ClayBlock CLAY_BLOCK = new ClayBlock();

    private static final Map<String, ClayBlock>    byName = new SimpleStringHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<ClayBlock> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    @SuppressWarnings("MagicNumber")
    protected ClayBlock()
    {
        super("CLAY_BLOCK", 82, "minecraft:clay", "CLAY_BLOCK", (byte) 0x00);
    }

    public ClayBlock(final String enumName, final int type)
    {
        super(CLAY_BLOCK.name(), CLAY_BLOCK.getId(), CLAY_BLOCK.getMinecraftId(), enumName, (byte) type);
    }

    public ClayBlock(final int maxStack, final String typeName, final byte type)
    {
        super(CLAY_BLOCK.name(), CLAY_BLOCK.getId(), CLAY_BLOCK.getMinecraftId(), maxStack, typeName, type);
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
    public ClayBlock getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public ClayBlock getType(final int id)
    {
        return getByID(id);
    }

    public static ClayBlock getByID(final int id)
    {
        return byID.get((byte) id);
    }

    public static ClayBlock getByEnumName(final String name)
    {
        return byName.get(name);
    }

    public static void register(final ClayBlock element)
    {
        byID.put(element.getType(), element);
        byName.put(element.name(), element);
    }

    static
    {
        ClayBlock.register(CLAY_BLOCK);
    }
}
