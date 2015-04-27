package org.diorite.material.blocks.stony;

import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.BlockFace;
import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.material.BlockMaterialData;
import org.diorite.material.blocks.Stairs;
import org.diorite.utils.collections.SimpleStringHashMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing block "StoneBrickStairs" and all its subtypes.
 */
public class StoneBrickStairs extends BlockMaterialData implements Stairs
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final byte  USED_DATA_VALUES = 8;
    /**
     * Blast resistance of block, can be changed only before server start.
     * Final copy of blast resistance from {@link MagicNumbers} class.
     */
    public static final float BLAST_RESISTANCE = MagicNumbers.MATERIAL__STONE_BRICK_STAIRS__BLAST_RESISTANCE;
    /**
     * Hardness of block, can be changed only before server start.
     * Final copy of hardness from {@link MagicNumbers} class.
     */
    public static final float HARDNESS         = MagicNumbers.MATERIAL__STONE_BRICK_STAIRS__HARDNESS;

    public static final StoneBrickStairs STONE_BRICK_STAIRS_EAST  = new StoneBrickStairs();
    public static final StoneBrickStairs STONE_BRICK_STAIRS_WEST  = new StoneBrickStairs("WEST", BlockFace.WEST, false);
    public static final StoneBrickStairs STONE_BRICK_STAIRS_SOUTH = new StoneBrickStairs("SOUTH", BlockFace.SOUTH, false);
    public static final StoneBrickStairs STONE_BRICK_STAIRS_NORTH = new StoneBrickStairs("NORTH", BlockFace.NORTH, false);

    public static final StoneBrickStairs STONE_BRICK_STAIRS_EAST_UPSIDE_DOWN  = new StoneBrickStairs("EAST_UPSIDE_DOWN", BlockFace.EAST, true);
    public static final StoneBrickStairs STONE_BRICK_STAIRS_WEST_UPSIDE_DOWN  = new StoneBrickStairs("WEST_UPSIDE_DOWN", BlockFace.WEST, true);
    public static final StoneBrickStairs STONE_BRICK_STAIRS_SOUTH_UPSIDE_DOWN = new StoneBrickStairs("SOUTH_UPSIDE_DOWN", BlockFace.SOUTH, true);
    public static final StoneBrickStairs STONE_BRICK_STAIRS_NORTH_UPSIDE_DOWN = new StoneBrickStairs("NORTH_UPSIDE_DOWN", BlockFace.NORTH, true);

    private static final Map<String, StoneBrickStairs>    byName = new SimpleStringHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<StoneBrickStairs> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    protected final BlockFace face;
    protected final boolean   upsideDown;

    @SuppressWarnings("MagicNumber")
    protected StoneBrickStairs()
    {
        super("STONE_BRICK_STAIRS", 109, "minecraft:stone_brick_stairs", "EAST", (byte) 0x00);
        this.face = BlockFace.EAST;
        this.upsideDown = false;
    }

    public StoneBrickStairs(final String enumName, final BlockFace face, final boolean upsideDown)
    {
        super(STONE_BRICK_STAIRS_EAST.name(), STONE_BRICK_STAIRS_EAST.getId(), STONE_BRICK_STAIRS_EAST.getMinecraftId(), enumName, Stairs.combine(face, upsideDown));
        this.face = face;
        this.upsideDown = upsideDown;
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
    public StoneBrickStairs getUpsideDown(final boolean upsideDown)
    {
        return getByID(Stairs.combine(this.face, upsideDown));
    }

    @Override
    public boolean isUpsideDown()
    {
        return this.upsideDown;
    }

    @Override
    public BlockFace getBlockFacing()
    {
        return this.face;
    }

    @Override
    public StoneBrickStairs getBlockFacing(final BlockFace face)
    {
        return getByID(Stairs.combine(face, this.upsideDown));
    }

    @Override
    public StoneBrickStairs getType(final BlockFace face, final boolean upsideDown)
    {
        return getByID(Stairs.combine(face, upsideDown));
    }

    @Override
    public StoneBrickStairs getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public StoneBrickStairs getType(final int id)
    {
        return getByID(id);
    }

    /**
     * Returns one of StoneBrickStairs sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of StoneBrickStairs or null
     */
    public static StoneBrickStairs getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of StoneBrickStairs sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of StoneBrickStairs or null
     */
    public static StoneBrickStairs getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Returns one of StoneBrickStairs sub-type based on facing direction and upside-down state.
     * It will never return null.
     *
     * @param blockFace  facing direction of stairs.
     * @param upsideDown if stairs should be upside-down.
     *
     * @return sub-type of StoneBrickStairs
     */
    public static StoneBrickStairs getStoneBrickStairs(final BlockFace blockFace, final boolean upsideDown)
    {
        return getByID(Stairs.combine(blockFace, upsideDown));
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final StoneBrickStairs element)
    {
        byID.put(element.getType(), element);
        byName.put(element.name(), element);
    }

    static
    {
        StoneBrickStairs.register(STONE_BRICK_STAIRS_EAST);
        StoneBrickStairs.register(STONE_BRICK_STAIRS_WEST);
        StoneBrickStairs.register(STONE_BRICK_STAIRS_SOUTH);
        StoneBrickStairs.register(STONE_BRICK_STAIRS_NORTH);
        StoneBrickStairs.register(STONE_BRICK_STAIRS_EAST_UPSIDE_DOWN);
        StoneBrickStairs.register(STONE_BRICK_STAIRS_WEST_UPSIDE_DOWN);
        StoneBrickStairs.register(STONE_BRICK_STAIRS_SOUTH_UPSIDE_DOWN);
        StoneBrickStairs.register(STONE_BRICK_STAIRS_NORTH_UPSIDE_DOWN);
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("face", this.face).append("upsideDown", this.upsideDown).toString();
    }
}
