package diorite.impl.connection.listeners;

import javax.crypto.SecretKey;

import java.security.PrivateKey;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.common.base.Charsets;
import com.mojang.authlib.GameProfile;

import diorite.BlockLocation;
import diorite.Difficulty;
import diorite.Dimension;
import diorite.GameMode;
import diorite.TeleportData;
import diorite.WorldType;
import diorite.chat.BaseComponent;
import diorite.chat.TextComponent;
import diorite.impl.ServerImpl;
import diorite.impl.connection.EnumProtocol;
import diorite.impl.connection.MinecraftEncryption;
import diorite.impl.connection.NetworkManager;
import diorite.impl.connection.packets.PacketDataSerializer;
import diorite.impl.connection.packets.login.PacketLoginInListener;
import diorite.impl.connection.packets.login.in.PacketLoginInEncryptionBegin;
import diorite.impl.connection.packets.login.in.PacketLoginInStart;
import diorite.impl.connection.packets.login.out.PacketLoginOutEncryptionBegin;
import diorite.impl.connection.packets.login.out.PacketLoginOutSuccess;
import diorite.impl.connection.packets.play.out.PacketPlayOutAbilities;
import diorite.impl.connection.packets.play.out.PacketPlayOutCustomPayload;
import diorite.impl.connection.packets.play.out.PacketPlayOutHeldItemSlot;
import diorite.impl.connection.packets.play.out.PacketPlayOutLogin;
import diorite.impl.connection.packets.play.out.PacketPlayOutMapChunkBulk;
import diorite.impl.connection.packets.play.out.PacketPlayOutPosition;
import diorite.impl.connection.packets.play.out.PacketPlayOutServerDifficulty;
import diorite.impl.connection.packets.play.out.PacketPlayOutSpawnPosition;
import diorite.impl.map.chunk.ChunkImpl;
import diorite.impl.map.chunk.ChunkManagerImpl;
import io.netty.buffer.Unpooled;

public class LoginListener implements PacketLoginInListener
{
    private static final int TIMEOUT_TICKS = 600;

    private static final Random        random  = new Random();
    private static final AtomicInteger counter = new AtomicInteger();

    private final Logger logger = LogManager.getLogger();
    private final byte[] token  = new byte[4];
    private final ServerImpl     server;
    private final NetworkManager networkManager;
    private       GameProfile    gameProfile;
    private       SecretKey      secretKey;
    private       String         hostname;
    private       ProtocolState  protocolState;
    private       boolean        onlineMode;
    private       int            ticks;

    private String serverID = ""; // unused?

    public LoginListener(final ServerImpl server, final NetworkManager networkManager)
    {
        this.server = server;
        this.networkManager = networkManager;
        this.onlineMode = server.isOnlineMode();
        random.nextBytes(this.token);
        this.protocolState = ProtocolState.HELLO;
    }

    public LoginListener(final ServerImpl server, final NetworkManager networkManager, final String hostname)
    {
        this(server, networkManager);
        this.hostname = hostname;
    }

