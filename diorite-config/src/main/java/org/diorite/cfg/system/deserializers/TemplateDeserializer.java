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

package org.diorite.cfg.system.deserializers;

import java.util.Iterator;

import org.yaml.snakeyaml.nodes.MappingNode;
import org.yaml.snakeyaml.nodes.Node;
import org.yaml.snakeyaml.nodes.NodeTuple;
import org.yaml.snakeyaml.nodes.ScalarNode;

import org.diorite.cfg.system.TemplateYamlConstructor;
import org.diorite.utils.reflections.DioriteReflectionUtils;

public abstract class TemplateDeserializer<T>
{
    /**
     * type of supported/handled element.
     */
    protected final Class<T> fieldType;

    /**
     * Create new TemplateDeserializer for given class.
     *
     * @param fieldType class for this deserializer.
     */
    public TemplateDeserializer(final Class<T> fieldType)
    {
        this.fieldType = fieldType;
    }

    /**
     * Check if given class is compatible with this template.
     *
     * @param clazz class to check.
     *
     * @return true if class can be used in this template.
     */
    public final boolean isValidType(final Class<?> clazz)
    {
        return (DioriteReflectionUtils.getPrimitive(this.fieldType).isAssignableFrom(clazz) || DioriteReflectionUtils.getWrapperClass(this.fieldType).isAssignableFrom(clazz)) || this.isValidType0(clazz);
    }

    /**
     * Method to override in sub-classes, allow accepting more types to this deserializer. <br>
     * Checks if given class can be handled by this deserializer.
     *
     * @param clazz clazz to test.
     *
     * @return true if this deserializer can handle given class.
     */
    protected boolean isValidType0(final Class<?> clazz)
    {
        return false;
    }

    /**
     * Returns yaml constructor for given mapping node.
     *
     * @param constructMapping mapping instance.
     * @param node             mapping node to deserialize.
     *
     * @return yaml constructor for given mapping node.
     */
    public abstract T construct(final TemplateYamlConstructor.TemplateConstructMapping constructMapping, MappingNode node);

    /**
     * Returns node for given key from mapping node instance.
     *
     * @param node mapping node instance.
     * @param key  key of node.
     *
     * @return node for given key or null.
     */
    public static Node getNode(final MappingNode node, final String key)
    {
        return getNode(node, key, false);
    }

    /**
     * Returns and removes node for given key from mapping node instance.
     *
     * @param node mapping node instance.
     * @param key  key of node.
     *
     * @return node for given key or null.
     */
    public static Node getAndRemoveNode(final MappingNode node, final String key)
    {
        return getNode(node, key, true);
    }

    /**
     * Returns node for given key from mapping node instance.
     *
     * @param node mapping node instance.
     * @param key  key of node.
     *
     * @return node for given key or null.
     */
    public static Node getNode(final MappingNode node, final String... key)
    {
        return getNode(node, 0, false, key);
    }

    /**
     * Returns and removes node for given path from mapping node instance.
     *
     * @param node mapping node instance.
     * @param path path of node.
     *
     * @return node for given key or null.
     */
    public static Node getAndRemoveNode(final MappingNode node, final String... path)
    {
        return getNode(node, 0, true, path);
    }

    private static Node getNode(final MappingNode node, final String key, final boolean remove)
    {
        for (final Iterator<NodeTuple> it = node.getValue().iterator(); it.hasNext(); )
        {
            final NodeTuple nodeTuple = it.next();
            final Node keyNode = nodeTuple.getKeyNode();
            if (keyNode instanceof ScalarNode)
            {
                if (key.equals(((ScalarNode) keyNode).getValue()))
                {
                    if (remove)
                    {
                        it.remove();
                    }
                    return nodeTuple.getValueNode();
                }
            }
        }
        return null;
    }

    private static Node getNode(final MappingNode node, final int keyIndex, final boolean remove, final String... key)
    {
        if ((key == null) || (key.length == 0) || (keyIndex >= key.length))
        {
            return null;
        }
        for (final Iterator<NodeTuple> it = node.getValue().iterator(); it.hasNext(); )
        {
            final NodeTuple nodeTuple = it.next();
            final Node keyNode = nodeTuple.getKeyNode();
            if ((keyNode instanceof ScalarNode))
            {//(keyIndex == (key.length - 1))
                if (key[keyIndex].equals(((ScalarNode) keyNode).getValue()))
                {
                    final Node valueNode = nodeTuple.getValueNode();
                    if (keyIndex == (key.length - 1))
                    {
                        if (remove)
                        {
                            it.remove();
                        }
                        return valueNode;
                    }
                    if (valueNode instanceof MappingNode)
                    {
                        return getNode(node, keyIndex + 1, remove, key);
                    }
                    else
                    {
                        return null;
                    }
                }
            }
        }
        return null;
    }
}
