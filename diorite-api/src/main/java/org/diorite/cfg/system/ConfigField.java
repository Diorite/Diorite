/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2015 Diorite (by Bartłomiej Mazur (aka GotoFinal))
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

package org.diorite.cfg.system;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.regex.Pattern;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.cfg.annotations.CfgName;
import org.diorite.cfg.annotations.CfgPriority;
import org.diorite.cfg.annotations.defaults.CfgBooleanArrayDefault;
import org.diorite.cfg.annotations.defaults.CfgBooleanDefault;
import org.diorite.cfg.annotations.defaults.CfgByteArrayDefault;
import org.diorite.cfg.annotations.defaults.CfgByteDefault;
import org.diorite.cfg.annotations.defaults.CfgCharArrayDefault;
import org.diorite.cfg.annotations.defaults.CfgCharDefault;
import org.diorite.cfg.annotations.defaults.CfgCustomDefault;
import org.diorite.cfg.annotations.defaults.CfgDelegateDefault;
import org.diorite.cfg.annotations.defaults.CfgDelegateImport;
import org.diorite.cfg.annotations.defaults.CfgDelegateImportArray;
import org.diorite.cfg.annotations.defaults.CfgDelegateImports;
import org.diorite.cfg.annotations.defaults.CfgDoubleArrayDefault;
import org.diorite.cfg.annotations.defaults.CfgDoubleDefault;
import org.diorite.cfg.annotations.defaults.CfgFloatArrayDefault;
import org.diorite.cfg.annotations.defaults.CfgFloatDefault;
import org.diorite.cfg.annotations.defaults.CfgIntArrayDefault;
import org.diorite.cfg.annotations.defaults.CfgIntDefault;
import org.diorite.cfg.annotations.defaults.CfgLongArrayDefault;
import org.diorite.cfg.annotations.defaults.CfgLongDefault;
import org.diorite.cfg.annotations.defaults.CfgShortArrayDefault;
import org.diorite.cfg.annotations.defaults.CfgShortDefault;
import org.diorite.cfg.annotations.defaults.CfgStringArrayDefault;
import org.diorite.cfg.annotations.defaults.CfgStringDefault;
import org.diorite.cfg.system.elements.TemplateElement;
import org.diorite.cfg.system.elements.TemplateElements;
import org.diorite.utils.collections.comparators.AlphanumComparator;
import org.diorite.utils.function.eval.BooleanEvaluator;
import org.diorite.utils.function.eval.ByteEvaluator;
import org.diorite.utils.function.eval.CharEvaluator;
import org.diorite.utils.function.eval.DoubleEvaluator;
import org.diorite.utils.function.eval.FloatEvaluator;
import org.diorite.utils.function.eval.IntEvaluator;
import org.diorite.utils.function.eval.LongEvaluator;
import org.diorite.utils.function.eval.ObjectEvaluator;
import org.diorite.utils.function.eval.ShortEvaluator;
import org.diorite.utils.math.DioriteRandomUtils;
import org.diorite.utils.reflections.DioriteReflectionUtils;
import org.diorite.utils.reflections.MethodInvoker;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtNewMethod;
import javassist.NotFoundException;

/**
 * Main implementation of {@link CfgEntryData} contains all information
 * about config field.
 */
@SuppressWarnings({"unchecked", "rawtypes"})
public class ConfigField implements Comparable<ConfigField>, CfgEntryData
{
    public static final CtClass[] EMPTY_CLASSES = new CtClass[0];

    private static final Pattern VALID_JAVA_NAME = Pattern.compile("[^a-zA-Z0-9_]");

    private final Field            field;
    private final int              index;
    private final String           name;
    private final String           header;
    private final String           footer;
    private final int              priority;
    private final Supplier<Object> def;
    private final Map<FieldOptions, Object> options = new HashMap<>(3);
    private MethodInvoker invoker;

