package org.diorite.material.blocks.liquid;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.material.BlockMaterialData;

public abstract class LiquidMat extends BlockMaterialData
{
    public static final byte FALLING_FLAG = 0x08;

    protected final LiquidStageMat stage;
    protected final LiquidTypeMat  liquidType;

    protected LiquidMat(final String enumName, final int id, final String minecraftId, final String typeName, final LiquidStageMat stage, final LiquidTypeMat liquidType, final float hardness, final float blastResistance)
    {
        super(enumName, id, minecraftId, typeName, stage.getDataValue(), hardness, blastResistance);
        this.stage = stage;
        this.liquidType = liquidType;
    }

    protected LiquidMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final LiquidStageMat stage, final LiquidTypeMat liquidType, final float hardness, final float blastResistance)
    {
        super(enumName, id, minecraftId, maxStack, typeName, stage.getDataValue(), hardness, blastResistance);
        this.stage = stage;
        this.liquidType = liquidType;
    }

    public abstract LiquidMat getStage(LiquidStageMat stage);

    public abstract LiquidMat getLiquidType(LiquidTypeMat type);

    public LiquidMat getNormalType()
    {
        return this.getLiquidType(LiquidTypeMat.NORMAL);
    }

    public LiquidMat getStillType()
    {
        return this.getLiquidType(LiquidTypeMat.STILL);
    }

    public LiquidMat getOtherType()
    {
        return this.getLiquidType((this.liquidType.isStill()) ? LiquidTypeMat.NORMAL : LiquidTypeMat.STILL);
    }

    public LiquidMat nextStage()
    {
        return this.getStage(this.stage.getNextStage());
    }

    public LiquidMat previousStage()
    {
        return this.getStage(this.stage.getPreviousStage());
    }

    public LiquidMat falling()
    {
        return this.getStage(this.stage.getFalling());
    }

    public LiquidMat normal()
    {
        return this.getStage(this.stage.getNextStage());
    }

    public LiquidMat switchFalling()
    {
        return this.getStage(this.stage.switchFalling());
    }

    public LiquidMat source()
    {
        return this.getStage(this.stage.getSource());
    }

    public boolean isSource()
    {
        return this.stage.isSource();
    }

    public boolean isNormalStage()
    {
        return this.stage.isNormal();
    }

    public boolean isFalling()
    {
        return this.stage.isFalling();
    }

    public LiquidStageMat getStage()
    {
        return this.stage;
    }

    public LiquidTypeMat getLiquidType()
    {
        return this.liquidType;
    }

    public boolean isStill()
    {
        return this.liquidType.isStill();
    }

    @Override
    public boolean isSolid()
    {
        return false;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("stage", this.stage).append("liquidType", this.liquidType).toString();
    }
}
