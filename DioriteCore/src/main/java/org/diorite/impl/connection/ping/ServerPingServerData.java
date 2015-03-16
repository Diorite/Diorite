package org.diorite.impl.connection.ping;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class ServerPingServerData
{
    private String name;
    private int    protocol;

    public ServerPingServerData(final String paramString, final int paramInt)
    {
        this.name = paramString;
        this.protocol = paramInt;
    }

    public String getName()
    {
        return this.name;
    }

    public void setName(final String name)
    {
        this.name = name;
    }

    public int getProtocol()
    {
        return this.protocol;
    }

    public void setProtocol(final int protocol)
    {
        this.protocol = protocol;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("name", this.name).append("protocol", this.protocol).toString();
    }
}