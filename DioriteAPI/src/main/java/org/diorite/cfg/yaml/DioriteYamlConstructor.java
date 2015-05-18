package org.diorite.cfg.yaml;

import java.util.LinkedHashMap;
import java.util.Map;

import org.yaml.snakeyaml.TypeDescription;
import org.yaml.snakeyaml.constructor.Construct;
import org.yaml.snakeyaml.constructor.Constructor;
import org.yaml.snakeyaml.constructor.SafeConstructor;
import org.yaml.snakeyaml.error.YAMLException;
import org.yaml.snakeyaml.nodes.Node;
import org.yaml.snakeyaml.nodes.NodeId;
import org.yaml.snakeyaml.nodes.Tag;

import org.diorite.cfg.simple.serialization.ConfigurationSerialization;

public class DioriteYamlConstructor extends Constructor
{
    {
        this.yamlConstructors.put(Tag.MAP, new ConstructCustomObject());
    }

    public DioriteYamlConstructor()
    {
    }

    public DioriteYamlConstructor(final Class<?> theRoot)
    {
        super(theRoot);
    }

    public DioriteYamlConstructor(final TypeDescription theRoot)
    {
        super(theRoot);
    }

    public DioriteYamlConstructor(final String theRoot) throws ClassNotFoundException
    {
        super(theRoot);
    }

    public Map<Class<?>, TypeDescription> getTypeDefinitions()
    {
        return this.typeDefinitions;
    }

    public Map<NodeId, Construct> getYamlClassConstructors()
    {
        return this.yamlClassConstructors;
    }

    public Map<Tag, Construct> getYamlConstructors()
    {
        return this.yamlConstructors;
    }

    public Map<String, Construct> getYamlMultiConstructors()
    {
        return this.yamlMultiConstructors;
    }

    private class ConstructCustomObject extends SafeConstructor.ConstructYamlMap
    {
        @Override
        public Object construct(final Node node)
        {
            final Map<?, ?> raw = (Map<?, ?>) super.construct(node);
            if (raw.containsKey("==") && ! node.isTwoStepsConstruction())
            {
                final Map<String, Object> typed = new LinkedHashMap<>(raw.size());
                for (final Map.Entry<?, ?> entry : raw.entrySet())
                {
                    typed.put(entry.getKey().toString(), entry.getValue());
                }
                try
                {
                    return ConfigurationSerialization.deserializeObject(typed);
                } catch (final IllegalArgumentException ex)
                {
                    throw new YAMLException("Could not deserialize object", ex);
                }
            }
            return raw;
        }
    }

}
