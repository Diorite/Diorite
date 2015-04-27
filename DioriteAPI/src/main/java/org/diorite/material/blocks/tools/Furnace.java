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
 * Class representing block "Furnace" and all its subtypes.
 */
public class Furnace extends BlockMaterialData implements Directional
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final byte  USED_DATA_VALUES = 4;
    /**
     * Blast resistance of block, can be changed only before server start.
     * Final copy of blast resistance from {@link MagicNumbers} class.
     */
    public static final float BLAST_RESISTANCE = MagicNumbers.MATERIAL__FURNACE__BLAST_RESISTANCE;
    /**
     * Hardness of block, can be changed only before server start.
     * Final copy of hardness from {@link MagicNumbers} class.
     */
    public static final float HARDNESS         = MagicNumbers.MATERIAL__FURNACE__HARDNESS;

    public static final Furnace FURNACE_NORTH = new Furnace();
    public static final Furnace FURNACE_SOUTH = new Furnace(BlockFace.SOUTH);
    public static final Furnace FURNACE_WEST  = new Furnace(BlockFace.WEST);
    public static final Furnace FURNACE_EAST  = new Furnace(BlockFace.EAST);

    private static final Map<String, Furnace>    byName = new SimpleStringHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<Furnace> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    protected final BlockFace face;

    @SuppressWarnings("MagicNumber")
    protected Furnace()
    {
        super("FURNACE", 65, "minecraft:ladder", "NORTH", (byte) 0x00);
        this.face = BlockFace.NORTH;
    }

    public Furnace(final BlockFace face)
    {
        super(FURNACE_NORTH.name(), FURNACE_NORTH.getId(), FURNACE_NORTH.getMinecraftId(), face.name(), combine(face));
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
    public Furnace getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public Furnace getType(final int id)
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
    public Furnace getBlockFacing(final BlockFace face)
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
     * Returns one of Furnace sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of Furnace or null
     */
    public static Furnace getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of Furnace sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of Furnace or null
     */
    public static Furnace getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Returns one of Furnace sub-type based on {@link BlockFace}
     * It will never return null.
     *
     * @param face facing of Furnace.
     *
     * @return sub-type of Furnace
     */
    public static Furnace getFurnace(final BlockFace face)
    {
        return getByID(combine(face));
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final Furnace element)
    {
        byID.put(element.getType(), element);
        byName.put(element.name(), element);
    }

    static
    {
        Furnace.register(FURNACE_NORTH);
        Furnace.register(FURNACE_SOUTH);
        Furnace.register(FURNACE_WEST);
        Furnace.register(FURNACE_EAST);
    }
}
