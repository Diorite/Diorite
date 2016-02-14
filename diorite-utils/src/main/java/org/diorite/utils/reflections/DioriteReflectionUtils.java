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

package org.diorite.utils.reflections;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.diorite.utils.SimpleEnum;

@SuppressWarnings({"unchecked", "ObjectEquality"})
public final class DioriteReflectionUtils
{
    private DioriteReflectionUtils()
    {
    }

    /**
     * Method will try find simple method setter and getter for selected field,
     * or directly use field if method can't be found.
     *
     * @param fieldName name of field to find element.
     * @param clazz     clazz with this field.
     * @param <T>       type of field.
     *
     * @return Element for field value.
     */
    public static <T> ReflectElement<T> getReflectElement(final String fieldName, final Class<?> clazz)
    {
        final ReflectGetter<T> getter = getReflectGetter(fieldName, clazz);
        if (getter instanceof ReflectField)
        {
            final ReflectField<T> reflectField = (ReflectField<T>) getter;
            return new ReflectElement<>(reflectField, reflectField);
        }
        else
        {
            return new ReflectElement<>(getter, getReflectSetter(fieldName, clazz));
        }
    }

    /**
     * Method will try find simple method setter and getter for selected field,
     * or directly use field if method can't be found.
     *
     * @param field field to find element.
     * @param <T>   type of field.
     *
     * @return Element for field value.
     */
    public static <T> ReflectElement<T> getReflectElement(final Field field)
    {
        return getReflectElement(field, field.getDeclaringClass());
    }

    /**
     * Method will try find simple method setter and getter for selected field,
     * or directly use field if method can't be found.
     *
     * @param field field to find element.
     * @param clazz clazz with this field.
     * @param <T>   type of field.
     *
     * @return Element for field value.
     */
    public static <T> ReflectElement<T> getReflectElement(final Field field, final Class<?> clazz)
    {
        final ReflectGetter<T> getter = getReflectGetter(field, clazz);
        if (getter instanceof ReflectField)
        {
            final ReflectField<T> reflectField = (ReflectField<T>) getter;
            return new ReflectElement<>(reflectField, reflectField);
        }
        else
        {
            return new ReflectElement<>(getter, getReflectSetter(field, clazz));
        }
    }

    /**
     * Method will try find simple method setter for selected field,
     * or directly use field if method can't be found.
     *
     * @param fieldName name of field to find setter.
     * @param clazz     clazz with this field.
     * @param <T>       type of field.
     *
     * @return Setter for field value.
     */
    public static <T> ReflectSetter<T> getReflectSetter(String fieldName, final Class<?> clazz)
    {
        final String fieldNameCpy = fieldName;
        fieldName = Character.toUpperCase(fieldName.charAt(0)) + fieldName.substring(1);
        Method m = null;
        try
        {
            m = clazz.getMethod("get" + fieldName);
        } catch (final NoSuchMethodException ignored1)
        {
            try
            {
                m = clazz.getMethod("is" + fieldName);
            } catch (final NoSuchMethodException ignored2)
            {
                for (final Method cm : clazz.getMethods())
                {
                    if ((cm.getName().equalsIgnoreCase("get" + fieldName) || cm.getName().equalsIgnoreCase("is" + fieldName)) && (cm.getReturnType() != Void.class) && (cm.getReturnType() != void.class) && (cm.getParameterCount() == 0))
                    {
                        m = cm;
                        break;
                    }
                }
            }
        }

        if (m != null)
        {
            return new ReflectMethodSetter<>(m);
        }
        else
        {
            return new ReflectField<>(getField(clazz, fieldNameCpy));
        }
    }

    /**
     * Method will try find simple method setter for selected field,
     * or directly use field if method can't be found.
     *
     * @param field field to find setter.
     * @param <T>   type of field.
     *
     * @return Setter for field value.
     */
    public static <T> ReflectSetter<T> getReflectSetter(final Field field)
    {
        return getReflectSetter(field, field.getDeclaringClass());
    }

