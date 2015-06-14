package org.diorite.material.blocks.wooden.wood.stairs;

import java.util.Map;

import org.diorite.BlockFace;
import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.material.blocks.StairsMat;
import org.diorite.material.blocks.wooden.WoodTypeMat;
import org.diorite.utils.collections.maps.CaseInsensitiveMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing block "OakStairs" and all its subtypes.
 */
public class OakStairsMat extends WoodenStairsMat
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

    public static final OakStairsMat OAK_STAIRS_EAST  = new OakStairsMat();
    public static final OakStairsMat OAK_STAIRS_WEST  = new OakStairsMat(BlockFace.WEST, false);
    public static final OakStairsMat OAK_STAIRS_SOUTH = new OakStairsMat(BlockFace.SOUTH, false);
    public static final OakStairsMat OAK_STAIRS_NORTH = new OakStairsMat(BlockFace.NORTH, false);

    public static final OakStairsMat OAK_STAIRS_EAST_UPSIDE_DOWN  = new OakStairsMat(BlockFace.EAST, true);
    public static final OakStairsMat OAK_STAIRS_WEST_UPSIDE_DOWN  = new OakStairsMat(BlockFace.WEST, true);
    public static final OakStairsMat OAK_STAIRS_SOUTH_UPSIDE_DOWN = new OakStairsMat(BlockFace.SOUTH, true);
    public static final OakStairsMat OAK_STAIRS_NORTH_UPSIDE_DOWN = new OakStairsMat(BlockFace.NORTH, true);

    private static final Map<String, OakStairsMat>    byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<OakStairsMat> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    @SuppressWarnings("MagicNumber")
    protected OakStairsMat()
    {
        super("OAK_STAIRS", 53, "minecraft:oak_stairs", WoodTypeMat.OAK, BlockFace.EAST, false);
    }

    protected OakStairsMat(final BlockFace face, final boolean upsideDown)
    {
        super(OAK_STAIRS_EAST.name(), OAK_STAIRS_EAST.ordinal(), OAK_STAIRS_EAST.getMinecraftId(), WoodTypeMat.OAK, face, upsideDown);
    }

    protected OakStairsMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type, final WoodTypeMat woodType, final BlockFace face, final boolean upsideDown)
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
    public OakStairsMat getBlockFacing(final BlockFace face)
    {
        return getByID(StairsMat.combine(face, this.upsideDown));
    }

    @Override
    public OakStairsMat getUpsideDown(final boolean upsideDown)
    {
        return getByID(StairsMat.combine(this.face, upsideDown));
    }

    @Override
    public OakStairsMat getType(final BlockFace face, final boolean upsideDown)
    {
        return getByID(StairsMat.combine(face, upsideDown));
    }

    @Override
    public OakStairsMat getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public OakStairsMat getType(final int id)
    {
        return getByID(id);
    }

    /**
     * Returns one of OakStairs sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of OakStairs or null
     */
    public static OakStairsMat getByID(final int id)
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
    public static OakStairsMat getByEnumName(final String name)
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
    public static OakStairsMat getOakStairs(final BlockFace blockFace, final boolean upsideDown)
    {
        return getByID(StairsMat.combine(blockFace, upsideDown));
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final OakStairsMat element)
    {
        byID.put((byte) element.getType(), element);
        byName.put(element.name(), element);
    }

    @Override
    public OakStairsMat[] types()
    {
        return OakStairsMat.oakStairsTypes();
    }

    /**
     * @return array that contains all sub-types of this block.
     */
    public static OakStairsMat[] oakStairsTypes()
    {
        return byID.values(new OakStairsMat[byID.size()]);
    }

    static
    {
        OakStairsMat.register(OAK_STAIRS_EAST);
        OakStairsMat.register(OAK_STAIRS_WEST);
        OakStairsMat.register(OAK_STAIRS_SOUTH);
        OakStairsMat.register(OAK_STAIRS_NORTH);
        OakStairsMat.register(OAK_STAIRS_EAST_UPSIDE_DOWN);
        OakStairsMat.register(OAK_STAIRS_WEST_UPSIDE_DOWN);
        OakStairsMat.register(OAK_STAIRS_SOUTH_UPSIDE_DOWN);
        OakStairsMat.register(OAK_STAIRS_NORTH_UPSIDE_DOWN);
    }
}
