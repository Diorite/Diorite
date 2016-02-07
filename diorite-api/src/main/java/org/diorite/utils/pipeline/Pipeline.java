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

package org.diorite.utils.pipeline;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * Interface representing pipeline where every element have name.
 * Names don't needs to be unique, but it's easier and safer to
 * keep names unique.
 * <br>
 * Interface contains many method allowing to inserting elements
 * at specific positions, like before other element, or after it.
 * Or just add new element on the end or beginning of pipeline.
 * <br>
 * If there is more than one element with some name, people may
 * need use indexes, that are allowing to select elements to edit.
 * <br>
 * Due to incompatybilites with {@link Collection} and {@link java.util.Map}
 * pipeline don't extends them, only {@link Iterable} is compatybile with it.
 *
 * @param <E> type of elements stored in pipeline.
 */
public interface Pipeline<E> extends Iterable<E>
{
    /**
     * @return collection with all values
     */
    Collection<E> toCollection();

    /**
     * @return collection with all names
     */
    Collection<String> toNamesCollection();

    /**
     * @return all elements from pipeline as set of entries with name and value.
     */
    Set<Entry<String, E>> entrySet();

    /**
     * Remove element from pipeline head.
     *
     * @return removed element or null if pipeline was empty.
     */
    E removeFirst();

    /**
     * Remove element from pipeline tail.
     *
     * @return removed element or null if pipeline was empty.
     */
    E removeLast();

    /**
     * Method to get iterator of names and values of all elements of pipeline using entries.
     *
     * @return iterator of pipeline, from head to tail.
     */
    Iterator<Entry<String, E>> entriesIterator();

    /**
     * Method to get iterator of names and values of all elements of pipeline using entries.
     *
     * @return iterator of pipeline, from tail to head.
     */
    Iterator<Entry<String, E>> entriesDescendingIterator();

    /**
     * Remove all elements from pipeline.
     */
    void clear();

    /**
     * inverted iterator.
     *
     * @return iterator of pipeline, from tail to head.
     *
     * @see Iterable#iterator()
     */
    Iterator<E> descendingIterator();

    /**
     * Method will look for {@code index} pipeline element named as in {@code element} param,
     * and change it name and value to given params.
     * use 0 as index to use first found element.
     * <br>
     * Index is decremented each time when iterator find element with matching name,
     * when index is equals to 0 iterator stops and change name and value of current element.
     * <br>
     * Method is iterating from tail to head of pipeline.
     * <br>
     * Use this same value as element and name to just change value of element.
     * <br>
     * If there is no element with given name, method will just return false,
     * and no changes will be made.
     *
     * @param element name of element to find.
     * @param name    name to set.
     * @param value   value to set.
     * @param index   index of element.
     *
     * @return true if any element was changed.
     *
     * @see #setFromTail(String, Object)
     * @see #setFromTail(String, Object, int)
     * @see #setIfContainsFromTail(String, Object)
     * @see #setIfContainsFromTail(String, Object, int)
     * @see #replaceFromTail(String, String, Object)
     * @see #replaceFromTail(String, String, Object, int)
     * @see #replaceIfContainsFromTail(String, String, Object)
     * @see #replaceIfContainsFromTail(String, String, Object, int)
     */
    boolean linkInsteadFromTail(String element, String name, E value, int index);

    /**
     * Method will look for {@code index} pipeline element named as in {@code element} param,
     * and change it name and value to given params.
     * use 0 as index to use first found element.
     * <br>
     * Index is decremented each time when iterator find element with matching name,
     * when index is equals to 0 iterator stops and change name and value of current element.
     * <br>
     * Method is iterating from head to tail of pipeline.
     * <br>
     * Use this same value as element and name to just change value of element.
     * <br>
     * If there is no element with given name, method will just return false,
     * and no changes will be made.
     *
     * @param element name of element to find.
     * @param name    name to set.
     * @param value   value to set.
     * @param index   index of element.
     *
     * @return true if any element was changed.
     *
     * @see #set(String, Object)
     * @see #set(String, Object, int)
     * @see #setFromHead(String, Object)
     * @see #setFromHead(String, Object, int)
     * @see #setIfContains(String, Object)
     * @see #setIfContains(String, Object, int)
     * @see #setIfContainsFromHead(String, Object)
     * @see #setIfContainsFromHead(String, Object, int)
     * @see #replace(String, String, Object)
     * @see #replace(String, String, Object, int)
     * @see #replaceFromHead(String, String, Object)
     * @see #replaceFromHead(String, String, Object, int)
     * @see #replaceIfContainsFromHead(String, String, Object)
     * @see #replaceIfContainsFromHead(String, String, Object, int)
     */
    boolean linkInsteadFromHead(String element, String name, E value, int index);

    /**
     * Method will look for {@code index} pipeline element named as in {@code after} param,
     * and add new element with given name and value AFTER it.
     * <br>
     * Index is decremented each time when iterator find element with matching name,
     * when index is equals to 0 iterator stops and add new element AFTER current one.
     * <br>
     * Method is iterating from head to tail of pipeline.
     * <br>
     * If there is no element with given name, method will just return false,
     * and no changes will be made.
     *
     * @param after name of relative element.
     * @param name  name of new element.
     * @param value value of new element.
     * @param index index of relative element.
     *
     * @return true if any element was added.
     *
     * @see #addAfter(String, String, Object)
     * @see #addAfter(String, String, Object, int)
     * @see #addAfterFromHead(String, String, Object)
     * @see #addAfterFromHead(String, String, Object, int)
     * @see #addAfterIfContains(String, String, Object)
     * @see #addAfterIfContains(String, String, Object, int)
     * @see #addAfterIfContainsFromHead(String, String, Object)
     * @see #addAfterIfContainsFromHead(String, String, Object, int)
     */
    boolean linkAfterFromHead(String after, String name, E value, int index);

    /**
     * Method will look for {@code index} pipeline element named as in {@code after} param,
     * and add new element with given name and value AFTER it.
     * <br>
     * Index is decremented each time when iterator find element with matching name,
     * when index is equals to 0 iterator stops and add new element AFTER current one.
     * <br>
     * Method is iterating from tail to head of pipeline.
     * <br>
     * If there is no element with given name, method will just return false,
     * and no changes will be made.
     *
     * @param after name of relative element.
     * @param name  name of new element.
     * @param value value of new element.
     * @param index index of relative element.
     *
     * @return true if any element was added.
     *
     * @see #addAfterFromTail(String, String, Object)
     * @see #addAfterFromTail(String, String, Object, int)
     * @see #addAfterIfContainsFromTail(String, String, Object)
     * @see #addAfterIfContainsFromTail(String, String, Object, int)
     */
    boolean linkAfterFromTail(String after, String name, E value, int index);

    /**
     * Method will look for {@code index} pipeline element named as in {@code before} param,
     * and add new element with given name and value BEFORE it.
     * <br>
     * Index is decremented each time when iterator find element with matching name,
     * when index is equals to 0 iterator stops and add new element BEFORE current one.
     * <br>
     * Method is iterating from head to tail of pipeline.
     * <br>
     * If there is no element with given name, method will just return false,
     * and no changes will be made.
     *
     * @param before name of relative element.
     * @param name   name of new element.
     * @param value  value of new element.
     * @param index  index of relative element.
     *
     * @return true if any element was added.
     *
     * @see #addBefore(String, String, Object)
     * @see #addBefore(String, String, Object, int)
     * @see #addBeforeFromHead(String, String, Object)
     * @see #addBeforeFromHead(String, String, Object, int)
     * @see #addBeforeIfContains(String, String, Object)
     * @see #addBeforeIfContains(String, String, Object, int)
     * @see #addBeforeIfContainsFromHead(String, String, Object)
     * @see #addBeforeIfContainsFromHead(String, String, Object, int)
     */
    boolean linkBeforeFromHead(String before, String name, E value, int index);

    /**
     * Method will look for {@code index} pipeline element named as in {@code before} param,
     * and add new element with given name and value BEFORE it.
     * <br>
     * Index is decremented each time when iterator find element with matching name,
     * when index is equals to 0 iterator stops and add new element BEFORE current one.
     * <br>
     * Method is iterating from tail to head of pipeline.
     * <br>
     * If there is no element with given name, method will just return false,
     * and no changes will be made.
     *
     * @param before name of relative element.
     * @param name   name of new element.
     * @param value  value of new element.
     * @param index  index of relative element.
     *
     * @return true if any element was added.
     *
     * @see #addBeforeFromTail(String, String, Object)
     * @see #addBeforeFromTail(String, String, Object, int)
     * @see #addBeforeIfContainsFromTail(String, String, Object)
     * @see #addBeforeIfContainsFromTail(String, String, Object, int)
     */
    boolean linkBeforeFromTail(String before, String name, E value, int index);

    /**
     * Method will look for {@code index} pipeline element with value equals to given param,
     * and remove it.
     * <br>
     * Index is decremented each time when iterator find element with matching name,
     * when index is equals to 0 iterator stops and remove current element.
     * <br>
     * Method is iterating from tail to head of pipeline.
     * <br>
     * If there is no element with given name, method will just return null,
     * and no changes will be made.
     *
     * @param o     element to remove from pipeline.
     * @param index index of element to remove.
     *
     * @return name of removed element.
     *
     * @see #removeLastOccurrence(Object)
     */
    String removeSingleOccurrenceFromTail(Object o, int index);

    /**
     * Method will look for first pipeline element with given value,
     * and remove it.
     * <br>
     * Method is iterating from tail to head of pipeline.
     * <br>
     * If there is no element with given name, method will just return null,
     * and no changes will be made.
     *
     * @param o element to remove from pipeline.
     *
     * @return name of removed element.
     *
     * @see #removeSingleOccurrenceFromTail(Object, int)
     */
    String removeLastOccurrence(Object o);

