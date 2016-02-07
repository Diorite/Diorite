/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2016. Diorite (by Bartłomiej Mazur (aka GotoFinal))
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

package org.diorite.utils.math.geometry;

import static org.diorite.utils.math.DioriteMathUtils.square;


import javax.vecmath.Vector2d;
import javax.vecmath.Vector2f;
import javax.vecmath.Vector3d;
import javax.vecmath.Vector3f;

/**
 * Possible shapes of entity lookup.
 */
@SuppressWarnings("Duplicates")
public enum LookupShape
{
    RECTANGLE
            {
                @Override
                public Result isIn(final double cx, final double cy, final double cz, final double size, final double px, final double py, final double pz)
                {
                    final double dx;
                    final double dy;
                    final double dz;
                    if (((dx = Math.abs(px - cx) - size) > 0) || ((dy = Math.abs(py - cy) - size) > 0) || ((dz = Math.abs(pz - cz) - size) > 0))
                    {
                        return Result.OUT;
                    }
                    if ((dx == 0) || (dy == 0) || (dz == 0))
                    {
                        return Result.ON;
                    }
                    return Result.IN;
                }

                @Override
                public Result isIn(final double cx, final double cy, final double cz, final double sxz, final double sy, final double px, final double py, final double pz)
                {
                    final double dx;
                    final double dy;
                    final double dz;
                    if (((dx = Math.abs(px - cx) - sxz) > 0) || ((dy = Math.abs(py - cy) - sy) > 0) || ((dz = Math.abs(pz - cz) - sxz) > 0))
                    {
                        return Result.OUT;
                    }
                    if ((dx == 0) || (dy == 0) || (dz == 0))
                    {
                        return Result.ON;
                    }
                    return Result.IN;
                }

                @Override
                public Result isIn(final double cx, final double cy, final double cz, final double sx, final double sy, final double sz, final double px, final double py, final double pz)
                {
                    final double dx;
                    final double dy;
                    final double dz;
                    if (((dx = Math.abs(px - cx) - sx) > 0) || ((dy = Math.abs(py - cy) - sy) > 0) || ((dz = Math.abs(pz - cz) - sz) > 0))
                    {
                        return Result.OUT;
                    }
                    if ((dx == 0) || (dy == 0) || (dz == 0))
                    {
                        return Result.ON;
                    }
                    return Result.IN;
                }

                @Override
                public Result isIn(final long cx, final long cy, final long cz, final long size, final long px, final long py, final long pz)
                {
                    final long dx;
                    final long dy;
                    final long dz;
                    if (((dx = Math.abs(px - cx) - size) > 0) || ((dy = Math.abs(py - cy) - size) > 0) || ((dz = Math.abs(pz - cz) - size) > 0))
                    {
                        return Result.OUT;
                    }
                    if ((dx == 0) || (dy == 0) || (dz == 0))
                    {
                        return Result.ON;
                    }
                    return Result.IN;
                }

                @Override
                public Result isIn(final long cx, final long cy, final long cz, final long sxz, final long sy, final long px, final long py, final long pz)
                {
                    final long dx;
                    final long dy;
                    final long dz;
                    if (((dx = Math.abs(px - cx) - sxz) > 0) || ((dy = Math.abs(py - cy) - sy) > 0) || ((dz = Math.abs(pz - cz) - sxz) > 0))
                    {
                        return Result.OUT;
                    }
                    if ((dx == 0) || (dy == 0) || (dz == 0))
                    {
                        return Result.ON;
                    }
                    return Result.IN;
                }

                @Override
                public Result isIn(final long cx, final long cy, final long cz, final long sx, final long sy, final long sz, final long px, final long py, final long pz)
                {
                    final long dx;
                    final long dy;
                    final long dz;
                    if (((dx = Math.abs(px - cx) - sx) > 0) || ((dy = Math.abs(py - cy) - sy) > 0) || ((dz = Math.abs(pz - cz) - sz) > 0))
                    {
                        return Result.OUT;
                    }
                    if ((dx == 0) || (dy == 0) || (dz == 0))
                    {
                        return Result.ON;
                    }
                    return Result.IN;
                }

                @Override
                public Result isIn(final float cx, final float cy, final float cz, final float size, final float px, final float py, final float pz)
                {
                    final float dx;
                    final float dy;
                    final float dz;
                    if (((dx = Math.abs(px - cx) - size) > 0) || ((dy = Math.abs(py - cy) - size) > 0) || ((dz = Math.abs(pz - cz) - size) > 0))
                    {
                        return Result.OUT;
                    }
                    if ((dx == 0) || (dy == 0) || (dz == 0))
                    {
                        return Result.ON;
                    }
                    return Result.IN;
                }

                @Override
                public Result isIn(final float cx, final float cy, final float cz, final float sxz, final float sy, final float px, final float py, final float pz)
                {
                    final float dx;
                    final float dy;
                    final float dz;
                    if (((dx = Math.abs(px - cx) - sxz) > 0) || ((dy = Math.abs(py - cy) - sy) > 0) || ((dz = Math.abs(pz - cz) - sxz) > 0))
                    {
                        return Result.OUT;
                    }
                    if ((dx == 0) || (dy == 0) || (dz == 0))
                    {
                        return Result.ON;
                    }
                    return Result.IN;
                }

                @Override
                public Result isIn(final float cx, final float cy, final float cz, final float sx, final float sy, final float sz, final float px, final float py, final float pz)
                {
                    final float dx;
                    final float dy;
                    final float dz;
                    if (((dx = Math.abs(px - cx) - sx) > 0) || ((dy = Math.abs(py - cy) - sy) > 0) || ((dz = Math.abs(pz - cz) - sz) > 0))
                    {
                        return Result.OUT;
                    }
                    if ((dx == 0) || (dy == 0) || (dz == 0))
                    {
                        return Result.ON;
                    }
                    return Result.IN;
                }

                @Override
                public Result isIn(final int cx, final int cy, final int cz, final int size, final int px, final int py, final int pz)
                {
                    final int dx;
                    final int dy;
                    final int dz;
                    if (((dx = Math.abs(px - cx) - size) > 0) || ((dy = Math.abs(py - cy) - size) > 0) || ((dz = Math.abs(pz - cz) - size) > 0))
                    {
                        return Result.OUT;
                    }
                    if ((dx == 0) || (dy == 0) || (dz == 0))
                    {
                        return Result.ON;
                    }
                    return Result.IN;
                }

                @Override
                public Result isIn(final int cx, final int cy, final int cz, final int sxz, final int sy, final int px, final int py, final int pz)
                {
                    final int dx;
                    final int dy;
                    final int dz;
                    if (((dx = Math.abs(px - cx) - sxz) > 0) || ((dy = Math.abs(py - cy) - sy) > 0) || ((dz = Math.abs(pz - cz) - sxz) > 0))
                    {
                        return Result.OUT;
                    }
                    if ((dx == 0) || (dy == 0) || (dz == 0))
                    {
                        return Result.ON;
                    }
                    return Result.IN;
                }

                @Override
                public Result isIn(final int cx, final int cy, final int cz, final int sx, final int sy, final int sz, final int px, final int py, final int pz)
                {
                    final int dx;
                    final int dy;
                    final int dz;
                    if (((dx = Math.abs(px - cx) - sx) > 0) || ((dy = Math.abs(py - cy) - sy) > 0) || ((dz = Math.abs(pz - cz) - sz) > 0))
                    {
                        return Result.OUT;
                    }
                    if ((dx == 0) || (dy == 0) || (dz == 0))
                    {
                        return Result.ON;
                    }
                    return Result.IN;
                }

                @Override
                public boolean isNotOutside(final double cx, final double cy, final double cz, final double size, final double px, final double py, final double pz)
                {
                    return ((Math.abs(px - cx) - size) <= 0) && ((Math.abs(py - cy) - size) <= 0) && ((Math.abs(pz - cz) - size) <= 0);
                }

                @Override
                public boolean isNotOutside(final double cx, final double cy, final double cz, final double sxz, final double sy, final double px, final double py, final double pz)
                {
                    return ((Math.abs(px - cx) - sxz) <= 0) && ((Math.abs(py - cy) - sy) <= 0) && ((Math.abs(pz - cz) - sxz) <= 0);
                }

                @Override
                public boolean isNotOutside(final double cx, final double cy, final double cz, final double sx, final double sy, final double sz, final double px, final double py, final double pz)
                {
                    return ((Math.abs(px - cx) - sx) <= 0) && ((Math.abs(py - cy) - sy) <= 0) && ((Math.abs(pz - cz) - sz) <= 0);
                }

                @Override
                public boolean isNotOutside(final long cx, final long cy, final long cz, final long size, final long px, final long py, final long pz)
                {
                    return ((Math.abs(px - cx) - size) <= 0) && ((Math.abs(py - cy) - size) <= 0) && ((Math.abs(pz - cz) - size) <= 0);
                }

                @Override
                public boolean isNotOutside(final long cx, final long cy, final long cz, final long sxz, final long sy, final long px, final long py, final long pz)
                {
                    return ((Math.abs(px - cx) - sxz) <= 0) && ((Math.abs(py - cy) - sy) <= 0) && ((Math.abs(pz - cz) - sxz) <= 0);
                }

                @Override
                public boolean isNotOutside(final long cx, final long cy, final long cz, final long sx, final long sy, final long sz, final long px, final long py, final long pz)
                {
                    return ((Math.abs(px - cx) - sx) <= 0) && ((Math.abs(py - cy) - sy) <= 0) && ((Math.abs(pz - cz) - sz) <= 0);
                }

                @Override
                public boolean isNotOutside(final float cx, final float cy, final float cz, final float size, final float px, final float py, final float pz)
                {
                    return ((Math.abs(px - cx) - size) <= 0) && ((Math.abs(py - cy) - size) <= 0) && ((Math.abs(pz - cz) - size) <= 0);
                }

                @Override
                public boolean isNotOutside(final float cx, final float cy, final float cz, final float sxz, final float sy, final float px, final float py, final float pz)
                {
                    return ((Math.abs(px - cx) - sxz) <= 0) && ((Math.abs(py - cy) - sy) <= 0) && ((Math.abs(pz - cz) - sxz) <= 0);
                }

                @Override
                public boolean isNotOutside(final float cx, final float cy, final float cz, final float sx, final float sy, final float sz, final float px, final float py, final float pz)
                {
                    return ((Math.abs(px - cx) - sx) <= 0) && ((Math.abs(py - cy) - sy) <= 0) && ((Math.abs(pz - cz) - sz) <= 0);
                }

                @Override
                public boolean isNotOutside(final int cx, final int cy, final int cz, final int size, final int px, final int py, final int pz)
                {
                    return ((Math.abs(px - cx) - size) <= 0) && ((Math.abs(py - cy) - size) <= 0) && ((Math.abs(pz - cz) - size) <= 0);
                }

                @Override
                public boolean isNotOutside(final int cx, final int cy, final int cz, final int sxz, final int sy, final int px, final int py, final int pz)
                {
                    return ((Math.abs(px - cx) - sxz) <= 0) && ((Math.abs(py - cy) - sy) <= 0) && ((Math.abs(pz - cz) - sxz) <= 0);
                }

                @Override
                public boolean isNotOutside(final int cx, final int cy, final int cz, final int sx, final int sy, final int sz, final int px, final int py, final int pz)
                {
                    return ((Math.abs(px - cx) - sx) <= 0) && ((Math.abs(py - cy) - sy) <= 0) && ((Math.abs(pz - cz) - sz) <= 0);
                }

                @Override
                public boolean isExactlyIn(final double cx, final double cy, final double cz, final double size, final double px, final double py, final double pz)
                {
                    return ((Math.abs(px - cx) - size) < 0) && ((Math.abs(py - cy) - size) < 0) && ((Math.abs(pz - cz) - size) < 0);
                }

                @Override
                public boolean isExactlyIn(final double cx, final double cy, final double cz, final double sxz, final double sy, final double px, final double py, final double pz)
                {
                    return ((Math.abs(px - cx) - sxz) < 0) && ((Math.abs(py - cy) - sy) < 0) && ((Math.abs(pz - cz) - sxz) < 0);
                }

                @Override
                public boolean isExactlyIn(final double cx, final double cy, final double cz, final double sx, final double sy, final double sz, final double px, final double py, final double pz)
                {
                    return ((Math.abs(px - cx) - sx) < 0) && ((Math.abs(py - cy) - sy) < 0) && ((Math.abs(pz - cz) - sz) < 0);
                }

                @Override
                public boolean isExactlyIn(final long cx, final long cy, final long cz, final long size, final long px, final long py, final long pz)
                {
                    return ((Math.abs(px - cx) - size) < 0) && ((Math.abs(py - cy) - size) < 0) && ((Math.abs(pz - cz) - size) < 0);
                }

                @Override
                public boolean isExactlyIn(final long cx, final long cy, final long cz, final long sxz, final long sy, final long px, final long py, final long pz)
                {
                    return ((Math.abs(px - cx) - sxz) < 0) && ((Math.abs(py - cy) - sy) < 0) && ((Math.abs(pz - cz) - sxz) < 0);
                }

                @Override
                public boolean isExactlyIn(final long cx, final long cy, final long cz, final long sx, final long sy, final long sz, final long px, final long py, final long pz)
                {
                    return ((Math.abs(px - cx) - sx) < 0) && ((Math.abs(py - cy) - sy) < 0) && ((Math.abs(pz - cz) - sz) < 0);
                }

                @Override
                public boolean isExactlyIn(final float cx, final float cy, final float cz, final float size, final float px, final float py, final float pz)
                {
                    return ((Math.abs(px - cx) - size) < 0) && ((Math.abs(py - cy) - size) < 0) && ((Math.abs(pz - cz) - size) < 0);
                }

                @Override
                public boolean isExactlyIn(final float cx, final float cy, final float cz, final float sxz, final float sy, final float px, final float py, final float pz)
                {
                    return ((Math.abs(px - cx) - sxz) < 0) && ((Math.abs(py - cy) - sy) < 0) && ((Math.abs(pz - cz) - sxz) < 0);
                }

                @Override
                public boolean isExactlyIn(final float cx, final float cy, final float cz, final float sx, final float sy, final float sz, final float px, final float py, final float pz)
                {
                    return ((Math.abs(px - cx) - sx) < 0) && ((Math.abs(py - cy) - sy) < 0) && ((Math.abs(pz - cz) - sz) < 0);
                }

                @Override
                public boolean isExactlyIn(final int cx, final int cy, final int cz, final int size, final int px, final int py, final int pz)
                {
                    return ((Math.abs(px - cx) - size) < 0) && ((Math.abs(py - cy) - size) < 0) && ((Math.abs(pz - cz) - size) < 0);
                }

                @Override
                public boolean isExactlyIn(final int cx, final int cy, final int cz, final int sxz, final int sy, final int px, final int py, final int pz)
                {
                    return ((Math.abs(px - cx) - sxz) < 0) && ((Math.abs(py - cy) - sy) < 0) && ((Math.abs(pz - cz) - sxz) < 0);
                }

                @Override
                public boolean isExactlyIn(final int cx, final int cy, final int cz, final int sx, final int sy, final int sz, final int px, final int py, final int pz)
                {
                    return ((Math.abs(px - cx) - sx) < 0) && ((Math.abs(py - cy) - sy) < 0) && ((Math.abs(pz - cz) - sz) < 0);
                }

                @Override
                public boolean isExactlyOn(final double cx, final double cy, final double cz, final double size, final double px, final double py, final double pz)
                {
                    return ((Math.abs(px - cx) - size) == 0) && ((Math.abs(py - cy) - size) == 0) && ((Math.abs(pz - cz) - size) == 0);
                }

                @Override
                public boolean isExactlyOn(final double cx, final double cy, final double cz, final double sxz, final double sy, final double px, final double py, final double pz)
                {
                    return ((Math.abs(px - cx) - sxz) == 0) && ((Math.abs(py - cy) - sy) == 0) && ((Math.abs(pz - cz) - sxz) == 0);
                }

                @Override
                public boolean isExactlyOn(final double cx, final double cy, final double cz, final double sx, final double sy, final double sz, final double px, final double py, final double pz)
                {
                    return ((Math.abs(px - cx) - sx) == 0) && ((Math.abs(py - cy) - sy) == 0) && ((Math.abs(pz - cz) - sz) == 0);
                }

                @Override
                public boolean isExactlyOn(final long cx, final long cy, final long cz, final long size, final long px, final long py, final long pz)
                {
                    return ((Math.abs(px - cx) - size) == 0) && ((Math.abs(py - cy) - size) == 0) && ((Math.abs(pz - cz) - size) == 0);
                }

                @Override
                public boolean isExactlyOn(final long cx, final long cy, final long cz, final long sxz, final long sy, final long px, final long py, final long pz)
                {
                    return ((Math.abs(px - cx) - sxz) == 0) && ((Math.abs(py - cy) - sy) == 0) && ((Math.abs(pz - cz) - sxz) == 0);
                }

                @Override
                public boolean isExactlyOn(final long cx, final long cy, final long cz, final long sx, final long sy, final long sz, final long px, final long py, final long pz)
                {
                    return ((Math.abs(px - cx) - sx) == 0) && ((Math.abs(py - cy) - sy) == 0) && ((Math.abs(pz - cz) - sz) == 0);
                }

                @Override
                public boolean isExactlyOn(final float cx, final float cy, final float cz, final float size, final float px, final float py, final float pz)
                {
                    return ((Math.abs(px - cx) - size) == 0) && ((Math.abs(py - cy) - size) == 0) && ((Math.abs(pz - cz) - size) == 0);
                }

                @Override
                public boolean isExactlyOn(final float cx, final float cy, final float cz, final float sxz, final float sy, final float px, final float py, final float pz)
                {
                    return ((Math.abs(px - cx) - sxz) == 0) && ((Math.abs(py - cy) - sy) == 0) && ((Math.abs(pz - cz) - sxz) == 0);
                }

                @Override
                public boolean isExactlyOn(final float cx, final float cy, final float cz, final float sx, final float sy, final float sz, final float px, final float py, final float pz)
                {
                    return ((Math.abs(px - cx) - sx) == 0) && ((Math.abs(py - cy) - sy) == 0) && ((Math.abs(pz - cz) - sz) == 0);
                }

                @Override
                public boolean isExactlyOn(final int cx, final int cy, final int cz, final int size, final int px, final int py, final int pz)
                {
                    return ((Math.abs(px - cx) - size) == 0) && ((Math.abs(py - cy) - size) == 0) && ((Math.abs(pz - cz) - size) == 0);
                }

                @Override
                public boolean isExactlyOn(final int cx, final int cy, final int cz, final int sxz, final int sy, final int px, final int py, final int pz)
                {
                    return ((Math.abs(px - cx) - sxz) == 0) && ((Math.abs(py - cy) - sy) == 0) && ((Math.abs(pz - cz) - sxz) == 0);
                }

                @Override
                public boolean isExactlyOn(final int cx, final int cy, final int cz, final int sx, final int sy, final int sz, final int px, final int py, final int pz)
                {
                    return ((Math.abs(px - cx) - sx) == 0) && ((Math.abs(py - cy) - sy) == 0) && ((Math.abs(pz - cz) - sz) == 0);
                }
            },
    CYLINDER
            {
                @Override
                public Result isIn(final double cx, final double cy, final double cz, final double size, final double px, final double py, final double pz)
                {
                    if ((Math.abs(py - cy) - size) > 0)
                    {
                        return Result.OUT;
                    }
                    return getResult((square(px - cx) + square(pz - cz)) - square(size));
                }

                @Override
                public Result isIn(final double cx, final double cy, final double cz, final double sxz, final double sy, final double px, final double py, final double pz)
                {
                    if ((Math.abs(py - cy) - sy) > 0)
                    {
                        return Result.OUT;
                    }
                    return getResult((square(px - cx) + square(pz - cz)) - square(sxz));
                }

                @Override
                public Result isIn(final double cx, final double cy, final double cz, final double sx, final double sy, final double sz, final double px, final double py, final double pz)
                {
                    if ((Math.abs(py - cy) - sy) > 0)
                    {
                        return Result.OUT;
                    }
                    return getResult(((square(px - cx) / square(sx)) + (square(pz - cz) / square(sz))) - 1);
                }

                @Override
                public Result isIn(final long cx, final long cy, final long cz, final long size, final long px, final long py, final long pz)
                {
                    if ((Math.abs(py - cy) - size) > 0)
                    {
                        return Result.OUT;
                    }
                    return getResult((square(px - cx) + square(pz - cz)) - square(size));
                }

                @Override
                public Result isIn(final long cx, final long cy, final long cz, final long sxz, final long sy, final long px, final long py, final long pz)
                {
                    if ((Math.abs(py - cy) - sy) > 0)
                    {
                        return Result.OUT;
                    }
                    return getResult((square(px - cx) + square(pz - cz)) - square(sxz));
                }

                @Override
                public Result isIn(final long cx, final long cy, final long cz, final long sx, final long sy, final long sz, final long px, final long py, final long pz)
                {
                    if ((Math.abs(py - cy) - sy) > 0)
                    {
                        return Result.OUT;
                    }
                    return getResult(((square(px - cx) / square(sx)) + (square(pz - cz) / square(sz))) - 1);
                }

                @Override
                public Result isIn(final float cx, final float cy, final float cz, final float size, final float px, final float py, final float pz)
                {
                    if ((Math.abs(py - cy) - size) > 0)
                    {
                        return Result.OUT;
                    }
                    return getResult((square(px - cx) + square(pz - cz)) - square(size));
                }

                @Override
                public Result isIn(final float cx, final float cy, final float cz, final float sxz, final float sy, final float px, final float py, final float pz)
                {
                    if ((Math.abs(py - cy) - sy) > 0)
                    {
                        return Result.OUT;
                    }
                    return getResult((square(px - cx) + square(pz - cz)) - square(sxz));
                }

                @Override
                public Result isIn(final float cx, final float cy, final float cz, final float sx, final float sy, final float sz, final float px, final float py, final float pz)
                {
                    if ((Math.abs(py - cy) - sy) > 0)
                    {
                        return Result.OUT;
                    }
                    return getResult(((square(px - cx) / square(sx)) + (square(pz - cz) / square(sz))) - 1);
                }

                @Override
                public Result isIn(final int cx, final int cy, final int cz, final int size, final int px, final int py, final int pz)
                {
                    if ((Math.abs(py - cy) - size) > 0)
                    {
                        return Result.OUT;
                    }
                    return getResult((square(px - cx) + square(pz - cz)) - square(size));
                }

                @Override
                public Result isIn(final int cx, final int cy, final int cz, final int sxz, final int sy, final int px, final int py, final int pz)
                {
                    if ((Math.abs(py - cy) - sy) > 0)
                    {
                        return Result.OUT;
                    }
                    return getResult((square(px - cx) + square(pz - cz)) - square(sxz));
                }

                @Override
                public Result isIn(final int cx, final int cy, final int cz, final int sx, final int sy, final int sz, final int px, final int py, final int pz)
                {
                    if ((Math.abs(py - cy) - sy) > 0)
                    {
                        return Result.OUT;
                    }
                    return getResult(((square(px - cx) / square(sx)) + (square(pz - cz) / square(sz))) - 1);
                }

                @Override
                public boolean isNotOutside(final double cx, final double cy, final double cz, final double size, final double px, final double py, final double pz)
                {
                    return ((Math.abs(py - cy) - size) <= 0) && (((square(px - cx) + square(pz - cz)) - square(size)) <= 0);
                }

                @Override
                public boolean isNotOutside(final double cx, final double cy, final double cz, final double sxz, final double sy, final double px, final double py, final double pz)
                {
                    return ((Math.abs(py - cy) - sy) <= 0) && (((square(px - cx) + square(pz - cz)) - square(sxz)) <= 0);
                }

                @Override
                public boolean isNotOutside(final double cx, final double cy, final double cz, final double sx, final double sy, final double sz, final double px, final double py, final double pz)
                {
                    return ((Math.abs(py - cy) - sy) <= 0) && ((((square(px - cx) / square(sx)) + (square(pz - cz) / square(sz))) - 1) <= 0);
                }

                @Override
                public boolean isNotOutside(final long cx, final long cy, final long cz, final long size, final long px, final long py, final long pz)
                {
                    return ((Math.abs(py - cy) - size) <= 0) && (((square(px - cx) + square(pz - cz)) - square(size)) <= 0);
                }

                @Override
                public boolean isNotOutside(final long cx, final long cy, final long cz, final long sxz, final long sy, final long px, final long py, final long pz)
                {
                    return ((Math.abs(py - cy) - sy) <= 0) && (((square(px - cx) + square(pz - cz)) - square(sxz)) <= 0);
                }

                @Override
                public boolean isNotOutside(final long cx, final long cy, final long cz, final long sx, final long sy, final long sz, final long px, final long py, final long pz)
                {
                    return ((Math.abs(py - cy) - sy) <= 0) && ((((square(px - cx) / square(sx)) + (square(pz - cz) / square(sz))) - 1) <= 0);
                }

                @Override
                public boolean isNotOutside(final float cx, final float cy, final float cz, final float size, final float px, final float py, final float pz)
                {
                    return ((Math.abs(py - cy) - size) <= 0) && (((square(px - cx) + square(pz - cz)) - square(size)) <= 0);
                }

                @Override
                public boolean isNotOutside(final float cx, final float cy, final float cz, final float sxz, final float sy, final float px, final float py, final float pz)
                {
                    return ((Math.abs(py - cy) - sy) <= 0) && (((square(px - cx) + square(pz - cz)) - square(sxz)) <= 0);
                }

                @Override
                public boolean isNotOutside(final float cx, final float cy, final float cz, final float sx, final float sy, final float sz, final float px, final float py, final float pz)
                {
                    return ((Math.abs(py - cy) - sy) <= 0) && ((((square(px - cx) / square(sx)) + (square(pz - cz) / square(sz))) - 1) <= 0);
                }

                @Override
                public boolean isNotOutside(final int cx, final int cy, final int cz, final int size, final int px, final int py, final int pz)
                {
                    return ((Math.abs(py - cy) - size) <= 0) && (((square(px - cx) + square(pz - cz)) - square(size)) <= 0);
                }

                @Override
                public boolean isNotOutside(final int cx, final int cy, final int cz, final int sxz, final int sy, final int px, final int py, final int pz)
                {
                    return ((Math.abs(py - cy) - sy) <= 0) && (((square(px - cx) + square(pz - cz)) - square(sxz)) <= 0);
                }

                @Override
                public boolean isNotOutside(final int cx, final int cy, final int cz, final int sx, final int sy, final int sz, final int px, final int py, final int pz)
                {
                    return ((Math.abs(py - cy) - sy) <= 0) && ((((square(px - cx) / square(sx)) + (square(pz - cz) / square(sz))) - 1) <= 0);
                }

                @Override
                public boolean isExactlyIn(final double cx, final double cy, final double cz, final double size, final double px, final double py, final double pz)
                {
                    return ((Math.abs(py - cy) - size) < 0) && (((square(px - cx) + square(pz - cz)) - square(size)) < 0);
                }

                @Override
                public boolean isExactlyIn(final double cx, final double cy, final double cz, final double sxz, final double sy, final double px, final double py, final double pz)
                {
                    return ((Math.abs(py - cy) - sy) < 0) && (((square(px - cx) + square(pz - cz)) - square(sxz)) < 0);
                }

                @Override
                public boolean isExactlyIn(final double cx, final double cy, final double cz, final double sx, final double sy, final double sz, final double px, final double py, final double pz)
                {
                    return ((Math.abs(py - cy) - sy) < 0) && ((((square(px - cx) / square(sx)) + (square(pz - cz) / square(sz))) - 1) < 0);
                }

                @Override
                public boolean isExactlyIn(final long cx, final long cy, final long cz, final long size, final long px, final long py, final long pz)
                {
                    return ((Math.abs(py - cy) - size) < 0) && (((square(px - cx) + square(pz - cz)) - square(size)) < 0);
                }

                @Override
                public boolean isExactlyIn(final long cx, final long cy, final long cz, final long sxz, final long sy, final long px, final long py, final long pz)
                {
                    return ((Math.abs(py - cy) - sy) < 0) && (((square(px - cx) + square(pz - cz)) - square(sxz)) < 0);
                }

                @Override
                public boolean isExactlyIn(final long cx, final long cy, final long cz, final long sx, final long sy, final long sz, final long px, final long py, final long pz)
                {
                    return ((Math.abs(py - cy) - sy) <= 0) && ((((square(px - cx) / square(sx)) + (square(pz - cz) / square(sz))) - 1) <= 0);
                }

                @Override
                public boolean isExactlyIn(final float cx, final float cy, final float cz, final float size, final float px, final float py, final float pz)
                {
                    return ((Math.abs(py - cy) - size) < 0) && (((square(px - cx) + square(pz - cz)) - square(size)) < 0);
                }

                @Override
                public boolean isExactlyIn(final float cx, final float cy, final float cz, final float sxz, final float sy, final float px, final float py, final float pz)
                {
                    return ((Math.abs(py - cy) - sy) < 0) && (((square(px - cx) + square(pz - cz)) - square(sxz)) < 0);
                }

                @Override
                public boolean isExactlyIn(final float cx, final float cy, final float cz, final float sx, final float sy, final float sz, final float px, final float py, final float pz)
                {
                    return ((Math.abs(py - cy) - sy) <= 0) && ((((square(px - cx) / square(sx)) + (square(pz - cz) / square(sz))) - 1) <= 0);
                }

                @Override
                public boolean isExactlyIn(final int cx, final int cy, final int cz, final int size, final int px, final int py, final int pz)
                {
                    return ((Math.abs(py - cy) - size) < 0) && (((square(px - cx) + square(pz - cz)) - square(size)) < 0);
                }

                @Override
                public boolean isExactlyIn(final int cx, final int cy, final int cz, final int sxz, final int sy, final int px, final int py, final int pz)
                {
                    return ((Math.abs(py - cy) - sy) < 0) && (((square(px - cx) + square(pz - cz)) - square(sxz)) < 0);
                }

                @Override
                public boolean isExactlyIn(final int cx, final int cy, final int cz, final int sx, final int sy, final int sz, final int px, final int py, final int pz)
                {
                    return ((Math.abs(py - cy) - sy) <= 0) && ((((square(px - cx) / square(sx)) + (square(pz - cz) / square(sz))) - 1) <= 0);
                }

                @Override
                public boolean isExactlyOn(final double cx, final double cy, final double cz, final double size, final double px, final double py, final double pz)
                {
                    return ((Math.abs(py - cy) - size) == 0) && (((square(px - cx) + square(pz - cz)) - square(size)) == 0);
                }

                @Override
                public boolean isExactlyOn(final double cx, final double cy, final double cz, final double sxz, final double sy, final double px, final double py, final double pz)
                {
                    return ((Math.abs(py - cy) - sy) == 0) && (((square(px - cx) + square(pz - cz)) - square(sxz)) == 0);
                }

                @Override
                public boolean isExactlyOn(final double cx, final double cy, final double cz, final double sx, final double sy, final double sz, final double px, final double py, final double pz)
                {
                    return ((Math.abs(py - cy) - sy) == 0) && ((((square(px - cx) / square(sx)) + (square(pz - cz) / square(sz))) - 1) == 0);
                }

                @Override
                public boolean isExactlyOn(final long cx, final long cy, final long cz, final long size, final long px, final long py, final long pz)
                {
                    return ((Math.abs(py - cy) - size) == 0) && (((square(px - cx) + square(pz - cz)) - square(size)) == 0);
                }

                @Override
                public boolean isExactlyOn(final long cx, final long cy, final long cz, final long sxz, final long sy, final long px, final long py, final long pz)
                {
                    return ((Math.abs(py - cy) - sy) == 0) && (((square(px - cx) + square(pz - cz)) - square(sxz)) == 0);
                }

                @Override
                public boolean isExactlyOn(final long cx, final long cy, final long cz, final long sx, final long sy, final long sz, final long px, final long py, final long pz)
                {
                    return ((Math.abs(py - cy) - sy) == 0) && ((((square(px - cx) / square(sx)) + (square(pz - cz) / square(sz))) - 1) == 0);
                }

                @Override
                public boolean isExactlyOn(final float cx, final float cy, final float cz, final float size, final float px, final float py, final float pz)
                {
                    return ((Math.abs(py - cy) - size) == 0) && (((square(px - cx) + square(pz - cz)) - square(size)) == 0);
                }

                @Override
                public boolean isExactlyOn(final float cx, final float cy, final float cz, final float sxz, final float sy, final float px, final float py, final float pz)
                {
                    return ((Math.abs(py - cy) - sy) == 0) && (((square(px - cx) + square(pz - cz)) - square(sxz)) == 0);
                }

                @Override
                public boolean isExactlyOn(final float cx, final float cy, final float cz, final float sx, final float sy, final float sz, final float px, final float py, final float pz)
                {
                    return ((Math.abs(py - cy) - sy) == 0) && ((((square(px - cx) / square(sx)) + (square(pz - cz) / square(sz))) - 1) == 0);
                }

                @Override
                public boolean isExactlyOn(final int cx, final int cy, final int cz, final int size, final int px, final int py, final int pz)
                {
                    return ((Math.abs(py - cy) - size) == 0) && (((square(px - cx) + square(pz - cz)) - square(size)) == 0);
                }

                @Override
                public boolean isExactlyOn(final int cx, final int cy, final int cz, final int sxz, final int sy, final int px, final int py, final int pz)
                {
                    return ((Math.abs(py - cy) - sy) == 0) && (((square(px - cx) + square(pz - cz)) - square(sxz)) == 0);
                }

                @Override
                public boolean isExactlyOn(final int cx, final int cy, final int cz, final int sx, final int sy, final int sz, final int px, final int py, final int pz)
                {
                    return ((Math.abs(py - cy) - sy) == 0) && ((((square(px - cx) / square(sx)) + (square(pz - cz) / square(sz))) - 1) == 0);
                }
            },
    ELLIPSOID
            {
                @Override
                public Result isIn(final double cx, final double cy, final double cz, final double size, final double px, final double py, final double pz)
                {
                    return getResult((square(px - cx) + square(py - cy) + square(pz - cz)) - square(size));
                }

                @Override
                public Result isIn(final double cx, final double cy, final double cz, final double sxz, final double sy, final double px, final double py, final double pz)
                {
                    return getResult((((square(px - cx) + square(pz - cz)) / square(sxz)) + (square(py - cy) / square(sy))) - 1);
                }

                @Override
                public Result isIn(final double cx, final double cy, final double cz, final double sx, final double sy, final double sz, final double px, final double py, final double pz)
                {
                    return getResult(((square(px - cx) / square(sx)) + (square(py - cy) / square(sy)) + (square(pz - cz) / square(sz))) - 1);
                }

                @Override
                public Result isIn(final long cx, final long cy, final long cz, final long size, final long px, final long py, final long pz)
                {
                    return getResult((square(px - cx) + square(py - cy) + square(pz - cz)) - square(size));
                }

                @Override
                public Result isIn(final long cx, final long cy, final long cz, final long sxz, final long sy, final long px, final long py, final long pz)
                {
                    return getResult((((square(px - cx) + square(pz - cz)) / square(sxz)) + (square(py - cy) / square(sy))) - 1);
                }

                @Override
                public Result isIn(final long cx, final long cy, final long cz, final long sx, final long sy, final long sz, final long px, final long py, final long pz)
                {
                    return getResult(((square(px - cx) / square(sx)) + (square(py - cy) / square(sy)) + (square(pz - cz) / square(sz))) - 1);
                }

                @Override
                public Result isIn(final float cx, final float cy, final float cz, final float size, final float px, final float py, final float pz)
                {
                    return getResult((square(px - cx) + square(py - cy) + square(pz - cz)) - square(size));
                }

                @Override
                public Result isIn(final float cx, final float cy, final float cz, final float sxz, final float sy, final float px, final float py, final float pz)
                {
                    return getResult((((square(px - cx) + square(pz - cz)) / square(sxz)) + (square(py - cy) / square(sy))) - 1);
                }

                @Override
                public Result isIn(final float cx, final float cy, final float cz, final float sx, final float sy, final float sz, final float px, final float py, final float pz)
                {
                    return getResult(((square(px - cx) / square(sx)) + (square(py - cy) / square(sy)) + (square(pz - cz) / square(sz))) - 1);
                }

                @Override
                public Result isIn(final int cx, final int cy, final int cz, final int size, final int px, final int py, final int pz)
                {
                    return getResult((square(px - cx) + square(py - cy) + square(pz - cz)) - square(size));
                }

                @Override
                public Result isIn(final int cx, final int cy, final int cz, final int sxz, final int sy, final int px, final int py, final int pz)
                {
                    return getResult((((square(px - cx) + square(pz - cz)) / square(sxz)) + (square(py - cy) / square(sy))) - 1);
                }

                @Override
                public Result isIn(final int cx, final int cy, final int cz, final int sx, final int sy, final int sz, final int px, final int py, final int pz)
                {
                    return getResult(((square(px - cx) / square(sx)) + (square(py - cy) / square(sy)) + (square(pz - cz) / square(sz))) - 1);
                }

                @Override
                public boolean isNotOutside(final double cx, final double cy, final double cz, final double size, final double px, final double py, final double pz)
                {
                    return ((square(px - cx) + square(py - cy) + square(pz - cz)) - square(size)) <= 0;
                }

                @Override
                public boolean isNotOutside(final double cx, final double cy, final double cz, final double sxz, final double sy, final double px, final double py, final double pz)
                {
                    return ((((square(px - cx) + square(pz - cz)) / square(sxz)) + (square(py - cy) / square(sy))) - 1) <= 0;
                }

                @Override
                public boolean isNotOutside(final double cx, final double cy, final double cz, final double sx, final double sy, final double sz, final double px, final double py, final double pz)
                {
                    return (((square(px - cx) / square(sx)) + (square(py - cy) / square(sy)) + (square(pz - cz) / square(sz))) - 1) <= 0;
                }

                @Override
                public boolean isNotOutside(final long cx, final long cy, final long cz, final long size, final long px, final long py, final long pz)
                {
                    return ((square(px - cx) + square(py - cy) + square(pz - cz)) - square(size)) <= 0;
                }

                @Override
                public boolean isNotOutside(final long cx, final long cy, final long cz, final long sxz, final long sy, final long px, final long py, final long pz)
                {
                    return ((((square(px - cx) + square(pz - cz)) / square(sxz)) + (square(py - cy) / square(sy))) - 1) <= 0;
                }

                @Override
                public boolean isNotOutside(final long cx, final long cy, final long cz, final long sx, final long sy, final long sz, final long px, final long py, final long pz)
                {
                    return (((square(px - cx) / square(sx)) + (square(py - cy) / square(sy)) + (square(pz - cz) / square(sz))) - 1) <= 0;
                }

                @Override
                public boolean isNotOutside(final float cx, final float cy, final float cz, final float size, final float px, final float py, final float pz)
                {
                    return ((square(px - cx) + square(py - cy) + square(pz - cz)) - square(size)) <= 0;
                }

                @Override
                public boolean isNotOutside(final float cx, final float cy, final float cz, final float sxz, final float sy, final float px, final float py, final float pz)
                {
                    return ((((square(px - cx) + square(pz - cz)) / square(sxz)) + (square(py - cy) / square(sy))) - 1) <= 0;
                }

                @Override
                public boolean isNotOutside(final float cx, final float cy, final float cz, final float sx, final float sy, final float sz, final float px, final float py, final float pz)
                {
                    return (((square(px - cx) / square(sx)) + (square(py - cy) / square(sy)) + (square(pz - cz) / square(sz))) - 1) <= 0;
                }

                @Override
                public boolean isNotOutside(final int cx, final int cy, final int cz, final int size, final int px, final int py, final int pz)
                {
                    return ((square(px - cx) + square(py - cy) + square(pz - cz)) - square(size)) <= 0;
                }

                @Override
                public boolean isNotOutside(final int cx, final int cy, final int cz, final int sxz, final int sy, final int px, final int py, final int pz)
                {
                    return ((((square(px - cx) + square(pz - cz)) / square(sxz)) + (square(py - cy) / square(sy))) - 1) <= 0;
                }

                @Override
                public boolean isNotOutside(final int cx, final int cy, final int cz, final int sx, final int sy, final int sz, final int px, final int py, final int pz)
                {
                    return (((square(px - cx) / square(sx)) + (square(py - cy) / square(sy)) + (square(pz - cz) / square(sz))) - 1) <= 0;
                }

                @Override
                public boolean isExactlyIn(final double cx, final double cy, final double cz, final double size, final double px, final double py, final double pz)
                {
                    return ((square(px - cx) + square(py - cy) + square(pz - cz)) - square(size)) < 0;
                }

                @Override
                public boolean isExactlyIn(final double cx, final double cy, final double cz, final double sxz, final double sy, final double px, final double py, final double pz)
                {
                    return ((((square(px - cx) + square(pz - cz)) / square(sxz)) + (square(py - cy) / square(sy))) - 1) < 0;
                }

                @Override
                public boolean isExactlyIn(final double cx, final double cy, final double cz, final double sx, final double sy, final double sz, final double px, final double py, final double pz)
                {
                    return (((square(px - cx) / square(sx)) + (square(py - cy) / square(sy)) + (square(pz - cz) / square(sz))) - 1) < 0;
                }

                @Override
                public boolean isExactlyIn(final long cx, final long cy, final long cz, final long size, final long px, final long py, final long pz)
                {
                    return ((square(px - cx) + square(py - cy) + square(pz - cz)) - square(size)) < 0;
                }

                @Override
                public boolean isExactlyIn(final long cx, final long cy, final long cz, final long sxz, final long sy, final long px, final long py, final long pz)
                {
                    return ((((square(px - cx) + square(pz - cz)) / square(sxz)) + (square(py - cy) / square(sy))) - 1) < 0;
                }

                @Override
                public boolean isExactlyIn(final long cx, final long cy, final long cz, final long sx, final long sy, final long sz, final long px, final long py, final long pz)
                {
                    return (((square(px - cx) / square(sx)) + (square(py - cy) / square(sy)) + (square(pz - cz) / square(sz))) - 1) < 0;
                }

                @Override
                public boolean isExactlyIn(final float cx, final float cy, final float cz, final float size, final float px, final float py, final float pz)
                {
                    return ((square(px - cx) + square(py - cy) + square(pz - cz)) - square(size)) < 0;
                }

                @Override
                public boolean isExactlyIn(final float cx, final float cy, final float cz, final float sxz, final float sy, final float px, final float py, final float pz)
                {
                    return ((((square(px - cx) + square(pz - cz)) / square(sxz)) + (square(py - cy) / square(sy))) - 1) < 0;
                }

                @Override
                public boolean isExactlyIn(final float cx, final float cy, final float cz, final float sx, final float sy, final float sz, final float px, final float py, final float pz)
                {
                    return (((square(px - cx) / square(sx)) + (square(py - cy) / square(sy)) + (square(pz - cz) / square(sz))) - 1) < 0;
                }

                @Override
                public boolean isExactlyIn(final int cx, final int cy, final int cz, final int size, final int px, final int py, final int pz)
                {
                    return ((square(px - cx) + square(py - cy) + square(pz - cz)) - square(size)) < 0;
                }

                @Override
                public boolean isExactlyIn(final int cx, final int cy, final int cz, final int sxz, final int sy, final int px, final int py, final int pz)
                {
                    return ((((square(px - cx) + square(pz - cz)) / square(sxz)) + (square(py - cy) / square(sy))) - 1) < 0;
                }

                @Override
                public boolean isExactlyIn(final int cx, final int cy, final int cz, final int sx, final int sy, final int sz, final int px, final int py, final int pz)
                {
                    return (((square(px - cx) / square(sx)) + (square(py - cy) / square(sy)) + (square(pz - cz) / square(sz))) - 1) < 0;
                }

                @Override
                public boolean isExactlyOn(final double cx, final double cy, final double cz, final double size, final double px, final double py, final double pz)
                {
                    return ((square(px - cx) + square(py - cy) + square(pz - cz)) - square(size)) == 0;
                }

                @Override
                public boolean isExactlyOn(final double cx, final double cy, final double cz, final double sxz, final double sy, final double px, final double py, final double pz)
                {
                    return ((((square(px - cx) + square(pz - cz)) / square(sxz)) + (square(py - cy) / square(sy))) - 1) == 0;
                }

                @Override
                public boolean isExactlyOn(final double cx, final double cy, final double cz, final double sx, final double sy, final double sz, final double px, final double py, final double pz)
                {
                    return (((square(px - cx) / square(sx)) + (square(py - cy) / square(sy)) + (square(pz - cz) / square(sz))) - 1) == 0;
                }

                @Override
                public boolean isExactlyOn(final long cx, final long cy, final long cz, final long size, final long px, final long py, final long pz)
                {
                    return ((square(px - cx) + square(py - cy) + square(pz - cz)) - square(size)) == 0;
                }

                @Override
                public boolean isExactlyOn(final long cx, final long cy, final long cz, final long sxz, final long sy, final long px, final long py, final long pz)
                {
                    return ((((square(px - cx) + square(pz - cz)) / square(sxz)) + (square(py - cy) / square(sy))) - 1) == 0;
                }

                @Override
                public boolean isExactlyOn(final long cx, final long cy, final long cz, final long sx, final long sy, final long sz, final long px, final long py, final long pz)
                {
                    return (((square(px - cx) / square(sx)) + (square(py - cy) / square(sy)) + (square(pz - cz) / square(sz))) - 1) == 0;
                }

                @Override
                public boolean isExactlyOn(final float cx, final float cy, final float cz, final float size, final float px, final float py, final float pz)
                {
                    return ((square(px - cx) + square(py - cy) + square(pz - cz)) - square(size)) == 0;
                }

                @Override
                public boolean isExactlyOn(final float cx, final float cy, final float cz, final float sxz, final float sy, final float px, final float py, final float pz)
                {
                    return ((((square(px - cx) + square(pz - cz)) / square(sxz)) + (square(py - cy) / square(sy))) - 1) == 0;
                }

                @Override
                public boolean isExactlyOn(final float cx, final float cy, final float cz, final float sx, final float sy, final float sz, final float px, final float py, final float pz)
                {
                    return (((square(px - cx) / square(sx)) + (square(py - cy) / square(sy)) + (square(pz - cz) / square(sz))) - 1) == 0;
                }

                @Override
                public boolean isExactlyOn(final int cx, final int cy, final int cz, final int size, final int px, final int py, final int pz)
                {
                    return ((square(px - cx) + square(py - cy) + square(pz - cz)) - square(size)) == 0;
                }

                @Override
                public boolean isExactlyOn(final int cx, final int cy, final int cz, final int sxz, final int sy, final int px, final int py, final int pz)
                {
                    return ((((square(px - cx) + square(pz - cz)) / square(sxz)) + (square(py - cy) / square(sy))) - 1) == 0;
                }

                @Override
                public boolean isExactlyOn(final int cx, final int cy, final int cz, final int sx, final int sy, final int sz, final int px, final int py, final int pz)
                {
                    return (((square(px - cx) / square(sx)) + (square(py - cy) / square(sy)) + (square(pz - cz) / square(sz))) - 1) == 0;
                }
            };

