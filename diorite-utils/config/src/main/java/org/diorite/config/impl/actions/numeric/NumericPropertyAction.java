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

package org.diorite.config.impl.actions.numeric;

import javax.annotation.Nullable;

import org.diorite.commons.reflections.DioriteReflectionUtils;
import org.diorite.commons.reflections.MethodInvoker;
import org.diorite.config.ConfigPropertyValue;
import org.diorite.config.impl.actions.AbstractPropertyAction;

@SuppressWarnings({"rawtypes", "unchecked"})
public abstract class NumericPropertyAction extends AbstractPropertyAction
{

    protected NumericPropertyAction(String name, String... pattern)
    {
        super(name, pattern);
    }

    @Override
    protected boolean matchesAction0(MethodInvoker method, Class<?>[] parameters)
    {
        Class<?> returnType = method.getReturnType();
        if (parameters.length != 1)
        {
            return false;
        }
        Class<?> parameterType = parameters[0];

        if ((returnType != void.class) && (! returnType.isAssignableFrom(parameterType) && ! (returnType.isPrimitive() && parameterType.isPrimitive())))
        {
            return false;
        }

        return Number.class.isAssignableFrom(DioriteReflectionUtils.getWrapperClass(parameterType));
    }

    @Nullable
    static Object performNumericAdd(ConfigPropertyValue value, Object... args)
    {
        Class rawType = value.getRawType();
        Number numberValue = (Number) value.getRawValue();
        Number argNumber = (Number) args[0];
        if (numberValue == null)
        {
            numberValue = 0;
        }
        if (rawType.isPrimitive())
        {
            if (argNumber == null)
            {
                argNumber = 0;
            }
            if (rawType == byte.class)
            {
                byte rawValue = numberValue.byteValue();
                value.setRawValue(rawValue + argNumber.byteValue());
            }
            else if (rawType == short.class)
            {
                short rawValue = numberValue.shortValue();
                value.setRawValue(rawValue + argNumber.shortValue());
            }
            else if (rawType == int.class)
            {
                int rawValue = numberValue.intValue();
                value.setRawValue(rawValue + argNumber.intValue());
            }
            else if (rawType == long.class)
            {
                long rawValue = numberValue.longValue();
                value.setRawValue(rawValue + argNumber.longValue());
            }
            else if (rawType == float.class)
            {
                float rawValue = numberValue.floatValue();
                value.setRawValue(rawValue + argNumber.floatValue());
            }
            else if (rawType == double.class)
            {
                double rawValue = numberValue.doubleValue();
                value.setRawValue(rawValue + argNumber.doubleValue());
            }
            else
            {
                throw new IllegalStateException("Unexpected type: " + rawType);
            }
            return numberValue;
        }
        if (argNumber == null)
        {
            value.setRawValue(null);
            return numberValue;
        }
        if (rawType == Byte.class)
        {
            byte rawValue = numberValue.byteValue();
            value.setRawValue(rawValue + argNumber.byteValue());
        }
        else if (rawType == Short.class)
        {
            short rawValue = numberValue.shortValue();
            value.setRawValue(rawValue + argNumber.shortValue());
        }
        else if (rawType == Integer.class)
        {
            int rawValue = numberValue.intValue();
            value.setRawValue(rawValue + argNumber.intValue());
        }
        else if (rawType == Long.class)
        {
            long rawValue = numberValue.longValue();
            value.setRawValue(rawValue + argNumber.longValue());
        }
        else if (rawType == Float.class)
        {
            float rawValue = numberValue.floatValue();
            value.setRawValue(rawValue + argNumber.floatValue());
        }
        else if (rawType == Double.class)
        {
            double rawValue = numberValue.doubleValue();
            value.setRawValue(rawValue + argNumber.doubleValue());
        }
        else
        {
            throw new IllegalStateException("Unexpected type: " + rawType);
        }
        return numberValue;
    }

