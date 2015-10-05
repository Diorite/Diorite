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

/**
 * Diorite extension of {@link Representer} with more public methods.
 */
public class DioriteYamlRepresenter extends Representer
{
    {
        this.multiRepresenters.put(ConfigurationSection.class, new RepresentConfigurationSection());
        this.multiRepresenters.put(ConfigurationSerializable.class, new RepresentConfigurationSerializable());
    }

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
