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

package org.diorite.impl.inventory.item;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.diorite.inventory.item.enchantments.Enchantment;
import org.diorite.inventory.item.meta.ItemFlag;
import org.diorite.inventory.item.meta.ItemMeta;
import org.diorite.nbt.NbtTag;
import org.diorite.nbt.NbtTagCompound;
import org.diorite.nbt.NbtTagList;
import org.diorite.nbt.NbtTagType;

import gnu.trove.map.TObjectIntMap;

@SuppressWarnings({"unchecked", "rawtypes"})
public class SimpleItemMetaImpl extends ItemMetaImpl
{
    private static final String SEP          = ".";
    private static final String DISPLAY      = "display";
    private static final String DISPLAY_NAME = DISPLAY + SEP + "Name";
    private static final String LORE         = "Lore";
    private static final String DISPLAY_LORE = DISPLAY + SEP + LORE;

    public SimpleItemMetaImpl(final NbtTagCompound tag)
    {
        super(tag);
    }

    @Override
    public boolean hasDisplayName()
    {
        return this.tag.containsTag(DISPLAY_NAME);
    }

    @Override
    public String getDisplayName()
    {
        return this.tag.getString(DISPLAY_NAME);
    }

    @Override
    public void setDisplayName(final String name)
    {
        if (name == null)
        {
            this.tag.removeTag(DISPLAY_NAME);
            return;
        }
        this.tag.setString(DISPLAY_NAME, name);
    }

    @Override
    public boolean hasLore()
    {
        return this.tag.containsTag(DISPLAY_LORE);
    }

    @Override
    public List<String> getLore()
    {
        final NbtTag tag = this.tag.getTag(DISPLAY_LORE, NbtTagList.class);
        if (tag == null)
        {
            return null;
        }
        return new ArrayList<>((Collection) tag.getNBTValue());
    }

    @Override
    public void setLore(final List<String> lore)
    {
        if (lore == null)
        {
            this.tag.removeTag(DISPLAY_LORE);
            return;
        }
        this.tag.addTag(DISPLAY, new NbtTagList(LORE, NbtTagType.STRING, lore));
    }

    @Override
    public boolean hasEnchants()
    {
        return false;
    }

    @Override
    public boolean hasEnchant(final Enchantment enchantment)
    {
        return false;
    }

    @Override
    public int getEnchantLevel(final Enchantment enchantment)
    {
        return 0;
    }

    @Override
    public TObjectIntMap<Enchantment> getEnchants()
    {
        return null;
    }

    @Override
    public boolean addEnchant(final Enchantment enchantment, final int level, final boolean ignoreLevelRestriction)
    {
        return false;
    }

    @Override
    public boolean removeEnchant(final Enchantment enchantment)
    {
        return false;
    }

    @Override
    public boolean hasConflictingEnchant(final Enchantment enchantment)
    {
        return false;
    }

    @Override
    public void removeEnchantments()
    {

    }

    @Override
    public void addItemFlags(final ItemFlag... itemFlags)
    {

    }

    @Override
    public void removeItemFlags(final ItemFlag... itemFlags)
    {

    }

    @Override
    public Set<ItemFlag> getItemFlags()
    {
        return null;
    }

    @Override
    public boolean hasItemFlag(final ItemFlag flag)
    {
        return false;
    }

    @Override
    public void removeItemFlags()
    {

    }

    @Override
    public ItemMeta clone()
    {
        return null;
    }
}
