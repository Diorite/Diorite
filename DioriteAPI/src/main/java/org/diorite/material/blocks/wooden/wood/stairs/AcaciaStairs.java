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
 * Class representing block "AcaciaStairs" and all its subtypes.
 */
public class AcaciaStairs extends WoodenStairs
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final byte  USED_DATA_VALUES = 8;
    /**
     * Blast resistance of block, can be changed only before server start.
     * Final copy of blast resistance from {@link MagicNumbers} class.
     */
    public static final float BLAST_RESISTANCE = MagicNumbers.MATERIAL__ACACIA_STAIRS__BLAST_RESISTANCE;
    /**
     * Hardness of block, can be changed only before server start.
     * Final copy of hardness from {@link MagicNumbers} class.
     */
    public static final float HARDNESS         = MagicNumbers.MATERIAL__ACACIA_STAIRS__HARDNESS;

    public static final AcaciaStairs ACACIA_STAIRS_EAST  = new AcaciaStairs();
    public static final AcaciaStairs ACACIA_STAIRS_WEST  = new AcaciaStairs("WEST", BlockFace.WEST, false);
    public static final AcaciaStairs ACACIA_STAIRS_SOUTH = new AcaciaStairs("SOUTH", BlockFace.SOUTH, false);
    public static final AcaciaStairs ACACIA_STAIRS_NORTH = new AcaciaStairs("NORTH", BlockFace.NORTH, false);

    public static final AcaciaStairs ACACIA_STAIRS_EAST_UPSIDE_DOWN  = new AcaciaStairs("EAST_UPSIDE_DOWN", BlockFace.EAST, true);
    public static final AcaciaStairs ACACIA_STAIRS_WEST_UPSIDE_DOWN  = new AcaciaStairs("WEST_UPSIDE_DOWN", BlockFace.WEST, true);
    public static final AcaciaStairs ACACIA_STAIRS_SOUTH_UPSIDE_DOWN = new AcaciaStairs("SOUTH_UPSIDE_DOWN", BlockFace.SOUTH, true);
    public static final AcaciaStairs ACACIA_STAIRS_NORTH_UPSIDE_DOWN = new AcaciaStairs("NORTH_UPSIDE_DOWN", BlockFace.NORTH, true);

    private static final Map<String, AcaciaStairs>    byName = new SimpleStringHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<AcaciaStairs> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    @SuppressWarnings("MagicNumber")
    protected AcaciaStairs()
    {
        super("ACACIA_STAIRS", 163, "minecraft:acacia_stairs", "EAST", WoodType.ACACIA, BlockFace.EAST, false);
    }

    @SuppressWarnings("MagicNumber")
    public AcaciaStairs(final String enumName, final BlockFace face, final boolean upsideDown)
    {
        super(ACACIA_STAIRS_EAST.name(), ACACIA_STAIRS_EAST.getId(), ACACIA_STAIRS_EAST.getMinecraftId(), enumName, WoodType.ACACIA, face, upsideDown);
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
    public AcaciaStairs getBlockFacing(final BlockFace face)
    {
        return getByID(Stairs.combine(face, this.upsideDown));
    }

    @Override
    public AcaciaStairs getUpsideDown(final boolean upsideDown)
    {
        return getByID(Stairs.combine(this.face, upsideDown));
    }

    @Override
    public AcaciaStairs getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public AcaciaStairs getType(final int id)
    {
        return getByID(id);
    }

    @Override
    public AcaciaStairs getType(final BlockFace face, final boolean upsideDown)
    {
        return getByID(Stairs.combine(face, upsideDown));
    }

    /**
     * Returns one of AcaciaStairs sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of AcaciaStairs or null
     */
    public static AcaciaStairs getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of AcaciaStairs sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of AcaciaStairs or null
     */
    public static AcaciaStairs getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Returns one of AcaciaStairs sub-type based on facing direction and upside-down state.
     * It will never return null.
     *
     * @param blockFace  facing direction of stairs.
     * @param upsideDown if stairs should be upside-down.
     *
     * @return sub-type of AcaciaStairs
     */
    public static AcaciaStairs getAcaciaStairs(final BlockFace blockFace, final boolean upsideDown)
    {
        return getByID(Stairs.combine(blockFace, upsideDown));
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final AcaciaStairs element)
    {
        byID.put(element.getType(), element);
        byName.put(element.name(), element);
    }

    static
    {
        AcaciaStairs.register(ACACIA_STAIRS_EAST);
        AcaciaStairs.register(ACACIA_STAIRS_WEST);
        AcaciaStairs.register(ACACIA_STAIRS_SOUTH);
        AcaciaStairs.register(ACACIA_STAIRS_NORTH);
        AcaciaStairs.register(ACACIA_STAIRS_EAST_UPSIDE_DOWN);
        AcaciaStairs.register(ACACIA_STAIRS_WEST_UPSIDE_DOWN);
        AcaciaStairs.register(ACACIA_STAIRS_SOUTH_UPSIDE_DOWN);
        AcaciaStairs.register(ACACIA_STAIRS_NORTH_UPSIDE_DOWN);
    }
}
