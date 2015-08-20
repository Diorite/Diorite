package org.diorite.impl.server.connection.listeners;

import javax.crypto.SecretKey;

import java.security.PrivateKey;
import java.util.Arrays;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.slf4j.Logger;

import org.diorite.impl.DioriteCore;
import org.diorite.impl.auth.GameProfile;
import org.diorite.impl.connection.CoreNetworkManager;
import org.diorite.impl.connection.EnumProtocol;
import org.diorite.impl.connection.MinecraftEncryption;
import org.diorite.impl.connection.packets.login.PacketLoginClientListener;
import org.diorite.impl.connection.packets.login.client.PacketLoginClientEncryptionBegin;
import org.diorite.impl.connection.packets.login.client.PacketLoginClientStart;
import org.diorite.impl.connection.packets.login.server.PacketLoginServerDisconnect;
import org.diorite.impl.connection.packets.login.server.PacketLoginServerEncryptionBegin;
import org.diorite.impl.connection.packets.login.server.PacketLoginServerSetCompression;
import org.diorite.impl.connection.packets.login.server.PacketLoginServerSuccess;
import org.diorite.impl.entity.PlayerImpl;
import org.diorite.impl.server.connection.NetworkManager;
import org.diorite.impl.server.connection.ThreadPlayerLookupUUID;
import org.diorite.cfg.DioriteConfig.OnlineMode;
import org.diorite.chat.component.BaseComponent;
import org.diorite.chat.component.TextComponent;

public class LoginListener implements PacketLoginClientListener
{
    private static final int TIMEOUT_TICKS = 600;

    private static final Random        random  = new Random();
    private static final AtomicInteger counter = new AtomicInteger();

    private final Logger logger;
    private final byte[] token = new byte[4];
    private final DioriteCore        core;
    private final CoreNetworkManager networkManager;
    private       GameProfile        gameProfile;
    private       SecretKey          secretKey;
    private       String             hostname;
    private       ProtocolState      protocolState;
    private       OnlineMode         onlineMode;
    private       int                ticks;

    private String serverID = ""; // unused?

    public LoginListener(final DioriteCore core, final CoreNetworkManager networkManager)
    {
        this.core = core;
        this.networkManager = networkManager;
        this.onlineMode = core.getOnlineMode();
        random.nextBytes(this.token);
        this.protocolState = ProtocolState.HELLO;
        this.logger = core.getLogger();
    }

    public LoginListener(final DioriteCore core, final CoreNetworkManager networkManager, final String hostname)
    {
        this(core, networkManager);
        this.hostname = hostname;
    }

    private int getTimeoutTicks()
    {
        return (int) (this.core.getSpeedMutli() * TIMEOUT_TICKS);
    }

    @Override
    public void tick()
    {
        if (this.protocolState == ProtocolState.READY_TO_ACCEPT)
        {
            this.acceptPlayer();
        }
        if (this.ticks++ >= this.getTimeoutTicks())
        {
            this.disconnect("Took too long to log in");
        }
    }

    @Override
    public void handle(final PacketLoginClientStart packet)
    {
        Validate.validState(this.protocolState == ProtocolState.HELLO, "Unexpected hello packet");
        this.gameProfile = packet.getProfile();
        if (this.onlineMode == OnlineMode.TRUE) // TODO
        {
            this.protocolState = ProtocolState.KEY;
            this.networkManager.sendPacket(new PacketLoginServerEncryptionBegin(this.serverID, this.core.getKeyPair().getPublic(), this.token));
        }
        else
        {
            new ThreadPlayerLookupUUID(this, "Diorite User Authenticator (" + this.gameProfile.getName() + ") #0 (cracked)", this::acceptPlayer).start();
        }
    }

