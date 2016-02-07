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

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.utils.DioriteUtils;

/**
 * Based on {@link java.util.concurrent.ConcurrentLinkedDeque}
 * <br>
 * Basic implementation of {@link Pipeline} class.
 *
 * @param <E> type of elements stored in pipeline.
 */
@SuppressWarnings("ObjectEquality")
public class BasePipeline<E> implements Pipeline<E>
{
    @SuppressWarnings("rawtypes")
    private static final Node PREV_TERMINATOR, NEXT_TERMINATOR;
    private static final int HOPS = 2;
    private static final       sun.misc.Unsafe UNSAFE;
    private static final       long            headOffset;
    private static final       long            tailOffset;
    private transient volatile Node<E>         head;
    private transient volatile Node<E>         tail;

    public BasePipeline()
    {
        this.head = this.tail = new Node<>("", null);
    }

    public BasePipeline(final Map<? extends String, ? extends E> map)
    {
        Node<E> h = null, t = null;
        for (final Entry<? extends String, ? extends E> e : map.entrySet())
        {
            checkNotNull(e);
            final Node<E> newNode = new Node<>(e.getKey(), e.getValue());
            if (h == null)
            {
                h = t = newNode;
            }
            else
            {
                t.lazySetNext(newNode);
                newNode.lazySetPrev(t);
                t = newNode;
            }
        }
        this.initHeadTail(h, t);
    }

    @SuppressWarnings("unchecked")
    Node<E> prevTerminator()
    {
        return (Node<E>) PREV_TERMINATOR;
    }

    @SuppressWarnings("unchecked")
    Node<E> nextTerminator()
    {
        return (Node<E>) NEXT_TERMINATOR;
    }

    private void initHeadTail(Node<E> h, Node<E> t)
    {
        if (h == t)
        {
            if (h == null)
            {
                h = t = new Node<>("", null);
            }
            else
            {
                final Node<E> newNode = new Node<>("", null);
                t.lazySetNext(newNode);
                newNode.lazySetPrev(t);
                t = newNode;
            }
        }
        this.head = h;
        this.tail = t;
    }

    private void linkFirst(final String name, final E e)
    {
        checkNotNull(e);
        final Node<E> newNode = new Node<>(name, e);

        restartFromHead:
        while (true)
        {
            for (Node<E> h = this.head, p = h, q; ; )
            {
                if ((((q = p.prev)) != null) && (((q = (p = q).prev)) != null))
                {
                    p = (h != (h = this.head)) ? h : q;
                }
                else if (p.next == p)
                {
                    continue restartFromHead;
                }
                else
                {
                    newNode.lazySetNext(p);
                    if (p.casPrev(null, newNode))
                    {
                        if (p != h)
                        {
                            this.casHead(h, newNode);
                        }
                        return;
                    }
                }
            }
        }
    }

    private void linkLast(final String name, final E value)
    {
        checkNotNull(value);
        final Node<E> newNode = new Node<>(name, value);

        restartFromTail:
        while (true)
        {
            for (Node<E> t = this.tail, p = t, q; ; )
            {
                if ((((q = p.next)) != null) && (((q = (p = q).next)) != null))
                {
                    p = (t != (t = this.tail)) ? t : q;
                }
                else if (p.prev == p)
                {
                    continue restartFromTail;
                }
                else
                {
                    newNode.lazySetPrev(p);
                    if (p.casNext(null, newNode))
                    {
                        if (p != t)
                        {
                            this.casTail(t, newNode);
                        }
                        return;
                    }
                }
            }
        }
    }

    void unlink(final Node<E> x)
    {
        final Node<E> prev = x.prev;
        final Node<E> next = x.next;
        if (prev == null)
        {
            this.unlinkFirst(x, next);
        }
        else if (next == null)
        {
            this.unlinkLast(x, prev);
        }
        else
        {
            final Node<E> activePred;
            final Node<E> activeSucc;
            final boolean isFirst;
            final boolean isLast;
            int hops = 1;

            for (Node<E> p = prev; ; ++ hops)
            {
                if (p.item != null)
                {
                    activePred = p;
                    isFirst = false;
                    break;
                }
                final Node<E> q = p.prev;
                if (q == null)
                {
                    if (p.next == p)
                    {
                        return;
                    }
                    activePred = p;
                    isFirst = true;
                    break;
                }
                else if (p == q)
                {
                    return;
                }
                else
                {
                    p = q;
                }
            }

            for (Node<E> p = next; ; ++ hops)
            {
                if (p.item != null)
                {
                    activeSucc = p;
                    isLast = false;
                    break;
                }
                final Node<E> q = p.next;
                if (q == null)
                {
                    if (p.prev == p)
                    {
                        return;
                    }
                    activeSucc = p;
                    isLast = true;
                    break;
                }
                else if (p == q)
                {
                    return;
                }
                else
                {
                    p = q;
                }
            }

            if ((hops < HOPS) && (isFirst | isLast))
            {
                return;
            }

            this.skipDeletedSuccessors(activePred);
            this.skipDeletedPredecessors(activeSucc);

            if ((isFirst | isLast) && (activePred.next == activeSucc) && (activeSucc.prev == activePred) && (isFirst ? (activePred.prev == null) : (activePred.item != null)) && (isLast ? (activeSucc.next == null) : (activeSucc.item != null)))
            {

                this.updateHead();
                this.updateTail();

                x.lazySetPrev(isFirst ? this.prevTerminator() : x);
                x.lazySetNext(isLast ? this.nextTerminator() : x);
            }
        }
    }

