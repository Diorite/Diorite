package org.diorite.material.blocks.tools;

import java.util.Map;

import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.material.BlockMaterialData;
import org.diorite.utils.collections.maps.CaseInsensitiveMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing block "Beacon" and all its subtypes.
 */
public class BeaconMat extends BlockMaterialData
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final byte  USED_DATA_VALUES = 1;

    public static final BeaconMat BEACON = new BeaconMat();

    private static final Map<String, BeaconMat>    byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<BeaconMat> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    @SuppressWarnings("MagicNumber")
    protected BeaconMat()
    {
        super("BEACON", 138, "minecraft:beacon", "BEACON", (byte) 0x00, 3, 15);
    }

    protected BeaconMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type, final float hardness, final float blastResistance)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, hardness, blastResistance);
    }

    @Override
    public BeaconMat getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public BeaconMat getType(final int id)
    {
        return getByID(id);
    }

    /**
     * Returns one of Beacon sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of Beacon or null
     */
    public static BeaconMat getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of Beacon sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of Beacon or null
     */
    public static BeaconMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final BeaconMat element)
    {
        byID.put((byte) element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    @Override
    public BeaconMat[] types()
    {
        return BeaconMat.beaconTypes();
    }

    /**
     * @return array that contains all sub-types of this block.
     */
    public static BeaconMat[] beaconTypes()
    {
        return byID.values(new BeaconMat[byID.size()]);
    }

    static
    {
        BeaconMat.register(BEACON);
    }
}