    /**
     * Returns enum element indicating that element is inside, outside or on border of area.
     *
     * @param center center point of area.
     * @param size   size of area in all 3 axis.
     * @param point  point to be checked if it is inside of given area.
     *
     * @return enum element indicating that element is inside, outside or on border of area.
     */
    public Result isIn(final Vector3d center, final Vector3d size, final Vector3d point)
    {
        return this.isIn(center.x, center.y, center.z, size.x, size.y, size.z, point.z, point.y, point.z);
    }

    /**
     * Returns enum element indicating that element is inside, outside or on border of area.
     *
     * @param center center point of area.
     * @param sx     size of area in x axis.
     * @param sy     size of area in y axis.
     * @param sz     size of area in z axis.
     * @param point  point to be checked if it is inside of given area.
     *
     * @return enum element indicating that element is inside, outside or on border of area.
     */
    public Result isIn(final Vector3d center, final double sx, final double sy, final double sz, final Vector3d point)
    {
        return this.isIn(center.x, center.y, center.z, sx, sy, sz, point.x, point.y, point.z);
    }

    /**
     * Returns enum element indicating that element is inside, outside or on border of area.
     *
     * @param center center point of area.
     * @param size   size of area in all 3 axis.
     * @param point  point to be checked if it is inside of given area.
     *
     * @return enum element indicating that element is inside, outside or on border of area.
     */
    public Result isIn(final Vector3d center, final Vector2d size, final Vector3d point)
    {
        return this.isIn(center.x, center.y, center.z, size.x, size.y, point.x, point.y, point.z);
    }

