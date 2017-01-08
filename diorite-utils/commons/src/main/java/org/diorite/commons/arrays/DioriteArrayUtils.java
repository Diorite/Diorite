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

package org.diorite.commons.arrays;

import java.lang.reflect.Array;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.IntFunction;

/**
 * Utility class for empty arrays and joining multiple arrays into one.
 */
@SuppressWarnings("Duplicates")
public final class DioriteArrayUtils
{
    private static final Map<Class<?>, Object> arrayMaps      = new ConcurrentHashMap<>(20);
    /**
     * Empty array of boolean type.
     */
    public static final  boolean[]             EMPTY_BOOLEANS = getEmptyArrayByArrayClass(boolean[].class);
    /**
     * Empty array of char type.
     */
    public static final  char[]                EMPTY_CHARS    = getEmptyArrayByArrayClass(char[].class);
    /**
     * Empty array of byte type.
     */
    public static final  byte[]                EMPTY_BYTES    = getEmptyArrayByArrayClass(byte[].class);
    /**
     * Empty array of short type.
     */
    public static final  short[]               EMPTY_SHORTS   = getEmptyArrayByArrayClass(short[].class);
    /**
     * Empty array of int type.
     */
    public static final  int[]                 EMPTY_INTS     = getEmptyArrayByArrayClass(int[].class);
    /**
     * Empty array of long type.
     */
    public static final  long[]                EMPTY_LONGS    = getEmptyArrayByArrayClass(long[].class);
    /**
     * Empty array of float type.
     */
    public static final  float[]               EMPTY_FLOATS   = getEmptyArrayByArrayClass(float[].class);
    /**
     * Empty array of double type.
     */
    public static final  double[]              EMPTY_DOUBLES  = getEmptyArrayByArrayClass(double[].class);
    /**
     * Empty array of Object type.
     */
    public static final  Object[]              EMPTY_OBJECT   = getEmptyArrayByArrayClass(Object[].class);
    /**
     * Empty array of String type.
     */
    public static final  String[]              EMPTY_STRINGS  = getEmptyArrayByArrayClass(String[].class);

    private DioriteArrayUtils()
    {
    }

    /**
     * Adds prefix to each array element.
     *
     * @param prefix
     *         prefix to be added.
     * @param array
     *         array to edit.
     *
     * @return this same array as given after editing elements.
     */
    public static String[] addPrefix(String prefix, String[] array)
    {
        return addPrefixAndSuffix(prefix, "", array);
    }

    /**
     * Adds suffix to each array element.
     *
     * @param suffix
     *         suffix to be added.
     * @param array
     *         array to edit.
     *
     * @return this same array as given after editing elements.
     */
    public static String[] addSuffix(String suffix, String[] array)
    {
        return addPrefixAndSuffix("", suffix, array);
    }

    /**
     * Adds prefix and suffix to each array element.
     *
     * @param prefix
     *         prefix to be added.
     * @param suffix
     *         suffix to be added.
     * @param array
     *         array to edit.
     *
     * @return this same array as given after editing elements.
     */
    public static String[] addPrefixAndSuffix(String prefix, String suffix, String[] array)
    {
        for (int i = 0, arrayLength = array.length; i < arrayLength; i++)
        {
            String s = array[i];
            array[i] = prefix + s + suffix;
        }
        return array;
    }

    /**
     * Returns empty array of given type, must be an object type, for prymitives use {@link #getEmptyArray(Class)} or {@link #getEmptyArrayByArrayClass(Class)}
     * <br> All arrays are cached, method will always return this same array for this same class.
     *
     * @param clazz
     *         type of array, must be class of object.
     * @param <T>
     *         type of array.
     *
     * @return empty array of given type.
     *
     * @throws IllegalArgumentException
     *         if given class is primitive type. {@link Class#isPrimitive()}
     */
    @SuppressWarnings("unchecked")
    public static <T> T[] getEmptyObjectArray(Class<T> clazz) throws IllegalArgumentException
    {
        if (clazz.isPrimitive())
        {
            throw new IllegalArgumentException("Can't create array of primitive type: " + clazz);
        }
        Object o = arrayMaps.get(clazz);
        if (o != null)
        {
            return (T[]) o;
        }
        T[] array = (T[]) Array.newInstance(clazz, 0);
        arrayMaps.put(clazz, array);
        return array;
    }

    @SuppressWarnings("unchecked")
    public static <T> T[] getCachedEmptyArray(T[] array)
    {
        if (array.length != 0)
        {
            return array;
        }
        Class<?> componentType = array.getClass().getComponentType();
        Object o = arrayMaps.get(componentType);
        if (o != null)
        {
            return (T[]) o;
        }
        arrayMaps.put(componentType, array);
        return array;
    }

    /**
     * Returns array of given type as object. (to support primitive types) <br>
     * All arrays are cached, method will always return this same array for this same class.
     *
     * @param clazz
     *         type of array.
     *
     * @return empty array of given type.
     */
    @SuppressWarnings("unchecked")
    public static Object getEmptyArray(Class<?> clazz)
    {
        Object o = arrayMaps.get(clazz);
        if (o != null)
        {
            return o;
        }
        Object array = Array.newInstance(clazz, 0);
        arrayMaps.put(clazz, array);
        return array;
    }

    /**
     * Returns array of given type, given class must be type of array, like int[].class <br>
     * All arrays are cached, method will always return this same array for this same class.
     *
     * @param clazz
     *         type of array, must be class of array type.
     * @param <T>
     *         type of array.
     *
     * @return empty array of given type.
     *
     * @throws IllegalArgumentException
     *         if given class isn't array. {@link Class#isArray()}
     */
    @SuppressWarnings("unchecked")
    public static <T> T getEmptyArrayByArrayClass(Class<T> clazz) throws IllegalArgumentException
    {
        if (! clazz.isArray())
        {
            throw new IllegalArgumentException("Class must be array type: " + clazz);
        }
        Object o = arrayMaps.get(clazz);
        if (o != null)
        {
            return (T) o;
        }
        T array = (T) Array.newInstance(clazz.getComponentType(), 0);
        arrayMaps.put(clazz, array);
        return array;
    }

    /**
     * Returns array of given type, must be an object type, for prymitives use {@link #getEmptyArray(Class)} or {@link #getEmptyArrayByArrayClass(Class)} <br>
     * If given size is equals to 0, result of {@link #getEmptyArray(Class)} will be returned.
     *
     * @param clazz
     *         type of array, must be class of object.
     * @param size
     *         size of array.
     * @param <T>
     *         type of array.
     *
     * @return array of given type and size.
     *
     * @throws IllegalArgumentException
     *         if given class is primitive type. {@link Class#isPrimitive()}
     */
    @SuppressWarnings("unchecked")
    public static <T> T[] newObjectArray(Class<T> clazz, int size) throws IllegalArgumentException
    {
        if (size == 0)
        {
            return getEmptyObjectArray(clazz);
        }
        if (clazz.isPrimitive())
        {
            throw new IllegalArgumentException("Can't create array of primitive type: " + clazz);
        }
        return (T[]) Array.newInstance(clazz, size);
    }

    /**
     * Returns array of given type as object. (to support primitive types) <br>
     * If given size is equals to 0, result of {@link #getEmptyArray(Class)} will be returned.
     *
     * @param clazz
     *         type of array.
     * @param size
     *         size of array.
     *
     * @return array of given type and size.
     */
    @SuppressWarnings("unchecked")
    public static Object newArray(Class<?> clazz, int size)
    {
        if (size == 0)
        {
            return getEmptyArray(clazz);
        }
        return Array.newInstance(clazz, size);
    }

    /**
     * Returns new array of given type, given class must be type of array, like int[].class <br>
     * If given size is equals to 0, result of {@link #getEmptyArrayByArrayClass(Class)} will be returned.
     *
     * @param clazz
     *         type of array, must be class of array type.
     * @param size
     *         size of array.
     * @param <T>
     *         type of array.
     *
     * @return array of given type and size.
     *
     * @throws IllegalArgumentException
     *         if given class isn't array. {@link Class#isArray()}
     */
    @SuppressWarnings("unchecked")
    public static <T> T newArrayByArrayClass(Class<T> clazz, int size) throws IllegalArgumentException
    {
        if (size == 0)
        {
            return getEmptyArrayByArrayClass(clazz);
        }
        if (! clazz.isArray())
        {
            throw new IllegalArgumentException("Class must be array type: " + clazz);
        }
        return (T) Array.newInstance(clazz.getComponentType(), size);
    }

    /**
     * Joins arrays together, if only one array contains elements it will be returned without coping anything. <br>
     * If given array or arrays is empty, new empty array will be returned.
     *
     * @param arrayFunction
     *         function that create array of given size, just T[]::new.
     * @param arrays
     *         arrays to join.
     * @param <T>
     *         type of array.
     *
     * @return new joined array, or one of given ones if other arrays were empty.
     */
    @SafeVarargs
    public static <T> T[] join(IntFunction<T[]> arrayFunction, T[]... arrays)
    {
        if (arrays.length == 0)
        {
            return getCachedEmptyArray(arrayFunction.apply(0));
        }
        if (arrays.length == 1)
        {
            return arrays[0];
        }
        if (arrays.length == 2)
        {
            return append(arrayFunction, arrays[0], arrays[1]);
        }
        T[] notNull = null;
        int finalSize = 0;
        int nullArrays = 0;
        for (T[] array : arrays)
        {
            if ((array == null) || (array.length == 0))
            {
                nullArrays++;
            }
            else
            {
                notNull = array;
                finalSize += array.length;
            }
        }
        if (nullArrays == arrays.length)
        {
            return getCachedEmptyArray(arrayFunction.apply(0));
        }
        if (nullArrays == (arrays.length - 1))
        {
            if (notNull == null)
            {
                return getCachedEmptyArray(arrayFunction.apply(0));
            }
            return notNull;
        }
        T[] finalArray = arrayFunction.apply(finalSize);
        int destPos = 0;
        for (T[] array : arrays)
        {
            System.arraycopy(array, 0, finalArray, destPos, array.length);
            destPos += array.length;
        }
        return finalArray;
    }

    /**
     * Joins arrays together, if only one array contains elements it will be returned without coping anything. <br>
     * If given array or arrays is empty, new empty array will be returned.<br>
     * NOTE: this method use reflections!
     *
     * @param arrays
     *         arrays to join.
     * @param <T>
     *         type of array.
     *
     * @return new joined array, or one of given ones if other arrays were empty.
     */
    @SuppressWarnings("unchecked")
    @SafeVarargs
    public static <T> T[] join(T[]... arrays)
    {
        return join(i -> (T[]) newArray(arrays.getClass().getComponentType().getComponentType(), i), arrays);
    }

