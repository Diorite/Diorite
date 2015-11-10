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

package org.diorite.impl;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

import org.apache.commons.lang3.Validate;

import org.diorite.impl.inventory.item.meta.BannerMetaImpl;
import org.diorite.impl.inventory.item.meta.BookMetaImpl;
import org.diorite.impl.inventory.item.meta.EnchantmentStorageMetaImpl;
import org.diorite.impl.inventory.item.meta.FireworkEffectMetaImpl;
import org.diorite.impl.inventory.item.meta.FireworkMetaImpl;
import org.diorite.impl.inventory.item.meta.LeatherArmorMetaImpl;
import org.diorite.impl.inventory.item.meta.MapMetaImpl;
import org.diorite.impl.inventory.item.meta.PotionMetaImpl;
import org.diorite.impl.inventory.item.meta.RepairableMetaImpl;
import org.diorite.impl.inventory.item.meta.SimpleItemMetaImpl;
import org.diorite.impl.inventory.item.meta.SkullMetaImpl;
import org.diorite.impl.inventory.item.meta.ToolMetaImpl;
import org.diorite.ItemFactory;
import org.diorite.inventory.item.meta.BannerMeta;
import org.diorite.inventory.item.meta.BookMeta;
import org.diorite.inventory.item.meta.EnchantmentStorageMeta;
import org.diorite.inventory.item.meta.FireworkEffectMeta;
import org.diorite.inventory.item.meta.FireworkMeta;
import org.diorite.inventory.item.meta.ItemMeta;
import org.diorite.inventory.item.meta.LeatherArmorMeta;
import org.diorite.inventory.item.meta.MapMeta;
import org.diorite.inventory.item.meta.PotionMeta;
import org.diorite.inventory.item.meta.RepairableMeta;
import org.diorite.inventory.item.meta.SkullMeta;
import org.diorite.inventory.item.meta.ToolMeta;
import org.diorite.material.Material;
import org.diorite.nbt.NbtTagCompound;

public class ItemFactoryImpl implements ItemFactory
{
    private final Map<Class<? extends ItemMeta>, Function<NbtTagCompound, ? extends ItemMeta>> metaTypes = new ConcurrentHashMap<>(10, 0.3F, 4);

    {
        this.metaTypes.put(BannerMeta.class, BannerMetaImpl::new);
        this.metaTypes.put(BookMeta.class, BookMetaImpl::new);
        this.metaTypes.put(EnchantmentStorageMeta.class, EnchantmentStorageMetaImpl::new);
        this.metaTypes.put(FireworkEffectMeta.class, FireworkEffectMetaImpl::new);
        this.metaTypes.put(FireworkMeta.class, FireworkMetaImpl::new);
        this.metaTypes.put(LeatherArmorMeta.class, LeatherArmorMetaImpl::new);
        this.metaTypes.put(MapMeta.class, MapMetaImpl::new);
        this.metaTypes.put(PotionMeta.class, PotionMetaImpl::new);
        this.metaTypes.put(RepairableMeta.class, RepairableMetaImpl::new);
        this.metaTypes.put(SkullMeta.class, SkullMetaImpl::new);
        this.metaTypes.put(ToolMeta.class, ToolMetaImpl::new);
        // TODO add rest
    }

    @Override
    public <T extends ItemMeta> void registerMetaType(final Class<T> clazz, final Function<NbtTagCompound, T> constructor)
    {
        Validate.notNull(clazz, "Class can't be null.");
        Validate.notNull(constructor, "Constructor can't be null.");
        Validate.isTrue(constructor.apply(null) != null, "Constructor function must support null values.");
        this.metaTypes.put(clazz, constructor);
    }

    @Override
    public void unregisterMetaType(final Class<? extends ItemMeta> clazz)
    {
        Validate.notNull(clazz, "Class can't be null.");
        this.metaTypes.remove(clazz);
    }

    @Override
    public ItemMeta construct(final Material material, final NbtTagCompound nbt)
    {
        final Function<NbtTagCompound, ? extends ItemMeta> constructor = this.metaTypes.get(material.getMetaType());
        if (constructor == null)
        {
            return new SimpleItemMetaImpl(nbt);
        }
        return constructor.apply(nbt);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T extends ItemMeta> T construct(final Class<T> clazz, final NbtTagCompound nbt)
    {
        if (clazz.equals(ItemMeta.class))
        {
            return (T) new SimpleItemMetaImpl(nbt);
        }
        final Function<NbtTagCompound, ? extends ItemMeta> constructor = this.metaTypes.get(clazz);
        if (constructor == null)
        {
            throw new IllegalArgumentException("No constructor registered for " + clazz.getCanonicalName() + " meta type.");
        }
        return (T) constructor.apply(nbt);
    }
}
