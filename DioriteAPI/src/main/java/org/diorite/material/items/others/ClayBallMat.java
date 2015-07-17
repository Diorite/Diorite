package org.diorite.material.items.others;

import java.util.Map;

import org.diorite.material.ItemMaterialData;
import org.diorite.utils.collections.maps.CaseInsensitiveMap;

import gnu.trove.map.TShortObjectMap;
import gnu.trove.map.hash.TShortObjectHashMap;

@SuppressWarnings("MagicNumber")
public class ClayBallMat extends ItemMaterialData
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final byte USED_DATA_VALUES = 1;

    public static final ClayBallMat CLAY_BALL = new ClayBallMat();

    private static final Map<String, ClayBallMat>     byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TShortObjectMap<ClayBallMat> byID   = new TShortObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    protected ClayBallMat()
    {
        super("CLAY_BALL", 337, "minecraft:clay_ball", "CLAY_BALL", (short) 0x00);
    }

    protected ClayBallMat(final String enumName, final int id, final String minecraftId, final String typeName, final short type)
    {
        super(enumName, id, minecraftId, typeName, type);
    }

    protected ClayBallMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final short type)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type);
    }

    @Override
    public ClayBallMat getType(final int type)
    {
        return getByID(type);
    }

    @Override
    public ClayBallMat getType(final String type)
    {
        return getByEnumName(type);
    }

    /**
     * Returns one of ClayBall sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of ClayBall or null
     */
    public static ClayBallMat getByID(final int id)
    {
        return byID.get((short) id);
    }

    /**
     * Returns one of ClayBall sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of ClayBall or null
     */
    public static ClayBallMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final ClayBallMat element)
    {
        byID.put(element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    @Override
    public ClayBallMat[] types()
    {
        return ClayBallMat.clayBallTypes();
    }

    /**
     * @return array that contains all sub-types of this block.
     */
    public static ClayBallMat[] clayBallTypes()
    {
        return byID.values(new ClayBallMat[byID.size()]);
    }

    static
    {
        ClayBallMat.register(CLAY_BALL);
    }
}

