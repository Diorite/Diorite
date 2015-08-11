package org.diorite.impl.connection.listeners;

import java.math.BigInteger;
import java.security.KeyPair;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.impl.DioriteCore;
import org.diorite.impl.auth.GameProfile;
import org.diorite.impl.auth.exceptions.AuthenticationUnavailableException;
import org.diorite.impl.connection.MinecraftEncryption;
import org.diorite.impl.connection.listeners.server.LoginListener;
import org.diorite.cfg.DioriteConfig.OnlineMode;

public class ThreadPlayerLookupUUID extends Thread
{
    private final LoginListener loginListener;
    private final Runnable      onSuccess;

    public ThreadPlayerLookupUUID(final LoginListener loginListener, final String name, final Runnable onSuccess)
    {
        super(name);
        this.loginListener = loginListener;
        this.onSuccess = onSuccess;
    }

    @Override
    public void run()
    {
        final GameProfile oldProfile = this.loginListener.getGameProfile();
        try
        {
            if (this.loginListener.getOnlineMode() != OnlineMode.TRUE) // TODO
            {
                this.loginListener.setCrackedUUID();
                this.allow();
                return;
            }
            final KeyPair serverKeyPair = this.getServer().getKeyPair();
            //noinspection MagicNumber
            final String hash = new BigInteger(MinecraftEncryption.combineKeys(this.loginListener.getServerID(), serverKeyPair.getPublic(), this.loginListener.getSecretKey())).toString(16);
            final GameProfile newProfile = this.getServer().getSessionService().hasJoinedServer(oldProfile, hash);
            this.loginListener.setGameProfile(newProfile);
            if (newProfile == null)
            {
                this.loginListener.disconnect("Failed to verify username!");
                this.loginListener.getLogger().error("Username '" + oldProfile.getName() + "' tried to join with an invalid session");
                return;
            }
            this.allow();
        } catch (final AuthenticationUnavailableException serverEx)
        {
            this.loginListener.disconnect("Authentication servers are down. Please try again later, sorry!");
            this.loginListener.getLogger().error("Couldn't verify username because servers are unavailable");

        } catch (final Exception e)
        {
            e.printStackTrace();
            this.loginListener.disconnect("Failed to verify username!");
            this.loginListener.getLogger().error("Username '" + oldProfile.getName() + "' tried to join with an invalid session");
        }

    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("loginListener", this.loginListener).toString();
    }

    private void allow()
    {
        final GameProfile profile = this.loginListener.getGameProfile();
        //noinspection ObjectToString
        this.loginListener.getLogger().info("Player " + profile.getName() + " (" + profile.getId() + ") [" + this.loginListener.getNetworkManager().getSocketAddress() + "] connected to server! (online-mode: " + this.loginListener.getOnlineMode() + ")");
        this.loginListener.setProtocolState(LoginListener.ProtocolState.READY_TO_ACCEPT);
        this.onSuccess.run();
    }

    public LoginListener getLoginListener()
    {
        return this.loginListener;
    }

    private DioriteCore getServer()
    {
        return this.loginListener.getCore();
    }
}