    @Nullable
    static Object performNumericSub(ConfigPropertyValue value, Object... args)
    {
        Class rawType = value.getRawType();
        Number numberValue = (Number) value.getRawValue();
        Number argNumber = (Number) args[0];
        if (numberValue == null)
        {
            numberValue = 0;
        }
        if (rawType.isPrimitive())
        {
            if (argNumber == null)
            {
                argNumber = 0;
            }
            if (rawType == byte.class)
            {
                byte rawValue = numberValue.byteValue();
                value.setRawValue(rawValue - argNumber.byteValue());
            }
            else if (rawType == short.class)
            {
                short rawValue = numberValue.shortValue();
                value.setRawValue(rawValue - argNumber.shortValue());
            }
            else if (rawType == int.class)
            {
                int rawValue = numberValue.intValue();
                value.setRawValue(rawValue - argNumber.intValue());
            }
            else if (rawType == long.class)
            {
                long rawValue = numberValue.longValue();
                value.setRawValue(rawValue - argNumber.longValue());
            }
            else if (rawType == float.class)
            {
                float rawValue = numberValue.floatValue();
                value.setRawValue(rawValue - argNumber.floatValue());
            }
            else if (rawType == double.class)
            {
                double rawValue = numberValue.doubleValue();
                value.setRawValue(rawValue - argNumber.doubleValue());
            }
            else
            {
                throw new IllegalStateException("Unexpected type: " + rawType);
            }
            return numberValue;
        }
        if (argNumber == null)
        {
            value.setRawValue(null);
            return numberValue;
        }
        if (rawType == Byte.class)
        {
            byte rawValue = numberValue.byteValue();
            value.setRawValue(rawValue - argNumber.byteValue());
        }
        else if (rawType == Short.class)
        {
            short rawValue = numberValue.shortValue();
            value.setRawValue(rawValue - argNumber.shortValue());
        }
        else if (rawType == Integer.class)
        {
            int rawValue = numberValue.intValue();
            value.setRawValue(rawValue - argNumber.intValue());
        }
        else if (rawType == Long.class)
        {
            long rawValue = numberValue.longValue();
            value.setRawValue(rawValue - argNumber.longValue());
        }
        else if (rawType == Float.class)
        {
            float rawValue = numberValue.floatValue();
            value.setRawValue(rawValue - argNumber.floatValue());
        }
        else if (rawType == Double.class)
        {
            double rawValue = numberValue.doubleValue();
            value.setRawValue(rawValue - argNumber.doubleValue());
        }
        else
        {
            throw new IllegalStateException("Unexpected type: " + rawType);
        }
        return numberValue;
    }

    @Nullable
    static Object performNumericMulti(ConfigPropertyValue value, Object... args)
    {
        Class rawType = value.getRawType();
        Number numberValue = (Number) value.getRawValue();
        Number argNumber = (Number) args[0];
        if (numberValue == null)
        {
            numberValue = 0;
        }
        if (rawType.isPrimitive())
        {
            if (argNumber == null)
            {
                argNumber = 0;
            }
            if (rawType == byte.class)
            {
                byte rawValue = numberValue.byteValue();
                value.setRawValue(rawValue * argNumber.byteValue());
            }
            else if (rawType == short.class)
            {
                short rawValue = numberValue.shortValue();
                value.setRawValue(rawValue * argNumber.shortValue());
            }
            else if (rawType == int.class)
            {
                int rawValue = numberValue.intValue();
                value.setRawValue(rawValue * argNumber.intValue());
            }
            else if (rawType == long.class)
            {
                long rawValue = numberValue.longValue();
                value.setRawValue(rawValue * argNumber.longValue());
            }
            else if (rawType == float.class)
            {
                float rawValue = numberValue.floatValue();
                value.setRawValue(rawValue * argNumber.floatValue());
            }
            else if (rawType == double.class)
            {
                double rawValue = numberValue.doubleValue();
                value.setRawValue(rawValue * argNumber.doubleValue());
            }
            else
            {
                throw new IllegalStateException("Unexpected type: " + rawType);
            }
            return numberValue;
        }
        if (argNumber == null)
        {
            value.setRawValue(null);
            return numberValue;
        }
        if (rawType == Byte.class)
        {
            byte rawValue = numberValue.byteValue();
            value.setRawValue(rawValue * argNumber.byteValue());
        }
        else if (rawType == Short.class)
        {
            short rawValue = numberValue.shortValue();
            value.setRawValue(rawValue * argNumber.shortValue());
        }
        else if (rawType == Integer.class)
        {
            int rawValue = numberValue.intValue();
            value.setRawValue(rawValue * argNumber.intValue());
        }
        else if (rawType == Long.class)
        {
            long rawValue = numberValue.longValue();
            value.setRawValue(rawValue * argNumber.longValue());
        }
        else if (rawType == Float.class)
        {
            float rawValue = numberValue.floatValue();
            value.setRawValue(rawValue * argNumber.floatValue());
        }
        else if (rawType == Double.class)
        {
            double rawValue = numberValue.doubleValue();
            value.setRawValue(rawValue * argNumber.doubleValue());
        }
        else
        {
            throw new IllegalStateException("Unexpected type: " + rawType);
        }
        return numberValue;
    }

