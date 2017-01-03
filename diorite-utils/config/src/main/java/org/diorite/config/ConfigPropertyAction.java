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

import org.diorite.commons.reflections.MethodInvoker;

/**
 * Represent config property action, like get/set/add methods.
 */
public interface ConfigPropertyAction
{
//    /**
//     * Returns property regex pattern. <br>
//     * Action pattern MUST contains one named matching group named `property` that will match property name: {@literal (?<property>[A-Z0-9].*)}
//     *
//     * @return property regex pattern.
//     */
//    Pattern getActionPattern();

    /**
     * Returns result object with data if given method matches action patterns and name of property.
     *
     * @param method
     *         method to check.
     *
     * @return result object with data if given method matches action patterns and name of property.
     */
    ActionMatcherResult matchesAction(Method method);

    /**
     * Returns property action name.
     *
     * @return property action name.
     */
    String getActionName();

    /**
     * Perform this operation on given object with given values.
     *
     * @param method
     *         used method.
     * @param value
     *         value of property.
     * @param args
     *         arguments of action if any.
     *
     * @return result of action.
     */
    @Nullable
    Object perform(MethodInvoker method, ConfigPropertyValue<?> value, Object... args);
}
