package diorite.impl.entity;

import java.util.UUID;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.mojang.authlib.GameProfile;

import diorite.ImmutableLocation;
import diorite.cfg.magic.MagicNumbers;
import diorite.entity.Player;
import diorite.entity.attrib.AttributeModifier;
import diorite.entity.attrib.AttributeProperty;
import diorite.entity.attrib.AttributeType;
import diorite.entity.attrib.ModifierOperation;
import diorite.impl.ServerImpl;
import diorite.impl.connection.NetworkManager;
import diorite.impl.connection.packets.play.out.PacketPlayOutUpdateAttributes;
import diorite.impl.entity.attrib.AttributeModifierImpl;
import diorite.impl.map.chunk.PlayerChunksImpl;
import diorite.map.chunk.ChunkPos;

public class PlayerImpl extends AttributableEntityImpl implements Player
{
    // TODO: move this
    private static final AttributeModifier tempSprintMod = new AttributeModifierImpl(UUID.fromString("662A6B8D-DA3E-4C1C-8813-96EA6097278D"), MagicNumbers.ATTRIBUTES__MODIFIERS__SPRINT, ModifierOperation.ADD_PERCENTAGE);
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

    public PlayerChunksImpl getPlayerChunks()
    {
        return this.playerChunks;
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
        final AttributeProperty attrib = this.getProperty(AttributeType.GENERIC_MOVEMENT_SPEED, MagicNumbers.PLAYER__MOVE_SPEED);
        attrib.removeModifier(tempSprintMod.getUuid());
        if (isSprinting)
        {
            attrib.addModifier(tempSprintMod);
        }
        this.networkManager.handle(new PacketPlayOutUpdateAttributes(this.id, this.attributes));
        this.isSprinting = isSprinting;
    }

    @Override
    public byte getViewDistance()
    {
        return this.viewDistance;
    }

    public void setViewDistance(final byte viewDistance)
    {
        this.viewDistance = viewDistance;
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
    public void move(final double modX, final double modY, final double modZ, final float modYaw, final float modPitch)
    {
        if (! ChunkPos.fromWorldPos((int) this.x, (int) this.z, this.world).equals(ChunkPos.fromWorldPos((int) this.lastX, (int) this.lastZ, this.world)))
        {
            this.playerChunks.update();
        }
        super.move(modX, modY, modZ, modYaw, modPitch);
    }

    @Override
    public void setPosition(final double modX, final double modY, final double modZ)
    {
        if (! ChunkPos.fromWorldPos((int) this.x, (int) this.z, this.world).equals(ChunkPos.fromWorldPos((int) this.lastX, (int) this.lastZ, this.world)))
        {
            this.playerChunks.update();
        }
        super.setPosition(modX, modY, modZ);
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("name", this.gameProfile.getName()).append("uuid", this.gameProfile.getId()).toString();
    }
}
