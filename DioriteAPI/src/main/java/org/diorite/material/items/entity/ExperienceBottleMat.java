package org.diorite.material.items.entity;

import java.util.Map;

import org.diorite.material.ItemMaterialData;
import org.diorite.material.items.PlaceableEntityMat;
import org.diorite.utils.collections.maps.CaseInsensitiveMap;

import gnu.trove.map.TShortObjectMap;
import gnu.trove.map.hash.TShortObjectHashMap;

@SuppressWarnings("MagicNumber")
public class ExperienceBottleMat extends ItemMaterialData implements PlaceableEntityMat
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final byte USED_DATA_VALUES = 1;

    public static final ExperienceBottleMat EXPERIENCE_BOTTLE = new ExperienceBottleMat();

    private static final Map<String, ExperienceBottleMat>     byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TShortObjectMap<ExperienceBottleMat> byID   = new TShortObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    protected ExperienceBottleMat()
    {
        super("EXPERIENCE_BOTTLE", 384, "minecraft:experience_bottle", "EXPERIENCE_BOTTLE", (short) 0x00);
    }

    protected ExperienceBottleMat(final String enumName, final int id, final String minecraftId, final String typeName, final short type)
    {
        super(enumName, id, minecraftId, typeName, type);
    }

    protected ExperienceBottleMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final short type)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type);
    }

    @Override
    public ExperienceBottleMat getType(final int type)
    {
        return getByID(type);
    }

    @Override
    public ExperienceBottleMat getType(final String type)
    {
        return getByEnumName(type);
    }

    /**
     * Returns one of ExperienceBottle sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of ExperienceBottle or null
     */
    public static ExperienceBottleMat getByID(final int id)
    {
        return byID.get((short) id);
    }

    /**
     * Returns one of ExperienceBottle sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of ExperienceBottle or null
     */
    public static ExperienceBottleMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final ExperienceBottleMat element)
    {
        byID.put(element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    @Override
    public ExperienceBottleMat[] types()
    {
        return ExperienceBottleMat.experienceBottleTypes();
    }

    /**
     * @return array that contains all sub-types of this block.
     */
    public static ExperienceBottleMat[] experienceBottleTypes()
    {
        return byID.values(new ExperienceBottleMat[byID.size()]);
    }

    static
    {
        ExperienceBottleMat.register(EXPERIENCE_BOTTLE);
    }
}

