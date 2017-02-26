/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2017. Diorite (by Bartłomiej Mazur (aka GotoFinal))
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package org.diorite.impl.protocol.p16w50a.clientbound;

import javax.annotation.Nullable;

import java.security.PublicKey;

import org.diorite.impl.protocol.AbstractPacketDataSerializer;
import org.diorite.core.protocol.InvalidPacketException;
import org.diorite.core.protocol.PacketClass;
import org.diorite.core.protocol.connection.ProtocolDirection;
import org.diorite.core.protocol.connection.internal.ProtocolState;

@SuppressWarnings("MagicNumber")
@PacketClass(id = 0x01, direction = ProtocolDirection.CLIENTBOUND, state = ProtocolState.LOGIN, minSize = 170, maxSize = 170, preferredSize = 170)
public class CL01EncryptionRequest extends ClientboundPacket
{
    private @Nullable PublicKey publicKey; // 162 bytes + 2 bytes size
    private @Nullable byte[]    verifyToken; // 4 bytes + 1 byte size

    public CL01EncryptionRequest()
    {
    }

    public CL01EncryptionRequest(PublicKey publicKey, byte[] verifyToken)
    {
        this.publicKey = publicKey;
        this.verifyToken = verifyToken;
    }

    @Nullable
    public PublicKey getPublicKey()
    {
        return this.publicKey;
    }

    public void setPublicKey(PublicKey publicKey)
    {
        this.publicKey = publicKey;
    }

    @Nullable
    public byte[] getVerifyToken()
    {
        return this.verifyToken;
    }

    public void setVerifyToken(byte[] verifyToken)
    {
        this.verifyToken = verifyToken;
    }

    @Override
    protected void write(AbstractPacketDataSerializer serializer) throws InvalidPacketException
    {
        assert this.publicKey != null;
        assert this.verifyToken != null;
        serializer.writeText("");
        serializer.writeByteWord(this.publicKey.getEncoded());
        serializer.writeByteWord(this.verifyToken);
    }
}
