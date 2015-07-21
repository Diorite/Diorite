package org.diorite.material.items.mob;

import java.util.Map;

import org.diorite.material.ItemMaterialData;
import org.diorite.material.SkullType;
import org.diorite.utils.collections.maps.CaseInsensitiveMap;
import org.diorite.utils.collections.maps.SimpleEnumMap;

import gnu.trove.map.TShortObjectMap;
import gnu.trove.map.hash.TShortObjectHashMap;

@SuppressWarnings("MagicNumber")
public class SkullMat extends ItemMaterialData
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final int USED_DATA_VALUES = 5;

    public static final SkullMat SKULL_SKELETON        = new SkullMat();
    public static final SkullMat SKULL_WITHER_SKELETON = new SkullMat(SkullType.WITHER_SKELETON);
    public static final SkullMat SKULL_ZOMBIE          = new SkullMat(SkullType.ZOMBIE);
    public static final SkullMat SKULL_PLAYER          = new SkullMat(SkullType.PLAYER);
    public static final SkullMat SKULL_CREEPER         = new SkullMat(SkullType.CREEPER);

    private static final Map<String, SkullMat>     byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TShortObjectMap<SkullMat> byID   = new TShortObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    private static final SimpleEnumMap<SkullType, SkullMat> byType = new SimpleEnumMap<>(5);

    protected final SkullType skullType;

    protected SkullMat()
    {
        super("SKULL", 397, "minecraft:skull", SkullType.SKELETON.name(), (short) 0x00);
        this.skullType = SkullType.SKELETON;
    }

    protected SkullMat(final SkullType skullType)
    {
        super(SKULL_SKELETON.name(), SKULL_SKELETON.getId(), SKULL_SKELETON.getMinecraftId(), skullType.name(), skullType.getItemTypeID());
        this.skullType = skullType;
    }

    protected SkullMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final short type, final SkullType skullType)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type);
        this.skullType = skullType;
    }

    public SkullType getSkullType()
    {
        return this.skullType;
    }

    @Override
    public SkullMat getType(final int type)
    {
        return getByID(type);
    }

    @Override
    public SkullMat getType(final String type)
    {
        return getByEnumName(type);
    }

    /**
     * Returns one of Slimeball sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of Slimeball or null
     */
    public static SkullMat getByID(final int id)
    {
        return byID.get((short) id);
    }

    /**
     * Returns one of Slimeball sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of Slimeball or null
     */
    public static SkullMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final SkullMat element)
    {
        byID.put(element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    @Override
    public SkullMat[] types()
    {
        return SkullMat.slimeballTypes();
    }

    /**
     * @return array that contains all sub-types of this block.
     */
    public static SkullMat[] slimeballTypes()
    {
        return byID.values(new SkullMat[byID.size()]);
    }

    static
    {
        SkullMat.register(SKULL_SKELETON);
    }
}

