package org.diorite.material.blocks.stony.ore;

import java.util.Map;

import org.diorite.material.Material;
import org.diorite.material.blocks.stony.oreblocks.OreBlockMat;
import org.diorite.material.items.OreItemMat;
import org.diorite.utils.collections.maps.CaseInsensitiveMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing block "RedstoneOreGlowing" and all its subtypes.
 * <p>
 * NOTE: Will crash game when in inventory.
 */
public class RedstoneOreGlowingMat extends OreMat
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final int USED_DATA_VALUES = 1;

    public static final RedstoneOreGlowingMat REDSTONE_ORE_GLOWING = new RedstoneOreGlowingMat();

    private static final Map<String, RedstoneOreGlowingMat>    byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<RedstoneOreGlowingMat> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    @SuppressWarnings("MagicNumber")
    protected RedstoneOreGlowingMat()
    {
        super("REDSTONE_ORE_GLOWING", 74, "minecraft:lit_redstone_ore", "REDSTONE_ORE_GLOWING", (byte) 0x00, Material.REDSTONE, Material.REDSTONE_BLOCK, 3, 15);
    }

    protected RedstoneOreGlowingMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type, final OreItemMat item, final OreBlockMat block, final float hardness, final float blastResistance)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, item, block, hardness, blastResistance);
    }

    @Override
    public Material ensureValidInventoryItem()
    {
        return Material.REDSTONE_ORE;
    }

    @Override
    public RedstoneOreGlowingMat getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public RedstoneOreGlowingMat getType(final int id)
    {
        return getByID(id);
    }

    /**
     * Returns one of RedstoneOreGlowing sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of RedstoneOreGlowing or null
     */
    public static RedstoneOreGlowingMat getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of RedstoneOreGlowing sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of RedstoneOreGlowing or null
     */
    public static RedstoneOreGlowingMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final RedstoneOreGlowingMat element)
    {
        byID.put((byte) element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    @Override
    public RedstoneOreGlowingMat[] types()
    {
        return RedstoneOreGlowingMat.redstoneOreGlowingTypes();
    }

    /**
     * @return array that contains all sub-types of this block.
     */
    public static RedstoneOreGlowingMat[] redstoneOreGlowingTypes()
    {
        return byID.values(new RedstoneOreGlowingMat[byID.size()]);
    }

    static
    {
        RedstoneOreGlowingMat.register(REDSTONE_ORE_GLOWING);
    }
}
