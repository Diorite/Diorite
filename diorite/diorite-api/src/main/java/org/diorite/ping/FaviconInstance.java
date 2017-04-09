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

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

class FaviconInstance implements Favicon
{
    private final String        encoded;
    private final BufferedImage image;

    FaviconInstance(String encoded, BufferedImage image)
    {
        this.encoded = encoded;
        this.image = image;
    }

    @Override
    public String getEncoded()
    {
        return this.encoded;
    }

    @Override
    public BufferedImage getImage()
    {
        return deepCopy(this.image);
    }

    static BufferedImage deepCopy(BufferedImage bi)
    {
        ColorModel cm = bi.getColorModel();
        boolean isAlphaPreMultiplied = cm.isAlphaPremultiplied();
        WritableRaster raster = bi.copyData(null);
        return new BufferedImage(cm, raster, isAlphaPreMultiplied, null);
    }

    /**
     * Creates favicon from buffered image.
     *
     * @return favicon instance.
     */
    static Favicon from(BufferedImage image)
    {
        String encodeFavicon = encodeFavicon(image);
        return new FaviconInstance(encodeFavicon, deepCopy(image));
    }

    /**
     * Creates favicon from encoded string favicon.
     *
     * @return favicon instance.
     */
    static Favicon from(String encoded)
    {
        byte[] bytes = encoded.getBytes(StandardCharsets.UTF_8);
        try (ByteArrayInputStream byteInputStream = new ByteArrayInputStream(bytes))
        {
            BufferedImage bufferedImage = ImageIO.read(Base64.getDecoder().wrap(byteInputStream));
            if ((bufferedImage.getWidth() == FAVICON_WIDTH) && (bufferedImage.getHeight() == FAVICON_HEIGHT))
            {
                return new FaviconInstance(encoded, bufferedImage);
            }
            return from(bufferedImage);
        }
        catch (IOException e)
        {
            throw new RuntimeException("Can't decode image.", e);
        }
    }

    /**
     * Helper method to encode buffered image, result should be cached and reused.
     *
     * @param faviconImage
     *         image to encode.
     *
     * @return encoded image in valid favicon format.
     */
    public static String encodeFavicon(BufferedImage faviconImage)
    {
        if ((faviconImage.getWidth() != FAVICON_WIDTH) && (faviconImage.getHeight() != FAVICON_HEIGHT))
        {
            int type = (faviconImage.getType() == 0) ? BufferedImage.TYPE_INT_ARGB : faviconImage.getType();
            faviconImage = resizeImageWithHint(faviconImage, type);
        }
        try
        {
            ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream(FAVICON_WIDTH * FAVICON_HEIGHT * 4);
            OutputStream outputStream = Base64.getEncoder().wrap(arrayOutputStream);
            ImageIO.write(faviconImage, "PNG", outputStream);
            return "data:image/png;base64," + arrayOutputStream.toString(StandardCharsets.UTF_8.name());
        }
        catch (IOException e)
        {
            throw new RuntimeException("Can't scale image.", e);
        }
    }

    private static BufferedImage resizeImageWithHint(BufferedImage originalImage, int type)
    {
        BufferedImage resizedImage = new BufferedImage(FAVICON_WIDTH, FAVICON_HEIGHT, type);
        Graphics2D g = resizedImage.createGraphics();

        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g.drawImage(originalImage, 0, 0, FAVICON_WIDTH, FAVICON_HEIGHT, null);
        g.dispose();
        g.setComposite(AlphaComposite.Src);
        return resizedImage;
    }
}
