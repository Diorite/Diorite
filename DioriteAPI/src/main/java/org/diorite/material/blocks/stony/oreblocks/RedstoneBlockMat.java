package org.diorite.material.blocks.stony.oreblocks;

import java.util.Map;

import org.diorite.material.Material;
import org.diorite.material.blocks.stony.ore.OreMat;
import org.diorite.material.items.OreItemMat;
import org.diorite.utils.collections.maps.CaseInsensitiveMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing 'Redstone Block' block material in minecraft. <br>
 * ID of block: 152 <br>
 * String ID of block: minecraft:redstone_block <br>
 * Hardness: 5 <br>
 * Blast Resistance 30
 */
@SuppressWarnings("JavaDoc")
public class RedstoneBlockMat extends OreBlockMat
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final int USED_DATA_VALUES = 1;

    public static final RedstoneBlockMat REDSTONE_BLOCK = new RedstoneBlockMat();

    private static final Map<String, RedstoneBlockMat>    byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<RedstoneBlockMat> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR, Byte.MIN_VALUE);

    @SuppressWarnings("MagicNumber")
    protected RedstoneBlockMat()
    {
        super("REDSTONE_BLOCK", 152, "minecraft:redstone_block", "REDSTONE_BLOCK", (byte) 0x00, Material.REDSTONE_ORE, Material.REDSTONE, 5, 30);
    }

    protected RedstoneBlockMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type, final OreMat ore, final OreItemMat item, final float hardness, final float blastResistance)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, ore, item, hardness, blastResistance);
    }

    @Override
    public RedstoneBlockMat getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public RedstoneBlockMat getType(final int id)
    {
        return getByID(id);
    }

    /**
     * Returns one of RedstoneBlock sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of RedstoneBlock or null
     */
    public static RedstoneBlockMat getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of RedstoneBlock sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of RedstoneBlock or null
     */
    public static RedstoneBlockMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final RedstoneBlockMat element)
    {
        byID.put((byte) element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    @Override
    public RedstoneBlockMat[] types()
    {
        return RedstoneBlockMat.redstoneBlockTypes();
    }

    /**
     * @return array that contains all sub-types of this block.
     */
    public static RedstoneBlockMat[] redstoneBlockTypes()
    {
        return byID.values(new RedstoneBlockMat[byID.size()]);
    }

    static
    {
        RedstoneBlockMat.register(REDSTONE_BLOCK);
    }
}
