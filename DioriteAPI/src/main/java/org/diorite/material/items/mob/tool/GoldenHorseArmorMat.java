package org.diorite.material.items.mob.tool;

import java.util.Map;

import org.diorite.material.ItemMaterialData;
import org.diorite.utils.collections.maps.CaseInsensitiveMap;

import gnu.trove.map.TShortObjectMap;
import gnu.trove.map.hash.TShortObjectHashMap;

@SuppressWarnings("MagicNumber")
public class GoldenHorseArmorMat extends ItemMaterialData
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final int USED_DATA_VALUES = 1;

    public static final GoldenHorseArmorMat GOLDEN_HORSE_ARMOR = new GoldenHorseArmorMat();

    private static final Map<String, GoldenHorseArmorMat>     byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TShortObjectMap<GoldenHorseArmorMat> byID   = new TShortObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    protected GoldenHorseArmorMat()
    {
        super("GOLDEN_HORSE_ARMOR", 418, "minecraft:golden_horse_armor", "GOLDEN_HORSE_ARMOR", (short) 0x00);
    }

    protected GoldenHorseArmorMat(final String enumName, final int id, final String minecraftId, final String typeName, final short type)
    {
        super(enumName, id, minecraftId, typeName, type);
    }

    protected GoldenHorseArmorMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final short type)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type);
    }

    @Override
    public GoldenHorseArmorMat getType(final int type)
    {
        return getByID(type);
    }

    @Override
    public GoldenHorseArmorMat getType(final String type)
    {
        return getByEnumName(type);
    }

    /**
     * Returns one of GoldenHorseArmor sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of GoldenHorseArmor or null
     */
    public static GoldenHorseArmorMat getByID(final int id)
    {
        return byID.get((short) id);
    }

    /**
     * Returns one of GoldenHorseArmor sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of GoldenHorseArmor or null
     */
    public static GoldenHorseArmorMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final GoldenHorseArmorMat element)
    {
        byID.put(element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    @Override
    public GoldenHorseArmorMat[] types()
    {
        return GoldenHorseArmorMat.goldenHorseArmorTypes();
    }

    /**
     * @return array that contains all sub-types of this block.
     */
    public static GoldenHorseArmorMat[] goldenHorseArmorTypes()
    {
        return byID.values(new GoldenHorseArmorMat[byID.size()]);
    }

    static
    {
        GoldenHorseArmorMat.register(GOLDEN_HORSE_ARMOR);
    }
}