    private int getTimeoutTicks()
    {
        return (int) (this.server.getMutli() * TIMEOUT_TICKS);
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

    public void acceptPlayer() // TODO implement compression
    {
        this.networkManager.handle(new PacketLoginOutSuccess(this.gameProfile), future -> {
            this.networkManager.setProtocol(EnumProtocol.PLAY);

            this.networkManager.setPacketListener(new PlayListener(LoginListener.this.server, LoginListener.this.networkManager));


            // TODO: this is only test code
            this.networkManager.handle(new PacketPlayOutLogin(1, GameMode.SURVIVAL, false, Dimension.OVERWORLD, Difficulty.PEACEFUL, 20, WorldType.FLAT));
            this.networkManager.handle(new PacketPlayOutCustomPayload("MC|Brand", new PacketDataSerializer(Unpooled.buffer()).writeText(LoginListener.this.server.getServerModName())));
            this.networkManager.handle(new PacketPlayOutServerDifficulty(Difficulty.EASY));
            this.networkManager.handle(new PacketPlayOutSpawnPosition(new BlockLocation(2, 71, - 2)));
            this.networkManager.handle(new PacketPlayOutAbilities(false, false, false, false, 0.5f, 0.5f));
            this.networkManager.handle(new PacketPlayOutHeldItemSlot(3));
            this.networkManager.handle(new PacketPlayOutPosition(new TeleportData(4, 71, - 4)));
            ChunkManagerImpl mag = new ChunkManagerImpl();
            ArrayList<ChunkImpl> chunks = new ArrayList<>(15);
            for (int x = - 1; x < 1; x++)
            {
                for (int z = - 2; z < 2; z++)
                {
                    chunks.add(mag.getChunkAt(x, z));
                }
            }
            this.networkManager.handle(new PacketPlayOutMapChunkBulk(true, chunks.toArray(new ChunkImpl[chunks.size()])));

            this.server.getPlayersManager().playerJoin(this.gameProfile, this.networkManager);
        });
    }

    @Override
    public void handle(final PacketLoginInStart packet)
    {
        Validate.validState(this.protocolState == ProtocolState.HELLO, "Unexpected hello packet");
        this.gameProfile = packet.getProfile();
        if (this.onlineMode)
        {
            this.protocolState = ProtocolState.KEY;
            this.networkManager.handle(new PacketLoginOutEncryptionBegin(this.serverID, this.server.getKeyPair().getPublic(), this.token));
        }
        else
        {
            new ThreadPlayerLookupUUID(this, "Diorite User Authenticator (" + this.gameProfile.getName() + ") #0 (cracked)", this::acceptPlayer).start();
        }
    }

    @Override
    public void handle(final PacketLoginInEncryptionBegin packet)
    {
        Validate.validState(this.protocolState == ProtocolState.KEY, "Unexpected key packet");
        final PrivateKey privateKey = this.server.getKeyPair().getPrivate();
        if (Arrays.equals(this.token, packet.getVerifyToken()))
        {
            throw new IllegalStateException("Invalid nonce!");
        }
        this.secretKey = MinecraftEncryption.createKeySpec(privateKey, packet.getSharedSecret());
        this.protocolState = ProtocolState.AUTHENTICATING;
        this.networkManager.enableEncryption(this.secretKey);
        new ThreadPlayerLookupUUID(this, "Diorite User Authenticator (" + this.gameProfile.getName() + ") #" + counter.incrementAndGet(), this::acceptPlayer).start();
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

    public void setCrackedUUID()
    {
        this.gameProfile.setId(UUID.nameUUIDFromBytes(("OfflinePlayer:" + this.gameProfile.getName()).getBytes(Charsets.UTF_8)));
    }

    public void disconnect(final String msg)
    {
        try
        {
            this.logger.info("Disconnecting " + this.gameProfile + ": " + msg);
            this.networkManager.disconnect(new TextComponent(msg));
        } catch (final Exception exception)
        {
            this.logger.error("Error whilst disconnecting player", exception);
        }
    }

    public Logger getLogger()
    {
        return this.logger;
    }

    public ProtocolState getProtocolState()
    {
        return this.protocolState;
    }

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

    public GameProfile getGameProfile()
    {
        return this.gameProfile;
    }

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

    public NetworkManager getNetworkManager()
    {
        return this.networkManager;
    }

    public ServerImpl getServer()
    {
        return this.server;
    }

    public boolean isOnlineMode()
    {
        return this.onlineMode;
    }

    public void setOnlineMode(final boolean onlineMode)
    {
        this.onlineMode = onlineMode;
    }

    public enum ProtocolState
    {
        HELLO,
        KEY,
        AUTHENTICATING,
        READY_TO_ACCEPT,
        ACCEPTED
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("networkManager", this.networkManager).append("gameProfile", this.gameProfile).append("secretKey", this.secretKey).append("hostname", this.hostname).append("protocolState", this.protocolState).append("onlineMode", this.onlineMode).append("serverID", this.serverID).append("token", this.token).append("ticks", this.ticks).toString();
    }
}
