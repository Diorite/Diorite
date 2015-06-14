package org.diorite.material.blocks.redstone;

import java.util.Map;

import org.diorite.cfg.magic.MagicNumbers;
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
    /**
     * Blast resistance of block, can be changed only before server start.
     * Final copy of blast resistance from {@link MagicNumbers} class.
     */
    public static final float BLAST_RESISTANCE = MagicNumbers.MATERIAL__STONE_PRESSURE_PLATE__BLAST_RESISTANCE;
    /**
     * Hardness of block, can be changed only before server start.
     * Final copy of hardness from {@link MagicNumbers} class.
     */
    public static final float HARDNESS         = MagicNumbers.MATERIAL__STONE_PRESSURE_PLATE__HARDNESS;

    public static final StonePressurePlateMat STONE_PRESSURE_PLATE         = new StonePressurePlateMat();
    public static final StonePressurePlateMat STONE_PRESSURE_PLATE_POWERED = new StonePressurePlateMat(0x1, true);

    private static final Map<String, StonePressurePlateMat>    byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<StonePressurePlateMat> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    @SuppressWarnings("MagicNumber")
    protected StonePressurePlateMat()
    {
        super("STONE_PRESSURE_PLATE", 70, "minecraft:stone_pressure_plate", "STONE_PRESSURE_PLATE", (byte) 0x00, false);
    }

    protected StonePressurePlateMat(final int type, final boolean powered)
    {
        super(STONE_PRESSURE_PLATE.name(), STONE_PRESSURE_PLATE.ordinal(), STONE_PRESSURE_PLATE.getMinecraftId(), powered ? "POWERED" : "WOODEN_PRESSURE_PLATE", (byte) type, powered);
    }

    protected StonePressurePlateMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type, final boolean powered)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, powered);
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
        byName.put(element.name(), element);
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