    /**
     * Returns enum element indicating that element is inside, outside or on border of area.
     *
     * @param center center point of area.
     * @param sxz    size of area in xz axis.
     * @param sy     size of area in y axis.
     * @param point  point to be checked if it is inside of given area.
     *
     * @return enum element indicating that element is inside, outside or on border of area.
     */
    public Result isIn(final Vector3d center, final double sxz, final double sy, final Vector3d point)
    {
        return this.isIn(center.x, center.y, center.z, sxz, sy, point.x, point.y, point.z);
    }

    /**
     * Returns enum element indicating that element is inside, outside or on border of area.
     *
     * @param center center point of area.
     * @param size   size of area in all 3 axis.
     * @param point  point to be checked if it is inside of given area.
     *
     * @return enum element indicating that element is inside, outside or on border of area.
     */
    public Result isIn(final Vector3d center, final double size, final Vector3d point)
    {
        return this.isIn(center.x, center.y, center.z, size, point.x, point.y, point.z);
    }

    /**
     * Returns enum element indicating that element is inside, outside or on border of area.
     *
     * @param cx    x center coordinates of area.
     * @param cy    y center coordinates of area.
     * @param cz    z center coordinates of area.
     * @param size  size of area in all 3 axis.
     * @param point point to be checked if it is inside of given area.
     *
     * @return enum element indicating that element is inside, outside or on border of area.
     */
    public Result isIn(final double cx, final double cy, final double cz, final Vector3d size, final Vector3d point)
    {
        return this.isIn(cx, cy, cz, size.x, size.y, size.z, point.x, point.y, point.z);
    }

    /**
     * Returns enum element indicating that element is inside, outside or on border of area.
     *
     * @param cx    x center coordinates of area.
     * @param cy    y center coordinates of area.
     * @param cz    z center coordinates of area.
     * @param size  size of area in all 3 axis.
     * @param point point to be checked if it is inside of given area.
     *
     * @return enum element indicating that element is inside, outside or on border of area.
     */
    public Result isIn(final double cx, final double cy, final double cz, final Vector2d size, final Vector3d point)
    {
        return this.isIn(cx, cy, cz, size.x, size.y, point.x, point.y, point.z);
    }

    /**
     * Returns enum element indicating that element is inside, outside or on border of area.
     *
     * @param cx    x center coordinates of area.
     * @param cy    y center coordinates of area.
     * @param cz    z center coordinates of area.
     * @param size  size of area in all 3 axis.
     * @param point point to be checked if it is inside of given area.
     *
     * @return enum element indicating that element is inside, outside or on border of area.
     */
    public Result isIn(final double cx, final double cy, final double cz, final double size, final Vector3d point)
    {
        return this.isIn(cx, cy, cz, size, point.x, point.y, point.z);
    }

    /**
     * Returns enum element indicating that element is inside, outside or on border of area.
     *
     * @param center center point of area.
     * @param size   size of area in all 3 axis.
     * @param px     x coordinates of point to check.
     * @param py     y coordinates of point to check.
     * @param pz     z coordinates of point to check.
     *
     * @return enum element indicating that element is inside, outside or on border of area.
     */
    public Result isIn(final Vector3d center, final Vector3d size, final double px, final double py, final double pz)
    {
        return this.isIn(center.x, center.y, center.z, size.x, size.y, size.z, px, py, pz);
    }

    /**
     * Returns enum element indicating that element is inside, outside or on border of area.
     *
     * @param center center point of area.
     * @param sx     size of area in x axis.
     * @param sy     size of area in y axis.
     * @param sz     size of area in z axis.
     * @param px     x coordinates of point to check.
     * @param py     y coordinates of point to check.
     * @param pz     z coordinates of point to check.
     *
     * @return enum element indicating that element is inside, outside or on border of area.
     */
    public Result isIn(final Vector3d center, final double sx, final double sy, final double sz, final double px, final double py, final double pz)
    {
        return this.isIn(center.x, center.y, center.z, sx, sy, sz, px, py, pz);
    }

    /**
     * Returns enum element indicating that element is inside, outside or on border of area.
     *
     * @param center center point of area.
     * @param size   size of area in all 3 axis.
     * @param px     x coordinates of point to check.
     * @param py     y coordinates of point to check.
     * @param pz     z coordinates of point to check.
     *
     * @return enum element indicating that element is inside, outside or on border of area.
     */
    public Result isIn(final Vector3d center, final Vector2d size, final double px, final double py, final double pz)
    {
        return this.isIn(center.x, center.y, center.z, size.x, size.y, px, py, pz);
    }

