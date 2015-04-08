package org.diorite.material.blocks.others;

import java.util.Map;

import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.utils.collections.SimpleStringHashMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

public class IronTrapdoor extends Trapdoor
{
    // TODO: auto-generated class, implement other types (sub-ids).
    public static final byte  USED_DATA_VALUES = 1;
    public static final float BLAST_RESISTANCE = MagicNumbers.MATERIAL__IRON_TRAPDOOR__BLAST_RESISTANCE;
    public static final float HARDNESS         = MagicNumbers.MATERIAL__IRON_TRAPDOOR__HARDNESS;

    public static final IronTrapdoor IRON_TRAPDOOR = new IronTrapdoor();

    private static final Map<String, IronTrapdoor>    byName = new SimpleStringHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<IronTrapdoor> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    @SuppressWarnings("MagicNumber")
    protected IronTrapdoor()
    {
        super("IRON_TRAPDOOR", 167, "minecraft:iron_trapdoor", "IRON_TRAPDOOR", (byte) 0x00);
    }

    public IronTrapdoor(final String enumName, final int type)
    {
        super(IRON_TRAPDOOR.name(), IRON_TRAPDOOR.getId(), IRON_TRAPDOOR.getMinecraftId(), enumName, (byte) type);
    }

    public IronTrapdoor(final int maxStack, final String typeName, final byte type)
    {
        super(IRON_TRAPDOOR.name(), IRON_TRAPDOOR.getId(), IRON_TRAPDOOR.getMinecraftId(), maxStack, typeName, type);
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
    public IronTrapdoor getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public IronTrapdoor getType(final int id)
    {
        return getByID(id);
    }

    public static IronTrapdoor getByID(final int id)
    {
        return byID.get((byte) id);
    }

    public static IronTrapdoor getByEnumName(final String name)
    {
        return byName.get(name);
    }

    public static void register(final IronTrapdoor element)
    {
        byID.put(element.getType(), element);
        byName.put(element.name(), element);
    }

    static
    {
        IronTrapdoor.register(IRON_TRAPDOOR);
    }
}