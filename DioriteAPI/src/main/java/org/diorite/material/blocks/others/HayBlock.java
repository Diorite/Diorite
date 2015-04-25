package org.diorite.material.blocks.others;

import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.material.BlockMaterialData;
import org.diorite.material.blocks.Rotatable;
import org.diorite.material.blocks.RotateAxis;
import org.diorite.utils.collections.SimpleStringHashMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing block "HayBlock" and all its subtypes.
 */
public class HayBlock extends BlockMaterialData implements Rotatable
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

    public static final HayBlock HAY_BLOCK_UP_DOWN     = new HayBlock();
    public static final HayBlock HAY_BLOCK_EAST_WEST   = new HayBlock(RotateAxis.EAST_WEST);
    public static final HayBlock HAY_BLOCK_NORTH_SOUTH = new HayBlock(RotateAxis.NORTH_SOUTH);

    private static final Map<String, HayBlock>    byName = new SimpleStringHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<HayBlock> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    protected final RotateAxis rotateAxis;

    @SuppressWarnings("MagicNumber")
    protected HayBlock()
    {
        super("HAY_BLOCK", 170, "minecraft:hay_block", "UP_DOWN", (byte) 0x00);
        this.rotateAxis = RotateAxis.UP_DOWN;
    }

    public HayBlock(final RotateAxis rotateAxis)
    {
        super(HAY_BLOCK_UP_DOWN.name(), HAY_BLOCK_UP_DOWN.getId(), HAY_BLOCK_UP_DOWN.getMinecraftId(), rotateAxis.name(), combine(rotateAxis));
        this.rotateAxis = rotateAxis;
    }

    private static byte combine(final RotateAxis axis)
    {
        switch (axis)
        {
            case EAST_WEST:
                return 0x4;
            case NORTH_SOUTH:
                return 0x8;
            default:
                return 0x0;
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
    public HayBlock getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public HayBlock getType(final int id)
    {
        return getByID(id);
    }

    @Override
    public RotateAxis getRotateAxis()
    {
        return this.rotateAxis;
    }

    @Override
    public BlockMaterialData getRotated(final RotateAxis axis)
    {
        return getByID(combine(axis));
    }

    /**
     * Returns one of HayBlock sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of HayBlock or null
     */
    public static HayBlock getByID(final int id)
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
    public static HayBlock getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Returns one of HayBlock sub-type based on {@link RotateAxis}.
     * It will never return null;
     *
     * @param rotateAxis RotateAxis of HayBlock
     *
     * @return sub-type of HayBlock
     */
    public static HayBlock getHayBlock(final RotateAxis rotateAxis)
    {
        return getByID(combine(rotateAxis));
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final HayBlock element)
    {
        byID.put(element.getType(), element);
        byName.put(element.name(), element);
    }

    static
    {
        HayBlock.register(HAY_BLOCK_UP_DOWN);
        HayBlock.register(HAY_BLOCK_EAST_WEST);
        HayBlock.register(HAY_BLOCK_NORTH_SOUTH);
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("rotateAxis", this.rotateAxis).toString();
    }
}
