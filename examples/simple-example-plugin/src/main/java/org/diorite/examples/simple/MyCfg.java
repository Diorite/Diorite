/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2015 Diorite (by Bartłomiej Mazur (aka GotoFinal))
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

package org.diorite.examples.simple;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.cfg.annotations.CfgComment;
import org.diorite.cfg.annotations.defaults.CfgDelegateDefault;
import org.diorite.cfg.annotations.defaults.CfgStringDefault;

@CfgComment("Welcome in configuration file!")
@CfgDelegateDefault("new MyCfg()")
public class MyCfg
{
    @CfgComment("This is some option with comment on it.")
    @CfgStringDefault("Default message")
    private String               message;
    @CfgDelegateDefault("adv|" +
                                "Map map = new HashMap(2);\n" +
                                "int[] key = new int[3];\n" +
                                "key[0] = 1;\n" +
                                "key[1] = 2;\n" +
                                "key[2] = 3;\n" +
                                "double[] value = new double[3];\n" +
                                "value[0] = 0.1;\n" +
                                "value[1] = 0.2;\n" +
                                "value[2] = 0.3;\n" +
                                "map.put(key, value);\n" +
                                "return map;")
    private Map<int[], double[]> map;

    public String getMessage()
    {
        return this.message;
    }

    public void setMessage(final String message)
    {
        this.message = message;
    }

    public Map<int[], double[]> getMap()
    {
        return this.map;
    }

    public void setMap(final Map<int[], double[]> map)
    {
        this.map = map;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("message", this.message).append("map", this.map.entrySet().stream().map(e -> Arrays.toString(e.getKey()) + ": " + Arrays.toString(e.getValue())).collect(Collectors.toList())).toString();
    }
}
