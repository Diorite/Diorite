package org.diorite.material.items.others;

import java.util.Map;

import org.diorite.material.ItemMaterialData;
import org.diorite.utils.collections.maps.CaseInsensitiveMap;

import gnu.trove.map.TShortObjectMap;
import gnu.trove.map.hash.TShortObjectHashMap;

/**
 * Class representing 'Firework Charge' item material in minecraft. <br>
 * ID of material: 402 <br>
 * String ID of material: minecraft:firework_charge <br>
 * Max item stack size: 64
 */
@SuppressWarnings("JavaDoc")
public class FireworkChargeMat extends ItemMaterialData
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final int USED_DATA_VALUES = 1;

    public static final FireworkChargeMat FIREWORK_CHARGE = new FireworkChargeMat();

    private static final Map<String, FireworkChargeMat>     byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TShortObjectMap<FireworkChargeMat> byID   = new TShortObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR, Short.MIN_VALUE);

    @SuppressWarnings("MagicNumber")
    protected FireworkChargeMat()
    {
        super("FIREWORK_CHARGE", 402, "minecraft:firework_charge", "FIREWORK_CHARGE", (short) 0x00);
    }

    protected FireworkChargeMat(final String enumName, final int id, final String minecraftId, final String typeName, final short type)
    {
        super(enumName, id, minecraftId, typeName, type);
    }

    protected FireworkChargeMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final short type)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type);
    }

    @Override
    public FireworkChargeMat getType(final int type)
    {
        return getByID(type);
    }

    @Override
    public FireworkChargeMat getType(final String type)
    {
        return getByEnumName(type);
    }

    /**
     * Returns one of FireworkCharge sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of FireworkCharge or null
     */
    public static FireworkChargeMat getByID(final int id)
    {
        return byID.get((short) id);
    }

    /**
     * Returns one of FireworkCharge sub-type based on name (selected by diorite team), may return null
     * If item contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of FireworkCharge or null
     */
    public static FireworkChargeMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final FireworkChargeMat element)
    {
        byID.put(element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    @Override
    public FireworkChargeMat[] types()
    {
        return FireworkChargeMat.fireworkChargeTypes();
    }

    /**
     * @return array that contains all sub-types of this item.
     */
    public static FireworkChargeMat[] fireworkChargeTypes()
    {
        return byID.values(new FireworkChargeMat[byID.size()]);
    }

    static
    {
        FireworkChargeMat.register(FIREWORK_CHARGE);
    }
}

