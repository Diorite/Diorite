package diorite.impl;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

class ServerShutdownThread extends Thread
{
    private final ServerImpl server;

    ServerShutdownThread(final ServerImpl server)
    {
        this.server = server;
    }

    @Override
    public void run()
    {
        try
        {
            this.server.stop();
        } catch (final Exception ex)
        {
            ex.printStackTrace();
        }
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("server", this.server).toString();
    }
}
