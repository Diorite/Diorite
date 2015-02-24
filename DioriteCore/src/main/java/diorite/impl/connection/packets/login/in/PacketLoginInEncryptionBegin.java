package diorite.impl.connection.packets.login.in;

import java.io.IOException;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import diorite.impl.connection.packets.login.PacketLoginInListener;
import diorite.impl.connection.packets.PacketDataSerializer;

public class PacketLoginInEncryptionBegin implements PacketLoginIn
{
    private byte[] sharedSecret;
    private byte[] verifyToken;

    public PacketLoginInEncryptionBegin()
    {
    }

    public PacketLoginInEncryptionBegin(final byte[] sharedSecret, final byte[] verifyToken)
    {
        this.sharedSecret = sharedSecret;
        this.verifyToken = verifyToken;
    }

    @Override
    public void readPacket(final PacketDataSerializer data) throws IOException
    {
        this.sharedSecret = data.readByteWord();
        this.verifyToken = data.readByteWord();
    }

    @Override
    public void writePacket(final PacketDataSerializer data) throws IOException
    {
        data.writeByteWord(this.sharedSecret);
        data.writeByteWord(this.verifyToken);
    }

    public byte[] getSharedSecret()
    {
        return this.sharedSecret;
    }

    public void setSharedSecret(final byte[] sharedSecret)
    {
        this.sharedSecret = sharedSecret;
    }

    public byte[] getVerifyToken()
    {
        return this.verifyToken;
    }

    public void setVerifyToken(final byte[] verifyToken)
    {
        this.verifyToken = verifyToken;
    }

    @Override
    public void handle(final PacketLoginInListener listener)
    {
        listener.handle(this);
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("sharedSecret", this.sharedSecret).append("verifyToken", this.verifyToken).toString();
    }
}
