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

package org.diorite.commons.math;

import java.security.Provider;
import java.security.SecureRandom;

/**
 * Represent instance of secure random number generator.
 */
public interface DioriteSecureRandom extends DioriteRandom
{
    /**
     * Returns the provider of this SecureRandom object.
     *
     * @return the provider of this SecureRandom object.
     */
    Provider getProvider();

    /**
     * Returns the name of the algorithm implemented by this SecureRandom
     * object.
     *
     * @return the name of the algorithm or {@code unknown} if the algorithm name cannot be determined.
     */
    String getAlgorithm();

    /**
     * Reseeds this random object. The given seed supplements, rather than
     * replaces, the existing seed. Thus, repeated calls are guaranteed
     * never to reduce randomness.
     *
     * @param seed
     *         the seed.
     */
    void setSeed(byte[] seed);

    /**
     * Returns the given number of seed bytes, computed using the seed
     * generation algorithm that this class uses to seed itself.  This
     * call may be used to seed other random number generators.
     *
     * @param numBytes
     *         the number of seed bytes to generate.
     *
     * @return the seed bytes.
     */
    byte[] generateSeed(int numBytes);

    @Override
    SecureRandom asRandom();
}
