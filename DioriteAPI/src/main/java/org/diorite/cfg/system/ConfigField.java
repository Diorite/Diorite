package org.diorite.cfg.system;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.function.Supplier;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.cfg.annotations.CfgName;
import org.diorite.cfg.annotations.CfgWeight;
import org.diorite.cfg.annotations.defaults.CfgBooleanArrayDefault;
import org.diorite.cfg.annotations.defaults.CfgBooleanDefault;
import org.diorite.cfg.annotations.defaults.CfgByteArrayDefault;
import org.diorite.cfg.annotations.defaults.CfgByteDefault;
import org.diorite.cfg.annotations.defaults.CfgCharArrayDefault;
import org.diorite.cfg.annotations.defaults.CfgCharDefault;
import org.diorite.cfg.annotations.defaults.CfgCustomDefault;
import org.diorite.cfg.annotations.defaults.CfgDelegateDefault;
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
import org.diorite.utils.collections.AlphanumComparator;
import org.diorite.utils.reflections.DioriteReflectionUtils;
import org.diorite.utils.reflections.MethodInvoker;

public class ConfigField implements Comparable<ConfigField>, CfgEntryData
{
    private final Field            field;
    private final int              index;
    private final String           name;
    private final String           header;
    private final String           footer;
    private final int              weight;
    private final Supplier<Object> def;
    private final Map<FieldOptions, Object> options = new HashMap<>(3);
    private MethodInvoker invoker;

