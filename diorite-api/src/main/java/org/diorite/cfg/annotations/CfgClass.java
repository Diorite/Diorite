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

package org.diorite.cfg.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * You can annotate classes used by your configuration system,
 * additionally it provide few options:
 * option to disable auto-using all fields. (enabled by default)
 * option to disable using super class. (enabled by default)
 * option to disable skipping transient fields. (enabled by default)
 * option to skip selected fields. (empty by default)
 *
 * @see CfgExclude
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface CfgClass
{

    /**
     * Name of configuration unit, should be class name.
     *
     * @return Name of configuration unit
     */
    String name();

    /**
     * true by default.
     * Disabling that gives you ability to manually select fields by {@link CfgField} annotation.
     *
     * @return if all fields non-excluded should be used.
     */
    boolean allFields() default true;

    /**
     * true by default.
     *
     * @return if fields from super class should be used too.
     */
    boolean superFields() default true;

    /**
     * true by default.
     *
     * @return if transient fields should be ignored.
     */
    boolean ignoreTransient() default true;

    /**
     * Fields to exclude (type name)
     *
     * @return list of field names to skip.
     *
     * @see CfgExclude
     */
    String[] excludeFields() default {};
}