    /**
     * Construct new config field for given {@link Field}.
     *
     * @param field source field.
     * @param index index of field in class. Used to set priority of field.
     */
    public ConfigField(final Field field, final int index)
    {
        this.field = field;
        {
            getAllPossibleTypes(field).forEach(TemplateCreator::checkTemplate);
        }
        this.index = index;

        final String[] comments = TemplateCreator.readComments(field);
        this.header = comments[0];
        this.footer = comments[1];
        {
            final CfgName annotation = field.getAnnotation(CfgName.class);
            this.name = (annotation != null) ? annotation.value() : field.getName();
        }
        {
            final CfgPriority annotation = field.getAnnotation(CfgPriority.class);
            this.priority = (annotation != null) ? (annotation.value() * - 1) : index;
        }

        for (final FieldOptions option : FieldOptions.values())
        {
            if (! option.contains(field))
            {
                continue;
            }
            this.options.put(option, option.get(this, field));
        }

        final Class<?> type = DioriteReflectionUtils.getPrimitive(field.getType());
        Supplier<Object> def = null;
        annotation:
        {
            {
                final CfgBooleanDefault annotation = field.getAnnotation(CfgBooleanDefault.class);
                if (annotation != null)
                {
                    def = annotation::value;
                    break annotation;
                }
            }
            {
                final CfgBooleanArrayDefault annotation = field.getAnnotation(CfgBooleanArrayDefault.class);
                if (annotation != null)
                {
                    def = annotation::value;
                    break annotation;
                }
            }
            {
                final CfgByteDefault annotation = field.getAnnotation(CfgByteDefault.class);
                if (annotation != null)
                {
                    def = annotation::value;
                    break annotation;
                }
            }
            {
                final CfgShortDefault annotation = field.getAnnotation(CfgShortDefault.class);
                if (annotation != null)
                {
                    def = annotation::value;
                    break annotation;
                }
            }
            {
                final CfgIntDefault annotation = field.getAnnotation(CfgIntDefault.class);
                if (annotation != null)
                {
                    def = annotation::value;
                    break annotation;
                }
            }
            {
                final CfgLongDefault annotation = field.getAnnotation(CfgLongDefault.class);
                if (annotation != null)
                {
                    def = annotation::value;
                    break annotation;
                }
            }
            {
                final CfgFloatDefault annotation = field.getAnnotation(CfgFloatDefault.class);
                if (annotation != null)
                {
                    def = annotation::value;
                    break annotation;
                }
            }
            {
                final CfgDoubleDefault annotation = field.getAnnotation(CfgDoubleDefault.class);
                if (annotation != null)
                {
                    def = annotation::value;
                    break annotation;
                }
            }

            {
                final CfgByteArrayDefault annotation = field.getAnnotation(CfgByteArrayDefault.class);
                if (annotation != null)
                {
                    def = annotation::value;
                    break annotation;
                }
            }
            {
                final CfgCharDefault annotation = field.getAnnotation(CfgCharDefault.class);
                if (annotation != null)
                {
                    def = annotation::value;
                    break annotation;
                }
            }
            {
                final CfgCharArrayDefault annotation = field.getAnnotation(CfgCharArrayDefault.class);
                if (annotation != null)
                {
                    def = annotation::value;
                    break annotation;
                }
            }
            {
                final CfgShortArrayDefault annotation = field.getAnnotation(CfgShortArrayDefault.class);
                if (annotation != null)
                {
                    def = annotation::value;
                    break annotation;
                }
            }
            {
                final CfgIntArrayDefault annotation = field.getAnnotation(CfgIntArrayDefault.class);
                if (annotation != null)
                {
                    def = annotation::value;
                    break annotation;
                }
            }
            {
                final CfgLongArrayDefault annotation = field.getAnnotation(CfgLongArrayDefault.class);
                if (annotation != null)
                {
                    def = annotation::value;
                    break annotation;
                }
            }
            {
                final CfgFloatArrayDefault annotation = field.getAnnotation(CfgFloatArrayDefault.class);
                if (annotation != null)
                {
                    def = annotation::value;
                    break annotation;
                }
            }
            {
                final CfgDoubleArrayDefault annotation = field.getAnnotation(CfgDoubleArrayDefault.class);
                if (annotation != null)
                {
                    def = annotation::value;
                    break annotation;
                }
            }
            {
                final CfgStringDefault annotation = field.getAnnotation(CfgStringDefault.class);
                if (annotation != null)
                {
                    def = annotation::value;
                    break annotation;
                }
            }
            {
                final CfgStringArrayDefault annotation = field.getAnnotation(CfgStringArrayDefault.class);
                if (annotation != null)
                {
                    def = annotation::value;
                    break annotation;
                }
            }

            if (type.isEnum())
            {
                for (final Annotation a : field.getAnnotations())
                {
                    if (a.annotationType().isAnnotationPresent(CfgCustomDefault.class))
                    {
                        final Annotation annotation = field.getAnnotation(a.annotationType());
                        this.invoker = DioriteReflectionUtils.getMethod(annotation.getClass(), "value");
                        def = () -> this.invoker.invoke(annotation);
                        break annotation;
                    }
                }
            }
        }
        final CfgDelegateDefault annotation = field.getAnnotation(CfgDelegateDefault.class);
        final Collection<String> imports = new HashSet<>(5);
        final Package pack = field.getType().getPackage();
        if ((pack != null) && (pack.getName() != null) && ! pack.getName().equals("java.lang"))
        {
            imports.add(pack.getName());
        }
        {
            {
                final CfgDelegateImportArray a = field.getAnnotation(CfgDelegateImportArray.class);
                if (a != null)
                {
                    for (final CfgDelegateImport di : a.value())
                    {
                        imports.add(di.value());
                    }
                }
            }
            {
                final CfgDelegateImports a = field.getAnnotation(CfgDelegateImports.class);
                if (a != null)
                {
                    Collections.addAll(imports, a.value());
                }
            }
            {
                final CfgDelegateImport a = field.getAnnotation(CfgDelegateImport.class);
                if (a != null)
                {
                    imports.add(a.value());
                }
            }
        }
        if (annotation != null)
        {
            final String path = annotation.value();
            switch (path)
            {
                case "{emptyMap}":
                    def = () -> new HashMap<>(1);
                    break;
                case "{emptyList}":
                    def = () -> new ArrayList<>(1);
                    break;
                case "{emptySet}":
                    def = () -> new HashSet<>(1);
                    break;
                case "{<init>}":
                    def = () -> DioriteReflectionUtils.getConstructor(field.getType()).invoke();
                    break;
                default:
                    try
                    {
                        def = path.startsWith("adv|") ? ConfigField.parseMethodAdv(path.substring(4), field.getDeclaringClass(), field.getType(), imports.toArray(new String[imports.size()])) : parseMethod(path, field.getDeclaringClass(), field.getType(), imports.toArray(new String[imports.size()]));
                    } catch (CannotCompileException | NotFoundException e)
                    {
                        e.printStackTrace();
                    }
                    break;
            }
        }
        this.def = def;
    }

