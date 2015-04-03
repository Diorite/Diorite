package org.diorite.impl.connection;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.security.spec.X509EncodedKeySpec;

public final class MinecraftEncryption
{
    public static final int KEYSIZE = 1024;

    private MinecraftEncryption()
    {
    }

    public static KeyPair generateKeyPair()
    {
        try
        {
            final KeyPairGenerator localKeyPairGenerator = KeyPairGenerator.getInstance("RSA");
            localKeyPairGenerator.initialize(KEYSIZE);
            return localKeyPairGenerator.generateKeyPair();
        } catch (final NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    @SuppressWarnings("TypeMayBeWeakened")
    public static byte[] combineKeys(final String name, final PublicKey publicKey, final SecretKey secretKey)
    {
        try
        {
            final MessageDigest localMessageDigest = MessageDigest.getInstance("SHA-1");
            localMessageDigest.update(name.getBytes("ISO_8859_1"));
            localMessageDigest.update(secretKey.getEncoded());
            localMessageDigest.update(publicKey.getEncoded());
            return localMessageDigest.digest();
        } catch (final NoSuchAlgorithmException | UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public static PublicKey generatePublicKey(final byte[] paramArrayOfByte)
    {
        try
        {
            final KeySpec localX509EncodedKeySpec = new X509EncodedKeySpec(paramArrayOfByte);
            final KeyFactory localKeyFactory = KeyFactory.getInstance("RSA");
            return localKeyFactory.generatePublic(localX509EncodedKeySpec);
        } catch (final NoSuchAlgorithmException | InvalidKeySpecException ignored)
        {
        }
        return null;
    }

    @SuppressWarnings("TypeMayBeWeakened")
    public static SecretKey createKeySpec(final PrivateKey privateKey, final byte[] bytes)
    {
        return new SecretKeySpec(doFinal(privateKey, bytes), "AES");
    }

    public static byte[] doFinal(final Key key, final byte[] bytes)
    {
        try
        {
            final Cipher localCipher = Cipher.getInstance(key.getAlgorithm());
            localCipher.init(2, key);
            return localCipher.doFinal(bytes);
        } catch (final GeneralSecurityException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public static Cipher getCipher(final int i, final Key key)
    {
        try
        {
            //noinspection HardcodedFileSeparator
            final Cipher localCipher = Cipher.getInstance("AES/CFB8/NoPadding");
            localCipher.init(i, key, new IvParameterSpec(key.getEncoded()));
            return localCipher;
        } catch (final GeneralSecurityException e)
        {
            throw new RuntimeException(e);
        }
    }
}
