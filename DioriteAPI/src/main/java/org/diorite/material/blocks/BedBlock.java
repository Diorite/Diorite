package org.diorite.material.blocks;

import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.BlockFace;
import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.material.BlockMaterialData;
import org.diorite.utils.collections.SimpleStringHashMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

public class BedBlock extends BlockMaterialData implements Directional
{
    public static final byte  USED_DATA_VALUES = 16;
    public static final float BLAST_RESISTANCE = MagicNumbers.MATERIAL__BED_BLOCK__BLAST_RESISTANCE;
    public static final float HARDNESS         = MagicNumbers.MATERIAL__BED_BLOCK__HARDNESS;
    public static final byte  OCCUPIED_FLAG    = 0x04;
    public static final byte  HEAD_PART_FLAG   = 0x08; // 1 for head, 0 for foot part

    public static final BedBlock BED_FOOT_SOUTH = new BedBlock();
    public static final BedBlock BED_FOOT_WEST  = new BedBlock("FOOT_WEST", BlockFace.WEST, false, false);
    public static final BedBlock BED_FOOT_NORTH = new BedBlock("FOOT_NORTH", BlockFace.NORTH, false, false);
    public static final BedBlock BED_FOOT_EAST  = new BedBlock("FOOT_EAST", BlockFace.EAST, false, false);

    public static final BedBlock BED_FOOT_SOUTH_OCCUPIED = new BedBlock("FOOT_SOUTH_OCCUPIED", BlockFace.SOUTH, false, true);
    public static final BedBlock BED_FOOT_WEST_OCCUPIED  = new BedBlock("FOOT_WEST_OCCUPIED", BlockFace.WEST, false, true);
    public static final BedBlock BED_FOOT_NORTH_OCCUPIED = new BedBlock("FOOT_NORTH_OCCUPIED", BlockFace.NORTH, false, true);
    public static final BedBlock BED_FOOT_EAST_OCCUPIED  = new BedBlock("FOOT_EAST_OCCUPIED", BlockFace.EAST, false, true);

    public static final BedBlock BED_HEAD_SOUTH = new BedBlock("HEAD_SOUTH", BlockFace.SOUTH, true, false);
    public static final BedBlock BED_HEAD_WEST  = new BedBlock("HEAD_WEST", BlockFace.WEST, true, false);
    public static final BedBlock BED_HEAD_NORTH = new BedBlock("HEAD_NORTH", BlockFace.NORTH, true, false);
    public static final BedBlock BED_HEAD_EAST  = new BedBlock("HEAD_EAST", BlockFace.EAST, true, false);

    public static final BedBlock BED_HEAD_SOUTH_OCCUPIED = new BedBlock("HEAD_SOUTH_OCCUPIED", BlockFace.SOUTH, true, true);
    public static final BedBlock BED_HEAD_WEST_OCCUPIED  = new BedBlock("HEAD_WEST_OCCUPIED", BlockFace.WEST, true, true);
    public static final BedBlock BED_HEAD_NORTH_OCCUPIED = new BedBlock("HEAD_NORTH_OCCUPIED", BlockFace.NORTH, true, true);
    public static final BedBlock BED_HEAD_EAST_OCCUPIED  = new BedBlock("HEAD_EAST_OCCUPIED", BlockFace.EAST, true, true);

    private static final Map<String, BedBlock>    byName = new SimpleStringHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<BedBlock> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    private final BlockFace blockFacing;
    private final boolean   isHeadPart;
    private final boolean   isOccupied;

    @SuppressWarnings("MagicNumber")
    protected BedBlock()
    {
        super("BED_BLOCK", 26, "minecraft:bed", "FOOT_SOUTH", (byte) 0x00);
        this.blockFacing = BlockFace.SOUTH;
        this.isHeadPart = false;
        this.isOccupied = false;
    }

    public BedBlock(final String enumName, final BlockFace face, final boolean isHeadPart, final boolean isOccupied)
    {
        super(BED_FOOT_SOUTH.name(), BED_FOOT_SOUTH.getId(), BED_FOOT_SOUTH.getMinecraftId(), enumName, combine(face, isHeadPart, isOccupied));
        this.blockFacing = face;
        this.isHeadPart = isHeadPart;
        this.isOccupied = isOccupied;
    }

    public BedBlock(final int maxStack, final String typeName, final BlockFace face, final boolean isHeadPart, final boolean isOccupied)
    {
        super(BED_FOOT_SOUTH.name(), BED_FOOT_SOUTH.getId(), BED_FOOT_SOUTH.getMinecraftId(), maxStack, typeName, combine(face, isHeadPart, isOccupied));
        this.blockFacing = face;
        this.isHeadPart = isHeadPart;
        this.isOccupied = isOccupied;
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
    public BedBlock getBlockFacing(final BlockFace face)
    {
        return getByID(combine(face, this.isHeadPart, this.isOccupied));
    }

    public BedBlock getHeadPart(final boolean isHeadPart)
    {
        return getByID(combine(this.blockFacing, isHeadPart, this.isOccupied));
    }

    public BedBlock getOccuped(final boolean isOccupied)
    {
        return getByID(combine(this.blockFacing, this.isHeadPart, isOccupied));
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
    public BedBlock getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public BedBlock getType(final int id)
    {
        return getByID(id);
    }

    public BedBlock getType(final BlockFace face, final boolean isHeadPart, final boolean isOccupied)
    {
        return getByID(combine(face, isHeadPart, isOccupied));
    }

    public static BedBlock getByID(final int id)
    {
        return byID.get((byte) id);
    }

    public static BedBlock getByEnumName(final String name)
    {
        return byName.get(name);
    }

    public static BedBlock getBed(final BlockFace face, final boolean isHeadPart, final boolean isOccupied)
    {
        return getByID(combine(face, isHeadPart, isOccupied));
    }

    public static void register(final BedBlock element)
    {
        byID.put(element.getType(), element);
        byName.put(element.name(), element);
    }

    static
    {
        BedBlock.register(BED_FOOT_SOUTH);
        BedBlock.register(BED_FOOT_WEST);
        BedBlock.register(BED_FOOT_NORTH);
        BedBlock.register(BED_FOOT_EAST);
        BedBlock.register(BED_FOOT_SOUTH_OCCUPIED);
        BedBlock.register(BED_FOOT_WEST_OCCUPIED);
        BedBlock.register(BED_FOOT_NORTH_OCCUPIED);
        BedBlock.register(BED_FOOT_EAST_OCCUPIED);
        BedBlock.register(BED_HEAD_SOUTH);
        BedBlock.register(BED_HEAD_WEST);
        BedBlock.register(BED_HEAD_NORTH);
        BedBlock.register(BED_HEAD_EAST);
        BedBlock.register(BED_HEAD_SOUTH_OCCUPIED);
        BedBlock.register(BED_HEAD_WEST_OCCUPIED);
        BedBlock.register(BED_HEAD_NORTH_OCCUPIED);
        BedBlock.register(BED_HEAD_EAST_OCCUPIED);
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("blockFacing", this.blockFacing).append("isHeadPart", this.isHeadPart).append("isOccupied", this.isOccupied).toString();
    }
}
