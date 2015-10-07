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
 * Represents gold chestplate.
 */
@SuppressWarnings("ClassHasNoToStringMethod")
public class GoldChestplateMat extends ChestplateMat
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final int USED_DATA_VALUES = 1;

    public static final GoldChestplateMat GOLD_CHESTPLATE = new GoldChestplateMat();

    private static final Map<String, GoldChestplateMat>     byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TShortObjectMap<GoldChestplateMat> byID   = new TShortObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR, Short.MIN_VALUE);

    protected final LazyValue<GoldChestplateMat> next = new LazyValue<>(() -> (this.haveValidDurability()) ? getByDurability(this.getDurability() + 1) : null);
    protected final LazyValue<GoldChestplateMat> prev = new LazyValue<>(() -> (this.haveValidDurability()) ? getByDurability(this.getDurability() - 1) : null);

    @SuppressWarnings("MagicNumber")
    protected GoldChestplateMat()
    {
        super("GOLD_CHESTPLATE", 315, "minecraft:gold_chestplate", "NEW", (short) 0, ArmorMaterial.GOLD);
    }

    protected GoldChestplateMat(final int durability)
    {
        super(GOLD_CHESTPLATE.name(), GOLD_CHESTPLATE.getId(), GOLD_CHESTPLATE.getMinecraftId(), Integer.toString(durability), (short) durability, ArmorMaterial.GOLD);
    }

    protected GoldChestplateMat(final String enumName, final int id, final String minecraftId, final String typeName, final short type, final ArmorMaterial armorMaterial, final ArmorType armorType)
    {
        super(enumName, id, minecraftId, typeName, type, armorMaterial, armorType);
    }

    protected GoldChestplateMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final short type, final ArmorMaterial armorMaterial, final ArmorType armorType)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, armorMaterial, armorType);
    }

    @Override
    public GoldChestplateMat[] types()
    {
        return new GoldChestplateMat[]{GOLD_CHESTPLATE};
    }

    @Override
    public GoldChestplateMat getType(final String type)
    {
        return getByEnumName(type);
    }

    @Override
    public GoldChestplateMat getType(final int type)
    {
        return getByID(type);
    }

    @Override
    public GoldChestplateMat increaseDurability()
    {
        return this.next.get();
    }

    @Override
    public GoldChestplateMat decreaseDurability()
    {
        return this.prev.get();
    }

    @Override
    public GoldChestplateMat setDurability(final int durability)
    {
        return this.getType(durability);
    }

    /**
     * Returns one of GoldChestplate sub-type based on sub-id.
     *
     * @param id sub-type id
     *
     * @return sub-type of GoldChestplate.
     */
    public static GoldChestplateMat getByID(final int id)
    {
        GoldChestplateMat mat = byID.get((short) id);
        if (mat == null)
        {
            mat = new GoldChestplateMat(id);
            if ((id > 0) && (id < GOLD_CHESTPLATE.getBaseDurability()))
            {
                GoldChestplateMat.register(mat);
            }
        }
        return mat;
    }

    /**
     * Returns one of GoldChestplate sub-type based on durability.
     *
     * @param id durability of type.
     *
     * @return sub-type of GoldChestplate.
     */
    public static GoldChestplateMat getByDurability(final int id)
    {
        return getByID(id);
    }

    /**
     * Returns one of GoldChestplate-type based on name (selected by diorite team).
     * If block contains only one type, sub-name of it will be this same as name of material.<br>
     * Returns null if name can't be parsed to int and it isn't "NEW" one.
     *
     * @param name name of sub-type
     *
     * @return sub-type of GoldChestplate.
     */
    public static GoldChestplateMat getByEnumName(final String name)
    {
        final GoldChestplateMat mat = byName.get(name);
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
    public static void register(final GoldChestplateMat element)
    {
        byID.put(element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    /**
     * @return array that contains all sub-types of this block.
     */
    public static GoldChestplateMat[] goldChestplateTypes()
    {
        return new GoldChestplateMat[]{GOLD_CHESTPLATE};
    }

    static
    {
        GoldChestplateMat.register(GOLD_CHESTPLATE);
    }
}
