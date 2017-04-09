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
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;
import java.lang.management.OperatingSystemMXBean;
import java.lang.management.RuntimeMXBean;
import java.net.InetSocketAddress;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.Formatter;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.LoggerConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MarkerFactory;

import org.diorite.impl.log.LoggerOutputStream;
import org.diorite.Diorite;
import org.diorite.DioriteConfig;
import org.diorite.DioriteConfig.HostConfiguration;
import org.diorite.DioriteConfig.OnlineMode;
import org.diorite.DioriteConfig.ProtocolSettings;
import org.diorite.commons.function.supplier.Supplier;
import org.diorite.commons.math.DioriteMathUtils;
import org.diorite.config.ConfigManager;
import org.diorite.config.ConfigTemplate;
import org.diorite.inject.Controller;
import org.diorite.inject.Injection;
import org.diorite.inject.Named;
import org.diorite.inject.Qualifiers;

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
        parser.acceptsAll(Collections.singletonList("?"), "Print help.");
        parser.acceptsAll(Arrays.asList("v", "version"), "Print version.");
        parser.acceptsAll(Collections.singletonList("debug"), "Enable debug mode.");
        parser.acceptsAll(Arrays.asList("resourceleakdetector", "rld"), "ResourceLeakDetector level.").withRequiredArg()
              .ofType(String.class).describedAs("rld").defaultsTo(ResourceLeakDetector.Level.DISABLED.name());
        parser.acceptsAll(Collections.singletonList("config"), "Configuration file to use.").withRequiredArg().ofType(File.class).describedAs("config")
              .defaultsTo(new File("diorite.yml"));
        parser.acceptsAll(Arrays.asList("keepalivetimer", "keep-alive-timer", "kat"), "Every x seconds server will send keep alive packet to players.")
              .withRequiredArg().ofType(Integer.class).describedAs("keepalivetimer").defaultsTo(10);
        parser.acceptsAll(Collections.singletonList("noconsole"), "Disables the console.");
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
                startDiorite(options, args);
                return;
            case VERSION:
                String implementationVersion = Diorite.class.getPackage().getImplementationVersion();
                if (implementationVersion == null)
                {
                    implementationVersion = "Unknown";
                }
                logger.info("Diorite version: " + implementationVersion + " (MC: " + Diorite.getMinecraftVersion() + ")");
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

    public static void startDiorite(OptionSet options, String[] args)
    {
        ConfigTemplate<DioriteConfig> configTemplate = ConfigManager.get().getConfigFile(DioriteConfig.class);
        File configFile = (File) options.valueOf("config");
        boolean exists = configFile.exists();
        DioriteConfig dioriteConfig = configTemplate.create();
        dioriteConfig.bindFile(configFile);
        if (exists)
        {
            dioriteConfig.load();
            if (dioriteConfig.getConfigVersion() != DioriteConfig.CURRENT_VERSION)
            {
                logger.info(
                        "Invalid version of config file ('" + configFile.getAbsolutePath() + "') found: " + dioriteConfig.getConfigVersion() + ", expected: " +
                        DioriteConfig.CURRENT_VERSION + "! Updating it...");
                dioriteConfig.set("configVersion", DioriteConfig.CURRENT_VERSION);
            }
            dioriteConfig.save(configFile);
        }
        else
        {
            logger.info("Config file ('" + configFile.getAbsolutePath() + "') Doesn't exist yet! Creating new one...");
            try
            {
                configFile.createNewFile();
                dioriteConfig.set("configVersion", DioriteConfig.CURRENT_VERSION);
                dioriteConfig.save(configFile);
            }
            catch (IOException e)
            {
                throw new RuntimeException("Can't create config file: " + configFile.getAbsolutePath(), e);
            }
        }
        addBindings(dioriteConfig);

        DioriteServer dioriteServer = new DioriteServer(dioriteConfig);
        printDioriteHello(dioriteServer, args);
        dioriteServer.start();
    }

    private static void addBindings(DioriteConfig config)
    {
        Controller controller = Injection.getController();
        controller.bindToClass(Logger.class).with(Qualifiers.of(Named.class, "diorite")).toInstance(logger);
        controller.bindToClass(DioriteConfig.class).with().toInstance(config);
        controller.rebind();
    }

    private static final DecimalFormat format = new DecimalFormat("#0.##");

    @SuppressWarnings("MagicNumber")
    private static String formatMemory(long memory)
    {
        double mem = memory / (1024.0);
        if (mem < 1024)
        {
            return format.format(mem) + " kiB";
        }
        mem /= 1024.0;
        if (mem < 1024)
        {
            return format.format(mem) + " MiB";
        }
        mem /= 1024.0;
        return format.format(mem) + " GiB";
    }

    private static void printDioriteHello(DioriteServer diorite, String[] args)
    {
        DioriteConfig config = diorite.getConfig();
        RuntimeMXBean runtimeMxBean = ManagementFactory.getRuntimeMXBean();
        MemoryMXBean memoryMXBean = ManagementFactory.getMemoryMXBean();
        MemoryUsage heapMemoryUsage = memoryMXBean.getHeapMemoryUsage();
        OperatingSystemMXBean systemMXBean = ManagementFactory.getOperatingSystemMXBean();

        try (Formatter out = new Formatter(new LoggerOutputStream(logger, java.util.logging.Level.INFO)))
        {
            out.format("%n%s%n", "Starting Diorite server: '" + config.getServerName() + "' version: " + diorite.getVersion());
            out.format("%-30s %s%n", "  Arguments: ", StringUtils.join(args, ' '));
            out.format("%-30s %s%n", "  Enabled languages: ", Arrays.toString(config.getLanguages()));
            out.format("%-30s %s%n", "  Resource pack: ", config.getResourcePack());
            out.format("%-30s %s%n", "  Native transport: ", config.getUseNativeTransport());
            out.format("%-30s %s%n", "  View distance: ", config.getViewDistance());
            out.format("%-30s %s%n", "  Whitelist: ", config.getWhiteListEnabled() + " (" + config.getWhiteListFile().getPath() + ")");
            out.format("%s%n", "  Hosts: ");
            for (InetSocketAddress inetSocketAddress : config.getHosts())
            {
                HostConfiguration hostCfg = config.getHostConfigurationOrDefault(inetSocketAddress);
                out.format("%s%n", "    " + inetSocketAddress.toString());
                out.format("  %-30s %s%n", "  Accepting Players: ", hostCfg.isAcceptingPlayers());
                out.format("  %-30s %s%n", "  Netty threads: ", hostCfg.getNettyThreads());
                out.format("  %-30s %s%n", "  Online mode: ", hostCfg.getOnlineMode().toString().toLowerCase());
                out.format("  %-30s %s%n", "  Query port: ", hostCfg.getQueryPort());
                out.format("  %-30s %s%n", "  Rcon port: ", hostCfg.getRconPort());
            }
            out.format("%s%n", "  Protocols: ");
            for (String prot : config.getProtocols())
            {
                ProtocolSettings protocolSettings = config.getFromProtocolSettings(prot);
                if (protocolSettings == null)
                {
                    out.format("%s%n", "    UNKNOWN PROTOCOL: " + prot);
                    continue;
                }
                out.format("%s%n", "    " + prot);
                out.format("  %-30s %s%n", "  Compression threshold: ", protocolSettings.getNetworkCompressionThreshold());
            }
            out.format("%s%n", "Runtime configuration: ");
            out.format("%-30s %s%n", "  JVM: ", runtimeMxBean.getVmName());
            out.format("%-30s %s%n", "  JVM version: ", runtimeMxBean.getVmVersion() + " [" + System.getProperty("java.class.version") + "]");
            out.format("%-30s %s%n", "  Arguments: ", StringUtils.join(runtimeMxBean.getInputArguments(), ' '));
            out.format("%-30s %s%n", "  OS: ", systemMXBean.getName() + " (" + systemMXBean.getArch() + ")" + " v" + systemMXBean.getVersion());
            out.format("%-30s %s%n", "  Available Processors: ", systemMXBean.getAvailableProcessors());
            out.format("%-30s %s%n", "  Available Memory for VM: ", formatMemory(heapMemoryUsage.getMax()));
            out.flush();
        }
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
            logger.info("Starting server, please wait...");

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

    /**
     * Runs given supplier if debug is enabled.
     *
     * @param supplier
     *         action to run.
     */
    public static void debugRun(Supplier<?> supplier)
    {
        if (enabledDebug)
        {
            Object result = supplier.get();
            if (result instanceof Throwable)
            {
                debug(result);
            }
            else
            {
                debug(result.toString());
            }
        }
    }
}