    /**
     * Joins arrays together, if only one array contains elements it will be returned without coping anything. <br>
     * If given array or arrays is empty, new empty array will be returned.
     *
     * @param arrays
     *         arrays to join.
     *
     * @return new joined array, or one of given ones if other arrays were empty.
     */
    public static boolean[] join(boolean[]... arrays)
    {
        if (arrays.length == 0)
        {
            return EMPTY_BOOLEANS;
        }
        if (arrays.length == 1)
        {
            return arrays[0];
        }
        if (arrays.length == 2)
        {
            return append(arrays[0], arrays[1]);
        }
        boolean[] notNull = null;
        int finalSize = 0;
        int nullArrays = 0;
        for (boolean[] array : arrays)
        {
            if ((array == null) || (array.length == 0))
            {
                nullArrays++;
            }
            else
            {
                notNull = array;
                finalSize += array.length;
            }
        }
        if (nullArrays == arrays.length)
        {
            return EMPTY_BOOLEANS;
        }
        if (nullArrays == (arrays.length - 1))
        {
            if (notNull == null)
            {
                return EMPTY_BOOLEANS;
            }
            return notNull;
        }
        boolean[] finalArray = new boolean[finalSize];
        int destPos = 0;
        for (boolean[] array : arrays)
        {
            System.arraycopy(array, 0, finalArray, destPos, array.length);
            destPos += array.length;
        }
        return finalArray;
    }

    /**
     * Joins arrays together, if only one array contains elements it will be returned without coping anything. <br>
     * If given array or arrays is empty, new empty array will be returned.
     *
     * @param arrays
     *         arrays to join.
     *
     * @return new joined array, or one of given ones if other arrays were empty.
     */
    public static char[] join(char[]... arrays)
    {
        if (arrays.length == 0)
        {
            return EMPTY_CHARS;
        }
        if (arrays.length == 1)
        {
            return arrays[0];
        }
        if (arrays.length == 2)
        {
            return append(arrays[0], arrays[1]);
        }
        char[] notNull = null;
        int finalSize = 0;
        int nullArrays = 0;
        for (char[] array : arrays)
        {
            if ((array == null) || (array.length == 0))
            {
                nullArrays++;
            }
            else
            {
                notNull = array;
                finalSize += array.length;
            }
        }
        if (nullArrays == arrays.length)
        {
            return EMPTY_CHARS;
        }
        if (nullArrays == (arrays.length - 1))
        {
            if (notNull == null)
            {
                return EMPTY_CHARS;
            }
            return notNull;
        }
        char[] finalArray = new char[finalSize];
        int destPos = 0;
        for (char[] array : arrays)
        {
            System.arraycopy(array, 0, finalArray, destPos, array.length);
            destPos += array.length;
        }
        return finalArray;
    }

    /**
     * Joins arrays together, if only one array contains elements it will be returned without coping anything. <br>
     * If given array or arrays is empty, new empty array will be returned.
     *
     * @param arrays
     *         arrays to join.
     *
     * @return new joined array, or one of given ones if other arrays were empty.
     */
    public static byte[] join(byte[]... arrays)
    {
        if (arrays.length == 0)
        {
            return EMPTY_BYTES;
        }
        if (arrays.length == 1)
        {
            return arrays[0];
        }
        if (arrays.length == 2)
        {
            return append(arrays[0], arrays[1]);
        }
        byte[] notNull = null;
        int finalSize = 0;
        int nullArrays = 0;
        for (byte[] array : arrays)
        {
            if ((array == null) || (array.length == 0))
            {
                nullArrays++;
            }
            else
            {
                notNull = array;
                finalSize += array.length;
            }
        }
        if (nullArrays == arrays.length)
        {
            return EMPTY_BYTES;
        }
        if (nullArrays == (arrays.length - 1))
        {
            if (notNull == null)
            {
                return EMPTY_BYTES;
            }
            return notNull;
        }
        byte[] finalArray = new byte[finalSize];
        int destPos = 0;
        for (byte[] array : arrays)
        {
            System.arraycopy(array, 0, finalArray, destPos, array.length);
            destPos += array.length;
        }
        return finalArray;
    }

    /**
     * Joins arrays together, if only one array contains elements it will be returned without coping anything. <br>
     * If given array or arrays is empty, new empty array will be returned.
     *
     * @param arrays
     *         arrays to join.
     *
     * @return new joined array, or one of given ones if other arrays were empty.
     */
    public static short[] join(short[]... arrays)
    {
        if (arrays.length == 0)
        {
            return EMPTY_SHORTS;
        }
        if (arrays.length == 1)
        {
            return arrays[0];
        }
        if (arrays.length == 2)
        {
            return append(arrays[0], arrays[1]);
        }
        short[] notNull = null;
        int finalSize = 0;
        int nullArrays = 0;
        for (short[] array : arrays)
        {
            if ((array == null) || (array.length == 0))
            {
                nullArrays++;
            }
            else
            {
                notNull = array;
                finalSize += array.length;
            }
        }
        if (nullArrays == arrays.length)
        {
            return EMPTY_SHORTS;
        }
        if (nullArrays == (arrays.length - 1))
        {
            if (notNull == null)
            {
                return EMPTY_SHORTS;
            }
            return notNull;
        }
        short[] finalArray = new short[finalSize];
        int destPos = 0;
        for (short[] array : arrays)
        {
            System.arraycopy(array, 0, finalArray, destPos, array.length);
            destPos += array.length;
        }
        return finalArray;
    }

    /**
     * Joins arrays together, if only one array contains elements it will be returned without coping anything. <br>
     * If given array or arrays is empty, new empty array will be returned.
     *
     * @param arrays
     *         arrays to join.
     *
     * @return new joined array, or one of given ones if other arrays were empty.
     */
    public static int[] join(int[]... arrays)
    {
        if (arrays.length == 0)
        {
            return EMPTY_INTS;
        }
        if (arrays.length == 1)
        {
            return arrays[0];
        }
        if (arrays.length == 2)
        {
            return append(arrays[0], arrays[1]);
        }
        int[] notNull = null;
        int finalSize = 0;
        int nullArrays = 0;
        for (int[] array : arrays)
        {
            if ((array == null) || (array.length == 0))
            {
                nullArrays++;
            }
            else
            {
                notNull = array;
                finalSize += array.length;
            }
        }
        if (nullArrays == arrays.length)
        {
            return EMPTY_INTS;
        }
        if (nullArrays == (arrays.length - 1))
        {
            if (notNull == null)
            {
                return EMPTY_INTS;
            }
            return notNull;
        }
        int[] finalArray = new int[finalSize];
        int destPos = 0;
        for (int[] array : arrays)
        {
            System.arraycopy(array, 0, finalArray, destPos, array.length);
            destPos += array.length;
        }
        return finalArray;
    }

    /**
     * Joins arrays together, if only one array contains elements it will be returned without coping anything. <br>
     * If given array or arrays is empty, new empty array will be returned.
     *
     * @param arrays
     *         arrays to join.
     *
     * @return new joined array, or one of given ones if other arrays were empty.
     */
    public static long[] join(long[]... arrays)
    {
        if (arrays.length == 0)
        {
            return EMPTY_LONGS;
        }
        if (arrays.length == 1)
        {
            return arrays[0];
        }
        if (arrays.length == 2)
        {
            return append(arrays[0], arrays[1]);
        }
        long[] notNull = null;
        int finalSize = 0;
        int nullArrays = 0;
        for (long[] array : arrays)
        {
            if ((array == null) || (array.length == 0))
            {
                nullArrays++;
            }
            else
            {
                notNull = array;
                finalSize += array.length;
            }
        }
        if (nullArrays == arrays.length)
        {
            return EMPTY_LONGS;
        }
        if (nullArrays == (arrays.length - 1))
        {
            if (notNull == null)
            {
                return EMPTY_LONGS;
            }
            return notNull;
        }
        long[] finalArray = new long[finalSize];
        int destPos = 0;
        for (long[] array : arrays)
        {
            System.arraycopy(array, 0, finalArray, destPos, array.length);
            destPos += array.length;
        }
        return finalArray;
    }

    /**
     * Joins arrays together, if only one array contains elements it will be returned without coping anything. <br>
     * If given array or arrays is empty, new empty array will be returned.
     *
     * @param arrays
     *         arrays to join.
     *
     * @return new joined array, or one of given ones if other arrays were empty.
     */
    public static float[] join(float[]... arrays)
    {
        if (arrays.length == 0)
        {
            return EMPTY_FLOATS;
        }
        if (arrays.length == 1)
        {
            return arrays[0];
        }
        if (arrays.length == 2)
        {
            return append(arrays[0], arrays[1]);
        }
        float[] notNull = null;
        int finalSize = 0;
        int nullArrays = 0;
        for (float[] array : arrays)
        {
            if ((array == null) || (array.length == 0))
            {
                nullArrays++;
            }
            else
            {
                notNull = array;
                finalSize += array.length;
            }
        }
        if (nullArrays == arrays.length)
        {
            return EMPTY_FLOATS;
        }
        if (nullArrays == (arrays.length - 1))
        {
            if (notNull == null)
            {
                return EMPTY_FLOATS;
            }
            return notNull;
        }
        float[] finalArray = new float[finalSize];
        int destPos = 0;
        for (float[] array : arrays)
        {
            System.arraycopy(array, 0, finalArray, destPos, array.length);
            destPos += array.length;
        }
        return finalArray;
    }

    /**
     * Joins arrays together, if only one array contains elements it will be returned without coping anything. <br>
     * If given array or arrays is empty, new empty array will be returned.
     *
     * @param arrays
     *         arrays to join.
     *
     * @return new joined array, or one of given ones if other arrays were empty.
     */
    public static double[] join(double[]... arrays)
    {
        if (arrays.length == 0)
        {
            return EMPTY_DOUBLES;
        }
        if (arrays.length == 1)
        {
            return arrays[0];
        }
        if (arrays.length == 2)
        {
            return append(arrays[0], arrays[1]);
        }
        double[] notNull = null;
        int finalSize = 0;
        int nullArrays = 0;
        for (double[] array : arrays)
        {
            if ((array == null) || (array.length == 0))
            {
                nullArrays++;
            }
            else
            {
                notNull = array;
                finalSize += array.length;
            }
        }
        if (nullArrays == arrays.length)
        {
            return EMPTY_DOUBLES;
        }
        if (nullArrays == (arrays.length - 1))
        {
            if (notNull == null)
            {
                return EMPTY_DOUBLES;
            }
            return notNull;
        }
        double[] finalArray = new double[finalSize];
        int destPos = 0;
        for (double[] array : arrays)
        {
            System.arraycopy(array, 0, finalArray, destPos, array.length);
            destPos += array.length;
        }
        return finalArray;
    }

    /**
     * Joins 2 arrays together, if any array is empty then other array will be returned without coping anything.
     *
     * @param arrayFunction
     *         function that create array of given size, just T[]::new.
     * @param arrayA
     *         first array.
     * @param arrayB
     *         second array.
     * @param <T>
     *         type of array.
     *
     * @return new joined array, or one of given ones if any of arrays was empty.
     */
    @SafeVarargs
    public static <T> T[] append(IntFunction<T[]> arrayFunction, T[] arrayA, T... arrayB)
    {
        if (arrayB.length == 0)
        {
            return arrayA;
        }
        if (arrayA.length == 0)
        {
            return arrayB;
        }
        T[] array = arrayFunction.apply(arrayA.length + arrayB.length);
        copy(array, arrayA, arrayB, true);
        return array;
    }