    @Override
    public void handle(final PacketLoginClientEncryptionBegin packet)
    {
        Validate.validState(this.protocolState == ProtocolState.KEY, "Unexpected key packet");
        final PrivateKey privateKey = this.core.getKeyPair().getPrivate();
        if (Arrays.equals(this.token, packet.getVerifyToken()))
        {
            throw new IllegalStateException("Invalid nonce!");
        }
        this.secretKey = MinecraftEncryption.createKeySpec(privateKey, packet.getSharedSecret());
        this.protocolState = ProtocolState.AUTHENTICATING;
        this.networkManager.enableEncryption(this.secretKey);
        new ThreadPlayerLookupUUID(this, "Diorite User Authenticator (" + this.gameProfile.getName() + ") #" + counter.incrementAndGet(), this::acceptPlayer).start();
    }

    public void acceptPlayer()
    {
        if (this.core.getCompressionThreshold() >= 0)
        {
            this.networkManager.sendPacket(new PacketLoginServerSetCompression(this.core.getCompressionThreshold()), future -> {
                this.networkManager.setCompression(this.core.getCompressionThreshold());
            });
        }
        this.networkManager.sendPacket(new PacketLoginServerSuccess(this.gameProfile), future -> {
            this.networkManager.setProtocol(EnumProtocol.PLAY);

            PlayerImpl player = this.core.getPlayersManager().createPlayer(this.gameProfile, this.networkManager);
            this.networkManager.setPacketListener(new PlayListener(LoginListener.this.core, LoginListener.this.networkManager, player));
            this.core.getPlayersManager().playerJoin(player);
        });
    }

    private String playerInfo()
    {
        //noinspection ObjectToString
        return (this.gameProfile == null) ? String.valueOf(this.networkManager.getSocketAddress()) : (this.gameProfile.getName() + " (" + this.gameProfile.getId() + ") [" + String.valueOf(this.networkManager.getSocketAddress()) + "]");
    }

    @Override
    public void disconnect(final BaseComponent message)
    {
        this.logger.info(this.playerInfo() + " disconnected from server: " + message.toLegacyText());
    }

    @Override
    public void setUUID(final UUID uuid)
    {
        this.gameProfile.setId(uuid);
    }

    public void disconnect(final String msg)
    {
        try
        {
            this.logger.info("Disconnecting " + this.gameProfile + ": " + msg);
            final BaseComponent tc = TextComponent.fromLegacyText(msg);
            this.networkManager.sendPacket(new PacketLoginServerDisconnect(tc));
            this.networkManager.close(tc, false);
        } catch (final Exception exception)
        {
            this.logger.error("Error whilst disconnecting player", exception);
        }
    }

    @Override
    public Logger getLogger()
    {
        return this.logger;
    }

    public ProtocolState getProtocolState()
    {
        return this.protocolState;
    }

    @Override
    public void setProtocolState(final ProtocolState protocolState)
    {
        this.protocolState = protocolState;
    }

    public byte[] getToken()
    {
        return this.token;
    }

    public SecretKey getSecretKey()
    {
        return this.secretKey;
    }

    public void setSecretKey(final SecretKey secretKey)
    {
        this.secretKey = secretKey;
    }

    public String getHostname()
    {
        return this.hostname;
    }

    public void setHostname(final String hostname)
    {
        this.hostname = hostname;
    }

    @Override
    public GameProfile getGameProfile()
    {
        return this.gameProfile;
    }

    @Override
    public void setGameProfile(final GameProfile gameProfile)
    {
        this.gameProfile = gameProfile;
    }

    public String getServerID()
    {
        return this.serverID;
    }

    public void setServerID(final String serverID)
    {
        this.serverID = serverID;
    }

    @Override
    public NetworkManager getNetworkManager()
    {
        return (NetworkManager) this.networkManager;
    }

    @Override
    public DioriteCore getCore()
    {
        return this.core;
    }

    @Override
    public OnlineMode getOnlineMode()
    {
        return this.onlineMode;
    }

    public void setOnlineMode(final OnlineMode onlineMode)
    {
        this.onlineMode = onlineMode;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("networkManager", this.networkManager).append("gameProfile", this.gameProfile).append("secretKey", this.secretKey).append("hostname", this.hostname).append("protocolState", this.protocolState).append("onlineMode", this.onlineMode).append("serverID", this.serverID).append("token", this.token).append("ticks", this.ticks).toString();
    }
}
