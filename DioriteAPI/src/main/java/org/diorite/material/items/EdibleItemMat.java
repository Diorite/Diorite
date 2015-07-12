package org.diorite.material.items;

/**
 * Represents an item that can be eaten by player.
 */
public interface EdibleItemMat
{
    /**
     * Returns how much player food level will increase
     * after eating this.
     * @return player food level increase.
     */
    int getFoodLevelIncrease();

    /**
     * Returns how much player food saturation level will increase
     * after eating this.
     * @return player food saturation level increase.
     */
    float getSaturationIncrease();

    // TODO: implement when potion effect will be added
//    /**
//     * Returns (immutable) set of possible potion effects, each entry of this set contains
//     * percent chance to get this effect, type and power of effect, and duration of it.
//     * @return set of possible potion effects.
//     */
//     Set<PossiblePotionEffect> getPossibleEffects();
}