    /**
     * Joins 2 arrays together, if any array is empty then other array will be returned without coping anything.
     *
     * @param arrayFunction
     *         function that create array of given size, just T[]::new.
     * @param arrayA
     *         first array.
     * @param arrayB
     *         second array.
     * @param <T>
     *         type of array.
     *
     * @return new joined array, or one of given ones if any of arrays was empty.
     */
    @SafeVarargs
    public static <T> T[] prepend(IntFunction<T[]> arrayFunction, T[] arrayA, T... arrayB)
    {
        if (arrayB.length == 0)
        {
            return arrayA;
        }
        if (arrayA.length == 0)
        {
            return arrayB;
        }
        T[] array = arrayFunction.apply(arrayA.length + arrayB.length);
        copy(array, arrayA, arrayB, false);
        return array;
    }

    /**
     * Joins 2 arrays together, if any array is empty then other array will be returned without coping anything. <br>
     * NOTE: this method use reflections!
     *
     * @param arrayA
     *         first array.
     * @param arrayB
     *         second array.
     * @param <T>
     *         type of array.
     *
     * @return new joined array, or one of given ones if any of arrays was empty.
     */
    @SuppressWarnings("unchecked")
    @SafeVarargs
    public static <T> T[] append(T[] arrayA, T... arrayB)
    {
        return append(i -> (T[]) newArray(arrayA.getClass().getComponentType(), i), arrayA, arrayB);
    }

    /**
     * Joins 2 arrays together, if any array is empty then other array will be returned without coping anything. <br>
     * NOTE: this method use reflections!
     *
     * @param arrayA
     *         first array.
     * @param arrayB
     *         second array.
     * @param <T>
     *         type of array.
     *
     * @return new joined array, or one of given ones if any of arrays was empty.
     */
    @SuppressWarnings("unchecked")
    @SafeVarargs
    public static <T> T[] prepend(T[] arrayA, T... arrayB)
    {
        return prepend(i -> (T[]) newArray(arrayA.getClass().getComponentType(), i), arrayA, arrayB);
    }

    /**
     * Joins 2 arrays together, if any array is empty then other array will be returned without coping anything.
     *
     * @param arrayA
     *         first array.
     * @param arrayB
     *         second array.
     *
     * @return new joined array, or one of given ones if any of arrays was empty.
     */
    public static boolean[] append(boolean[] arrayA, boolean... arrayB)
    {
        if (arrayB.length == 0)
        {
            return arrayA;
        }
        if (arrayA.length == 0)
        {
            return arrayB;
        }
        boolean[] array = new boolean[arrayA.length + arrayB.length];
        copy(array, arrayA, arrayB, true);
        return array;
    }

    /**
     * Joins 2 arrays together, if any array is empty then other array will be returned without coping anything.
     *
     * @param arrayA
     *         first array.
     * @param arrayB
     *         second array.
     *
     * @return new joined array, or one of given ones if any of arrays was empty.
     */
    public static boolean[] prepend(boolean[] arrayA, boolean... arrayB)
    {
        if (arrayB.length == 0)
        {
            return arrayA;
        }
        if (arrayA.length == 0)
        {
            return arrayB;
        }
        boolean[] array = new boolean[arrayA.length + arrayB.length];
        copy(array, arrayA, arrayB, false);
        return array;
    }

    /**
     * Joins 2 arrays together, if any array is empty then other array will be returned without coping anything.
     *
     * @param arrayA
     *         first array.
     * @param arrayB
     *         second array.
     *
     * @return new joined array, or one of given ones if any of arrays was empty.
     */
    public static char[] append(char[] arrayA, char... arrayB)
    {
        if (arrayB.length == 0)
        {
            return arrayA;
        }
        if (arrayA.length == 0)
        {
            return arrayB;
        }
        char[] array = new char[arrayA.length + arrayB.length];
        copy(array, arrayA, arrayB, true);
        return array;
    }

    /**
     * Joins 2 arrays together, if any array is empty then other array will be returned without coping anything.
     *
     * @param arrayA
     *         first array.
     * @param arrayB
     *         second array.
     *
     * @return new joined array, or one of given ones if any of arrays was empty.
     */
    public static char[] prepend(char[] arrayA, char... arrayB)
    {
        if (arrayB.length == 0)
        {
            return arrayA;
        }
        if (arrayA.length == 0)
        {
            return arrayB;
        }
        char[] array = new char[arrayA.length + arrayB.length];
        copy(array, arrayA, arrayB, false);
        return array;
    }

    /**
     * Joins 2 arrays together, if any array is empty then other array will be returned without coping anything.
     *
     * @param arrayA
     *         first array.
     * @param arrayB
     *         second array.
     *
     * @return new joined array, or one of given ones if any of arrays was empty.
     */
    public static byte[] append(byte[] arrayA, byte... arrayB)
    {
        if (arrayB.length == 0)
        {
            return arrayA;
        }
        if (arrayA.length == 0)
        {
            return arrayB;
        }
        byte[] array = new byte[arrayA.length + arrayB.length];
        copy(array, arrayA, arrayB, true);
        return array;
    }

    /**
     * Joins 2 arrays together, if any array is empty then other array will be returned without coping anything.
     *
     * @param arrayA
     *         first array.
     * @param arrayB
     *         second array.
     *
     * @return new joined array, or one of given ones if any of arrays was empty.
     */
    public static byte[] prepend(byte[] arrayA, byte... arrayB)
    {
        if (arrayB.length == 0)
        {
            return arrayA;
        }
        if (arrayA.length == 0)
        {
            return arrayB;
        }
        byte[] array = new byte[arrayA.length + arrayB.length];
        copy(array, arrayA, arrayB, false);
        return array;
    }

    /**
     * Joins 2 arrays together, if any array is empty then other array will be returned without coping anything.
     *
     * @param arrayA
     *         first array.
     * @param arrayB
     *         second array.
     *
     * @return new joined array, or one of given ones if any of arrays was empty.
     */
    public static short[] append(short[] arrayA, short... arrayB)
    {
        if (arrayB.length == 0)
        {
            return arrayA;
        }
        if (arrayA.length == 0)
        {
            return arrayB;
        }
        short[] array = new short[arrayA.length + arrayB.length];
        copy(array, arrayA, arrayB, true);
        return array;
    }

    /**
     * Joins 2 arrays together, if any array is empty then other array will be returned without coping anything.
     *
     * @param arrayA
     *         first array.
     * @param arrayB
     *         second array.
     *
     * @return new joined array, or one of given ones if any of arrays was empty.
     */
    public static short[] prepend(short[] arrayA, short... arrayB)
    {
        if (arrayB.length == 0)
        {
            return arrayA;
        }
        if (arrayA.length == 0)
        {
            return arrayB;
        }
        short[] array = new short[arrayA.length + arrayB.length];
        copy(array, arrayA, arrayB, false);
        return array;
    }

    /**
     * Joins 2 arrays together, if any array is empty then other array will be returned without coping anything.
     *
     * @param arrayA
     *         first array.
     * @param arrayB
     *         second array.
     *
     * @return new joined array, or one of given ones if any of arrays was empty.
     */
    public static int[] append(int[] arrayA, int... arrayB)
    {
        if (arrayB.length == 0)
        {
            return arrayA;
        }
        if (arrayA.length == 0)
        {
            return arrayB;
        }
        int[] array = new int[arrayA.length + arrayB.length];
        copy(array, arrayA, arrayB, true);
        return array;
    }

    /**
     * Joins 2 arrays together, if any array is empty then other array will be returned without coping anything.
     *
     * @param arrayA
     *         first array.
     * @param arrayB
     *         second array.
     *
     * @return new joined array, or one of given ones if any of arrays was empty.
     */
    public static int[] prepend(int[] arrayA, int... arrayB)
    {
        if (arrayB.length == 0)
        {
            return arrayA;
        }
        if (arrayA.length == 0)
        {
            return arrayB;
        }
        int[] array = new int[arrayA.length + arrayB.length];
        copy(array, arrayA, arrayB, false);
        return array;
    }

    /**
     * Joins 2 arrays together, if any array is empty then other array will be returned without coping anything.
     *
     * @param arrayA
     *         first array.
     * @param arrayB
     *         second array.
     *
     * @return new joined array, or one of given ones if any of arrays was empty.
     */
    public static long[] append(long[] arrayA, long... arrayB)
    {
        if (arrayB.length == 0)
        {
            return arrayA;
        }
        if (arrayA.length == 0)
        {
            return arrayB;
        }
        long[] array = new long[arrayA.length + arrayB.length];
        copy(array, arrayA, arrayB, true);
        return array;
    }

    /**
     * Joins 2 arrays together, if any array is empty then other array will be returned without coping anything.
     *
     * @param arrayA
     *         first array.
     * @param arrayB
     *         second array.
     *
     * @return new joined array, or one of given ones if any of arrays was empty.
     */
    public static long[] prepend(long[] arrayA, long... arrayB)
    {
        if (arrayB.length == 0)
        {
            return arrayA;
        }
        if (arrayA.length == 0)
        {
            return arrayB;
        }
        long[] array = new long[arrayA.length + arrayB.length];
        copy(array, arrayA, arrayB, false);
        return array;
    }

    /**
     * Joins 2 arrays together, if any array is empty then other array will be returned without coping anything.
     *
     * @param arrayA
     *         first array.
     * @param arrayB
     *         second array.
     *
     * @return new joined array, or one of given ones if any of arrays was empty.
     */
    public static float[] append(float[] arrayA, float... arrayB)
    {
        if (arrayB.length == 0)
        {
            return arrayA;
        }
        if (arrayA.length == 0)
        {
            return arrayB;
        }
        float[] array = new float[arrayA.length + arrayB.length];
        copy(array, arrayA, arrayB, true);
        return array;
    }

    /**
     * Joins 2 arrays together, if any array is empty then other array will be returned without coping anything.
     *
     * @param arrayA
     *         first array.
     * @param arrayB
     *         second array.
     *
     * @return new joined array, or one of given ones if any of arrays was empty.
     */
    public static float[] prepend(float[] arrayA, float... arrayB)
    {
        if (arrayB.length == 0)
        {
            return arrayA;
        }
        if (arrayA.length == 0)
        {
            return arrayB;
        }
        float[] array = new float[arrayA.length + arrayB.length];
        copy(array, arrayA, arrayB, false);
        return array;
    }

    /**
     * Joins 2 arrays together, if any array is empty then other array will be returned without coping anything.
     *
     * @param arrayA
     *         first array.
     * @param arrayB
     *         second array.
     *
     * @return new joined array, or one of given ones if any of arrays was empty.
     */
    public static double[] append(double[] arrayA, double... arrayB)
    {
        if (arrayB.length == 0)
        {
            return arrayA;
        }
        if (arrayA.length == 0)
        {
            return arrayB;
        }
        double[] array = new double[arrayA.length + arrayB.length];
        copy(array, arrayA, arrayB, true);
        return array;
    }