    /**
     * Method will look for {@code index} pipeline element with value equals to given param,
     * and remove it.
     * <br>
     * Index is decremented each time when iterator find element with matching name,
     * when index is equals to 0 iterator stops and remove current element.
     * <br>
     * Method is iterating from head to tail of pipeline.
     * <br>
     * If there is no element with given name, method will just return null,
     * and no changes will be made.
     *
     * @param o     element to remove from pipeline.
     * @param index index of element to remove.
     *
     * @return name of removed element.
     *
     * @see #removeFirstOccurrence(Object)
     */
    String removeSingleOccurrenceFromHead(Object o, int index);

    /**
     * Method will look for first pipeline element named as in {@code before} param,
     * and remove it.
     * <br>
     * Method is iterating from head to rail of pipeline.
     * <br>
     * If there is no element with given name, method will just return null,
     * and no changes will be made.
     *
     * @param o element to remove from pipeline.
     *
     * @return name of removed element.
     *
     * @see #removeSingleOccurrenceFromHead(Object, int)
     */
    String removeFirstOccurrence(Object o);

    /**
     * Remove all elements from pipeline with given value.
     * Removed values are limited by {@code limit} param.
     * <br>
     * Method is iterating from tail to head of pipeline.
     * <br>
     * Every removed element decrement {@code limit},
     * and when it hit 0, method stops.
     * Use -1 for no limit.
     *
     * @param o     element to remove from pipeline.
     * @param limit limit of removed elements.
     *
     * @return collection of removed element names, never null.
     */
    Collection<String> removeAllFromTail(Object o, int limit);

    /**
     * Remove all elements from pipeline with given name.
     * Removed values are limited by {@code limit} param.
     * <br>
     * Method is iterating from tail to head of pipeline.
     * <br>
     * Every removed element decrement {@code limit},
     * and when it hit 0, method stops.
     * Use -1 for no limit.
     *
     * @param name  name of elements to remove from pipeline.
     * @param limit limit of removed elements.
     *
     * @return collection of removed elements, never null.
     */
    Collection<E> removeAllFromTail(String name, int limit);

    /**
     * Remove all elements from pipeline with given value.
     * Removed values are limited by {@code limit} param.
     * <br>
     * Method is iterating from head to tail of pipeline.
     * <br>
     * Every removed element decrement {@code limit},
     * and when it hit 0, method stops.
     * Use -1 for no limit.
     *
     * @param o     element to remove from pipeline.
     * @param limit limit of removed elements.
     *
     * @return collection of removed element names, never null.
     */
    Collection<String> removeAllFromHead(Object o, int limit);

    /**
     * Remove all elements from pipeline with given name.
     * Removed values are limited by {@code limit} param.
     * <br>
     * Method is iterating from head to tail of pipeline.
     * <br>
     * Every removed element decrement {@code limit},
     * and when it hit 0, method stops.
     * Use -1 for no limit.
     *
     * @param name  name of elements to remove from pipeline.
     * @param limit limit of removed elements.
     *
     * @return collection of removed elements, never null.
     */
    Collection<E> removeAllFromHead(String name, int limit);

    /**
     * Method will look for {@code index} pipeline element named as in {@code name} param,
     * and remove it.
     * <br>
     * Index is decremented each time when iterator find element with matching name,
     * when index is equals to 0 iterator stops and remove current element.
     * <br>
     * Method is iterating from tail to head of pipeline.
     * <br>
     * If there is no element with given name, method will just return null,
     * and no changes will be made.
     *
     * @param name  name of element to remove from pipeline.
     * @param index index of element to remove.
     *
     * @return removed element.
     *
     * @see #remove(String)
     * @see #removeLastOccurrence(String)
     */
    E removeSingleOccurrenceFromTail(String name, int index);

    /**
     * Method will look for first pipeline element named as in {@code name} param,
     * and remove it.
     * <br>
     * Method is iterating from tail to head of pipeline.
     * <br>
     * If there is no element with given name, method will just return null,
     * and no changes will be made.
     *
     * @param name name of element to remove from pipeline.
     *
     * @return removed element.
     *
     * @see #remove(String)
     * @see #removeSingleOccurrenceFromTail(String, int)
     */
    E removeLastOccurrence(String name);

    /**
     * Method will look for {@code index} pipeline element named as in {@code name} param,
     * and remove it.
     * <br>
     * Index is decremented each time when iterator find element with matching name,
     * when index is equals to 0 iterator stops and remove current element.
     * <br>
     * Method is iterating from head to tail of pipeline.
     * <br>
     * If there is no element with given name, method will just return null,
     * and no changes will be made.
     *
     * @param name  name of element to remove from pipeline.
     * @param index index of element to remove.
     *
     * @return removed element.
     */
    E removeSingleOccurrenceFromHead(String name, int index);

    /**
     * Method will look for first pipeline element named as in {@code name} param,
     * and remove it.
     * <br>
     * Method is iterating from head to tail of pipeline.
     * <br>
     * If there is no element with given name, method will just return null,
     * and no changes will be made.
     *
     * @param name name of element to remove from pipeline.
     *
     * @return removed element.
     */
    E removeFirstOccurrence(String name);

    /**
     * Remove all elements from pipeline with given name.
     * <br>
     * Method is iterating from tail to head of pipeline.
     *
     * @param name name of elements to remove from pipeline.
     *
     * @return collection of removed elements, never null.
     *
     * @see #removeAllFromTail(String, int)
     */
    Collection<E> removeAll(String name);

    /**
     * Remove all elements from pipeline with given value.
     * <br>
     * Method is iterating from tail to head of pipeline.
     *
     * @param o element to remove from pipeline.
     *
     * @return collection of removed element names, never null.
     *
     * @see #removeAllFromTail(String, int)
     */
    Collection<String> removeAll(Object o);

    /**
     * Method will look for first pipeline element named as in {@code name} param,
     * and remove it.
     * <br>
     * Method is iterating from tail to head of pipeline.
     * <br>
     * If there is no element with given name, method will just return null,
     * and no changes will be made.
     *
     * @param name name of element to remove from pipeline.
     *
     * @return removed element.
     *
     * @see #removeLastOccurrence(String)
     * @see #removeSingleOccurrenceFromTail(String, int)
     */
    E remove(String name);

    /**
     * Method will add new element to the end of pipeline.
     *
     * @param name  name of new element.
     * @param value value of new element.
     *
     * @see #addLast(String, Object)
     */
    void add(String name, E value);

    /**
     * Method will add new element to the beginning of pipeline.
     *
     * @param name  name of new element.
     * @param value value of new element.
     */
    void addFirst(String name, E value);

    /**
     * Method will add new element to the end of pipeline.
     *
     * @param name  name of new element.
     * @param value value of new element.
     *
     * @see #addLast(String, Object)
     */
    void addLast(String name, E value);

    /**
     * @return first element of pipeline, head value.
     */
    E getFirst();

    /**
     * @return name of first element in pipeline, head name.
     */
    String getNameOfFirst();

    /**
     * @return last element of pipeline, tail value.
     */
    E getLast();

    /**
     * @return name of last element in pipeline, tail name.
     */
    String getNameOfLast();


    /**
     * Method will look for first pipeline element named as in {@code element} param,
     * and change it value to given value.
     * <br>
     * Method is iterating from head to tail of pipeline.
     * <br>
     * If there is no element with given name, method will throw error.
     * {@link #setIfContains(String, Object)}
     *
     * @param element element name to find.
     * @param value   new element value
     *
     * @return pipeline itself.
     *
     * @throws NoSuchElementException if there is no element to change
     * @see #linkInsteadFromHead(String, String, Object, int)
     * @see #set(String, Object, int)
     * @see #setFromHead(String, Object)
     * @see #setFromHead(String, Object, int)
     * @see #setIfContains(String, Object)
     * @see #setIfContains(String, Object, int)
     * @see #setIfContainsFromHead(String, Object)
     * @see #setIfContainsFromHead(String, Object, int)
     * @see #replace(String, String, Object)
     * @see #replace(String, String, Object, int)
     * @see #replaceFromHead(String, String, Object)
     * @see #replaceFromHead(String, String, Object, int)
     * @see #replaceIfContainsFromHead(String, String, Object)
     * @see #replaceIfContainsFromHead(String, String, Object, int)
     */
    BasePipeline<E> set(String element, E value) throws NoSuchElementException;

    /**
     * Method will look for first pipeline element named as in {@code element} param,
     * and change it value to given value.
     * <br>
     * Method is iterating from head to tail of pipeline.
     * <br>
     * If there is no element with given name, method will throw error.
     * {@link #setIfContains(String, Object)}
     *
     * @param element element name to find.
     * @param value   new element value
     *
     * @return pipeline itself.
     *
     * @throws NoSuchElementException if there is no element to change
     * @see #linkInsteadFromHead(String, String, Object, int)
     * @see #set(String, Object)
     * @see #set(String, Object, int)
     * @see #setFromHead(String, Object, int)
     * @see #setIfContains(String, Object)
     * @see #setIfContains(String, Object, int)
     * @see #setIfContainsFromHead(String, Object)
     * @see #setIfContainsFromHead(String, Object, int)
     * @see #replace(String, String, Object)
     * @see #replace(String, String, Object, int)
     * @see #replaceFromHead(String, String, Object)
     * @see #replaceFromHead(String, String, Object, int)
     * @see #replaceIfContainsFromHead(String, String, Object)
     * @see #replaceIfContainsFromHead(String, String, Object, int)
     */
    BasePipeline<E> setFromHead(String element, E value) throws NoSuchElementException;

