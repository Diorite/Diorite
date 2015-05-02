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
 * Class representing block "Chest" and all its subtypes.
 */
public class ChestMat extends BlockMaterialData implements DirectionalMat
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final byte  USED_DATA_VALUES = 4;
    /**
     * Blast resistance of block, can be changed only before server start.
     * Final copy of blast resistance from {@link MagicNumbers} class.
     */
    public static final float BLAST_RESISTANCE = MagicNumbers.MATERIAL__CHEST__BLAST_RESISTANCE;
    /**
     * Hardness of block, can be changed only before server start.
     * Final copy of hardness from {@link MagicNumbers} class.
     */
    public static final float HARDNESS         = MagicNumbers.MATERIAL__CHEST__HARDNESS;

    public static final ChestMat CHEST_NORTH = new ChestMat();
    public static final ChestMat CHEST_SOUTH = new ChestMat(BlockFace.SOUTH);
    public static final ChestMat CHEST_WEST  = new ChestMat(BlockFace.WEST);
    public static final ChestMat CHEST_EAST  = new ChestMat(BlockFace.EAST);

    private static final Map<String, ChestMat>    byName = new SimpleStringHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<ChestMat> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    protected final BlockFace face;

    @SuppressWarnings("MagicNumber")
    protected ChestMat()
    {
        super("CHEST", 54, "minecraft:chest", "NORTH", (byte) 0x00);
        this.face = BlockFace.NORTH;
    }

    protected ChestMat(final BlockFace face)
    {
        super(CHEST_NORTH.name(), CHEST_NORTH.getId(), CHEST_NORTH.getMinecraftId(), face.name(), combine(face));
        this.face = face;
    }

    protected ChestMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type, final BlockFace face)
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
    public ChestMat getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public ChestMat getType(final int id)
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
    public ChestMat getBlockFacing(final BlockFace face)
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
     * Returns one of Chest sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of Chest or null
     */
    public static ChestMat getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of Chest sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of Chest or null
     */
    public static ChestMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Returns one of Chest sub-type based on {@link BlockFace}
     * It will never return null.
     *
     * @param face facing of Chest.
     *
     * @return sub-type of Chest
     */
    public static ChestMat getChest(final BlockFace face)
    {
        return getByID(combine(face));
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final ChestMat element)
    {
        byID.put(element.getType(), element);
        byName.put(element.name(), element);
    }

    static
    {
        ChestMat.register(CHEST_NORTH);
        ChestMat.register(CHEST_SOUTH);
        ChestMat.register(CHEST_WEST);
        ChestMat.register(CHEST_EAST);
    }
}
