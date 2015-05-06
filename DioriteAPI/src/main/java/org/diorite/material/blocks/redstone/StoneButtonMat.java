package org.diorite.material.blocks.redstone;

import java.util.Map;

import org.diorite.BlockFace;
import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.utils.collections.SimpleStringHashMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing block "StoneButton" and all its subtypes.
 */
public class StoneButtonMat extends ButtonMat
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final byte  USED_DATA_VALUES = 12;
    /**
     * Blast resistance of block, can be changed only before server start.
     * Final copy of blast resistance from {@link MagicNumbers} class.
     */
    public static final float BLAST_RESISTANCE = MagicNumbers.MATERIAL__STONE_BUTTON__BLAST_RESISTANCE;
    /**
     * Hardness of block, can be changed only before server start.
     * Final copy of hardness from {@link MagicNumbers} class.
     */
    public static final float HARDNESS         = MagicNumbers.MATERIAL__STONE_BUTTON__HARDNESS;

    public static final StoneButtonMat STONE_BUTTON_DOWN          = new StoneButtonMat();
    public static final StoneButtonMat STONE_BUTTON_EAST          = new StoneButtonMat(BlockFace.EAST, false);
    public static final StoneButtonMat STONE_BUTTON_WEST          = new StoneButtonMat(BlockFace.WEST, false);
    public static final StoneButtonMat STONE_BUTTON_SOUTH         = new StoneButtonMat(BlockFace.SOUTH, false);
    public static final StoneButtonMat STONE_BUTTON_NORTH         = new StoneButtonMat(BlockFace.NORTH, false);
    public static final StoneButtonMat STONE_BUTTON_UP            = new StoneButtonMat(BlockFace.UP, false);
    public static final StoneButtonMat STONE_BUTTON_DOWN_POWERED  = new StoneButtonMat(BlockFace.DOWN, true);
    public static final StoneButtonMat STONE_BUTTON_EAST_POWERED  = new StoneButtonMat(BlockFace.EAST, true);
    public static final StoneButtonMat STONE_BUTTON_WEST_POWERED  = new StoneButtonMat(BlockFace.WEST, true);
    public static final StoneButtonMat STONE_BUTTON_SOUTH_POWERED = new StoneButtonMat(BlockFace.SOUTH, true);
    public static final StoneButtonMat STONE_BUTTON_NORTH_POWERED = new StoneButtonMat(BlockFace.NORTH, true);
    public static final StoneButtonMat STONE_BUTTON_UP_POWERED    = new StoneButtonMat(BlockFace.UP, true);

    private static final Map<String, StoneButtonMat>    byName = new SimpleStringHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<StoneButtonMat> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    @SuppressWarnings("MagicNumber")
    protected StoneButtonMat()
    {
        super("STONE_BUTTON", 143, "minecraft:wooden_button", BlockFace.DOWN, false);
    }

    protected StoneButtonMat(final BlockFace face, final boolean powered)
    {
        super(STONE_BUTTON_DOWN.name(), STONE_BUTTON_DOWN.getId(), STONE_BUTTON_DOWN.getMinecraftId(), face, powered);
    }

    protected StoneButtonMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type, final BlockFace face, final boolean powered)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, face, powered);
    }

    @Override
    public float getBlastResistance()
    {
        return BLAST_RESISTANCE;
    }

    @Override
    public float getHardness()
    {
        return HARDNESS;
    }

    @Override
    public StoneButtonMat getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public StoneButtonMat getType(final int id)
    {
        return getByID(id);
    }

    @Override
    public StoneButtonMat getType(final BlockFace face, final boolean powered)
    {
        return getByID(combine(face, powered));
    }

    @Override
    public StoneButtonMat getPowered(final boolean powered)
    {
        return getByID(combine(this.face, powered));
    }

    @Override
    public StoneButtonMat getBlockFacing(final BlockFace face)
    {
        return getByID(combine(face, this.powered));
    }

    @Override
    public StoneButtonMat getAttachedFace(final BlockFace face)
    {
        return getByID(combine(face.getOppositeFace(), this.powered));
    }

    /**
     * Returns one of StoneButton sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of StoneButton or null
     */
    public static StoneButtonMat getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of StoneButton sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of StoneButton or null
     */
    public static StoneButtonMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Returns sub-type of StoneButton based on {@link BlockFace} and powered state.
     * It will never return null.
     *
     * @param face    facing direction of button
     * @param powered if button should be powered.
     *
     * @return sub-type of StoneButton
     */
    public static StoneButtonMat getStoneButton(final BlockFace face, final boolean powered)
    {
        return getByID(combine(face, powered));
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final StoneButtonMat element)
    {
        byID.put((byte) element.getType(), element);
        byName.put(element.name(), element);
    }

    static
    {
        StoneButtonMat.register(STONE_BUTTON_DOWN);
        StoneButtonMat.register(STONE_BUTTON_EAST);
        StoneButtonMat.register(STONE_BUTTON_WEST);
        StoneButtonMat.register(STONE_BUTTON_SOUTH);
        StoneButtonMat.register(STONE_BUTTON_NORTH);
        StoneButtonMat.register(STONE_BUTTON_UP);
        StoneButtonMat.register(STONE_BUTTON_DOWN_POWERED);
        StoneButtonMat.register(STONE_BUTTON_EAST_POWERED);
        StoneButtonMat.register(STONE_BUTTON_WEST_POWERED);
        StoneButtonMat.register(STONE_BUTTON_SOUTH_POWERED);
        StoneButtonMat.register(STONE_BUTTON_NORTH_POWERED);
        StoneButtonMat.register(STONE_BUTTON_UP_POWERED);
    }
}
