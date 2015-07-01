package org.diorite.material.blocks.tools;

import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.BlockFace;
import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.material.BlockMaterialData;
import org.diorite.material.blocks.DirectionalMat;
import org.diorite.utils.collections.maps.CaseInsensitiveMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing block "EnderChest" and all its subtypes.
 */
public class EnderChestMat extends BlockMaterialData implements DirectionalMat
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final byte  USED_DATA_VALUES = 4;
    /**
     * Blast resistance of block, can be changed only before server start.
     * Final copy of blast resistance from {@link MagicNumbers} class.
     */
    public static final float BLAST_RESISTANCE = MagicNumbers.MATERIAL__ENDER_CHEST__BLAST_RESISTANCE;
    /**
     * Hardness of block, can be changed only before server start.
     * Final copy of hardness from {@link MagicNumbers} class.
     */
    public static final float HARDNESS         = MagicNumbers.MATERIAL__ENDER_CHEST__HARDNESS;

    public static final EnderChestMat ENDER_CHEST_NORTH = new EnderChestMat();
    public static final EnderChestMat ENDER_CHEST_SOUTH = new EnderChestMat(BlockFace.SOUTH);
    public static final EnderChestMat ENDER_CHEST_WEST  = new EnderChestMat(BlockFace.WEST);
    public static final EnderChestMat ENDER_CHEST_EAST  = new EnderChestMat(BlockFace.EAST);

    private static final Map<String, EnderChestMat>    byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<EnderChestMat> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    protected final BlockFace face;

    @SuppressWarnings("MagicNumber")
    protected EnderChestMat()
    {
        super("ENDER_CHEST", 65, "minecraft:ladder", "NORTH", (byte) 0x00);
        this.face = BlockFace.NORTH;
    }

    protected EnderChestMat(final BlockFace face)
    {
        super(ENDER_CHEST_NORTH.name(), ENDER_CHEST_NORTH.ordinal(), ENDER_CHEST_NORTH.getMinecraftId(), face.name(), combine(face));
        this.face = face;
    }

    protected EnderChestMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type, final BlockFace face)
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
    public EnderChestMat getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public EnderChestMat getType(final int id)
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
    public EnderChestMat getBlockFacing(final BlockFace face)
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
     * Returns one of EnderChest sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of EnderChest or null
     */
    public static EnderChestMat getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of EnderChest sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of EnderChest or null
     */
    public static EnderChestMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Returns one of EnderChest sub-type based on {@link BlockFace}
     * It will never return null.
     *
     * @param face facing of EnderChest.
     *
     * @return sub-type of EnderChest
     */
    public static EnderChestMat getEnderChest(final BlockFace face)
    {
        return getByID(combine(face));
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final EnderChestMat element)
    {
        byID.put((byte) element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    @Override
    public EnderChestMat[] types()
    {
        return EnderChestMat.enderChestTypes();
    }

    /**
     * @return array that contains all sub-types of this block.
     */
    public static EnderChestMat[] enderChestTypes()
    {
        return byID.values(new EnderChestMat[byID.size()]);
    }

    static
    {
        EnderChestMat.register(ENDER_CHEST_NORTH);
        EnderChestMat.register(ENDER_CHEST_SOUTH);
        EnderChestMat.register(ENDER_CHEST_WEST);
        EnderChestMat.register(ENDER_CHEST_EAST);
    }
}
