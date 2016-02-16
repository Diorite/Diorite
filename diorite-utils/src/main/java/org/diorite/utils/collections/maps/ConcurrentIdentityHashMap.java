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

package org.diorite.utils.collections.maps;

import java.io.ObjectStreamField;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.AbstractMap;
import java.util.Arrays;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.Set;
import java.util.Spliterator;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CountedCompleter;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.DoubleBinaryOperator;
import java.util.function.DoubleConsumer;
import java.util.function.Function;
import java.util.function.IntBinaryOperator;
import java.util.function.IntConsumer;
import java.util.function.LongBinaryOperator;
import java.util.function.LongConsumer;
import java.util.function.ToDoubleBiFunction;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntBiFunction;
import java.util.function.ToIntFunction;
import java.util.function.ToLongBiFunction;
import java.util.function.ToLongFunction;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.StreamSupport;

import org.diorite.utils.DioriteUtils;

/**
 * Based on {@link java.util.concurrent.ConcurrentHashMap} and {@link java.util.IdentityHashMap}
 */
@SuppressWarnings("ALL")
public class ConcurrentIdentityHashMap<K, V> extends AbstractMap<K, V> implements ConcurrentMap<K, V>, Serializable
{
    /**
     * The largest possible (non-power of two) array size.
     * Needed by toArray and related methods.
     */
    static final int MAX_ARRAY_SIZE = Integer.MAX_VALUE - 8;


    /* ---------------- Constants -------------- */
    /**
     * The bin count threshold for using a tree rather than list for a
     * bin.  Bins are converted to trees when adding an element to a
     * bin with at least this many nodes. The value must be greater
     * than 2, and should be at least 8 to mesh with assumptions in
     * tree removal about conversion back to plain bins upon
     * shrinkage.
     */
    static final         int                 TREEIFY_THRESHOLD         = 8;
    /**
     * The bin count threshold for untreeifying a (split) bin during a
     * resize operation. Should be less than TREEIFY_THRESHOLD, and at
     * most 6 to mesh with shrinkage detection under removal.
     */
    static final         int                 UNTREEIFY_THRESHOLD       = 6;
    /**
     * The smallest table capacity for which bins may be treeified.
     * (Otherwise the table is resized if too many nodes in a bin.)
     * The value should be at least 4 * TREEIFY_THRESHOLD to avoid
     * conflicts between resizing and treeification thresholds.
     */
    static final         int                 MIN_TREEIFY_CAPACITY      = 64;
    /*
     * Encodings for Node hash fields. See above for explanation.
     */
    static final         int                 MOVED                     = - 1; // hash for forwarding nodes
    static final         int                 TREEBIN                   = - 2; // hash for roots of trees
    static final         int                 RESERVED                  = - 3; // hash for transient reservations
    static final         int                 HASH_BITS                 = 0x7fffffff; // usable bits of normal node hash
    /**
     * Number of CPUS, to place bounds on some sizings
     */
    static final         int                 NCPU                      = Runtime.getRuntime().availableProcessors();
    private static final long                serialVersionUID          = 0;
    /**
     * The largest possible table capacity.  This value must be
     * exactly 1<<30 to stay within Java array allocation and indexing
     * bounds for power of two table sizes, and is further required
     * because the top two bits of 32bit hash fields are used for
     * control purposes.
     */
    private static final int                 MAXIMUM_CAPACITY          = 1 << 30;
    /**
     * The default initial table capacity.  Must be a power of 2
     * (i.e., at least 1) and at most MAXIMUM_CAPACITY.
     */
    private static final int                 DEFAULT_CAPACITY          = 16;
    /**
     * The default concurrency level for this table. Unused but
     * defined for compatibility with previous versions of this class.
     */
    private static final int                 DEFAULT_CONCURRENCY_LEVEL = 16;
    /**
     * The load factor for this table. Overrides of this value in
     * constructors affect only the initial table capacity.  The
     * actual floating point value isn't normally used -- it is
     * simpler to use expressions such as {@code n - (n >>> 2)} for
     * the associated resizing threshold.
     */
    private static final float               LOAD_FACTOR               = 0.75f;
    /**
     * Minimum number of rebinnings per transfer step. Ranges are
     * subdivided to allow multiple resizer threads.  This value
     * serves as a lower bound to avoid resizers encountering
     * excessive memory contention.  The value should be at least
     * DEFAULT_CAPACITY.
     */
    private static final int                 MIN_TRANSFER_STRIDE       = 16;
    /**
     * The number of bits used for generation stamp in sizeCtl.
     * Must be at least 6 for 32bit arrays.
     */
    private static final int                 RESIZE_STAMP_BITS         = 16;
    /**
     * The maximum number of threads that can help resize.
     * Must fit in 32 - RESIZE_STAMP_BITS bits.
     */
    private static final int                 MAX_RESIZERS              = (1 << (32 - RESIZE_STAMP_BITS)) - 1;
    /**
     * The bit shift for recording size stamp in sizeCtl.
     */
    private static final int                 RESIZE_STAMP_SHIFT        = 32 - RESIZE_STAMP_BITS;
    /**
     * For serialization compatibility.
     */
    private static final ObjectStreamField[] serialPersistentFields    = {new ObjectStreamField("segments", Segment[].class), new ObjectStreamField("segmentMask", Integer.TYPE), new ObjectStreamField("segmentShift", Integer.TYPE)};

    /* ---------------- Nodes -------------- */
    // Unsafe mechanics
    private static final sun.misc.Unsafe U;

    /* ---------------- Static utilities -------------- */
    private static final long SIZECTL;
    private static final long TRANSFERINDEX;
    private static final long BASECOUNT;
    private static final long CELLSBUSY;

    /* ---------------- Table element access -------------- */

    /*
     * Volatile access methods are used for table elements as well as
     * elements of in-progress next table while resizing.  All uses of
     * the tab arguments must be null checked by callers.  All callers
     * also paranoically precheck that tab's length is not zero (or an
     * equivalent check), thus ensuring that any index argument taking
     * the form of a hash value anded with (length - 1) is a valid
     * index.  Note that, to be correct wrt arbitrary concurrency
     * errors by users, these checks must operate on local variables,
     * which accounts for some odd-looking inline assignments below.
     * Note that calls to setTabAt always occur within locked regions,
     * and so in principle require only release ordering, not
     * full volatile semantics, but are currently coded as volatile
     * writes to be conservative.
     */
    private static final long CELLVALUE;
    private static final long ABASE;
    private static final int  ASHIFT;

    /* ---------------- Fields -------------- */
    /**
     * The array of bins. Lazily initialized upon first insertion.
     * Size is always a power of two. Accessed directly by iterators.
     */
    transient volatile Node<K, V>[] table;

    /**
     * The next table to use; non-null only while resizing.
     */
    private transient volatile Node<K, V>[] nextTable;

    /**
     * Base counter value, used mainly when there is no contention,
     * but also as a fallback during table initialization
     * races. Updated via CAS.
     */
    private transient volatile long baseCount;

    /**
     * Table initialization and resizing control.  When negative, the
     * table is being initialized or resized: -1 for initialization,
     * else -(1 + the number of active resizing threads).  Otherwise,
     * when table is null, holds the initial table size to use upon
     * creation, or 0 for default. After initialization, holds the
     * next element count value upon which to resize the table.
     */
    private transient volatile int sizeCtl;

    /**
     * The next table index (plus one) to split while resizing.
     */
    private transient volatile int transferIndex;

    /**
     * Spinlock (locked via CAS) used when resizing and/or creating CounterCells.
     */
    private transient volatile int cellsBusy;

    /**
     * Table of counter cells. When non-null, size is a power of 2.
     */
    private transient volatile CounterCell[] counterCells;

    // views
    private transient KeySetView<K, V>   keySet;
    private transient ValuesView<K, V>   values;
    private transient EntrySetView<K, V> entrySet;


    /* ---------------- Public operations -------------- */

    /**
     * Creates a new, empty map with the default initial table size (16).
     */
    public ConcurrentIdentityHashMap()
    {
    }

    /**
     * Creates a new, empty map with an initial table size
     * accommodating the specified number of elements without the need
     * to dynamically resize.
     *
     * @param initialCapacity The implementation performs internal
     *                        sizing to accommodate this many elements.
     *
     * @throws IllegalArgumentException if the initial capacity of
     *                                  elements is negative
     */
    public ConcurrentIdentityHashMap(final int initialCapacity)
    {
        if (initialCapacity < 0)
        {
            throw new IllegalArgumentException();
        }
        this.sizeCtl = ((initialCapacity >= (MAXIMUM_CAPACITY >>> 1)) ? MAXIMUM_CAPACITY : tableSizeFor(initialCapacity + (initialCapacity >>> 1) + 1));
    }

    /**
     * Creates a new map with the same mappings as the given map.
     *
     * @param m the map
     */
    public ConcurrentIdentityHashMap(final Map<? extends K, ? extends V> m)
    {
        this.sizeCtl = DEFAULT_CAPACITY;
        this.putAll(m);
    }

    /**
     * Creates a new, empty map with an initial table size based on
     * the given number of elements ({@code initialCapacity}) and
     * initial table density ({@code loadFactor}).
     *
     * @param initialCapacity the initial capacity. The implementation
     *                        performs internal sizing to accommodate this many elements,
     *                        given the specified load factor.
     * @param loadFactor      the load factor (table density) for
     *                        establishing the initial table size
     *
     * @throws IllegalArgumentException if the initial capacity of
     *                                  elements is negative or the load factor is nonpositive
     * @since 1.6
     */
    public ConcurrentIdentityHashMap(final int initialCapacity, final float loadFactor)
    {
        this(initialCapacity, loadFactor, 1);
    }

    /**
     * Creates a new, empty map with an initial table size based on
     * the given number of elements ({@code initialCapacity}), table
     * density ({@code loadFactor}), and number of concurrently
     * updating threads ({@code concurrencyLevel}).
     *
     * @param initialCapacity  the initial capacity. The implementation
     *                         performs internal sizing to accommodate this many elements,
     *                         given the specified load factor.
     * @param loadFactor       the load factor (table density) for
     *                         establishing the initial table size
     * @param concurrencyLevel the estimated number of concurrently
     *                         updating threads. The implementation may use this value as
     *                         a sizing hint.
     *
     * @throws IllegalArgumentException if the initial capacity is
     *                                  negative or the load factor or concurrencyLevel are
     *                                  nonpositive
     */
    public ConcurrentIdentityHashMap(int initialCapacity, final float loadFactor, final int concurrencyLevel)
    {
        if (! (loadFactor > 0.0f) || (initialCapacity < 0) || (concurrencyLevel <= 0))
        {
            throw new IllegalArgumentException();
        }
        if (initialCapacity < concurrencyLevel)   // Use at least as many bins
        {
            initialCapacity = concurrencyLevel;   // as estimated threads
        }
        final long size = (long) (1.0 + ((long) initialCapacity / loadFactor));
        this.sizeCtl = (size >= (long) MAXIMUM_CAPACITY) ? MAXIMUM_CAPACITY : tableSizeFor((int) size);
    }

    // Original (since JDK1.2) Map methods

    @Override
    public int size()
    {
        final long n = this.sumCount();
        return ((n < 0L) ? 0 : ((n > (long) Integer.MAX_VALUE) ? Integer.MAX_VALUE : (int) n));
    }

    @Override
    public boolean isEmpty()
    {
        return this.sumCount() <= 0L; // ignore transient negative values
    }

