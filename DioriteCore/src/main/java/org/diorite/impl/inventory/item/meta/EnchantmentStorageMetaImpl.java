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

package org.diorite.impl.inventory.item.meta;

import org.diorite.enchantments.EnchantmentType;
import org.diorite.inventory.item.ItemStack;
import org.diorite.inventory.item.meta.EnchantmentStorageMeta;
import org.diorite.nbt.NbtTagCompound;

import gnu.trove.map.TObjectShortMap;

public class EnchantmentStorageMetaImpl extends SimpleItemMetaImpl implements EnchantmentStorageMeta
{
    protected static final String STORED_ENCHANTMENTS = "StoredEnchantments";

    public EnchantmentStorageMetaImpl(final NbtTagCompound tag)
    {
        super(tag);
    }

    public EnchantmentStorageMetaImpl(final NbtTagCompound tag, final ItemStack itemStack)
    {
        super(tag, itemStack);
    }

    @Override
    public boolean hasStoredEnchants()
    {
        return (this.tag != null) && (this.tag.getList(STORED_ENCHANTMENTS, NbtTagCompound.class) != null);
    }

    @Override
    public boolean hasStoredEnchant(final EnchantmentType enchantment)
    {
        return this.hasEnchant(STORED_ENCHANTMENTS, enchantment);
    }

    @Override
    public int getStoredEnchantLevel(final EnchantmentType enchantment)
    {
        return this.getEnchantLevel(STORED_ENCHANTMENTS, enchantment);
    }

    @Override
    public TObjectShortMap<EnchantmentType> getStoredEnchants()
    {
        return this.getEnchants(STORED_ENCHANTMENTS);
    }

    @Override
    public boolean addStoredEnchant(final EnchantmentType enchantment, final int level, final boolean ignoreLevelRestriction, final boolean ignoreDuplicates)
    {
        return this.addEnchant(STORED_ENCHANTMENTS, enchantment, level, ignoreLevelRestriction, ignoreDuplicates);
    }

    @Override
    public boolean removeStoredEnchant(final EnchantmentType enchantment) throws IllegalArgumentException
    {
        return this.removeEnchant(STORED_ENCHANTMENTS, enchantment);
    }

    @Override
    public void removeStoredEnchants()
    {
        this.removeEnchants(STORED_ENCHANTMENTS);
    }

    @Override
    public boolean hasConflictingStoredEnchant(final EnchantmentType enchantment)
    {
        return this.hasConflictingEnchant(STORED_ENCHANTMENTS, enchantment);
    }

    @SuppressWarnings("CloneDoesntCallSuperClone")
    @Override
    public EnchantmentStorageMetaImpl clone()
    {
        return new EnchantmentStorageMetaImpl((this.tag == null) ? null : this.tag.clone(), this.itemStack);
    }
}
