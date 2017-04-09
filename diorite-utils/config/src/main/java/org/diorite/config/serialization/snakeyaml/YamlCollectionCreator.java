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

package org.diorite.config.serialization.snakeyaml;

import javax.annotation.Nullable;

import java.lang.reflect.Array;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.NavigableMap;
import java.util.NavigableSet;
import java.util.Properties;
import java.util.Queue;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.function.Function;
import java.util.function.IntFunction;

import org.yaml.snakeyaml.error.YAMLException;

import org.diorite.commons.arrays.DioriteArrayUtils;
import org.diorite.commons.reflections.ConstructorInvoker;
import org.diorite.commons.reflections.DioriteReflectionUtils;

import it.unimi.dsi.fastutil.booleans.BooleanArrayList;
import it.unimi.dsi.fastutil.booleans.BooleanBigArrayBigList;
import it.unimi.dsi.fastutil.booleans.BooleanBigList;
import it.unimi.dsi.fastutil.booleans.BooleanCollection;
import it.unimi.dsi.fastutil.booleans.BooleanList;
import it.unimi.dsi.fastutil.booleans.BooleanOpenHashSet;
import it.unimi.dsi.fastutil.booleans.BooleanSet;
import it.unimi.dsi.fastutil.booleans.BooleanStack;
import it.unimi.dsi.fastutil.bytes.Byte2BooleanAVLTreeMap;
import it.unimi.dsi.fastutil.bytes.Byte2BooleanLinkedOpenHashMap;
import it.unimi.dsi.fastutil.bytes.Byte2BooleanMap;
import it.unimi.dsi.fastutil.bytes.Byte2BooleanMaps;
import it.unimi.dsi.fastutil.bytes.Byte2BooleanSortedMap;
import it.unimi.dsi.fastutil.bytes.Byte2ByteAVLTreeMap;
import it.unimi.dsi.fastutil.bytes.Byte2ByteLinkedOpenHashMap;
import it.unimi.dsi.fastutil.bytes.Byte2ByteMap;
import it.unimi.dsi.fastutil.bytes.Byte2ByteMaps;
import it.unimi.dsi.fastutil.bytes.Byte2ByteSortedMap;
import it.unimi.dsi.fastutil.bytes.Byte2CharAVLTreeMap;
import it.unimi.dsi.fastutil.bytes.Byte2CharLinkedOpenHashMap;
import it.unimi.dsi.fastutil.bytes.Byte2CharMap;
import it.unimi.dsi.fastutil.bytes.Byte2CharMaps;
import it.unimi.dsi.fastutil.bytes.Byte2CharSortedMap;
import it.unimi.dsi.fastutil.bytes.Byte2DoubleAVLTreeMap;
import it.unimi.dsi.fastutil.bytes.Byte2DoubleLinkedOpenHashMap;
import it.unimi.dsi.fastutil.bytes.Byte2DoubleMap;
import it.unimi.dsi.fastutil.bytes.Byte2DoubleMaps;
import it.unimi.dsi.fastutil.bytes.Byte2DoubleSortedMap;
import it.unimi.dsi.fastutil.bytes.Byte2FloatAVLTreeMap;
import it.unimi.dsi.fastutil.bytes.Byte2FloatLinkedOpenHashMap;
import it.unimi.dsi.fastutil.bytes.Byte2FloatMap;
import it.unimi.dsi.fastutil.bytes.Byte2FloatMaps;
import it.unimi.dsi.fastutil.bytes.Byte2FloatSortedMap;
import it.unimi.dsi.fastutil.bytes.Byte2IntAVLTreeMap;
import it.unimi.dsi.fastutil.bytes.Byte2IntLinkedOpenHashMap;
import it.unimi.dsi.fastutil.bytes.Byte2IntMap;
import it.unimi.dsi.fastutil.bytes.Byte2IntMaps;
import it.unimi.dsi.fastutil.bytes.Byte2IntSortedMap;
import it.unimi.dsi.fastutil.bytes.Byte2LongAVLTreeMap;
import it.unimi.dsi.fastutil.bytes.Byte2LongLinkedOpenHashMap;
import it.unimi.dsi.fastutil.bytes.Byte2LongMap;
import it.unimi.dsi.fastutil.bytes.Byte2LongMaps;
import it.unimi.dsi.fastutil.bytes.Byte2LongSortedMap;
import it.unimi.dsi.fastutil.bytes.Byte2ObjectAVLTreeMap;
import it.unimi.dsi.fastutil.bytes.Byte2ObjectLinkedOpenHashMap;
import it.unimi.dsi.fastutil.bytes.Byte2ObjectMap;
import it.unimi.dsi.fastutil.bytes.Byte2ObjectMaps;
import it.unimi.dsi.fastutil.bytes.Byte2ObjectSortedMap;
import it.unimi.dsi.fastutil.bytes.Byte2ShortAVLTreeMap;
import it.unimi.dsi.fastutil.bytes.Byte2ShortLinkedOpenHashMap;
import it.unimi.dsi.fastutil.bytes.Byte2ShortMap;
import it.unimi.dsi.fastutil.bytes.Byte2ShortMaps;
import it.unimi.dsi.fastutil.bytes.Byte2ShortSortedMap;
import it.unimi.dsi.fastutil.bytes.ByteAVLTreeSet;
import it.unimi.dsi.fastutil.bytes.ByteArrayList;
import it.unimi.dsi.fastutil.bytes.ByteBigArrayBigList;
import it.unimi.dsi.fastutil.bytes.ByteBigList;
import it.unimi.dsi.fastutil.bytes.ByteCollection;
import it.unimi.dsi.fastutil.bytes.ByteLinkedOpenHashSet;
import it.unimi.dsi.fastutil.bytes.ByteList;
import it.unimi.dsi.fastutil.bytes.ByteSet;
import it.unimi.dsi.fastutil.bytes.ByteSortedSet;
import it.unimi.dsi.fastutil.bytes.ByteStack;
import it.unimi.dsi.fastutil.chars.Char2BooleanAVLTreeMap;
import it.unimi.dsi.fastutil.chars.Char2BooleanLinkedOpenHashMap;
import it.unimi.dsi.fastutil.chars.Char2BooleanMap;
import it.unimi.dsi.fastutil.chars.Char2BooleanMaps;
import it.unimi.dsi.fastutil.chars.Char2BooleanSortedMap;
import it.unimi.dsi.fastutil.chars.Char2ByteAVLTreeMap;
import it.unimi.dsi.fastutil.chars.Char2ByteLinkedOpenHashMap;
import it.unimi.dsi.fastutil.chars.Char2ByteMap;
import it.unimi.dsi.fastutil.chars.Char2ByteMaps;
import it.unimi.dsi.fastutil.chars.Char2ByteSortedMap;
import it.unimi.dsi.fastutil.chars.Char2CharAVLTreeMap;
import it.unimi.dsi.fastutil.chars.Char2CharLinkedOpenHashMap;
import it.unimi.dsi.fastutil.chars.Char2CharMap;
import it.unimi.dsi.fastutil.chars.Char2CharMaps;
import it.unimi.dsi.fastutil.chars.Char2CharSortedMap;
import it.unimi.dsi.fastutil.chars.Char2DoubleAVLTreeMap;
import it.unimi.dsi.fastutil.chars.Char2DoubleLinkedOpenHashMap;
import it.unimi.dsi.fastutil.chars.Char2DoubleMap;
import it.unimi.dsi.fastutil.chars.Char2DoubleMaps;
import it.unimi.dsi.fastutil.chars.Char2DoubleSortedMap;
import it.unimi.dsi.fastutil.chars.Char2FloatAVLTreeMap;
import it.unimi.dsi.fastutil.chars.Char2FloatLinkedOpenHashMap;
import it.unimi.dsi.fastutil.chars.Char2FloatMap;
import it.unimi.dsi.fastutil.chars.Char2FloatMaps;
import it.unimi.dsi.fastutil.chars.Char2FloatSortedMap;
import it.unimi.dsi.fastutil.chars.Char2IntAVLTreeMap;
import it.unimi.dsi.fastutil.chars.Char2IntLinkedOpenHashMap;
import it.unimi.dsi.fastutil.chars.Char2IntMap;
import it.unimi.dsi.fastutil.chars.Char2IntMaps;
import it.unimi.dsi.fastutil.chars.Char2IntSortedMap;
import it.unimi.dsi.fastutil.chars.Char2LongAVLTreeMap;
import it.unimi.dsi.fastutil.chars.Char2LongLinkedOpenHashMap;
import it.unimi.dsi.fastutil.chars.Char2LongMap;
import it.unimi.dsi.fastutil.chars.Char2LongMaps;
import it.unimi.dsi.fastutil.chars.Char2LongSortedMap;
import it.unimi.dsi.fastutil.chars.Char2ObjectAVLTreeMap;
import it.unimi.dsi.fastutil.chars.Char2ObjectLinkedOpenHashMap;
import it.unimi.dsi.fastutil.chars.Char2ObjectMap;
import it.unimi.dsi.fastutil.chars.Char2ObjectMaps;
import it.unimi.dsi.fastutil.chars.Char2ObjectSortedMap;
import it.unimi.dsi.fastutil.chars.Char2ShortAVLTreeMap;
import it.unimi.dsi.fastutil.chars.Char2ShortLinkedOpenHashMap;
import it.unimi.dsi.fastutil.chars.Char2ShortMap;
import it.unimi.dsi.fastutil.chars.Char2ShortMaps;
import it.unimi.dsi.fastutil.chars.Char2ShortSortedMap;
import it.unimi.dsi.fastutil.chars.CharAVLTreeSet;
import it.unimi.dsi.fastutil.chars.CharArrayList;
import it.unimi.dsi.fastutil.chars.CharBigArrayBigList;
import it.unimi.dsi.fastutil.chars.CharBigList;
import it.unimi.dsi.fastutil.chars.CharCollection;
import it.unimi.dsi.fastutil.chars.CharLinkedOpenHashSet;
import it.unimi.dsi.fastutil.chars.CharList;
import it.unimi.dsi.fastutil.chars.CharSet;
import it.unimi.dsi.fastutil.chars.CharSortedSet;
import it.unimi.dsi.fastutil.chars.CharStack;
import it.unimi.dsi.fastutil.doubles.Double2BooleanAVLTreeMap;
import it.unimi.dsi.fastutil.doubles.Double2BooleanLinkedOpenHashMap;
import it.unimi.dsi.fastutil.doubles.Double2BooleanMap;
import it.unimi.dsi.fastutil.doubles.Double2BooleanMaps;
import it.unimi.dsi.fastutil.doubles.Double2BooleanSortedMap;
import it.unimi.dsi.fastutil.doubles.Double2ByteAVLTreeMap;
import it.unimi.dsi.fastutil.doubles.Double2ByteLinkedOpenHashMap;
import it.unimi.dsi.fastutil.doubles.Double2ByteMap;
import it.unimi.dsi.fastutil.doubles.Double2ByteMaps;
import it.unimi.dsi.fastutil.doubles.Double2ByteSortedMap;
import it.unimi.dsi.fastutil.doubles.Double2CharAVLTreeMap;
import it.unimi.dsi.fastutil.doubles.Double2CharLinkedOpenHashMap;
import it.unimi.dsi.fastutil.doubles.Double2CharMap;
import it.unimi.dsi.fastutil.doubles.Double2CharMaps;
import it.unimi.dsi.fastutil.doubles.Double2CharSortedMap;
import it.unimi.dsi.fastutil.doubles.Double2DoubleAVLTreeMap;
import it.unimi.dsi.fastutil.doubles.Double2DoubleLinkedOpenHashMap;
import it.unimi.dsi.fastutil.doubles.Double2DoubleMap;
import it.unimi.dsi.fastutil.doubles.Double2DoubleMaps;
import it.unimi.dsi.fastutil.doubles.Double2DoubleSortedMap;
import it.unimi.dsi.fastutil.doubles.Double2FloatAVLTreeMap;
import it.unimi.dsi.fastutil.doubles.Double2FloatLinkedOpenHashMap;
import it.unimi.dsi.fastutil.doubles.Double2FloatMap;
import it.unimi.dsi.fastutil.doubles.Double2FloatMaps;
import it.unimi.dsi.fastutil.doubles.Double2FloatSortedMap;
import it.unimi.dsi.fastutil.doubles.Double2IntAVLTreeMap;
import it.unimi.dsi.fastutil.doubles.Double2IntLinkedOpenHashMap;
import it.unimi.dsi.fastutil.doubles.Double2IntMap;
import it.unimi.dsi.fastutil.doubles.Double2IntMaps;
import it.unimi.dsi.fastutil.doubles.Double2IntSortedMap;
import it.unimi.dsi.fastutil.doubles.Double2LongAVLTreeMap;
import it.unimi.dsi.fastutil.doubles.Double2LongLinkedOpenHashMap;
import it.unimi.dsi.fastutil.doubles.Double2LongMap;
import it.unimi.dsi.fastutil.doubles.Double2LongMaps;
import it.unimi.dsi.fastutil.doubles.Double2LongSortedMap;
import it.unimi.dsi.fastutil.doubles.Double2ObjectAVLTreeMap;
import it.unimi.dsi.fastutil.doubles.Double2ObjectLinkedOpenHashMap;
import it.unimi.dsi.fastutil.doubles.Double2ObjectMap;
import it.unimi.dsi.fastutil.doubles.Double2ObjectMaps;
import it.unimi.dsi.fastutil.doubles.Double2ObjectSortedMap;
import it.unimi.dsi.fastutil.doubles.Double2ShortAVLTreeMap;
import it.unimi.dsi.fastutil.doubles.Double2ShortLinkedOpenHashMap;
import it.unimi.dsi.fastutil.doubles.Double2ShortMap;
import it.unimi.dsi.fastutil.doubles.Double2ShortMaps;
import it.unimi.dsi.fastutil.doubles.Double2ShortSortedMap;
import it.unimi.dsi.fastutil.doubles.DoubleAVLTreeSet;
import it.unimi.dsi.fastutil.doubles.DoubleArrayList;
import it.unimi.dsi.fastutil.doubles.DoubleBigArrayBigList;
import it.unimi.dsi.fastutil.doubles.DoubleBigList;
import it.unimi.dsi.fastutil.doubles.DoubleCollection;
import it.unimi.dsi.fastutil.doubles.DoubleLinkedOpenHashSet;
import it.unimi.dsi.fastutil.doubles.DoubleList;
import it.unimi.dsi.fastutil.doubles.DoubleSet;
import it.unimi.dsi.fastutil.doubles.DoubleSortedSet;
import it.unimi.dsi.fastutil.doubles.DoubleStack;
import it.unimi.dsi.fastutil.floats.Float2BooleanAVLTreeMap;
import it.unimi.dsi.fastutil.floats.Float2BooleanLinkedOpenHashMap;
import it.unimi.dsi.fastutil.floats.Float2BooleanMap;
import it.unimi.dsi.fastutil.floats.Float2BooleanMaps;
import it.unimi.dsi.fastutil.floats.Float2BooleanSortedMap;
import it.unimi.dsi.fastutil.floats.Float2ByteAVLTreeMap;
import it.unimi.dsi.fastutil.floats.Float2ByteLinkedOpenHashMap;
import it.unimi.dsi.fastutil.floats.Float2ByteMap;
import it.unimi.dsi.fastutil.floats.Float2ByteMaps;
import it.unimi.dsi.fastutil.floats.Float2ByteSortedMap;
import it.unimi.dsi.fastutil.floats.Float2CharAVLTreeMap;
import it.unimi.dsi.fastutil.floats.Float2CharLinkedOpenHashMap;
import it.unimi.dsi.fastutil.floats.Float2CharMap;
import it.unimi.dsi.fastutil.floats.Float2CharMaps;
import it.unimi.dsi.fastutil.floats.Float2CharSortedMap;
import it.unimi.dsi.fastutil.floats.Float2DoubleAVLTreeMap;
import it.unimi.dsi.fastutil.floats.Float2DoubleLinkedOpenHashMap;
import it.unimi.dsi.fastutil.floats.Float2DoubleMap;
import it.unimi.dsi.fastutil.floats.Float2DoubleMaps;
import it.unimi.dsi.fastutil.floats.Float2DoubleSortedMap;
import it.unimi.dsi.fastutil.floats.Float2FloatAVLTreeMap;
import it.unimi.dsi.fastutil.floats.Float2FloatLinkedOpenHashMap;
import it.unimi.dsi.fastutil.floats.Float2FloatMap;
import it.unimi.dsi.fastutil.floats.Float2FloatMaps;
import it.unimi.dsi.fastutil.floats.Float2FloatSortedMap;
import it.unimi.dsi.fastutil.floats.Float2IntAVLTreeMap;
import it.unimi.dsi.fastutil.floats.Float2IntLinkedOpenHashMap;
import it.unimi.dsi.fastutil.floats.Float2IntMap;
import it.unimi.dsi.fastutil.floats.Float2IntMaps;
import it.unimi.dsi.fastutil.floats.Float2IntSortedMap;
import it.unimi.dsi.fastutil.floats.Float2LongAVLTreeMap;
import it.unimi.dsi.fastutil.floats.Float2LongLinkedOpenHashMap;
import it.unimi.dsi.fastutil.floats.Float2LongMap;
import it.unimi.dsi.fastutil.floats.Float2LongMaps;
import it.unimi.dsi.fastutil.floats.Float2LongSortedMap;
import it.unimi.dsi.fastutil.floats.Float2ObjectAVLTreeMap;
import it.unimi.dsi.fastutil.floats.Float2ObjectLinkedOpenHashMap;
import it.unimi.dsi.fastutil.floats.Float2ObjectMap;
import it.unimi.dsi.fastutil.floats.Float2ObjectMaps;
import it.unimi.dsi.fastutil.floats.Float2ObjectSortedMap;
import it.unimi.dsi.fastutil.floats.Float2ShortAVLTreeMap;
import it.unimi.dsi.fastutil.floats.Float2ShortLinkedOpenHashMap;
import it.unimi.dsi.fastutil.floats.Float2ShortMap;
import it.unimi.dsi.fastutil.floats.Float2ShortMaps;
import it.unimi.dsi.fastutil.floats.Float2ShortSortedMap;
import it.unimi.dsi.fastutil.floats.FloatAVLTreeSet;
import it.unimi.dsi.fastutil.floats.FloatArrayList;
import it.unimi.dsi.fastutil.floats.FloatBigArrayBigList;
import it.unimi.dsi.fastutil.floats.FloatBigList;
import it.unimi.dsi.fastutil.floats.FloatCollection;
import it.unimi.dsi.fastutil.floats.FloatLinkedOpenHashSet;
import it.unimi.dsi.fastutil.floats.FloatList;
import it.unimi.dsi.fastutil.floats.FloatSet;
import it.unimi.dsi.fastutil.floats.FloatSortedSet;
import it.unimi.dsi.fastutil.floats.FloatStack;
import it.unimi.dsi.fastutil.ints.Int2BooleanAVLTreeMap;
import it.unimi.dsi.fastutil.ints.Int2BooleanLinkedOpenHashMap;
import it.unimi.dsi.fastutil.ints.Int2BooleanMap;
import it.unimi.dsi.fastutil.ints.Int2BooleanMaps;
import it.unimi.dsi.fastutil.ints.Int2BooleanSortedMap;
import it.unimi.dsi.fastutil.ints.Int2ByteAVLTreeMap;
import it.unimi.dsi.fastutil.ints.Int2ByteLinkedOpenHashMap;
import it.unimi.dsi.fastutil.ints.Int2ByteMap;
import it.unimi.dsi.fastutil.ints.Int2ByteMaps;
import it.unimi.dsi.fastutil.ints.Int2ByteSortedMap;
import it.unimi.dsi.fastutil.ints.Int2CharAVLTreeMap;
import it.unimi.dsi.fastutil.ints.Int2CharLinkedOpenHashMap;
import it.unimi.dsi.fastutil.ints.Int2CharMap;
import it.unimi.dsi.fastutil.ints.Int2CharMaps;
import it.unimi.dsi.fastutil.ints.Int2CharSortedMap;
import it.unimi.dsi.fastutil.ints.Int2DoubleAVLTreeMap;
import it.unimi.dsi.fastutil.ints.Int2DoubleLinkedOpenHashMap;
import it.unimi.dsi.fastutil.ints.Int2DoubleMap;
import it.unimi.dsi.fastutil.ints.Int2DoubleMaps;
import it.unimi.dsi.fastutil.ints.Int2DoubleSortedMap;
import it.unimi.dsi.fastutil.ints.Int2FloatAVLTreeMap;
import it.unimi.dsi.fastutil.ints.Int2FloatLinkedOpenHashMap;
import it.unimi.dsi.fastutil.ints.Int2FloatMap;
import it.unimi.dsi.fastutil.ints.Int2FloatMaps;
import it.unimi.dsi.fastutil.ints.Int2FloatSortedMap;
import it.unimi.dsi.fastutil.ints.Int2IntAVLTreeMap;
import it.unimi.dsi.fastutil.ints.Int2IntLinkedOpenHashMap;
import it.unimi.dsi.fastutil.ints.Int2IntMap;
import it.unimi.dsi.fastutil.ints.Int2IntMaps;
import it.unimi.dsi.fastutil.ints.Int2IntSortedMap;
import it.unimi.dsi.fastutil.ints.Int2LongAVLTreeMap;
import it.unimi.dsi.fastutil.ints.Int2LongLinkedOpenHashMap;
import it.unimi.dsi.fastutil.ints.Int2LongMap;
import it.unimi.dsi.fastutil.ints.Int2LongMaps;
import it.unimi.dsi.fastutil.ints.Int2LongSortedMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectAVLTreeMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectLinkedOpenHashMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMaps;
import it.unimi.dsi.fastutil.ints.Int2ObjectSortedMap;
import it.unimi.dsi.fastutil.ints.Int2ShortAVLTreeMap;
import it.unimi.dsi.fastutil.ints.Int2ShortLinkedOpenHashMap;
import it.unimi.dsi.fastutil.ints.Int2ShortMap;
import it.unimi.dsi.fastutil.ints.Int2ShortMaps;
import it.unimi.dsi.fastutil.ints.Int2ShortSortedMap;
import it.unimi.dsi.fastutil.ints.IntAVLTreeSet;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntBigArrayBigList;
import it.unimi.dsi.fastutil.ints.IntBigList;
import it.unimi.dsi.fastutil.ints.IntCollection;
import it.unimi.dsi.fastutil.ints.IntLinkedOpenHashSet;
import it.unimi.dsi.fastutil.ints.IntList;
import it.unimi.dsi.fastutil.ints.IntSet;
import it.unimi.dsi.fastutil.ints.IntSortedSet;
import it.unimi.dsi.fastutil.ints.IntStack;
import it.unimi.dsi.fastutil.longs.Long2BooleanAVLTreeMap;
import it.unimi.dsi.fastutil.longs.Long2BooleanLinkedOpenHashMap;
import it.unimi.dsi.fastutil.longs.Long2BooleanMap;
import it.unimi.dsi.fastutil.longs.Long2BooleanMaps;
import it.unimi.dsi.fastutil.longs.Long2BooleanSortedMap;
import it.unimi.dsi.fastutil.longs.Long2ByteAVLTreeMap;
import it.unimi.dsi.fastutil.longs.Long2ByteLinkedOpenHashMap;
import it.unimi.dsi.fastutil.longs.Long2ByteMap;
import it.unimi.dsi.fastutil.longs.Long2ByteMaps;
import it.unimi.dsi.fastutil.longs.Long2ByteSortedMap;
import it.unimi.dsi.fastutil.longs.Long2CharAVLTreeMap;
import it.unimi.dsi.fastutil.longs.Long2CharLinkedOpenHashMap;
import it.unimi.dsi.fastutil.longs.Long2CharMap;
import it.unimi.dsi.fastutil.longs.Long2CharMaps;
import it.unimi.dsi.fastutil.longs.Long2CharSortedMap;
import it.unimi.dsi.fastutil.longs.Long2DoubleAVLTreeMap;
import it.unimi.dsi.fastutil.longs.Long2DoubleLinkedOpenHashMap;
import it.unimi.dsi.fastutil.longs.Long2DoubleMap;
import it.unimi.dsi.fastutil.longs.Long2DoubleMaps;
import it.unimi.dsi.fastutil.longs.Long2DoubleSortedMap;
import it.unimi.dsi.fastutil.longs.Long2FloatAVLTreeMap;
import it.unimi.dsi.fastutil.longs.Long2FloatLinkedOpenHashMap;
import it.unimi.dsi.fastutil.longs.Long2FloatMap;
import it.unimi.dsi.fastutil.longs.Long2FloatMaps;
import it.unimi.dsi.fastutil.longs.Long2FloatSortedMap;
import it.unimi.dsi.fastutil.longs.Long2IntAVLTreeMap;
import it.unimi.dsi.fastutil.longs.Long2IntLinkedOpenHashMap;
import it.unimi.dsi.fastutil.longs.Long2IntMap;
import it.unimi.dsi.fastutil.longs.Long2IntMaps;
import it.unimi.dsi.fastutil.longs.Long2IntSortedMap;
import it.unimi.dsi.fastutil.longs.Long2LongAVLTreeMap;
import it.unimi.dsi.fastutil.longs.Long2LongLinkedOpenHashMap;
import it.unimi.dsi.fastutil.longs.Long2LongMap;
import it.unimi.dsi.fastutil.longs.Long2LongMaps;
import it.unimi.dsi.fastutil.longs.Long2LongSortedMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectAVLTreeMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectLinkedOpenHashMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectMaps;
import it.unimi.dsi.fastutil.longs.Long2ObjectSortedMap;
import it.unimi.dsi.fastutil.longs.Long2ShortAVLTreeMap;
import it.unimi.dsi.fastutil.longs.Long2ShortLinkedOpenHashMap;
import it.unimi.dsi.fastutil.longs.Long2ShortMap;
import it.unimi.dsi.fastutil.longs.Long2ShortMaps;
import it.unimi.dsi.fastutil.longs.Long2ShortSortedMap;
import it.unimi.dsi.fastutil.longs.LongAVLTreeSet;
import it.unimi.dsi.fastutil.longs.LongArrayList;
import it.unimi.dsi.fastutil.longs.LongBigArrayBigList;
import it.unimi.dsi.fastutil.longs.LongBigList;
import it.unimi.dsi.fastutil.longs.LongCollection;
import it.unimi.dsi.fastutil.longs.LongLinkedOpenHashSet;
import it.unimi.dsi.fastutil.longs.LongList;
import it.unimi.dsi.fastutil.longs.LongSet;
import it.unimi.dsi.fastutil.longs.LongSortedSet;
import it.unimi.dsi.fastutil.longs.LongStack;
import it.unimi.dsi.fastutil.objects.Object2BooleanAVLTreeMap;
import it.unimi.dsi.fastutil.objects.Object2BooleanLinkedOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2BooleanMap;
import it.unimi.dsi.fastutil.objects.Object2BooleanMaps;
import it.unimi.dsi.fastutil.objects.Object2BooleanSortedMap;
import it.unimi.dsi.fastutil.objects.Object2ByteAVLTreeMap;
import it.unimi.dsi.fastutil.objects.Object2ByteLinkedOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2ByteMap;
import it.unimi.dsi.fastutil.objects.Object2ByteMaps;
import it.unimi.dsi.fastutil.objects.Object2ByteSortedMap;
import it.unimi.dsi.fastutil.objects.Object2CharAVLTreeMap;
import it.unimi.dsi.fastutil.objects.Object2CharLinkedOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2CharMap;
import it.unimi.dsi.fastutil.objects.Object2CharMaps;
import it.unimi.dsi.fastutil.objects.Object2CharSortedMap;
import it.unimi.dsi.fastutil.objects.Object2DoubleAVLTreeMap;
import it.unimi.dsi.fastutil.objects.Object2DoubleLinkedOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2DoubleMap;
import it.unimi.dsi.fastutil.objects.Object2DoubleMaps;
import it.unimi.dsi.fastutil.objects.Object2DoubleSortedMap;
import it.unimi.dsi.fastutil.objects.Object2FloatAVLTreeMap;
import it.unimi.dsi.fastutil.objects.Object2FloatLinkedOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2FloatMap;
import it.unimi.dsi.fastutil.objects.Object2FloatMaps;
import it.unimi.dsi.fastutil.objects.Object2FloatSortedMap;
import it.unimi.dsi.fastutil.objects.Object2IntAVLTreeMap;
import it.unimi.dsi.fastutil.objects.Object2IntLinkedOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntMaps;
import it.unimi.dsi.fastutil.objects.Object2IntSortedMap;
import it.unimi.dsi.fastutil.objects.Object2LongAVLTreeMap;
import it.unimi.dsi.fastutil.objects.Object2LongLinkedOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2LongMap;
import it.unimi.dsi.fastutil.objects.Object2LongMaps;
import it.unimi.dsi.fastutil.objects.Object2LongSortedMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectAVLTreeMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectMaps;
import it.unimi.dsi.fastutil.objects.Object2ObjectSortedMap;
import it.unimi.dsi.fastutil.objects.Object2ShortAVLTreeMap;
import it.unimi.dsi.fastutil.objects.Object2ShortLinkedOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2ShortMap;
import it.unimi.dsi.fastutil.objects.Object2ShortMaps;
import it.unimi.dsi.fastutil.objects.Object2ShortSortedMap;
import it.unimi.dsi.fastutil.objects.ObjectAVLTreeSet;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectLinkedOpenHashSet;
import it.unimi.dsi.fastutil.shorts.Short2BooleanAVLTreeMap;
import it.unimi.dsi.fastutil.shorts.Short2BooleanLinkedOpenHashMap;
import it.unimi.dsi.fastutil.shorts.Short2BooleanMap;
import it.unimi.dsi.fastutil.shorts.Short2BooleanMaps;
import it.unimi.dsi.fastutil.shorts.Short2BooleanSortedMap;
import it.unimi.dsi.fastutil.shorts.Short2ByteAVLTreeMap;
import it.unimi.dsi.fastutil.shorts.Short2ByteLinkedOpenHashMap;
import it.unimi.dsi.fastutil.shorts.Short2ByteMap;
import it.unimi.dsi.fastutil.shorts.Short2ByteMaps;
import it.unimi.dsi.fastutil.shorts.Short2ByteSortedMap;
import it.unimi.dsi.fastutil.shorts.Short2CharAVLTreeMap;
import it.unimi.dsi.fastutil.shorts.Short2CharLinkedOpenHashMap;
import it.unimi.dsi.fastutil.shorts.Short2CharMap;
import it.unimi.dsi.fastutil.shorts.Short2CharMaps;
import it.unimi.dsi.fastutil.shorts.Short2CharSortedMap;
import it.unimi.dsi.fastutil.shorts.Short2DoubleAVLTreeMap;
import it.unimi.dsi.fastutil.shorts.Short2DoubleLinkedOpenHashMap;
import it.unimi.dsi.fastutil.shorts.Short2DoubleMap;
import it.unimi.dsi.fastutil.shorts.Short2DoubleMaps;
import it.unimi.dsi.fastutil.shorts.Short2DoubleSortedMap;
import it.unimi.dsi.fastutil.shorts.Short2FloatAVLTreeMap;
import it.unimi.dsi.fastutil.shorts.Short2FloatLinkedOpenHashMap;
import it.unimi.dsi.fastutil.shorts.Short2FloatMap;
import it.unimi.dsi.fastutil.shorts.Short2FloatMaps;
import it.unimi.dsi.fastutil.shorts.Short2FloatSortedMap;
import it.unimi.dsi.fastutil.shorts.Short2IntAVLTreeMap;
import it.unimi.dsi.fastutil.shorts.Short2IntLinkedOpenHashMap;
import it.unimi.dsi.fastutil.shorts.Short2IntMap;
import it.unimi.dsi.fastutil.shorts.Short2IntMaps;
import it.unimi.dsi.fastutil.shorts.Short2IntSortedMap;
import it.unimi.dsi.fastutil.shorts.Short2LongAVLTreeMap;
import it.unimi.dsi.fastutil.shorts.Short2LongLinkedOpenHashMap;
import it.unimi.dsi.fastutil.shorts.Short2LongMap;
import it.unimi.dsi.fastutil.shorts.Short2LongMaps;
import it.unimi.dsi.fastutil.shorts.Short2LongSortedMap;
import it.unimi.dsi.fastutil.shorts.Short2ObjectAVLTreeMap;
import it.unimi.dsi.fastutil.shorts.Short2ObjectLinkedOpenHashMap;
import it.unimi.dsi.fastutil.shorts.Short2ObjectMap;
import it.unimi.dsi.fastutil.shorts.Short2ObjectMaps;
import it.unimi.dsi.fastutil.shorts.Short2ObjectSortedMap;
import it.unimi.dsi.fastutil.shorts.Short2ShortAVLTreeMap;
import it.unimi.dsi.fastutil.shorts.Short2ShortLinkedOpenHashMap;
import it.unimi.dsi.fastutil.shorts.Short2ShortMap;
import it.unimi.dsi.fastutil.shorts.Short2ShortMaps;
import it.unimi.dsi.fastutil.shorts.Short2ShortSortedMap;
import it.unimi.dsi.fastutil.shorts.ShortAVLTreeSet;
import it.unimi.dsi.fastutil.shorts.ShortArrayList;
import it.unimi.dsi.fastutil.shorts.ShortBigArrayBigList;
import it.unimi.dsi.fastutil.shorts.ShortBigList;
import it.unimi.dsi.fastutil.shorts.ShortCollection;
import it.unimi.dsi.fastutil.shorts.ShortLinkedOpenHashSet;
import it.unimi.dsi.fastutil.shorts.ShortList;
import it.unimi.dsi.fastutil.shorts.ShortSet;
import it.unimi.dsi.fastutil.shorts.ShortSortedSet;
import it.unimi.dsi.fastutil.shorts.ShortStack;

