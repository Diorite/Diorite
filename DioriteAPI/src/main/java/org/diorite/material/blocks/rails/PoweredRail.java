package org.diorite.material.blocks.rails;

import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.material.blocks.Activatable;
import org.diorite.utils.collections.SimpleStringHashMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

public class PoweredRail extends Rails implements Activatable
{
    public static final byte  USED_DATA_VALUES = 12;
    public static final float BLAST_RESISTANCE = MagicNumbers.MATERIAL__POWERED_RAIL__BLAST_RESISTANCE;
    public static final float HARDNESS         = MagicNumbers.MATERIAL__POWERED_RAIL__HARDNESS;
    public static final byte  ACTIVE_FLAG      = 0x08;

    public static final PoweredRail POWERED_RAIL_NORTH_SOUTH     = new PoweredRail();
    public static final PoweredRail POWERED_RAIL_WEST_EAST       = new PoweredRail("WEST_EAST", RailType.FLAT_WEST_EAST, false);
    public static final PoweredRail POWERED_RAIL_ASCENDING_EAST  = new PoweredRail("ASCENDING_EAST", RailType.ASCENDING_EAST, false);
    public static final PoweredRail POWERED_RAIL_ASCENDING_WEST  = new PoweredRail("ASCENDING_WEST", RailType.ASCENDING_WEST, false);
    public static final PoweredRail POWERED_RAIL_ASCENDING_NORTH = new PoweredRail("ASCENDING_NORTH", RailType.ASCENDING_NORTH, false);
    public static final PoweredRail POWERED_RAIL_ASCENDING_SOUTH = new PoweredRail("ASCENDING_SOUTH", RailType.ASCENDING_SOUTH, false);

    public static final PoweredRail POWERED_RAIL_NORTH_SOUTH_ACTIVE     = new PoweredRail("NORTH_SOUTH_ACTIVE", RailType.FLAT_WEST_EAST, true);
    public static final PoweredRail POWERED_RAIL_WEST_EAST_ACTIVE       = new PoweredRail("WEST_EAST_ACTIVE", RailType.FLAT_WEST_EAST, true);
    public static final PoweredRail POWERED_RAIL_ASCENDING_EAST_ACTIVE  = new PoweredRail("ASCENDING_EAST_ACTIVE", RailType.ASCENDING_EAST, true);
    public static final PoweredRail POWERED_RAIL_ASCENDING_WEST_ACTIVE  = new PoweredRail("ASCENDING_WEST_ACTIVE", RailType.ASCENDING_WEST, true);
    public static final PoweredRail POWERED_RAIL_ASCENDING_NORTH_ACTIVE = new PoweredRail("ASCENDING_NORTH_ACTIVE", RailType.ASCENDING_NORTH, true);
    public static final PoweredRail POWERED_RAIL_ASCENDING_SOUTH_ACTIVE = new PoweredRail("ASCENDING_SOUTH_ACTIVE", RailType.ASCENDING_SOUTH, true);

    private static final Map<String, PoweredRail>    byName = new SimpleStringHashMap<>(USED_DATA_VALUES, SLOW_GROW);
    private static final TByteObjectMap<PoweredRail> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SLOW_GROW);

    protected final boolean active;

    @SuppressWarnings("MagicNumber")
    protected PoweredRail()
    {
        super("POWERED_RAIL", 27, "minecraft:golden_rail", "NORTH_SOUTH", RailType.FLAT_NORTH_SOUTH, (byte) 0x00);
        this.active = false;
    }

    public PoweredRail(final String enumName, final RailType type, final boolean active)
    {
        super(POWERED_RAIL_NORTH_SOUTH.name(), POWERED_RAIL_NORTH_SOUTH.getId(), POWERED_RAIL_NORTH_SOUTH.getMinecraftId(), enumName, type, active ? ACTIVE_FLAG : 0x00);
        this.active = active;
    }

    public PoweredRail(final int maxStack, final String typeName, final RailType type, final boolean active)
    {
        super(POWERED_RAIL_NORTH_SOUTH.name(), POWERED_RAIL_NORTH_SOUTH.getId(), POWERED_RAIL_NORTH_SOUTH.getMinecraftId(), maxStack, typeName, type, active ? ACTIVE_FLAG : 0x00);
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
    public PoweredRail getRailType(final RailType railType)
    {
        return getPoweredRail(railType, this.active);
    }

    @Override
    public PoweredRail getActivated(final boolean activate)
    {
        return getPoweredRail(this.railType, activate);
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

    public PoweredRail getType(final RailType railType, final boolean activate)
    {
        return getPoweredRail(railType, activate);
    }

    public static PoweredRail getByID(final int id)
    {
        return byID.get((byte) id);
    }

    public static PoweredRail getByEnumName(final String name)
    {
        return byName.get(name);
    }

    public static PoweredRail getPoweredRail(final RailType type, final boolean isActive)
    {
        return getByID(type.getFlag() | (isActive ? ACTIVE_FLAG : 0x00));
    }

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

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("active", this.active).toString();
    }
}