    /**
     * Method will look for first pipeline element named as in {@code element} param,
     * and change it value to given value.
     * <br>
     * Method is iterating from tail to head of pipeline.
     * <br>
     * If there is no element with given name, method will throw error.
     * {@link #setIfContains(String, Object)}
     *
     * @param element element name to find.
     * @param value   new element value
     *
     * @return pipeline itself.
     *
     * @throws NoSuchElementException if there is no element to change
     * @see #linkInsteadFromTail(String, String, Object, int)
     * @see #setFromTail(String, Object, int)
     * @see #setIfContainsFromTail(String, Object)
     * @see #setIfContainsFromTail(String, Object, int)
     * @see #replaceFromTail(String, String, Object)
     * @see #replaceFromTail(String, String, Object, int)
     * @see #replaceIfContainsFromTail(String, String, Object)
     * @see #replaceIfContainsFromTail(String, String, Object, int)
     */
    BasePipeline<E> setFromTail(String element, E value) throws NoSuchElementException;

    /**
     * Method will look for {@code index} pipeline element named as in {@code element} param,
     * and change it name and value to given params.
     * use 0 as index to use first found element, or use {@link #set(String, Object)}
     * <br>
     * Index is decremented each time when iterator find element with matching name,
     * when index is equals to 0 iterator stops and change name and value of current element.
     * <br>
     * Method is iterating from head to tail of pipeline.
     * <br>
     * If there is no element with given name, method will throw error.
     * {@link #setIfContains(String, Object, int)}
     *
     * @param element element name to find.
     * @param value   new element value
     * @param index   index of element.
     *
     * @return pipeline itself.
     *
     * @throws NoSuchElementException if there is no element to change
     * @see #linkInsteadFromHead(String, String, Object, int)
     * @see #set(String, Object)
     * @see #setFromHead(String, Object)
     * @see #setFromHead(String, Object, int)
     * @see #setIfContains(String, Object)
     * @see #setIfContains(String, Object, int)
     * @see #setIfContainsFromHead(String, Object)
     * @see #setIfContainsFromHead(String, Object, int)
     * @see #replace(String, String, Object)
     * @see #replace(String, String, Object, int)
     * @see #replaceFromHead(String, String, Object)
     * @see #replaceFromHead(String, String, Object, int)
     * @see #replaceIfContainsFromHead(String, String, Object)
     * @see #replaceIfContainsFromHead(String, String, Object, int)
     */
    BasePipeline<E> set(String element, E value, int index) throws NoSuchElementException;

    /**
     * Method will look for {@code index} pipeline element named as in {@code element} param,
     * and change it name and value to given params.
     * use 0 as index to use first found element, or use {@link #setFromHead(String, Object)}
     * <br>
     * Index is decremented each time when iterator find element with matching name,
     * when index is equals to 0 iterator stops and change name and value of current element.
     * <br>
     * Method is iterating from head to tail of pipeline.
     * <br>
     * If there is no element with given name, method will throw error.
     * {@link #setIfContains(String, Object, int)}
     *
     * @param element element name to find.
     * @param value   new element value
     * @param index   index of element.
     *
     * @return pipeline itself.
     *
     * @throws NoSuchElementException if there is no element to change
     * @see #linkInsteadFromHead(String, String, Object, int)
     * @see #set(String, Object)
     * @see #set(String, Object, int)
     * @see #setFromHead(String, Object)
     * @see #setIfContains(String, Object)
     * @see #setIfContains(String, Object, int)
     * @see #setIfContainsFromHead(String, Object)
     * @see #setIfContainsFromHead(String, Object, int)
     * @see #replace(String, String, Object)
     * @see #replace(String, String, Object, int)
     * @see #replaceFromHead(String, String, Object)
     * @see #replaceFromHead(String, String, Object, int)
     * @see #replaceIfContainsFromHead(String, String, Object)
     * @see #replaceIfContainsFromHead(String, String, Object, int)
     */
    BasePipeline<E> setFromHead(String element, E value, int index) throws NoSuchElementException;

    /**
     * Method will look for {@code index} pipeline element named as in {@code element} param,
     * and change it name and value to given params.
     * use 0 as index to use first found element, or use {@link #setFromTail(String, Object)}
     * <br>
     * Index is decremented each time when iterator find element with matching name,
     * when index is equals to 0 iterator stops and change name and value of current element.
     * <br>
     * Method is iterating from tail to head of pipeline.
     * <br>
     * If there is no element with given name, method will throw error.
     * {@link #setIfContainsFromTail(String, Object, int)}
     *
     * @param element element name to find.
     * @param value   new element value
     * @param index   index of element.
     *
     * @return pipeline itself.
     *
     * @throws NoSuchElementException if there is no element to change
     * @see #linkInsteadFromTail(String, String, Object, int)
     * @see #setFromTail(String, Object)
     * @see #setIfContainsFromTail(String, Object)
     * @see #setIfContainsFromTail(String, Object, int)
     * @see #replaceFromTail(String, String, Object)
     * @see #replaceFromTail(String, String, Object, int)
     * @see #replaceIfContainsFromTail(String, String, Object)
     * @see #replaceIfContainsFromTail(String, String, Object, int)
     */
    BasePipeline<E> setFromTail(String element, E value, int index) throws NoSuchElementException;

    /**
     * Method will look for first pipeline element named as in {@code element} param,
     * and change it name and value to given params.
     * <br>
     * Method is iterating from head to tail of pipeline.
     * <br>
     * Use this same value as element and name to just change value of element.
     * Or use {@link #set(String, Object)}
     * <br>
     * If there is no element with given name, method will throw error.
     * {@link #replaceIfContains(String, String, Object)}
     *
     * @param element name of element to find.
     * @param name    name to set.
     * @param value   value to set.
     *
     * @return pipeline itself.
     *
     * @throws NoSuchElementException if there is no element to change
     * @see #linkInsteadFromHead(String, String, Object, int)
     * @see #set(String, Object)
     * @see #set(String, Object, int)
     * @see #setFromHead(String, Object)
     * @see #setFromHead(String, Object, int)
     * @see #setIfContains(String, Object)
     * @see #setIfContains(String, Object, int)
     * @see #setIfContainsFromHead(String, Object)
     * @see #setIfContainsFromHead(String, Object, int)
     * @see #replace(String, String, Object, int)
     * @see #replaceFromHead(String, String, Object)
     * @see #replaceFromHead(String, String, Object, int)
     * @see #replaceIfContainsFromHead(String, String, Object)
     * @see #replaceIfContainsFromHead(String, String, Object, int)
     */
    BasePipeline<E> replace(String element, String name, E value) throws NoSuchElementException;

    /**
     * Method will look for first pipeline element named as in {@code element} param,
     * and change it name and value to given params.
     * <br>
     * Method is iterating from head to tail of pipeline.
     * <br>
     * Use this same value as element and name to just change value of element.
     * Or use {@link #setFromHead(String, Object)}
     * <br>
     * If there is no element with given name, method will throw error.
     * {@link #replaceIfContainsFromHead(String, String, Object)}
     *
     * @param element name of element to find.
     * @param name    name to set.
     * @param value   value to set.
     *
     * @return pipeline itself.
     *
     * @throws NoSuchElementException if there is no element to change
     * @see #linkInsteadFromHead(String, String, Object, int)
     * @see #set(String, Object)
     * @see #set(String, Object, int)
     * @see #setFromHead(String, Object)
     * @see #setFromHead(String, Object, int)
     * @see #setIfContains(String, Object)
     * @see #setIfContains(String, Object, int)
     * @see #setIfContainsFromHead(String, Object)
     * @see #setIfContainsFromHead(String, Object, int)
     * @see #replace(String, String, Object)
     * @see #replace(String, String, Object, int)
     * @see #replaceFromHead(String, String, Object, int)
     * @see #replaceIfContainsFromHead(String, String, Object)
     * @see #replaceIfContainsFromHead(String, String, Object, int)
     */
    BasePipeline<E> replaceFromHead(String element, String name, E value) throws NoSuchElementException;

    /**
     * Method will look for first pipeline element named as in {@code element} param,
     * and change it name and value to given params.
     * <br>
     * Method is iterating from tail to head of pipeline.
     * <br>
     * Use this same value as element and name to just change value of element.
     * Or use {@link #setFromTail(String, Object)}
     * <br>
     * If there is no element with given name, method will throw error.
     * {@link #replaceIfContainsFromTail(String, String, Object)}
     *
     * @param element name of element to find.
     * @param name    name to set.
     * @param value   value to set.
     *
     * @return pipeline itself.
     *
     * @throws NoSuchElementException if there is no element to change
     * @see #linkInsteadFromTail(String, String, Object, int)
     * @see #setFromTail(String, Object)
     * @see #setFromTail(String, Object, int)
     * @see #setIfContainsFromTail(String, Object)
     * @see #setIfContainsFromTail(String, Object, int)
     * @see #replaceFromTail(String, String, Object, int)
     * @see #replaceIfContainsFromTail(String, String, Object)
     * @see #replaceIfContainsFromTail(String, String, Object, int)
     */
    BasePipeline<E> replaceFromTail(String element, String name, E value) throws NoSuchElementException;

