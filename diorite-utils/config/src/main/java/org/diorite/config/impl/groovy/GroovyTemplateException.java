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

package org.diorite.config.impl.groovy;

import javax.annotation.Nullable;

import org.apache.commons.lang3.StringUtils;

public class GroovyTemplateException extends RuntimeException
{
    private static final long serialVersionUID = 0;

    public GroovyTemplateException(String classCode)
    {
        super(prepareMessage(classCode, null));
    }

    public GroovyTemplateException(String classCode, String message)
    {
        super(prepareMessage(classCode, message));
    }

    public GroovyTemplateException(String classCode, String message, Throwable cause)
    {
        super(prepareMessage(classCode, message), cause);
    }

    public GroovyTemplateException(String classCode, Throwable cause)
    {
        super(prepareMessage(classCode, null), cause);
    }

    private static String prepareMessage(String code, @Nullable String message)
    {
        if (message == null)
        {
            return "Unexpected problem when generation groovy config implementation!\n\nGenerated code: \n============================================================================\n" +
                   addDebugLines(code) + "\n============================================================================\n";
        }
        else
        {
            return message + "\n\nGenerated code: \n============================================================================\n" + addDebugLines(code) +
                   "\n============================================================================\n";
        }
    }

    @SuppressWarnings("MagicNumber")
    static String addDebugLines(String classCode)
    {
        StringBuilder implStr = new StringBuilder(classCode.length() + (classCode.length() / 30));
        int i = 1;
        for (String s : StringUtils.splitPreserveAllTokens(classCode, '\n'))
        {
            implStr.append("/* ").append(i++).append(" */").append(s).append("\n");
        }
        return implStr.toString();
    }
}
