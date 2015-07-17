package org.diorite.material.items.others;

import java.util.Map;

import org.diorite.material.blocks.stony.ore.OreMat;
import org.diorite.material.blocks.stony.oreblocks.OreBlockMat;
import org.diorite.material.items.OreItemMat;
import org.diorite.utils.collections.maps.CaseInsensitiveMap;

import gnu.trove.map.TShortObjectMap;
import gnu.trove.map.hash.TShortObjectHashMap;

@SuppressWarnings("MagicNumber")
public class EmeraldMat extends OreItemMat
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final byte USED_DATA_VALUES = 1;

    public static final EmeraldMat EMERALD = new EmeraldMat();

    private static final Map<String, EmeraldMat>     byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TShortObjectMap<EmeraldMat> byID   = new TShortObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    protected EmeraldMat()
    {
        super("EMERALD", 388, "minecraft:emerald", "EMERALD", (short) 0x00, EMERALD_ORE, EMERALD_BLOCK);
    }

    protected EmeraldMat(final String enumName, final int id, final String minecraftId, final String typeName, final short type, final OreMat oreType, final OreBlockMat blockType)
    {
        super(enumName, id, minecraftId, typeName, type, oreType, blockType);
    }

    protected EmeraldMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final short type, final OreMat oreType, final OreBlockMat blockType)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, oreType, blockType);
    }

    @Override
    public EmeraldMat getType(final int type)
    {
        return getByID(type);
    }

    @Override
    public EmeraldMat getType(final String type)
    {
        return getByEnumName(type);
    }

    /**
     * Returns one of Emerald sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of Emerald or null
     */
    public static EmeraldMat getByID(final int id)
    {
        return byID.get((short) id);
    }

    /**
     * Returns one of Emerald sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of Emerald or null
     */
    public static EmeraldMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final EmeraldMat element)
    {
        byID.put(element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    @Override
    public EmeraldMat[] types()
    {
        return EmeraldMat.emeraldTypes();
    }

    /**
     * @return array that contains all sub-types of this block.
     */
    public static EmeraldMat[] emeraldTypes()
    {
        return byID.values(new EmeraldMat[byID.size()]);
    }

    static
    {
        EmeraldMat.register(EMERALD);
    }
}

