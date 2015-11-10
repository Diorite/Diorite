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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.diorite.enchantments.EnchantmentType;
import org.diorite.entity.attrib.AttributeModifier;
import org.diorite.inventory.item.HideFlag;
import org.diorite.inventory.item.ItemStack;
import org.diorite.inventory.item.meta.ItemMeta;
import org.diorite.nbt.NbtTag;
import org.diorite.nbt.NbtTagCompound;
import org.diorite.nbt.NbtTagList;
import org.diorite.nbt.NbtTagType;

import gnu.trove.TCollections;
import gnu.trove.map.TObjectShortMap;
import gnu.trove.map.hash.TObjectShortHashMap;

@SuppressWarnings({"unchecked", "rawtypes"})
public class SimpleItemMetaImpl extends ItemMetaImpl
{
    protected static final String DISPLAY             = "display";
    protected static final String DISPLAY_NAME        = DISPLAY + SEP + "Name";
    protected static final String LORE                = "Lore";
    protected static final String DISPLAY_LORE        = DISPLAY + SEP + LORE;
    protected static final String HIDE_FLAGS          = "HideFlags";
    protected static final String ENCHANTMENTS        = "ench";
    protected static final String ENCHANTMENT_ID      = "id";
    protected static final String ENCHANTMENT_LEVEL   = "lvl";
    protected static final String ATTRIBUTE_MODIFIERS = "AttributeModifiers";

    protected ItemStack itemStack;

    public SimpleItemMetaImpl(final NbtTagCompound tag)
    {
        super(tag);
    }

    public SimpleItemMetaImpl(final NbtTagCompound tag, final ItemStack itemStack)
    {
        super(tag);
        this.itemStack = itemStack;
    }

    @Override
    public Optional<ItemStack> getItemStack()
    {
        return Optional.ofNullable(this.itemStack);
    }

    @Override
    public ItemMeta apply(final ItemStack item)
    {
        final SimpleItemMetaImpl meta = this.clone();
        meta.itemStack = item;
        item.setItemMeta(meta);
        return meta;
    }

    @Override
    public boolean hasDisplayName()
    {
        return (this.tag != null) && this.tag.containsTag(DISPLAY_NAME);
    }

    @Override
    public String getDisplayName()
    {
        if (this.tag == null)
        {
            return null;
        }
        return this.tag.getString(DISPLAY_NAME);
    }

    @Override
    public void setDisplayName(final String name)
    {
        this.setStringTag(DISPLAY_NAME, name);
    }

    @Override
    public boolean hasLore()
    {
        return (this.tag != null) && this.tag.containsTag(DISPLAY_LORE);
    }

