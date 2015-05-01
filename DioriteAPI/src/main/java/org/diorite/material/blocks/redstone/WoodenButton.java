package org.diorite.material.blocks.redstone;

import java.util.Map;

import org.diorite.BlockFace;
import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.material.blocks.Activatable;
import org.diorite.material.blocks.Directional;
import org.diorite.utils.collections.SimpleStringHashMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing block "WoodenButton" and all its subtypes.
 */
public class WoodenButton extends Button
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final byte  USED_DATA_VALUES = 12;
    /**
     * Blast resistance of block, can be changed only before server start.
     * Final copy of blast resistance from {@link MagicNumbers} class.
     */
    public static final float BLAST_RESISTANCE = MagicNumbers.MATERIAL__WOODEN_BUTTON__BLAST_RESISTANCE;
    /**
     * Hardness of block, can be changed only before server start.
     * Final copy of hardness from {@link MagicNumbers} class.
     */
    public static final float HARDNESS         = MagicNumbers.MATERIAL__WOODEN_BUTTON__HARDNESS;

    public static final WoodenButton WOODEN_BUTTON_DOWN            = new WoodenButton();
    public static final WoodenButton WOODEN_BUTTON_EAST            = new WoodenButton(BlockFace.EAST, false);
    public static final WoodenButton WOODEN_BUTTON_WEST            = new WoodenButton(BlockFace.WEST, false);
    public static final WoodenButton WOODEN_BUTTON_SOUTH           = new WoodenButton(BlockFace.SOUTH, false);
    public static final WoodenButton WOODEN_BUTTON_NORTH           = new WoodenButton(BlockFace.NORTH, false);
    public static final WoodenButton WOODEN_BUTTON_UP              = new WoodenButton(BlockFace.UP, false);
    public static final WoodenButton WOODEN_BUTTON_DOWN_ACTIVATED  = new WoodenButton(BlockFace.DOWN, true);
    public static final WoodenButton WOODEN_BUTTON_EAST_ACTIVATED  = new WoodenButton(BlockFace.EAST, true);
    public static final WoodenButton WOODEN_BUTTON_WEST_ACTIVATED  = new WoodenButton(BlockFace.WEST, true);
    public static final WoodenButton WOODEN_BUTTON_SOUTH_ACTIVATED = new WoodenButton(BlockFace.SOUTH, true);
    public static final WoodenButton WOODEN_BUTTON_NORTH_ACTIVATED = new WoodenButton(BlockFace.NORTH, true);
    public static final WoodenButton WOODEN_BUTTON_UP_ACTIVATED    = new WoodenButton(BlockFace.UP, true);

    private static final Map<String, WoodenButton>    byName = new SimpleStringHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<WoodenButton> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    @SuppressWarnings("MagicNumber")
    protected WoodenButton()
    {
        super("WOODEN_BUTTON", 143, "minecraft:wooden_button", BlockFace.DOWN, false);
    }

    public WoodenButton(final BlockFace face, final boolean activated)
    {
        super(WOODEN_BUTTON_DOWN.name(), WOODEN_BUTTON_DOWN.getId(), WOODEN_BUTTON_DOWN.getMinecraftId(), face, activated);
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
    public WoodenButton getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public WoodenButton getType(final int id)
    {
        return getByID(id);
    }

    @Override
    public Button getType(final BlockFace face, final boolean activated)
    {
        return getByID(combine(face, activated));
    }

    @Override
    public Activatable getActivated(final boolean activated)
    {
        return getByID(combine(this.face, activated));
    }

    @Override
    public Directional getBlockFacing(final BlockFace face)
    {
        return getByID(combine(face, this.activated));
    }

    /**
     * Returns one of WoodenButton sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of WoodenButton or null
     */
    public static WoodenButton getByID(final int id)
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
    public static WoodenButton getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Returns sub-type of WoodenButton based on {@link BlockFace} and activate state.
     * It will never return null.
     *
     * @param face      facing direction of button
     * @param activated if button should be activated.
     *
     * @return sub-type of WoodenButton
     */
    public static WoodenButton getWoodenButton(final BlockFace face, final boolean activated)
    {
        return getByID(combine(face, activated));
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final WoodenButton element)
    {
        byID.put(element.getType(), element);
        byName.put(element.name(), element);
    }

    static
    {
        WoodenButton.register(WOODEN_BUTTON_DOWN);
        WoodenButton.register(WOODEN_BUTTON_EAST);
        WoodenButton.register(WOODEN_BUTTON_WEST);
        WoodenButton.register(WOODEN_BUTTON_SOUTH);
        WoodenButton.register(WOODEN_BUTTON_NORTH);
        WoodenButton.register(WOODEN_BUTTON_UP);
        WoodenButton.register(WOODEN_BUTTON_DOWN_ACTIVATED);
        WoodenButton.register(WOODEN_BUTTON_EAST_ACTIVATED);
        WoodenButton.register(WOODEN_BUTTON_WEST_ACTIVATED);
        WoodenButton.register(WOODEN_BUTTON_SOUTH_ACTIVATED);
        WoodenButton.register(WOODEN_BUTTON_NORTH_ACTIVATED);
        WoodenButton.register(WOODEN_BUTTON_UP_ACTIVATED);
    }
}
