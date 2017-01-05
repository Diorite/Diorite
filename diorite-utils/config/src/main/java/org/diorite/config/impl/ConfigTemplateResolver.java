/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2017. Diorite (by Bartłomiej Mazur (aka GotoFinal))
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

package org.diorite.config.impl;

import java.util.Collection;
import java.util.Map;

import org.yaml.snakeyaml.nodes.NodeId;
import org.yaml.snakeyaml.nodes.Tag;
import org.yaml.snakeyaml.resolver.Resolver;

import org.diorite.commons.reflections.DioriteReflectionUtils;
import org.diorite.config.Config;
import org.diorite.config.ConfigPropertyTemplate;
import org.diorite.config.ConfigTemplate;
import org.diorite.config.SimpleConfig;

public class ConfigTemplateResolver extends Resolver
{
    private final ConfigTemplate<?> configTemplate;
    private static final Tag nodeTag = new Tag(SimpleConfig.class);
    private boolean first;

    public ConfigTemplateResolver(ConfigTemplate<?> configTemplate)
    {
        this.configTemplate = configTemplate;
    }

    @Override
    public Tag resolve(NodeId kind, String value, boolean implicit)
    {
        if ((kind == NodeId.mapping) && (value == null) && implicit)
        {
            if (this.first)
            {
                this.first = false;
                return new Tag(this.configTemplate.getConfigType());
            }
            return nodeTag;
        }
        Tag resolve = super.resolve(kind, value, implicit);
        if (value != null)
        {
            ConfigPropertyTemplate<?> templateFor = this.configTemplate.getTemplateFor(value);
            if (templateFor != null)
            {
                Class<?> rawType = templateFor.getRawType();
                if (DioriteReflectionUtils.getPrimitive(rawType).isPrimitive())
                {
                    return resolve;
                }
                if (Collection.class.isAssignableFrom(rawType))
                {
                    return resolve;
                }
                if (Map.class.isAssignableFrom(rawType) && ! Config.class.isAssignableFrom(rawType))
                {
                    return resolve;
                }
                return new Tag(rawType);
            }
        }

        return resolve;
    }
}
