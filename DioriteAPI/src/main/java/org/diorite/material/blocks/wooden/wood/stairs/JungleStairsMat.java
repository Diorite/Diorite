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
 * Class representing block "JungleStairs" and all its subtypes.
 */
public class JungleStairsMat extends WoodenStairsMat
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final byte  USED_DATA_VALUES = 8;
    /**
     * Blast resistance of block, can be changed only before server start.
     * Final copy of blast resistance from {@link MagicNumbers} class.
     */
    public static final float BLAST_RESISTANCE = MagicNumbers.MATERIAL__JUNGLE_STAIRS__BLAST_RESISTANCE;
    /**
     * Hardness of block, can be changed only before server start.
     * Final copy of hardness from {@link MagicNumbers} class.
     */
    public static final float HARDNESS         = MagicNumbers.MATERIAL__JUNGLE_STAIRS__HARDNESS;

    public static final JungleStairsMat JUNGLE_STAIRS_EAST  = new JungleStairsMat();
    public static final JungleStairsMat JUNGLE_STAIRS_WEST  = new JungleStairsMat(BlockFace.WEST, false);
    public static final JungleStairsMat JUNGLE_STAIRS_SOUTH = new JungleStairsMat(BlockFace.SOUTH, false);
    public static final JungleStairsMat JUNGLE_STAIRS_NORTH = new JungleStairsMat(BlockFace.NORTH, false);

    public static final JungleStairsMat JUNGLE_STAIRS_EAST_UPSIDE_DOWN  = new JungleStairsMat(BlockFace.EAST, true);
    public static final JungleStairsMat JUNGLE_STAIRS_WEST_UPSIDE_DOWN  = new JungleStairsMat(BlockFace.WEST, true);
    public static final JungleStairsMat JUNGLE_STAIRS_SOUTH_UPSIDE_DOWN = new JungleStairsMat(BlockFace.SOUTH, true);
    public static final JungleStairsMat JUNGLE_STAIRS_NORTH_UPSIDE_DOWN = new JungleStairsMat(BlockFace.NORTH, true);

    private static final Map<String, JungleStairsMat>    byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<JungleStairsMat> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    @SuppressWarnings("MagicNumber")
    protected JungleStairsMat()
    {
        super("JUNGLE_STAIRS", 136, "minecraft:jungle_stairs", WoodTypeMat.JUNGLE, BlockFace.EAST, false);
    }

    protected JungleStairsMat(final BlockFace face, final boolean upsideDown)
    {
        super(JUNGLE_STAIRS_EAST.name(), JUNGLE_STAIRS_EAST.getId(), JUNGLE_STAIRS_EAST.getMinecraftId(), WoodTypeMat.JUNGLE, face, upsideDown);
    }

    protected JungleStairsMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type, final WoodTypeMat woodType, final BlockFace face, final boolean upsideDown)
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
    public JungleStairsMat getBlockFacing(final BlockFace face)
    {
        return getByID(StairsMat.combine(face, this.upsideDown));
    }

    @Override
    public JungleStairsMat getUpsideDown(final boolean upsideDown)
    {
        return getByID(StairsMat.combine(this.face, upsideDown));
    }

    @Override
    public JungleStairsMat getType(final BlockFace face, final boolean upsideDown)
    {
        return getByID(StairsMat.combine(face, upsideDown));
    }

    @Override
    public JungleStairsMat getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public JungleStairsMat getType(final int id)
    {
        return getByID(id);
    }

    /**
     * Returns one of JungleStairs sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of JungleStairs or null
     */
    public static JungleStairsMat getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of JungleStairs sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of JungleStairs or null
     */
    public static JungleStairsMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Returns one of JungleStairs sub-type based on facing direction and upside-down state.
     * It will never return null.
     *
     * @param blockFace  facing direction of stairs.
     * @param upsideDown if stairs should be upside-down.
     *
     * @return sub-type of JungleStairs
     */
    public static JungleStairsMat getJungleStairs(final BlockFace blockFace, final boolean upsideDown)
    {
        return getByID(StairsMat.combine(blockFace, upsideDown));
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final JungleStairsMat element)
    {
        byID.put((byte) element.getType(), element);
        byName.put(element.name(), element);
    }

    @Override
    public JungleStairsMat[] types()
    {
        return JungleStairsMat.jungleStairsTypes();
    }

    /**
     * @return array that contains all sub-types of this block.
     */
    public static JungleStairsMat[] jungleStairsTypes()
    {
        return byID.values(new JungleStairsMat[byID.size()]);
    }

    static
    {
        JungleStairsMat.register(JUNGLE_STAIRS_EAST);
        JungleStairsMat.register(JUNGLE_STAIRS_WEST);
        JungleStairsMat.register(JUNGLE_STAIRS_SOUTH);
        JungleStairsMat.register(JUNGLE_STAIRS_NORTH);
        JungleStairsMat.register(JUNGLE_STAIRS_EAST_UPSIDE_DOWN);
        JungleStairsMat.register(JUNGLE_STAIRS_WEST_UPSIDE_DOWN);
        JungleStairsMat.register(JUNGLE_STAIRS_SOUTH_UPSIDE_DOWN);
        JungleStairsMat.register(JUNGLE_STAIRS_NORTH_UPSIDE_DOWN);
    }
}
