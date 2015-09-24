package org.diorite.utils.function;

@FunctionalInterface
public interface ObjIntFunction<R, V>
{

    /**
     * Applies this function to the given argument.
     *
     * @param i the function argument
     * @param v the function argument
     *
     * @return the function result
     */
    R apply(int i, V v);
}
