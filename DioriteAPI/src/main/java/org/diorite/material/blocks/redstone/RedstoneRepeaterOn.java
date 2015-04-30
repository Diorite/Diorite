package org.diorite.material.blocks.redstone;

import java.util.Map;

import org.diorite.BlockFace;
import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.utils.collections.SimpleStringHashMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing block "RedstoneRepeaterOn" and all its subtypes.
 */
public class RedstoneRepeaterOn extends RedstoneRepeater
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

    public static final RedstoneRepeaterOn REDSTONE_REPEATER_ON_NORTH_1 = new RedstoneRepeaterOn();
    public static final RedstoneRepeaterOn REDSTONE_REPEATER_ON_EAST_1  = new RedstoneRepeaterOn(BlockFace.EAST, 1);
    public static final RedstoneRepeaterOn REDSTONE_REPEATER_ON_SOUTH_1 = new RedstoneRepeaterOn(BlockFace.SOUTH, 1);
    public static final RedstoneRepeaterOn REDSTONE_REPEATER_ON_WEST_1  = new RedstoneRepeaterOn(BlockFace.WEST, 1);

    public static final RedstoneRepeaterOn REDSTONE_REPEATER_ON_NORTH_2 = new RedstoneRepeaterOn(BlockFace.NORTH, 2);
    public static final RedstoneRepeaterOn REDSTONE_REPEATER_ON_EAST_2  = new RedstoneRepeaterOn(BlockFace.EAST, 2);
    public static final RedstoneRepeaterOn REDSTONE_REPEATER_ON_SOUTH_2 = new RedstoneRepeaterOn(BlockFace.SOUTH, 2);
    public static final RedstoneRepeaterOn REDSTONE_REPEATER_ON_WEST_2  = new RedstoneRepeaterOn(BlockFace.WEST, 2);

    public static final RedstoneRepeaterOn REDSTONE_REPEATER_ON_NORTH_3 = new RedstoneRepeaterOn(BlockFace.NORTH, 3);
    public static final RedstoneRepeaterOn REDSTONE_REPEATER_ON_EAST_3  = new RedstoneRepeaterOn(BlockFace.EAST, 3);
    public static final RedstoneRepeaterOn REDSTONE_REPEATER_ON_SOUTH_3 = new RedstoneRepeaterOn(BlockFace.SOUTH, 3);
    public static final RedstoneRepeaterOn REDSTONE_REPEATER_ON_WEST_3  = new RedstoneRepeaterOn(BlockFace.WEST, 3);

    public static final RedstoneRepeaterOn REDSTONE_REPEATER_ON_NORTH_4 = new RedstoneRepeaterOn(BlockFace.NORTH, 4);
    public static final RedstoneRepeaterOn REDSTONE_REPEATER_ON_EAST_4  = new RedstoneRepeaterOn(BlockFace.EAST, 4);
    public static final RedstoneRepeaterOn REDSTONE_REPEATER_ON_SOUTH_4 = new RedstoneRepeaterOn(BlockFace.SOUTH, 4);
    public static final RedstoneRepeaterOn REDSTONE_REPEATER_ON_WEST_4  = new RedstoneRepeaterOn(BlockFace.WEST, 4);

    private static final Map<String, RedstoneRepeaterOn>    byName = new SimpleStringHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<RedstoneRepeaterOn> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    @SuppressWarnings("MagicNumber")
    protected RedstoneRepeaterOn()
    {
        super("REDSTONE_REPEATER_ON", 93, "minecraft:unpowered_repeater", BlockFace.NORTH, 1);
    }

    public RedstoneRepeaterOn(final BlockFace face, final int delay)
    {
        super(REDSTONE_REPEATER_ON_NORTH_1.name(), REDSTONE_REPEATER_ON_NORTH_1.getId(), REDSTONE_REPEATER_ON_NORTH_1.getMinecraftId(), face, delay);
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
    public RedstoneRepeaterOn getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public RedstoneRepeaterOn getType(final int id)
    {
        return getByID(id);
    }

    @Override
    public boolean isActivated()
    {
        return true;
    }

    @Override
    public RedstoneRepeaterOn getDelay(final int delay)
    {
        return getByID(combine(this.face, delay));
    }

    @Override
    public RedstoneRepeaterOn getType(final BlockFace face, final int delay)
    {
        return getByID(combine(face, delay));
    }

    @Override
    public RedstoneRepeaterOn getBlockFacing(final BlockFace face)
    {
        return getByID(combine(face, this.delay));
    }

    /**
     * Returns one of RedstoneRepeaterOn sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of RedstoneRepeaterOn or null
     */
    public static RedstoneRepeaterOn getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of RedstoneRepeaterOn sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of RedstoneRepeaterOn or null
     */
    public static RedstoneRepeaterOn getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final RedstoneRepeaterOn element)
    {
        byID.put(element.getType(), element);
        byName.put(element.name(), element);
    }

    static
    {
        RedstoneRepeaterOn.register(REDSTONE_REPEATER_ON_NORTH_1);
        RedstoneRepeaterOn.register(REDSTONE_REPEATER_ON_EAST_1);
        RedstoneRepeaterOn.register(REDSTONE_REPEATER_ON_SOUTH_1);
        RedstoneRepeaterOn.register(REDSTONE_REPEATER_ON_WEST_1);
        RedstoneRepeaterOn.register(REDSTONE_REPEATER_ON_NORTH_2);
        RedstoneRepeaterOn.register(REDSTONE_REPEATER_ON_EAST_2);
        RedstoneRepeaterOn.register(REDSTONE_REPEATER_ON_SOUTH_2);
        RedstoneRepeaterOn.register(REDSTONE_REPEATER_ON_WEST_2);
        RedstoneRepeaterOn.register(REDSTONE_REPEATER_ON_NORTH_3);
        RedstoneRepeaterOn.register(REDSTONE_REPEATER_ON_EAST_3);
        RedstoneRepeaterOn.register(REDSTONE_REPEATER_ON_SOUTH_3);
        RedstoneRepeaterOn.register(REDSTONE_REPEATER_ON_WEST_3);
        RedstoneRepeaterOn.register(REDSTONE_REPEATER_ON_NORTH_4);
        RedstoneRepeaterOn.register(REDSTONE_REPEATER_ON_EAST_4);
        RedstoneRepeaterOn.register(REDSTONE_REPEATER_ON_SOUTH_4);
        RedstoneRepeaterOn.register(REDSTONE_REPEATER_ON_WEST_4);
    }
}
