package org.diorite.material.blocks.earth;

import java.util.Map;

import org.diorite.utils.collections.maps.CaseInsensitiveMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing block "Mycelium" and all its subtypes.
 */
public class MyceliumMat extends EarthMat
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final int USED_DATA_VALUES = 1;

    public static final MyceliumMat MYCELIUM = new MyceliumMat();

    private static final Map<String, MyceliumMat>    byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<MyceliumMat> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    @SuppressWarnings("MagicNumber")
    protected MyceliumMat()
    {
        super("MYCELIUM", 110, "minecraft:mycelium", "MYCELIUM", (byte) 0x00, 0.6f, 2.5f);
    }

    protected MyceliumMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type, final float hardness, final float blastResistance)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, hardness, blastResistance);
    }

    @Override
    public MyceliumMat getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public MyceliumMat getType(final int id)
    {
        return getByID(id);
    }

    /**
     * Returns one of Mycelium sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of Mycelium or null
     */
    public static MyceliumMat getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of Mycelium sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of Mycelium or null
     */
    public static MyceliumMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final MyceliumMat element)
    {
        byID.put((byte) element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    @Override
    public MyceliumMat[] types()
    {
        return MyceliumMat.myceliumTypes();
    }

    /**
     * @return array that contains all sub-types of this block.
     */
    public static MyceliumMat[] myceliumTypes()
    {
        return byID.values(new MyceliumMat[byID.size()]);
    }

    static
    {
        MyceliumMat.register(MYCELIUM);
    }
}
