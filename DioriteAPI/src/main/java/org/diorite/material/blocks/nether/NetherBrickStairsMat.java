package org.diorite.material.blocks.nether;

import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.BlockFace;
import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.material.BlockMaterialData;
import org.diorite.material.blocks.StairsMat;
import org.diorite.utils.collections.maps.CaseInsensitiveMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing block "NetherBrickStairs" and all its subtypes.
 */
public class NetherBrickStairsMat extends BlockMaterialData implements StairsMat
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final byte  USED_DATA_VALUES = 8;
    /**
     * Blast resistance of block, can be changed only before server start.
     * Final copy of blast resistance from {@link MagicNumbers} class.
     */
    public static final float BLAST_RESISTANCE = MagicNumbers.MATERIAL__NETHER_BRICK_STAIRS__BLAST_RESISTANCE;
    /**
     * Hardness of block, can be changed only before server start.
     * Final copy of hardness from {@link MagicNumbers} class.
     */
    public static final float HARDNESS         = MagicNumbers.MATERIAL__NETHER_BRICK_STAIRS__HARDNESS;

    public static final NetherBrickStairsMat NETHER_BRICK_STAIRS_EAST  = new NetherBrickStairsMat();
    public static final NetherBrickStairsMat NETHER_BRICK_STAIRS_WEST  = new NetherBrickStairsMat(BlockFace.WEST, false);
    public static final NetherBrickStairsMat NETHER_BRICK_STAIRS_SOUTH = new NetherBrickStairsMat(BlockFace.SOUTH, false);
    public static final NetherBrickStairsMat NETHER_BRICK_STAIRS_NORTH = new NetherBrickStairsMat(BlockFace.NORTH, false);

    public static final NetherBrickStairsMat NETHER_BRICK_STAIRS_EAST_UPSIDE_DOWN  = new NetherBrickStairsMat(BlockFace.EAST, true);
    public static final NetherBrickStairsMat NETHER_BRICK_STAIRS_WEST_UPSIDE_DOWN  = new NetherBrickStairsMat(BlockFace.WEST, true);
    public static final NetherBrickStairsMat NETHER_BRICK_STAIRS_SOUTH_UPSIDE_DOWN = new NetherBrickStairsMat(BlockFace.SOUTH, true);
    public static final NetherBrickStairsMat NETHER_BRICK_STAIRS_NORTH_UPSIDE_DOWN = new NetherBrickStairsMat(BlockFace.NORTH, true);

    private static final Map<String, NetherBrickStairsMat>    byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<NetherBrickStairsMat> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    protected final BlockFace face;
    protected final boolean   upsideDown;

    @SuppressWarnings("MagicNumber")
    protected NetherBrickStairsMat()
    {
        super("NETHER_BRICK_STAIRS", 114, "minecraft:nether_brick_stairs", "EAST", (byte) 0x00);
        this.face = BlockFace.EAST;
        this.upsideDown = false;
    }

    protected NetherBrickStairsMat(final BlockFace face, final boolean upsideDown)
    {
        super(NETHER_BRICK_STAIRS_EAST.name(), NETHER_BRICK_STAIRS_EAST.ordinal(), NETHER_BRICK_STAIRS_EAST.getMinecraftId(), face.name() + (upsideDown ? "_UPSIDE_DOWN" : ""), StairsMat.combine(face, upsideDown));
        this.face = face;
        this.upsideDown = upsideDown;
    }

    protected NetherBrickStairsMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type, final BlockFace face, final boolean upsideDown)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type);
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
    public NetherBrickStairsMat getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public NetherBrickStairsMat getType(final int id)
    {
        return getByID(id);
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("face", this.face).append("upsideDown", this.upsideDown).toString();
    }

    @Override
    public boolean isUpsideDown()
    {
        return this.upsideDown;
    }

    @Override
    public NetherBrickStairsMat getUpsideDown(final boolean upsideDown)
    {
        return getByID(StairsMat.combine(this.face, upsideDown));
    }

    @Override
    public NetherBrickStairsMat getType(final BlockFace face, final boolean upsideDown)
    {
        return getByID(StairsMat.combine(face, upsideDown));
    }

    @Override
    public BlockFace getBlockFacing()
    {
        return this.face;
    }

    @Override
    public NetherBrickStairsMat getBlockFacing(final BlockFace face)
    {
        return getByID(StairsMat.combine(face, this.upsideDown));
    }

    /**
     * Returns one of NetherBrickStairs sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of NetherBrickStairs or null
     */
    public static NetherBrickStairsMat getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of NetherBrickStairs sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of NetherBrickStairs or null
     */
    public static NetherBrickStairsMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Returns one of NetherBrickStairs sub-type based on {@link BlockFace} and upsideDown flag.
     * It will never return null;
     *
     * @param face       face of block, unsupported face will cause using face from default type.
     * @param upsideDown if stairs are upside down
     *
     * @return sub-type of NetherBrickStairs
     */
    public static NetherBrickStairsMat getNetherBrickStairs(final BlockFace face, final boolean upsideDown)
    {
        return getByID(StairsMat.combine(face, upsideDown));
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final NetherBrickStairsMat element)
    {
        byID.put((byte) element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    @Override
    public NetherBrickStairsMat[] types()
    {
        return NetherBrickStairsMat.netherBrickStairsTypes();
    }

    /**
     * @return array that contains all sub-types of this block.
     */
    public static NetherBrickStairsMat[] netherBrickStairsTypes()
    {
        return byID.values(new NetherBrickStairsMat[byID.size()]);
    }

    static
    {
        NetherBrickStairsMat.register(NETHER_BRICK_STAIRS_EAST);
        NetherBrickStairsMat.register(NETHER_BRICK_STAIRS_WEST);
        NetherBrickStairsMat.register(NETHER_BRICK_STAIRS_SOUTH);
        NetherBrickStairsMat.register(NETHER_BRICK_STAIRS_NORTH);
        NetherBrickStairsMat.register(NETHER_BRICK_STAIRS_EAST_UPSIDE_DOWN);
        NetherBrickStairsMat.register(NETHER_BRICK_STAIRS_WEST_UPSIDE_DOWN);
        NetherBrickStairsMat.register(NETHER_BRICK_STAIRS_SOUTH_UPSIDE_DOWN);
        NetherBrickStairsMat.register(NETHER_BRICK_STAIRS_NORTH_UPSIDE_DOWN);
    }
}
