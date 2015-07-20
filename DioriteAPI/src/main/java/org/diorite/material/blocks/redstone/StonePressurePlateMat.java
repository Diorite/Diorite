package org.diorite.material.blocks.redstone;

import java.util.Map;

import org.diorite.material.Material;
import org.diorite.utils.collections.maps.CaseInsensitiveMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing block "StonePressurePlate" and all its subtypes.
 */
public class StonePressurePlateMat extends PressurePlateMat
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final byte  USED_DATA_VALUES = 2;

    public static final StonePressurePlateMat STONE_PRESSURE_PLATE         = new StonePressurePlateMat();
    public static final StonePressurePlateMat STONE_PRESSURE_PLATE_POWERED = new StonePressurePlateMat(0x1, true);

    private static final Map<String, StonePressurePlateMat>    byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<StonePressurePlateMat> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    @SuppressWarnings("MagicNumber")
    protected StonePressurePlateMat()
    {
        super("STONE_PRESSURE_PLATE", 70, "minecraft:stone_pressure_plate", "STONE_PRESSURE_PLATE", (byte) 0x00, false, 0.5f, 2.5f);
    }

    protected StonePressurePlateMat(final int type, final boolean powered)
    {
        super(STONE_PRESSURE_PLATE.name(), STONE_PRESSURE_PLATE.ordinal(), STONE_PRESSURE_PLATE.getMinecraftId(), powered ? "POWERED" : "WOODEN_PRESSURE_PLATE", (byte) type, powered, STONE_PRESSURE_PLATE.getHardness(), STONE_PRESSURE_PLATE.getBlastResistance());
    }

    protected StonePressurePlateMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type, final boolean powered, final float hardness, final float blastResistance)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, powered, hardness, blastResistance);
    }

    @Override
    public Material ensureValidInventoryItem()
    {
        return Material.STONE_PRESSURE_PLATE;
    }

    @Override
    public StonePressurePlateMat getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public StonePressurePlateMat getType(final int id)
    {
        return getByID(id);
    }

    @Override
    public StonePressurePlateMat getPowered(final boolean powered)
    {
        return getByID(powered ? 1 : 0);
    }

    /**
     * Returns one of StonePressurePlate sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of StonePressurePlate or null
     */
    public static StonePressurePlateMat getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of StonePressurePlate sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of StonePressurePlate or null
     */
    public static StonePressurePlateMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Returns one of StonePressurePlate sub-type based on powered state.
     * It will never return null.
     *
     * @param powered if pressure player should be powered.
     *
     * @return sub-type of StonePressurePlate
     */
    public static StonePressurePlateMat getStonePressurePlate(final boolean powered)
    {
        return getByID(powered ? 1 : 0);
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final StonePressurePlateMat element)
    {
        byID.put((byte) element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    @Override
    public StonePressurePlateMat[] types()
    {
        return StonePressurePlateMat.stonePressurePlateTypes();
    }

    /**
     * @return array that contains all sub-types of this block.
     */
    public static StonePressurePlateMat[] stonePressurePlateTypes()
    {
        return byID.values(new StonePressurePlateMat[byID.size()]);
    }

    static
    {
        StonePressurePlateMat.register(STONE_PRESSURE_PLATE);
        StonePressurePlateMat.register(STONE_PRESSURE_PLATE_POWERED);
    }
}
