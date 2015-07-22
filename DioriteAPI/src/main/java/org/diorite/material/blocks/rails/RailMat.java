package org.diorite.material.blocks.rails;

import java.util.Map;

import org.diorite.material.Material;
import org.diorite.utils.collections.maps.CaseInsensitiveMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing block "Rail" and all its subtypes.
 */
public class RailMat extends RailsMat
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final int USED_DATA_VALUES = 10;

    public static final RailMat RAIL_FLAT_NORTH_SOUTH  = new RailMat();
    public static final RailMat RAIL_FLAT_WEST_EAST    = new RailMat(RailTypeMat.FLAT_WEST_EAST);
    public static final RailMat RAIL_ASCENDING_EAST    = new RailMat(RailTypeMat.ASCENDING_EAST);
    public static final RailMat RAIL_ASCENDING_WEST    = new RailMat(RailTypeMat.ASCENDING_WEST);
    public static final RailMat RAIL_ASCENDING_NORTH   = new RailMat(RailTypeMat.ASCENDING_NORTH);
    public static final RailMat RAIL_ASCENDING_SOUTH   = new RailMat(RailTypeMat.ASCENDING_SOUTH);
    public static final RailMat RAIL_CURVED_SOUTH_EAST = new RailMat(RailTypeMat.CURVED_SOUTH_EAST);
    public static final RailMat RAIL_CURVED_SOUTH_WEST = new RailMat(RailTypeMat.CURVED_SOUTH_WEST);
    public static final RailMat RAIL_CURVED_NORTH_WEST = new RailMat(RailTypeMat.CURVED_NORTH_WEST);
    public static final RailMat RAIL_CURVED_NORTH_EAST = new RailMat(RailTypeMat.CURVED_NORTH_EAST);

    private static final Map<String, RailMat>    byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<RailMat> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR, Byte.MIN_VALUE);

    @SuppressWarnings("MagicNumber")
    protected RailMat()
    {
        super("RAIL", 66, "minecraft:rail", "FLAT_NORTH_SOUTH", RailTypeMat.FLAT_NORTH_SOUTH, (byte) 0x00, 0.7f, 3.5f);
    }

    protected RailMat(final RailTypeMat type)
    {
        super(RAIL_FLAT_NORTH_SOUTH.name(), RAIL_FLAT_NORTH_SOUTH.ordinal(), RAIL_FLAT_NORTH_SOUTH.getMinecraftId(), type.name(), type, (byte) 0x0, RAIL_FLAT_NORTH_SOUTH.getHardness(), RAIL_FLAT_NORTH_SOUTH.getBlastResistance());
    }

    protected RailMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type, final RailTypeMat railType, final float hardness, final float blastResistance)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, railType, hardness, blastResistance);
    }

    @Override
    public Material ensureValidInventoryItem()
    {
        return Material.RAIL;
    }

    @Override
    public RailMat getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public RailMat getType(final int id)
    {
        return getByID(id);
    }

    @Override
    public RailMat getRailType(final RailTypeMat railType)
    {
        return getByID(railType.getFlag());
    }

    /**
     * Returns one of Rail sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of Rail or null
     */
    public static RailMat getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of Rail sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of Rail or null
     */
    public static RailMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Returns one of Rail sub-type based on {@link RailTypeMat}.
     * It will never return null.
     *
     * @param railType {@link RailTypeMat} of Rail.
     *
     * @return sub-type of Rail
     */
    public static RailMat getRail(final RailTypeMat railType)
    {
        return getByID(railType.getFlag());
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final RailMat element)
    {
        byID.put((byte) element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    @Override
    public RailMat[] types()
    {
        return RailMat.railTypes();
    }

    /**
     * @return array that contains all sub-types of this block.
     */
    public static RailMat[] railTypes()
    {
        return byID.values(new RailMat[byID.size()]);
    }

    static
    {
        RailMat.register(RAIL_FLAT_NORTH_SOUTH);
        RailMat.register(RAIL_FLAT_WEST_EAST);
        RailMat.register(RAIL_ASCENDING_EAST);
        RailMat.register(RAIL_ASCENDING_WEST);
        RailMat.register(RAIL_ASCENDING_NORTH);
        RailMat.register(RAIL_ASCENDING_SOUTH);
        RailMat.register(RAIL_CURVED_SOUTH_EAST);
        RailMat.register(RAIL_CURVED_SOUTH_WEST);
        RailMat.register(RAIL_CURVED_NORTH_WEST);
        RailMat.register(RAIL_CURVED_NORTH_EAST);
    }
}
