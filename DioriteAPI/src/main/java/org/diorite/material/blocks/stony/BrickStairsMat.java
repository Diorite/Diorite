package org.diorite.material.blocks.stony;

import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.BlockFace;
import org.diorite.material.BlockMaterialData;
import org.diorite.material.Material;
import org.diorite.material.blocks.StairsMat;
import org.diorite.utils.collections.maps.CaseInsensitiveMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing block "BrickStairs" and all its subtypes.
 */
public class BrickStairsMat extends BlockMaterialData implements StairsMat
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final int USED_DATA_VALUES = 8;

    public static final BrickStairsMat BRICK_STAIRS_EAST  = new BrickStairsMat();
    public static final BrickStairsMat BRICK_STAIRS_WEST  = new BrickStairsMat(BlockFace.WEST, false);
    public static final BrickStairsMat BRICK_STAIRS_SOUTH = new BrickStairsMat(BlockFace.SOUTH, false);
    public static final BrickStairsMat BRICK_STAIRS_NORTH = new BrickStairsMat(BlockFace.NORTH, false);

    public static final BrickStairsMat BRICK_STAIRS_EAST_UPSIDE_DOWN  = new BrickStairsMat(BlockFace.EAST, true);
    public static final BrickStairsMat BRICK_STAIRS_WEST_UPSIDE_DOWN  = new BrickStairsMat(BlockFace.WEST, true);
    public static final BrickStairsMat BRICK_STAIRS_SOUTH_UPSIDE_DOWN = new BrickStairsMat(BlockFace.SOUTH, true);
    public static final BrickStairsMat BRICK_STAIRS_NORTH_UPSIDE_DOWN = new BrickStairsMat(BlockFace.NORTH, true);

    private static final Map<String, BrickStairsMat>    byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<BrickStairsMat> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    protected final BlockFace face;
    protected final boolean   upsideDown;

    @SuppressWarnings("MagicNumber")
    protected BrickStairsMat()
    {
        super("BRICK_STAIRS", 108, "minecraft:brick_stairs", "EAST", (byte) 0x00, 2, 30);
        this.face = BlockFace.EAST;
        this.upsideDown = false;
    }

    protected BrickStairsMat(final BlockFace face, final boolean upsideDown)
    {
        super(BRICK_STAIRS_EAST.name(), BRICK_STAIRS_EAST.ordinal(), BRICK_STAIRS_EAST.getMinecraftId(), face.name() + (upsideDown ? "_UPSIDE_DOWN" : ""), StairsMat.combine(face, upsideDown), BRICK_STAIRS_EAST.getHardness(), BRICK_STAIRS_EAST.getBlastResistance());
        this.face = face;
        this.upsideDown = upsideDown;
    }

    protected BrickStairsMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type, final BlockFace face, final boolean upsideDown, final float hardness, final float blastResistance)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, hardness, blastResistance);
        this.face = face;
        this.upsideDown = upsideDown;
    }

    @Override
    public Material ensureValidInventoryItem()
    {
        return Material.BRICK_STAIRS;
    }

    @Override
    public boolean isUpsideDown()
    {
        return this.upsideDown;
    }

    @Override
    public BrickStairsMat getUpsideDown(final boolean upsideDown)
    {
        return getByID(StairsMat.combine(this.face, upsideDown));
    }

    @Override
    public BrickStairsMat getType(final BlockFace face, final boolean upsideDown)
    {
        return getByID(StairsMat.combine(face, upsideDown));
    }

    @Override
    public BlockFace getBlockFacing()
    {
        return this.face;
    }

    @Override
    public BrickStairsMat getBlockFacing(final BlockFace face)
    {
        return getByID(StairsMat.combine(face, this.upsideDown));
    }

    @Override
    public BrickStairsMat getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public BrickStairsMat getType(final int id)
    {
        return getByID(id);
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("face", this.face).append("upsideDown", this.upsideDown).toString();
    }

    /**
     * Returns one of BrickStairs sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of BrickStairs or null
     */
    public static BrickStairsMat getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of BrickStairs sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of BrickStairs or null
     */
    public static BrickStairsMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Returns one of BrickStairs sub-type based on facing direction and upside-down state.
     * It will never return null.
     *
     * @param blockFace  facing direction of stairs.
     * @param upsideDown if stairs should be upside-down.
     *
     * @return sub-type of BrickStairs
     */
    public static BrickStairsMat getBrickStairs(final BlockFace blockFace, final boolean upsideDown)
    {
        return getByID(StairsMat.combine(blockFace, upsideDown));
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final BrickStairsMat element)
    {
        byID.put((byte) element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    @Override
    public BrickStairsMat[] types()
    {
        return BrickStairsMat.brickStairsTypes();
    }

    /**
     * @return array that contains all sub-types of this block.
     */
    public static BrickStairsMat[] brickStairsTypes()
    {
        return byID.values(new BrickStairsMat[byID.size()]);
    }

    static
    {
        BrickStairsMat.register(BRICK_STAIRS_EAST);
        BrickStairsMat.register(BRICK_STAIRS_WEST);
        BrickStairsMat.register(BRICK_STAIRS_SOUTH);
        BrickStairsMat.register(BRICK_STAIRS_NORTH);
        BrickStairsMat.register(BRICK_STAIRS_EAST_UPSIDE_DOWN);
        BrickStairsMat.register(BRICK_STAIRS_WEST_UPSIDE_DOWN);
        BrickStairsMat.register(BRICK_STAIRS_SOUTH_UPSIDE_DOWN);
        BrickStairsMat.register(BRICK_STAIRS_NORTH_UPSIDE_DOWN);
    }
}
