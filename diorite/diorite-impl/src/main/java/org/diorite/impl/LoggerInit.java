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

package org.diorite.impl;

import javax.annotation.Nullable;

import java.io.IOException;
import java.io.PrintStream;
import java.util.logging.Handler;
import java.util.logging.Level;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.appender.ConsoleAppender;
import org.fusesource.jansi.AnsiConsole;
import org.slf4j.Logger;

import org.diorite.impl.log.ForwardLogHandler;
import org.diorite.impl.log.LoggerOutputStream;
import org.diorite.impl.log.TerminalConsoleWriterThread;

import jline.UnsupportedTerminal;
import jline.console.ConsoleReader;

final class LoggerInit
{
    @Nullable static ConsoleReader reader;
    @Nullable static Boolean       coloredConsole;

    private LoggerInit() {}

    static void init(Logger coreLogger)
    {
        java.util.logging.Logger global = java.util.logging.Logger.getLogger("");
        global.setUseParentHandlers(false);
        for (Handler handler : global.getHandlers())
        {
            global.removeHandler(handler);
        }
        global.addHandler(new ForwardLogHandler());

        org.apache.logging.log4j.core.Logger logger = (org.apache.logging.log4j.core.Logger) LogManager.getRootLogger();
        logger.getAppenders().values().stream().filter(appender -> (appender instanceof ConsoleAppender)).forEach(logger::removeAppender);

        Thread writer = new Thread(new TerminalConsoleWriterThread(System.out));
        writer.setDaemon(true);
        writer.start();
        DioriteMain.debug("===> Debug is enabled! <===");

        if (System.console() == null)
        {
            System.setProperty("jline.terminal", "jline.UnsupportedTerminal");
            DioriteMain.useJline = false;
        }
        try
        {
            reader = new ConsoleReader(System.in, System.out);
            reader.setExpandEvents(false);
            coreLogger.debug("Created console reader instance.");
        }
        catch (Throwable t)
        {
            t.printStackTrace();
            try
            {
                System.setProperty("jline.terminal", "jline.UnsupportedTerminal");
                System.setProperty("user.language", "en");
                DioriteMain.useJline = false;
                reader = new ConsoleReader(System.in, System.out);
                reader.setExpandEvents(false);
                coreLogger.debug("Created console reader instance for disabled Jline.");
            }
            catch (IOException e)
            {
                coloredConsole = false;
                e.printStackTrace();
            }
        }
        if (coloredConsole == null)
        {
            coreLogger.debug("Enabled support for colored console.");
            coloredConsole = true;
        }


        DioriteMain.useJline = ! "jline.UnsupportedTerminal".equals(System.getProperty("jline.terminal"));
        if (DioriteMain.useJline)
        {
            AnsiConsole.systemInstall();
            coreLogger.debug("Installing console for jline.");
        }
        else
        {
            System.setProperty("jline.terminal", UnsupportedTerminal.class.getName());
            coreLogger.debug("Jline unsupported terminal.");
        }

        System.setOut(new PrintStream(new LoggerOutputStream(coreLogger, Level.INFO), true));
        System.setErr(new PrintStream(new LoggerOutputStream(coreLogger, Level.WARNING), true));
    }
}