    @Nullable
    static Object performNumericDiv(ConfigPropertyValue value, Object... args)
    {
        Class rawType = value.getRawType();
        Number numberValue = (Number) value.getRawValue();
        Number argNumber = (Number) args[0];
        if (numberValue == null)
        {
            numberValue = 0;
        }
        if (rawType.isPrimitive())
        {
            if (argNumber == null)
            {
                argNumber = 0;
            }
            if (rawType == byte.class)
            {
                byte rawValue = numberValue.byteValue();
                value.setRawValue(rawValue / argNumber.byteValue());
            }
            else if (rawType == short.class)
            {
                short rawValue = numberValue.shortValue();
                value.setRawValue(rawValue / argNumber.shortValue());
            }
            else if (rawType == int.class)
            {
                int rawValue = numberValue.intValue();
                value.setRawValue(rawValue / argNumber.intValue());
            }
            else if (rawType == long.class)
            {
                long rawValue = numberValue.longValue();
                value.setRawValue(rawValue / argNumber.longValue());
            }
            else if (rawType == float.class)
            {
                float rawValue = numberValue.floatValue();
                value.setRawValue(rawValue / argNumber.floatValue());
            }
            else if (rawType == double.class)
            {
                double rawValue = numberValue.doubleValue();
                value.setRawValue(rawValue / argNumber.doubleValue());
            }
            else
            {
                throw new IllegalStateException("Unexpected type: " + rawType);
            }
            return numberValue;
        }
        if (argNumber == null)
        {
            value.setRawValue(null);
            return numberValue;
        }
        if (rawType == Byte.class)
        {
            byte rawValue = numberValue.byteValue();
            value.setRawValue(rawValue / argNumber.byteValue());
        }
        else if (rawType == Short.class)
        {
            short rawValue = numberValue.shortValue();
            value.setRawValue(rawValue / argNumber.shortValue());
        }
        else if (rawType == Integer.class)
        {
            int rawValue = numberValue.intValue();
            value.setRawValue(rawValue / argNumber.intValue());
        }
        else if (rawType == Long.class)
        {
            long rawValue = numberValue.longValue();
            value.setRawValue(rawValue / argNumber.longValue());
        }
        else if (rawType == Float.class)
        {
            float rawValue = numberValue.floatValue();
            value.setRawValue(rawValue / argNumber.floatValue());
        }
        else if (rawType == Double.class)
        {
            double rawValue = numberValue.doubleValue();
            value.setRawValue(rawValue / argNumber.doubleValue());
        }
        else
        {
            throw new IllegalStateException("Unexpected type: " + rawType);
        }
        return numberValue;
    }
}
