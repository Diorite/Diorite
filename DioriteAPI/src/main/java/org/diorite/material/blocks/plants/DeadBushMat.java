package org.diorite.material.blocks.plants;

import java.util.Map;

import org.diorite.utils.collections.maps.CaseInsensitiveMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing block "DeadBush" and all its subtypes.
 */
public class DeadBushMat extends FlowerMat
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final int USED_DATA_VALUES = 1;

    public static final DeadBushMat DEAD_BUSH = new DeadBushMat();

    private static final Map<String, DeadBushMat>    byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<DeadBushMat> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR, Byte.MIN_VALUE);

    @SuppressWarnings("MagicNumber")
    protected DeadBushMat()
    {
        super("DEAD_BUSH", 32, "minecraft:deadbush", (byte) 0x00, FlowerTypeMat.DEAD_BUSH, 0, 0);
    }

    protected DeadBushMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type, final FlowerTypeMat flowerType, final float hardness, final float blastResistance)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, flowerType, hardness, blastResistance);
    }

    @Override
    public DeadBushMat getFlowerType(final FlowerTypeMat flowerType)
    {
        return getDeadBush(flowerType);
    }

    @Override
    public DeadBushMat getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public DeadBushMat getType(final int id)
    {
        return getByID(id);
    }

    /**
     * Returns one of DeadBush sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of DeadBush or null
     */
    public static DeadBushMat getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of DeadBush sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of DeadBush or null
     */
    public static DeadBushMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Returns one of DeadBush sub-type based on {@link FlowerTypeMat}
     * If this flower don't supprot given type, it will return default one.
     *
     * @param flowerType type of flower
     *
     * @return sub-type of DeadBush
     */
    public static DeadBushMat getDeadBush(final FlowerTypeMat flowerType)
    {
        for (final DeadBushMat mat : byName.values())
        {
            if (mat.flowerType == flowerType)
            {
                return mat;
            }
        }
        return DEAD_BUSH;
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final DeadBushMat element)
    {
        byID.put((byte) element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    @Override
    public DeadBushMat[] types()
    {
        return DeadBushMat.deadBushTypes();
    }

    /**
     * @return array that contains all sub-types of this block.
     */
    public static DeadBushMat[] deadBushTypes()
    {
        return byID.values(new DeadBushMat[byID.size()]);
    }

    static
    {
        DeadBushMat.register(DEAD_BUSH);
    }
}
