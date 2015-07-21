package org.diorite.material.blocks.stony.ore;

import java.util.Map;

import org.diorite.material.Material;
import org.diorite.material.blocks.stony.oreblocks.OreBlockMat;
import org.diorite.material.items.OreItemMat;
import org.diorite.utils.collections.maps.CaseInsensitiveMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing block "IronOre" and all its subtypes.
 */
public class IronOreMat extends OreMat
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final int USED_DATA_VALUES = 1;

    public static final IronOreMat IRON_ORE = new IronOreMat();

    private static final Map<String, IronOreMat>    byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<IronOreMat> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    @SuppressWarnings("MagicNumber")
    protected IronOreMat()
    {
        super("IRON_ORE", 15, "minecraft:iron_ore", "IRON_ORE", (byte) 0x00, Material.IRON_INGOT, Material.IRON_BLOCK, 3, 15);
    }

    protected IronOreMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type, final OreItemMat item, final OreBlockMat block, final float hardness, final float blastResistance)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, item, block, hardness, blastResistance);
    }

    @Override
    public IronOreMat getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public IronOreMat getType(final int id)
    {
        return getByID(id);
    }

    /**
     * Returns one of IronOre sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of IronOre or null
     */
    public static IronOreMat getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of IronOre sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of IronOre or null
     */
    public static IronOreMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final IronOreMat element)
    {
        byID.put((byte) element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    @Override
    public IronOreMat[] types()
    {
        return IronOreMat.ironOreTypes();
    }

    /**
     * @return array that contains all sub-types of this block.
     */
    public static IronOreMat[] ironOreTypes()
    {
        return byID.values(new IronOreMat[byID.size()]);
    }

    static
    {
        IronOreMat.register(IRON_ORE);
    }
}