    /**
     * Returns enum element indicating that element is inside, outside or on border of area.
     *
     * @param center center point of area.
     * @param sxz    size of area in xz axis.
     * @param sy     size of area in y axis.
     * @param px     x coordinates of point to check.
     * @param py     y coordinates of point to check.
     * @param pz     z coordinates of point to check.
     *
     * @return enum element indicating that element is inside, outside or on border of area.
     */
    public Result isIn(final Vector3d center, final double sxz, final double sy, final double px, final double py, final double pz)
    {
        return this.isIn(center.x, center.y, center.z, sxz, sy, px, py, pz);
    }

    /**
     * Returns enum element indicating that element is inside, outside or on border of area.
     *
     * @param center center point of area.
     * @param size   size of area in all 3 axis.
     * @param px     x coordinates of point to check.
     * @param py     y coordinates of point to check.
     * @param pz     z coordinates of point to check.
     *
     * @return enum element indicating that element is inside, outside or on border of area.
     */
    public Result isIn(final Vector3d center, final double size, final double px, final double py, final double pz)
    {
        return this.isIn(center.x, center.y, center.z, size, px, py, pz);
    }

    /**
     * Returns enum element indicating that element is inside, outside or on border of area.
     *
     * @param cx   x center coordinates of area.
     * @param cy   y center coordinates of area.
     * @param cz   z center coordinates of area.
     * @param size size of area in all 3 axis.
     * @param px   x coordinates of point to check.
     * @param py   y coordinates of point to check.
     * @param pz   z coordinates of point to check.
     *
     * @return enum element indicating that element is inside, outside or on border of area.
     */
    public Result isIn(final double cx, final double cy, final double cz, final Vector3d size, final double px, final double py, final double pz)
    {
        return this.isIn(cx, cy, cz, size.x, size.y, size.z, px, py, pz);
    }

    /**
     * Returns enum element indicating that element is inside, outside or on border of area.
     *
     * @param cx   x center coordinates of area.
     * @param cy   y center coordinates of area.
     * @param cz   z center coordinates of area.
     * @param size size of area in all 3 axis.
     * @param px   x coordinates of point to check.
     * @param py   y coordinates of point to check.
     * @param pz   z coordinates of point to check.
     *
     * @return enum element indicating that element is inside, outside or on border of area.
     */
    public Result isIn(final double cx, final double cy, final double cz, final Vector2d size, final double px, final double py, final double pz)
    {
        return this.isIn(cx, cy, cz, size.x, size.y, px, py, pz);
    }

    /**
     * Returns enum element indicating that element is inside, outside or on border of area.
     *
     * @param cx   x center coordinates of area.
     * @param cy   y center coordinates of area.
     * @param cz   z center coordinates of area.
     * @param size size of area in all 3 axis.
     * @param px   x coordinates of point to check.
     * @param py   y coordinates of point to check.
     * @param pz   z coordinates of point to check.
     *
     * @return enum element indicating that element is inside, outside or on border of area.
     */
    public abstract Result isIn(double cx, double cy, double cz, double size, double px, double py, double pz);

    /**
     * Returns enum element indicating that element is inside, outside or on border of area.
     *
     * @param cx  x center coordinates of area.
     * @param cy  y center coordinates of area.
     * @param cz  z center coordinates of area.
     * @param sxz size of area in xz axis.
     * @param sy  size of area in y axis.
     * @param px  x coordinates of point to check.
     * @param py  y coordinates of point to check.
     * @param pz  z coordinates of point to check.
     *
     * @return enum element indicating that element is inside, outside or on border of area.
     */
    public abstract Result isIn(double cx, double cy, double cz, double sxz, double sy, double px, double py, double pz);

    /**
     * Returns enum element indicating that element is inside, outside or on border of area.
     *
     * @param cx x center coordinates of area.
     * @param cy y center coordinates of area.
     * @param cz z center coordinates of area.
     * @param sx size of area in x axis.
     * @param sy size of area in y axis.
     * @param sz size of area in z axis.
     * @param px x coordinates of point to check.
     * @param py y coordinates of point to check.
     * @param pz z coordinates of point to check.
     *
     * @return enum element indicating that element is inside, outside or on border of area.
     */
    public abstract Result isIn(double cx, double cy, double cz, double sx, double sy, double sz, double px, double py, double pz);

    /**
     * Returns enum element indicating that element is inside, outside or on border of area.
     *
     * @param cx   x center coordinates of area.
     * @param cy   y center coordinates of area.
     * @param cz   z center coordinates of area.
     * @param size size of area in all 3 axis.
     * @param px   x coordinates of point to check.
     * @param py   y coordinates of point to check.
     * @param pz   z coordinates of point to check.
     *
     * @return enum element indicating that element is inside, outside or on border of area.
     */
    public abstract Result isIn(long cx, long cy, long cz, long size, long px, long py, long pz);

    /**
     * Returns enum element indicating that element is inside, outside or on border of area.
     *
     * @param cx  x center coordinates of area.
     * @param cy  y center coordinates of area.
     * @param cz  z center coordinates of area.
     * @param sxz size of area in xz axis.
     * @param sy  size of area in y axis.
     * @param px  x coordinates of point to check.
     * @param py  y coordinates of point to check.
     * @param pz  z coordinates of point to check.
     *
     * @return enum element indicating that element is inside, outside or on border of area.
     */
    public abstract Result isIn(long cx, long cy, long cz, long sxz, long sy, long px, long py, long pz);

    /**
     * Returns enum element indicating that element is inside, outside or on border of area.
     *
     * @param cx x center coordinates of area.
     * @param cy y center coordinates of area.
     * @param cz z center coordinates of area.
     * @param sx size of area in x axis.
     * @param sy size of area in y axis.
     * @param sz size of area in z axis.
     * @param px x coordinates of point to check.
     * @param py y coordinates of point to check.
     * @param pz z coordinates of point to check.
     *
     * @return enum element indicating that element is inside, outside or on border of area.
     */
    public abstract Result isIn(long cx, long cy, long cz, long sx, long sy, long sz, long px, long py, long pz);

    /**
     * Returns enum element indicating that element is inside, outside or on border of area.
     *
     * @param center center point of area.
     * @param size   size of area in all 3 axis.
     * @param point  point to be checked if it is inside of given area.
     *
     * @return enum element indicating that element is inside, outside or on border of area.
     */
    public Result isIn(final Vector3f center, final Vector3f size, final Vector3f point)
    {
        return this.isIn(center.x, center.y, center.z, size.x, size.y, size.z, point.z, point.y, point.z);
    }

    /**
     * Returns enum element indicating that element is inside, outside or on border of area.
     *
     * @param center center point of area.
     * @param sx     size of area in x axis.
     * @param sy     size of area in y axis.
     * @param sz     size of area in z axis.
     * @param point  point to be checked if it is inside of given area.
     *
     * @return enum element indicating that element is inside, outside or on border of area.
     */
    public Result isIn(final Vector3f center, final float sx, final float sy, final float sz, final Vector3f point)
    {
        return this.isIn(center.x, center.y, center.z, sx, sy, sz, point.x, point.y, point.z);
    }

    /**
     * Returns enum element indicating that element is inside, outside or on border of area.
     *
     * @param center center point of area.
     * @param size   size of area in all 3 axis.
     * @param point  point to be checked if it is inside of given area.
     *
     * @return enum element indicating that element is inside, outside or on border of area.
     */
    public Result isIn(final Vector3f center, final Vector2f size, final Vector3f point)
    {
        return this.isIn(center.x, center.y, center.z, size.x, size.y, point.x, point.y, point.z);
    }

    /**
     * Returns enum element indicating that element is inside, outside or on border of area.
     *
     * @param center center point of area.
     * @param sxz    size of area in xz axis.
     * @param sy     size of area in y axis.
     * @param point  point to be checked if it is inside of given area.
     *
     * @return enum element indicating that element is inside, outside or on border of area.
     */
    public Result isIn(final Vector3f center, final float sxz, final float sy, final Vector3f point)
    {
        return this.isIn(center.x, center.y, center.z, sxz, sy, point.x, point.y, point.z);
    }

    /**
     * Returns enum element indicating that element is inside, outside or on border of area.
     *
     * @param center center point of area.
     * @param size   size of area in all 3 axis.
     * @param point  point to be checked if it is inside of given area.
     *
     * @return enum element indicating that element is inside, outside or on border of area.
     */
    public Result isIn(final Vector3f center, final float size, final Vector3f point)
    {
        return this.isIn(center.x, center.y, center.z, size, point.x, point.y, point.z);
    }

    /**
     * Returns enum element indicating that element is inside, outside or on border of area.
     *
     * @param cx    x center coordinates of area.
     * @param cy    y center coordinates of area.
     * @param cz    z center coordinates of area.
     * @param size  size of area in all 3 axis.
     * @param point point to be checked if it is inside of given area.
     *
     * @return enum element indicating that element is inside, outside or on border of area.
     */
    public Result isIn(final float cx, final float cy, final float cz, final Vector3f size, final Vector3f point)
    {
        return this.isIn(cx, cy, cz, size.x, size.y, size.z, point.x, point.y, point.z);
    }

    /**
     * Returns enum element indicating that element is inside, outside or on border of area.
     *
     * @param cx    x center coordinates of area.
     * @param cy    y center coordinates of area.
     * @param cz    z center coordinates of area.
     * @param size  size of area in all 3 axis.
     * @param point point to be checked if it is inside of given area.
     *
     * @return enum element indicating that element is inside, outside or on border of area.
     */
    public Result isIn(final float cx, final float cy, final float cz, final Vector2f size, final Vector3f point)
    {
        return this.isIn(cx, cy, cz, size.x, size.y, point.x, point.y, point.z);
    }

    /**
     * Returns enum element indicating that element is inside, outside or on border of area.
     *
     * @param cx    x center coordinates of area.
     * @param cy    y center coordinates of area.
     * @param cz    z center coordinates of area.
     * @param size  size of area in all 3 axis.
     * @param point point to be checked if it is inside of given area.
     *
     * @return enum element indicating that element is inside, outside or on border of area.
     */
    public Result isIn(final float cx, final float cy, final float cz, final float size, final Vector3f point)
    {
        return this.isIn(cx, cy, cz, size, point.x, point.y, point.z);
    }

    /**
     * Returns enum element indicating that element is inside, outside or on border of area.
     *
     * @param center center point of area.
     * @param size   size of area in all 3 axis.
     * @param px     x coordinates of point to check.
     * @param py     y coordinates of point to check.
     * @param pz     z coordinates of point to check.
     *
     * @return enum element indicating that element is inside, outside or on border of area.
     */
    public Result isIn(final Vector3f center, final Vector3f size, final float px, final float py, final float pz)
    {
        return this.isIn(center.x, center.y, center.z, size.x, size.y, size.z, px, py, pz);
    }

    /**
     * Returns enum element indicating that element is inside, outside or on border of area.
     *
     * @param center center point of area.
     * @param sx     size of area in x axis.
     * @param sy     size of area in y axis.
     * @param sz     size of area in z axis.
     * @param px     x coordinates of point to check.
     * @param py     y coordinates of point to check.
     * @param pz     z coordinates of point to check.
     *
     * @return enum element indicating that element is inside, outside or on border of area.
     */
    public Result isIn(final Vector3f center, final float sx, final float sy, final float sz, final float px, final float py, final float pz)
    {
        return this.isIn(center.x, center.y, center.z, sx, sy, sz, px, py, pz);
    }

    /**
     * Returns enum element indicating that element is inside, outside or on border of area.
     *
     * @param center center point of area.
     * @param size   size of area in all 3 axis.
     * @param px     x coordinates of point to check.
     * @param py     y coordinates of point to check.
     * @param pz     z coordinates of point to check.
     *
     * @return enum element indicating that element is inside, outside or on border of area.
     */
    public Result isIn(final Vector3f center, final Vector2f size, final float px, final float py, final float pz)
    {
        return this.isIn(center.x, center.y, center.z, size.x, size.y, px, py, pz);
    }

    /**
     * Returns enum element indicating that element is inside, outside or on border of area.
     *
     * @param center center point of area.
     * @param sxz    size of area in xz axis.
     * @param sy     size of area in y axis.
     * @param px     x coordinates of point to check.
     * @param py     y coordinates of point to check.
     * @param pz     z coordinates of point to check.
     *
     * @return enum element indicating that element is inside, outside or on border of area.
     */
    public Result isIn(final Vector3f center, final float sxz, final float sy, final float px, final float py, final float pz)
    {
        return this.isIn(center.x, center.y, center.z, sxz, sy, px, py, pz);
    }

    /**
     * Returns enum element indicating that element is inside, outside or on border of area.
     *
     * @param center center point of area.
     * @param size   size of area in all 3 axis.
     * @param px     x coordinates of point to check.
     * @param py     y coordinates of point to check.
     * @param pz     z coordinates of point to check.
     *
     * @return enum element indicating that element is inside, outside or on border of area.
     */
    public Result isIn(final Vector3f center, final float size, final float px, final float py, final float pz)
    {
        return this.isIn(center.x, center.y, center.z, size, px, py, pz);
    }

    /**
     * Returns enum element indicating that element is inside, outside or on border of area.
     *
     * @param cx   x center coordinates of area.
     * @param cy   y center coordinates of area.
     * @param cz   z center coordinates of area.
     * @param size size of area in all 3 axis.
     * @param px   x coordinates of point to check.
     * @param py   y coordinates of point to check.
     * @param pz   z coordinates of point to check.
     *
     * @return enum element indicating that element is inside, outside or on border of area.
     */
    public Result isIn(final float cx, final float cy, final float cz, final Vector3f size, final float px, final float py, final float pz)
    {
        return this.isIn(cx, cy, cz, size.x, size.y, size.z, px, py, pz);
    }

    /**
     * Returns enum element indicating that element is inside, outside or on border of area.
     *
     * @param cx   x center coordinates of area.
     * @param cy   y center coordinates of area.
     * @param cz   z center coordinates of area.
     * @param size size of area in all 3 axis.
     * @param px   x coordinates of point to check.
     * @param py   y coordinates of point to check.
     * @param pz   z coordinates of point to check.
     *
     * @return enum element indicating that element is inside, outside or on border of area.
     */
    public Result isIn(final float cx, final float cy, final float cz, final Vector2f size, final float px, final float py, final float pz)
    {
        return this.isIn(cx, cy, cz, size.x, size.y, px, py, pz);
    }

    /**
     * Returns enum element indicating that element is inside, outside or on border of area.
     *
     * @param cx   x center coordinates of area.
     * @param cy   y center coordinates of area.
     * @param cz   z center coordinates of area.
     * @param size size of area in all 3 axis.
     * @param px   x coordinates of point to check.
     * @param py   y coordinates of point to check.
     * @param pz   z coordinates of point to check.
     *
     * @return enum element indicating that element is inside, outside or on border of area.
     */
    public abstract Result isIn(float cx, float cy, float cz, float size, float px, float py, float pz);

    /**
     * Returns enum element indicating that element is inside, outside or on border of area.
     *
     * @param cx  x center coordinates of area.
     * @param cy  y center coordinates of area.
     * @param cz  z center coordinates of area.
     * @param sxz size of area in xz axis.
     * @param sy  size of area in y axis.
     * @param px  x coordinates of point to check.
     * @param py  y coordinates of point to check.
     * @param pz  z coordinates of point to check.
     *
     * @return enum element indicating that element is inside, outside or on border of area.
     */
    public abstract Result isIn(float cx, float cy, float cz, float sxz, float sy, float px, float py, float pz);

    /**
     * Returns enum element indicating that element is inside, outside or on border of area.
     *
     * @param cx x center coordinates of area.
     * @param cy y center coordinates of area.
     * @param cz z center coordinates of area.
     * @param sx size of area in x axis.
     * @param sy size of area in y axis.
     * @param sz size of area in z axis.
     * @param px x coordinates of point to check.
     * @param py y coordinates of point to check.
     * @param pz z coordinates of point to check.
     *
     * @return enum element indicating that element is inside, outside or on border of area.
     */
    public abstract Result isIn(float cx, float cy, float cz, float sx, float sy, float sz, float px, float py, float pz);

    /**
     * Returns enum element indicating that element is inside, outside or on border of area.
     *
     * @param cx   x center coordinates of area.
     * @param cy   y center coordinates of area.
     * @param cz   z center coordinates of area.
     * @param size size of area in all 3 axis.
     * @param px   x coordinates of point to check.
     * @param py   y coordinates of point to check.
     * @param pz   z coordinates of point to check.
     *
     * @return enum element indicating that element is inside, outside or on border of area.
     */
    public abstract Result isIn(int cx, int cy, int cz, int size, int px, int py, int pz);

    /**
     * Returns enum element indicating that element is inside, outside or on border of area.
     *
     * @param cx  x center coordinates of area.
     * @param cy  y center coordinates of area.
     * @param cz  z center coordinates of area.
     * @param sxz size of area in xz axis.
     * @param sy  size of area in y axis.
     * @param px  x coordinates of point to check.
     * @param py  y coordinates of point to check.
     * @param pz  z coordinates of point to check.
     *
     * @return enum element indicating that element is inside, outside or on border of area.
     */
    public abstract Result isIn(int cx, int cy, int cz, int sxz, int sy, int px, int py, int pz);

