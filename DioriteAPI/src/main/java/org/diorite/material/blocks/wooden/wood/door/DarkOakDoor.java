package org.diorite.material.blocks.wooden.wood.door;

import java.util.Map;

import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.material.blocks.wooden.WoodType;
import org.diorite.utils.collections.SimpleStringHashMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

public class DarkOakDoor extends WoodenDoor
{
    // TODO: auto-generated class, implement other types (sub-ids).
    public static final byte  USED_DATA_VALUES = 1;
    public static final float BLAST_RESISTANCE = MagicNumbers.MATERIAL__DARK_OAK_DOOR__BLAST_RESISTANCE;
    public static final float HARDNESS         = MagicNumbers.MATERIAL__DARK_OAK_DOOR__HARDNESS;

    public static final DarkOakDoor DARK_OAK_DOOR = new DarkOakDoor();

    private static final Map<String, DarkOakDoor>    byName = new SimpleStringHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<DarkOakDoor> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    @SuppressWarnings("MagicNumber")
    protected DarkOakDoor()
    {
        super("DARK_OAK_DOOR", 197, "minecraft:dark_oak_door", "DARK_OAK_DOOR", WoodType.DARK_OAK);
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
    public DarkOakDoor getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public DarkOakDoor getType(final int id)
    {
        return getByID(id);
    }

    public static DarkOakDoor getByID(final int id)
    {
        return byID.get((byte) id);
    }

    public static DarkOakDoor getByEnumName(final String name)
    {
        return byName.get(name);
    }

    public static void register(final DarkOakDoor element)
    {
        byID.put(element.getType(), element);
        byName.put(element.name(), element);
    }

    static
    {
        DarkOakDoor.register(DARK_OAK_DOOR);
    }
}