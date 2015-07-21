package org.diorite.material.blocks.stony.ore;

import java.util.Map;

import org.diorite.material.Material;
import org.diorite.material.blocks.stony.oreblocks.OreBlockMat;
import org.diorite.material.items.OreItemMat;
import org.diorite.utils.collections.maps.CaseInsensitiveMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing block "RedstoneOre" and all its subtypes.
 */
public class RedstoneOreMat extends OreMat
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final int USED_DATA_VALUES = 1;

    public static final RedstoneOreMat REDSTONE_ORE = new RedstoneOreMat();

    private static final Map<String, RedstoneOreMat>    byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<RedstoneOreMat> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    @SuppressWarnings("MagicNumber")
    protected RedstoneOreMat()
    {
        super("REDSTONE_ORE", 73, "minecraft:redstone_ore", "REDSTONE_ORE", (byte) 0x00, Material.REDSTONE, Material.REDSTONE_BLOCK, 3, 15);
    }

    protected RedstoneOreMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type, final OreItemMat item, final OreBlockMat block, final float hardness, final float blastResistance)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, item, block, hardness, blastResistance);
    }

    @Override
    public RedstoneOreMat getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public RedstoneOreMat getType(final int id)
    {
        return getByID(id);
    }

    /**
     * Returns one of RedstoneOre sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of RedstoneOre or null
     */
    public static RedstoneOreMat getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of RedstoneOre sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of RedstoneOre or null
     */
    public static RedstoneOreMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final RedstoneOreMat element)
    {
        byID.put((byte) element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    @Override
    public RedstoneOreMat[] types()
    {
        return RedstoneOreMat.redstoneOreTypes();
    }

    /**
     * @return array that contains all sub-types of this block.
     */
    public static RedstoneOreMat[] redstoneOreTypes()
    {
        return byID.values(new RedstoneOreMat[byID.size()]);
    }

    static
    {
        RedstoneOreMat.register(REDSTONE_ORE);
    }
}
