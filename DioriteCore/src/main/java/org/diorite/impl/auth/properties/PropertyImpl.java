/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2015 Diorite (by Bartłomiej Mazur (aka GotoFinal))
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

package org.diorite.impl.auth.properties;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.auth.Property;
import org.diorite.nbt.NbtTagCompound;

public class PropertyImpl implements Property
{
    private final String name;
    private final String value;
    private final String signature;

    public PropertyImpl(final String value, final String name)
    {
        this(value, name, null);
    }

    public PropertyImpl(final String name, final String value, final String signature)
    {
        this.name = name;
        this.value = value;
        this.signature = signature;
    }

    public PropertyImpl(final NbtTagCompound tag)
    {
        this.name = tag.getString("Name");
        this.value = tag.getString("Value");
        this.signature = tag.getString("Signature");
    }

    @Override
    public String getName()
    {
        return this.name;
    }

    @Override
    public String getValue()
    {
        return this.value;
    }

    @Override
    public String getSignature()
    {
        return this.signature;
    }

    @Override
    public boolean hasSignature()
    {
        return this.signature != null;
    }

    @Override
    public boolean isSignatureValid(final PublicKey publicKey)
    {
        try
        {
            final Signature signature = Signature.getInstance("SHA1withRSA");
            signature.initVerify(publicKey);
            signature.update(this.value.getBytes());
            return signature.verify(Base64.decodeBase64(this.signature));
        } catch (final NoSuchAlgorithmException | SignatureException | InvalidKeyException e)
        {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("name", this.name).append("value", this.value).append("signature", this.signature).toString();
    }

    @Override
    public NbtTagCompound serializeToNBT()
    {
        final NbtTagCompound nbt = new NbtTagCompound();
        if (this.signature != null)
        {
            nbt.setString("Signature", this.signature);
        }
        nbt.setString("Value", this.value);
        nbt.setString("Name", this.name);
        return nbt;
    }
}
