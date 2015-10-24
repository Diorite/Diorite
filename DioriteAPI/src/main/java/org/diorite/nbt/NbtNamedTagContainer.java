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

package org.diorite.nbt;

import java.util.Map;

/**
 * Represent {@link NbtTagContainer} where all elements have name.
 */
public interface NbtNamedTagContainer extends NbtTagContainer, Map<String, NbtTag>
{
    /**
     * Returns nbt tag with given name from this container.
     *
     * @param name name of nbt tag to get.
     * @param <T>  type of nbt tag.
     *
     * @return nbt tag from this container, or null.
     */
    <T extends NbtTag> T getTag(String name);

    /**
     * Returns nbt tag with given name from this container.
     *
     * @param name     name of nbt tag to get.
     * @param tagClass type of nbt tag.
     * @param <T>      type of nbt tag.
     *
     * @return nbt tag from this container, or null.
     */
    <T extends NbtTag> T getTag(String name, Class<T> tagClass);

    /**
     * Returns nbt tag with given name from this container.
     *
     * @param name name of nbt tag to get.
     * @param def  default value for this tag.
     * @param <T>  type of nbt tag.
     *
     * @return nbt tag from this container, or default value.
     */
    <T extends NbtTag> T getTag(String name, T def);

    /**
     * Returns nbt tag with given name from this container.
     *
     * @param name     name of nbt tag to get.
     * @param tagClass type of nbt tag.
     * @param def      default value for this tag.
     * @param <T>      type of nbt tag.
     *
     * @return nbt tag from this container, or default value.
     */
    <T extends NbtTag> T getTag(String name, Class<T> tagClass, T def);

    /**
     * Returns map of nbt tags in this container.
     *
     * @return map of nbt tags in this container.
     */
    Map<String, NbtTag> getTags();

    /**
     * Remove nbt tag with given name from this container.
     *
     * @param tag name of tag to be removed.
     *
     * @return removed nbt tag.
     */
    NbtTag removeTag(String tag);

    /**
     * Add new NbtTag to this container, {@link NbtTag#getName()} will be used to get name for this value.
     *
     * @param tag nbt tag to be added.
     */
    void addTag(NbtTag tag);

    /**
     * Set NbtTag on given key to given value.
     *
     * @param key key of nbt tag.
     * @param tag nbt tag to set.
     */
    void setTag(String key, NbtTag tag);

    /**
     * Add NbtTag on given path to this container.
     *
     * @param path path to nbt tag.
     * @param tag  nbt tag to be added.
     */
    void addTag(String path, NbtTag tag);

    /**
     * Returns true if this container contains nbt tag with given name.
     *
     * @param name name of nbt tag to be checked.
     *
     * @return true if this container contains nbt tag with given name.
     */
    boolean containsTag(String name);
}