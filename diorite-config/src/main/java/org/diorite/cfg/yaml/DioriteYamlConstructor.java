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

import java.beans.IntrospectionException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import org.yaml.snakeyaml.TypeDescription;
import org.yaml.snakeyaml.constructor.Construct;
import org.yaml.snakeyaml.constructor.Constructor;
import org.yaml.snakeyaml.error.Mark;
import org.yaml.snakeyaml.error.YAMLException;
import org.yaml.snakeyaml.introspector.GenericProperty;
import org.yaml.snakeyaml.introspector.Property;
import org.yaml.snakeyaml.nodes.Node;
import org.yaml.snakeyaml.nodes.NodeId;
import org.yaml.snakeyaml.nodes.ScalarNode;
import org.yaml.snakeyaml.nodes.Tag;

import org.diorite.cfg.system.ConfigField;
import org.diorite.cfg.system.Template;
import org.diorite.cfg.system.TemplateCreator;
import org.diorite.cfg.system.elements.TemplateElement;
import org.diorite.cfg.system.elements.TemplateElements;
import org.diorite.utils.reflections.ReflectElement;

/**
 * Diorite extension of {@link Constructor} with more public methods.
 */
public class DioriteYamlConstructor extends Constructor
{
    {
        this.yamlClassConstructors.put(NodeId.scalar, new ConstructCustomElementObject());
        this.yamlClassConstructors.put(NodeId.mapping, new DioriteConstructMapping());
    }

    /**
     * Construct new instance with default settings.
     */
    public DioriteYamlConstructor()
    {
    }

    /**
     * Create Constructor for the specified class as the root.
     *
     * @param theRoot - the class (usually JavaBean) to be constructed
     */
    public DioriteYamlConstructor(final Class<?> theRoot)
    {
        super(theRoot);
    }

    /**
     * Create Constructor for the specified class as the root.
     *
     * @param theRoot - the class (usually JavaBean) to be constructed
     */
    public DioriteYamlConstructor(final TypeDescription theRoot)
    {
        super(theRoot);
    }

    /**
     * Create Constructor for the specified class as the root.
     *
     * @param theRoot - the class (usually JavaBean) to be constructed
     *
     * @throws ClassNotFoundException if there is no class with given name.
     */
    public DioriteYamlConstructor(final String theRoot) throws ClassNotFoundException
    {
        super(theRoot);
    }

    /**
     * Returns type definitions of this yaml constructor instance.
     *
     * @return type definitions of this yaml constructor instance.
     */
    public Map<Class<?>, TypeDescription> getTypeDefinitions()
    {
        return this.typeDefinitions;
    }

    /**
     * Returns yaml class constructor map of this yaml constructor instance.
     *
     * @return yaml class constructor map of this yaml constructor instance.
     */
    public Map<NodeId, Construct> getYamlClassConstructors()
    {
        return this.yamlClassConstructors;
    }

    /**
     * Returns yaml constructors map of this yaml constructor instance.
     *
     * @return yaml constructors map of this yaml constructor instance.
     */
    public Map<Tag, Construct> getYamlConstructors()
    {
        return this.yamlConstructors;
    }

    /**
     * Returns yaml multi constructors of this yaml constructor instance.
     *
     * @return yaml multi constructors of this yaml constructor instance.
     */
    public Map<String, Construct> getYamlMultiConstructors()
    {
        return this.yamlMultiConstructors;
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

    /**
     * Diorite yaml constructor to support template-based files.
     *
     * @see Template
     */
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


    /**
     * Diorite wrapper for yaml {@link org.yaml.snakeyaml.constructor.ConstructorException} as it have protected access.
     */
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