    static Supplier parseMethod(final String str, final Class<?> clazz, final Class<?> returnClass, final String... imports) throws CannotCompileException, NotFoundException
    {
        return parseMethodAdv("return " + str + ";", clazz, returnClass, imports);
    }

    static Supplier parseMethodAdv(final String str, final Class<?> clazz, final Class<?> returnClass, final String... imports) throws CannotCompileException, NotFoundException
    {
        final ClassPool pool = ClassPool.getDefault();

        pool.clearImportedPackages();
        for (final String impor : imports)
        {
            pool.importPackage(impor);
        }

        final CtClass init = pool.makeClass((clazz.getName().startsWith("java") ? "org.diorite.cfg.system" : clazz.getPackage().getName()) + "." + VALID_JAVA_NAME.matcher(clazz.getName() + DioriteRandomUtils.nextLong()).replaceAll("_"));

        final CtClass returnType;
        final CtClass interfaceType;
        final Function<Class<?>, Supplier<Object>> func;
        if (returnClass.isPrimitive())
        {
            if (returnClass.equals(boolean.class))
            {
                returnType = CtClass.booleanType;
                interfaceType = pool.get("org.diorite.utils.function.eval.BooleanEvaluator");
                func = c -> {
                    try
                    {
                        BooleanEvaluator e = (BooleanEvaluator) c.newInstance();
                        return e::eval;
                    } catch (Exception e)
                    {
                        throw new RuntimeException(e);
                    }
                };
            }
            else if (returnClass.equals(byte.class))
            {
                returnType = CtClass.byteType;
                interfaceType = pool.get("org.diorite.utils.function.eval.ByteEvaluator");
                func = c -> {
                    try
                    {
                        ByteEvaluator e = (ByteEvaluator) c.newInstance();
                        return e::eval;
                    } catch (Exception e)
                    {
                        throw new RuntimeException(e);
                    }
                };
            }
            else if (returnClass.equals(char.class))
            {
                returnType = CtClass.charType;
                interfaceType = pool.get("org.diorite.utils.function.eval.CharEvaluator");
                func = c -> {
                    try
                    {
                        CharEvaluator e = (CharEvaluator) c.newInstance();
                        return e::eval;
                    } catch (Exception e)
                    {
                        throw new RuntimeException(e);
                    }
                };
            }
            else if (returnClass.equals(short.class))
            {
                returnType = CtClass.shortType;
                interfaceType = pool.get("org.diorite.utils.function.eval.ShortEvaluator");
                func = c -> {
                    try
                    {
                        ShortEvaluator e = (ShortEvaluator) c.newInstance();
                        return e::eval;
                    } catch (Exception e)
                    {
                        throw new RuntimeException(e);
                    }
                };
            }
            else if (returnClass.equals(int.class))
            {
                returnType = CtClass.intType;
                interfaceType = pool.get("org.diorite.utils.function.eval.IntEvaluator");
                func = c -> {
                    try
                    {
                        IntEvaluator e = (IntEvaluator) c.newInstance();
                        return e::eval;
                    } catch (Exception e)
                    {
                        throw new RuntimeException(e);
                    }
                };
            }
            else if (returnClass.equals(long.class))
            {
                returnType = CtClass.longType;
                interfaceType = pool.get("org.diorite.utils.function.eval.LongEvaluator");
                func = c -> {
                    try
                    {
                        LongEvaluator e = (LongEvaluator) c.newInstance();
                        return e::eval;
                    } catch (Exception e)
                    {
                        throw new RuntimeException(e);
                    }
                };
            }
            else if (returnClass.equals(float.class))
            {
                returnType = CtClass.floatType;
                interfaceType = pool.get("org.diorite.utils.function.eval.FloatEvaluator");
                func = c -> {
                    try
                    {
                        FloatEvaluator e = (FloatEvaluator) c.newInstance();
                        return e::eval;
                    } catch (Exception e)
                    {
                        throw new RuntimeException(e);
                    }
                };
            }
            else if (returnClass.equals(double.class))
            {
                returnType = CtClass.doubleType;
                interfaceType = pool.get("org.diorite.utils.function.eval.DoubleEvaluator");
                func = c -> {
                    try
                    {
                        DoubleEvaluator e = (DoubleEvaluator) c.newInstance();
                        return e::eval;
                    } catch (Exception e)
                    {
                        throw new RuntimeException(e);
                    }
                };
            }
            else
            {
                throw new AssertionError("Unknown primitive type.");
            }
        }
        else
        {
            returnType = pool.get("java.lang.Object");
            interfaceType = pool.get("org.diorite.utils.function.eval.ObjectEvaluator");
            func = c -> {
                try
                {
                    ObjectEvaluator e = (ObjectEvaluator) c.newInstance();
                    return e::eval;
                } catch (Exception e)
                {
                    throw new RuntimeException(e);
                }
            };
        }
        init.setInterfaces(new CtClass[]{interfaceType});
        init.addMethod(CtNewMethod.make(returnType, "eval", EMPTY_CLASSES, new CtClass[]{pool.get("java.lang.Exception")}, "{\n" + str + "\n}", init));
        return func.apply(init.toClass(clazz.getClassLoader()));
    }

