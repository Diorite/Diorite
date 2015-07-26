package org.diorite.material.items.tool.armor;

import java.util.Map;

import org.diorite.material.ArmorMaterial;
import org.diorite.material.ArmorType;
import org.diorite.material.items.tool.ArmorMat;
import org.diorite.utils.collections.maps.CaseInsensitiveMap;
import org.diorite.utils.lazy.LazyValue;
import org.diorite.utils.math.DioriteMathUtils;

import gnu.trove.map.TShortObjectMap;
import gnu.trove.map.hash.TShortObjectHashMap;

/**
 * Represents iron leggings.
 */
@SuppressWarnings("ClassHasNoToStringMethod")
public class IronLeggingsMat extends ArmorMat
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final int USED_DATA_VALUES = 1;

    public static final IronLeggingsMat IRON_LEGGINGS = new IronLeggingsMat();

    private static final Map<String, IronLeggingsMat>     byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TShortObjectMap<IronLeggingsMat> byID   = new TShortObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR, Short.MIN_VALUE);

    protected final LazyValue<IronLeggingsMat> next = new LazyValue<>(() -> (this.haveValidDurability()) ? getByDurability(this.getDurability() + 1) : null);
    protected final LazyValue<IronLeggingsMat> prev = new LazyValue<>(() -> (this.haveValidDurability()) ? getByDurability(this.getDurability() - 1) : null);

    @SuppressWarnings("MagicNumber")
    protected IronLeggingsMat()
    {
        super("IRON_LEGGINGS", 308, "minecraft:iron_leggings", "NEW", (short) 0, ArmorMaterial.IRON, ArmorType.LEGGINGS);
    }

    protected IronLeggingsMat(final int durability)
    {
        super(IRON_LEGGINGS.name(), IRON_LEGGINGS.getId(), IRON_LEGGINGS.getMinecraftId(), Integer.toString(durability), (short) durability, ArmorMaterial.IRON, ArmorType.LEGGINGS);
    }

    protected IronLeggingsMat(final String enumName, final int id, final String minecraftId, final String typeName, final short type, final ArmorMaterial armorMaterial, final ArmorType armorType)
    {
        super(enumName, id, minecraftId, typeName, type, armorMaterial, armorType);
    }

    protected IronLeggingsMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final short type, final ArmorMaterial armorMaterial, final ArmorType armorType)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, armorMaterial, armorType);
    }

    @Override
    public IronLeggingsMat[] types()
    {
        return new IronLeggingsMat[]{IRON_LEGGINGS};
    }

    @Override
    public IronLeggingsMat getType(final String type)
    {
        return getByEnumName(type);
    }

    @Override
    public IronLeggingsMat getType(final int type)
    {
        return getByID(type);
    }

    @Override
    public IronLeggingsMat increaseDurability()
    {
        return this.next.get();
    }

    @Override
    public IronLeggingsMat decreaseDurability()
    {
        return this.prev.get();
    }

    @Override
    public IronLeggingsMat setDurability(final int durability)
    {
        return this.getType(durability);
    }

    /**
     * Returns one of IronLeggings sub-type based on sub-id.
     *
     * @param id sub-type id
     *
     * @return sub-type of IronLeggings.
     */
    public static IronLeggingsMat getByID(final int id)
    {
        IronLeggingsMat mat = byID.get((short) id);
        if (mat == null)
        {
            mat = new IronLeggingsMat(id);
            if ((id > 0) && (id < IRON_LEGGINGS.getBaseDurability()))
            {
                IronLeggingsMat.register(mat);
            }
        }
        return mat;
    }

    /**
     * Returns one of IronLeggings sub-type based on durability.
     *
     * @param id durability of type.
     *
     * @return sub-type of IronLeggings.
     */
    public static IronLeggingsMat getByDurability(final int id)
    {
        return getByID(id);
    }

    /**
     * Returns one of IronLeggings-type based on name (selected by diorite team).
     * If block contains only one type, sub-name of it will be this same as name of material.<br>
     * Returns null if name can't be parsed to int and it isn't "NEW" one.
     *
     * @param name name of sub-type
     *
     * @return sub-type of IronLeggings.
     */
    public static IronLeggingsMat getByEnumName(final String name)
    {
        final IronLeggingsMat mat = byName.get(name);
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
    public static void register(final IronLeggingsMat element)
    {
        byID.put(element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    /**
     * @return array that contains all sub-types of this block.
     */
    public static IronLeggingsMat[] ironLeggingsTypes()
    {
        return new IronLeggingsMat[]{IRON_LEGGINGS};
    }

    static
    {
        IronLeggingsMat.register(IRON_LEGGINGS);
    }
}