    /**
     * Method will try find simple method setter for selected field,
     * or directly use field if method can't be found.
     *
     * @param field field to find setter.
     * @param clazz clazz with this field.
     * @param <T>   type of field.
     *
     * @return Setter for field value.
     */
    public static <T> ReflectSetter<T> getReflectSetter(final Field field, final Class<?> clazz)
    {
        String fieldName = field.getName();
        fieldName = Character.toUpperCase(fieldName.charAt(0)) + fieldName.substring(1);
        Method m = null;
        try
        {
            m = clazz.getMethod("set" + fieldName);
        } catch (final NoSuchMethodException ignored1)
        {
            for (final Method cm : clazz.getMethods())
            {
                if (cm.getName().equalsIgnoreCase("set" + fieldName) && (cm.getParameterCount() == 1))
                {
                    m = cm;
                    break;
                }
            }
        }

        if (m != null)
        {
            return new ReflectMethodSetter<>(m);
        }
        else
        {
            return new ReflectField<>(field);
        }
    }

    /**
     * Method will try find simple method getter for selected field,
     * or directly use field if method can't be found.
     *
     * @param fieldName name of field to find getter.
     * @param clazz     clazz with this field.
     * @param <T>       type of field.
     *
     * @return Getter for field value.
     */
    public static <T> ReflectGetter<T> getReflectGetter(String fieldName, final Class<?> clazz)
    {
        final String fieldNameCpy = fieldName;
        fieldName = Character.toUpperCase(fieldName.charAt(0)) + fieldName.substring(1);
        Method m = null;
        try
        {
            m = clazz.getMethod("get" + fieldName);
        } catch (final NoSuchMethodException ignored1)
        {
            try
            {
                m = clazz.getMethod("is" + fieldName);
            } catch (final NoSuchMethodException ignored2)
            {
                for (final Method cm : clazz.getMethods())
                {
                    if ((cm.getName().equalsIgnoreCase("get" + fieldName) || cm.getName().equalsIgnoreCase("is" + fieldName)) && (cm.getReturnType() != Void.class) && (cm.getReturnType() != void.class) && (cm.getParameterCount() == 0))
                    {
                        m = cm;
                        break;
                    }
                }
            }
        }

        if (m != null)
        {
            return new ReflectMethodGetter<>(m);
        }
        else
        {
            return new ReflectField<>(getField(clazz, fieldNameCpy));
        }
    }

    /**
     * Method will try find simple method getter for selected field,
     * or directly use field if method can't be found.
     *
     * @param field field to find getter.
     * @param <T>   type of field.
     *
     * @return Getter for field value.
     */
    public static <T> ReflectGetter<T> getReflectGetter(final Field field)
    {
        return getReflectGetter(field, field.getDeclaringClass());
    }

    /**
     * Method will try find simple method getter for selected field,
     * or directly use field if method can't be found.
     *
     * @param field field to find getter.
     * @param clazz clazz with this field.
     * @param <T>   type of field.
     *
     * @return Getter for field value.
     */
    public static <T> ReflectGetter<T> getReflectGetter(final Field field, final Class<?> clazz)
    {
        String fieldName = field.getName();
        fieldName = Character.toUpperCase(fieldName.charAt(0)) + fieldName.substring(1);
        Method m = null;
        try
        {
            m = clazz.getMethod("get" + fieldName);
        } catch (final NoSuchMethodException ignored1)
        {
            try
            {
                m = clazz.getMethod("is" + fieldName);
            } catch (final NoSuchMethodException ignored2)
            {
                for (final Method cm : clazz.getMethods())
                {
                    if ((cm.getName().equalsIgnoreCase("get" + fieldName) || cm.getName().equalsIgnoreCase("is" + fieldName)) && (cm.getReturnType() != Void.class) && (cm.getReturnType() != void.class) && (cm.getParameterCount() == 0))
                    {
                        m = cm;
                        break;
                    }
                }
            }
        }

        if (m != null)
        {
            return new ReflectMethodGetter<>(m);
        }
        else
        {
            return new ReflectField<>(field);
        }
    }

    /**
     * Safe method to get one of enum values by name or ordinal,
     * use -1 as id, to use only name,
     * and null or empty string for name, to use only ordinal id.
     *
     * @param name      name of enum field (ignore-case)
     * @param id        ordinal id.
     * @param enumClass class of enum.
     * @param <T>       type of enum.
     *
     * @return enum element or null.
     */
    public static <T extends SimpleEnum<T>> T getSimpleEnumValueSafe(final String name, final int id, final Class<? extends SimpleEnum<T>> enumClass)
    {
        return getSimpleEnumValueSafe(name, id, enumClass, null);
    }

