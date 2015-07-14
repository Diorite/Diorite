package org.diorite.material.blocks.plants;

import java.util.Map;

import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.utils.collections.maps.CaseInsensitiveMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing block "MushroomRed" and all its subtypes.
 */
public class MushroomRedMat extends MushroomMat
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final byte  USED_DATA_VALUES = 1;

    public static final MushroomRedMat RED_MUSHROOM = new MushroomRedMat();

    private static final Map<String, MushroomRedMat>    byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<MushroomRedMat> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    @SuppressWarnings("MagicNumber")
    protected MushroomRedMat()
    {
        super("RED_MUSHROOM", 40, "minecraft:red_mushroom", "RED_MUSHROOM", (byte) 0x00, 0, 0);
    }

    protected MushroomRedMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type, final float hardness, final float blastResistance)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, hardness, blastResistance);
    }

    @Override
    public MushroomRedMat getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public MushroomRedMat getType(final int id)
    {
        return getByID(id);
    }

    /**
     * Returns one of MushroomRed sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of MushroomRed or null
     */
    public static MushroomRedMat getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of MushroomRed sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of MushroomRed or null
     */
    public static MushroomRedMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final MushroomRedMat element)
    {
        byID.put((byte) element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    @Override
    public MushroomRedMat[] types()
    {
        return MushroomRedMat.mushroomRedTypes();
    }

    /**
     * @return array that contains all sub-types of this block.
     */
    public static MushroomRedMat[] mushroomRedTypes()
    {
        return byID.values(new MushroomRedMat[byID.size()]);
    }

    static
    {
        MushroomRedMat.register(RED_MUSHROOM);
    }
}