    private void unlinkFirst(final Node<E> first, final Node<E> next)
    {
        for (Node<E> o = null, p = next, q; ; )
        {
            if ((p.item != null) || (((q = p.next)) == null))
            {
                if ((o != null) && (p.prev != p) && first.casNext(next, p))
                {
                    this.skipDeletedPredecessors(p);
                    if ((first.prev == null) && ((p.next == null) || (p.item != null)) && (p.prev == first))
                    {

                        this.updateHead();
                        this.updateTail();

                        o.lazySetNext(o);
                        o.lazySetPrev(this.prevTerminator());
                    }
                }
                return;
            }
            else if (p == q)
            {
                return;
            }
            else
            {
                o = p;
                p = q;
            }
        }
    }

    private void unlinkLast(final Node<E> last, final Node<E> prev)
    {
        for (Node<E> o = null, p = prev, q; ; )
        {
            if ((p.item != null) || (((q = p.prev)) == null))
            {
                if ((o != null) && (p.next != p) && last.casPrev(prev, p))
                {
                    this.skipDeletedSuccessors(p);
                    if ((last.next == null) && ((p.prev == null) || (p.item != null)) && (p.next == last))
                    {

                        this.updateHead();
                        this.updateTail();

                        o.lazySetPrev(o);
                        o.lazySetNext(this.nextTerminator());
                    }
                }
                return;
            }
            else if (p == q)
            {
                return;
            }
            else
            {
                o = p;
                p = q;
            }
        }
    }

    private void updateHead()
    {
        Node<E> h, p, q;
        restartFromHead:
        while (((h = this.head).item == null) && (((p = h.prev)) != null))
        {
            while (true)
            {
                if ((((q = p.prev)) == null) || (((q = (p = q).prev)) == null))
                {
                    if (this.casHead(h, p))
                    {
                        return;
                    }
                    else
                    {
                        continue restartFromHead;
                    }
                }
                else if (h != this.head)
                {
                    continue restartFromHead;
                }
                else
                {
                    p = q;
                }
            }
        }
    }

    private void updateTail()
    {
        Node<E> t, p, q;
        restartFromTail:
        while (((t = this.tail).item == null) && (((p = t.next)) != null))
        {
            while (true)
            {
                if ((((q = p.next)) == null) || (((q = (p = q).next)) == null))
                {
                    if (this.casTail(t, p))
                    {
                        return;
                    }
                    else
                    {
                        continue restartFromTail;
                    }
                }
                else if (t != this.tail)
                {
                    continue restartFromTail;
                }
                else
                {
                    p = q;
                }
            }
        }
    }

    private void skipDeletedPredecessors(final Node<E> x)
    {
        whileActive:
        do
        {
            final Node<E> prev = x.prev;
            Node<E> p = prev;
            while (true)
            {
                if (p.item != null)
                {
                    break;
                }
                final Node<E> q = p.prev;
                if (q == null)
                {
                    if (p.next == p)
                    {
                        continue whileActive;
                    }
                    break;
                }
                else if (p == q)
                {
                    continue whileActive;
                }
                else
                {
                    p = q;
                }
            }

            if ((prev == p) || x.casPrev(prev, p))
            {
                return;
            }

        } while ((x.item != null) || (x.next == null));
    }

    private void skipDeletedSuccessors(final Node<E> x)
    {
        whileActive:
        do
        {
            final Node<E> next = x.next;
            Node<E> p = next;
            while (true)
            {
                if (p.item != null)
                {
                    break;
                }
                final Node<E> q = p.next;
                if (q == null)
                {
                    if (p.prev == p)
                    {
                        continue whileActive;
                    }
                    break;
                }
                else if (p == q)
                {
                    continue whileActive;
                }
                else
                {
                    p = q;
                }
            }

            if ((next == p) || x.casNext(next, p))
            {
                return;
            }

        } while ((x.item != null) || (x.prev == null));
    }

    Node<E> succ(final Node<E> p)
    {
        final Node<E> q = p.next;
        return (p == q) ? this.first() : q;
    }

    Node<E> pred(final Node<E> p)
    {
        final Node<E> q = p.prev;
        return (p == q) ? this.last() : q;
    }

    Node<E> first()
    {
        restartFromHead:
        while (true)
        {
            for (Node<E> h = this.head, p = h, q; ; )
            {
                if ((((q = p.prev)) != null) && (((q = (p = q).prev)) != null))
                {
                    p = (h != (h = this.head)) ? h : q;
                }
                else if ((p == h) || this.casHead(h, p))
                {
                    return p;
                }
                else
                {
                    continue restartFromHead;
                }
            }
        }
    }

    Node<E> last()
    {
        restartFromTail:
        while (true)
        {
            for (Node<E> t = this.tail, p = t, q; ; )
            {
                if ((((q = p.next)) != null) && (((q = (p = q).next)) != null))
                {
                    p = (t != (t = this.tail)) ? t : q;
                }
                else if ((p == t) || this.casTail(t, p))
                {
                    return p;
                }
                else
                {
                    continue restartFromTail;
                }
            }
        }
    }

    @Override
    public Collection<E> toCollection()
    {
        final Collection<E> list = new ArrayList<>(20);
        for (Node<E> p = this.first(); p != null; p = this.succ(p))
        {
            final E item = p.item;
            if (item != null)
            {
                list.add(item);
            }
        }
        return list;
    }

    @Override
    public Collection<String> toNamesCollection()
    {
        final Collection<String> list = new ArrayList<>(20);
        for (Node<E> p = this.first(); p != null; p = this.succ(p))
        {
            if (p.item != null)
            {
                list.add(p.name);
            }
        }
        return list;
    }

    @Override
    public Set<Entry<String, E>> entrySet()
    {
        final Set<Entry<String, E>> set = new LinkedHashSet<>(20);
        for (Node<E> p = this.first(); p != null; p = this.succ(p))
        {
            final E item = p.item;
            if (item != null)
            {
                set.add(new SimpleEntry<>(p.name, item));
            }
        }
        return set;
    }