    public static void getAllPossibleTypes(final Set<Class<?>> classes, final Set<Type> checkedTypes, final Type rawType)
    {
        if (! checkedTypes.add(rawType))
        {
            return;
        }
        if (rawType instanceof Class)
        {
            classes.add((Class<?>) rawType);
        }
        if (rawType instanceof WildcardType)
        {
            final WildcardType type = (WildcardType) rawType;
            for (final Type t : type.getLowerBounds())
            {
                getAllPossibleTypes(classes, checkedTypes, t);
            }
            for (final Type t : type.getUpperBounds())
            {
                getAllPossibleTypes(classes, checkedTypes, t);
            }
        }
        if (rawType instanceof GenericArrayType)
        {
            getAllPossibleTypes(classes, checkedTypes, ((GenericArrayType) rawType).getGenericComponentType());
        }
        if (rawType instanceof TypeVariable)
        {
            final TypeVariable<?> type = (TypeVariable<?>) rawType;
            for (final Type t : type.getBounds())
            {
                getAllPossibleTypes(classes, checkedTypes, t);
            }
        }
        if (rawType instanceof ParameterizedType)
        {
            final ParameterizedType type = (ParameterizedType) rawType;
            getAllPossibleTypes(classes, checkedTypes, type.getRawType());
            getAllPossibleTypes(classes, checkedTypes, type.getOwnerType());
            for (final Type t : type.getActualTypeArguments())
            {
                getAllPossibleTypes(classes, checkedTypes, t);
            }
        }
    }

