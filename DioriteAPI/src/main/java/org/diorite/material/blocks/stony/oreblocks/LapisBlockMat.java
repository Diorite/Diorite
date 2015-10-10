package org.diorite.material.blocks.stony.oreblocks;

import java.util.Map;

import org.diorite.material.Material;
import org.diorite.material.blocks.stony.ore.OreMat;
import org.diorite.material.items.OreItemMat;
import org.diorite.material.items.others.DyeMat;
import org.diorite.utils.collections.maps.CaseInsensitiveMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing 'Lapis Block' block material in minecraft. <br>
 * ID of block: 22 <br>
 * String ID of block: minecraft:lapis_block <br>
 * Hardness: 3 <br>
 * Blast Resistance 15
 */
@SuppressWarnings("JavaDoc")
public class LapisBlockMat extends OreBlockMat
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final int USED_DATA_VALUES = 1;

    public static final LapisBlockMat LAPIS_BLOCK = new LapisBlockMat();

    private static final Map<String, LapisBlockMat>    byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<LapisBlockMat> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR, Byte.MIN_VALUE);

    @SuppressWarnings("MagicNumber")
    protected LapisBlockMat()
    {
        super("LAPIS_BLOCK", 22, "minecraft:lapis_block", "LAPIS_BLOCK", (byte) 0x00, Material.LAPIS_ORE, DyeMat.DYE_LAPIS_LAZULI, 3, 15);
    }

    protected LapisBlockMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type, final OreMat ore, final OreItemMat item, final float hardness, final float blastResistance)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, ore, item, hardness, blastResistance);
    }

    @Override
    public LapisBlockMat getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public LapisBlockMat getType(final int id)
    {
        return getByID(id);
    }

    /**
     * Returns one of LapisBlock sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of LapisBlock or null
     */
    public static LapisBlockMat getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of LapisBlock sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of LapisBlock or null
     */
    public static LapisBlockMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final LapisBlockMat element)
    {
        byID.put((byte) element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    @Override
    public LapisBlockMat[] types()
    {
        return LapisBlockMat.lapisBlockTypes();
    }

    /**
     * @return array that contains all sub-types of this block.
     */
    public static LapisBlockMat[] lapisBlockTypes()
    {
        return byID.values(new LapisBlockMat[byID.size()]);
    }

    static
    {
        LapisBlockMat.register(LAPIS_BLOCK);
    }
}
