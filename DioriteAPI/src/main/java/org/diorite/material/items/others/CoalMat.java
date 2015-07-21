package org.diorite.material.items.others;

import java.util.Map;

import org.diorite.material.blocks.stony.ore.OreMat;
import org.diorite.material.blocks.stony.oreblocks.OreBlockMat;
import org.diorite.material.items.OreItemMat;
import org.diorite.utils.collections.maps.CaseInsensitiveMap;

import gnu.trove.map.TShortObjectMap;
import gnu.trove.map.hash.TShortObjectHashMap;

@SuppressWarnings("MagicNumber")
public class CoalMat extends OreItemMat
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final int USED_DATA_VALUES = 2;

    public static final CoalMat COAL     = new CoalMat();
    public static final CoalMat CHARCOAL = new CoalMat("CHARCOAL", 0x01, null, COAL_BLOCK);

    private static final Map<String, CoalMat>     byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TShortObjectMap<CoalMat> byID   = new TShortObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    protected CoalMat()
    {
        super("COAL", 263, "minecraft:coal", "COAL", (short) 0x00, COAL_ORE, COAL_BLOCK);
    }

    protected CoalMat(final String name, final int type, final OreMat oreType, final OreBlockMat blockType)
    {
        super(COAL.name(), COAL.getId(), COAL.getMinecraftId(), name, (short) type, oreType, blockType);
    }

    protected CoalMat(final String enumName, final int id, final String minecraftId, final String typeName, final short type, final OreMat oreType, final OreBlockMat blockType)
    {
        super(enumName, id, minecraftId, typeName, type, oreType, blockType);
    }

    protected CoalMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final short type, final OreMat oreType, final OreBlockMat blockType)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, oreType, blockType);
    }

    @Override
    public CoalMat getType(final int type)
    {
        return getByID(type);
    }

    @Override
    public CoalMat getType(final String type)
    {
        return getByEnumName(type);
    }

    /**
     * Returns one of Coal sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of Coal or null
     */
    public static CoalMat getByID(final int id)
    {
        return byID.get((short) id);
    }

    /**
     * Returns one of Coal sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of Coal or null
     */
    public static CoalMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final CoalMat element)
    {
        byID.put(element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    @Override
    public CoalMat[] types()
    {
        return CoalMat.coalTypes();
    }

    /**
     * @return array that contains all sub-types of this block.
     */
    public static CoalMat[] coalTypes()
    {
        return byID.values(new CoalMat[byID.size()]);
    }

    static
    {
        CoalMat.register(COAL);
        CoalMat.register(CHARCOAL);
    }
}

