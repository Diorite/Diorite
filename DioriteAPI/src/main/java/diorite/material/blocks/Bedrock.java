package diorite.material.blocks;

import java.util.Map;

import diorite.material.BlockMaterialData;
import diorite.utils.collections.SimpleStringHashMap;
import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

public class Bedrock extends BlockMaterialData
{
    public static final byte USED_DATA_VALUES = 1;

    public static final Bedrock BEDROCK = new Bedrock();

    private static final Map<String, Bedrock>    byName = new SimpleStringHashMap<>(USED_DATA_VALUES, SLOW_GROW);
    private static final TByteObjectMap<Bedrock> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SLOW_GROW);

    protected Bedrock()
    {
        super("BEDROCK", 7, "BEDROCK", (byte) 0x00);
    }

    public Bedrock(final String enumName, final int type)
    {
        super(AIR.name(), AIR.getId(), AIR.getMaxStack(), enumName, (byte) type);
    }

    public Bedrock(final int maxStack, final int durability, final String typeName, final byte type)
    {
        super(AIR.name(), AIR.getId(), maxStack, durability, typeName, type);
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

    public static Bedrock getByID(final int id)
    {
        return byID.get((byte) id);
    }

    public static Bedrock getByEnumName(final String name)
    {
        return byName.get(name);
    }

    public static void register(final Bedrock element)
    {
        byID.put((byte) element.getId(), element);
        byName.put(element.name(), element);
    }

    static
    {
        Bedrock.register(BEDROCK);
    }
}
