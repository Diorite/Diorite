package org.diorite.material.blocks.plants;

import java.util.Map;

import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.utils.collections.maps.CaseInsensitiveMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing block "DoubleFlowers" and all its subtypes.
 */
public class DoubleFlowersMat extends FlowerMat
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final byte  USED_DATA_VALUES = 7;
    /**
     * Blast resistance of block, can be changed only before server start.
     * Final copy of blast resistance from {@link MagicNumbers} class.
     */
    public static final float BLAST_RESISTANCE = MagicNumbers.MATERIAL__DOUBLE_FLOWERS__BLAST_RESISTANCE;
    /**
     * Hardness of block, can be changed only before server start.
     * Final copy of hardness from {@link MagicNumbers} class.
     */
    public static final float HARDNESS         = MagicNumbers.MATERIAL__DOUBLE_FLOWERS__HARDNESS;

    public static final DoubleFlowersMat DOUBLE_FLOWERS_SUNFLOWER  = new DoubleFlowersMat();
    public static final DoubleFlowersMat DOUBLE_FLOWERS_LILAC      = new DoubleFlowersMat(0x1, FlowerTypeMat.LILAC);
    public static final DoubleFlowersMat DOUBLE_FLOWERS_TALL_GRASS = new DoubleFlowersMat(0x2, FlowerTypeMat.TALL_GRASS);
    public static final DoubleFlowersMat DOUBLE_FLOWERS_TALL_FERN  = new DoubleFlowersMat(0x3, FlowerTypeMat.TALL_FERN);
    public static final DoubleFlowersMat DOUBLE_FLOWERS_ROSE_BUSH  = new DoubleFlowersMat(0x4, FlowerTypeMat.ROSE_BUSH);
    public static final DoubleFlowersMat DOUBLE_FLOWERS_PEONY      = new DoubleFlowersMat(0x5, FlowerTypeMat.PEONY);
    public static final DoubleFlowersMat DOUBLE_FLOWERS_DOUBLE_TOP = new DoubleFlowersMat(0x8, FlowerTypeMat.DOUBLE_TOP);

    private static final Map<String, DoubleFlowersMat>    byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<DoubleFlowersMat> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    @SuppressWarnings("MagicNumber")
    protected DoubleFlowersMat()
    {
        super("DOUBLE_FLOWERS", 175, "minecraft:double_plant", (byte) 0x00, FlowerTypeMat.SUNFLOWER);
    }

    protected DoubleFlowersMat(final int type, final FlowerTypeMat flowerType)
    {
        super(DOUBLE_FLOWERS_SUNFLOWER.name(), DOUBLE_FLOWERS_SUNFLOWER.getId(), DOUBLE_FLOWERS_SUNFLOWER.getMinecraftId(), (byte) type, flowerType);
    }

    protected DoubleFlowersMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type, final FlowerTypeMat flowerType)
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
    public DoubleFlowersMat getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public DoubleFlowersMat getType(final int id)
    {
        return getByID(id);
    }

    @Override
    public DoubleFlowersMat getFlowerType(final FlowerTypeMat flowerType)
    {
        return getDoubleFlowers(flowerType);
    }

    /**
     * Returns one of DoubleFlowers sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of DoubleFlowers or null
     */
    public static DoubleFlowersMat getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of DoubleFlowers sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of DoubleFlowers or null
     */
    public static DoubleFlowersMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Returns one of DoubleFlowers sub-type based on {@link FlowerTypeMat}
     * If this flower don't supprot given type, it will return default one.
     *
     * @param flowerType type of flower
     *
     * @return sub-type of DoubleFlowers
     */
    public static DoubleFlowersMat getDoubleFlowers(final FlowerTypeMat flowerType)
    {
        for (final DoubleFlowersMat mat : byName.values())
        {
            if (mat.flowerType == flowerType)
            {
                return mat;
            }
        }
        return DOUBLE_FLOWERS_SUNFLOWER;
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final DoubleFlowersMat element)
    {
        byID.put((byte) element.getType(), element);
        byName.put(element.name(), element);
    }

    static
    {
        DoubleFlowersMat.register(DOUBLE_FLOWERS_SUNFLOWER);
        DoubleFlowersMat.register(DOUBLE_FLOWERS_LILAC);
        DoubleFlowersMat.register(DOUBLE_FLOWERS_TALL_GRASS);
        DoubleFlowersMat.register(DOUBLE_FLOWERS_TALL_FERN);
        DoubleFlowersMat.register(DOUBLE_FLOWERS_ROSE_BUSH);
        DoubleFlowersMat.register(DOUBLE_FLOWERS_PEONY);
        DoubleFlowersMat.register(DOUBLE_FLOWERS_DOUBLE_TOP);
    }
}
