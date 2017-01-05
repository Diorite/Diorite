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

package org.diorite.config.exceptions;

import javax.annotation.Nullable;

import java.io.File;

import org.diorite.config.ConfigTemplate;

public class ConfigSaveException extends RuntimeException
{
    private static final long serialVersionUID = 0;

    public ConfigSaveException(ConfigTemplate<?> configTemplate, @Nullable File file)
    {
        super(fixMessage(configTemplate, file, null));
    }

    public ConfigSaveException(ConfigTemplate<?> configTemplate, @Nullable File file, @Nullable String message)
    {
        super(fixMessage(configTemplate, file, message));
    }

    public ConfigSaveException(ConfigTemplate<?> configTemplate, @Nullable File file, @Nullable String message, Throwable cause)
    {
        super(fixMessage(configTemplate, file, message), cause);
    }

    public ConfigSaveException(ConfigTemplate<?> configTemplate, @Nullable File file, Throwable cause)
    {
        super(fixMessage(configTemplate, file, null), cause);
    }

    public ConfigSaveException(ConfigTemplate<?> configTemplate, @Nullable File file, @Nullable String message, Throwable cause, boolean enableSuppression,
                               boolean writableStackTrace)
    {
        super(fixMessage(configTemplate, file, message), cause, enableSuppression, writableStackTrace);
    }

    private static String fixMessage(ConfigTemplate<?> configTemplate, @Nullable File file, @Nullable String message)
    {
        StringBuilder error = new StringBuilder();
        error.append("There was an exception while saving config file '")
             .append(configTemplate.getName())
             .append("', from: '").append((file == null) ? "<unknown>" : file.getAbsolutePath()).append("'");
        if (message == null)
        {
            return error.toString();
        }
        error.append(", error message: ").append(message);
        return error.toString();
    }
}
