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

package org.diorite.cfg.system;

/**
 * Simple interface for classes contains basic informations about config field.
 */
public interface CfgEntryData
{
    /**
     * Returns value of selected {@link FieldOptions} from this config field.
     * May return null.
     *
     * @param option option to get.
     * @param <T>    type of value.
     *
     * @return value of option or null.
     */
    <T> T getOption(FieldOptions option);

    /**
     * Returns value of selected {@link FieldOptions} from this config field.
     *
     * @param option option to get.
     * @param def    default value of option.
     * @param <T>    type of value.
     *
     * @return value of option or default one.
     */
    <T> T getOption(FieldOptions option, T def);

    /**
     * @return header comment of node, may be null.
     */
    String getHeader();

    /**
     * @return footer comment of node, may be null.
     */
    String getFooter();
}
