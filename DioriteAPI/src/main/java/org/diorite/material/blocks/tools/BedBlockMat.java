package org.diorite.material.blocks.tools;

import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.BlockFace;
import org.diorite.material.BlockMaterialData;
import org.diorite.material.Material;
import org.diorite.material.blocks.DirectionalMat;
import org.diorite.utils.collections.maps.CaseInsensitiveMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing block "BedBlock" and all its subtypes.
 * <p>
 * NOTE: Will crash game when in inventory.
 */
public class BedBlockMat extends BlockMaterialData implements DirectionalMat
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final int USED_DATA_VALUES = 16;

    public static final byte OCCUPIED_FLAG  = 0x04;
    public static final byte HEAD_PART_FLAG = 0x08; // 1 for head, 0 for foot part

    public static final BedBlockMat BED_FOOT_SOUTH = new BedBlockMat();
    public static final BedBlockMat BED_FOOT_WEST  = new BedBlockMat(BlockFace.WEST, false, false);
    public static final BedBlockMat BED_FOOT_NORTH = new BedBlockMat(BlockFace.NORTH, false, false);
    public static final BedBlockMat BED_FOOT_EAST  = new BedBlockMat(BlockFace.EAST, false, false);

    public static final BedBlockMat BED_FOOT_SOUTH_OCCUPIED = new BedBlockMat(BlockFace.SOUTH, false, true);
    public static final BedBlockMat BED_FOOT_WEST_OCCUPIED  = new BedBlockMat(BlockFace.WEST, false, true);
    public static final BedBlockMat BED_FOOT_NORTH_OCCUPIED = new BedBlockMat(BlockFace.NORTH, false, true);
    public static final BedBlockMat BED_FOOT_EAST_OCCUPIED  = new BedBlockMat(BlockFace.EAST, false, true);

    public static final BedBlockMat BED_HEAD_SOUTH = new BedBlockMat(BlockFace.SOUTH, true, false);
    public static final BedBlockMat BED_HEAD_WEST  = new BedBlockMat(BlockFace.WEST, true, false);
    public static final BedBlockMat BED_HEAD_NORTH = new BedBlockMat(BlockFace.NORTH, true, false);
    public static final BedBlockMat BED_HEAD_EAST  = new BedBlockMat(BlockFace.EAST, true, false);

    public static final BedBlockMat BED_HEAD_SOUTH_OCCUPIED = new BedBlockMat(BlockFace.SOUTH, true, true);
    public static final BedBlockMat BED_HEAD_WEST_OCCUPIED  = new BedBlockMat(BlockFace.WEST, true, true);
    public static final BedBlockMat BED_HEAD_NORTH_OCCUPIED = new BedBlockMat(BlockFace.NORTH, true, true);
    public static final BedBlockMat BED_HEAD_EAST_OCCUPIED  = new BedBlockMat(BlockFace.EAST, true, true);

    private static final Map<String, BedBlockMat>    byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<BedBlockMat> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    private final BlockFace blockFacing;
    private final boolean   isHeadPart;
    private final boolean   isOccupied;

    @SuppressWarnings("MagicNumber")
    protected BedBlockMat()
    {
        super("BED_BLOCK", 26, "minecraft:bed", "FOOT_SOUTH", (byte) 0x00, 0.2f, 1);
        this.blockFacing = BlockFace.SOUTH;
        this.isHeadPart = false;
        this.isOccupied = false;
    }

    protected BedBlockMat(final BlockFace face, final boolean isHeadPart, final boolean isOccupied)
    {
        super(BED_FOOT_SOUTH.name(), BED_FOOT_SOUTH.ordinal(), BED_FOOT_SOUTH.getMinecraftId(), (isHeadPart ? "HEAD_" : "FOOT_") + face.name() + (isOccupied ? "_OCCUPIED" : ""), combine(face, isHeadPart, isOccupied), BED_FOOT_SOUTH.getHardness(), BED_FOOT_SOUTH.getBlastResistance());
        this.blockFacing = face;
        this.isHeadPart = isHeadPart;
        this.isOccupied = isOccupied;
    }

    protected BedBlockMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type, final BlockFace blockFacing, final boolean isHeadPart, final boolean isOccupied, final float hardness, final float blastResistance)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, hardness, blastResistance);
        this.blockFacing = blockFacing;
        this.isHeadPart = isHeadPart;
        this.isOccupied = isOccupied;
    }

    @Override
    public Material ensureValidInventoryItem()
    {
        return Material.BED;
    }

    public boolean isHeadPart()
    {
        return this.isHeadPart;
    }

    public boolean isOccupied()
    {
        return this.isOccupied;
    }

    @Override
    public BlockFace getBlockFacing()
    {
        return this.blockFacing;
    }

    @Override
    public BedBlockMat getBlockFacing(final BlockFace face)
    {
        return getByID(combine(face, this.isHeadPart, this.isOccupied));
    }

    public BedBlockMat getHeadPart(final boolean isHeadPart)
    {
        return getByID(combine(this.blockFacing, isHeadPart, this.isOccupied));
    }

    public BedBlockMat getOccuped(final boolean isOccupied)
    {
        return getByID(combine(this.blockFacing, this.isHeadPart, isOccupied));
    }

    @Override
    public BedBlockMat getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public BedBlockMat getType(final int id)
    {
        return getByID(id);
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("blockFacing", this.blockFacing).append("isHeadPart", this.isHeadPart).append("isOccupied", this.isOccupied).toString();
    }

    public BedBlockMat getType(final BlockFace face, final boolean isHeadPart, final boolean isOccupied)
    {
        return getByID(combine(face, isHeadPart, isOccupied));
    }

    private static byte combine(final BlockFace face, final boolean isHeadPart, final boolean isOccupied)
    {
        byte result = isHeadPart ? HEAD_PART_FLAG : 0x00;
        if (isOccupied)
        {
            result |= OCCUPIED_FLAG;
        }
        switch (face)
        {
            case WEST:
                result |= 0x01;
                break;
            case NORTH:
                result |= 0x02;
                break;
            case EAST:
                result |= 0x03;
                break;
            default:
                return result;
        }
        return result;
    }

    /**
     * Returns one of BedBlock sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of BedBlock or null
     */
    public static BedBlockMat getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of BedBlock sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of BedBlock or null
     */
    public static BedBlockMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    public static BedBlockMat getBed(final BlockFace face, final boolean isHeadPart, final boolean isOccupied)
    {
        return getByID(combine(face, isHeadPart, isOccupied));
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final BedBlockMat element)
    {
        byID.put((byte) element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    @Override
    public BedBlockMat[] types()
    {
        return BedBlockMat.bedBlockTypes();
    }

    /**
     * @return array that contains all sub-types of this block.
     */
    public static BedBlockMat[] bedBlockTypes()
    {
        return byID.values(new BedBlockMat[byID.size()]);
    }

    static
    {
        BedBlockMat.register(BED_FOOT_SOUTH);
        BedBlockMat.register(BED_FOOT_WEST);
        BedBlockMat.register(BED_FOOT_NORTH);
        BedBlockMat.register(BED_FOOT_EAST);
        BedBlockMat.register(BED_FOOT_SOUTH_OCCUPIED);
        BedBlockMat.register(BED_FOOT_WEST_OCCUPIED);
        BedBlockMat.register(BED_FOOT_NORTH_OCCUPIED);
        BedBlockMat.register(BED_FOOT_EAST_OCCUPIED);
        BedBlockMat.register(BED_HEAD_SOUTH);
        BedBlockMat.register(BED_HEAD_WEST);
        BedBlockMat.register(BED_HEAD_NORTH);
        BedBlockMat.register(BED_HEAD_EAST);
        BedBlockMat.register(BED_HEAD_SOUTH_OCCUPIED);
        BedBlockMat.register(BED_HEAD_WEST_OCCUPIED);
        BedBlockMat.register(BED_HEAD_NORTH_OCCUPIED);
        BedBlockMat.register(BED_HEAD_EAST_OCCUPIED);
    }
}
