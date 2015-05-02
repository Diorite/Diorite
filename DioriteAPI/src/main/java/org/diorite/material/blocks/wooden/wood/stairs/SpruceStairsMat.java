package org.diorite.material.blocks.wooden.wood.stairs;

import java.util.Map;

import org.diorite.BlockFace;
import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.material.blocks.StairsMat;
import org.diorite.material.blocks.wooden.WoodTypeMat;
import org.diorite.utils.collections.SimpleStringHashMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing block "SpruceStairs" and all its subtypes.
 */
public class SpruceStairsMat extends WoodenStairsMat
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

    public static final SpruceStairsMat SPRUCE_STAIRS_EAST  = new SpruceStairsMat();
    public static final SpruceStairsMat SPRUCE_STAIRS_WEST  = new SpruceStairsMat(BlockFace.WEST, false);
    public static final SpruceStairsMat SPRUCE_STAIRS_SOUTH = new SpruceStairsMat(BlockFace.SOUTH, false);
    public static final SpruceStairsMat SPRUCE_STAIRS_NORTH = new SpruceStairsMat(BlockFace.NORTH, false);

    public static final SpruceStairsMat SPRUCE_STAIRS_EAST_UPSIDE_DOWN  = new SpruceStairsMat(BlockFace.EAST, true);
    public static final SpruceStairsMat SPRUCE_STAIRS_WEST_UPSIDE_DOWN  = new SpruceStairsMat(BlockFace.WEST, true);
    public static final SpruceStairsMat SPRUCE_STAIRS_SOUTH_UPSIDE_DOWN = new SpruceStairsMat(BlockFace.SOUTH, true);
    public static final SpruceStairsMat SPRUCE_STAIRS_NORTH_UPSIDE_DOWN = new SpruceStairsMat(BlockFace.NORTH, true);

    private static final Map<String, SpruceStairsMat>    byName = new SimpleStringHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<SpruceStairsMat> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    @SuppressWarnings("MagicNumber")
    protected SpruceStairsMat()
    {
        super("SPRUCE_STAIRS", 134, "minecraft:spruce_stairs", WoodTypeMat.SPRUCE, BlockFace.EAST, false);
    }

    protected SpruceStairsMat(final BlockFace face, final boolean upsideDown)
    {
        super(SPRUCE_STAIRS_EAST.name(), SPRUCE_STAIRS_EAST.getId(), SPRUCE_STAIRS_EAST.getMinecraftId(), WoodTypeMat.SPRUCE, face, upsideDown);
    }

    protected SpruceStairsMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type, final WoodTypeMat woodType, final BlockFace face, final boolean upsideDown)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, woodType, face, upsideDown);
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
    public SpruceStairsMat getBlockFacing(final BlockFace face)
    {
        return getByID(StairsMat.combine(face, this.upsideDown));
    }

    @Override
    public SpruceStairsMat getUpsideDown(final boolean upsideDown)
    {
        return getByID(StairsMat.combine(this.face, upsideDown));
    }

    @Override
    public SpruceStairsMat getType(final BlockFace face, final boolean upsideDown)
    {
        return getByID(StairsMat.combine(face, upsideDown));
    }

    @Override
    public SpruceStairsMat getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public SpruceStairsMat getType(final int id)
    {
        return getByID(id);
    }

    /**
     * Returns one of SpruceStairs sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of SpruceStairs or null
     */
    public static SpruceStairsMat getByID(final int id)
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
    public static SpruceStairsMat getByEnumName(final String name)
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
    public static SpruceStairsMat getSpruceStairs(final BlockFace blockFace, final boolean upsideDown)
    {
        return getByID(StairsMat.combine(blockFace, upsideDown));
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final SpruceStairsMat element)
    {
        byID.put(element.getType(), element);
        byName.put(element.name(), element);
    }

    static
    {
        SpruceStairsMat.register(SPRUCE_STAIRS_EAST);
        SpruceStairsMat.register(SPRUCE_STAIRS_WEST);
        SpruceStairsMat.register(SPRUCE_STAIRS_SOUTH);
        SpruceStairsMat.register(SPRUCE_STAIRS_NORTH);
        SpruceStairsMat.register(SPRUCE_STAIRS_EAST_UPSIDE_DOWN);
        SpruceStairsMat.register(SPRUCE_STAIRS_WEST_UPSIDE_DOWN);
        SpruceStairsMat.register(SPRUCE_STAIRS_SOUTH_UPSIDE_DOWN);
        SpruceStairsMat.register(SPRUCE_STAIRS_NORTH_UPSIDE_DOWN);
    }
}
