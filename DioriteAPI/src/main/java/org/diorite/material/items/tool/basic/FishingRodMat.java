package org.diorite.material.items.tool.basic;

import java.util.Map;

import org.diorite.material.BasicToolData;
import org.diorite.material.items.tool.BasicToolMat;
import org.diorite.utils.collections.maps.CaseInsensitiveMap;
import org.diorite.utils.lazy.LazyValue;
import org.diorite.utils.math.DioriteMathUtils;

import gnu.trove.map.TShortObjectMap;
import gnu.trove.map.hash.TShortObjectHashMap;

@SuppressWarnings("ClassHasNoToStringMethod")
public class FishingRodMat extends BasicToolMat
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final int USED_DATA_VALUES = 1;

    public static final FishingRodMat FISHING_ROD = new FishingRodMat();

    private static final Map<String, FishingRodMat>     byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TShortObjectMap<FishingRodMat> byID   = new TShortObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR, Short.MIN_VALUE);

    protected final LazyValue<FishingRodMat> next = new LazyValue<>(() -> (this.haveValidDurability()) ? getByDurability(this.getDurability() + 1) : null);
    protected final LazyValue<FishingRodMat> prev = new LazyValue<>(() -> (this.haveValidDurability()) ? getByDurability(this.getDurability() - 1) : null);

    @SuppressWarnings("MagicNumber")
    protected FishingRodMat()
    {
        super("FISHING_ROD", 346, "minecraft:fishing_rod", "NEW", (short) 0x00, new BasicToolData(65));
    }

    protected FishingRodMat(final int durability)
    {
        super(FISHING_ROD.name(), FISHING_ROD.getId(), FISHING_ROD.getMinecraftId(), Integer.toString(durability), (short) durability, new BasicToolData(FISHING_ROD.getToolData()));
    }

    public FishingRodMat(final String enumName, final int id, final String minecraftId, final String typeName, final short type, final BasicToolData toolData)
    {
        super(enumName, id, minecraftId, typeName, type, toolData);
    }

    public FishingRodMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final short type, final BasicToolData toolData)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, toolData);
    }

    @Override
    public FishingRodMat getType(final String type)
    {
        return getByEnumName(type);
    }

    @Override
    public FishingRodMat getType(final int type)
    {
        return getByID(type);
    }

    @Override
    public FishingRodMat increaseDurability()
    {
        return this.next.get();
    }

    @Override
    public FishingRodMat decreaseDurability()
    {
        return this.prev.get();
    }

    @Override
    public FishingRodMat setDurability(final int durability)
    {
        return this.getType(durability);
    }

    @Override
    public FishingRodMat[] types()
    {
        return fishingRodTypes();
    }

    /**
     * Returns one of FishingRod sub-type based on sub-id.
     *
     * @param id sub-type id
     *
     * @return sub-type of FishingRod.
     */
    public static FishingRodMat getByID(final int id)
    {
        FishingRodMat mat = byID.get((short) id);
        if (mat == null)
        {
            mat = new FishingRodMat(id);
            if ((id > 0) && (id < FISHING_ROD.getBaseDurability()))
            {
                FishingRodMat.register(mat);
            }
        }
        return mat;
    }

    /**
     * Returns one of FishingRod sub-type based on durability.
     *
     * @param id durability of type.
     *
     * @return sub-type of FishingRod.
     */
    public static FishingRodMat getByDurability(final int id)
    {
        return getByID(id);
    }

    /**
     * Returns one of FishingRod-type based on name (selected by diorite team).
     * If block contains only one type, sub-name of it will be this same as name of material.<br>
     * Returns null if name can't be parsed to int and it isn't "NEW" one.
     *
     * @param name name of sub-type
     *
     * @return sub-type of FishingRod.
     */
    public static FishingRodMat getByEnumName(final String name)
    {
        final FishingRodMat mat = byName.get(name);
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
    public static void register(final FishingRodMat element)
    {
        byID.put(element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    /**
     * @return array that contains all sub-types of this item.
     */
    public static FishingRodMat[] fishingRodTypes()
    {
        return new FishingRodMat[]{FISHING_ROD};
    }

    static
    {
        FishingRodMat.register(FISHING_ROD);
    }
}

