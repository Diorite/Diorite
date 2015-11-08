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

package org.diorite.auth;

import java.security.PublicKey;

import org.diorite.nbt.NbtSerializable;

/**
 * Represent GameProfile property, each property can have name, value and signature.
 */
public interface Property extends NbtSerializable
{
    /**
     * Returns name of this property.
     *
     * @return name of this property.
     */
    String getName();

    /**
     * Returns value of this property.
     *
     * @return value of this property.
     */
    String getValue();

    /**
     * Returns signature of this property.
     *
     * @return signature of this property.
     */
    String getSignature();

    /**
     * Returns true if property have signature.
     *
     * @return true if property have signature.
     */
    boolean hasSignature();

    /**
     * Checks if this property is signed with valid signature for given key.
     *
     * @param publicKey key to be checked.
     *
     * @return true if this property is signed with valid signature.
     */
    boolean isSignatureValid(PublicKey publicKey);
}
