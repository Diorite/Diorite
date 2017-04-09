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

package org.diorite.config.serialization.serializers;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.diorite.commons.DioriteUtils;
import org.diorite.config.serialization.StringSerializer;

/**
 * String serializer of {@link InetAddress}.
 */
public class InetAddressSerializer implements StringSerializer<InetAddress>
{
    @Override
    public Class<? super InetAddress> getType()
    {
        return InetAddress.class;
    }

    @Override
    public InetAddress deserialize(String data)
    {
        try
        {
            int i = data.indexOf('/');
            if (i >= 0)
            {
                return InetAddress.getByName(data.substring(i + 1));
            }
            return InetAddress.getByName(data);
        }
        catch (UnknownHostException e)
        {
            throw DioriteUtils.sneakyThrow(e);
        }
    }

    @Override
    public String serialize(InetAddress data)
    {
        String str = data.toString();
        int indexOf = str.indexOf('/');
        if (indexOf >= 0)
        {
            return str.substring(str.indexOf('/') + 1);
        }
        return str;
    }
}
