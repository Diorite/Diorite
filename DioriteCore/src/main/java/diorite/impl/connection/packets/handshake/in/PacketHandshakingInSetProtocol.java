package diorite.impl.connection.packets.handshake.in;

import java.io.IOException;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import diorite.impl.connection.listeners.PacketHandshakingInListener;
import diorite.impl.connection.packets.PacketDataSerializer;
import diorite.impl.connection.packets.handshake.RequestType;

public class PacketHandshakingInSetProtocol implements PacketHandshakingIn
{
    private int         protocolVersion;
    private String      serverAddress;
    private int         serverPort;
    private RequestType requestType;

    @Override
    public void readPacket(final PacketDataSerializer data) throws IOException
    {
        this.protocolVersion = data.readVarInt();
        this.serverAddress = data.readText(Short.MAX_VALUE);
        this.serverPort = data.readVarInt();
        this.requestType = RequestType.getByInt(data.readVarInt());
    }

    @Override
    public void writePacket(final PacketDataSerializer data) throws IOException
    {
        data.writeVarInt(this.protocolVersion);
        data.writeText(this.serverAddress);
        data.writeVarInt(this.requestType.getValue());
    }

    @Override
    public void handle(final PacketHandshakingInListener listener)
    {
        listener.handle(this);
    }

    public int getProtocolVersion()
    {
        return this.protocolVersion;
    }

    public void setProtocolVersion(final int protocolVersion)
    {
        this.protocolVersion = protocolVersion;
    }

    public RequestType getRequestType()
    {
        return this.requestType;
    }

    public void setRequestType(final RequestType requestType)
    {
        this.requestType = requestType;
    }

    public int getServerPort()
    {
        return this.serverPort;
    }

    public void setServerPort(final int serverPort)
    {
        this.serverPort = serverPort;
    }

    public String getServerAddress()
    {
        return this.serverAddress;
    }

    public void setServerAddress(final String serverAddress)
    {
        this.serverAddress = serverAddress;
    }

    @Override
    public boolean equals(final Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (! (o instanceof PacketHandshakingInSetProtocol))
        {
            return false;
        }

        final PacketHandshakingInSetProtocol that = (PacketHandshakingInSetProtocol) o;

        return (this.protocolVersion == that.protocolVersion) && (this.serverPort == that.serverPort) && (this.requestType == that.requestType) && ! ((this.serverAddress != null) ? ! this.serverAddress.equals(that.serverAddress) : (that.serverAddress != null));

    }

    @Override
    public int hashCode()
    {
        int result = this.protocolVersion;
        result = (31 * result) + ((this.serverAddress != null) ? this.serverAddress.hashCode() : 0);
        result = (31 * result) + this.serverPort;
        result = (31 * result) + ((this.requestType != null) ? this.requestType.hashCode() : 0);
        return result;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("protocolVersion", this.protocolVersion).append("serverAddress", this.serverAddress).append("serverPort", this.serverPort).append("requestType", this.requestType).toString();
    }
}
