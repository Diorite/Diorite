package org.diorite.material.blocks.wooden.wood.stairs;

import java.util.Map;

import org.diorite.BlockFace;
import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.material.blocks.Stairs;
import org.diorite.material.blocks.wooden.WoodType;
import org.diorite.utils.collections.SimpleStringHashMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing block "BirchStairs" and all its subtypes.
 */
public class BirchStairs extends WoodenStairs
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final byte  USED_DATA_VALUES = 8;
    /**
     * Blast resistance of block, can be changed only before server start.
     * Final copy of blast resistance from {@link MagicNumbers} class.
     */
    public static final float BLAST_RESISTANCE = MagicNumbers.MATERIAL__BIRCH_STAIRS__BLAST_RESISTANCE;
    /**
     * Hardness of block, can be changed only before server start.
     * Final copy of hardness from {@link MagicNumbers} class.
     */
    public static final float HARDNESS         = MagicNumbers.MATERIAL__BIRCH_STAIRS__HARDNESS;

    public static final BirchStairs BIRCH_STAIRS_EAST  = new BirchStairs();
    public static final BirchStairs BIRCH_STAIRS_WEST  = new BirchStairs("WEST", BlockFace.WEST, false);
    public static final BirchStairs BIRCH_STAIRS_SOUTH = new BirchStairs("SOUTH", BlockFace.SOUTH, false);
    public static final BirchStairs BIRCH_STAIRS_NORTH = new BirchStairs("NORTH", BlockFace.NORTH, false);

    public static final BirchStairs BIRCH_STAIRS_EAST_UPSIDE_DOWN  = new BirchStairs("EAST_UPSIDE_DOWN", BlockFace.EAST, true);
    public static final BirchStairs BIRCH_STAIRS_WEST_UPSIDE_DOWN  = new BirchStairs("WEST_UPSIDE_DOWN", BlockFace.WEST, true);
    public static final BirchStairs BIRCH_STAIRS_SOUTH_UPSIDE_DOWN = new BirchStairs("SOUTH_UPSIDE_DOWN", BlockFace.SOUTH, true);
    public static final BirchStairs BIRCH_STAIRS_NORTH_UPSIDE_DOWN = new BirchStairs("NORTH_UPSIDE_DOWN", BlockFace.NORTH, true);

    private static final Map<String, BirchStairs>    byName = new SimpleStringHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<BirchStairs> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    @SuppressWarnings("MagicNumber")
    protected BirchStairs()
    {
        super("BIRCH_STAIRS", 135, "minecraft:birch_stairs", "EAST", WoodType.BIRCH, BlockFace.EAST, false);
    }

    public BirchStairs(final String enumName, final BlockFace face, final boolean upsideDown)
    {
        super(BIRCH_STAIRS_EAST.name(), BIRCH_STAIRS_EAST.getId(), BIRCH_STAIRS_EAST.getMinecraftId(), enumName, WoodType.BIRCH, face, upsideDown);
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
    public BirchStairs getBlockFacing(final BlockFace face)
    {
        return getByID(Stairs.combine(face, this.upsideDown));
    }

    @Override
    public BirchStairs getUpsideDown(final boolean upsideDown)
    {
        return getByID(Stairs.combine(this.face, upsideDown));
    }

    @Override
    public BirchStairs getType(final BlockFace face, final boolean upsideDown)
    {
        return getByID(Stairs.combine(face, upsideDown));
    }

    @Override
    public BirchStairs getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public BirchStairs getType(final int id)
    {
        return getByID(id);
    }

    /**
     * Returns one of BirchStairs sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of BirchStairs or null
     */
    public static BirchStairs getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of BirchStairs sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of BirchStairs or null
     */
    public static BirchStairs getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Returns one of BirchStairs sub-type based on facing direction and upside-down state.
     * It will never return null.
     *
     * @param blockFace  facing direction of stairs.
     * @param upsideDown if stairs should be upside-down.
     *
     * @return sub-type of BirchStairs
     */
    public static BirchStairs getBirchStairs(final BlockFace blockFace, final boolean upsideDown)
    {
        return getByID(Stairs.combine(blockFace, upsideDown));
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final BirchStairs element)
    {
        byID.put(element.getType(), element);
        byName.put(element.name(), element);
    }

    static
    {
        BirchStairs.register(BIRCH_STAIRS_EAST);
        BirchStairs.register(BIRCH_STAIRS_WEST);
        BirchStairs.register(BIRCH_STAIRS_SOUTH);
        BirchStairs.register(BIRCH_STAIRS_NORTH);
        BirchStairs.register(BIRCH_STAIRS_EAST_UPSIDE_DOWN);
        BirchStairs.register(BIRCH_STAIRS_WEST_UPSIDE_DOWN);
        BirchStairs.register(BIRCH_STAIRS_SOUTH_UPSIDE_DOWN);
        BirchStairs.register(BIRCH_STAIRS_NORTH_UPSIDE_DOWN);
    }
}
