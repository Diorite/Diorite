package org.diorite.material.blocks.nether;

import java.util.Map;

import org.diorite.material.BlockMaterialData;
import org.diorite.utils.collections.maps.CaseInsensitiveMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing block "NetherBrick" and all its subtypes.
 */
public class NetherBrickMat extends BlockMaterialData
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final int USED_DATA_VALUES = 1;

    public static final NetherBrickMat NETHER_BRICK = new NetherBrickMat();

    private static final Map<String, NetherBrickMat>    byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<NetherBrickMat> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    @SuppressWarnings("MagicNumber")
    protected NetherBrickMat()
    {
        super("NETHER_BRICK", 112, "minecraft:nether_brick", "NETHER_BRICK", (byte) 0x00, 2, 30);
    }

    protected NetherBrickMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type, final float hardness, final float blastResistance)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, hardness, blastResistance);
    }

    @Override
    public NetherBrickMat getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public NetherBrickMat getType(final int id)
    {
        return getByID(id);
    }

    /**
     * Returns one of NetherBrick sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of NetherBrick or null
     */
    public static NetherBrickMat getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of NetherBrick sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of NetherBrick or null
     */
    public static NetherBrickMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final NetherBrickMat element)
    {
        byID.put((byte) element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    @Override
    public NetherBrickMat[] types()
    {
        return NetherBrickMat.netherBrickTypes();
    }

    /**
     * @return array that contains all sub-types of this block.
     */
    public static NetherBrickMat[] netherBrickTypes()
    {
        return byID.values(new NetherBrickMat[byID.size()]);
    }

    static
    {
        NetherBrickMat.register(NETHER_BRICK);
    }
}
