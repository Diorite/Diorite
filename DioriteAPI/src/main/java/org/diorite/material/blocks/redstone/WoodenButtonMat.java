package org.diorite.material.blocks.redstone;

import java.util.Map;

import org.diorite.BlockFace;
import org.diorite.material.Material;
import org.diorite.utils.collections.maps.CaseInsensitiveMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing block "WoodenButton" and all its subtypes.
 */
public class WoodenButtonMat extends ButtonMat
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final byte  USED_DATA_VALUES = 12;

    public static final WoodenButtonMat WOODEN_BUTTON_DOWN          = new WoodenButtonMat();
    public static final WoodenButtonMat WOODEN_BUTTON_EAST          = new WoodenButtonMat(BlockFace.EAST, false);
    public static final WoodenButtonMat WOODEN_BUTTON_WEST          = new WoodenButtonMat(BlockFace.WEST, false);
    public static final WoodenButtonMat WOODEN_BUTTON_SOUTH         = new WoodenButtonMat(BlockFace.SOUTH, false);
    public static final WoodenButtonMat WOODEN_BUTTON_NORTH         = new WoodenButtonMat(BlockFace.NORTH, false);
    public static final WoodenButtonMat WOODEN_BUTTON_UP            = new WoodenButtonMat(BlockFace.UP, false);
    public static final WoodenButtonMat WOODEN_BUTTON_DOWN_POWERED  = new WoodenButtonMat(BlockFace.DOWN, true);
    public static final WoodenButtonMat WOODEN_BUTTON_EAST_POWERED  = new WoodenButtonMat(BlockFace.EAST, true);
    public static final WoodenButtonMat WOODEN_BUTTON_WEST_POWERED  = new WoodenButtonMat(BlockFace.WEST, true);
    public static final WoodenButtonMat WOODEN_BUTTON_SOUTH_POWERED = new WoodenButtonMat(BlockFace.SOUTH, true);
    public static final WoodenButtonMat WOODEN_BUTTON_NORTH_POWERED = new WoodenButtonMat(BlockFace.NORTH, true);
    public static final WoodenButtonMat WOODEN_BUTTON_UP_POWERED    = new WoodenButtonMat(BlockFace.UP, true);

    private static final Map<String, WoodenButtonMat>    byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<WoodenButtonMat> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    @SuppressWarnings("MagicNumber")
    protected WoodenButtonMat()
    {
        super("WOODEN_BUTTON", 143, "minecraft:wooden_button", BlockFace.DOWN, false, 0.5f, 2.5f);
    }

    protected WoodenButtonMat(final BlockFace face, final boolean powered)
    {
        super(WOODEN_BUTTON_DOWN.name(), WOODEN_BUTTON_DOWN.ordinal(), WOODEN_BUTTON_DOWN.getMinecraftId(), face, powered, WOODEN_BUTTON_DOWN.getHardness(), WOODEN_BUTTON_DOWN.getBlastResistance());
    }

    protected WoodenButtonMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type, final BlockFace face, final boolean powered, final float hardness, final float blastResistance)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, face, powered, hardness, blastResistance);
    }

    @Override
    public Material ensureValidInventoryItem()
    {
        return Material.WOODEN_BUTTON;
    }

    @Override
    public WoodenButtonMat getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public WoodenButtonMat getType(final int id)
    {
        return getByID(id);
    }

    @Override
    public WoodenButtonMat getType(final BlockFace face, final boolean powered)
    {
        return getByID(combine(face, powered));
    }

    @Override
    public WoodenButtonMat getPowered(final boolean powered)
    {
        return getByID(combine(this.face, powered));
    }

    @Override
    public WoodenButtonMat getBlockFacing(final BlockFace face)
    {
        return getByID(combine(face, this.powered));
    }

    @Override
    public WoodenButtonMat getAttachedFace(final BlockFace face)
    {
        return getByID(combine(face.getOppositeFace(), this.powered));
    }

    /**
     * Returns one of WoodenButton sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of WoodenButton or null
     */
    public static WoodenButtonMat getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of WoodenButton sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of WoodenButton or null
     */
    public static WoodenButtonMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Returns sub-type of WoodenButton based on {@link BlockFace} and powered state.
     * It will never return null.
     *
     * @param face    facing direction of button
     * @param powered if button should be powered.
     *
     * @return sub-type of WoodenButton
     */
    public static WoodenButtonMat getWoodenButton(final BlockFace face, final boolean powered)
    {
        return getByID(combine(face, powered));
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final WoodenButtonMat element)
    {
        byID.put((byte) element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    @Override
    public WoodenButtonMat[] types()
    {
        return WoodenButtonMat.woodenButtonTypes();
    }

    /**
     * @return array that contains all sub-types of this block.
     */
    public static WoodenButtonMat[] woodenButtonTypes()
    {
        return byID.values(new WoodenButtonMat[byID.size()]);
    }

    static
    {
        WoodenButtonMat.register(WOODEN_BUTTON_DOWN);
        WoodenButtonMat.register(WOODEN_BUTTON_EAST);
        WoodenButtonMat.register(WOODEN_BUTTON_WEST);
        WoodenButtonMat.register(WOODEN_BUTTON_SOUTH);
        WoodenButtonMat.register(WOODEN_BUTTON_NORTH);
        WoodenButtonMat.register(WOODEN_BUTTON_UP);
        WoodenButtonMat.register(WOODEN_BUTTON_DOWN_POWERED);
        WoodenButtonMat.register(WOODEN_BUTTON_EAST_POWERED);
        WoodenButtonMat.register(WOODEN_BUTTON_WEST_POWERED);
        WoodenButtonMat.register(WOODEN_BUTTON_SOUTH_POWERED);
        WoodenButtonMat.register(WOODEN_BUTTON_NORTH_POWERED);
        WoodenButtonMat.register(WOODEN_BUTTON_UP_POWERED);
    }
}
