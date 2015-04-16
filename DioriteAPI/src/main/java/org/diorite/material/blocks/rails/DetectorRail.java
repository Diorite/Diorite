package org.diorite.material.blocks.rails;

import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.material.blocks.Powerable;
import org.diorite.utils.collections.SimpleStringHashMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing block "DetectorRail" and all its subtypes.
 */
public class DetectorRail extends Rails implements Powerable
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final byte  USED_DATA_VALUES = 12;
    /**
     * Blast resistance of block, can be changed only before server start.
     * Final copy of blast resistance from {@link MagicNumbers} class.
     */
    public static final float BLAST_RESISTANCE = MagicNumbers.MATERIAL__DETECTOR_RAIL__BLAST_RESISTANCE;
    /**
     * Hardness of block, can be changed only before server start.
     * Final copy of hardness from {@link MagicNumbers} class.
     */
    public static final float HARDNESS         = MagicNumbers.MATERIAL__DETECTOR_RAIL__HARDNESS;
    public static final byte  POWERED_FLAG     = 0x08;

    public static final DetectorRail DETECTOR_RAIL_NORTH_SOUTH             = new DetectorRail();
    public static final DetectorRail DETECTOR_RAIL_WEST_EAST               = new DetectorRail("WEST_EAST", RailType.FLAT_WEST_EAST, false);
    public static final DetectorRail DETECTOR_RAIL_ASCENDING_EAST          = new DetectorRail("ASCENDING_EAST", RailType.ASCENDING_EAST, false);
    public static final DetectorRail DETECTOR_RAIL_ASCENDING_WEST          = new DetectorRail("ASCENDING_WEST", RailType.ASCENDING_WEST, false);
    public static final DetectorRail DETECTOR_RAIL_ASCENDING_NORTH         = new DetectorRail("ASCENDING_NORTH", RailType.ASCENDING_NORTH, false);
    public static final DetectorRail DETECTOR_RAIL_ASCENDING_SOUTH         = new DetectorRail("ASCENDING_SOUTH", RailType.ASCENDING_SOUTH, false);
    public static final DetectorRail DETECTOR_RAIL_NORTH_SOUTH_POWERED     = new DetectorRail("NORTH_SOUTH_POWERED", RailType.FLAT_WEST_EAST, true);
    public static final DetectorRail DETECTOR_RAIL_WEST_EAST_POWERED       = new DetectorRail("WEST_EAST_POWERED", RailType.FLAT_WEST_EAST, true);
    public static final DetectorRail DETECTOR_RAIL_ASCENDING_EAST_POWERED  = new DetectorRail("ASCENDING_EAST_POWERED", RailType.ASCENDING_EAST, true);
    public static final DetectorRail DETECTOR_RAIL_ASCENDING_WEST_POWERED  = new DetectorRail("ASCENDING_WEST_POWERED", RailType.ASCENDING_WEST, true);
    public static final DetectorRail DETECTOR_RAIL_ASCENDING_NORTH_POWERED = new DetectorRail("ASCENDING_NORTH_POWERED", RailType.ASCENDING_NORTH, true);
    public static final DetectorRail DETECTOR_RAIL_ASCENDING_SOUTH_POWERED = new DetectorRail("ASCENDING_SOUTH_POWERED", RailType.ASCENDING_SOUTH, true);

    private static final Map<String, DetectorRail>    byName = new SimpleStringHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<DetectorRail> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    protected final boolean powered;

    @SuppressWarnings("MagicNumber")
    protected DetectorRail()
    {
        super("DETECTOR_RAIL", 28, "minecraft:golden_rail", "NORTH_SOUTH", RailType.FLAT_NORTH_SOUTH, (byte) 0x00);
        this.powered = false;
    }

    public DetectorRail(final String enumName, final RailType type, final boolean powered)
    {
        super(DETECTOR_RAIL_NORTH_SOUTH.name(), DETECTOR_RAIL_NORTH_SOUTH.getId(), DETECTOR_RAIL_NORTH_SOUTH.getMinecraftId(), enumName, type, powered ? POWERED_FLAG : 0x00);
        this.powered = powered;
    }

    public DetectorRail(final int maxStack, final String typeName, final RailType type, final boolean powered)
    {
        super(DETECTOR_RAIL_NORTH_SOUTH.name(), DETECTOR_RAIL_NORTH_SOUTH.getId(), DETECTOR_RAIL_NORTH_SOUTH.getMinecraftId(), maxStack, typeName, type, powered ? POWERED_FLAG : 0x00);
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
    public DetectorRail getRailType(final RailType railType)
    {
        return getDetectorRail(railType, this.powered);
    }

    @Override
    public DetectorRail getPowered(final boolean powered)
    {
        return getDetectorRail(this.railType, powered);
    }

    @Override
    public DetectorRail getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public DetectorRail getType(final int id)
    {
        return getByID(id);
    }

    public DetectorRail getType(final RailType railType, final boolean powered)
    {
        return getDetectorRail(railType, powered);
    }

    /**
     * Returns one of DetectorRail sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of DetectorRail or null
     */
    public static DetectorRail getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of DetectorRail sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of DetectorRail or null
     */
    public static DetectorRail getByEnumName(final String name)
    {
        return byName.get(name);
    }

    public static DetectorRail getDetectorRail(final RailType type, final boolean isPowered)
    {
        return getByID(type.getFlag() | (isPowered ? POWERED_FLAG : 0x00));
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final DetectorRail element)
    {
        byID.put(element.getType(), element);
        byName.put(element.name(), element);
    }

    static
    {
        DetectorRail.register(DETECTOR_RAIL_NORTH_SOUTH);
        DetectorRail.register(DETECTOR_RAIL_WEST_EAST);
        DetectorRail.register(DETECTOR_RAIL_ASCENDING_EAST);
        DetectorRail.register(DETECTOR_RAIL_ASCENDING_WEST);
        DetectorRail.register(DETECTOR_RAIL_ASCENDING_NORTH);
        DetectorRail.register(DETECTOR_RAIL_ASCENDING_SOUTH);
        DetectorRail.register(DETECTOR_RAIL_NORTH_SOUTH_POWERED);
        DetectorRail.register(DETECTOR_RAIL_WEST_EAST_POWERED);
        DetectorRail.register(DETECTOR_RAIL_ASCENDING_EAST_POWERED);
        DetectorRail.register(DETECTOR_RAIL_ASCENDING_WEST_POWERED);
        DetectorRail.register(DETECTOR_RAIL_ASCENDING_NORTH_POWERED);
        DetectorRail.register(DETECTOR_RAIL_ASCENDING_SOUTH_POWERED);
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("powered", this.powered).toString();
    }
}