    /**
     * Joins 2 arrays together, if any array is empty then other array will be returned without coping anything.
     *
     * @param arrayA
     *         first array.
     * @param arrayB
     *         second array.
     *
     * @return new joined array, or one of given ones if any of arrays was empty.
     */
    public static double[] prepend(double[] arrayA, double... arrayB)
    {
        if (arrayB.length == 0)
        {
            return arrayA;
        }
        if (arrayA.length == 0)
        {
            return arrayB;
        }
        double[] array = new double[arrayA.length + arrayB.length];
        copy(array, arrayA, arrayB, false);
        return array;
    }

    /**
     * Coverts given bytes array to array of booleans.
     *
     * @param array
     *         bytes array to convert.
     *
     * @return converted array of booleans.
     */
    public static boolean[] toBooleanArray(byte[] array)
    {
        boolean[] result = new boolean[array.length];
        for (int i = 0; i < array.length; i++)
        {
            result[i] = array[i] > 0;
        }
        return result;
    }

    /**
     * Coverts given shorts array to array of booleans.
     *
     * @param array
     *         shorts array to convert.
     *
     * @return converted array of booleans.
     */
    public static boolean[] toBooleanArray(short[] array)
    {
        boolean[] result = new boolean[array.length];
        for (int i = 0; i < array.length; i++)
        {
            result[i] = array[i] > 0;
        }
        return result;
    }

    /**
     * Coverts given chars array to array of booleans.
     *
     * @param array
     *         chars array to convert.
     *
     * @return converted array of booleans.
     */
    public static boolean[] toBooleanArray(char[] array)
    {
        boolean[] result = new boolean[array.length];
        for (int i = 0; i < array.length; i++)
        {
            result[i] = array[i] > 0;
        }
        return result;
    }

    /**
     * Coverts given ints array to array of booleans.
     *
     * @param array
     *         ints array to convert.
     *
     * @return converted array of booleans.
     */
    public static boolean[] toBooleanArray(int[] array)
    {
        boolean[] result = new boolean[array.length];
        for (int i = 0; i < array.length; i++)
        {
            result[i] = array[i] > 0;
        }
        return result;
    }

    /**
     * Coverts given longs array to array of booleans.
     *
     * @param array
     *         longs array to convert.
     *
     * @return converted array of booleans.
     */
    public static boolean[] toBooleanArray(long[] array)
    {
        boolean[] result = new boolean[array.length];
        for (int i = 0; i < array.length; i++)
        {
            result[i] = array[i] > 0;
        }
        return result;
    }

    /**
     * Coverts given floats array to array of booleans.
     *
     * @param array
     *         floats array to convert.
     *
     * @return converted array of booleans.
     */
    public static boolean[] toBooleanArray(float[] array)
    {
        boolean[] result = new boolean[array.length];
        for (int i = 0; i < array.length; i++)
        {
            result[i] = array[i] > 0;
        }
        return result;
    }

    /**
     * Coverts given doubles array to array of booleans.
     *
     * @param array
     *         doubles array to convert.
     *
     * @return converted array of booleans.
     */
    public static boolean[] toBooleanArray(double[] array)
    {
        boolean[] result = new boolean[array.length];
        for (int i = 0; i < array.length; i++)
        {
            result[i] = array[i] > 0;
        }
        return result;
    }

    /**
     * Coverts given booleans array to array of bytes.
     *
     * @param array
     *         booleans array to convert.
     *
     * @return converted array of bytes.
     */
    public static byte[] toByteArray(boolean[] array)
    {
        byte[] result = new byte[array.length];
        for (int i = 0; i < array.length; i++)
        {
            result[i] = array[i] ? (byte) 1 : (byte) 0;
        }
        return result;
    }

    /**
     * Coverts given shorts array to array of bytes.
     *
     * @param array
     *         shorts array to convert.
     *
     * @return converted array of bytes.
     */
    public static byte[] toByteArray(short[] array)
    {
        byte[] result = new byte[array.length];
        for (int i = 0; i < array.length; i++)
        {
            result[i] = (byte) array[i];
        }
        return result;
    }

    /**
     * Coverts given chars array to array of bytes.
     *
     * @param array
     *         chars array to convert.
     *
     * @return converted array of bytes.
     */
    public static byte[] toByteArray(char[] array)
    {
        byte[] result = new byte[array.length];
        for (int i = 0; i < array.length; i++)
        {
            result[i] = (byte) array[i];
        }
        return result;
    }

    /**
     * Coverts given ints array to array of bytes.
     *
     * @param array
     *         ints array to convert.
     *
     * @return converted array of bytes.
     */
    public static byte[] toByteArray(int[] array)
    {
        byte[] result = new byte[array.length];
        for (int i = 0; i < array.length; i++)
        {
            result[i] = (byte) array[i];
        }
        return result;
    }

    /**
     * Coverts given longs array to array of bytes.
     *
     * @param array
     *         longs array to convert.
     *
     * @return converted array of bytes.
     */
    public static byte[] toByteArray(long[] array)
    {
        byte[] result = new byte[array.length];
        for (int i = 0; i < array.length; i++)
        {
            result[i] = (byte) array[i];
        }
        return result;
    }

    /**
     * Coverts given floats array to array of bytes.
     *
     * @param array
     *         floats array to convert.
     *
     * @return converted array of bytes.
     */
    public static byte[] toByteArray(float[] array)
    {
        byte[] result = new byte[array.length];
        for (int i = 0; i < array.length; i++)
        {
            result[i] = (byte) array[i];
        }
        return result;
    }

    /**
     * Coverts given doubles array to array of bytes.
     *
     * @param array
     *         doubles array to convert.
     *
     * @return converted array of bytes.
     */
    public static byte[] toByteArray(double[] array)
    {
        byte[] result = new byte[array.length];
        for (int i = 0; i < array.length; i++)
        {
            result[i] = (byte) array[i];
        }
        return result;
    }

    /**
     * Coverts given booleans array to array of shorts.
     *
     * @param array
     *         booleans array to convert.
     *
     * @return converted array of shorts.
     */
    public static short[] toShortArray(boolean[] array)
    {
        short[] result = new short[array.length];
        for (int i = 0; i < array.length; i++)
        {
            result[i] = array[i] ? (short) 1 : (short) 0;
        }
        return result;
    }

    /**
     * Coverts given bytes array to array of shorts.
     *
     * @param array
     *         bytes array to convert.
     *
     * @return converted array of shorts.
     */
    public static short[] toShortArray(byte[] array)
    {
        short[] result = new short[array.length];
        for (int i = 0; i < array.length; i++)
        {
            result[i] = (short) array[i];
        }
        return result;
    }

    /**
     * Coverts given chars array to array of shorts.
     *
     * @param array
     *         chars array to convert.
     *
     * @return converted array of shorts.
     */
    public static short[] toShortArray(char[] array)
    {
        short[] result = new short[array.length];
        for (int i = 0; i < array.length; i++)
        {
            result[i] = (short) array[i];
        }
        return result;
    }

    /**
     * Coverts given ints array to array of shorts.
     *
     * @param array
     *         ints array to convert.
     *
     * @return converted array of shorts.
     */
    public static short[] toShortArray(int[] array)
    {
        short[] result = new short[array.length];
        for (int i = 0; i < array.length; i++)
        {
            result[i] = (short) array[i];
        }
        return result;
    }

    /**
     * Coverts given longs array to array of shorts.
     *
     * @param array
     *         longs array to convert.
     *
     * @return converted array of shorts.
     */
    public static short[] toShortArray(long[] array)
    {
        short[] result = new short[array.length];
        for (int i = 0; i < array.length; i++)
        {
            result[i] = (short) array[i];
        }
        return result;
    }

    /**
     * Coverts given floats array to array of shorts.
     *
     * @param array
     *         floats array to convert.
     *
     * @return converted array of shorts.
     */
    public static short[] toShortArray(float[] array)
    {
        short[] result = new short[array.length];
        for (int i = 0; i < array.length; i++)
        {
            result[i] = (short) array[i];
        }
        return result;
    }

    /**
     * Coverts given doubles array to array of shorts.
     *
     * @param array
     *         doubles array to convert.
     *
     * @return converted array of shorts.
     */
    public static short[] toShortArray(double[] array)
    {
        short[] result = new short[array.length];
        for (int i = 0; i < array.length; i++)
        {
            result[i] = (short) array[i];
        }
        return result;
    }

    /**
     * Coverts given booleans array to array of chars.
     *
     * @param array
     *         booleans array to convert.
     *
     * @return converted array of chars.
     */
    public static char[] toCharArray(boolean[] array)
    {
        char[] result = new char[array.length];
        for (int i = 0; i < array.length; i++)
        {
            result[i] = array[i] ? (char) 1 : (char) 0;
        }
        return result;
    }

    /**
     * Coverts given shorts array to array of chars.
     *
     * @param array
     *         shorts array to convert.
     *
     * @return converted array of chars.
     */
    public static char[] toCharArray(short[] array)
    {
        char[] result = new char[array.length];
        for (int i = 0; i < array.length; i++)
        {
            result[i] = (char) array[i];
        }
        return result;
    }

    /**
     * Coverts given bytes array to array of chars.
     *
     * @param array
     *         bytes array to convert.
     *
     * @return converted array of chars.
     */
    public static char[] toCharArray(byte[] array)
    {
        char[] result = new char[array.length];
        for (int i = 0; i < array.length; i++)
        {
            result[i] = (char) array[i];
        }
        return result;
    }

    /**
     * Coverts given ints array to array of chars.
     *
     * @param array
     *         ints array to convert.
     *
     * @return converted array of chars.
     */
    public static char[] toCharArray(int[] array)
    {
        char[] result = new char[array.length];
        for (int i = 0; i < array.length; i++)
        {
            result[i] = (char) array[i];
        }
        return result;
    }

    /**
     * Coverts given longs array to array of chars.
     *
     * @param array
     *         longs array to convert.
     *
     * @return converted array of chars.
     */
    public static char[] toCharArray(long[] array)
    {
        char[] result = new char[array.length];
        for (int i = 0; i < array.length; i++)
        {
            result[i] = (char) array[i];
        }
        return result;
    }

    /**
     * Coverts given floats array to array of chars.
     *
     * @param array
     *         floats array to convert.
     *
     * @return converted array of chars.
     */
    public static char[] toCharArray(float[] array)
    {
        char[] result = new char[array.length];
        for (int i = 0; i < array.length; i++)
        {
            result[i] = (char) array[i];
        }
        return result;
    }

    /**
     * Coverts given doubles array to array of chars.
     *
     * @param array
     *         doubles array to convert.
     *
     * @return converted array of chars.
     */
    public static char[] toCharArray(double[] array)
    {
        char[] result = new char[array.length];
        for (int i = 0; i < array.length; i++)
        {
            result[i] = (char) array[i];
        }
        return result;
    }

    /**
     * Coverts given booleans array to array of ints.
     *
     * @param array
     *         booleans array to convert.
     *
     * @return converted array of ints.
     */
    public static int[] toIntArray(boolean[] array)
    {
        int[] result = new int[array.length];
        for (int i = 0; i < array.length; i++)
        {
            result[i] = array[i] ? 1 : 0;
        }
        return result;
    }

