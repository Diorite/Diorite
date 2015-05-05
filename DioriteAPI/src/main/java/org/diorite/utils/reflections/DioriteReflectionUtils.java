package org.diorite.utils.reflections;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;

import org.diorite.utils.SimpleEnum;

public final class DioriteReflectionUtils
{
    private DioriteReflectionUtils()
    {
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

    private static <T extends SimpleEnum<T>> T getSimpleEnumValueSafe(final String name, final int id, Class<?> enumClass, final Object def)
    {
        do
        {
            System.out.println("trying for: " + name + ", " + enumClass + ", " + def);
            try
            {
                final Method m = enumClass.getMethod("values");
                final SimpleEnum<?>[] objects = (SimpleEnum<?>[]) m.invoke(null);
                for (final SimpleEnum<?> object : objects)
                {
                    System.out.println("Object: " + object);
                    if (object.name().equalsIgnoreCase(name) || (object.getId() == id))
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
     * @param target    - the target type.
     * @param name      - the name of the field, or NULL to ignore.
     * @param fieldType - a compatible field type.
     *
     * @return The field accessor.
     */
    public static <T> FieldAccessor<T> getField(final Class<?> target, final String name, final Class<T> fieldType)
    {
        return getField(target, name, fieldType, 0);
    }

    /**
     * Retrieve a field accessor for a specific field type and name.
     *
     * @param className - lookup name of the class, see {@link #getCanonicalClass(String)}.
     * @param name      - the name of the field, or NULL to ignore.
     * @param fieldType - a compatible field type.
     *
     * @return The field accessor.
     */
    public static <T> FieldAccessor<T> getField(final String className, final String name, final Class<T> fieldType)
    {
        return getField(getCanonicalClass(className), name, fieldType, 0);
    }

    /**
     * Retrieve a field accessor for a specific field type and name.
     *
     * @param target    - the target type.
     * @param fieldType - a compatible field type.
     * @param index     - the number of compatible fields to skip.
     *
     * @return The field accessor.
     */
    public static <T> FieldAccessor<T> getField(final Class<?> target, final Class<T> fieldType, final int index)
    {
        return getField(target, null, fieldType, index);
    }

    /**
     * Retrieve a field accessor for a specific field type and name.
     *
     * @param className - lookup name of the class, see {@link #getCanonicalClass(String)}.
     * @param fieldType - a compatible field type.
     * @param index     - the number of compatible fields to skip.
     *
     * @return The field accessor.
     */
    public static <T> FieldAccessor<T> getField(final String className, final Class<T> fieldType, final int index)
    {
        return getField(getCanonicalClass(className), fieldType, index);
    }

    private static AccessibleObject getAccess(final AccessibleObject o)
    {
        o.setAccessible(true);
        return o;
    }

    private static Field getAccess(final Field field)
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
            return getField(target.getSuperclass(), name);
        }
        throw new IllegalArgumentException("Cannot find field with name " + name);
    }


    // Common method
    private static <T> FieldAccessor<T> getField(final Class<?> target, final String name, final Class<T> fieldType, int index)
    {
        for (final Field field : target.getDeclaredFields())
        {
            if (((name == null) || field.getName().equals(name)) &&
                        (fieldType.isAssignableFrom(field.getType()) && (index-- <= 0)))
            {
                getAccess(field);
                return new FieldAccessor<>(field);
            }
        }

        // Search in parent classes
        if (target.getSuperclass() != null)
        {
            return getField(target.getSuperclass(), name, fieldType, index);
        }
        throw new IllegalArgumentException("Cannot find field with type " + fieldType + " in " + target + ", index: " + index);
    }

    /**
     * Search for the first publically and privately defined method of the given name and parameter count.
     *
     * @param className  - lookup name of the class, see {@link #getCanonicalClass(String)}.
     * @param methodName - the method name, or NULL to skip.
     * @param params     - the expected parameters.
     *
     * @return An object that invokes this specific method.
     *
     * @throws IllegalStateException If we cannot find this method.
     */
    public static MethodInvoker getMethod(final String className, final String methodName, final Class<?>... params)
    {
        return getSimpleMethod(getCanonicalClass(className), methodName, null, params);
    }

    /**
     * Search for the first publically and privately defined method of the given name and parameter count.
     *
     * @param clazz      - a class to start with.
     * @param methodName - the method name, or NULL to skip.
     * @param params     - the expected parameters.
     *
     * @return An object that invokes this specific method.
     *
     * @throws IllegalStateException If we cannot find this method.
     */
    private static MethodInvoker getMethod(final Class<?> clazz, final String methodName, final Class<?>... params)
    {
        return getSimpleMethod(clazz, methodName, null, params);
    }

    /**
     * Search for the first publically and privately defined method of the given name and parameter count.
     *
     * @param clazz      - a class to start with.
     * @param methodName - the method name, or NULL to skip.
     * @param returnType - the expected return type, or NULL to ignore.
     * @param params     - the expected parameters.
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

                method.setAccessible(true);
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
     * @param clazz      - a class to start with.
     * @param methodName - the method name, or NULL to skip.
     * @param returnType - the expected return type, or NULL to ignore.
     * @param params     - the expected parameters.
     *
     * @return An object that invokes this specific method.
     *
     * @throws IllegalStateException If we cannot find this method.
     */
    private static MethodInvoker getSimpleMethod(final Class<?> clazz, final String methodName, final Class<?> returnType, final Class<?>... params)
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
            return getMethod(clazz.getSuperclass(), methodName, params);
        }
        throw new IllegalStateException(String.format("Unable to find method %s (%s).", methodName, Arrays.asList(params)));
    }

    /**
     * Search for the first publically and privately defined constructor of the given name and parameter count.
     *
     * @param className - lookup name of the class, see {@link #getCanonicalClass(String)}.
     * @param params    - the expected parameters.
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
     * @param clazz  - a class to start with.
     * @param params - the expected parameters.
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
     * @param clazz - a class to start with.
     * @param name  - name of class.
     *
     * @return
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
}
