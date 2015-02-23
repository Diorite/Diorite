package diorite.impl.connection.ping;


import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import diorite.chat.BaseComponent;

public class ServerPing
{
    private BaseComponent          motd;
    private ServerPingPlayerSample playerData;
    private ServerPingServerData   serverData;
    private String                 favicon;

    public ServerPing()
    {
    }

    public ServerPing(final BaseComponent motd, final String favicon, final ServerPingServerData serverData, final ServerPingPlayerSample playerData)
    {
        this.motd = motd;
        this.favicon = favicon;
        this.serverData = serverData;
        this.playerData = playerData;
    }

    public BaseComponent getMotd()
    {
        return this.motd;
    }

    public void setMotd(final BaseComponent motd)
    {
        this.motd = motd;
    }

    public String getFavicon()
    {
        return this.favicon;
    }

    public void setFavicon(final String favicon)
    {
        this.favicon = favicon;
    }

    public ServerPingServerData getServerData()
    {
        return this.serverData;
    }

    public void setServerData(final ServerPingServerData serverData)
    {
        this.serverData = serverData;
    }

    public ServerPingPlayerSample getPlayerData()
    {
        return this.playerData;
    }

    public void setPlayerData(final ServerPingPlayerSample playerData)
    {
        this.playerData = playerData;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("motd", this.motd).append("playerData", this.playerData).append("serverData", this.serverData).append("favicon", this.favicon).toString();
    }
}