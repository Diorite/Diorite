package org.diorite.material.blocks.nether;

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
 * Class representing block "NetherBrickStairs" and all its subtypes.
 */
public class NetherBrickStairs extends BlockMaterialData implements Stairs
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

    public static final NetherBrickStairs NETHER_BRICK_STAIRS_EAST              = new NetherBrickStairs();
    public static final NetherBrickStairs NETHER_BRICK_STAIRS_WEST              = new NetherBrickStairs("WEST", BlockFace.WEST, false);
    public static final NetherBrickStairs NETHER_BRICK_STAIRS_SOUTH             = new NetherBrickStairs("SOUTH", BlockFace.SOUTH, false);
    public static final NetherBrickStairs NETHER_BRICK_STAIRS_NORTH             = new NetherBrickStairs("NORTH", BlockFace.NORTH, false);
    public static final NetherBrickStairs NETHER_BRICK_STAIRS_EAST_UPSIDE_DOWN  = new NetherBrickStairs("EAST_UPSIDE_DOWN", BlockFace.EAST, true);
    public static final NetherBrickStairs NETHER_BRICK_STAIRS_WEST_UPSIDE_DOWN  = new NetherBrickStairs("WEST_UPSIDE_DOWN", BlockFace.WEST, true);
    public static final NetherBrickStairs NETHER_BRICK_STAIRS_SOUTH_UPSIDE_DOWN = new NetherBrickStairs("SOUTH_UPSIDE_DOWN", BlockFace.SOUTH, true);
    public static final NetherBrickStairs NETHER_BRICK_STAIRS_NORTH_UPSIDE_DOWN = new NetherBrickStairs("NORTH_UPSIDE_DOWN", BlockFace.NORTH, true);

    private static final Map<String, NetherBrickStairs>    byName = new SimpleStringHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<NetherBrickStairs> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    protected final BlockFace face;
    protected final boolean   upsideDown;

    @SuppressWarnings("MagicNumber")
    protected NetherBrickStairs()
    {
        super("NETHER_BRICK_STAIRS", 114, "minecraft:nether_brick_stairs", "EAST", (byte) 0x00);
        this.face = BlockFace.EAST;
        this.upsideDown = false;
    }

    public NetherBrickStairs(final String enumName, final BlockFace face, final boolean upsideDown)
    {
        super(NETHER_BRICK_STAIRS_EAST.name(), NETHER_BRICK_STAIRS_EAST.getId(), NETHER_BRICK_STAIRS_EAST.getMinecraftId(), enumName, Stairs.combine(face, upsideDown));
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
    public NetherBrickStairs getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public NetherBrickStairs getType(final int id)
    {
        return getByID(id);
    }

    @Override
    public boolean isUpsideDown()
    {
        return this.upsideDown;
    }

    @Override
    public NetherBrickStairs getUpsideDown(final boolean upsideDown)
    {
        return getByID(Stairs.combine(this.face, upsideDown));
    }

    @Override
    public BlockFace getBlockFacing()
    {
        return this.face;
    }

    @Override
    public NetherBrickStairs getBlockFacing(final BlockFace face)
    {
        return getByID(Stairs.combine(face, this.upsideDown));
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
    public NetherBrickStairs getType(final BlockFace face, final boolean activated)
    {
        return getByID(Stairs.combine(face, activated));
    }

    /**
     * Returns one of NetherBrickStairs sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of NetherBrickStairs or null
     */
    public static NetherBrickStairs getByID(final int id)
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
    public static NetherBrickStairs getByEnumName(final String name)
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
    public static NetherBrickStairs getNetherBrickStairs(final BlockFace face, final boolean upsideDown)
    {
        return getByID(Stairs.combine(face, upsideDown));
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final NetherBrickStairs element)
    {
        byID.put(element.getType(), element);
        byName.put(element.name(), element);
    }

    static
    {
        NetherBrickStairs.register(NETHER_BRICK_STAIRS_EAST);
        NetherBrickStairs.register(NETHER_BRICK_STAIRS_WEST);
        NetherBrickStairs.register(NETHER_BRICK_STAIRS_SOUTH);
        NetherBrickStairs.register(NETHER_BRICK_STAIRS_NORTH);
        NetherBrickStairs.register(NETHER_BRICK_STAIRS_EAST_UPSIDE_DOWN);
        NetherBrickStairs.register(NETHER_BRICK_STAIRS_WEST_UPSIDE_DOWN);
        NetherBrickStairs.register(NETHER_BRICK_STAIRS_SOUTH_UPSIDE_DOWN);
        NetherBrickStairs.register(NETHER_BRICK_STAIRS_NORTH_UPSIDE_DOWN);
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("face", this.face).append("upsideDown", this.upsideDown).toString();
    }
}