    /**
     * Safe method to get one of enum values by name or ordinal,
     * use -1 as id, to use only name,
     * and null or empty string for name, to use only ordinal id.
     *
     * @param name name of enum field (ignore-case)
     * @param id   ordinal id.
     * @param def  default value, can't be null.
     * @param <T>  type of enum.
     *
     * @return enum element or def.
     */
    public static <T extends SimpleEnum<T>> T getSimpleEnumValueSafe(final String name, final int id, final T def)
    {
        return getSimpleEnumValueSafe(name, id, def.getClass(), def);
    }

    private static final Map<Class<?>, Method> simpleEnumMethods = new HashMap<>(40);

    private static <T extends SimpleEnum<T>> T getSimpleEnumValueSafe(final String name, final int id, Class<?> enumClass, final Object def)
    {
        do
        {
            try
            {
                Method m = simpleEnumMethods.get(enumClass);
                if (m == null)
                {
                    m = enumClass.getMethod("values");
                    simpleEnumMethods.put(enumClass, m);
                }
                final SimpleEnum<?>[] objects = (SimpleEnum<?>[]) m.invoke(null);
                for (final SimpleEnum<?> object : objects)
                {
                    if (object.name().equalsIgnoreCase(name) || (object.ordinal() == id))
                    {
                        return (T) object;
                    }
                }
            } catch (final Exception ignored)
            {
            }
            enumClass = enumClass.getSuperclass();
        } while ((enumClass != null) && ! (enumClass.equals(Object.class)));
        return (T) def;
    }

    /**
     * Safe method to get one of enum values by name or ordinal,
     * use -1 as id, to use only name,
     * and null or empty string for name, to use only ordinal id.
     *
     * @param name      name of enum field (ignore-case)
     * @param id        ordinal id.
     * @param enumClass class of enum.
     * @param <T>       type of enum.
     *
     * @return enum element or null.
     */
    public static <T extends Enum<T>> T getEnumValueSafe(final String name, final int id, final Class<T> enumClass)
    {
        return getEnumValueSafe(name, id, enumClass, null);
    }

    /**
     * Safe method to get one of enum values by name or ordinal,
     * use -1 as id, to use only name,
     * and null or empty string for name, to use only ordinal id.
     *
     * @param name name of enum field (ignore-case)
     * @param id   ordinal id.
     * @param def  default value, can't be null.
     * @param <T>  type of enum.
     *
     * @return enum element or def.
     */
    public static <T extends Enum<T>> T getEnumValueSafe(final String name, final int id, final T def)
    {
        return getEnumValueSafe(name, id, def.getDeclaringClass(), def);
    }

    private static <T extends Enum<T>> T getEnumValueSafe(final String name, final int id, final Class<T> enumClass, final T def)
    {
        final T[] elements = enumClass.getEnumConstants();
        for (final T element : elements)
        {
            if (element.name().equalsIgnoreCase(name) || (element.ordinal() == id))
            {
                return element;
            }
        }
        return def;
    }

    /**
     * Retrieve a field accessor for a specific field type and name.
     *
     * @param target    the target type.
     * @param name      the name of the field, or NULL to ignore.
     * @param fieldType a compatible field type.
     * @param <T>       type of field.
     *
     * @return The field accessor.
     */
    public static <T> FieldAccessor<T> getField(final Class<?> target, final String name, final Class<T> fieldType)
    {
        return getField(target, name, fieldType, 0, true);
    }

    /**
     * Retrieve a field accessor for a specific field type and name.
     *
     * @param className lookup name of the class, see {@link #getCanonicalClass(String)}.
     * @param name      the name of the field, or NULL to ignore.
     * @param fieldType a compatible field type.
     * @param <T>       type of field.
     *
     * @return The field accessor.
     */
    public static <T> FieldAccessor<T> getField(final String className, final String name, final Class<T> fieldType)
    {
        return getField(getCanonicalClass(className), name, fieldType, 0, true);
    }

    /**
     * Retrieve a field accessor for a specific field type and name.
     *
     * @param target    the target type.
     * @param fieldType a compatible field type.
     * @param index     the number of compatible fields to skip.
     * @param <T>       type of field.
     *
     * @return The field accessor.
     */
    public static <T> FieldAccessor<T> getField(final Class<?> target, final Class<T> fieldType, final int index)
    {
        return getField(target, null, fieldType, index, true);
    }

