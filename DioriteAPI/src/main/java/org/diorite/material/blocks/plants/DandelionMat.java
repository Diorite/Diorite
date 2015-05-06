package org.diorite.material.blocks.plants;

import java.util.Map;

import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.utils.collections.SimpleStringHashMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing block "Dandelion" and all its subtypes.
 */
public class DandelionMat extends FlowerMat
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final byte  USED_DATA_VALUES = 1;
    /**
     * Blast resistance of block, can be changed only before server start.
     * Final copy of blast resistance from {@link MagicNumbers} class.
     */
    public static final float BLAST_RESISTANCE = MagicNumbers.MATERIAL__DANDELION__BLAST_RESISTANCE;
    /**
     * Hardness of block, can be changed only before server start.
     * Final copy of hardness from {@link MagicNumbers} class.
     */
    public static final float HARDNESS         = MagicNumbers.MATERIAL__DANDELION__HARDNESS;

    public static final DandelionMat DANDELION = new DandelionMat();

    private static final Map<String, DandelionMat>    byName = new SimpleStringHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<DandelionMat> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    @SuppressWarnings("MagicNumber")
    protected DandelionMat()
    {
        super("DANDELION", 37, "minecraft:yellow_flower", (byte) 0x00, FlowerTypeMat.DANDELION);
    }

    protected DandelionMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type, final FlowerTypeMat flowerType)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, flowerType);
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
    public DandelionMat getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public DandelionMat getType(final int id)
    {
        return getByID(id);
    }

    @Override
    public DandelionMat getFlowerType(final FlowerTypeMat flowerType)
    {
        return getDandelion(flowerType);
    }

    /**
     * Returns one of Dandelion sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of Dandelion or null
     */
    public static DandelionMat getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of Dandelion sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of Dandelion or null
     */
    public static DandelionMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Returns one of Dandelion sub-type based on {@link FlowerTypeMat}
     * If this flower don't supprot given type, it will return default one.
     *
     * @param flowerType type of flower
     *
     * @return sub-type of Dandelion
     */
    public static DandelionMat getDandelion(final FlowerTypeMat flowerType)
    {
        for (final DandelionMat mat : byName.values())
        {
            if (mat.flowerType == flowerType)
            {
                return mat;
            }
        }
        return DANDELION;
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final DandelionMat element)
    {
        byID.put((byte) element.getType(), element);
        byName.put(element.name(), element);
    }

    static
    {
        DandelionMat.register(DANDELION);
    }
}