    @Override
    public List<String> getLore()
    {
        if (this.tag == null)
        {
            return null;
        }
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
            if (this.tag != null)
            {
                this.tag.removeTag(DISPLAY_LORE);
                this.checkTag(false);
                this.setDirty();
            }
            return;
        }
        this.checkTag(true);
        this.tag.addTag(DISPLAY, new NbtTagList(LORE, NbtTagType.STRING, lore));
        this.setDirty();
    }

    @Override
    public boolean hasEnchants()
    {
        return (this.tag != null) && (! this.tag.getList(ENCHANTMENTS, NbtTagCompound.class, new ArrayList<>(1)).isEmpty());
    }

    boolean hasEnchant(final String key, final EnchantmentType enchantment)
    {
        if (this.tag == null)
        {
            return false;
        }
        final List<NbtTagCompound> tags = this.tag.getList(key, NbtTagCompound.class);
        if (tags == null)
        {
            return false;
        }
        for (final NbtTagCompound tag : tags)
        {
            if (tag.getShort(ENCHANTMENT_ID) == enchantment.getNumericID())
            {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean hasEnchant(final EnchantmentType enchantment)
    {
        return this.hasEnchant(ENCHANTMENTS, enchantment);
    }

    int getEnchantLevel(final String key, final EnchantmentType enchantment)
    {
        if (this.tag == null)
        {
            return 0;
        }
        final List<NbtTagCompound> tags = this.tag.getList(key, NbtTagCompound.class);
        if (tags == null)
        {
            return 0;
        }
        for (final NbtTagCompound tag : tags)
        {
            if (tag.getShort(ENCHANTMENT_ID) == enchantment.getNumericID())
            {
                return tag.getShort(ENCHANTMENT_LEVEL);
            }
        }
        return 0;
    }

    @Override
    public int getEnchantLevel(final EnchantmentType enchantment)
    {
        return this.getEnchantLevel(ENCHANTMENTS, enchantment);
    }

    TObjectShortMap<EnchantmentType> getEnchants(final String key)
    {
        if (this.tag == null)
        {
            return TCollections.unmodifiableMap(new TObjectShortHashMap<>(1, 1, (short) - 1));
        }
        final List<NbtTagCompound> tags = this.tag.getList(key, NbtTagCompound.class);
        if (tags == null)
        {
            return TCollections.unmodifiableMap(new TObjectShortHashMap<>(1, 1, (short) - 1));
        }
        final TObjectShortMap<EnchantmentType> result = new TObjectShortHashMap<>(tags.size(), 1, (short) - 1);
        for (final NbtTagCompound tag : tags)
        {
            final EnchantmentType ench = EnchantmentType.getByNumericID(tag.getShort(ENCHANTMENT_ID));
            result.put(ench, tag.getShort(ENCHANTMENT_LEVEL));
        }
        return TCollections.unmodifiableMap(result);
    }

    @Override
    public TObjectShortMap<EnchantmentType> getEnchants()
    {
        return this.getEnchants(ENCHANTMENTS);
    }

    @Override
    public boolean addEnchant(final EnchantmentType enchantment, final int level, final boolean ignoreLevelRestriction, final boolean ignoreDuplicates)
    {
        return this.addEnchant(ENCHANTMENTS, enchantment, level, ignoreLevelRestriction, ignoreDuplicates);
    }

    boolean addEnchant(final String key, final EnchantmentType enchantment, final int level, final boolean ignoreLevelRestriction, final boolean ignoreDuplicates)
    {
        if ((level <= 0) || (! ignoreLevelRestriction && (level > enchantment.getMaxLevel(this.itemStack, false))))
        {
            return false;
        }
        NbtTagList list = (this.tag == null) ? null : this.tag.getTag(key, NbtTagList.class);
        if (list == null)
        {
            list = new NbtTagList(key, 4);
            this.checkTag(true);
            this.tag.addTag(list);
        }
        else if (! ignoreDuplicates)
        {
            for (final NbtTagCompound nbt : list.getTags(NbtTagCompound.class))
            {
                if (nbt.getShort(ENCHANTMENT_ID) == enchantment.getNumericID())
                {
                    if (nbt.getShort(ENCHANTMENT_LEVEL) == level)
                    {
                        return false;
                    }
                    nbt.setShort(ENCHANTMENT_LEVEL, level);
                    this.setDirty();
                    return true;
                }
            }
        }
        final NbtTagCompound ench = new NbtTagCompound();
        ench.setShort(ENCHANTMENT_ID, enchantment.getNumericID());
        ench.setShort(ENCHANTMENT_LEVEL, level);
        list.addTag(ench);
        this.setDirty();
        return true;
    }

    boolean removeEnchant(final String key, final EnchantmentType enchantment)
    {
        if (this.tag == null)
        {
            return false;
        }
        final NbtTagList list = this.tag.getTag(key, NbtTagList.class);
        for (final Iterator<NbtTag> it = list.iterator(); it.hasNext(); )
        {
            final NbtTagCompound nbt = (NbtTagCompound) it.next();
            if (nbt.getShort(ENCHANTMENT_ID) == enchantment.getNumericID())
            {
                it.remove();
                if (list.isEmpty())
                {
                    this.tag.removeTag(key);
                    this.checkTag(false);
                }
                this.setDirty();
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean removeEnchant(final EnchantmentType enchantment)
    {
        return this.removeEnchant(ENCHANTMENTS, enchantment);
    }

    boolean hasConflictingEnchant(final String key, final EnchantmentType enchantment)
    {
        if (this.tag == null)
        {
            return false;
        }
        final List<NbtTagCompound> list = this.tag.getList(key, NbtTagCompound.class);
        for (final NbtTagCompound nbt : list)
        {
            final EnchantmentType ench = EnchantmentType.getByNumericID(nbt.getShort(ENCHANTMENT_ID));
            if (enchantment.conflictsWith(ench))
            {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean hasConflictingEnchant(final EnchantmentType enchantment)
    {
        return this.hasConflictingEnchant(ENCHANTMENTS, enchantment);
    }

    void removeEnchants(final String key)
    {
        if (this.tag == null)
        {
            return;
        }
        this.tag.removeTag(key);
        this.checkTag(false);
        this.setDirty();
    }

    @Override
    public void removeEnchants()
    {
        this.removeEnchants(ENCHANTMENTS);
    }

    @Override
    public void addHideFlags(final HideFlag... hideFlags)
    {
        int flags;
        final int oldFlags;
        final Set<HideFlag> set;
        if (this.tag != null)
        {
            flags = this.tag.getInt(HIDE_FLAGS);
            set = HideFlag.getFlags(flags);
            oldFlags = flags;
        }
        else
        {
            set = new HashSet<>(hideFlags.length);
            oldFlags = 0;
        }
        Collections.addAll(set, hideFlags);
        flags = HideFlag.join(set);
        if (flags == 0)
        {
            this.tag.removeTag(HIDE_FLAGS);
        }
        this.tag.setInt(HIDE_FLAGS, flags);
        if (flags != oldFlags)
        {
            this.setDirty();
        }
    }

    @Override
    public void removeHideFlags(final HideFlag... hideFlags)
    {
        if (this.tag == null)
        {
            return;
        }
        int flags = this.tag.getInt(HIDE_FLAGS);
        final Set<HideFlag> set = HideFlag.getFlags(flags);
        for (final HideFlag hideFlag : hideFlags)
        {
            set.remove(hideFlag);
        }
        flags = HideFlag.join(set);
        if (flags == 0)
        {
            this.tag.removeTag(HIDE_FLAGS);
            this.checkTag(false);
            this.setDirty();
            return;
        }
        this.tag.setInt(HIDE_FLAGS, flags);
        this.setDirty();
    }

    @Override
    public Set<HideFlag> getHideFlags()
    {
        if (this.tag == null)
        {
            return null;
        }
        return HideFlag.getFlags(this.tag.getInt(HIDE_FLAGS));
    }

    @Override
    public boolean hasHideFlag(final HideFlag flag)
    {
        return (this.tag != null) && HideFlag.getFlags(this.tag.getInt(HIDE_FLAGS)).contains(flag);
    }

    @Override
    public void removeHideFlags()
    {
        if (this.tag == null)
        {
            return;
        }
        this.tag.removeTag(HIDE_FLAGS);
        this.checkTag(false);
        this.setDirty();
    }

    @Override
    public List<AttributeModifier> getAttributeModifiers()
    {
        if (this.tag == null)
        {
            return null;
        }
        if (! this.tag.containsTag(ATTRIBUTE_MODIFIERS))
        {
            return null;
        }
        return this.tag.getList(ATTRIBUTE_MODIFIERS, NbtTagCompound.class).stream().map(AttributeModifier::fromNbt).collect(Collectors.toList());
    }

    @Override
    public void setAttributeModifiers(final List<AttributeModifier> modifiers, final boolean overrideDefaults)
    {
        final List<AttributeModifier> newModifiers;
        if (overrideDefaults)
        {
            newModifiers = new ArrayList<>(modifiers);
        }
        else
        {
            final List<AttributeModifier> defs = this.getDefaultAttributeModifiers();
            newModifiers = new ArrayList<>(modifiers.size() + defs.size() + 1);
            newModifiers.addAll(defs);
            newModifiers.addAll(modifiers);
        }
        this.checkTag(true);
        this.tag.removeTag(ATTRIBUTE_MODIFIERS);
        this.tag.addTag(new NbtTagList(ATTRIBUTE_MODIFIERS, newModifiers.stream().map(AttributeModifier::serializeToNBT).collect(Collectors.toList())));
        this.setDirty();
    }

    @Override
    public void addAttributeModifier(final AttributeModifier modifier)
    {
        final NbtTagList list = (this.tag == null) ? new NbtTagList(ATTRIBUTE_MODIFIERS, 3) : this.tag.getTag(ATTRIBUTE_MODIFIERS, NbtTagList.class, new NbtTagList(ATTRIBUTE_MODIFIERS, 3));
        if (list.isEmpty() && ((this.tag == null) || ! this.tag.containsTag(ATTRIBUTE_MODIFIERS)))
        {
            this.checkTag(true);
            this.tag.addTag(list);
        }
        list.add(modifier.serializeToNBT());
        this.setDirty();
    }

    @Override
    public boolean removeAttributeModifiers(final UUID uuid)
    {
        if (this.tag == null)
        {
            return false;
        }
        final NbtTagList list = this.tag.getTag(ATTRIBUTE_MODIFIERS, NbtTagList.class);
        if (list == null)
        {
            return false;
        }
        if (list.removeIf(e -> AttributeModifier.fromNbt((NbtTagCompound) e).getUuid().equals(uuid)))
        {
            if (list.isEmpty())
            {
                this.removeAttributeModifiers();
            }
            this.setDirty();
            return true;
        }
        return false;
    }

    @Override
    public boolean removeAttributeModifiers(final String name)
    {
        if (this.tag == null)
        {
            return false;
        }
        final NbtTagList list = this.tag.getTag(ATTRIBUTE_MODIFIERS, NbtTagList.class);
        if (list == null)
        {
            return false;
        }
        if (list.removeIf(e -> AttributeModifier.fromNbt((NbtTagCompound) e).getName().map(s -> s.equals(name)).orElse(false)))
        {
            if (list.isEmpty())
            {
                this.removeAttributeModifiers();
            }
            this.setDirty();
            return true;
        }
        return false;
    }

    @Override
    public boolean removeAttributeModifier(final AttributeModifier modifier)
    {
        if (this.tag == null)
        {
            return false;
        }
        final NbtTagList list = this.tag.getTag(ATTRIBUTE_MODIFIERS, NbtTagList.class);
        if (list == null)
        {
            return false;
        }
        if (list.removeIf(e -> AttributeModifier.fromNbt((NbtTagCompound) e).equals(modifier)))
        {
            if (list.isEmpty())
            {
                this.removeAttributeModifiers();
            }
            this.setDirty();
            return true;
        }
        return false;
    }

    @Override
    public List<AttributeModifier> getDefaultAttributeModifiers()
    {
        // TODO
        return new ArrayList<>(2);
    }

    @Override
    public boolean hasCustomModifiers()
    {
        return (this.tag != null) && this.tag.containsTag(ATTRIBUTE_MODIFIERS);
    }

    @Override
    public void removeAttributeModifiers()
    {
        if (this.tag == null)
        {
            return;
        }
        this.tag.removeTag(ATTRIBUTE_MODIFIERS);
        this.checkTag(false);
        this.setDirty();
    }

    @SuppressWarnings("CloneDoesntCallSuperClone")
    @Override
    public SimpleItemMetaImpl clone()
    {
        return new SimpleItemMetaImpl((this.tag == null) ? null : this.tag.clone(), this.itemStack);
    }
}
