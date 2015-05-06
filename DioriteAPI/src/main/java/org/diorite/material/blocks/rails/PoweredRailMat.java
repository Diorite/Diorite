package org.diorite.material.blocks.rails;

import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.material.blocks.PowerableMat;
import org.diorite.utils.collections.SimpleStringHashMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing block "PoweredRail" and all its subtypes.
 */
public class PoweredRailMat extends RailsMat implements PowerableMat
{
    /**
     * Bit flag defining if rail is powered.
     * If bit is set to 0, then it isn't powered
     */
    public static final byte  POWERED_FLAG     = 0x8;
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final byte  USED_DATA_VALUES = 12;
    /**
     * Blast resistance of block, can be changed only before server start.
     * Final copy of blast resistance from {@link MagicNumbers} class.
     */
    public static final float BLAST_RESISTANCE = MagicNumbers.MATERIAL__POWERED_RAIL__BLAST_RESISTANCE;
    /**
     * Hardness of block, can be changed only before server start.
     * Final copy of hardness from {@link MagicNumbers} class.
     */
    public static final float HARDNESS         = MagicNumbers.MATERIAL__POWERED_RAIL__HARDNESS;

    public static final PoweredRailMat POWERED_RAIL_NORTH_SOUTH     = new PoweredRailMat();
    public static final PoweredRailMat POWERED_RAIL_WEST_EAST       = new PoweredRailMat(RailTypeMat.FLAT_WEST_EAST, false);
    public static final PoweredRailMat POWERED_RAIL_ASCENDING_EAST  = new PoweredRailMat(RailTypeMat.ASCENDING_EAST, false);
    public static final PoweredRailMat POWERED_RAIL_ASCENDING_WEST  = new PoweredRailMat(RailTypeMat.ASCENDING_WEST, false);
    public static final PoweredRailMat POWERED_RAIL_ASCENDING_NORTH = new PoweredRailMat(RailTypeMat.ASCENDING_NORTH, false);
    public static final PoweredRailMat POWERED_RAIL_ASCENDING_SOUTH = new PoweredRailMat(RailTypeMat.ASCENDING_SOUTH, false);

    public static final PoweredRailMat POWERED_RAIL_NORTH_SOUTH_POWERED     = new PoweredRailMat(RailTypeMat.FLAT_NORTH_SOUTH, true);
    public static final PoweredRailMat POWERED_RAIL_WEST_EAST_POWERED       = new PoweredRailMat(RailTypeMat.FLAT_WEST_EAST, true);
    public static final PoweredRailMat POWERED_RAIL_ASCENDING_EAST_POWERED  = new PoweredRailMat(RailTypeMat.ASCENDING_EAST, true);
    public static final PoweredRailMat POWERED_RAIL_ASCENDING_WEST_POWERED  = new PoweredRailMat(RailTypeMat.ASCENDING_WEST, true);
    public static final PoweredRailMat POWERED_RAIL_ASCENDING_NORTH_POWERED = new PoweredRailMat(RailTypeMat.ASCENDING_NORTH, true);
    public static final PoweredRailMat POWERED_RAIL_ASCENDING_SOUTH_POWERED = new PoweredRailMat(RailTypeMat.ASCENDING_SOUTH, true);

    private static final Map<String, PoweredRailMat>    byName = new SimpleStringHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<PoweredRailMat> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    protected final boolean powered;

    @SuppressWarnings("MagicNumber")
    protected PoweredRailMat()
    {
        super("POWERED_RAIL", 27, "minecraft:golden_rail", "FLAT_NORTH_SOUTH", RailTypeMat.FLAT_NORTH_SOUTH, (byte) 0x00);
        this.powered = false;
    }

    protected PoweredRailMat(final RailTypeMat type, final boolean powered)
    {
        super(POWERED_RAIL_NORTH_SOUTH.name(), POWERED_RAIL_NORTH_SOUTH.getId(), POWERED_RAIL_NORTH_SOUTH.getMinecraftId(), type.name() + (powered ? "_POWERED" : ""), type, powered ? POWERED_FLAG : 0x00);
        this.powered = powered;
    }

    protected PoweredRailMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type, final RailTypeMat railType, final boolean powered)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, railType);
        this.powered = powered;
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
    public boolean isPowered()
    {
        return this.powered;
    }

    @Override
    public PoweredRailMat getPowered(final boolean powered)
    {
        return getPoweredRail(this.railType, powered);
    }

    @Override
    public PoweredRailMat getRailType(final RailTypeMat railType)
    {
        return getPoweredRail(railType, this.powered);
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("powered", this.powered).toString();
    }

    @Override
    public PoweredRailMat getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public PoweredRailMat getType(final int id)
    {
        return getByID(id);
    }

    /**
     * Returns sub-type of PoweredRail based on {@link RailTypeMat} and powered state.
     * It will never return null.
     *
     * @param railType type of rails
     * @param powered  if rails should be powered.
     *
     * @return sub-type of PoweredRail
     */
    public PoweredRailMat getType(final RailTypeMat railType, final boolean powered)
    {
        return getPoweredRail(railType, powered);
    }

    /**
     * Returns one of PoweredRail sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of PoweredRail or null
     */
    public static PoweredRailMat getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of PoweredRail sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of PoweredRail or null
     */
    public static PoweredRailMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Returns sub-type of PoweredRail based on {@link RailTypeMat} and powered state.
     * It will never return null.
     *
     * @param railType type of rails
     * @param powered  if rails should be powered.
     *
     * @return sub-type of PoweredRail
     */
    public static PoweredRailMat getPoweredRail(final RailTypeMat type, final boolean isPowered)
    {
        byte flag = type.getFlag();
        if (flag >= POWERED_FLAG)
        {
            flag = RailTypeMat.FLAT_NORTH_SOUTH.getFlag();
        }
        return getByID(flag | (isPowered ? POWERED_FLAG : 0x00));
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final PoweredRailMat element)
    {
        byID.put((byte) element.getType(), element);
        byName.put(element.name(), element);
    }

    static
    {
        PoweredRailMat.register(POWERED_RAIL_NORTH_SOUTH);
        PoweredRailMat.register(POWERED_RAIL_WEST_EAST);
        PoweredRailMat.register(POWERED_RAIL_ASCENDING_EAST);
        PoweredRailMat.register(POWERED_RAIL_ASCENDING_WEST);
        PoweredRailMat.register(POWERED_RAIL_ASCENDING_NORTH);
        PoweredRailMat.register(POWERED_RAIL_ASCENDING_SOUTH);
        PoweredRailMat.register(POWERED_RAIL_NORTH_SOUTH_POWERED);
        PoweredRailMat.register(POWERED_RAIL_WEST_EAST_POWERED);
        PoweredRailMat.register(POWERED_RAIL_ASCENDING_EAST_POWERED);
        PoweredRailMat.register(POWERED_RAIL_ASCENDING_WEST_POWERED);
        PoweredRailMat.register(POWERED_RAIL_ASCENDING_NORTH_POWERED);
        PoweredRailMat.register(POWERED_RAIL_ASCENDING_SOUTH_POWERED);
    }
}
