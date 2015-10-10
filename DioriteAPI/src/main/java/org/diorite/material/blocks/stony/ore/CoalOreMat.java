package org.diorite.material.blocks.stony.ore;

import java.util.Map;

import org.diorite.material.Material;
import org.diorite.material.blocks.stony.oreblocks.OreBlockMat;
import org.diorite.material.items.OreItemMat;
import org.diorite.utils.collections.maps.CaseInsensitiveMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing 'Coal Ore' block material in minecraft. <br>
 * ID of block: 16 <br>
 * String ID of block: minecraft:coal_ore <br>
 * Hardness: 3 <br>
 * Blast Resistance 15
 */
@SuppressWarnings("JavaDoc")
public class CoalOreMat extends OreMat
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final int USED_DATA_VALUES = 1;

    public static final CoalOreMat COAL_ORE = new CoalOreMat();

    private static final Map<String, CoalOreMat>    byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<CoalOreMat> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR, Byte.MIN_VALUE);

    @SuppressWarnings("MagicNumber")
    protected CoalOreMat()
    {
        super("COAL_ORE", 16, "minecraft:coal_ore", "COAL_ORE", (byte) 0x00, Material.COAL, Material.COAL_BLOCK, 3, 15);
    }

    protected CoalOreMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type, final OreItemMat item, final OreBlockMat block, final float hardness, final float blastResistance)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, item, block, hardness, blastResistance);
    }

    @Override
    public CoalOreMat getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public CoalOreMat getType(final int id)
    {
        return getByID(id);
    }

    /**
     * Returns one of CoalOre sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of CoalOre or null
     */
    public static CoalOreMat getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of CoalOre sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of CoalOre or null
     */
    public static CoalOreMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final CoalOreMat element)
    {
        byID.put((byte) element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    @Override
    public CoalOreMat[] types()
    {
        return CoalOreMat.coalOreTypes();
    }

    /**
     * @return array that contains all sub-types of this block.
     */
    public static CoalOreMat[] coalOreTypes()
    {
        return byID.values(new CoalOreMat[byID.size()]);
    }

    static
    {
        CoalOreMat.register(COAL_ORE);
    }
}