    /**
     * Method will look for {@code index} pipeline element named as in {@code element} param,
     * and change it name and value to given params.
     * use 0 as index to use first found element.
     * <br>
     * Index is decremented each time when iterator find element with matching name,
     * when index is equals to 0 iterator stops and change name and value of current element.
     * <br>
     * Method is iterating from head to tail of pipeline.
     * <br>
     * Use this same value as element and name to just change value of element.
     * Or use {@link #set(String, Object, int)}
     * <br>
     * If there is no element with given name, method will throw error.
     * {@link #replaceIfContains(String, String, Object, int)}
     *
     * @param element name of element to find.
     * @param name    name to set.
     * @param value   value to set.
     * @param index   index of element.
     *
     * @return pipeline itself.
     *
     * @throws NoSuchElementException if there is no element to change
     * @see #linkInsteadFromHead(String, String, Object, int)
     * @see #set(String, Object)
     * @see #set(String, Object, int)
     * @see #setFromHead(String, Object)
     * @see #setFromHead(String, Object, int)
     * @see #setIfContains(String, Object)
     * @see #setIfContains(String, Object, int)
     * @see #setIfContainsFromHead(String, Object)
     * @see #setIfContainsFromHead(String, Object, int)
     * @see #replace(String, String, Object)
     * @see #replaceFromHead(String, String, Object)
     * @see #replaceFromHead(String, String, Object, int)
     * @see #replaceIfContainsFromHead(String, String, Object)
     * @see #replaceIfContainsFromHead(String, String, Object, int)
     */
    BasePipeline<E> replace(String element, String name, E value, int index) throws NoSuchElementException;

    /**
     * Method will look for {@code index} pipeline element named as in {@code element} param,
     * and change it name and value to given params.
     * use 0 as index to use first found element.
     * <br>
     * Index is decremented each time when iterator find element with matching name,
     * when index is equals to 0 iterator stops and change name and value of current element.
     * <br>
     * Method is iterating from head to tail of pipeline.
     * <br>
     * Use this same value as element and name to just change value of element.
     * Or use {@link #setFromHead(String, Object, int)}
     * <br>
     * If there is no element with given name, method will throw error.
     * {@link #replaceIfContainsFromHead(String, String, Object, int)}
     *
     * @param element name of element to find.
     * @param name    name to set.
     * @param value   value to set.
     * @param index   index of element.
     *
     * @return pipeline itself.
     *
     * @throws NoSuchElementException if there is no element to change
     * @see #linkInsteadFromHead(String, String, Object, int)
     * @see #set(String, Object)
     * @see #set(String, Object, int)
     * @see #setFromHead(String, Object)
     * @see #setFromHead(String, Object, int)
     * @see #setIfContains(String, Object)
     * @see #setIfContains(String, Object, int)
     * @see #setIfContainsFromHead(String, Object)
     * @see #setIfContainsFromHead(String, Object, int)
     * @see #replace(String, String, Object)
     * @see #replace(String, String, Object, int)
     * @see #replaceFromHead(String, String, Object)
     * @see #replaceIfContainsFromHead(String, String, Object)
     * @see #replaceIfContainsFromHead(String, String, Object, int)
     */
    BasePipeline<E> replaceFromHead(String element, String name, E value, int index) throws NoSuchElementException;

    /**
     * Method will look for {@code index} pipeline element named as in {@code element} param,
     * and change it name and value to given params.
     * use 0 as index to use first found element.
     * <br>
     * Index is decremented each time when iterator find element with matching name,
     * when index is equals to 0 iterator stops and change name and value of current element.
     * <br>
     * Method is iterating from tail to head of pipeline.
     * <br>
     * Use this same value as element and name to just change value of element.
     * Or use {@link #setFromTail(String, Object, int)}
     * <br>
     * If there is no element with given name, method will throw error.
     * {@link #replaceIfContainsFromTail(String, String, Object, int)}
     *
     * @param element name of element to find.
     * @param name    name to set.
     * @param value   value to set.
     * @param index   index of element.
     *
     * @return pipeline itself.
     *
     * @throws NoSuchElementException if there is no element to change
     * @see #linkInsteadFromTail(String, String, Object, int)
     * @see #setFromTail(String, Object)
     * @see #setFromTail(String, Object, int)
     * @see #setIfContainsFromTail(String, Object)
     * @see #setIfContainsFromTail(String, Object, int)
     * @see #replaceFromTail(String, String, Object)
     * @see #replaceIfContainsFromTail(String, String, Object)
     * @see #replaceIfContainsFromTail(String, String, Object, int)
     */
    BasePipeline<E> replaceFromTail(String element, String name, E value, int index) throws NoSuchElementException;

    /**
     * Method will look for first pipeline element named as in {@code before} param,
     * and add new element with given name and value BEFORE it.
     * <br>
     * Method is iterating from head to tail of pipeline.
     * <br>
     * If there is no element with given name, method will throw error.
     * {@link #addBeforeIfContains(String, String, Object)}
     *
     * @param before name of relative element.
     * @param name   name of new element.
     * @param value  value of new element.
     *
     * @return pipeline itself.
     *
     * @throws NoSuchElementException if there is no element to change
     * @see #linkBeforeFromHead(String, String, Object, int)
     * @see #addBefore(String, String, Object, int)
     * @see #addBeforeFromHead(String, String, Object)
     * @see #addBeforeFromHead(String, String, Object, int)
     * @see #addBeforeIfContains(String, String, Object)
     * @see #addBeforeIfContains(String, String, Object, int)
     * @see #addBeforeIfContainsFromHead(String, String, Object)
     * @see #addBeforeIfContainsFromHead(String, String, Object, int)
     */
    BasePipeline<E> addBefore(String before, String name, E value) throws NoSuchElementException;

    /**
     * Method will look for first pipeline element named as in {@code before} param,
     * and add new element with given name and value BEFORE it.
     * <br>
     * Method is iterating from head to tail of pipeline.
     * <br>
     * If there is no element with given name, method will throw error.
     * {@link #addBeforeIfContainsFromHead(String, String, Object)}
     *
     * @param before name of relative element.
     * @param name   name of new element.
     * @param value  value of new element.
     *
     * @return pipeline itself.
     *
     * @throws NoSuchElementException if there is no element to change
     * @see #linkBeforeFromHead(String, String, Object, int)
     * @see #addBefore(String, String, Object)
     * @see #addBefore(String, String, Object, int)
     * @see #addBeforeFromHead(String, String, Object, int)
     * @see #addBeforeIfContains(String, String, Object)
     * @see #addBeforeIfContains(String, String, Object, int)
     * @see #addBeforeIfContainsFromHead(String, String, Object)
     * @see #addBeforeIfContainsFromHead(String, String, Object, int)
     */
    BasePipeline<E> addBeforeFromHead(String before, String name, E value) throws NoSuchElementException;

    /**
     * Method will look for first pipeline element named as in {@code before} param,
     * and add new element with given name and value BEFORE it.
     * <br>
     * Method is iterating from tail to head of pipeline.
     * <br>
     * If there is no element with given name, method will throw error.
     * {@link #addBeforeIfContainsFromTail(String, String, Object)}
     *
     * @param before name of relative element.
     * @param name   name of new element.
     * @param value  value of new element.
     *
     * @return pipeline itself.
     *
     * @throws NoSuchElementException if there is no element to change
     * @see #linkBeforeFromTail(String, String, Object, int)
     * @see #addBeforeFromTail(String, String, Object, int)
     * @see #addBeforeIfContainsFromTail(String, String, Object)
     * @see #addBeforeIfContainsFromTail(String, String, Object, int)
     */
    BasePipeline<E> addBeforeFromTail(String before, String name, E value) throws NoSuchElementException;

    /**
     * Method will look for {@code index} pipeline element named as in {@code before} param,
     * and add new element with given name and value BEFORE it.
     * <br>
     * Index is decremented each time when iterator find element with matching name,
     * when index is equals to 0 iterator stops and add new element BEFORE current one.
     * <br>
     * Method is iterating from head to tail of pipeline.
     * <br>
     * If there is no element with given name, method will throw error.
     * {@link #addBeforeIfContains(String, String, Object, int)}
     *
     * @param before name of relative element.
     * @param name   name of new element.
     * @param value  value of new element.
     * @param index  index of relative element.
     *
     * @return pipeline itself.
     *
     * @throws NoSuchElementException if there is no element to change
     * @see #linkBeforeFromHead(String, String, Object, int)
     * @see #addBefore(String, String, Object)
     * @see #addBeforeFromHead(String, String, Object)
     * @see #addBeforeFromHead(String, String, Object, int)
     * @see #addBeforeIfContains(String, String, Object)
     * @see #addBeforeIfContains(String, String, Object, int)
     * @see #addBeforeIfContainsFromHead(String, String, Object)
     * @see #addBeforeIfContainsFromHead(String, String, Object, int)
     */
    BasePipeline<E> addBefore(String before, String name, E value, int index) throws NoSuchElementException;

    /**
     * Method will look for {@code index} pipeline element named as in {@code before} param,
     * and add new element with given name and value BEFORE it.
     * <br>
     * Index is decremented each time when iterator find element with matching name,
     * when index is equals to 0 iterator stops and add new element BEFORE current one.
     * <br>
     * Method is iterating from head to tail of pipeline.
     * <br>
     * If there is no element with given name, method will throw error.
     * {@link #addBeforeIfContainsFromHead(String, String, Object, int)}
     *
     * @param before name of relative element.
     * @param name   name of new element.
     * @param value  value of new element.
     * @param index  index of relative element.
     *
     * @return pipeline itself.
     *
     * @throws NoSuchElementException if there is no element to change
     * @see #linkBeforeFromHead(String, String, Object, int)
     * @see #addBefore(String, String, Object)
     * @see #addBefore(String, String, Object, int)
     * @see #addBeforeFromHead(String, String, Object)
     * @see #addBeforeIfContains(String, String, Object)
     * @see #addBeforeIfContains(String, String, Object, int)
     * @see #addBeforeIfContainsFromHead(String, String, Object)
     * @see #addBeforeIfContainsFromHead(String, String, Object, int)
     */
    BasePipeline<E> addBeforeFromHead(String before, String name, E value, int index) throws NoSuchElementException;