    /**
     * Retrieve a field accessor for a specific field type and name.
     *
     * @param target         the target type.
     * @param name           the name of the field, or NULL to ignore.
     * @param fieldType      a compatible field type.
     * @param throwException if method should throw exception when there is no given field.
     * @param <T>            type of field.
     *
     * @return The field accessor.
     */
    public static <T> FieldAccessor<T> getField(final Class<?> target, final String name, final Class<T> fieldType, final boolean throwException)
    {
        return getField(target, name, fieldType, 0, throwException);
    }

    /**
     * Retrieve a field accessor for a specific field type and name.
     *
     * @param className      lookup name of the class, see {@link #getCanonicalClass(String)}.
     * @param name           the name of the field, or NULL to ignore.
     * @param fieldType      a compatible field type.
     * @param throwException if method should throw exception when there is no given field.
     * @param <T>            type of field.
     *
     * @return The field accessor.
     */
    public static <T> FieldAccessor<T> getField(final String className, final String name, final Class<T> fieldType, final boolean throwException)
    {
        return getField(getCanonicalClass(className), name, fieldType, 0, throwException);
    }

    /**
     * Retrieve a field accessor for a specific field type and name.
     *
     * @param target         the target type.
     * @param fieldType      a compatible field type.
     * @param index          the number of compatible fields to skip.
     * @param throwException if method should throw exception when there is no given field.
     * @param <T>            type of field.
     *
     * @return The field accessor.
     */
    public static <T> FieldAccessor<T> getField(final Class<?> target, final Class<T> fieldType, final int index, final boolean throwException)
    {
        return getField(target, null, fieldType, index, throwException);
    }

    /**
     * Retrieve a field accessor for a specific field type and name.
     *
     * @param className lookup name of the class, see {@link #getCanonicalClass(String)}.
     * @param fieldType a compatible field type.
     * @param index     the number of compatible fields to skip.
     * @param <T>       type of field.
     *
     * @return The field accessor.
     */
    public static <T> FieldAccessor<T> getField(final String className, final Class<T> fieldType, final int index)
    {
        return getField(getCanonicalClass(className), fieldType, index);
    }

    /**
     * Set given accessible object as accessible if needed.
     *
     * @param o   accessible to get access.
     * @param <T> type of accessible object, used to keep type of returned value.
     *
     * @return this same object as given.
     */
    public static <T extends AccessibleObject> T getAccess(final T o)
    {
        if (! o.isAccessible())
        {
            o.setAccessible(true);
        }
        return o;
    }

