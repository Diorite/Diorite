package org.diorite.material.blocks.wooden.wood.door;

import java.util.Map;

import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.material.blocks.wooden.WoodType;
import org.diorite.utils.collections.SimpleStringHashMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing block "SpruceDoor" and all its subtypes.
 */
public class SpruceDoor extends WoodenDoor
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
    public static final float BLAST_RESISTANCE = MagicNumbers.MATERIAL__SPRUCE_DOOR__BLAST_RESISTANCE;	
    /**
     * Hardness of block, can be changed only before server start.
     * Final copy of hardness from {@link MagicNumbers} class.
     */
    public static final float HARDNESS         = MagicNumbers.MATERIAL__SPRUCE_DOOR__HARDNESS;

    public static final SpruceDoor SPRUCE_DOOR = new SpruceDoor();

    private static final Map<String, SpruceDoor>    byName = new SimpleStringHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<SpruceDoor> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    @SuppressWarnings("MagicNumber")
    protected SpruceDoor()
    {
        super("SPRUCE_DOOR", 193, "minecraft:spruce_door", "SPRUCE_DOOR", WoodType.SPRUCE);
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
    public SpruceDoor getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public SpruceDoor getType(final int id)
    {
        return getByID(id);
    }

    /**
     * Returns one of SpruceDoor sub-type based on sub-id, may return null
     * @param id sub-type id
     * @return sub-type of SpruceDoor or null
     */
    public static SpruceDoor getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of SpruceDoor sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     * @param name name of sub-type
     * @return sub-type of SpruceDoor or null
     */
    public static SpruceDoor getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     * @param element sub-type to register
     */
    public static void register(final SpruceDoor element)
    {
        byID.put(element.getType(), element);
        byName.put(element.name(), element);
    }

    static
    {
        SpruceDoor.register(SPRUCE_DOOR);
    }
}
