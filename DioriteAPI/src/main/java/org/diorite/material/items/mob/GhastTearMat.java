package org.diorite.material.items.mob;

import java.util.Map;

import org.diorite.material.ItemMaterialData;
import org.diorite.utils.collections.maps.CaseInsensitiveMap;

import gnu.trove.map.TShortObjectMap;
import gnu.trove.map.hash.TShortObjectHashMap;

@SuppressWarnings("MagicNumber")
public class GhastTearMat extends ItemMaterialData
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final int USED_DATA_VALUES = 1;

    public static final GhastTearMat GHAST_TEAR = new GhastTearMat();

    private static final Map<String, GhastTearMat>     byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TShortObjectMap<GhastTearMat> byID   = new TShortObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    protected GhastTearMat()
    {
        super("GHAST_TEAR", 370, "minecraft:ghast_tear", "GHAST_TEAR", (short) 0x00);
    }

    protected GhastTearMat(final String enumName, final int id, final String minecraftId, final String typeName, final short type)
    {
        super(enumName, id, minecraftId, typeName, type);
    }

    protected GhastTearMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final short type)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type);
    }

    @Override
    public GhastTearMat getType(final int type)
    {
        return getByID(type);
    }

    @Override
    public GhastTearMat getType(final String type)
    {
        return getByEnumName(type);
    }

    /**
     * Returns one of GhastTear sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of GhastTear or null
     */
    public static GhastTearMat getByID(final int id)
    {
        return byID.get((short) id);
    }

    /**
     * Returns one of GhastTear sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of GhastTear or null
     */
    public static GhastTearMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final GhastTearMat element)
    {
        byID.put(element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    @Override
    public GhastTearMat[] types()
    {
        return GhastTearMat.ghastTearTypes();
    }

    /**
     * @return array that contains all sub-types of this block.
     */
    public static GhastTearMat[] ghastTearTypes()
    {
        return byID.values(new GhastTearMat[byID.size()]);
    }

    static
    {
        GhastTearMat.register(GHAST_TEAR);
    }
}