    @Override
    public E removeFirst()
    {
        for (Node<E> p = this.first(); p != null; p = this.succ(p))
        {
            final E item = p.item;
            if ((item != null) && p.casItem(item, null))
            {
                this.unlink(p);
                return item;
            }
        }
        return null;
    }

    @Override
    public E removeLast()
    {
        for (Node<E> p = this.last(); p != null; p = this.pred(p))
        {
            final E item = p.item;
            if ((item != null) && p.casItem(item, null))
            {
                this.unlink(p);
                return item;
            }
        }
        return null;
    }

    @Override
    public Iterator<Entry<String, E>> entriesIterator()
    {
        return new EntriesItr();
    }

    @Override
    public Iterator<Entry<String, E>> entriesDescendingIterator()
    {
        return new EntriesDescendingItr();
    }

    @Override
    public void clear()
    {
        //noinspection StatementWithEmptyBody
        while (this.removeFirst() != null)
        {
        }
    }

    @Override
    public Iterator<E> descendingIterator()
    {
        return new DescendingItr();
    }

    @Override
    public boolean linkInsteadFromTail(final String element, final String name, final E value, int index)
    {
        checkNotNull(value);
        for (Node<E> p = this.last(); p != null; p = this.pred(p))
        {
            final E item = p.item;
            if ((item != null) && p.name.equals(element) && (index-- <= 0) && p.casItem(item, value))
            {
                p.name = name;
                return true;  // true if element was replaced
            }
        }
        return false;
    }

    @Override
    public boolean linkInsteadFromHead(final String element, final String name, final E value, int index)
    {
        checkNotNull(value);
        for (Node<E> p = this.first(); p != null; p = this.succ(p))
        {
            final E item = p.item;
            if ((item != null) && p.name.equals(element) && (index-- <= 0) && p.casItem(item, value))
            {
                p.name = name;
                return true;  // true if element was replaced
            }
        }
        return false;
    }