    /**
     * Returns enum element indicating that element is inside, outside or on border of area.
     *
     * @param cx x center coordinates of area.
     * @param cy y center coordinates of area.
     * @param cz z center coordinates of area.
     * @param sx size of area in x axis.
     * @param sy size of area in y axis.
     * @param sz size of area in z axis.
     * @param px x coordinates of point to check.
     * @param py y coordinates of point to check.
     * @param pz z coordinates of point to check.
     *
     * @return enum element indicating that element is inside, outside or on border of area.
     */
    public abstract Result isIn(int cx, int cy, int cz, int sx, int sy, int sz, int px, int py, int pz);

    /**
     * Returns true if point is in area or on area border.
     *
     * @param center center point of area.
     * @param size   size of area in all 3 axis.
     * @param point  point to be checked if it is inside of given area.
     *
     * @return true if point is in area or on area border.
     */
    public boolean isNotOutside(final Vector3d center, final Vector3d size, final Vector3d point)
    {
        return this.isNotOutside(center.x, center.y, center.z, size.x, size.y, size.z, point.z, point.y, point.z);
    }

    /**
     * Returns true if point is in area or on area border.
     *
     * @param center center point of area.
     * @param sx     size of area in x axis.
     * @param sy     size of area in y axis.
     * @param sz     size of area in z axis.
     * @param point  point to be checked if it is inside of given area.
     *
     * @return true if point is in area or on area border.
     */
    public boolean isNotOutside(final Vector3d center, final double sx, final double sy, final double sz, final Vector3d point)
    {
        return this.isNotOutside(center.x, center.y, center.z, sx, sy, sz, point.x, point.y, point.z);
    }

    /**
     * Returns true if point is in area or on area border.
     *
     * @param center center point of area.
     * @param size   size of area in all 3 axis.
     * @param point  point to be checked if it is inside of given area.
     *
     * @return true if point is in area or on area border.
     */
    public boolean isNotOutside(final Vector3d center, final Vector2d size, final Vector3d point)
    {
        return this.isNotOutside(center.x, center.y, center.z, size.x, size.y, point.x, point.y, point.z);
    }

    /**
     * Returns true if point is in area or on area border.
     *
     * @param center center point of area.
     * @param sxz    size of area in xz axis.
     * @param sy     size of area in y axis.
     * @param point  point to be checked if it is inside of given area.
     *
     * @return true if point is in area or on area border.
     */
    public boolean isNotOutside(final Vector3d center, final double sxz, final double sy, final Vector3d point)
    {
        return this.isNotOutside(center.x, center.y, center.z, sxz, sy, point.x, point.y, point.z);
    }

    /**
     * Returns true if point is in area or on area border.
     *
     * @param center center point of area.
     * @param size   size of area in all 3 axis.
     * @param point  point to be checked if it is inside of given area.
     *
     * @return true if point is in area or on area border.
     */
    public boolean isNotOutside(final Vector3d center, final double size, final Vector3d point)
    {
        return this.isNotOutside(center.x, center.y, center.z, size, point.x, point.y, point.z);
    }

    /**
     * Returns true if point is in area or on area border.
     *
     * @param cx    x center coordinates of area.
     * @param cy    y center coordinates of area.
     * @param cz    z center coordinates of area.
     * @param size  size of area in all 3 axis.
     * @param point point to be checked if it is inside of given area.
     *
     * @return true if point is in area or on area border.
     */
    public boolean isNotOutside(final double cx, final double cy, final double cz, final Vector3d size, final Vector3d point)
    {
        return this.isNotOutside(cx, cy, cz, size.x, size.y, size.z, point.x, point.y, point.z);
    }

    /**
     * Returns true if point is in area or on area border.
     *
     * @param cx    x center coordinates of area.
     * @param cy    y center coordinates of area.
     * @param cz    z center coordinates of area.
     * @param size  size of area in all 3 axis.
     * @param point point to be checked if it is inside of given area.
     *
     * @return true if point is in area or on area border.
     */
    public boolean isNotOutside(final double cx, final double cy, final double cz, final Vector2d size, final Vector3d point)
    {
        return this.isNotOutside(cx, cy, cz, size.x, size.y, point.x, point.y, point.z);
    }

    /**
     * Returns true if point is in area or on area border.
     *
     * @param cx    x center coordinates of area.
     * @param cy    y center coordinates of area.
     * @param cz    z center coordinates of area.
     * @param size  size of area in all 3 axis.
     * @param point point to be checked if it is inside of given area.
     *
     * @return true if point is in area or on area border.
     */
    public boolean isNotOutside(final double cx, final double cy, final double cz, final double size, final Vector3d point)
    {
        return this.isNotOutside(cx, cy, cz, size, point.x, point.y, point.z);
    }

    /**
     * Returns true if point is in area or on area border.
     *
     * @param center center point of area.
     * @param size   size of area in all 3 axis.
     * @param px     x coordinates of point to check.
     * @param py     y coordinates of point to check.
     * @param pz     z coordinates of point to check.
     *
     * @return true if point is in area or on area border.
     */
    public boolean isNotOutside(final Vector3d center, final Vector3d size, final double px, final double py, final double pz)
    {
        return this.isNotOutside(center.x, center.y, center.z, size.x, size.y, size.z, px, py, pz);
    }

    /**
     * Returns true if point is in area or on area border.
     *
     * @param center center point of area.
     * @param sx     size of area in x axis.
     * @param sy     size of area in y axis.
     * @param sz     size of area in z axis.
     * @param px     x coordinates of point to check.
     * @param py     y coordinates of point to check.
     * @param pz     z coordinates of point to check.
     *
     * @return true if point is in area or on area border.
     */
    public boolean isNotOutside(final Vector3d center, final double sx, final double sy, final double sz, final double px, final double py, final double pz)
    {
        return this.isNotOutside(center.x, center.y, center.z, sx, sy, sz, px, py, pz);
    }

    /**
     * Returns true if point is in area or on area border.
     *
     * @param center center point of area.
     * @param size   size of area in all 3 axis.
     * @param px     x coordinates of point to check.
     * @param py     y coordinates of point to check.
     * @param pz     z coordinates of point to check.
     *
     * @return true if point is in area or on area border.
     */
    public boolean isNotOutside(final Vector3d center, final Vector2d size, final double px, final double py, final double pz)
    {
        return this.isNotOutside(center.x, center.y, center.z, size.x, size.y, px, py, pz);
    }

    /**
     * Returns true if point is in area or on area border.
     *
     * @param center center point of area.
     * @param sxz    size of area in xz axis.
     * @param sy     size of area in y axis.
     * @param px     x coordinates of point to check.
     * @param py     y coordinates of point to check.
     * @param pz     z coordinates of point to check.
     *
     * @return true if point is in area or on area border.
     */
    public boolean isNotOutside(final Vector3d center, final double sxz, final double sy, final double px, final double py, final double pz)
    {
        return this.isNotOutside(center.x, center.y, center.z, sxz, sy, px, py, pz);
    }

    /**
     * Returns true if point is in area or on area border.
     *
     * @param center center point of area.
     * @param size   size of area in all 3 axis.
     * @param px     x coordinates of point to check.
     * @param py     y coordinates of point to check.
     * @param pz     z coordinates of point to check.
     *
     * @return true if point is in area or on area border.
     */
    public boolean isNotOutside(final Vector3d center, final double size, final double px, final double py, final double pz)
    {
        return this.isNotOutside(center.x, center.y, center.z, size, px, py, pz);
    }

    /**
     * Returns true if point is in area or on area border.
     *
     * @param cx   x center coordinates of area.
     * @param cy   y center coordinates of area.
     * @param cz   z center coordinates of area.
     * @param size size of area in all 3 axis.
     * @param px   x coordinates of point to check.
     * @param py   y coordinates of point to check.
     * @param pz   z coordinates of point to check.
     *
     * @return true if point is in area or on area border.
     */
    public boolean isNotOutside(final double cx, final double cy, final double cz, final Vector3d size, final double px, final double py, final double pz)
    {
        return this.isNotOutside(cx, cy, cz, size.x, size.y, size.z, px, py, pz);
    }

    /**
     * Returns true if point is in area or on area border.
     *
     * @param cx   x center coordinates of area.
     * @param cy   y center coordinates of area.
     * @param cz   z center coordinates of area.
     * @param size size of area in all 3 axis.
     * @param px   x coordinates of point to check.
     * @param py   y coordinates of point to check.
     * @param pz   z coordinates of point to check.
     *
     * @return true if point is in area or on area border.
     */
    public boolean isNotOutside(final double cx, final double cy, final double cz, final Vector2d size, final double px, final double py, final double pz)
    {
        return this.isNotOutside(cx, cy, cz, size.x, size.y, px, py, pz);
    }

    /**
     * Returns true if point is in area or on area border.
     *
     * @param cx   x center coordinates of area.
     * @param cy   y center coordinates of area.
     * @param cz   z center coordinates of area.
     * @param size size of area in all 3 axis.
     * @param px   x coordinates of point to check.
     * @param py   y coordinates of point to check.
     * @param pz   z coordinates of point to check.
     *
     * @return true if point is in area or on area border.
     */
    public abstract boolean isNotOutside(double cx, double cy, double cz, double size, double px, double py, double pz);

    /**
     * Returns true if point is in area or on area border.
     *
     * @param cx  x center coordinates of area.
     * @param cy  y center coordinates of area.
     * @param cz  z center coordinates of area.
     * @param sxz size of area in xz axis.
     * @param sy  size of area in y axis.
     * @param px  x coordinates of point to check.
     * @param py  y coordinates of point to check.
     * @param pz  z coordinates of point to check.
     *
     * @return true if point is in area or on area border.
     */
    public abstract boolean isNotOutside(double cx, double cy, double cz, double sxz, double sy, double px, double py, double pz);

    /**
     * Returns true if point is in area or on area border.
     *
     * @param cx x center coordinates of area.
     * @param cy y center coordinates of area.
     * @param cz z center coordinates of area.
     * @param sx size of area in x axis.
     * @param sy size of area in y axis.
     * @param sz size of area in z axis.
     * @param px x coordinates of point to check.
     * @param py y coordinates of point to check.
     * @param pz z coordinates of point to check.
     *
     * @return true if point is in area or on area border.
     */
    public abstract boolean isNotOutside(double cx, double cy, double cz, double sx, double sy, double sz, double px, double py, double pz);

    /**
     * Returns true if point is in area or on area border.
     *
     * @param cx   x center coordinates of area.
     * @param cy   y center coordinates of area.
     * @param cz   z center coordinates of area.
     * @param size size of area in all 3 axis.
     * @param px   x coordinates of point to check.
     * @param py   y coordinates of point to check.
     * @param pz   z coordinates of point to check.
     *
     * @return true if point is in area or on area border.
     */
    public abstract boolean isNotOutside(long cx, long cy, long cz, long size, long px, long py, long pz);

    /**
     * Returns true if point is in area or on area border.
     *
     * @param cx  x center coordinates of area.
     * @param cy  y center coordinates of area.
     * @param cz  z center coordinates of area.
     * @param sxz size of area in xz axis.
     * @param sy  size of area in y axis.
     * @param px  x coordinates of point to check.
     * @param py  y coordinates of point to check.
     * @param pz  z coordinates of point to check.
     *
     * @return true if point is in area or on area border.
     */
    public abstract boolean isNotOutside(long cx, long cy, long cz, long sxz, long sy, long px, long py, long pz);

    /**
     * Returns true if point is in area or on area border.
     *
     * @param cx x center coordinates of area.
     * @param cy y center coordinates of area.
     * @param cz z center coordinates of area.
     * @param sx size of area in x axis.
     * @param sy size of area in y axis.
     * @param sz size of area in z axis.
     * @param px x coordinates of point to check.
     * @param py y coordinates of point to check.
     * @param pz z coordinates of point to check.
     *
     * @return true if point is in area or on area border.
     */
    public abstract boolean isNotOutside(long cx, long cy, long cz, long sx, long sy, long sz, long px, long py, long pz);

    /**
     * Returns true if point is in area or on area border.
     *
     * @param center center point of area.
     * @param size   size of area in all 3 axis.
     * @param point  point to be checked if it is inside of given area.
     *
     * @return true if point is in area or on area border.
     */
    public boolean isNotOutside(final Vector3f center, final Vector3f size, final Vector3f point)
    {
        return this.isNotOutside(center.x, center.y, center.z, size.x, size.y, size.z, point.z, point.y, point.z);
    }

    /**
     * Returns true if point is in area or on area border.
     *
     * @param center center point of area.
     * @param sx     size of area in x axis.
     * @param sy     size of area in y axis.
     * @param sz     size of area in z axis.
     * @param point  point to be checked if it is inside of given area.
     *
     * @return true if point is in area or on area border.
     */
    public boolean isNotOutside(final Vector3f center, final float sx, final float sy, final float sz, final Vector3f point)
    {
        return this.isNotOutside(center.x, center.y, center.z, sx, sy, sz, point.x, point.y, point.z);
    }

    /**
     * Returns true if point is in area or on area border.
     *
     * @param center center point of area.
     * @param size   size of area in all 3 axis.
     * @param point  point to be checked if it is inside of given area.
     *
     * @return true if point is in area or on area border.
     */
    public boolean isNotOutside(final Vector3f center, final Vector2f size, final Vector3f point)
    {
        return this.isNotOutside(center.x, center.y, center.z, size.x, size.y, point.x, point.y, point.z);
    }

    /**
     * Returns true if point is in area or on area border.
     *
     * @param center center point of area.
     * @param sxz    size of area in xz axis.
     * @param sy     size of area in y axis.
     * @param point  point to be checked if it is inside of given area.
     *
     * @return true if point is in area or on area border.
     */
    public boolean isNotOutside(final Vector3f center, final float sxz, final float sy, final Vector3f point)
    {
        return this.isNotOutside(center.x, center.y, center.z, sxz, sy, point.x, point.y, point.z);
    }

    /**
     * Returns true if point is in area or on area border.
     *
     * @param center center point of area.
     * @param size   size of area in all 3 axis.
     * @param point  point to be checked if it is inside of given area.
     *
     * @return true if point is in area or on area border.
     */
    public boolean isNotOutside(final Vector3f center, final float size, final Vector3f point)
    {
        return this.isNotOutside(center.x, center.y, center.z, size, point.x, point.y, point.z);
    }

    /**
     * Returns true if point is in area or on area border.
     *
     * @param cx    x center coordinates of area.
     * @param cy    y center coordinates of area.
     * @param cz    z center coordinates of area.
     * @param size  size of area in all 3 axis.
     * @param point point to be checked if it is inside of given area.
     *
     * @return true if point is in area or on area border.
     */
    public boolean isNotOutside(final float cx, final float cy, final float cz, final Vector3f size, final Vector3f point)
    {
        return this.isNotOutside(cx, cy, cz, size.x, size.y, size.z, point.x, point.y, point.z);
    }

    /**
     * Returns true if point is in area or on area border.
     *
     * @param cx    x center coordinates of area.
     * @param cy    y center coordinates of area.
     * @param cz    z center coordinates of area.
     * @param size  size of area in all 3 axis.
     * @param point point to be checked if it is inside of given area.
     *
     * @return true if point is in area or on area border.
     */
    public boolean isNotOutside(final float cx, final float cy, final float cz, final Vector2f size, final Vector3f point)
    {
        return this.isNotOutside(cx, cy, cz, size.x, size.y, point.x, point.y, point.z);
    }

    /**
     * Returns true if point is in area or on area border.
     *
     * @param cx    x center coordinates of area.
     * @param cy    y center coordinates of area.
     * @param cz    z center coordinates of area.
     * @param size  size of area in all 3 axis.
     * @param point point to be checked if it is inside of given area.
     *
     * @return true if point is in area or on area border.
     */
    public boolean isNotOutside(final float cx, final float cy, final float cz, final float size, final Vector3f point)
    {
        return this.isNotOutside(cx, cy, cz, size, point.x, point.y, point.z);
    }

    /**
     * Returns true if point is in area or on area border.
     *
     * @param center center point of area.
     * @param size   size of area in all 3 axis.
     * @param px     x coordinates of point to check.
     * @param py     y coordinates of point to check.
     * @param pz     z coordinates of point to check.
     *
     * @return true if point is in area or on area border.
     */
    public boolean isNotOutside(final Vector3f center, final Vector3f size, final float px, final float py, final float pz)
    {
        return this.isNotOutside(center.x, center.y, center.z, size.x, size.y, size.z, px, py, pz);
    }

    /**
     * Returns true if point is in area or on area border.
     *
     * @param center center point of area.
     * @param sx     size of area in x axis.
     * @param sy     size of area in y axis.
     * @param sz     size of area in z axis.
     * @param px     x coordinates of point to check.
     * @param py     y coordinates of point to check.
     * @param pz     z coordinates of point to check.
     *
     * @return true if point is in area or on area border.
     */
    public boolean isNotOutside(final Vector3f center, final float sx, final float sy, final float sz, final float px, final float py, final float pz)
    {
        return this.isNotOutside(center.x, center.y, center.z, sx, sy, sz, px, py, pz);
    }

    /**
     * Returns true if point is in area or on area border.
     *
     * @param center center point of area.
     * @param size   size of area in all 3 axis.
     * @param px     x coordinates of point to check.
     * @param py     y coordinates of point to check.
     * @param pz     z coordinates of point to check.
     *
     * @return true if point is in area or on area border.
     */
    public boolean isNotOutside(final Vector3f center, final Vector2f size, final float px, final float py, final float pz)
    {
        return this.isNotOutside(center.x, center.y, center.z, size.x, size.y, px, py, pz);
    }

    /**
     * Returns true if point is in area or on area border.
     *
     * @param center center point of area.
     * @param sxz    size of area in xz axis.
     * @param sy     size of area in y axis.
     * @param px     x coordinates of point to check.
     * @param py     y coordinates of point to check.
     * @param pz     z coordinates of point to check.
     *
     * @return true if point is in area or on area border.
     */
    public boolean isNotOutside(final Vector3f center, final float sxz, final float sy, final float px, final float py, final float pz)
    {
        return this.isNotOutside(center.x, center.y, center.z, sxz, sy, px, py, pz);
    }

