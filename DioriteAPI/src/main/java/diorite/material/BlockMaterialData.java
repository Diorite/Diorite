package diorite.material;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import diorite.utils.math.IntRange;

public abstract class BlockMaterialData extends Material
{
    protected final String typeName;
    protected final byte   type;

    public BlockMaterialData(final String enumName, final int id, final String typeName, final byte type)
    {
        super(enumName, id);
        this.typeName = typeName;
        this.type = type;
    }

    public BlockMaterialData(final String enumName, final int id, final int maxStack, final String typeName, final byte type)
    {
        super(enumName, id, maxStack);
        this.typeName = typeName;
        this.type = type;
    }

    public String getTypeName()
    {
        return this.typeName;
    }

    public byte getType()
    {
        return this.type;
    }

    public abstract BlockMaterialData getType(final String name);

    public abstract BlockMaterialData getType(final int id);

    @Override
    public boolean isBlock()
    {
        return true;
    }

    @Override
    public boolean isSolid()
    {
        return true;
    }

    @Override
    public boolean isTransparent()
    {
        return false;
    }

    @Override
    public boolean isFlammable()
    {
        return false;
    }

    @Override
    public boolean isBurnable()
    {
        return false;
    }

    @Override
    public boolean isOccluding()
    {
        return true;
    }

    @Override
    public boolean hasGravity()
    {
        return false;
    }

    @Override
    public boolean isEdible()
    {
        return false;
    }

    @Override
    public boolean isReplaceable()
    {
        return false;
    }

    @Override
    public boolean isGlowing()
    {
        return false;
    }

    @Override
    public int getLuminance()
    {
        return 0;
    }

    @Override
    public IntRange getExperienceWhenMined()
    {
        return IntRange.EMPTY;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("typeName", this.typeName).append("type", this.type).toString();
    }

    @Override
    public boolean equals(final Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (! (o instanceof BlockMaterialData))
        {
            return false;
        }
        if (! super.equals(o))
        {
            return false;
        }

        final BlockMaterialData that = (BlockMaterialData) o;

        return this.typeName.equals(that.typeName);
    }

    @Override
    public int hashCode()
    {
        int result = super.hashCode();
        result = (31 * result) + this.typeName.hashCode();
        return result;
    }
}