    @SuppressWarnings("ObjectEquality")
    public ConfigField(final Field field, final int index)
    {
        this.field = field;
        this.index = index;

        final String[] comments = TemplateCreator.readComments(field);
        this.header = comments[0];
        this.footer = comments[1];
        {
            final CfgName nameInfo = field.getAnnotation(CfgName.class);
            this.name = (nameInfo != null) ? nameInfo.value() : field.getName();
        }
        {
            final CfgWeight weightInfo = field.getAnnotation(CfgWeight.class);
            this.weight = (weightInfo != null) ? weightInfo.value() : index;
        }

        for (final FieldOptions option : FieldOptions.values())
        {
            if (! option.contains(field))
            {
                continue;
            }
            this.options.put(option, option.get(this, field));
        }
        System.out.println(this.options);

        final Class<?> type = DioriteReflectionUtils.getPrimitive(field.getType());
        Supplier<Object> def = null;
        annotation:
        {
            if (type == boolean.class)
            {
                final CfgBooleanDefault annotation = field.getAnnotation(CfgBooleanDefault.class);
                if (annotation != null)
                {
                    def = annotation::value;
                }
            }
            else if (type == boolean[].class)
            {
                final CfgBooleanArrayDefault annotation = field.getAnnotation(CfgBooleanArrayDefault.class);
                if (annotation != null)
                {
                    def = annotation::value;
                }
            }
            else if (type == byte.class)
            {
                final CfgByteDefault annotation = field.getAnnotation(CfgByteDefault.class);
                if (annotation != null)
                {
                    def = annotation::value;
                }
            }
            else if (type == byte[].class)
            {
                final CfgByteArrayDefault annotation = field.getAnnotation(CfgByteArrayDefault.class);
                if (annotation != null)
                {
                    def = annotation::value;
                }
            }
            else if (type == char.class)
            {
                final CfgCharDefault annotation = field.getAnnotation(CfgCharDefault.class);
                if (annotation != null)
                {
                    def = annotation::value;
                }
            }
            else if (type == char[].class)
            {
                final CfgCharArrayDefault annotation = field.getAnnotation(CfgCharArrayDefault.class);
                if (annotation != null)
                {
                    def = annotation::value;
                }
            }
            else if (type == short.class)
            {
                final CfgShortDefault annotation = field.getAnnotation(CfgShortDefault.class);
                if (annotation != null)
                {
                    def = annotation::value;
                }
            }
            else if (type == short[].class)
            {
                final CfgShortArrayDefault annotation = field.getAnnotation(CfgShortArrayDefault.class);
                if (annotation != null)
                {
                    def = annotation::value;
                }
            }
            else if (type == int.class)
            {
                final CfgIntDefault annotation = field.getAnnotation(CfgIntDefault.class);
                if (annotation != null)
                {
                    def = annotation::value;
                }
            }
            else if (type == int[].class)
            {
                final CfgIntArrayDefault annotation = field.getAnnotation(CfgIntArrayDefault.class);
                if (annotation != null)
                {
                    def = annotation::value;
                }
            }
            else if (type == long.class)
            {
                final CfgLongDefault annotation = field.getAnnotation(CfgLongDefault.class);
                if (annotation != null)
                {
                    def = annotation::value;
                }
            }
            else if (type == long[].class)
            {
                final CfgLongArrayDefault annotation = field.getAnnotation(CfgLongArrayDefault.class);
                if (annotation != null)
                {
                    def = annotation::value;
                }
            }
            else if (type == float.class)
            {
                final CfgFloatDefault annotation = field.getAnnotation(CfgFloatDefault.class);
                if (annotation != null)
                {
                    def = annotation::value;
                }
            }
            else if (type == float[].class)
            {
                final CfgFloatArrayDefault annotation = field.getAnnotation(CfgFloatArrayDefault.class);
                if (annotation != null)
                {
                    def = annotation::value;
                }
            }
            else if (type == double.class)
            {
                final CfgDoubleDefault annotation = field.getAnnotation(CfgDoubleDefault.class);
                if (annotation != null)
                {
                    def = annotation::value;
                }
            }
            else if (type == double[].class)
            {
                final CfgDoubleArrayDefault annotation = field.getAnnotation(CfgDoubleArrayDefault.class);
                if (annotation != null)
                {
                    def = annotation::value;
                }
            }
            else if (type == String.class)
            {
                final CfgStringDefault annotation = field.getAnnotation(CfgStringDefault.class);
                if (annotation != null)
                {
                    def = annotation::value;
                }
            }
            else if (type == String[].class)
            {
                final CfgStringArrayDefault annotation = field.getAnnotation(CfgStringArrayDefault.class);
                if (annotation != null)
                {
                    def = annotation::value;
                }
            }
            else
            {
                if (type.isEnum())
                {
                    for (final Annotation a : field.getAnnotations())
                    {
                        if (a.getClass().isAnnotationPresent(CfgCustomDefault.class))
                        {
                            final Annotation annotation = field.getAnnotation(a.getClass());
                            this.invoker = DioriteReflectionUtils.getMethod(annotation.getClass(), "value");
                            def = () -> this.invoker.invoke(null);
                            break annotation;
                        }
                    }
                }
                final CfgDelegateDefault annotation = field.getAnnotation(CfgDelegateDefault.class);
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
                        default:
                            final String[] parts = StringUtils.split(path, '#');
                            if (parts.length == 1)
                            {
                                this.invoker = DioriteReflectionUtils.getMethod(field.getDeclaringClass(), parts[0]);
                                def = () -> this.invoker.invoke(null);
                            }
                            else if (parts.length == 2)
                            {
                                this.invoker = DioriteReflectionUtils.getMethod(parts[0], parts[1]);
                                def = () -> this.invoker.invoke(null);
                            }
                            break;
                    }
                }
            }
        }
        this.def = def;
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

    public Field getField()
    {
        return this.field;
    }

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

    public int getIndex()
    {
        return this.index;
    }

    public int getWeight()
    {
        return this.weight;
    }

    public Object getDefault()
    {
        if (this.def == null)
        {
            return null;
        }
        return this.def.get();
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
        return (this.weight == that.weight) && this.field.equals(that.field) && this.name.equals(that.name) && ! ((this.header != null) ? ! this.header.equals(that.header) : (that.header != null)) && ! ((this.footer != null) ? ! this.footer.equals(that.footer) : (that.footer != null));
    }

    @Override
    public int hashCode()
    {
        int result = this.field.hashCode();
        result = (31 * result) + this.name.hashCode();
        result = (31 * result) + ((this.header != null) ? this.header.hashCode() : 0);
        result = (31 * result) + ((this.footer != null) ? this.footer.hashCode() : 0);
        result = (31 * result) + this.weight;
        return result;
    }

    @Override
    public int compareTo(final ConfigField o)
    {
        final int weight = Integer.compare(this.weight, o.weight);
        if (weight != 0)
        {
            return weight;
        }
        return AlphanumComparator.compareStatic(this.name, o.name);
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("field", this.field).append("name", this.name).append("header", this.header).append("footer", this.footer).append("weight", this.weight).toString();
    }
}
