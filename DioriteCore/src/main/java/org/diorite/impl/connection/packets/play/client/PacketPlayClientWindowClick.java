package org.diorite.impl.connection.packets.play.client;

import java.io.IOException;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.impl.connection.EnumProtocol;
import org.diorite.impl.connection.EnumProtocolDirection;
import org.diorite.impl.connection.packets.PacketClass;
import org.diorite.impl.connection.packets.PacketDataSerializer;
import org.diorite.impl.connection.packets.play.PacketPlayClientListener;
import org.diorite.inventory.ClickType;
import org.diorite.inventory.item.ItemStack;

@PacketClass(id = 0x0E, protocol = EnumProtocol.PLAY, direction = EnumProtocolDirection.SERVERBOUND)
public class PacketPlayClientWindowClick extends PacketPlayClient
{
    public static final int SLOT_NOT_NEEDED = - 999;
    private int       id; // inventory id
    private short     clickedSlot;
    private short     actionNumber;
    private ClickType clickType;
    private ItemStack clicked; // should be ignored?

    public PacketPlayClientWindowClick()
    {
    }

    public PacketPlayClientWindowClick(final int id, final short actionNumber, final ClickType clickType)
    {
        this.id = id;
        this.actionNumber = actionNumber;
        this.clickType = clickType;
    }

    public PacketPlayClientWindowClick(final int id, final short clickedSlot, final short actionNumber, final ClickType clickType)
    {
        this.id = id;
        this.clickedSlot = clickedSlot;
        this.actionNumber = actionNumber;
        this.clickType = clickType;
    }

    public PacketPlayClientWindowClick(final int id, final short clickedSlot, final short actionNumber, final ClickType clickType, final ItemStack clicked)
    {
        this.id = id;
        this.clickedSlot = clickedSlot;
        this.actionNumber = actionNumber;
        this.clickType = clickType;
        this.clicked = clicked;
    }

    @Override
    public void readPacket(final PacketDataSerializer data) throws IOException
    {
        this.id = data.readByte();
        this.clickedSlot = data.readShort();
        final byte button = data.readByte();
        this.actionNumber = data.readShort();
        final byte mode = data.readByte();
        this.clickType = ClickType.get(mode, button, this.clickedSlot != SLOT_NOT_NEEDED);
        data.skipBytes(data.readableBytes());
    }

    @Override
    public void writeFields(final PacketDataSerializer data) throws IOException
    {
        data.writeByte(this.id);
        data.writeShort(this.clickedSlot);
        data.writeByte(this.clickType.getButton());
        data.writeShort(this.actionNumber);
        data.writeByte(this.clickType.getMode());
        data.writeItemStack(this.clicked);
    }

    public int getId()
    {
        return this.id;
    }

    public void setId(final int id)
    {
        this.id = id;
    }

    public short getClickedSlot()
    {
        return this.clickedSlot;
    }

    public void setClickedSlot(final short clickedSlot)
    {
        this.clickedSlot = clickedSlot;
    }

    public short getActionNumber()
    {
        return this.actionNumber;
    }

    public void setActionNumber(final short actionNumber)
    {
        this.actionNumber = actionNumber;
    }

    public ClickType getClickType()
    {
        return this.clickType;
    }

    public void setClickType(final ClickType clickType)
    {
        this.clickType = clickType;
    }

    public ItemStack getClicked()
    {
        return this.clicked;
    }

    public void setClicked(final ItemStack clicked)
    {
        this.clicked = clicked;
    }

    @Override
    public void handle(final PacketPlayClientListener listener)
    {
        listener.handle(this);
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("id", this.id).append("clickedSlot", this.clickedSlot).append("actionNumber", this.actionNumber).append("clickType", this.clickType).append("clicked", this.clicked).toString();
    }
}
