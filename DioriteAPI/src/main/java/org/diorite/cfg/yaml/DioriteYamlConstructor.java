package org.diorite.cfg.yaml;

import java.beans.IntrospectionException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import org.yaml.snakeyaml.TypeDescription;
import org.yaml.snakeyaml.constructor.Construct;
import org.yaml.snakeyaml.constructor.Constructor;
import org.yaml.snakeyaml.constructor.SafeConstructor;
import org.yaml.snakeyaml.error.Mark;
import org.yaml.snakeyaml.error.YAMLException;
import org.yaml.snakeyaml.introspector.GenericProperty;
import org.yaml.snakeyaml.introspector.Property;
import org.yaml.snakeyaml.nodes.Node;
import org.yaml.snakeyaml.nodes.NodeId;
import org.yaml.snakeyaml.nodes.ScalarNode;
import org.yaml.snakeyaml.nodes.Tag;

import org.diorite.cfg.simple.serialization.ConfigurationSerialization;
import org.diorite.cfg.system.ConfigField;
import org.diorite.cfg.system.Template;
import org.diorite.cfg.system.TemplateCreator;
import org.diorite.cfg.system.elements.TemplateElement;
import org.diorite.cfg.system.elements.TemplateElements;
import org.diorite.utils.reflections.ReflectElement;