    /**
     * Coverts given shorts array to array of ints.
     *
     * @param array
     *         shorts array to convert.
     *
     * @return converted array of ints.
     */
    public static int[] toIntArray(short[] array)
    {
        int[] result = new int[array.length];
        for (int i = 0; i < array.length; i++)
        {
            result[i] = (int) array[i];
        }
        return result;
    }

    /**
     * Coverts given chars array to array of ints.
     *
     * @param array
     *         chars array to convert.
     *
     * @return converted array of ints.
     */
    public static int[] toIntArray(char[] array)
    {
        int[] result = new int[array.length];
        for (int i = 0; i < array.length; i++)
        {
            result[i] = (int) array[i];
        }
        return result;
    }

    /**
     * Coverts given bytes array to array of ints.
     *
     * @param array
     *         bytes array to convert.
     *
     * @return converted array of ints.
     */
    public static int[] toIntArray(byte[] array)
    {
        int[] result = new int[array.length];
        for (int i = 0; i < array.length; i++)
        {
            result[i] = (int) array[i];
        }
        return result;
    }

    /**
     * Coverts given longs array to array of ints.
     *
     * @param array
     *         longs array to convert.
     *
     * @return converted array of ints.
     */
    public static int[] toIntArray(long[] array)
    {
        int[] result = new int[array.length];
        for (int i = 0; i < array.length; i++)
        {
            result[i] = (int) array[i];
        }
        return result;
    }

    /**
     * Coverts given floats array to array of ints.
     *
     * @param array
     *         floats array to convert.
     *
     * @return converted array of ints.
     */
    public static int[] toIntArray(float[] array)
    {
        int[] result = new int[array.length];
        for (int i = 0; i < array.length; i++)
        {
            result[i] = (int) array[i];
        }
        return result;
    }

    /**
     * Coverts given doubles array to array of ints.
     *
     * @param array
     *         doubles array to convert.
     *
     * @return converted array of ints.
     */
    public static int[] toIntArray(double[] array)
    {
        int[] result = new int[array.length];
        for (int i = 0; i < array.length; i++)
        {
            result[i] = (int) array[i];
        }
        return result;
    }

    /**
     * Coverts given booleans array to array of longs.
     *
     * @param array
     *         booleans array to convert.
     *
     * @return converted array of longs.
     */
    public static long[] toLongArray(boolean[] array)
    {
        long[] result = new long[array.length];
        for (int i = 0; i < array.length; i++)
        {
            result[i] = array[i] ? 1 : 0;
        }
        return result;
    }

    /**
     * Coverts given shorts array to array of longs.
     *
     * @param array
     *         shorts array to convert.
     *
     * @return converted array of longs.
     */
    public static long[] toLongArray(short[] array)
    {
        long[] result = new long[array.length];
        for (int i = 0; i < array.length; i++)
        {
            result[i] = (long) array[i];
        }
        return result;
    }

    /**
     * Coverts given chars array to array of longs.
     *
     * @param array
     *         chars array to convert.
     *
     * @return converted array of longs.
     */
    public static long[] toLongArray(char[] array)
    {
        long[] result = new long[array.length];
        for (int i = 0; i < array.length; i++)
        {
            result[i] = (long) array[i];
        }
        return result;
    }

    /**
     * Coverts given ints array to array of longs.
     *
     * @param array
     *         ints array to convert.
     *
     * @return converted array of longs.
     */
    public static long[] toLongArray(int[] array)
    {
        long[] result = new long[array.length];
        for (int i = 0; i < array.length; i++)
        {
            result[i] = (long) array[i];
        }
        return result;
    }

    /**
     * Coverts given bytes array to array of longs.
     *
     * @param array
     *         bytes array to convert.
     *
     * @return converted array of longs.
     */
    public static long[] toLongArray(byte[] array)
    {
        long[] result = new long[array.length];
        for (int i = 0; i < array.length; i++)
        {
            result[i] = (long) array[i];
        }
        return result;
    }

    /**
     * Coverts given floats array to array of longs.
     *
     * @param array
     *         floats array to convert.
     *
     * @return converted array of longs.
     */
    public static long[] toLongArray(float[] array)
    {
        long[] result = new long[array.length];
        for (int i = 0; i < array.length; i++)
        {
            result[i] = (long) array[i];
        }
        return result;
    }

    /**
     * Coverts given doubles array to array of longs.
     *
     * @param array
     *         doubles array to convert.
     *
     * @return converted array of longs.
     */
    public static long[] toLongArray(double[] array)
    {
        long[] result = new long[array.length];
        for (int i = 0; i < array.length; i++)
        {
            result[i] = (long) array[i];
        }
        return result;
    }

    /**
     * Coverts given booleans array to array of floats.
     *
     * @param array
     *         booleans array to convert.
     *
     * @return converted array of floats.
     */
    public static float[] toFloatArray(boolean[] array)
    {
        float[] result = new float[array.length];
        for (int i = 0; i < array.length; i++)
        {
            result[i] = array[i] ? 1 : 0;
        }
        return result;
    }

    /**
     * Coverts given shorts array to array of floats.
     *
     * @param array
     *         shorts array to convert.
     *
     * @return converted array of floats.
     */
    public static float[] toFloatArray(short[] array)
    {
        float[] result = new float[array.length];
        for (int i = 0; i < array.length; i++)
        {
            result[i] = (float) array[i];
        }
        return result;
    }

    /**
     * Coverts given chars array to array of floats.
     *
     * @param array
     *         chars array to convert.
     *
     * @return converted array of floats.
     */
    public static float[] toFloatArray(char[] array)
    {
        float[] result = new float[array.length];
        for (int i = 0; i < array.length; i++)
        {
            result[i] = (float) array[i];
        }
        return result;
    }

    /**
     * Coverts given ints array to array of floats.
     *
     * @param array
     *         ints array to convert.
     *
     * @return converted array of floats.
     */
    public static float[] toFloatArray(int[] array)
    {
        float[] result = new float[array.length];
        for (int i = 0; i < array.length; i++)
        {
            result[i] = (float) array[i];
        }
        return result;
    }

    /**
     * Coverts given longs array to array of floats.
     *
     * @param array
     *         longs array to convert.
     *
     * @return converted array of floats.
     */
    public static float[] toFloatArray(long[] array)
    {
        float[] result = new float[array.length];
        for (int i = 0; i < array.length; i++)
        {
            result[i] = (float) array[i];
        }
        return result;
    }

    /**
     * Coverts given bytes array to array of floats.
     *
     * @param array
     *         bytes array to convert.
     *
     * @return converted array of floats.
     */
    public static float[] toFloatArray(byte[] array)
    {
        float[] result = new float[array.length];
        for (int i = 0; i < array.length; i++)
        {
            result[i] = (float) array[i];
        }
        return result;
    }

    /**
     * Coverts given doubles array to array of floats.
     *
     * @param array
     *         doubles array to convert.
     *
     * @return converted array of floats.
     */
    public static float[] toFloatArray(double[] array)
    {
        float[] result = new float[array.length];
        for (int i = 0; i < array.length; i++)
        {
            result[i] = (float) array[i];
        }
        return result;
    }

    /**
     * Coverts given booleans array to array of doubles.
     *
     * @param array
     *         booleans array to convert.
     *
     * @return converted array of doubles.
     */
    public static double[] toDoubleArray(boolean[] array)
    {
        double[] result = new double[array.length];
        for (int i = 0; i < array.length; i++)
        {
            result[i] = array[i] ? 1 : 0;
        }
        return result;
    }

    /**
     * Coverts given shorts array to array of doubles.
     *
     * @param array
     *         shorts array to convert.
     *
     * @return converted array of doubles.
     */
    public static double[] toDoubleArray(short[] array)
    {
        double[] result = new double[array.length];
        for (int i = 0; i < array.length; i++)
        {
            result[i] = (double) array[i];
        }
        return result;
    }

    /**
     * Coverts given chars array to array of doubles.
     *
     * @param array
     *         chars array to convert.
     *
     * @return converted array of doubles.
     */
    public static double[] toDoubleArray(char[] array)
    {
        double[] result = new double[array.length];
        for (int i = 0; i < array.length; i++)
        {
            result[i] = (double) array[i];
        }
        return result;
    }

    /**
     * Coverts given ints array to array of doubles.
     *
     * @param array
     *         ints array to convert.
     *
     * @return converted array of doubles.
     */
    public static double[] toDoubleArray(int[] array)
    {
        double[] result = new double[array.length];
        for (int i = 0; i < array.length; i++)
        {
            result[i] = (double) array[i];
        }
        return result;
    }

    /**
     * Coverts given longs array to array of doubles.
     *
     * @param array
     *         longs array to convert.
     *
     * @return converted array of doubles.
     */
    public static double[] toDoubleArray(long[] array)
    {
        double[] result = new double[array.length];
        for (int i = 0; i < array.length; i++)
        {
            result[i] = (double) array[i];
        }
        return result;
    }

    /**
     * Coverts given floats array to array of doubles.
     *
     * @param array
     *         floats array to convert.
     *
     * @return converted array of doubles.
     */
    public static double[] toDoubleArray(float[] array)
    {
        double[] result = new double[array.length];
        for (int i = 0; i < array.length; i++)
        {
            result[i] = (double) array[i];
        }
        return result;
    }

    /**
     * Coverts given bytes array to array of doubles.
     *
     * @param array
     *         bytes array to convert.
     *
     * @return converted array of doubles.
     */
    public static double[] toDoubleArray(byte[] array)
    {
        double[] result = new double[array.length];
        for (int i = 0; i < array.length; i++)
        {
            result[i] = (double) array[i];
        }
        return result;
    }

    /*
     * Object versions
     */

    /**
     * Coverts given booleans array to array of booleans.
     *
     * @param array
     *         booleans array to convert.
     *
     * @return converted array of booleans.
     */
    public static boolean[] toBooleanArray(Boolean[] array)
    {
        boolean[] result = new boolean[array.length];
        for (int i = 0; i < array.length; i++)
        {
            result[i] = array[i];
        }
        return result;
    }

    /**
     * Coverts given numbers array to array of booleans.
     *
     * @param array
     *         numbers array to convert.
     *
     * @return converted array of booleans.
     */
    public static boolean[] toBooleanArray(Number[] array)
    {
        boolean[] result = new boolean[array.length];
        for (int i = 0; i < array.length; i++)
        {
            result[i] = array[i].doubleValue() > 0;
        }
        return result;
    }

    /**
     * Coverts given bytes array to array of booleans.
     *
     * @param array
     *         bytes array to convert.
     *
     * @return converted array of booleans.
     */
    public static boolean[] toBooleanArray(Byte[] array)
    {
        boolean[] result = new boolean[array.length];
        for (int i = 0; i < array.length; i++)
        {
            result[i] = array[i] > 0;
        }
        return result;
    }

