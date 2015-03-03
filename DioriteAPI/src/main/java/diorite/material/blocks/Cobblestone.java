package diorite.material.blocks;

import java.util.Map;

import diorite.material.BlockMaterialData;
import diorite.utils.collections.SimpleStringHashMap;
import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

public class Cobblestone extends BlockMaterialData
{
    public static final Cobblestone COBBLESTONE = new Cobblestone();

    private static final Map<String, Cobblestone>    byName = new SimpleStringHashMap<>(1, .1f);
    private static final TByteObjectMap<Cobblestone> byID   = new TByteObjectHashMap<>(1, .1f);

    protected Cobblestone()
    {
        super("COBBLESTONE", 4, "COBBLESTONE", (byte) 0x00);
    }

    public Cobblestone(final String enumName, final int type)
    {
        super(COBBLESTONE.name(), COBBLESTONE.getId(), enumName, (byte) type);
    }

    public Cobblestone(final int maxStack, final int durability, final String typeName, final byte type)
    {
        super(COBBLESTONE.name(), COBBLESTONE.getId(), maxStack, durability, typeName, type);
    }

    @Override
    public BlockMaterialData getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public BlockMaterialData getType(final int id)
    {
        return getByID(id);
    }

    public static Cobblestone getByID(final int id)
    {
        return byID.get((byte) id);
    }

    public static Cobblestone getByEnumName(final String name)
    {
        return byName.get(name);
    }

    public static void register(final Cobblestone element)
    {
        byID.put((byte) element.getId(), element);
        byName.put(element.name(), element);
    }

    static
    {
        Cobblestone.register(COBBLESTONE);
    }
}