    /**
     * Returns true if point is in area or on area border.
     *
     * @param center center point of area.
     * @param size   size of area in all 3 axis.
     * @param px     x coordinates of point to check.
     * @param py     y coordinates of point to check.
     * @param pz     z coordinates of point to check.
     *
     * @return true if point is in area or on area border.
     */
    public boolean isNotOutside(final Vector3f center, final float size, final float px, final float py, final float pz)
    {
        return this.isNotOutside(center.x, center.y, center.z, size, px, py, pz);
    }

    /**
     * Returns true if point is in area or on area border.
     *
     * @param cx   x center coordinates of area.
     * @param cy   y center coordinates of area.
     * @param cz   z center coordinates of area.
     * @param size size of area in all 3 axis.
     * @param px   x coordinates of point to check.
     * @param py   y coordinates of point to check.
     * @param pz   z coordinates of point to check.
     *
     * @return true if point is in area or on area border.
     */
    public boolean isNotOutside(final float cx, final float cy, final float cz, final Vector3f size, final float px, final float py, final float pz)
    {
        return this.isNotOutside(cx, cy, cz, size.x, size.y, size.z, px, py, pz);
    }

    /**
     * Returns true if point is in area or on area border.
     *
     * @param cx   x center coordinates of area.
     * @param cy   y center coordinates of area.
     * @param cz   z center coordinates of area.
     * @param size size of area in all 3 axis.
     * @param px   x coordinates of point to check.
     * @param py   y coordinates of point to check.
     * @param pz   z coordinates of point to check.
     *
     * @return true if point is in area or on area border.
     */
    public boolean isNotOutside(final float cx, final float cy, final float cz, final Vector2f size, final float px, final float py, final float pz)
    {
        return this.isNotOutside(cx, cy, cz, size.x, size.y, px, py, pz);
    }

    /**
     * Returns true if point is in area or on area border.
     *
     * @param cx   x center coordinates of area.
     * @param cy   y center coordinates of area.
     * @param cz   z center coordinates of area.
     * @param size size of area in all 3 axis.
     * @param px   x coordinates of point to check.
     * @param py   y coordinates of point to check.
     * @param pz   z coordinates of point to check.
     *
     * @return true if point is in area or on area border.
     */
    public abstract boolean isNotOutside(float cx, float cy, float cz, float size, float px, float py, float pz);

    /**
     * Returns true if point is in area or on area border.
     *
     * @param cx  x center coordinates of area.
     * @param cy  y center coordinates of area.
     * @param cz  z center coordinates of area.
     * @param sxz size of area in xz axis.
     * @param sy  size of area in y axis.
     * @param px  x coordinates of point to check.
     * @param py  y coordinates of point to check.
     * @param pz  z coordinates of point to check.
     *
     * @return true if point is in area or on area border.
     */
    public abstract boolean isNotOutside(float cx, float cy, float cz, float sxz, float sy, float px, float py, float pz);

    /**
     * Returns true if point is in area or on area border.
     *
     * @param cx x center coordinates of area.
     * @param cy y center coordinates of area.
     * @param cz z center coordinates of area.
     * @param sx size of area in x axis.
     * @param sy size of area in y axis.
     * @param sz size of area in z axis.
     * @param px x coordinates of point to check.
     * @param py y coordinates of point to check.
     * @param pz z coordinates of point to check.
     *
     * @return true if point is in area or on area border.
     */
    public abstract boolean isNotOutside(float cx, float cy, float cz, float sx, float sy, float sz, float px, float py, float pz);

    /**
     * Returns true if point is in area or on area border.
     *
     * @param cx   x center coordinates of area.
     * @param cy   y center coordinates of area.
     * @param cz   z center coordinates of area.
     * @param size size of area in all 3 axis.
     * @param px   x coordinates of point to check.
     * @param py   y coordinates of point to check.
     * @param pz   z coordinates of point to check.
     *
     * @return true if point is in area or on area border.
     */
    public abstract boolean isNotOutside(int cx, int cy, int cz, int size, int px, int py, int pz);

    /**
     * Returns true if point is in area or on area border.
     *
     * @param cx  x center coordinates of area.
     * @param cy  y center coordinates of area.
     * @param cz  z center coordinates of area.
     * @param sxz size of area in xz axis.
     * @param sy  size of area in y axis.
     * @param px  x coordinates of point to check.
     * @param py  y coordinates of point to check.
     * @param pz  z coordinates of point to check.
     *
     * @return true if point is in area or on area border.
     */
    public abstract boolean isNotOutside(int cx, int cy, int cz, int sxz, int sy, int px, int py, int pz);

    /**
     * Returns true if point is in area or on area border.
     *
     * @param cx x center coordinates of area.
     * @param cy y center coordinates of area.
     * @param cz z center coordinates of area.
     * @param sx size of area in x axis.
     * @param sy size of area in y axis.
     * @param sz size of area in z axis.
     * @param px x coordinates of point to check.
     * @param py y coordinates of point to check.
     * @param pz z coordinates of point to check.
     *
     * @return true if point is in area or on area border.
     */
    public abstract boolean isNotOutside(int cx, int cy, int cz, int sx, int sy, int sz, int px, int py, int pz);

    /**
     * Returns true if point is exacly in area. (it can't be on border)
     *
     * @param center center point of area.
     * @param size   size of area in all 3 axis.
     * @param point  point to be checked if it is inside of given area.
     *
     * @return true if point is exacly in area. (it can't be on border)
     */
    public boolean isExactlyIn(final Vector3d center, final Vector3d size, final Vector3d point)
    {
        return this.isExactlyIn(center.x, center.y, center.z, size.x, size.y, size.z, point.z, point.y, point.z);
    }

    /**
     * Returns true if point is exacly in area. (it can't be on border)
     *
     * @param center center point of area.
     * @param sx     size of area in x axis.
     * @param sy     size of area in y axis.
     * @param sz     size of area in z axis.
     * @param point  point to be checked if it is inside of given area.
     *
     * @return true if point is exacly in area. (it can't be on border)
     */
    public boolean isExactlyIn(final Vector3d center, final double sx, final double sy, final double sz, final Vector3d point)
    {
        return this.isExactlyIn(center.x, center.y, center.z, sx, sy, sz, point.x, point.y, point.z);
    }

    /**
     * Returns true if point is exacly in area. (it can't be on border)
     *
     * @param center center point of area.
     * @param size   size of area in all 3 axis.
     * @param point  point to be checked if it is inside of given area.
     *
     * @return true if point is exacly in area. (it can't be on border)
     */
    public boolean isExactlyIn(final Vector3d center, final Vector2d size, final Vector3d point)
    {
        return this.isExactlyIn(center.x, center.y, center.z, size.x, size.y, point.x, point.y, point.z);
    }

    /**
     * Returns true if point is exacly in area. (it can't be on border)
     *
     * @param center center point of area.
     * @param sxz    size of area in xz axis.
     * @param sy     size of area in y axis.
     * @param point  point to be checked if it is inside of given area.
     *
     * @return true if point is exacly in area. (it can't be on border)
     */
    public boolean isExactlyIn(final Vector3d center, final double sxz, final double sy, final Vector3d point)
    {
        return this.isExactlyIn(center.x, center.y, center.z, sxz, sy, point.x, point.y, point.z);
    }

    /**
     * Returns true if point is exacly in area. (it can't be on border)
     *
     * @param center center point of area.
     * @param size   size of area in all 3 axis.
     * @param point  point to be checked if it is inside of given area.
     *
     * @return true if point is exacly in area. (it can't be on border)
     */
    public boolean isExactlyIn(final Vector3d center, final double size, final Vector3d point)
    {
        return this.isExactlyIn(center.x, center.y, center.z, size, point.x, point.y, point.z);
    }

    /**
     * Returns true if point is exacly in area. (it can't be on border)
     *
     * @param cx    x center coordinates of area.
     * @param cy    y center coordinates of area.
     * @param cz    z center coordinates of area.
     * @param size  size of area in all 3 axis.
     * @param point point to be checked if it is inside of given area.
     *
     * @return true if point is exacly in area. (it can't be on border)
     */
    public boolean isExactlyIn(final double cx, final double cy, final double cz, final Vector3d size, final Vector3d point)
    {
        return this.isExactlyIn(cx, cy, cz, size.x, size.y, size.z, point.x, point.y, point.z);
    }

    /**
     * Returns true if point is exacly in area. (it can't be on border)
     *
     * @param cx    x center coordinates of area.
     * @param cy    y center coordinates of area.
     * @param cz    z center coordinates of area.
     * @param size  size of area in all 3 axis.
     * @param point point to be checked if it is inside of given area.
     *
     * @return true if point is exacly in area. (it can't be on border)
     */
    public boolean isExactlyIn(final double cx, final double cy, final double cz, final Vector2d size, final Vector3d point)
    {
        return this.isExactlyIn(cx, cy, cz, size.x, size.y, point.x, point.y, point.z);
    }

    /**
     * Returns true if point is exacly in area. (it can't be on border)
     *
     * @param cx    x center coordinates of area.
     * @param cy    y center coordinates of area.
     * @param cz    z center coordinates of area.
     * @param size  size of area in all 3 axis.
     * @param point point to be checked if it is inside of given area.
     *
     * @return true if point is exacly in area. (it can't be on border)
     */
    public boolean isExactlyIn(final double cx, final double cy, final double cz, final double size, final Vector3d point)
    {
        return this.isExactlyIn(cx, cy, cz, size, point.x, point.y, point.z);
    }

    /**
     * Returns true if point is exacly in area. (it can't be on border)
     *
     * @param center center point of area.
     * @param size   size of area in all 3 axis.
     * @param px     x coordinates of point to check.
     * @param py     y coordinates of point to check.
     * @param pz     z coordinates of point to check.
     *
     * @return true if point is exacly in area. (it can't be on border)
     */
    public boolean isExactlyIn(final Vector3d center, final Vector3d size, final double px, final double py, final double pz)
    {
        return this.isExactlyIn(center.x, center.y, center.z, size.x, size.y, size.z, px, py, pz);
    }

    /**
     * Returns true if point is exacly in area. (it can't be on border)
     *
     * @param center center point of area.
     * @param sx     size of area in x axis.
     * @param sy     size of area in y axis.
     * @param sz     size of area in z axis.
     * @param px     x coordinates of point to check.
     * @param py     y coordinates of point to check.
     * @param pz     z coordinates of point to check.
     *
     * @return true if point is exacly in area. (it can't be on border)
     */
    public boolean isExactlyIn(final Vector3d center, final double sx, final double sy, final double sz, final double px, final double py, final double pz)
    {
        return this.isExactlyIn(center.x, center.y, center.z, sx, sy, sz, px, py, pz);
    }

    /**
     * Returns true if point is exacly in area. (it can't be on border)
     *
     * @param center center point of area.
     * @param size   size of area in all 3 axis.
     * @param px     x coordinates of point to check.
     * @param py     y coordinates of point to check.
     * @param pz     z coordinates of point to check.
     *
     * @return true if point is exacly in area. (it can't be on border)
     */
    public boolean isExactlyIn(final Vector3d center, final Vector2d size, final double px, final double py, final double pz)
    {
        return this.isExactlyIn(center.x, center.y, center.z, size.x, size.y, px, py, pz);
    }

    /**
     * Returns true if point is exacly in area. (it can't be on border)
     *
     * @param center center point of area.
     * @param sxz    size of area in xz axis.
     * @param sy     size of area in y axis.
     * @param px     x coordinates of point to check.
     * @param py     y coordinates of point to check.
     * @param pz     z coordinates of point to check.
     *
     * @return true if point is exacly in area. (it can't be on border)
     */
    public boolean isExactlyIn(final Vector3d center, final double sxz, final double sy, final double px, final double py, final double pz)
    {
        return this.isExactlyIn(center.x, center.y, center.z, sxz, sy, px, py, pz);
    }

    /**
     * Returns true if point is exacly in area. (it can't be on border)
     *
     * @param center center point of area.
     * @param size   size of area in all 3 axis.
     * @param px     x coordinates of point to check.
     * @param py     y coordinates of point to check.
     * @param pz     z coordinates of point to check.
     *
     * @return true if point is exacly in area. (it can't be on border)
     */
    public boolean isExactlyIn(final Vector3d center, final double size, final double px, final double py, final double pz)
    {
        return this.isExactlyIn(center.x, center.y, center.z, size, px, py, pz);
    }

    /**
     * Returns true if point is exacly in area. (it can't be on border)
     *
     * @param cx   x center coordinates of area.
     * @param cy   y center coordinates of area.
     * @param cz   z center coordinates of area.
     * @param size size of area in all 3 axis.
     * @param px   x coordinates of point to check.
     * @param py   y coordinates of point to check.
     * @param pz   z coordinates of point to check.
     *
     * @return true if point is exacly in area. (it can't be on border)
     */
    public boolean isExactlyIn(final double cx, final double cy, final double cz, final Vector3d size, final double px, final double py, final double pz)
    {
        return this.isExactlyIn(cx, cy, cz, size.x, size.y, size.z, px, py, pz);
    }

    /**
     * Returns true if point is exacly in area. (it can't be on border)
     *
     * @param cx   x center coordinates of area.
     * @param cy   y center coordinates of area.
     * @param cz   z center coordinates of area.
     * @param size size of area in all 3 axis.
     * @param px   x coordinates of point to check.
     * @param py   y coordinates of point to check.
     * @param pz   z coordinates of point to check.
     *
     * @return true if point is exacly in area. (it can't be on border)
     */
    public boolean isExactlyIn(final double cx, final double cy, final double cz, final Vector2d size, final double px, final double py, final double pz)
    {
        return this.isExactlyIn(cx, cy, cz, size.x, size.y, px, py, pz);
    }

    /**
     * Returns true if point is exacly in area. (it can't be on border)
     *
     * @param cx   x center coordinates of area.
     * @param cy   y center coordinates of area.
     * @param cz   z center coordinates of area.
     * @param size size of area in all 3 axis.
     * @param px   x coordinates of point to check.
     * @param py   y coordinates of point to check.
     * @param pz   z coordinates of point to check.
     *
     * @return true if point is exacly in area. (it can't be on border)
     */
    public abstract boolean isExactlyIn(double cx, double cy, double cz, double size, double px, double py, double pz);

    /**
     * Returns true if point is exacly in area. (it can't be on border)
     *
     * @param cx  x center coordinates of area.
     * @param cy  y center coordinates of area.
     * @param cz  z center coordinates of area.
     * @param sxz size of area in xz axis.
     * @param sy  size of area in y axis.
     * @param px  x coordinates of point to check.
     * @param py  y coordinates of point to check.
     * @param pz  z coordinates of point to check.
     *
     * @return true if point is exacly in area. (it can't be on border)
     */
    public abstract boolean isExactlyIn(double cx, double cy, double cz, double sxz, double sy, double px, double py, double pz);

    /**
     * Returns true if point is exacly in area. (it can't be on border)
     *
     * @param cx x center coordinates of area.
     * @param cy y center coordinates of area.
     * @param cz z center coordinates of area.
     * @param sx size of area in x axis.
     * @param sy size of area in y axis.
     * @param sz size of area in z axis.
     * @param px x coordinates of point to check.
     * @param py y coordinates of point to check.
     * @param pz z coordinates of point to check.
     *
     * @return true if point is exacly in area. (it can't be on border)
     */
    public abstract boolean isExactlyIn(double cx, double cy, double cz, double sx, double sy, double sz, double px, double py, double pz);

    /**
     * Returns true if point is exacly in area. (it can't be on border)
     *
     * @param cx   x center coordinates of area.
     * @param cy   y center coordinates of area.
     * @param cz   z center coordinates of area.
     * @param size size of area in all 3 axis.
     * @param px   x coordinates of point to check.
     * @param py   y coordinates of point to check.
     * @param pz   z coordinates of point to check.
     *
     * @return true if point is exacly in area. (it can't be on border)
     */
    public abstract boolean isExactlyIn(long cx, long cy, long cz, long size, long px, long py, long pz);

    /**
     * Returns true if point is exacly in area. (it can't be on border)
     *
     * @param cx  x center coordinates of area.
     * @param cy  y center coordinates of area.
     * @param cz  z center coordinates of area.
     * @param sxz size of area in xz axis.
     * @param sy  size of area in y axis.
     * @param px  x coordinates of point to check.
     * @param py  y coordinates of point to check.
     * @param pz  z coordinates of point to check.
     *
     * @return true if point is exacly in area. (it can't be on border)
     */
    public abstract boolean isExactlyIn(long cx, long cy, long cz, long sxz, long sy, long px, long py, long pz);

    /**
     * Returns true if point is exacly in area. (it can't be on border)
     *
     * @param cx x center coordinates of area.
     * @param cy y center coordinates of area.
     * @param cz z center coordinates of area.
     * @param sx size of area in x axis.
     * @param sy size of area in y axis.
     * @param sz size of area in z axis.
     * @param px x coordinates of point to check.
     * @param py y coordinates of point to check.
     * @param pz z coordinates of point to check.
     *
     * @return true if point is exacly in area. (it can't be on border)
     */
    public abstract boolean isExactlyIn(long cx, long cy, long cz, long sx, long sy, long sz, long px, long py, long pz);

    /**
     * Returns true if point is exacly in area. (it can't be on border)
     *
     * @param center center point of area.
     * @param size   size of area in all 3 axis.
     * @param point  point to be checked if it is inside of given area.
     *
     * @return true if point is exacly in area. (it can't be on border)
     */
    public boolean isExactlyIn(final Vector3f center, final Vector3f size, final Vector3f point)
    {
        return this.isExactlyIn(center.x, center.y, center.z, size.x, size.y, size.z, point.z, point.y, point.z);
    }

