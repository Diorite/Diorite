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

import java.io.File;

public interface ChildPlugin extends BasePlugin
{
    String CHILD_SEPARATOR = "::";

    BasePlugin getParent();

    /**
     * Returns only last part of name, without parent plugins.
     *
     * @return simple name, without parent names.
     */
    default String getSimpleName()
    {
        final int i = this.getName().lastIndexOf(CHILD_SEPARATOR);
        if (i == - 1)
        {
            return this.getName();
        }
        return this.getName().substring(i + 2);
    }

    @Override
    default File getDataFolder()
    {
        return new File(this.getParent().getDataFolder(), this.getSimpleName());
    }

    @Override
    default String getFullName()
    {
        return this.getSimpleName() + " v" + this.getVersion() + " (" + this.getParent().getFullName() + ")";
    }
}
