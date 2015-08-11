package org.diorite.material.blocks.stony.oreblocks;

import java.util.Map;

import org.diorite.material.Material;
import org.diorite.material.blocks.stony.ore.OreMat;
import org.diorite.material.items.OreItemMat;
import org.diorite.utils.collections.maps.CaseInsensitiveMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing block "IronBlock" and all its subtypes.
 */
public class IronBlockMat extends OreBlockMat
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final int USED_DATA_VALUES = 1;

    public static final IronBlockMat IRON_BLOCK = new IronBlockMat();

    private static final Map<String, IronBlockMat>    byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<IronBlockMat> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR, Byte.MIN_VALUE);

    @SuppressWarnings("MagicNumber")
    protected IronBlockMat()
    {
        super("IRON_BLOCK", 42, "minecraft:iron_block", "IRON_BLOCK", (byte) 0x00, Material.IRON_ORE, Material.IRON_INGOT, 5, 30);
    }

    protected IronBlockMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type, final OreMat ore, final OreItemMat item, final float hardness, final float blastResistance)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, ore, item, hardness, blastResistance);
    }

    @Override
    public IronBlockMat getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public IronBlockMat getType(final int id)
    {
        return getByID(id);
    }

    /**
     * Returns one of IronBlock sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of IronBlock or null
     */
    public static IronBlockMat getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of IronBlock sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of IronBlock or null
     */
    public static IronBlockMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final IronBlockMat element)
    {
        byID.put((byte) element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    @Override
    public IronBlockMat[] types()
    {
        return IronBlockMat.ironBlockTypes();
    }

    /**
     * @return array that contains all sub-types of this block.
     */
    public static IronBlockMat[] ironBlockTypes()
    {
        return byID.values(new IronBlockMat[byID.size()]);
    }

    static
    {
        IronBlockMat.register(IRON_BLOCK);
    }
}
