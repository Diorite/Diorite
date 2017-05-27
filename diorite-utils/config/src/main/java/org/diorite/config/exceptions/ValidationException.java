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

package org.diorite.config.exceptions;

import javax.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.commons.reflections.DioriteReflectionUtils;
import org.diorite.config.Config;
import org.diorite.config.ConfigPropertyTemplate;
import org.diorite.config.ConfigPropertyValue;
import org.diorite.config.ConfigTemplate;

/**
 * Thrown by internal code when validation fails, should not be thrown from validators!
 */
public final class ValidationException extends RuntimeException
{
    private static final long serialVersionUID = 0;

    private <T> ValidationException(String message, Throwable cause)
    {
        super(message, cause);
    }

    private <T> ValidationException(String message)
    {
        super(message);
    }

    static <T> ValidationException of(ConfigPropertyValue<T> template, @Nullable T value, @Nullable String message, @Nullable Throwable cause)
    {
        if ((message == null) && (cause != null))
        {
            message = cause.getMessage();
        }
        if (message == null)
        {
            message = "unknown error";
        }
        StringBuilder expBuilder = new StringBuilder();
        ConfigPropertyTemplate<T> property = template.getProperty();
        Config declaringConfig = template.getDeclaringConfig();
        ConfigTemplate<?> cfgTemplate = declaringConfig.template();
        expBuilder.append("Failed to validate value for property: '").append(message).append("'\n")
                  .append("    Property name: ").append(template.getName()).append(" (").append(property.getOriginalName()).append(")\n")
                  .append("    Property type: ").append(template.getGenericType()).append("\n")
                  .append("    Config: ").append(declaringConfig.name()).append(" (").append(cfgTemplate.getConfigType().getCanonicalName()).append(")\n");

        List<Exception> suppressed = new ArrayList<>(2);
        try
        {
            T rawValue = template.getRawValue();
            expBuilder.append("    Current value: ").append(rawValue);

            if ((rawValue != null) && ! DioriteReflectionUtils.getPrimitive(rawValue.getClass()).isPrimitive())
            {
                expBuilder.append(" (").append(ReflectionToStringBuilder.toString(template.getRawType(), ToStringStyle.JSON_STYLE)).append(")");
            }
            expBuilder.append("\n");
        }
        catch (Exception ignored)
        {
            suppressed.add(ignored);
        }

        expBuilder.append("    Failed value: ").append(value);
        if ((value != null) && ! DioriteReflectionUtils.getPrimitive(value.getClass()).isPrimitive())
        {
            try
            {
                expBuilder.append(" (").append(ReflectionToStringBuilder.toString(value, ToStringStyle.JSON_STYLE)).append(")");
            }
            catch (Exception ignored)
            {
                suppressed.add(ignored);
            }
        }
        if (cause != null)
        {
            for (Exception exception : suppressed)
            {
                cause.addSuppressed(exception);
            }
            return new ValidationException(expBuilder.toString(), cause);
        }
        else
        {
            ValidationException validationException = new ValidationException(expBuilder.toString());
            for (Exception exception : suppressed)
            {
                validationException.addSuppressed(exception);
            }
            return validationException;
        }
    }

    public static <T> ValidationException of(ConfigPropertyValue<T> template, @Nullable T value, Throwable cause)
    {
        return of(template, value, cause.getMessage(), cause);
    }

    public static <T> ValidationException of(ConfigPropertyValue<T> template, @Nullable T value, String cause)
    {
        return of(template, value, cause, null);
    }
}
