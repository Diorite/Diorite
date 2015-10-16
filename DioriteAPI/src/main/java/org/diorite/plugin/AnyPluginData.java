/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2015 Diorite (by Bartłomiej Mazur (aka GotoFinal))
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

package org.diorite.plugin;

interface AnyPluginData
{

    /**
     * Returns name of plugin, can't be null.
     *
     * @return name of plugin.
     */
    String getName();

    /**
     * Returns version of plugin, can't be null.
     *
     * @return version of plugin.
     */
    String getVersion();

    /**
     * Returns log prefix of plugin, can't be null.
     *
     * @return prefix of plugin.
     */
    String getPrefix();

    /**
     * Returns author/s of plugin, can't be null, may contains custom text.
     *
     * @return author/s of plugin.
     */
    String getAuthor();

    /**
     * Returns description of plugin, can't be null.
     *
     * @return description of plugin.
     */
    String getDescription();

    /**
     * Returns website of plugin, can't be null.
     *
     * @return website of plugin.
     */
    String getWebsite();
}
