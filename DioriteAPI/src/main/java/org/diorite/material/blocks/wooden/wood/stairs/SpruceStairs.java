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
 * Class representing block "SpruceStairs" and all its subtypes.
 */
public class SpruceStairs extends WoodenStairs
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final byte  USED_DATA_VALUES = 8;
    /**
     * Blast resistance of block, can be changed only before server start.
     * Final copy of blast resistance from {@link MagicNumbers} class.
     */
    public static final float BLAST_RESISTANCE = MagicNumbers.MATERIAL__SPRUCE_STAIRS__BLAST_RESISTANCE;
    /**
     * Hardness of block, can be changed only before server start.
     * Final copy of hardness from {@link MagicNumbers} class.
     */
    public static final float HARDNESS         = MagicNumbers.MATERIAL__SPRUCE_STAIRS__HARDNESS;

    public static final SpruceStairs SPRUCE_STAIRS_EAST  = new SpruceStairs();
    public static final SpruceStairs SPRUCE_STAIRS_WEST  = new SpruceStairs("WEST", BlockFace.WEST, false);
    public static final SpruceStairs SPRUCE_STAIRS_SOUTH = new SpruceStairs("SOUTH", BlockFace.SOUTH, false);
    public static final SpruceStairs SPRUCE_STAIRS_NORTH = new SpruceStairs("NORTH", BlockFace.NORTH, false);

    public static final SpruceStairs SPRUCE_STAIRS_EAST_UPSIDE_DOWN  = new SpruceStairs("EAST_UPSIDE_DOWN", BlockFace.EAST, true);
    public static final SpruceStairs SPRUCE_STAIRS_WEST_UPSIDE_DOWN  = new SpruceStairs("WEST_UPSIDE_DOWN", BlockFace.WEST, true);
    public static final SpruceStairs SPRUCE_STAIRS_SOUTH_UPSIDE_DOWN = new SpruceStairs("SOUTH_UPSIDE_DOWN", BlockFace.SOUTH, true);
    public static final SpruceStairs SPRUCE_STAIRS_NORTH_UPSIDE_DOWN = new SpruceStairs("NORTH_UPSIDE_DOWN", BlockFace.NORTH, true);

    private static final Map<String, SpruceStairs>    byName = new SimpleStringHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<SpruceStairs> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    @SuppressWarnings("MagicNumber")
    protected SpruceStairs()
    {
        super("SPRUCE_STAIRS", 134, "minecraft:spruce_stairs", "EAST", WoodType.SPRUCE, BlockFace.EAST, false);
    }

    @SuppressWarnings("MagicNumber")
    public SpruceStairs(final String enumName, final BlockFace face, final boolean upsideDown)
    {
        super(SPRUCE_STAIRS_EAST.name(), SPRUCE_STAIRS_EAST.getId(), SPRUCE_STAIRS_EAST.getMinecraftId(), enumName, WoodType.SPRUCE, face, upsideDown);
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
    public SpruceStairs getBlockFacing(final BlockFace face)
    {
        return getByID(Stairs.combine(face, this.upsideDown));
    }

    @Override
    public SpruceStairs getUpsideDown(final boolean upsideDown)
    {
        return getByID(Stairs.combine(this.face, upsideDown));
    }

    @Override
    public SpruceStairs getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public SpruceStairs getType(final int id)
    {
        return getByID(id);
    }

    @Override
    public SpruceStairs getType(final BlockFace face, final boolean upsideDown)
    {
        return getByID(Stairs.combine(face, upsideDown));
    }

    /**
     * Returns one of SpruceStairs sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of SpruceStairs or null
     */
    public static SpruceStairs getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of SpruceStairs sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of SpruceStairs or null
     */
    public static SpruceStairs getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Returns one of SpruceStairs sub-type based on facing direction and upside-down state.
     * It will never return null.
     *
     * @param blockFace  facing direction of stairs.
     * @param upsideDown if stairs should be upside-down.
     *
     * @return sub-type of SpruceStairs
     */
    public static SpruceStairs getSpruceStairs(final BlockFace blockFace, final boolean upsideDown)
    {
        return getByID(Stairs.combine(blockFace, upsideDown));
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final SpruceStairs element)
    {
        byID.put(element.getType(), element);
        byName.put(element.name(), element);
    }

    static
    {
        SpruceStairs.register(SPRUCE_STAIRS_EAST);
        SpruceStairs.register(SPRUCE_STAIRS_WEST);
        SpruceStairs.register(SPRUCE_STAIRS_SOUTH);
        SpruceStairs.register(SPRUCE_STAIRS_NORTH);
        SpruceStairs.register(SPRUCE_STAIRS_EAST_UPSIDE_DOWN);
        SpruceStairs.register(SPRUCE_STAIRS_WEST_UPSIDE_DOWN);
        SpruceStairs.register(SPRUCE_STAIRS_SOUTH_UPSIDE_DOWN);
        SpruceStairs.register(SPRUCE_STAIRS_NORTH_UPSIDE_DOWN);
    }
}
