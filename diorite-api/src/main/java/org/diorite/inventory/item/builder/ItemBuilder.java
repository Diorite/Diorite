package org.diorite.inventory.item.builder;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.inventory.item.BaseItemStack;
import org.diorite.inventory.item.ItemStack;
import org.diorite.material.Material;

/**
 * Simple builder for item stack.
 */
public class ItemBuilder
{
    /**
     * Wrapped item stack used by builder.
     */
    protected ItemStack itemStack;

    private ItemBuilder(final Material material)
    {
        this.itemStack = new BaseItemStack(material);
    }

    private ItemBuilder(final ItemStack itemStack)
    {
        this.itemStack = itemStack.clone();
    }

    /**
     * Set material of builder.
     *
     * @param material new material of item.
     *
     * @return builder for method chains.
     */
    public ItemBuilder material(final Material material)
    {
        this.itemStack.setMaterial(material);
        return this;
    }

    /**
     * Set material of item.
     *
     * @param src source item to copy material from it.
     *
     * @return builder for method chains.
     */
    public ItemBuilder material(final ItemStack src)
    {
        this.itemStack.setMaterial(src.getMaterial());
        return this;
    }

    /**
     * Set amount of material of item.
     *
     * @param amount new amount of item.
     *
     * @return builder for method chains.
     */
    public ItemBuilder amount(final int amount)
    {
        this.itemStack.setAmount(amount);
        return this;
    }

    /**
     * Set amount of material of item.
     *
     * @param src source item to copy amount from it.
     *
     * @return builder for method chains.
     */
    public ItemBuilder amount(final ItemStack src)
    {
        this.itemStack.setAmount(src.getAmount());
        return this;
    }

    /**
     * Set meta data of this item.
     *
     * @param builder builder of meta data.
     *
     * @return builder for method chains.
     */
    public ItemBuilder meta(final IMetaBuilder<?, ?> builder)
    {
        this.itemStack.setItemMeta(builder.build());
        return this;
    }

    /**
     * Returns created item stack from this builder.
     *
     * @return created item stack from this builder.
     */
    public ItemStack build()
    {
        return this.itemStack.clone();
    }

    /**
     * Start builder of itemstack of given material.
     *
     * @param material material of item stack to build.
     *
     * @return created item builder.
     */
    public static ItemBuilder start(final Material material)
    {
        return new ItemBuilder(material);
    }

    /**
     * Start builder based on given item stack
     *
     * @param itemStack basic item stack, builder will copy data from it.
     *
     * @return created item builder.
     */
    public static ItemBuilder start(final ItemStack itemStack)
    {
        return new ItemBuilder(itemStack);
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("itemStack", this.itemStack).toString();
    }
}
