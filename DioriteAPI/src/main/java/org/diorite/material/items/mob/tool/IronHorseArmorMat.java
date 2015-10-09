package org.diorite.material.items.mob.tool;

import java.util.Map;

import org.diorite.utils.collections.maps.CaseInsensitiveMap;

import gnu.trove.map.TShortObjectMap;
import gnu.trove.map.hash.TShortObjectHashMap;

@SuppressWarnings("MagicNumber")
public class IronHorseArmorMat extends AbstractHorseArmor
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final int USED_DATA_VALUES = 1;

    public static final IronHorseArmorMat IRON_HORSE_ARMOR = new IronHorseArmorMat();

    private static final Map<String, IronHorseArmorMat>     byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TShortObjectMap<IronHorseArmorMat> byID   = new TShortObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR, Short.MIN_VALUE);

    protected IronHorseArmorMat()
    {
        super("IRON_HORSE_ARMOR", 417, "minecraft:iron_horse_armor", "IRON_HORSE_ARMOR", (short) 0x00);
    }

    protected IronHorseArmorMat(final String enumName, final int id, final String minecraftId, final String typeName, final short type)
    {
        super(enumName, id, minecraftId, typeName, type);
    }

    protected IronHorseArmorMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final short type)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type);
    }

    @Override
    public IronHorseArmorMat getType(final int type)
    {
        return getByID(type);
    }

    @Override
    public IronHorseArmorMat getType(final String type)
    {
        return getByEnumName(type);
    }

    /**
     * Returns one of IronHorseArmor sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of IronHorseArmor or null
     */
    public static IronHorseArmorMat getByID(final int id)
    {
        return byID.get((short) id);
    }

    /**
     * Returns one of IronHorseArmor sub-type based on name (selected by diorite team), may return null
     * If item contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of IronHorseArmor or null
     */
    public static IronHorseArmorMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final IronHorseArmorMat element)
    {
        byID.put(element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    @Override
    public IronHorseArmorMat[] types()
    {
        return IronHorseArmorMat.ironHorseArmorTypes();
    }

    /**
     * @return array that contains all sub-types of this item.
     */
    public static IronHorseArmorMat[] ironHorseArmorTypes()
    {
        return byID.values(new IronHorseArmorMat[byID.size()]);
    }

    static
    {
        IronHorseArmorMat.register(IRON_HORSE_ARMOR);
    }
}

