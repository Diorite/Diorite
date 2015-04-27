package org.diorite.material.blocks.tools;

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
 * Class representing block "TrappedChest" and all its subtypes.
 */
public class TrappedChest extends BlockMaterialData implements Directional
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final byte  USED_DATA_VALUES = 4;
    /**
     * Blast resistance of block, can be changed only before server start.
     * Final copy of blast resistance from {@link MagicNumbers} class.
     */
    public static final float BLAST_RESISTANCE = MagicNumbers.MATERIAL__TRAPPED_CHEST__BLAST_RESISTANCE;
    /**
     * Hardness of block, can be changed only before server start.
     * Final copy of hardness from {@link MagicNumbers} class.
     */
    public static final float HARDNESS         = MagicNumbers.MATERIAL__TRAPPED_CHEST__HARDNESS;

    public static final TrappedChest TRAPPED_CHEST_NORTH = new TrappedChest();
    public static final TrappedChest TRAPPED_CHEST_SOUTH = new TrappedChest(BlockFace.SOUTH);
    public static final TrappedChest TRAPPED_CHEST_WEST  = new TrappedChest(BlockFace.WEST);
    public static final TrappedChest TRAPPED_CHEST_EAST  = new TrappedChest(BlockFace.EAST);

    private static final Map<String, TrappedChest>    byName = new SimpleStringHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<TrappedChest> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    protected final BlockFace face;

    @SuppressWarnings("MagicNumber")
    protected TrappedChest()
    {
        super("TRAPPED_CHEST", 65, "minecraft:ladder", "NORTH", (byte) 0x00);
        this.face = BlockFace.NORTH;
    }

    public TrappedChest(final BlockFace face)
    {
        super(TRAPPED_CHEST_NORTH.name(), TRAPPED_CHEST_NORTH.getId(), TRAPPED_CHEST_NORTH.getMinecraftId(), face.name(), combine(face));
        this.face = face;
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
    public TrappedChest getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public TrappedChest getType(final int id)
    {
        return getByID(id);
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("face", this.face).toString();
    }

    @Override
    public BlockFace getBlockFacing()
    {
        return this.face;
    }

    @Override
    public TrappedChest getBlockFacing(final BlockFace face)
    {
        return getByID(combine(face));
    }

    private static byte combine(final BlockFace face)
    {
        switch (face)
        {
            case SOUTH:
                return 0x3;
            case WEST:
                return 0x4;
            case EAST:
                return 0x5;
            default:
                return 0x2;
        }
    }

    /**
     * Returns one of TrappedChest sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of TrappedChest or null
     */
    public static TrappedChest getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of TrappedChest sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of TrappedChest or null
     */
    public static TrappedChest getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Returns one of TrappedChest sub-type based on {@link BlockFace}
     * It will never return null.
     *
     * @param face facing of TrappedChest.
     *
     * @return sub-type of TrappedChest
     */
    public static TrappedChest getTrappedChest(final BlockFace face)
    {
        return getByID(combine(face));
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final TrappedChest element)
    {
        byID.put(element.getType(), element);
        byName.put(element.name(), element);
    }

    static
    {
        TrappedChest.register(TRAPPED_CHEST_NORTH);
        TrappedChest.register(TRAPPED_CHEST_SOUTH);
        TrappedChest.register(TRAPPED_CHEST_WEST);
        TrappedChest.register(TRAPPED_CHEST_EAST);
    }
}
