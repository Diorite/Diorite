package org.diorite.material.blocks.wooden.wood.stairs;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.BlockFace;
import org.diorite.material.WoodTypeMat;
import org.diorite.material.blocks.StairsMat;
import org.diorite.material.blocks.wooden.wood.WoodMat;
import org.diorite.utils.collections.maps.SimpleEnumMap;

/**
 * Abstract class for all WoodenStairs-based blocks
 */
public abstract class WoodenStairsMat extends WoodMat implements StairsMat
{
    protected final BlockFace face;
    protected final boolean   upsideDown;

    protected WoodenStairsMat(final String enumName, final int id, final String minecraftId, final WoodTypeMat woodType, final BlockFace face, final boolean upsideDown, final float hardness, final float blastResistance)
    {
        super(enumName, id, minecraftId, face.name() + (upsideDown ? "_UPSIDE_DOWN" : ""), (byte) 0, woodType, hardness, blastResistance);
        this.face = face;
        this.upsideDown = upsideDown;
    }

    protected WoodenStairsMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type, final WoodTypeMat woodType, final BlockFace face, final boolean upsideDown, final float hardness, final float blastResistance)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, woodType, hardness, blastResistance);
        this.face = face;
        this.upsideDown = upsideDown;
    }

    private static final SimpleEnumMap<WoodTypeMat, WoodenStairsMat> types = new SimpleEnumMap<>(6, SMALL_LOAD_FACTOR);

    @Override
    public abstract WoodenStairsMat getUpsideDown(final boolean upsideDown);

    @Override
    public abstract WoodenStairsMat getType(final BlockFace face, final boolean upsideDown);

    @Override
    public abstract WoodenStairsMat getBlockFacing(final BlockFace face);

    @Override
    public BlockFace getBlockFacing()
    {
        return this.face;
    }

    @Override
    public boolean isUpsideDown()
    {
        return this.upsideDown;
    }

    @Override
    public WoodenStairsMat getWoodType(final WoodTypeMat woodType)
    {
        return types.get(woodType).getType(this.face, this.upsideDown);
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("face", this.face).append("upsideDown", this.upsideDown).toString();
    }

    /**
     * Returns sub-type of {@link WoodTypeMat} WoodenStairs, based on {@link BlockFace} and upside-down state.
     *
     * @param woodType   {@link WoodTypeMat} of WoodenStairs
     * @param face       facing direction of WoodenStairs
     * @param upsideDown if stairs should be upside-down.
     *
     * @return sub-type of {@link WoodTypeMat} WoodenStairs.
     */
    public static WoodenStairsMat getWoodenStairs(final WoodTypeMat woodType, final BlockFace face, final boolean upsideDown)
    {
        return types.get(woodType).getType(face, upsideDown);
    }

    /**
     * Register new wood type to one of stairs, like OAK to OAK_STAIRS.
     *
     * @param type type of wood.
     * @param mat  stairs material.
     */
    public static void registerWoodType(final WoodTypeMat type, final WoodenStairsMat mat)
    {
        types.put(type, mat);
    }

    static
    {
        registerWoodType(WoodTypeMat.OAK, OAK_STAIRS);
        registerWoodType(WoodTypeMat.SPRUCE, SPRUCE_STAIRS);
        registerWoodType(WoodTypeMat.BIRCH, BIRCH_STAIRS);
        registerWoodType(WoodTypeMat.JUNGLE, JUNGLE_STAIRS);
        registerWoodType(WoodTypeMat.ACACIA, ACACIA_STAIRS);
        registerWoodType(WoodTypeMat.DARK_OAK, DARK_OAK_STAIRS);
    }
}
