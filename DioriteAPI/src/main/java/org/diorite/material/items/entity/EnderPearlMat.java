package org.diorite.material.items.entity;

import java.util.Map;

import org.diorite.material.ItemMaterialData;
import org.diorite.material.PlaceableEntityMat;
import org.diorite.utils.collections.maps.CaseInsensitiveMap;

import gnu.trove.map.TShortObjectMap;
import gnu.trove.map.hash.TShortObjectHashMap;

/**
 * Class representing 'Ender Pearl' item material in minecraft. <br>
 * ID of material: 368 <br>
 * String ID of material: minecraft:ender_pearl <br>
 * Max item stack size: 16
 */
@SuppressWarnings("JavaDoc")
public class EnderPearlMat extends ItemMaterialData implements PlaceableEntityMat
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final int USED_DATA_VALUES = 1;

    public static final EnderPearlMat ENDER_PEARL = new EnderPearlMat();

    private static final Map<String, EnderPearlMat>     byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TShortObjectMap<EnderPearlMat> byID   = new TShortObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR, Short.MIN_VALUE);

    @SuppressWarnings("MagicNumber")
    protected EnderPearlMat()
    {
        super("ENDER_PEARL", 368, "minecraft:ender_pearl", 16, "ENDER_PEARL", (short) 0x00);
    }

    protected EnderPearlMat(final String enumName, final int id, final String minecraftId, final String typeName, final short type)
    {
        super(enumName, id, minecraftId, ENDER_PEARL.getMaxStack(), typeName, type);
    }

    protected EnderPearlMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final short type)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type);
    }

    @Override
    public EnderPearlMat getType(final int type)
    {
        return getByID(type);
    }

    @Override
    public EnderPearlMat getType(final String type)
    {
        return getByEnumName(type);
    }

    /**
     * Returns one of EnderPearl sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of EnderPearl or null
     */
    public static EnderPearlMat getByID(final int id)
    {
        return byID.get((short) id);
    }

    /**
     * Returns one of EnderPearl sub-type based on name (selected by diorite team), may return null
     * If item contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of EnderPearl or null
     */
    public static EnderPearlMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final EnderPearlMat element)
    {
        byID.put(element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    @Override
    public EnderPearlMat[] types()
    {
        return EnderPearlMat.enderPearlTypes();
    }

    /**
     * @return array that contains all sub-types of this item.
     */
    public static EnderPearlMat[] enderPearlTypes()
    {
        return byID.values(new EnderPearlMat[byID.size()]);
    }

    static
    {
        EnderPearlMat.register(ENDER_PEARL);
    }
}

