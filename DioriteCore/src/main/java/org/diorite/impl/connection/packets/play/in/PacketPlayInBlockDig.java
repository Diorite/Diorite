package org.diorite.impl.connection.packets.play.in;

import java.io.IOException;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.impl.connection.EnumProtocol;
import org.diorite.impl.connection.EnumProtocolDirection;
import org.diorite.impl.connection.packets.PacketClass;
import org.diorite.impl.connection.packets.PacketDataSerializer;
import org.diorite.impl.connection.packets.play.PacketPlayInListener;
import org.diorite.BlockFace;
import org.diorite.BlockLocation;

@PacketClass(id = 0x07, protocol = EnumProtocol.PLAY, direction = EnumProtocolDirection.SERVERBOUND)
public class PacketPlayInBlockDig extends PacketPlayIn
{
    private BlockDigAction action;
    private BlockLocation  blockLocation;
    private BlockFace      blockFace;

    public PacketPlayInBlockDig()
    {
    }

    public PacketPlayInBlockDig(final BlockDigAction action)
    {
        this.action = action;
        this.blockLocation = BlockLocation.ZERO;
    }

    public PacketPlayInBlockDig(final BlockDigAction action, final BlockLocation blockLocation, final BlockFace blockFace)
    {
        this.action = action;
        this.blockLocation = blockLocation;
        this.blockFace = blockFace;
    }

    @Override
    public void readPacket(final PacketDataSerializer data) throws IOException
    {
        this.action = BlockDigAction.values()[data.readByte()];
        this.blockLocation = data.readBlockLocation();
        this.blockFace = data.readBlockFace();
    }

    @Override
    public void writeFields(final PacketDataSerializer data) throws IOException
    {
        data.writeByte(this.action.ordinal());
        data.writeBlockLocation(this.blockLocation);
        data.writeBlockFace(this.blockFace);
    }

    @Override
    public void handle(final PacketPlayInListener listener)
    {
        listener.handle(this);
    }

    public BlockDigAction getAction()
    {
        return this.action;
    }

    public void setAction(final BlockDigAction action)
    {
        this.action = action;
    }

    public BlockLocation getBlockLocation()
    {
        return this.blockLocation;
    }

    public void setBlockLocation(final BlockLocation blockLocation)
    {
        this.blockLocation = blockLocation;
    }

    public BlockFace getBlockFace()
    {
        return this.blockFace;
    }

    public void setBlockFace(final BlockFace blockFace)
    {
        this.blockFace = blockFace;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("action", this.action).append("blockLocation", this.blockLocation).append("blockFace", this.blockFace).toString();
    }

    public enum BlockDigAction
    {
        START_DIG,
        CANCEL_DIG,
        FINISH_DIG,
        DROP_ITEM_STACK,
        DROP_ITEM,
        SHOT_ARROW_OR_EAT
    }
}