    /**
     * Method will look for {@code index} pipeline element named as in {@code before} param,
     * and add new element with given name and value BEFORE it.
     * <br>
     * Index is decremented each time when iterator find element with matching name,
     * when index is equals to 0 iterator stops and add new element BEFORE current one.
     * <br>
     * Method is iterating from tail to head of pipeline.
     * <br>
     * If there is no element with given name, method will throw error.
     * {@link #addBeforeIfContainsFromTail(String, String, Object, int)}
     *
     * @param before name of relative element.
     * @param name   name of new element.
     * @param value  value of new element.
     * @param index  index of relative element.
     *
     * @return pipeline itself.
     *
     * @throws NoSuchElementException if there is no element to change
     * @see #linkBeforeFromTail(String, String, Object, int)
     * @see #addBeforeFromTail(String, String, Object)
     * @see #addBeforeIfContainsFromTail(String, String, Object)
     * @see #addBeforeIfContainsFromTail(String, String, Object, int)
     */
    BasePipeline<E> addBeforeFromTail(String before, String name, E value, int index) throws NoSuchElementException;

    /**
     * Method will look for first pipeline element named as in {@code after} param,
     * and add new element with given name and value AFTER it.
     * <br>
     * Index is decremented each time when iterator find element with matching name,
     * when index is equals to 0 iterator stops and add new element AFTER current one.
     * <br>
     * Method is iterating from head to tail of pipeline.
     * <br>
     * If there is no element with given name, method will throw error.
     * {@link #addAfterIfContains(String, String, Object)}
     *
     * @param after name of relative element.
     * @param name  name of new element.
     * @param value value of new element.
     *
     * @return pipeline itself.
     *
     * @throws NoSuchElementException if there is no element to change
     * @see #linkAfterFromHead(String, String, Object, int)
     * @see #addAfter(String, String, Object, int)
     * @see #addAfterFromHead(String, String, Object)
     * @see #addAfterFromHead(String, String, Object, int)
     * @see #addAfterIfContains(String, String, Object)
     * @see #addAfterIfContains(String, String, Object, int)
     * @see #addAfterIfContainsFromHead(String, String, Object)
     * @see #addAfterIfContainsFromHead(String, String, Object, int)
     */
    BasePipeline<E> addAfter(String after, String name, E value) throws NoSuchElementException;

    /**
     * Method will look for first pipeline element named as in {@code after} param,
     * and add new element with given name and value AFTER it.
     * <br>
     * Index is decremented each time when iterator find element with matching name,
     * when index is equals to 0 iterator stops and add new element AFTER current one.
     * <br>
     * Method is iterating from head to tail of pipeline.
     * <br>
     * If there is no element with given name, method will throw error.
     * {@link #addAfterIfContainsFromHead(String, String, Object)}
     *
     * @param after name of relative element.
     * @param name  name of new element.
     * @param value value of new element.
     *
     * @return pipeline itself.
     *
     * @throws NoSuchElementException if there is no element to change
     * @see #linkAfterFromHead(String, String, Object, int)
     * @see #addAfter(String, String, Object)
     * @see #addAfter(String, String, Object, int)
     * @see #addAfterFromHead(String, String, Object, int)
     * @see #addAfterIfContains(String, String, Object)
     * @see #addAfterIfContains(String, String, Object, int)
     * @see #addAfterIfContainsFromHead(String, String, Object)
     * @see #addAfterIfContainsFromHead(String, String, Object, int)
     */
    BasePipeline<E> addAfterFromHead(String after, String name, E value) throws NoSuchElementException;

    /**
     * Method will look for first pipeline element named as in {@code after} param,
     * and add new element with given name and value AFTER it.
     * <br>
     * Index is decremented each time when iterator find element with matching name,
     * when index is equals to 0 iterator stops and add new element AFTER current one.
     * <br>
     * Method is iterating from tail to head of pipeline.
     * <br>
     * If there is no element with given name, method will throw error.
     * {@link #addAfterIfContainsFromTail(String, String, Object)}
     *
     * @param after name of relative element.
     * @param name  name of new element.
     * @param value value of new element.
     *
     * @return pipeline itself.
     *
     * @throws NoSuchElementException if there is no element to change
     * @see #linkAfterFromTail(String, String, Object, int)
     * @see #addAfterFromTail(String, String, Object, int)
     * @see #addAfterIfContainsFromTail(String, String, Object)
     * @see #addAfterIfContainsFromTail(String, String, Object, int)
     */
    BasePipeline<E> addAfterFromTail(String after, String name, E value) throws NoSuchElementException;

    /**
     * Method will look for {@code index} pipeline element named as in {@code after} param,
     * and add new element with given name and value AFTER it.
     * <br>
     * Index is decremented each time when iterator find element with matching name,
     * when index is equals to 0 iterator stops and add new element AFTER current one.
     * <br>
     * Method is iterating from head to tail of pipeline.
     * <br>
     * If there is no element with given name, method will throw error.
     * {@link #addAfterIfContains(String, String, Object, int)}
     *
     * @param after name of relative element.
     * @param name  name of new element.
     * @param value value of new element.
     * @param index index of relative element.
     *
     * @return pipeline itself.
     *
     * @throws NoSuchElementException if there is no element to change
     * @see #linkAfterFromHead(String, String, Object, int)
     * @see #addAfter(String, String, Object)
     * @see #addAfterFromHead(String, String, Object)
     * @see #addAfterFromHead(String, String, Object, int)
     * @see #addAfterIfContains(String, String, Object)
     * @see #addAfterIfContains(String, String, Object, int)
     * @see #addAfterIfContainsFromHead(String, String, Object)
     * @see #addAfterIfContainsFromHead(String, String, Object, int)
     */
    BasePipeline<E> addAfter(String after, String name, E value, int index) throws NoSuchElementException;

    /**
     * Method will look for {@code index} pipeline element named as in {@code after} param,
     * and add new element with given name and value AFTER it.
     * <br>
     * Index is decremented each time when iterator find element with matching name,
     * when index is equals to 0 iterator stops and add new element AFTER current one.
     * <br>
     * Method is iterating from head to tail of pipeline.
     * <br>
     * If there is no element with given name, method will throw error.
     * {@link #addAfterIfContainsFromHead(String, String, Object, int)}
     *
     * @param after name of relative element.
     * @param name  name of new element.
     * @param value value of new element.
     * @param index index of relative element.
     *
     * @return pipeline itself.
     *
     * @throws NoSuchElementException if there is no element to change
     * @see #linkAfterFromHead(String, String, Object, int)
     * @see #addAfter(String, String, Object)
     * @see #addAfter(String, String, Object, int)
     * @see #addAfterFromHead(String, String, Object)
     * @see #addAfterIfContains(String, String, Object)
     * @see #addAfterIfContains(String, String, Object, int)
     * @see #addAfterIfContainsFromHead(String, String, Object)
     * @see #addAfterIfContainsFromHead(String, String, Object, int)
     */
    BasePipeline<E> addAfterFromHead(String after, String name, E value, int index) throws NoSuchElementException;

    /**
     * Method will look for {@code index} pipeline element named as in {@code after} param,
     * and add new element with given name and value AFTER it.
     * <br>
     * Index is decremented each time when iterator find element with matching name,
     * when index is equals to 0 iterator stops and add new element AFTER current one.
     * <br>
     * Method is iterating from tail to head of pipeline.
     * <br>
     * If there is no element with given name, method will throw error.
     * {@link #addAfterIfContainsFromTail(String, String, Object, int)}
     *
     * @param after name of relative element.
     * @param name  name of new element.
     * @param value value of new element.
     * @param index index of relative element.
     *
     * @return pipeline itself.
     *
     * @throws NoSuchElementException if there is no element to change
     * @see #linkAfterFromTail(String, String, Object, int)
     * @see #addAfterFromTail(String, String, Object)
     * @see #addAfterIfContainsFromTail(String, String, Object)
     * @see #addAfterIfContainsFromTail(String, String, Object, int)
     */
    BasePipeline<E> addAfterFromTail(String after, String name, E value, int index) throws NoSuchElementException;

    /**
     * Method will look for first pipeline element named as in {@code element} param,
     * and change it value to given value.
     * <br>
     * Method is iterating from head to tail of pipeline.
     * <br>
     * If there is no element with given name, method will do nothing.
     * {@link #set(String, Object)}
     *
     * @param element element name to find.
     * @param value   new element value
     *
     * @return pipeline itself.
     *
     * @see #linkInsteadFromHead(String, String, Object, int)
     * @see #set(String, Object)
     * @see #set(String, Object, int)
     * @see #setFromHead(String, Object)
     * @see #setFromHead(String, Object, int)
     * @see #setIfContains(String, Object, int)
     * @see #setIfContainsFromHead(String, Object)
     * @see #setIfContainsFromHead(String, Object, int)
     * @see #replace(String, String, Object)
     * @see #replace(String, String, Object, int)
     * @see #replaceFromHead(String, String, Object)
     * @see #replaceFromHead(String, String, Object, int)
     * @see #replaceIfContains(String, String, Object)
     * @see #replaceIfContains(String, String, Object, int)
     * @see #replaceIfContainsFromHead(String, String, Object)
     * @see #replaceIfContainsFromHead(String, String, Object, int)
     */
    BasePipeline<E> setIfContains(String element, E value);

