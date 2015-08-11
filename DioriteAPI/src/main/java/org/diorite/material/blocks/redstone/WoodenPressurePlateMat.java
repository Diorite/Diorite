package org.diorite.material.blocks.redstone;

import java.util.Map;

import org.diorite.material.FuelMat;
import org.diorite.material.Material;
import org.diorite.utils.collections.maps.CaseInsensitiveMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing block "WoodenPressurePlate" and all its subtypes.
 */
public class WoodenPressurePlateMat extends PressurePlateMat implements FuelMat
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final int USED_DATA_VALUES = 2;

    public static final WoodenPressurePlateMat WOODEN_PRESSURE_PLATE         = new WoodenPressurePlateMat();
    public static final WoodenPressurePlateMat WOODEN_PRESSURE_PLATE_POWERED = new WoodenPressurePlateMat(0x1, true);

    private static final Map<String, WoodenPressurePlateMat>    byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<WoodenPressurePlateMat> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR, Byte.MIN_VALUE);

    @SuppressWarnings("MagicNumber")
    protected WoodenPressurePlateMat()
    {
        super("WOODEN_PRESSURE_PLATE", 72, "minecraft:wooden_pressure_plate", "WOODEN_PRESSURE_PLATE", (byte) 0x00, false, 0.5f, 2.5f);
    }

    protected WoodenPressurePlateMat(final int type, final boolean powered)
    {
        super(WOODEN_PRESSURE_PLATE.name(), WOODEN_PRESSURE_PLATE.ordinal(), WOODEN_PRESSURE_PLATE.getMinecraftId(), powered ? "POWERED" : "WOODEN_PRESSURE_PLATE", (byte) type, powered, WOODEN_PRESSURE_PLATE.getHardness(), WOODEN_PRESSURE_PLATE.getBlastResistance());
    }

    protected WoodenPressurePlateMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type, final boolean powered, final float hardness, final float blastResistance)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, powered, hardness, blastResistance);
    }

    @Override
    public Material ensureValidInventoryItem()
    {
        return Material.WOODEN_PRESSURE_PLATE;
    }

    @Override
    public WoodenPressurePlateMat getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public WoodenPressurePlateMat getType(final int id)
    {
        return getByID(id);
    }

    @Override
    public WoodenPressurePlateMat getPowered(final boolean powered)
    {
        return getByID(powered ? 1 : 0);
    }

    @SuppressWarnings("MagicNumber")
    @Override
    public int getFuelPower()
    {
        return 1500;
    }

    /**
     * Returns one of WoodenPressurePlate sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of WoodenPressurePlate or null
     */
    public static WoodenPressurePlateMat getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of WoodenPressurePlate sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of WoodenPressurePlate or null
     */
    public static WoodenPressurePlateMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Returns one of WoodenPressurePlate sub-type based on powered state.
     * It will never return null.
     *
     * @param powered if pressure player should be powered.
     *
     * @return sub-type of WoodenPressurePlate
     */
    public static WoodenPressurePlateMat getWoodenPressurePlate(final boolean powered)
    {
        return getByID(powered ? 1 : 0);
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final WoodenPressurePlateMat element)
    {
        byID.put((byte) element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    @Override
    public WoodenPressurePlateMat[] types()
    {
        return WoodenPressurePlateMat.woodenPressurePlateTypes();
    }

    /**
     * @return array that contains all sub-types of this block.
     */
    public static WoodenPressurePlateMat[] woodenPressurePlateTypes()
    {
        return byID.values(new WoodenPressurePlateMat[byID.size()]);
    }

    static
    {
        WoodenPressurePlateMat.register(WOODEN_PRESSURE_PLATE);
        WoodenPressurePlateMat.register(WOODEN_PRESSURE_PLATE_POWERED);
    }
}
