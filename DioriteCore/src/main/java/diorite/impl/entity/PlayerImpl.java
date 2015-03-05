package diorite.impl.entity;

import java.util.UUID;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.mojang.authlib.GameProfile;

import diorite.entity.Player;
import diorite.impl.ServerImpl;
import diorite.impl.connection.NetworkManager;

public class PlayerImpl extends EntityImpl implements Player
{
    protected final GameProfile    gameProfile;
    protected       NetworkManager networkManager;
    protected       boolean        isCrouching;
    protected       boolean        isSprinting;

    public PlayerImpl(final ServerImpl server, final int id, final GameProfile gameProfile, final NetworkManager networkManager)
    {
        super(server, id);
        this.gameProfile = gameProfile;
        this.uniqueID = gameProfile.getId();
        this.networkManager = networkManager;
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