    /**
     * Coverts given shorts array to array of booleans.
     *
     * @param array
     *         shorts array to convert.
     *
     * @return converted array of booleans.
     */
    public static boolean[] toBooleanArray(Short[] array)
    {
        boolean[] result = new boolean[array.length];
        for (int i = 0; i < array.length; i++)
        {
            result[i] = array[i] > 0;
        }
        return result;
    }

    /**
     * Coverts given chars array to array of booleans.
     *
     * @param array
     *         chars array to convert.
     *
     * @return converted array of booleans.
     */
    public static boolean[] toBooleanArray(Character[] array)
    {
        boolean[] result = new boolean[array.length];
        for (int i = 0; i < array.length; i++)
        {
            result[i] = array[i] > 0;
        }
        return result;
    }

    /**
     * Coverts given ints array to array of booleans.
     *
     * @param array
     *         ints array to convert.
     *
     * @return converted array of booleans.
     */
    public static boolean[] toBooleanArray(Integer[] array)
    {
        boolean[] result = new boolean[array.length];
        for (int i = 0; i < array.length; i++)
        {
            result[i] = array[i] > 0;
        }
        return result;
    }

    /**
     * Coverts given longs array to array of booleans.
     *
     * @param array
     *         longs array to convert.
     *
     * @return converted array of booleans.
     */
    public static boolean[] toBooleanArray(Long[] array)
    {
        boolean[] result = new boolean[array.length];
        for (int i = 0; i < array.length; i++)
        {
            result[i] = array[i] > 0;
        }
        return result;
    }

    /**
     * Coverts given floats array to array of booleans.
     *
     * @param array
     *         floats array to convert.
     *
     * @return converted array of booleans.
     */
    public static boolean[] toBooleanArray(Float[] array)
    {
        boolean[] result = new boolean[array.length];
        for (int i = 0; i < array.length; i++)
        {
            result[i] = array[i] > 0;
        }
        return result;
    }

    /**
     * Coverts given doubles array to array of booleans.
     *
     * @param array
     *         doubles array to convert.
     *
     * @return converted array of booleans.
     */
    public static boolean[] toBooleanArray(Double[] array)
    {
        boolean[] result = new boolean[array.length];
        for (int i = 0; i < array.length; i++)
        {
            result[i] = array[i] > 0;
        }
        return result;
    }

    /**
     * Coverts given bytes array to array of bytes.
     *
     * @param array
     *         bytes array to convert.
     *
     * @return converted array of bytes.
     */
    public static byte[] toByteArray(Byte[] array)
    {
        byte[] result = new byte[array.length];
        for (int i = 0; i < array.length; i++)
        {
            result[i] = array[i];
        }
        return result;
    }

    /**
     * Coverts given numbers array to array of bytes.
     *
     * @param array
     *         numbers array to convert.
     *
     * @return converted array of bytes.
     */
    public static byte[] toByteArray(Number[] array)
    {
        byte[] result = new byte[array.length];
        for (int i = 0; i < array.length; i++)
        {
            result[i] = array[i].byteValue();
        }
        return result;
    }

    /**
     * Coverts given booleans array to array of bytes.
     *
     * @param array
     *         booleans array to convert.
     *
     * @return converted array of bytes.
     */
    public static byte[] toByteArray(Boolean[] array)
    {
        byte[] result = new byte[array.length];
        for (int i = 0; i < array.length; i++)
        {
            result[i] = array[i] ? (byte) 1 : (byte) 0;
        }
        return result;
    }

    /**
     * Coverts given shorts array to array of bytes.
     *
     * @param array
     *         shorts array to convert.
     *
     * @return converted array of bytes.
     */
    public static byte[] toByteArray(Short[] array)
    {
        byte[] result = new byte[array.length];
        for (int i = 0; i < array.length; i++)
        {
            result[i] = array[i].byteValue();
        }
        return result;
    }

    /**
     * Coverts given chars array to array of bytes.
     *
     * @param array
     *         chars array to convert.
     *
     * @return converted array of bytes.
     */
    public static byte[] toByteArray(Character[] array)
    {
        byte[] result = new byte[array.length];
        for (int i = 0; i < array.length; i++)
        {
            //noinspection UnnecessaryUnboxing
            result[i] = ((byte) array[i].charValue());
        }
        return result;
    }

    /**
     * Coverts given ints array to array of bytes.
     *
     * @param array
     *         ints array to convert.
     *
     * @return converted array of bytes.
     */
    public static byte[] toByteArray(Integer[] array)
    {
        byte[] result = new byte[array.length];
        for (int i = 0; i < array.length; i++)
        {
            result[i] = array[i].byteValue();
        }
        return result;
    }

    /**
     * Coverts given longs array to array of bytes.
     *
     * @param array
     *         longs array to convert.
     *
     * @return converted array of bytes.
     */
    public static byte[] toByteArray(Long[] array)
    {
        byte[] result = new byte[array.length];
        for (int i = 0; i < array.length; i++)
        {
            result[i] = array[i].byteValue();
        }
        return result;
    }

    /**
     * Coverts given floats array to array of bytes.
     *
     * @param array
     *         floats array to convert.
     *
     * @return converted array of bytes.
     */
    public static byte[] toByteArray(Float[] array)
    {
        byte[] result = new byte[array.length];
        for (int i = 0; i < array.length; i++)
        {
            result[i] = array[i].byteValue();
        }
        return result;
    }

    /**
     * Coverts given doubles array to array of bytes.
     *
     * @param array
     *         doubles array to convert.
     *
     * @return converted array of bytes.
     */
    public static byte[] toByteArray(Double[] array)
    {
        byte[] result = new byte[array.length];
        for (int i = 0; i < array.length; i++)
        {
            result[i] = array[i].byteValue();
        }
        return result;
    }

    /**
     * Coverts given shorts array to array of shorts.
     *
     * @param array
     *         shorts array to convert.
     *
     * @return converted array of shorts.
     */
    public static short[] toShortArray(Short[] array)
    {
        short[] result = new short[array.length];
        for (int i = 0; i < array.length; i++)
        {
            result[i] = array[i];
        }
        return result;
    }

    /**
     * Coverts given numbers array to array of bytes.
     *
     * @param array
     *         numbers array to convert.
     *
     * @return converted array of bytes.
     */
    public static short[] toShortArray(Number[] array)
    {
        short[] result = new short[array.length];
        for (int i = 0; i < array.length; i++)
        {
            result[i] = array[i].shortValue();
        }
        return result;
    }

    /**
     * Coverts given booleans array to array of shorts.
     *
     * @param array
     *         booleans array to convert.
     *
     * @return converted array of shorts.
     */
    public static short[] toShortArray(Boolean[] array)
    {
        short[] result = new short[array.length];
        for (int i = 0; i < array.length; i++)
        {
            result[i] = array[i] ? (short) 1 : (short) 0;
        }
        return result;
    }

    /**
     * Coverts given bytes array to array of shorts.
     *
     * @param array
     *         bytes array to convert.
     *
     * @return converted array of shorts.
     */
    public static short[] toShortArray(Byte[] array)
    {
        short[] result = new short[array.length];
        for (int i = 0; i < array.length; i++)
        {
            result[i] = array[i].shortValue();
        }
        return result;
    }

    /**
     * Coverts given chars array to array of shorts.
     *
     * @param array
     *         chars array to convert.
     *
     * @return converted array of shorts.
     */
    public static short[] toShortArray(Character[] array)
    {
        short[] result = new short[array.length];
        for (int i = 0; i < array.length; i++)
        {
            //noinspection UnnecessaryUnboxing
            result[i] = ((short) array[i].charValue());
        }
        return result;
    }

    /**
     * Coverts given ints array to array of shorts.
     *
     * @param array
     *         ints array to convert.
     *
     * @return converted array of shorts.
     */
    public static short[] toShortArray(Integer[] array)
    {
        short[] result = new short[array.length];
        for (int i = 0; i < array.length; i++)
        {
            result[i] = array[i].shortValue();
        }
        return result;
    }

    /**
     * Coverts given longs array to array of shorts.
     *
     * @param array
     *         longs array to convert.
     *
     * @return converted array of shorts.
     */
    public static short[] toShortArray(Long[] array)
    {
        short[] result = new short[array.length];
        for (int i = 0; i < array.length; i++)
        {
            result[i] = array[i].shortValue();
        }
        return result;
    }

    /**
     * Coverts given floats array to array of shorts.
     *
     * @param array
     *         floats array to convert.
     *
     * @return converted array of shorts.
     */
    public static short[] toShortArray(Float[] array)
    {
        short[] result = new short[array.length];
        for (int i = 0; i < array.length; i++)
        {
            result[i] = array[i].shortValue();
        }
        return result;
    }

    /**
     * Coverts given doubles array to array of shorts.
     *
     * @param array
     *         doubles array to convert.
     *
     * @return converted array of shorts.
     */
    public static short[] toShortArray(Double[] array)
    {
        short[] result = new short[array.length];
        for (int i = 0; i < array.length; i++)
        {
            result[i] = array[i].shortValue();
        }
        return result;
    }

    /**
     * Coverts given chars array to array of chars.
     *
     * @param array
     *         chars array to convert.
     *
     * @return converted array of chars.
     */
    public static char[] toCharArray(Character[] array)
    {
        char[] result = new char[array.length];
        for (int i = 0; i < array.length; i++)
        {
            result[i] = array[i];
        }
        return result;
    }

    /**
     * Coverts given numbers array to array of bytes.
     *
     * @param array
     *         numbers array to convert.
     *
     * @return converted array of bytes.
     */
    public static char[] toCharArray(Number[] array)
    {
        char[] result = new char[array.length];
        for (int i = 0; i < array.length; i++)
        {
            result[i] = (char) array[i].intValue();
        }
        return result;
    }

    /**
     * Coverts given booleans array to array of chars.
     *
     * @param array
     *         booleans array to convert.
     *
     * @return converted array of chars.
     */
    public static char[] toCharArray(Boolean[] array)
    {
        char[] result = new char[array.length];
        for (int i = 0; i < array.length; i++)
        {
            result[i] = array[i] ? (char) 1 : (char) 0;
        }
        return result;
    }

    /**
     * Coverts given shorts array to array of chars.
     *
     * @param array
     *         shorts array to convert.
     *
     * @return converted array of chars.
     */
    public static char[] toCharArray(Short[] array)
    {
        char[] result = new char[array.length];
        for (int i = 0; i < array.length; i++)
        {
            result[i] = (char) array[i].shortValue();
        }
        return result;
    }

    /**
     * Coverts given bytes array to array of chars.
     *
     * @param array
     *         bytes array to convert.
     *
     * @return converted array of chars.
     */
    public static char[] toCharArray(Byte[] array)
    {
        char[] result = new char[array.length];
        for (int i = 0; i < array.length; i++)
        {
            result[i] = (char) array[i].byteValue();
        }
        return result;
    }

    /**
     * Coverts given ints array to array of chars.
     *
     * @param array
     *         ints array to convert.
     *
     * @return converted array of chars.
     */
    public static char[] toCharArray(Integer[] array)
    {
        char[] result = new char[array.length];
        for (int i = 0; i < array.length; i++)
        {
            result[i] = (char) array[i].intValue();
        }
        return result;
    }

