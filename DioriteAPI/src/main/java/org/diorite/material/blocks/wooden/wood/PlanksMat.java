package org.diorite.material.blocks.wooden.wood;

import java.util.Map;

import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.material.blocks.wooden.WoodTypeMat;
import org.diorite.utils.collections.maps.CaseInsensitiveMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing block "Planks" and all its subtypes.
 */
public class PlanksMat extends WoodMat
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final byte  USED_DATA_VALUES = 6;
    /**
     * Blast resistance of block, can be changed only before server start.
     * Final copy of blast resistance from {@link MagicNumbers} class.
     */
    public static final float BLAST_RESISTANCE = MagicNumbers.MATERIAL__PLANKS__BLAST_RESISTANCE;
    /**
     * Hardness of block, can be changed only before server start.
     * Final copy of hardness from {@link MagicNumbers} class.
     */
    public static final float HARDNESS         = MagicNumbers.MATERIAL__PLANKS__HARDNESS;

    public static final PlanksMat PLANKS_OAK      = new PlanksMat();
    public static final PlanksMat PLANKS_SPRUCE   = new PlanksMat(WoodTypeMat.SPRUCE);
    public static final PlanksMat PLANKS_BIRCH    = new PlanksMat(WoodTypeMat.BIRCH);
    public static final PlanksMat PLANKS_JUNGLE   = new PlanksMat(WoodTypeMat.JUNGLE);
    public static final PlanksMat PLANKS_ACACIA   = new PlanksMat(WoodTypeMat.ACACIA);
    public static final PlanksMat PLANKS_DARK_OAK = new PlanksMat(WoodTypeMat.DARK_OAK);

    private static final Map<String, PlanksMat>    byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<PlanksMat> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    protected PlanksMat()
    {
        super("PLANKS", 5, "minecraft:planks", "QAK", WoodTypeMat.OAK.getPlanksMeta(), WoodTypeMat.OAK);
    }

    protected PlanksMat(final WoodTypeMat woodType)
    {
        super(PLANKS_OAK.name(), PLANKS_OAK.getId(), PLANKS_OAK.getMinecraftId(), woodType.name(), woodType.getPlanksMeta(), woodType);
    }

    protected PlanksMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type, final WoodTypeMat woodType)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, woodType);
    }

    @Override
    public PlanksMat getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public PlanksMat getType(final int id)
    {
        return getByID(id);
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
    public PlanksMat getWoodType(final WoodTypeMat woodType)
    {
        return getPlanks(woodType);
    }

    /**
     * Returns one of Planks sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of Planks or null
     */
    public static PlanksMat getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of Planks sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of Planks or null
     */
    public static PlanksMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    public static PlanksMat getPlanks(final WoodTypeMat type)
    {
        switch (type)
        {
            case OAK:
                return PLANKS_OAK;
            case SPRUCE:
                return PLANKS_SPRUCE;
            case BIRCH:
                return PLANKS_BIRCH;
            case JUNGLE:
                return PLANKS_JUNGLE;
            case ACACIA:
                return PLANKS_ACACIA;
            case DARK_OAK:
                return PLANKS_DARK_OAK;
            default:
                return PLANKS_OAK;
        }
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final PlanksMat element)
    {
        byID.put((byte) element.getType(), element);
        byName.put(element.name(), element);
    }

    static
    {
        PlanksMat.register(PLANKS_OAK);
        PlanksMat.register(PLANKS_SPRUCE);
        PlanksMat.register(PLANKS_BIRCH);
        PlanksMat.register(PLANKS_JUNGLE);
        PlanksMat.register(PLANKS_ACACIA);
        PlanksMat.register(PLANKS_DARK_OAK);
    }
}
