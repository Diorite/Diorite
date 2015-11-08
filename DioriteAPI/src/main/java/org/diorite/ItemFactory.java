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

package org.diorite;

import java.util.function.Function;

import org.diorite.inventory.item.meta.ItemMeta;
import org.diorite.material.Material;
import org.diorite.nbt.NbtTagCompound;

/**
 * Helper class to construct item meta implementation object by interface classes.
 */
public interface ItemFactory
{
    /**
     * Register new meta type to this item factory.
     *
     * @param clazz       class of ItemMeta (api interface if possible, don't use implementation class here)
     * @param constructor constructor of item meta, constructor function must support null nbt data.
     * @param <T>         type of ItemMeta.
     */
    <T extends ItemMeta> void registerMetaType(Class<T> clazz, Function<NbtTagCompound, T> constructor);

    /**
     * Unregister meta constructor for given meta class.
     *
     * @param clazz class of ItemMeta, this same as used in {@link #registerMetaType(Class, Function)}
     */
    void unregisterMetaType(Class<? extends ItemMeta> clazz);

    /**
     * Construct new ItemMeta instance for given material, {@link Material#getMetaType()} is used to get type of meta data.
     *
     * @param material material to be used.
     * @param nbt      nbt data to be used, null value allowed.
     *
     * @return constructed ItemMeta instance.
     */
    ItemMeta construct(Material material, NbtTagCompound nbt);

    /**
     * Construct new ItemMeta instance for given item meta class, api class must be used.
     *
     * @param clazz item meta class used in {@link #registerMetaType(Class, Function)}, don't use implementation classes here.
     * @param nbt   nbt data to be used, null value allowed.
     *
     * @return constructed ItemMeta instance.
     */
    <T extends ItemMeta> T construct(Class<T> clazz, NbtTagCompound nbt);

    /**
     * Construct new ItemMeta instance for given material, {@link Material#getMetaType()} is used to get type of meta data.
     *
     * @param material material to be used.
     *
     * @return constructed ItemMeta instance.
     */
    default ItemMeta construct(final Material material)
    {
        return this.construct(material, null);
    }

    /**
     * Construct new ItemMeta instance for given item meta class, api class must be used.
     *
     * @param clazz item meta class used in {@link #registerMetaType(Class, Function)}, don't use implementation classes here.
     *
     * @return constructed ItemMeta instance.
     */
    default <T extends ItemMeta> T construct(final Class<T> clazz)
    {
        return this.construct(clazz, null);
    }
}
