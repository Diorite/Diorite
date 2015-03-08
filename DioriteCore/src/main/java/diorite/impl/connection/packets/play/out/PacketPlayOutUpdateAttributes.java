package diorite.impl.connection.packets.play.out;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import diorite.entity.attrib.AttributeProperty;
import diorite.impl.connection.EnumProtocol;
import diorite.impl.connection.EnumProtocolDirection;
import diorite.impl.connection.packets.PacketClass;
import diorite.impl.connection.packets.PacketDataSerializer;
import diorite.impl.connection.packets.play.PacketPlayOutListener;

@PacketClass(id = 0x05, protocol = EnumProtocol.PLAY, direction = EnumProtocolDirection.CLIENTBOUND)
public class PacketPlayOutUpdateAttributes implements PacketPlayOut
{
    private int entityID;
    private Collection<AttributeProperty> attributes = new ArrayList<>(2);

    public PacketPlayOutUpdateAttributes()
    {
    }

    public PacketPlayOutUpdateAttributes(final int entityID)
    {
        this.entityID = entityID;
    }

    public PacketPlayOutUpdateAttributes(final int entityID, final Collection<AttributeProperty> attributes)
    {
        this.entityID = entityID;
        this.attributes = (attributes == null) ? new ArrayList<>(1) : attributes;
    }

    @Override
    public void readPacket(final PacketDataSerializer data) throws IOException
    {
        this.entityID = data.readVarInt();
        final int size = data.readVarInt();
        if (size == 0)
        {
            return;
        }
        this.attributes = new ArrayList<>(size);
        for (int i = 0; i < size; i++)
        {
            this.attributes.add(data.readAttributeProperty());
        }
    }

    @Override
    public void writePacket(final PacketDataSerializer data) throws IOException
    {
        data.writeVarInt(this.entityID);
        data.writeVarInt(this.attributes.size());
        this.attributes.forEach(data::writeAttributeProperty);
    }

    @Override
    public void handle(final PacketPlayOutListener listener)
    {
        listener.handle(this);
    }

    public int getEntityID()
    {
        return this.entityID;
    }

    public void setEntityID(final int entityID)
    {
        this.entityID = entityID;
    }

    public Collection<AttributeProperty> getAttributes()
    {
        return this.attributes;
    }

    public void setAttributes(final Collection<AttributeProperty> attributes)
    {
        this.attributes = attributes;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("entityID", this.entityID).append("attributes", this.attributes).toString();
    }
}