    /**
     * Set field as accessible, and remove final flag if set.
     *
     * @param field field to get access.
     *
     * @return this same field.
     */
    public static Field getAccess(final Field field)
    {
        getAccess((AccessibleObject) field);
        if (Modifier.isFinal(field.getModifiers()))
        {
            try
            {
                final Field modifiersField = Field.class.getDeclaredField("modifiers");
                getAccess(modifiersField);
                modifiersField.setInt(field, field.getModifiers() & ~ Modifier.FINAL);
            } catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e)
            {
                throw new IllegalArgumentException("That shouldn't happend (0)");
            }
        }
        return field;
    }

    /**
     * Search for the first publically and privately defined field of thie given name.
     *
     * @param target         class to search in.
     * @param name           name of field.
     * @param throwException if method should throw exception when there is no given field.
     * @param <T>            type of field.
     *
     * @return field accessor for this field.
     *
     * @throws IllegalStateException If we cannot find this field.
     */
    public static <T> FieldAccessor<T> getField(final Class<?> target, final String name, final boolean throwException)
    {
        for (final Field field : target.getDeclaredFields())
        {
            if (((name == null) || field.getName().equals(name)))
            {
                getAccess(field);
                return new FieldAccessor<>(field);
            }
        }

        // Search in parent classes
        if (target.getSuperclass() != null)
        {
            return getField(target.getSuperclass(), name, throwException);
        }
        if (throwException)
        {
            throw new IllegalArgumentException("Cannot find field with name " + name);
        }
        return null;
    }

    /**
     * Search for the first publically and privately defined field of thie given name.
     *
     * @param target class to search in.
     * @param name   name of field.
     * @param <T>    type of field.
     *
     * @return field accessor for this field.
     *
     * @throws IllegalStateException If we cannot find this field.
     */
    public static <T> FieldAccessor<T> getField(final Class<?> target, final String name)
    {
        return getField(target, name, true);
    }


    // Common method
    private static <T> FieldAccessor<T> getField(final Class<?> target, final String name, final Class<T> fieldType, int index, final boolean throwException)
    {
        for (final Field field : target.getDeclaredFields())
        {
            if (((name == null) || field.getName().equals(name)) && (fieldType.isAssignableFrom(field.getType()) && (index-- <= 0)))
            {
                getAccess(field);
                return new FieldAccessor<>(field);
            }
        }

        // Search in parent classes
        if (target.getSuperclass() != null)
        {
            return getField(target.getSuperclass(), name, fieldType, index, throwException);
        }
        if (throwException)
        {
            throw new IllegalArgumentException("Cannot find field with type " + fieldType + " in " + target + ", index: " + index);
        }
        return null;
    }

    /**
     * Search for the first publically and privately defined method of the given name and parameter count.
     *
     * @param className  lookup name of the class, see {@link #getCanonicalClass(String)}.
     * @param methodName the method name, or NULL to skip.
     * @param params     the expected parameters.
     *
     * @return An object that invokes this specific method.
     *
     * @throws IllegalStateException If we cannot find this method.
     */
    public static MethodInvoker getMethod(final String className, final String methodName, final Class<?>... params)
    {
        return getSimpleMethod(getCanonicalClass(className), methodName, null, true, params);
    }

    /**
     * Search for the first publically and privately defined method of the given name and parameter count.
     *
     * @param clazz      a class to start with.
     * @param methodName the method name, or NULL to skip.
     * @param params     the expected parameters.
     *
     * @return An object that invokes this specific method.
     *
     * @throws IllegalStateException If we cannot find this method.
     */
    public static MethodInvoker getMethod(final Class<?> clazz, final String methodName, final Class<?>... params)
    {
        return getSimpleMethod(clazz, methodName, null, true, params);
    }

    /**
     * Search for the first publically and privately defined method of the given name and parameter count.
     *
     * @param className      lookup name of the class, see {@link #getCanonicalClass(String)}.
     * @param methodName     the method name, or NULL to skip.
     * @param throwException if method should throw exception when there is no given method.
     * @param params         the expected parameters.
     *
     * @return An object that invokes this specific method.
     *
     * @throws IllegalStateException If we cannot find this method.
     */
    public static MethodInvoker getMethod(final String className, final String methodName, final boolean throwException, final Class<?>... params)
    {
        return getSimpleMethod(getCanonicalClass(className), methodName, null, throwException, params);
    }

    /**
     * Search for the first publically and privately defined method of the given name and parameter count.
     *
     * @param clazz          a class to start with.
     * @param methodName     the method name, or NULL to skip.
     * @param throwException if method should throw exception when there is no given method.
     * @param params         the expected parameters.
     *
     * @return An object that invokes this specific method.
     *
     * @throws IllegalStateException If we cannot find this method.
     */
    public static MethodInvoker getMethod(final Class<?> clazz, final String methodName, final boolean throwException, final Class<?>... params)
    {
        return getSimpleMethod(clazz, methodName, null, throwException, params);
    }

    /**
     * Search for the first publically and privately defined method of the given name and parameter count.
     *
     * @param clazz      a class to start with.
     * @param methodName the method name, or NULL to skip.
     * @param returnType the expected return type, or NULL to ignore.
     * @param params     the expected parameters.
     *
     * @return An object that invokes this specific method.
     *
     * @throws IllegalStateException If we cannot find this method.
     */
    public static MethodInvoker getTypedMethod(final Class<?> clazz, final String methodName, final Class<?> returnType, final Class<?>... params)
    {
        for (final Method method : clazz.getDeclaredMethods())
        {
            if (((methodName == null) || method.getName().equals(methodName)) && ((returnType == null) || method.getReturnType().equals(returnType)) && Arrays.equals(method.getParameterTypes(), params))
            {
                getAccess(method);
                return new MethodInvoker(method);
            }
        }
        // Search in every superclass
        if (clazz.getSuperclass() != null)
        {
            return getMethod(clazz.getSuperclass(), methodName, params);
        }
        throw new IllegalStateException(String.format("Unable to find method %s (%s).", methodName, Arrays.asList(params)));
    }

    /**
     * Search for the first publically and privately defined method of the given name and parameter count.
     *
     * @param clazz          a class to start with.
     * @param methodName     the method name, or NULL to skip.
     * @param returnType     the expected return type, or NULL to ignore.
     * @param throwException if method should throw exception when there is no given method.
     * @param params         the expected parameters.
     *
     * @return An object that invokes this specific method.
     *
     * @throws IllegalStateException If we cannot find this method.
     */
    public static MethodInvoker getTypedMethod(final Class<?> clazz, final String methodName, final Class<?> returnType, final boolean throwException, final Class<?>... params)
    {
        for (final Method method : clazz.getDeclaredMethods())
        {
            if (((methodName == null) || method.getName().equals(methodName)) && ((returnType == null) || method.getReturnType().equals(returnType)) && Arrays.equals(method.getParameterTypes(), params))
            {
                getAccess(method);
                return new MethodInvoker(method);
            }
        }
        // Search in every superclass
        if (clazz.getSuperclass() != null)
        {
            return getMethod(clazz.getSuperclass(), methodName, throwException, params);
        }
        if (throwException)
        {
            throw new IllegalStateException(String.format("Unable to find method %s (%s).", methodName, Arrays.asList(params)));
        }
        return null;
    }

    /**
     * Search for the first publically and privately defined method of the given name and parameter count.
     *
     * @param clazz      a class to start with.
     * @param methodName the method name, or NULL to skip.
     * @param returnType the expected return type, or NULL to ignore.
     * @param params     the expected parameters.
     *
     * @return An object that invokes this specific method.
     *
     * @throws IllegalStateException If we cannot find this method.
     */
    private static MethodInvoker getSimpleMethod(final Class<?> clazz, final String methodName, final Class<?> returnType, final boolean throwException, final Class<?>... params)
    {
        for (final Method method : clazz.getDeclaredMethods())
        {
            if (((params.length == 0) || Arrays.equals(method.getParameterTypes(), params)) && ((methodName == null) || method.getName().equals(methodName)) && ((returnType == null) || method.getReturnType().equals(returnType)))
            {
                getAccess(method);
                return new MethodInvoker(method);
            }
        }
        // Search in every superclass
        if (clazz.getSuperclass() != null)
        {
            return getMethod(clazz.getSuperclass(), methodName, throwException, params);
        }
        if (throwException)
        {
            throw new IllegalStateException(String.format("Unable to find method %s (%s).", methodName, Arrays.asList(params)));
        }
        return null;
    }

    /**
     * Search for the first publically and privately defined constructor of the given name and parameter count.
     *
     * @param className lookup name of the class, see {@link #getCanonicalClass(String)}.
     * @param params    the expected parameters.
     *
     * @return An object that invokes this constructor.
     *
     * @throws IllegalStateException If we cannot find this method.
     */
    public static ConstructorInvoker getConstructor(final String className, final Class<?>... params)
    {
        return getConstructor(getCanonicalClass(className), params);
    }

    /**
     * Search for the first publically and privately defined constructor of the given name and parameter count.
     *
     * @param clazz  a class to start with.
     * @param params the expected parameters.
     *
     * @return An object that invokes this constructor.
     *
     * @throws IllegalStateException If we cannot find this method.
     */
    public static ConstructorInvoker getConstructor(final Class<?> clazz, final Class<?>... params)
    {
        for (final Constructor<?> constructor : clazz.getDeclaredConstructors())
        {
            if (Arrays.equals(constructor.getParameterTypes(), params))
            {
                getAccess(constructor);
                return new ConstructorInvoker(constructor);
            }
        }
        throw new IllegalStateException(String.format("Unable to find constructor for %s (%s).", clazz, Arrays.asList(params)));
    }

    /**
     * Search for nested class with givan name.
     *
     * @param clazz a class to start with.
     * @param name  name of class.
     *
     * @return nested class form given class.
     */
    public static Class<?> getNestedClass(final Class<?> clazz, final String name)
    {
        for (final Class<?> nc : clazz.getDeclaredClasses())
        {
            if ((name == null) || nc.getSimpleName().equals(name))
            {
                return nc;
            }
        }
        if (clazz.getSuperclass() != null)
        {
            return getNestedClass(clazz.getSuperclass(), name);
        }
        throw new IllegalStateException("Unable to find nested class: " + name + " in " + clazz.getName());
    }

    /**
     * If given class is non-primitive type {@link Class#isPrimitive()} then it will return
     * privitive class for it. Like: Boolean.class {@literal ->} boolean.class
     * If given class is primitive, then it will return given class.
     * If given class can't be primitive (like {@link String}) then it will return given class.
     *
     * @param clazz class to get primitive of it.
     *
     * @return primitive class if possible.
     */
    @SuppressWarnings("ObjectEquality")
    public static Class<?> getPrimitive(final Class<?> clazz)
    {
        if (clazz.isPrimitive())
        {
            return clazz;
        }
        if (clazz == Boolean.class)
        {
            return boolean.class;
        }
        if (clazz == Byte.class)
        {
            return byte.class;
        }
        if (clazz == Short.class)
        {
            return short.class;
        }
        if (clazz == Character.class)
        {
            return char.class;
        }
        if (clazz == Integer.class)
        {
            return int.class;
        }
        if (clazz == Long.class)
        {
            return long.class;
        }
        if (clazz == Float.class)
        {
            return float.class;
        }
        if (clazz == Double.class)
        {
            return double.class;
        }
        return clazz;
    }

    /**
     * If given class is primitive type {@link Class#isPrimitive()} then it will return
     * wrapper class for it. Like: boolean.class {@literal ->} Boolean.class
     * If given class isn't primitive, then it will return given class.
     *
     * @param clazz class to get wrapper of it.
     *
     * @return non-primitive class.
     */
    @SuppressWarnings("ObjectEquality")
    public static Class<?> getWrapperClass(final Class<?> clazz)
    {
        if (! clazz.isPrimitive())
        {
            return clazz;
        }
        if (clazz == boolean.class)
        {
            return Boolean.class;
        }
        if (clazz == byte.class)
        {
            return Byte.class;
        }
        if (clazz == short.class)
        {
            return Short.class;
        }
        if (clazz == char.class)
        {
            return Character.class;
        }
        if (clazz == int.class)
        {
            return Integer.class;
        }
        if (clazz == long.class)
        {
            return Long.class;
        }
        if (clazz == float.class)
        {
            return Float.class;
        }
        if (clazz == double.class)
        {
            return Double.class;
        }
        throw new Error("Unknown primitive type?"); // not possible?
    }

    /**
     * Get array class for given class.
     *
     * @param clazz class to get array type of it.
     *
     * @return array version of class.
     */
    @SuppressWarnings("ObjectEquality")
    public static Class<?> getArrayClass(final Class<?> clazz)
    {
        if (clazz.isPrimitive())
        {
            if (clazz == boolean.class)
            {
                return boolean[].class;
            }
            if (clazz == byte.class)
            {
                return byte[].class;
            }
            if (clazz == short.class)
            {
                return short[].class;
            }
            if (clazz == char.class)
            {
                return char[].class;
            }
            if (clazz == int.class)
            {
                return int[].class;
            }
            if (clazz == long.class)
            {
                return long[].class;
            }
            if (clazz == float.class)
            {
                return float[].class;
            }
            if (clazz == double.class)
            {
                return double[].class;
            }
        }
        if (clazz.isArray())
        {
            return getCanonicalClass("[" + clazz.getName());
        }
        return getCanonicalClass("[L" + clazz.getName() + ";");
    }

    /**
     * Return class for given name or throw exception.
     *
     * @param canonicalName name of class to find.
     *
     * @return class for given name.
     *
     * @throws IllegalArgumentException if there is no class with given name.
     */
    public static Class<?> getCanonicalClass(final String canonicalName) throws IllegalArgumentException
    {
        try
        {
            return Class.forName(canonicalName);
        } catch (final ClassNotFoundException e)
        {
            throw new IllegalArgumentException("Cannot find " + canonicalName, e);
        }
    }

    /**
     * Return class for given name or null.
     *
     * @param canonicalName name of class to find.
     *
     * @return class for given name or null.
     */
    public static Class<?> tryGetCanonicalClass(final String canonicalName)
    {
        try
        {
            return Class.forName(canonicalName);
        } catch (final ClassNotFoundException e)
        {
            return null;
        }
    }
}
