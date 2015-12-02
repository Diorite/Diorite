package org.diorite.inventory.item.builder;

import org.diorite.enchantments.EnchantmentType;
import org.diorite.inventory.item.meta.EnchantmentStorageMeta;

import it.unimi.dsi.fastutil.objects.Object2ShortMap;
import it.unimi.dsi.fastutil.objects.Object2ShortMap.Entry;

/**
 * Interface of builder of enchantment storage item meta data.
 */
public interface IEnchantmentStorageMetaBuilder<B extends IEnchantmentStorageMetaBuilder<B, M>, M extends EnchantmentStorageMeta> extends IMetaBuilder<B, M>
{
    /**
     * Stores the specified enchantment in this item meta.
     *
     * @param ench  EnchantmentType to store
     * @param level Level for the enchantment
     *
     * @return builder for method chains.
     */
    default B addStoredEnchant(final EnchantmentType ench, final int level)
    {
        this.meta().addStoredEnchant(ench, level, true);
        return this.getBuilder();
    }

    /**
     * Stores the specified enchantment in this item meta.
     *
     * @param enchantment EnchantmentType to store
     * @param level       Level for the enchantment
     *
     * @return builder for method chains.
     */
    default B forceAddStoredEnchant(final EnchantmentType enchantment, final int level)
    {
        this.meta().addStoredEnchant(enchantment, level, true, true);
        return this.getBuilder();
    }

    /**
     * Set stored enchants of item.
     *
     * @param src source item meta to copy enchants from it.
     *
     * @return builder for method chains.
     */
    default B setStoredEnchants(final EnchantmentStorageMeta src)
    {
        final M meta = this.meta();
        meta.removeStoredEnchants();
        final Object2ShortMap<EnchantmentType> map = src.getStoredEnchants();
        if (map == null)
        {
            return this.getBuilder();
        }
        for (final Entry<EnchantmentType> entry : map.object2ShortEntrySet())
        {
            meta.addStoredEnchant(entry.getKey(), entry.getShortValue(), true);
        }
        return this.getBuilder();
    }

    /**
     * Remove all stored enchants.
     *
     * @return builder for method chains.
     */
    default B clearStoredEnchants()
    {
        this.meta().removeStoredEnchants();
        return this.getBuilder();
    }

}
