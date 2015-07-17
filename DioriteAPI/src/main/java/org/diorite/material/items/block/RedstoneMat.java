package org.diorite.material.items.block;

import java.util.Map;

import org.diorite.material.Material;
import org.diorite.material.blocks.stony.ore.OreMat;
import org.diorite.material.blocks.stony.oreblocks.OreBlockMat;
import org.diorite.material.items.OreItemMat;
import org.diorite.material.items.PlaceableItemMat;
import org.diorite.utils.collections.maps.CaseInsensitiveMap;

import gnu.trove.map.TShortObjectMap;
import gnu.trove.map.hash.TShortObjectHashMap;

@SuppressWarnings("MagicNumber")
public class RedstoneMat extends OreItemMat implements PlaceableItemMat
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final byte USED_DATA_VALUES = 1;

    public static final RedstoneMat REDSTONE = new RedstoneMat();

    private static final Map<String, RedstoneMat>     byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TShortObjectMap<RedstoneMat> byID   = new TShortObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    protected RedstoneMat()
    {
        super("REDSTONE", 331, "minecraft:redstone", "REDSTONE", (short) 0x00, Material.REDSTONE_ORE, Material.REDSTONE_BLOCK);
    }

    public RedstoneMat(final String enumName, final int id, final String minecraftId, final String typeName, final short type, final OreMat oreType, final OreBlockMat blockType)
    {
        super(enumName, id, minecraftId, typeName, type, oreType, blockType);
    }

    public RedstoneMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final short type, final OreMat oreType, final OreBlockMat blockType)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, oreType, blockType);
    }

    @Override
    public RedstoneMat getType(final int type)
    {
        return getByID(type);
    }

    @Override
    public RedstoneMat getType(final String type)
    {
        return getByEnumName(type);
    }

    /**
     * Returns one of Redstone sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of Redstone or null
     */
    public static RedstoneMat getByID(final int id)
    {
        return byID.get((short) id);
    }

    /**
     * Returns one of Redstone sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of Redstone or null
     */
    public static RedstoneMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final RedstoneMat element)
    {
        byID.put(element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    @Override
    public RedstoneMat[] types()
    {
        return RedstoneMat.redstoneTypes();
    }

    /**
     * @return array that contains all sub-types of this block.
     */
    public static RedstoneMat[] redstoneTypes()
    {
        return byID.values(new RedstoneMat[byID.size()]);
    }

    static
    {
        RedstoneMat.register(REDSTONE);
    }
}

