package org.diorite.material.blocks.cold;

import java.util.Map;

import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.material.BlockMaterialData;
import org.diorite.utils.collections.SimpleStringHashMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing block "SnowBlock" and all its subtypes.
 */
public class SnowBlock extends BlockMaterialData
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final byte  USED_DATA_VALUES = 1;
    /**
     * Blast resistance of block, can be changed only before server start.
     * Final copy of blast resistance from {@link MagicNumbers} class.
     */
    public static final float BLAST_RESISTANCE = MagicNumbers.MATERIAL__SNOW_BLOCK__BLAST_RESISTANCE;	
    /**
     * Hardness of block, can be changed only before server start.
     * Final copy of hardness from {@link MagicNumbers} class.
     */
    public static final float HARDNESS         = MagicNumbers.MATERIAL__SNOW_BLOCK__HARDNESS;

    public static final SnowBlock SNOW_BLOCK = new SnowBlock();

    private static final Map<String, SnowBlock>    byName = new SimpleStringHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<SnowBlock> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    @SuppressWarnings("MagicNumber")
    protected SnowBlock()
    {
        super("SNOW_BLOCK", 80, "minecraft:snow", "SNOW_BLOCK", (byte) 0x00);
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

    /**
     * Returns one of SnowBlock sub-type based on sub-id, may return null
     * @param id sub-type id
     * @return sub-type of SnowBlock or null
     */
    public static SnowBlock getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of SnowBlock sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     * @param name name of sub-type
     * @return sub-type of SnowBlock or null
     */
    public static SnowBlock getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     * @param element sub-type to register
     */
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
