package org.diorite.material.blocks.stony.ore;

import java.util.Map;

import org.diorite.material.Material;
import org.diorite.material.blocks.stony.oreblocks.OreBlockMat;
import org.diorite.material.items.OreItemMat;
import org.diorite.material.items.others.DyeMat;
import org.diorite.utils.collections.maps.CaseInsensitiveMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing 'Lapis Ore' block material in minecraft. <br>
 * ID of block: 21 <br>
 * String ID of block: minecraft:lapis_ore <br>
 * Hardness: 3 <br>
 * Blast Resistance 15
 */
@SuppressWarnings("JavaDoc")
public class LapisOreMat extends OreMat
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final int USED_DATA_VALUES = 1;

    public static final LapisOreMat LAPIS_ORE = new LapisOreMat();

    private static final Map<String, LapisOreMat>    byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<LapisOreMat> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR, Byte.MIN_VALUE);

    @SuppressWarnings("MagicNumber")
    protected LapisOreMat()
    {
        super("LAPIS_ORE", 21, "minecraft:lapis_ore", "LAPIS_ORE", (byte) 0x00, DyeMat.DYE_LAPIS_LAZULI, Material.LAPIS_BLOCK, 3, 15);
    }

    protected LapisOreMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type, final OreItemMat item, final OreBlockMat block, final float hardness, final float blastResistance)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, item, block, hardness, blastResistance);
    }

    @Override
    public LapisOreMat getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public LapisOreMat getType(final int id)
    {
        return getByID(id);
    }

    /**
     * Returns one of LapisOre sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of LapisOre or null
     */
    public static LapisOreMat getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of LapisOre sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of LapisOre or null
     */
    public static LapisOreMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final LapisOreMat element)
    {
        byID.put((byte) element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    @Override
    public LapisOreMat[] types()
    {
        return LapisOreMat.lapisOreTypes();
    }

    /**
     * @return array that contains all sub-types of this block.
     */
    public static LapisOreMat[] lapisOreTypes()
    {
        return byID.values(new LapisOreMat[byID.size()]);
    }

    static
    {
        LapisOreMat.register(LAPIS_ORE);
    }
}
