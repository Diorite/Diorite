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

package org.diorite.config.serialization.snakeyaml;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.yaml.snakeyaml.constructor.Construct;
import org.yaml.snakeyaml.constructor.Constructor;
import org.yaml.snakeyaml.nodes.MappingNode;
import org.yaml.snakeyaml.nodes.Node;
import org.yaml.snakeyaml.nodes.ScalarNode;
import org.yaml.snakeyaml.nodes.SequenceNode;
import org.yaml.snakeyaml.nodes.Tag;

public class YamlConstructor extends Constructor
{
    public void addConstruct(Class<?> type, Construct construct)
    {
        this.yamlConstructors.put(new Tag(type), construct);
    }

    @Override
    public void constructMapping2ndStep(MappingNode node, Map<Object, Object> mapping)
    {
        super.constructMapping2ndStep(node, mapping);
    }

    @Override
    public void constructSet2ndStep(MappingNode node, Set<Object> set)
    {
        super.constructSet2ndStep(node, set);
    }

    /**
     * Construct object from the specified Node. Return existing instance if the
     * node is already constructed.
     *
     * @param node
     *         Node to be constructed
     *
     * @return Java instance
     */
    @Override
    public Object constructObject(Node node)
    {
        return super.constructObject(node);
    }

    /**
     * Get the constructor to construct the Node. For implicit tags if the
     * runtime class is known a dedicated Construct implementation is used.
     * Otherwise the constructor is chosen by the tag.
     *
     * @param node
     *         Node to be constructed
     *
     * @return Construct implementation for the specified node
     */
    @Override
    public Construct getConstructor(Node node)
    {
        return super.getConstructor(node);
    }

    @Override
    public Object constructScalar(ScalarNode node)
    {
        return super.constructScalar(node);
    }

    @Override
    public List<?> constructSequence(SequenceNode node)
    {
        return super.constructSequence(node);
    }

    @Override
    public Set<?> constructSet(SequenceNode node)
    {
        return super.constructSet(node);
    }

    @Override
    public Object constructArray(SequenceNode node)
    {
        return super.constructArray(node);
    }

    @Override
    public void constructSequenceStep2(SequenceNode node, Collection<Object> collection)
    {
        super.constructSequenceStep2(node, collection);
    }

    @Override
    public Object constructArrayStep2(SequenceNode node, Object array)
    {
        return super.constructArrayStep2(node, array);
    }

    @Override
    public Set<Object> constructSet(MappingNode node)
    {
        return super.constructSet(node);
    }

    @Override
    public Map<Object, Object> constructMapping(MappingNode node)
    {
        return super.constructMapping(node);
    }
}
