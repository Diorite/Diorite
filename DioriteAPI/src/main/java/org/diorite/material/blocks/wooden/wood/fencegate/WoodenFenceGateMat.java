package org.diorite.material.blocks.wooden.wood.fencegate;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.BlockFace;
import org.diorite.material.WoodTypeMat;
import org.diorite.material.blocks.FenceGateMat;
import org.diorite.material.blocks.wooden.wood.WoodMat;
import org.diorite.utils.collections.maps.SimpleEnumMap;

/**
 * Abstract class for all WoodenFenceGate-based blocks
 */
public abstract class WoodenFenceGateMat extends WoodMat implements FenceGateMat
{
    protected final BlockFace face;
    protected final boolean   open;

    protected WoodenFenceGateMat(final String enumName, final int id, final String minecraftId, final WoodTypeMat woodType, final BlockFace face, final boolean open, final float hardness, final float blastResistance)
    {
        super(enumName, id, minecraftId, face.name() + (open ? "_OPEN" : ""), FenceGateMat.combine(face, open), woodType, hardness, blastResistance);
        this.face = face;
        this.open = open;
    }

    protected WoodenFenceGateMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type, final WoodTypeMat woodType, final BlockFace face, final boolean open, final float hardness, final float blastResistance)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, woodType, hardness, blastResistance);
        this.face = face;
        this.open = open;
    }

    private static final SimpleEnumMap<WoodTypeMat, WoodenFenceGateMat> types = new SimpleEnumMap<>(6, SMALL_LOAD_FACTOR);

    @Override
    public abstract WoodenFenceGateMat getType(final BlockFace face, final boolean open);

    @Override
    public BlockFace getBlockFacing()
    {
        return this.face;
    }

    @Override
    public boolean isOpen()
    {
        return this.open;
    }

    @Override
    public WoodenFenceGateMat getWoodType(final WoodTypeMat woodType)
    {
        return types.get(woodType).getType(this.face, this.open);
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("face", this.face).append("open", this.open).toString();
    }

    /**
     * Returns sub-type of {@link WoodenFenceGateMat}, based on {@link WoodTypeMat}, {@link BlockFace} and open state.
     *
     * @param woodType {@link WoodTypeMat} of WoodenFenceGate
     * @param face     facing direction of WoodenFenceGate
     * @param open     if gate should be open.
     *
     * @return sub-type of {@link WoodenFenceGateMat}.
     */
    public static WoodenFenceGateMat getWoodenFenceGate(final WoodTypeMat woodType, final BlockFace face, final boolean open)
    {
        return types.get(woodType).getType(face, open);
    }

    /**
     * Register new wood type to one of fence gates, like OAK to OAK_FENCE_GATE.
     *
     * @param type type of wood.
     * @param mat  fence gate material.
     */
    public static void registerWoodType(final WoodTypeMat type, final WoodenFenceGateMat mat)
    {
        types.put(type, mat);
    }

    static
    {
        registerWoodType(WoodTypeMat.OAK, OAK_FENCE_GATE);
        registerWoodType(WoodTypeMat.SPRUCE, SPRUCE_FENCE_GATE);
        registerWoodType(WoodTypeMat.BIRCH, BIRCH_FENCE_GATE);
        registerWoodType(WoodTypeMat.JUNGLE, JUNGLE_FENCE_GATE);
        registerWoodType(WoodTypeMat.ACACIA, ACACIA_FENCE_GATE);
        registerWoodType(WoodTypeMat.DARK_OAK, DARK_OAK_FENCE_GATE);
    }
}
