package org.diorite.material.blocks.stony;

import java.util.Map;

import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.utils.collections.SimpleStringHashMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing block "StoneBrick" and all its subtypes.
 */
public class StoneBrick extends Stony
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final byte  USED_DATA_VALUES = 4;
    /**
     * Blast resistance of block, can be changed only before server start.
     * Final copy of blast resistance from {@link MagicNumbers} class.
     */
    public static final float BLAST_RESISTANCE = MagicNumbers.MATERIAL__STONE_BRICK__BLAST_RESISTANCE;
    /**
     * Hardness of block, can be changed only before server start.
     * Final copy of hardness from {@link MagicNumbers} class.
     */
    public static final float HARDNESS         = MagicNumbers.MATERIAL__STONE_BRICK__HARDNESS;

    public static final StoneBrick STONE_BRICK          = new StoneBrick(); // TODO: maybe later add some variant enum with all values like MOSSY, CRACKED etc...
    public static final StoneBrick STONE_BRICK_MOSSY    = new StoneBrick("MOSSY", 0x01);
    public static final StoneBrick STONE_BRICK_CRACKED  = new StoneBrick("CRACKED", 0x02);
    public static final StoneBrick STONE_BRICK_CHISELED = new StoneBrick("CHISELED", 0x03);

    private static final Map<String, StoneBrick>    byName = new SimpleStringHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<StoneBrick> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    @SuppressWarnings("MagicNumber")
    protected StoneBrick()
    {
        super("STONE_BRICK", 98, "minecraft:stonebrick", "STONE_BRICK", (byte) 0x00);
    }

    public StoneBrick(final String enumName, final int type)
    {
        super(STONE_BRICK.name(), STONE_BRICK.getId(), STONE_BRICK.getMinecraftId(), enumName, (byte) type);
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
    public StoneBrick getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public StoneBrick getType(final int id)
    {
        return getByID(id);
    }

    /**
     * Returns one of StoneBrick sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of StoneBrick or null
     */
    public static StoneBrick getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of StoneBrick sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of StoneBrick or null
     */
    public static StoneBrick getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final StoneBrick element)
    {
        byID.put(element.getType(), element);
        byName.put(element.name(), element);
    }

    static
    {
        StoneBrick.register(STONE_BRICK);
        StoneBrick.register(STONE_BRICK_MOSSY);
        StoneBrick.register(STONE_BRICK_CRACKED);
        StoneBrick.register(STONE_BRICK_CHISELED);
    }
}
