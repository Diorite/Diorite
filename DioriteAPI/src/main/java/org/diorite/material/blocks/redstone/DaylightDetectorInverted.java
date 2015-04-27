package org.diorite.material.blocks.redstone;

import java.util.Map;

import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.material.BlockMaterialData;
import org.diorite.material.blocks.Activatable;
import org.diorite.utils.collections.SimpleStringHashMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing block "DaylightDetectorInverted" and all its subtypes.
 */
public class DaylightDetectorInverted extends BlockMaterialData implements Activatable
{
    // TODO: auto-generated class, implement other types (sub-ids).	
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final byte  USED_DATA_VALUES = 1;
    /**
     * Blast resistance of block, can be changed only before server start.
     * Final copy of blast resistance from {@link MagicNumbers} class.
     */
    public static final float BLAST_RESISTANCE = MagicNumbers.MATERIAL__DAYLIGHT_DETECTOR_INVERTED__BLAST_RESISTANCE;
    /**
     * Hardness of block, can be changed only before server start.
     * Final copy of hardness from {@link MagicNumbers} class.
     */
    public static final float HARDNESS         = MagicNumbers.MATERIAL__DAYLIGHT_DETECTOR_INVERTED__HARDNESS;

    public static final DaylightDetectorInverted DAYLIGHT_DETECTOR_INVERTED = new DaylightDetectorInverted();

    private static final Map<String, DaylightDetectorInverted>    byName = new SimpleStringHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<DaylightDetectorInverted> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    @SuppressWarnings("MagicNumber")
    protected DaylightDetectorInverted()
    {
        super("DAYLIGHT_DETECTOR_INVERTED", 178, "minecraft:daylight_detector_inverted", "DAYLIGHT_DETECTOR_INVERTED", (byte) 0x00);
    }

    public DaylightDetectorInverted(final String enumName, final int type)
    {
        super(DAYLIGHT_DETECTOR_INVERTED.name(), DAYLIGHT_DETECTOR_INVERTED.getId(), DAYLIGHT_DETECTOR_INVERTED.getMinecraftId(), enumName, (byte) type);
    }

    public DaylightDetectorInverted(final int maxStack, final String typeName, final byte type)
    {
        super(DAYLIGHT_DETECTOR_INVERTED.name(), DAYLIGHT_DETECTOR_INVERTED.getId(), DAYLIGHT_DETECTOR_INVERTED.getMinecraftId(), maxStack, typeName, type);
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
    public DaylightDetectorInverted getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public DaylightDetectorInverted getType(final int id)
    {
        return getByID(id);
    }

    @Override
    public boolean isActivated()
    {
        return false; // TODO: implement
    }

    @Override
    public DaylightDetectorInverted getActivated(final boolean activate)
    {
        return null; // TODO: implement
    }

    /**
     * Returns one of DaylightDetectorInverted sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of DaylightDetectorInverted or null
     */
    public static DaylightDetectorInverted getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of DaylightDetectorInverted sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of DaylightDetectorInverted or null
     */
    public static DaylightDetectorInverted getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final DaylightDetectorInverted element)
    {
        byID.put(element.getType(), element);
        byName.put(element.name(), element);
    }

    static
    {
        DaylightDetectorInverted.register(DAYLIGHT_DETECTOR_INVERTED);
    }
}
