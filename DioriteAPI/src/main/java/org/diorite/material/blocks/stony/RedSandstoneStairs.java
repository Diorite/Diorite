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
 * Class representing block "RedSandstoneStairs" and all its subtypes.
 */
public class RedSandstoneStairs extends BlockMaterialData implements Stairs
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final byte  USED_DATA_VALUES = 8;
    /**
     * Blast resistance of block, can be changed only before server start.
     * Final copy of blast resistance from {@link MagicNumbers} class.
     */
    public static final float BLAST_RESISTANCE = MagicNumbers.MATERIAL__RED_SANDSTONE_STAIRS__BLAST_RESISTANCE;
    /**
     * Hardness of block, can be changed only before server start.
     * Final copy of hardness from {@link MagicNumbers} class.
     */
    public static final float HARDNESS         = MagicNumbers.MATERIAL__RED_SANDSTONE_STAIRS__HARDNESS;

    public static final RedSandstoneStairs RED_SANDSTONE_STAIRS_EAST  = new RedSandstoneStairs();
    public static final RedSandstoneStairs RED_SANDSTONE_STAIRS_WEST  = new RedSandstoneStairs("WEST", BlockFace.WEST, false);
    public static final RedSandstoneStairs RED_SANDSTONE_STAIRS_SOUTH = new RedSandstoneStairs("SOUTH", BlockFace.SOUTH, false);
    public static final RedSandstoneStairs RED_SANDSTONE_STAIRS_NORTH = new RedSandstoneStairs("NORTH", BlockFace.NORTH, false);

    public static final RedSandstoneStairs RED_SANDSTONE_STAIRS_EAST_UPSIDE_DOWN  = new RedSandstoneStairs("EAST_UPSIDE_DOWN", BlockFace.EAST, true);
    public static final RedSandstoneStairs RED_SANDSTONE_STAIRS_WEST_UPSIDE_DOWN  = new RedSandstoneStairs("WEST_UPSIDE_DOWN", BlockFace.WEST, true);
    public static final RedSandstoneStairs RED_SANDSTONE_STAIRS_SOUTH_UPSIDE_DOWN = new RedSandstoneStairs("SOUTH_UPSIDE_DOWN", BlockFace.SOUTH, true);
    public static final RedSandstoneStairs RED_SANDSTONE_STAIRS_NORTH_UPSIDE_DOWN = new RedSandstoneStairs("NORTH_UPSIDE_DOWN", BlockFace.NORTH, true);

    private static final Map<String, RedSandstoneStairs>    byName = new SimpleStringHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<RedSandstoneStairs> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    protected final BlockFace face;
    protected final boolean   upsideDown;

    @SuppressWarnings("MagicNumber")
    protected RedSandstoneStairs()
    {
        super("RED_SANDSTONE_STAIRS", 180, "minecraft:red_sandstone_stairs", "EAST", (byte) 0x00);
        this.face = BlockFace.EAST;
        this.upsideDown = false;
    }

    public RedSandstoneStairs(final String enumName, final BlockFace face, final boolean upsideDown)
    {
        super(RED_SANDSTONE_STAIRS_EAST.name(), RED_SANDSTONE_STAIRS_EAST.getId(), RED_SANDSTONE_STAIRS_EAST.getMinecraftId(), enumName, Stairs.combine(face, upsideDown));
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
    public boolean isUpsideDown()
    {
        return this.upsideDown;
    }

    @Override
    public RedSandstoneStairs getUpsideDown(final boolean upsideDown)
    {
        return getByID(Stairs.combine(this.face, upsideDown));
    }

    @Override
    public RedSandstoneStairs getType(final BlockFace face, final boolean upsideDown)
    {
        return getByID(Stairs.combine(face, upsideDown));
    }

    @Override
    public BlockFace getBlockFacing()
    {
        return this.face;
    }

    @Override
    public RedSandstoneStairs getBlockFacing(final BlockFace face)
    {
        return getByID(Stairs.combine(face, this.upsideDown));
    }

    @Override
    public RedSandstoneStairs getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public RedSandstoneStairs getType(final int id)
    {
        return getByID(id);
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("face", this.face).append("upsideDown", this.upsideDown).toString();
    }

    /**
     * Returns one of RedSandstoneStairs sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of RedSandstoneStairs or null
     */
    public static RedSandstoneStairs getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of RedSandstoneStairs sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of RedSandstoneStairs or null
     */
    public static RedSandstoneStairs getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Returns one of RedSandstoneStairs sub-type based on facing direction and upside-down state.
     * It will never return null.
     *
     * @param blockFace  facing direction of stairs.
     * @param upsideDown if stairs should be upside-down.
     *
     * @return sub-type of RedSandstoneStairs
     */
    public static RedSandstoneStairs getRedSandstoneStairs(final BlockFace blockFace, final boolean upsideDown)
    {
        return getByID(Stairs.combine(blockFace, upsideDown));
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final RedSandstoneStairs element)
    {
        byID.put(element.getType(), element);
        byName.put(element.name(), element);
    }

    static
    {
        RedSandstoneStairs.register(RED_SANDSTONE_STAIRS_EAST);
        RedSandstoneStairs.register(RED_SANDSTONE_STAIRS_WEST);
        RedSandstoneStairs.register(RED_SANDSTONE_STAIRS_SOUTH);
        RedSandstoneStairs.register(RED_SANDSTONE_STAIRS_NORTH);
        RedSandstoneStairs.register(RED_SANDSTONE_STAIRS_EAST_UPSIDE_DOWN);
        RedSandstoneStairs.register(RED_SANDSTONE_STAIRS_WEST_UPSIDE_DOWN);
        RedSandstoneStairs.register(RED_SANDSTONE_STAIRS_SOUTH_UPSIDE_DOWN);
        RedSandstoneStairs.register(RED_SANDSTONE_STAIRS_NORTH_UPSIDE_DOWN);
    }
}
