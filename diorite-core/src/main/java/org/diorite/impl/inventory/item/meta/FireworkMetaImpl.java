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
import java.util.stream.Collectors;

import org.diorite.firework.FireworkEffect;
import org.diorite.inventory.item.ItemStack;
import org.diorite.inventory.item.meta.FireworkMeta;
import org.diorite.nbt.NbtSerialization;
import org.diorite.nbt.NbtTagCompound;
import org.diorite.nbt.NbtTagList;

public class FireworkMetaImpl extends SimpleItemMetaImpl implements FireworkMeta
{
    protected static final String FIREWORK         = "Fireworks";
    protected static final String EFFECTS          = "Explosions";
    protected static final String FIREWORK_EFFECTS = FIREWORK + SEP + EFFECTS;
    protected static final String FIREWORK_POWER   = FIREWORK + SEP + "Flight";

    public FireworkMetaImpl(final NbtTagCompound tag)
    {
        super(tag);
    }

    public FireworkMetaImpl(final NbtTagCompound tag, final ItemStack itemStack)
    {
        super(tag, itemStack);
    }

    @Override
    public void addEffect(final FireworkEffect effect) throws IllegalArgumentException
    {
        this.checkTag(true);
        NbtTagList list = this.tag.getTag(FIREWORK_EFFECTS, NbtTagList.class);
        if (list == null)
        {
            list = new NbtTagList(EFFECTS, 5);
            this.tag.addTag(FIREWORK, list);
        }
        list.add(effect.serializeToNBT());
        this.setDirty();
    }

    @Override
    public void addEffects(final FireworkEffect... effects) throws IllegalArgumentException
    {
        if ((effects == null) || (effects.length == 0))
        {
            return;
        }
        this.checkTag(true);
        NbtTagList list = this.tag.getTag(FIREWORK_EFFECTS, NbtTagList.class);
        if (list == null)
        {
            list = new NbtTagList(EFFECTS, 5);
            this.tag.addTag(FIREWORK, list);
        }
        for (final FireworkEffect effect : effects)
        {
            list.add(effect.serializeToNBT());
        }
        this.setDirty();
    }

    @Override
    public void addEffects(final Iterable<FireworkEffect> effects) throws IllegalArgumentException
    {
        if ((effects == null) || ((effects instanceof Collection) && ((Collection<FireworkEffect>) effects).isEmpty()))
        {
            return;
        }
        this.checkTag(true);
        NbtTagList list = this.tag.getTag(FIREWORK_EFFECTS, NbtTagList.class);
        if (list == null)
        {
            list = new NbtTagList(EFFECTS, 5);
            this.tag.addTag(FIREWORK, list);
        }
        for (final FireworkEffect effect : effects)
        {
            list.add(effect.serializeToNBT());
        }
        this.setDirty();
    }

    @Override
    public List<FireworkEffect> getEffects()
    {
        if (this.tag == null)
        {
            return null;
        }
        final NbtTagList list = this.tag.getTag(FIREWORK_EFFECTS, NbtTagList.class);
        if (list == null)
        {
            return null;
        }
        return list.getTags(NbtTagCompound.class).stream().map(t -> NbtSerialization.deserialize(FireworkEffect.class, t)).collect(Collectors.toList());
    }

    @Override
    public int getEffectsSize()
    {
        if (this.tag == null)
        {
            return 0;
        }
        final NbtTagList list = this.tag.getTag(FIREWORK_EFFECTS, NbtTagList.class);
        if (list == null)
        {
            return 0;
        }
        return list.size();
    }

    @Override
    public void removeEffect(final int index) throws IndexOutOfBoundsException
    {
        if (this.tag == null)
        {
            throw new IndexOutOfBoundsException("There is no effects in this firework.");
        }
        final NbtTagList list = this.tag.getTag(FIREWORK_EFFECTS, NbtTagList.class);
        if (list == null)
        {
            throw new IndexOutOfBoundsException("There is no effects in this firework.");
        }
        if ((index < 0) || (index >= list.size()))
        {
            throw new IndexOutOfBoundsException("Invalid effect index, index must be < 0 and >= amount of effects");
        }
        list.remove(index);
        this.setDirty();
    }

    @Override
    public void removeEffects()
    {
        if (this.tag == null)
        {
            return;
        }
        this.tag.removeTag(FIREWORK_EFFECTS);
        this.checkTag(false);
        this.setDirty();
    }

    @Override
    public boolean hasEffects()
    {
        return (this.tag != null) && this.tag.containsTag(FIREWORK_EFFECTS);
    }

    @Override
    public int getPower()
    {
        if (this.tag == null)
        {
            return 0;
        }
        return this.tag.getByte(FIREWORK_POWER);
    }

    @Override
    public void setPower(final int power)
    {
        this.checkTag(true);
        this.tag.setByte(FIREWORK_POWER, power);
    }

    @SuppressWarnings("CloneDoesntCallSuperClone")
    @Override
    public FireworkMetaImpl clone()
    {
        return new FireworkMetaImpl((this.tag == null) ? null : this.tag.clone(), this.itemStack);
    }
}
