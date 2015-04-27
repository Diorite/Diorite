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
 * Class representing block "OakStairs" and all its subtypes.
 */
public class OakStairs extends WoodenStairs
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final byte  USED_DATA_VALUES = 8;
    /**
     * Blast resistance of block, can be changed only before server start.
     * Final copy of blast resistance from {@link MagicNumbers} class.
     */
    public static final float BLAST_RESISTANCE = MagicNumbers.MATERIAL__OAK_STAIRS__BLAST_RESISTANCE;
    /**
     * Hardness of block, can be changed only before server start.
     * Final copy of hardness from {@link MagicNumbers} class.
     */
    public static final float HARDNESS         = MagicNumbers.MATERIAL__OAK_STAIRS__HARDNESS;

    public static final OakStairs OAK_STAIRS_EAST  = new OakStairs();
    public static final OakStairs OAK_STAIRS_WEST  = new OakStairs("WEST", BlockFace.WEST, false);
    public static final OakStairs OAK_STAIRS_SOUTH = new OakStairs("SOUTH", BlockFace.SOUTH, false);
    public static final OakStairs OAK_STAIRS_NORTH = new OakStairs("NORTH", BlockFace.NORTH, false);

    public static final OakStairs OAK_STAIRS_EAST_UPSIDE_DOWN  = new OakStairs("EAST_UPSIDE_DOWN", BlockFace.EAST, true);
    public static final OakStairs OAK_STAIRS_WEST_UPSIDE_DOWN  = new OakStairs("WEST_UPSIDE_DOWN", BlockFace.WEST, true);
    public static final OakStairs OAK_STAIRS_SOUTH_UPSIDE_DOWN = new OakStairs("SOUTH_UPSIDE_DOWN", BlockFace.SOUTH, true);
    public static final OakStairs OAK_STAIRS_NORTH_UPSIDE_DOWN = new OakStairs("NORTH_UPSIDE_DOWN", BlockFace.NORTH, true);

    private static final Map<String, OakStairs>    byName = new SimpleStringHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<OakStairs> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    @SuppressWarnings("MagicNumber")
    protected OakStairs()
    {
        super("OAK_STAIRS", 53, "minecraft:oak_stairs", "EAST", WoodType.OAK, BlockFace.EAST, false);
    }

    @SuppressWarnings("MagicNumber")
    public OakStairs(final String enumName, final BlockFace face, final boolean upsideDown)
    {
        super(OAK_STAIRS_EAST.name(), OAK_STAIRS_EAST.getId(), OAK_STAIRS_EAST.getMinecraftId(), enumName, WoodType.OAK, face, upsideDown);
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
    public OakStairs getBlockFacing(final BlockFace face)
    {
        return getByID(Stairs.combine(face, this.upsideDown));
    }

    @Override
    public OakStairs getUpsideDown(final boolean upsideDown)
    {
        return getByID(Stairs.combine(this.face, upsideDown));
    }

    @Override
    public OakStairs getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public OakStairs getType(final int id)
    {
        return getByID(id);
    }

    @Override
    public OakStairs getType(final BlockFace face, final boolean upsideDown)
    {
        return getByID(Stairs.combine(face, upsideDown));
    }

    /**
     * Returns one of OakStairs sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of OakStairs or null
     */
    public static OakStairs getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of OakStairs sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of OakStairs or null
     */
    public static OakStairs getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Returns one of OakStairs sub-type based on facing direction and upside-down state.
     * It will never return null.
     *
     * @param blockFace  facing direction of stairs.
     * @param upsideDown if stairs should be upside-down.
     *
     * @return sub-type of OakStairs
     */
    public static OakStairs getOakStairs(final BlockFace blockFace, final boolean upsideDown)
    {
        return getByID(Stairs.combine(blockFace, upsideDown));
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final OakStairs element)
    {
        byID.put(element.getType(), element);
        byName.put(element.name(), element);
    }

    static
    {
        OakStairs.register(OAK_STAIRS_EAST);
        OakStairs.register(OAK_STAIRS_WEST);
        OakStairs.register(OAK_STAIRS_SOUTH);
        OakStairs.register(OAK_STAIRS_NORTH);
        OakStairs.register(OAK_STAIRS_EAST_UPSIDE_DOWN);
        OakStairs.register(OAK_STAIRS_WEST_UPSIDE_DOWN);
        OakStairs.register(OAK_STAIRS_SOUTH_UPSIDE_DOWN);
        OakStairs.register(OAK_STAIRS_NORTH_UPSIDE_DOWN);
    }
}
