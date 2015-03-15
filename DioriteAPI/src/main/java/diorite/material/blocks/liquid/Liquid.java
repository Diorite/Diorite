package diorite.material.blocks.liquid;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import diorite.material.BlockMaterialData;

public abstract class Liquid extends BlockMaterialData
{
    public static final byte FALLING_FLAG = 0x08;

    protected final LiquidStage stage;
    protected final LiquidType  liquidType;

    public Liquid(final String enumName, final int id, final String typeName, final LiquidStage stage, final LiquidType liquidType)
    {
        super(enumName, id, typeName, stage.getDataValue());
        this.stage = stage;
        this.liquidType = liquidType;
    }

    public Liquid(final String enumName, final int id, final int maxStack, final String typeName, final LiquidStage stage, final LiquidType liquidType)
    {
        super(enumName, id, maxStack, typeName, stage.getDataValue());
        this.stage = stage;
        this.liquidType = liquidType;
    }

    public abstract Liquid getStage(LiquidStage stage);

    public abstract Liquid getLiquidType(LiquidType type);

    public Liquid getNormalType()
    {
        return this.getLiquidType(LiquidType.NORMAL);
    }

    public Liquid getStillType()
    {
        return this.getLiquidType(LiquidType.STILL);
    }

    public Liquid getOtherType()
    {
        return this.getLiquidType((this.liquidType == LiquidType.STILL) ? LiquidType.NORMAL : LiquidType.STILL);
    }

    public Liquid nextStage()
    {
        return this.getStage(this.stage.getNextStage());
    }

    public Liquid previousStage()
    {
        return this.getStage(this.stage.getPreviousStage());
    }

    public Liquid falling()
    {
        return this.getStage(this.stage.getFalling());
    }

    public Liquid normal()
    {
        return this.getStage(this.stage.getNextStage());
    }

    public Liquid switchFalling()
    {
        return this.getStage(this.stage.switchFalling());
    }

    public Liquid source()
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

    public LiquidStage getStage()
    {
        return this.stage;
    }

    public LiquidType getLiquidType()
    {
        return this.liquidType;
    }

    public boolean isStill()
    {
        return this.liquidType == LiquidType.STILL;
    }

    @Override
    public boolean isSolid()
    {
        return false;
    }

    @Override
    public boolean isOccluding()
    {
        return false;
    }

    @Override
    public boolean isReplaceable()
    {
        return true;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("stage", this.stage).append("liquidType", this.liquidType).toString();
    }

    @SuppressWarnings("MagicNumber")
    @Override
    public float getBlastResistance()
    {
        return 500;
    }

    @Override
    public float getHardness()
    {
        return 100;
    }
}