    /**
     * Coverts given longs array to array of chars.
     *
     * @param array
     *         longs array to convert.
     *
     * @return converted array of chars.
     */
    public static char[] toCharArray(Long[] array)
    {
        char[] result = new char[array.length];
        for (int i = 0; i < array.length; i++)
        {
            result[i] = (char) array[i].longValue();
        }
        return result;
    }

    /**
     * Coverts given floats array to array of chars.
     *
     * @param array
     *         floats array to convert.
     *
     * @return converted array of chars.
     */
    public static char[] toCharArray(Float[] array)
    {
        char[] result = new char[array.length];
        for (int i = 0; i < array.length; i++)
        {
            result[i] = (char) array[i].floatValue();
        }
        return result;
    }

    /**
     * Coverts given doubles array to array of chars.
     *
     * @param array
     *         doubles array to convert.
     *
     * @return converted array of chars.
     */
    public static char[] toCharArray(Double[] array)
    {
        char[] result = new char[array.length];
        for (int i = 0; i < array.length; i++)
        {
            result[i] = (char) array[i].doubleValue();
        }
        return result;
    }

    /**
     * Coverts given ints array to array of ints.
     *
     * @param array
     *         ints array to convert.
     *
     * @return converted array of ints.
     */
    public static int[] toIntArray(Integer[] array)
    {
        int[] result = new int[array.length];
        for (int i = 0; i < array.length; i++)
        {
            result[i] = array[i];
        }
        return result;
    }

    /**
     * Coverts given numbers array to array of bytes.
     *
     * @param array
     *         numbers array to convert.
     *
     * @return converted array of bytes.
     */
    public static int[] toIntArray(Number[] array)
    {
        int[] result = new int[array.length];
        for (int i = 0; i < array.length; i++)
        {
            result[i] = array[i].intValue();
        }
        return result;
    }

    /**
     * Coverts given booleans array to array of ints.
     *
     * @param array
     *         booleans array to convert.
     *
     * @return converted array of ints.
     */
    public static int[] toIntArray(Boolean[] array)
    {
        int[] result = new int[array.length];
        for (int i = 0; i < array.length; i++)
        {
            result[i] = array[i] ? 1 : 0;
        }
        return result;
    }

    /**
     * Coverts given shorts array to array of ints.
     *
     * @param array
     *         shorts array to convert.
     *
     * @return converted array of ints.
     */
    public static int[] toIntArray(Short[] array)
    {
        int[] result = new int[array.length];
        for (int i = 0; i < array.length; i++)
        {
            result[i] = array[i].intValue();
        }
        return result;
    }

    /**
     * Coverts given chars array to array of ints.
     *
     * @param array
     *         chars array to convert.
     *
     * @return converted array of ints.
     */
    public static int[] toIntArray(Character[] array)
    {
        int[] result = new int[array.length];
        for (int i = 0; i < array.length; i++)
        {
            //noinspection UnnecessaryUnboxing
            result[i] = ((int) array[i].charValue());
        }
        return result;
    }

    /**
     * Coverts given bytes array to array of ints.
     *
     * @param array
     *         bytes array to convert.
     *
     * @return converted array of ints.
     */
    public static int[] toIntArray(Byte[] array)
    {
        int[] result = new int[array.length];
        for (int i = 0; i < array.length; i++)
        {
            result[i] = array[i].intValue();
        }
        return result;
    }

    /**
     * Coverts given longs array to array of ints.
     *
     * @param array
     *         longs array to convert.
     *
     * @return converted array of ints.
     */
    public static int[] toIntArray(Long[] array)
    {
        int[] result = new int[array.length];
        for (int i = 0; i < array.length; i++)
        {
            result[i] = array[i].intValue();
        }
        return result;
    }

    /**
     * Coverts given floats array to array of ints.
     *
     * @param array
     *         floats array to convert.
     *
     * @return converted array of ints.
     */
    public static int[] toIntArray(Float[] array)
    {
        int[] result = new int[array.length];
        for (int i = 0; i < array.length; i++)
        {
            result[i] = array[i].intValue();
        }
        return result;
    }

    /**
     * Coverts given doubles array to array of ints.
     *
     * @param array
     *         doubles array to convert.
     *
     * @return converted array of ints.
     */
    public static int[] toIntArray(Double[] array)
    {
        int[] result = new int[array.length];
        for (int i = 0; i < array.length; i++)
        {
            result[i] = array[i].intValue();
        }
        return result;
    }

    /**
     * Coverts given longs array to array of longs.
     *
     * @param array
     *         longs array to convert.
     *
     * @return converted array of longs.
     */
    public static long[] toLongArray(Long[] array)
    {
        long[] result = new long[array.length];
        for (int i = 0; i < array.length; i++)
        {
            result[i] = array[i];
        }
        return result;
    }

    /**
     * Coverts given numbers array to array of bytes.
     *
     * @param array
     *         numbers array to convert.
     *
     * @return converted array of bytes.
     */
    public static long[] toLongArray(Number[] array)
    {
        long[] result = new long[array.length];
        for (int i = 0; i < array.length; i++)
        {
            result[i] = array[i].longValue();
        }
        return result;
    }

    /**
     * Coverts given booleans array to array of longs.
     *
     * @param array
     *         booleans array to convert.
     *
     * @return converted array of longs.
     */
    public static long[] toLongArray(Boolean[] array)
    {
        long[] result = new long[array.length];
        for (int i = 0; i < array.length; i++)
        {
            result[i] = array[i] ? 1 : 0;
        }
        return result;
    }

    /**
     * Coverts given shorts array to array of longs.
     *
     * @param array
     *         shorts array to convert.
     *
     * @return converted array of longs.
     */
    public static long[] toLongArray(Short[] array)
    {
        long[] result = new long[array.length];
        for (int i = 0; i < array.length; i++)
        {
            result[i] = array[i].longValue();
        }
        return result;
    }

    /**
     * Coverts given chars array to array of longs.
     *
     * @param array
     *         chars array to convert.
     *
     * @return converted array of longs.
     */
    public static long[] toLongArray(Character[] array)
    {
        long[] result = new long[array.length];
        for (int i = 0; i < array.length; i++)
        {
            //noinspection UnnecessaryUnboxing
            result[i] = ((long) array[i].charValue());
        }
        return result;
    }

    /**
     * Coverts given ints array to array of longs.
     *
     * @param array
     *         ints array to convert.
     *
     * @return converted array of longs.
     */
    public static long[] toLongArray(Integer[] array)
    {
        long[] result = new long[array.length];
        for (int i = 0; i < array.length; i++)
        {
            result[i] = array[i].longValue();
        }
        return result;
    }

    /**
     * Coverts given bytes array to array of longs.
     *
     * @param array
     *         bytes array to convert.
     *
     * @return converted array of longs.
     */
    public static long[] toLongArray(Byte[] array)
    {
        long[] result = new long[array.length];
        for (int i = 0; i < array.length; i++)
        {
            result[i] = array[i].longValue();
        }
        return result;
    }

    /**
     * Coverts given floats array to array of longs.
     *
     * @param array
     *         floats array to convert.
     *
     * @return converted array of longs.
     */
    public static long[] toLongArray(Float[] array)
    {
        long[] result = new long[array.length];
        for (int i = 0; i < array.length; i++)
        {
            result[i] = array[i].longValue();
        }
        return result;
    }

    /**
     * Coverts given doubles array to array of longs.
     *
     * @param array
     *         doubles array to convert.
     *
     * @return converted array of longs.
     */
    public static long[] toLongArray(Double[] array)
    {
        long[] result = new long[array.length];
        for (int i = 0; i < array.length; i++)
        {
            result[i] = array[i].longValue();
        }
        return result;
    }

    /**
     * Coverts given floats array to array of floats.
     *
     * @param array
     *         floats array to convert.
     *
     * @return converted array of floats.
     */
    public static float[] toFloatArray(Float[] array)
    {
        float[] result = new float[array.length];
        for (int i = 0; i < array.length; i++)
        {
            result[i] = array[i];
        }
        return result;
    }

    /**
     * Coverts given numbers array to array of bytes.
     *
     * @param array
     *         numbers array to convert.
     *
     * @return converted array of bytes.
     */
    public static float[] toFloatArray(Number[] array)
    {
        float[] result = new float[array.length];
        for (int i = 0; i < array.length; i++)
        {
            result[i] = array[i].floatValue();
        }
        return result;
    }

    /**
     * Coverts given booleans array to array of floats.
     *
     * @param array
     *         booleans array to convert.
     *
     * @return converted array of floats.
     */
    public static float[] toFloatArray(Boolean[] array)
    {
        float[] result = new float[array.length];
        for (int i = 0; i < array.length; i++)
        {
            result[i] = array[i] ? 1 : 0;
        }
        return result;
    }

    /**
     * Coverts given shorts array to array of floats.
     *
     * @param array
     *         shorts array to convert.
     *
     * @return converted array of floats.
     */
    public static float[] toFloatArray(Short[] array)
    {
        float[] result = new float[array.length];
        for (int i = 0; i < array.length; i++)
        {
            result[i] = array[i].floatValue();
        }
        return result;
    }

    /**
     * Coverts given chars array to array of floats.
     *
     * @param array
     *         chars array to convert.
     *
     * @return converted array of floats.
     */
    public static float[] toFloatArray(Character[] array)
    {
        float[] result = new float[array.length];
        for (int i = 0; i < array.length; i++)
        {
            //noinspection UnnecessaryUnboxing
            result[i] = ((float) array[i].charValue());
        }
        return result;
    }

    /**
     * Coverts given ints array to array of floats.
     *
     * @param array
     *         ints array to convert.
     *
     * @return converted array of floats.
     */
    public static float[] toFloatArray(Integer[] array)
    {
        float[] result = new float[array.length];
        for (int i = 0; i < array.length; i++)
        {
            result[i] = array[i].floatValue();
        }
        return result;
    }

    /**
     * Coverts given longs array to array of floats.
     *
     * @param array
     *         longs array to convert.
     *
     * @return converted array of floats.
     */
    public static float[] toFloatArray(Long[] array)
    {
        float[] result = new float[array.length];
        for (int i = 0; i < array.length; i++)
        {
            result[i] = array[i].floatValue();
        }
        return result;
    }

    /**
     * Coverts given bytes array to array of floats.
     *
     * @param array
     *         bytes array to convert.
     *
     * @return converted array of floats.
     */
    public static float[] toFloatArray(Byte[] array)
    {
        float[] result = new float[array.length];
        for (int i = 0; i < array.length; i++)
        {
            result[i] = array[i].floatValue();
        }
        return result;
    }

    /**
     * Coverts given doubles array to array of floats.
     *
     * @param array
     *         doubles array to convert.
     *
     * @return converted array of floats.
     */
    public static float[] toFloatArray(Double[] array)
    {
        float[] result = new float[array.length];
        for (int i = 0; i < array.length; i++)
        {
            result[i] = array[i].floatValue();
        }
        return result;
    }

