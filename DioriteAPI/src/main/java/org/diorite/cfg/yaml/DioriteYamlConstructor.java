package org.diorite.cfg.yaml;

import java.util.Map;

import org.yaml.snakeyaml.TypeDescription;
import org.yaml.snakeyaml.constructor.Construct;
import org.yaml.snakeyaml.constructor.Constructor;
import org.yaml.snakeyaml.nodes.NodeId;
import org.yaml.snakeyaml.nodes.Tag;

public class DioriteYamlConstructor extends Constructor
{
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
}
