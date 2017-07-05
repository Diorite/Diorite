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

package org.diorite.command.parser;

import java.util.Collection;
import java.util.function.Function;
import java.util.function.Supplier;

import org.apache.commons.lang3.builder.Builder;

/**
 * Represent builder for {@link CustomArgumentParser} <br>
 * Each parser should only use one factor of custom object, if you have Guild object that can be matched both by name and tag, two different parsers should
 * be registered.
 *
 * @param <K>
 *         key type. (value used by user in command)
 * @param <T>
 *         value type. (value used by command code)
 */
public interface CustomArgumentBuilder<K, T> extends Builder<CustomArgumentParser<K, T>>
{
    /**
     * Sets name of argument factor used by this parser.
     *
     * Each parser should only use one factor of custom object, if you have Guild object that can be matched both by name and tag, two different parsers should
     * be registered.
     *
     * @param name
     *         name of argument factor used by this parser.
     *
     * @return this same builder for method chain.
     */
    CustomArgumentBuilder<K, T> withFactorName(String name);

    /**
     * Sets own parser for this custom argument.
     *
     * @param parser
     *         parser for this custom argument.
     *
     * @return this same builder for method chain.
     */
    CustomArgumentBuilder<K, T> withParser(TypeParser<T> parser);

    /**
     * Sets supplier of all available elements.
     *
     * @param elements
     *         supplier of all available elements.
     *
     * @return this same builder for method chain.
     */
    CustomArgumentBuilder<K, T> withElements(Supplier<Collection<? extends T>> elements);

    /**
     * Sets optimized filter function, it's optional but can be used to improve performance for large collections or when it is needed to fetch data from
     * database.
     *
     * @param filterFunction
     *         filter function.
     *
     * @return this same builder for method chain.
     */
    CustomArgumentBuilder<K, T> withElementsFilter(Function<String, Collection<? extends T>> filterFunction);

    /**
     * Provide function to resolve given argument key to custom argument object.
     *
     * @param resolver
     *         function to resolve given argument key to custom argument object.
     *
     * @return this same builder for method chain.
     */
    CustomArgumentBuilder<K, T> withResolver(Function<K, ? extends T> resolver);

    /**
     * Provide function to change custom argument object to command argument representation.
     *
     * @param representer
     *         function to change custom argument object to command argument representation.
     *
     * @return this same builder for method chain.
     */
    CustomArgumentBuilder<K, T> withRepresenter(Function<? extends T, K> representer);

    /**
     * When build is called parser is created and registered.
     *
     * @return created parser instance.
     */
    @Override
    CustomArgumentParser<K, T> build();
}
