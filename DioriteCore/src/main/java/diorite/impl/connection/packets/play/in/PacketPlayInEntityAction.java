package diorite.impl.connection.packets.play.in;

import java.io.IOException;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import diorite.EntityAction;
import diorite.impl.connection.EnumProtocol;
import diorite.impl.connection.EnumProtocolDirection;
import diorite.impl.connection.packets.PacketClass;
import diorite.impl.connection.packets.PacketDataSerializer;
import diorite.impl.connection.packets.play.PacketPlayInListener;

@PacketClass(id = 0x0B, protocol = EnumProtocol.PLAY, direction = EnumProtocolDirection.SERVERBOUND)
public class PacketPlayInEntityAction implements PacketPlayIn
{
    private int          entityID;
    private EntityAction entityAction;
    private int          jumpBoost;

    public PacketPlayInEntityAction()
    {
    }

    public PacketPlayInEntityAction(final int entityID, final EntityAction entityAction, final int jumpBoost)
    {
        this.entityID = entityID;
        this.entityAction = entityAction;
        this.jumpBoost = jumpBoost;
    }

    public PacketPlayInEntityAction(final int entityID, final EntityAction entityAction)
    {
        this.entityID = entityID;
        this.entityAction = entityAction;
    }

    @Override
    public void readPacket(final PacketDataSerializer data) throws IOException
    {
        this.entityID = data.readVarInt();
        this.entityAction = EntityAction.getByID(data.readVarInt());
        this.jumpBoost = data.readVarInt();
    }

    @Override
    public void writePacket(final PacketDataSerializer data) throws IOException
    {
        data.writeVarInt(this.entityID);
        data.writeVarInt(this.entityAction.getId());
        data.writeVarInt(this.jumpBoost);
    }

    public int getEntityID()
    {
        return this.entityID;
    }

    public void setEntityID(final int entityID)
    {
        this.entityID = entityID;
    }

    public EntityAction getEntityAction()
    {
        return this.entityAction;
    }

    public void setEntityAction(final EntityAction entityAction)
    {
        this.entityAction = entityAction;
    }

    public int getJumpBoost()
    {
        return this.jumpBoost;
    }

    public void setJumpBoost(final int jumpBoost)
    {
        this.jumpBoost = jumpBoost;
    }

    @Override
    public void handle(final PacketPlayInListener listener)
    {
        listener.handle(this);
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("entityID", this.entityID).append("entityAction", this.entityAction).append("jumpBoost", this.jumpBoost).toString();
    }
}
