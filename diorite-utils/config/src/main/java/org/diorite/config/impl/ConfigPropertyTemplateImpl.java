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

import javax.annotation.Nullable;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.WildcardType;
import java.util.Collection;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.ToStringBuilder;

import org.diorite.commons.arrays.DioriteArrayUtils;
import org.diorite.commons.reflections.DioriteReflectionUtils;
import org.diorite.config.Config;
import org.diorite.config.ConfigPropertyTemplate;
import org.diorite.config.ConfigPropertyValue;
import org.diorite.config.ConfigTemplate;
import org.diorite.config.ValidatorFunction;
import org.diorite.config.annotations.AsList;
import org.diorite.config.annotations.BooleanFormat;
import org.diorite.config.annotations.CollectionType;
import org.diorite.config.annotations.Comment;
import org.diorite.config.annotations.CustomKey;
import org.diorite.config.annotations.Formatted;
import org.diorite.config.annotations.HexNumber;
import org.diorite.config.annotations.MapTypes;
import org.diorite.config.annotations.Mapped;
import org.diorite.config.annotations.PaddedNumber;
import org.diorite.config.annotations.PropertyType;
import org.diorite.config.annotations.Unmodifiable;
import org.diorite.config.serialization.DeserializationData;
import org.diorite.config.serialization.SerializationData;
import org.diorite.config.serialization.comments.DocumentComments;
import org.diorite.config.serialization.snakeyaml.YamlCollectionCreator;

@SuppressWarnings({"unchecked", "rawtypes"})
public class ConfigPropertyTemplateImpl<T> implements ConfigPropertyTemplate<T>
{
    private final ConfigTemplate<?> template;

    private final     Class<T>                     rawType;
    private final     Type                         genericType;
    private final     String                       originalName;
    private final     String                       name;
    private           Function<Config, T>          defaultValueSupplier;
    private @Nullable ValidatorFunction<Config, T> validator;
    private final     AnnotatedElement             annotatedElement;

    @Nullable
    private BiConsumer<SerializationData, ConfigPropertyValue>   serializeFunc;
    @Nullable
    private BiConsumer<DeserializationData, ConfigPropertyValue> deserializeFunc;

    @Nullable
    private BiFunction<Config, String, T> toKeyMapper;
    @Nullable
    private BiFunction<Config, T, String> toStringMapper;

    private boolean returnUnmodifiableCollections;

    public ConfigPropertyTemplateImpl(ConfigTemplate<?> template, Class<T> rawType, Type genericType, String name, Function<Config, T> defaultValueSupplier,
                                      AnnotatedElement annotatedElement)
    {
        this.template = template;
        this.rawType = rawType;
        this.genericType = genericType;
        this.defaultValueSupplier = defaultValueSupplier;
        this.annotatedElement = annotatedElement;
        this.originalName = name;
        Comment comment = this.annotatedElement.getAnnotation(Comment.class);
        if (this.annotatedElement.isAnnotationPresent(CustomKey.class))
        {
            this.name = this.annotatedElement.getAnnotation(CustomKey.class).value();
        }
        else if ((comment != null) && ! comment.name().isEmpty())
        {
            this.name = comment.name();
        }
        else
        {
            this.name = name;
        }
    }

    @Override
    public void appendValidator(ValidatorFunction<Config, T> validator)
    {
        if (this.validator == null)
        {
            this.validator = validator;
            return;
        }
        ValidatorFunction<Config, T> old = this.validator;
        this.validator = (t, c) -> validator.validate(old.validate(t, c), c);
    }

    @Override
    public void prependValidator(ValidatorFunction<Config, T> validator)
    {
        if (this.validator == null)
        {
            this.validator = validator;
            return;
        }
        ValidatorFunction<Config, T> old = this.validator;
        this.validator = (t, c) -> old.validate(validator.validate(t, c), c);
    }

    @Override
    public ValidatorFunction<Config, T> getValidator()
    {
        return (this.validator == null) ? ValidatorFunction.nothing() : this.validator;
    }

    @Override
    public String getOriginalName()
    {
        return this.originalName;
    }

    public void init()
    {
        Comment comment = this.annotatedElement.getAnnotation(Comment.class);

        this.initSerializeFunc(this.name);

        if (comment != null)
        {
            DocumentComments comments = this.template.getComments();
            comments.setComment(this.name, comment.value());
        }

        this.returnUnmodifiableCollections = this.annotatedElement.isAnnotationPresent(Unmodifiable.class);
    }

    public void setToKeyMapper(@Nullable BiFunction<Config, String, T> toKeyMapper)
    {
        this.toKeyMapper = toKeyMapper;
    }

    public void setToStringMapper(@Nullable BiFunction<Config, T, String> toStringMapper)
    {
        this.toStringMapper = toStringMapper;
    }

