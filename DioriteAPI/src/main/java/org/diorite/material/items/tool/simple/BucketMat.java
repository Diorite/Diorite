package org.diorite.material.items.tool.simple;

import java.util.Map;

import org.diorite.material.ItemMaterialData;
import org.diorite.utils.collections.maps.CaseInsensitiveMap;

import gnu.trove.map.TShortObjectMap;
import gnu.trove.map.hash.TShortObjectHashMap;

@SuppressWarnings("MagicNumber")
public class BucketMat extends ItemMaterialData
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final int USED_DATA_VALUES = 1;

    public static final BucketMat BUCKET = new BucketMat();

    private static final Map<String, BucketMat>     byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TShortObjectMap<BucketMat> byID   = new TShortObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR, Short.MIN_VALUE);

    protected BucketMat()
    {
        super("BUCKET", 325, "minecraft:bucket", 16, "BUCKET", (short) 0x00);
    }

    protected BucketMat(final String enumName, final int id, final String minecraftId, final String typeName, final short type)
    {
        super(enumName, id, minecraftId, BUCKET.getMaxStack(), typeName, type);
    }

    protected BucketMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final short type)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type);
    }

    @Override
    public BucketMat getType(final int type)
    {
        return getByID(type);
    }

    @Override
    public BucketMat getType(final String type)
    {
        return getByEnumName(type);
    }

    /**
     * Returns one of Bucket sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of Bucket or null
     */
    public static BucketMat getByID(final int id)
    {
        return byID.get((short) id);
    }

    /**
     * Returns one of Bucket sub-type based on name (selected by diorite team), may return null
     * If item contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of Bucket or null
     */
    public static BucketMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final BucketMat element)
    {
        byID.put(element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    @Override
    public BucketMat[] types()
    {
        return BucketMat.bucketTypes();
    }

    /**
     * @return array that contains all sub-types of this item.
     */
    public static BucketMat[] bucketTypes()
    {
        return byID.values(new BucketMat[byID.size()]);
    }

    static
    {
        BucketMat.register(BUCKET);
    }
}

