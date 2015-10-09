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
 * Represents gold helmet.
 */
@SuppressWarnings("ClassHasNoToStringMethod")
public class GoldHelmetMat extends HelmetMat
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final int USED_DATA_VALUES = 1;

    public static final GoldHelmetMat GOLD_HELMET = new GoldHelmetMat();

    private static final Map<String, GoldHelmetMat>     byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TShortObjectMap<GoldHelmetMat> byID   = new TShortObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR, Short.MIN_VALUE);

    protected final LazyValue<GoldHelmetMat> next = new LazyValue<>(() -> (this.haveValidDurability()) ? getByDurability(this.getDurability() + 1) : null);
    protected final LazyValue<GoldHelmetMat> prev = new LazyValue<>(() -> (this.haveValidDurability()) ? getByDurability(this.getDurability() - 1) : null);

    @SuppressWarnings("MagicNumber")
    protected GoldHelmetMat()
    {
        super("GOLD_HELMET", 314, "minecraft:gold_helmet", "NEW", (short) 0, ArmorMaterial.GOLD);
    }

    protected GoldHelmetMat(final int durability)
    {
        super(GOLD_HELMET.name(), GOLD_HELMET.getId(), GOLD_HELMET.getMinecraftId(), Integer.toString(durability), (short) durability, ArmorMaterial.GOLD);
    }

    protected GoldHelmetMat(final String enumName, final int id, final String minecraftId, final String typeName, final short type, final ArmorMaterial armorMaterial, final ArmorType armorType)
    {
        super(enumName, id, minecraftId, typeName, type, armorMaterial, armorType);
    }

    protected GoldHelmetMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final short type, final ArmorMaterial armorMaterial, final ArmorType armorType)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, armorMaterial, armorType);
    }

    @Override
    public GoldHelmetMat[] types()
    {
        return new GoldHelmetMat[]{GOLD_HELMET};
    }

    @Override
    public GoldHelmetMat getType(final String type)
    {
        return getByEnumName(type);
    }

    @Override
    public GoldHelmetMat getType(final int type)
    {
        return getByID(type);
    }

    @Override
    public GoldHelmetMat increaseDurability()
    {
        return this.next.get();
    }

    @Override
    public GoldHelmetMat decreaseDurability()
    {
        return this.prev.get();
    }

    @Override
    public GoldHelmetMat setDurability(final int durability)
    {
        return this.getType(durability);
    }

    /**
     * Returns one of GoldHelmet sub-type based on sub-id.
     *
     * @param id sub-type id
     *
     * @return sub-type of GoldHelmet.
     */
    public static GoldHelmetMat getByID(final int id)
    {
        GoldHelmetMat mat = byID.get((short) id);
        if (mat == null)
        {
            mat = new GoldHelmetMat(id);
            if ((id > 0) && (id < GOLD_HELMET.getBaseDurability()))
            {
                GoldHelmetMat.register(mat);
            }
        }
        return mat;
    }

    /**
     * Returns one of GoldHelmet sub-type based on durability.
     *
     * @param id durability of type.
     *
     * @return sub-type of GoldHelmet.
     */
    public static GoldHelmetMat getByDurability(final int id)
    {
        return getByID(id);
    }

    /**
     * Returns one of GoldHelmet-type based on name (selected by diorite team).
     * If item contains only one type, sub-name of it will be this same as name of material.<br>
     * Returns null if name can't be parsed to int and it isn't "NEW" one.
     *
     * @param name name of sub-type
     *
     * @return sub-type of GoldHelmet.
     */
    public static GoldHelmetMat getByEnumName(final String name)
    {
        final GoldHelmetMat mat = byName.get(name);
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
    public static void register(final GoldHelmetMat element)
    {
        byID.put(element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    /**
     * @return array that contains all sub-types of this item.
     */
    public static GoldHelmetMat[] goldHelmetTypes()
    {
        return new GoldHelmetMat[]{GOLD_HELMET};
    }

    static
    {
        GoldHelmetMat.register(GOLD_HELMET);
    }
}
