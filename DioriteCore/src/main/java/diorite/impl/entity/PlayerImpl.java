package diorite.impl.entity;

import java.util.UUID;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.mojang.authlib.GameProfile;

import diorite.ImmutableLocation;
import diorite.entity.Player;
import diorite.impl.ServerImpl;
import diorite.impl.connection.NetworkManager;
import diorite.impl.map.chunk.PlayerChunksImpl;
import diorite.map.chunk.ChunkPos;

public class PlayerImpl extends EntityImpl implements Player
{
    protected final GameProfile      gameProfile;
    protected       NetworkManager   networkManager;
    protected       boolean          isCrouching;
    protected       boolean          isSprinting;
    protected       byte             viewDistance;
    protected       byte             renderDistance;
    protected       PlayerChunksImpl playerChunks;

    public PlayerImpl(final ServerImpl server, final int id, final GameProfile gameProfile, final NetworkManager networkManager, final ImmutableLocation location)
    {
        super(server, id, location);
        this.gameProfile = gameProfile;
        this.uniqueID = gameProfile.getId();
        this.networkManager = networkManager;
        this.renderDistance = server.getRenderDistance();
        this.playerChunks = new PlayerChunksImpl(this);
    }

    public GameProfile getGameProfile()
    {
        return this.gameProfile;
    }

    public NetworkManager getNetworkManager()
    {
        return this.networkManager;
    }

    @Override
    public String getName()
    {
        return this.gameProfile.getName();
    }

    @Override
    public void sendMessage(final String str)
    {
        // TODO: implement
//        this.networkManager.handle();
    }

    @Override
    public void move(final double modX, final double modY, final double modZ, final float modYaw, final float modPitch)
    {
        if (!ChunkPos.fromWorldPos((int) this.x, (int) this.z, this.world).equals(ChunkPos.fromWorldPos((int) this.lastX, (int) this.lastZ, this.world)))
        {
            this.playerChunks.update();
        }
        super.move(modX, modY, modZ, modYaw, modPitch);
    }

    @Override
    public boolean isCrouching()
    {
        return this.isCrouching;
    }

    @Override
    public void setCrouching(final boolean isCrouching)
    {
        this.isCrouching = isCrouching;
    }

    @Override
    public boolean isSprinting()
    {
        return this.isSprinting;
    }

    @Override
    public void setSprinting(final boolean isSprinting)
    {
        this.isSprinting = isSprinting;
    }

    public void setViewDistance(final byte viewDistance)
    {
        this.viewDistance = viewDistance;
    }

    @Override
    public byte getViewDistance()
    {
        return this.viewDistance;
    }

    @Override
    public byte getRenderDistance()
    {
        return this.renderDistance;
    }

    @Override
    public void setRenderDistance(final byte renderDistance)
    {
        this.renderDistance = renderDistance;
    }

    @Override
    public UUID getUniqueID()
    {
        return this.gameProfile.getId();
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("name", this.gameProfile.getName()).append("uuid", this.gameProfile.getId()).toString();
    }
}
