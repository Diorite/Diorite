package org.diorite.material.blocks.stony;

import java.util.Map;

import org.diorite.utils.collections.maps.CaseInsensitiveMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing block "Bedrock" and all its subtypes.
 */
public class BedrockMat extends StonyMat
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final int USED_DATA_VALUES = 1;

    public static final BedrockMat BEDROCK = new BedrockMat();

    private static final Map<String, BedrockMat>    byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<BedrockMat> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    protected BedrockMat()
    {
        super("BEDROCK", 7, "minecraft:bedrock", "BEDROCK", (byte) 0x00, - 1, 18_000_000);
    }

    protected BedrockMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type, final float hardness, final float blastResistance)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, hardness, blastResistance);
    }

    @Override
    public BedrockMat getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public BedrockMat getType(final int id)
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
    public static BedrockMat getByID(final int id)
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
    public static BedrockMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final BedrockMat element)
    {
        byID.put((byte) element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    @Override
    public BedrockMat[] types()
    {
        return BedrockMat.bedrockTypes();
    }

    /**
     * @return array that contains all sub-types of this block.
     */
    public static BedrockMat[] bedrockTypes()
    {
        return byID.values(new BedrockMat[byID.size()]);
    }

    static
    {
        BedrockMat.register(BEDROCK);
    }
}
