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

package org.diorite.impl.server.connection;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.KeyPair;
import java.util.UUID;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.impl.DioriteCore;
import org.diorite.impl.auth.GameProfileImpl;
import org.diorite.impl.auth.exceptions.AuthenticationUnavailableException;
import org.diorite.impl.connection.MinecraftEncryption;
import org.diorite.impl.server.connection.listeners.LoginListener;
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
        final GameProfileImpl oldProfile = this.loginListener.getGameProfile();
        try
        {
            if (this.loginListener.getOnlineMode() != OnlineMode.TRUE) // TODO
            {
                this.loginListener.setUUID(UUID.nameUUIDFromBytes(("OfflinePlayer:" + oldProfile.getName()).getBytes(StandardCharsets.UTF_8)));
                this.allow();
                return;
            }
            final KeyPair serverKeyPair = this.getServer().getKeyPair();
            //noinspection MagicNumber
            final String hash = new BigInteger(MinecraftEncryption.combineKeys(this.loginListener.getServerID(), serverKeyPair.getPublic(), this.loginListener.getSecretKey())).toString(16);
            final GameProfileImpl newProfile = this.getServer().getSessionService().hasJoinedServer(oldProfile, hash);
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
        final GameProfileImpl profile = this.loginListener.getGameProfile();
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
