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

package org.diorite.impl.input;

import java.io.IOException;
import java.util.NoSuchElementException;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.impl.CoreMain;
import org.diorite.impl.DioriteCore;
import org.diorite.event.EventType;
import org.diorite.event.input.SenderCommandEvent;

import jline.console.ConsoleReader;

public class ConsoleReaderThread extends Thread
{
    private final DioriteCore core;

    public ConsoleReaderThread(final DioriteCore core)
    {
        super("{Diorite|Console}");
        this.core = core;
        this.setDaemon(true);
    }

    @Override
    public void run()
    {
        if (! CoreMain.isConsoleEnabled())
        {
            return;
        }
        final ConsoleReader reader = this.core.getReader();
        try
        {
            while (this.core.isRunning())
            {
                final String line = CoreMain.isUseJline() ? reader.readLine(">", null) : reader.readLine();
                if ((line == null) || (line.trim().length() <= 0))
                {
                    continue;
                }
                EventType.callEvent(new SenderCommandEvent(this.core.getConsoleSender(), line));
            }
        } catch (final NoSuchElementException ignored)
        {
        } catch (final IOException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("server", this.core).toString();
    }

    public static void start(final DioriteCore core)
    {
        new ConsoleReaderThread(core).start();
    }
}
