package org.diorite.material;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.inventory.item.BaseItemStack;
import org.diorite.material.data.drops.PossibleDrops;
import org.diorite.material.data.drops.PossibleFixedDrop;
import org.diorite.utils.lazy.LazyValue;

public abstract class BlockMaterialData extends Material implements PlaceableMat
{
    protected final float hardness;
    protected final float blastResistance;

    protected BlockMaterialData(final String enumName, final int id, final String minecraftId, final String typeName, final short type, final float hardness, final float blastResistance)
    {
        super(enumName, id, minecraftId, typeName, type);
        this.hardness = hardness;
        this.blastResistance = blastResistance;
    }

    protected BlockMaterialData(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final short type, final float hardness, final float blastResistance)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type);
        this.hardness = hardness;
        this.blastResistance = blastResistance;
    }

    protected final LazyValue<PossibleDrops> possibleDrops = new LazyValue<>(this::initPossibleDrops);

    @Override
    public abstract BlockMaterialData getType(String name);

    @Override
    public abstract BlockMaterialData getType(int id);

    // TODO: change that methods/add others when needed
    @Override
    public boolean isBlock()
    {
        return true;
    }

    public boolean isSolid()
    {
        return true;
    }

    public float getBlastResistance()
    {
        return this.blastResistance;
    }

    public float getHardness()
    {
        return this.hardness;
    }

    protected PossibleDrops initPossibleDrops()
    {
        // TODO: implement in all block that don't drop itself.
        return new PossibleDrops(new PossibleFixedDrop(new BaseItemStack(this)));
    }

    public PossibleDrops getPossibleDrops()
    {
        return this.possibleDrops.get();
    }

    public void resetPossibleDrops()
    {
        this.possibleDrops.reset();
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

    @Override
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
