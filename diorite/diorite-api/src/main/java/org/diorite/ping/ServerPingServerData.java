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

package org.diorite.ping;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Represent ping server data.
 */
public class ServerPingServerData
{
    private String name;
    private int    protocol;

    /**
     * Construct new ping server data.
     *
     * @param protocolName
     *         name of protocol version.
     * @param protocolVersion
     *         numeric version of protocol.
     */
    public ServerPingServerData(String protocolName, int protocolVersion)
    {
        this.name = protocolName;
        this.protocol = protocolVersion;
    }

    /**
     * Returns name of protocol version.
     *
     * @return name of protocol version.
     */
    public String getName()
    {
        return this.name;
    }

    /**
     * Sets new name of protocol version.
     *
     * @param name
     *         new name of protocol version.
     */
    public void setName(String name)
    {
        this.name = name;
    }

    /**
     * Returns numeric protocol version.
     *
     * @return numeric protocol version.
     */
    public int getProtocol()
    {
        return this.protocol;
    }

    /**
     * Sets new numeric protocol version.
     *
     * @param protocol
     *         new numeric protocol version.
     */
    public void setProtocol(int protocol)
    {
        this.protocol = protocol;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("name", this.name)
                                                                          .append("protocol", this.protocol).toString();
    }
}