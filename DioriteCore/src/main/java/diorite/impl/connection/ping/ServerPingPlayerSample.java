package diorite.impl.connection.ping;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.mojang.authlib.GameProfile;

public class ServerPingPlayerSample
{
    private int           maxPlayers;
    private int           onlinePlayers;
    private GameProfile[] profiles;

    public ServerPingPlayerSample(final int maxPlayers, final int onlinePlayers, final GameProfile... profiles)
    {
        this.maxPlayers = maxPlayers;
        this.onlinePlayers = onlinePlayers;
        this.profiles = profiles;
    }

    public ServerPingPlayerSample(final int maxPlayers, final int onlinePlayers)
    {
        this.maxPlayers = maxPlayers;
        this.onlinePlayers = onlinePlayers;
    }

    public int getMaxPlayers()
    {
        return this.maxPlayers;
    }

    public int getOnlinePlayers()
    {
        return this.onlinePlayers;
    }

    public GameProfile[] getProfiles()
    {
        return this.profiles;
    }

    public void setProfiles(final GameProfile[] profiles)
    {
        this.profiles = profiles;
    }

    public void setMaxPlayers(final int maxPlayers)
    {
        this.maxPlayers = maxPlayers;
    }

    public void setOnlinePlayers(final int onlinePlayers)
    {
        this.onlinePlayers = onlinePlayers;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("maxPlayers", this.maxPlayers).append("onlinePlayers", this.onlinePlayers).append("profiles", this.profiles).toString();
    }
}