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

package org.diorite.script;

import org.diorite.plugin.Plugin;

/**
 * Represent factory of script engines for given language.
 */
public interface ScriptEngineFactory
{
    /**
     * Returns plugin providing this factory.
     *
     * @return plugin providing this factory.
     */
    Plugin getPlugin();

    /**
     * Returns language of script engines created by this factory.
     *
     * @return language of script engines created by this factory.
     */
    String getLanguage();

    /**
     * Create new script engine.
     *
     * @return created script engine.
     */
    ScriptEngine create();

    /**
     * Returns priority of this factory. <br>
     * When using {@link ScriptManager#getFactoryFor(String)} and there is more than one factory for given language, highest
     * priority one will be returned, if more than one factory have this same priority, first registered will be selected.
     *
     * @return priority of this factory.
     */
    int priority();
}
