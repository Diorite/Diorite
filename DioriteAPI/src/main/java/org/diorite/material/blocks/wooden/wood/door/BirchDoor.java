package org.diorite.material.blocks.wooden.wood.door;

import java.util.Map;

import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.material.blocks.wooden.WoodType;
import org.diorite.utils.collections.SimpleStringHashMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing block "BirchDoor" and all its subtypes.
 */
public class BirchDoor extends WoodenDoor
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
    public static final float BLAST_RESISTANCE = MagicNumbers.MATERIAL__BIRCH_DOOR__BLAST_RESISTANCE;	
    /**
     * Hardness of block, can be changed only before server start.
     * Final copy of hardness from {@link MagicNumbers} class.
     */
    public static final float HARDNESS         = MagicNumbers.MATERIAL__BIRCH_DOOR__HARDNESS;

    public static final BirchDoor BIRCH_DOOR = new BirchDoor();

    private static final Map<String, BirchDoor>    byName = new SimpleStringHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<BirchDoor> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    @SuppressWarnings("MagicNumber")
    protected BirchDoor()
    {
        super("BIRCH_DOOR", 194, "minecraft:birch_door", "BIRCH_DOOR", WoodType.BIRCH);
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
    public BirchDoor getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public BirchDoor getType(final int id)
    {
        return getByID(id);
    }

    /**
     * Returns one of BirchDoor sub-type based on sub-id, may return null
     * @param id sub-type id
     * @return sub-type of BirchDoor or null
     */
    public static BirchDoor getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of BirchDoor sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     * @param name name of sub-type
     * @return sub-type of BirchDoor or null
     */
    public static BirchDoor getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     * @param element sub-type to register
     */
    public static void register(final BirchDoor element)
    {
        byID.put(element.getType(), element);
        byName.put(element.name(), element);
    }

    static
    {
        BirchDoor.register(BIRCH_DOOR);
    }
}
