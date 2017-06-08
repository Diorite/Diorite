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
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;

/**
 * Represent diorite favicon.
 */
public interface Favicon
{
    /**
     * Favicon height.
     */
    int FAVICON_HEIGHT = 64;
    /**
     * Favicon width.
     */
    int FAVICON_WIDTH  = 64;

    /**
     * Returns favicon in encoded format as string starting with `data:image/png;base64` containing encoded image data in utf8 base64.
     *
     * @return favicon in encoded format as string starting with `data:image/png;base64` containing encoded image data in utf8 base64.
     */
    String getEncoded();

    /**
     * Returns favicon as buffered image. <br>
     * This method should return copy of image.
     *
     * @return favicon as buffered image.
     */
    BufferedImage getImage();

    /**
     * Creates favicon from file.
     *
     * @return favicon instance.
     *
     * @throws IOException
     *         if read operation fails. {@link ImageIO#read(URL)}
     */
    static Favicon from(URL url) throws IOException
    {
        if (isLocalFile(url))
        {
            try
            {
                return DynamicFaviconInstance.from(Paths.get(url.toURI()).toFile());
            }
            catch (URISyntaxException e)
            {
                throw new IOException(e);
            }
        }
        return from(ImageIO.read(url));
    }

    private static boolean isLocalFile(java.net.URL url)
    {
        String scheme = url.getProtocol();
        return "file".equalsIgnoreCase(scheme) && ! hasHost(url);
    }

    private static boolean hasHost(java.net.URL url)
    {
        String host = url.getHost();
        return (host != null) && ! host.isEmpty();
    }

    /**
     * Creates favicon from file.
     *
     * @return favicon instance.
     *
     * @throws IOException
     *         if read operation fails. {@link ImageIO#read(File)}
     */
    static Favicon from(File file) throws IOException
    {
        return DynamicFaviconInstance.from(file);
    }

    /**
     * Creates favicon from buffered image.
     *
     * @return favicon instance.
     */
    static Favicon from(BufferedImage image)
    {
        return FaviconInstance.from(image);
    }

    /**
     * Creates favicon from encoded string favicon.
     *
     * @return favicon instance.
     */
    static Favicon from(String encoded)
    {
        return FaviconInstance.from(encoded);
    }
}
