package org.diorite.impl.entity;

import java.util.UUID;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.impl.ServerImpl;
import org.diorite.impl.auth.GameProfile;
import org.diorite.impl.connection.NetworkManager;
import org.diorite.impl.connection.packets.play.in.PacketPlayInAbilities;
import org.diorite.impl.connection.packets.play.out.PacketPlayOutAbilities;
import org.diorite.impl.connection.packets.play.out.PacketPlayOutChat;
import org.diorite.impl.connection.packets.play.out.PacketPlayOutPlayerInfo;
import org.diorite.impl.connection.packets.play.out.PacketPlayOutResourcePackSend;
import org.diorite.impl.connection.packets.play.out.PacketPlayOutUpdateAttributes;
import org.diorite.impl.connection.packets.play.out.PacketPlayOutWorldParticles;
import org.diorite.impl.entity.attrib.AttributeModifierImpl;
import org.diorite.impl.world.chunk.PlayerChunksImpl;
import org.diorite.GameMode;
import org.diorite.ImmutableLocation;
import org.diorite.Particle;
import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.chat.ChatPosition;
import org.diorite.chat.component.BaseComponent;
import org.diorite.entity.EntityType;
import org.diorite.entity.Player;
import org.diorite.entity.attrib.AttributeModifier;
import org.diorite.entity.attrib.AttributeProperty;
import org.diorite.entity.attrib.AttributeType;
import org.diorite.entity.attrib.ModifierOperation;

public class PlayerImpl extends AttributableEntityImpl implements Player
{
    // TODO: move this
    private static final AttributeModifier tempSprintMod = new AttributeModifierImpl(UUID.fromString("662A6B8D-DA3E-4C1C-8813-96EA6097278D"), MagicNumbers.ATTRIBUTES__MODIFIERS__SPRINT, ModifierOperation.ADD_PERCENTAGE);
    protected final GameProfile      gameProfile;
    protected final NetworkManager   networkManager;
    protected       boolean          isCrouching;
    protected       boolean          isSprinting;
    protected       byte             viewDistance;
    protected       byte             renderDistance;
    protected final PlayerChunksImpl playerChunks;
    protected       GameMode         gameMode;

    protected PacketPlayOutAbilities abilities = new PacketPlayOutAbilities(false, false, false, false, Player.WALK_SPEED, Player.FLY_SPEED);

    // TODO: add saving/loading data to/from NBT
    public PlayerImpl(final ServerImpl server, final int id, final GameProfile gameProfile, final NetworkManager networkManager, final ImmutableLocation location)
    {
        super(server, id, location);
        this.gameProfile = gameProfile;
        this.uniqueID = gameProfile.getId();
        this.networkManager = networkManager;
        this.renderDistance = server.getRenderDistance();
        this.playerChunks = new PlayerChunksImpl(this);
        this.gameMode = this.world.getDefaultGameMode();
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
    public GameMode getGameMode()
    {
        return this.gameMode;
    }

    @Override
    public void setGameMode(final GameMode gameMode)
    {
        this.gameMode = gameMode;
        this.server.getPlayersManager().forEach(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.PlayerInfoAction.UPDATE_GAMEMODE, new PacketPlayOutPlayerInfo.PlayerInfoData(this.getUniqueID(), gameMode)));
    }

    @Override
    public int getPing()
    {
        return this.networkManager.getPing();
    }

    @Override
    public void kick(final BaseComponent s)
    {
        this.networkManager.close(s, false);
    }

    @Override
    public void sendMessage(final ChatPosition position, final BaseComponent component)
    {
        this.networkManager.sendPacket(new PacketPlayOutChat(component, position));
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
        this.networkManager.sendPacket(new PacketPlayOutUpdateAttributes(this.id, this.attributes));
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
    public void setResourcePack(final String resourcePack)
    {
        this.networkManager.sendPacket(new PacketPlayOutResourcePackSend(resourcePack, "DIORITE"));
    }

    @Override
    public void setResourcePack(final String resourcePack, final String hash)
    {
        this.networkManager.sendPacket(new PacketPlayOutResourcePackSend(resourcePack, hash));
    }

    @Override
    public boolean canFly()
    {
        return this.abilities.isCanFly();
    }

    @Override
    public void setCanFly(final boolean value)
    {
        this.abilities.setCanFly(value);
        if (! value)
        {
            this.abilities.setFlying(false);
        }
        this.updateAbilities();
    }

    @Override
    public void setCanFly(final boolean value, final double flySpeed)
    {
        this.abilities.setCanFly(value);
        this.abilities.setFlyingSpeed((float) flySpeed);
        if (! value)
        {
            this.abilities.setFlying(false);
        }
        this.updateAbilities();
    }

    @Override
    public float getFlySpeed()
    {
        return this.abilities.getFlyingSpeed();
    }

    @Override
    public void setFlySpeed(final double flySpeed)
    {
        this.abilities.setFlyingSpeed((float) flySpeed);
        this.updateAbilities();
    }

    @Override
    public float getWalkSpeed()
    {
        return this.abilities.getWalkingSpeed();
    }

    @Override
    public void setWalkSpeed(final double walkSpeed)
    {
        this.abilities.setWalkingSpeed((float) walkSpeed);
        this.updateAbilities();
    }

    @Override
    public void showParticle(final Particle particle, final boolean isLongDistance, final float x, final float y, final float z, final float offsetX, final float offsetY, final float offsetZ, final float particleData, final int particleCount, final int... data)
    {
        this.networkManager.sendPacket(new PacketPlayOutWorldParticles(particle, isLongDistance, x, y, z, offsetX, offsetY, offsetZ, particleData, particleCount, data));
    }

    public PacketPlayOutAbilities getAbilities()
    {
        return this.abilities;
    }

    public void setAbilities(final PacketPlayOutAbilities abilities)
    {
        this.abilities = abilities;
    }

    public void setAbilities(final PacketPlayInAbilities abilities)
    {
        // TOOD: I should check what player want change here (cheats)
        this.abilities.setCanFly(abilities.isCanFly());
        this.abilities.setFlying(abilities.isFlying());
        this.abilities.setWalkingSpeed(abilities.getWalkingSpeed());
        this.abilities.setFlyingSpeed(abilities.getFlyingSpeed());
        this.abilities.setInvulnerable(abilities.isInvulnerable());
        this.abilities.setCanInstantlyBuild(abilities.isCanInstantlyBuild());
    }

    private void updateAbilities()
    {
        this.abilities.send(this.networkManager);
    }

    @Override
    public UUID getUniqueID()
    {
        return this.gameProfile.getId();
    }

//    @Override
//    public void move(final double modX, final double modY, final double modZ, final float modYaw, final float modPitch)
//    {
////        if (! ChunkPos.fromWorldPos((int) this.x, (int) this.z, this.world).equals(ChunkPos.fromWorldPos((int) this.lastX, (int) this.lastZ, this.world)))
////        {
////            this.playerChunks.wantUpdate();
////        }
//        super.move(modX, modY, modZ, modYaw, modPitch);
//    }

//    @Override
//    public void setPosition(final double modX, final double modY, final double modZ)
//    {
////        if (! ChunkPos.fromWorldPos((int) this.x, (int) this.z, this.world).equals(ChunkPos.fromWorldPos((int) this.lastX, (int) this.lastZ, this.world)))
////        {
////            this.playerChunks.wantUpdate();
////        }
//        super.setPosition(modX, modY, modZ);
//    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("name", this.gameProfile.getName()).append("uuid", this.gameProfile.getId()).toString();
    }

    @Override
    public EntityType getType()
    {
        return EntityType.PLAYER;
    }
}
