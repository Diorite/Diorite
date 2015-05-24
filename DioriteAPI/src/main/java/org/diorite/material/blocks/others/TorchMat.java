package org.diorite.material.blocks.others;

import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.BlockFace;
import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.material.BlockMaterialData;
import org.diorite.material.blocks.AttachableMat;
import org.diorite.utils.collections.maps.CaseInsensitiveMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing block "Torch" and all its subtypes.
 */
public class TorchMat extends BlockMaterialData implements AttachableMat
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final byte  USED_DATA_VALUES = 5;
    /**
     * Blast resistance of block, can be changed only before server start.
     * Final copy of blast resistance from {@link MagicNumbers} class.
     */
    public static final float BLAST_RESISTANCE = MagicNumbers.MATERIAL__TORCH__BLAST_RESISTANCE;
    /**
     * Hardness of block, can be changed only before server start.
     * Final copy of hardness from {@link MagicNumbers} class.
     */
    public static final float HARDNESS         = MagicNumbers.MATERIAL__TORCH__HARDNESS;

    public static final TorchMat TORCH_EAST  = new TorchMat();
    public static final TorchMat TORCH_WEST  = new TorchMat(BlockFace.WEST);
    public static final TorchMat TORCH_SOUTH = new TorchMat(BlockFace.SOUTH);
    public static final TorchMat TORCH_NORTH = new TorchMat(BlockFace.NORTH);
    public static final TorchMat TORCH_UP    = new TorchMat(BlockFace.UP);

    private static final Map<String, TorchMat>    byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<TorchMat> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    protected final BlockFace face;

    @SuppressWarnings("MagicNumber")
    protected TorchMat()
    {
        super("TORCH_EAST", 50, "minecraft:torch", "EAST", (byte) 0x01);
        this.face = BlockFace.EAST;
    }

    protected TorchMat(final BlockFace face)
    {
        super(TORCH_EAST.name(), TORCH_EAST.getId(), TORCH_EAST.getMinecraftId(), face.name(), combine(face));
        this.face = face;
    }

    protected TorchMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type, final BlockFace face)
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
    public TorchMat getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public TorchMat getType(final int id)
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
    public TorchMat getBlockFacing(final BlockFace face)
    {
        return getByID(combine(face));
    }

    @Override
    public TorchMat getAttachedFace(final BlockFace face)
    {
        return getByID(combine(face.getOppositeFace()));
    }

    private static byte combine(final BlockFace face)
    {
        switch (face)
        {
            case WEST:
                return 0x2;
            case SOUTH:
                return 0x3;
            case NORTH:
                return 0x4;
            case UP:
                return 0x5;
            default:
                return 0x1;
        }
    }

    /**
     * Returns one of Torch sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of Torch or null
     */
    public static TorchMat getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of Torch sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of Torch or null
     */
    public static TorchMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Returns one of Torch sub-type based on {@link BlockFace}.
     * It will never return null;
     *
     * @param face facing of Torch
     *
     * @return sub-type of Torch
     */
    public static TorchMat getStandingSign(final BlockFace face)
    {
        return getByID(combine(face));
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final TorchMat element)
    {
        byID.put((byte) element.getType(), element);
        byName.put(element.name(), element);
    }

    @Override
    public TorchMat[] types()
    {
        return TorchMat.torchTypes();
    }

    /**
     * @return array that contains all sub-types of this block.
     */
    public static TorchMat[] torchTypes()
    {
        return byID.values(new TorchMat[byID.size()]);
    }

    static
    {
        TorchMat.register(TORCH_EAST);
        TorchMat.register(TORCH_WEST);
        TorchMat.register(TORCH_SOUTH);
        TorchMat.register(TORCH_NORTH);
        TorchMat.register(TORCH_UP);
    }
}