    public static Set<Class<?>> getAllPossibleTypes(final Field field)
    {
        final Set<Class<?>> classes = new HashSet<>(1);
        classes.add(field.getType());
        getAllPossibleTypes(classes, new HashSet<>(5), field.getGenericType());
        return classes;
    }


    @Override
    public <T> T getOption(final FieldOptions option)
    {
        //noinspection unchecked
        return (T) this.options.get(option);
    }

    @Override
    public <T> T getOption(final FieldOptions option, final T def)
    {
        //noinspection unchecked
        return (T) this.options.getOrDefault(option, def);
    }

    /**
     * @return java source field of this config field.
     */
    public Field getField()
    {
        return this.field;
    }

    /**
     * @return name of config field.
     */
    public String getName()
    {
        return this.name;
    }

    @Override
    public String getHeader()
    {
        return this.header;
    }

    @Override
    public String getFooter()
    {
        return this.footer;
    }

    /**
     * @return index of field from class file.
     */
    public int getIndex()
    {
        return this.index;
    }

    /**
     * Priority is used to choose order of fields.
     *
     * @return priority of config field.
     */
    public int getPriority()
    {
        return this.priority;
    }

    /**
     * @return true if field have default value.
     */
    public boolean hasDefaultValue()
    {
        return this.def != null;
    }

    /**
     * @return default value of field or null.
     */
    public Object getDefaultValue()
    {
        if (this.def == null)
        {
            return null;
        }
        final TemplateElement<?> templateElement = TemplateElements.getElement(this);
        return templateElement.convertDefault(this.def.get(), this.field.getType());
    }

    @Override
    public boolean equals(final Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (! (o instanceof ConfigField))
        {
            return false;
        }
        final ConfigField that = (ConfigField) o;
        return (this.priority == that.priority) && this.field.equals(that.field) && this.name.equals(that.name) && ! ((this.header != null) ? ! this.header.equals(that.header) : (that.header != null)) && ! ((this.footer != null) ? ! this.footer.equals(that.footer) : (that.footer != null));
    }

    @Override
    public int hashCode()
    {
        int result = this.field.hashCode();
        result = (31 * result) + this.name.hashCode();
        result = (31 * result) + ((this.header != null) ? this.header.hashCode() : 0);
        result = (31 * result) + ((this.footer != null) ? this.footer.hashCode() : 0);
        result = (31 * result) + this.priority;
        return result;
    }

    @Override
    public int compareTo(final ConfigField o)
    {
        final int weight = Integer.compare(this.priority, o.priority);
        if (weight != 0)
        {
            return weight;
        }
        return AlphanumComparator.compareStatic(this.name, o.name);
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("field", this.field).append("name", this.name).append("header", this.header).append("footer", this.footer).append("priority", this.priority).toString();
    }
}
