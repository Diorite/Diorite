package org.diorite.material.items.tool.simple;

import java.util.Map;

import org.diorite.material.ItemMaterialData;
import org.diorite.utils.collections.maps.CaseInsensitiveMap;

import gnu.trove.map.TShortObjectMap;
import gnu.trove.map.hash.TShortObjectHashMap;

@SuppressWarnings("MagicNumber")
public class FireChargeMat extends ItemMaterialData
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final int USED_DATA_VALUES = 1;

    public static final FireChargeMat FIRE_CHARGE = new FireChargeMat();

    private static final Map<String, FireChargeMat>     byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TShortObjectMap<FireChargeMat> byID   = new TShortObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    protected FireChargeMat()
    {
        super("FIRE_CHARGE", 385, "minecraft:fire_charge", "FIRE_CHARGE", (short) 0x00);
    }

    protected FireChargeMat(final String enumName, final int id, final String minecraftId, final String typeName, final short type)
    {
        super(enumName, id, minecraftId, typeName, type);
    }

    protected FireChargeMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final short type)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type);
    }

    @Override
    public FireChargeMat getType(final int type)
    {
        return getByID(type);
    }

    @Override
    public FireChargeMat getType(final String type)
    {
        return getByEnumName(type);
    }

    /**
     * Returns one of FireCharge sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of FireCharge or null
     */
    public static FireChargeMat getByID(final int id)
    {
        return byID.get((short) id);
    }

    /**
     * Returns one of FireCharge sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of FireCharge or null
     */
    public static FireChargeMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final FireChargeMat element)
    {
        byID.put(element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    @Override
    public FireChargeMat[] types()
    {
        return FireChargeMat.fireChargeTypes();
    }

    /**
     * @return array that contains all sub-types of this block.
     */
    public static FireChargeMat[] fireChargeTypes()
    {
        return byID.values(new FireChargeMat[byID.size()]);
    }

    static
    {
        FireChargeMat.register(FIRE_CHARGE);
    }
}

