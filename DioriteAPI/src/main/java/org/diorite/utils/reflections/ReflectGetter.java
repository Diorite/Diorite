package org.diorite.utils.reflections;

import java.lang.reflect.Type;

/**
 * Classes implementing this interface can get something from given object.
 */
public interface ReflectGetter<E>
{
    /**
     * Get value from given object.
     *
     * @param src object to get value from it.
     *
     * @return value from object.
     */
    E get(Object src);


    /**
     * @return generic type.
     */
    Type getGenericType();
}
