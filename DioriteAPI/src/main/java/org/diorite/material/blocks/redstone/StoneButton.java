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
 * Class representing block "StoneButton" and all its subtypes.
 */
public class StoneButton extends Button
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

    public static final StoneButton STONE_BUTTON_DOWN            = new StoneButton();
    public static final StoneButton STONE_BUTTON_EAST            = new StoneButton(BlockFace.EAST, false);
    public static final StoneButton STONE_BUTTON_WEST            = new StoneButton(BlockFace.WEST, false);
    public static final StoneButton STONE_BUTTON_SOUTH           = new StoneButton(BlockFace.SOUTH, false);
    public static final StoneButton STONE_BUTTON_NORTH           = new StoneButton(BlockFace.NORTH, false);
    public static final StoneButton STONE_BUTTON_UP              = new StoneButton(BlockFace.UP, false);
    public static final StoneButton STONE_BUTTON_DOWN_ACTIVATED  = new StoneButton(BlockFace.DOWN, true);
    public static final StoneButton STONE_BUTTON_EAST_ACTIVATED  = new StoneButton(BlockFace.EAST, true);
    public static final StoneButton STONE_BUTTON_WEST_ACTIVATED  = new StoneButton(BlockFace.WEST, true);
    public static final StoneButton STONE_BUTTON_SOUTH_ACTIVATED = new StoneButton(BlockFace.SOUTH, true);
    public static final StoneButton STONE_BUTTON_NORTH_ACTIVATED = new StoneButton(BlockFace.NORTH, true);
    public static final StoneButton STONE_BUTTON_UP_ACTIVATED    = new StoneButton(BlockFace.UP, true);

    private static final Map<String, StoneButton>    byName = new SimpleStringHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<StoneButton> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    @SuppressWarnings("MagicNumber")
    protected StoneButton()
    {
        super("STONE_BUTTON", 143, "minecraft:wooden_button", BlockFace.DOWN, false);
    }

    public StoneButton(final BlockFace face, final boolean activated)
    {
        super(STONE_BUTTON_DOWN.name(), STONE_BUTTON_DOWN.getId(), STONE_BUTTON_DOWN.getMinecraftId(), face, activated);
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
    public StoneButton getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public StoneButton getType(final int id)
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
     * Returns one of StoneButton sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of StoneButton or null
     */
    public static StoneButton getByID(final int id)
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
    public static StoneButton getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Returns sub-type of StoneButton based on {@link BlockFace} and activate state.
     * It will never return null.
     *
     * @param face      facing direction of button
     * @param activated if button should be activated.
     *
     * @return sub-type of StoneButton
     */
    public static StoneButton getStoneButton(final BlockFace face, final boolean activated)
    {
        return getByID(combine(face, activated));
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final StoneButton element)
    {
        byID.put(element.getType(), element);
        byName.put(element.name(), element);
    }

    static
    {
        StoneButton.register(STONE_BUTTON_DOWN);
        StoneButton.register(STONE_BUTTON_EAST);
        StoneButton.register(STONE_BUTTON_WEST);
        StoneButton.register(STONE_BUTTON_SOUTH);
        StoneButton.register(STONE_BUTTON_NORTH);
        StoneButton.register(STONE_BUTTON_UP);
        StoneButton.register(STONE_BUTTON_DOWN_ACTIVATED);
        StoneButton.register(STONE_BUTTON_EAST_ACTIVATED);
        StoneButton.register(STONE_BUTTON_WEST_ACTIVATED);
        StoneButton.register(STONE_BUTTON_SOUTH_ACTIVATED);
        StoneButton.register(STONE_BUTTON_NORTH_ACTIVATED);
        StoneButton.register(STONE_BUTTON_UP_ACTIVATED);
    }
}
