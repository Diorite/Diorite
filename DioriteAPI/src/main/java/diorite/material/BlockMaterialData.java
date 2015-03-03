package diorite.material;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public abstract class BlockMaterialData extends Material
{
    private final String typeName;
    private final byte   type;

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
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("typeName", this.typeName).append("type", this.type).toString();
    }
}
