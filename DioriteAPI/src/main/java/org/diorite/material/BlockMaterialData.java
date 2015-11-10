/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2015 Diorite (by Bartłomiej Mazur (aka GotoFinal))
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package org.diorite.material;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.inventory.item.BaseItemStack;
import org.diorite.inventory.item.meta.BlockItemMeta;
import org.diorite.material.data.drops.PossibleDrops;
import org.diorite.material.data.drops.PossibleFixedDrop;
import org.diorite.utils.lazy.LazyValue;

/**
 * Abstract class for all block-based materials.
 */
public abstract class BlockMaterialData extends Material implements PlaceableMat
{
    /**
     * Hardness of block, used to get time needed to destroy block by player.
     */
    protected final float hardness;
    /**
     * Blast resistance of block, block with low resistance can be easly destroyed by explosions.
     */
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

    {
        this.metaType = BlockItemMeta.class;
    }

    /**
     * Lazy value containing possible drops for this blocks.
     */
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

    /**
     * Returns if block is solid. <br>
     * Block is solid if entities can walk on them. (collisions)
     * And they aren't soild if entitites can walk in them.
     *
     * @return if block is solid.
     */
    public boolean isSolid()
    {
        return true;
    }

    /**
     * Returns blast resistance of block, block with low resistance can be easly destroyed by explosions.
     *
     * @return blast resistance of block.
     */
    public float getBlastResistance()
    {
        return this.blastResistance;
    }

    /**
     * Returns hardness of block, used to get time needed to destroy block by player.
     *
     * @return hardness of block.
     */
    public float getHardness()
    {
        return this.hardness;
    }

    /**
     * This method is invoked by material on first use of {@link #getPossibleDrops()} to get basic/default possible drops for material. <br>
     * Drops can be changed later.
     *
     * @return created defaults drops.
     */
    protected PossibleDrops initPossibleDrops()
    {
        // TODO: implement in all block that don't drop itself.
        return new PossibleDrops(new PossibleFixedDrop(new BaseItemStack(this)));
    }

    /**
     * Returns possible drops for this blocks.
     *
     * @return possible drops for this blocks.
     */
    public PossibleDrops getPossibleDrops()
    {
        return this.possibleDrops.get();
    }

    /**
     * Reset state of lazy value, so new instance of {@link PossibleDrops} will be created on next invoke of {@link #getPossibleDrops()} from {@link #initPossibleDrops()}
     */
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
}