    /**
     * Coverts given doubles array to array of doubles.
     *
     * @param array
     *         doubles array to convert.
     *
     * @return converted array of doubles.
     */
    public static double[] toDoubleArray(Double[] array)
    {
        double[] result = new double[array.length];
        for (int i = 0; i < array.length; i++)
        {
            result[i] = array[i];
        }
        return result;
    }

    /**
     * Coverts given numbers array to array of bytes.
     *
     * @param array
     *         numbers array to convert.
     *
     * @return converted array of bytes.
     */
    public static double[] toDoubleArray(Number[] array)
    {
        double[] result = new double[array.length];
        for (int i = 0; i < array.length; i++)
        {
            result[i] = array[i].doubleValue();
        }
        return result;
    }

    /**
     * Coverts given booleans array to array of doubles.
     *
     * @param array
     *         booleans array to convert.
     *
     * @return converted array of doubles.
     */
    public static double[] toDoubleArray(Boolean[] array)
    {
        double[] result = new double[array.length];
        for (int i = 0; i < array.length; i++)
        {
            result[i] = array[i] ? 1 : 0;
        }
        return result;
    }

    /**
     * Coverts given shorts array to array of doubles.
     *
     * @param array
     *         shorts array to convert.
     *
     * @return converted array of doubles.
     */
    public static double[] toDoubleArray(Short[] array)
    {
        double[] result = new double[array.length];
        for (int i = 0; i < array.length; i++)
        {
            result[i] = array[i].doubleValue();
        }
        return result;
    }

    /**
     * Coverts given chars array to array of doubles.
     *
     * @param array
     *         chars array to convert.
     *
     * @return converted array of doubles.
     */
    public static double[] toDoubleArray(Character[] array)
    {
        double[] result = new double[array.length];
        for (int i = 0; i < array.length; i++)
        {
            //noinspection UnnecessaryUnboxing
            result[i] = ((double) array[i].charValue());
        }
        return result;
    }

    /**
     * Coverts given ints array to array of doubles.
     *
     * @param array
     *         ints array to convert.
     *
     * @return converted array of doubles.
     */
    public static double[] toDoubleArray(Integer[] array)
    {
        double[] result = new double[array.length];
        for (int i = 0; i < array.length; i++)
        {
            result[i] = array[i].doubleValue();
        }
        return result;
    }

    /**
     * Coverts given longs array to array of doubles.
     *
     * @param array
     *         longs array to convert.
     *
     * @return converted array of doubles.
     */
    public static double[] toDoubleArray(Long[] array)
    {
        double[] result = new double[array.length];
        for (int i = 0; i < array.length; i++)
        {
            result[i] = array[i].doubleValue();
        }
        return result;
    }

    /**
     * Coverts given floats array to array of doubles.
     *
     * @param array
     *         floats array to convert.
     *
     * @return converted array of doubles.
     */
    public static double[] toDoubleArray(Float[] array)
    {
        double[] result = new double[array.length];
        for (int i = 0; i < array.length; i++)
        {
            result[i] = array[i].doubleValue();
        }
        return result;
    }

    /**
     * Coverts given bytes array to array of doubles.
     *
     * @param array
     *         bytes array to convert.
     *
     * @return converted array of doubles.
     */
    public static double[] toDoubleArray(Byte[] array)
    {
        double[] result = new double[array.length];
        for (int i = 0; i < array.length; i++)
        {
            result[i] = array[i].doubleValue();
        }
        return result;
    }

    /**
     * Sum all numbers from array.
     *
     * @param array
     *         array to sum.
     *
     * @return sum of all elements in given array.
     */
    public static byte sum(byte[] array)
    {
        byte sum = 0;
        for (byte x : array)
        {
            sum += x;
        }
        return sum;
    }

    /**
     * Sum all numbers from array.
     *
     * @param array
     *         array to sum.
     *
     * @return sum of all elements in given array.
     */
    public static int sumToInt(byte[] array)
    {
        int sum = 0;
        for (byte x : array)
        {
            sum += x;
        }
        return sum;
    }

    /**
     * Sum all numbers from array.
     *
     * @param array
     *         array to sum.
     *
     * @return sum of all elements in given array.
     */
    public static long sumToLong(byte[] array)
    {
        long sum = 0;
        for (byte x : array)
        {
            sum += x;
        }
        return sum;
    }

    /**
     * Sum all numbers from array.
     *
     * @param array
     *         array to sum.
     *
     * @return sum of all elements in given array.
     */
    public static char sum(char[] array)
    {
        char sum = 0;
        for (char x : array)
        {
            sum += x;
        }
        return sum;
    }

    /**
     * Sum all numbers from array.
     *
     * @param array
     *         array to sum.
     *
     * @return sum of all elements in given array.
     */
    public static int sumToInt(char[] array)
    {
        int sum = 0;
        for (char x : array)
        {
            sum += x;
        }
        return sum;
    }

    /**
     * Sum all numbers from array.
     *
     * @param array
     *         array to sum.
     *
     * @return sum of all elements in given array.
     */
    public static long sumToLong(char[] array)
    {
        long sum = 0;
        for (char x : array)
        {
            sum += x;
        }
        return sum;
    }

    /**
     * Sum all numbers from array.
     *
     * @param array
     *         array to sum.
     *
     * @return sum of all elements in given array.
     */
    public static int sum(int[] array)
    {
        int sum = 0;
        for (int x : array)
        {
            sum += x;
        }
        return sum;
    }

    /**
     * Sum all numbers from array.
     *
     * @param array
     *         array to sum.
     *
     * @return sum of all elements in given array.
     */
    public static long sumToLong(int[] array)
    {
        long sum = 0;
        for (int x : array)
        {
            sum += x;
        }
        return sum;
    }

    /**
     * Sum all numbers from array.
     *
     * @param array
     *         array to sum.
     *
     * @return sum of all elements in given array.
     */
    public static long sum(long[] array)
    {
        long sum = 0;
        for (long x : array)
        {
            sum += x;
        }
        return sum;
    }

    /**
     * Sum all numbers from array.
     *
     * @param array
     *         array to sum.
     *
     * @return sum of all elements in given array.
     */
    public static float sum(float[] array)
    {
        float sum = 0;
        for (float x : array)
        {
            sum += x;
        }
        return sum;
    }

    /**
     * Sum all numbers from array.
     *
     * @param array
     *         array to sum.
     *
     * @return sum of all elements in given array.
     */
    public static double sumToDouble(float[] array)
    {
        double sum = 0;
        for (float x : array)
        {
            sum += x;
        }
        return sum;
    }

    /**
     * Sum all numbers from array.
     *
     * @param array
     *         array to sum.
     *
     * @return sum of all elements in given array.
     */
    public static double sum(double[] array)
    {
        double sum = 0;
        for (double x : array)
        {
            sum += x;
        }
        return sum;
    }

    private static <T> void copy(T[] array, T[] arrayA, T[] arrayB, boolean append)
    {
        if (append)
        {
            System.arraycopy(arrayA, 0, array, 0, arrayA.length);
            System.arraycopy(arrayB, 0, array, arrayA.length, arrayB.length);
        }
        else
        {
            System.arraycopy(arrayB, 0, array, 0, arrayB.length);
            System.arraycopy(arrayA, 0, array, arrayB.length, arrayA.length);
        }
    }

    private static void copy(boolean[] array, boolean[] arrayA, boolean[] arrayB, boolean append)
    {
        if (append)
        {
            System.arraycopy(arrayA, 0, array, 0, arrayA.length);
            System.arraycopy(arrayB, 0, array, arrayA.length, arrayB.length);
        }
        else
        {
            System.arraycopy(arrayB, 0, array, 0, arrayB.length);
            System.arraycopy(arrayA, 0, array, arrayB.length, arrayA.length);
        }
    }

    private static void copy(byte[] array, byte[] arrayA, byte[] arrayB, boolean append)
    {
        if (append)
        {
            System.arraycopy(arrayA, 0, array, 0, arrayA.length);
            System.arraycopy(arrayB, 0, array, arrayA.length, arrayB.length);
        }
        else
        {
            System.arraycopy(arrayB, 0, array, 0, arrayB.length);
            System.arraycopy(arrayA, 0, array, arrayB.length, arrayA.length);
        }
    }

    private static void copy(char[] array, char[] arrayA, char[] arrayB, boolean append)
    {
        if (append)
        {
            System.arraycopy(arrayA, 0, array, 0, arrayA.length);
            System.arraycopy(arrayB, 0, array, arrayA.length, arrayB.length);
        }
        else
        {
            System.arraycopy(arrayB, 0, array, 0, arrayB.length);
            System.arraycopy(arrayA, 0, array, arrayB.length, arrayA.length);
        }
    }

    private static void copy(short[] array, short[] arrayA, short[] arrayB, boolean append)
    {
        if (append)
        {
            System.arraycopy(arrayA, 0, array, 0, arrayA.length);
            System.arraycopy(arrayB, 0, array, arrayA.length, arrayB.length);
        }
        else
        {
            System.arraycopy(arrayB, 0, array, 0, arrayB.length);
            System.arraycopy(arrayA, 0, array, arrayB.length, arrayA.length);
        }
    }

    private static void copy(int[] array, int[] arrayA, int[] arrayB, boolean append)
    {
        if (append)
        {
            System.arraycopy(arrayA, 0, array, 0, arrayA.length);
            System.arraycopy(arrayB, 0, array, arrayA.length, arrayB.length);
        }
        else
        {
            System.arraycopy(arrayB, 0, array, 0, arrayB.length);
            System.arraycopy(arrayA, 0, array, arrayB.length, arrayA.length);
        }

    }

    private static void copy(long[] array, long[] arrayA, long[] arrayB, boolean append)
    {
        if (append)
        {
            System.arraycopy(arrayA, 0, array, 0, arrayA.length);
            System.arraycopy(arrayB, 0, array, arrayA.length, arrayB.length);
        }
        else
        {
            System.arraycopy(arrayB, 0, array, 0, arrayB.length);
            System.arraycopy(arrayA, 0, array, arrayB.length, arrayA.length);
        }
    }

    private static void copy(float[] array, float[] arrayA, float[] arrayB, boolean append)
    {
        if (append)
        {
            System.arraycopy(arrayA, 0, array, 0, arrayA.length);
            System.arraycopy(arrayB, 0, array, arrayA.length, arrayB.length);
        }
        else
        {
            System.arraycopy(arrayB, 0, array, 0, arrayB.length);
            System.arraycopy(arrayA, 0, array, arrayB.length, arrayA.length);
        }
    }

    private static void copy(double[] array, double[] arrayA, double[] arrayB, boolean append)
    {
        if (append)
        {
            System.arraycopy(arrayA, 0, array, 0, arrayA.length);
            System.arraycopy(arrayB, 0, array, arrayA.length, arrayB.length);
        }
        else
        {
            System.arraycopy(arrayB, 0, array, 0, arrayB.length);
            System.arraycopy(arrayA, 0, array, arrayB.length, arrayA.length);
        }
    }
}
