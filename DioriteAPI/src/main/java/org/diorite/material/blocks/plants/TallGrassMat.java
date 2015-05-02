package org.diorite.material.blocks.plants;

import java.util.Map;

import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.utils.collections.SimpleStringHashMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing block "TallGrass" and all its subtypes.
 */
public class TallGrassMat extends FlowerMat
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final byte  USED_DATA_VALUES = 3;
    /**
     * Blast resistance of block, can be changed only before server start.
     * Final copy of blast resistance from {@link MagicNumbers} class.
     */
    public static final float BLAST_RESISTANCE = MagicNumbers.MATERIAL__TALL_GRASS__BLAST_RESISTANCE;
    /**
     * Hardness of block, can be changed only before server start.
     * Final copy of hardness from {@link MagicNumbers} class.
     */
    public static final float HARDNESS         = MagicNumbers.MATERIAL__TALL_GRASS__HARDNESS;

    public static final TallGrassMat TALL_GRASS_SHRUB = new TallGrassMat();
    public static final TallGrassMat TALL_GRASS_GRASS = new TallGrassMat(0x1, FlowerTypeMat.GRASS);
    public static final TallGrassMat TALL_GRASS_FERN  = new TallGrassMat(0x2, FlowerTypeMat.FERN);

    private static final Map<String, TallGrassMat>    byName = new SimpleStringHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<TallGrassMat> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    @SuppressWarnings("MagicNumber")
    protected TallGrassMat()
    {
        super("TALL_GRASS", 31, "minecraft:tallgrass", (byte) 0x00, FlowerTypeMat.SHRUB);
    }

    protected TallGrassMat(final int type, final FlowerTypeMat flowerType)
    {
        super(TALL_GRASS_SHRUB.name(), TALL_GRASS_SHRUB.getId(), TALL_GRASS_SHRUB.getMinecraftId(), (byte) type, flowerType);
    }

    protected TallGrassMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type, final FlowerTypeMat flowerType)
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
    public TallGrassMat getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public TallGrassMat getType(final int id)
    {
        return getByID(id);
    }

    @Override
    public TallGrassMat getFlowerType(final FlowerTypeMat flowerType)
    {
        return getTallGrass(flowerType);
    }

    /**
     * Returns one of TallGrass sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of TallGrass or null
     */
    public static TallGrassMat getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of TallGrass sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of TallGrass or null
     */
    public static TallGrassMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Returns one of TallGrass sub-type based on {@link FlowerTypeMat}
     * If this flower don't supprot given type, it will return default one.
     *
     * @param flowerType type of flower
     *
     * @return sub-type of TallGrass
     */
    public static TallGrassMat getTallGrass(final FlowerTypeMat flowerType)
    {
        for (final TallGrassMat mat : byName.values())
        {
            if (mat.flowerType == flowerType)
            {
                return mat;
            }
        }
        return TALL_GRASS_SHRUB;
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final TallGrassMat element)
    {
        byID.put(element.getType(), element);
        byName.put(element.name(), element);
    }

    static
    {
        TallGrassMat.register(TALL_GRASS_SHRUB);
        TallGrassMat.register(TALL_GRASS_GRASS);
        TallGrassMat.register(TALL_GRASS_FERN);
    }
}
