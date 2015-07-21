package org.diorite.material.items.food;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.material.ItemMaterialData;

/**
 * Represents an item that can be eaten by player.
 */
public abstract class EdibleItemMat extends ItemMaterialData
{
    protected final int   foodLevelIncrease;
    protected final float saturationIncrease;

    protected EdibleItemMat(final String enumName, final int id, final String minecraftId, final String typeName, final short type, final int foodLevelIncrease, final float saturationIncrease)
    {
        super(enumName, id, minecraftId, typeName, type);
        this.foodLevelIncrease = foodLevelIncrease;
        this.saturationIncrease = saturationIncrease;
    }

    protected EdibleItemMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final short type, final int foodLevelIncrease, final float saturationIncrease)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type);
        this.foodLevelIncrease = foodLevelIncrease;
        this.saturationIncrease = saturationIncrease;
    }

    /**
     * Returns how much player food level will increase
     * after eating this.
     *
     * @return player food level increase.
     */
    public int getFoodLevelIncrease()
    {
        return this.foodLevelIncrease;
    }

    /**
     * Returns how much player food saturation level will increase
     * after eating this.
     *
     * @return player food saturation level increase.
     */
    public float getSaturationIncrease()
    {
        return this.saturationIncrease;
    }

    // TODO: implement when potion effect will be added
//    /**
//     * Returns (immutable) set of possible potion effects, each entry of this set contains
//     * percent chance to get this effect, type and power of effect, and duration of it.
//     * @return set of possible potion effects.
//     */
//     Set<PossiblePotionEffect> getPossibleEffects();

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("foodLevelIncrease", this.foodLevelIncrease).append("saturationIncrease", this.saturationIncrease).toString();
    }
}
