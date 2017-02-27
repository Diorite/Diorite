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

package org.diorite.commons.enums;

import java.lang.StackWalker.Option;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.stream.Collectors;

/**
 * Base class for creating dynamic enums, new elements can be added to dynamic enums, but not removed/changed.
 *
 * @param <T>
 *         type of enum.
 */
@SuppressWarnings({"unchecked", "rawtypes"})
public abstract class DynamicEnum<T extends DynamicEnum<T>> implements Comparable<T>
{
    private static final StackWalker walker = StackWalker.getInstance(Option.RETAIN_CLASS_REFERENCE);

    int    ordinal = - 1;
    String name    = "\0";

    @SuppressWarnings("StringEquality")
    final void validate()
    {
        if ((this.ordinal == - 1) || (this.name == "\0"))
        {
            throw new IllegalStateException("Unregistered enum element: " + this.getClass() + ", " + this.name + ", " + this.ordinal);
        }
    }

    protected DynamicEnum()
    {
        Class<T> enumType = this.getDeclaringClass0();
        DynamicEnumType<T> dynamicEnumType = DynamicEnumType.getDynamicEnumType(enumType);
        switch (ValueType.findType(walker.walk(s -> s.skip(2).limit(2).collect(Collectors.toList())), enumType, this.getClass()))
        {
            case DYNAMIC:
            case DYNAMIC_EXTENDED:
                return;
            case NORMAL:
            case EXTENDED:
            {
                this.ordinal = dynamicEnumType.counter++;
                int index = 0;
                for (Field field : enumType.getDeclaredFields())
                {
                    if (Modifier.isStatic(field.getModifiers()) && Modifier.isFinal(field.getModifiers()) && (field.getType() == enumType))
                    {
                        if (index++ == this.ordinal)
                        {
                            this.name = field.getName();
                            dynamicEnumType.addElement((T) this);
                            return;
                        }
                    }
                }
                throw new IllegalStateException("Can't find enum value declaration");
            }
        }
    }

    /**
     * Returns ordinal of this enum element.
     *
     * @return ordinal of this enum element.
     */
    public final int ordinal()
    {
        this.validate();
        return this.ordinal;
    }

    /**
     * Returns name of this enum element.
     *
     * @return name of this enum element.
     */
    public final String name()
    {
        this.validate();
        return this.name;
    }

    /**
     * Returns the Class object corresponding to this enum constant's enum type. Two enum constants e1 and  e2 are of the same enum type if and only if
     * e1.getDeclaringClass() == e2.getDeclaringClass(). (The value returned by this method may differ from the one returned by the {@link Object#getClass}
     * method for enum constants with constant-specific class bodies.)
     *
     * @return the Class object corresponding to this enum constant's enum type
     */
    public final Class<T> getDeclaringClass()
    {
        this.validate();
        return this.getDeclaringClass0();
    }

    private Class<T> getDeclaringClass0()
    {
        Class<?> clazz = this.getClass();
        Class<?> superClass = clazz.getSuperclass();
        return (superClass.getSuperclass() == DynamicEnum.class) ? (Class<T>) superClass : (Class<T>) clazz;
    }

    /**
     * Compares this enum with the specified object for order.  Returns a
     * negative integer, zero, or a positive integer as this object is less
     * than, equal to, or greater than the specified object.
     *
     * Enum constants are only comparable to other enum constants of the
     * same enum type.  The natural order implemented by this
     * method is the order in which the constants are declared.
     */
    @Override
    public final int compareTo(T o)
    {
        this.validate();
        DynamicEnum<T> self = this;
        if ((self.getClass() != ((DynamicEnum<?>) o).getClass()) && // optimization
            (self.getDeclaringClass() != ((DynamicEnum<?>) o).getDeclaringClass()))
        {
            throw new ClassCastException();
        }
        o.validate();
        return self.ordinal - o.ordinal;
    }

    /**
     * Returns the name of this enum constant, as contained in the
     * declaration.  This method may be overridden, though it typically
     * isn't necessary or desirable.  An enum type should override this
     * method when a more "programmer-friendly" string form exists.
     *
     * @return the name of this enum constant
     */
    public String toString()
    {
        this.validate();
        return this.name;
    }

    /**
     * Returns true if the specified object is equal to this
     * enum constant.
     *
     * @param other
     *         the object to be compared for equality with this object.
     *
     * @return true if the specified object is equal to this enum constant.
     */
    public final boolean equals(Object other)
    {
        this.validate();
        if (other instanceof DynamicEnum)
        {
            ((DynamicEnum) other).validate();
            return this == other;
        }
        else
        {
            return false;
        }
    }

    /**
     * Returns a hash code for this enum constant.
     *
     * @return a hash code for this enum constant.
     */
    public final int hashCode()
    {
        this.validate();
        return super.hashCode();
    }

    /**
     * Throws CloneNotSupportedException.  This guarantees that enums
     * are never cloned, which is necessary to preserve their "singleton"
     * status.
     *
     * @return (never returns)
     */
    @Override
    protected final Object clone() throws CloneNotSupportedException
    {
        throw new CloneNotSupportedException();
    }

    /**
     * enum classes cannot have finalize methods.
     */
    @Override
    protected final void finalize() {}

    /**
     * Returns array of values of given enum.
     *
     * @param enumType
     *         type of enum.
     * @param <T>
     *         type og enum.
     *
     * @return array of values of given enum.
     */
    public static <T extends DynamicEnum<T>> T[] values(Class<T> enumType)
    {
        return DynamicEnumType.getDynamicEnumType(enumType).values.clone();
    }

    /**
     * Adds new enum element to this dynamic enum.
     *
     * @param enumValue
     *         enum value.
     * @param name
     *         name of enum value.
     * @param <T>
     *         type of enum.
     *
     * @return ordinal value of enum.
     */
    public static <T extends DynamicEnum<T>> int addEnumElement(String name, T enumValue)
    {
        Class<T> enumValueClass = (Class<T>) enumValue.getClass();
        while (enumValueClass.getSuperclass() != DynamicEnum.class)
        {
            enumValueClass = (Class<T>) enumValueClass.getSuperclass();
        }
        DynamicEnumType<T> dynamicEnumType = DynamicEnumType.getDynamicEnumType(enumValueClass);
        dynamicEnumType.addElement(enumValue, name);
        return enumValue.ordinal;
    }

    /**
     * Returns the enum constant of the specified enum type with the
     * specified name.
     * The name must match exactly an identifier used
     * to declare an enum constant in this type.
     *
     * @param <T>
     *         The enum type whose constant is to be returned
     * @param enumType
     *         the {@code Class} object of the enum type from which to return a constant
     * @param name
     *         the name of the constant to return
     *
     * @return the enum constant of the specified enum type with the specified name
     *
     * @throws IllegalArgumentException
     *         if the specified enum type has no constant with the specified name, or the specified class object does not represent an enum type
     */
    public static <T extends DynamicEnum<T>> T valueOf(Class<T> enumType, String name)
    {
        T result = DynamicEnumType.getDynamicEnumType(enumType).elementsMap.get(name);
        if (result != null)
        {
            return result;
        }
        throw new IllegalArgumentException("No enum constant " + enumType.getCanonicalName() + "." + name);
    }

}
