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
 * Class representing block "JungleStairs" and all its subtypes.
 */
public class JungleStairs extends WoodenStairs
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

    public static final JungleStairs JUNGLE_STAIRS_EAST  = new JungleStairs();
    public static final JungleStairs JUNGLE_STAIRS_WEST  = new JungleStairs("WEST", BlockFace.WEST, false);
    public static final JungleStairs JUNGLE_STAIRS_SOUTH = new JungleStairs("SOUTH", BlockFace.SOUTH, false);
    public static final JungleStairs JUNGLE_STAIRS_NORTH = new JungleStairs("NORTH", BlockFace.NORTH, false);

    public static final JungleStairs JUNGLE_STAIRS_EAST_UPSIDE_DOWN  = new JungleStairs("EAST_UPSIDE_DOWN", BlockFace.EAST, true);
    public static final JungleStairs JUNGLE_STAIRS_WEST_UPSIDE_DOWN  = new JungleStairs("WEST_UPSIDE_DOWN", BlockFace.WEST, true);
    public static final JungleStairs JUNGLE_STAIRS_SOUTH_UPSIDE_DOWN = new JungleStairs("SOUTH_UPSIDE_DOWN", BlockFace.SOUTH, true);
    public static final JungleStairs JUNGLE_STAIRS_NORTH_UPSIDE_DOWN = new JungleStairs("NORTH_UPSIDE_DOWN", BlockFace.NORTH, true);

    private static final Map<String, JungleStairs>    byName = new SimpleStringHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<JungleStairs> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    @SuppressWarnings("MagicNumber")
    protected JungleStairs()
    {
        super("JUNGLE_STAIRS", 136, "minecraft:jungle_stairs", "EAST", WoodType.JUNGLE, BlockFace.EAST, false);
    }

    @SuppressWarnings("MagicNumber")
    public JungleStairs(final String enumName, final BlockFace face, final boolean upsideDown)
    {
        super(JUNGLE_STAIRS_EAST.name(), JUNGLE_STAIRS_EAST.getId(), JUNGLE_STAIRS_EAST.getMinecraftId(), enumName, WoodType.JUNGLE, face, upsideDown);
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
    public JungleStairs getBlockFacing(final BlockFace face)
    {
        return getByID(Stairs.combine(face, this.upsideDown));
    }

    @Override
    public JungleStairs getUpsideDown(final boolean upsideDown)
    {
        return getByID(Stairs.combine(this.face, upsideDown));
    }

    @Override
    public JungleStairs getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public JungleStairs getType(final int id)
    {
        return getByID(id);
    }

    @Override
    public JungleStairs getType(final BlockFace face, final boolean upsideDown)
    {
        return getByID(Stairs.combine(face, upsideDown));
    }

    /**
     * Returns one of JungleStairs sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of JungleStairs or null
     */
    public static JungleStairs getByID(final int id)
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
    public static JungleStairs getByEnumName(final String name)
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
    public static JungleStairs getJungleStairs(final BlockFace blockFace, final boolean upsideDown)
    {
        return getByID(Stairs.combine(blockFace, upsideDown));
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final JungleStairs element)
    {
        byID.put(element.getType(), element);
        byName.put(element.name(), element);
    }

    static
    {
        JungleStairs.register(JUNGLE_STAIRS_EAST);
        JungleStairs.register(JUNGLE_STAIRS_WEST);
        JungleStairs.register(JUNGLE_STAIRS_SOUTH);
        JungleStairs.register(JUNGLE_STAIRS_NORTH);
        JungleStairs.register(JUNGLE_STAIRS_EAST_UPSIDE_DOWN);
        JungleStairs.register(JUNGLE_STAIRS_WEST_UPSIDE_DOWN);
        JungleStairs.register(JUNGLE_STAIRS_SOUTH_UPSIDE_DOWN);
        JungleStairs.register(JUNGLE_STAIRS_NORTH_UPSIDE_DOWN);
    }
}
