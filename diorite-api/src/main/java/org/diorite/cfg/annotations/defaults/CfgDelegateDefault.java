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

package org.diorite.cfg.annotations.defaults;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Used to define default value of custom object type.
 * Object is read using String path to static non-arg method
 * that should return default value.
 * <br>
 * Additionally you can use few placeholders:
 * {@literal {emptyMap} -> new HashMap<>(1);}
 * {@literal {emptyList} -> new ArrayList<>(1);}
 * {@literal {emptySet} -> new HashSet<>(1);}
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.TYPE})
public @interface CfgDelegateDefault
{
    /**
     * String should be a path to default value, like package.Class#getDefaultValue
     * Mehod must be static and no-args, you can skip class with method is in this same
     * class as field that will use this default value.
     * <br>
     * Additionally you can use few placeholders:
     * {@literal {emptyMap} -> new HashMap<>(1);}
     * {@literal {emptyList} -> new ArrayList<>(1);}
     * {@literal {emptySet} -> new HashSet<>(1);}
     *
     * @return name of method (and class) that returns default value or placeholder.
     */
    String value(); // it should show no-args method that will return default value, like package.Class#getDefaultValue
}
