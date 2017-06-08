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

import java.lang.reflect.Method;

/**
 * Represents instance of property action, pair of the action and a method signature.
 */
public interface ConfigPropertyActionInstance
{
    /**
     * Returns property action object.
     *
     * @return property action object.
     */
    ConfigPropertyAction getPropertyAction();

    /**
     * Returns method signature.
     *
     * @return method signature.
     */
    MethodSignature getMethodSignature();

    /**
     * Returns true if this action is used to declare property type.
     *
     * @return true if this action is used to declare property type.
     */
    default boolean declaresProperty()
    {
        return this.getPropertyAction().declaresProperty();
    }

    /**
     * Returns result object with data if given method matches action patterns and name of property.
     *
     * @param method
     *         method to check.
     *
     * @return result object with data if given method matches action patterns and name of property.
     */
    default ActionMatcherResult matchesAction(Method method)
    {
        return this.getPropertyAction().matchesAction(method);
    }

    /**
     * Returns property action name.
     *
     * @return property action name.
     */
    default String getActionName()
    {
        return this.getPropertyAction().getActionName();
    }
}