    /**
     * Returns true if point is exacly in area. (it can't be on border)
     *
     * @param center center point of area.
     * @param sx     size of area in x axis.
     * @param sy     size of area in y axis.
     * @param sz     size of area in z axis.
     * @param point  point to be checked if it is inside of given area.
     *
     * @return true if point is exacly in area. (it can't be on border)
     */
    public boolean isExactlyIn(final Vector3f center, final float sx, final float sy, final float sz, final Vector3f point)
    {
        return this.isExactlyIn(center.x, center.y, center.z, sx, sy, sz, point.x, point.y, point.z);
    }

    /**
     * Returns true if point is exacly in area. (it can't be on border)
     *
     * @param center center point of area.
     * @param size   size of area in all 3 axis.
     * @param point  point to be checked if it is inside of given area.
     *
     * @return true if point is exacly in area. (it can't be on border)
     */
    public boolean isExactlyIn(final Vector3f center, final Vector2f size, final Vector3f point)
    {
        return this.isExactlyIn(center.x, center.y, center.z, size.x, size.y, point.x, point.y, point.z);
    }

    /**
     * Returns true if point is exacly in area. (it can't be on border)
     *
     * @param center center point of area.
     * @param sxz    size of area in xz axis.
     * @param sy     size of area in y axis.
     * @param point  point to be checked if it is inside of given area.
     *
     * @return true if point is exacly in area. (it can't be on border)
     */
    public boolean isExactlyIn(final Vector3f center, final float sxz, final float sy, final Vector3f point)
    {
        return this.isExactlyIn(center.x, center.y, center.z, sxz, sy, point.x, point.y, point.z);
    }

    /**
     * Returns true if point is exacly in area. (it can't be on border)
     *
     * @param center center point of area.
     * @param size   size of area in all 3 axis.
     * @param point  point to be checked if it is inside of given area.
     *
     * @return true if point is exacly in area. (it can't be on border)
     */
    public boolean isExactlyIn(final Vector3f center, final float size, final Vector3f point)
    {
        return this.isExactlyIn(center.x, center.y, center.z, size, point.x, point.y, point.z);
    }

    /**
     * Returns true if point is exacly in area. (it can't be on border)
     *
     * @param cx    x center coordinates of area.
     * @param cy    y center coordinates of area.
     * @param cz    z center coordinates of area.
     * @param size  size of area in all 3 axis.
     * @param point point to be checked if it is inside of given area.
     *
     * @return true if point is exacly in area. (it can't be on border)
     */
    public boolean isExactlyIn(final float cx, final float cy, final float cz, final Vector3f size, final Vector3f point)
    {
        return this.isExactlyIn(cx, cy, cz, size.x, size.y, size.z, point.x, point.y, point.z);
    }

    /**
     * Returns true if point is exacly in area. (it can't be on border)
     *
     * @param cx    x center coordinates of area.
     * @param cy    y center coordinates of area.
     * @param cz    z center coordinates of area.
     * @param size  size of area in all 3 axis.
     * @param point point to be checked if it is inside of given area.
     *
     * @return true if point is exacly in area. (it can't be on border)
     */
    public boolean isExactlyIn(final float cx, final float cy, final float cz, final Vector2f size, final Vector3f point)
    {
        return this.isExactlyIn(cx, cy, cz, size.x, size.y, point.x, point.y, point.z);
    }

    /**
     * Returns true if point is exacly in area. (it can't be on border)
     *
     * @param cx    x center coordinates of area.
     * @param cy    y center coordinates of area.
     * @param cz    z center coordinates of area.
     * @param size  size of area in all 3 axis.
     * @param point point to be checked if it is inside of given area.
     *
     * @return true if point is exacly in area. (it can't be on border)
     */
    public boolean isExactlyIn(final float cx, final float cy, final float cz, final float size, final Vector3f point)
    {
        return this.isExactlyIn(cx, cy, cz, size, point.x, point.y, point.z);
    }

    /**
     * Returns true if point is exacly in area. (it can't be on border)
     *
     * @param center center point of area.
     * @param size   size of area in all 3 axis.
     * @param px     x coordinates of point to check.
     * @param py     y coordinates of point to check.
     * @param pz     z coordinates of point to check.
     *
     * @return true if point is exacly in area. (it can't be on border)
     */
    public boolean isExactlyIn(final Vector3f center, final Vector3f size, final float px, final float py, final float pz)
    {
        return this.isExactlyIn(center.x, center.y, center.z, size.x, size.y, size.z, px, py, pz);
    }

    /**
     * Returns true if point is exacly in area. (it can't be on border)
     *
     * @param center center point of area.
     * @param sx     size of area in x axis.
     * @param sy     size of area in y axis.
     * @param sz     size of area in z axis.
     * @param px     x coordinates of point to check.
     * @param py     y coordinates of point to check.
     * @param pz     z coordinates of point to check.
     *
     * @return true if point is exacly in area. (it can't be on border)
     */
    public boolean isExactlyIn(final Vector3f center, final float sx, final float sy, final float sz, final float px, final float py, final float pz)
    {
        return this.isExactlyIn(center.x, center.y, center.z, sx, sy, sz, px, py, pz);
    }

    /**
     * Returns true if point is exacly in area. (it can't be on border)
     *
     * @param center center point of area.
     * @param size   size of area in all 3 axis.
     * @param px     x coordinates of point to check.
     * @param py     y coordinates of point to check.
     * @param pz     z coordinates of point to check.
     *
     * @return true if point is exacly in area. (it can't be on border)
     */
    public boolean isExactlyIn(final Vector3f center, final Vector2f size, final float px, final float py, final float pz)
    {
        return this.isExactlyIn(center.x, center.y, center.z, size.x, size.y, px, py, pz);
    }

    /**
     * Returns true if point is exacly in area. (it can't be on border)
     *
     * @param center center point of area.
     * @param sxz    size of area in xz axis.
     * @param sy     size of area in y axis.
     * @param px     x coordinates of point to check.
     * @param py     y coordinates of point to check.
     * @param pz     z coordinates of point to check.
     *
     * @return true if point is exacly in area. (it can't be on border)
     */
    public boolean isExactlyIn(final Vector3f center, final float sxz, final float sy, final float px, final float py, final float pz)
    {
        return this.isExactlyIn(center.x, center.y, center.z, sxz, sy, px, py, pz);
    }

    /**
     * Returns true if point is exacly in area. (it can't be on border)
     *
     * @param center center point of area.
     * @param size   size of area in all 3 axis.
     * @param px     x coordinates of point to check.
     * @param py     y coordinates of point to check.
     * @param pz     z coordinates of point to check.
     *
     * @return true if point is exacly in area. (it can't be on border)
     */
    public boolean isExactlyIn(final Vector3f center, final float size, final float px, final float py, final float pz)
    {
        return this.isExactlyIn(center.x, center.y, center.z, size, px, py, pz);
    }

    /**
     * Returns true if point is exacly in area. (it can't be on border)
     *
     * @param cx   x center coordinates of area.
     * @param cy   y center coordinates of area.
     * @param cz   z center coordinates of area.
     * @param size size of area in all 3 axis.
     * @param px   x coordinates of point to check.
     * @param py   y coordinates of point to check.
     * @param pz   z coordinates of point to check.
     *
     * @return true if point is exacly in area. (it can't be on border)
     */
    public boolean isExactlyIn(final float cx, final float cy, final float cz, final Vector3f size, final float px, final float py, final float pz)
    {
        return this.isExactlyIn(cx, cy, cz, size.x, size.y, size.z, px, py, pz);
    }

    /**
     * Returns true if point is exacly in area. (it can't be on border)
     *
     * @param cx   x center coordinates of area.
     * @param cy   y center coordinates of area.
     * @param cz   z center coordinates of area.
     * @param size size of area in all 3 axis.
     * @param px   x coordinates of point to check.
     * @param py   y coordinates of point to check.
     * @param pz   z coordinates of point to check.
     *
     * @return true if point is exacly in area. (it can't be on border)
     */
    public boolean isExactlyIn(final float cx, final float cy, final float cz, final Vector2f size, final float px, final float py, final float pz)
    {
        return this.isExactlyIn(cx, cy, cz, size.x, size.y, px, py, pz);
    }

    /**
     * Returns true if point is exacly in area. (it can't be on border)
     *
     * @param cx   x center coordinates of area.
     * @param cy   y center coordinates of area.
     * @param cz   z center coordinates of area.
     * @param size size of area in all 3 axis.
     * @param px   x coordinates of point to check.
     * @param py   y coordinates of point to check.
     * @param pz   z coordinates of point to check.
     *
     * @return true if point is exacly in area. (it can't be on border)
     */
    public abstract boolean isExactlyIn(float cx, float cy, float cz, float size, float px, float py, float pz);

    /**
     * Returns true if point is exacly in area. (it can't be on border)
     *
     * @param cx  x center coordinates of area.
     * @param cy  y center coordinates of area.
     * @param cz  z center coordinates of area.
     * @param sxz size of area in xz axis.
     * @param sy  size of area in y axis.
     * @param px  x coordinates of point to check.
     * @param py  y coordinates of point to check.
     * @param pz  z coordinates of point to check.
     *
     * @return true if point is exacly in area. (it can't be on border)
     */
    public abstract boolean isExactlyIn(float cx, float cy, float cz, float sxz, float sy, float px, float py, float pz);

    /**
     * Returns true if point is exacly in area. (it can't be on border)
     *
     * @param cx x center coordinates of area.
     * @param cy y center coordinates of area.
     * @param cz z center coordinates of area.
     * @param sx size of area in x axis.
     * @param sy size of area in y axis.
     * @param sz size of area in z axis.
     * @param px x coordinates of point to check.
     * @param py y coordinates of point to check.
     * @param pz z coordinates of point to check.
     *
     * @return true if point is exacly in area. (it can't be on border)
     */
    public abstract boolean isExactlyIn(float cx, float cy, float cz, float sx, float sy, float sz, float px, float py, float pz);

    /**
     * Returns true if point is exacly in area. (it can't be on border)
     *
     * @param cx   x center coordinates of area.
     * @param cy   y center coordinates of area.
     * @param cz   z center coordinates of area.
     * @param size size of area in all 3 axis.
     * @param px   x coordinates of point to check.
     * @param py   y coordinates of point to check.
     * @param pz   z coordinates of point to check.
     *
     * @return true if point is exacly in area. (it can't be on border)
     */
    public abstract boolean isExactlyIn(int cx, int cy, int cz, int size, int px, int py, int pz);

    /**
     * Returns true if point is exacly in area. (it can't be on border)
     *
     * @param cx  x center coordinates of area.
     * @param cy  y center coordinates of area.
     * @param cz  z center coordinates of area.
     * @param sxz size of area in xz axis.
     * @param sy  size of area in y axis.
     * @param px  x coordinates of point to check.
     * @param py  y coordinates of point to check.
     * @param pz  z coordinates of point to check.
     *
     * @return true if point is exacly in area. (it can't be on border)
     */
    public abstract boolean isExactlyIn(int cx, int cy, int cz, int sxz, int sy, int px, int py, int pz);

    /**
     * Returns true if point is exacly in area. (it can't be on border)
     *
     * @param cx x center coordinates of area.
     * @param cy y center coordinates of area.
     * @param cz z center coordinates of area.
     * @param sx size of area in x axis.
     * @param sy size of area in y axis.
     * @param sz size of area in z axis.
     * @param px x coordinates of point to check.
     * @param py y coordinates of point to check.
     * @param pz z coordinates of point to check.
     *
     * @return true if point is exacly in area. (it can't be on border)
     */
    public abstract boolean isExactlyIn(int cx, int cy, int cz, int sx, int sy, int sz, int px, int py, int pz);

    /**
     * Returns true if point is exacly on area border.
     *
     * @param center center point of area.
     * @param size   size of area in all 3 axis.
     * @param point  point to be checked if it is inside of given area.
     *
     * @return true if point is exacly on area border.
     */
    public boolean isExactlyOn(final Vector3d center, final Vector3d size, final Vector3d point)
    {
        return this.isExactlyOn(center.x, center.y, center.z, size.x, size.y, size.z, point.z, point.y, point.z);
    }

    /**
     * Returns true if point is exacly on area border.
     *
     * @param center center point of area.
     * @param sx     size of area in x axis.
     * @param sy     size of area in y axis.
     * @param sz     size of area in z axis.
     * @param point  point to be checked if it is inside of given area.
     *
     * @return true if point is exacly on area border.
     */
    public boolean isExactlyOn(final Vector3d center, final double sx, final double sy, final double sz, final Vector3d point)
    {
        return this.isExactlyOn(center.x, center.y, center.z, sx, sy, sz, point.x, point.y, point.z);
    }

    /**
     * Returns true if point is exacly on area border.
     *
     * @param center center point of area.
     * @param size   size of area in all 3 axis.
     * @param point  point to be checked if it is inside of given area.
     *
     * @return true if point is exacly on area border.
     */
    public boolean isExactlyOn(final Vector3d center, final Vector2d size, final Vector3d point)
    {
        return this.isExactlyOn(center.x, center.y, center.z, size.x, size.y, point.x, point.y, point.z);
    }

    /**
     * Returns true if point is exacly on area border.
     *
     * @param center center point of area.
     * @param sxz    size of area in xz axis.
     * @param sy     size of area in y axis.
     * @param point  point to be checked if it is inside of given area.
     *
     * @return true if point is exacly on area border.
     */
    public boolean isExactlyOn(final Vector3d center, final double sxz, final double sy, final Vector3d point)
    {
        return this.isExactlyOn(center.x, center.y, center.z, sxz, sy, point.x, point.y, point.z);
    }

    /**
     * Returns true if point is exacly on area border.
     *
     * @param center center point of area.
     * @param size   size of area in all 3 axis.
     * @param point  point to be checked if it is inside of given area.
     *
     * @return true if point is exacly on area border.
     */
    public boolean isExactlyOn(final Vector3d center, final double size, final Vector3d point)
    {
        return this.isExactlyOn(center.x, center.y, center.z, size, point.x, point.y, point.z);
    }

    /**
     * Returns true if point is exacly on area border.
     *
     * @param cx    x center coordinates of area.
     * @param cy    y center coordinates of area.
     * @param cz    z center coordinates of area.
     * @param size  size of area in all 3 axis.
     * @param point point to be checked if it is inside of given area.
     *
     * @return true if point is exacly on area border.
     */
    public boolean isExactlyOn(final double cx, final double cy, final double cz, final Vector3d size, final Vector3d point)
    {
        return this.isExactlyOn(cx, cy, cz, size.x, size.y, size.z, point.x, point.y, point.z);
    }

    /**
     * Returns true if point is exacly on area border.
     *
     * @param cx    x center coordinates of area.
     * @param cy    y center coordinates of area.
     * @param cz    z center coordinates of area.
     * @param size  size of area in all 3 axis.
     * @param point point to be checked if it is inside of given area.
     *
     * @return true if point is exacly on area border.
     */
    public boolean isExactlyOn(final double cx, final double cy, final double cz, final Vector2d size, final Vector3d point)
    {
        return this.isExactlyOn(cx, cy, cz, size.x, size.y, point.x, point.y, point.z);
    }

    /**
     * Returns true if point is exacly on area border.
     *
     * @param cx    x center coordinates of area.
     * @param cy    y center coordinates of area.
     * @param cz    z center coordinates of area.
     * @param size  size of area in all 3 axis.
     * @param point point to be checked if it is inside of given area.
     *
     * @return true if point is exacly on area border.
     */
    public boolean isExactlyOn(final double cx, final double cy, final double cz, final double size, final Vector3d point)
    {
        return this.isExactlyOn(cx, cy, cz, size, point.x, point.y, point.z);
    }

    /**
     * Returns true if point is exacly on area border.
     *
     * @param center center point of area.
     * @param size   size of area in all 3 axis.
     * @param px     x coordinates of point to check.
     * @param py     y coordinates of point to check.
     * @param pz     z coordinates of point to check.
     *
     * @return true if point is exacly on area border.
     */
    public boolean isExactlyOn(final Vector3d center, final Vector3d size, final double px, final double py, final double pz)
    {
        return this.isExactlyOn(center.x, center.y, center.z, size.x, size.y, size.z, px, py, pz);
    }

    /**
     * Returns true if point is exacly on area border.
     *
     * @param center center point of area.
     * @param sx     size of area in x axis.
     * @param sy     size of area in y axis.
     * @param sz     size of area in z axis.
     * @param px     x coordinates of point to check.
     * @param py     y coordinates of point to check.
     * @param pz     z coordinates of point to check.
     *
     * @return true if point is exacly on area border.
     */
    public boolean isExactlyOn(final Vector3d center, final double sx, final double sy, final double sz, final double px, final double py, final double pz)
    {
        return this.isExactlyOn(center.x, center.y, center.z, sx, sy, sz, px, py, pz);
    }

    /**
     * Returns true if point is exacly on area border.
     *
     * @param center center point of area.
     * @param size   size of area in all 3 axis.
     * @param px     x coordinates of point to check.
     * @param py     y coordinates of point to check.
     * @param pz     z coordinates of point to check.
     *
     * @return true if point is exacly on area border.
     */
    public boolean isExactlyOn(final Vector3d center, final Vector2d size, final double px, final double py, final double pz)
    {
        return this.isExactlyOn(center.x, center.y, center.z, size.x, size.y, px, py, pz);
    }

    /**
     * Returns true if point is exacly on area border.
     *
     * @param center center point of area.
     * @param sxz    size of area in xz axis.
     * @param sy     size of area in y axis.
     * @param px     x coordinates of point to check.
     * @param py     y coordinates of point to check.
     * @param pz     z coordinates of point to check.
     *
     * @return true if point is exacly on area border.
     */
    public boolean isExactlyOn(final Vector3d center, final double sxz, final double sy, final double px, final double py, final double pz)
    {
        return this.isExactlyOn(center.x, center.y, center.z, sxz, sy, px, py, pz);
    }

