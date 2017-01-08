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

package org.diorite.impl;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.LoggerConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MarkerFactory;

import org.diorite.Diorite;
import org.diorite.commons.math.DioriteMathUtils;

import io.netty.util.ResourceLeakDetector;
import joptsimple.OptionParser;
import joptsimple.OptionSet;

public final class DioriteMain
{
    private static final Logger logger;
    static boolean consoleEnabled = true;
    static boolean useJline       = true;
    static boolean enabledDebug   = false;

    static
    {
        logger = LoggerFactory.getLogger("");
    }

    private DioriteMain() {}

    public static boolean isUseJline()
    {
        return useJline;
    }

    public static boolean isEnabledDebug()
    {
        return enabledDebug;
    }

    public static boolean isConsoleEnabled()
    {
        return consoleEnabled;
    }

    public static void main(String[] args)
    {
        OptionParser parser = new OptionParser();
        parser.acceptsAll(Collections.singletonList("?"), "Print help");
        parser.acceptsAll(Arrays.asList("v", "version"), "Print version");
        parser.acceptsAll(Collections.singletonList("debug"), "Enable debug mode");
        parser.acceptsAll(Arrays.asList("resourceleakdetector", "rld"), "ResourceLeakDetector level").withRequiredArg()
              .ofType(String.class).describedAs("rld").defaultsTo(ResourceLeakDetector.Level.DISABLED.name());
        parser.acceptsAll(Arrays.asList("p", "port", "server-port"), "Port to listen on").withRequiredArg().ofType(Integer.class).describedAs("port")
              .defaultsTo(Diorite.DEFAULT_PORT);
        parser.acceptsAll(Arrays.asList("hostname", "h"), "hostname to listen on").withRequiredArg().ofType(String.class).describedAs("hostname")
              .defaultsTo("localhost");
        parser.acceptsAll(Arrays.asList("online-mode", "online", "o"), "online mode of server").withRequiredArg().ofType(Boolean.class)
              .describedAs("online").defaultsTo(true);
        parser.acceptsAll(Collections.singletonList("config"), "Configuration file to use.").withRequiredArg().ofType(File.class).describedAs("config")
              .defaultsTo(new File("diorite.yml"));
        parser.acceptsAll(Arrays.asList("keepalivetimer", "keep-alive-timer", "kat"), "Each x seconds server will send keep alive packet to players")
              .withRequiredArg().ofType(Integer.class).describedAs("keepalivetimer").defaultsTo(10);
        parser.acceptsAll(Arrays.asList("netty", "netty-threads"), "Amount of netty event loop threads.").withRequiredArg().ofType(Integer.class)
              .describedAs("netty").defaultsTo(4);
        parser.acceptsAll(Collections.singletonList("noconsole"), "Disables the console");
        OptionSet options;
        try
        {
            options = parser.parse(args);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            options = parser.parse(ArrayUtils.EMPTY_STRING_ARRAY);
        }
        InitResult result = init(options);

        switch (result)
        {
            case RUN:
                logger.info("Diorite version: " + Diorite.class.getPackage().getImplementationVersion() + " (MC: " + Diorite.getMinecraftVersion() + ")");
                return;
            case VERSION:
                logger.info("Diorite version: " + Diorite.class.getPackage().getImplementationVersion() + " (MC: " + Diorite.getMinecraftVersion() + ")");
                return;
            case HELP:
                try
                {
                    parser.printHelpOn(System.out);
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
                return;
            case STOP:
                return;
        }
        throw new RuntimeException("Unexpected result.");
    }

    public static InitResult init(OptionSet options)
    {
        if (options.has("version"))
        {
            return InitResult.VERSION;
        }
        if (options.has("?"))
        {
            return InitResult.HELP;
        }
        String path = new File(".").getAbsolutePath();
        if (path.contains("!") || path.contains("+"))
        {
            System.err.println("Cannot run server in a directory with ! or + in the pathname. Please rename the affected folders and try again.");
            return InitResult.STOP;
        }
        try
        {
            if (options.has("noconsole"))
            {
                consoleEnabled = false;
            }
            enabledDebug = options.has("debug");
            if (enabledDebug)
            {
                LoggerContext ctx = (LoggerContext) LogManager.getContext(false);
                Configuration config = ctx.getConfiguration();
                LoggerConfig loggerConfig = config.getLoggerConfig(logger.getName());
                loggerConfig.setLevel(Level.ALL);
                ctx.updateLoggers();
            }
            LoggerInit.init(logger);
            try
            {
                String lvl = options.valueOf("rld").toString();
                if (lvl.length() == 1)
                {
                    ResourceLeakDetector.setLevel(ResourceLeakDetector.Level.values()[DioriteMathUtils.asInt(lvl, 0)]);
                }
                else
                {
                    ResourceLeakDetector.setLevel(ResourceLeakDetector.Level.valueOf(lvl));
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            System.out.println("Starting server, please wait...");

            // TODO: register basic nbt stuff, packets, etc.
        }
        catch (Throwable t)
        {
            t.printStackTrace();
        }
        return InitResult.RUN;
    }

    public enum InitResult
    {
        RUN,
        HELP,
        VERSION,
        STOP
    }

    /**
     * Prints stacktrace to console if debug is enabled
     *
     * @param throwable
     *         stacktrace to print
     */
    public static void debug(Throwable throwable)
    {
        if (enabledDebug)
        {
            logger.debug(MarkerFactory.getMarker("debugException"), throwable.getMessage(), throwable);
        }
    }

    /**
     * Prints objects to console if debug is enabled
     *
     * @param obj
     *         objects to print
     */
    public static void debug(Object obj)
    {
        if (enabledDebug)
        {
            logger.debug(obj.toString());
        }
    }

    /**
     * Runs given runnable if debug is enabled.
     *
     * @param runnable
     *         action to run.
     */
    public static void debugRun(Runnable runnable)
    {
        if (enabledDebug)
        {
            runnable.run();
        }
    }
}
