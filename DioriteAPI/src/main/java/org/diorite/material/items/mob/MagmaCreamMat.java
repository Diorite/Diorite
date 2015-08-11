package org.diorite.material.items.mob;

import java.util.Map;

import org.diorite.material.ItemMaterialData;
import org.diorite.utils.collections.maps.CaseInsensitiveMap;

import gnu.trove.map.TShortObjectMap;
import gnu.trove.map.hash.TShortObjectHashMap;

@SuppressWarnings("MagicNumber")
public class MagmaCreamMat extends ItemMaterialData
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final int USED_DATA_VALUES = 1;

    public static final MagmaCreamMat MAGMA_CREAM = new MagmaCreamMat();

    private static final Map<String, MagmaCreamMat>     byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TShortObjectMap<MagmaCreamMat> byID   = new TShortObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR, Short.MIN_VALUE);

    protected MagmaCreamMat()
    {
        super("MAGMA_CREAM", 378, "minecraft:magma_cream", "MAGMA_CREAM", (short) 0x00);
    }

    protected MagmaCreamMat(final String enumName, final int id, final String minecraftId, final String typeName, final short type)
    {
        super(enumName, id, minecraftId, typeName, type);
    }

    protected MagmaCreamMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final short type)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type);
    }

    @Override
    public MagmaCreamMat getType(final int type)
    {
        return getByID(type);
    }

    @Override
    public MagmaCreamMat getType(final String type)
    {
        return getByEnumName(type);
    }

    /**
     * Returns one of MagmaCream sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of MagmaCream or null
     */
    public static MagmaCreamMat getByID(final int id)
    {
        return byID.get((short) id);
    }

    /**
     * Returns one of MagmaCream sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of MagmaCream or null
     */
    public static MagmaCreamMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final MagmaCreamMat element)
    {
        byID.put(element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    @Override
    public MagmaCreamMat[] types()
    {
        return MagmaCreamMat.magmaCreamTypes();
    }

    /**
     * @return array that contains all sub-types of this block.
     */
    public static MagmaCreamMat[] magmaCreamTypes()
    {
        return byID.values(new MagmaCreamMat[byID.size()]);
    }

    static
    {
        MagmaCreamMat.register(MAGMA_CREAM);
    }
}

