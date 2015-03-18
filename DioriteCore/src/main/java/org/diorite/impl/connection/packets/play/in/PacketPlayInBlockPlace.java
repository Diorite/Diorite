package org.diorite.impl.connection.packets.play.in;

import java.io.IOException;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.impl.connection.EnumProtocol;
import org.diorite.impl.connection.EnumProtocolDirection;
import org.diorite.impl.connection.packets.PacketClass;
import org.diorite.impl.connection.packets.PacketDataSerializer;
import org.diorite.impl.connection.packets.play.PacketPlayInListener;
import org.diorite.impl.inventory.item.ItemStackImpl;
import org.diorite.BlockFace;
import org.diorite.BlockLocation;

@PacketClass(id = 0x08, protocol = EnumProtocol.PLAY, direction = EnumProtocolDirection.SERVERBOUND)
public class PacketPlayInBlockPlace implements PacketPlayIn
{
    private BlockLocation location;
    private BlockFace     blockFace;
    private ItemStackImpl itemStack; // ignored by server
    private float         cursorX;
    private float         cursorY;
    private float         cursorZ;

    public PacketPlayInBlockPlace()
    {
    }

    public PacketPlayInBlockPlace(final ItemStackImpl itemStack)
    {
        this.itemStack = itemStack;
    }

    public PacketPlayInBlockPlace(final BlockLocation location, final BlockFace blockFace, final ItemStackImpl itemStack, final float cursorX, final float cursorY, final float cursorZ)
    {
        this.location = location;
        this.blockFace = blockFace;
        this.itemStack = itemStack;
        this.cursorX = cursorX;
        this.cursorY = cursorY;
        this.cursorZ = cursorZ;
    }

    @SuppressWarnings("MagicNumber")
    @Override
    public void readPacket(final PacketDataSerializer data) throws IOException
    {
        this.location = data.readBlockLocation();
        this.blockFace = data.readBlockFace();
        this.itemStack = data.readItemStack();
        this.cursorX = ((float) data.readUnsignedByte()) / 16.0f;
        this.cursorY = ((float) data.readUnsignedByte()) / 16.0f;
        this.cursorZ = ((float) data.readUnsignedByte()) / 16.0f;
    }

    @SuppressWarnings("MagicNumber")
    @Override
    public void writePacket(final PacketDataSerializer data) throws IOException
    {
        data.writeBlockLocation(this.location);
        data.writeBlockFace(this.blockFace);
        data.writeItemStack(this.itemStack);
        data.writeByte((int) (this.cursorX * 16));
        data.writeByte((int) (this.cursorY * 16));
        data.writeByte((int) (this.cursorZ * 16));
    }

    @Override
    public void handle(final PacketPlayInListener listener)
    {
        listener.handle(this);
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("location", this.location).append("blockFace", this.blockFace).append("itemStack", this.itemStack).append("cursorX", this.cursorX).append("cursorY", this.cursorY).append("cursorZ", this.cursorZ).toString();
    }
}