    private void initSerializeFunc(String key)
    {
        String format = null;
        if (this.annotatedElement.isAnnotationPresent(Formatted.class))
        {
            format = this.annotatedElement.getAnnotation(Formatted.class).value();
        }

        if ((this.rawType == boolean.class) || (this.rawType == Boolean.class))
        {
            this.initForBoolean(key, format);
            return;
        }

        if (Number.class.isAssignableFrom(DioriteReflectionUtils.getWrapperClass(this.rawType)))
        {
            this.initForNumber(key, format);
            return;
        }

        if (Collection.class.isAssignableFrom(this.rawType))
        {
            this.initForCollection(key);
            return;
        }

        if (! Config.class.isAssignableFrom(this.rawType) && Map.class.isAssignableFrom(this.rawType))
        {
            this.initForMap(key);
            return;
        }

        Class type;
        if (this.annotatedElement.isAnnotationPresent(PropertyType.class))
        {
            type = this.annotatedElement.getAnnotation(PropertyType.class).annotationType();
        }
        else
        {
            type = this.rawType;
        }
        this.serializeFunc = (data, val) -> data.add(key, val.getPropertyValue(), type);
        this.deserializeFunc = (data, val) -> val.setPropertyValue(data.get(key, type, val.getDefault()));
    }

    private void initForMap(String key)
    {
        AsList asList = this.annotatedElement.getAnnotation(AsList.class);
        Class<?>[] mapType = this.getMapType(asList);
        Class keyType = mapType[0];
        Class valueType = mapType[1];

        if (asList == null)
        {
            if (this.toStringMapper == null)
            {
                if ((keyType == null) || (this.toKeyMapper != null))
                {
                    if (this.toKeyMapper == null)
                    {
                        throw new IllegalStateException("Missing toKeyMapper/toStringMapper/keyType for: '" + this.name + "' (" + this.genericType + ") in: " +
                                                        this.template.getConfigType());
                    }
                    this.serializeFunc = (data, val) -> data.addMap(key, ((Map) val.getPropertyValue()), valueType);
                    this.deserializeFunc = (data, val) ->
                    {
                        Object collection = YamlCollectionCreator.createCollection(this.rawType, 10);
                        data.getMap(key, s -> this.toKeyMapper.apply(val.getDeclaringConfig(), s), valueType);
                        val.setPropertyValue(collection);
                    };
                }
                else
                {
                    this.serializeFunc = (data, val) -> data.addMap(key, ((Map) val.getPropertyValue()), keyType, valueType);
                    this.deserializeFunc = (data, val) ->
                    {
                        Object collection = YamlCollectionCreator.createCollection(this.rawType, 10);
                        data.getMap(key, keyType, valueType);
                        val.setPropertyValue(collection);
                    };
                }
            }
            else
            {
                if (this.toKeyMapper == null)
                {
                    throw new IllegalStateException("Missing toKeyMapper for: '" + this.name + "' (" + this.genericType + ") in: " +
                                                    this.template.getConfigType());
                }
                this.serializeFunc = (data, val) -> data.addMap(key, ((Map) val.getPropertyValue()), valueType,
                                                                s -> String.valueOf(this.toStringMapper.apply(val.getDeclaringConfig(), (T) s)));
                this.deserializeFunc = (data, val) ->
                {
                    Object collection = YamlCollectionCreator.createCollection(this.rawType, 10);
                    data.getMap(key, s -> this.toKeyMapper.apply(val.getDeclaringConfig(), s), valueType);
                    val.setPropertyValue(collection);
                };
            }
        }
        else
        {
            String keyProperty = asList.keyProperty();
            if (keyProperty.isEmpty() || (this.toStringMapper != null))
            {
                if (this.toStringMapper == null)
                {
                    throw new IllegalStateException("Missing toStringMapper for: '" + this.name + "' (" + this.genericType + ") in: " +
                                                    this.template.getConfigType());
                }
                this.serializeFunc = (data, val) -> data.addMapAsList(key, ((Map) val.getPropertyValue()), valueType);
                this.deserializeFunc = (data, val) ->
                {
                    Object collection = YamlCollectionCreator.createCollection(this.rawType, 10);
                    data.getAsMap(key, valueType, s -> String.valueOf(this.toStringMapper.apply(val.getDeclaringConfig(), (T) s)), (Map) collection);
                    val.setPropertyValue(collection);
                };
            }
            else
            {
                this.serializeFunc = (data, val) -> data.addMapAsListWithKeys(key, ((Map) val.getPropertyValue()), valueType, keyProperty);
                this.deserializeFunc = (data, val) ->
                {
                    Object collection = YamlCollectionCreator.createCollection(this.rawType, 10);
                    data.getAsMapWithKeys(key, keyType, valueType, keyProperty, (Map) collection);
                    val.setPropertyValue(collection);
                };
            }
        }
    }

