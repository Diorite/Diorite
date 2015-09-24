package org.diorite.material.items.others;

import java.util.Map;

import org.diorite.material.ItemMaterialData;
import org.diorite.utils.collections.maps.CaseInsensitiveMap;

import gnu.trove.map.TShortObjectMap;
import gnu.trove.map.hash.TShortObjectHashMap;

@SuppressWarnings("MagicNumber")
public class FilledMapMat extends ItemMaterialData
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final int USED_DATA_VALUES = 1;

    public static final FilledMapMat FILLED_MAP = new FilledMapMat();

    private static final Map<String, FilledMapMat>     byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TShortObjectMap<FilledMapMat> byID   = new TShortObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR, Short.MIN_VALUE);

    protected FilledMapMat()
    {
        super("FILLED_MAP", 358, "minecraft:filled_map", "0", (short) 0x00);
    }

    protected FilledMapMat(final short type)
    {
        super(FILLED_MAP.name(), FILLED_MAP.getId(), FILLED_MAP.getMinecraftId(), Integer.toString(type), type);
    }

    protected FilledMapMat(final String enumName, final int id, final String minecraftId, final String typeName, final short type)
    {
        super(enumName, id, minecraftId, typeName, type);
    }

    protected FilledMapMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final short type)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type);
    }

    @Override
    public FilledMapMat getType(final int type)
    {
        return getByID(type);
    }

    @Override
    public FilledMapMat getType(final String type)
    {
        return getByEnumName(type);
    }

    /**
     * Returns one of FilledMap sub-type based on sub-id, or
     * create new one if it don't exist, new sub-type will be
     * automatically registread.
     *
     * @param id map sub-type id.
     *
     * @return sub-type of FilledMap, never null.
     */
    public static FilledMapMat getOrCreateType(final short id)
    {
        FilledMapMat type = byID.get(id);
        if (type == null)
        {
            FilledMapMat.register(type = new FilledMapMat(id));
        }
        return type;
    }

    /**
     * Returns one of FilledMap sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of FilledMap or null
     */
    public static FilledMapMat getByID(final int id)
    {
        return byID.get((short) id);
    }

    /**
     * Returns one of FilledMap sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of FilledMap or null
     */
    public static FilledMapMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final FilledMapMat element)
    {
        byID.put(element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    @Override
    public FilledMapMat[] types()
    {
        return FilledMapMat.filledMapTypes();
    }

    /**
     * @return array that contains all sub-types of this block.
     */
    public static FilledMapMat[] filledMapTypes()
    {
        return byID.values(new FilledMapMat[byID.size()]);
    }

    static
    {
        FilledMapMat.register(FILLED_MAP);
    }
}