    /**
     * Returns {@code true} if this map maps one or more keys to the
     * specified value. Note: This method may require a full traversal
     * of the map, and is much slower than method {@code containsKey}.
     *
     * @param value value whose presence in this map is to be tested
     *
     * @return {@code true} if this map maps one or more keys to the
     * specified value
     *
     * @throws NullPointerException if the specified value is null
     */
    @Override
    public boolean containsValue(final Object value)
    {
        if (value == null)
        {
            throw new NullPointerException();
        }
        final Node<K, V>[] t;
        if ((t = this.table) != null)
        {
            final Traverser<K, V> it = new Traverser<>(t, t.length, 0, t.length);
            for (Node<K, V> p; (p = it.advance()) != null; )
            {
                final V v;
                if ((((v = p.val)) == value) || ((v != null) && value.equals(v)))
                {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Tests if the specified object is a key in this table.
     *
     * @param key possible key
     *
     * @return {@code true} if and only if the specified object
     * is a key in this table, as determined by the
     * {@code equals} method; {@code false} otherwise
     *
     * @throws NullPointerException if the specified key is null
     */
    @Override
    public boolean containsKey(final Object key)
    {
        return this.get(key) != null;
    }

    /**
     * Returns the value to which the specified key is mapped,
     * or {@code null} if this map contains no mapping for the key.
     * <br>
     * <p>More formally, if this map contains a mapping from a key
     * {@code k} to a value {@code v} such that {@code key.equals(k)},
     * then this method returns {@code v}; otherwise it returns
     * {@code null}.  (There can be at most one such mapping.)
     *
     * @throws NullPointerException if the specified key is null
     */
    @Override
    public V get(final Object key)
    {
        final Node<K, V>[] tab;
        Node<K, V> e;
        final Node<K, V> p;
        final int n;
        final int eh;
        K ek;
        final int h = spread(key.hashCode());
        if ((((tab = this.table)) != null) && (((n = tab.length)) > 0) &&
                    (((e = tabAt(tab, (n - 1) & h))) != null))
        {
            if ((eh = e.hash) == h)
            {
                if (((e.key) == key))
                {
                    return e.val;
                }
            }
            else if (eh < 0)
            {
                return (((p = e.find(h, key))) != null) ? p.val : null;
            }
            while ((e = e.next) != null)
            {
                if ((e.hash == h) && (((e.key) == key)))
                {
                    return e.val;
                }
            }
        }
        return null;
    }

    /**
     * Maps the specified key to the specified value in this table.
     * Neither the key nor the value can be null.
     * <br>
     * <p>The value can be retrieved by calling the {@code get} method
     * with a key that is equal to the original key.
     *
     * @param key   key with which the specified value is to be associated
     * @param value value to be associated with the specified key
     *
     * @return the previous value associated with {@code key}, or
     * {@code null} if there was no mapping for {@code key}
     *
     * @throws NullPointerException if the specified key or value is null
     */
    @Override
    public V put(final K key, final V value)
    {
        return this.putVal(key, value, false);
    }

    /**
     * Removes the key (and its corresponding value) from this map.
     * This method does nothing if the key is not in the map.
     *
     * @param key the key that needs to be removed
     *
     * @return the previous value associated with {@code key}, or
     * {@code null} if there was no mapping for {@code key}
     *
     * @throws NullPointerException if the specified key is null
     */
    @Override
    public V remove(final Object key)
    {
        return this.replaceNode(key, null, null);
    }

    /**
     * Copies all of the mappings from the specified map to this one.
     * These mappings replace any mappings that this map had for any of the
     * keys currently in the specified map.
     *
     * @param m mappings to be stored in this map
     */
    @Override
    public void putAll(final Map<? extends K, ? extends V> m)
    {
        this.tryPresize(m.size());
        for (final Map.Entry<? extends K, ? extends V> e : m.entrySet())
        {
            this.putVal(e.getKey(), e.getValue(), false);
        }
    }

    /**
     * Removes all of the mappings from this map.
     */
    @Override
    public void clear()
    {
        long delta = 0L; // negative number of deletions
        int i = 0;
        Node<K, V>[] tab = this.table;
        while ((tab != null) && (i < tab.length))
        {
            final int fh;
            final Node<K, V> f = tabAt(tab, i);
            if (f == null)
            {
                ++ i;
            }
            else if ((fh = f.hash) == MOVED)
            {
                tab = this.helpTransfer(tab, f);
                i = 0; // restart
            }
            else
            {
                synchronized (f)
                {
                    if (tabAt(tab, i) == f)
                    {
                        Node<K, V> p = ((fh >= 0) ? f : ((f instanceof TreeBin) ? ((TreeBin<K, V>) f).first : null));
                        while (p != null)
                        {
                            -- delta;
                            p = p.next;
                        }
                        setTabAt(tab, i++, null);
                    }
                }
            }
        }
        if (delta != 0L)
        {
            this.addCount(delta, - 1);
        }
    }

    /**
     * Returns a {@link Set} view of the keys contained in this map.
     * The set is backed by the map, so changes to the map are
     * reflected in the set, and vice-versa. The set supports element
     * removal, which removes the corresponding mapping from this map,
     * via the {@code Iterator.remove}, {@code Set.remove},
     * {@code removeAll}, {@code retainAll}, and {@code clear}
     * operations.  It does not support the {@code add} or
     * {@code addAll} operations.
     * <br>
     * <p>The view's iterators and spliterators are
     * <a href="package-summary.html#Weakly"><i>weakly consistent</i></a>.
     * <br>
     * <p>The view's {@code spliterator} reports {@link Spliterator#CONCURRENT},
     * {@link Spliterator#DISTINCT}, and {@link Spliterator#NONNULL}.
     *
     * @return the set view
     */
    @Override
    public KeySetView<K, V> keySet()
    {
        final KeySetView<K, V> ks;
        return (((ks = this.keySet)) != null) ? ks : ((this.keySet = new KeySetView<>(this, null)));
    }

    /**
     * Returns a {@link Collection} view of the values contained in this map.
     * The collection is backed by the map, so changes to the map are
     * reflected in the collection, and vice-versa.  The collection
     * supports element removal, which removes the corresponding
     * mapping from this map, via the {@code Iterator.remove},
     * {@code Collection.remove}, {@code removeAll},
     * {@code retainAll}, and {@code clear} operations.  It does not
     * support the {@code add} or {@code addAll} operations.
     * <br>
     * <p>The view's iterators and spliterators are
     * <a href="package-summary.html#Weakly"><i>weakly consistent</i></a>.
     * <br>
     * <p>The view's {@code spliterator} reports {@link Spliterator#CONCURRENT}
     * and {@link Spliterator#NONNULL}.
     *
     * @return the collection view
     */
    @Override
    public Collection<V> values()
    {
        final ValuesView<K, V> vs;
        return (((vs = this.values)) != null) ? vs : ((this.values = new ValuesView<>(this)));
    }

    /**
     * Returns a {@link Set} view of the mappings contained in this map.
     * The set is backed by the map, so changes to the map are
     * reflected in the set, and vice-versa.  The set supports element
     * removal, which removes the corresponding mapping from the map,
     * via the {@code Iterator.remove}, {@code Set.remove},
     * {@code removeAll}, {@code retainAll}, and {@code clear}
     * operations.
     * <br>
     * <p>The view's iterators and spliterators are
     * <a href="package-summary.html#Weakly"><i>weakly consistent</i></a>.
     * <br>
     * <p>The view's {@code spliterator} reports {@link Spliterator#CONCURRENT},
     * {@link Spliterator#DISTINCT}, and {@link Spliterator#NONNULL}.
     *
     * @return the set view
     */
    @Override
    public Set<Map.Entry<K, V>> entrySet()
    {
        final EntrySetView<K, V> es;
        return (((es = this.entrySet)) != null) ? es : ((this.entrySet = new EntrySetView<>(this)));
    }

    /**
     * Compares the specified object with this map for equality.
     * Returns {@code true} if the given object is a map with the same
     * mappings as this map.  This operation may return misleading
     * results if either map is concurrently modified during execution
     * of this method.
     *
     * @param o object to be compared for equality with this map
     *
     * @return {@code true} if the specified object is equal to this map
     */
    public boolean equals(final Object o)
    {
        if (o != this)
        {
            if (! (o instanceof Map))
            {
                return false;
            }
            final Map<?, ?> m = (Map<?, ?>) o;
            final Node<K, V>[] t;
            final int f = (((t = this.table)) == null) ? 0 : t.length;
            final Traverser<K, V> it = new Traverser<>(t, f, 0, f);
            for (Node<K, V> p; (p = it.advance()) != null; )
            {
                final V val = p.val;
                final Object v = m.get(p.key);
                if ((v == null) || (v != val))
                {
                    return false;
                }
            }
            for (final Map.Entry<?, ?> e : m.entrySet())
            {
                final Object mk;
                final Object mv;
                final Object v;
                if ((((mk = e.getKey())) == null) ||
                            (((mv = e.getValue())) == null) ||
                            (((v = this.get(mk))) == null) ||
                            (mv != v))
                {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Returns the hash code value for this {@link Map}, i.e.,
     * the sum of, for each key-value pair in the map,
     * {@code key.hashCode() ^ value.hashCode()}.
     *
     * @return the hash code value for this map
     */
    public int hashCode()
    {
        int h = 0;
        final Node<K, V>[] t;
        if ((t = this.table) != null)
        {
            final Traverser<K, V> it = new Traverser<>(t, t.length, 0, t.length);
            for (Node<K, V> p; (p = it.advance()) != null; )
            {
                h += p.key.hashCode() ^ p.val.hashCode();
            }
        }
        return h;
    }

    /**
     * Returns a string representation of this map.  The string
     * representation consists of a list of key-value mappings (in no
     * particular order) enclosed in braces ("{@code {}}").  Adjacent
     * mappings are separated by the characters {@code ", "} (comma
     * and space).  Each key-value mapping is rendered as the key
     * followed by an equals sign ("{@code =}") followed by the
     * associated value.
     *
     * @return a string representation of this map
     */
    public String toString()
    {
        final Node<K, V>[] t;
        final int f = (((t = this.table)) == null) ? 0 : t.length;
        final Traverser<K, V> it = new Traverser<>(t, f, 0, f);
        final StringBuilder sb = new StringBuilder();
        sb.append('{');
        Node<K, V> p;
        if ((p = it.advance()) != null)
        {
            while (true)
            {
                final K k = p.key;
                final V v = p.val;
                sb.append((k == this) ? "(this Map)" : k);
                sb.append('=');
                sb.append((v == this) ? "(this Map)" : v);
                if ((p = it.advance()) == null)
                {
                    break;
                }
                sb.append(',').append(' ');
            }
        }
        return sb.append('}').toString();
    }

    /**
     * Implementation for put and putIfAbsent
     */
    final V putVal(final K key, final V value, final boolean onlyIfAbsent)
    {
        if ((key == null) || (value == null))
        {
            throw new NullPointerException();
        }
        final int hash = spread(key.hashCode());
        int binCount = 0;
        for (Node<K, V>[] tab = this.table; ; )
        {
            final Node<K, V> f;
            final int n;
            final int i;
            final int fh;
            if ((tab == null) || (((n = tab.length)) == 0))
            {
                tab = this.initTable();
            }
            else if ((f = tabAt(tab, i = (n - 1) & hash)) == null)
            {
                if (casTabAt(tab, i, null, new Node<>(hash, key, value, null)))
                {
                    break;                   // no lock when adding to empty bin
                }
            }
            else if ((fh = f.hash) == MOVED)
            {
                tab = this.helpTransfer(tab, f);
            }
            else
            {
                V oldVal = null;
                synchronized (f)
                {
                    if (tabAt(tab, i) == f)
                    {
                        if (fh >= 0)
                        {
                            binCount = 1;
                            for (Node<K, V> e = f; ; ++ binCount)
                            {
                                final K ek;
                                if ((e.hash == hash) && ((((ek = e.key)) == key) || ((ek != null) && key.equals(ek))))
                                {
                                    oldVal = e.val;
                                    if (! onlyIfAbsent)
                                    {
                                        e.val = value;
                                    }
                                    break;
                                }
                                final Node<K, V> pred = e;
                                if ((e = e.next) == null)
                                {
                                    pred.next = new Node<>(hash, key, value, null);
                                    break;
                                }
                            }
                        }
                        else if (f instanceof TreeBin)
                        {
                            final Node<K, V> p;
                            binCount = 2;
                            if ((p = ((TreeBin<K, V>) f).putTreeVal(hash, key, value)) != null)
                            {
                                oldVal = p.val;
                                if (! onlyIfAbsent)
                                {
                                    p.val = value;
                                }
                            }
                        }
                    }
                }
                if (binCount != 0)
                {
                    if (binCount >= TREEIFY_THRESHOLD)
                    {
                        this.treeifyBin(tab, i);
                    }
                    if (oldVal != null)
                    {
                        return oldVal;
                    }
                    break;
                }
            }
        }
        this.addCount(1L, binCount);
        return null;
    }

    /**
     * Implementation for the four public remove/replace methods:
     * Replaces node value with v, conditional upon match of cv if
     * non-null.  If resulting value is null, delete.
     */
    final V replaceNode(final Object key, final V value, final Object cv)
    {
        final int hash = spread(key.hashCode());
        for (Node<K, V>[] tab = this.table; ; )
        {
            final Node<K, V> f;
            final int n;
            final int i;
            final int fh;
            if ((tab == null) || (((n = tab.length)) == 0) ||
                        (((f = tabAt(tab, i = (n - 1) & hash))) == null))
            {
                break;
            }
            else if ((fh = f.hash) == MOVED)
            {
                tab = this.helpTransfer(tab, f);
            }
            else
            {
                V oldVal = null;
                boolean validated = false;
                synchronized (f)
                {
                    if (tabAt(tab, i) == f)
                    {
                        if (fh >= 0)
                        {
                            validated = true;
                            for (Node<K, V> e = f, pred = null; ; )
                            {
                                final K ek;
                                if ((e.hash == hash) && ((((ek = e.key)) == key) || ((ek != null) && key.equals(ek))))
                                {
                                    final V ev = e.val;
                                    if ((cv == null) || (cv == ev) ||
                                                ((ev != null) && cv.equals(ev)))
                                    {
                                        oldVal = ev;
                                        if (value != null)
                                        {
                                            e.val = value;
                                        }
                                        else if (pred != null)
                                        {
                                            pred.next = e.next;
                                        }
                                        else
                                        {
                                            setTabAt(tab, i, e.next);
                                        }
                                    }
                                    break;
                                }
                                pred = e;
                                if ((e = e.next) == null)
                                {
                                    break;
                                }
                            }
                        }
                        else if (f instanceof TreeBin)
                        {
                            validated = true;
                            final TreeBin<K, V> t = (TreeBin<K, V>) f;
                            final TreeNode<K, V> r;
                            final TreeNode<K, V> p;
                            if ((((r = t.root)) != null) && (((p = r.findTreeNode(hash, key, null))) != null))
                            {
                                final V pv = p.val;
                                if ((cv == null) || (cv == pv) ||
                                            ((pv != null) && cv.equals(pv)))
                                {
                                    oldVal = pv;
                                    if (value != null)
                                    {
                                        p.val = value;
                                    }
                                    else if (t.removeTreeNode(p))
                                    {
                                        setTabAt(tab, i, untreeify(t.first));
                                    }
                                }
                            }
                        }
                    }
                }
                if (validated)
                {
                    if (oldVal != null)
                    {
                        if (value == null)
                        {
                            this.addCount(- 1L, - 1);
                        }
                        return oldVal;
                    }
                    break;
                }
            }
        }
        return null;
    }

    /**
     * Saves the state of the {@code ConcurrentIdentityHashMap} instance to a
     * stream (i.e., serializes it).
     *
     * @param s the stream
     *
     * @throws java.io.IOException if an I/O error occurs
     * @serialData the key (Object) and value (Object)
     * for each key-value mapping, followed by a null pair.
     * The key-value mappings are emitted in no particular order.
     */
    private void writeObject(final java.io.ObjectOutputStream s) throws java.io.IOException
    {
        // For serialization compatibility
        // Emulate segment calculation from previous version of this class
        int sshift = 0;
        int ssize = 1;
        while (ssize < DEFAULT_CONCURRENCY_LEVEL)
        {
            ++ sshift;
            ssize <<= 1;
        }
        final int segmentShift = 32 - sshift;
        final int segmentMask = ssize - 1;
        @SuppressWarnings("unchecked")
        final Segment<K, V>[] segments = (Segment<K, V>[]) new Segment<?, ?>[DEFAULT_CONCURRENCY_LEVEL];
        for (int i = 0; i < segments.length; ++ i)
        {
            segments[i] = new Segment<>(LOAD_FACTOR);
        }
        s.putFields().put("segments", segments);
        s.putFields().put("segmentShift", segmentShift);
        s.putFields().put("segmentMask", segmentMask);
        s.writeFields();

        final Node<K, V>[] t;
        if ((t = this.table) != null)
        {
            final Traverser<K, V> it = new Traverser<>(t, t.length, 0, t.length);
            for (Node<K, V> p; (p = it.advance()) != null; )
            {
                s.writeObject(p.key);
                s.writeObject(p.val);
            }
        }
        s.writeObject(null);
        s.writeObject(null);
    }

    /**
     * Reconstitutes the instance from a stream (that is, deserializes it).
     *
     * @param s the stream
     *
     * @throws ClassNotFoundException if the class of a serialized object
     *                                could not be found
     * @throws java.io.IOException    if an I/O error occurs
     */
    private void readObject(final java.io.ObjectInputStream s) throws java.io.IOException, ClassNotFoundException
    {
        /*
         * To improve performance in typical cases, we create nodes
         * while reading, then place in table once size is known.
         * However, we must also validate uniqueness and deal with
         * overpopulated bins while doing so, which requires
         * specialized versions of putVal mechanics.
         */
        this.sizeCtl = - 1; // force exclusion for table construction
        s.defaultReadObject();
        long size = 0L;
        Node<K, V> p = null;
        while (true)
        {
            @SuppressWarnings("unchecked")
            final K k = (K) s.readObject();
            @SuppressWarnings("unchecked")
            final V v = (V) s.readObject();
            if ((k != null) && (v != null))
            {
                p = new Node<>(spread(k.hashCode()), k, v, p);
                ++ size;
            }
            else
            {
                break;
            }
        }
        if (size == 0L)
        {
            this.sizeCtl = 0;
        }
        else
        {
            final int n;
            if (size >= (long) (MAXIMUM_CAPACITY >>> 1))
            {
                n = MAXIMUM_CAPACITY;
            }
            else
            {
                final int sz = (int) size;
                n = tableSizeFor(sz + (sz >>> 1) + 1);
            }
            @SuppressWarnings("unchecked")
            final Node<K, V>[] tab = (Node<K, V>[]) new Node<?, ?>[n];
            final int mask = n - 1;
            long added = 0L;
            while (p != null)
            {
                boolean insertAtFront;
                final Node<K, V> next = p.next;
                final Node<K, V> first;
                final int h = p.hash;
                final int j = h & mask;
                if ((first = tabAt(tab, j)) == null)
                {
                    insertAtFront = true;
                }
                else
                {
                    final K k = p.key;
                    if (first.hash < 0)
                    {
                        final TreeBin<K, V> t = (TreeBin<K, V>) first;
                        if (t.putTreeVal(h, k, p.val) == null)
                        {
                            ++ added;
                        }
                        insertAtFront = false;
                    }
                    else
                    {
                        int binCount = 0;
                        insertAtFront = true;
                        Node<K, V> q;
                        K qk;
                        for (q = first; q != null; q = q.next)
                        {
                            if ((q.hash == h) && ((((qk = q.key)) == k) || ((qk != null) && k.equals(qk))))
                            {
                                insertAtFront = false;
                                break;
                            }
                            ++ binCount;
                        }
                        if (insertAtFront && (binCount >= TREEIFY_THRESHOLD))
                        {
                            insertAtFront = false;
                            ++ added;
                            p.next = first;
                            TreeNode<K, V> hd = null, tl = null;
                            for (q = p; q != null; q = q.next)
                            {
                                final TreeNode<K, V> t = new TreeNode<>(q.hash, q.key, q.val, null, null);
                                if ((t.prev = tl) == null)
                                {
                                    hd = t;
                                }
                                else
                                {
                                    tl.next = t;
                                }
                                tl = t;
                            }
                            setTabAt(tab, j, new TreeBin<>(hd));
                        }
                    }
                }
                if (insertAtFront)
                {
                    ++ added;
                    p.next = first;
                    setTabAt(tab, j, p);
                }
                p = next;
            }
            this.table = tab;
            this.sizeCtl = n - (n >>> 2);
            this.baseCount = added;
        }
    }

    /**
     * Returns the value to which the specified key is mapped, or the
     * given default value if this map contains no mapping for the
     * key.
     *
     * @param key          the key whose associated value is to be returned
     * @param defaultValue the value to return if this map contains
     *                     no mapping for the given key
     *
     * @return the mapping for the key, if present; else the default value
     *
     * @throws NullPointerException if the specified key is null
     */
    @Override
    public V getOrDefault(final Object key, final V defaultValue)
    {
        final V v;
        return (((v = this.get(key))) == null) ? defaultValue : v;
    }

    // ConcurrentMap methods

    @Override
    public void forEach(final BiConsumer<? super K, ? super V> action)
    {
        if (action == null)
        {
            throw new NullPointerException();
        }
        final Node<K, V>[] t;
        if ((t = this.table) != null)
        {
            final Traverser<K, V> it = new Traverser<>(t, t.length, 0, t.length);
            for (Node<K, V> p; (p = it.advance()) != null; )
            {
                action.accept(p.key, p.val);
            }
        }
    }

    @Override
    public void replaceAll(final BiFunction<? super K, ? super V, ? extends V> function)
    {
        if (function == null)
        {
            throw new NullPointerException();
        }
        final Node<K, V>[] t;
        if ((t = this.table) != null)
        {
            final Traverser<K, V> it = new Traverser<>(t, t.length, 0, t.length);
            for (Node<K, V> p; (p = it.advance()) != null; )
            {
                V oldValue = p.val;
                for (final K key = p.key; ; )
                {
                    final V newValue = function.apply(key, oldValue);
                    if (newValue == null)
                    {
                        throw new NullPointerException();
                    }
                    if ((this.replaceNode(key, newValue, oldValue) != null) || (((oldValue = this.get(key))) == null))
                    {
                        break;
                    }
                }
            }
        }
    }

    /**
     * @return the previous value associated with the specified key,
     * or {@code null} if there was no mapping for the key
     *
     * @throws NullPointerException if the specified key or value is null
     */
    @Override
    public V putIfAbsent(final K key, final V value)
    {
        return this.putVal(key, value, true);
    }

    /**
     * @throws NullPointerException if the specified key is null
     */
    @Override
    public boolean remove(final Object key, final Object value)
    {
        if (key == null)
        {
            throw new NullPointerException();
        }
        return (value != null) && (this.replaceNode(key, null, value) != null);
    }

    // Overrides of JDK8+ Map extension method defaults

    /**
     * @throws NullPointerException if any of the arguments are null
     */
    @Override
    public boolean replace(final K key, final V oldValue, final V newValue)
    {
        if ((key == null) || (oldValue == null) || (newValue == null))
        {
            throw new NullPointerException();
        }
        return this.replaceNode(key, newValue, oldValue) != null;
    }

    /**
     * @return the previous value associated with the specified key,
     * or {@code null} if there was no mapping for the key
     *
     * @throws NullPointerException if the specified key or value is null
     */
    @Override
    public V replace(final K key, final V value)
    {
        if ((key == null) || (value == null))
        {
            throw new NullPointerException();
        }
        return this.replaceNode(key, value, null);
    }

    /**
     * If the specified key is not already associated with a value,
     * attempts to compute its value using the given mapping function
     * and enters it into this map unless {@code null}.  The entire
     * method invocation is performed atomically, so the function is
     * applied at most once per key.  Some attempted update operations
     * on this map by other threads may be blocked while computation
     * is in progress, so the computation should be short and simple,
     * and must not attempt to update any other mappings of this map.
     *
     * @param key             key with which the specified value is to be associated
     * @param mappingFunction the function to compute a value
     *
     * @return the current (existing or computed) value associated with
     * the specified key, or null if the computed value is null
     *
     * @throws NullPointerException  if the specified key or mappingFunction
     *                               is null
     * @throws IllegalStateException if the computation detectably
     *                               attempts a recursive update to this map that would
     *                               otherwise never complete
     * @throws RuntimeException      or Error if the mappingFunction does so,
     *                               in which case the mapping is left unestablished
     */
    @Override
    public V computeIfAbsent(final K key, final Function<? super K, ? extends V> mappingFunction)
    {
        if ((key == null) || (mappingFunction == null))
        {
            throw new NullPointerException();
        }
        final int h = spread(key.hashCode());
        V val = null;
        int binCount = 0;
        for (Node<K, V>[] tab = this.table; ; )
        {
            final Node<K, V> f;
            final int n;
            final int i;
            final int fh;
            if ((tab == null) || (((n = tab.length)) == 0))
            {
                tab = this.initTable();
            }
            else if ((f = tabAt(tab, i = (n - 1) & h)) == null)
            {
                final Node<K, V> r = new ReservationNode<>();
                synchronized (r)
                {
                    if (casTabAt(tab, i, null, r))
                    {
                        binCount = 1;
                        Node<K, V> node = null;
                        try
                        {
                            if ((val = mappingFunction.apply(key)) != null)
                            {
                                node = new Node<>(h, key, val, null);
                            }
                        } finally
                        {
                            setTabAt(tab, i, node);
                        }
                    }
                }
                if (binCount != 0)
                {
                    break;
                }
            }
            else if ((fh = f.hash) == MOVED)
            {
                tab = this.helpTransfer(tab, f);
            }
            else
            {
                boolean added = false;
                synchronized (f)
                {
                    if (tabAt(tab, i) == f)
                    {
                        if (fh >= 0)
                        {
                            binCount = 1;
                            for (Node<K, V> e = f; ; ++ binCount)
                            {
                                final K ek;
                                if ((e.hash == h) && ((((ek = e.key)) == key) || ((ek != null) && key.equals(ek))))
                                {
                                    val = e.val;
                                    break;
                                }
                                final Node<K, V> pred = e;
                                if ((e = e.next) == null)
                                {
                                    if ((val = mappingFunction.apply(key)) != null)
                                    {
                                        added = true;
                                        pred.next = new Node<>(h, key, val, null);
                                    }
                                    break;
                                }
                            }
                        }
                        else if (f instanceof TreeBin)
                        {
                            binCount = 2;
                            final TreeBin<K, V> t = (TreeBin<K, V>) f;
                            final TreeNode<K, V> r;
                            final TreeNode<K, V> p;
                            if ((((r = t.root)) != null) && (((p = r.findTreeNode(h, key, null))) != null))
                            {
                                val = p.val;
                            }
                            else if ((val = mappingFunction.apply(key)) != null)
                            {
                                added = true;
                                t.putTreeVal(h, key, val);
                            }
                        }
                    }
                }
                if (binCount != 0)
                {
                    if (binCount >= TREEIFY_THRESHOLD)
                    {
                        this.treeifyBin(tab, i);
                    }
                    if (! added)
                    {
                        return val;
                    }
                    break;
                }
            }
        }
        if (val != null)
        {
            this.addCount(1L, binCount);
        }
        return val;
    }

    /**
     * If the value for the specified key is present, attempts to
     * compute a new mapping given the key and its current mapped
     * value.  The entire method invocation is performed atomically.
     * Some attempted update operations on this map by other threads
     * may be blocked while computation is in progress, so the
     * computation should be short and simple, and must not attempt to
     * update any other mappings of this map.
     *
     * @param key               key with which a value may be associated
     * @param remappingFunction the function to compute a value
     *
     * @return the new value associated with the specified key, or null if none
     *
     * @throws NullPointerException  if the specified key or remappingFunction
     *                               is null
     * @throws IllegalStateException if the computation detectably
     *                               attempts a recursive update to this map that would
     *                               otherwise never complete
     * @throws RuntimeException      or Error if the remappingFunction does so,
     *                               in which case the mapping is unchanged
     */
    @Override
    public V computeIfPresent(final K key, final BiFunction<? super K, ? super V, ? extends V> remappingFunction)
    {
        if ((key == null) || (remappingFunction == null))
        {
            throw new NullPointerException();
        }
        final int h = spread(key.hashCode());
        V val = null;
        int delta = 0;
        int binCount = 0;
        for (Node<K, V>[] tab = this.table; ; )
        {
            final Node<K, V> f;
            final int n;
            final int i;
            final int fh;
            if ((tab == null) || (((n = tab.length)) == 0))
            {
                tab = this.initTable();
            }
            else if ((f = tabAt(tab, i = (n - 1) & h)) == null)
            {
                break;
            }
            else if ((fh = f.hash) == MOVED)
            {
                tab = this.helpTransfer(tab, f);
            }
            else
            {
                synchronized (f)
                {
                    if (tabAt(tab, i) == f)
                    {
                        if (fh >= 0)
                        {
                            binCount = 1;
                            for (Node<K, V> e = f, pred = null; ; ++ binCount)
                            {
                                final K ek;
                                if ((e.hash == h) && ((((ek = e.key)) == key) || ((ek != null) && key.equals(ek))))
                                {
                                    val = remappingFunction.apply(key, e.val);
                                    if (val != null)
                                    {
                                        e.val = val;
                                    }
                                    else
                                    {
                                        delta = - 1;
                                        final Node<K, V> en = e.next;
                                        if (pred != null)
                                        {
                                            pred.next = en;
                                        }
                                        else
                                        {
                                            setTabAt(tab, i, en);
                                        }
                                    }
                                    break;
                                }
                                pred = e;
                                if ((e = e.next) == null)
                                {
                                    break;
                                }
                            }
                        }
                        else if (f instanceof TreeBin)
                        {
                            binCount = 2;
                            final TreeBin<K, V> t = (TreeBin<K, V>) f;
                            final TreeNode<K, V> r;
                            final TreeNode<K, V> p;
                            if ((((r = t.root)) != null) && (((p = r.findTreeNode(h, key, null))) != null))
                            {
                                val = remappingFunction.apply(key, p.val);
                                if (val != null)
                                {
                                    p.val = val;
                                }
                                else
                                {
                                    delta = - 1;
                                    if (t.removeTreeNode(p))
                                    {
                                        setTabAt(tab, i, untreeify(t.first));
                                    }
                                }
                            }
                        }
                    }
                }
                if (binCount != 0)
                {
                    break;
                }
            }
        }
        if (delta != 0)
        {
            this.addCount((long) delta, binCount);
        }
        return val;
    }

    /**
     * Attempts to compute a mapping for the specified key and its
     * current mapped value (or {@code null} if there is no current
     * mapping). The entire method invocation is performed atomically.
     * Some attempted update operations on this map by other threads
     * may be blocked while computation is in progress, so the
     * computation should be short and simple, and must not attempt to
     * update any other mappings of this Map.
     *
     * @param key               key with which the specified value is to be associated
     * @param remappingFunction the function to compute a value
     *
     * @return the new value associated with the specified key, or null if none
     *
     * @throws NullPointerException  if the specified key or remappingFunction
     *                               is null
     * @throws IllegalStateException if the computation detectably
     *                               attempts a recursive update to this map that would
     *                               otherwise never complete
     * @throws RuntimeException      or Error if the remappingFunction does so,
     *                               in which case the mapping is unchanged
     */
    @Override
    public V compute(final K key, final BiFunction<? super K, ? super V, ? extends V> remappingFunction)
    {
        if ((key == null) || (remappingFunction == null))
        {
            throw new NullPointerException();
        }
        final int h = spread(key.hashCode());
        V val = null;
        int delta = 0;
        int binCount = 0;
        for (Node<K, V>[] tab = this.table; ; )
        {
            final Node<K, V> f;
            final int n;
            final int i;
            final int fh;
            if ((tab == null) || (((n = tab.length)) == 0))
            {
                tab = this.initTable();
            }
            else if ((f = tabAt(tab, i = (n - 1) & h)) == null)
            {
                final Node<K, V> r = new ReservationNode<>();
                synchronized (r)
                {
                    if (casTabAt(tab, i, null, r))
                    {
                        binCount = 1;
                        Node<K, V> node = null;
                        try
                        {
                            if ((val = remappingFunction.apply(key, null)) != null)
                            {
                                delta = 1;
                                node = new Node<>(h, key, val, null);
                            }
                        } finally
                        {
                            setTabAt(tab, i, node);
                        }
                    }
                }
                if (binCount != 0)
                {
                    break;
                }
            }
            else if ((fh = f.hash) == MOVED)
            {
                tab = this.helpTransfer(tab, f);
            }
            else
            {
                synchronized (f)
                {
                    if (tabAt(tab, i) == f)
                    {
                        if (fh >= 0)
                        {
                            binCount = 1;
                            for (Node<K, V> e = f, pred = null; ; ++ binCount)
                            {
                                final K ek;
                                if ((e.hash == h) && ((((ek = e.key)) == key) || ((ek != null) && key.equals(ek))))
                                {
                                    val = remappingFunction.apply(key, e.val);
                                    if (val != null)
                                    {
                                        e.val = val;
                                    }
                                    else
                                    {
                                        delta = - 1;
                                        final Node<K, V> en = e.next;
                                        if (pred != null)
                                        {
                                            pred.next = en;
                                        }
                                        else
                                        {
                                            setTabAt(tab, i, en);
                                        }
                                    }
                                    break;
                                }
                                pred = e;
                                if ((e = e.next) == null)
                                {
                                    val = remappingFunction.apply(key, null);
                                    if (val != null)
                                    {
                                        delta = 1;
                                        pred.next = new Node<>(h, key, val, null);
                                    }
                                    break;
                                }
                            }
                        }
                        else if (f instanceof TreeBin)
                        {
                            binCount = 1;
                            final TreeBin<K, V> t = (TreeBin<K, V>) f;
                            final TreeNode<K, V> r;
                            final TreeNode<K, V> p;
                            if ((r = t.root) != null)
                            {
                                p = r.findTreeNode(h, key, null);
                            }
                            else
                            {
                                p = null;
                            }
                            final V pv = (p == null) ? null : p.val;
                            val = remappingFunction.apply(key, pv);
                            if (val != null)
                            {
                                if (p != null)
                                {
                                    p.val = val;
                                }
                                else
                                {
                                    delta = 1;
                                    t.putTreeVal(h, key, val);
                                }
                            }
                            else if (p != null)
                            {
                                delta = - 1;
                                if (t.removeTreeNode(p))
                                {
                                    setTabAt(tab, i, untreeify(t.first));
                                }
                            }
                        }
                    }
                }
                if (binCount != 0)
                {
                    if (binCount >= TREEIFY_THRESHOLD)
                    {
                        this.treeifyBin(tab, i);
                    }
                    break;
                }
            }
        }
        if (delta != 0)
        {
            this.addCount((long) delta, binCount);
        }
        return val;
    }

    /**
     * If the specified key is not already associated with a
     * (non-null) value, associates it with the given value.
     * Otherwise, replaces the value with the results of the given
     * remapping function, or removes if {@code null}. The entire
     * method invocation is performed atomically.  Some attempted
     * update operations on this map by other threads may be blocked
     * while computation is in progress, so the computation should be
     * short and simple, and must not attempt to update any other
     * mappings of this Map.
     *
     * @param key               key with which the specified value is to be associated
     * @param value             the value to use if absent
     * @param remappingFunction the function to recompute a value if present
     *
     * @return the new value associated with the specified key, or null if none
     *
     * @throws NullPointerException if the specified key or the
     *                              remappingFunction is null
     * @throws RuntimeException     or Error if the remappingFunction does so,
     *                              in which case the mapping is unchanged
     */
    @Override
    public V merge(final K key, final V value, final BiFunction<? super V, ? super V, ? extends V> remappingFunction)
    {
        if ((key == null) || (value == null) || (remappingFunction == null))
        {
            throw new NullPointerException();
        }
        final int h = spread(key.hashCode());
        V val = null;
        int delta = 0;
        int binCount = 0;
        for (Node<K, V>[] tab = this.table; ; )
        {
            final Node<K, V> f;
            final int n;
            final int i;
            final int fh;
            if ((tab == null) || (((n = tab.length)) == 0))
            {
                tab = this.initTable();
            }
            else if ((f = tabAt(tab, i = (n - 1) & h)) == null)
            {
                if (casTabAt(tab, i, null, new Node<>(h, key, value, null)))
                {
                    delta = 1;
                    val = value;
                    break;
                }
            }
            else if ((fh = f.hash) == MOVED)
            {
                tab = this.helpTransfer(tab, f);
            }
            else
            {
                synchronized (f)
                {
                    if (tabAt(tab, i) == f)
                    {
                        if (fh >= 0)
                        {
                            binCount = 1;
                            for (Node<K, V> e = f, pred = null; ; ++ binCount)
                            {
                                final K ek;
                                if ((e.hash == h) && ((((ek = e.key)) == key) || ((ek != null) && key.equals(ek))))
                                {
                                    val = remappingFunction.apply(e.val, value);
                                    if (val != null)
                                    {
                                        e.val = val;
                                    }
                                    else
                                    {
                                        delta = - 1;
                                        final Node<K, V> en = e.next;
                                        if (pred != null)
                                        {
                                            pred.next = en;
                                        }
                                        else
                                        {
                                            setTabAt(tab, i, en);
                                        }
                                    }
                                    break;
                                }
                                pred = e;
                                if ((e = e.next) == null)
                                {
                                    delta = 1;
                                    val = value;
                                    pred.next = new Node<>(h, key, val, null);
                                    break;
                                }
                            }
                        }
                        else if (f instanceof TreeBin)
                        {
                            binCount = 2;
                            final TreeBin<K, V> t = (TreeBin<K, V>) f;
                            final TreeNode<K, V> r = t.root;
                            final TreeNode<K, V> p = (r == null) ? null : r.findTreeNode(h, key, null);
                            val = (p == null) ? value : remappingFunction.apply(p.val, value);
                            if (val != null)
                            {
                                if (p != null)
                                {
                                    p.val = val;
                                }
                                else
                                {
                                    delta = 1;
                                    t.putTreeVal(h, key, val);
                                }
                            }
                            else
                            {
                                delta = - 1;
                                if (t.removeTreeNode(p))
                                {
                                    setTabAt(tab, i, untreeify(t.first));
                                }
                            }
                        }
                    }
                }
                if (binCount != 0)
                {
                    if (binCount >= TREEIFY_THRESHOLD)
                    {
                        this.treeifyBin(tab, i);
                    }
                    break;
                }
            }
        }
        if (delta != 0)
        {
            this.addCount((long) delta, binCount);
        }
        return val;
    }

    /**
     * Legacy method testing if some key maps into the specified value
     * in this table.  This method is identical in functionality to
     * {@link #containsValue(Object)}, and exists solely to ensure
     * full compatibility with class {@link java.util.Hashtable},
     * which supported this method prior to introduction of the
     * Java Collections framework.
     *
     * @param value a value to search for
     *
     * @return {@code true} if and only if some key maps to the
     * {@code value} argument in this table as
     * determined by the {@code equals} method;
     * {@code false} otherwise
     *
     * @throws NullPointerException if the specified value is null
     */
    public boolean contains(final Object value)
    {
        return this.containsValue(value);
    }

    // Hashtable legacy methods

    /**
     * Returns an enumeration of the keys in this table.
     *
     * @return an enumeration of the keys in this table
     *
     * @see #keySet()
     */
    public Enumeration<K> keys()
    {
        final Node<K, V>[] t;
        final int f = (((t = this.table)) == null) ? 0 : t.length;
        return new KeyIterator<>(t, f, 0, f, this);
    }

    /**
     * Returns an enumeration of the values in this table.
     *
     * @return an enumeration of the values in this table
     *
     * @see #values()
     */
    public Enumeration<V> elements()
    {
        final Node<K, V>[] t;
        final int f = (((t = this.table)) == null) ? 0 : t.length;
        return new ValueIterator<>(t, f, 0, f, this);
    }

    /**
     * Returns the number of mappings. This method should be used
     * instead of {@link #size} because a ConcurrentIdentityHashMap may
     * contain more mappings than can be represented as an int. The
     * value returned is an estimate; the actual count may differ if
     * there are concurrent insertions or removals.
     *
     * @return the number of mappings
     *
     * @since 1.8
     */
    public long mappingCount()
    {
        final long n = this.sumCount();
        return (n < 0L) ? 0L : n; // ignore transient negative values
    }

    // ConcurrentIdentityHashMap-only methods

    /**
     * Returns a {@link Set} view of the keys in this map, using the
     * given common mapped value for any additions (i.e., {@link
     * Collection#add} and {@link Collection#addAll(Collection)}).
     * This is of course only appropriate if it is acceptable to use
     * the same value for all additions from this view.
     *
     * @param mappedValue the mapped value to use for any additions
     *
     * @return the set view
     *
     * @throws NullPointerException if the mappedValue is null
     */
    public KeySetView<K, V> keySet(final V mappedValue)
    {
        if (mappedValue == null)
        {
            throw new NullPointerException();
        }
        return new KeySetView<>(this, mappedValue);
    }

    /**
     * Initializes table, using the size recorded in sizeCtl.
     */
    private Node<K, V>[] initTable()
    {
        Node<K, V>[] tab;
        int sc;
        while ((((tab = this.table)) == null) || (tab.length == 0))
        {
            if ((sc = this.sizeCtl) < 0)
            {
                Thread.yield(); // lost initialization race; just spin
            }
            else if (U.compareAndSwapInt(this, SIZECTL, sc, - 1))
            {
                try
                {
                    if ((((tab = this.table)) == null) || (tab.length == 0))
                    {
                        final int n = (sc > 0) ? sc : DEFAULT_CAPACITY;
                        @SuppressWarnings("unchecked")
                        final Node<K, V>[] nt = (Node<K, V>[]) new Node<?, ?>[n];
                        this.table = tab = nt;
                        sc = n - (n >>> 2);
                    }
                } finally
                {
                    this.sizeCtl = sc;
                }
                break;
            }
        }
        return tab;
    }

    /**
     * Adds to count, and if table is too small and not already
     * resizing, initiates transfer. If already resizing, helps
     * perform transfer if work is available.  Rechecks occupancy
     * after a transfer to see if another resize is already needed
     * because resizings are lagging additions.
     *
     * @param x     the count to add
     * @param check if <0, don't check resize, if <= 1 only check if uncontended
     */
    private void addCount(final long x, final int check)
    {
        final CounterCell[] as;
        final long b;
        long s;
        if ((((as = this.counterCells)) != null) || ! U.compareAndSwapLong(this, BASECOUNT, b = this.baseCount, s = b + x))
        {
            final CounterCell a;
            final long v;
            final int m;
            boolean uncontended = true;
            if ((as == null) || (((m = as.length - 1)) < 0) ||
                        (((a = as[DioriteThreadLocalRandom.getProbe() & m])) == null) ||
                        ! (uncontended = U.compareAndSwapLong(a, CELLVALUE, v = a.value, v + x)))
            {
                this.fullAddCount(x, uncontended);
                return;
            }
            if (check <= 1)
            {
                return;
            }
            s = this.sumCount();
        }
        if (check >= 0)
        {
            Node<K, V>[] tab, nt;
            int n, sc;
            while ((s >= (long) (sc = this.sizeCtl)) && (((tab = this.table)) != null) &&
                           (((n = tab.length)) < MAXIMUM_CAPACITY))
            {
                final int rs = resizeStamp(n);
                if (sc < 0)
                {
                    if (((sc >>> RESIZE_STAMP_SHIFT) != rs) || (sc == (rs + 1)) ||
                                (sc == (rs + MAX_RESIZERS)) || (((nt = this.nextTable)) == null) ||
                                (this.transferIndex <= 0))
                    {
                        break;
                    }
                    if (U.compareAndSwapInt(this, SIZECTL, sc, sc + 1))
                    {
                        this.transfer(tab, nt);
                    }
                }
                else if (U.compareAndSwapInt(this, SIZECTL, sc, (rs << RESIZE_STAMP_SHIFT) + 2))
                {
                    this.transfer(tab, null);
                }
                s = this.sumCount();
            }
        }
    }

    /**
     * Helps transfer if a resize is in progress.
     */
    final Node<K, V>[] helpTransfer(final Node<K, V>[] tab, final Node<K, V> f)
    {
        final Node<K, V>[] nextTab;
        int sc;
        if ((tab != null) && (f instanceof ForwardingNode) &&
                    (((nextTab = ((ForwardingNode<K, V>) f).nextTable)) != null))
        {
            final int rs = resizeStamp(tab.length);
            while ((nextTab == this.nextTable) && (this.table == tab) &&
                           (((sc = this.sizeCtl)) < 0))
            {
                if (((sc >>> RESIZE_STAMP_SHIFT) != rs) || (sc == (rs + 1)) ||
                            (sc == (rs + MAX_RESIZERS)) || (this.transferIndex <= 0))
                {
                    break;
                }
                if (U.compareAndSwapInt(this, SIZECTL, sc, sc + 1))
                {
                    this.transfer(tab, nextTab);
                    break;
                }
            }
            return nextTab;
        }
        return this.table;
    }

    /* ---------------- Special Nodes -------------- */

    /**
     * Tries to presize table to accommodate the given number of elements.
     *
     * @param size number of elements (doesn't need to be perfectly accurate)
     */
    private void tryPresize(final int size)
    {
        final int c = (size >= (MAXIMUM_CAPACITY >>> 1)) ? MAXIMUM_CAPACITY : tableSizeFor(size + (size >>> 1) + 1);
        int sc;
        while ((sc = this.sizeCtl) >= 0)
        {
            final Node<K, V>[] tab = this.table;
            int n;
            if ((tab == null) || (((n = tab.length)) == 0))
            {
                n = (sc > c) ? sc : c;
                if (U.compareAndSwapInt(this, SIZECTL, sc, - 1))
                {
                    try
                    {
                        if (this.table == tab)
                        {
                            @SuppressWarnings("unchecked")
                            final Node<K, V>[] nt = (Node<K, V>[]) new Node<?, ?>[n];
                            this.table = nt;
                            sc = n - (n >>> 2);
                        }
                    } finally
                    {
                        this.sizeCtl = sc;
                    }
                }
            }
            else if ((c <= sc) || (n >= MAXIMUM_CAPACITY))
            {
                break;
            }
            else if (tab == this.table)
            {
                final int rs = resizeStamp(n);
                if (sc < 0)
                {
                    final Node<K, V>[] nt;
                    if (((sc >>> RESIZE_STAMP_SHIFT) != rs) || (sc == (rs + 1)) ||
                                (sc == (rs + MAX_RESIZERS)) || (((nt = this.nextTable)) == null) ||
                                (this.transferIndex <= 0))
                    {
                        break;
                    }
                    if (U.compareAndSwapInt(this, SIZECTL, sc, sc + 1))
                    {
                        this.transfer(tab, nt);
                    }
                }
                else if (U.compareAndSwapInt(this, SIZECTL, sc, (rs << RESIZE_STAMP_SHIFT) + 2))
                {
                    this.transfer(tab, null);
                }
            }
        }
    }

    /**
     * Moves and/or copies the nodes in each bin to new table. See
     * above for explanation.
     */
    private void transfer(final Node<K, V>[] tab, Node<K, V>[] nextTab)
    {
        final int n = tab.length;
        int stride;
        if (((stride = (NCPU > 1) ? ((n >>> 3) / NCPU) : n)) < MIN_TRANSFER_STRIDE)
        {
            stride = MIN_TRANSFER_STRIDE; // subdivide range
        }
        if (nextTab == null)
        {            // initiating
            try
            {
                @SuppressWarnings("unchecked")
                final Node<K, V>[] nt = (Node<K, V>[]) new Node<?, ?>[n << 1];
                nextTab = nt;
            } catch (final Throwable ex)
            {      // try to cope with OOME
                this.sizeCtl = Integer.MAX_VALUE;
                return;
            }
            this.nextTable = nextTab;
            this.transferIndex = n;
        }
        final int nextn = nextTab.length;
        final ForwardingNode<K, V> fwd = new ForwardingNode<>(nextTab);
        boolean advance = true;
        boolean finishing = false; // to ensure sweep before committing nextTab
        for (int i = 0, bound = 0; ; )
        {
            final Node<K, V> f;
            final int fh;
            while (advance)
            {
                final int nextIndex;
                final int nextBound;
                if ((-- i >= bound) || finishing)
                {
                    advance = false;
                }
                else if ((nextIndex = this.transferIndex) <= 0)
                {
                    i = - 1;
                    advance = false;
                }
                else if (U.compareAndSwapInt(this, TRANSFERINDEX, nextIndex, (nextBound = ((nextIndex > stride) ? (nextIndex - stride) : 0))))
                {
                    bound = nextBound;
                    i = nextIndex - 1;
                    advance = false;
                }
            }
            if ((i < 0) || (i >= n) || ((i + n) >= nextn))
            {
                final int sc;
                if (finishing)
                {
                    this.nextTable = null;
                    this.table = nextTab;
                    this.sizeCtl = (n << 1) - (n >>> 1);
                    return;
                }
                if (U.compareAndSwapInt(this, SIZECTL, sc = this.sizeCtl, sc - 1))
                {
                    if ((sc - 2) != (resizeStamp(n) << RESIZE_STAMP_SHIFT))
                    {
                        return;
                    }
                    finishing = advance = true;
                    i = n; // recheck before commit
                }
            }
            else if ((f = tabAt(tab, i)) == null)
            {
                advance = casTabAt(tab, i, null, fwd);
            }
            else if ((fh = f.hash) == MOVED)
            {
                advance = true; // already processed
            }
            else
            {
                synchronized (f)
                {
                    if (tabAt(tab, i) == f)
                    {
                        Node<K, V> ln, hn;
                        if (fh >= 0)
                        {
                            int runBit = fh & n;
                            Node<K, V> lastRun = f;
                            for (Node<K, V> p = f.next; p != null; p = p.next)
                            {
                                final int b = p.hash & n;
                                if (b != runBit)
                                {
                                    runBit = b;
                                    lastRun = p;
                                }
                            }
                            if (runBit == 0)
                            {
                                ln = lastRun;
                                hn = null;
                            }
                            else
                            {
                                hn = lastRun;
                                ln = null;
                            }
                            for (Node<K, V> p = f; p != lastRun; p = p.next)
                            {
                                final int ph = p.hash;
                                final K pk = p.key;
                                final V pv = p.val;
                                if ((ph & n) == 0)
                                {
                                    ln = new Node<>(ph, pk, pv, ln);
                                }
                                else
                                {
                                    hn = new Node<>(ph, pk, pv, hn);
                                }
                            }
                            setTabAt(nextTab, i, ln);
                            setTabAt(nextTab, i + n, hn);
                            setTabAt(tab, i, fwd);
                            advance = true;
                        }
                        else if (f instanceof TreeBin)
                        {
                            final TreeBin<K, V> t = (TreeBin<K, V>) f;
                            TreeNode<K, V> lo = null, loTail = null;
                            TreeNode<K, V> hi = null, hiTail = null;
                            int lc = 0, hc = 0;
                            for (Node<K, V> e = t.first; e != null; e = e.next)
                            {
                                final int h = e.hash;
                                final TreeNode<K, V> p = new TreeNode<>(h, e.key, e.val, null, null);
                                if ((h & n) == 0)
                                {
                                    if ((p.prev = loTail) == null)
                                    {
                                        lo = p;
                                    }
                                    else
                                    {
                                        loTail.next = p;
                                    }
                                    loTail = p;
                                    ++ lc;
                                }
                                else
                                {
                                    if ((p.prev = hiTail) == null)
                                    {
                                        hi = p;
                                    }
                                    else
                                    {
                                        hiTail.next = p;
                                    }
                                    hiTail = p;
                                    ++ hc;
                                }
                            }
                            ln = (lc <= UNTREEIFY_THRESHOLD) ? untreeify(lo) : ((hc != 0) ? new TreeBin<>(lo) : t);
                            hn = (hc <= UNTREEIFY_THRESHOLD) ? untreeify(hi) : ((lc != 0) ? new TreeBin<>(hi) : t);
                            setTabAt(nextTab, i, ln);
                            setTabAt(nextTab, i + n, hn);
                            setTabAt(tab, i, fwd);
                            advance = true;
                        }
                    }
                }
            }
        }
    }

    /* ---------------- Table Initialization and Resizing -------------- */

    final long sumCount()
    {
        final CounterCell[] as = this.counterCells;
        CounterCell a;
        long sum = this.baseCount;
        if (as != null)
        {
            for (final CounterCell a1 : as)
            {
                if ((a = a1) != null)
                {
                    sum += a.value;
                }
            }
        }
        return sum;
    }

    // See LongAdder version for explanation
    private void fullAddCount(final long x, boolean wasUncontended)
    {
        int h;
        if ((h = DioriteThreadLocalRandom.getProbe()) == 0)
        {
            DioriteThreadLocalRandom.localInit();      // force initialization
            h = DioriteThreadLocalRandom.getProbe();
            wasUncontended = true;
        }
        boolean collide = false;                // True if last slot nonempty
        while (true)
        {
            final CounterCell[] as;
            final CounterCell a;
            final int n;
            final long v;
            if ((((as = this.counterCells)) != null) && (((n = as.length)) > 0))
            {
                if ((a = as[(n - 1) & h]) == null)
                {
                    if (this.cellsBusy == 0)
                    {            // Try to attach new Cell
                        final CounterCell r = new CounterCell(x); // Optimistic create
                        if ((this.cellsBusy == 0) && U.compareAndSwapInt(this, CELLSBUSY, 0, 1))
                        {
                            boolean created = false;
                            try
                            {               // Recheck under lock
                                final CounterCell[] rs;
                                final int m;
                                final int j;
                                if ((((rs = this.counterCells)) != null) &&
                                            (((m = rs.length)) > 0) &&
                                            (rs[j = (m - 1) & h] == null))
                                {
                                    rs[j] = r;
                                    created = true;
                                }
                            } finally
                            {
                                this.cellsBusy = 0;
                            }
                            if (created)
                            {
                                break;
                            }
                            continue;           // Slot is now non-empty
                        }
                    }
                    collide = false;
                }
                else if (! wasUncontended)       // CAS already known to fail
                {
                    wasUncontended = true;      // Continue after rehash
                }
                else if (U.compareAndSwapLong(a, CELLVALUE, v = a.value, v + x))
                {
                    break;
                }
                else if ((this.counterCells != as) || (n >= NCPU))
                {
                    collide = false;            // At max size or stale
                }
                else if (! collide)
                {
                    collide = true;
                }
                else if ((this.cellsBusy == 0) && U.compareAndSwapInt(this, CELLSBUSY, 0, 1))
                {
                    try
                    {
                        if (this.counterCells == as)
                        {// Expand table unless stale
                            final CounterCell[] rs = new CounterCell[n << 1];
                            System.arraycopy(as, 0, rs, 0, n);
                            this.counterCells = rs;
                        }
                    } finally
                    {
                        this.cellsBusy = 0;
                    }
                    collide = false;
                    continue;                   // Retry with expanded table
                }
                h = DioriteThreadLocalRandom.advanceProbe(h);
            }
            else if ((this.cellsBusy == 0) && (this.counterCells == as) &&
                             U.compareAndSwapInt(this, CELLSBUSY, 0, 1))
            {
                boolean init = false;
                try
                {                           // Initialize table
                    if (this.counterCells == as)
                    {
                        final CounterCell[] rs = new CounterCell[2];
                        rs[h & 1] = new CounterCell(x);
                        this.counterCells = rs;
                        init = true;
                    }
                } finally
                {
                    this.cellsBusy = 0;
                }
                if (init)
                {
                    break;
                }
            }
            else if (U.compareAndSwapLong(this, BASECOUNT, v = this.baseCount, v + x))
            {
                break;                          // Fall back on using base
            }
        }
    }

    /**
     * Replaces all linked nodes in bin at given index unless table is
     * too small, in which case resizes instead.
     */
    private void treeifyBin(final Node<K, V>[] tab, final int index)
    {
        final Node<K, V> b;
        final int n;
        if (tab != null)
        {
            if ((n = tab.length) < MIN_TREEIFY_CAPACITY)
            {
                this.tryPresize(n << 1);
            }
            else if ((((b = tabAt(tab, index))) != null) && (b.hash >= 0))
            {
                synchronized (b)
                {
                    if (tabAt(tab, index) == b)
                    {
                        TreeNode<K, V> hd = null, tl = null;
                        for (Node<K, V> e = b; e != null; e = e.next)
                        {
                            final TreeNode<K, V> p = new TreeNode<>(e.hash, e.key, e.val, null, null);
                            if ((p.prev = tl) == null)
                            {
                                hd = p;
                            }
                            else
                            {
                                tl.next = p;
                            }
                            tl = p;
                        }
                        setTabAt(tab, index, new TreeBin<>(hd));
                    }
                }
            }
        }
    }

    /**
     * Computes initial batch value for bulk tasks. The returned value
     * is approximately exp2 of the number of times (minus one) to
     * split task by two before executing leaf action. This value is
     * faster to compute and more convenient to use as a guide to
     * splitting than is the depth, since it is used while dividing by
     * two anyway.
     */
    final int batchFor(final long b)
    {
        long n;
        if ((b == Long.MAX_VALUE) || (((n = this.sumCount())) <= 1L) || (n < b))
        {
            return 0;
        }
        final int sp = ForkJoinPool.getCommonPoolParallelism() << 2; // slack of 4
        return ((b <= 0L) || (((n /= b)) >= sp)) ? sp : (int) n;
    }

    /**
     * Performs the given action for each (key, value).
     *
     * @param parallelismThreshold the (estimated) number of elements
     *                             needed for this operation to be executed in parallel
     * @param action               the action
     *
     * @since 1.8
     */
    public void forEach(final long parallelismThreshold, final BiConsumer<? super K, ? super V> action)
    {
        if (action == null)
        {
            throw new NullPointerException();
        }
        new ForEachMappingTask<>(null, this.batchFor(parallelismThreshold), 0, 0, this.table, action).invoke();
    }

    /**
     * Performs the given action for each non-null transformation
     * of each (key, value).
     *
     * @param parallelismThreshold the (estimated) number of elements
     *                             needed for this operation to be executed in parallel
     * @param transformer          a function returning the transformation
     *                             for an element, or null if there is no transformation (in
     *                             which case the action is not applied)
     * @param action               the action
     * @param <U>                  the return type of the transformer
     *
     * @since 1.8
     */
    public <U> void forEach(final long parallelismThreshold, final BiFunction<? super K, ? super V, ? extends U> transformer, final Consumer<? super U> action)
    {
        if ((transformer == null) || (action == null))
        {
            throw new NullPointerException();
        }
        new ForEachTransformedMappingTask<>(null, this.batchFor(parallelismThreshold), 0, 0, this.table, transformer, action).invoke();
    }

    /* ---------------- Counter support -------------- */

    /**
     * Returns a non-null result from applying the given search
     * function on each (key, value), or null if none.  Upon
     * success, further element processing is suppressed and the
     * results of any other parallel invocations of the search
     * function are ignored.
     *
     * @param parallelismThreshold the (estimated) number of elements
     *                             needed for this operation to be executed in parallel
     * @param searchFunction       a function returning a non-null
     *                             result on success, else null
     * @param <U>                  the return type of the search function
     *
     * @return a non-null result from applying the given search
     * function on each (key, value), or null if none
     *
     * @since 1.8
     */
    public <U> U search(final long parallelismThreshold, final BiFunction<? super K, ? super V, ? extends U> searchFunction)
    {
        if (searchFunction == null)
        {
            throw new NullPointerException();
        }
        return new SearchMappingsTask<>(null, this.batchFor(parallelismThreshold), 0, 0, this.table, searchFunction, new AtomicReference<>()).invoke();
    }

    /**
     * Returns the result of accumulating the given transformation
     * of all (key, value) pairs using the given reducer to
     * combine values, or null if none.
     *
     * @param parallelismThreshold the (estimated) number of elements
     *                             needed for this operation to be executed in parallel
     * @param transformer          a function returning the transformation
     *                             for an element, or null if there is no transformation (in
     *                             which case it is not combined)
     * @param reducer              a commutative associative combining function
     * @param <U>                  the return type of the transformer
     *
     * @return the result of accumulating the given transformation
     * of all (key, value) pairs
     *
     * @since 1.8
     */
    public <U> U reduce(final long parallelismThreshold, final BiFunction<? super K, ? super V, ? extends U> transformer, final BiFunction<? super U, ? super U, ? extends U> reducer)
    {
        if ((transformer == null) || (reducer == null))
        {
            throw new NullPointerException();
        }
        return new MapReduceMappingsTask<>(null, this.batchFor(parallelismThreshold), 0, 0, this.table, null, transformer, reducer).invoke();
    }

    /**
     * Returns the result of accumulating the given transformation
     * of all (key, value) pairs using the given reducer to
     * combine values, and the given basis as an identity value.
     *
     * @param parallelismThreshold the (estimated) number of elements
     *                             needed for this operation to be executed in parallel
     * @param transformer          a function returning the transformation
     *                             for an element
     * @param basis                the identity (initial default value) for the reduction
     * @param reducer              a commutative associative combining function
     *
     * @return the result of accumulating the given transformation
     * of all (key, value) pairs
     *
     * @since 1.8
     */
    public double reduceToDouble(final long parallelismThreshold, final ToDoubleBiFunction<? super K, ? super V> transformer, final double basis, final DoubleBinaryOperator reducer)
    {
        if ((transformer == null) || (reducer == null))
        {
            throw new NullPointerException();
        }
        return new MapReduceMappingsToDoubleTask<>(null, this.batchFor(parallelismThreshold), 0, 0, this.table, null, transformer, basis, reducer).invoke();
    }

    /* ---------------- Conversion from/to TreeBins -------------- */

    /**
     * Returns the result of accumulating the given transformation
     * of all (key, value) pairs using the given reducer to
     * combine values, and the given basis as an identity value.
     *
     * @param parallelismThreshold the (estimated) number of elements
     *                             needed for this operation to be executed in parallel
     * @param transformer          a function returning the transformation
     *                             for an element
     * @param basis                the identity (initial default value) for the reduction
     * @param reducer              a commutative associative combining function
     *
     * @return the result of accumulating the given transformation
     * of all (key, value) pairs
     *
     * @since 1.8
     */
    public long reduceToLong(final long parallelismThreshold, final ToLongBiFunction<? super K, ? super V> transformer, final long basis, final LongBinaryOperator reducer)
    {
        if ((transformer == null) || (reducer == null))
        {
            throw new NullPointerException();
        }
        return new MapReduceMappingsToLongTask<>(null, this.batchFor(parallelismThreshold), 0, 0, this.table, null, transformer, basis, reducer).invoke();
    }

    /**
     * Returns the result of accumulating the given transformation
     * of all (key, value) pairs using the given reducer to
     * combine values, and the given basis as an identity value.
     *
     * @param parallelismThreshold the (estimated) number of elements
     *                             needed for this operation to be executed in parallel
     * @param transformer          a function returning the transformation
     *                             for an element
     * @param basis                the identity (initial default value) for the reduction
     * @param reducer              a commutative associative combining function
     *
     * @return the result of accumulating the given transformation
     * of all (key, value) pairs
     *
     * @since 1.8
     */
    public int reduceToInt(final long parallelismThreshold, final ToIntBiFunction<? super K, ? super V> transformer, final int basis, final IntBinaryOperator reducer)
    {
        if ((transformer == null) || (reducer == null))
        {
            throw new NullPointerException();
        }
        return new MapReduceMappingsToIntTask<>(null, this.batchFor(parallelismThreshold), 0, 0, this.table, null, transformer, basis, reducer).invoke();
    }

    /* ---------------- TreeNodes -------------- */

    /**
     * Performs the given action for each key.
     *
     * @param parallelismThreshold the (estimated) number of elements
     *                             needed for this operation to be executed in parallel
     * @param action               the action
     *
     * @since 1.8
     */
    public void forEachKey(final long parallelismThreshold, final Consumer<? super K> action)
    {
        if (action == null)
        {
            throw new NullPointerException();
        }
        new ForEachKeyTask<>(null, this.batchFor(parallelismThreshold), 0, 0, this.table, action).invoke();
    }

    /* ---------------- TreeBins -------------- */

    /**
     * Performs the given action for each non-null transformation
     * of each key.
     *
     * @param parallelismThreshold the (estimated) number of elements
     *                             needed for this operation to be executed in parallel
     * @param transformer          a function returning the transformation
     *                             for an element, or null if there is no transformation (in
     *                             which case the action is not applied)
     * @param action               the action
     * @param <U>                  the return type of the transformer
     *
     * @since 1.8
     */
    public <U> void forEachKey(final long parallelismThreshold, final Function<? super K, ? extends U> transformer, final Consumer<? super U> action)
    {
        if ((transformer == null) || (action == null))
        {
            throw new NullPointerException();
        }
        new ForEachTransformedKeyTask<>(null, this.batchFor(parallelismThreshold), 0, 0, this.table, transformer, action).invoke();
    }

    /* ----------------Table Traversal -------------- */

    /**
     * Returns a non-null result from applying the given search
     * function on each key, or null if none. Upon success,
     * further element processing is suppressed and the results of
     * any other parallel invocations of the search function are
     * ignored.
     *
     * @param parallelismThreshold the (estimated) number of elements
     *                             needed for this operation to be executed in parallel
     * @param searchFunction       a function returning a non-null
     *                             result on success, else null
     * @param <U>                  the return type of the search function
     *
     * @return a non-null result from applying the given search
     * function on each key, or null if none
     *
     * @since 1.8
     */
    public <U> U searchKeys(final long parallelismThreshold, final Function<? super K, ? extends U> searchFunction)
    {
        if (searchFunction == null)
        {
            throw new NullPointerException();
        }
        return new SearchKeysTask<>(null, this.batchFor(parallelismThreshold), 0, 0, this.table, searchFunction, new AtomicReference<>()).invoke();
    }

    /**
     * Returns the result of accumulating all keys using the given
     * reducer to combine values, or null if none.
     *
     * @param parallelismThreshold the (estimated) number of elements
     *                             needed for this operation to be executed in parallel
     * @param reducer              a commutative associative combining function
     *
     * @return the result of accumulating all keys using the given
     * reducer to combine values, or null if none
     *
     * @since 1.8
     */
    public K reduceKeys(final long parallelismThreshold, final BiFunction<? super K, ? super K, ? extends K> reducer)
    {
        if (reducer == null)
        {
            throw new NullPointerException();
        }
        return new ReduceKeysTask<>(null, this.batchFor(parallelismThreshold), 0, 0, this.table, null, reducer).invoke();
    }

    /**
     * Returns the result of accumulating the given transformation
     * of all keys using the given reducer to combine values, or
     * null if none.
     *
     * @param parallelismThreshold the (estimated) number of elements
     *                             needed for this operation to be executed in parallel
     * @param transformer          a function returning the transformation
     *                             for an element, or null if there is no transformation (in
     *                             which case it is not combined)
     * @param reducer              a commutative associative combining function
     * @param <U>                  the return type of the transformer
     *
     * @return the result of accumulating the given transformation
     * of all keys
     *
     * @since 1.8
     */
    public <U> U reduceKeys(final long parallelismThreshold, final Function<? super K, ? extends U> transformer, final BiFunction<? super U, ? super U, ? extends U> reducer)
    {
        if ((transformer == null) || (reducer == null))
        {
            throw new NullPointerException();
        }
        return new MapReduceKeysTask<>(null, this.batchFor(parallelismThreshold), 0, 0, this.table, null, transformer, reducer).invoke();
    }

    /**
     * Returns the result of accumulating the given transformation
     * of all keys using the given reducer to combine values, and
     * the given basis as an identity value.
     *
     * @param parallelismThreshold the (estimated) number of elements
     *                             needed for this operation to be executed in parallel
     * @param transformer          a function returning the transformation
     *                             for an element
     * @param basis                the identity (initial default value) for the reduction
     * @param reducer              a commutative associative combining function
     *
     * @return the result of accumulating the given transformation
     * of all keys
     *
     * @since 1.8
     */
    public double reduceKeysToDouble(final long parallelismThreshold, final ToDoubleFunction<? super K> transformer, final double basis, final DoubleBinaryOperator reducer)
    {
        if ((transformer == null) || (reducer == null))
        {
            throw new NullPointerException();
        }
        return new MapReduceKeysToDoubleTask<>(null, this.batchFor(parallelismThreshold), 0, 0, this.table, null, transformer, basis, reducer).invoke();
    }

    /**
     * Returns the result of accumulating the given transformation
     * of all keys using the given reducer to combine values, and
     * the given basis as an identity value.
     *
     * @param parallelismThreshold the (estimated) number of elements
     *                             needed for this operation to be executed in parallel
     * @param transformer          a function returning the transformation
     *                             for an element
     * @param basis                the identity (initial default value) for the reduction
     * @param reducer              a commutative associative combining function
     *
     * @return the result of accumulating the given transformation
     * of all keys
     *
     * @since 1.8
     */
    public long reduceKeysToLong(final long parallelismThreshold, final ToLongFunction<? super K> transformer, final long basis, final LongBinaryOperator reducer)
    {
        if ((transformer == null) || (reducer == null))
        {
            throw new NullPointerException();
        }
        return new MapReduceKeysToLongTask<>(null, this.batchFor(parallelismThreshold), 0, 0, this.table, null, transformer, basis, reducer).invoke();
    }

    /**
     * Returns the result of accumulating the given transformation
     * of all keys using the given reducer to combine values, and
     * the given basis as an identity value.
     *
     * @param parallelismThreshold the (estimated) number of elements
     *                             needed for this operation to be executed in parallel
     * @param transformer          a function returning the transformation
     *                             for an element
     * @param basis                the identity (initial default value) for the reduction
     * @param reducer              a commutative associative combining function
     *
     * @return the result of accumulating the given transformation
     * of all keys
     *
     * @since 1.8
     */
    public int reduceKeysToInt(final long parallelismThreshold, final ToIntFunction<? super K> transformer, final int basis, final IntBinaryOperator reducer)
    {
        if ((transformer == null) || (reducer == null))
        {
            throw new NullPointerException();
        }
        return new MapReduceKeysToIntTask<>(null, this.batchFor(parallelismThreshold), 0, 0, this.table, null, transformer, basis, reducer).invoke();
    }

    /**
     * Performs the given action for each value.
     *
     * @param parallelismThreshold the (estimated) number of elements
     *                             needed for this operation to be executed in parallel
     * @param action               the action
     *
     * @since 1.8
     */
    public void forEachValue(final long parallelismThreshold, final Consumer<? super V> action)
    {
        if (action == null)
        {
            throw new NullPointerException();
        }
        new ForEachValueTask<>(null, this.batchFor(parallelismThreshold), 0, 0, this.table, action).invoke();
    }

    /**
     * Performs the given action for each non-null transformation
     * of each value.
     *
     * @param parallelismThreshold the (estimated) number of elements
     *                             needed for this operation to be executed in parallel
     * @param transformer          a function returning the transformation
     *                             for an element, or null if there is no transformation (in
     *                             which case the action is not applied)
     * @param action               the action
     * @param <U>                  the return type of the transformer
     *
     * @since 1.8
     */
    public <U> void forEachValue(final long parallelismThreshold, final Function<? super V, ? extends U> transformer, final Consumer<? super U> action)
    {
        if ((transformer == null) || (action == null))
        {
            throw new NullPointerException();
        }
        new ForEachTransformedValueTask<>(null, this.batchFor(parallelismThreshold), 0, 0, this.table, transformer, action).invoke();
    }

    /**
     * Returns a non-null result from applying the given search
     * function on each value, or null if none.  Upon success,
     * further element processing is suppressed and the results of
     * any other parallel invocations of the search function are
     * ignored.
     *
     * @param parallelismThreshold the (estimated) number of elements
     *                             needed for this operation to be executed in parallel
     * @param searchFunction       a function returning a non-null
     *                             result on success, else null
     * @param <U>                  the return type of the search function
     *
     * @return a non-null result from applying the given search
     * function on each value, or null if none
     *
     * @since 1.8
     */
    public <U> U searchValues(final long parallelismThreshold, final Function<? super V, ? extends U> searchFunction)
    {
        if (searchFunction == null)
        {
            throw new NullPointerException();
        }
        return new SearchValuesTask<>(null, this.batchFor(parallelismThreshold), 0, 0, this.table, searchFunction, new AtomicReference<>()).invoke();
    }

    /**
     * Returns the result of accumulating all values using the
     * given reducer to combine values, or null if none.
     *
     * @param parallelismThreshold the (estimated) number of elements
     *                             needed for this operation to be executed in parallel
     * @param reducer              a commutative associative combining function
     *
     * @return the result of accumulating all values
     *
     * @since 1.8
     */
    public V reduceValues(final long parallelismThreshold, final BiFunction<? super V, ? super V, ? extends V> reducer)
    {
        if (reducer == null)
        {
            throw new NullPointerException();
        }
        return new ReduceValuesTask<>(null, this.batchFor(parallelismThreshold), 0, 0, this.table, null, reducer).invoke();
    }

    // Parallel bulk operations

    /**
     * Returns the result of accumulating the given transformation
     * of all values using the given reducer to combine values, or
     * null if none.
     *
     * @param parallelismThreshold the (estimated) number of elements
     *                             needed for this operation to be executed in parallel
     * @param transformer          a function returning the transformation
     *                             for an element, or null if there is no transformation (in
     *                             which case it is not combined)
     * @param reducer              a commutative associative combining function
     * @param <U>                  the return type of the transformer
     *
     * @return the result of accumulating the given transformation
     * of all values
     *
     * @since 1.8
     */
    public <U> U reduceValues(final long parallelismThreshold, final Function<? super V, ? extends U> transformer, final BiFunction<? super U, ? super U, ? extends U> reducer)
    {
        if ((transformer == null) || (reducer == null))
        {
            throw new NullPointerException();
        }
        return new MapReduceValuesTask<>(null, this.batchFor(parallelismThreshold), 0, 0, this.table, null, transformer, reducer).invoke();
    }

    /**
     * Returns the result of accumulating the given transformation
     * of all values using the given reducer to combine values,
     * and the given basis as an identity value.
     *
     * @param parallelismThreshold the (estimated) number of elements
     *                             needed for this operation to be executed in parallel
     * @param transformer          a function returning the transformation
     *                             for an element
     * @param basis                the identity (initial default value) for the reduction
     * @param reducer              a commutative associative combining function
     *
     * @return the result of accumulating the given transformation
     * of all values
     *
     * @since 1.8
     */
    public double reduceValuesToDouble(final long parallelismThreshold, final ToDoubleFunction<? super V> transformer, final double basis, final DoubleBinaryOperator reducer)
    {
        if ((transformer == null) || (reducer == null))
        {
            throw new NullPointerException();
        }
        return new MapReduceValuesToDoubleTask<>(null, this.batchFor(parallelismThreshold), 0, 0, this.table, null, transformer, basis, reducer).invoke();
    }

    /**
     * Returns the result of accumulating the given transformation
     * of all values using the given reducer to combine values,
     * and the given basis as an identity value.
     *
     * @param parallelismThreshold the (estimated) number of elements
     *                             needed for this operation to be executed in parallel
     * @param transformer          a function returning the transformation
     *                             for an element
     * @param basis                the identity (initial default value) for the reduction
     * @param reducer              a commutative associative combining function
     *
     * @return the result of accumulating the given transformation
     * of all values
     *
     * @since 1.8
     */
    public long reduceValuesToLong(final long parallelismThreshold, final ToLongFunction<? super V> transformer, final long basis, final LongBinaryOperator reducer)
    {
        if ((transformer == null) || (reducer == null))
        {
            throw new NullPointerException();
        }
        return new MapReduceValuesToLongTask<>(null, this.batchFor(parallelismThreshold), 0, 0, this.table, null, transformer, basis, reducer).invoke();
    }

    /**
     * Returns the result of accumulating the given transformation
     * of all values using the given reducer to combine values,
     * and the given basis as an identity value.
     *
     * @param parallelismThreshold the (estimated) number of elements
     *                             needed for this operation to be executed in parallel
     * @param transformer          a function returning the transformation
     *                             for an element
     * @param basis                the identity (initial default value) for the reduction
     * @param reducer              a commutative associative combining function
     *
     * @return the result of accumulating the given transformation
     * of all values
     *
     * @since 1.8
     */
    public int reduceValuesToInt(final long parallelismThreshold, final ToIntFunction<? super V> transformer, final int basis, final IntBinaryOperator reducer)
    {
        if ((transformer == null) || (reducer == null))
        {
            throw new NullPointerException();
        }
        return new MapReduceValuesToIntTask<>(null, this.batchFor(parallelismThreshold), 0, 0, this.table, null, transformer, basis, reducer).invoke();
    }

    /**
     * Performs the given action for each entry.
     *
     * @param parallelismThreshold the (estimated) number of elements
     *                             needed for this operation to be executed in parallel
     * @param action               the action
     *
     * @since 1.8
     */
    public void forEachEntry(final long parallelismThreshold, final Consumer<? super Map.Entry<K, V>> action)
    {
        if (action == null)
        {
            throw new NullPointerException();
        }
        new ForEachEntryTask<>(null, this.batchFor(parallelismThreshold), 0, 0, this.table, action).invoke();
    }

    /**
     * Performs the given action for each non-null transformation
     * of each entry.
     *
     * @param parallelismThreshold the (estimated) number of elements
     *                             needed for this operation to be executed in parallel
     * @param transformer          a function returning the transformation
     *                             for an element, or null if there is no transformation (in
     *                             which case the action is not applied)
     * @param action               the action
     * @param <U>                  the return type of the transformer
     *
     * @since 1.8
     */
    public <U> void forEachEntry(final long parallelismThreshold, final Function<Map.Entry<K, V>, ? extends U> transformer, final Consumer<? super U> action)
    {
        if ((transformer == null) || (action == null))
        {
            throw new NullPointerException();
        }
        new ForEachTransformedEntryTask<>(null, this.batchFor(parallelismThreshold), 0, 0, this.table, transformer, action).invoke();
    }

    /**
     * Returns a non-null result from applying the given search
     * function on each entry, or null if none.  Upon success,
     * further element processing is suppressed and the results of
     * any other parallel invocations of the search function are
     * ignored.
     *
     * @param parallelismThreshold the (estimated) number of elements
     *                             needed for this operation to be executed in parallel
     * @param searchFunction       a function returning a non-null
     *                             result on success, else null
     * @param <U>                  the return type of the search function
     *
     * @return a non-null result from applying the given search
     * function on each entry, or null if none
     *
     * @since 1.8
     */
    public <U> U searchEntries(final long parallelismThreshold, final Function<Map.Entry<K, V>, ? extends U> searchFunction)
    {
        if (searchFunction == null)
        {
            throw new NullPointerException();
        }
        return new SearchEntriesTask<>(null, this.batchFor(parallelismThreshold), 0, 0, this.table, searchFunction, new AtomicReference<>()).invoke();
    }

    /**
     * Returns the result of accumulating all entries using the
     * given reducer to combine values, or null if none.
     *
     * @param parallelismThreshold the (estimated) number of elements
     *                             needed for this operation to be executed in parallel
     * @param reducer              a commutative associative combining function
     *
     * @return the result of accumulating all entries
     *
     * @since 1.8
     */
    public Map.Entry<K, V> reduceEntries(final long parallelismThreshold, final BiFunction<Map.Entry<K, V>, Map.Entry<K, V>, ? extends Map.Entry<K, V>> reducer)
    {
        if (reducer == null)
        {
            throw new NullPointerException();
        }
        return new ReduceEntriesTask<>(null, this.batchFor(parallelismThreshold), 0, 0, this.table, null, reducer).invoke();
    }

    /**
     * Returns the result of accumulating the given transformation
     * of all entries using the given reducer to combine values,
     * or null if none.
     *
     * @param parallelismThreshold the (estimated) number of elements
     *                             needed for this operation to be executed in parallel
     * @param transformer          a function returning the transformation
     *                             for an element, or null if there is no transformation (in
     *                             which case it is not combined)
     * @param reducer              a commutative associative combining function
     * @param <U>                  the return type of the transformer
     *
     * @return the result of accumulating the given transformation
     * of all entries
     *
     * @since 1.8
     */
    public <U> U reduceEntries(final long parallelismThreshold, final Function<Map.Entry<K, V>, ? extends U> transformer, final BiFunction<? super U, ? super U, ? extends U> reducer)
    {
        if ((transformer == null) || (reducer == null))
        {
            throw new NullPointerException();
        }
        return new MapReduceEntriesTask<>(null, this.batchFor(parallelismThreshold), 0, 0, this.table, null, transformer, reducer).invoke();
    }

    /**
     * Returns the result of accumulating the given transformation
     * of all entries using the given reducer to combine values,
     * and the given basis as an identity value.
     *
     * @param parallelismThreshold the (estimated) number of elements
     *                             needed for this operation to be executed in parallel
     * @param transformer          a function returning the transformation
     *                             for an element
     * @param basis                the identity (initial default value) for the reduction
     * @param reducer              a commutative associative combining function
     *
     * @return the result of accumulating the given transformation
     * of all entries
     *
     * @since 1.8
     */
    public double reduceEntriesToDouble(final long parallelismThreshold, final ToDoubleFunction<Map.Entry<K, V>> transformer, final double basis, final DoubleBinaryOperator reducer)
    {
        if ((transformer == null) || (reducer == null))
        {
            throw new NullPointerException();
        }
        return new MapReduceEntriesToDoubleTask<>(null, this.batchFor(parallelismThreshold), 0, 0, this.table, null, transformer, basis, reducer).invoke();
    }

    /**
     * Returns the result of accumulating the given transformation
     * of all entries using the given reducer to combine values,
     * and the given basis as an identity value.
     *
     * @param parallelismThreshold the (estimated) number of elements
     *                             needed for this operation to be executed in parallel
     * @param transformer          a function returning the transformation
     *                             for an element
     * @param basis                the identity (initial default value) for the reduction
     * @param reducer              a commutative associative combining function
     *
     * @return the result of accumulating the given transformation
     * of all entries
     *
     * @since 1.8
     */
    public long reduceEntriesToLong(final long parallelismThreshold, final ToLongFunction<Map.Entry<K, V>> transformer, final long basis, final LongBinaryOperator reducer)
    {
        if ((transformer == null) || (reducer == null))
        {
            throw new NullPointerException();
        }
        return new MapReduceEntriesToLongTask<>(null, this.batchFor(parallelismThreshold), 0, 0, this.table, null, transformer, basis, reducer).invoke();
    }

    /**
     * Returns the result of accumulating the given transformation
     * of all entries using the given reducer to combine values,
     * and the given basis as an identity value.
     *
     * @param parallelismThreshold the (estimated) number of elements
     *                             needed for this operation to be executed in parallel
     * @param transformer          a function returning the transformation
     *                             for an element
     * @param basis                the identity (initial default value) for the reduction
     * @param reducer              a commutative associative combining function
     *
     * @return the result of accumulating the given transformation
     * of all entries
     *
     * @since 1.8
     */
    public int reduceEntriesToInt(final long parallelismThreshold, final ToIntFunction<Map.Entry<K, V>> transformer, final int basis, final IntBinaryOperator reducer)
    {
        if ((transformer == null) || (reducer == null))
        {
            throw new NullPointerException();
        }
        return new MapReduceEntriesToIntTask<>(null, this.batchFor(parallelismThreshold), 0, 0, this.table, null, transformer, basis, reducer).invoke();
    }

    /**
     * Spreads (XORs) higher bits of hash to lower and also forces top
     * bit to 0. Because the table uses power-of-two masking, sets of
     * hashes that vary only in bits above the current mask will
     * always collide. (Among known examples are sets of Float keys
     * holding consecutive whole numbers in small tables.)  So we
     * apply a transform that spreads the impact of higher bits
     * downward. There is a tradeoff between speed, utility, and
     * quality of bit-spreading. Because many common sets of hashes
     * are already reasonably distributed (so don't benefit from
     * spreading), and because we use trees to handle large sets of
     * collisions in bins, we just XOR some shifted bits in the
     * cheapest possible way to reduce systematic lossage, as well as
     * to incorporate impact of the highest bits that would otherwise
     * never be used in index calculations because of table bounds.
     */
    static int spread(final int h)
    {
        return (h ^ (h >>> 16)) & HASH_BITS;
    }

    /**
     * Returns a power of two table size for the given desired capacity.
     * See Hackers Delight, sec 3.2
     */
    private static int tableSizeFor(final int c)
    {
        int n = c - 1;
        n |= n >>> 1;
        n |= n >>> 2;
        n |= n >>> 4;
        n |= n >>> 8;
        n |= n >>> 16;
        return (n < 0) ? 1 : ((n >= MAXIMUM_CAPACITY) ? MAXIMUM_CAPACITY : (n + 1));
    }

    /**
     * Returns x's Class if it is of the form "class C implements
     * Comparable<C>", else null.
     */
    static Class<?> comparableClassFor(final Object x)
    {
        if (x instanceof Comparable)
        {
            final Class<?> c;
            final Type[] ts;
            Type[] as;
            Type t;
            ParameterizedType p;
            if ((c = x.getClass()) == String.class) // bypass checks
            {
                return c;
            }
            if ((ts = c.getGenericInterfaces()) != null)
            {
                for (final Type t1 : ts)
                {
                    if ((((t = t1)) instanceof ParameterizedType) &&
                                ((p = (ParameterizedType) t).getRawType() == Comparable.class) &&
                                (((as = p.getActualTypeArguments())) != null) &&
                                (as.length == 1) && (as[0] == c)) // type arg is c
                    {
                        return c;
                    }
                }
            }
        }
        return null;
    }

    /**
     * Returns k.compareTo(x) if x matches kc (k's screened comparable
     * class), else 0.
     */
    @SuppressWarnings({"rawtypes", "unchecked"}) // for cast to Comparable
    static int compareComparables(final Class<?> kc, final Object k, final Object x)
    {
        return (((x == null) || (x.getClass() != kc)) ? 0 : ((Comparable) k).compareTo(x));
    }

    @SuppressWarnings("unchecked")
    static <K, V> Node<K, V> tabAt(final Node<K, V>[] tab, final int i)
    {
        return (Node<K, V>) U.getObjectVolatile(tab, ((long) i << ASHIFT) + ABASE);
    }

    static <K, V> boolean casTabAt(final Node<K, V>[] tab, final int i, final Node<K, V> c, final Node<K, V> v)
    {
        return U.compareAndSwapObject(tab, ((long) i << ASHIFT) + ABASE, c, v);
    }

    static <K, V> void setTabAt(final Node<K, V>[] tab, final int i, final Node<K, V> v)
    {
        U.putObjectVolatile(tab, ((long) i << ASHIFT) + ABASE, v);
    }

    /**
     * Creates a new {@link Set} backed by a ConcurrentIdentityHashMap
     * from the given type to {@code Boolean.TRUE}.
     *
     * @param <K> the element type of the returned set
     *
     * @return the new set
     *
     * @since 1.8
     */
    public static <K> KeySetView<K, Boolean> newKeySet()
    {
        return new KeySetView<>(new ConcurrentIdentityHashMap<>(), Boolean.TRUE);
    }

    /**
     * Creates a new {@link Set} backed by a ConcurrentIdentityHashMap
     * from the given type to {@code Boolean.TRUE}.
     *
     * @param initialCapacity The implementation performs internal
     *                        sizing to accommodate this many elements.
     * @param <K>             the element type of the returned set
     *
     * @return the new set
     *
     * @throws IllegalArgumentException if the initial capacity of
     *                                  elements is negative
     * @since 1.8
     */
    public static <K> KeySetView<K, Boolean> newKeySet(final int initialCapacity)
    {
        return new KeySetView<>(new ConcurrentIdentityHashMap<>(initialCapacity), Boolean.TRUE);
    }

    /**
     * Returns the stamp bits for resizing a table of size n.
     * Must be negative when shifted left by RESIZE_STAMP_SHIFT.
     */
    static int resizeStamp(final int n)
    {
        return Integer.numberOfLeadingZeros(n) | (1 << (RESIZE_STAMP_BITS - 1));
    }

    /**
     * Returns a list on non-TreeNodes replacing those in given list.
     */
    static <K, V> Node<K, V> untreeify(final Node<K, V> b)
    {
        Node<K, V> hd = null, tl = null;
        for (Node<K, V> q = b; q != null; q = q.next)
        {
            final Node<K, V> p = new Node<>(q.hash, q.key, q.val, null);
            if (tl == null)
            {
                hd = p;
            }
            else
            {
                tl.next = p;
            }
            tl = p;
        }
        return hd;
    }

    /**
     * Key-value entry.  This class is never exported out as a
     * user-mutable Map.Entry (i.e., one supporting setValue; see
     * MapEntry below), but can be used for read-only traversals used
     * in bulk tasks.  Subclasses of Node with a negative hash field
     * are special, and contain null keys and values (but are never
     * exported).  Otherwise, keys and vals are never null.
     */
    static class Node<K, V> implements Map.Entry<K, V>
    {
        final    int        hash;
        final    K          key;
        volatile V          val;
        volatile Node<K, V> next;

        Node(final int hash, final K key, final V val, final Node<K, V> next)
        {
            this.hash = hash;
            this.key = key;
            this.val = val;
            this.next = next;
        }

        @Override
        public final K getKey()
        {
            return this.key;
        }

        public final int hashCode()
        {
            return this.key.hashCode() ^ this.val.hashCode();
        }

        @Override
        public final V getValue()
        {
            return this.val;
        }

        /**
         * Virtualized support for map.get(); overridden in subclasses.
         */
        Node<K, V> find(final int h, final Object k)
        {
            Node<K, V> e = this;
            if (k != null)
            {
                do
                {
                    if ((e.hash == h) && ((e.key) == k))
                    {
                        return e;
                    }
                } while ((e = e.next) != null);
            }
            return null;
        }

        public final String toString()
        {
            return this.key + "=" + this.val;
        }

        @Override
        public final V setValue(final V value)
        {
            throw new UnsupportedOperationException();
        }

        public final boolean equals(final Object o)
        {
            final Object k;
            final Object v;
            final Map.Entry<?, ?> e;
            return ((o instanceof Entry) &&
                            (((k = (e = (Entry<?, ?>) o).getKey())) != null) &&
                            (((v = e.getValue())) != null) &&
                            (k == this.key) &&
                            (v == this.val));
        }


    }

    /**
     * Stripped-down version of helper class used in previous version,
     * declared for the sake of serialization compatibility
     */
    @SuppressWarnings("ClassHasNoToStringMethod")
    static class Segment<K, V> extends ReentrantLock implements Serializable
    {
        private static final long serialVersionUID = 0;
        final float loadFactor;

        Segment(final float lf)
        {
            this.loadFactor = lf;
        }
    }

    /**
     * A node inserted at head of bins during transfer operations.
     */
    static final class ForwardingNode<K, V> extends Node<K, V>
    {
        final Node<K, V>[] nextTable;

        ForwardingNode(final Node<K, V>[] tab)
        {
            super(MOVED, null, null, null);
            this.nextTable = tab;
        }

        @Override
        Node<K, V> find(final int h, final Object k)
        {
            // loop to avoid arbitrarily deep recursion on forwarding nodes
            outer:
            for (Node<K, V>[] tab = this.nextTable; ; )
            {
                Node<K, V> e;
                final int n;
                if ((k == null) || (tab == null) || (((n = tab.length)) == 0) ||
                            (((e = tabAt(tab, (n - 1) & h))) == null))
                {
                    return null;
                }
                while (true)
                {
                    final int eh;
                    final K ek;
                    if ((((eh = e.hash)) == h) && ((((ek = e.key)) == k) || ((ek != null) && k.equals(ek))))
                    {
                        return e;
                    }
                    if (eh < 0)
                    {
                        if (e instanceof ForwardingNode)
                        {
                            tab = ((ForwardingNode<K, V>) e).nextTable;
                            continue outer;
                        }
                        else
                        {
                            return e.find(h, k);
                        }
                    }
                    if ((e = e.next) == null)
                    {
                        return null;
                    }
                }
            }
        }
    }

    /**
     * A place-holder node used in computeIfAbsent and compute
     */
    static final class ReservationNode<K, V> extends Node<K, V>
    {
        ReservationNode()
        {
            super(RESERVED, null, null, null);
        }

        @Override
        Node<K, V> find(final int h, final Object k)
        {
            return null;
        }
    }

    /**
     * A padded cell for distributing counts.  Adapted from LongAdder
     * and Striped64.  See their internal docs for explanation.
     */
    @SuppressWarnings("ClassHasNoToStringMethod")
    @sun.misc.Contended
    static final class CounterCell
    {
        final long value;

        CounterCell(final long x)
        {
            this.value = x;
        }
    }

    /**
     * Nodes for use in TreeBins
     */
    static final class TreeNode<K, V> extends Node<K, V>
    {
        TreeNode<K, V> parent;  // red-black tree links
        TreeNode<K, V> left;
        TreeNode<K, V> right;
        TreeNode<K, V> prev;    // needed to unlink next upon deletion
        boolean        red;

        TreeNode(final int hash, final K key, final V val, final Node<K, V> next, final TreeNode<K, V> parent)
        {
            super(hash, key, val, next);
            this.parent = parent;
        }

        /**
         * Returns the TreeNode (or null if not found) for the given key
         * starting at given root.
         */
        final TreeNode<K, V> findTreeNode(final int h, final Object k, Class<?> kc)
        {
            if (k != null)
            {
                TreeNode<K, V> p = this;
                do
                {
                    final int ph;
                    final int dir;
                    final K pk;
                    final TreeNode<K, V> q;
                    final TreeNode<K, V> pl = p.left;
                    final TreeNode<K, V> pr = p.right;
                    if ((ph = p.hash) > h)
                    {
                        p = pl;
                    }
                    else if (ph < h)
                    {
                        p = pr;
                    }
                    else if ((((pk = p.key)) == k) || ((pk != null) && k.equals(pk)))
                    {
                        return p;
                    }
                    else if (pl == null)
                    {
                        p = pr;
                    }
                    else if (pr == null)
                    {
                        p = pl;
                    }
                    else if (((kc != null) || (((kc = comparableClassFor(k))) != null)) && (((dir = compareComparables(kc, k, pk))) != 0))
                    {
                        p = (dir < 0) ? pl : pr;
                    }
                    else if ((q = pr.findTreeNode(h, k, kc)) != null)
                    {
                        return q;
                    }
                    else
                    {
                        p = pl;
                    }
                } while (p != null);
            }
            return null;
        }

        @Override
        Node<K, V> find(final int h, final Object k)
        {
            return this.findTreeNode(h, k, null);
        }


    }

    /**
     * TreeNodes used at the heads of bins. TreeBins do not hold user
     * keys or values, but instead point to list of TreeNodes and
     * their root. They also maintain a parasitic read-write lock
     * forcing writers (who hold bin lock) to wait for readers (who do
     * not) to complete before tree restructuring operations.
     */
    static final class TreeBin<K, V> extends Node<K, V>
    {
        // values for lockState
        static final int WRITER = 1; // set while holding write lock
        static final int WAITER = 2; // set when waiting for write lock
        static final int READER = 4; // increment value for setting read lock
        private static final sun.misc.Unsafe U;
        private static final long            LOCKSTATE;
        TreeNode<K, V> root;
        volatile TreeNode<K, V> first;
        volatile Thread         waiter;
        volatile int            lockState;

        /**
         * Creates bin with initial set of nodes headed by b.
         */
        TreeBin(final TreeNode<K, V> b)
        {
            super(TREEBIN, null, null, null);
            this.first = b;
            TreeNode<K, V> r = null;
            for (TreeNode<K, V> x = b, next; x != null; x = next)
            {
                next = (TreeNode<K, V>) x.next;
                x.left = x.right = null;
                if (r == null)
                {
                    x.parent = null;
                    x.red = false;
                    r = x;
                }
                else
                {
                    final K k = x.key;
                    final int h = x.hash;
                    Class<?> kc = null;
                    for (TreeNode<K, V> p = r; ; )
                    {
                        int dir;
                        final int ph;
                        final K pk = p.key;
                        if ((ph = p.hash) > h)
                        {
                            dir = - 1;
                        }
                        else if (ph < h)
                        {
                            dir = 1;
                        }
                        else if (((kc == null) && (((kc = comparableClassFor(k))) == null)) || (((dir = compareComparables(kc, k, pk))) == 0))
                        {
                            dir = tieBreakOrder(k, pk);
                        }
                        final TreeNode<K, V> xp = p;
                        if ((p = (dir <= 0) ? p.left : p.right) == null)
                        {
                            x.parent = xp;
                            if (dir <= 0)
                            {
                                xp.left = x;
                            }
                            else
                            {
                                xp.right = x;
                            }
                            r = balanceInsertion(r, x);
                            break;
                        }
                    }
                }
            }
            this.root = r;
            assert checkInvariants(this.root);
        }

        /**
         * Acquires write lock for tree restructuring.
         */
        private void lockRoot()
        {
            if (! U.compareAndSwapInt(this, LOCKSTATE, 0, WRITER))
            {
                this.contendedLock(); // offload to separate method
            }
        }

        /**
         * Releases write lock for tree restructuring.
         */
        private void unlockRoot()
        {
            this.lockState = 0;
        }

        /**
         * Possibly blocks awaiting root lock.
         */
        private void contendedLock()
        {
            boolean waiting = false;
            for (int s; ; )
            {
                if (((s = this.lockState) & ~ WAITER) == 0)
                {
                    if (U.compareAndSwapInt(this, LOCKSTATE, s, WRITER))
                    {
                        if (waiting)
                        {
                            this.waiter = null;
                        }
                        return;
                    }
                }
                else if ((s & WAITER) == 0)
                {
                    if (U.compareAndSwapInt(this, LOCKSTATE, s, s | WAITER))
                    {
                        waiting = true;
                        this.waiter = Thread.currentThread();
                    }
                }
                else if (waiting)
                {
                    LockSupport.park(this);
                }
            }
        }

        /**
         * Returns matching node or null if none. Tries to search
         * using tree comparisons from root, but continues linear
         * search when lock not available.
         */
        @Override
        final Node<K, V> find(final int h, final Object k)
        {
            if (k != null)
            {
                for (Node<K, V> e = this.first; e != null; )
                {
                    final int s;
                    final K ek;
                    if (((s = this.lockState) & (WAITER | WRITER)) != 0)
                    {
                        if ((e.hash == h) && ((((ek = e.key)) == k) || ((ek != null) && k.equals(ek))))
                        {
                            return e;
                        }
                        e = e.next;
                    }
                    else if (U.compareAndSwapInt(this, LOCKSTATE, s, s + READER))
                    {
                        final TreeNode<K, V> r;
                        TreeNode<K, V> p;
                        try
                        {
                            p = ((((r = this.root)) == null) ? null : r.findTreeNode(h, k, null));
                        } finally
                        {
                            final Thread w;
                            if ((U.getAndAddInt(this, LOCKSTATE, - READER) == (READER | WAITER)) && (((w = this.waiter)) != null))
                            {
                                LockSupport.unpark(w);
                            }
                        }
                        return p;
                    }
                }
            }
            return null;
        }

        /**
         * Finds or adds a node.
         *
         * @return null if added
         */
        final TreeNode<K, V> putTreeVal(final int h, final K k, final V v)
        {
            Class<?> kc = null;
            boolean searched = false;
            for (TreeNode<K, V> p = this.root; ; )
            {
                int dir;
                final int ph;
                final K pk;
                if (p == null)
                {
                    this.first = this.root = new TreeNode<>(h, k, v, null, null);
                    break;
                }
                else if ((ph = p.hash) > h)
                {
                    dir = - 1;
                }
                else if (ph < h)
                {
                    dir = 1;
                }
                else if ((((pk = p.key)) == k) || ((pk != null) && k.equals(pk)))
                {
                    return p;
                }
                else if (((kc == null) && (((kc = comparableClassFor(k))) == null)) || (((dir = compareComparables(kc, k, pk))) == 0))
                {
                    if (! searched)
                    {
                        TreeNode<K, V> q, ch;
                        searched = true;
                        if (((((ch = p.left)) != null) && (((q = ch.findTreeNode(h, k, kc))) != null)) || ((((ch = p.right)) != null) && (((q = ch.findTreeNode(h, k, kc))) != null)))
                        {
                            return q;
                        }
                    }
                    dir = tieBreakOrder(k, pk);
                }

                final TreeNode<K, V> xp = p;
                if ((p = (dir <= 0) ? p.left : p.right) == null)
                {
                    final TreeNode<K, V> x;
                    final TreeNode<K, V> f = this.first;
                    this.first = x = new TreeNode<>(h, k, v, f, xp);
                    if (f != null)
                    {
                        f.prev = x;
                    }
                    if (dir <= 0)
                    {
                        xp.left = x;
                    }
                    else
                    {
                        xp.right = x;
                    }
                    if (! xp.red)
                    {
                        x.red = true;
                    }
                    else
                    {
                        this.lockRoot();
                        try
                        {
                            this.root = balanceInsertion(this.root, x);
                        } finally
                        {
                            this.unlockRoot();
                        }
                    }
                    break;
                }
            }
            assert checkInvariants(this.root);
            return null;
        }

        /**
         * Removes the given node, that must be present before this
         * call.  This is messier than typical red-black deletion code
         * because we cannot swap the contents of an interior node
         * with a leaf successor that is pinned by "next" pointers
         * that are accessible independently of lock. So instead we
         * swap the tree linkages.
         *
         * @return true if now too small, so should be untreeified
         */
        final boolean removeTreeNode(final TreeNode<K, V> p)
        {
            final TreeNode<K, V> next = (TreeNode<K, V>) p.next;
            final TreeNode<K, V> pred = p.prev;  // unlink traversal pointers
            TreeNode<K, V> r;
            final TreeNode<K, V> rl;
            if (pred == null)
            {
                this.first = next;
            }
            else
            {
                pred.next = next;
            }
            if (next != null)
            {
                next.prev = pred;
            }
            if (this.first == null)
            {
                this.root = null;
                return true;
            }
            if ((((r = this.root)) == null) || (r.right == null) || // too small
                        (((rl = r.left)) == null) || (rl.left == null))
            {
                return true;
            }
            this.lockRoot();
            try
            {
                final TreeNode<K, V> replacement;
                final TreeNode<K, V> pl = p.left;
                final TreeNode<K, V> pr = p.right;
                if ((pl != null) && (pr != null))
                {
                    TreeNode<K, V> s = pr, sl;
                    while ((sl = s.left) != null) // find successor
                    {
                        s = sl;
                    }
                    final boolean c = s.red;
                    s.red = p.red;
                    p.red = c; // swap colors
                    final TreeNode<K, V> sr = s.right;
                    final TreeNode<K, V> pp = p.parent;
                    if (s == pr)
                    { // p was s's direct parent
                        p.parent = s;
                        s.right = p;
                    }
                    else
                    {
                        final TreeNode<K, V> sp = s.parent;
                        if ((p.parent = sp) != null)
                        {
                            if (s == sp.left)
                            {
                                sp.left = p;
                            }
                            else
                            {
                                sp.right = p;
                            }
                        }
                        if ((s.right = pr) != null)
                        {
                            pr.parent = s;
                        }
                    }
                    p.left = null;
                    if ((p.right = sr) != null)
                    {
                        sr.parent = p;
                    }
                    if ((s.left = pl) != null)
                    {
                        pl.parent = s;
                    }
                    if ((s.parent = pp) == null)
                    {
                        r = s;
                    }
                    else if (p == pp.left)
                    {
                        pp.left = s;
                    }
                    else
                    {
                        pp.right = s;
                    }
                    if (sr != null)
                    {
                        replacement = sr;
                    }
                    else
                    {
                        replacement = p;
                    }
                }
                else if (pl != null)
                {
                    replacement = pl;
                }
                else if (pr != null)
                {
                    replacement = pr;
                }
                else
                {
                    replacement = p;
                }
                if (replacement != p)
                {
                    final TreeNode<K, V> pp = replacement.parent = p.parent;
                    if (pp == null)
                    {
                        r = replacement;
                    }
                    else if (p == pp.left)
                    {
                        pp.left = replacement;
                    }
                    else
                    {
                        pp.right = replacement;
                    }
                    p.left = p.right = p.parent = null;
                }

                this.root = (p.red) ? r : balanceDeletion(r, replacement);

                if (p == replacement)
                {  // detach pointers
                    final TreeNode<K, V> pp;
                    if ((pp = p.parent) != null)
                    {
                        if (p == pp.left)
                        {
                            pp.left = null;
                        }
                        else if (p == pp.right)
                        {
                            pp.right = null;
                        }
                        p.parent = null;
                    }
                }
            } finally
            {
                this.unlockRoot();
            }
            assert checkInvariants(this.root);
            return false;
        }

        /* ------------------------------------------------------------ */
        // Red-black tree methods, all adapted from CLR

        /**
         * Tie-breaking utility for ordering insertions when equal
         * hashCodes and non-comparable. We don't require a total
         * order, just a consistent insertion rule to maintain
         * equivalence across rebalancings. Tie-breaking further than
         * necessary simplifies testing a bit.
         */
        static int tieBreakOrder(final Object a, final Object b)
        {
            int d;
            if ((a == null) || (b == null) ||
                        (((d = a.getClass().getName().
                                                             compareTo(b.getClass().getName()))) == 0))
            {
                d = ((System.identityHashCode(a) <= System.identityHashCode(b)) ? - 1 : 1);
            }
            return d;
        }

        static <K, V> TreeNode<K, V> rotateLeft(TreeNode<K, V> root, final TreeNode<K, V> p)
        {
            final TreeNode<K, V> r;
            final TreeNode<K, V> pp;
            final TreeNode<K, V> rl;
            if ((p != null) && (((r = p.right)) != null))
            {
                if ((rl = p.right = r.left) != null)
                {
                    rl.parent = p;
                }
                if ((pp = r.parent = p.parent) == null)
                {
                    (root = r).red = false;
                }
                else if (pp.left == p)
                {
                    pp.left = r;
                }
                else
                {
                    pp.right = r;
                }
                r.left = p;
                p.parent = r;
            }
            return root;
        }

        static <K, V> TreeNode<K, V> rotateRight(TreeNode<K, V> root, final TreeNode<K, V> p)
        {
            final TreeNode<K, V> l;
            final TreeNode<K, V> pp;
            final TreeNode<K, V> lr;
            if ((p != null) && (((l = p.left)) != null))
            {
                if ((lr = p.left = l.right) != null)
                {
                    lr.parent = p;
                }
                if ((pp = l.parent = p.parent) == null)
                {
                    (root = l).red = false;
                }
                else if (pp.right == p)
                {
                    pp.right = l;
                }
                else
                {
                    pp.left = l;
                }
                l.right = p;
                p.parent = l;
            }
            return root;
        }

        static <K, V> TreeNode<K, V> balanceInsertion(TreeNode<K, V> root, TreeNode<K, V> x)
        {
            x.red = true;
            for (TreeNode<K, V> xp, xpp, xppl, xppr; ; )
            {
                if ((xp = x.parent) == null)
                {
                    x.red = false;
                    return x;
                }
                if (! xp.red || (((xpp = xp.parent)) == null))
                {
                    return root;
                }
                if (xp == (xppl = xpp.left))
                {
                    if ((((xppr = xpp.right)) != null) && xppr.red)
                    {
                        xppr.red = false;
                        xp.red = false;
                        xpp.red = true;
                        x = xpp;
                    }
                    else
                    {
                        if (x == xp.right)
                        {
                            root = rotateLeft(root, x = xp);
                            xpp = (((xp = x.parent)) == null) ? null : xp.parent;
                        }
                        if (xp != null)
                        {
                            xp.red = false;
                            if (xpp != null)
                            {
                                xpp.red = true;
                                root = rotateRight(root, xpp);
                            }
                        }
                    }
                }
                else
                {
                    if ((xppl != null) && xppl.red)
                    {
                        xppl.red = false;
                        xp.red = false;
                        xpp.red = true;
                        x = xpp;
                    }
                    else
                    {
                        if (x == xp.left)
                        {
                            root = rotateRight(root, x = xp);
                            xpp = (((xp = x.parent)) == null) ? null : xp.parent;
                        }
                        if (xp != null)
                        {
                            xp.red = false;
                            if (xpp != null)
                            {
                                xpp.red = true;
                                root = rotateLeft(root, xpp);
                            }
                        }
                    }
                }
            }
        }

        static <K, V> TreeNode<K, V> balanceDeletion(TreeNode<K, V> root, TreeNode<K, V> x)
        {
            for (TreeNode<K, V> xp, xpl, xpr; ; )
            {
                if ((x == null) || (x == root))
                {
                    return root;
                }
                else if ((xp = x.parent) == null)
                {
                    x.red = false;
                    return x;
                }
                else if (x.red)
                {
                    x.red = false;
                    return root;
                }
                else if ((xpl = xp.left) == x)
                {
                    if ((((xpr = xp.right)) != null) && xpr.red)
                    {
                        xpr.red = false;
                        xp.red = true;
                        root = rotateLeft(root, xp);
                        xpr = (((xp = x.parent)) == null) ? null : xp.right;
                    }
                    if (xpr == null)
                    {
                        x = xp;
                    }
                    else
                    {
                        final TreeNode<K, V> sl = xpr.left;
                        TreeNode<K, V> sr = xpr.right;
                        if (((sr == null) || ! sr.red) && ((sl == null) || ! sl.red))
                        {
                            xpr.red = true;
                            x = xp;
                        }
                        else
                        {
                            if ((sr == null) || ! sr.red)
                            {
                                sl.red = false;
                                xpr.red = true;
                                root = rotateRight(root, xpr);
                                xpr = (((xp = x.parent)) == null) ? null : xp.right;
                            }
                            if (xpr != null)
                            {
                                xpr.red = xp.red;
                                if ((sr = xpr.right) != null)
                                {
                                    sr.red = false;
                                }
                            }
                            if (xp != null)
                            {
                                xp.red = false;
                                root = rotateLeft(root, xp);
                            }
                            x = root;
                        }
                    }
                }
                else
                { // symmetric
                    if ((xpl != null) && xpl.red)
                    {
                        xpl.red = false;
                        xp.red = true;
                        root = rotateRight(root, xp);
                        xpl = (((xp = x.parent)) == null) ? null : xp.left;
                    }
                    if (xpl == null)
                    {
                        x = xp;
                    }
                    else
                    {
                        TreeNode<K, V> sl = xpl.left;
                        final TreeNode<K, V> sr = xpl.right;
                        if (((sl == null) || ! sl.red) && ((sr == null) || ! sr.red))
                        {
                            xpl.red = true;
                            x = xp;
                        }
                        else
                        {
                            if ((sl == null) || ! sl.red)
                            {
                                sr.red = false;
                                xpl.red = true;
                                root = rotateLeft(root, xpl);
                                xpl = (((xp = x.parent)) == null) ? null : xp.left;
                            }
                            if (xpl != null)
                            {
                                xpl.red = xp.red;
                                if ((sl = xpl.left) != null)
                                {
                                    sl.red = false;
                                }
                            }
                            if (xp != null)
                            {
                                xp.red = false;
                                root = rotateRight(root, xp);
                            }
                            x = root;
                        }
                    }
                }
            }
        }

        /**
         * Recursive invariant check
         */
        static <K, V> boolean checkInvariants(final TreeNode<K, V> t)
        {
            final TreeNode<K, V> tp = t.parent;
            final TreeNode<K, V> tl = t.left;
            final TreeNode<K, V> tr = t.right;
            final TreeNode<K, V> tb = t.prev;
            final TreeNode<K, V> tn = (TreeNode<K, V>) t.next;
            if ((tb != null) && (tb.next != t) || (tn != null) && (tn.prev != t) || (tp != null) && (t != tp.left) && (t != tp.right) || (tl != null) && ((tl.parent != t) || (tl.hash > t.hash)) || (tr != null) && ((tr.parent != t) || (tr.hash < t.hash)) || t.red && (tl != null) && tl.red && (tr != null) && tr.red)
            {
                return false;
            }
            return ! ((tl != null) && ! checkInvariants(tl)) && ! ((tr != null) && ! checkInvariants(tr));
        }

        static
        {
            try
            {
                U = DioriteUtils.getUnsafe();
                final Class<?> k = TreeBin.class;
                LOCKSTATE = U.objectFieldOffset(k.getDeclaredField("lockState"));
            } catch (final Exception e)
            {
                throw new Error(e);
            }
        }


    }

    /**
     * Records the table, its length, and current traversal index for a
     * traverser that must process a region of a forwarded table before
     * proceeding with current table.
     */
    @SuppressWarnings("ClassHasNoToStringMethod")
    static final class TableStack<K, V>
    {
        int              length;
        int              index;
        Node<K, V>[]     tab;
        TableStack<K, V> next;
    }

    /**
     * Encapsulates traversal for methods such as containsValue; also
     * serves as a base class for other iterators and spliterators.
     * <br>
     * Method advance visits once each still-valid node that was
     * reachable upon iterator construction. It might miss some that
     * were added to a bin after the bin was visited, which is OK wrt
     * consistency guarantees. Maintaining this property in the face
     * of possible ongoing resizes requires a fair amount of
     * bookkeeping state that is difficult to optimize away amidst
     * volatile accesses.  Even so, traversal maintains reasonable
     * throughput.
     * <br>
     * Normally, iteration proceeds bin-by-bin traversing lists.
     * However, if the table has been resized, then all future steps
     * must traverse both the bin at the current index as well as at
     * (index + baseSize); and so on for further resizings. To
     * paranoically cope with potential sharing by users of iterators
     * across threads, iteration terminates if a bounds checks fails
     * for a table read.
     */
    @SuppressWarnings("ClassHasNoToStringMethod")
    static class Traverser<K, V>
    {
        final int baseSize;     // initial table size
        Node<K, V>[]     tab;        // current table; updated if resized
        Node<K, V>       next;         // the next entry to use
        TableStack<K, V> stack, spare; // to save/restore on ForwardingNodes
        int index;              // index of bin to use next
        int baseIndex;          // current index of initial table
        int baseLimit;          // index bound for initial table

        Traverser(final Node<K, V>[] tab, final int size, final int index, final int limit)
        {
            this.tab = tab;
            this.baseSize = size;
            this.baseIndex = this.index = index;
            this.baseLimit = limit;
            this.next = null;
        }

        /**
         * Advances if possible, returning next valid node, or null if none.
         */
        final Node<K, V> advance()
        {
            Node<K, V> e;
            if ((e = this.next) != null)
            {
                e = e.next;
            }
            while (true)
            {
                final Node<K, V>[] t;
                final int i;  // must use locals in checks
                final int n;
                if (e != null)
                {
                    return this.next = e;
                }
                if ((this.baseIndex >= this.baseLimit) || (((t = this.tab)) == null) ||
                            (((n = t.length)) <= ((i = this.index))) || (i < 0))
                {
                    return this.next = null;
                }
                if ((((e = tabAt(t, i))) != null) && (e.hash < 0))
                {
                    if (e instanceof ForwardingNode)
                    {
                        this.tab = ((ForwardingNode<K, V>) e).nextTable;
                        e = null;
                        this.pushState(t, i, n);
                        continue;
                    }
                    else if (e instanceof TreeBin)
                    {
                        e = ((TreeBin<K, V>) e).first;
                    }
                    else
                    {
                        e = null;
                    }
                }
                if (this.stack != null)
                {
                    this.recoverState(n);
                }
                else if ((this.index = i + this.baseSize) >= n)
                {
                    this.index = ++ this.baseIndex; // visit upper slots if present
                }
            }
        }

        /**
         * Saves traversal state upon encountering a forwarding node.
         */
        private void pushState(final Node<K, V>[] t, final int i, final int n)
        {
            TableStack<K, V> s = this.spare;  // reuse if possible
            if (s != null)
            {
                this.spare = s.next;
            }
            else
            {
                s = new TableStack<>();
            }
            s.tab = t;
            s.length = n;
            s.index = i;
            s.next = this.stack;
            this.stack = s;
        }

        /**
         * Possibly pops traversal state.
         *
         * @param n length of current table
         */
        private void recoverState(int n)
        {
            TableStack<K, V> s;
            int len;
            while ((((s = this.stack)) != null) && (((this.index += ((len = s.length)))) >= n))
            {
                n = len;
                this.index = s.index;
                this.tab = s.tab;
                s.tab = null;
                final TableStack<K, V> next = s.next;
                s.next = this.spare; // save for reuse
                this.stack = next;
                this.spare = s;
            }
            if ((s == null) && (((this.index += this.baseSize)) >= n))
            {
                this.index = ++ this.baseIndex;
            }
        }
    }


    /* ----------------Views -------------- */

    /**
     * Base of key, value, and entry Iterators. Adds fields to
     * Traverser to support iterator.remove.
     */
    @SuppressWarnings("ClassHasNoToStringMethod")
    static class BaseIterator<K, V> extends Traverser<K, V>
    {
        final ConcurrentIdentityHashMap<K, V> map;
        Node<K, V> lastReturned;

        BaseIterator(final Node<K, V>[] tab, final int size, final int index, final int limit, final ConcurrentIdentityHashMap<K, V> map)
        {
            super(tab, size, index, limit);
            this.map = map;
            this.advance();
        }

        public final boolean hasNext()
        {
            return this.next != null;
        }

        public final boolean hasMoreElements()
        {
            return this.next != null;
        }

        public final void remove()
        {
            final Node<K, V> p;
            if ((p = this.lastReturned) == null)
            {
                throw new IllegalStateException();
            }
            this.lastReturned = null;
            this.map.replaceNode(p.key, null, null);
        }
    }

    static final class KeyIterator<K, V> extends BaseIterator<K, V> implements Iterator<K>, Enumeration<K>
    {
        KeyIterator(final Node<K, V>[] tab, final int index, final int size, final int limit, final ConcurrentIdentityHashMap<K, V> map)
        {
            super(tab, index, size, limit, map);
        }

        @Override
        public final K next()
        {
            final Node<K, V> p;
            if ((p = this.next) == null)
            {
                throw new NoSuchElementException();
            }
            final K k = p.key;
            this.lastReturned = p;
            this.advance();
            return k;
        }

        @Override
        public final K nextElement()
        {
            return this.next();
        }
    }

    static final class ValueIterator<K, V> extends BaseIterator<K, V> implements Iterator<V>, Enumeration<V>
    {
        ValueIterator(final Node<K, V>[] tab, final int index, final int size, final int limit, final ConcurrentIdentityHashMap<K, V> map)
        {
            super(tab, index, size, limit, map);
        }

        @Override
        public final V next()
        {
            final Node<K, V> p;
            if ((p = this.next) == null)
            {
                throw new NoSuchElementException();
            }
            final V v = p.val;
            this.lastReturned = p;
            this.advance();
            return v;
        }

        @Override
        public final V nextElement()
        {
            return this.next();
        }
    }

    static final class EntryIterator<K, V> extends BaseIterator<K, V> implements Iterator<Map.Entry<K, V>>
    {
        EntryIterator(final Node<K, V>[] tab, final int index, final int size, final int limit, final ConcurrentIdentityHashMap<K, V> map)
        {
            super(tab, index, size, limit, map);
        }

        @Override
        public final Map.Entry<K, V> next()
        {
            final Node<K, V> p;
            if ((p = this.next) == null)
            {
                throw new NoSuchElementException();
            }
            final K k = p.key;
            final V v = p.val;
            this.lastReturned = p;
            this.advance();
            return new MapEntry<>(k, v, this.map);
        }
    }

    // -------------------------------------------------------

    /**
     * Exported Entry for EntryIterator
     */
    static final class MapEntry<K, V> implements Map.Entry<K, V>
    {
        final K                               key; // non-null
        final ConcurrentIdentityHashMap<K, V> map;
        V val;       // non-null

        MapEntry(final K key, final V val, final ConcurrentIdentityHashMap<K, V> map)
        {
            this.key = key;
            this.val = val;
            this.map = map;
        }

        @Override
        public K getKey()
        {
            return this.key;
        }

        @Override
        public V getValue()
        {
            return this.val;
        }

        public int hashCode()
        {
            return this.key.hashCode() ^ this.val.hashCode();
        }

        public String toString()
        {
            return this.key + "=" + this.val;
        }

        public boolean equals(final Object o)
        {
            final Object k;
            final Object v;
            final Map.Entry<?, ?> e;
            return ((o instanceof Entry) &&
                            (((k = (e = (Entry<?, ?>) o).getKey())) != null) &&
                            (((v = e.getValue())) != null) &&
                            (k == this.key) &&
                            (v == this.val));
        }

        /**
         * Sets our entry's value and writes through to the map. The
         * value to return is somewhat arbitrary here. Since we do not
         * necessarily track asynchronous changes, the most recent
         * "previous" value could be different from what we return (or
         * could even have been removed, in which case the put will
         * re-establish). We do not and cannot guarantee more.
         */
        @Override
        public V setValue(final V value)
        {
            if (value == null)
            {
                throw new NullPointerException();
            }
            final V v = this.val;
            this.val = value;
            this.map.put(this.key, value);
            return v;
        }
    }

    @SuppressWarnings("ClassHasNoToStringMethod")
    static final class KeySpliterator<K, V> extends Traverser<K, V> implements Spliterator<K>
    {
        long est;               // size estimate

        KeySpliterator(final Node<K, V>[] tab, final int size, final int index, final int limit, final long est)
        {
            super(tab, size, index, limit);
            this.est = est;
        }

        @Override
        public boolean tryAdvance(final Consumer<? super K> action)
        {
            if (action == null)
            {
                throw new NullPointerException();
            }
            final Node<K, V> p;
            if ((p = this.advance()) == null)
            {
                return false;
            }
            action.accept(p.key);
            return true;
        }

        @Override
        public Spliterator<K> trySplit()
        {
            final int i;
            final int f;
            final int h;
            return (((h = (((i = this.baseIndex)) + ((f = this.baseLimit))) >>> 1)) <= i) ? null : new KeySpliterator<>(this.tab, this.baseSize, this.baseLimit = h, f, this.est >>>= 1);
        }

        @Override
        public void forEachRemaining(final Consumer<? super K> action)
        {
            if (action == null)
            {
                throw new NullPointerException();
            }
            for (Node<K, V> p; (p = this.advance()) != null; )
            {
                action.accept(p.key);
            }
        }


        @Override
        public long estimateSize()
        {
            return this.est;
        }

        @Override
        public int characteristics()
        {
            return Spliterator.DISTINCT | Spliterator.CONCURRENT |
                           Spliterator.NONNULL;
        }
    }

    @SuppressWarnings("ClassHasNoToStringMethod")
    static final class ValueSpliterator<K, V> extends Traverser<K, V> implements Spliterator<V>
    {
        long est;               // size estimate

        ValueSpliterator(final Node<K, V>[] tab, final int size, final int index, final int limit, final long est)
        {
            super(tab, size, index, limit);
            this.est = est;
        }

        @Override
        public Spliterator<V> trySplit()
        {
            final int i;
            final int f;
            final int h;
            return (((h = (((i = this.baseIndex)) + ((f = this.baseLimit))) >>> 1)) <= i) ? null : new ValueSpliterator<>(this.tab, this.baseSize, this.baseLimit = h, f, this.est >>>= 1);
        }

        @Override
        public void forEachRemaining(final Consumer<? super V> action)
        {
            if (action == null)
            {
                throw new NullPointerException();
            }
            for (Node<K, V> p; (p = this.advance()) != null; )
            {
                action.accept(p.val);
            }
        }

        @Override
        public boolean tryAdvance(final Consumer<? super V> action)
        {
            if (action == null)
            {
                throw new NullPointerException();
            }
            final Node<K, V> p;
            if ((p = this.advance()) == null)
            {
                return false;
            }
            action.accept(p.val);
            return true;
        }

        @Override
        public long estimateSize()
        {
            return this.est;
        }

        @Override
        public int characteristics()
        {
            return Spliterator.CONCURRENT | Spliterator.NONNULL;
        }
    }

    @SuppressWarnings("ClassHasNoToStringMethod")
    static final class EntrySpliterator<K, V> extends Traverser<K, V> implements Spliterator<Map.Entry<K, V>>
    {
        final ConcurrentIdentityHashMap<K, V> map; // To export MapEntry
        long est;               // size estimate

        EntrySpliterator(final Node<K, V>[] tab, final int size, final int index, final int limit, final long est, final ConcurrentIdentityHashMap<K, V> map)
        {
            super(tab, size, index, limit);
            this.map = map;
            this.est = est;
        }

        @Override
        public Spliterator<Map.Entry<K, V>> trySplit()
        {
            final int i;
            final int f;
            final int h;
            return (((h = (((i = this.baseIndex)) + ((f = this.baseLimit))) >>> 1)) <= i) ? null : new EntrySpliterator<>(this.tab, this.baseSize, this.baseLimit = h, f, this.est >>>= 1, this.map);
        }

        @Override
        public void forEachRemaining(final Consumer<? super Map.Entry<K, V>> action)
        {
            if (action == null)
            {
                throw new NullPointerException();
            }
            for (Node<K, V> p; (p = this.advance()) != null; )
            {
                action.accept(new MapEntry<>(p.key, p.val, this.map));
            }
        }

        @Override
        public boolean tryAdvance(final Consumer<? super Map.Entry<K, V>> action)
        {
            if (action == null)
            {
                throw new NullPointerException();
            }
            final Node<K, V> p;
            if ((p = this.advance()) == null)
            {
                return false;
            }
            action.accept(new MapEntry<>(p.key, p.val, this.map));
            return true;
        }

        @Override
        public long estimateSize()
        {
            return this.est;
        }

        @Override
        public int characteristics()
        {
            return Spliterator.DISTINCT | Spliterator.CONCURRENT |
                           Spliterator.NONNULL;
        }
    }

    /**
     * Base class for views.
     */
    abstract static class CollectionView<K, V, E> implements Collection<E>, java.io.Serializable
    {
        private static final long   serialVersionUID = 0;
        private static final String oomeMsg          = "Required array size too large";
        final ConcurrentIdentityHashMap<K, V> map;

        CollectionView(final ConcurrentIdentityHashMap<K, V> map)
        {
            this.map = map;
        }

        /**
         * Returns the map backing this view.
         *
         * @return the map backing this view
         */
        public ConcurrentIdentityHashMap<K, V> getMap()
        {
            return this.map;
        }

        /**
         * Removes all of the elements from this view, by removing all
         * the mappings from the map backing this view.
         */
        @Override
        public final void clear()
        {
            this.map.clear();
        }

        @Override
        public final int size()
        {
            return this.map.size();
        }

        @Override
        public final boolean isEmpty()
        {
            return this.map.isEmpty();
        }

        // implementations below rely on concrete classes supplying these
        // abstract methods

        @Override
        public abstract boolean contains(Object o);

        /**
         * Returns an iterator over the elements in this collection.
         * <br>
         * <p>The returned iterator is
         * <a href="package-summary.html#Weakly"><i>weakly consistent</i></a>.
         *
         * @return an iterator over the elements in this collection
         */
        @Override
        public abstract Iterator<E> iterator();

        @Override
        public final Object[] toArray()
        {
            final long sz = this.map.mappingCount();
            if (sz > MAX_ARRAY_SIZE)
            {
                throw new OutOfMemoryError(oomeMsg);
            }
            int n = (int) sz;
            Object[] r = new Object[n];
            int i = 0;
            for (final E e : this)
            {
                if (i == n)
                {
                    if (n >= MAX_ARRAY_SIZE)
                    {
                        throw new OutOfMemoryError(oomeMsg);
                    }
                    if (n >= (MAX_ARRAY_SIZE - (MAX_ARRAY_SIZE >>> 1) - 1))
                    {
                        n = MAX_ARRAY_SIZE;
                    }
                    else
                    {
                        n += (n >>> 1) + 1;
                    }
                    r = Arrays.copyOf(r, n);
                }
                r[i++] = e;
            }
            return (i == n) ? r : Arrays.copyOf(r, i);
        }

        @Override
        public abstract boolean remove(Object o);

        @Override
        @SuppressWarnings("unchecked")
        public final <T> T[] toArray(final T[] a)
        {
            final long sz = this.map.mappingCount();
            if (sz > MAX_ARRAY_SIZE)
            {
                throw new OutOfMemoryError(oomeMsg);
            }
            final int m = (int) sz;
            T[] r = (a.length >= m) ? a : (T[]) java.lang.reflect.Array.newInstance(a.getClass().getComponentType(), m);
            int n = r.length;
            int i = 0;
            for (final E e : this)
            {
                if (i == n)
                {
                    if (n >= MAX_ARRAY_SIZE)
                    {
                        throw new OutOfMemoryError(oomeMsg);
                    }
                    if (n >= (MAX_ARRAY_SIZE - (MAX_ARRAY_SIZE >>> 1) - 1))
                    {
                        n = MAX_ARRAY_SIZE;
                    }
                    else
                    {
                        n += (n >>> 1) + 1;
                    }
                    r = Arrays.copyOf(r, n);
                }
                r[i++] = (T) e;
            }
            if ((a == r) && (i < n))
            {
                r[i] = null; // null-terminate
                return r;
            }
            return (i == n) ? r : Arrays.copyOf(r, i);
        }


        /**
         * Returns a string representation of this collection.
         * The string representation consists of the string representations
         * of the collection's elements in the order they are returned by
         * its iterator, enclosed in square brackets ({@code "[]"}).
         * Adjacent elements are separated by the characters {@code ", "}
         * (comma and space).  Elements are converted to strings as by
         * {@link String#valueOf(Object)}.
         *
         * @return a string representation of this collection
         */
        public final String toString()
        {
            final StringBuilder sb = new StringBuilder();
            sb.append('[');
            final Iterator<E> it = this.iterator();
            if (it.hasNext())
            {
                while (true)
                {
                    final Object e = it.next();
                    sb.append((e == this) ? "(this Collection)" : e);
                    if (! it.hasNext())
                    {
                        break;
                    }
                    sb.append(',').append(' ');
                }
            }
            return sb.append(']').toString();
        }

        @Override
        public final boolean containsAll(final Collection<?> c)
        {
            if (c != this)
            {
                for (final Object e : c)
                {
                    if ((e == null) || ! this.contains(e))
                    {
                        return false;
                    }
                }
            }
            return true;
        }

        @Override
        public final boolean removeAll(final Collection<?> c)
        {
            if (c == null)
            {
                throw new NullPointerException();
            }
            boolean modified = false;
            for (final Iterator<E> it = this.iterator(); it.hasNext(); )
            {
                if (c.contains(it.next()))
                {
                    it.remove();
                    modified = true;
                }
            }
            return modified;
        }

        @Override
        public final boolean retainAll(final Collection<?> c)
        {
            if (c == null)
            {
                throw new NullPointerException();
            }
            boolean modified = false;
            for (final Iterator<E> it = this.iterator(); it.hasNext(); )
            {
                if (! c.contains(it.next()))
                {
                    it.remove();
                    modified = true;
                }
            }
            return modified;
        }

    }

    /**
     * A view of a ConcurrentIdentityHashMap as a {@link Set} of keys, in
     * which additions may optionally be enabled by mapping to a
     * common value.  This class cannot be directly instantiated.
     * See {@link #keySet() keySet()},
     * {@link #keySet(Object) keySet(V)},
     * {@link #newKeySet() newKeySet()},
     * {@link #newKeySet(int) newKeySet(int)}.
     *
     * @since 1.8
     */
    public static class KeySetView<K, V> extends CollectionView<K, V, K> implements Set<K>, java.io.Serializable
    {
        private static final long serialVersionUID = 0;
        private final V value;

        KeySetView(final ConcurrentIdentityHashMap<K, V> map, final V value)
        {  // non-public
            super(map);
            this.value = value;
        }

        /**
         * Returns the default mapped value for additions,
         * or {@code null} if additions are not supported.
         *
         * @return the default mapped value for additions, or {@code null}
         * if not supported
         */
        public V getMappedValue()
        {
            return this.value;
        }

        /**
         * @return an iterator over the keys of the backing map
         */
        @Override
        public Iterator<K> iterator()
        {
            final Node<K, V>[] t;
            final ConcurrentIdentityHashMap<K, V> m = this.map;
            final int f = (((t = m.table)) == null) ? 0 : t.length;
            return new KeyIterator<>(t, f, 0, f, m);
        }

        /**
         * @throws NullPointerException if the specified key is null
         */
        @Override
        public boolean contains(final Object o)
        {
            return this.map.containsKey(o);
        }

        @Override
        public void forEach(final Consumer<? super K> action)
        {
            if (action == null)
            {
                throw new NullPointerException();
            }
            final Node<K, V>[] t;
            if ((t = this.map.table) != null)
            {
                final Traverser<K, V> it = new Traverser<>(t, t.length, 0, t.length);
                for (Node<K, V> p; (p = it.advance()) != null; )
                {
                    action.accept(p.key);
                }
            }
        }

        /**
         * Removes the key from this map view, by removing the key (and its
         * corresponding value) from the backing map.  This method does
         * nothing if the key is not in the map.
         *
         * @param o the key to be removed from the backing map
         *
         * @return {@code true} if the backing map contained the specified key
         *
         * @throws NullPointerException if the specified key is null
         */
        @Override
        public boolean remove(final Object o)
        {
            return this.map.remove(o) != null;
        }


        /**
         * Adds the specified key to this set view by mapping the key to
         * the default mapped value in the backing map, if defined.
         *
         * @param e key to be added
         *
         * @return {@code true} if this set changed as a result of the call
         *
         * @throws NullPointerException          if the specified key is null
         * @throws UnsupportedOperationException if no default mapped value
         *                                       for additions was provided
         */
        @Override
        public boolean add(final K e)
        {
            final V v;
            if ((v = this.value) == null)
            {
                throw new UnsupportedOperationException();
            }
            return this.map.putVal(e, v, true) == null;
        }

        /**
         * Adds all of the elements in the specified collection to this set,
         * as if by calling {@link #add} on each one.
         *
         * @param c the elements to be inserted into this set
         *
         * @return {@code true} if this set changed as a result of the call
         *
         * @throws NullPointerException          if the collection or any of its
         *                                       elements are {@code null}
         * @throws UnsupportedOperationException if no default mapped value
         *                                       for additions was provided
         */
        @Override
        public boolean addAll(final Collection<? extends K> c)
        {
            boolean added = false;
            final V v;
            if ((v = this.value) == null)
            {
                throw new UnsupportedOperationException();
            }
            for (final K e : c)
            {
                if (this.map.putVal(e, v, true) == null)
                {
                    added = true;
                }
            }
            return added;
        }

        public int hashCode()
        {
            int h = 0;
            for (final K e : this)
            {
                h += e.hashCode();
            }
            return h;
        }

        public boolean equals(final Object o)
        {
            final Set<?> c;
            return ((o instanceof Set) && ((((c = (Set<?>) o)) == this) || (this.containsAll(c) && c.containsAll(this))));
        }

        @Override
        public Spliterator<K> spliterator()
        {
            final Node<K, V>[] t;
            final ConcurrentIdentityHashMap<K, V> m = this.map;
            final long n = m.sumCount();
            final int f = (((t = m.table)) == null) ? 0 : t.length;
            return new KeySpliterator<>(t, f, 0, f, (n < 0L) ? 0L : n);
        }


    }

    /**
     * A view of a ConcurrentIdentityHashMap as a {@link Collection} of
     * values, in which additions are disabled. This class cannot be
     * directly instantiated. See {@link #values()}.
     */
    static final class ValuesView<K, V> extends CollectionView<K, V, V> implements Collection<V>, java.io.Serializable
    {
        private static final long serialVersionUID = 0;

        ValuesView(final ConcurrentIdentityHashMap<K, V> map)
        {
            super(map);
        }

        @Override
        public final boolean contains(final Object o)
        {
            return this.map.containsValue(o);
        }

        @Override
        public final boolean remove(final Object o)
        {
            if (o != null)
            {
                for (final Iterator<V> it = this.iterator(); it.hasNext(); )
                {
                    if (o.equals(it.next()))
                    {
                        it.remove();
                        return true;
                    }
                }
            }
            return false;
        }

        @Override
        public final Iterator<V> iterator()
        {
            final ConcurrentIdentityHashMap<K, V> m = this.map;
            final Node<K, V>[] t;
            final int f = (((t = m.table)) == null) ? 0 : t.length;
            return new ValueIterator<>(t, f, 0, f, m);
        }

        @Override
        public final boolean add(final V e)
        {
            throw new UnsupportedOperationException();
        }

        @Override
        public final boolean addAll(final Collection<? extends V> c)
        {
            throw new UnsupportedOperationException();
        }

        @Override
        public Spliterator<V> spliterator()
        {
            final Node<K, V>[] t;
            final ConcurrentIdentityHashMap<K, V> m = this.map;
            final long n = m.sumCount();
            final int f = (((t = m.table)) == null) ? 0 : t.length;
            return new ValueSpliterator<>(t, f, 0, f, (n < 0L) ? 0L : n);
        }

        @Override
        public void forEach(final Consumer<? super V> action)
        {
            if (action == null)
            {
                throw new NullPointerException();
            }
            final Node<K, V>[] t;
            if ((t = this.map.table) != null)
            {
                final Traverser<K, V> it = new Traverser<>(t, t.length, 0, t.length);
                for (Node<K, V> p; (p = it.advance()) != null; )
                {
                    action.accept(p.val);
                }
            }
        }
    }

    /**
     * A view of a ConcurrentIdentityHashMap as a {@link Set} of (key, value)
     * entries.  This class cannot be directly instantiated. See
     * {@link #entrySet()}.
     */
    static final class EntrySetView<K, V> extends CollectionView<K, V, Map.Entry<K, V>> implements Set<Map.Entry<K, V>>, java.io.Serializable
    {
        private static final long serialVersionUID = 0;

        EntrySetView(final ConcurrentIdentityHashMap<K, V> map)
        {
            super(map);
        }

        @Override
        public boolean contains(final Object o)
        {
            final Object k;
            final Object v;
            final Object r;
            final Map.Entry<?, ?> e;
            return ((o instanceof Entry) &&
                            (((k = (e = (Entry<?, ?>) o).getKey())) != null) &&
                            (((r = this.map.get(k))) != null) &&
                            (((v = e.getValue())) != null) &&
                            ((v == r) || v.equals(r)));
        }

        @Override
        public boolean remove(final Object o)
        {
            final Object k;
            final Object v;
            final Map.Entry<?, ?> e;
            return ((o instanceof Entry) &&
                            (((k = (e = (Entry<?, ?>) o).getKey())) != null) &&
                            (((v = e.getValue())) != null) &&
                            this.map.remove(k, v));
        }

        /**
         * @return an iterator over the entries of the backing map
         */
        @Override
        public Iterator<Map.Entry<K, V>> iterator()
        {
            final ConcurrentIdentityHashMap<K, V> m = this.map;
            final Node<K, V>[] t;
            final int f = (((t = m.table)) == null) ? 0 : t.length;
            return new EntryIterator<>(t, f, 0, f, m);
        }

        @Override
        public boolean add(final Entry<K, V> e)
        {
            return this.map.putVal(e.getKey(), e.getValue(), false) == null;
        }

        @Override
        public boolean addAll(final Collection<? extends Entry<K, V>> c)
        {
            boolean added = false;
            for (final Entry<K, V> e : c)
            {
                if (this.add(e))
                {
                    added = true;
                }
            }
            return added;
        }

        public final int hashCode()
        {
            int h = 0;
            final Node<K, V>[] t;
            if ((t = this.map.table) != null)
            {
                final Traverser<K, V> it = new Traverser<>(t, t.length, 0, t.length);
                for (Node<K, V> p; (p = it.advance()) != null; )
                {
                    h += p.hashCode();
                }
            }
            return h;
        }

        public final boolean equals(final Object o)
        {
            final Set<?> c;
            return ((o instanceof Set) && ((((c = (Set<?>) o)) == this) || (this.containsAll(c) && c.containsAll(this))));
        }

        @Override
        public Spliterator<Map.Entry<K, V>> spliterator()
        {
            final Node<K, V>[] t;
            final ConcurrentIdentityHashMap<K, V> m = this.map;
            final long n = m.sumCount();
            final int f = (((t = m.table)) == null) ? 0 : t.length;
            return new EntrySpliterator<>(t, f, 0, f, (n < 0L) ? 0L : n, m);
        }

        @Override
        public void forEach(final Consumer<? super Map.Entry<K, V>> action)
        {
            if (action == null)
            {
                throw new NullPointerException();
            }
            final Node<K, V>[] t;
            if ((t = this.map.table) != null)
            {
                final Traverser<K, V> it = new Traverser<>(t, t.length, 0, t.length);
                for (Node<K, V> p; (p = it.advance()) != null; )
                {
                    action.accept(new MapEntry<>(p.key, p.val, this.map));
                }
            }
        }

    }

    /**
     * Base class for bulk tasks. Repeats some fields and code from
     * class Traverser, because we need to subclass CountedCompleter.
     */
    @SuppressWarnings({"serial", "ClassHasNoToStringMethod"})
    abstract static class BulkTask<K, V, R> extends CountedCompleter<R>
    {
        final int baseSize;
        Node<K, V>[]     tab;        // same as Traverser
        Node<K, V>       next;
        TableStack<K, V> stack, spare;
        int index;
        int baseIndex;
        int baseLimit;
        int batch;              // split control

        BulkTask(final BulkTask<K, V, ?> par, final int b, final int i, final int f, final Node<K, V>[] t)
        {
            super(par);
            this.batch = b;
            this.index = this.baseIndex = i;
            if ((this.tab = t) == null)
            {
                this.baseSize = this.baseLimit = 0;
            }
            else if (par == null)
            {
                this.baseSize = this.baseLimit = t.length;
            }
            else
            {
                this.baseLimit = f;
                this.baseSize = par.baseSize;
            }
        }

        /**
         * Same as Traverser version
         */
        final Node<K, V> advance()
        {
            Node<K, V> e;
            if ((e = this.next) != null)
            {
                e = e.next;
            }
            while (true)
            {
                final Node<K, V>[] t;
                final int i;
                final int n;
                if (e != null)
                {
                    return this.next = e;
                }
                if ((this.baseIndex >= this.baseLimit) || (((t = this.tab)) == null) ||
                            (((n = t.length)) <= ((i = this.index))) || (i < 0))
                {
                    return this.next = null;
                }
                if ((((e = tabAt(t, i))) != null) && (e.hash < 0))
                {
                    if (e instanceof ForwardingNode)
                    {
                        this.tab = ((ForwardingNode<K, V>) e).nextTable;
                        e = null;
                        this.pushState(t, i, n);
                        continue;
                    }
                    else if (e instanceof TreeBin)
                    {
                        e = ((TreeBin<K, V>) e).first;
                    }
                    else
                    {
                        e = null;
                    }
                }
                if (this.stack != null)
                {
                    this.recoverState(n);
                }
                else if ((this.index = i + this.baseSize) >= n)
                {
                    this.index = ++ this.baseIndex;
                }
            }
        }

        private void pushState(final Node<K, V>[] t, final int i, final int n)
        {
            TableStack<K, V> s = this.spare;
            if (s != null)
            {
                this.spare = s.next;
            }
            else
            {
                s = new TableStack<>();
            }
            s.tab = t;
            s.length = n;
            s.index = i;
            s.next = this.stack;
            this.stack = s;
        }

        private void recoverState(int n)
        {
            TableStack<K, V> s;
            int len;
            while ((((s = this.stack)) != null) && (((this.index += ((len = s.length)))) >= n))
            {
                n = len;
                this.index = s.index;
                this.tab = s.tab;
                s.tab = null;
                final TableStack<K, V> next = s.next;
                s.next = this.spare; // save for reuse
                this.stack = next;
                this.spare = s;
            }
            if ((s == null) && (((this.index += this.baseSize)) >= n))
            {
                this.index = ++ this.baseIndex;
            }
        }
    }

    /*
     * Task classes. Coded in a regular but ugly format/style to
     * simplify checks that each variant differs in the right way from
     * others. The null screenings exist because compilers cannot tell
     * that we've already null-checked task arguments, so we force
     * simplest hoisted bypass to help avoid convoluted traps.
     */
    @SuppressWarnings({"serial", "ClassHasNoToStringMethod"})
    static final class ForEachKeyTask<K, V> extends BulkTask<K, V, Void>
    {
        final Consumer<? super K> action;

        ForEachKeyTask(final BulkTask<K, V, ?> p, final int b, final int i, final int f, final Node<K, V>[] t, final Consumer<? super K> action)
        {
            super(p, b, i, f, t);
            this.action = action;
        }

        @Override
        public final void compute()
        {
            final Consumer<? super K> action;
            if ((action = this.action) != null)
            {
                for (int i = this.baseIndex, f, h; (this.batch > 0) && (((h = (((f = this.baseLimit)) + i) >>> 1)) > i); )
                {
                    this.addToPendingCount(1);
                    new ForEachKeyTask<>(this, this.batch >>>= 1, this.baseLimit = h, f, this.tab, action).fork();
                }
                for (Node<K, V> p; (p = this.advance()) != null; )
                {
                    action.accept(p.key);
                }
                this.propagateCompletion();
            }
        }
    }

    @SuppressWarnings({"serial", "ClassHasNoToStringMethod"})
    static final class ForEachValueTask<K, V> extends BulkTask<K, V, Void>
    {
        final Consumer<? super V> action;

        ForEachValueTask(final BulkTask<K, V, ?> p, final int b, final int i, final int f, final Node<K, V>[] t, final Consumer<? super V> action)
        {
            super(p, b, i, f, t);
            this.action = action;
        }

        @Override
        public final void compute()
        {
            final Consumer<? super V> action;
            if ((action = this.action) != null)
            {
                for (int i = this.baseIndex, f, h; (this.batch > 0) && (((h = (((f = this.baseLimit)) + i) >>> 1)) > i); )
                {
                    this.addToPendingCount(1);
                    new ForEachValueTask<>(this, this.batch >>>= 1, this.baseLimit = h, f, this.tab, action).fork();
                }
                for (Node<K, V> p; (p = this.advance()) != null; )
                {
                    action.accept(p.val);
                }
                this.propagateCompletion();
            }
        }
    }

    @SuppressWarnings({"serial", "ClassHasNoToStringMethod"})
    static final class ForEachEntryTask<K, V> extends BulkTask<K, V, Void>
    {
        final Consumer<? super Entry<K, V>> action;

        ForEachEntryTask(final BulkTask<K, V, ?> p, final int b, final int i, final int f, final Node<K, V>[] t, final Consumer<? super Entry<K, V>> action)
        {
            super(p, b, i, f, t);
            this.action = action;
        }

        @Override
        public final void compute()
        {
            final Consumer<? super Entry<K, V>> action;
            if ((action = this.action) != null)
            {
                for (int i = this.baseIndex, f, h; (this.batch > 0) && (((h = (((f = this.baseLimit)) + i) >>> 1)) > i); )
                {
                    this.addToPendingCount(1);
                    new ForEachEntryTask<>(this, this.batch >>>= 1, this.baseLimit = h, f, this.tab, action).fork();
                }
                for (Node<K, V> p; (p = this.advance()) != null; )
                {
                    action.accept(p);
                }
                this.propagateCompletion();
            }
        }
    }

    @SuppressWarnings({"serial", "ClassHasNoToStringMethod"})
    static final class ForEachMappingTask<K, V> extends BulkTask<K, V, Void>
    {
        final BiConsumer<? super K, ? super V> action;

        ForEachMappingTask(final BulkTask<K, V, ?> p, final int b, final int i, final int f, final Node<K, V>[] t, final BiConsumer<? super K, ? super V> action)
        {
            super(p, b, i, f, t);
            this.action = action;
        }

        @Override
        public final void compute()
        {
            final BiConsumer<? super K, ? super V> action;
            if ((action = this.action) != null)
            {
                for (int i = this.baseIndex, f, h; (this.batch > 0) && (((h = (((f = this.baseLimit)) + i) >>> 1)) > i); )
                {
                    this.addToPendingCount(1);
                    new ForEachMappingTask<>(this, this.batch >>>= 1, this.baseLimit = h, f, this.tab, action).fork();
                }
                for (Node<K, V> p; (p = this.advance()) != null; )
                {
                    action.accept(p.key, p.val);
                }
                this.propagateCompletion();
            }
        }
    }

    @SuppressWarnings({"serial", "ClassHasNoToStringMethod"})
    static final class ForEachTransformedKeyTask<K, V, U> extends BulkTask<K, V, Void>
    {
        final Function<? super K, ? extends U> transformer;
        final Consumer<? super U>              action;

        ForEachTransformedKeyTask(final BulkTask<K, V, ?> p, final int b, final int i, final int f, final Node<K, V>[] t, final Function<? super K, ? extends U> transformer, final Consumer<? super U> action)
        {
            super(p, b, i, f, t);
            this.transformer = transformer;
            this.action = action;
        }

        @Override
        public final void compute()
        {
            final Function<? super K, ? extends U> transformer;
            final Consumer<? super U> action;
            if ((((transformer = this.transformer)) != null) && (((action = this.action)) != null))
            {
                for (int i = this.baseIndex, f, h; (this.batch > 0) && (((h = (((f = this.baseLimit)) + i) >>> 1)) > i); )
                {
                    this.addToPendingCount(1);
                    new ForEachTransformedKeyTask<>(this, this.batch >>>= 1, this.baseLimit = h, f, this.tab, transformer, action).fork();
                }
                for (Node<K, V> p; (p = this.advance()) != null; )
                {
                    final U u;
                    if ((u = transformer.apply(p.key)) != null)
                    {
                        action.accept(u);
                    }
                }
                this.propagateCompletion();
            }
        }
    }

    @SuppressWarnings({"serial", "ClassHasNoToStringMethod"})
    static final class ForEachTransformedValueTask<K, V, U> extends BulkTask<K, V, Void>
    {
        final Function<? super V, ? extends U> transformer;
        final Consumer<? super U>              action;

        ForEachTransformedValueTask(final BulkTask<K, V, ?> p, final int b, final int i, final int f, final Node<K, V>[] t, final Function<? super V, ? extends U> transformer, final Consumer<? super U> action)
        {
            super(p, b, i, f, t);
            this.transformer = transformer;
            this.action = action;
        }

        @Override
        public final void compute()
        {
            final Function<? super V, ? extends U> transformer;
            final Consumer<? super U> action;
            if ((((transformer = this.transformer)) != null) && (((action = this.action)) != null))
            {
                for (int i = this.baseIndex, f, h; (this.batch > 0) && (((h = (((f = this.baseLimit)) + i) >>> 1)) > i); )
                {
                    this.addToPendingCount(1);
                    new ForEachTransformedValueTask<>(this, this.batch >>>= 1, this.baseLimit = h, f, this.tab, transformer, action).fork();
                }
                for (Node<K, V> p; (p = this.advance()) != null; )
                {
                    final U u;
                    if ((u = transformer.apply(p.val)) != null)
                    {
                        action.accept(u);
                    }
                }
                this.propagateCompletion();
            }
        }
    }

    @SuppressWarnings({"serial", "ClassHasNoToStringMethod"})
    static final class ForEachTransformedEntryTask<K, V, U> extends BulkTask<K, V, Void>
    {
        final Function<Map.Entry<K, V>, ? extends U> transformer;
        final Consumer<? super U>                    action;

        ForEachTransformedEntryTask(final BulkTask<K, V, ?> p, final int b, final int i, final int f, final Node<K, V>[] t, final Function<Map.Entry<K, V>, ? extends U> transformer, final Consumer<? super U> action)
        {
            super(p, b, i, f, t);
            this.transformer = transformer;
            this.action = action;
        }

        @Override
        public final void compute()
        {
            final Function<Map.Entry<K, V>, ? extends U> transformer;
            final Consumer<? super U> action;
            if ((((transformer = this.transformer)) != null) && (((action = this.action)) != null))
            {
                for (int i = this.baseIndex, f, h; (this.batch > 0) && (((h = (((f = this.baseLimit)) + i) >>> 1)) > i); )
                {
                    this.addToPendingCount(1);
                    new ForEachTransformedEntryTask<>(this, this.batch >>>= 1, this.baseLimit = h, f, this.tab, transformer, action).fork();
                }
                for (Node<K, V> p; (p = this.advance()) != null; )
                {
                    final U u;
                    if ((u = transformer.apply(p)) != null)
                    {
                        action.accept(u);
                    }
                }
                this.propagateCompletion();
            }
        }
    }

    @SuppressWarnings({"serial", "ClassHasNoToStringMethod"})
    static final class ForEachTransformedMappingTask<K, V, U> extends BulkTask<K, V, Void>
    {
        final BiFunction<? super K, ? super V, ? extends U> transformer;
        final Consumer<? super U>                           action;

        ForEachTransformedMappingTask(final BulkTask<K, V, ?> p, final int b, final int i, final int f, final Node<K, V>[] t, final BiFunction<? super K, ? super V, ? extends U> transformer, final Consumer<? super U> action)
        {
            super(p, b, i, f, t);
            this.transformer = transformer;
            this.action = action;
        }

        @Override
        public final void compute()
        {
            final BiFunction<? super K, ? super V, ? extends U> transformer;
            final Consumer<? super U> action;
            if ((((transformer = this.transformer)) != null) && (((action = this.action)) != null))
            {
                for (int i = this.baseIndex, f, h; (this.batch > 0) && (((h = (((f = this.baseLimit)) + i) >>> 1)) > i); )
                {
                    this.addToPendingCount(1);
                    new ForEachTransformedMappingTask<>(this, this.batch >>>= 1, this.baseLimit = h, f, this.tab, transformer, action).fork();
                }
                for (Node<K, V> p; (p = this.advance()) != null; )
                {
                    final U u;
                    if ((u = transformer.apply(p.key, p.val)) != null)
                    {
                        action.accept(u);
                    }
                }
                this.propagateCompletion();
            }
        }
    }

    @SuppressWarnings({"serial", "ClassHasNoToStringMethod"})
    static final class SearchKeysTask<K, V, U> extends BulkTask<K, V, U>
    {
        final Function<? super K, ? extends U> searchFunction;
        final AtomicReference<U>               result;

        SearchKeysTask(final BulkTask<K, V, ?> p, final int b, final int i, final int f, final Node<K, V>[] t, final Function<? super K, ? extends U> searchFunction, final AtomicReference<U> result)
        {
            super(p, b, i, f, t);
            this.searchFunction = searchFunction;
            this.result = result;
        }

        @Override
        public final U getRawResult()
        {
            return this.result.get();
        }

        @Override
        public final void compute()
        {
            final Function<? super K, ? extends U> searchFunction;
            final AtomicReference<U> result;
            if ((((searchFunction = this.searchFunction)) != null) && (((result = this.result)) != null))
            {
                for (int i = this.baseIndex, f, h; (this.batch > 0) && (((h = (((f = this.baseLimit)) + i) >>> 1)) > i); )
                {
                    if (result.get() != null)
                    {
                        return;
                    }
                    this.addToPendingCount(1);
                    new SearchKeysTask<>(this, this.batch >>>= 1, this.baseLimit = h, f, this.tab, searchFunction, result).fork();
                }
                while (result.get() == null)
                {
                    final U u;
                    final Node<K, V> p;
                    if ((p = this.advance()) == null)
                    {
                        this.propagateCompletion();
                        break;
                    }
                    if ((u = searchFunction.apply(p.key)) != null)
                    {
                        if (result.compareAndSet(null, u))
                        {
                            this.quietlyCompleteRoot();
                        }
                        break;
                    }
                }
            }
        }
    }

    @SuppressWarnings({"serial", "ClassHasNoToStringMethod"})
    static final class SearchValuesTask<K, V, U> extends BulkTask<K, V, U>
    {
        final Function<? super V, ? extends U> searchFunction;
        final AtomicReference<U>               result;

        SearchValuesTask(final BulkTask<K, V, ?> p, final int b, final int i, final int f, final Node<K, V>[] t, final Function<? super V, ? extends U> searchFunction, final AtomicReference<U> result)
        {
            super(p, b, i, f, t);
            this.searchFunction = searchFunction;
            this.result = result;
        }

        @Override
        public final U getRawResult()
        {
            return this.result.get();
        }

        @Override
        public final void compute()
        {
            final Function<? super V, ? extends U> searchFunction;
            final AtomicReference<U> result;
            if ((((searchFunction = this.searchFunction)) != null) && (((result = this.result)) != null))
            {
                for (int i = this.baseIndex, f, h; (this.batch > 0) && (((h = (((f = this.baseLimit)) + i) >>> 1)) > i); )
                {
                    if (result.get() != null)
                    {
                        return;
                    }
                    this.addToPendingCount(1);
                    new SearchValuesTask<>(this, this.batch >>>= 1, this.baseLimit = h, f, this.tab, searchFunction, result).fork();
                }
                while (result.get() == null)
                {
                    final U u;
                    final Node<K, V> p;
                    if ((p = this.advance()) == null)
                    {
                        this.propagateCompletion();
                        break;
                    }
                    if ((u = searchFunction.apply(p.val)) != null)
                    {
                        if (result.compareAndSet(null, u))
                        {
                            this.quietlyCompleteRoot();
                        }
                        break;
                    }
                }
            }
        }
    }

    @SuppressWarnings({"serial", "ClassHasNoToStringMethod"})
    static final class SearchEntriesTask<K, V, U> extends BulkTask<K, V, U>
    {
        final Function<Entry<K, V>, ? extends U> searchFunction;
        final AtomicReference<U>                 result;

        SearchEntriesTask(final BulkTask<K, V, ?> p, final int b, final int i, final int f, final Node<K, V>[] t, final Function<Entry<K, V>, ? extends U> searchFunction, final AtomicReference<U> result)
        {
            super(p, b, i, f, t);
            this.searchFunction = searchFunction;
            this.result = result;
        }

        @Override
        public final U getRawResult()
        {
            return this.result.get();
        }

        @Override
        public final void compute()
        {
            final Function<Entry<K, V>, ? extends U> searchFunction;
            final AtomicReference<U> result;
            if ((((searchFunction = this.searchFunction)) != null) && (((result = this.result)) != null))
            {
                for (int i = this.baseIndex, f, h; (this.batch > 0) && (((h = (((f = this.baseLimit)) + i) >>> 1)) > i); )
                {
                    if (result.get() != null)
                    {
                        return;
                    }
                    this.addToPendingCount(1);
                    new SearchEntriesTask<>(this, this.batch >>>= 1, this.baseLimit = h, f, this.tab, searchFunction, result).fork();
                }
                while (result.get() == null)
                {
                    final U u;
                    final Node<K, V> p;
                    if ((p = this.advance()) == null)
                    {
                        this.propagateCompletion();
                        break;
                    }
                    if ((u = searchFunction.apply(p)) != null)
                    {
                        if (result.compareAndSet(null, u))
                        {
                            this.quietlyCompleteRoot();
                        }
                        return;
                    }
                }
            }
        }
    }

    @SuppressWarnings({"serial", "ClassHasNoToStringMethod"})
    static final class SearchMappingsTask<K, V, U> extends BulkTask<K, V, U>
    {
        final BiFunction<? super K, ? super V, ? extends U> searchFunction;
        final AtomicReference<U>                            result;

        SearchMappingsTask(final BulkTask<K, V, ?> p, final int b, final int i, final int f, final Node<K, V>[] t, final BiFunction<? super K, ? super V, ? extends U> searchFunction, final AtomicReference<U> result)
        {
            super(p, b, i, f, t);
            this.searchFunction = searchFunction;
            this.result = result;
        }

        @Override
        public final U getRawResult()
        {
            return this.result.get();
        }

        @Override
        public final void compute()
        {
            final BiFunction<? super K, ? super V, ? extends U> searchFunction;
            final AtomicReference<U> result;
            if ((((searchFunction = this.searchFunction)) != null) && (((result = this.result)) != null))
            {
                for (int i = this.baseIndex, f, h; (this.batch > 0) && (((h = (((f = this.baseLimit)) + i) >>> 1)) > i); )
                {
                    if (result.get() != null)
                    {
                        return;
                    }
                    this.addToPendingCount(1);
                    new SearchMappingsTask<>(this, this.batch >>>= 1, this.baseLimit = h, f, this.tab, searchFunction, result).fork();
                }
                while (result.get() == null)
                {
                    final U u;
                    final Node<K, V> p;
                    if ((p = this.advance()) == null)
                    {
                        this.propagateCompletion();
                        break;
                    }
                    if ((u = searchFunction.apply(p.key, p.val)) != null)
                    {
                        if (result.compareAndSet(null, u))
                        {
                            this.quietlyCompleteRoot();
                        }
                        break;
                    }
                }
            }
        }
    }

    @SuppressWarnings({"serial", "ClassHasNoToStringMethod"})
    static final class ReduceKeysTask<K, V> extends BulkTask<K, V, K>
    {
        final BiFunction<? super K, ? super K, ? extends K> reducer;
        final ReduceKeysTask<K, V>                          nextRight;
        K                    result;
        ReduceKeysTask<K, V> rights;

        ReduceKeysTask(final BulkTask<K, V, ?> p, final int b, final int i, final int f, final Node<K, V>[] t, final ReduceKeysTask<K, V> nextRight, final BiFunction<? super K, ? super K, ? extends K> reducer)
        {
            super(p, b, i, f, t);
            this.nextRight = nextRight;
            this.reducer = reducer;
        }

        @Override
        public final K getRawResult()
        {
            return this.result;
        }

        @Override
        public final void compute()
        {
            final BiFunction<? super K, ? super K, ? extends K> reducer;
            if ((reducer = this.reducer) != null)
            {
                for (int i = this.baseIndex, f, h; (this.batch > 0) && (((h = (((f = this.baseLimit)) + i) >>> 1)) > i); )
                {
                    this.addToPendingCount(1);
                    (this.rights = new ReduceKeysTask<>(this, this.batch >>>= 1, this.baseLimit = h, f, this.tab, this.rights, reducer)).fork();
                }
                K r = null;
                for (Node<K, V> p; (p = this.advance()) != null; )
                {
                    final K u = p.key;
                    r = (r == null) ? u : ((u == null) ? r : reducer.apply(r, u));
                }
                this.result = r;
                CountedCompleter<?> c;
                for (c = this.firstComplete(); c != null; c = c.nextComplete())
                {
                    @SuppressWarnings("unchecked")
                    final ReduceKeysTask<K, V> t = (ReduceKeysTask<K, V>) c;
                    @SuppressWarnings("unchecked")
                    ReduceKeysTask<K, V> s = t.rights;
                    while (s != null)
                    {
                        final K tr;
                        final K sr;
                        if ((sr = s.result) != null)
                        {
                            t.result = (((tr = t.result) == null) ? sr : reducer.apply(tr, sr));
                        }
                        s = t.rights = s.nextRight;
                    }
                }
            }
        }
    }

    @SuppressWarnings({"serial", "ClassHasNoToStringMethod"})
    static final class ReduceValuesTask<K, V> extends BulkTask<K, V, V>
    {
        final BiFunction<? super V, ? super V, ? extends V> reducer;
        final ReduceValuesTask<K, V>                        nextRight;
        V                      result;
        ReduceValuesTask<K, V> rights;

        ReduceValuesTask(final BulkTask<K, V, ?> p, final int b, final int i, final int f, final Node<K, V>[] t, final ReduceValuesTask<K, V> nextRight, final BiFunction<? super V, ? super V, ? extends V> reducer)
        {
            super(p, b, i, f, t);
            this.nextRight = nextRight;
            this.reducer = reducer;
        }

        @Override
        public final V getRawResult()
        {
            return this.result;
        }

        @Override
        public final void compute()
        {
            final BiFunction<? super V, ? super V, ? extends V> reducer;
            if ((reducer = this.reducer) != null)
            {
                for (int i = this.baseIndex, f, h; (this.batch > 0) && (((h = (((f = this.baseLimit)) + i) >>> 1)) > i); )
                {
                    this.addToPendingCount(1);
                    (this.rights = new ReduceValuesTask<>(this, this.batch >>>= 1, this.baseLimit = h, f, this.tab, this.rights, reducer)).fork();
                }
                V r = null;
                for (Node<K, V> p; (p = this.advance()) != null; )
                {
                    final V v = p.val;
                    r = (r == null) ? v : reducer.apply(r, v);
                }
                this.result = r;
                CountedCompleter<?> c;
                for (c = this.firstComplete(); c != null; c = c.nextComplete())
                {
                    @SuppressWarnings("unchecked")
                    final ReduceValuesTask<K, V> t = (ReduceValuesTask<K, V>) c;
                    @SuppressWarnings("unchecked")
                    ReduceValuesTask<K, V> s = t.rights;
                    while (s != null)
                    {
                        final V tr;
                        final V sr;
                        if ((sr = s.result) != null)
                        {
                            t.result = (((tr = t.result) == null) ? sr : reducer.apply(tr, sr));
                        }
                        s = t.rights = s.nextRight;
                    }
                }
            }
        }
    }

    @SuppressWarnings({"serial", "ClassHasNoToStringMethod"})
    static final class ReduceEntriesTask<K, V> extends BulkTask<K, V, Map.Entry<K, V>>
    {
        final BiFunction<Map.Entry<K, V>, Map.Entry<K, V>, ? extends Map.Entry<K, V>> reducer;
        final ReduceEntriesTask<K, V>                                                 nextRight;
        Map.Entry<K, V>         result;
        ReduceEntriesTask<K, V> rights;

        ReduceEntriesTask(final BulkTask<K, V, ?> p, final int b, final int i, final int f, final Node<K, V>[] t, final ReduceEntriesTask<K, V> nextRight, final BiFunction<Entry<K, V>, Map.Entry<K, V>, ? extends Map.Entry<K, V>> reducer)
        {
            super(p, b, i, f, t);
            this.nextRight = nextRight;
            this.reducer = reducer;
        }

        @Override
        public final Map.Entry<K, V> getRawResult()
        {
            return this.result;
        }

        @Override
        public final void compute()
        {
            final BiFunction<Map.Entry<K, V>, Map.Entry<K, V>, ? extends Map.Entry<K, V>> reducer;
            if ((reducer = this.reducer) != null)
            {
                for (int i = this.baseIndex, f, h; (this.batch > 0) && (((h = (((f = this.baseLimit)) + i) >>> 1)) > i); )
                {
                    this.addToPendingCount(1);
                    (this.rights = new ReduceEntriesTask<>(this, this.batch >>>= 1, this.baseLimit = h, f, this.tab, this.rights, reducer)).fork();
                }
                Map.Entry<K, V> r = null;
                for (Node<K, V> p; (p = this.advance()) != null; )
                {
                    r = (r == null) ? p : reducer.apply(r, p);
                }
                this.result = r;
                CountedCompleter<?> c;
                for (c = this.firstComplete(); c != null; c = c.nextComplete())
                {
                    @SuppressWarnings("unchecked")
                    final ReduceEntriesTask<K, V> t = (ReduceEntriesTask<K, V>) c;
                    @SuppressWarnings("unchecked")
                    ReduceEntriesTask<K, V> s = t.rights;
                    while (s != null)
                    {
                        final Map.Entry<K, V> tr;
                        final Map.Entry<K, V> sr;
                        if ((sr = s.result) != null)
                        {
                            t.result = (((tr = t.result) == null) ? sr : reducer.apply(tr, sr));
                        }
                        s = t.rights = s.nextRight;
                    }
                }
            }
        }
    }

    @SuppressWarnings({"serial", "ClassHasNoToStringMethod"})
    static final class MapReduceKeysTask<K, V, U> extends BulkTask<K, V, U>
    {
        final Function<? super K, ? extends U>              transformer;
        final BiFunction<? super U, ? super U, ? extends U> reducer;
        final MapReduceKeysTask<K, V, U>                    nextRight;
        U                          result;
        MapReduceKeysTask<K, V, U> rights;

        MapReduceKeysTask(final BulkTask<K, V, ?> p, final int b, final int i, final int f, final Node<K, V>[] t, final MapReduceKeysTask<K, V, U> nextRight, final Function<? super K, ? extends U> transformer, final BiFunction<? super U, ? super U, ? extends U> reducer)
        {
            super(p, b, i, f, t);
            this.nextRight = nextRight;
            this.transformer = transformer;
            this.reducer = reducer;
        }

        @Override
        public final U getRawResult()
        {
            return this.result;
        }

        @Override
        public final void compute()
        {
            final Function<? super K, ? extends U> transformer;
            final BiFunction<? super U, ? super U, ? extends U> reducer;
            if ((((transformer = this.transformer)) != null) && (((reducer = this.reducer)) != null))
            {
                for (int i = this.baseIndex, f, h; (this.batch > 0) && (((h = (((f = this.baseLimit)) + i) >>> 1)) > i); )
                {
                    this.addToPendingCount(1);
                    (this.rights = new MapReduceKeysTask<>(this, this.batch >>>= 1, this.baseLimit = h, f, this.tab, this.rights, transformer, reducer)).fork();
                }
                U r = null;
                for (Node<K, V> p; (p = this.advance()) != null; )
                {
                    final U u;
                    if ((u = transformer.apply(p.key)) != null)
                    {
                        r = (r == null) ? u : reducer.apply(r, u);
                    }
                }
                this.result = r;
                CountedCompleter<?> c;
                for (c = this.firstComplete(); c != null; c = c.nextComplete())
                {
                    @SuppressWarnings("unchecked")
                    final MapReduceKeysTask<K, V, U> t = (MapReduceKeysTask<K, V, U>) c;
                    @SuppressWarnings("unchecked")
                    MapReduceKeysTask<K, V, U> s = t.rights;
                    while (s != null)
                    {
                        final U tr;
                        final U sr;
                        if ((sr = s.result) != null)
                        {
                            t.result = (((tr = t.result) == null) ? sr : reducer.apply(tr, sr));
                        }
                        s = t.rights = s.nextRight;
                    }
                }
            }
        }
    }

    @SuppressWarnings({"serial", "ClassHasNoToStringMethod"})
    static final class MapReduceValuesTask<K, V, U> extends BulkTask<K, V, U>
    {
        final Function<? super V, ? extends U>              transformer;
        final BiFunction<? super U, ? super U, ? extends U> reducer;
        final MapReduceValuesTask<K, V, U>                  nextRight;
        U                            result;
        MapReduceValuesTask<K, V, U> rights;

        MapReduceValuesTask(final BulkTask<K, V, ?> p, final int b, final int i, final int f, final Node<K, V>[] t, final MapReduceValuesTask<K, V, U> nextRight, final Function<? super V, ? extends U> transformer, final BiFunction<? super U, ? super U, ? extends U> reducer)
        {
            super(p, b, i, f, t);
            this.nextRight = nextRight;
            this.transformer = transformer;
            this.reducer = reducer;
        }

        @Override
        public final U getRawResult()
        {
            return this.result;
        }

        @Override
        public final void compute()
        {
            final Function<? super V, ? extends U> transformer;
            final BiFunction<? super U, ? super U, ? extends U> reducer;
            if ((((transformer = this.transformer)) != null) && (((reducer = this.reducer)) != null))
            {
                for (int i = this.baseIndex, f, h; (this.batch > 0) && (((h = (((f = this.baseLimit)) + i) >>> 1)) > i); )
                {
                    this.addToPendingCount(1);
                    (this.rights = new MapReduceValuesTask<>(this, this.batch >>>= 1, this.baseLimit = h, f, this.tab, this.rights, transformer, reducer)).fork();
                }
                U r = null;
                for (Node<K, V> p; (p = this.advance()) != null; )
                {
                    final U u;
                    if ((u = transformer.apply(p.val)) != null)
                    {
                        r = (r == null) ? u : reducer.apply(r, u);
                    }
                }
                this.result = r;
                CountedCompleter<?> c;
                for (c = this.firstComplete(); c != null; c = c.nextComplete())
                {
                    @SuppressWarnings("unchecked")
                    final MapReduceValuesTask<K, V, U> t = (MapReduceValuesTask<K, V, U>) c;
                    @SuppressWarnings("unchecked")
                    MapReduceValuesTask<K, V, U> s = t.rights;
                    while (s != null)
                    {
                        final U tr;
                        final U sr;
                        if ((sr = s.result) != null)
                        {
                            t.result = (((tr = t.result) == null) ? sr : reducer.apply(tr, sr));
                        }
                        s = t.rights = s.nextRight;
                    }
                }
            }
        }
    }

    @SuppressWarnings({"serial", "ClassHasNoToStringMethod"})
    static final class MapReduceEntriesTask<K, V, U> extends BulkTask<K, V, U>
    {
        final Function<Map.Entry<K, V>, ? extends U>        transformer;
        final BiFunction<? super U, ? super U, ? extends U> reducer;
        final MapReduceEntriesTask<K, V, U>                 nextRight;
        U                             result;
        MapReduceEntriesTask<K, V, U> rights;

        MapReduceEntriesTask(final BulkTask<K, V, ?> p, final int b, final int i, final int f, final Node<K, V>[] t, final MapReduceEntriesTask<K, V, U> nextRight, final Function<Map.Entry<K, V>, ? extends U> transformer, final BiFunction<? super U, ? super U, ? extends U> reducer)
        {
            super(p, b, i, f, t);
            this.nextRight = nextRight;
            this.transformer = transformer;
            this.reducer = reducer;
        }

        @Override
        public final U getRawResult()
        {
            return this.result;
        }

        @Override
        public final void compute()
        {
            final Function<Map.Entry<K, V>, ? extends U> transformer;
            final BiFunction<? super U, ? super U, ? extends U> reducer;
            if ((((transformer = this.transformer)) != null) && (((reducer = this.reducer)) != null))
            {
                for (int i = this.baseIndex, f, h; (this.batch > 0) && (((h = (((f = this.baseLimit)) + i) >>> 1)) > i); )
                {
                    this.addToPendingCount(1);
                    (this.rights = new MapReduceEntriesTask<>(this, this.batch >>>= 1, this.baseLimit = h, f, this.tab, this.rights, transformer, reducer)).fork();
                }
                U r = null;
                for (Node<K, V> p; (p = this.advance()) != null; )
                {
                    final U u;
                    if ((u = transformer.apply(p)) != null)
                    {
                        r = (r == null) ? u : reducer.apply(r, u);
                    }
                }
                this.result = r;
                CountedCompleter<?> c;
                for (c = this.firstComplete(); c != null; c = c.nextComplete())
                {
                    @SuppressWarnings("unchecked")
                    final MapReduceEntriesTask<K, V, U> t = (MapReduceEntriesTask<K, V, U>) c;
                    @SuppressWarnings("unchecked")
                    MapReduceEntriesTask<K, V, U> s = t.rights;
                    while (s != null)
                    {
                        final U tr;
                        final U sr;
                        if ((sr = s.result) != null)
                        {
                            t.result = (((tr = t.result) == null) ? sr : reducer.apply(tr, sr));
                        }
                        s = t.rights = s.nextRight;
                    }
                }
            }
        }
    }

    @SuppressWarnings({"serial", "ClassHasNoToStringMethod"})
    static final class MapReduceMappingsTask<K, V, U> extends BulkTask<K, V, U>
    {
        final BiFunction<? super K, ? super V, ? extends U> transformer;
        final BiFunction<? super U, ? super U, ? extends U> reducer;
        final MapReduceMappingsTask<K, V, U>                nextRight;
        U                              result;
        MapReduceMappingsTask<K, V, U> rights;

        MapReduceMappingsTask(final BulkTask<K, V, ?> p, final int b, final int i, final int f, final Node<K, V>[] t, final MapReduceMappingsTask<K, V, U> nextRight, final BiFunction<? super K, ? super V, ? extends U> transformer, final BiFunction<? super U, ? super U, ? extends U> reducer)
        {
            super(p, b, i, f, t);
            this.nextRight = nextRight;
            this.transformer = transformer;
            this.reducer = reducer;
        }

        @Override
        public final U getRawResult()
        {
            return this.result;
        }

        @Override
        public final void compute()
        {
            final BiFunction<? super K, ? super V, ? extends U> transformer;
            final BiFunction<? super U, ? super U, ? extends U> reducer;
            if ((((transformer = this.transformer)) != null) && (((reducer = this.reducer)) != null))
            {
                for (int i = this.baseIndex, f, h; (this.batch > 0) && (((h = (((f = this.baseLimit)) + i) >>> 1)) > i); )
                {
                    this.addToPendingCount(1);
                    (this.rights = new MapReduceMappingsTask<>(this, this.batch >>>= 1, this.baseLimit = h, f, this.tab, this.rights, transformer, reducer)).fork();
                }
                U r = null;
                for (Node<K, V> p; (p = this.advance()) != null; )
                {
                    final U u;
                    if ((u = transformer.apply(p.key, p.val)) != null)
                    {
                        r = (r == null) ? u : reducer.apply(r, u);
                    }
                }
                this.result = r;
                CountedCompleter<?> c;
                for (c = this.firstComplete(); c != null; c = c.nextComplete())
                {
                    @SuppressWarnings("unchecked")
                    final MapReduceMappingsTask<K, V, U> t = (MapReduceMappingsTask<K, V, U>) c;
                    @SuppressWarnings("unchecked")
                    MapReduceMappingsTask<K, V, U> s = t.rights;
                    while (s != null)
                    {
                        final U tr;
                        final U sr;
                        if ((sr = s.result) != null)
                        {
                            t.result = (((tr = t.result) == null) ? sr : reducer.apply(tr, sr));
                        }
                        s = t.rights = s.nextRight;
                    }
                }
            }
        }
    }

    @SuppressWarnings({"serial", "ClassHasNoToStringMethod"})
    static final class MapReduceKeysToDoubleTask<K, V> extends BulkTask<K, V, Double>
    {
        final ToDoubleFunction<? super K>     transformer;
        final DoubleBinaryOperator            reducer;
        final double                          basis;
        final MapReduceKeysToDoubleTask<K, V> nextRight;
        double                          result;
        MapReduceKeysToDoubleTask<K, V> rights;

        MapReduceKeysToDoubleTask(final BulkTask<K, V, ?> p, final int b, final int i, final int f, final Node<K, V>[] t, final MapReduceKeysToDoubleTask<K, V> nextRight, final ToDoubleFunction<? super K> transformer, final double basis, final DoubleBinaryOperator reducer)
        {
            super(p, b, i, f, t);
            this.nextRight = nextRight;
            this.transformer = transformer;
            this.basis = basis;
            this.reducer = reducer;
        }

        @Override
        public final Double getRawResult()
        {
            return this.result;
        }

        @Override
        public final void compute()
        {
            final ToDoubleFunction<? super K> transformer;
            final DoubleBinaryOperator reducer;
            if ((((transformer = this.transformer)) != null) && (((reducer = this.reducer)) != null))
            {
                double r = this.basis;
                for (int i = this.baseIndex, f, h; (this.batch > 0) && (((h = (((f = this.baseLimit)) + i) >>> 1)) > i); )
                {
                    this.addToPendingCount(1);
                    (this.rights = new MapReduceKeysToDoubleTask<>(this, this.batch >>>= 1, this.baseLimit = h, f, this.tab, this.rights, transformer, r, reducer)).fork();
                }
                for (Node<K, V> p; (p = this.advance()) != null; )
                {
                    r = reducer.applyAsDouble(r, transformer.applyAsDouble(p.key));
                }
                this.result = r;
                CountedCompleter<?> c;
                for (c = this.firstComplete(); c != null; c = c.nextComplete())
                {
                    @SuppressWarnings("unchecked")
                    final MapReduceKeysToDoubleTask<K, V> t = (MapReduceKeysToDoubleTask<K, V>) c;
                    @SuppressWarnings("unchecked")
                    MapReduceKeysToDoubleTask<K, V> s = t.rights;
                    while (s != null)
                    {
                        t.result = reducer.applyAsDouble(t.result, s.result);
                        s = t.rights = s.nextRight;
                    }
                }
            }
        }
    }

    @SuppressWarnings({"serial", "ClassHasNoToStringMethod"})
    static final class MapReduceValuesToDoubleTask<K, V> extends BulkTask<K, V, Double>
    {
        final ToDoubleFunction<? super V>       transformer;
        final DoubleBinaryOperator              reducer;
        final double                            basis;
        final MapReduceValuesToDoubleTask<K, V> nextRight;
        double                            result;
        MapReduceValuesToDoubleTask<K, V> rights;

        MapReduceValuesToDoubleTask(final BulkTask<K, V, ?> p, final int b, final int i, final int f, final Node<K, V>[] t, final MapReduceValuesToDoubleTask<K, V> nextRight, final ToDoubleFunction<? super V> transformer, final double basis, final DoubleBinaryOperator reducer)
        {
            super(p, b, i, f, t);
            this.nextRight = nextRight;
            this.transformer = transformer;
            this.basis = basis;
            this.reducer = reducer;
        }

        @Override
        public final Double getRawResult()
        {
            return this.result;
        }

        @Override
        public final void compute()
        {
            final ToDoubleFunction<? super V> transformer;
            final DoubleBinaryOperator reducer;
            if ((((transformer = this.transformer)) != null) && (((reducer = this.reducer)) != null))
            {
                double r = this.basis;
                for (int i = this.baseIndex, f, h; (this.batch > 0) && (((h = (((f = this.baseLimit)) + i) >>> 1)) > i); )
                {
                    this.addToPendingCount(1);
                    (this.rights = new MapReduceValuesToDoubleTask<>(this, this.batch >>>= 1, this.baseLimit = h, f, this.tab, this.rights, transformer, r, reducer)).fork();
                }
                for (Node<K, V> p; (p = this.advance()) != null; )
                {
                    r = reducer.applyAsDouble(r, transformer.applyAsDouble(p.val));
                }
                this.result = r;
                CountedCompleter<?> c;
                for (c = this.firstComplete(); c != null; c = c.nextComplete())
                {
                    @SuppressWarnings("unchecked")
                    final MapReduceValuesToDoubleTask<K, V> t = (MapReduceValuesToDoubleTask<K, V>) c;
                    @SuppressWarnings("unchecked")
                    MapReduceValuesToDoubleTask<K, V> s = t.rights;
                    while (s != null)
                    {
                        t.result = reducer.applyAsDouble(t.result, s.result);
                        s = t.rights = s.nextRight;
                    }
                }
            }
        }
    }

    @SuppressWarnings({"serial", "ClassHasNoToStringMethod"})
    static final class MapReduceEntriesToDoubleTask<K, V> extends BulkTask<K, V, Double>
    {
        final ToDoubleFunction<Map.Entry<K, V>>  transformer;
        final DoubleBinaryOperator               reducer;
        final double                             basis;
        final MapReduceEntriesToDoubleTask<K, V> nextRight;
        double                             result;
        MapReduceEntriesToDoubleTask<K, V> rights;

        MapReduceEntriesToDoubleTask(final BulkTask<K, V, ?> p, final int b, final int i, final int f, final Node<K, V>[] t, final MapReduceEntriesToDoubleTask<K, V> nextRight, final ToDoubleFunction<Map.Entry<K, V>> transformer, final double basis, final DoubleBinaryOperator reducer)
        {
            super(p, b, i, f, t);
            this.nextRight = nextRight;
            this.transformer = transformer;
            this.basis = basis;
            this.reducer = reducer;
        }

        @Override
        public final Double getRawResult()
        {
            return this.result;
        }

        @Override
        public final void compute()
        {
            final ToDoubleFunction<Map.Entry<K, V>> transformer;
            final DoubleBinaryOperator reducer;
            if ((((transformer = this.transformer)) != null) && (((reducer = this.reducer)) != null))
            {
                double r = this.basis;
                for (int i = this.baseIndex, f, h; (this.batch > 0) && (((h = (((f = this.baseLimit)) + i) >>> 1)) > i); )
                {
                    this.addToPendingCount(1);
                    (this.rights = new MapReduceEntriesToDoubleTask<>(this, this.batch >>>= 1, this.baseLimit = h, f, this.tab, this.rights, transformer, r, reducer)).fork();
                }
                for (Node<K, V> p; (p = this.advance()) != null; )
                {
                    r = reducer.applyAsDouble(r, transformer.applyAsDouble(p));
                }
                this.result = r;
                CountedCompleter<?> c;
                for (c = this.firstComplete(); c != null; c = c.nextComplete())
                {
                    @SuppressWarnings("unchecked")
                    final MapReduceEntriesToDoubleTask<K, V> t = (MapReduceEntriesToDoubleTask<K, V>) c;
                    @SuppressWarnings("unchecked")
                    MapReduceEntriesToDoubleTask<K, V> s = t.rights;
                    while (s != null)
                    {
                        t.result = reducer.applyAsDouble(t.result, s.result);
                        s = t.rights = s.nextRight;
                    }
                }
            }
        }
    }

    @SuppressWarnings({"serial", "ClassHasNoToStringMethod"})
    static final class MapReduceMappingsToDoubleTask<K, V> extends BulkTask<K, V, Double>
    {
        final ToDoubleBiFunction<? super K, ? super V> transformer;
        final DoubleBinaryOperator                     reducer;
        final double                                   basis;
        final MapReduceMappingsToDoubleTask<K, V>      nextRight;
        double                              result;
        MapReduceMappingsToDoubleTask<K, V> rights;

        MapReduceMappingsToDoubleTask(final BulkTask<K, V, ?> p, final int b, final int i, final int f, final Node<K, V>[] t, final MapReduceMappingsToDoubleTask<K, V> nextRight, final ToDoubleBiFunction<? super K, ? super V> transformer, final double basis, final DoubleBinaryOperator reducer)
        {
            super(p, b, i, f, t);
            this.nextRight = nextRight;
            this.transformer = transformer;
            this.basis = basis;
            this.reducer = reducer;
        }

        @Override
        public final Double getRawResult()
        {
            return this.result;
        }

        @Override
        public final void compute()
        {
            final ToDoubleBiFunction<? super K, ? super V> transformer;
            final DoubleBinaryOperator reducer;
            if ((((transformer = this.transformer)) != null) && (((reducer = this.reducer)) != null))
            {
                double r = this.basis;
                for (int i = this.baseIndex, f, h; (this.batch > 0) && (((h = (((f = this.baseLimit)) + i) >>> 1)) > i); )
                {
                    this.addToPendingCount(1);
                    (this.rights = new MapReduceMappingsToDoubleTask<>(this, this.batch >>>= 1, this.baseLimit = h, f, this.tab, this.rights, transformer, r, reducer)).fork();
                }
                for (Node<K, V> p; (p = this.advance()) != null; )
                {
                    r = reducer.applyAsDouble(r, transformer.applyAsDouble(p.key, p.val));
                }
                this.result = r;
                CountedCompleter<?> c;
                for (c = this.firstComplete(); c != null; c = c.nextComplete())
                {
                    @SuppressWarnings("unchecked")
                    final MapReduceMappingsToDoubleTask<K, V> t = (MapReduceMappingsToDoubleTask<K, V>) c;
                    @SuppressWarnings("unchecked")
                    MapReduceMappingsToDoubleTask<K, V> s = t.rights;
                    while (s != null)
                    {
                        t.result = reducer.applyAsDouble(t.result, s.result);
                        s = t.rights = s.nextRight;
                    }
                }
            }
        }
    }

    @SuppressWarnings({"serial", "ClassHasNoToStringMethod"})
    static final class MapReduceKeysToLongTask<K, V> extends BulkTask<K, V, Long>
    {
        final ToLongFunction<? super K>     transformer;
        final LongBinaryOperator            reducer;
        final long                          basis;
        final MapReduceKeysToLongTask<K, V> nextRight;
        long                          result;
        MapReduceKeysToLongTask<K, V> rights;

        MapReduceKeysToLongTask(final BulkTask<K, V, ?> p, final int b, final int i, final int f, final Node<K, V>[] t, final MapReduceKeysToLongTask<K, V> nextRight, final ToLongFunction<? super K> transformer, final long basis, final LongBinaryOperator reducer)
        {
            super(p, b, i, f, t);
            this.nextRight = nextRight;
            this.transformer = transformer;
            this.basis = basis;
            this.reducer = reducer;
        }

        @Override
        public final Long getRawResult()
        {
            return this.result;
        }

        @Override
        public final void compute()
        {
            final ToLongFunction<? super K> transformer;
            final LongBinaryOperator reducer;
            if ((((transformer = this.transformer)) != null) && (((reducer = this.reducer)) != null))
            {
                long r = this.basis;
                for (int i = this.baseIndex, f, h; (this.batch > 0) && (((h = (((f = this.baseLimit)) + i) >>> 1)) > i); )
                {
                    this.addToPendingCount(1);
                    (this.rights = new MapReduceKeysToLongTask<>(this, this.batch >>>= 1, this.baseLimit = h, f, this.tab, this.rights, transformer, r, reducer)).fork();
                }
                for (Node<K, V> p; (p = this.advance()) != null; )
                {
                    r = reducer.applyAsLong(r, transformer.applyAsLong(p.key));
                }
                this.result = r;
                CountedCompleter<?> c;
                for (c = this.firstComplete(); c != null; c = c.nextComplete())
                {
                    @SuppressWarnings("unchecked")
                    final MapReduceKeysToLongTask<K, V> t = (MapReduceKeysToLongTask<K, V>) c;
                    @SuppressWarnings("unchecked")
                    MapReduceKeysToLongTask<K, V> s = t.rights;
                    while (s != null)
                    {
                        t.result = reducer.applyAsLong(t.result, s.result);
                        s = t.rights = s.nextRight;
                    }
                }
            }
        }
    }

    @SuppressWarnings({"serial", "ClassHasNoToStringMethod"})
    static final class MapReduceValuesToLongTask<K, V> extends BulkTask<K, V, Long>
    {
        final ToLongFunction<? super V>       transformer;
        final LongBinaryOperator              reducer;
        final long                            basis;
        final MapReduceValuesToLongTask<K, V> nextRight;
        long                            result;
        MapReduceValuesToLongTask<K, V> rights;

        MapReduceValuesToLongTask(final BulkTask<K, V, ?> p, final int b, final int i, final int f, final Node<K, V>[] t, final MapReduceValuesToLongTask<K, V> nextRight, final ToLongFunction<? super V> transformer, final long basis, final LongBinaryOperator reducer)
        {
            super(p, b, i, f, t);
            this.nextRight = nextRight;
            this.transformer = transformer;
            this.basis = basis;
            this.reducer = reducer;
        }

        @Override
        public final Long getRawResult()
        {
            return this.result;
        }

        @Override
        public final void compute()
        {
            final ToLongFunction<? super V> transformer;
            final LongBinaryOperator reducer;
            if ((((transformer = this.transformer)) != null) && (((reducer = this.reducer)) != null))
            {
                long r = this.basis;
                for (int i = this.baseIndex, f, h; (this.batch > 0) && (((h = (((f = this.baseLimit)) + i) >>> 1)) > i); )
                {
                    this.addToPendingCount(1);
                    (this.rights = new MapReduceValuesToLongTask<>(this, this.batch >>>= 1, this.baseLimit = h, f, this.tab, this.rights, transformer, r, reducer)).fork();
                }
                for (Node<K, V> p; (p = this.advance()) != null; )
                {
                    r = reducer.applyAsLong(r, transformer.applyAsLong(p.val));
                }
                this.result = r;
                CountedCompleter<?> c;
                for (c = this.firstComplete(); c != null; c = c.nextComplete())
                {
                    @SuppressWarnings("unchecked")
                    final MapReduceValuesToLongTask<K, V> t = (MapReduceValuesToLongTask<K, V>) c;
                    @SuppressWarnings("unchecked")
                    MapReduceValuesToLongTask<K, V> s = t.rights;
                    while (s != null)
                    {
                        t.result = reducer.applyAsLong(t.result, s.result);
                        s = t.rights = s.nextRight;
                    }
                }
            }
        }
    }

    @SuppressWarnings({"serial", "ClassHasNoToStringMethod"})
    static final class MapReduceEntriesToLongTask<K, V> extends BulkTask<K, V, Long>
    {
        final ToLongFunction<Map.Entry<K, V>>  transformer;
        final LongBinaryOperator               reducer;
        final long                             basis;
        final MapReduceEntriesToLongTask<K, V> nextRight;
        long                             result;
        MapReduceEntriesToLongTask<K, V> rights;

        MapReduceEntriesToLongTask(final BulkTask<K, V, ?> p, final int b, final int i, final int f, final Node<K, V>[] t, final MapReduceEntriesToLongTask<K, V> nextRight, final ToLongFunction<Map.Entry<K, V>> transformer, final long basis, final LongBinaryOperator reducer)
        {
            super(p, b, i, f, t);
            this.nextRight = nextRight;
            this.transformer = transformer;
            this.basis = basis;
            this.reducer = reducer;
        }

        @Override
        public final Long getRawResult()
        {
            return this.result;
        }

        @Override
        public final void compute()
        {
            final ToLongFunction<Map.Entry<K, V>> transformer;
            final LongBinaryOperator reducer;
            if ((((transformer = this.transformer)) != null) && (((reducer = this.reducer)) != null))
            {
                long r = this.basis;
                for (int i = this.baseIndex, f, h; (this.batch > 0) && (((h = (((f = this.baseLimit)) + i) >>> 1)) > i); )
                {
                    this.addToPendingCount(1);
                    (this.rights = new MapReduceEntriesToLongTask<>(this, this.batch >>>= 1, this.baseLimit = h, f, this.tab, this.rights, transformer, r, reducer)).fork();
                }
                for (Node<K, V> p; (p = this.advance()) != null; )
                {
                    r = reducer.applyAsLong(r, transformer.applyAsLong(p));
                }
                this.result = r;
                CountedCompleter<?> c;
                for (c = this.firstComplete(); c != null; c = c.nextComplete())
                {
                    @SuppressWarnings("unchecked")
                    final MapReduceEntriesToLongTask<K, V> t = (MapReduceEntriesToLongTask<K, V>) c;
                    @SuppressWarnings("unchecked")
                    MapReduceEntriesToLongTask<K, V> s = t.rights;
                    while (s != null)
                    {
                        t.result = reducer.applyAsLong(t.result, s.result);
                        s = t.rights = s.nextRight;
                    }
                }
            }
        }
    }

    @SuppressWarnings({"serial", "ClassHasNoToStringMethod"})
    static final class MapReduceMappingsToLongTask<K, V> extends BulkTask<K, V, Long>
    {
        final ToLongBiFunction<? super K, ? super V> transformer;
        final LongBinaryOperator                     reducer;
        final long                                   basis;
        final MapReduceMappingsToLongTask<K, V>      nextRight;
        long                              result;
        MapReduceMappingsToLongTask<K, V> rights;

        MapReduceMappingsToLongTask(final BulkTask<K, V, ?> p, final int b, final int i, final int f, final Node<K, V>[] t, final MapReduceMappingsToLongTask<K, V> nextRight, final ToLongBiFunction<? super K, ? super V> transformer, final long basis, final LongBinaryOperator reducer)
        {
            super(p, b, i, f, t);
            this.nextRight = nextRight;
            this.transformer = transformer;
            this.basis = basis;
            this.reducer = reducer;
        }

        @Override
        public final Long getRawResult()
        {
            return this.result;
        }

        @Override
        public final void compute()
        {
            final ToLongBiFunction<? super K, ? super V> transformer;
            final LongBinaryOperator reducer;
            if ((((transformer = this.transformer)) != null) && (((reducer = this.reducer)) != null))
            {
                long r = this.basis;
                for (int i = this.baseIndex, f, h; (this.batch > 0) && (((h = (((f = this.baseLimit)) + i) >>> 1)) > i); )
                {
                    this.addToPendingCount(1);
                    (this.rights = new MapReduceMappingsToLongTask<>(this, this.batch >>>= 1, this.baseLimit = h, f, this.tab, this.rights, transformer, r, reducer)).fork();
                }
                for (Node<K, V> p; (p = this.advance()) != null; )
                {
                    r = reducer.applyAsLong(r, transformer.applyAsLong(p.key, p.val));
                }
                this.result = r;
                CountedCompleter<?> c;
                for (c = this.firstComplete(); c != null; c = c.nextComplete())
                {
                    @SuppressWarnings("unchecked")
                    final MapReduceMappingsToLongTask<K, V> t = (MapReduceMappingsToLongTask<K, V>) c;
                    @SuppressWarnings("unchecked")
                    MapReduceMappingsToLongTask<K, V> s = t.rights;
                    while (s != null)
                    {
                        t.result = reducer.applyAsLong(t.result, s.result);
                        s = t.rights = s.nextRight;
                    }
                }
            }
        }
    }

    @SuppressWarnings({"serial", "ClassHasNoToStringMethod"})
    static final class MapReduceKeysToIntTask<K, V> extends BulkTask<K, V, Integer>
    {
        final ToIntFunction<? super K>     transformer;
        final IntBinaryOperator            reducer;
        final int                          basis;
        final MapReduceKeysToIntTask<K, V> nextRight;
        int                          result;
        MapReduceKeysToIntTask<K, V> rights;

        MapReduceKeysToIntTask(final BulkTask<K, V, ?> p, final int b, final int i, final int f, final Node<K, V>[] t, final MapReduceKeysToIntTask<K, V> nextRight, final ToIntFunction<? super K> transformer, final int basis, final IntBinaryOperator reducer)
        {
            super(p, b, i, f, t);
            this.nextRight = nextRight;
            this.transformer = transformer;
            this.basis = basis;
            this.reducer = reducer;
        }

        @Override
        public final Integer getRawResult()
        {
            return this.result;
        }

        @Override
        public final void compute()
        {
            final ToIntFunction<? super K> transformer;
            final IntBinaryOperator reducer;
            if ((((transformer = this.transformer)) != null) && (((reducer = this.reducer)) != null))
            {
                int r = this.basis;
                for (int i = this.baseIndex, f, h; (this.batch > 0) && (((h = (((f = this.baseLimit)) + i) >>> 1)) > i); )
                {
                    this.addToPendingCount(1);
                    (this.rights = new MapReduceKeysToIntTask<>(this, this.batch >>>= 1, this.baseLimit = h, f, this.tab, this.rights, transformer, r, reducer)).fork();
                }
                for (Node<K, V> p; (p = this.advance()) != null; )
                {
                    r = reducer.applyAsInt(r, transformer.applyAsInt(p.key));
                }
                this.result = r;
                CountedCompleter<?> c;
                for (c = this.firstComplete(); c != null; c = c.nextComplete())
                {
                    @SuppressWarnings("unchecked")
                    final MapReduceKeysToIntTask<K, V> t = (MapReduceKeysToIntTask<K, V>) c;
                    @SuppressWarnings("unchecked")
                    MapReduceKeysToIntTask<K, V> s = t.rights;
                    while (s != null)
                    {
                        t.result = reducer.applyAsInt(t.result, s.result);
                        s = t.rights = s.nextRight;
                    }
                }
            }
        }
    }

    @SuppressWarnings({"serial", "ClassHasNoToStringMethod"})
    static final class MapReduceValuesToIntTask<K, V> extends BulkTask<K, V, Integer>
    {
        final ToIntFunction<? super V>       transformer;
        final IntBinaryOperator              reducer;
        final int                            basis;
        final MapReduceValuesToIntTask<K, V> nextRight;
        int                            result;
        MapReduceValuesToIntTask<K, V> rights;

        MapReduceValuesToIntTask(final BulkTask<K, V, ?> p, final int b, final int i, final int f, final Node<K, V>[] t, final MapReduceValuesToIntTask<K, V> nextRight, final ToIntFunction<? super V> transformer, final int basis, final IntBinaryOperator reducer)
        {
            super(p, b, i, f, t);
            this.nextRight = nextRight;
            this.transformer = transformer;
            this.basis = basis;
            this.reducer = reducer;
        }

        @Override
        public final Integer getRawResult()
        {
            return this.result;
        }

        @Override
        public final void compute()
        {
            final ToIntFunction<? super V> transformer;
            final IntBinaryOperator reducer;
            if ((((transformer = this.transformer)) != null) && (((reducer = this.reducer)) != null))
            {
                int r = this.basis;
                for (int i = this.baseIndex, f, h; (this.batch > 0) && (((h = (((f = this.baseLimit)) + i) >>> 1)) > i); )
                {
                    this.addToPendingCount(1);
                    (this.rights = new MapReduceValuesToIntTask<>(this, this.batch >>>= 1, this.baseLimit = h, f, this.tab, this.rights, transformer, r, reducer)).fork();
                }
                for (Node<K, V> p; (p = this.advance()) != null; )
                {
                    r = reducer.applyAsInt(r, transformer.applyAsInt(p.val));
                }
                this.result = r;
                CountedCompleter<?> c;
                for (c = this.firstComplete(); c != null; c = c.nextComplete())
                {
                    @SuppressWarnings("unchecked")
                    final MapReduceValuesToIntTask<K, V> t = (MapReduceValuesToIntTask<K, V>) c;
                    @SuppressWarnings("unchecked")
                    MapReduceValuesToIntTask<K, V> s = t.rights;
                    while (s != null)
                    {
                        t.result = reducer.applyAsInt(t.result, s.result);
                        s = t.rights = s.nextRight;
                    }
                }
            }
        }
    }

    @SuppressWarnings({"serial", "ClassHasNoToStringMethod"})
    static final class MapReduceEntriesToIntTask<K, V> extends BulkTask<K, V, Integer>
    {
        final ToIntFunction<Map.Entry<K, V>>  transformer;
        final IntBinaryOperator               reducer;
        final int                             basis;
        final MapReduceEntriesToIntTask<K, V> nextRight;
        int                             result;
        MapReduceEntriesToIntTask<K, V> rights;

        MapReduceEntriesToIntTask(final BulkTask<K, V, ?> p, final int b, final int i, final int f, final Node<K, V>[] t, final MapReduceEntriesToIntTask<K, V> nextRight, final ToIntFunction<Map.Entry<K, V>> transformer, final int basis, final IntBinaryOperator reducer)
        {
            super(p, b, i, f, t);
            this.nextRight = nextRight;
            this.transformer = transformer;
            this.basis = basis;
            this.reducer = reducer;
        }

        @Override
        public final Integer getRawResult()
        {
            return this.result;
        }

        @Override
        public final void compute()
        {
            final ToIntFunction<Map.Entry<K, V>> transformer;
            final IntBinaryOperator reducer;
            if ((((transformer = this.transformer)) != null) && (((reducer = this.reducer)) != null))
            {
                int r = this.basis;
                for (int i = this.baseIndex, f, h; (this.batch > 0) && (((h = (((f = this.baseLimit)) + i) >>> 1)) > i); )
                {
                    this.addToPendingCount(1);
                    (this.rights = new MapReduceEntriesToIntTask<>(this, this.batch >>>= 1, this.baseLimit = h, f, this.tab, this.rights, transformer, r, reducer)).fork();
                }
                for (Node<K, V> p; (p = this.advance()) != null; )
                {
                    r = reducer.applyAsInt(r, transformer.applyAsInt(p));
                }
                this.result = r;
                CountedCompleter<?> c;
                for (c = this.firstComplete(); c != null; c = c.nextComplete())
                {
                    @SuppressWarnings("unchecked")
                    final MapReduceEntriesToIntTask<K, V> t = (MapReduceEntriesToIntTask<K, V>) c;
                    @SuppressWarnings("unchecked")
                    MapReduceEntriesToIntTask<K, V> s = t.rights;
                    while (s != null)
                    {
                        t.result = reducer.applyAsInt(t.result, s.result);
                        s = t.rights = s.nextRight;
                    }
                }
            }
        }
    }

    @SuppressWarnings({"serial", "ClassHasNoToStringMethod"})
    static final class MapReduceMappingsToIntTask<K, V> extends BulkTask<K, V, Integer>
    {
        final ToIntBiFunction<? super K, ? super V> transformer;
        final IntBinaryOperator                     reducer;
        final int                                   basis;
        final MapReduceMappingsToIntTask<K, V>      nextRight;
        int                              result;
        MapReduceMappingsToIntTask<K, V> rights;

        MapReduceMappingsToIntTask(final BulkTask<K, V, ?> p, final int b, final int i, final int f, final Node<K, V>[] t, final MapReduceMappingsToIntTask<K, V> nextRight, final ToIntBiFunction<? super K, ? super V> transformer, final int basis, final IntBinaryOperator reducer)
        {
            super(p, b, i, f, t);
            this.nextRight = nextRight;
            this.transformer = transformer;
            this.basis = basis;
            this.reducer = reducer;
        }

        @Override
        public final Integer getRawResult()
        {
            return this.result;
        }

        @Override
        public final void compute()
        {
            final ToIntBiFunction<? super K, ? super V> transformer;
            final IntBinaryOperator reducer;
            if ((((transformer = this.transformer)) != null) && (((reducer = this.reducer)) != null))
            {
                int r = this.basis;
                for (int i = this.baseIndex, f, h; (this.batch > 0) && (((h = (((f = this.baseLimit)) + i) >>> 1)) > i); )
                {
                    this.addToPendingCount(1);
                    (this.rights = new MapReduceMappingsToIntTask<>(this, this.batch >>>= 1, this.baseLimit = h, f, this.tab, this.rights, transformer, r, reducer)).fork();
                }
                for (Node<K, V> p; (p = this.advance()) != null; )
                {
                    r = reducer.applyAsInt(r, transformer.applyAsInt(p.key, p.val));
                }
                this.result = r;
                CountedCompleter<?> c;
                for (c = this.firstComplete(); c != null; c = c.nextComplete())
                {
                    @SuppressWarnings("unchecked")
                    final MapReduceMappingsToIntTask<K, V> t = (MapReduceMappingsToIntTask<K, V>) c;
                    @SuppressWarnings("unchecked")
                    MapReduceMappingsToIntTask<K, V> s = t.rights;
                    while (s != null)
                    {
                        t.result = reducer.applyAsInt(t.result, s.result);
                        s = t.rights = s.nextRight;
                    }
                }
            }
        }
    }

    @SuppressWarnings({"StatementWithEmptyBody", "MagicNumber", "ClassHasNoToStringMethod"})
    public static class DioriteThreadLocalRandom extends Random
    {
    /*
     * This class implements the java.util.Random API (and subclasses
     * Random) using a single static instance that accesses random
     * number state held in class Thread (primarily, field
     * DioriteThreadLocalRandomSeed). In doing so, it also provides a home
     * for managing package-private utilities that rely on exactly the
     * same state as needed to maintain the DioriteThreadLocalRandom
     * instances. We leverage the need for an initialization flag
     * field to also use it as a "probe" -- a self-adjusting thread
     * hash used for contention avoidance, as well as a secondary
     * simpler (xorShift) random seed that is conservatively used to
     * avoid otherwise surprising users by hijacking the
     * DioriteThreadLocalRandom sequence.  The dual use is a marriage of
     * convenience, but is a simple and efficient way of reducing
     * application-level overhead and footprint of most concurrent
     * programs.
     *
     * Even though this class subclasses java.util.Random, it uses the
     * same basic algorithm as java.util.SplittableRandom.  (See its
     * internal documentation for explanations, which are not repeated
     * here.)  Because DioriteThreadLocalRandoms are not splittable
     * though, we use only a single 64bit gamma.
     *
     * Because this class is in a different package than class Thread,
     * field access methods use Unsafe to bypass access control rules.
     * To conform to the requirements of the Random superclass
     * constructor, the common static DioriteThreadLocalRandom maintains an
     * "initialized" field for the sake of rejecting user calls to
     * setSeed while still allowing a call from constructor.  Note
     * that serialization is completely unnecessary because there is
     * only a static singleton.  But we generate a serial form
     * containing "rnd" and "initialized" fields to ensure
     * compatibility across versions.
     *
     * Implementations of non-core methods are mostly the same as in
     * SplittableRandom, that were in part derived from a previous
     * version of this class.
     *
     * The nextLocalGaussian ThreadLocal supports the very rarely used
     * nextGaussian method by providing a holder for the second of a
     * pair of them. As is true for the base class version of this
     * method, this time/space tradeoff is probably never worthwhile,
     * but we provide identical statistical properties.
     */

        /**
         * The common DioriteThreadLocalRandom
         */
        static final         DioriteThreadLocalRandom instance               = new DioriteThreadLocalRandom();
        // IllegalArgumentException messages
        static final         String                   badBound               = "bound must be positive";
        static final         String                   badRange               = "bound must be greater than origin";
        static final         String                   badSize                = "size must be non-negative";
        /**
         * Generates per-thread initialization/probe field
         */
        private static final AtomicInteger            probeGenerator         = new AtomicInteger();
        /**
         * The next seed for default constructors.
         */
        private static final AtomicLong               seeder                 = new AtomicLong(initialSeed());
        /**
         * The seed increment
         */
        private static final long                     GAMMA                  = 0x9e3779b97f4a7c15L;
        /**
         * The increment for generating probe values
         */
        private static final int                      PROBE_INCREMENT        = 0x9e3779b9;
        /**
         * The increment of seeder per new instance
         */
        private static final long                     SEEDER_INCREMENT       = 0xbb67ae8584caa73bL;
        // Constants from SplittableRandom
        private static final double                   DOUBLE_UNIT            = 0x1.0p-53;  // 1.0  / (1L << 53)
        private static final float                    FLOAT_UNIT             = 0x1.0p-24f; // 1.0f / (1 << 24)
        /**
         * Rarely-used holder for the second of a pair of Gaussians
         */
        private static final ThreadLocal<Double>      nextLocalGaussian      = new ThreadLocal<>();
        private static final long                     serialVersionUID       = 0;
        /**
         * @serialField rnd long
         * seed for random computations
         * @serialField initialized boolean
         * always true
         */
        private static final ObjectStreamField[]      serialPersistentFields = {new ObjectStreamField("rnd", long.class), new ObjectStreamField("initialized", boolean.class),};
        // Unsafe mechanics
        private static final sun.misc.Unsafe UNSAFE;
        private static final long            SEED;
        private static final long            PROBE;
        private static final long            SECONDARY;
        /**
         * Field used only during singleton initialization.
         * True when constructor completes.
         */
        final                boolean         initialized;

        /**
         * Constructor used only for static singleton
         */
        private DioriteThreadLocalRandom()
        {
            this.initialized = true; // false during super() call
        }

        /**
         * Throws {@code UnsupportedOperationException}.  Setting seeds in
         * this generator is not supported.
         *
         * @throws UnsupportedOperationException always
         */
        @Override
        public void setSeed(final long seed)
        {
            // only allow call from super() constructor
            if (this.initialized)
            {
                throw new UnsupportedOperationException();
            }
        }

        // We must define this, but never use it.
        @Override
        public int next(final int bits)
        {
            return (int) (mix64(this.nextSeed()) >>> (64 - bits));
        }

        /**
         * Returns a pseudorandom {@code int} value.
         *
         * @return a pseudorandom {@code int} value
         */
        @Override
        public int nextInt()
        {
            return mix32(this.nextSeed());
        }

        /**
         * Returns a pseudorandom {@code int} value between zero (inclusive)
         * and the specified bound (exclusive).
         *
         * @param bound the upper bound (exclusive).  Must be positive.
         *
         * @return a pseudorandom {@code int} value between zero
         * (inclusive) and the bound (exclusive)
         *
         * @throws IllegalArgumentException if {@code bound} is not positive
         */
        @Override
        public int nextInt(final int bound)
        {
            if (bound <= 0)
            {
                throw new IllegalArgumentException(badBound);
            }
            int r = mix32(this.nextSeed());
            final int m = bound - 1;
            if ((bound & m) == 0) // power of two
            {
                r &= m;
            }
            else
            { // reject over-represented candidates
                for (int u = r >>> 1; ((u + m) - ((r = u % bound))) < 0; u = mix32(this.nextSeed()) >>> 1)
                {
                }
            }
            return r;
        }

        /**
         * Returns a pseudorandom {@code long} value.
         *
         * @return a pseudorandom {@code long} value
         */
        @Override
        public long nextLong()
        {
            return mix64(this.nextSeed());
        }

        /**
         * Returns a pseudorandom {@code boolean} value.
         *
         * @return a pseudorandom {@code boolean} value
         */
        @Override
        public boolean nextBoolean()
        {
            return mix32(this.nextSeed()) < 0;
        }

        /**
         * Returns a pseudorandom {@code float} value between zero
         * (inclusive) and one (exclusive).
         *
         * @return a pseudorandom {@code float} value between zero
         * (inclusive) and one (exclusive)
         */
        @Override
        public float nextFloat()
        {
            return (mix32(this.nextSeed()) >>> 8) * FLOAT_UNIT;
        }

        /**
         * Returns a pseudorandom {@code double} value between zero
         * (inclusive) and one (exclusive).
         *
         * @return a pseudorandom {@code double} value between zero
         * (inclusive) and one (exclusive)
         */
        @Override
        public double nextDouble()
        {
            return (mix64(this.nextSeed()) >>> 11) * DOUBLE_UNIT;
        }

        @Override
        public double nextGaussian()
        {
            // Use nextLocalGaussian instead of nextGaussian field
            final Double d = nextLocalGaussian.get();
            if (d != null)
            {
                nextLocalGaussian.set(null);
                return d;
            }
            double v1, v2, s;
            do
            {
                v1 = (2 * this.nextDouble()) - 1; // between -1 and 1
                v2 = (2 * this.nextDouble()) - 1; // between -1 and 1
                s = (v1 * v1) + (v2 * v2);
            } while ((s >= 1) || (s == 0));
            final double multiplier = StrictMath.sqrt((- 2 * StrictMath.log(s)) / s);
            nextLocalGaussian.set(v2 * multiplier);
            return v1 * multiplier;
        }

        /**
         * Returns a stream producing the given {@code streamSize} number of
         * pseudorandom {@code int} values.
         *
         * @param streamSize the number of values to generate
         *
         * @return a stream of pseudorandom {@code int} values
         *
         * @throws IllegalArgumentException if {@code streamSize} is
         *                                  less than zero
         * @since 1.8
         */
        @Override
        public IntStream ints(final long streamSize)
        {
            if (streamSize < 0L)
            {
                throw new IllegalArgumentException(badSize);
            }
            return StreamSupport.intStream(new RandomIntsSpliterator(0L, streamSize, Integer.MAX_VALUE, 0), false);
        }

        /**
         * Returns an effectively unlimited stream of pseudorandom {@code int}
         * values.
         *
         * @return a stream of pseudorandom {@code int} values
         * <br>
         * This method is implemented to be equivalent to {@code
         * ints(Long.MAX_VALUE)}.
         *
         * @since 1.8
         */
        @Override
        public IntStream ints()
        {
            return StreamSupport.intStream(new RandomIntsSpliterator(0L, Long.MAX_VALUE, Integer.MAX_VALUE, 0), false);
        }

        /**
         * Returns a stream producing the given {@code streamSize} number
         * of pseudorandom {@code int} values, each conforming to the given
         * origin (inclusive) and bound (exclusive).
         *
         * @param streamSize         the number of values to generate
         * @param randomNumberOrigin the origin (inclusive) of each random value
         * @param randomNumberBound  the bound (exclusive) of each random value
         *
         * @return a stream of pseudorandom {@code int} values,
         * each with the given origin (inclusive) and bound (exclusive)
         *
         * @throws IllegalArgumentException if {@code streamSize} is
         *                                  less than zero, or {@code randomNumberOrigin}
         *                                  is greater than or equal to {@code randomNumberBound}
         * @since 1.8
         */
        @Override
        public IntStream ints(final long streamSize, final int randomNumberOrigin, final int randomNumberBound)
        {
            if (streamSize < 0L)
            {
                throw new IllegalArgumentException(badSize);
            }
            if (randomNumberOrigin >= randomNumberBound)
            {
                throw new IllegalArgumentException(badRange);
            }
            return StreamSupport.intStream(new RandomIntsSpliterator(0L, streamSize, randomNumberOrigin, randomNumberBound), false);
        }

        /**
         * Returns an effectively unlimited stream of pseudorandom {@code
         * int} values, each conforming to the given origin (inclusive) and bound
         * (exclusive).
         *
         * @param randomNumberOrigin the origin (inclusive) of each random value
         * @param randomNumberBound  the bound (exclusive) of each random value
         *
         * @return a stream of pseudorandom {@code int} values,
         * each with the given origin (inclusive) and bound (exclusive)
         *
         * @throws IllegalArgumentException if {@code randomNumberOrigin}
         *                                  is greater than or equal to {@code randomNumberBound}
         *                                  This method is implemented to be equivalent to {@code
         *                                  ints(Long.MAX_VALUE, randomNumberOrigin, randomNumberBound)}.
         * @since 1.8
         */
        @Override
        public IntStream ints(final int randomNumberOrigin, final int randomNumberBound)
        {
            if (randomNumberOrigin >= randomNumberBound)
            {
                throw new IllegalArgumentException(badRange);
            }
            return StreamSupport.intStream(new RandomIntsSpliterator(0L, Long.MAX_VALUE, randomNumberOrigin, randomNumberBound), false);
        }

        /**
         * Returns a stream producing the given {@code streamSize} number of
         * pseudorandom {@code long} values.
         *
         * @param streamSize the number of values to generate
         *
         * @return a stream of pseudorandom {@code long} values
         *
         * @throws IllegalArgumentException if {@code streamSize} is
         *                                  less than zero
         * @since 1.8
         */
        @Override
        public LongStream longs(final long streamSize)
        {
            if (streamSize < 0L)
            {
                throw new IllegalArgumentException(badSize);
            }
            return StreamSupport.longStream(new RandomLongsSpliterator(0L, streamSize, Long.MAX_VALUE, 0L), false);
        }

        /**
         * Returns an effectively unlimited stream of pseudorandom {@code long}
         * values.
         *
         * @return a stream of pseudorandom {@code long} values
         * <br>
         * This method is implemented to be equivalent to {@code
         * longs(Long.MAX_VALUE)}.
         *
         * @since 1.8
         */
        @Override
        public LongStream longs()
        {
            return StreamSupport.longStream(new RandomLongsSpliterator(0L, Long.MAX_VALUE, Long.MAX_VALUE, 0L), false);
        }

        /**
         * Returns a stream producing the given {@code streamSize} number of
         * pseudorandom {@code long}, each conforming to the given origin
         * (inclusive) and bound (exclusive).
         *
         * @param streamSize         the number of values to generate
         * @param randomNumberOrigin the origin (inclusive) of each random value
         * @param randomNumberBound  the bound (exclusive) of each random value
         *
         * @return a stream of pseudorandom {@code long} values,
         * each with the given origin (inclusive) and bound (exclusive)
         *
         * @throws IllegalArgumentException if {@code streamSize} is
         *                                  less than zero, or {@code randomNumberOrigin}
         *                                  is greater than or equal to {@code randomNumberBound}
         * @since 1.8
         */
        @Override
        public LongStream longs(final long streamSize, final long randomNumberOrigin, final long randomNumberBound)
        {
            if (streamSize < 0L)
            {
                throw new IllegalArgumentException(badSize);
            }
            if (randomNumberOrigin >= randomNumberBound)
            {
                throw new IllegalArgumentException(badRange);
            }
            return StreamSupport.longStream(new RandomLongsSpliterator(0L, streamSize, randomNumberOrigin, randomNumberBound), false);
        }

        /**
         * Returns an effectively unlimited stream of pseudorandom {@code
         * long} values, each conforming to the given origin (inclusive) and bound
         * (exclusive).
         *
         * @param randomNumberOrigin the origin (inclusive) of each random value
         * @param randomNumberBound  the bound (exclusive) of each random value
         *
         * @return a stream of pseudorandom {@code long} values,
         * each with the given origin (inclusive) and bound (exclusive)
         *
         * @throws IllegalArgumentException if {@code randomNumberOrigin}
         *                                  is greater than or equal to {@code randomNumberBound}
         *                                  This method is implemented to be equivalent to {@code
         *                                  longs(Long.MAX_VALUE, randomNumberOrigin, randomNumberBound)}.
         * @since 1.8
         */
        @Override
        public LongStream longs(final long randomNumberOrigin, final long randomNumberBound)
        {
            if (randomNumberOrigin >= randomNumberBound)
            {
                throw new IllegalArgumentException(badRange);
            }
            return StreamSupport.longStream(new RandomLongsSpliterator(0L, Long.MAX_VALUE, randomNumberOrigin, randomNumberBound), false);
        }

        // stream methods, coded in a way intended to better isolate for
        // maintenance purposes the small differences across forms.

        /**
         * Returns a stream producing the given {@code streamSize} number of
         * pseudorandom {@code double} values, each between zero
         * (inclusive) and one (exclusive).
         *
         * @param streamSize the number of values to generate
         *
         * @return a stream of {@code double} values
         *
         * @throws IllegalArgumentException if {@code streamSize} is
         *                                  less than zero
         * @since 1.8
         */
        @Override
        public DoubleStream doubles(final long streamSize)
        {
            if (streamSize < 0L)
            {
                throw new IllegalArgumentException(badSize);
            }
            return StreamSupport.doubleStream(new RandomDoublesSpliterator(0L, streamSize, Double.MAX_VALUE, 0.0), false);
        }

        /**
         * Returns an effectively unlimited stream of pseudorandom {@code
         * double} values, each between zero (inclusive) and one
         * (exclusive).
         *
         * @return a stream of pseudorandom {@code double} values
         * <br>
         * This method is implemented to be equivalent to {@code
         * doubles(Long.MAX_VALUE)}.
         *
         * @since 1.8
         */
        @Override
        public DoubleStream doubles()
        {
            return StreamSupport.doubleStream(new RandomDoublesSpliterator(0L, Long.MAX_VALUE, Double.MAX_VALUE, 0.0), false);
        }

        /**
         * Returns a stream producing the given {@code streamSize} number of
         * pseudorandom {@code double} values, each conforming to the given origin
         * (inclusive) and bound (exclusive).
         *
         * @param streamSize         the number of values to generate
         * @param randomNumberOrigin the origin (inclusive) of each random value
         * @param randomNumberBound  the bound (exclusive) of each random value
         *
         * @return a stream of pseudorandom {@code double} values,
         * each with the given origin (inclusive) and bound (exclusive)
         *
         * @throws IllegalArgumentException if {@code streamSize} is
         *                                  less than zero
         * @throws IllegalArgumentException if {@code randomNumberOrigin}
         *                                  is greater than or equal to {@code randomNumberBound}
         * @since 1.8
         */
        @Override
        public DoubleStream doubles(final long streamSize, final double randomNumberOrigin, final double randomNumberBound)
        {
            if (streamSize < 0L)
            {
                throw new IllegalArgumentException(badSize);
            }
            if (! (randomNumberOrigin < randomNumberBound))
            {
                throw new IllegalArgumentException(badRange);
            }
            return StreamSupport.doubleStream(new RandomDoublesSpliterator(0L, streamSize, randomNumberOrigin, randomNumberBound), false);
        }

        /**
         * Returns an effectively unlimited stream of pseudorandom {@code
         * double} values, each conforming to the given origin (inclusive) and bound
         * (exclusive).
         *
         * @param randomNumberOrigin the origin (inclusive) of each random value
         * @param randomNumberBound  the bound (exclusive) of each random value
         *
         * @return a stream of pseudorandom {@code double} values,
         * each with the given origin (inclusive) and bound (exclusive)
         *
         * @throws IllegalArgumentException if {@code randomNumberOrigin}
         *                                  is greater than or equal to {@code randomNumberBound}
         *                                  This method is implemented to be equivalent to {@code
         *                                  doubles(Long.MAX_VALUE, randomNumberOrigin, randomNumberBound)}.
         * @since 1.8
         */
        @Override
        public DoubleStream doubles(final double randomNumberOrigin, final double randomNumberBound)
        {
            if (! (randomNumberOrigin < randomNumberBound))
            {
                throw new IllegalArgumentException(badRange);
            }
            return StreamSupport.doubleStream(new RandomDoublesSpliterator(0L, Long.MAX_VALUE, randomNumberOrigin, randomNumberBound), false);
        }

        public long nextSeed()
        {
            final Thread t;
            final long r; // read and update per-thread seed
            UNSAFE.putLong(t = Thread.currentThread(), SEED, r = UNSAFE.getLong(t, SEED) + GAMMA);
            return r;
        }

        /**
         * The form of nextLong used by LongStream Spliterators.  If
         * origin is greater than bound, acts as unbounded form of
         * nextLong, else as bounded form.
         *
         * @param origin the least value, unless greater than bound
         * @param bound  the upper bound (exclusive), must not equal origin
         *
         * @return a pseudorandom value
         */
        public long internalNextLong(final long origin, final long bound)
        {
            long r = mix64(this.nextSeed());
            if (origin < bound)
            {
                final long n = bound - origin;
                final long m = n - 1;
                if ((n & m) == 0L)  // power of two
                {
                    r = (r & m) + origin;
                }
                else if (n > 0L)
                {  // reject over-represented candidates
                    for (long u = r >>> 1;            // ensure nonnegative
                         ((u + m) - ((r = u % n))) < 0L;    // rejection check
                         u = mix64(this.nextSeed()) >>> 1) // retry
                    {
                    }
                    r += origin;
                }
                else
                {              // range not representable as long
                    while ((r < origin) || (r >= bound))
                    {
                        r = mix64(this.nextSeed());
                    }
                }
            }
            return r;
        }

        /**
         * The form of nextInt used by IntStream Spliterators.
         * Exactly the same as long version, except for types.
         *
         * @param origin the least value, unless greater than bound
         * @param bound  the upper bound (exclusive), must not equal origin
         *
         * @return a pseudorandom value
         */
        public int internalNextInt(final int origin, final int bound)
        {
            int r = mix32(this.nextSeed());
            if (origin < bound)
            {
                final int n = bound - origin;
                final int m = n - 1;
                if ((n & m) == 0)
                {
                    r = (r & m) + origin;
                }
                else if (n > 0)
                {
                    for (int u = r >>> 1; ((u + m) - ((r = u % n))) < 0; u = mix32(this.nextSeed()) >>> 1)
                    {
                    }
                    r += origin;
                }
                else
                {
                    while ((r < origin) || (r >= bound))
                    {
                        r = mix32(this.nextSeed());
                    }
                }
            }
            return r;
        }

        /**
         * The form of nextDouble used by DoubleStream Spliterators.
         *
         * @param origin the least value, unless greater than bound
         * @param bound  the upper bound (exclusive), must not equal origin
         *
         * @return a pseudorandom value
         */
        public double internalNextDouble(final double origin, final double bound)
        {
            double r = (this.nextLong() >>> 11) * DOUBLE_UNIT;
            if (origin < bound)
            {
                r = (r * (bound - origin)) + origin;
                if (r >= bound) // correct for rounding
                {
                    r = Double.longBitsToDouble(Double.doubleToLongBits(bound) - 1);
                }
            }
            return r;
        }

        /**
         * Returns a pseudorandom {@code int} value between the specified
         * origin (inclusive) and the specified bound (exclusive).
         *
         * @param origin the least value returned
         * @param bound  the upper bound (exclusive)
         *
         * @return a pseudorandom {@code int} value between the origin
         * (inclusive) and the bound (exclusive)
         *
         * @throws IllegalArgumentException if {@code origin} is greater than
         *                                  or equal to {@code bound}
         */
        public int nextInt(final int origin, final int bound)
        {
            if (origin >= bound)
            {
                throw new IllegalArgumentException(badRange);
            }
            return this.internalNextInt(origin, bound);
        }

        /**
         * Returns a pseudorandom {@code long} value between zero (inclusive)
         * and the specified bound (exclusive).
         *
         * @param bound the upper bound (exclusive).  Must be positive.
         *
         * @return a pseudorandom {@code long} value between zero
         * (inclusive) and the bound (exclusive)
         *
         * @throws IllegalArgumentException if {@code bound} is not positive
         */
        public long nextLong(final long bound)
        {
            if (bound <= 0)
            {
                throw new IllegalArgumentException(badBound);
            }
            long r = mix64(this.nextSeed());
            final long m = bound - 1;
            if ((bound & m) == 0L) // power of two
            {
                r &= m;
            }
            else
            { // reject over-represented candidates
                for (long u = r >>> 1; ((u + m) - ((r = u % bound))) < 0L; u = mix64(this.nextSeed()) >>> 1)
                {
                }
            }
            return r;
        }

        /**
         * Returns a pseudorandom {@code long} value between the specified
         * origin (inclusive) and the specified bound (exclusive).
         *
         * @param origin the least value returned
         * @param bound  the upper bound (exclusive)
         *
         * @return a pseudorandom {@code long} value between the origin
         * (inclusive) and the bound (exclusive)
         *
         * @throws IllegalArgumentException if {@code origin} is greater than
         *                                  or equal to {@code bound}
         */
        public long nextLong(final long origin, final long bound)
        {
            if (origin >= bound)
            {
                throw new IllegalArgumentException(badRange);
            }
            return this.internalNextLong(origin, bound);
        }

        /**
         * Returns a pseudorandom {@code double} value between 0.0
         * (inclusive) and the specified bound (exclusive).
         *
         * @param bound the upper bound (exclusive).  Must be positive.
         *
         * @return a pseudorandom {@code double} value between zero
         * (inclusive) and the bound (exclusive)
         *
         * @throws IllegalArgumentException if {@code bound} is not positive
         */
        public double nextDouble(final double bound)
        {
            if (! (bound > 0.0))
            {
                throw new IllegalArgumentException(badBound);
            }
            final double result = (mix64(this.nextSeed()) >>> 11) * DOUBLE_UNIT * bound;
            return (result < bound) ? result : // correct for rounding
                           Double.longBitsToDouble(Double.doubleToLongBits(bound) - 1);
        }

        /**
         * Returns a pseudorandom {@code double} value between the specified
         * origin (inclusive) and bound (exclusive).
         *
         * @param origin the least value returned
         * @param bound  the upper bound (exclusive)
         *
         * @return a pseudorandom {@code double} value between the origin
         * (inclusive) and the bound (exclusive)
         *
         * @throws IllegalArgumentException if {@code origin} is greater than
         *                                  or equal to {@code bound}
         */
        public double nextDouble(final double origin, final double bound)
        {
            if (! (origin < bound))
            {
                throw new IllegalArgumentException(badRange);
            }
            return this.internalNextDouble(origin, bound);
        }

        /**
         * Saves the {@code DioriteThreadLocalRandom} to a stream (that is, serializes it).
         *
         * @param s the stream
         *
         * @throws java.io.IOException if an I/O error occurs
         */
        private void writeObject(final java.io.ObjectOutputStream s) throws java.io.IOException
        {

            final java.io.ObjectOutputStream.PutField fields = s.putFields();
            fields.put("rnd", UNSAFE.getLong(Thread.currentThread(), SEED));
            fields.put("initialized", true);
            s.writeFields();
        }

        /**
         * Returns the {@link #current() current} thread's {@code DioriteThreadLocalRandom}.
         *
         * @return the {@link #current() current} thread's {@code DioriteThreadLocalRandom}
         */
        public Object readResolve()
        {
            return current();
        }


        // Within-package utilities

    /*
     * Descriptions of the usages of the methods below can be found in
     * the classes that use them. Briefly, a thread's "probe" value is
     * a non-zero hash code that (probably) does not collide with
     * other existing threads with respect to any power of two
     * collision space. When it does collide, it is pseudo-randomly
     * adjusted (using a Marsaglia XorShift). The nextSecondarySeed
     * method is used in the same contexts as DioriteThreadLocalRandom, but
     * only for transient usages such as random adaptive spin/block
     * sequences for which a cheap RNG suffices and for which it could
     * in principle disrupt user-visible statistical properties of the
     * main DioriteThreadLocalRandom if we were to use it.
     *
     * Note: Because of package-protection issues, versions of some
     * these methods also appear in some subpackage classes.
     */

        public static long initialSeed()
        {
            final String pp = java.security.AccessController.doPrivileged(new sun.security.action.GetPropertyAction("java.util.secureRandomSeed"));
            if ((pp != null) && pp.equalsIgnoreCase("true"))
            {
                final byte[] seedBytes = java.security.SecureRandom.getSeed(8);
                long s = (long) (seedBytes[0]) & 0xffL;
                for (int i = 1; i < 8; ++ i)
                {
                    s = (s << 8) | ((long) (seedBytes[i]) & 0xffL);
                }
                return s;
            }
            return (mix64(System.currentTimeMillis()) ^ mix64(System.nanoTime()));
        }

        public static long mix64(long z)
        {
            z = (z ^ (z >>> 33)) * 0xff51afd7ed558ccdL;
            z = (z ^ (z >>> 33)) * 0xc4ceb9fe1a85ec53L;
            return z ^ (z >>> 33);
        }

        public static int mix32(long z)
        {
            z = (z ^ (z >>> 33)) * 0xff51afd7ed558ccdL;
            return (int) (((z ^ (z >>> 33)) * 0xc4ceb9fe1a85ec53L) >>> 32);
        }

        // Serialization support

        /**
         * Initialize Thread fields for the current thread.  Called only
         * when Thread.DioriteThreadLocalRandomProbe is zero, indicating that a
         * thread local seed value needs to be generated. Note that even
         * though the initialization is purely thread-local, we need to
         * rely on (static) atomic generators to initialize the values.
         */
        public static void localInit()
        {
            final int p = probeGenerator.addAndGet(PROBE_INCREMENT);
            final int probe = (p == 0) ? 1 : p; // skip 0
            final long seed = mix64(seeder.getAndAdd(SEEDER_INCREMENT));
            final Thread t = Thread.currentThread();
            UNSAFE.putLong(t, SEED, seed);
            UNSAFE.putInt(t, PROBE, probe);
        }

        /**
         * Returns the current thread's {@code DioriteThreadLocalRandom}.
         *
         * @return the current thread's {@code DioriteThreadLocalRandom}
         */
        public static DioriteThreadLocalRandom current()
        {
            if (UNSAFE.getInt(Thread.currentThread(), PROBE) == 0)
            {
                localInit();
            }
            return instance;
        }

        /**
         * Returns the probe value for the current thread without forcing
         * initialization. Note that invoking DioriteThreadLocalRandom.current()
         * can be used to force initialization on zero return.
         *
         * @return probe value for the current thread without forcing
         * initialization.
         */
        public static int getProbe()
        {
            return UNSAFE.getInt(Thread.currentThread(), PROBE);
        }

        /**
         * Pseudo-randomly advances and records the given probe value for the
         * given thread.
         *
         * @param probe probe value to advance.
         *
         * @return advanced probe value.
         */
        public static int advanceProbe(int probe)
        {
            probe ^= probe << 13;   // xorshift
            probe ^= probe >>> 17;
            probe ^= probe << 5;
            UNSAFE.putInt(Thread.currentThread(), PROBE, probe);
            return probe;
        }

        /**
         * Returns the pseudo-randomly initialized or updated secondary seed.
         *
         * @return the pseudo-randomly initialized or updated secondary seed.
         */
        public static int nextSecondarySeed()
        {
            int r;
            final Thread t = Thread.currentThread();
            if ((r = UNSAFE.getInt(t, SECONDARY)) != 0)
            {
                r ^= r << 13;   // xorshift
                r ^= r >>> 17;
                r ^= r << 5;
            }
            else
            {
                localInit();
                if ((r = (int) UNSAFE.getLong(t, SEED)) == 0)
                {
                    r = 1; // avoid zero
                }
            }
            UNSAFE.putInt(t, SECONDARY, r);
            return r;
        }

        /**
         * Spliterator for int streams.  We multiplex the four int
         * versions into one class by treating a bound less than origin as
         * unbounded, and also by treating "infinite" as equivalent to
         * Long.MAX_VALUE. For splits, it uses the standard divide-by-two
         * approach. The long and double versions of this class are
         * identical except for types.
         */
        @SuppressWarnings("ClassHasNoToStringMethod")
        static final class RandomIntsSpliterator implements Spliterator.OfInt
        {
            final long fence;
            final int  origin;
            final int  bound;
            long index;

            RandomIntsSpliterator(final long index, final long fence, final int origin, final int bound)
            {
                this.index = index;
                this.fence = fence;
                this.origin = origin;
                this.bound = bound;
            }

            @Override
            public RandomIntsSpliterator trySplit()
            {
                final long i = this.index;
                final long m = (i + this.fence) >>> 1;
                return (m <= i) ? null : new RandomIntsSpliterator(i, this.index = m, this.origin, this.bound);
            }

            @Override
            public boolean tryAdvance(final IntConsumer consumer)
            {
                if (consumer == null)
                {
                    throw new NullPointerException();
                }
                final long i = this.index;
                final long f = this.fence;
                if (i < f)
                {
                    consumer.accept(DioriteThreadLocalRandom.current().internalNextInt(this.origin, this.bound));
                    this.index = i + 1;
                    return true;
                }
                return false;
            }

            @Override
            public void forEachRemaining(final IntConsumer consumer)
            {
                if (consumer == null)
                {
                    throw new NullPointerException();
                }
                long i = this.index;
                final long f = this.fence;
                if (i < f)
                {
                    this.index = f;
                    final int o = this.origin;
                    final int b = this.bound;
                    final DioriteThreadLocalRandom rng = DioriteThreadLocalRandom.current();
                    do
                    {
                        consumer.accept(rng.internalNextInt(o, b));
                    } while (++ i < f);
                }
            }

            @Override
            public int characteristics()
            {
                return (Spliterator.SIZED | Spliterator.SUBSIZED |
                                Spliterator.NONNULL | Spliterator.IMMUTABLE);
            }

            @Override
            public long estimateSize()
            {
                return this.fence - this.index;
            }


        }

        /**
         * Spliterator for long streams.
         */
        @SuppressWarnings("ClassHasNoToStringMethod")
        static final class RandomLongsSpliterator implements Spliterator.OfLong
        {
            final long fence;
            final long origin;
            final long bound;
            long index;

            RandomLongsSpliterator(final long index, final long fence, final long origin, final long bound)
            {
                this.index = index;
                this.fence = fence;
                this.origin = origin;
                this.bound = bound;
            }

            @Override
            public RandomLongsSpliterator trySplit()
            {
                final long i = this.index;
                final long m = (i + this.fence) >>> 1;
                return (m <= i) ? null : new RandomLongsSpliterator(i, this.index = m, this.origin, this.bound);
            }

            @Override
            public boolean tryAdvance(final LongConsumer consumer)
            {
                if (consumer == null)
                {
                    throw new NullPointerException();
                }
                final long i = this.index;
                final long f = this.fence;
                if (i < f)
                {
                    consumer.accept(DioriteThreadLocalRandom.current().internalNextLong(this.origin, this.bound));
                    this.index = i + 1;
                    return true;
                }
                return false;
            }

            @Override
            public void forEachRemaining(final LongConsumer consumer)
            {
                if (consumer == null)
                {
                    throw new NullPointerException();
                }
                long i = this.index;
                final long f = this.fence;
                if (i < f)
                {
                    this.index = f;
                    final long o = this.origin;
                    final long b = this.bound;
                    final DioriteThreadLocalRandom rng = DioriteThreadLocalRandom.current();
                    do
                    {
                        consumer.accept(rng.internalNextLong(o, b));
                    } while (++ i < f);
                }
            }

            @Override
            public long estimateSize()
            {
                return this.fence - this.index;
            }


            @Override
            public int characteristics()
            {
                return (Spliterator.SIZED | Spliterator.SUBSIZED |
                                Spliterator.NONNULL | Spliterator.IMMUTABLE);
            }


        }

        /**
         * Spliterator for double streams.
         */
        @SuppressWarnings("ClassHasNoToStringMethod")
        static final class RandomDoublesSpliterator implements Spliterator.OfDouble
        {
            final long   fence;
            final double origin;
            final double bound;
            long index;

            RandomDoublesSpliterator(final long index, final long fence, final double origin, final double bound)
            {
                this.index = index;
                this.fence = fence;
                this.origin = origin;
                this.bound = bound;
            }

            @Override
            public RandomDoublesSpliterator trySplit()
            {
                final long i = this.index;
                final long m = (i + this.fence) >>> 1;
                return (m <= i) ? null : new RandomDoublesSpliterator(i, this.index = m, this.origin, this.bound);
            }

            @Override
            public boolean tryAdvance(final DoubleConsumer consumer)
            {
                if (consumer == null)
                {
                    throw new NullPointerException();
                }
                final long i = this.index;
                final long f = this.fence;
                if (i < f)
                {
                    consumer.accept(DioriteThreadLocalRandom.current().internalNextDouble(this.origin, this.bound));
                    this.index = i + 1;
                    return true;
                }
                return false;
            }

            @Override
            public void forEachRemaining(final DoubleConsumer consumer)
            {
                if (consumer == null)
                {
                    throw new NullPointerException();
                }
                long i = this.index;
                final long f = this.fence;
                if (i < f)
                {
                    this.index = f;
                    final double o = this.origin;
                    final double b = this.bound;
                    final DioriteThreadLocalRandom rng = DioriteThreadLocalRandom.current();
                    do
                    {
                        consumer.accept(rng.internalNextDouble(o, b));
                    } while (++ i < f);
                }
            }

            @Override
            public long estimateSize()
            {
                return this.fence - this.index;
            }


            @Override
            public int characteristics()
            {
                return (Spliterator.SIZED | Spliterator.SUBSIZED | Spliterator.NONNULL | Spliterator.IMMUTABLE);
            }
        }

        static
        {
            try
            {
                UNSAFE = DioriteUtils.getUnsafe();
                final Class<?> tk = Thread.class;
                SEED = UNSAFE.objectFieldOffset(tk.getDeclaredField("threadLocalRandomSeed"));
                PROBE = UNSAFE.objectFieldOffset(tk.getDeclaredField("threadLocalRandomProbe"));
                SECONDARY = UNSAFE.objectFieldOffset(tk.getDeclaredField("threadLocalRandomSecondarySeed"));
            } catch (final Exception e)
            {
                throw new Error(e);
            }
        }
    }

    static
    {
        try
        {
            U = DioriteUtils.getUnsafe();
            final Class<?> k = ConcurrentIdentityHashMap.class;
            SIZECTL = U.objectFieldOffset(k.getDeclaredField("sizeCtl"));
            TRANSFERINDEX = U.objectFieldOffset(k.getDeclaredField("transferIndex"));
            BASECOUNT = U.objectFieldOffset(k.getDeclaredField("baseCount"));
            CELLSBUSY = U.objectFieldOffset(k.getDeclaredField("cellsBusy"));
            final Class<?> ck = CounterCell.class;
            CELLVALUE = U.objectFieldOffset(ck.getDeclaredField("value"));
            final Class<?> ak = Node[].class;
            ABASE = U.arrayBaseOffset(ak);
            final int scale = U.arrayIndexScale(ak);
            if ((scale & (scale - 1)) != 0)
            {
                throw new Error("data type scale not a power of two");
            }
            ASHIFT = 31 - Integer.numberOfLeadingZeros(scale);
        } catch (final Exception e)
        {
            throw new Error(e);
        }
    }
}