public final class YamlCollectionCreator
{
    private static final Map<Class<?>, IntFunction<?>> collectionCreators   = new ConcurrentHashMap<>(20);
    private static final Map<Class<?>, Function<?, ?>> unmodifiableWrappers = new ConcurrentHashMap<>(20);

    private YamlCollectionCreator()
    {
    }

    static
    {
        JavaCollections.putAllCollections(collectionCreators, unmodifiableWrappers);
        Class<?> aClass = DioriteReflectionUtils.tryGetCanonicalClass("it.unimi.dsi.fastutil.Maps");
        if (aClass != null)
        {
            FastUtilsCollections.putAllCollections(collectionCreators, unmodifiableWrappers);
        }
    }

    @SuppressWarnings("unchecked")
    private static <T> T create(Map<Class<?>, IntFunction<?>> map, Class<T> clazz, int size)
    {
        IntFunction<?> intFunction = map.get(clazz);
        if (intFunction != null)
        {
            return (T) intFunction.apply(size);
        }
        if ((! Modifier.isAbstract(clazz.getModifiers())) && (Map.class.isAssignableFrom(clazz) || Collection.class.isAssignableFrom(clazz)))
        {
            ConstructorInvoker<T> constructor = DioriteReflectionUtils.getConstructor(clazz, false);
            if (constructor != null)
            {
                constructor.ensureAccessible();
                IntFunction<T> creator = constructor::invokeWith;
                map.put(clazz, creator);
                return creator.apply(size);
            }
        }
        for (Entry<Class<?>, IntFunction<?>> entry : map.entrySet())
        {
            if (clazz.isAssignableFrom(entry.getKey()))
            {
                IntFunction<?> function = entry.getValue();
                map.put(clazz, function);
                return (T) function.apply(size);
            }
        }
        throw new YAMLException("Can't create collection: " + clazz);
    }

