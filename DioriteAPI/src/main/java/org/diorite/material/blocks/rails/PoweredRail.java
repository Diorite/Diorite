package org.diorite.material.blocks.rails;

import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.material.blocks.Activatable;
import org.diorite.utils.collections.SimpleStringHashMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing block "PoweredRail" and all its subtypes.
 */
public class PoweredRail extends Rails implements Activatable
{
    /**
     * Bit flag defining if rail is active.
     * If bit is set to 0, then it isn't active
     */
    public static final byte  ACTIVE_FLAG      = 0x8;
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

    public static final PoweredRail POWERED_RAIL_NORTH_SOUTH     = new PoweredRail();
    public static final PoweredRail POWERED_RAIL_WEST_EAST       = new PoweredRail(RailType.FLAT_WEST_EAST, false);
    public static final PoweredRail POWERED_RAIL_ASCENDING_EAST  = new PoweredRail(RailType.ASCENDING_EAST, false);
    public static final PoweredRail POWERED_RAIL_ASCENDING_WEST  = new PoweredRail(RailType.ASCENDING_WEST, false);
    public static final PoweredRail POWERED_RAIL_ASCENDING_NORTH = new PoweredRail(RailType.ASCENDING_NORTH, false);
    public static final PoweredRail POWERED_RAIL_ASCENDING_SOUTH = new PoweredRail(RailType.ASCENDING_SOUTH, false);

    public static final PoweredRail POWERED_RAIL_NORTH_SOUTH_ACTIVE     = new PoweredRail(RailType.FLAT_NORTH_SOUTH, true);
    public static final PoweredRail POWERED_RAIL_WEST_EAST_ACTIVE       = new PoweredRail(RailType.FLAT_WEST_EAST, true);
    public static final PoweredRail POWERED_RAIL_ASCENDING_EAST_ACTIVE  = new PoweredRail(RailType.ASCENDING_EAST, true);
    public static final PoweredRail POWERED_RAIL_ASCENDING_WEST_ACTIVE  = new PoweredRail(RailType.ASCENDING_WEST, true);
    public static final PoweredRail POWERED_RAIL_ASCENDING_NORTH_ACTIVE = new PoweredRail(RailType.ASCENDING_NORTH, true);
    public static final PoweredRail POWERED_RAIL_ASCENDING_SOUTH_ACTIVE = new PoweredRail(RailType.ASCENDING_SOUTH, true);

    private static final Map<String, PoweredRail>    byName = new SimpleStringHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<PoweredRail> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    protected final boolean active;

    @SuppressWarnings("MagicNumber")
    protected PoweredRail()
    {
        super("POWERED_RAIL", 27, "minecraft:golden_rail", "FLAT_NORTH_SOUTH", RailType.FLAT_NORTH_SOUTH, (byte) 0x00);
        this.active = false;
    }

    public PoweredRail(final RailType type, final boolean active)
    {
        super(POWERED_RAIL_NORTH_SOUTH.name(), POWERED_RAIL_NORTH_SOUTH.getId(), POWERED_RAIL_NORTH_SOUTH.getMinecraftId(), type.name() + (active ? "_ACTIVE" : ""), type, active ? ACTIVE_FLAG : 0x00);
        this.active = active;
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
    public boolean isActivated()
    {
        return this.active;
    }

    @Override
    public PoweredRail getActivated(final boolean activate)
    {
        return getPoweredRail(this.railType, activate);
    }

    @Override
    public PoweredRail getRailType(final RailType railType)
    {
        return getPoweredRail(railType, this.active);
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("active", this.active).toString();
    }

    @Override
    public PoweredRail getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public PoweredRail getType(final int id)
    {
        return getByID(id);
    }

    /**
     * Returns sub-type of PoweredRail based on {@link RailType} and activate state.
     * It will never return null.
     *
     * @param railType type of rails
     * @param activate if rails should be activated.
     *
     * @return sub-type of PoweredRail
     */
    public PoweredRail getType(final RailType railType, final boolean activate)
    {
        return getPoweredRail(railType, activate);
    }

    /**
     * Returns one of PoweredRail sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of PoweredRail or null
     */
    public static PoweredRail getByID(final int id)
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
    public static PoweredRail getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Returns sub-type of PoweredRail based on {@link RailType} and activate state.
     * It will never return null.
     *
     * @param railType type of rails
     * @param activate if rails should be activated.
     *
     * @return sub-type of PoweredRail
     */
    public static PoweredRail getPoweredRail(final RailType type, final boolean isActive)
    {
        byte flag = type.getFlag();
        if (flag >= ACTIVE_FLAG)
        {
            flag = RailType.FLAT_NORTH_SOUTH.getFlag();
        }
        return getByID(flag | (isActive ? ACTIVE_FLAG : 0x00));
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final PoweredRail element)
    {
        byID.put(element.getType(), element);
        byName.put(element.name(), element);
    }

    static
    {
        PoweredRail.register(POWERED_RAIL_NORTH_SOUTH);
        PoweredRail.register(POWERED_RAIL_WEST_EAST);
        PoweredRail.register(POWERED_RAIL_ASCENDING_EAST);
        PoweredRail.register(POWERED_RAIL_ASCENDING_WEST);
        PoweredRail.register(POWERED_RAIL_ASCENDING_NORTH);
        PoweredRail.register(POWERED_RAIL_ASCENDING_SOUTH);
        PoweredRail.register(POWERED_RAIL_NORTH_SOUTH_ACTIVE);
        PoweredRail.register(POWERED_RAIL_WEST_EAST_ACTIVE);
        PoweredRail.register(POWERED_RAIL_ASCENDING_EAST_ACTIVE);
        PoweredRail.register(POWERED_RAIL_ASCENDING_WEST_ACTIVE);
        PoweredRail.register(POWERED_RAIL_ASCENDING_NORTH_ACTIVE);
        PoweredRail.register(POWERED_RAIL_ASCENDING_SOUTH_ACTIVE);
    }
}
