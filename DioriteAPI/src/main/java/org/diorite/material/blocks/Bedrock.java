package org.diorite.material.blocks;

import java.util.Map;

import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.material.BlockMaterialData;
import org.diorite.utils.collections.SimpleStringHashMap;

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
        super("BEDROCK", 7, "minecraft:bedrock", "BEDROCK", (byte) 0x00);
    }

    public Bedrock(final String enumName, final int type)
    {
        super(BEDROCK.name(), BEDROCK.getId(), BEDROCK.getMinecraftId(), BEDROCK.getMaxStack(), enumName, (byte) type);
    }

    public Bedrock(final int maxStack, final String typeName, final byte type)
    {
        super(BEDROCK.name(), BEDROCK.getId(), BEDROCK.getMinecraftId(), maxStack, typeName, type);
    }

    @Override
    public float getBlastResistance()
    {
        return MagicNumbers.MATERIAL__BEDROCK__BLAST_RESISTANCE;
    }

    @Override
    public float getHardness()
    {
        return MagicNumbers.MATERIAL__BEDROCK__HARDNESS;
    }

    @Override
    public Bedrock getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public Bedrock getType(final int id)
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
        byID.put(element.getType(), element);
        byName.put(element.name(), element);
    }

    static
    {
        Bedrock.register(BEDROCK);
    }
}
