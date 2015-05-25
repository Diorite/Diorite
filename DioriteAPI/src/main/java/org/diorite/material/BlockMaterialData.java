package org.diorite.material;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public abstract class BlockMaterialData extends Material
{
    protected final String typeName;

    protected BlockMaterialData(final String enumName, final int id, final String minecraftId, final String typeName, final short type)
    {
        super(enumName, id, minecraftId, type);
        this.typeName = typeName;
    }

    protected BlockMaterialData(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final short type)
    {
        super(enumName, id, minecraftId, maxStack, type);
        this.typeName = typeName;
    }

    public String getTypeName()
    {
        return this.typeName;
    }

    public abstract BlockMaterialData getType(String name);

    public abstract BlockMaterialData getType(int id);

    // TODO: change that methods/add others when needed
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

//    @Override
//    public boolean isTransparent()
//    {
//        return false;
//    }
//
//    @Override
//    public boolean isFlammable()
//    {
//        return false;
//    }
//
//    @Override
//    public boolean isBurnable()
//    {
//        return false;
//    }
//
//    @Override
//    public boolean isOccluding()
//    {
//        return true;
//    }
//
//    @Override
//    public boolean hasGravity()
//    {
//        return false;
//    }
//
//    @Override
//    public boolean isEdible()
//    {
//        return false;
//    }
//
//    @Override
//    public boolean isReplaceable()
//    {
//        return false;
//    }
//
//    @Override
//    public boolean isGlowing()
//    {
//        return false;
//    }
//
//    @Override
//    public int getLuminance()
//    {
//        return 0;
//    }
//
//    @Override
//    public IntRange getExperienceWhenMined()
//    {
//        return IntRange.EMPTY;
//    }

    @Override
    public int hashCode()
    {
        int result = super.hashCode();
        result = (31 * result) + this.typeName.hashCode();
        return result;
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
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("typeName", this.typeName).append("type", this.type).toString();
    }

    /**
     * @return array contains all other (including current one) sub-types of this material.
     */
    public abstract BlockMaterialData[] types();

    public static BlockMaterialData getByID(final int id)
    {
        final Material mat = Material.getByID(id);
        if (mat instanceof BlockMaterialData)
        {
            return (BlockMaterialData) mat;
        }
        return null;
    }

    public static BlockMaterialData getByID(final int id, final int meta)
    {
        final Material mat = Material.getByID(id, meta);
        if (mat instanceof BlockMaterialData)
        {
            return (BlockMaterialData) mat;
        }
        return null;
    }

    public static BlockMaterialData getByID(final int id, final String meta)
    {
        final Material mat = Material.getByID(id);
        if (mat instanceof BlockMaterialData)
        {
            return (BlockMaterialData) mat;
        }
        return null;
    }

    public static BlockMaterialData getByEnumName(final String name)
    {
        final Material mat = Material.getByEnumName(name);
        if (mat instanceof BlockMaterialData)
        {
            return (BlockMaterialData) mat;
        }
        return null;
    }

    public static BlockMaterialData getByEnumName(final String name, final int meta)
    {
        final Material mat = Material.getByEnumName(name, meta);
        if (mat instanceof BlockMaterialData)
        {
            return (BlockMaterialData) mat;
        }
        return null;
    }

    public static BlockMaterialData getByEnumName(final String name, final String meta)
    {
        final Material mat = Material.getByEnumName(name, meta);
        if (mat instanceof BlockMaterialData)
        {
            return (BlockMaterialData) mat;
        }
        return null;
    }

    public static BlockMaterialData getByMinecraftId(final String name)
    {
        final Material mat = Material.getByMinecraftId(name);
        if (mat instanceof BlockMaterialData)
        {
            return (BlockMaterialData) mat;
        }
        return null;
    }
}
