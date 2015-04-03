package org.diorite.impl.auth.properties;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class Property
{
    private final String name;
    private final String value;
    private final String signature;

    public Property(final String value, final String name)
    {
        this(value, name, null);
    }

    public Property(final String name, final String value, final String signature)
    {
        this.name = name;
        this.value = value;
        this.signature = signature;
    }

    public String getName()
    {
        return this.name;
    }

    public String getValue()
    {
        return this.value;
    }

    public String getSignature()
    {
        return this.signature;
    }

    public boolean hasSignature()
    {
        return this.signature != null;
    }

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
}