    private void initForCollection(String key)
    {
        Mapped mapped = this.annotatedElement.getAnnotation(Mapped.class);
        Class collectionType = this.getCollectionType(mapped);
        if (mapped == null)
        {

            this.serializeFunc = (data, val) -> data.addCollection(key, (Collection) val.getPropertyValue(), collectionType);
            this.deserializeFunc = (data, val) ->
            {
                Object collection = YamlCollectionCreator.createCollection(this.rawType, 10);
                data.getAsCollection(key, collectionType, (Collection) collection);
                val.setPropertyValue(collection);
            };
        }
        else
        {
            if (this.toStringMapper == null)
            {
                throw new IllegalStateException("Missing toStringMapper for: '" + this.name + "' (" + this.genericType + ") in: " +
                                                this.template.getConfigType());
            }
            this.serializeFunc = (data, val) -> data.addMappedList(key, collectionType, (Collection) val.getPropertyValue(),
                                                                   o -> String.valueOf(this.toStringMapper.apply(val.getDeclaringConfig(), (T) o)));
            this.deserializeFunc = (data, val) ->
            {
                Object collection = YamlCollectionCreator.createCollection(this.rawType, 10);
                data.getAsCollection(key, collectionType, (Collection) collection);
                val.setPropertyValue(collection);
            };
        }
    }

    private Class<?>[] getMapType(@Nullable AsList asList)
    {
        Class<?>[] result = new Class[2];
        if (asList != null)
        {
            result[0] = (asList.keyType() == Object.class) ? null : asList.keyType();
            result[1] = (asList.valueType() == Object.class) ? null : asList.valueType();
        }
        MapTypes mapTypes = this.annotatedElement.getAnnotation(MapTypes.class);
        if (mapTypes != null)
        {
            result[0] = mapTypes.keyType();
            result[1] = mapTypes.valueType();
        }
        if (this.genericType instanceof ParameterizedType)
        {
            if (result[0] == null)
            {
                Type keyType = ((ParameterizedType) this.genericType).getActualTypeArguments()[0];
                if (keyType instanceof WildcardType)
                {
                    Type[] upperBounds = ((WildcardType) keyType).getUpperBounds();
                    keyType = (upperBounds.length == 0) ? null : upperBounds[0];
                }
                result[0] = (keyType instanceof Class) ? (Class<?>) keyType : null;
            }
            if (result[1] == null)
            {
                Type valueType = ((ParameterizedType) this.genericType).getActualTypeArguments()[1];
                if (valueType instanceof WildcardType)
                {
                    Type[] upperBounds = ((WildcardType) valueType).getUpperBounds();
                    valueType = (upperBounds.length == 0) ? null : upperBounds[0];
                }
                result[1] = (valueType instanceof Class) ? (Class<?>) valueType : null;
            }
        }
        if ((result[1] == null)) // key can be null
        {
            throw new IllegalStateException("Can't read generic type of map '" + this.name + "' (" + this.genericType + ") in: " +
                                            this.template.getConfigType());
        }

        return result;
    }

    private Class<?> getCollectionType(@Nullable Mapped mapped)
    {
        Class<?> collectionType = null;
        if (this.annotatedElement.isAnnotationPresent(CollectionType.class))
        {
            collectionType = this.annotatedElement.getAnnotation(CollectionType.class).value();
        }
        else if ((mapped != null) && (mapped.type() != Object.class))
        {
            collectionType = mapped.type();
        }
        else if (this.genericType instanceof ParameterizedType)
        {
            Type type = ((ParameterizedType) this.genericType).getActualTypeArguments()[0];
            if (type instanceof WildcardType)
            {
                Type[] upperBounds = ((WildcardType) type).getUpperBounds();
                type = (upperBounds.length == 0) ? null : upperBounds[0];
            }
            collectionType = (type instanceof Class) ? (Class<?>) type : null;
        }
        if (collectionType == null)
        {
            throw new IllegalStateException("Can't read generic type of collection '" + this.name + "' (" + this.genericType + ") in: " +
                                            this.template.getConfigType());
        }
        return collectionType;
    }

