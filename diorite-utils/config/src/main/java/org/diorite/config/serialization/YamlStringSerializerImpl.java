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

package org.diorite.config.serialization;

import org.yaml.snakeyaml.constructor.Construct;
import org.yaml.snakeyaml.error.YAMLException;
import org.yaml.snakeyaml.nodes.AnchorNode;
import org.yaml.snakeyaml.nodes.Node;
import org.yaml.snakeyaml.nodes.ScalarNode;

import org.diorite.commons.reflections.DioriteReflectionUtils;
import org.diorite.config.serialization.snakeyaml.AbstractRepresent;
import org.diorite.config.serialization.snakeyaml.Representer;

class YamlStringSerializerImpl<T> extends AbstractRepresent implements Construct
{
    private final StringSerializer<T> stringSerializer;

    YamlStringSerializerImpl(Representer representer, StringSerializer<T> stringSerializer)
    {
        super(representer);
        this.stringSerializer = stringSerializer;
    }

    public Class<? super T> getType()
    {
        return this.stringSerializer.getType();
    }

    @SuppressWarnings("unchecked")
    @Override
    public Node representData(Object data)
    {
        return this.representString(this.stringSerializer.serialize((T) data));
    }

    @Override
    public Object construct(Node node)
    {
        while (node instanceof AnchorNode)
        {
            node = ((AnchorNode) node).getRealNode();
        }
        if (node instanceof ScalarNode)
        {
            Class<?> type;
            if ((node.getType() == null) || Object.class.equals(node.getType()))
            {
                try
                {
                    type = DioriteReflectionUtils.tryGetCanonicalClass(node.getTag().getClassName());
                }
                catch (YAMLException e)
                {
                    type = null;
                }
            }
            else
            {
                type = node.getType();
            }
            return this.stringSerializer.deserialize(((ScalarNode) node).getValue(), type);
        }
        throw new RuntimeException("Can't deserialize simple string from yaml node: " + node);
    }

    @Override
    public void construct2ndStep(Node node, Object object)
    {
// skip
    }
}
