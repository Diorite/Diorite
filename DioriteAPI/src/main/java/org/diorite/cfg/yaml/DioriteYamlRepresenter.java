package org.diorite.cfg.yaml;

import java.util.LinkedHashMap;
import java.util.Map;

import org.yaml.snakeyaml.nodes.Node;
import org.yaml.snakeyaml.nodes.Tag;
import org.yaml.snakeyaml.representer.Represent;
import org.yaml.snakeyaml.representer.Representer;

import org.diorite.cfg.simple.ConfigurationSection;
import org.diorite.cfg.simple.serialization.ConfigurationSerializable;
import org.diorite.cfg.simple.serialization.ConfigurationSerialization;

public class DioriteYamlRepresenter extends Representer
{
    {
        this.multiRepresenters.put(ConfigurationSection.class, new RepresentConfigurationSection());
        this.multiRepresenters.put(ConfigurationSerializable.class, new RepresentConfigurationSerializable());
    }

    public Map<Class<?>, Represent> getRepresenters()
    {
        return this.representers;
    }

    public Represent getNullRepresenter()
    {
        return this.nullRepresenter;
    }

    public void setNullRepresenter(final Represent nullRepresenter)
    {
        this.nullRepresenter = nullRepresenter;
    }

    public Map<Class<?>, Represent> getMultiRepresenters()
    {
        return this.multiRepresenters;
    }

    public Character getDefaultScalarStyle()
    {
        return this.defaultScalarStyle;
    }

    public void setDefaultScalarStyle(final Character character)
    {
        this.defaultScalarStyle = character;
    }

    public Map<Object, Node> getRepresentedObjects()
    {
        return this.representedObjects;
    }

    public Map<Class<?>, Tag> getClassTags()
    {
        return this.classTags;
    }

    public void setClassTags(final Map<Class<?>, Tag> classTags)
    {
        this.classTags = classTags;
    }

    private class RepresentConfigurationSection extends RepresentMap
    {
        @Override
        public Node representData(final Object data)
        {
            return super.representData(((ConfigurationSection) data).getValues(false));
        }
    }

    private class RepresentConfigurationSerializable extends RepresentMap
    {
        @Override
        public Node representData(final Object data)
        {
            final ConfigurationSerializable serializable = (ConfigurationSerializable) data;
            final Map<String, Object> serialized = serializable.serialize();
            final Map<String, Object> values = new LinkedHashMap<>(serialized.size() + 1);
            values.put(ConfigurationSerialization.SERIALIZED_TYPE_KEY, ConfigurationSerialization.getAlias(serializable.getClass()));
            values.putAll(serialized);

            return super.representData(values);
        }
    }
}
