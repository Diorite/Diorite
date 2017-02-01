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

import javax.annotation.Nullable;
import javax.imageio.ImageIO;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.chat.ChatMessage;

/**
 * Represent server ping data.
 */
public class ServerPing
{
    public static final int MAX_FAVICON_HEIGHT = 64;
    public static final int MAX_FAVICON_WIDTH  = 64;

    private           ChatMessage            motd;
    private           ServerPingPlayerSample playerData;
    private           ServerPingServerData   serverData;
    private @Nullable String                 encodedFavicon;

    /**
     * Create new ping instance.
     *
     * @param motd
     *         message of the day to show.
     * @param favicon
     *         encoded favicon (as string starting with `data:image/png;base64` containing encoded image data in utf8 base64) to display, favicon should be
     *         cached.
     * @param serverData
     *         version server data.
     * @param playerData
     *         player sample data.
     */
    public ServerPing(ChatMessage motd, @Nullable String favicon, ServerPingServerData serverData, ServerPingPlayerSample playerData)
    {
        this.motd = motd;
        this.encodedFavicon = favicon;
        this.serverData = serverData;
        this.playerData = playerData;
    }

    /**
     * Returns message of the day to display.
     *
     * @return message of the day to display.
     */
    public ChatMessage getMotd()
    {
        return this.motd;
    }

    /**
     * Set message of the day to display.
     *
     * @param motd
     *         new message of the day.
     */
    public void setMotd(ChatMessage motd)
    {
        this.motd = motd;
    }

    /**
     * Returns encoded favicon, as string starting with `data:image/png;base64` containing encoded image data in utf8 base64
     *
     * @return encoded favicon.
     */
    @Nullable
    public String getEncodedFavicon()
    {
        return this.encodedFavicon;
    }

    /**
     * Change encoded favicon as string starting with `data:image/png;base64` containing encoded image data in utf8 base64.
     *
     * @param favicon
     *         new favicon data.
     */
    public void setEncodedFavicon(@Nullable String favicon)
    {
        this.encodedFavicon = favicon;
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
        if ((faviconImage.getWidth() != MAX_FAVICON_WIDTH) && (faviconImage.getHeight() != MAX_FAVICON_HEIGHT))
        {
            int type = (faviconImage.getType() == 0) ? BufferedImage.TYPE_INT_ARGB : faviconImage.getType();
            faviconImage = resizeImageWithHint(faviconImage, type);
        }
        try
        {
            ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream(MAX_FAVICON_WIDTH * MAX_FAVICON_HEIGHT * 4);
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
        BufferedImage resizedImage = new BufferedImage(MAX_FAVICON_WIDTH, MAX_FAVICON_HEIGHT, type);
        Graphics2D g = resizedImage.createGraphics();

        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g.drawImage(originalImage, 0, 0, MAX_FAVICON_WIDTH, MAX_FAVICON_HEIGHT, null);
        g.dispose();
        g.setComposite(AlphaComposite.Src);
        return resizedImage;
    }

    /**
     * Returns server version ping data.
     *
     * @return server version ping data.
     */
    public ServerPingServerData getServerData()
    {
        return this.serverData;
    }

    /**
     * Set new ping version data.
     *
     * @param serverData
     *         new ping version data.
     */
    public void setServerData(ServerPingServerData serverData)
    {
        this.serverData = serverData;
    }

    /**
     * Returns player sample data.
     *
     * @return player sample data.
     */
    public ServerPingPlayerSample getPlayerData()
    {
        return this.playerData;
    }

    /**
     * Set new player sample data.
     *
     * @param playerData
     *         new player sample data.
     */
    public void setPlayerData(ServerPingPlayerSample playerData)
    {
        this.playerData = playerData;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("motd", this.motd)
                                                                          .append("playerData", this.playerData).append("serverData", this.serverData)
                                                                          .append("favicon", this.encodedFavicon != null).toString();
    }
}