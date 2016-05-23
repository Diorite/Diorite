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

package org.diorite;

import java.util.UUID;

import org.diorite.chat.component.BaseComponent;

public interface BossBar // TODO javadocs
{
    enum Color
    {
        PINK, BLUE, RED, GREEN, YELLOW, PURPLE, WHITE
    }

    enum Style
    {
        PROGRESS, NOTCHED_6, NOTCHED_10, NOTCHED_12, NOTCHED_20
    }

    /**
     * Returns the Universally Unique Identifier of this BossBar
     * Every BossBar has own UUID
     *
     * @return UUID of this BossBar
     */
    UUID getUUID();

    /**
     * Return text (in form of {@link BaseComponent}) displayed above BossBar
     *
     * @return text displayed above BossBar
     */
    BaseComponent getTitle();

    void setTitle(BaseComponent component);

    /**
     * Returns current progress of BossBar
     * This is a numbe
     *
     * @return
     */
    int getHealth();

    void setHealth(int health);

    Color getColor();

    void setColor(Color color);

    Style getStyle();

    void setStyle(Style style);

    void setColorAndStyle(Color color, Style style);

    boolean isDragonBar();

    void setIsDragonBar(boolean isDragonBar);

    boolean isDarkSky();

    void setIsDarkSky(boolean isDarkSky);
}