    /**
     * Method will look for first pipeline element named as in {@code element} param,
     * and change it value to given value.
     * <br>
     * Method is iterating from head to tail of pipeline.
     * <br>
     * If there is no element with given name, method will do nothing.
     * {@link #setFromHead(String, Object)}
     *
     * @param element element name to find.
     * @param value   new element value
     *
     * @return pipeline itself.
     *
     * @see #linkInsteadFromHead(String, String, Object, int)
     * @see #set(String, Object)
     * @see #set(String, Object, int)
     * @see #setFromHead(String, Object)
     * @see #setFromHead(String, Object, int)
     * @see #setIfContains(String, Object)
     * @see #setIfContains(String, Object, int)
     * @see #setIfContainsFromHead(String, Object, int)
     * @see #replace(String, String, Object)
     * @see #replace(String, String, Object, int)
     * @see #replaceFromHead(String, String, Object)
     * @see #replaceFromHead(String, String, Object, int)
     * @see #replaceIfContains(String, String, Object)
     * @see #replaceIfContains(String, String, Object, int)
     * @see #replaceIfContainsFromHead(String, String, Object)
     * @see #replaceIfContainsFromHead(String, String, Object, int)
     */
    BasePipeline<E> setIfContainsFromHead(String element, E value);

    /**
     * Method will look for first pipeline element named as in {@code element} param,
     * and change it value to given value.
     * <br>
     * Method is iterating from tail to head of pipeline.
     * <br>
     * If there is no element with given name, method will do nothing.
     * {@link #setFromTail(String, Object)}
     *
     * @param element element name to find.
     * @param value   new element value
     *
     * @return pipeline itself.
     *
     * @see #linkInsteadFromTail(String, String, Object, int)
     * @see #setFromTail(String, Object)
     * @see #setFromTail(String, Object, int)
     * @see #setIfContainsFromTail(String, Object, int)
     * @see #replaceFromTail(String, String, Object)
     * @see #replaceFromTail(String, String, Object, int)
     * @see #replaceIfContainsFromTail(String, String, Object)
     * @see #replaceIfContainsFromTail(String, String, Object, int)
     */
    BasePipeline<E> setIfContainsFromTail(String element, E value);

    /**
     * Method will look for {@code index} pipeline element named as in {@code element} param,
     * and change it name and value to given params.
     * use 0 as index to use first found element, or use {@link #setIfContains(String, Object)}
     * <br>
     * Index is decremented each time when iterator find element with matching name,
     * when index is equals to 0 iterator stops and change name and value of current element.
     * <br>
     * Method is iterating from head to tail of pipeline.
     * <br>
     * If there is no element with given name, method will do nothing.
     * {@link #set(String, Object, int)}
     *
     * @param element element name to find.
     * @param value   new element value
     * @param index   index of element.
     *
     * @return pipeline itself.
     *
     * @see #linkInsteadFromHead(String, String, Object, int)
     * @see #set(String, Object)
     * @see #set(String, Object, int)
     * @see #setFromHead(String, Object)
     * @see #setFromHead(String, Object, int)
     * @see #setIfContains(String, Object)
     * @see #setIfContainsFromHead(String, Object)
     * @see #setIfContainsFromHead(String, Object, int)
     * @see #replace(String, String, Object)
     * @see #replace(String, String, Object, int)
     * @see #replaceFromHead(String, String, Object)
     * @see #replaceFromHead(String, String, Object, int)
     * @see #replaceIfContains(String, String, Object)
     * @see #replaceIfContains(String, String, Object, int)
     * @see #replaceIfContainsFromHead(String, String, Object)
     * @see #replaceIfContainsFromHead(String, String, Object, int)
     */
    BasePipeline<E> setIfContains(String element, E value, int index);

    /**
     * Method will look for {@code index} pipeline element named as in {@code element} param,
     * and change it name and value to given params.
     * use 0 as index to use first found element, or use {@link #setIfContainsFromHead(String, Object)}
     * <br>
     * Index is decremented each time when iterator find element with matching name,
     * when index is equals to 0 iterator stops and change name and value of current element.
     * <br>
     * Method is iterating from head to tail of pipeline.
     * <br>
     * If there is no element with given name, method will do nothing.
     * {@link #setFromHead(String, Object, int)}
     *
     * @param element element name to find.
     * @param value   new element value
     * @param index   index of element.
     *
     * @return pipeline itself.
     *
     * @see #linkInsteadFromHead(String, String, Object, int)
     * @see #set(String, Object)
     * @see #set(String, Object, int)
     * @see #setFromHead(String, Object)
     * @see #setFromHead(String, Object, int)
     * @see #setIfContains(String, Object)
     * @see #setIfContains(String, Object, int)
     * @see #setIfContainsFromHead(String, Object)
     * @see #replace(String, String, Object)
     * @see #replace(String, String, Object, int)
     * @see #replaceFromHead(String, String, Object)
     * @see #replaceFromHead(String, String, Object, int)
     * @see #replaceIfContains(String, String, Object)
     * @see #replaceIfContains(String, String, Object, int)
     * @see #replaceIfContainsFromHead(String, String, Object)
     * @see #replaceIfContainsFromHead(String, String, Object, int)
     */
    BasePipeline<E> setIfContainsFromHead(String element, E value, int index);

    /**
     * Method will look for {@code index} pipeline element named as in {@code element} param,
     * and change it name and value to given params.
     * use 0 as index to use first found element, or use {@link #setIfContainsFromTail(String, Object)}
     * <br>
     * Index is decremented each time when iterator find element with matching name,
     * when index is equals to 0 iterator stops and change name and value of current element.
     * <br>
     * Method is iterating from tail to head of pipeline.
     * <br>
     * If there is no element with given name, method will do nothing.
     * {@link #setFromTail(String, Object, int)}
     *
     * @param element element name to find.
     * @param value   new element value
     * @param index   index of element.
     *
     * @return pipeline itself.
     *
     * @see #linkInsteadFromTail(String, String, Object, int)
     * @see #setFromTail(String, Object)
     * @see #setFromTail(String, Object, int)
     * @see #setIfContainsFromTail(String, Object)
     * @see #replaceFromTail(String, String, Object)
     * @see #replaceFromTail(String, String, Object, int)
     * @see #replaceIfContainsFromTail(String, String, Object)
     * @see #replaceIfContainsFromTail(String, String, Object, int)
     */
    BasePipeline<E> setIfContainsFromTail(String element, E value, int index);

    /**
     * Method will look for first pipeline element named as in {@code element} param,
     * and change it name and value to given params.
     * <br>
     * Method is iterating from head to tail of pipeline.
     * <br>
     * Use this same value as element and name to just change value of element.
     * Or use {@link #setIfContains(String, Object)}
     * <br>
     * If there is no element with given name, method will do nothing.
     * {@link #replace(String, String, Object)}
     *
     * @param element name of element to find.
     * @param name    name to set.
     * @param value   value to set.
     *
     * @return pipeline itself.
     *
     * @see #linkInsteadFromHead(String, String, Object, int)
     * @see #set(String, Object)
     * @see #set(String, Object, int)
     * @see #setFromHead(String, Object)
     * @see #setFromHead(String, Object, int)
     * @see #setIfContains(String, Object)
     * @see #setIfContains(String, Object, int)
     * @see #setIfContainsFromHead(String, Object)
     * @see #setIfContainsFromHead(String, Object, int)
     * @see #replace(String, String, Object, int)
     * @see #replaceFromHead(String, String, Object)
     * @see #replaceFromHead(String, String, Object, int)
     * @see #replaceIfContains(String, String, Object)
     * @see #replaceIfContains(String, String, Object, int)
     * @see #replaceIfContainsFromHead(String, String, Object)
     * @see #replaceIfContainsFromHead(String, String, Object, int)
     */
    BasePipeline<E> replaceIfContains(String element, String name, E value);

    /**
     * Method will look for first pipeline element named as in {@code element} param,
     * and change it name and value to given params.
     * <br>
     * Method is iterating from head to tail of pipeline.
     * <br>
     * Use this same value as element and name to just change value of element.
     * Or use {@link #setIfContainsFromHead(String, Object)}
     * <br>
     * If there is no element with given name, method will do nothing.
     * {@link #replaceFromHead(String, String, Object)}
     *
     * @param element name of element to find.
     * @param name    name to set.
     * @param value   value to set.
     *
     * @return pipeline itself.
     *
     * @see #linkInsteadFromHead(String, String, Object, int)
     * @see #set(String, Object)
     * @see #set(String, Object, int)
     * @see #setFromHead(String, Object)
     * @see #setFromHead(String, Object, int)
     * @see #setIfContains(String, Object)
     * @see #setIfContains(String, Object, int)
     * @see #setIfContainsFromHead(String, Object)
     * @see #setIfContainsFromHead(String, Object, int)
     * @see #replace(String, String, Object)
     * @see #replace(String, String, Object, int)
     * @see #replaceFromHead(String, String, Object)
     * @see #replaceFromHead(String, String, Object, int)
     * @see #replaceIfContains(String, String, Object)
     * @see #replaceIfContains(String, String, Object, int)
     * @see #replaceIfContainsFromHead(String, String, Object, int)
     */
    BasePipeline<E> replaceIfContainsFromHead(String element, String name, E value);

