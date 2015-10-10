package org.diorite.material.blocks.redstone;

import java.util.Map;

import org.diorite.utils.collections.maps.CaseInsensitiveMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing 'Redstone Lamp Off' block material in minecraft. <br>
 * ID of block: 123 <br>
 * String ID of block: minecraft:redstone_lamp <br>
 * Hardness: 0,3 <br>
 * Blast Resistance 1,5
 */
@SuppressWarnings("JavaDoc")
public class RedstoneLampOffMat extends RedstoneLampMat
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final int USED_DATA_VALUES = 1;

    public static final RedstoneLampOffMat REDSTONE_LAMP_OFF = new RedstoneLampOffMat();

    private static final Map<String, RedstoneLampOffMat>    byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<RedstoneLampOffMat> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR, Byte.MIN_VALUE);

    @SuppressWarnings("MagicNumber")
    protected RedstoneLampOffMat()
    {
        super("REDSTONE_LAMP_OFF", 123, "minecraft:redstone_lamp", "REDSTONE_LAMP_OFF", (byte) 0x00, 0.3f, 1.5f);
    }

    protected RedstoneLampOffMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type, final float hardness, final float blastResistance)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, hardness, blastResistance);
    }

    @Override
    public RedstoneLampOffMat getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public RedstoneLampOffMat getType(final int id)
    {
        return getByID(id);
    }

    @Override
    public boolean isPowered()
    {
        return false;
    }

    /**
     * Returns one of RedstoneLampOff sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of RedstoneLampOff or null
     */
    public static RedstoneLampOffMat getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of RedstoneLampOff sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of RedstoneLampOff or null
     */
    public static RedstoneLampOffMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final RedstoneLampOffMat element)
    {
        byID.put((byte) element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    @Override
    public RedstoneLampOffMat[] types()
    {
        return RedstoneLampOffMat.redstoneLampOffTypes();
    }

    /**
     * @return array that contains all sub-types of this block.
     */
    public static RedstoneLampOffMat[] redstoneLampOffTypes()
    {
        return byID.values(new RedstoneLampOffMat[byID.size()]);
    }

    static
    {
        RedstoneLampOffMat.register(REDSTONE_LAMP_OFF);
    }
}
