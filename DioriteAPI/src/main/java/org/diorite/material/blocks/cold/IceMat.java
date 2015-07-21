package org.diorite.material.blocks.cold;

import java.util.Map;

import org.diorite.material.BlockMaterialData;
import org.diorite.utils.collections.maps.CaseInsensitiveMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing block "Ice" and all its subtypes.
 */
public class IceMat extends BlockMaterialData
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final int USED_DATA_VALUES = 1;

    public static final IceMat ICE = new IceMat();

    private static final Map<String, IceMat>    byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<IceMat> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    @SuppressWarnings("MagicNumber")
    protected IceMat()
    {
        super("ICE", 79, "minecraft:ice", "ICE", (byte) 0x00, 0.5f, 2.5f);
    }

    protected IceMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type, final float hardness, final float blastResistance)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, hardness, blastResistance);
    }

    @Override
    public IceMat getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public IceMat getType(final int id)
    {
        return getByID(id);
    }

    /**
     * Returns one of Ice sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of Ice or null
     */
    public static IceMat getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of Ice sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of Ice or null
     */
    public static IceMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final IceMat element)
    {
        byID.put((byte) element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    @Override
    public IceMat[] types()
    {
        return IceMat.iceTypes();
    }

    /**
     * @return array that contains all sub-types of this block.
     */
    public static IceMat[] iceTypes()
    {
        return byID.values(new IceMat[byID.size()]);
    }

    static
    {
        IceMat.register(ICE);
    }
}