    /**
     * Returns true if point is exacly on area border.
     *
     * @param center center point of area.
     * @param size   size of area in all 3 axis.
     * @param px     x coordinates of point to check.
     * @param py     y coordinates of point to check.
     * @param pz     z coordinates of point to check.
     *
     * @return true if point is exacly on area border.
     */
    public boolean isExactlyOn(final Vector3d center, final double size, final double px, final double py, final double pz)
    {
        return this.isExactlyOn(center.x, center.y, center.z, size, px, py, pz);
    }

    /**
     * Returns true if point is exacly on area border.
     *
     * @param cx   x center coordinates of area.
     * @param cy   y center coordinates of area.
     * @param cz   z center coordinates of area.
     * @param size size of area in all 3 axis.
     * @param px   x coordinates of point to check.
     * @param py   y coordinates of point to check.
     * @param pz   z coordinates of point to check.
     *
     * @return true if point is exacly on area border.
     */
    public boolean isExactlyOn(final double cx, final double cy, final double cz, final Vector3d size, final double px, final double py, final double pz)
    {
        return this.isExactlyOn(cx, cy, cz, size.x, size.y, size.z, px, py, pz);
    }

    /**
     * Returns true if point is exacly on area border.
     *
     * @param cx   x center coordinates of area.
     * @param cy   y center coordinates of area.
     * @param cz   z center coordinates of area.
     * @param size size of area in all 3 axis.
     * @param px   x coordinates of point to check.
     * @param py   y coordinates of point to check.
     * @param pz   z coordinates of point to check.
     *
     * @return true if point is exacly on area border.
     */
    public boolean isExactlyOn(final double cx, final double cy, final double cz, final Vector2d size, final double px, final double py, final double pz)
    {
        return this.isExactlyOn(cx, cy, cz, size.x, size.y, px, py, pz);
    }

    /**
     * Returns true if point is exacly on area border.
     *
     * @param cx   x center coordinates of area.
     * @param cy   y center coordinates of area.
     * @param cz   z center coordinates of area.
     * @param size size of area in all 3 axis.
     * @param px   x coordinates of point to check.
     * @param py   y coordinates of point to check.
     * @param pz   z coordinates of point to check.
     *
     * @return true if point is exacly on area border.
     */
    public abstract boolean isExactlyOn(double cx, double cy, double cz, double size, double px, double py, double pz);

    /**
     * Returns true if point is exacly on area border.
     *
     * @param cx  x center coordinates of area.
     * @param cy  y center coordinates of area.
     * @param cz  z center coordinates of area.
     * @param sxz size of area in xz axis.
     * @param sy  size of area in y axis.
     * @param px  x coordinates of point to check.
     * @param py  y coordinates of point to check.
     * @param pz  z coordinates of point to check.
     *
     * @return true if point is exacly on area border.
     */
    public abstract boolean isExactlyOn(double cx, double cy, double cz, double sxz, double sy, double px, double py, double pz);

    /**
     * Returns true if point is exacly on area border.
     *
     * @param cx x center coordinates of area.
     * @param cy y center coordinates of area.
     * @param cz z center coordinates of area.
     * @param sx size of area in x axis.
     * @param sy size of area in y axis.
     * @param sz size of area in z axis.
     * @param px x coordinates of point to check.
     * @param py y coordinates of point to check.
     * @param pz z coordinates of point to check.
     *
     * @return true if point is exacly on area border.
     */
    public abstract boolean isExactlyOn(double cx, double cy, double cz, double sx, double sy, double sz, double px, double py, double pz);

    /**
     * Returns true if point is exacly on area border.
     *
     * @param cx   x center coordinates of area.
     * @param cy   y center coordinates of area.
     * @param cz   z center coordinates of area.
     * @param size size of area in all 3 axis.
     * @param px   x coordinates of point to check.
     * @param py   y coordinates of point to check.
     * @param pz   z coordinates of point to check.
     *
     * @return true if point is exacly on area border.
     */
    public abstract boolean isExactlyOn(long cx, long cy, long cz, long size, long px, long py, long pz);

    /**
     * Returns true if point is exacly on area border.
     *
     * @param cx  x center coordinates of area.
     * @param cy  y center coordinates of area.
     * @param cz  z center coordinates of area.
     * @param sxz size of area in xz axis.
     * @param sy  size of area in y axis.
     * @param px  x coordinates of point to check.
     * @param py  y coordinates of point to check.
     * @param pz  z coordinates of point to check.
     *
     * @return true if point is exacly on area border.
     */
    public abstract boolean isExactlyOn(long cx, long cy, long cz, long sxz, long sy, long px, long py, long pz);

    /**
     * Returns true if point is exacly on area border.
     *
     * @param cx x center coordinates of area.
     * @param cy y center coordinates of area.
     * @param cz z center coordinates of area.
     * @param sx size of area in x axis.
     * @param sy size of area in y axis.
     * @param sz size of area in z axis.
     * @param px x coordinates of point to check.
     * @param py y coordinates of point to check.
     * @param pz z coordinates of point to check.
     *
     * @return true if point is exacly on area border.
     */
    public abstract boolean isExactlyOn(long cx, long cy, long cz, long sx, long sy, long sz, long px, long py, long pz);

    /**
     * Returns true if point is exacly on area border.
     *
     * @param center center point of area.
     * @param size   size of area in all 3 axis.
     * @param point  point to be checked if it is inside of given area.
     *
     * @return true if point is exacly on area border.
     */
    public boolean isExactlyOn(final Vector3f center, final Vector3f size, final Vector3f point)
    {
        return this.isExactlyOn(center.x, center.y, center.z, size.x, size.y, size.z, point.z, point.y, point.z);
    }

    /**
     * Returns true if point is exacly on area border.
     *
     * @param center center point of area.
     * @param sx     size of area in x axis.
     * @param sy     size of area in y axis.
     * @param sz     size of area in z axis.
     * @param point  point to be checked if it is inside of given area.
     *
     * @return true if point is exacly on area border.
     */
    public boolean isExactlyOn(final Vector3f center, final float sx, final float sy, final float sz, final Vector3f point)
    {
        return this.isExactlyOn(center.x, center.y, center.z, sx, sy, sz, point.x, point.y, point.z);
    }

    /**
     * Returns true if point is exacly on area border.
     *
     * @param center center point of area.
     * @param size   size of area in all 3 axis.
     * @param point  point to be checked if it is inside of given area.
     *
     * @return true if point is exacly on area border.
     */
    public boolean isExactlyOn(final Vector3f center, final Vector2f size, final Vector3f point)
    {
        return this.isExactlyOn(center.x, center.y, center.z, size.x, size.y, point.x, point.y, point.z);
    }

    /**
     * Returns true if point is exacly on area border.
     *
     * @param center center point of area.
     * @param sxz    size of area in xz axis.
     * @param sy     size of area in y axis.
     * @param point  point to be checked if it is inside of given area.
     *
     * @return true if point is exacly on area border.
     */
    public boolean isExactlyOn(final Vector3f center, final float sxz, final float sy, final Vector3f point)
    {
        return this.isExactlyOn(center.x, center.y, center.z, sxz, sy, point.x, point.y, point.z);
    }

    /**
     * Returns true if point is exacly on area border.
     *
     * @param center center point of area.
     * @param size   size of area in all 3 axis.
     * @param point  point to be checked if it is inside of given area.
     *
     * @return true if point is exacly on area border.
     */
    public boolean isExactlyOn(final Vector3f center, final float size, final Vector3f point)
    {
        return this.isExactlyOn(center.x, center.y, center.z, size, point.x, point.y, point.z);
    }

    /**
     * Returns true if point is exacly on area border.
     *
     * @param cx    x center coordinates of area.
     * @param cy    y center coordinates of area.
     * @param cz    z center coordinates of area.
     * @param size  size of area in all 3 axis.
     * @param point point to be checked if it is inside of given area.
     *
     * @return true if point is exacly on area border.
     */
    public boolean isExactlyOn(final float cx, final float cy, final float cz, final Vector3f size, final Vector3f point)
    {
        return this.isExactlyOn(cx, cy, cz, size.x, size.y, size.z, point.x, point.y, point.z);
    }

    /**
     * Returns true if point is exacly on area border.
     *
     * @param cx    x center coordinates of area.
     * @param cy    y center coordinates of area.
     * @param cz    z center coordinates of area.
     * @param size  size of area in all 3 axis.
     * @param point point to be checked if it is inside of given area.
     *
     * @return true if point is exacly on area border.
     */
    public boolean isExactlyOn(final float cx, final float cy, final float cz, final Vector2f size, final Vector3f point)
    {
        return this.isExactlyOn(cx, cy, cz, size.x, size.y, point.x, point.y, point.z);
    }

    /**
     * Returns true if point is exacly on area border.
     *
     * @param cx    x center coordinates of area.
     * @param cy    y center coordinates of area.
     * @param cz    z center coordinates of area.
     * @param size  size of area in all 3 axis.
     * @param point point to be checked if it is inside of given area.
     *
     * @return true if point is exacly on area border.
     */
    public boolean isExactlyOn(final float cx, final float cy, final float cz, final float size, final Vector3f point)
    {
        return this.isExactlyOn(cx, cy, cz, size, point.x, point.y, point.z);
    }

    /**
     * Returns true if point is exacly on area border.
     *
     * @param center center point of area.
     * @param size   size of area in all 3 axis.
     * @param px     x coordinates of point to check.
     * @param py     y coordinates of point to check.
     * @param pz     z coordinates of point to check.
     *
     * @return true if point is exacly on area border.
     */
    public boolean isExactlyOn(final Vector3f center, final Vector3f size, final float px, final float py, final float pz)
    {
        return this.isExactlyOn(center.x, center.y, center.z, size.x, size.y, size.z, px, py, pz);
    }

    /**
     * Returns true if point is exacly on area border.
     *
     * @param center center point of area.
     * @param sx     size of area in x axis.
     * @param sy     size of area in y axis.
     * @param sz     size of area in z axis.
     * @param px     x coordinates of point to check.
     * @param py     y coordinates of point to check.
     * @param pz     z coordinates of point to check.
     *
     * @return true if point is exacly on area border.
     */
    public boolean isExactlyOn(final Vector3f center, final float sx, final float sy, final float sz, final float px, final float py, final float pz)
    {
        return this.isExactlyOn(center.x, center.y, center.z, sx, sy, sz, px, py, pz);
    }

    /**
     * Returns true if point is exacly on area border.
     *
     * @param center center point of area.
     * @param size   size of area in all 3 axis.
     * @param px     x coordinates of point to check.
     * @param py     y coordinates of point to check.
     * @param pz     z coordinates of point to check.
     *
     * @return true if point is exacly on area border.
     */
    public boolean isExactlyOn(final Vector3f center, final Vector2f size, final float px, final float py, final float pz)
    {
        return this.isExactlyOn(center.x, center.y, center.z, size.x, size.y, px, py, pz);
    }

    /**
     * Returns true if point is exacly on area border.
     *
     * @param center center point of area.
     * @param sxz    size of area in xz axis.
     * @param sy     size of area in y axis.
     * @param px     x coordinates of point to check.
     * @param py     y coordinates of point to check.
     * @param pz     z coordinates of point to check.
     *
     * @return true if point is exacly on area border.
     */
    public boolean isExactlyOn(final Vector3f center, final float sxz, final float sy, final float px, final float py, final float pz)
    {
        return this.isExactlyOn(center.x, center.y, center.z, sxz, sy, px, py, pz);
    }

    /**
     * Returns true if point is exacly on area border.
     *
     * @param center center point of area.
     * @param size   size of area in all 3 axis.
     * @param px     x coordinates of point to check.
     * @param py     y coordinates of point to check.
     * @param pz     z coordinates of point to check.
     *
     * @return true if point is exacly on area border.
     */
    public boolean isExactlyOn(final Vector3f center, final float size, final float px, final float py, final float pz)
    {
        return this.isExactlyOn(center.x, center.y, center.z, size, px, py, pz);
    }

    /**
     * Returns true if point is exacly on area border.
     *
     * @param cx   x center coordinates of area.
     * @param cy   y center coordinates of area.
     * @param cz   z center coordinates of area.
     * @param size size of area in all 3 axis.
     * @param px   x coordinates of point to check.
     * @param py   y coordinates of point to check.
     * @param pz   z coordinates of point to check.
     *
     * @return true if point is exacly on area border.
     */
    public boolean isExactlyOn(final float cx, final float cy, final float cz, final Vector3f size, final float px, final float py, final float pz)
    {
        return this.isExactlyOn(cx, cy, cz, size.x, size.y, size.z, px, py, pz);
    }

    /**
     * Returns true if point is exacly on area border.
     *
     * @param cx   x center coordinates of area.
     * @param cy   y center coordinates of area.
     * @param cz   z center coordinates of area.
     * @param size size of area in all 3 axis.
     * @param px   x coordinates of point to check.
     * @param py   y coordinates of point to check.
     * @param pz   z coordinates of point to check.
     *
     * @return true if point is exacly on area border.
     */
    public boolean isExactlyOn(final float cx, final float cy, final float cz, final Vector2f size, final float px, final float py, final float pz)
    {
        return this.isExactlyOn(cx, cy, cz, size.x, size.y, px, py, pz);
    }

    /**
     * Returns true if point is exacly on area border.
     *
     * @param cx   x center coordinates of area.
     * @param cy   y center coordinates of area.
     * @param cz   z center coordinates of area.
     * @param size size of area in all 3 axis.
     * @param px   x coordinates of point to check.
     * @param py   y coordinates of point to check.
     * @param pz   z coordinates of point to check.
     *
     * @return true if point is exacly on area border.
     */
    public abstract boolean isExactlyOn(float cx, float cy, float cz, float size, float px, float py, float pz);

    /**
     * Returns true if point is exacly on area border.
     *
     * @param cx  x center coordinates of area.
     * @param cy  y center coordinates of area.
     * @param cz  z center coordinates of area.
     * @param sxz size of area in xz axis.
     * @param sy  size of area in y axis.
     * @param px  x coordinates of point to check.
     * @param py  y coordinates of point to check.
     * @param pz  z coordinates of point to check.
     *
     * @return true if point is exacly on area border.
     */
    public abstract boolean isExactlyOn(float cx, float cy, float cz, float sxz, float sy, float px, float py, float pz);

    /**
     * Returns true if point is exacly on area border.
     *
     * @param cx x center coordinates of area.
     * @param cy y center coordinates of area.
     * @param cz z center coordinates of area.
     * @param sx size of area in x axis.
     * @param sy size of area in y axis.
     * @param sz size of area in z axis.
     * @param px x coordinates of point to check.
     * @param py y coordinates of point to check.
     * @param pz z coordinates of point to check.
     *
     * @return true if point is exacly on area border.
     */
    public abstract boolean isExactlyOn(float cx, float cy, float cz, float sx, float sy, float sz, float px, float py, float pz);

    /**
     * Returns true if point is exacly on area border.
     *
     * @param cx   x center coordinates of area.
     * @param cy   y center coordinates of area.
     * @param cz   z center coordinates of area.
     * @param size size of area in all 3 axis.
     * @param px   x coordinates of point to check.
     * @param py   y coordinates of point to check.
     * @param pz   z coordinates of point to check.
     *
     * @return true if point is exacly on area border.
     */
    public abstract boolean isExactlyOn(int cx, int cy, int cz, int size, int px, int py, int pz);

    /**
     * Returns true if point is exacly on area border.
     *
     * @param cx  x center coordinates of area.
     * @param cy  y center coordinates of area.
     * @param cz  z center coordinates of area.
     * @param sxz size of area in xz axis.
     * @param sy  size of area in y axis.
     * @param px  x coordinates of point to check.
     * @param py  y coordinates of point to check.
     * @param pz  z coordinates of point to check.
     *
     * @return true if point is exacly on area border.
     */
    public abstract boolean isExactlyOn(int cx, int cy, int cz, int sxz, int sy, int px, int py, int pz);

    /**
     * Returns true if point is exacly on area border.
     *
     * @param cx x center coordinates of area.
     * @param cy y center coordinates of area.
     * @param cz z center coordinates of area.
     * @param sx size of area in x axis.
     * @param sy size of area in y axis.
     * @param sz size of area in z axis.
     * @param px x coordinates of point to check.
     * @param py y coordinates of point to check.
     * @param pz z coordinates of point to check.
     *
     * @return true if point is exacly on area border.
     */
    public abstract boolean isExactlyOn(int cx, int cy, int cz, int sx, int sy, int sz, int px, int py, int pz);

    /**
     * Possible result of checks.
     */
    public enum Result
    {
        /**
         * Point is inside of area.
         */
        IN,
        /**
         * Point is on border of area.
         */
        ON,
        /**
         * Point is outside of area.
         */
        OUT
    }

    private static Result getResult(final double d)
    {
        if (d > 0)
        {
            return Result.OUT;
        }
        if (d == 0)
        {
            return Result.ON;
        }
        return Result.IN;
    }

    private static Result getResult(final float f)
    {
        if (f > 0)
        {
            return Result.OUT;
        }
        if (f == 0)
        {
            return Result.ON;
        }
        return Result.IN;
    }

    private static Result getResult(final int i)
    {
        if (i > 0)
        {
            return Result.OUT;
        }
        if (i == 0)
        {
            return Result.ON;
        }
        return Result.IN;
    }

    private static Result getResult(final long i)
    {
        if (i > 0)
        {
            return Result.OUT;
        }
        if (i == 0)
        {
            return Result.ON;
        }
        return Result.IN;
    }
}
