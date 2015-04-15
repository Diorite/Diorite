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
 * Class representing block "ActivatorRail" and all its subtypes.
 */
public class ActivatorRail extends Rails implements Activatable
{	
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final byte  USED_DATA_VALUES = 12;	
    /**
     * Blast resistance of block, can be changed only before server start.
     * Final copy of blast resistance from {@link MagicNumbers} class.
     */
    public static final float BLAST_RESISTANCE = MagicNumbers.MATERIAL__ACTIVATOR_RAIL__BLAST_RESISTANCE;	
    /**
     * Hardness of block, can be changed only before server start.
     * Final copy of hardness from {@link MagicNumbers} class.
     */
    public static final float HARDNESS         = MagicNumbers.MATERIAL__ACTIVATOR_RAIL__HARDNESS;
    public static final byte  ACTIVE_FLAG      = 0x08;

    public static final ActivatorRail ACTIVATOR_RAIL_NORTH_SOUTH     = new ActivatorRail();
    public static final ActivatorRail ACTIVATOR_RAIL_WEST_EAST       = new ActivatorRail("WEST_EAST", RailType.FLAT_WEST_EAST, false);
    public static final ActivatorRail ACTIVATOR_RAIL_ASCENDING_EAST  = new ActivatorRail("ASCENDING_EAST", RailType.ASCENDING_EAST, false);
    public static final ActivatorRail ACTIVATOR_RAIL_ASCENDING_WEST  = new ActivatorRail("ASCENDING_WEST", RailType.ASCENDING_WEST, false);
    public static final ActivatorRail ACTIVATOR_RAIL_ASCENDING_NORTH = new ActivatorRail("ASCENDING_NORTH", RailType.ASCENDING_NORTH, false);
    public static final ActivatorRail ACTIVATOR_RAIL_ASCENDING_SOUTH = new ActivatorRail("ASCENDING_SOUTH", RailType.ASCENDING_SOUTH, false);

    public static final ActivatorRail ACTIVATOR_RAIL_NORTH_SOUTH_ACTIVE     = new ActivatorRail("NORTH_SOUTH_ACTIVE", RailType.FLAT_WEST_EAST, true);
    public static final ActivatorRail ACTIVATOR_RAIL_WEST_EAST_ACTIVE       = new ActivatorRail("WEST_EAST_ACTIVE", RailType.FLAT_WEST_EAST, true);
    public static final ActivatorRail ACTIVATOR_RAIL_ASCENDING_EAST_ACTIVE  = new ActivatorRail("ASCENDING_EAST_ACTIVE", RailType.ASCENDING_EAST, true);
    public static final ActivatorRail ACTIVATOR_RAIL_ASCENDING_WEST_ACTIVE  = new ActivatorRail("ASCENDING_WEST_ACTIVE", RailType.ASCENDING_WEST, true);
    public static final ActivatorRail ACTIVATOR_RAIL_ASCENDING_NORTH_ACTIVE = new ActivatorRail("ASCENDING_NORTH_ACTIVE", RailType.ASCENDING_NORTH, true);
    public static final ActivatorRail ACTIVATOR_RAIL_ASCENDING_SOUTH_ACTIVE = new ActivatorRail("ASCENDING_SOUTH_ACTIVE", RailType.ASCENDING_SOUTH, true);

    private static final Map<String, ActivatorRail>    byName = new SimpleStringHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<ActivatorRail> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    protected final boolean active;

    @SuppressWarnings("MagicNumber")
    protected ActivatorRail()
    {
        super("ACTIVATOR_RAIL", 157, "minecraft:activator_rail", "NORTH_SOUTH", RailType.FLAT_NORTH_SOUTH, (byte) 0x00);
        this.active = false;
    }

    public ActivatorRail(final String enumName, final RailType type, final boolean active)
    {
        super(ACTIVATOR_RAIL_NORTH_SOUTH.name(), ACTIVATOR_RAIL_NORTH_SOUTH.getId(), ACTIVATOR_RAIL_NORTH_SOUTH.getMinecraftId(), enumName, type, active ? ACTIVE_FLAG : 0x00);
        this.active = active;
    }

    public ActivatorRail(final int maxStack, final String typeName, final RailType type, final boolean active)
    {
        super(ACTIVATOR_RAIL_NORTH_SOUTH.name(), ACTIVATOR_RAIL_NORTH_SOUTH.getId(), ACTIVATOR_RAIL_NORTH_SOUTH.getMinecraftId(), maxStack, typeName, type, active ? ACTIVE_FLAG : 0x00);
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
    public ActivatorRail getRailType(final RailType railType)
    {
        return getPoweredRail(railType, this.active);
    }

    @Override
    public ActivatorRail getActivated(final boolean activate)
    {
        return getPoweredRail(this.railType, activate);
    }

    @Override
    public ActivatorRail getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public ActivatorRail getType(final int id)
    {
        return getByID(id);
    }

    public ActivatorRail getType(final RailType railType, final boolean activate)
    {
        return getPoweredRail(railType, activate);
    }

    /**
     * Returns one of ActivatorRail sub-type based on sub-id, may return null
     * @param id sub-type id
     * @return sub-type of ActivatorRail or null
     */
    public static ActivatorRail getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of ActivatorRail sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     * @param name name of sub-type
     * @return sub-type of ActivatorRail or null
     */
    public static ActivatorRail getByEnumName(final String name)
    {
        return byName.get(name);
    }

    public static ActivatorRail getPoweredRail(final RailType type, final boolean isActive)
    {
        return getByID(type.getFlag() | (isActive ? ACTIVE_FLAG : 0x00));
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     * @param element sub-type to register
     */
    public static void register(final ActivatorRail element)
    {
        byID.put(element.getType(), element);
        byName.put(element.name(), element);
    }

    static
    {
        ActivatorRail.register(ACTIVATOR_RAIL_NORTH_SOUTH);
        ActivatorRail.register(ACTIVATOR_RAIL_WEST_EAST);
        ActivatorRail.register(ACTIVATOR_RAIL_ASCENDING_EAST);
        ActivatorRail.register(ACTIVATOR_RAIL_ASCENDING_WEST);
        ActivatorRail.register(ACTIVATOR_RAIL_ASCENDING_NORTH);
        ActivatorRail.register(ACTIVATOR_RAIL_ASCENDING_SOUTH);
        ActivatorRail.register(ACTIVATOR_RAIL_NORTH_SOUTH_ACTIVE);
        ActivatorRail.register(ACTIVATOR_RAIL_WEST_EAST_ACTIVE);
        ActivatorRail.register(ACTIVATOR_RAIL_ASCENDING_EAST_ACTIVE);
        ActivatorRail.register(ACTIVATOR_RAIL_ASCENDING_WEST_ACTIVE);
        ActivatorRail.register(ACTIVATOR_RAIL_ASCENDING_NORTH_ACTIVE);
        ActivatorRail.register(ACTIVATOR_RAIL_ASCENDING_SOUTH_ACTIVE);
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("active", this.active).toString();
    }
}