    @Override
    public boolean linkAfterFromHead(final String after, final String name, final E value, int index)
    {
        checkNotNull(value);
        final Node<E> newNode = new Node<>(name, value);
        Node<E> p = this.first();
        for (; p != null; p = this.succ(p))
        {
            final E item = p.item;
            if ((item != null) && p.name.equals(after) && (index-- <= 0))
            {
                final Node<E> next = this.succ(p);
                newNode.lazySetPrev(p);
                if (p == this.tail)
                {
                    if (p.casNext(next, newNode))
                    {
                        this.casTail(this.tail, newNode);
                    }
                }
                else
                {
                    newNode.lazySetNext(next);
                    if (p.casNext(next, newNode))
                    {
                        next.casPrev(p, newNode);
                    }
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean linkAfterFromTail(final String after, final String name, final E value, int index)
    {
        checkNotNull(value);
        final Node<E> newNode = new Node<>(name, value);
        Node<E> p = this.last();
        Node<E> prev = p;
        for (; p != null; p = this.pred(p))
        {
            final E item = p.item;
            if ((item != null) && p.name.equals(after) && (index-- <= 0))
            {
                newNode.lazySetPrev(p);
                if (p == this.tail)
                {
                    if (p.casNext(null, newNode))
                    {
                        this.casTail(this.tail, newNode);
                    }
                }
                else
                {
                    newNode.lazySetNext(prev);
                    if (p.casNext(prev, newNode))
                    {
                        prev.casPrev(p, newNode);
                    }
                }
                return true;
            }
            prev = p;
        }
        return false;
    }

    @Override
    public boolean linkBeforeFromHead(final String before, final String name, final E value, int index)
    {
        checkNotNull(value);
        final Node<E> newNode = new Node<>(name, value);
        Node<E> p = this.first();
        Node<E> prev = p;
        for (; p != null; p = this.succ(p))
        {
            final E item = p.item;
            if ((item != null) && p.name.equals(before) && (index-- <= 0))
            {
                newNode.lazySetNext(p);
                if (prev == this.head)
                {
                    if (p.casPrev(prev, newNode))
                    {
                        this.casHead(this.head, newNode);
                    }
                }
                else
                {
                    newNode.lazySetPrev(prev);
                    if (prev.casNext(p, newNode))
                    {
                        p.casPrev(prev, newNode);
                    }
                }
                return true;
            }
            prev = p;
        }
        return false;
    }

    @Override
    public boolean linkBeforeFromTail(final String before, final String name, final E value, int index)
    {
        checkNotNull(value);
        final Node<E> newNode = new Node<>(name, value);
        Node<E> p = this.first();
        for (; p != null; p = this.succ(p))
        {
            final E item = p.item;
            if ((item != null) && p.name.equals(before) && (index-- <= 0))
            {
                final Node<E> next = this.pred(p);
                newNode.lazySetNext(p);
                if (p == this.head)
                {
                    if (p.casPrev(next, newNode))
                    {
                        this.casHead(this.head, newNode);
                    }
                }
                else
                {
                    newNode.lazySetPrev(next);
                    if (p.casPrev(next, newNode))
                    {
                        next.casNext(p, newNode);
                    }
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public String removeSingleOccurrenceFromTail(final Object o, int index)
    {
        checkNotNull(o);
        for (Node<E> p = this.last(); p != null; p = this.pred(p))
        {
            final E item = p.item;
            if ((item != null) && o.equals(item) && (index-- <= 0) && p.casItem(item, null))
            {
                this.unlink(p);
                return p.name;
            }
        }
        return null;
    }

    @Override
    public String removeLastOccurrence(final Object o)
    {
        return this.removeSingleOccurrenceFromTail(o, 0);
    }

    @Override
    public String removeSingleOccurrenceFromHead(final Object o, int index)
    {
        checkNotNull(o);
        for (Node<E> p = this.first(); p != null; p = this.succ(p))
        {
            final E item = p.item;
            if ((item != null) && o.equals(item) && (index-- <= 0) && p.casItem(item, null))
            {
                this.unlink(p);
                return p.name;
            }
        }
        return null;
    }

    @Override
    public String removeFirstOccurrence(final Object o)
    {
        return this.removeSingleOccurrenceFromHead(o, 0);
    }

    @Override
    public Collection<String> removeAllFromTail(final Object o, int limit)
    {
        checkNotNull(o);
        final Collection<String> result = new ArrayList<>((limit > 0) ? limit : 5);
        for (Node<E> p = this.last(); p != null; p = this.pred(p))
        {
            final E item = p.item;
            if ((item != null) && o.equals(item) && (limit-- != 0) && p.casItem(item, null))
            {
                this.unlink(p);
                result.add(p.name);
            }
        }
        return result;
    }

    @Override
    public Collection<E> removeAllFromTail(final String name, int limit)
    {
        checkNotNull(name);
        final Collection<E> result = new ArrayList<>((limit > 0) ? limit : 5);
        for (Node<E> p = this.last(); p != null; p = this.pred(p))
        {
            final E item = p.item;
            if ((item != null) && name.equals(p.name) && (limit-- != 0) && p.casItem(item, null))
            {
                this.unlink(p);
                result.add(item);
            }
        }
        return result;
    }

    @Override
    public Collection<String> removeAllFromHead(final Object o, int limit)
    {
        checkNotNull(o);
        final Collection<String> result = new ArrayList<>((limit > 0) ? limit : 5);
        for (Node<E> p = this.first(); p != null; p = this.succ(p))
        {
            final E item = p.item;
            if ((item != null) && o.equals(item) && (limit-- != 0) && p.casItem(item, null))
            {
                this.unlink(p);
                result.add(p.name);
            }
        }
        return result;
    }

    @Override
    public Collection<E> removeAllFromHead(final String name, int limit)
    {
        checkNotNull(name);
        final Collection<E> result = new ArrayList<>((limit > 0) ? limit : 5);
        for (Node<E> p = this.first(); p != null; p = this.succ(p))
        {
            final E item = p.item;
            if ((item != null) && name.equals(p.name) && (limit-- != 0) && p.casItem(item, null))
            {
                this.unlink(p);
                result.add(item);
            }
        }
        return result;
    }

    @Override
    public E removeSingleOccurrenceFromTail(final String name, int index)
    {
        checkNotNull(name);
        for (Node<E> p = this.last(); p != null; p = this.pred(p))
        {
            final E item = p.item;
            if ((item != null) && name.equals(p.name) && (index-- <= 0) && p.casItem(item, null))
            {
                this.unlink(p);
                return item;
            }
        }
        return null;
    }

    @Override
    public E removeLastOccurrence(final String name)
    {
        return this.removeSingleOccurrenceFromTail(name, 0);
    }

    @Override
    public E removeSingleOccurrenceFromHead(final String name, int index)
    {
        checkNotNull(name);
        for (Node<E> p = this.first(); p != null; p = this.succ(p))
        {
            final E item = p.item;
            if ((item != null) && name.equals(p.name) && (index-- <= 0) && p.casItem(item, null))
            {
                this.unlink(p);
                return item;
            }
        }
        return null;
    }

    @Override
    public E removeFirstOccurrence(final String name)
    {
        return this.removeSingleOccurrenceFromHead(name, 0);
    }

    @Override
    public Collection<E> removeAll(final String name)
    {
        return this.removeAllFromTail(name, - 1);
    }

    @Override
    public Collection<String> removeAll(final Object o)
    {
        return this.removeAllFromTail(o, - 1);
    }

    @Override
    public E remove(final String name)
    {
        return this.removeLastOccurrence(name);
    }

    @Override
    public void add(final String name, final E value)
    {
        this.addLast(name, value);
    }

    @Override
    public void addFirst(final String name, final E value)
    {
        this.linkFirst(name, value);
    }

    @Override
    public void addLast(final String name, final E value)
    {
        this.linkLast(name, value);
    }

    @Override
    public E getFirst()
    {
        for (Node<E> p = this.first(); p != null; p = this.succ(p))
        {
            final E item = p.item;
            if (item != null)
            {
                return item;
            }
        }
        return null;
    }

    @Override
    public String getNameOfFirst()
    {
        for (Node<E> p = this.first(); p != null; p = this.succ(p))
        {
            final E item = p.item;
            if (item != null)
            {
                return p.name;
            }
        }
        return null;
    }

    @Override
    public E getLast()
    {
        for (Node<E> p = this.last(); p != null; p = this.pred(p))
        {
            final E item = p.item;
            if (item != null)
            {
                return item;
            }
        }
        return null;
    }

    @Override
    public String getNameOfLast()
    {
        for (Node<E> p = this.last(); p != null; p = this.pred(p))
        {
            final E item = p.item;
            if (item != null)
            {
                return p.name;
            }
        }
        return null;
    }

    @Override
    public BasePipeline<E> set(final String element, final E value) throws NoSuchElementException
    {
        if (! this.linkInsteadFromHead(element, element, value, 0))
        {
            throw new NoSuchElementException("No element in pipeline named: " + element);
        }
        return this;
    }

    @Override
    public BasePipeline<E> setFromHead(final String element, final E value) throws NoSuchElementException
    {
        if (! this.linkInsteadFromHead(element, element, value, 0))
        {
            throw new NoSuchElementException("No element in pipeline named: " + element);
        }
        return this;
    }

    @Override
    public BasePipeline<E> setFromTail(final String element, final E value) throws NoSuchElementException
    {
        if (! this.linkInsteadFromTail(element, element, value, 0))
        {
            throw new NoSuchElementException("No element in pipeline named: " + element);
        }
        return this;
    }

    @Override
    public BasePipeline<E> set(final String element, final E value, final int index) throws NoSuchElementException
    {
        if (! this.linkInsteadFromHead(element, element, value, index))
        {
            throw new NoSuchElementException("No element in pipeline named: " + element);
        }
        return this;
    }

    @Override
    public BasePipeline<E> setFromHead(final String element, final E value, final int index) throws NoSuchElementException
    {
        if (! this.linkInsteadFromHead(element, element, value, index))
        {
            throw new NoSuchElementException("No element in pipeline named: " + element);
        }
        return this;
    }

    @Override
    public BasePipeline<E> setFromTail(final String element, final E value, final int index) throws NoSuchElementException
    {
        if (! this.linkInsteadFromTail(element, element, value, index))
        {
            throw new NoSuchElementException("No element in pipeline named: " + element);
        }
        return this;
    }

    @Override
    public BasePipeline<E> replace(final String element, final String name, final E value) throws NoSuchElementException
    {
        if (! this.linkInsteadFromHead(element, name, value, 0))
        {
            throw new NoSuchElementException("No element in pipeline named: " + element);
        }
        return this;
    }

    @Override
    public BasePipeline<E> replaceFromHead(final String element, final String name, final E value) throws NoSuchElementException
    {
        if (! this.linkInsteadFromHead(element, name, value, 0))
        {
            throw new NoSuchElementException("No element in pipeline named: " + element);
        }
        return this;
    }

    @Override
    public BasePipeline<E> replaceFromTail(final String element, final String name, final E value) throws NoSuchElementException
    {
        if (! this.linkInsteadFromTail(element, name, value, 0))
        {
            throw new NoSuchElementException("No element in pipeline named: " + element);
        }
        return this;
    }

    @Override
    public BasePipeline<E> replace(final String element, final String name, final E value, final int index) throws NoSuchElementException
    {
        if (! this.linkInsteadFromHead(element, name, value, index))
        {
            throw new NoSuchElementException("No element in pipeline named: " + element);
        }
        return this;
    }

    @Override
    public BasePipeline<E> replaceFromHead(final String element, final String name, final E value, final int index) throws NoSuchElementException
    {
        if (! this.linkInsteadFromHead(element, name, value, index))
        {
            throw new NoSuchElementException("No element in pipeline named: " + element);
        }
        return this;
    }

    @Override
    public BasePipeline<E> replaceFromTail(final String element, final String name, final E value, final int index) throws NoSuchElementException
    {
        if (! this.linkInsteadFromTail(element, name, value, index))
        {
            throw new NoSuchElementException("No element in pipeline named: " + element);
        }
        return this;
    }

    @Override
    public BasePipeline<E> addBefore(final String before, final String name, final E value) throws NoSuchElementException
    {
        if (! this.linkBeforeFromHead(before, name, value, 0))
        {
            throw new NoSuchElementException("No element in pipeline named: " + before);
        }
        return this;
    }

    @Override
    public BasePipeline<E> addBeforeFromHead(final String before, final String name, final E value) throws NoSuchElementException
    {
        if (! this.linkBeforeFromHead(before, name, value, 0))
        {
            throw new NoSuchElementException("No element in pipeline named: " + before);
        }
        return this;
    }

    @Override
    public BasePipeline<E> addBeforeFromTail(final String before, final String name, final E value) throws NoSuchElementException
    {
        if (! this.linkBeforeFromTail(before, name, value, 0))
        {
            throw new NoSuchElementException("No element in pipeline named: " + before);
        }
        return this;
    }

    @Override
    public BasePipeline<E> addBefore(final String before, final String name, final E value, final int index) throws NoSuchElementException
    {
        if (! this.linkBeforeFromHead(before, name, value, index))
        {
            throw new NoSuchElementException("No element in pipeline named: " + before);
        }
        return this;
    }

    @Override
    public BasePipeline<E> addBeforeFromHead(final String before, final String name, final E value, final int index) throws NoSuchElementException
    {
        if (! this.linkBeforeFromHead(before, name, value, index))
        {
            throw new NoSuchElementException("No element in pipeline named: " + before);
        }
        return this;
    }

    @Override
    public BasePipeline<E> addBeforeFromTail(final String before, final String name, final E value, final int index) throws NoSuchElementException
    {
        if (! this.linkBeforeFromTail(before, name, value, index))
        {
            throw new NoSuchElementException("No element in pipeline named: " + before);
        }
        return this;
    }

    @Override
    public BasePipeline<E> addAfter(final String after, final String name, final E value) throws NoSuchElementException
    {
        if (! this.linkAfterFromHead(after, name, value, 0))
        {
            throw new NoSuchElementException("No element in pipeline named: " + after);
        }
        return this;
    }

    @Override
    public BasePipeline<E> addAfterFromHead(final String after, final String name, final E value) throws NoSuchElementException
    {
        if (! this.linkAfterFromHead(after, name, value, 0))
        {
            throw new NoSuchElementException("No element in pipeline named: " + after);
        }
        return this;
    }

    @Override
    public BasePipeline<E> addAfterFromTail(final String after, final String name, final E value) throws NoSuchElementException
    {
        if (! this.linkAfterFromTail(after, name, value, 0))
        {
            throw new NoSuchElementException("No element in pipeline named: " + after);
        }
        return this;
    }

    @Override
    public BasePipeline<E> addAfter(final String after, final String name, final E value, final int index) throws NoSuchElementException
    {
        if (! this.linkAfterFromHead(after, name, value, index))
        {
            throw new NoSuchElementException("No element in pipeline named: " + after);
        }
        return this;
    }

    @Override
    public BasePipeline<E> addAfterFromHead(final String after, final String name, final E value, final int index) throws NoSuchElementException
    {
        if (! this.linkAfterFromHead(after, name, value, index))
        {
            throw new NoSuchElementException("No element in pipeline named: " + after);
        }
        return this;
    }

    @Override
    public BasePipeline<E> addAfterFromTail(final String after, final String name, final E value, final int index) throws NoSuchElementException
    {
        if (! this.linkAfterFromTail(after, name, value, index))
        {
            throw new NoSuchElementException("No element in pipeline named: " + after);
        }
        return this;
    }

    @Override
    public BasePipeline<E> setIfContains(final String element, final E value)
    {
        this.linkInsteadFromHead(element, element, value, 0);
        return this;
    }

    @Override
    public BasePipeline<E> setIfContainsFromHead(final String element, final E value)
    {
        this.linkInsteadFromHead(element, element, value, 0);
        return this;
    }

    @Override
    public BasePipeline<E> setIfContainsFromTail(final String element, final E value)
    {
        this.linkInsteadFromTail(element, element, value, 0);
        return this;
    }

    @Override
    public BasePipeline<E> setIfContains(final String element, final E value, final int index)
    {
        this.linkInsteadFromHead(element, element, value, index);
        return this;
    }

    @Override
    public BasePipeline<E> setIfContainsFromHead(final String element, final E value, final int index)
    {
        this.linkInsteadFromHead(element, element, value, index);
        return this;
    }

    @Override
    public BasePipeline<E> setIfContainsFromTail(final String element, final E value, final int index)
    {
        this.linkInsteadFromTail(element, element, value, index);
        return this;
    }

    @Override
    public BasePipeline<E> replaceIfContains(final String element, final String name, final E value)
    {
        this.linkInsteadFromHead(element, name, value, 0);
        return this;
    }

    @Override
    public BasePipeline<E> replaceIfContainsFromHead(final String element, final String name, final E value)
    {
        this.linkInsteadFromHead(element, name, value, 0);
        return this;
    }

    @Override
    public BasePipeline<E> replaceIfContainsFromTail(final String element, final String name, final E value)
    {
        this.linkInsteadFromTail(element, name, value, 0);
        return this;
    }

    @Override
    public BasePipeline<E> replaceIfContains(final String element, final String name, final E value, final int index)
    {
        this.linkInsteadFromHead(element, name, value, index);
        return this;
    }

    @Override
    public BasePipeline<E> replaceIfContainsFromHead(final String element, final String name, final E value, final int index)
    {
        this.linkInsteadFromHead(element, name, value, index);
        return this;
    }

    @Override
    public BasePipeline<E> replaceIfContainsFromTail(final String element, final String name, final E value, final int index)
    {
        this.linkInsteadFromTail(element, name, value, index);
        return this;
    }

    @Override
    public BasePipeline<E> addBeforeIfContains(final String before, final String name, final E value)
    {
        this.linkBeforeFromHead(before, name, value, 0);
        return this;
    }

    @Override
    public BasePipeline<E> addBeforeIfContainsFromHead(final String before, final String name, final E value)
    {
        this.linkBeforeFromHead(before, name, value, 0);
        return this;
    }

    @Override
    public BasePipeline<E> addBeforeIfContainsFromTail(final String before, final String name, final E value)
    {
        this.linkBeforeFromTail(before, name, value, 0);
        return this;
    }

    @Override
    public BasePipeline<E> addBeforeIfContains(final String before, final String name, final E value, final int index)
    {
        this.linkBeforeFromHead(before, name, value, index);
        return this;
    }

    @Override
    public BasePipeline<E> addBeforeIfContainsFromHead(final String before, final String name, final E value, final int index)
    {
        this.linkBeforeFromHead(before, name, value, index);
        return this;
    }

    @Override
    public BasePipeline<E> addBeforeIfContainsFromTail(final String before, final String name, final E value, final int index)
    {
        this.linkBeforeFromTail(before, name, value, index);
        return this;
    }

    @Override
    public BasePipeline<E> addAfterIfContains(final String after, final String name, final E value)
    {
        this.linkAfterFromHead(after, name, value, 0);
        return this;
    }

    @Override
    public BasePipeline<E> addAfterIfContainsFromHead(final String after, final String name, final E value)
    {
        this.linkAfterFromHead(after, name, value, 0);
        return this;
    }

    @Override
    public BasePipeline<E> addAfterIfContainsFromTail(final String after, final String name, final E value)
    {
        this.linkAfterFromTail(after, name, value, 0);
        return this;
    }

    @Override
    public BasePipeline<E> addAfterIfContains(final String after, final String name, final E value, final int index)
    {
        this.linkAfterFromHead(after, name, value, index);
        return this;
    }

    @Override
    public BasePipeline<E> addAfterIfContainsFromHead(final String after, final String name, final E value, final int index)
    {
        this.linkAfterFromHead(after, name, value, index);
        return this;
    }

    @Override
    public BasePipeline<E> addAfterIfContainsFromTail(final String after, final String name, final E value, final int index)
    {
        this.linkAfterFromTail(after, name, value, index);
        return this;
    }

    @Override
    public int size()
    {
        int count = 0;
        for (Node<E> p = this.first(); p != null; p = this.succ(p))
        {
            if (p.item != null)
            {
                if (++ count == Integer.MAX_VALUE)
                {
                    break;
                }
            }
        }
        return count;
    }

    @Override
    public boolean isEmpty()
    {
        return this.getFirst() == null;
    }

    @Override
    public boolean containsKey(final String name)
    {
        for (Node<E> p = this.first(); p != null; p = this.succ(p))
        {
            final E item = p.item;
            if ((item != null) && name.equals(p.name))
            {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean containsValue(final Object value)
    {
        if (value == null)
        {
            return false;
        }
        for (Node<E> p = this.first(); p != null; p = this.succ(p))
        {
            final E item = p.item;
            if ((item != null) && value.equals(item))
            {
                return true;
            }
        }
        return false;
    }

    @Override
    public E get(final String name)
    {
        for (Node<E> p = this.first(); p != null; p = this.succ(p))
        {
            final E item = p.item;
            if ((item != null) && name.equals(p.name))
            {
                return item;
            }
        }
        return null;
    }

    @Override
    public Object[] toArray()
    {
        return this.toCollection().toArray();
    }

    @Override
    public String[] toNamesArray()
    {
        final Collection<String> names = this.toNamesCollection();
        return names.toArray(new String[names.size()]);
    }

    @Override
    public <T> T[] toArray(final T[] a)
    {
        //noinspection SuspiciousToArrayCall
        return this.toCollection().toArray(a);
    }

    @Override
    public Iterator<E> iterator()
    {
        return new Itr();
    }

    @Override
    public Spliterator<E> spliterator()
    {
        return new PipelineSpliterator<>(this);
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("head", this.head).append("tail", this.tail).toString();
    }

    private boolean casHead(final Node<E> cmp, final Node<E> val)
    {
        return UNSAFE.compareAndSwapObject(this, headOffset, cmp, val);
    }

    private boolean casTail(final Node<E> cmp, final Node<E> val)
    {
        return UNSAFE.compareAndSwapObject(this, tailOffset, cmp, val);
    }

    @SuppressWarnings("ClassHasNoToStringMethod")
    private abstract class AbstractItr implements Iterator<E>
    {
        private Node<E> nextNode;
        private E       nextItem;
        private Node<E> lastRet;

        AbstractItr()
        {
            this.advance();
        }

        abstract Node<E> startNode();

        abstract Node<E> nextNode(Node<E> p);

        private void advance()
        {
            this.lastRet = this.nextNode;

            Node<E> p = (this.nextNode == null) ? this.startNode() : this.nextNode(this.nextNode);
            for (; ; p = this.nextNode(p))
            {
                if (p == null)
                {
                    this.nextNode = null;
                    this.nextItem = null;
                    break;
                }
                final E item = p.item;
                if (item != null)
                {
                    this.nextNode = p;
                    this.nextItem = item;
                    break;
                }
            }
        }

        @Override
        public boolean hasNext()
        {
            return this.nextItem != null;
        }

        @Override
        public E next()
        {
            final E item = this.nextItem;
            if (item == null)
            {
                throw new NoSuchElementException();
            }
            this.advance();
            return item;
        }

        @Override
        public void remove()
        {
            final Node<E> l = this.lastRet;
            if (l == null)
            {
                throw new IllegalStateException();
            }
            l.item = null;
            BasePipeline.this.unlink(l);
            this.lastRet = null;
        }
    }

    @SuppressWarnings("ClassHasNoToStringMethod")
    private abstract class AbstractEntriesItr implements Iterator<Entry<String, E>>
    {
        private Node<E>          nextNode;
        private Entry<String, E> nextItem;
        private Node<E>          lastRet;

        AbstractEntriesItr()
        {
            this.advance();
        }

        abstract Node<E> startNode();

        abstract Node<E> nextNode(Node<E> p);

        private void advance()
        {
            this.lastRet = this.nextNode;

            Node<E> p = (this.nextNode == null) ? this.startNode() : this.nextNode(this.nextNode);
            for (; ; p = this.nextNode(p))
            {
                if (p == null)
                {
                    this.nextNode = null;
                    this.nextItem = null;
                    break;
                }
                final E item = p.item;
                if (item != null)
                {
                    this.nextNode = p;
                    this.nextItem = new SimpleEntry<>(p.name, item);
                    break;
                }
            }
        }

        @Override
        public boolean hasNext()
        {
            return this.nextItem != null;
        }

        @Override
        public Entry<String, E> next()
        {
            final Entry<String, E> item = this.nextItem;
            if (item == null)
            {
                throw new NoSuchElementException();
            }
            this.advance();
            return item;
        }

        @Override
        public void remove()
        {
            final Node<E> l = this.lastRet;
            if (l == null)
            {
                throw new IllegalStateException();
            }
            l.item = null;
            BasePipeline.this.unlink(l);
            this.lastRet = null;
        }
    }

    private class EntriesItr extends AbstractEntriesItr
    {
        @Override
        Node<E> startNode()
        {
            return BasePipeline.this.first();
        }

        @Override
        Node<E> nextNode(final Node<E> p)
        {
            return BasePipeline.this.succ(p);
        }
    }

    private class EntriesDescendingItr extends AbstractEntriesItr
    {
        @Override
        Node<E> startNode()
        {
            return BasePipeline.this.last();
        }

        @Override
        Node<E> nextNode(final Node<E> p)
        {
            return BasePipeline.this.pred(p);
        }
    }

    private class Itr extends AbstractItr
    {
        @Override
        Node<E> startNode()
        {
            return BasePipeline.this.first();
        }

        @Override
        Node<E> nextNode(final Node<E> p)
        {
            return BasePipeline.this.succ(p);
        }
    }

    private class DescendingItr extends AbstractItr
    {
        @Override
        Node<E> startNode()
        {
            return BasePipeline.this.last();
        }

        @Override
        Node<E> nextNode(final Node<E> p)
        {
            return BasePipeline.this.pred(p);
        }
    }

    /**
     * Throws NullPointerException if argument is null.
     *
     * @param v the element
     */
    private static void checkNotNull(final Object v)
    {
        if (v == null)
        {
            throw new NullPointerException();
        }
    }

    @SuppressWarnings("ClassHasNoToStringMethod")
    static final class PipelineSpliterator<E> implements Spliterator<E>
    {
        static final int MAX_BATCH = 1 << 25;
        final BasePipeline<E> pipeline;
        Node<E> current;
        int     batch;
        boolean exhausted;

        PipelineSpliterator(final BasePipeline<E> pipeline)
        {
            this.pipeline = pipeline;
        }

        @Override
        public boolean tryAdvance(final Consumer<? super E> action)
        {
            Node<E> p;
            if (action == null)
            {
                throw new NullPointerException();
            }
            final BasePipeline<E> q = this.pipeline;
            if (! this.exhausted && ((((p = this.current)) != null) || (((p = q.first())) != null)))
            {
                E e;
                do
                {
                    e = p.item;
                    if (p == (p = p.next))
                    {
                        p = q.first();
                    }
                } while ((e == null) && (p != null));
                if ((this.current = p) == null)
                {
                    this.exhausted = true;
                }
                if (e != null)
                {
                    action.accept(e);
                    return true;
                }
            }
            return false;
        }

        @Override
        public void forEachRemaining(final Consumer<? super E> action)
        {
            Node<E> p;
            if (action == null)
            {
                throw new NullPointerException();
            }
            final BasePipeline<E> q = this.pipeline;
            if (! this.exhausted && ((((p = this.current)) != null) || (((p = q.first())) != null)))
            {
                this.exhausted = true;
                do
                {
                    final E e = p.item;
                    if (p == (p = p.next))
                    {
                        p = q.first();
                    }
                    if (e != null)
                    {
                        action.accept(e);
                    }
                } while (p != null);
            }
        }

        @Override
        public Spliterator<E> trySplit()
        {
            Node<E> p;
            final BasePipeline<E> q = this.pipeline;
            final int b = this.batch;
            final int n = (b <= 0) ? 1 : ((b >= MAX_BATCH) ? MAX_BATCH : (b + 1));
            if (! this.exhausted && ((((p = this.current)) != null) || (((p = q.first())) != null)))
            {
                if ((p.item == null) && (p == ((p = p.next))))
                {
                    this.current = p = q.first();
                }
                if ((p != null) && (p.next != null))
                {
                    final Object[] a = new Object[n];
                    int i = 0;
                    do
                    {
                        if ((a[i] = p.item) != null)
                        {
                            ++ i;
                        }
                        if (p == (p = p.next))
                        {
                            p = q.first();
                        }
                    } while ((p != null) && (i < n));
                    if ((this.current = p) == null)
                    {
                        this.exhausted = true;
                    }
                    if (i > 0)
                    {
                        this.batch = i;
                        return Spliterators.spliterator(a, 0, i, Spliterator.ORDERED | Spliterator.NONNULL | Spliterator.CONCURRENT);
                    }
                }
            }
            return null;
        }

        @Override
        public long estimateSize()
        {
            return Long.MAX_VALUE;
        }

        @Override
        public int characteristics()
        {
            return Spliterator.ORDERED | Spliterator.NONNULL | Spliterator.CONCURRENT;
        }
    }

    /**
     * Based on {@link java.util.concurrent.ConcurrentLinkedDeque.Node}
     */
    static class Node<E>
    {
        private static final AtomicInteger counter = new AtomicInteger(Integer.MIN_VALUE);
        private static final sun.misc.Unsafe UNSAFE;
        private static final long            prevOffset;
        private static final long            itemOffset;
        private static final long            nextOffset;
        String name;
        volatile Node<E> prev;
        volatile E       item;
        volatile Node<E> next;

        /**
         * Constructs a new node.  Uses relaxed write because item can
         * only be seen after publication via casNext or casPrev.
         */
        Node(final String name, final E item)
        {
            this.name = name;
            UNSAFE.putObject(this, itemOffset, item);
        }

        boolean casItem(final E cmp, final E val)
        {
            return UNSAFE.compareAndSwapObject(this, itemOffset, cmp, val);
        }

        // Unsafe mechanics

        void lazySetNext(final Node<E> val)
        {
            UNSAFE.putOrderedObject(this, nextOffset, val);
        }

        boolean casNext(final Node<E> cmp, final Node<E> val)
        {
            return UNSAFE.compareAndSwapObject(this, nextOffset, cmp, val);
        }

        void lazySetPrev(final Node<E> val)
        {
            UNSAFE.putOrderedObject(this, prevOffset, val);
        }

        boolean casPrev(final Node<E> cmp, final Node<E> val)
        {
            return UNSAFE.compareAndSwapObject(this, prevOffset, cmp, val);
        }

        static
        {
            try
            {
                UNSAFE = DioriteUtils.getUnsafe();
                final Class<?> k = Node.class;
                prevOffset = UNSAFE.objectFieldOffset(k.getDeclaredField("prev"));
                itemOffset = UNSAFE.objectFieldOffset(k.getDeclaredField("item"));
                nextOffset = UNSAFE.objectFieldOffset(k.getDeclaredField("next"));
            } catch (final Exception e)
            {
                throw new Error(e);
            }
        }

        @Override
        public String toString()
        {
            return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("name", this.name).append("item", this.item).append("prev", (this.prev == null) ? null : this.prev.name).append("next", (this.next == null) ? null : this.next.name).toString();
        }


    }

    static
    {
        PREV_TERMINATOR = new Node<>("", null);
        PREV_TERMINATOR.next = PREV_TERMINATOR;
        NEXT_TERMINATOR = new Node<>("", null);
        NEXT_TERMINATOR.prev = NEXT_TERMINATOR;
        try
        {
            UNSAFE = DioriteUtils.getUnsafe();
            final Class<?> k = BasePipeline.class;
            headOffset = UNSAFE.objectFieldOffset(k.getDeclaredField("head"));
            tailOffset = UNSAFE.objectFieldOffset(k.getDeclaredField("tail"));
        } catch (final Exception e)
        {
            throw new Error(e);
        }
    }
}
