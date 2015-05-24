package org.diorite.material.blocks.others;

import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.BlockFace;
import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.material.BlockMaterialData;
import org.diorite.material.blocks.RotatableMat;
import org.diorite.material.blocks.RotateAxisMat;
import org.diorite.utils.collections.maps.CaseInsensitiveMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing block "HayBlock" and all its subtypes.
 */
public class HayBlockMat extends BlockMaterialData implements RotatableMat
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final byte  USED_DATA_VALUES = 3;
    /**
     * Blast resistance of block, can be changed only before server start.
     * Final copy of blast resistance from {@link MagicNumbers} class.
     */
    public static final float BLAST_RESISTANCE = MagicNumbers.MATERIAL__HAY_BLOCK__BLAST_RESISTANCE;
    /**
     * Hardness of block, can be changed only before server start.
     * Final copy of hardness from {@link MagicNumbers} class.
     */
    public static final float HARDNESS         = MagicNumbers.MATERIAL__HAY_BLOCK__HARDNESS;

    public static final HayBlockMat HAY_BLOCK_UP_DOWN     = new HayBlockMat();
    public static final HayBlockMat HAY_BLOCK_EAST_WEST   = new HayBlockMat(RotateAxisMat.EAST_WEST);
    public static final HayBlockMat HAY_BLOCK_NORTH_SOUTH = new HayBlockMat(RotateAxisMat.NORTH_SOUTH);

    private static final Map<String, HayBlockMat>    byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<HayBlockMat> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    protected final RotateAxisMat rotateAxis;

    @SuppressWarnings("MagicNumber")
    protected HayBlockMat()
    {
        super("HAY_BLOCK", 170, "minecraft:hay_block", "UP_DOWN", (byte) 0x00);
        this.rotateAxis = RotateAxisMat.UP_DOWN;
    }

    protected HayBlockMat(final RotateAxisMat rotateAxis)
    {
        super(HAY_BLOCK_UP_DOWN.name(), HAY_BLOCK_UP_DOWN.getId(), HAY_BLOCK_UP_DOWN.getMinecraftId(), rotateAxis.name(), combine(rotateAxis));
        this.rotateAxis = rotateAxis;
    }

    protected HayBlockMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type, final RotateAxisMat rotateAxis)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type);
        this.rotateAxis = rotateAxis;
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
    public HayBlockMat getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public HayBlockMat getType(final int id)
    {
        return getByID(id);
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("rotateAxis", this.rotateAxis).toString();
    }

    @Override
    public RotateAxisMat getRotateAxis()
    {
        return this.rotateAxis;
    }

    @Override
    public HayBlockMat getRotated(final RotateAxisMat axis)
    {
        return getByID(combine(axis));
    }

    @Override
    public HayBlockMat getBlockFacing(final BlockFace face)
    {
        switch (face)
        {
            case NORTH:
            case SOUTH:
                return getByID(combine(RotateAxisMat.NORTH_SOUTH));
            case EAST:
            case WEST:
                return getByID(combine(RotateAxisMat.EAST_WEST));
            case UP:
            case DOWN:
                return getByID(combine(RotateAxisMat.UP_DOWN));
            case SELF:
                return getByID(combine(RotateAxisMat.NONE));
            default:
                return getByID(combine(RotateAxisMat.UP_DOWN));
        }
    }

    private static byte combine(final RotateAxisMat axis)
    {
        if (axis == RotateAxisMat.NONE)
        {
            return 0x0;
        }
        return axis.getFlag();
    }

    /**
     * Returns one of HayBlock sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of HayBlock or null
     */
    public static HayBlockMat getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of HayBlock sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of HayBlock or null
     */
    public static HayBlockMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Returns one of HayBlock sub-type based on {@link RotateAxisMat}.
     * It will never return null;
     *
     * @param rotateAxis RotateAxis of HayBlock
     *
     * @return sub-type of HayBlock
     */
    public static HayBlockMat getHayBlock(final RotateAxisMat rotateAxis)
    {
        return getByID(combine(rotateAxis));
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final HayBlockMat element)
    {
        byID.put((byte) element.getType(), element);
        byName.put(element.name(), element);
    }

    @Override
    public HayBlockMat[] types()
    {
        return HayBlockMat.hayBlockTypes();
    }

    /**
     * @return array that contains all sub-types of this block.
     */
    public static HayBlockMat[] hayBlockTypes()
    {
        return byID.values(new HayBlockMat[byID.size()]);
    }

    static
    {
        HayBlockMat.register(HAY_BLOCK_UP_DOWN);
        HayBlockMat.register(HAY_BLOCK_EAST_WEST);
        HayBlockMat.register(HAY_BLOCK_NORTH_SOUTH);
    }
}