    /**
     * Method will look for first pipeline element named as in {@code element} param,
     * and change it name and value to given params.
     * <br>
     * Method is iterating from tail to head of pipeline.
     * <br>
     * Use this same value as element and name to just change value of element.
     * Or use {@link #setIfContainsFromTail(String, Object)}
     * <br>
     * If there is no element with given name, method will do nothing.
     * {@link #replaceFromTail(String, String, Object)}
     *
     * @param element name of element to find.
     * @param name    name to set.
     * @param value   value to set.
     *
     * @return pipeline itself.
     *
     * @see #linkInsteadFromTail(String, String, Object, int)
     * @see #setFromTail(String, Object)
     * @see #setFromTail(String, Object, int)
     * @see #setIfContainsFromTail(String, Object)
     * @see #setIfContainsFromTail(String, Object, int)
     * @see #replaceFromTail(String, String, Object)
     * @see #replaceFromTail(String, String, Object, int)
     * @see #replaceIfContainsFromTail(String, String, Object, int)
     */
    BasePipeline<E> replaceIfContainsFromTail(String element, String name, E value);

    /**
     * Method will look for {@code index} pipeline element named as in {@code element} param,
     * and change it name and value to given params.
     * use 0 as index to use first found element.
     * <br>
     * Index is decremented each time when iterator find element with matching name,
     * when index is equals to 0 iterator stops and change name and value of current element.
     * <br>
     * Method is iterating from head to tail of pipeline.
     * <br>
     * Use this same value as element and name to just change value of element.
     * Or use {@link #setIfContains(String, Object, int)}
     * <br>
     * If there is no element with given name, method will do nothing.
     * {@link #replace(String, String, Object, int)}
     *
     * @param element name of element to find.
     * @param name    name to set.
     * @param value   value to set.
     * @param index   index of element.
     *
     * @return pipeline itself.
     *
     * @see #linkInsteadFromHead(String, String, Object, int)
     * @see #set(String, Object)
     * @see #set(String, Object, int)
     * @see #setFromHead(String, Object)
     * @see #setFromHead(String, Object, int)
     * @see #setIfContains(String, Object)
     * @see #setIfContains(String, Object, int)
     * @see #setIfContainsFromHead(String, Object)
     * @see #setIfContainsFromHead(String, Object, int)
     * @see #replace(String, String, Object)
     * @see #replace(String, String, Object, int)
     * @see #replaceFromHead(String, String, Object)
     * @see #replaceFromHead(String, String, Object, int)
     * @see #replaceIfContains(String, String, Object)
     * @see #replaceIfContainsFromHead(String, String, Object)
     * @see #replaceIfContainsFromHead(String, String, Object, int)
     */
    BasePipeline<E> replaceIfContains(String element, String name, E value, int index);

    /**
     * Method will look for {@code index} pipeline element named as in {@code element} param,
     * and change it name and value to given params.
     * use 0 as index to use first found element.
     * <br>
     * Index is decremented each time when iterator find element with matching name,
     * when index is equals to 0 iterator stops and change name and value of current element.
     * <br>
     * Method is iterating from head to tail of pipeline.
     * <br>
     * Use this same value as element and name to just change value of element.
     * Or use {@link #setIfContainsFromHead(String, Object, int)}
     * <br>
     * If there is no element with given name, method will do nothing.
     * {@link #replaceFromHead(String, String, Object, int)}
     *
     * @param element name of element to find.
     * @param name    name to set.
     * @param value   value to set.
     * @param index   index of element.
     *
     * @return pipeline itself.
     *
     * @see #linkInsteadFromHead(String, String, Object, int)
     * @see #set(String, Object)
     * @see #set(String, Object, int)
     * @see #setFromHead(String, Object)
     * @see #setFromHead(String, Object, int)
     * @see #setIfContains(String, Object)
     * @see #setIfContains(String, Object, int)
     * @see #setIfContainsFromHead(String, Object)
     * @see #setIfContainsFromHead(String, Object, int)
     * @see #replace(String, String, Object)
     * @see #replace(String, String, Object, int)
     * @see #replaceFromHead(String, String, Object)
     * @see #replaceFromHead(String, String, Object, int)
     * @see #replaceIfContains(String, String, Object)
     * @see #replaceIfContains(String, String, Object, int)
     * @see #replaceIfContainsFromHead(String, String, Object)
     */
    BasePipeline<E> replaceIfContainsFromHead(String element, String name, E value, int index);

    /**
     * Method will look for {@code index} pipeline element named as in {@code element} param,
     * and change it name and value to given params.
     * use 0 as index to use first found element.
     * <br>
     * Index is decremented each time when iterator find element with matching name,
     * when index is equals to 0 iterator stops and change name and value of current element.
     * <br>
     * Method is iterating from tail to head of pipeline.
     * <br>
     * Use this same value as element and name to just change value of element.
     * Or use {@link #setIfContainsFromTail(String, Object, int)}
     * <br>
     * If there is no element with given name, method will do nothing.
     * {@link #replaceFromTail(String, String, Object, int)}
     *
     * @param element name of element to find.
     * @param name    name to set.
     * @param value   value to set.
     * @param index   index of element.
     *
     * @return pipeline itself.
     *
     * @see #linkInsteadFromTail(String, String, Object, int)
     * @see #setFromTail(String, Object)
     * @see #setFromTail(String, Object, int)
     * @see #setIfContainsFromTail(String, Object)
     * @see #setIfContainsFromTail(String, Object, int)
     * @see #replaceFromTail(String, String, Object)
     * @see #replaceFromTail(String, String, Object, int)
     * @see #replaceIfContainsFromTail(String, String, Object)
     */
    BasePipeline<E> replaceIfContainsFromTail(String element, String name, E value, int index);

    /**
     * Method will look for first pipeline element named as in {@code before} param,
     * and add new element with given name and value BEFORE it.
     * <br>
     * Method is iterating from head to tail of pipeline.
     * <br>
     * If there is no element with given name, method will do nothing.
     * {@link #addBefore(String, String, Object)}
     *
     * @param before name of relative element.
     * @param name   name of new element.
     * @param value  value of new element.
     *
     * @return pipeline itself.
     *
     * @see #linkBeforeFromHead(String, String, Object, int)
     * @see #addBefore(String, String, Object)
     * @see #addBefore(String, String, Object, int)
     * @see #addBeforeFromHead(String, String, Object)
     * @see #addBeforeFromHead(String, String, Object, int)
     * @see #addBeforeIfContains(String, String, Object, int)
     * @see #addBeforeIfContainsFromHead(String, String, Object)
     * @see #addBeforeIfContainsFromHead(String, String, Object, int)
     */
    BasePipeline<E> addBeforeIfContains(String before, String name, E value);

    /**
     * Method will look for first pipeline element named as in {@code before} param,
     * and add new element with given name and value BEFORE it.
     * <br>
     * Method is iterating from head to tail of pipeline.
     * <br>
     * If there is no element with given name, method will do nothing.
     * {@link #addBeforeFromHead(String, String, Object)}
     *
     * @param before name of relative element.
     * @param name   name of new element.
     * @param value  value of new element.
     *
     * @return pipeline itself.
     *
     * @see #linkBeforeFromHead(String, String, Object, int)
     * @see #addBefore(String, String, Object)
     * @see #addBefore(String, String, Object, int)
     * @see #addBeforeFromHead(String, String, Object)
     * @see #addBeforeFromHead(String, String, Object, int)
     * @see #addBeforeIfContains(String, String, Object)
     * @see #addBeforeIfContains(String, String, Object, int)
     * @see #addBeforeIfContainsFromHead(String, String, Object, int)
     */
    BasePipeline<E> addBeforeIfContainsFromHead(String before, String name, E value);

    /**
     * Method will look for first pipeline element named as in {@code before} param,
     * and add new element with given name and value BEFORE it.
     * <br>
     * Method is iterating from tail to head of pipeline.
     * <br>
     * If there is no element with given name, method will do nothing.
     * {@link #addBeforeFromTail(String, String, Object)}
     *
     * @param before name of relative element.
     * @param name   name of new element.
     * @param value  value of new element.
     *
     * @return pipeline itself.
     *
     * @see #linkBeforeFromTail(String, String, Object, int)
     * @see #addBeforeFromTail(String, String, Object)
     * @see #addBeforeFromTail(String, String, Object, int)
     * @see #addBeforeIfContainsFromTail(String, String, Object, int)
     */
    BasePipeline<E> addBeforeIfContainsFromTail(String before, String name, E value);

    /**
     * Method will look for {@code index} pipeline element named as in {@code before} param,
     * and add new element with given name and value BEFORE it.
     * <br>
     * Index is decremented each time when iterator find element with matching name,
     * when index is equals to 0 iterator stops and add new element BEFORE current one.
     * <br>
     * Method is iterating from head to tail of pipeline.
     * <br>
     * If there is no element with given name, method will do nothing.
     * {@link #addBefore(String, String, Object, int)}
     *
     * @param before name of relative element.
     * @param name   name of new element.
     * @param value  value of new element.
     * @param index  index of relative element.
     *
     * @return pipeline itself.
     *
     * @see #linkBeforeFromHead(String, String, Object, int)
     * @see #addBefore(String, String, Object)
     * @see #addBefore(String, String, Object, int)
     * @see #addBeforeFromHead(String, String, Object)
     * @see #addBeforeFromHead(String, String, Object, int)
     * @see #addBeforeIfContains(String, String, Object)
     * @see #addBeforeIfContainsFromHead(String, String, Object)
     * @see #addBeforeIfContainsFromHead(String, String, Object, int)
     */
    BasePipeline<E> addBeforeIfContains(String before, String name, E value, int index);

