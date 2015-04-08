package org.diorite.material.blocks.redstone;

import java.util.Map;

import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.material.BlockMaterialData;
import org.diorite.material.blocks.Activatable;
import org.diorite.utils.collections.SimpleStringHashMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

public class DaylightDetectorInverted extends BlockMaterialData implements Activatable
{
    // TODO: auto-generated class, implement other types (sub-ids).
    public static final byte  USED_DATA_VALUES = 1;
    public static final float BLAST_RESISTANCE = MagicNumbers.MATERIAL__DAYLIGHT_DETECTOR_INVERTED__BLAST_RESISTANCE;
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
    public BlockMaterialData getActivated(final boolean activate)
    {
        return null; // TODO: implement
    }

    public static DaylightDetectorInverted getByID(final int id)
    {
        return byID.get((byte) id);
    }

    public static DaylightDetectorInverted getByEnumName(final String name)
    {
        return byName.get(name);
    }

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