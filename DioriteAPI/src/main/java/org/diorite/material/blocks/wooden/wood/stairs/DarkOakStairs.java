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
 * Class representing block "DarkDarkOakStairs" and all its subtypes.
 */
public class DarkOakStairs extends WoodenStairs
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final byte  USED_DATA_VALUES = 8;
    /**
     * Blast resistance of block, can be changed only before server start.
     * Final copy of blast resistance from {@link MagicNumbers} class.
     */
    public static final float BLAST_RESISTANCE = MagicNumbers.MATERIAL__DARK_OAK_STAIRS__BLAST_RESISTANCE;
    /**
     * Hardness of block, can be changed only before server start.
     * Final copy of hardness from {@link MagicNumbers} class.
     */
    public static final float HARDNESS         = MagicNumbers.MATERIAL__DARK_OAK_STAIRS__HARDNESS;

    public static final DarkOakStairs DARK_OAK_STAIRS_EAST  = new DarkOakStairs();
    public static final DarkOakStairs DARK_OAK_STAIRS_WEST  = new DarkOakStairs("WEST", BlockFace.WEST, false);
    public static final DarkOakStairs DARK_OAK_STAIRS_SOUTH = new DarkOakStairs("SOUTH", BlockFace.SOUTH, false);
    public static final DarkOakStairs DARK_OAK_STAIRS_NORTH = new DarkOakStairs("NORTH", BlockFace.NORTH, false);

    public static final DarkOakStairs DARK_OAK_STAIRS_EAST_UPSIDE_DOWN  = new DarkOakStairs("EAST_UPSIDE_DOWN", BlockFace.EAST, true);
    public static final DarkOakStairs DARK_OAK_STAIRS_WEST_UPSIDE_DOWN  = new DarkOakStairs("WEST_UPSIDE_DOWN", BlockFace.WEST, true);
    public static final DarkOakStairs DARK_OAK_STAIRS_SOUTH_UPSIDE_DOWN = new DarkOakStairs("SOUTH_UPSIDE_DOWN", BlockFace.SOUTH, true);
    public static final DarkOakStairs DARK_OAK_STAIRS_NORTH_UPSIDE_DOWN = new DarkOakStairs("NORTH_UPSIDE_DOWN", BlockFace.NORTH, true);

    private static final Map<String, DarkOakStairs>    byName = new SimpleStringHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<DarkOakStairs> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    @SuppressWarnings("MagicNumber")
    protected DarkOakStairs()
    {
        super("DARK_OAK_STAIRS", 164, "minecraft:dark_oak_stairs", "EAST", WoodType.DARK_OAK, BlockFace.EAST, false);
    }

    @SuppressWarnings("MagicNumber")
    public DarkOakStairs(final String enumName, final BlockFace face, final boolean upsideDown)
    {
        super(DARK_OAK_STAIRS_EAST.name(), DARK_OAK_STAIRS_EAST.getId(), DARK_OAK_STAIRS_EAST.getMinecraftId(), enumName, WoodType.DARK_OAK, face, upsideDown);
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
    public DarkOakStairs getBlockFacing(final BlockFace face)
    {
        return getByID(Stairs.combine(face, this.upsideDown));
    }

    @Override
    public DarkOakStairs getUpsideDown(final boolean upsideDown)
    {
        return getByID(Stairs.combine(this.face, upsideDown));
    }

    @Override
    public DarkOakStairs getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public DarkOakStairs getType(final int id)
    {
        return getByID(id);
    }

    @Override
    public DarkOakStairs getType(final BlockFace face, final boolean upsideDown)
    {
        return getByID(Stairs.combine(face, upsideDown));
    }

    /**
     * Returns one of DarkOakStairs sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of DarkOakStairs or null
     */
    public static DarkOakStairs getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of DarkOakStairs sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of DarkOakStairs or null
     */
    public static DarkOakStairs getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Returns one of DarkOakStairs sub-type based on facing direction and upside-down state.
     * It will never return null.
     *
     * @param blockFace  facing direction of stairs.
     * @param upsideDown if stairs should be upside-down.
     *
     * @return sub-type of DarkOakStairs
     */
    public static DarkOakStairs getDarkOakStairs(final BlockFace blockFace, final boolean upsideDown)
    {
        return getByID(Stairs.combine(blockFace, upsideDown));
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final DarkOakStairs element)
    {
        byID.put(element.getType(), element);
        byName.put(element.name(), element);
    }

    static
    {
        DarkOakStairs.register(DARK_OAK_STAIRS_EAST);
        DarkOakStairs.register(DARK_OAK_STAIRS_WEST);
        DarkOakStairs.register(DARK_OAK_STAIRS_SOUTH);
        DarkOakStairs.register(DARK_OAK_STAIRS_NORTH);
        DarkOakStairs.register(DARK_OAK_STAIRS_EAST_UPSIDE_DOWN);
        DarkOakStairs.register(DARK_OAK_STAIRS_WEST_UPSIDE_DOWN);
        DarkOakStairs.register(DARK_OAK_STAIRS_SOUTH_UPSIDE_DOWN);
        DarkOakStairs.register(DARK_OAK_STAIRS_NORTH_UPSIDE_DOWN);
    }
}