    @SuppressWarnings({"unchecked", "rawtypes", "SuspiciousSystemArraycopy"})
    @Nullable
    public static <T> T makeUnmodifiable(Object collection)
    {
        if (collection.getClass().isArray())
        {
            int length = Array.getLength(collection);
            if (length == 0)
            {
                return (T) DioriteArrayUtils.getEmptyObjectArray(collection.getClass().getComponentType());
            }
            Object copy = DioriteArrayUtils.newArray(collection.getClass().getComponentType(), length);
            System.arraycopy(collection, 0, copy, 0, length);
            return (T) copy;
        }
        Function function = unmodifiableWrappers.get(collection.getClass());
        if (function == null)
        {
            for (Entry<Class<?>, Function<?, ?>> entry : unmodifiableWrappers.entrySet())
            {
                if (entry.getKey().isInstance(collection))
                {
                    function = entry.getValue();
                    break;
                }
            }
            if (function != null)
            {
                unmodifiableWrappers.put(collection.getClass(), function);
                return (T) function.apply(collection);
            }

            if (collection instanceof Collection)
            {
                if (collection instanceof Set)
                {
                    if (collection instanceof NavigableSet)
                    {
                        return (T) Collections.unmodifiableNavigableSet((NavigableSet<?>) collection);
                    }
                    if (collection instanceof SortedSet)
                    {
                        return (T) Collections.unmodifiableSortedSet((SortedSet<?>) collection);
                    }
                    return (T) Collections.unmodifiableSet((Set<?>) collection);
                }
                if (collection instanceof List)
                {
                    return (T) Collections.unmodifiableList((List<?>) collection);
                }
                return (T) Collections.unmodifiableCollection((Collection<?>) collection);
            }
            else if (collection instanceof Map)
            {
                if (collection instanceof NavigableMap)
                {
                    return (T) Collections.unmodifiableNavigableMap((NavigableMap<?, ?>) collection);
                }
                if (collection instanceof SortedMap)
                {
                    return (T) Collections.unmodifiableSortedMap((SortedMap<?, ?>) collection);
                }
                return (T) Collections.unmodifiableMap((Map<?, ?>) collection);
            }
            else
            {
                new RuntimeException("Can't make this collection unmodifiable: " + collection.getClass().getName()).printStackTrace();
                return (T) collection;
            }
        }
        return (T) function.apply(collection);
    }

