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
 * Class representing block "Ladder" and all its subtypes.
 */
public class Ladder extends BlockMaterialData implements Directional
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final byte  USED_DATA_VALUES = 4;
    /**
     * Blast resistance of block, can be changed only before server start.
     * Final copy of blast resistance from {@link MagicNumbers} class.
     */
    public static final float BLAST_RESISTANCE = MagicNumbers.MATERIAL__LADDER__BLAST_RESISTANCE;
    /**
     * Hardness of block, can be changed only before server start.
     * Final copy of hardness from {@link MagicNumbers} class.
     */
    public static final float HARDNESS         = MagicNumbers.MATERIAL__LADDER__HARDNESS;

    public static final Ladder LADDER_NORTH = new Ladder();
    public static final Ladder LADDER_SOUTH = new Ladder(BlockFace.SOUTH);
    public static final Ladder LADDER_WEST  = new Ladder(BlockFace.WEST);
    public static final Ladder LADDER_EAST  = new Ladder(BlockFace.EAST);

    private static final Map<String, Ladder>    byName = new SimpleStringHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<Ladder> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    protected final BlockFace face;

    @SuppressWarnings("MagicNumber")
    protected Ladder()
    {
        super("LADDER_NORTH", 65, "minecraft:ladder", "NORTH", (byte) 0x00);
        this.face = BlockFace.NORTH;
    }

    public Ladder(final BlockFace face)
    {
        super(LADDER_NORTH.name(), LADDER_NORTH.getId(), LADDER_NORTH.getMinecraftId(), face.name(), combine(face));
        this.face = face;
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
    public Ladder getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public Ladder getType(final int id)
    {
        return getByID(id);
    }

    @Override
    public BlockFace getBlockFacing()
    {
        return this.face;
    }

    @Override
    public Ladder getBlockFacing(final BlockFace face)
    {
        return getByID(combine(face));
    }

    /**
     * Returns one of Ladder sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of Ladder or null
     */
    public static Ladder getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of Ladder sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of Ladder or null
     */
    public static Ladder getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Returns one of Ladder sub-type based on {@link BlockFace}
     * It will never return null.
     *
     * @param face facing of Ladder.
     *
     * @return sub-type of Ladder
     */
    public static Ladder getLadder(final BlockFace face)
    {
        return getByID(combine(face));
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final Ladder element)
    {
        byID.put(element.getType(), element);
        byName.put(element.name(), element);
    }

    static
    {
        Ladder.register(LADDER_NORTH);
        Ladder.register(LADDER_SOUTH);
        Ladder.register(LADDER_WEST);
        Ladder.register(LADDER_EAST);
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("face", this.face).toString();
    }
}