public class DioriteYamlConstructor extends Constructor
{
    {
        this.yamlConstructors.put(Tag.MAP, new ConstructCustomObject());
        this.yamlClassConstructors.put(NodeId.scalar, new ConstructCustomElementObject());
        this.yamlClassConstructors.put(NodeId.mapping, new DioriteConstructMapping());
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

    @SuppressWarnings("ObjectEquality")
    protected class ConstructCustomElementObject extends ConstructScalar
    {
        @SuppressWarnings("unchecked")
        protected Object constructStandardJavaInstance(@SuppressWarnings("rawtypes") final Class type, final ScalarNode node)
        {
            Object result;
            if (type == String.class)
            {
                final Construct stringConstructor = DioriteYamlConstructor.this.yamlConstructors.get(Tag.STR);
                result = stringConstructor.construct(node);
            }
            else if ((type == Boolean.class) || (type == Boolean.TYPE))
            {
                final Construct boolConstructor = DioriteYamlConstructor.this.yamlConstructors.get(Tag.BOOL);
                result = boolConstructor.construct(node);
            }
            else if ((type == Character.class) || (type == Character.TYPE))
            {
                final Construct charConstructor = DioriteYamlConstructor.this.yamlConstructors.get(Tag.STR);
                final String ch = (String) charConstructor.construct(node);
                if (ch.isEmpty())
                {
                    result = null;
                }
                else if (ch.length() != 1)
                {
                    throw new YAMLException("Invalid node Character: '" + ch + "'; length: " + ch.length());
                }
                else
                {
                    result = ch.charAt(0);
                }
            }
            else if (Date.class.isAssignableFrom(type))
            {
                final Construct dateConstructor = DioriteYamlConstructor.this.yamlConstructors.get(Tag.TIMESTAMP);
                final Date date = (Date) dateConstructor.construct(node);
                if (type == Date.class)
                {
                    result = date;
                }
                else
                {
                    try
                    {
                        final java.lang.reflect.Constructor<?> constr = type.getConstructor(long.class);
                        result = constr.newInstance(date.getTime());
                    } catch (final RuntimeException e)
                    {
                        throw e;
                    } catch (final Exception e)
                    {
                        throw new YAMLException("Cannot construct: '" + type + "'");
                    }
                }
            }
            else if ((type == Float.class) || (type == Double.class) || (type == Float.TYPE) || (type == Double.TYPE) || (type == BigDecimal.class))
            {
                if (type == BigDecimal.class)
                {
                    result = new BigDecimal(node.getValue());
                }
                else
                {
                    final Construct doubleConstructor = DioriteYamlConstructor.this.yamlConstructors.get(Tag.FLOAT);
                    result = doubleConstructor.construct(node);
                    if ((type == Float.class) || (type == Float.TYPE))
                    {
                        result = new Float((Double) result);
                    }
                }
            }
            else if ((type == Byte.class) || (type == Short.class) || (type == Integer.class) || (type == Long.class) || (type == BigInteger.class) || (type == Byte.TYPE) || (type == Short.TYPE) || (type == Integer.TYPE) || (type == Long.TYPE))
            {
                final Construct intConstructor = DioriteYamlConstructor.this.yamlConstructors.get(Tag.INT);
                result = intConstructor.construct(node);
                if ((type == Byte.class) || (type == Byte.TYPE))
                {
                    result = Byte.valueOf(result.toString());
                }
                else if ((type == Short.class) || (type == Short.TYPE))
                {
                    result = Short.valueOf(result.toString());
                }
                else if ((type == Integer.class) || (type == Integer.TYPE))
                {
                    result = Integer.parseInt(result.toString());
                }
                else if ((type == Long.class) || (type == Long.TYPE))
                {
                    result = Long.valueOf(result.toString());
                }
                else
                {
                    // only BigInteger left
                    result = new BigInteger(result.toString());
                }
            }
            else if (Enum.class.isAssignableFrom(type))
            {
                final String enumValueName = node.getValue();
                try
                {
                    result = Enum.valueOf(type, enumValueName);
                } catch (final Exception ex)
                {
                    throw new YAMLException("Unable to find enum value '" + enumValueName + "' for enum class: " + type.getName());
                }
            }
            else if (Calendar.class.isAssignableFrom(type))
            {
                final ConstructYamlTimestamp contr = new ConstructYamlTimestamp();
                contr.construct(node);
                result = contr.getCalendar();
            }
            else if (Number.class.isAssignableFrom(type))
            {
                final Construct contr = new ConstructYamlNumber();
                result = contr.construct(node);
            }
            else
            {
                throw new YAMLException("Unsupported class: " + type);
            }
            return result;
        }

        @Override
        public Object construct(final Node nnode)
        {
            final ScalarNode node = (ScalarNode) nnode;
            final Class<?> type = node.getType();
            final Object result;
            if (type.isPrimitive() || (type == String.class) || Number.class.isAssignableFrom(type) || (type == Boolean.class) || Date.class.isAssignableFrom(type) || (type == Character.class) || (type == BigInteger.class) || (type == BigDecimal.class) || Enum.class.isAssignableFrom(type) || Tag.BINARY.equals(node.getTag()) || Calendar.class.isAssignableFrom(type))
            {
                // standard classes created directly
                result = this.constructStandardJavaInstance(type, node);
            }
            else
            {
                // trying to get diorite template element
                final TemplateElement<?> templateElement = TemplateElements.getElement(type);
                if (templateElement != TemplateElements.getDefaultTemplatesHandler())
                {
                    return templateElement.convertDefault(node.getValue(), type);
                }
                // there must be only 1 constructor with 1 argument
                final java.lang.reflect.Constructor<?>[] javaConstructors = type.getConstructors();
                int oneArgCount = 0;
                java.lang.reflect.Constructor<?> javaConstructor = null;
                for (final java.lang.reflect.Constructor<?> c : javaConstructors)
                {
                    if (c.getParameterTypes().length == 1)
                    {
                        oneArgCount++;
                        javaConstructor = c;
                    }
                }
                final Object argument;
                if (javaConstructor == null)
                {
                    throw new YAMLException("No single argument constructor found for " + type);
                }
                if (oneArgCount == 1)
                {
                    argument = this.constructStandardJavaInstance(javaConstructor.getParameterTypes()[0], node);
                }
                else
                {
                    argument = DioriteYamlConstructor.this.constructScalar(node);
                    try
                    {
                        javaConstructor = type.getConstructor(String.class);
                    } catch (final Exception e)
                    {
                        throw new YAMLException("Can't construct a java object for scalar " + node.getTag() + "; No String constructor found. Exception=" + e.getMessage(), e);
                    }
                }
                try
                {
                    result = javaConstructor.newInstance(argument);
                } catch (final Exception e)
                {
//                    throw new RuntimeException(e);
                    throw new ConstructorException(null, null, "Can't construct a java object for scalar " + node.getTag() + "; exception=" + e.getMessage(), node.getStartMark(), e);
                }
            }
            return result;
        }
    }

    protected class DioriteConstructMapping extends ConstructMapping
    {
        @Override
        protected Property getProperty(final Class<?> type, final String name) throws IntrospectionException
        {
            final Template<?> template = TemplateCreator.getTemplate(type, false);
            if (template != null)
            {
                final ConfigField field = template.getFieldsNameMap().get(name);
                if (field != null)
                {
                    final ReflectElement<?> prop = template.getFields().get(field);
                    return new GenericProperty(name, field.getField().getType(), prop.getGenericType())
                    {
                        @Override
                        public void set(final Object object, final Object value) throws Exception
                        {
                            prop.set(object, value);
                        }

                        @Override
                        public Object get(final Object object)
                        {
                            return prop.get(object);
                        }
                    };
                }
                System.out.println("[YAML] Unknown entry in one of confguration file on " + type.getName() + "#" + name + ". Skipping...");
                return EMPTY;
            }
            return super.getProperty(type, name);
        }
    }

    private static final Property EMPTY = new Property("empty", Object.class)
    {
        @Override
        public Class<?>[] getActualTypeArguments()
        {
            return new Class<?>[0];
        }

        @Override
        public void set(final Object object, final Object value) throws Exception
        {

        }

        @Override
        public Object get(final Object object)
        {
            return null;
        }
    };


    public static class ConstructorException extends org.yaml.snakeyaml.constructor.ConstructorException
    {
        private static final long serialVersionUID = 0;

        protected ConstructorException(final String context, final Mark contextMark, final String problem, final Mark problemMark, final Throwable cause)
        {
            super(context, contextMark, problem, problemMark, cause);
        }

        protected ConstructorException(final String context, final Mark contextMark, final String problem, final Mark problemMark)
        {
            super(context, contextMark, problem, problemMark);
        }
    }
}
