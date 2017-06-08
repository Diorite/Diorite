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

package org.diorite.config;

import javax.annotation.Nullable;

import java.lang.reflect.Method;

/**
 * Represent results of {@link ConfigPropertyAction#matchesAction(Method)} method.
 */
public final class ActionMatcherResult
{
    private final           boolean matching;
    private                 boolean validatedName;
    @Nullable private final String  propertyName;

    public ActionMatcherResult(boolean matching, @Nullable String propertyName)
    {
        this.matching = matching;
        this.propertyName = propertyName;
        this.validatedName = false;
    }

    public boolean isValidatedName()
    {
        return this.validatedName;
    }

    public void setValidatedName(boolean validatedName)
    {
        this.validatedName = validatedName;
    }

    /**
     * Returns true if result if positive, and given method can implement given action.
     *
     * @return true if result if positive, and given method can implement given action.
     */
    public boolean isMatching()
    {
        return this.matching;
    }

    /**
     * Returns property name if found.
     *
     * @return property name if found.
     *
     * @exception IllegalStateException
     *         if property name was not found, so if {@link #isMatching()} is false.
     */
    public String getPropertyName() throws IllegalStateException
    {
        if (this.propertyName == null)
        {
            throw new IllegalStateException("Property name not found.");
        }
        return this.propertyName;
    }
}