    @SuppressWarnings("unchecked")
    public static <T> T createCollection(Class<?> clazz, int size)
    {
        return create(collectionCreators, (Class<T>) clazz, size);
    }

    public static <T> void addCollection(Class<T> type, IntFunction<T> func)
    {
        collectionCreators.put(type, func);
    }

    private static <T> void safePutUnmodf(Map<Class<?>, Function<?, ?>> map, Class<T> type, Function<T, Object> func)
    {
        map.put(type, func);
    }

    private static <T> void safePut(Map<Class<?>, IntFunction<?>> map, Class<T> type, IntFunction<T> func)
    {
        map.put(type, func);
    }

    static final class JavaCollections
    {
        private JavaCollections()
        {
        }

        static void putAllCollections(Map<Class<?>, IntFunction<?>> map, Map<Class<?>, Function<?, ?>> unmodMap)
        {
            safePut(map, ArrayList.class, ArrayList::new);
            safePut(map, HashSet.class, LinkedHashSet::new);
            safePut(map, Properties.class, x -> new Properties());
            safePut(map, Hashtable.class, Hashtable::new);

            safePut(map, Collection.class, ArrayList::new);
            safePut(map, Set.class, LinkedHashSet::new);
            safePut(map, List.class, ArrayList::new);
            safePut(map, SortedSet.class, x -> new TreeSet<>());
            safePut(map, Queue.class, x -> new ConcurrentLinkedQueue<>());
            safePut(map, Deque.class, x -> new ConcurrentLinkedDeque<>());
            safePut(map, BlockingQueue.class, x -> new LinkedBlockingQueue<>());
            safePut(map, BlockingDeque.class, x -> new LinkedBlockingDeque<>());


            safePut(map, HashMap.class, LinkedHashMap::new);
            safePut(map, LinkedHashMap.class, LinkedHashMap::new);
            safePut(map, ConcurrentHashMap.class, ConcurrentHashMap::new);

            safePut(map, Map.class, LinkedHashMap::new);
            safePut(map, ConcurrentMap.class, x -> new ConcurrentSkipListMap<>());
            safePut(map, ConcurrentNavigableMap.class, x -> new ConcurrentSkipListMap<>());
            safePut(map, SortedMap.class, i -> new TreeMap<>());
        }
    }