    /**
     * Method will look for {@code index} pipeline element named as in {@code before} param,
     * and add new element with given name and value BEFORE it.
     * <br>
     * Index is decremented each time when iterator find element with matching name,
     * when index is equals to 0 iterator stops and add new element BEFORE current one.
     * <br>
     * Method is iterating from head to tail of pipeline.
     * <br>
     * If there is no element with given name, method will do nothing.
     * {@link #addBeforeFromHead(String, String, Object, int)}
     *
     * @param before name of relative element.
     * @param name   name of new element.
     * @param value  value of new element.
     * @param index  index of relative element.
     *
     * @return pipeline itself.
     *
     * @see #linkBeforeFromHead(String, String, Object, int)
     * @see #addBefore(String, String, Object)
     * @see #addBefore(String, String, Object, int)
     * @see #addBeforeFromHead(String, String, Object)
     * @see #addBeforeFromHead(String, String, Object, int)
     * @see #addBeforeIfContains(String, String, Object)
     * @see #addBeforeIfContains(String, String, Object, int)
     * @see #addBeforeIfContainsFromHead(String, String, Object)
     */
    BasePipeline<E> addBeforeIfContainsFromHead(String before, String name, E value, int index);

    /**
     * Method will look for {@code index} pipeline element named as in {@code before} param,
     * and add new element with given name and value BEFORE it.
     * <br>
     * Index is decremented each time when iterator find element with matching name,
     * when index is equals to 0 iterator stops and add new element BEFORE current one.
     * <br>
     * Method is iterating from tail to head of pipeline.
     * <br>
     * If there is no element with given name, method will do nothing.
     * {@link #addBeforeFromTail(String, String, Object, int)}
     *
     * @param before name of relative element.
     * @param name   name of new element.
     * @param value  value of new element.
     * @param index  index of relative element.
     *
     * @return pipeline itself.
     *
     * @see #linkBeforeFromTail(String, String, Object, int)
     * @see #addBeforeFromTail(String, String, Object)
     * @see #addBeforeFromTail(String, String, Object, int)
     * @see #addBeforeIfContainsFromTail(String, String, Object)
     */
    BasePipeline<E> addBeforeIfContainsFromTail(String before, String name, E value, int index);

    /**
     * Method will look for first pipeline element named as in {@code after} param,
     * and add new element with given name and value AFTER it.
     * <br>
     * Index is decremented each time when iterator find element with matching name,
     * when index is equals to 0 iterator stops and add new element AFTER current one.
     * <br>
     * Method is iterating from head to tail of pipeline.
     * <br>
     * If there is no element with given name, method will do nothing.
     * {@link #addAfter(String, String, Object)}
     *
     * @param after name of relative element.
     * @param name  name of new element.
     * @param value value of new element.
     *
     * @return pipeline itself.
     *
     * @see #linkAfterFromHead(String, String, Object, int)
     * @see #addAfter(String, String, Object)
     * @see #addAfter(String, String, Object, int)
     * @see #addAfterFromHead(String, String, Object)
     * @see #addAfterFromHead(String, String, Object, int)
     * @see #addAfterIfContains(String, String, Object, int)
     * @see #addAfterIfContainsFromHead(String, String, Object)
     * @see #addAfterIfContainsFromHead(String, String, Object, int)
     */
    BasePipeline<E> addAfterIfContains(String after, String name, E value);

    /**
     * Method will look for first pipeline element named as in {@code after} param,
     * and add new element with given name and value AFTER it.
     * <br>
     * Index is decremented each time when iterator find element with matching name,
     * when index is equals to 0 iterator stops and add new element AFTER current one.
     * <br>
     * Method is iterating from head to tail of pipeline.
     * <br>
     * If there is no element with given name, method will do nothing.
     * {@link #addAfterFromHead(String, String, Object)}
     *
     * @param after name of relative element.
     * @param name  name of new element.
     * @param value value of new element.
     *
     * @return pipeline itself.
     *
     * @see #linkAfterFromHead(String, String, Object, int)
     * @see #addAfter(String, String, Object)
     * @see #addAfter(String, String, Object, int)
     * @see #addAfterFromHead(String, String, Object)
     * @see #addAfterFromHead(String, String, Object, int)
     * @see #addAfterIfContains(String, String, Object)
     * @see #addAfterIfContains(String, String, Object, int)
     * @see #addAfterIfContainsFromHead(String, String, Object, int)
     */
    BasePipeline<E> addAfterIfContainsFromHead(String after, String name, E value);

    /**
     * Method will look for first pipeline element named as in {@code after} param,
     * and add new element with given name and value AFTER it.
     * <br>
     * Index is decremented each time when iterator find element with matching name,
     * when index is equals to 0 iterator stops and add new element AFTER current one.
     * <br>
     * Method is iterating from tail to head of pipeline.
     * <br>
     * If there is no element with given name, method will do nothing.
     * {@link #addAfterFromTail(String, String, Object)}
     *
     * @param after name of relative element.
     * @param name  name of new element.
     * @param value value of new element.
     *
     * @return pipeline itself.
     *
     * @see #linkAfterFromTail(String, String, Object, int)
     * @see #addAfterFromTail(String, String, Object)
     * @see #addAfterFromTail(String, String, Object, int)
     * @see #addAfterIfContainsFromTail(String, String, Object, int)
     */
    BasePipeline<E> addAfterIfContainsFromTail(String after, String name, E value);

    /**
     * Method will look for {@code index} pipeline element named as in {@code after} param,
     * and add new element with given name and value AFTER it.
     * <br>
     * Index is decremented each time when iterator find element with matching name,
     * when index is equals to 0 iterator stops and add new element AFTER current one.
     * <br>
     * Method is iterating from head to tail of pipeline.
     * <br>
     * If there is no element with given name, method will do nothing.
     * {@link #addAfter(String, String, Object, int)}
     *
     * @param after name of relative element.
     * @param name  name of new element.
     * @param value value of new element.
     * @param index index of relative element.
     *
     * @return pipeline itself.
     *
     * @see #linkAfterFromHead(String, String, Object, int)
     * @see #addAfter(String, String, Object)
     * @see #addAfter(String, String, Object, int)
     * @see #addAfterFromHead(String, String, Object)
     * @see #addAfterFromHead(String, String, Object, int)
     * @see #addAfterIfContains(String, String, Object)
     * @see #addAfterIfContainsFromHead(String, String, Object)
     * @see #addAfterIfContainsFromHead(String, String, Object, int)
     */
    BasePipeline<E> addAfterIfContains(String after, String name, E value, int index);

    /**
     * Method will look for {@code index} pipeline element named as in {@code after} param,
     * and add new element with given name and value AFTER it.
     * <br>
     * Index is decremented each time when iterator find element with matching name,
     * when index is equals to 0 iterator stops and add new element AFTER current one.
     * <br>
     * Method is iterating from head to tail of pipeline.
     * <br>
     * If there is no element with given name, method will do nothing.
     * {@link #addAfterFromHead(String, String, Object, int)}
     *
     * @param after name of relative element.
     * @param name  name of new element.
     * @param value value of new element.
     * @param index index of relative element.
     *
     * @return pipeline itself.
     *
     * @see #linkAfterFromHead(String, String, Object, int)
     * @see #addAfter(String, String, Object)
     * @see #addAfter(String, String, Object, int)
     * @see #addAfterFromHead(String, String, Object)
     * @see #addAfterFromHead(String, String, Object, int)
     * @see #addAfterIfContains(String, String, Object)
     * @see #addAfterIfContains(String, String, Object, int)
     * @see #addAfterIfContainsFromHead(String, String, Object)
     */
    BasePipeline<E> addAfterIfContainsFromHead(String after, String name, E value, int index);

    /**
     * Method will look for {@code index} pipeline element named as in {@code after} param,
     * and add new element with given name and value AFTER it.
     * <br>
     * Index is decremented each time when iterator find element with matching name,
     * when index is equals to 0 iterator stops and add new element AFTER current one.
     * <br>
     * Method is iterating from tail to head of pipeline.
     * <br>
     * If there is no element with given name, method will do nothing.
     * {@link #addAfterFromTail(String, String, Object, int)}
     *
     * @param after name of relative element.
     * @param name  name of new element.
     * @param value value of new element.
     * @param index index of relative element.
     *
     * @return pipeline itself.
     *
     * @see #linkAfterFromTail(String, String, Object, int)
     * @see #addAfterFromTail(String, String, Object)
     * @see #addAfterFromTail(String, String, Object, int)
     * @see #addAfterIfContainsFromTail(String, String, Object)
     */
    BasePipeline<E> addAfterIfContainsFromTail(String after, String name, E value, int index);

    /**
     * @return number of elements in pipeline.
     */
    int size();

    /**
     * @return true if pipeline is empty.
     */
    boolean isEmpty();

    /**
     * Check if pipeline contains element with this name.
     *
     * @param name name of element.
     *
     * @return true if pipeline contains at least one element with this name.
     */
    boolean containsKey(String name);

    /**
     * Check if pipeline contains element with this value.
     *
     * @param value value of element.
     *
     * @return true if pipeline contains at least one element with this value.
     */
    boolean containsValue(Object value);

    /**
     * Returns pipeline element value for given name.
     *
     * @param name name of element.
     *
     * @return element value or null if not present.
     */
    E get(String name);

    /**
     * @return array with all elements.
     *
     * @see Collection#toArray()
     */
    Object[] toArray();

    /**
     * @return array with all elements names.
     *
     * @see Collection#toArray()
     */
    String[] toNamesArray();

    /**
     * @param a   array to add values into it.
     * @param <T> type of array
     *
     * @return add all values to given array.
     *
     * @see Collection#toArray(Object[])
     */
    <T> T[] toArray(T[] a);

    /**
     * @return a stream.
     *
     * @see Collection#stream()
     */
    default Stream<E> stream()
    {
        return StreamSupport.stream(this.spliterator(), false);
    }

    /**
     * @return a stream.
     *
     * @see Collection#parallelStream()
     */
    default Stream<E> parallelStream()
    {
        return StreamSupport.stream(this.spliterator(), true);
    }
}
