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
 * Represents iron helmet.
 */
@SuppressWarnings("ClassHasNoToStringMethod")
public class IronHelmetMat extends HelmetMat
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final int USED_DATA_VALUES = 1;

    public static final IronHelmetMat IRON_HELMET = new IronHelmetMat();

    private static final Map<String, IronHelmetMat>     byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TShortObjectMap<IronHelmetMat> byID   = new TShortObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR, Short.MIN_VALUE);

    protected final LazyValue<IronHelmetMat> next = new LazyValue<>(() -> (this.haveValidDurability()) ? getByDurability(this.getDurability() + 1) : null);
    protected final LazyValue<IronHelmetMat> prev = new LazyValue<>(() -> (this.haveValidDurability()) ? getByDurability(this.getDurability() - 1) : null);

    @SuppressWarnings("MagicNumber")
    protected IronHelmetMat()
    {
        super("IRON_HELMET", 306, "minecraft:iron_helmet", "NEW", (short) 0, ArmorMaterial.IRON);
    }

    protected IronHelmetMat(final int durability)
    {
        super(IRON_HELMET.name(), IRON_HELMET.getId(), IRON_HELMET.getMinecraftId(), Integer.toString(durability), (short) durability, ArmorMaterial.IRON);
    }

    protected IronHelmetMat(final String enumName, final int id, final String minecraftId, final String typeName, final short type, final ArmorMaterial armorMaterial, final ArmorType armorType)
    {
        super(enumName, id, minecraftId, typeName, type, armorMaterial, armorType);
    }

    protected IronHelmetMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final short type, final ArmorMaterial armorMaterial, final ArmorType armorType)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, armorMaterial, armorType);
    }

    @Override
    public IronHelmetMat[] types()
    {
        return new IronHelmetMat[]{IRON_HELMET};
    }

    @Override
    public IronHelmetMat getType(final String type)
    {
        return getByEnumName(type);
    }

    @Override
    public IronHelmetMat getType(final int type)
    {
        return getByID(type);
    }

    @Override
    public IronHelmetMat increaseDurability()
    {
        return this.next.get();
    }

    @Override
    public IronHelmetMat decreaseDurability()
    {
        return this.prev.get();
    }

    @Override
    public IronHelmetMat setDurability(final int durability)
    {
        return this.getType(durability);
    }

    /**
     * Returns one of IronHelmet sub-type based on sub-id.
     *
     * @param id sub-type id
     *
     * @return sub-type of IronHelmet.
     */
    public static IronHelmetMat getByID(final int id)
    {
        IronHelmetMat mat = byID.get((short) id);
        if (mat == null)
        {
            mat = new IronHelmetMat(id);
            if ((id > 0) && (id < IRON_HELMET.getBaseDurability()))
            {
                IronHelmetMat.register(mat);
            }
        }
        return mat;
    }

    /**
     * Returns one of IronHelmet sub-type based on durability.
     *
     * @param id durability of type.
     *
     * @return sub-type of IronHelmet.
     */
    public static IronHelmetMat getByDurability(final int id)
    {
        return getByID(id);
    }

    /**
     * Returns one of IronHelmet-type based on name (selected by diorite team).
     * If block contains only one type, sub-name of it will be this same as name of material.<br>
     * Returns null if name can't be parsed to int and it isn't "NEW" one.
     *
     * @param name name of sub-type
     *
     * @return sub-type of IronHelmet.
     */
    public static IronHelmetMat getByEnumName(final String name)
    {
        final IronHelmetMat mat = byName.get(name);
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
    public static void register(final IronHelmetMat element)
    {
        byID.put(element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    /**
     * @return array that contains all sub-types of this block.
     */
    public static IronHelmetMat[] ironHelmetTypes()
    {
        return new IronHelmetMat[]{IRON_HELMET};
    }

    static
    {
        IronHelmetMat.register(IRON_HELMET);
    }
}
