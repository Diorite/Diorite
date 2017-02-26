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

package org.diorite.impl.protocol.p16w50a.serverbound;

import javax.annotation.Nullable;
import javax.crypto.SecretKey;

import java.math.BigInteger;
import java.net.InetSocketAddress;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.util.Arrays;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import org.apache.commons.lang3.StringUtils;

import org.diorite.impl.protocol.MinecraftEncryption;
import org.diorite.impl.protocol.any.serverbound.H00Handshake;
import org.diorite.impl.protocol.p16w50a.clientbound.CL01EncryptionRequest;
import org.diorite.impl.protocol.p16w50a.clientbound.CL02SetCompression;
import org.diorite.chat.ChatMessage;
import org.diorite.core.DioriteCore;
import org.diorite.core.protocol.ProtocolVersion;
import org.diorite.core.protocol.connection.ActiveConnection;
import org.diorite.core.protocol.connection.internal.ProtocolState;
import org.diorite.core.protocol.connection.internal.ServerboundPacketListener;
import org.diorite.core.protocol.packets.ReceiveDioritePacketEvent;
import org.diorite.core.protocol.packets.serverbound.LoginStartPacket;
import org.diorite.gameprofile.GameProfile;
import org.diorite.gameprofile.SessionService;
import org.diorite.gameprofile.internal.GameProfileImpl;
import org.diorite.gameprofile.internal.exceptions.AuthenticationUnavailableException;

import net.engio.mbassy.listener.Handler;

public class ServerboundLoginPacketListener implements ServerboundPacketListener
{
    private static final Random random = new Random();

    private final           DioriteCore      dioriteCore;
    private final           ActiveConnection activeConnection;
    @Nullable private final H00Handshake     handshakePacket;

    private final byte[] token = new byte[4];
    private @Nullable GameProfileImpl gameProfile;
    private final ThreadPoolExecutor uuidPool = new ThreadPoolExecutor(2, 8, 10, TimeUnit.MINUTES, new LinkedBlockingQueue<>(),
                                                                       new ThreadFactoryBuilder().setDaemon(true).setNameFormat("AuthUUID-fetch-%s").build());

    public ServerboundLoginPacketListener(ActiveConnection activeConnection, @Nullable H00Handshake handshakePacket)
    {
        this.dioriteCore = activeConnection.getDioriteCore();
        this.activeConnection = activeConnection;
        this.handshakePacket = handshakePacket;
        random.nextBytes(this.token);
    }

    @Override
    public void disconnect(@Nullable ChatMessage disconnectMessage)
    {
        // TODO
    }

    @Handler
    public void handle(SL00LoginStart packet)
    {
        if (this.gameProfile != null)
        {
            throw new IllegalStateException("Received login packet twice?");
        }
        String username = packet.getUsername();
        assert username != null;
        LoginStartPacket loginStartPacket = new LoginStartPacket();
        loginStartPacket.setProtocolVersion(this.activeConnection.getProtocolVersion());
        InetSocketAddress serverAddress = this.activeConnection.getServerAddress();
        if (this.handshakePacket == null)
        {
            loginStartPacket.setServerAddress(serverAddress.getAddress().toString());
            loginStartPacket.setServerPort(serverAddress.getPort());
        }
        else
        {
            String handshakeAddress = this.handshakePacket.getAddress();
            assert handshakeAddress != null;
            loginStartPacket.setServerAddress(handshakeAddress);
            loginStartPacket.setServerPort(this.handshakePacket.getPort());
        }
        loginStartPacket.setUsername(username);
        ReceiveDioritePacketEvent receiveDioritePacketEvent = new ReceiveDioritePacketEvent(this.activeConnection, loginStartPacket);
        this.dioriteCore.getEventManager().callEvent(receiveDioritePacketEvent);
        if (! receiveDioritePacketEvent.isCancelled())
        {
            this.activeConnection.callEvent(loginStartPacket);
        }

        if (this.activeConnection.isAuthWithMojang())
        {
            this.gameProfile = new GameProfileImpl(null, username);
            this.activeConnection.sendPacket(new CL01EncryptionRequest(this.dioriteCore.getKeyPair().getPublic(), this.token));
        }
        else
        {
            this.gameProfile = new GameProfileImpl(GameProfile.getCrackedUuid(username), username);
            this.switchToPlay();
        }
    }

