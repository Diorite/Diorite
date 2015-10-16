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

package org.diorite.material;

import org.diorite.Diorite;

/**
 * Represents item or block that can be used as fuel in furnance.
 */
public interface FuelMat
{
    /**
     * Time in centisecond for which the item is able to power the furnance.
     *
     * @return fuel "power" in centiseconds.
     */
    int getFuelPower();

    /**
     * Time in ticks for which the item is able to power the furnance.
     * This value with change if TPS settings will be changed.
     *
     * @return fuel "power" in current ticks.
     */
    default int getTickFuelPower()
    {
        final int fp = this.getFuelPower();
        final int tps = Diorite.getTps();
        return ((fp % tps) == 0) ? (fp / tps) : ((fp / tps) + 1);
    }
}
