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

package org.diorite.ping;

import javax.imageio.ImageIO;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import org.diorite.Diorite;

class DynamicFaviconInstance implements Favicon
{
    private final File    file;
    private       Favicon favicon;
    private       long    lastMod;

    DynamicFaviconInstance(File file, Favicon favicon)
    {
        this.lastMod = file.lastModified();
        this.file = file;
        this.favicon = favicon;
    }

    @Override
    public String getEncoded()
    {
        this.update();
        return this.favicon.getEncoded();
    }

    @Override
    public BufferedImage getImage()
    {
        this.update();
        return this.favicon.getImage();
    }

    private void update()
    {
        long lastModified = this.file.lastModified();
        if (lastModified != this.lastMod)
        {
            this.lastMod = lastModified;
            try
            {
                this.favicon = FaviconInstance.from(ImageIO.read(this.file));
            }
            catch (IOException e)
            {
                Diorite.getDiorite().debug(e);
            }
        }
    }

    static DynamicFaviconInstance from(File file) throws IOException
    {
        return new DynamicFaviconInstance(file, FaviconInstance.from(ImageIO.read(file)));
    }
}
