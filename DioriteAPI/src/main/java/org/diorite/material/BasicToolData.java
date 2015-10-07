package org.diorite.material;

/**
 * Represents tool properties.
 */
public class BasicToolData
{
    protected final int   baseDurability;
    protected final int   enchantability;
    protected final float damage;

    /**
     * Construct new tool properties.
     *
     * @param baseDurability base durability of tool.
     * @param enchantability enchantability of tool.
     * @param damage         damage of tool.
     */
    public BasicToolData(final int baseDurability, final int enchantability, final float damage)
    {
        this.baseDurability = baseDurability;
        this.enchantability = enchantability;
        this.damage = damage;
    }

    /**
     * Returns base durability of this tool.
     *
     * @return base durability of this tool.
     */
    public int getBaseDurability()
    {
        return this.baseDurability;
    }

    /**
     * Returns enchantability level of tool, used to get possible enchantemnts using enchanting table.
     *
     * @return enchantability level of tool.
     */
    public int getEnchantability()
    {
        return this.enchantability;
    }

    /**
     * Returns attack damage of this tool.
     *
     * @return attack damage of this tool.
     */
    public float getDamage()
    {
        return this.damage;
    }
}