    private volatile boolean encrypted = false;

    @Handler
    public void handle(SL01EncryptionResponse packet)
    {
        if (this.encrypted)
        {
            throw new IllegalStateException("Received encryption packet twice");
        }
        this.encrypted = true;
        PrivateKey privateKey = this.dioriteCore.getKeyPair().getPrivate();
        if (Arrays.equals(this.token, packet.getVerifyToken()))
        {
            throw new IllegalStateException("Invalid nonce!");
        }
        SecretKey key = MinecraftEncryption.createKeySpec(privateKey, packet.getSharedSecret());
        this.activeConnection.enableEncryption(key);
        this.uuidPool.execute(new AuthTask(this, key));
    }

    private void switchToPlay()
    {
        ProtocolVersion<?> protocolVersion = this.activeConnection.getProtocolVersion();
        int compressionThreshold = protocolVersion.getSettings().getNetworkCompressionThreshold();
        this.activeConnection.sendPacket(new CL02SetCompression(compressionThreshold));
        this.activeConnection.setCompression(compressionThreshold);
        this.activeConnection.setProtocol(ProtocolState.PLAY);
        assert this.gameProfile != null;
        this.dioriteCore.getLogger().info("Player (uuid: " + this.gameProfile.getId() + ", name: " + this.gameProfile.getName() + ", ip: " +
                                          this.activeConnection.getSocketAddress().getAddress() + ") connected to server using `" +
                                          protocolVersion.getVersionName() + "` protocol, logging in...");
    }

    static class AuthTask implements Runnable
    {
        private final SecretKey                      key;
        private       ServerboundLoginPacketListener listener;

        AuthTask(ServerboundLoginPacketListener listener, SecretKey key)
        {
            this.key = key;
            this.listener = listener;
        }

        @SuppressWarnings("MagicNumber")
        @Override
        public void run()
        {
            assert this.listener.gameProfile != null;
            assert this.listener.gameProfile.getName() != null;
            try
            {
                SessionService sessionService = this.listener.dioriteCore.getSessionService();

                KeyPair serverKeyPair = this.listener.dioriteCore.getKeyPair();
                String hash = new BigInteger(MinecraftEncryption.combineKeys(StringUtils.EMPTY, serverKeyPair.getPublic(), this.key)).toString(16);
                GameProfileImpl newProfile = (GameProfileImpl) sessionService.hasJoinedServer(this.listener.gameProfile, hash);
                if (newProfile == null)
                {
                    // TODO: read from config
                    this.listener.disconnect(ChatMessage.fromLegacy("Failed to verify username!"));
                    this.listener.dioriteCore.getLogger().error("Username '" + this.listener.gameProfile.getName() + "' tried to join with an invalid session");
                    UUID crackedUuid = GameProfile.getCrackedUuid(this.listener.gameProfile.getName());
                    sessionService.getCache().addEmptyEntry(this.listener.gameProfile.getName(), crackedUuid);
                    return;
                }
                this.listener.gameProfile = newProfile;
                sessionService.getCache().addToCache(newProfile);
                this.listener.switchToPlay();

            }
            catch (AuthenticationUnavailableException serverEx)
            {
                // TODO: read from config
                this.listener.disconnect(ChatMessage.fromLegacy("Authentication servers are down. Please try again later, sorry!"));
                this.listener.dioriteCore.getLogger().error("Couldn't verify username because servers are unavailable");

            }
            catch (Exception e)
            {
                e.printStackTrace();
                // TODO: read from config
                this.listener.disconnect(ChatMessage.fromLegacy("Failed to verify username!"));
                this.listener.dioriteCore.getLogger().error("Username '" + this.listener.gameProfile.getName() + "' tried to join with an invalid session");
            }
        }
    }
}
