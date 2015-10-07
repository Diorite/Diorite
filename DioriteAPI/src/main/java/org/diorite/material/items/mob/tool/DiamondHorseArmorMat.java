package org.diorite.material.items.mob.tool;

import java.util.Map;

import org.diorite.utils.collections.maps.CaseInsensitiveMap;

import gnu.trove.map.TShortObjectMap;
import gnu.trove.map.hash.TShortObjectHashMap;

@SuppressWarnings("MagicNumber")
public class DiamondHorseArmorMat extends AbstractHorseArmor
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final int USED_DATA_VALUES = 1;

    public static final DiamondHorseArmorMat DIAMOND_HORSE_ARMOR = new DiamondHorseArmorMat();

    private static final Map<String, DiamondHorseArmorMat>     byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TShortObjectMap<DiamondHorseArmorMat> byID   = new TShortObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR, Short.MIN_VALUE);

    protected DiamondHorseArmorMat()
    {
        super("DIAMOND_HORSE_ARMOR", 419, "minecraft:diamond_horse_armor", "DIAMOND_HORSE_ARMOR", (short) 0x00);
    }

    protected DiamondHorseArmorMat(final String enumName, final int id, final String minecraftId, final String typeName, final short type)
    {
        super(enumName, id, minecraftId, typeName, type);
    }

    protected DiamondHorseArmorMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final short type)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type);
    }

    @Override
    public DiamondHorseArmorMat getType(final int type)
    {
        return getByID(type);
    }

    @Override
    public DiamondHorseArmorMat getType(final String type)
    {
        return getByEnumName(type);
    }

    /**
     * Returns one of DiamondHorseArmor sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of DiamondHorseArmor or null
     */
    public static DiamondHorseArmorMat getByID(final int id)
    {
        return byID.get((short) id);
    }

    /**
     * Returns one of DiamondHorseArmor sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of DiamondHorseArmor or null
     */
    public static DiamondHorseArmorMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final DiamondHorseArmorMat element)
    {
        byID.put(element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    @Override
    public DiamondHorseArmorMat[] types()
    {
        return DiamondHorseArmorMat.diamondHorseArmorTypes();
    }

    /**
     * @return array that contains all sub-types of this block.
     */
    public static DiamondHorseArmorMat[] diamondHorseArmorTypes()
    {
        return byID.values(new DiamondHorseArmorMat[byID.size()]);
    }

    static
    {
        DiamondHorseArmorMat.register(DIAMOND_HORSE_ARMOR);
    }
}

