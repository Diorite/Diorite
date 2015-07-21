package org.diorite.material.items.entity;

import java.util.Map;

import org.diorite.material.ItemMaterialData;
import org.diorite.material.PlaceableEntityMat;
import org.diorite.utils.collections.maps.CaseInsensitiveMap;

import gnu.trove.map.TShortObjectMap;
import gnu.trove.map.hash.TShortObjectHashMap;

@SuppressWarnings("MagicNumber")
public class EggMat extends ItemMaterialData implements PlaceableEntityMat
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final int USED_DATA_VALUES = 1;

    public static final EggMat EGG = new EggMat();

    private static final Map<String, EggMat>     byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TShortObjectMap<EggMat> byID   = new TShortObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    protected EggMat()
    {
        super("EGG", 344, "minecraft:egg", "EGG", (short) 0x00);
    }

    protected EggMat(final String enumName, final int id, final String minecraftId, final String typeName, final short type)
    {
        super(enumName, id, minecraftId, typeName, type);
    }

    protected EggMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final short type)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type);
    }

    @Override
    public EggMat getType(final int type)
    {
        return getByID(type);
    }

    @Override
    public EggMat getType(final String type)
    {
        return getByEnumName(type);
    }

    /**
     * Returns one of Egg sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of Egg or null
     */
    public static EggMat getByID(final int id)
    {
        return byID.get((short) id);
    }

    /**
     * Returns one of Egg sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of Egg or null
     */
    public static EggMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final EggMat element)
    {
        byID.put(element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    @Override
    public EggMat[] types()
    {
        return EggMat.eggTypes();
    }

    /**
     * @return array that contains all sub-types of this block.
     */
    public static EggMat[] eggTypes()
    {
        return byID.values(new EggMat[byID.size()]);
    }

    static
    {
        EggMat.register(EGG);
    }
}

