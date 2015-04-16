package org.diorite.material.blocks.stony;

import java.util.Map;

import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.utils.collections.SimpleStringHashMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing block "Bedrock" and all its subtypes.
 */
public class Bedrock extends Stony
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final byte  USED_DATA_VALUES = 1;
    /**
     * Blast resistance of block, can be changed only before server start.
     * Final copy of blast resistance from {@link MagicNumbers} class.
     */
    public static final float BLAST_RESISTANCE = MagicNumbers.MATERIAL__BEDROCK__BLAST_RESISTANCE;
    /**
     * Hardness of block, can be changed only before server start.
     * Final copy of hardness from {@link MagicNumbers} class.
     */
    public static final float HARDNESS         = MagicNumbers.MATERIAL__BEDROCK__HARDNESS;

    public static final Bedrock BEDROCK = new Bedrock();

    private static final Map<String, Bedrock>    byName = new SimpleStringHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<Bedrock> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

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
        return BLAST_RESISTANCE;
    }

    @Override
    public float getHardness()
    {
        return HARDNESS;
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

    /**
     * Returns one of Bedrock sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of Bedrock or null
     */
    public static Bedrock getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of Bedrock sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of Bedrock or null
     */
    public static Bedrock getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
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
