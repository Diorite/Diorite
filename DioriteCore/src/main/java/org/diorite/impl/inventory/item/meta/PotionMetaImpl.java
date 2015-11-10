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

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.diorite.effect.StatusEffect;
import org.diorite.effect.StatusEffectType;
import org.diorite.inventory.item.ItemStack;
import org.diorite.inventory.item.meta.PotionMeta;
import org.diorite.nbt.NbtSerialization;
import org.diorite.nbt.NbtTagCompound;
import org.diorite.nbt.NbtTagList;

public class PotionMetaImpl extends SimpleItemMetaImpl implements PotionMeta
{
    protected static final String EFFECTS = "CustomPotionEffects";
    protected static final String POTION  = "Potion";

    public PotionMetaImpl(final NbtTagCompound tag)
    {
        super(tag);
    }

    public PotionMetaImpl(final NbtTagCompound tag, final ItemStack itemStack)
    {
        super(tag, itemStack);
    }

    @Override
    public boolean hasCustomEffects()
    {
        return (this.tag != null) && ! this.tag.getTag(EFFECTS, NbtTagList.class, new NbtTagList(EFFECTS, 1)).isEmpty();
    }

    @Override
    public List<StatusEffect> getCustomEffects()
    {
        if (this.tag == null)
        {
            return null;
        }
        final NbtTagList list = this.tag.getTag(EFFECTS, NbtTagList.class);
        if (list == null)
        {
            return null;
        }
        return list.getTags(NbtTagCompound.class).stream().map(t -> NbtSerialization.deserialize(StatusEffect.class, t)).collect(Collectors.toList());
    }

    @Override
    public void setCustomEffects(final Collection<StatusEffect> effects)
    {
        this.checkTag(true);
        this.tag.removeTag(EFFECTS);
        final NbtTagList list = new NbtTagList(EFFECTS, effects.isEmpty() ? 1 : effects.size());
        list.addAll(effects.stream().map(StatusEffect::serializeToNBT).collect(Collectors.toList()));
        this.tag.addTag(list);
        this.setDirty();
    }

    @Override
    public boolean addCustomEffect(final StatusEffect effect, final boolean overwrite)
    {
        this.checkTag(true);
        NbtTagList list = this.tag.getTag(EFFECTS, NbtTagList.class);
        if (list == null)
        {
            list = new NbtTagList(EFFECTS, 4);
            this.tag.addTag(list);
        }
        int same = - 1;
        if (overwrite)
        {
            for (int i = 0; i < list.size(); i++)
            {
                final StatusEffect statusEffect = NbtSerialization.deserialize(StatusEffect.class, (NbtTagCompound) list.get(i));
                assert statusEffect != null;
                if (Objects.equals(effect.getType(), statusEffect.getType()))
                {
                    if (effect.equals(statusEffect))
                    {
                        return false;
                    }
                    same = i;
                    break;
                }
            }
        }
        if (same != - 1)
        {
            list.set(same, effect.serializeToNBT());
        }
        else
        {
            list.add(effect.serializeToNBT());
        }
        this.setDirty();
        return true;
    }

    @Override
    public boolean removeCustomEffect(final StatusEffectType type)
    {
        if (this.tag == null)
        {
            return false;
        }
        final NbtTagList list = this.tag.getTag(EFFECTS, NbtTagList.class);
        if (list == null)
        {
            return false;
        }
        if (list.removeIf(t -> new StatusEffect((NbtTagCompound) t).getType().equals(type)))
        {
            if (list.isEmpty())
            {
                this.clearCustomEffects();
            }
            this.setDirty();
            return true;
        }
        return false;
    }

    @Override
    public boolean hasCustomEffect(final StatusEffectType type)
    {
        if (this.tag == null)
        {
            return false;
        }
        final NbtTagList list = this.tag.getTag(EFFECTS, NbtTagList.class);
        return (list != null) && list.stream().anyMatch(t -> new StatusEffect((NbtTagCompound) t).getType().equals(type));
    }

    @Override
    public boolean clearCustomEffects()
    {
        if (this.tag == null)
        {
            return false;
        }
        final boolean result = this.tag.removeTag(EFFECTS) != null;
        this.checkTag(false);
        this.setDirty();
        return result;
    }

    @Override
    public String getPotionID()
    {
        if (this.tag == null)
        {
            return null;
        }
        return this.tag.getString(POTION);
    }

    @Override
    public void setPotionID(final String id)
    {
        this.setStringTag(POTION, id);
    }

    @SuppressWarnings("CloneDoesntCallSuperClone")
    @Override
    public PotionMetaImpl clone()
    {
        return new PotionMetaImpl((this.tag == null) ? null : this.tag.clone(), this.itemStack);
    }
}
