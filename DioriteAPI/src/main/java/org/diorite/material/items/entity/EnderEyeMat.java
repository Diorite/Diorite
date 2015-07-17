package org.diorite.material.items.entity;

import java.util.Map;

import org.diorite.material.ItemMaterialData;
import org.diorite.material.items.PlaceableEntityMat;
import org.diorite.utils.collections.maps.CaseInsensitiveMap;

import gnu.trove.map.TShortObjectMap;
import gnu.trove.map.hash.TShortObjectHashMap;

@SuppressWarnings("MagicNumber")
public class EnderEyeMat extends ItemMaterialData implements PlaceableEntityMat
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final byte USED_DATA_VALUES = 1;

    public static final EnderEyeMat ENDER_EYE = new EnderEyeMat();

    private static final Map<String, EnderEyeMat>     byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TShortObjectMap<EnderEyeMat> byID   = new TShortObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    protected EnderEyeMat()
    {
        super("ENDER_EYE", 381, "minecraft:ender_eye", "ENDER_EYE", (short) 0x00);
    }

    protected EnderEyeMat(final String enumName, final int id, final String minecraftId, final String typeName, final short type)
    {
        super(enumName, id, minecraftId, typeName, type);
    }

    protected EnderEyeMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final short type)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type);
    }

    @Override
    public EnderEyeMat getType(final int type)
    {
        return getByID(type);
    }

    @Override
    public EnderEyeMat getType(final String type)
    {
        return getByEnumName(type);
    }

    /**
     * Returns one of EnderEye sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of EnderEye or null
     */
    public static EnderEyeMat getByID(final int id)
    {
        return byID.get((short) id);
    }

    /**
     * Returns one of EnderEye sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of EnderEye or null
     */
    public static EnderEyeMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final EnderEyeMat element)
    {
        byID.put(element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    @Override
    public EnderEyeMat[] types()
    {
        return EnderEyeMat.enderEyeTypes();
    }

    /**
     * @return array that contains all sub-types of this block.
     */
    public static EnderEyeMat[] enderEyeTypes()
    {
        return byID.values(new EnderEyeMat[byID.size()]);
    }

    static
    {
        EnderEyeMat.register(ENDER_EYE);
    }
}

