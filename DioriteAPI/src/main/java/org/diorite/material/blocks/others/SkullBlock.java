package org.diorite.material.blocks.others;

import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.BlockFace;
import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.material.BlockMaterialData;
import org.diorite.material.blocks.Directional;
import org.diorite.utils.collections.SimpleStringHashMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing block "SkullBlock" and all its subtypes.
 */
public class SkullBlock extends BlockMaterialData implements Directional
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final byte  USED_DATA_VALUES = 5;
    /**
     * Blast resistance of block, can be changed only before server start.
     * Final copy of blast resistance from {@link MagicNumbers} class.
     */
    public static final float BLAST_RESISTANCE = MagicNumbers.MATERIAL__SKULL_BLOCK__BLAST_RESISTANCE;
    /**
     * Hardness of block, can be changed only before server start.
     * Final copy of hardness from {@link MagicNumbers} class.
     */
    public static final float HARDNESS         = MagicNumbers.MATERIAL__SKULL_BLOCK__HARDNESS;

    public static final SkullBlock SKULL_BLOCK_FLOOR      = new SkullBlock();
    public static final SkullBlock SKULL_BLOCK_WALL_NORTH = new SkullBlock("WALL_NORTH", BlockFace.NORTH);
    public static final SkullBlock SKULL_BLOCK_WALL_SOUTH = new SkullBlock("WALL_SOUTH", BlockFace.SOUTH);
    public static final SkullBlock SKULL_BLOCK_WALL_EAST  = new SkullBlock("WALL_EAST", BlockFace.EAST);
    public static final SkullBlock SKULL_BLOCK_WALL_WEST  = new SkullBlock("WALL_WEST", BlockFace.WEST);

    private static final Map<String, SkullBlock>    byName = new SimpleStringHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<SkullBlock> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    protected final BlockFace face;
    protected final boolean   onWall;

    @SuppressWarnings("MagicNumber")
    protected SkullBlock()
    {
        super("SKULL_BLOCK_FLOOR", 144, "minecraft:skull", "FLOOR", (byte) 0x01);
        this.face = null;
        this.onWall = false;
    }

    public SkullBlock(final String enumName, final BlockFace face)
    {
        super(SKULL_BLOCK_FLOOR.name(), SKULL_BLOCK_FLOOR.getId(), SKULL_BLOCK_FLOOR.getMinecraftId(), enumName, combine(face));
        this.face = face;
        this.onWall = true;
    }

    private static byte combine(final BlockFace face)
    {
        if (face == null)
        {
            return 0x1;
        }
        switch (face)
        {
            case NORTH:
                return 0x2;
            case SOUTH:
                return 0x3;
            case EAST:
                return 0x4;
            case WEST:
                return 0x5;
            default:
                return 0x1;
        }
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
    public SkullBlock getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public SkullBlock getType(final int id)
    {
        return getByID(id);
    }

    /**
     * @return facing firection of skull, or null if skull is on ground.
     */
    @Override
    public BlockFace getBlockFacing()
    {
        return this.face;
    }

    /**
     * Returns sub-type of SkullBlock based on {@link BlockFace}.
     * Use null of {@link BlockFace.SELF} to get floor type.
     *
     * @param face facing of SkullBlock
     *
     * @return sub-type of SkullBlock
     */
    @Override
    public SkullBlock getBlockFacing(final BlockFace face)
    {
        return getByID(combine(face));
    }

    /**
     * Returns one of SkullBlock sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of SkullBlock or null
     */
    public static SkullBlock getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of SkullBlock sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of SkullBlock or null
     */
    public static SkullBlock getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Returns one of SkullBlock sub-type based on {@link BlockFace}.
     * Use null of {@link BlockFace.SELF} to get floor type.
     * It will never return null;
     *
     * @param face facing of SkullBlock
     *
     * @return sub-type of SkullBlock
     */
    public static SkullBlock getSkullBlock(final BlockFace face)
    {
        return getByID(combine(face));
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final SkullBlock element)
    {
        byID.put(element.getType(), element);
        byName.put(element.name(), element);
    }

    static
    {
        SkullBlock.register(SKULL_BLOCK_FLOOR);
        SkullBlock.register(SKULL_BLOCK_WALL_NORTH);
        SkullBlock.register(SKULL_BLOCK_WALL_SOUTH);
        SkullBlock.register(SKULL_BLOCK_WALL_EAST);
        SkullBlock.register(SKULL_BLOCK_WALL_WEST);
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("face", this.face).append("onWall", this.onWall).toString();
    }
}
