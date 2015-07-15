package org.diorite.impl.connection.packets.login.out;

import java.io.IOException;
import java.security.PublicKey;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.impl.connection.EnumProtocol;
import org.diorite.impl.connection.EnumProtocolDirection;
import org.diorite.impl.connection.MinecraftEncryption;
import org.diorite.impl.connection.packets.PacketClass;
import org.diorite.impl.connection.packets.PacketDataSerializer;
import org.diorite.impl.connection.packets.login.PacketLoginOutListener;

@PacketClass(id = 0x01, protocol = EnumProtocol.LOGIN, direction = EnumProtocolDirection.CLIENTBOUND)
public class PacketLoginOutEncryptionBegin extends PacketLoginOut
{
    public static final int SERVERID_SIZE = 20;
    private String    serverID; // empty?
    private PublicKey publicKey;
    private byte[]    verifyToken;

    public PacketLoginOutEncryptionBegin()
    {
    }

    public PacketLoginOutEncryptionBegin(final String serverID, final PublicKey publicKey, final byte[] verifyToken)
    {
        this.serverID = serverID;
        this.publicKey = publicKey;
        this.verifyToken = verifyToken;
    }

    @Override
    public void readPacket(final PacketDataSerializer data) throws IOException
    {
        this.serverID = data.readText(SERVERID_SIZE);
        this.publicKey = MinecraftEncryption.generatePublicKey(data.readByteWord());
        this.verifyToken = data.readByteWord();
    }

    @Override
    public void writeFields(final PacketDataSerializer data) throws IOException
    {
        data.writeText(this.serverID);
        data.writeByteWord(this.publicKey.getEncoded());
        data.writeByteWord(this.verifyToken);
    }

    @Override
    public void handle(final PacketLoginOutListener listener)
    {
        listener.handle(this);
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("serverID", this.serverID).append("publicKey", this.publicKey).append("verifyToken", this.verifyToken).toString();
    }
}
