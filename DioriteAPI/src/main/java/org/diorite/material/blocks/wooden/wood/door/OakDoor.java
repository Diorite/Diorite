package org.diorite.material.blocks.wooden.wood.door;

import java.util.Map;

import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.material.blocks.wooden.WoodType;
import org.diorite.utils.collections.SimpleStringHashMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

public class OakDoor extends WoodenDoor
{
    // TODO: auto-generated class, implement other types (sub-ids).
    public static final byte  USED_DATA_VALUES = 1;
    public static final float BLAST_RESISTANCE = MagicNumbers.MATERIAL__OAK_DOOR__BLAST_RESISTANCE;
    public static final float HARDNESS         = MagicNumbers.MATERIAL__OAK_DOOR__HARDNESS;

    public static final OakDoor OAK_DOOR = new OakDoor();

    private static final Map<String, OakDoor>    byName = new SimpleStringHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<OakDoor> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    @SuppressWarnings("MagicNumber")
    protected OakDoor()
    {
        super("OAK_DOOR", 64, "minecraft:wooden_door", "OAK_DOOR", WoodType.OAK);
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
    public OakDoor getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public OakDoor getType(final int id)
    {
        return getByID(id);
    }

    public static OakDoor getByID(final int id)
    {
        return byID.get((byte) id);
    }

    public static OakDoor getByEnumName(final String name)
    {
        return byName.get(name);
    }

    public static void register(final OakDoor element)
    {
        byID.put(element.getType(), element);
        byName.put(element.name(), element);
    }

    static
    {
        OakDoor.register(OAK_DOOR);
    }
}