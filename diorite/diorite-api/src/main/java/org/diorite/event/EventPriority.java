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

package org.diorite.event;

/**
 * Interface with event priority constants.
 */
public interface EventPriority
{
    int LOWEST  = - 1_500_000_000;
    int LOWER   = - 1_000_000_000;
    int LOW     = - 500_000_000;
    int NORMAL  = 0;
    int HIGH    = 500_000_000;
    int HIGHER  = 1_000_000_000;
    int HIGHEST = 1_500_000_000;

    int SMALLEST_STEP = HIGH / 8;
    int SMALLER_STEP  = SMALLEST_STEP * 2;
    int SMALL_STEP    = SMALLEST_STEP * 3;
    int MEDIUM_STEP   = SMALLEST_STEP * 4;
    int BIG_STEP      = SMALLEST_STEP * 5;
    int BIGGER_STEP   = SMALLEST_STEP * 6;
    int BIGGEST_STEP  = SMALLEST_STEP * 7;
}
