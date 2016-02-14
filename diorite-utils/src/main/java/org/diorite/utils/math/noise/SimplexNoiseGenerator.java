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

package org.diorite.utils.math.noise;

import java.util.Random;


/**
 * Generates simplex-based noise.
 * <br>
 * From Bukkit project https://github.com/Bukkit/Bukkit
 * <br>
 * Source:
 * Stefan Gustavson at
 * <a href="http://staffwww.itn.liu.se/~stegu/simplexnoise/simplexnoise.pdf">
 * http://staffwww.itn.liu.se/~stegu/simplexnoise/simplexnoise.pdf</a>
 */
@SuppressWarnings("MagicNumber")
public class SimplexNoiseGenerator extends PerlinNoiseGenerator
{
    protected static final double                SQRT_3   = Math.sqrt(3);
    protected static final double                SQRT_5   = Math.sqrt(5);
    protected static final double                F2       = 0.5 * (SQRT_3 - 1);
    protected static final double                G2       = (3 - SQRT_3) / 6;
    protected static final double                G22      = (G2 * 2.0) - 1;
    protected static final double                F3       = 1.0 / 3.0;
    protected static final double                G3       = 1.0 / 6.0;
    protected static final double                F4       = (SQRT_5 - 1.0) / 4.0;
    protected static final double                G4       = (5.0 - SQRT_5) / 20.0;
    protected static final double                G42      = G4 * 2.0;
    protected static final double                G43      = G4 * 3.0;
    protected static final double                G44      = (G4 * 4.0) - 1.0;
    protected static final int[][]               grad4    = {{0, 1, 1, 1}, {0, 1, 1, - 1}, {0, 1, - 1, 1}, {0, 1, - 1, - 1}, {0, - 1, 1, 1}, {0, - 1, 1, - 1}, {0, - 1, - 1, 1}, {0, - 1, - 1, - 1}, {1, 0, 1, 1}, {1, 0, 1, - 1}, {1, 0, - 1, 1}, {1, 0, - 1, - 1}, {- 1, 0, 1, 1}, {- 1, 0, 1, - 1}, {- 1, 0, - 1, 1}, {- 1, 0, - 1, - 1}, {1, 1, 0, 1}, {1, 1, 0, - 1}, {1, - 1, 0, 1}, {1, - 1, 0, - 1}, {- 1, 1, 0, 1}, {- 1, 1, 0, - 1}, {- 1, - 1, 0, 1}, {- 1, - 1, 0, - 1}, {1, 1, 1, 0}, {1, 1, - 1, 0}, {1, - 1, 1, 0}, {1, - 1, - 1, 0}, {- 1, 1, 1, 0}, {- 1, 1, - 1, 0}, {- 1, - 1, 1, 0}, {- 1, - 1, - 1, 0}};
    protected static final int[][]               simplex  = {{0, 1, 2, 3}, {0, 1, 3, 2}, {0, 0, 0, 0}, {0, 2, 3, 1}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {1, 2, 3, 0}, {0, 2, 1, 3}, {0, 0, 0, 0}, {0, 3, 1, 2}, {0, 3, 2, 1}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {1, 3, 2, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {1, 2, 0, 3}, {0, 0, 0, 0}, {1, 3, 0, 2}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {2, 3, 0, 1}, {2, 3, 1, 0}, {1, 0, 2, 3}, {1, 0, 3, 2}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {2, 0, 3, 1}, {0, 0, 0, 0}, {2, 1, 3, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {2, 0, 1, 3}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {3, 0, 1, 2}, {3, 0, 2, 1}, {0, 0, 0, 0}, {3, 1, 2, 0}, {2, 1, 0, 3}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {3, 1, 0, 2}, {0, 0, 0, 0}, {3, 2, 0, 1}, {3, 2, 1, 0}};
    private static final   SimplexNoiseGenerator instance = new SimplexNoiseGenerator();
    protected static double offsetW;

    protected SimplexNoiseGenerator()
    {
        super();
    }

    /**
     * Creates a seeded simplex noise generator for the given seed
     *
     * @param seed Seed to construct this generator for
     */
    public SimplexNoiseGenerator(final long seed)
    {
        this(new Random(seed));
    }

    /**
     * Creates a seeded simplex noise generator with the given Random
     *
     * @param rand Random to construct with
     */
    public SimplexNoiseGenerator(final Random rand)
    {
        super(rand);
        offsetW = rand.nextDouble() * 256;
    }

    @Override
    public double noise(double xin, double yin, double zin)
    {
        xin += this.offsetX;
        yin += this.offsetY;
        zin += this.offsetZ;

        final double n0; // Noise contributions from the four corners
        final double n1;
        final double n2;
        final double n3;

        // Skew the input space to determine which simplex cell we're in
        final double s = (xin + yin + zin) * F3; // Very nice and simple skew factor for 3D
        final int i = floor(xin + s);
        final int j = floor(yin + s);
        final int k = floor(zin + s);
        final double t = (i + j + k) * G3;
        final double X0 = i - t; // Unskew the cell origin back to (x,y,z) space
        final double Y0 = j - t;
        final double Z0 = k - t;
        final double x0 = xin - X0; // The x,y,z distances from the cell origin
        final double y0 = yin - Y0;
        final double z0 = zin - Z0;

        // For the 3D case, the simplex shape is a slightly irregular tetrahedron.

        // Determine which simplex we are in.
        final int i1; // Offsets for second corner of simplex in (i,j,k) coords
        final int j1;
        final int k1;
        final int i2; // Offsets for third corner of simplex in (i,j,k) coords
        final int j2;
        final int k2;
        if (x0 >= y0)
        {
            if (y0 >= z0)
            {
                i1 = 1;
                j1 = 0;
                k1 = 0;
                i2 = 1;
                j2 = 1;
                k2 = 0;
            } // X Y Z order
            else if (x0 >= z0)
            {
                i1 = 1;
                j1 = 0;
                k1 = 0;
                i2 = 1;
                j2 = 0;
                k2 = 1;
            } // X Z Y order
            else
            {
                i1 = 0;
                j1 = 0;
                k1 = 1;
                i2 = 1;
                j2 = 0;
                k2 = 1;
            } // Z X Y order
        }
        else
        { // x0<y0
            if (y0 < z0)
            {
                i1 = 0;
                j1 = 0;
                k1 = 1;
                i2 = 0;
                j2 = 1;
                k2 = 1;
            } // Z Y X order
            else if (x0 < z0)
            {
                i1 = 0;
                j1 = 1;
                k1 = 0;
                i2 = 0;
                j2 = 1;
                k2 = 1;
            } // Y Z X order
            else
            {
                i1 = 0;
                j1 = 1;
                k1 = 0;
                i2 = 1;
                j2 = 1;
                k2 = 0;
            } // Y X Z order
        }

        // A step of (1,0,0) in (i,j,k) means a step of (1-c,-c,-c) in (x,y,z),
        // a step of (0,1,0) in (i,j,k) means a step of (-c,1-c,-c) in (x,y,z), and
        // a step of (0,0,1) in (i,j,k) means a step of (-c,-c,1-c) in (x,y,z), where
        // c = 1/6.
        final double x1 = (x0 - i1) + G3; // Offsets for second corner in (x,y,z) coords
        final double y1 = (y0 - j1) + G3;
        final double z1 = (z0 - k1) + G3;
        final double x2 = (x0 - i2) + (2.0 * G3); // Offsets for third corner in (x,y,z) coords
        final double y2 = (y0 - j2) + (2.0 * G3);
        final double z2 = (z0 - k2) + (2.0 * G3);
        final double x3 = (x0 - 1.0) + (3.0 * G3); // Offsets for last corner in (x,y,z) coords
        final double y3 = (y0 - 1.0) + (3.0 * G3);
        final double z3 = (z0 - 1.0) + (3.0 * G3);

        // Work out the hashed gradient indices of the four simplex corners
        final int ii = i & 255;
        final int jj = j & 255;
        final int kk = k & 255;
        final int gi0 = this.perm[ii + this.perm[jj + this.perm[kk]]] % 12;
        final int gi1 = this.perm[ii + i1 + this.perm[jj + j1 + this.perm[kk + k1]]] % 12;
        final int gi2 = this.perm[ii + i2 + this.perm[jj + j2 + this.perm[kk + k2]]] % 12;
        final int gi3 = this.perm[ii + 1 + this.perm[jj + 1 + this.perm[kk + 1]]] % 12;

        // Calculate the contribution from the four corners
        double t0 = 0.6 - (x0 * x0) - (y0 * y0) - (z0 * z0);
        if (t0 < 0)
        {
            n0 = 0.0;
        }
        else
        {
            t0 *= t0;
            n0 = t0 * t0 * dot(grad3[gi0], x0, y0, z0);
        }

        double t1 = 0.6 - (x1 * x1) - (y1 * y1) - (z1 * z1);
        if (t1 < 0)
        {
            n1 = 0.0;
        }
        else
        {
            t1 *= t1;
            n1 = t1 * t1 * dot(grad3[gi1], x1, y1, z1);
        }

        double t2 = 0.6 - (x2 * x2) - (y2 * y2) - (z2 * z2);
        if (t2 < 0)
        {
            n2 = 0.0;
        }
        else
        {
            t2 *= t2;
            n2 = t2 * t2 * dot(grad3[gi2], x2, y2, z2);
        }

        double t3 = 0.6 - (x3 * x3) - (y3 * y3) - (z3 * z3);
        if (t3 < 0)
        {
            n3 = 0.0;
        }
        else
        {
            t3 *= t3;
            n3 = t3 * t3 * dot(grad3[gi3], x3, y3, z3);
        }

        // Add contributions from each corner to get the final noise value.
        // The result is scaled to stay just inside [-1,1]
        return 32.0 * (n0 + n1 + n2 + n3);
    }

    @Override
    public double noise(double xin, double yin)
    {
        xin += this.offsetX;
        yin += this.offsetY;

        final double n0; // Noise contributions from the three corners
        final double n1;
        final double n2;

        // Skew the input space to determine which simplex cell we're in
        final double s = (xin + yin) * F2; // Hairy factor for 2D
        final int i = floor(xin + s);
        final int j = floor(yin + s);
        final double t = (i + j) * G2;
        final double X0 = i - t; // Unskew the cell origin back to (x,y) space
        final double Y0 = j - t;
        final double x0 = xin - X0; // The x,y distances from the cell origin
        final double y0 = yin - Y0;

        // For the 2D case, the simplex shape is an equilateral triangle.

        // Determine which simplex we are in.
        final int i1; // Offsets for second (middle) corner of simplex in (i,j) coords
        final int j1;
        if (x0 > y0)
        {
            i1 = 1;
            j1 = 0;
        } // lower triangle, XY order: (0,0)->(1,0)->(1,1)
        else
        {
            i1 = 0;
            j1 = 1;
        } // upper triangle, YX order: (0,0)->(0,1)->(1,1)

        // A step of (1,0) in (i,j) means a step of (1-c,-c) in (x,y), and
        // a step of (0,1) in (i,j) means a step of (-c,1-c) in (x,y), where
        // c = (3-sqrt(3))/6

        final double x1 = (x0 - i1) + G2; // Offsets for middle corner in (x,y) unskewed coords
        final double y1 = (y0 - j1) + G2;
        final double x2 = x0 + G22; // Offsets for last corner in (x,y) unskewed coords
        final double y2 = y0 + G22;

        // Work out the hashed gradient indices of the three simplex corners
        final int ii = i & 255;
        final int jj = j & 255;
        final int gi0 = this.perm[ii + this.perm[jj]] % 12;
        final int gi1 = this.perm[ii + i1 + this.perm[jj + j1]] % 12;
        final int gi2 = this.perm[ii + 1 + this.perm[jj + 1]] % 12;

        // Calculate the contribution from the three corners
        double t0 = 0.5 - (x0 * x0) - (y0 * y0);
        if (t0 < 0)
        {
            n0 = 0.0;
        }
        else
        {
            t0 *= t0;
            n0 = t0 * t0 * dot(grad3[gi0], x0, y0); // (x,y) of grad3 used for 2D gradient
        }

        double t1 = 0.5 - (x1 * x1) - (y1 * y1);
        if (t1 < 0)
        {
            n1 = 0.0;
        }
        else
        {
            t1 *= t1;
            n1 = t1 * t1 * dot(grad3[gi1], x1, y1);
        }

        double t2 = 0.5 - (x2 * x2) - (y2 * y2);
        if (t2 < 0)
        {
            n2 = 0.0;
        }
        else
        {
            t2 *= t2;
            n2 = t2 * t2 * dot(grad3[gi2], x2, y2);
        }

        // Add contributions from each corner to get the final noise value.
        // The result is scaled to return values in the interval [-1,1].
        return 70.0 * (n0 + n1 + n2);
    }

    /**
     * Computes and returns the 4D simplex noise for the given coordinates in
     * 4D space
     *
     * @param x X coordinate
     * @param y Y coordinate
     * @param z Z coordinate
     * @param w W coordinate
     *
     * @return Noise at given location, from range -1 to 1
     */
    public double noise(double x, double y, double z, double w)
    {
        x += this.offsetX;
        y += this.offsetY;
        z += this.offsetZ;
        w += offsetW;

        final double n0; // Noise contributions from the five corners
        final double n1;
        final double n2;
        final double n3;
        final double n4;

        // Skew the (x,y,z,w) space to determine which cell of 24 simplices we're in
        final double s = (x + y + z + w) * F4; // Factor for 4D skewing
        final int i = floor(x + s);
        final int j = floor(y + s);
        final int k = floor(z + s);
        final int l = floor(w + s);

        final double t = (i + j + k + l) * G4; // Factor for 4D unskewing
        final double X0 = i - t; // Unskew the cell origin back to (x,y,z,w) space
        final double Y0 = j - t;
        final double Z0 = k - t;
        final double W0 = l - t;
        final double x0 = x - X0; // The x,y,z,w distances from the cell origin
        final double y0 = y - Y0;
        final double z0 = z - Z0;
        final double w0 = w - W0;

        // For the 4D case, the simplex is a 4D shape I won't even try to describe.
        // To find out which of the 24 possible simplices we're in, we need to
        // determine the magnitude ordering of x0, y0, z0 and w0.
        // The method below is a good way of finding the ordering of x,y,z,w and
        // then find the correct traversal order for the simplex we’re in.
        // First, six pair-wise comparisons are performed between each possible pair
        // of the four coordinates, and the results are used to add up binary bits
        // for an integer index.
        final int c1 = (x0 > y0) ? 32 : 0;
        final int c2 = (x0 > z0) ? 16 : 0;
        final int c3 = (y0 > z0) ? 8 : 0;
        final int c4 = (x0 > w0) ? 4 : 0;
        final int c5 = (y0 > w0) ? 2 : 0;
        final int c6 = (z0 > w0) ? 1 : 0;
        final int c = c1 + c2 + c3 + c4 + c5 + c6;
        final int i1; // The integer offsets for the second simplex corner
        final int j1;
        final int k1;
        final int l1;
        final int i2; // The integer offsets for the third simplex corner
        final int j2;
        final int k2;
        final int l2;
        final int i3; // The integer offsets for the fourth simplex corner
        final int j3;
        final int k3;
        final int l3;

        // simplex[c] is a 4-vector with the numbers 0, 1, 2 and 3 in some order.
        // Many values of c will never occur, since e.g. x>y>z>w makes x<z, y<w and x<w
        // impossible. Only the 24 indices which have non-zero entries make any sense.
        // We use a thresholding to set the coordinates in turn from the largest magnitude.

        // The number 3 in the "simplex" array is at the position of the largest coordinate.
        i1 = (simplex[c][0] >= 3) ? 1 : 0;
        j1 = (simplex[c][1] >= 3) ? 1 : 0;
        k1 = (simplex[c][2] >= 3) ? 1 : 0;
        l1 = (simplex[c][3] >= 3) ? 1 : 0;

        // The number 2 in the "simplex" array is at the second largest coordinate.
        i2 = (simplex[c][0] >= 2) ? 1 : 0;
        j2 = (simplex[c][1] >= 2) ? 1 : 0;
        k2 = (simplex[c][2] >= 2) ? 1 : 0;
        l2 = (simplex[c][3] >= 2) ? 1 : 0;

        // The number 1 in the "simplex" array is at the second smallest coordinate.
        i3 = (simplex[c][0] >= 1) ? 1 : 0;
        j3 = (simplex[c][1] >= 1) ? 1 : 0;
        k3 = (simplex[c][2] >= 1) ? 1 : 0;
        l3 = (simplex[c][3] >= 1) ? 1 : 0;

        // The fifth corner has all coordinate offsets = 1, so no need to look that up.

        final double x1 = (x0 - i1) + G4; // Offsets for second corner in (x,y,z,w) coords
        final double y1 = (y0 - j1) + G4;
        final double z1 = (z0 - k1) + G4;
        final double w1 = (w0 - l1) + G4;

        final double x2 = (x0 - i2) + G42; // Offsets for third corner in (x,y,z,w) coords
        final double y2 = (y0 - j2) + G42;
        final double z2 = (z0 - k2) + G42;
        final double w2 = (w0 - l2) + G42;

        final double x3 = (x0 - i3) + G43; // Offsets for fourth corner in (x,y,z,w) coords
        final double y3 = (y0 - j3) + G43;
        final double z3 = (z0 - k3) + G43;
        final double w3 = (w0 - l3) + G43;

        final double x4 = x0 + G44; // Offsets for last corner in (x,y,z,w) coords
        final double y4 = y0 + G44;
        final double z4 = z0 + G44;
        final double w4 = w0 + G44;

        // Work out the hashed gradient indices of the five simplex corners
        final int ii = i & 255;
        final int jj = j & 255;
        final int kk = k & 255;
        final int ll = l & 255;

        final int gi0 = this.perm[ii + this.perm[jj + this.perm[kk + this.perm[ll]]]] % 32;
        final int gi1 = this.perm[ii + i1 + this.perm[jj + j1 + this.perm[kk + k1 + this.perm[ll + l1]]]] % 32;
        final int gi2 = this.perm[ii + i2 + this.perm[jj + j2 + this.perm[kk + k2 + this.perm[ll + l2]]]] % 32;
        final int gi3 = this.perm[ii + i3 + this.perm[jj + j3 + this.perm[kk + k3 + this.perm[ll + l3]]]] % 32;
        final int gi4 = this.perm[ii + 1 + this.perm[jj + 1 + this.perm[kk + 1 + this.perm[ll + 1]]]] % 32;

        // Calculate the contribution from the five corners
        double t0 = 0.6 - (x0 * x0) - (y0 * y0) - (z0 * z0) - (w0 * w0);
        if (t0 < 0)
        {
            n0 = 0.0;
        }
        else
        {
            t0 *= t0;
            n0 = t0 * t0 * dot(grad4[gi0], x0, y0, z0, w0);
        }

        double t1 = 0.6 - (x1 * x1) - (y1 * y1) - (z1 * z1) - (w1 * w1);
        if (t1 < 0)
        {
            n1 = 0.0;
        }
        else
        {
            t1 *= t1;
            n1 = t1 * t1 * dot(grad4[gi1], x1, y1, z1, w1);
        }

        double t2 = 0.6 - (x2 * x2) - (y2 * y2) - (z2 * z2) - (w2 * w2);
        if (t2 < 0)
        {
            n2 = 0.0;
        }
        else
        {
            t2 *= t2;
            n2 = t2 * t2 * dot(grad4[gi2], x2, y2, z2, w2);
        }

        double t3 = 0.6 - (x3 * x3) - (y3 * y3) - (z3 * z3) - (w3 * w3);
        if (t3 < 0)
        {
            n3 = 0.0;
        }
        else
        {
            t3 *= t3;
            n3 = t3 * t3 * dot(grad4[gi3], x3, y3, z3, w3);
        }

        double t4 = 0.6 - (x4 * x4) - (y4 * y4) - (z4 * z4) - (w4 * w4);
        if (t4 < 0)
        {
            n4 = 0.0;
        }
        else
        {
            t4 *= t4;
            n4 = t4 * t4 * dot(grad4[gi4], x4, y4, z4, w4);
        }

        // Sum up and scale the result to cover the range [-1,1]
        return 27.0 * (n0 + n1 + n2 + n3 + n4);
    }

    protected static double dot(final int[] g, final double x, final double y)
    {
        return (g[0] * x) + (g[1] * y);
    }

    protected static double dot(final int[] g, final double x, final double y, final double z)
    {
        return (g[0] * x) + (g[1] * y) + (g[2] * z);
    }

    protected static double dot(final int[] g, final double x, final double y, final double z, final double w)
    {
        return (g[0] * x) + (g[1] * y) + (g[2] * z) + (g[3] * w);
    }

    /**
     * Computes and returns the 1D unseeded simplex noise for the given
     * coordinates in 1D space
     *
     * @param xin X coordinate
     *
     * @return Noise at given location, from range -1 to 1
     */
    public static double getNoise(final double xin)
    {
        return instance.noise(xin);
    }

    /**
     * Computes and returns the 2D unseeded simplex noise for the given
     * coordinates in 2D space
     *
     * @param xin X coordinate
     * @param yin Y coordinate
     *
     * @return Noise at given location, from range -1 to 1
     */
    public static double getNoise(final double xin, final double yin)
    {
        return instance.noise(xin, yin);
    }

    /**
     * Computes and returns the 3D unseeded simplex noise for the given
     * coordinates in 3D space
     *
     * @param xin X coordinate
     * @param yin Y coordinate
     * @param zin Z coordinate
     *
     * @return Noise at given location, from range -1 to 1
     */
    public static double getNoise(final double xin, final double yin, final double zin)
    {
        return instance.noise(xin, yin, zin);
    }

    /**
     * Computes and returns the 4D simplex noise for the given coordinates in
     * 4D space
     *
     * @param x X coordinate
     * @param y Y coordinate
     * @param z Z coordinate
     * @param w W coordinate
     *
     * @return Noise at given location, from range -1 to 1
     */
    public static double getNoise(final double x, final double y, final double z, final double w)
    {
        return instance.noise(x, y, z, w);
    }

    /**
     * Gets the singleton unseeded instance of this generator
     *
     * @return Singleton
     */
    public static SimplexNoiseGenerator getInstance()
    {
        return instance;
    }
}