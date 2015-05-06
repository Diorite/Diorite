package org.diorite.material.blocks.tools;

import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.BlockFace;
import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.material.BlockMaterialData;
import org.diorite.material.blocks.DirectionalMat;
import org.diorite.utils.collections.SimpleStringHashMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing block "Furnace" and all its subtypes.
 */
public class FurnaceMat extends BlockMaterialData implements DirectionalMat
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

    public static final FurnaceMat FURNACE_NORTH = new FurnaceMat();
    public static final FurnaceMat FURNACE_SOUTH = new FurnaceMat(BlockFace.SOUTH);
    public static final FurnaceMat FURNACE_WEST  = new FurnaceMat(BlockFace.WEST);
    public static final FurnaceMat FURNACE_EAST  = new FurnaceMat(BlockFace.EAST);

    private static final Map<String, FurnaceMat>    byName = new SimpleStringHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<FurnaceMat> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    protected final BlockFace face;

    @SuppressWarnings("MagicNumber")
    protected FurnaceMat()
    {
        super("FURNACE", 65, "minecraft:ladder", "NORTH", (byte) 0x00);
        this.face = BlockFace.NORTH;
    }

    protected FurnaceMat(final BlockFace face)
    {
        super(FURNACE_NORTH.name(), FURNACE_NORTH.getId(), FURNACE_NORTH.getMinecraftId(), face.name(), combine(face));
        this.face = face;
    }

    protected FurnaceMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type, final BlockFace face)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type);
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
    public FurnaceMat getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public FurnaceMat getType(final int id)
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
    public FurnaceMat getBlockFacing(final BlockFace face)
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
    public static FurnaceMat getByID(final int id)
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
    public static FurnaceMat getByEnumName(final String name)
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
    public static FurnaceMat getFurnace(final BlockFace face)
    {
        return getByID(combine(face));
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final FurnaceMat element)
    {
        byID.put((byte) element.getType(), element);
        byName.put(element.name(), element);
    }

    static
    {
        FurnaceMat.register(FURNACE_NORTH);
        FurnaceMat.register(FURNACE_SOUTH);
        FurnaceMat.register(FURNACE_WEST);
        FurnaceMat.register(FURNACE_EAST);
    }
}
