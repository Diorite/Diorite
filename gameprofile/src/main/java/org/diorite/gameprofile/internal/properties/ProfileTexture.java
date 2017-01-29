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

package org.diorite.gameprofile.internal.properties;

import javax.annotation.Nullable;

import java.util.Map;

import org.apache.commons.io.FilenameUtils;

public class ProfileTexture
{
    private final           String              url;
    @Nullable private final Map<String, String> metadata;

    public ProfileTexture(String url, @Nullable Map<String, String> metadata)
    {
        this.url = url;
        this.metadata = metadata;
    }

    public String getUrl()
    {
        return this.url;
    }

    @Nullable
    public String getMetadata(String key)
    {
        if (this.metadata == null)
        {
            return null;
        }
        return this.metadata.get(key);
    }

    public String getHash()
    {
        return FilenameUtils.getBaseName(this.url);
    }
}