    static final class FastUtilsCollections
    {
        private FastUtilsCollections()
        {
        }

        static void putAllCollections(Map<Class<?>, IntFunction<?>> map, Map<Class<?>, Function<?, ?>> unmodMap)
        {
            safePut(map, Collection.class, ObjectArrayList::new);
            {
                safePut(map, BooleanCollection.class, BooleanArrayList::new);
                safePut(map, ByteCollection.class, ByteArrayList::new);
                safePut(map, CharCollection.class, CharArrayList::new);
                safePut(map, ShortCollection.class, ShortArrayList::new);
                safePut(map, IntCollection.class, IntArrayList::new);
                safePut(map, LongCollection.class, LongArrayList::new);
                safePut(map, FloatCollection.class, FloatArrayList::new);
                safePut(map, DoubleCollection.class, DoubleArrayList::new);

                safePut(map, BooleanStack.class, BooleanArrayList::new);
                safePut(map, ByteStack.class, ByteArrayList::new);
                safePut(map, CharStack.class, CharArrayList::new);
                safePut(map, ShortStack.class, ShortArrayList::new);
                safePut(map, IntStack.class, IntArrayList::new);
                safePut(map, LongStack.class, LongArrayList::new);
                safePut(map, FloatStack.class, FloatArrayList::new);
                safePut(map, DoubleStack.class, DoubleArrayList::new);
            }
            safePut(map, Set.class, ObjectLinkedOpenHashSet::new);
            {
                safePut(map, BooleanSet.class, BooleanOpenHashSet::new);
                safePut(map, ByteSet.class, ByteLinkedOpenHashSet::new);
                safePut(map, CharSet.class, CharLinkedOpenHashSet::new);
                safePut(map, ShortSet.class, ShortLinkedOpenHashSet::new);
                safePut(map, IntSet.class, IntLinkedOpenHashSet::new);
                safePut(map, LongSet.class, LongLinkedOpenHashSet::new);
                safePut(map, FloatSet.class, FloatLinkedOpenHashSet::new);
                safePut(map, DoubleSet.class, DoubleLinkedOpenHashSet::new);
            }
            safePut(map, List.class, ObjectArrayList::new);
            {
                safePut(map, BooleanList.class, BooleanArrayList::new);
                safePut(map, ByteList.class, ByteArrayList::new);
                safePut(map, CharList.class, CharArrayList::new);
                safePut(map, ShortList.class, ShortArrayList::new);
                safePut(map, IntList.class, IntArrayList::new);
                safePut(map, LongList.class, LongArrayList::new);
                safePut(map, FloatList.class, FloatArrayList::new);
                safePut(map, DoubleList.class, DoubleArrayList::new);

                safePut(map, BooleanBigList.class, BooleanBigArrayBigList::new);
                safePut(map, ByteBigList.class, ByteBigArrayBigList::new);
                safePut(map, CharBigList.class, CharBigArrayBigList::new);
                safePut(map, ShortBigList.class, ShortBigArrayBigList::new);
                safePut(map, IntBigList.class, IntBigArrayBigList::new);
                safePut(map, LongBigList.class, LongBigArrayBigList::new);
                safePut(map, FloatBigList.class, FloatBigArrayBigList::new);
                safePut(map, DoubleBigList.class, DoubleBigArrayBigList::new);
            }
            safePut(map, SortedSet.class, x -> new ObjectAVLTreeSet<>());
            {
                safePut(map, ByteSortedSet.class, x -> new ByteAVLTreeSet());
                safePut(map, CharSortedSet.class, x -> new CharAVLTreeSet());
                safePut(map, ShortSortedSet.class, x -> new ShortAVLTreeSet());
                safePut(map, IntSortedSet.class, x -> new IntAVLTreeSet());
                safePut(map, LongSortedSet.class, x -> new LongAVLTreeSet());
                safePut(map, FloatSortedSet.class, x -> new FloatAVLTreeSet());
                safePut(map, DoubleSortedSet.class, x -> new DoubleAVLTreeSet());
            }


            safePut(map, HashMap.class, LinkedHashMap::new);
            safePut(map, LinkedHashMap.class, LinkedHashMap::new);
            safePut(map, ConcurrentHashMap.class, ConcurrentHashMap::new);

            safePut(map, Map.class, Object2ObjectLinkedOpenHashMap::new);
            {
                safePut(map, Object2ObjectMap.class, Object2ObjectLinkedOpenHashMap::new);
                safePut(map, Object2BooleanMap.class, Object2BooleanLinkedOpenHashMap::new);
                safePut(map, Object2ByteMap.class, Object2ByteLinkedOpenHashMap::new);
                safePut(map, Object2CharMap.class, Object2CharLinkedOpenHashMap::new);
                safePut(map, Object2ShortMap.class, Object2ShortLinkedOpenHashMap::new);
                safePut(map, Object2IntMap.class, Object2IntLinkedOpenHashMap::new);
                safePut(map, Object2LongMap.class, Object2LongLinkedOpenHashMap::new);
                safePut(map, Object2FloatMap.class, Object2FloatLinkedOpenHashMap::new);
                safePut(map, Object2DoubleMap.class, Object2DoubleLinkedOpenHashMap::new);

                safePut(map, Byte2ObjectMap.class, Byte2ObjectLinkedOpenHashMap::new);
                safePut(map, Byte2BooleanMap.class, Byte2BooleanLinkedOpenHashMap::new);
                safePut(map, Byte2ByteMap.class, Byte2ByteLinkedOpenHashMap::new);
                safePut(map, Byte2CharMap.class, Byte2CharLinkedOpenHashMap::new);
                safePut(map, Byte2ShortMap.class, Byte2ShortLinkedOpenHashMap::new);
                safePut(map, Byte2IntMap.class, Byte2IntLinkedOpenHashMap::new);
                safePut(map, Byte2LongMap.class, Byte2LongLinkedOpenHashMap::new);
                safePut(map, Byte2FloatMap.class, Byte2FloatLinkedOpenHashMap::new);
                safePut(map, Byte2DoubleMap.class, Byte2DoubleLinkedOpenHashMap::new);

                safePut(map, Char2ObjectMap.class, Char2ObjectLinkedOpenHashMap::new);
                safePut(map, Char2BooleanMap.class, Char2BooleanLinkedOpenHashMap::new);
                safePut(map, Char2ByteMap.class, Char2ByteLinkedOpenHashMap::new);
                safePut(map, Char2CharMap.class, Char2CharLinkedOpenHashMap::new);
                safePut(map, Char2ShortMap.class, Char2ShortLinkedOpenHashMap::new);
                safePut(map, Char2IntMap.class, Char2IntLinkedOpenHashMap::new);
                safePut(map, Char2LongMap.class, Char2LongLinkedOpenHashMap::new);
                safePut(map, Char2FloatMap.class, Char2FloatLinkedOpenHashMap::new);
                safePut(map, Char2DoubleMap.class, Char2DoubleLinkedOpenHashMap::new);

                safePut(map, Short2ObjectMap.class, Short2ObjectLinkedOpenHashMap::new);
                safePut(map, Short2BooleanMap.class, Short2BooleanLinkedOpenHashMap::new);
                safePut(map, Short2ByteMap.class, Short2ByteLinkedOpenHashMap::new);
                safePut(map, Short2CharMap.class, Short2CharLinkedOpenHashMap::new);
                safePut(map, Short2ShortMap.class, Short2ShortLinkedOpenHashMap::new);
                safePut(map, Short2IntMap.class, Short2IntLinkedOpenHashMap::new);
                safePut(map, Short2LongMap.class, Short2LongLinkedOpenHashMap::new);
                safePut(map, Short2FloatMap.class, Short2FloatLinkedOpenHashMap::new);
                safePut(map, Short2DoubleMap.class, Short2DoubleLinkedOpenHashMap::new);

                safePut(map, Int2ObjectMap.class, Int2ObjectLinkedOpenHashMap::new);
                safePut(map, Int2BooleanMap.class, Int2BooleanLinkedOpenHashMap::new);
                safePut(map, Int2ByteMap.class, Int2ByteLinkedOpenHashMap::new);
                safePut(map, Int2CharMap.class, Int2CharLinkedOpenHashMap::new);
                safePut(map, Int2ShortMap.class, Int2ShortLinkedOpenHashMap::new);
                safePut(map, Int2IntMap.class, Int2IntLinkedOpenHashMap::new);
                safePut(map, Int2LongMap.class, Int2LongLinkedOpenHashMap::new);
                safePut(map, Int2FloatMap.class, Int2FloatLinkedOpenHashMap::new);
                safePut(map, Int2DoubleMap.class, Int2DoubleLinkedOpenHashMap::new);

                safePut(map, Long2ObjectMap.class, Long2ObjectLinkedOpenHashMap::new);
                safePut(map, Long2BooleanMap.class, Long2BooleanLinkedOpenHashMap::new);
                safePut(map, Long2ByteMap.class, Long2ByteLinkedOpenHashMap::new);
                safePut(map, Long2CharMap.class, Long2CharLinkedOpenHashMap::new);
                safePut(map, Long2ShortMap.class, Long2ShortLinkedOpenHashMap::new);
                safePut(map, Long2IntMap.class, Long2IntLinkedOpenHashMap::new);
                safePut(map, Long2LongMap.class, Long2LongLinkedOpenHashMap::new);
                safePut(map, Long2FloatMap.class, Long2FloatLinkedOpenHashMap::new);
                safePut(map, Long2DoubleMap.class, Long2DoubleLinkedOpenHashMap::new);

                safePut(map, Float2ObjectMap.class, Float2ObjectLinkedOpenHashMap::new);
                safePut(map, Float2BooleanMap.class, Float2BooleanLinkedOpenHashMap::new);
                safePut(map, Float2ByteMap.class, Float2ByteLinkedOpenHashMap::new);
                safePut(map, Float2CharMap.class, Float2CharLinkedOpenHashMap::new);
                safePut(map, Float2ShortMap.class, Float2ShortLinkedOpenHashMap::new);
                safePut(map, Float2IntMap.class, Float2IntLinkedOpenHashMap::new);
                safePut(map, Float2LongMap.class, Float2LongLinkedOpenHashMap::new);
                safePut(map, Float2FloatMap.class, Float2FloatLinkedOpenHashMap::new);
                safePut(map, Float2DoubleMap.class, Float2DoubleLinkedOpenHashMap::new);

                safePut(map, Double2ObjectMap.class, Double2ObjectLinkedOpenHashMap::new);
                safePut(map, Double2BooleanMap.class, Double2BooleanLinkedOpenHashMap::new);
                safePut(map, Double2ByteMap.class, Double2ByteLinkedOpenHashMap::new);
                safePut(map, Double2CharMap.class, Double2CharLinkedOpenHashMap::new);
                safePut(map, Double2ShortMap.class, Double2ShortLinkedOpenHashMap::new);
                safePut(map, Double2IntMap.class, Double2IntLinkedOpenHashMap::new);
                safePut(map, Double2LongMap.class, Double2LongLinkedOpenHashMap::new);
                safePut(map, Double2FloatMap.class, Double2FloatLinkedOpenHashMap::new);
                safePut(map, Double2DoubleMap.class, Double2DoubleLinkedOpenHashMap::new);
            }
            safePut(map, ConcurrentMap.class, x -> new ConcurrentSkipListMap<>());
            safePut(map, ConcurrentNavigableMap.class, x -> new ConcurrentSkipListMap<>());
            safePut(map, SortedMap.class, i -> new Object2ObjectAVLTreeMap<>());
            {
                safePut(map, Object2ObjectSortedMap.class, x -> new Object2ObjectAVLTreeMap<>());
                safePut(map, Object2BooleanSortedMap.class, x -> new Object2BooleanAVLTreeMap<>());
                safePut(map, Object2ByteSortedMap.class, x -> new Object2ByteAVLTreeMap<>());
                safePut(map, Object2CharSortedMap.class, x -> new Object2CharAVLTreeMap<>());
                safePut(map, Object2ShortSortedMap.class, x -> new Object2ShortAVLTreeMap<>());
                safePut(map, Object2IntSortedMap.class, x -> new Object2IntAVLTreeMap<>());
                safePut(map, Object2LongSortedMap.class, x -> new Object2LongAVLTreeMap<>());
                safePut(map, Object2FloatSortedMap.class, x -> new Object2FloatAVLTreeMap<>());
                safePut(map, Object2DoubleSortedMap.class, x -> new Object2DoubleAVLTreeMap<>());

                safePut(map, Byte2ObjectSortedMap.class, x -> new Byte2ObjectAVLTreeMap<>());
                safePut(map, Byte2BooleanSortedMap.class, x -> new Byte2BooleanAVLTreeMap());
                safePut(map, Byte2ByteSortedMap.class, x -> new Byte2ByteAVLTreeMap());
                safePut(map, Byte2CharSortedMap.class, x -> new Byte2CharAVLTreeMap());
                safePut(map, Byte2ShortSortedMap.class, x -> new Byte2ShortAVLTreeMap());
                safePut(map, Byte2IntSortedMap.class, x -> new Byte2IntAVLTreeMap());
                safePut(map, Byte2LongSortedMap.class, x -> new Byte2LongAVLTreeMap());
                safePut(map, Byte2FloatSortedMap.class, x -> new Byte2FloatAVLTreeMap());
                safePut(map, Byte2DoubleSortedMap.class, x -> new Byte2DoubleAVLTreeMap());

                safePut(map, Char2ObjectSortedMap.class, x -> new Char2ObjectAVLTreeMap<>());
                safePut(map, Char2BooleanSortedMap.class, x -> new Char2BooleanAVLTreeMap());
                safePut(map, Char2ByteSortedMap.class, x -> new Char2ByteAVLTreeMap());
                safePut(map, Char2CharSortedMap.class, x -> new Char2CharAVLTreeMap());
                safePut(map, Char2ShortSortedMap.class, x -> new Char2ShortAVLTreeMap());
                safePut(map, Char2IntSortedMap.class, x -> new Char2IntAVLTreeMap());
                safePut(map, Char2LongSortedMap.class, x -> new Char2LongAVLTreeMap());
                safePut(map, Char2FloatSortedMap.class, x -> new Char2FloatAVLTreeMap());
                safePut(map, Char2DoubleSortedMap.class, x -> new Char2DoubleAVLTreeMap());

                safePut(map, Short2ObjectSortedMap.class, x -> new Short2ObjectAVLTreeMap<>());
                safePut(map, Short2BooleanSortedMap.class, x -> new Short2BooleanAVLTreeMap());
                safePut(map, Short2ByteSortedMap.class, x -> new Short2ByteAVLTreeMap());
                safePut(map, Short2CharSortedMap.class, x -> new Short2CharAVLTreeMap());
                safePut(map, Short2ShortSortedMap.class, x -> new Short2ShortAVLTreeMap());
                safePut(map, Short2IntSortedMap.class, x -> new Short2IntAVLTreeMap());
                safePut(map, Short2LongSortedMap.class, x -> new Short2LongAVLTreeMap());
                safePut(map, Short2FloatSortedMap.class, x -> new Short2FloatAVLTreeMap());
                safePut(map, Short2DoubleSortedMap.class, x -> new Short2DoubleAVLTreeMap());

                safePut(map, Int2ObjectSortedMap.class, x -> new Int2ObjectAVLTreeMap<>());
                safePut(map, Int2BooleanSortedMap.class, x -> new Int2BooleanAVLTreeMap());
                safePut(map, Int2ByteSortedMap.class, x -> new Int2ByteAVLTreeMap());
                safePut(map, Int2CharSortedMap.class, x -> new Int2CharAVLTreeMap());
                safePut(map, Int2ShortSortedMap.class, x -> new Int2ShortAVLTreeMap());
                safePut(map, Int2IntSortedMap.class, x -> new Int2IntAVLTreeMap());
                safePut(map, Int2LongSortedMap.class, x -> new Int2LongAVLTreeMap());
                safePut(map, Int2FloatSortedMap.class, x -> new Int2FloatAVLTreeMap());
                safePut(map, Int2DoubleSortedMap.class, x -> new Int2DoubleAVLTreeMap());

                safePut(map, Long2ObjectSortedMap.class, x -> new Long2ObjectAVLTreeMap<>());
                safePut(map, Long2BooleanSortedMap.class, x -> new Long2BooleanAVLTreeMap());
                safePut(map, Long2ByteSortedMap.class, x -> new Long2ByteAVLTreeMap());
                safePut(map, Long2CharSortedMap.class, x -> new Long2CharAVLTreeMap());
                safePut(map, Long2ShortSortedMap.class, x -> new Long2ShortAVLTreeMap());
                safePut(map, Long2IntSortedMap.class, x -> new Long2IntAVLTreeMap());
                safePut(map, Long2LongSortedMap.class, x -> new Long2LongAVLTreeMap());
                safePut(map, Long2FloatSortedMap.class, x -> new Long2FloatAVLTreeMap());
                safePut(map, Long2DoubleSortedMap.class, x -> new Long2DoubleAVLTreeMap());

                safePut(map, Float2ObjectSortedMap.class, x -> new Float2ObjectAVLTreeMap<>());
                safePut(map, Float2BooleanSortedMap.class, x -> new Float2BooleanAVLTreeMap());
                safePut(map, Float2ByteSortedMap.class, x -> new Float2ByteAVLTreeMap());
                safePut(map, Float2CharSortedMap.class, x -> new Float2CharAVLTreeMap());
                safePut(map, Float2ShortSortedMap.class, x -> new Float2ShortAVLTreeMap());
                safePut(map, Float2IntSortedMap.class, x -> new Float2IntAVLTreeMap());
                safePut(map, Float2LongSortedMap.class, x -> new Float2LongAVLTreeMap());
                safePut(map, Float2FloatSortedMap.class, x -> new Float2FloatAVLTreeMap());
                safePut(map, Float2DoubleSortedMap.class, x -> new Float2DoubleAVLTreeMap());

                safePut(map, Double2ObjectSortedMap.class, x -> new Double2ObjectAVLTreeMap<>());
                safePut(map, Double2BooleanSortedMap.class, x -> new Double2BooleanAVLTreeMap());
                safePut(map, Double2ByteSortedMap.class, x -> new Double2ByteAVLTreeMap());
                safePut(map, Double2CharSortedMap.class, x -> new Double2CharAVLTreeMap());
                safePut(map, Double2ShortSortedMap.class, x -> new Double2ShortAVLTreeMap());
                safePut(map, Double2IntSortedMap.class, x -> new Double2IntAVLTreeMap());
                safePut(map, Double2LongSortedMap.class, x -> new Double2LongAVLTreeMap());
                safePut(map, Double2FloatSortedMap.class, x -> new Double2FloatAVLTreeMap());
                safePut(map, Double2DoubleSortedMap.class, x -> new Double2DoubleAVLTreeMap());
            }

            safePutUnmodf(unmodMap, Object2ObjectMap.class, Object2ObjectMaps::unmodifiable);
            safePutUnmodf(unmodMap, Object2ObjectMap.class, Object2ObjectMaps::unmodifiable);
            safePutUnmodf(unmodMap, Object2BooleanMap.class, Object2BooleanMaps::unmodifiable);
            safePutUnmodf(unmodMap, Object2ByteMap.class, Object2ByteMaps::unmodifiable);
            safePutUnmodf(unmodMap, Object2CharMap.class, Object2CharMaps::unmodifiable);
            safePutUnmodf(unmodMap, Object2ShortMap.class, Object2ShortMaps::unmodifiable);
            safePutUnmodf(unmodMap, Object2IntMap.class, Object2IntMaps::unmodifiable);
            safePutUnmodf(unmodMap, Object2LongMap.class, Object2LongMaps::unmodifiable);
            safePutUnmodf(unmodMap, Object2FloatMap.class, Object2FloatMaps::unmodifiable);
            safePutUnmodf(unmodMap, Object2DoubleMap.class, Object2DoubleMaps::unmodifiable);

            safePutUnmodf(unmodMap, Byte2ObjectMap.class, Byte2ObjectMaps::unmodifiable);
            safePutUnmodf(unmodMap, Byte2BooleanMap.class, Byte2BooleanMaps::unmodifiable);
            safePutUnmodf(unmodMap, Byte2ByteMap.class, Byte2ByteMaps::unmodifiable);
            safePutUnmodf(unmodMap, Byte2CharMap.class, Byte2CharMaps::unmodifiable);
            safePutUnmodf(unmodMap, Byte2ShortMap.class, Byte2ShortMaps::unmodifiable);
            safePutUnmodf(unmodMap, Byte2IntMap.class, Byte2IntMaps::unmodifiable);
            safePutUnmodf(unmodMap, Byte2LongMap.class, Byte2LongMaps::unmodifiable);
            safePutUnmodf(unmodMap, Byte2FloatMap.class, Byte2FloatMaps::unmodifiable);
            safePutUnmodf(unmodMap, Byte2DoubleMap.class, Byte2DoubleMaps::unmodifiable);

            safePutUnmodf(unmodMap, Char2ObjectMap.class, Char2ObjectMaps::unmodifiable);
            safePutUnmodf(unmodMap, Char2BooleanMap.class, Char2BooleanMaps::unmodifiable);
            safePutUnmodf(unmodMap, Char2ByteMap.class, Char2ByteMaps::unmodifiable);
            safePutUnmodf(unmodMap, Char2CharMap.class, Char2CharMaps::unmodifiable);
            safePutUnmodf(unmodMap, Char2ShortMap.class, Char2ShortMaps::unmodifiable);
            safePutUnmodf(unmodMap, Char2IntMap.class, Char2IntMaps::unmodifiable);
            safePutUnmodf(unmodMap, Char2LongMap.class, Char2LongMaps::unmodifiable);
            safePutUnmodf(unmodMap, Char2FloatMap.class, Char2FloatMaps::unmodifiable);
            safePutUnmodf(unmodMap, Char2DoubleMap.class, Char2DoubleMaps::unmodifiable);

            safePutUnmodf(unmodMap, Short2ObjectMap.class, Short2ObjectMaps::unmodifiable);
            safePutUnmodf(unmodMap, Short2BooleanMap.class, Short2BooleanMaps::unmodifiable);
            safePutUnmodf(unmodMap, Short2ByteMap.class, Short2ByteMaps::unmodifiable);
            safePutUnmodf(unmodMap, Short2CharMap.class, Short2CharMaps::unmodifiable);
            safePutUnmodf(unmodMap, Short2ShortMap.class, Short2ShortMaps::unmodifiable);
            safePutUnmodf(unmodMap, Short2IntMap.class, Short2IntMaps::unmodifiable);
            safePutUnmodf(unmodMap, Short2LongMap.class, Short2LongMaps::unmodifiable);
            safePutUnmodf(unmodMap, Short2FloatMap.class, Short2FloatMaps::unmodifiable);
            safePutUnmodf(unmodMap, Short2DoubleMap.class, Short2DoubleMaps::unmodifiable);

            safePutUnmodf(unmodMap, Int2ObjectMap.class, Int2ObjectMaps::unmodifiable);
            safePutUnmodf(unmodMap, Int2BooleanMap.class, Int2BooleanMaps::unmodifiable);
            safePutUnmodf(unmodMap, Int2ByteMap.class, Int2ByteMaps::unmodifiable);
            safePutUnmodf(unmodMap, Int2CharMap.class, Int2CharMaps::unmodifiable);
            safePutUnmodf(unmodMap, Int2ShortMap.class, Int2ShortMaps::unmodifiable);
            safePutUnmodf(unmodMap, Int2IntMap.class, Int2IntMaps::unmodifiable);
            safePutUnmodf(unmodMap, Int2LongMap.class, Int2LongMaps::unmodifiable);
            safePutUnmodf(unmodMap, Int2FloatMap.class, Int2FloatMaps::unmodifiable);
            safePutUnmodf(unmodMap, Int2DoubleMap.class, Int2DoubleMaps::unmodifiable);

            safePutUnmodf(unmodMap, Long2ObjectMap.class, Long2ObjectMaps::unmodifiable);
            safePutUnmodf(unmodMap, Long2BooleanMap.class, Long2BooleanMaps::unmodifiable);
            safePutUnmodf(unmodMap, Long2ByteMap.class, Long2ByteMaps::unmodifiable);
            safePutUnmodf(unmodMap, Long2CharMap.class, Long2CharMaps::unmodifiable);
            safePutUnmodf(unmodMap, Long2ShortMap.class, Long2ShortMaps::unmodifiable);
            safePutUnmodf(unmodMap, Long2IntMap.class, Long2IntMaps::unmodifiable);
            safePutUnmodf(unmodMap, Long2LongMap.class, Long2LongMaps::unmodifiable);
            safePutUnmodf(unmodMap, Long2FloatMap.class, Long2FloatMaps::unmodifiable);
            safePutUnmodf(unmodMap, Long2DoubleMap.class, Long2DoubleMaps::unmodifiable);

            safePutUnmodf(unmodMap, Float2ObjectMap.class, Float2ObjectMaps::unmodifiable);
            safePutUnmodf(unmodMap, Float2BooleanMap.class, Float2BooleanMaps::unmodifiable);
            safePutUnmodf(unmodMap, Float2ByteMap.class, Float2ByteMaps::unmodifiable);
            safePutUnmodf(unmodMap, Float2CharMap.class, Float2CharMaps::unmodifiable);
            safePutUnmodf(unmodMap, Float2ShortMap.class, Float2ShortMaps::unmodifiable);
            safePutUnmodf(unmodMap, Float2IntMap.class, Float2IntMaps::unmodifiable);
            safePutUnmodf(unmodMap, Float2LongMap.class, Float2LongMaps::unmodifiable);
            safePutUnmodf(unmodMap, Float2FloatMap.class, Float2FloatMaps::unmodifiable);
            safePutUnmodf(unmodMap, Float2DoubleMap.class, Float2DoubleMaps::unmodifiable);

            safePutUnmodf(unmodMap, Double2ObjectMap.class, Double2ObjectMaps::unmodifiable);
            safePutUnmodf(unmodMap, Double2BooleanMap.class, Double2BooleanMaps::unmodifiable);
            safePutUnmodf(unmodMap, Double2ByteMap.class, Double2ByteMaps::unmodifiable);
            safePutUnmodf(unmodMap, Double2CharMap.class, Double2CharMaps::unmodifiable);
            safePutUnmodf(unmodMap, Double2ShortMap.class, Double2ShortMaps::unmodifiable);
            safePutUnmodf(unmodMap, Double2IntMap.class, Double2IntMaps::unmodifiable);
            safePutUnmodf(unmodMap, Double2LongMap.class, Double2LongMaps::unmodifiable);
            safePutUnmodf(unmodMap, Double2FloatMap.class, Double2FloatMaps::unmodifiable);
            safePutUnmodf(unmodMap, Double2DoubleMap.class, Double2DoubleMaps::unmodifiable);
        }
    }
}
