package org.diorite.material.blocks.stony.ore;

import java.util.Map;

import org.diorite.material.Material;
import org.diorite.material.blocks.stony.oreblocks.OreBlockMat;
import org.diorite.material.items.OreItemMat;
import org.diorite.utils.collections.maps.CaseInsensitiveMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing block "DiamondOre" and all its subtypes.
 */
public class DiamondOreMat extends OreMat
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final int USED_DATA_VALUES = 1;

    public static final DiamondOreMat DIAMOND_ORE = new DiamondOreMat();

    private static final Map<String, DiamondOreMat>    byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<DiamondOreMat> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR, Byte.MIN_VALUE);

    @SuppressWarnings("MagicNumber")
    protected DiamondOreMat()
    {
        super("DIAMOND_ORE", 56, "minecraft:diamond_ore", "DIAMOND_ORE", (byte) 0x00, Material.DIAMOND, Material.DIAMOND_BLOCK, 3, 15);
    }

    protected DiamondOreMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type, final OreItemMat item, final OreBlockMat block, final float hardness, final float blastResistance)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, item, block, hardness, blastResistance);
    }

    @Override
    public DiamondOreMat getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public DiamondOreMat getType(final int id)
    {
        return getByID(id);
    }

    /**
     * Returns one of DiamondOre sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of DiamondOre or null
     */
    public static DiamondOreMat getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of DiamondOre sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of DiamondOre or null
     */
    public static DiamondOreMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final DiamondOreMat element)
    {
        byID.put((byte) element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    @Override
    public DiamondOreMat[] types()
    {
        return DiamondOreMat.diamondOreTypes();
    }

    /**
     * @return array that contains all sub-types of this block.
     */
    public static DiamondOreMat[] diamondOreTypes()
    {
        return byID.values(new DiamondOreMat[byID.size()]);
    }

    static
    {
        DiamondOreMat.register(DIAMOND_ORE);
    }
}
