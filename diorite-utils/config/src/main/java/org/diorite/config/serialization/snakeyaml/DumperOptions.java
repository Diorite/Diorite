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

package org.diorite.config.serialization.snakeyaml;

public class DumperOptions extends org.yaml.snakeyaml.DumperOptions
{
    private int    longCommentThreshold   = 5;
    private String longCommentBorder      = "#-";
    private String longCommentRightBorder = "#";

    public int getLongCommentThreshold()
    {
        return this.longCommentThreshold;
    }

    public void setLongCommentThreshold(int longCommentThreshold)
    {
        this.longCommentThreshold = longCommentThreshold;
    }

    public String getLongCommentBorder()
    {
        return this.longCommentBorder;
    }

    public void setLongCommentBorder(String longCommentBorder)
    {
        this.longCommentBorder = longCommentBorder;
    }

    public String getLongCommentRightBorder()
    {
        return this.longCommentRightBorder;
    }

    public void setLongCommentRightBorder(String longCommentRightBorder)
    {
        this.longCommentRightBorder = longCommentRightBorder;
    }
}
