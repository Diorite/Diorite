package org.diorite.material.items;

/**
 * Represents an item that have durability and can break when it go above {@link #getMaxDurability()}
 */
public interface BreakableItemMat
{
    /**
     * Returns max durability for this tool, tool is "new" when it have durability level at 0. <br>
     * And it breaks when durability goes below this number.
     *
     * @return max durbaility for this item.
     */
    int getMaxDurability();

    /**
     * Returns current level of durability, it is equals to item sub-id.
     *
     * @return current level of durability.
     */
    int getDurability();

    /**
     * Returns amount uses of this tool before it breaks. <br>
     * Real amount of uses can be affected by enchants, and
     * using tool in "wrong way" may affect durability faster. <br>
     * (like using sword to break blocks)
     *
     * @return amount uses of this tool before it breaks. (maxDur - curDur)
     */
    default int getLeftUses()
    {
        return this.getMaxDurability() - this.getDurability();
    }

    /**
     * Returns if this item have valid durability so it is bigger or equals to 0,
     * and smaller than {@link #getMaxDurability()}
     *
     * @return if this item have valid durability;
     */
    default boolean haveValidDurability()
    {
        final int dur = this.getDurability();
        return (dur >= 0) && (dur < this.getMaxDurability());
    }

    /**
     * Returns this same material but with increased durability level. <br>
     * In Minecraft durability equals to 0 means full, so this will return
     * more broken material type than current one. <br>
     * Like if current durability is 5, this method will return material with
     * durability equals to 6, so it is weaker. <br>
     * Implementation of this method should be faster than using {@link #setDurability(int)}
     *
     * @return sub-type of item with "increased" durability.
     */
    BreakableItemMat increaseDurability();

    /**
     * Returns this same material but with decreased durability level. <br>
     * In Minecraft durability equals to 0 means full, so this will return
     * less broken material type than current one. <br>
     * Like if current durability is 5, this method will return material with
     * durability equals to 4, so it is stronger. <br>
     * Implementation of this method should be faster than using {@link #setDurability(int)}
     *
     * @return sub-type of item with "decreased" durability.
     */
    BreakableItemMat decreaseDurability();

    /**
     * Get sub-type of item with given durability. <br>
     * Implementation should cache results. (at least in real-values range)
     *
     * @param durability durability level of item.
     *
     * @return sub-type of item with given durability.
     */
    BreakableItemMat setDurability(int durability);
}
