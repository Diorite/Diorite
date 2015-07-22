package org.diorite.material.items.others;

import java.util.Map;

import org.diorite.material.Material;
import org.diorite.material.blocks.stony.ore.OreMat;
import org.diorite.material.blocks.stony.oreblocks.OreBlockMat;
import org.diorite.material.items.OreItemMat;
import org.diorite.utils.collections.maps.CaseInsensitiveMap;

import gnu.trove.map.TShortObjectMap;
import gnu.trove.map.hash.TShortObjectHashMap;

@SuppressWarnings("MagicNumber")
public class IronIngotMat extends OreItemMat
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final int USED_DATA_VALUES = 1;

    public static final IronIngotMat IRON_INGOT = new IronIngotMat();

    private static final Map<String, IronIngotMat>     byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TShortObjectMap<IronIngotMat> byID   = new TShortObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR, Short.MIN_VALUE);

    protected IronIngotMat()
    {
        super("IRON_INGOT", 265, "minecraft:iron_ingot", "IRON_INGOT", (short) 0x00, Material.IRON_ORE, Material.IRON_BLOCK);
    }

    protected IronIngotMat(final String enumName, final int id, final String minecraftId, final String typeName, final short type, final OreMat oreType, final OreBlockMat blockType)
    {
        super(enumName, id, minecraftId, typeName, type, oreType, blockType);
    }

    protected IronIngotMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final short type, final OreMat oreType, final OreBlockMat blockType)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, oreType, blockType);
    }

    @Override
    public IronIngotMat getType(final int type)
    {
        return getByID(type);
    }

    @Override
    public IronIngotMat getType(final String type)
    {
        return getByEnumName(type);
    }

    /**
     * Returns one of IronIngot sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of IronIngot or null
     */
    public static IronIngotMat getByID(final int id)
    {
        return byID.get((short) id);
    }

    /**
     * Returns one of IronIngot sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of IronIngot or null
     */
    public static IronIngotMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final IronIngotMat element)
    {
        byID.put(element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    @Override
    public IronIngotMat[] types()
    {
        return IronIngotMat.ironIngotTypes();
    }

    /**
     * @return array that contains all sub-types of this block.
     */
    public static IronIngotMat[] ironIngotTypes()
    {
        return byID.values(new IronIngotMat[byID.size()]);
    }

    static
    {
        IronIngotMat.register(IRON_INGOT);
    }
}

