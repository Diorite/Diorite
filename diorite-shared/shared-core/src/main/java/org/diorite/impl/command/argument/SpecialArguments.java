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

package org.diorite.impl.command.argument;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedType;
import java.util.ArrayList;
import java.util.List;

import org.jetbrains.annotations.NotNull;

import org.diorite.command.CommandObject;
import org.diorite.command.annotation.parameter.Plugin;
import org.diorite.command.annotation.parameter.Sender;
import org.diorite.command.annotation.parameter.SpecialParameter;
import org.diorite.sender.CommandSender;

/**
 * Class to parse arguments and mark them as special
 */
public final class SpecialArguments
{
    private List<Integer> special = new ArrayList<Integer>();

    private SpecialArguments() { }

    @NotNull
    public static SpecialArguments create()
    {
        return new SpecialArguments();
    }

    public void parse(int parameterIndex, AnnotatedType parameter)
    {
        for(Annotation annotation : parameter.getAnnotations())
        {
            if(!annotation.annotationType().isAnnotationPresent(SpecialParameter.class)) continue;
            if(!checkIfAnnotationAllowed(parameter, annotation))
            {
                //TODO: error logging
                continue;
            }
            special.add(parameterIndex);
        }
    }

    public boolean isSpecial(int parameterIndex)
    {
        return special.contains(parameterIndex);
    }

    private boolean checkIfAnnotationAllowed(AnnotatedType parameter, Annotation annotation)
    {
        if(annotation.getClass() == Plugin.class)
            if(!(parameter instanceof org.diorite.plugin.Plugin)) return false;
        if(annotation.getClass() == Sender.class)
            if(!(parameter instanceof CommandSender)) return false;
        return true;
    }

    public void provide(Object[] arguments, CommandSender sender, CommandObject command, String alias)
    {
        //TODO
    }
}
