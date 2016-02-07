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

package org.diorite.utils.concurrent.atomic;

import java.util.Iterator;
import java.util.function.BinaryOperator;
import java.util.function.UnaryOperator;

/**
 * Represent atomic array of given type.
 *
 * @param <E> type of array.
 */
public interface AtomicArray<E> extends Iterable<E>
{
    int SHIFT_BASE = 31;

    /**
     * Returns the length of the array.
     *
     * @return the length of the array
     */
    int length();

    /**
     * Returns the offset of the array part.
     * It will be 0 for base(full) array.
     *
     * @return the offset of the array
     */
    int offset();

    /**
     * Gets the current value at position {@code i}.
     *
     * @param i the index
     *
     * @return the current value
     */
    E get(int i);

    /**
     * Sets the element at position {@code i} to the given value.
     *
     * @param i        the index
     * @param newValue the new value
     */
    void set(int i, E newValue);

    /**
     * Eventually sets the element at position {@code i} to the given value.
     *
     * @param i        the index
     * @param newValue the new value
     */
    void lazySet(int i, E newValue);

    /**
     * Atomically sets the element at position {@code i} to the given
     * value and returns the old value.
     *
     * @param i        the index
     * @param newValue the new value
     *
     * @return the previous value
     */
    E getAndSet(int i, E newValue);

    /**
     * Atomically sets the element at position {@code i} to the given
     * updated value if the current value {@code ==} the expected value.
     *
     * @param i      the index
     * @param expect the expected value
     * @param update the new value
     *
     * @return {@code true} if successful. False return indicates that
     * the actual value was not equal to the expected value.
     */
    boolean compareAndSet(int i, E expect, E update);

    /**
     * Atomically sets the element at position {@code i} to the given
     * updated value if the current value {@code ==} the expected value.
     * <br>
     * <a href="package-summary.html#weakCompareAndSet">May fail
     * spuriously and does not provide ordering guarantees</a>, so is
     * only rarely an appropriate alternative to {@code compareAndSet}.
     *
     * @param i      the index
     * @param expect the expected value
     * @param update the new value
     *
     * @return {@code true} if successful
     */
    boolean weakCompareAndSet(int i, E expect, E update);

    /**
     * Atomically updates the element at index {@code i} with the results
     * of applying the given function, returning the previous value. The
     * function should be side-effect-free, since it may be re-applied
     * when attempted updates fail due to contention among threads.
     *
     * @param i              the index
     * @param updateFunction a side-effect-free function
     *
     * @return the previous value
     */
    E getAndUpdate(int i, UnaryOperator<E> updateFunction);

    /**
     * Atomically updates the element at index {@code i} with the results
     * of applying the given function, returning the updated value. The
     * function should be side-effect-free, since it may be re-applied
     * when attempted updates fail due to contention among threads.
     *
     * @param i              the index
     * @param updateFunction a side-effect-free function
     *
     * @return the updated value
     */
    E updateAndGet(int i, UnaryOperator<E> updateFunction);

    /**
     * Atomically updates the element at index {@code i} with the
     * results of applying the given function to the current and
     * given values, returning the previous value. The function should
     * be side-effect-free, since it may be re-applied when attempted
     * updates fail due to contention among threads.  The function is
     * applied with the current value at index {@code i} as its first
     * argument, and the given update as the second argument.
     *
     * @param i                   the index
     * @param x                   the update value
     * @param accumulatorFunction a side-effect-free function of two arguments
     *
     * @return the previous value
     */
    E getAndAccumulate(int i, E x, BinaryOperator<E> accumulatorFunction);

    /**
     * Atomically updates the element at index {@code i} with the
     * results of applying the given function to the current and
     * given values, returning the updated value. The function should
     * be side-effect-free, since it may be re-applied when attempted
     * updates fail due to contention among threads.  The function is
     * applied with the current value at index {@code i} as its first
     * argument, and the given update as the second argument.
     *
     * @param i                   the index
     * @param x                   the update value
     * @param accumulatorFunction a side-effect-free function of two arguments
     *
     * @return the updated value
     */
    E accumulateAndGet(int i, E x, BinaryOperator<E> accumulatorFunction);

    /**
     * Get sub-array of this array, elements of sub-array are indexed,
     * from 0, like in new array.
     * <br>
     * sub-array is only wrapper for this array, so all changes are made,
     * in this object, but offset is added to every index used in sub array.
     *
     * @param offset offset of sub-array.
     * @param length size of sub-array.
     *
     * @return sub-array of this array.
     */
    AtomicArray<E> getSubArray(int offset, int length);

    /**
     * Get sub-array of this array, elements of sub-array are indexed,
     * from 0, like in new array.
     * <br>
     * sub-array is only wrapper for this array, so all changes are made,
     * in this object, but offset is added to every index used in sub array.
     *
     * @param offset offset of sub-array.
     *
     * @return sub-array of this array.
     */
    default AtomicArray<E> getSubArray(final int offset)
    {
        return this.getSubArray(offset, this.length() - offset);
    }

    /**
     * Return all elements of atomic array as normal array.
     *
     * @param a   array to use.
     * @param <T> type of array.
     *
     * @return array with all elements.
     */
    <T> T[] toArray(T[] a);

    /**
     * Return all elements of atomic array as normal array.
     *
     * @return array with all elements.
     */
    Object[] toArray();

    /**
     * Returns the String representation of the current values of array.
     *
     * @return the String representation of the current values of array
     */
    String toString();

    @Override
    default Iterator<E> iterator()
    {
        return new Iterator<E>()
        {
            private int currentIndex = 0;

            @Override
            public boolean hasNext()
            {
                return (this.currentIndex < AtomicArray.this.length());
            }

            @SuppressWarnings("IteratorNextCanNotThrowNoSuchElementException") // it will be thrown by array itself.
            @Override
            public E next()
            {
                return AtomicArray.this.get(this.currentIndex++);
            }

            @Override
            public void remove()
            {
                throw new UnsupportedOperationException();
            }
        };
    }
}
