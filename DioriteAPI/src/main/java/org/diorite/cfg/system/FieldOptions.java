package org.diorite.cfg.system;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.util.Map;
import java.util.function.BiFunction;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.cfg.annotations.CfgCollectionStyle;
import org.diorite.cfg.annotations.CfgCollectionStyle.CollectionStyle;
import org.diorite.cfg.annotations.CfgCollectionType;
import org.diorite.cfg.annotations.CfgCollectionType.CollectionType;
import org.diorite.cfg.annotations.CfgCommentOptions;
import org.diorite.cfg.annotations.CfgStringArrayMultilineThreshold;
import org.diorite.cfg.annotations.CfgStringMultilineThreshold;
import org.diorite.cfg.annotations.CfgStringStyle;
import org.diorite.cfg.annotations.CfgStringStyle.StringStyle;
import org.diorite.utils.SimpleEnum;
import org.diorite.utils.collections.SimpleStringHashMap;

import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.hash.TIntObjectHashMap;

@SuppressWarnings("MagicNumber")
public class FieldOptions implements SimpleEnum<FieldOptions>
{
    public static final FieldOptions STRING_STYLE                     = new FieldOptions("STRING_STYLE", 0, CfgStringStyle.class, (f, a) -> (a != null) ? a.value() : StringStyle.DEFAULT);
    public static final FieldOptions STRING_MULTILINE_THRESHOLD       = new FieldOptions("STRING_MULTILINE_THRESHOLD", 1, CfgStringMultilineThreshold.class, (f, a) -> (a != null) ? a.value() : 2);
    public static final FieldOptions STRING_ARRAY_MULTILINE_THRESHOLD = new FieldOptions("STRING_ARRAY_MULTILINE_THRESHOLD", 2, CfgStringArrayMultilineThreshold.class, (f, a) -> (a != null) ? a.value() : 25);
    public static final FieldOptions COLLECTION_STYLE                 = new FieldOptions("COLLECTION_STYLE", 3, CfgCollectionStyle.class, (f, a) -> (a != null) ? a.value() : CollectionStyle.DEFAULT);
    public static final FieldOptions COLLECTION_TYPE                  = new FieldOptions("COLLECTION_TYPE", 4, CfgCollectionType.class, (f, a) -> (a != null) ? a.value() : CollectionType.OBJECTS);
    public static final FieldOptions OTHERS_COMMENT_EVERY_ELEMENT     = new FieldOptions("OTHERS_COMMENT_EVERY_ELEMENT", 5, CfgCommentOptions.class, (f, a) -> (a != null) && a.commentEveryElement());

    private static final Map<String, FieldOptions>   byName = new SimpleStringHashMap<>(6, SMALL_LOAD_FACTOR);
    private static final TIntObjectMap<FieldOptions> byID   = new TIntObjectHashMap<>(6, SMALL_LOAD_FACTOR);

    private final String                                      enumName;
    private final int                                         id;
    private final Class<? extends Annotation>                 clazz;
    private final BiFunction<ConfigField, Annotation, Object> func;

    <T extends Annotation> FieldOptions(final String enumName, final int id, final Class<T> annotationClass, final BiFunction<ConfigField, T, Object> func)
    {
        this.enumName = enumName;
        this.id = id;
        this.clazz = annotationClass;
        //noinspection unchecked
        this.func = (BiFunction<ConfigField, Annotation, Object>) func;
    }

    public <T extends Annotation> Object get(final ConfigField f, final T a)
    {
        return this.func.apply(f, a);
    }

    public <T extends Annotation> Object get(final ConfigField f, final AnnotatedElement element)
    {
        return this.func.apply(f, element.getAnnotation(this.clazz));
    }

    public boolean contains(final AnnotatedElement element)
    {
        return element.isAnnotationPresent(this.clazz);
    }

    @Override
    public boolean equals(final Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (! (o instanceof FieldOptions))
        {
            return false;
        }

        final FieldOptions that = (FieldOptions) o;

        return this.id == that.id;

    }

    @Override
    public int hashCode()
    {
        return this.id;
    }

    @Override
    public String name()
    {
        return this.enumName;
    }

    @Override
    public int getId()
    {
        return this.id;
    }

    @Override
    public FieldOptions byId(final int id)
    {
        return byID.get(id);
    }

    @Override
    public FieldOptions byName(final String name)
    {
        return byName.get(name);
    }

    public static void register(final FieldOptions element)
    {
        byID.put(element.getId(), element);
        byName.put(element.name(), element);
    }

    /**
     * @return all values in array.
     */
    public static FieldOptions[] values()
    {
        return byID.values(new FieldOptions[byID.size()]);
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("enumName", this.enumName).append("id", this.id).append("clazz", this.clazz).append("func", this.func).toString();
    }

    static
    {
        register(STRING_STYLE);
        register(STRING_MULTILINE_THRESHOLD);
        register(STRING_ARRAY_MULTILINE_THRESHOLD);
        register(COLLECTION_STYLE);
        register(COLLECTION_TYPE);
        register(OTHERS_COMMENT_EVERY_ELEMENT);
    }
}
