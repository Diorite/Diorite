package diorite.material;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

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

    public BlockMaterialData(final String enumName, final int id, final int maxStack, final int durability, final String typeName, final byte type)
    {
        super(enumName, id, maxStack, durability);
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
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("typeName", this.typeName).append("type", this.type).toString();
    }
}
