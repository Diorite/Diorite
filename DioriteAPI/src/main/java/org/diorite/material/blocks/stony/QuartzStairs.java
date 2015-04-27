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
 * Class representing block "QuartzStairs" and all its subtypes.
 */
public class QuartzStairs extends BlockMaterialData implements Stairs
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final byte  USED_DATA_VALUES = 8;
    /**
     * Blast resistance of block, can be changed only before server start.
     * Final copy of blast resistance from {@link MagicNumbers} class.
     */
    public static final float BLAST_RESISTANCE = MagicNumbers.MATERIAL__QUARTZ_STAIRS__BLAST_RESISTANCE;
    /**
     * Hardness of block, can be changed only before server start.
     * Final copy of hardness from {@link MagicNumbers} class.
     */
    public static final float HARDNESS         = MagicNumbers.MATERIAL__QUARTZ_STAIRS__HARDNESS;

    public static final QuartzStairs QUARTZ_STAIRS_EAST  = new QuartzStairs();
    public static final QuartzStairs QUARTZ_STAIRS_WEST  = new QuartzStairs("WEST", BlockFace.WEST, false);
    public static final QuartzStairs QUARTZ_STAIRS_SOUTH = new QuartzStairs("SOUTH", BlockFace.SOUTH, false);
    public static final QuartzStairs QUARTZ_STAIRS_NORTH = new QuartzStairs("NORTH", BlockFace.NORTH, false);

    public static final QuartzStairs QUARTZ_STAIRS_EAST_UPSIDE_DOWN  = new QuartzStairs("EAST_UPSIDE_DOWN", BlockFace.EAST, true);
    public static final QuartzStairs QUARTZ_STAIRS_WEST_UPSIDE_DOWN  = new QuartzStairs("WEST_UPSIDE_DOWN", BlockFace.WEST, true);
    public static final QuartzStairs QUARTZ_STAIRS_SOUTH_UPSIDE_DOWN = new QuartzStairs("SOUTH_UPSIDE_DOWN", BlockFace.SOUTH, true);
    public static final QuartzStairs QUARTZ_STAIRS_NORTH_UPSIDE_DOWN = new QuartzStairs("NORTH_UPSIDE_DOWN", BlockFace.NORTH, true);

    private static final Map<String, QuartzStairs>    byName = new SimpleStringHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<QuartzStairs> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    protected final BlockFace face;
    protected final boolean   upsideDown;

    @SuppressWarnings("MagicNumber")
    protected QuartzStairs()
    {
        super("QUARTZ_STAIRS", 156, "minecraft:quartz_brick_stairs", "EAST", (byte) 0x00);
        this.face = BlockFace.EAST;
        this.upsideDown = false;
    }

    public QuartzStairs(final String enumName, final BlockFace face, final boolean upsideDown)
    {
        super(QUARTZ_STAIRS_EAST.name(), QUARTZ_STAIRS_EAST.getId(), QUARTZ_STAIRS_EAST.getMinecraftId(), enumName, Stairs.combine(face, upsideDown));
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
    public QuartzStairs getUpsideDown(final boolean upsideDown)
    {
        return getByID(Stairs.combine(this.face, upsideDown));
    }

    @Override
    public QuartzStairs getType(final BlockFace face, final boolean upsideDown)
    {
        return getByID(Stairs.combine(face, upsideDown));
    }

    @Override
    public BlockFace getBlockFacing()
    {
        return this.face;
    }

    @Override
    public QuartzStairs getBlockFacing(final BlockFace face)
    {
        return getByID(Stairs.combine(face, this.upsideDown));
    }

    @Override
    public QuartzStairs getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public QuartzStairs getType(final int id)
    {
        return getByID(id);
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("face", this.face).append("upsideDown", this.upsideDown).toString();
    }

    /**
     * Returns one of QuartzStairs sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of QuartzStairs or null
     */
    public static QuartzStairs getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of QuartzStairs sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of QuartzStairs or null
     */
    public static QuartzStairs getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Returns one of QuartzStairs sub-type based on facing direction and upside-down state.
     * It will never return null.
     *
     * @param blockFace  facing direction of stairs.
     * @param upsideDown if stairs should be upside-down.
     *
     * @return sub-type of QuartzStairs
     */
    public static QuartzStairs getQuartzStairs(final BlockFace blockFace, final boolean upsideDown)
    {
        return getByID(Stairs.combine(blockFace, upsideDown));
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final QuartzStairs element)
    {
        byID.put(element.getType(), element);
        byName.put(element.name(), element);
    }

    static
    {
        QuartzStairs.register(QUARTZ_STAIRS_EAST);
        QuartzStairs.register(QUARTZ_STAIRS_WEST);
        QuartzStairs.register(QUARTZ_STAIRS_SOUTH);
        QuartzStairs.register(QUARTZ_STAIRS_NORTH);
        QuartzStairs.register(QUARTZ_STAIRS_EAST_UPSIDE_DOWN);
        QuartzStairs.register(QUARTZ_STAIRS_WEST_UPSIDE_DOWN);
        QuartzStairs.register(QUARTZ_STAIRS_SOUTH_UPSIDE_DOWN);
        QuartzStairs.register(QUARTZ_STAIRS_NORTH_UPSIDE_DOWN);
    }
}
