package org.diorite.material.items.others;

import java.util.Map;

import org.diorite.material.blocks.stony.ore.OreMat;
import org.diorite.material.blocks.stony.oreblocks.OreBlockMat;
import org.diorite.material.items.OreItemMat;
import org.diorite.utils.collections.maps.CaseInsensitiveMap;

import gnu.trove.map.TShortObjectMap;
import gnu.trove.map.hash.TShortObjectHashMap;

@SuppressWarnings("MagicNumber")
public class QuartzMat extends OreItemMat
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final byte USED_DATA_VALUES = 1;

    public static final QuartzMat QUARTZ = new QuartzMat();

    private static final Map<String, QuartzMat>     byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TShortObjectMap<QuartzMat> byID   = new TShortObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    protected QuartzMat()
    {
        super("QUARTZ", 406, "minecraft:quartz", "QUARTZ", (short) 0x00, QUARTZ_ORE, QUARTZ_BLOCK);
    }

    protected QuartzMat(final String enumName, final int id, final String minecraftId, final String typeName, final short type, final OreMat oreType, final OreBlockMat blockType)
    {
        super(enumName, id, minecraftId, typeName, type, oreType, blockType);
    }

    protected QuartzMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final short type, final OreMat oreType, final OreBlockMat blockType)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, oreType, blockType);
    }

    @Override
    public QuartzMat getType(final int type)
    {
        return getByID(type);
    }

    @Override
    public QuartzMat getType(final String type)
    {
        return getByEnumName(type);
    }

    /**
     * Returns one of Quartz sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of Quartz or null
     */
    public static QuartzMat getByID(final int id)
    {
        return byID.get((short) id);
    }

    /**
     * Returns one of Quartz sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of Quartz or null
     */
    public static QuartzMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final QuartzMat element)
    {
        byID.put(element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    @Override
    public QuartzMat[] types()
    {
        return QuartzMat.quartzTypes();
    }

    /**
     * @return array that contains all sub-types of this block.
     */
    public static QuartzMat[] quartzTypes()
    {
        return byID.values(new QuartzMat[byID.size()]);
    }

    static
    {
        QuartzMat.register(QUARTZ);
    }
}

