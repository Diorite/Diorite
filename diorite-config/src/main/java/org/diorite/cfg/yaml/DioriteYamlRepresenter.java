/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2016. Diorite (by Bartłomiej Mazur (aka GotoFinal))
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

package org.diorite.cfg.yaml;

import java.util.Map;

import org.yaml.snakeyaml.nodes.Node;
import org.yaml.snakeyaml.nodes.Tag;
import org.yaml.snakeyaml.representer.Represent;
import org.yaml.snakeyaml.representer.Representer;

/**
 * Diorite extension of {@link Representer} with more public methods.
 */
public class DioriteYamlRepresenter extends Representer
{
    /**
     * Returns representers map of this yaml representer instance.
     *
     * @return representers map of this yaml representer instance.
     */
    public Map<Class<?>, Represent> getRepresenters()
    {
        return this.representers;
    }

    /**
     * Returns null representer of this yaml representer instance.
     *
     * @return null representer of this yaml representer instance.
     */
    public Represent getNullRepresenter()
    {
        return this.nullRepresenter;
    }

    /**
     * Set null representer of this yaml representer instance.
     *
     * @param nullRepresenter new null representer.
     */
    public void setNullRepresenter(final Represent nullRepresenter)
    {
        this.nullRepresenter = nullRepresenter;
    }

    /**
     * Returns representers map of this yaml representer instance.
     *
     * @return representers map of this yaml representer instance.
     */
    public Map<Class<?>, Represent> getMultiRepresenters()
    {
        return this.multiRepresenters;
    }

    /**
     * Returns default scalar type of this yaml representer instance.
     *
     * @return default scalar type of this yaml representer instance.
     */
    public Character getDefaultScalarStyle()
    {
        return this.defaultScalarStyle;
    }

    /**
     * Set default scalar type of this yaml representer instance.
     *
     * @param character new default scalar type.
     */
    public void setDefaultScalarStyle(final Character character)
    {
        this.defaultScalarStyle = character;
    }

    /**
     * Returns represented objects of this yaml representer instance.
     *
     * @return represented objects of this yaml representer instance.
     */
    public Map<Object, Node> getRepresentedObjects()
    {
        return this.representedObjects;
    }

    /**
     * Returns class tag map of this yaml representer instance.
     *
     * @return class tag map of this yaml representer instance.
     */
    public Map<Class<?>, Tag> getClassTags()
    {
        return this.classTags;
    }

    /**
     * Set class tag map of this yaml representer instance.
     *
     * @param classTags new class tag map.
     */
    public void setClassTags(final Map<Class<?>, Tag> classTags)
    {
        this.classTags = classTags;
    }
}
