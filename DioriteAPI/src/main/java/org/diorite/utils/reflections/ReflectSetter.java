package org.diorite.utils.reflections;

@FunctionalInterface
/**
 * Classes implementing this interface can set given object in other given object.
 */
public interface ReflectSetter<E>
{
    /**
     * Set value in given object.
     * @param src object to set value in it.
     * @param obj new value.
     */
    void set(Object src, Object obj);
}
