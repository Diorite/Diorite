package org.diorite.material.blocks.redstone;

import java.util.Map;

import org.diorite.BlockFace;
import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.utils.collections.SimpleStringHashMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing block "RedstoneRepeaterOff" and all its subtypes.
 */
public class RedstoneRepeaterOff extends RedstoneRepeater
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final byte  USED_DATA_VALUES = 16;
    /**
     * Blast resistance of block, can be changed only before server start.
     * Final copy of blast resistance from {@link MagicNumbers} class.
     */
    public static final float BLAST_RESISTANCE = MagicNumbers.MATERIAL__REPEATER_OFF__BLAST_RESISTANCE;
    /**
     * Hardness of block, can be changed only before server start.
     * Final copy of hardness from {@link MagicNumbers} class.
     */
    public static final float HARDNESS         = MagicNumbers.MATERIAL__REPEATER_OFF__HARDNESS;

    public static final RedstoneRepeaterOff REDSTONE_REPEATER_OFF_NORTH_1 = new RedstoneRepeaterOff();
    public static final RedstoneRepeaterOff REDSTONE_REPEATER_OFF_EAST_1  = new RedstoneRepeaterOff(BlockFace.EAST, 1);
    public static final RedstoneRepeaterOff REDSTONE_REPEATER_OFF_SOUTH_1 = new RedstoneRepeaterOff(BlockFace.SOUTH, 1);
    public static final RedstoneRepeaterOff REDSTONE_REPEATER_OFF_WEST_1  = new RedstoneRepeaterOff(BlockFace.WEST, 1);

    public static final RedstoneRepeaterOff REDSTONE_REPEATER_OFF_NORTH_2 = new RedstoneRepeaterOff(BlockFace.NORTH, 2);
    public static final RedstoneRepeaterOff REDSTONE_REPEATER_OFF_EAST_2  = new RedstoneRepeaterOff(BlockFace.EAST, 2);
    public static final RedstoneRepeaterOff REDSTONE_REPEATER_OFF_SOUTH_2 = new RedstoneRepeaterOff(BlockFace.SOUTH, 2);
    public static final RedstoneRepeaterOff REDSTONE_REPEATER_OFF_WEST_2  = new RedstoneRepeaterOff(BlockFace.WEST, 2);

    public static final RedstoneRepeaterOff REDSTONE_REPEATER_OFF_NORTH_3 = new RedstoneRepeaterOff(BlockFace.NORTH, 3);
    public static final RedstoneRepeaterOff REDSTONE_REPEATER_OFF_EAST_3  = new RedstoneRepeaterOff(BlockFace.EAST, 3);
    public static final RedstoneRepeaterOff REDSTONE_REPEATER_OFF_SOUTH_3 = new RedstoneRepeaterOff(BlockFace.SOUTH, 3);
    public static final RedstoneRepeaterOff REDSTONE_REPEATER_OFF_WEST_3  = new RedstoneRepeaterOff(BlockFace.WEST, 3);

    public static final RedstoneRepeaterOff REDSTONE_REPEATER_OFF_NORTH_4 = new RedstoneRepeaterOff(BlockFace.NORTH, 4);
    public static final RedstoneRepeaterOff REDSTONE_REPEATER_OFF_EAST_4  = new RedstoneRepeaterOff(BlockFace.EAST, 4);
    public static final RedstoneRepeaterOff REDSTONE_REPEATER_OFF_SOUTH_4 = new RedstoneRepeaterOff(BlockFace.SOUTH, 4);
    public static final RedstoneRepeaterOff REDSTONE_REPEATER_OFF_WEST_4  = new RedstoneRepeaterOff(BlockFace.WEST, 4);

    private static final Map<String, RedstoneRepeaterOff>    byName = new SimpleStringHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<RedstoneRepeaterOff> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    @SuppressWarnings("MagicNumber")
    protected RedstoneRepeaterOff()
    {
        super("REDSTONE_REPEATER_OFF", 93, "minecraft:unpowered_repeater", BlockFace.NORTH, 1);
    }

    public RedstoneRepeaterOff(final BlockFace face, final int delay)
    {
        super(REDSTONE_REPEATER_OFF_NORTH_1.name(), REDSTONE_REPEATER_OFF_NORTH_1.getId(), REDSTONE_REPEATER_OFF_NORTH_1.getMinecraftId(), face, delay);
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
    public RedstoneRepeaterOff getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public RedstoneRepeaterOff getType(final int id)
    {
        return getByID(id);
    }

    @Override
    public boolean isActivated()
    {
        return false;
    }

    @Override
    public RedstoneRepeaterOff getDelay(final int delay)
    {
        return getByID(combine(this.face, delay));
    }

    @Override
    public RedstoneRepeaterOff getType(final BlockFace face, final int delay)
    {
        return getByID(combine(face, delay));
    }

    @Override
    public RedstoneRepeaterOff getBlockFacing(final BlockFace face)
    {
        return getByID(combine(face, this.delay));
    }

    /**
     * Returns one of RedstoneRepeaterOff sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of RedstoneRepeaterOff or null
     */
    public static RedstoneRepeaterOff getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of RedstoneRepeaterOff sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of RedstoneRepeaterOff or null
     */
    public static RedstoneRepeaterOff getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final RedstoneRepeaterOff element)
    {
        byID.put(element.getType(), element);
        byName.put(element.name(), element);
    }

    static
    {
        RedstoneRepeaterOff.register(REDSTONE_REPEATER_OFF_NORTH_1);
        RedstoneRepeaterOff.register(REDSTONE_REPEATER_OFF_EAST_1);
        RedstoneRepeaterOff.register(REDSTONE_REPEATER_OFF_SOUTH_1);
        RedstoneRepeaterOff.register(REDSTONE_REPEATER_OFF_WEST_1);
        RedstoneRepeaterOff.register(REDSTONE_REPEATER_OFF_NORTH_2);
        RedstoneRepeaterOff.register(REDSTONE_REPEATER_OFF_EAST_2);
        RedstoneRepeaterOff.register(REDSTONE_REPEATER_OFF_SOUTH_2);
        RedstoneRepeaterOff.register(REDSTONE_REPEATER_OFF_WEST_2);
        RedstoneRepeaterOff.register(REDSTONE_REPEATER_OFF_NORTH_3);
        RedstoneRepeaterOff.register(REDSTONE_REPEATER_OFF_EAST_3);
        RedstoneRepeaterOff.register(REDSTONE_REPEATER_OFF_SOUTH_3);
        RedstoneRepeaterOff.register(REDSTONE_REPEATER_OFF_WEST_3);
        RedstoneRepeaterOff.register(REDSTONE_REPEATER_OFF_NORTH_4);
        RedstoneRepeaterOff.register(REDSTONE_REPEATER_OFF_EAST_4);
        RedstoneRepeaterOff.register(REDSTONE_REPEATER_OFF_SOUTH_4);
        RedstoneRepeaterOff.register(REDSTONE_REPEATER_OFF_WEST_4);
    }
}