    private void initForNumber(String key, @Nullable String format)
    {
        Class<?> primitive = DioriteReflectionUtils.getPrimitive(this.rawType);
        boolean hex = this.annotatedElement.isAnnotationPresent(HexNumber.class);
        int padding;
        if (this.annotatedElement.isAnnotationPresent(PaddedNumber.class))
        {
            padding = this.annotatedElement.getAnnotation(PaddedNumber.class).value();
        }
        else
        {
            padding = 0;
        }
        if (hex)
        {
            this.serializeFunc = (data, val) -> data.addHexNumber(key, (Number) val.getPropertyValue(), padding);
            this.deserializeFunc = (data, val) -> val.setPropertyValue(data.getAsHexNumber(key, (Class) this.rawType, (Number) val.getDefault()));
        }
        else if ((padding == 0) && (format != null))
        {
            this.serializeFunc = (data, val) -> data.addFormatted(key, format, val.getPropertyValue());
            this.deserializeFunc = (data, val) -> val.setPropertyValue(data.get(key, (Class) this.rawType, val.getDefault()));
        }
        else
        {
            this.serializeFunc = (data, val) -> data.addNumber(key, (Number) val.getPropertyValue(), padding);
            this.deserializeFunc = (data, val) -> val.setPropertyValue(data.get(key, (Class) this.rawType, val.getDefault()));
        }
    }

    private void initForBoolean(String key, @Nullable String format)
    {
        String trueValue;
        String falseValue;
        String[] trueValues;
        String[] falseValues;

        if (this.annotatedElement.isAnnotationPresent(BooleanFormat.class))
        {
            BooleanFormat annotation = this.annotatedElement.getAnnotation(BooleanFormat.class);
            trueValues = annotation.trueValues();
            falseValues = annotation.falseValues();
            trueValue = (trueValues.length > 0) ? trueValues[0] : "true";
            falseValue = (falseValues.length > 0) ? falseValues[0] : "false";
        }
        else if (format != null)
        {
            this.serializeFunc = (data, val) -> data.addFormatted(key, format, val.getPropertyValue());
            this.deserializeFunc = (data, val) -> val.setPropertyValue(data.getAsBoolean(key, (boolean) val.getDefault()));
            return;
        }
        else
        {
            trueValue = "true";
            falseValue = "false";
            trueValues = DioriteArrayUtils.EMPTY_STRINGS;
            falseValues = DioriteArrayUtils.EMPTY_STRINGS;
        }

        this.serializeFunc = (data, val) -> data.addBoolean(key, (Boolean) val.getPropertyValue(), trueValue, falseValue);
        this.deserializeFunc = (data, val) ->
        {
            data.addTrueValues(trueValues);
            data.addFalseValues(falseValues);
            val.setPropertyValue(data.getAsBoolean(key, (boolean) val.getDefault()));
        };
    }

    @Override
    public Class<T> getRawType()
    {
        return this.rawType;
    }

    @Override
    public Type getGenericType()
    {
        return this.genericType;
    }

    @Override
    public String getName()
    {
        return this.name;
    }

    @SuppressWarnings("unchecked")
    @Override
    public T getDefault(Config config)
    {
        T def = this.defaultValueSupplier.apply(config);
        if ((def == null) && this.rawType.isPrimitive())
        {
            return (T) this.getPrimitiveDefault();
        }
        return def;
    }

    @Override
    public void set(ConfigPropertyValue<T> propertyValue, @Nullable T value)
    {
        propertyValue.setRawValue(value);
    }

    @Nullable
    @Override
    public T get(ConfigPropertyValue<T> propertyValue)
    {
        T rawValue = propertyValue.getRawValue();
        if (rawValue == null)
        {
            return null;
        }
        if (this.returnUnmodifiableCollections)
        {
            return YamlCollectionCreator.makeUnmodifiable(rawValue);
        }
        return rawValue;
    }

    @Override
    public void serialize(SerializationData data, ConfigPropertyValue<T> value)
    {
        Validate.notNull(this.serializeFunc);
        this.serializeFunc.accept(data, value);
    }

    @Override
    public void deserialize(DeserializationData data, ConfigPropertyValue<T> value)
    {
        Validate.notNull(this.deserializeFunc);
        this.deserializeFunc.accept(data, value);
    }

    private Object getPrimitiveDefault()
    {
        Class<T> rawType = this.rawType;
        if (rawType == boolean.class)
        {
            return false;
        }
        if (rawType == byte.class)
        {
            return (byte) 0;
        }
        if (rawType == short.class)
        {
            return (short) 0;
        }
        if (rawType == char.class)
        {
            return '\0';
        }
        if (rawType == int.class)
        {
            return 0;
        }
        if (rawType == long.class)
        {
            return 0L;
        }
        if (rawType == float.class)
        {
            return 0.0F;
        }
        if (rawType == double.class)
        {
            return 0.0;
        }
        throw new InternalError("Unknown primitive type:" + rawType);
    }

    public void setDefaultValueSupplier(Function<Config, T> defaultValueSupplier)
    {
        this.defaultValueSupplier = defaultValueSupplier;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this).appendSuper(super.toString()).append("rawType", this.rawType).append("genericType", this.genericType)
                                        .append("name", this.name).toString();
    }
}
