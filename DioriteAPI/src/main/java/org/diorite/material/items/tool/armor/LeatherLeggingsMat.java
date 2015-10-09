package org.diorite.material.items.tool.armor;

import java.util.Map;

import org.diorite.material.ArmorMaterial;
import org.diorite.material.ArmorType;
import org.diorite.utils.collections.maps.CaseInsensitiveMap;
import org.diorite.utils.lazy.LazyValue;
import org.diorite.utils.math.DioriteMathUtils;

import gnu.trove.map.TShortObjectMap;
import gnu.trove.map.hash.TShortObjectHashMap;

/**
 * Represents leather leggings.
 */
@SuppressWarnings("ClassHasNoToStringMethod")
public class LeatherLeggingsMat extends LeggingsMat
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final int USED_DATA_VALUES = 1;

    public static final LeatherLeggingsMat LEATHER_LEGGINGS = new LeatherLeggingsMat();

    private static final Map<String, LeatherLeggingsMat>     byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TShortObjectMap<LeatherLeggingsMat> byID   = new TShortObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR, Short.MIN_VALUE);

    protected final LazyValue<LeatherLeggingsMat> next = new LazyValue<>(() -> (this.haveValidDurability()) ? getByDurability(this.getDurability() + 1) : null);
    protected final LazyValue<LeatherLeggingsMat> prev = new LazyValue<>(() -> (this.haveValidDurability()) ? getByDurability(this.getDurability() - 1) : null);

    @SuppressWarnings("MagicNumber")
    protected LeatherLeggingsMat()
    {
        super("LEATHER_LEGGINGS", 300, "minecraft:leather_leggings", "NEW", (short) 0, ArmorMaterial.LEATHER);
    }

    protected LeatherLeggingsMat(final int durability)
    {
        super(LEATHER_LEGGINGS.name(), LEATHER_LEGGINGS.getId(), LEATHER_LEGGINGS.getMinecraftId(), Integer.toString(durability), (short) durability, ArmorMaterial.LEATHER);
    }

    protected LeatherLeggingsMat(final String enumName, final int id, final String minecraftId, final String typeName, final short type, final ArmorMaterial armorMaterial, final ArmorType armorType)
    {
        super(enumName, id, minecraftId, typeName, type, armorMaterial, armorType);
    }

    protected LeatherLeggingsMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final short type, final ArmorMaterial armorMaterial, final ArmorType armorType)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, armorMaterial, armorType);
    }

    @Override
    public LeatherLeggingsMat[] types()
    {
        return new LeatherLeggingsMat[]{LEATHER_LEGGINGS};
    }

    @Override
    public LeatherLeggingsMat getType(final String type)
    {
        return getByEnumName(type);
    }

    @Override
    public LeatherLeggingsMat getType(final int type)
    {
        return getByID(type);
    }

    @Override
    public LeatherLeggingsMat increaseDurability()
    {
        return this.next.get();
    }

    @Override
    public LeatherLeggingsMat decreaseDurability()
    {
        return this.prev.get();
    }

    @Override
    public LeatherLeggingsMat setDurability(final int durability)
    {
        return this.getType(durability);
    }

    /**
     * Returns one of LeatherLeggings sub-type based on sub-id.
     *
     * @param id sub-type id
     *
     * @return sub-type of LeatherLeggings.
     */
    public static LeatherLeggingsMat getByID(final int id)
    {
        LeatherLeggingsMat mat = byID.get((short) id);
        if (mat == null)
        {
            mat = new LeatherLeggingsMat(id);
            if ((id > 0) && (id < LEATHER_LEGGINGS.getBaseDurability()))
            {
                LeatherLeggingsMat.register(mat);
            }
        }
        return mat;
    }

    /**
     * Returns one of LeatherLeggings sub-type based on durability.
     *
     * @param id durability of type.
     *
     * @return sub-type of LeatherLeggings.
     */
    public static LeatherLeggingsMat getByDurability(final int id)
    {
        return getByID(id);
    }

    /**
     * Returns one of LeatherLeggings-type based on name (selected by diorite team).
     * If item contains only one type, sub-name of it will be this same as name of material.<br>
     * Returns null if name can't be parsed to int and it isn't "NEW" one.
     *
     * @param name name of sub-type
     *
     * @return sub-type of LeatherLeggings.
     */
    public static LeatherLeggingsMat getByEnumName(final String name)
    {
        final LeatherLeggingsMat mat = byName.get(name);
        if (mat == null)
        {
            final Integer idType = DioriteMathUtils.asInt(name);
            if (idType == null)
            {
                return null;
            }
            return getByID(idType);
        }
        return mat;
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final LeatherLeggingsMat element)
    {
        byID.put(element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    /**
     * @return array that contains all sub-types of this item.
     */
    public static LeatherLeggingsMat[] leatherLeggingsTypes()
    {
        return new LeatherLeggingsMat[]{LEATHER_LEGGINGS};
    }

    static
    {
        LeatherLeggingsMat.register(LEATHER_LEGGINGS);
    }
}
