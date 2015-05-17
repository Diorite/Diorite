package org.diorite.material.blocks.plants;

import java.util.Map;

import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.utils.collections.maps.CaseInsensitiveMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing block "Flowers" and all its subtypes.
 */
public class FlowersMat extends FlowerMat
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final byte  USED_DATA_VALUES = 9;
    /**
     * Blast resistance of block, can be changed only before server start.
     * Final copy of blast resistance from {@link MagicNumbers} class.
     */
    public static final float BLAST_RESISTANCE = MagicNumbers.MATERIAL__FLOWERS__BLAST_RESISTANCE;
    /**
     * Hardness of block, can be changed only before server start.
     * Final copy of hardness from {@link MagicNumbers} class.
     */
    public static final float HARDNESS         = MagicNumbers.MATERIAL__FLOWERS__HARDNESS;

    public static final FlowersMat FLOWERS_POPPY        = new FlowersMat();
    public static final FlowersMat FLOWERS_BLUE_ORCHID  = new FlowersMat(0x1, FlowerTypeMat.BLUE_ORCHID);
    public static final FlowersMat FLOWERS_ALLIUM       = new FlowersMat(0x2, FlowerTypeMat.ALLIUM);
    public static final FlowersMat FLOWERS_AZURE_BLUET  = new FlowersMat(0x3, FlowerTypeMat.AZURE_BLUET);
    public static final FlowersMat FLOWERS_RED_TULIP    = new FlowersMat(0x4, FlowerTypeMat.RED_TULIP);
    public static final FlowersMat FLOWERS_ORANGE_TULIP = new FlowersMat(0x5, FlowerTypeMat.ORANGE_TULIP);
    public static final FlowersMat FLOWERS_WHITE_TULIP  = new FlowersMat(0x6, FlowerTypeMat.WHITE_TULIP);
    public static final FlowersMat FLOWERS_PINK_TULIP   = new FlowersMat(0x7, FlowerTypeMat.PINK_TULIP);
    public static final FlowersMat FLOWERS_OXEYE_DAISY  = new FlowersMat(0x8, FlowerTypeMat.OXEYE_DAISY);

    private static final Map<String, FlowersMat>    byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<FlowersMat> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    @SuppressWarnings("MagicNumber")
    protected FlowersMat()
    {
        super("FLOWERS", 38, "minecraft:red_flower", (byte) 0x00, FlowerTypeMat.POPPY);
    }

    protected FlowersMat(final int type, final FlowerTypeMat flowerType)
    {
        super(FLOWERS_POPPY.name(), FLOWERS_POPPY.getId(), FLOWERS_POPPY.getMinecraftId(), (byte) type, flowerType);
    }

    protected FlowersMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type, final FlowerTypeMat flowerType)
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
    public FlowersMat getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public FlowersMat getType(final int id)
    {
        return getByID(id);
    }

    @Override
    public FlowersMat getFlowerType(final FlowerTypeMat flowerType)
    {
        return getFlowers(flowerType);
    }

    /**
     * Returns one of Flowers sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of Flowers or null
     */
    public static FlowersMat getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of Flowers sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of Flowers or null
     */
    public static FlowersMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Returns one of Flowers sub-type based on {@link FlowerTypeMat}
     * If this flower don't supprot given type, it will return default one.
     *
     * @param flowerType type of flower
     *
     * @return sub-type of Flowers
     */
    public static FlowersMat getFlowers(final FlowerTypeMat flowerType)
    {
        for (final FlowersMat mat : byName.values())
        {
            if (mat.flowerType == flowerType)
            {
                return mat;
            }
        }
        return FLOWERS_POPPY;
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final FlowersMat element)
    {
        byID.put((byte) element.getType(), element);
        byName.put(element.name(), element);
    }

    static
    {
        FlowersMat.register(FLOWERS_POPPY);
        FlowersMat.register(FLOWERS_BLUE_ORCHID);
        FlowersMat.register(FLOWERS_ALLIUM);
        FlowersMat.register(FLOWERS_AZURE_BLUET);
        FlowersMat.register(FLOWERS_RED_TULIP);
        FlowersMat.register(FLOWERS_ORANGE_TULIP);
        FlowersMat.register(FLOWERS_WHITE_TULIP);
        FlowersMat.register(FLOWERS_PINK_TULIP);
        FlowersMat.register(FLOWERS_OXEYE_DAISY);
    }
}
