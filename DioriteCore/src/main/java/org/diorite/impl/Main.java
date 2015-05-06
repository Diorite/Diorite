package org.diorite.impl;

import java.io.File;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.net.Proxy;
import java.util.Arrays;
import java.util.Collections;
import java.util.regex.Pattern;

import org.apache.commons.lang3.ArrayUtils;
import org.fusesource.jansi.AnsiConsole;

import org.diorite.impl.connection.packets.RegisterPackets;
import org.diorite.Server;
import org.diorite.material.Material;

import io.netty.util.ResourceLeakDetector;
import jline.UnsupportedTerminal;
import joptsimple.OptionParser;
import joptsimple.OptionSet;

public final class Main
{
    public static final  float   JAVA_8         = 52.0f;
    public static final  int     MB_128         = 131072; // 1024KB * 128
    private static final Pattern PERM_GEN_PAT   = Pattern.compile("[^\\d]");
    static               boolean consoleEnabled = true;
    static               boolean useJline       = true;
    static               boolean enabledDebug   = false;

    private Main()
    {
    }

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

    public static void main(final String[] args)
    {
        final OptionParser parser = new OptionParser()
        {
            {
                this.acceptsAll(Collections.singletonList("?"), "Print help");
                this.acceptsAll(Collections.singletonList("debug"), "Enable debug mode");
                this.acceptsAll(Arrays.asList("resourceleakdetector", "rld"), "ResourceLeakDetector level, disabled by default").withRequiredArg().ofType(String.class).describedAs("rld").defaultsTo(ResourceLeakDetector.Level.DISABLED.name());
                this.acceptsAll(Arrays.asList("p", "port", "server-port"), "Port to listen on").withRequiredArg().ofType(Integer.class).describedAs("port").defaultsTo(Server.DEFAULT_PORT);
                this.acceptsAll(Arrays.asList("servername", "sn"), "name of server, should be simple like 'main'").withRequiredArg().ofType(String.class).describedAs("servername").defaultsTo(ServerImpl.DEFAULT_SERVER);
                this.acceptsAll(Arrays.asList("hostname", "h"), "hostname to listen on").withRequiredArg().ofType(String.class).describedAs("hostname").defaultsTo("localhost");
                this.acceptsAll(Arrays.asList("online-mode", "online", "o"), "is server should be in online-mode").withRequiredArg().ofType(Boolean.class).describedAs("online").defaultsTo(true);
                //noinspection MagicNumber
                this.acceptsAll(Arrays.asList("timeout", "player-timeout", "pt"), "If player don't send any keep alive packet in this time (seconds), then it will be disconnected.").withRequiredArg().ofType(Integer.class).describedAs("timeout").defaultsTo(60);
                this.acceptsAll(Arrays.asList("keepalivetimer", "keep-alive-timer", "kat"), "Each x seconds server will send keep alive packet to players").withRequiredArg().ofType(Integer.class).describedAs("keepalivetimer").defaultsTo(10);
                this.acceptsAll(Arrays.asList("compressionthreshold", "compression-threshold"), "Compression threshold to use, -1 to turn off (default)").withRequiredArg().ofType(Integer.class).describedAs("compressionthreshold").defaultsTo(- 1);
                this.acceptsAll(Arrays.asList("render-distance", "render", "rd"), "chunk render distance").withRequiredArg().ofType(Byte.class).describedAs("render").defaultsTo(Server.DEFAULT_RENDER_DISTANCE);
                this.acceptsAll(Collections.singletonList("worldsdir"), "Directory where all worlds are stored.").withRequiredArg().ofType(String.class).describedAs("worldsdir").defaultsTo("worlds");
                this.acceptsAll(Collections.singletonList("defworld"), "Name of default world.").withRequiredArg().ofType(String.class).describedAs("defworld").defaultsTo("world");
                this.acceptsAll(Arrays.asList("netty", "netty-threads"), "Amount of netty event loop threads.").withRequiredArg().ofType(Integer.class).describedAs("netty").defaultsTo(4);
                this.acceptsAll(Collections.singletonList("nojline"), "Disables jline and emulates the vanilla console");
                this.acceptsAll(Collections.singletonList("noconsole"), "Disables the console");
            }
        };
        OptionSet options;
        try
        {
            options = parser.parse(args);
        } catch (final Exception e)
        {
            e.printStackTrace();
            options = parser.parse(ArrayUtils.EMPTY_STRING_ARRAY);
        }
        if (! options.has("?"))
        {
            final String path = new File(".").getAbsolutePath();
            if (path.contains("!") || path.contains("+"))
            {
                System.err.println("Cannot run server in a directory with ! or + in the pathname. Please rename the affected folders and try again.");
                return;
            }
            try
            {
                Main.enabledDebug = options.has("debug");
                Main.debug("===> Debug is enabled! <===");
                try
                {
                    final String lvl = options.valueOf("rld").toString();
                    if (lvl.length() == 1)
                    {
                        ResourceLeakDetector.setLevel(ResourceLeakDetector.Level.values()[Integer.parseInt(lvl)]);
                    }
                    else
                    {
                        ResourceLeakDetector.setLevel(ResourceLeakDetector.Level.valueOf(lvl));
                    }
                } catch (final Exception e)
                {
                    e.printStackTrace();
                }
                final String jline_UnsupportedTerminal = new String(new char[]{'j', 'l', 'i', 'n', 'e', '.', 'U', 'n', 's', 'u', 'p', 'p', 'o', 'r', 't', 'e', 'd', 'T', 'e', 'r', 'm', 'i', 'n', 'a', 'l'});
                final String jline_terminal = new String(new char[]{'j', 'l', 'i', 'n', 'e', '.', 't', 'e', 'r', 'm', 'i', 'n', 'a', 'l'});
                Main.useJline = ! jline_UnsupportedTerminal.equals(System.getProperty(jline_terminal));
                if (options.has("nojline"))
                {
                    System.setProperty("user.language", "en");
                    Main.useJline = false;
                }
                if (Main.useJline)
                {
                    AnsiConsole.systemInstall();
                }
                else
                {
                    System.setProperty("jline.terminal", UnsupportedTerminal.class.getName());
                }
                if (options.has("noconsole"))
                {
                    Main.consoleEnabled = false;
                }
                int maxPermGen = 0;
                for (final String s : ManagementFactory.getRuntimeMXBean().getInputArguments())
                {
                    if (s.startsWith("-XX:MaxPermSize"))
                    {
                        maxPermGen = Integer.parseInt(PERM_GEN_PAT.matcher(s).replaceAll(""));
                        maxPermGen <<= 10 * "kmg".indexOf(Character.toLowerCase(s.charAt(s.length() - 1)));
                    }
                }
                if ((Float.parseFloat(System.getProperty("java.class.version")) < JAVA_8) && (maxPermGen < MB_128))
                {
                    System.out.println("Warning, your max perm gen size is not set or less than 128mb. It is recommended you restart Java with the following argument: -XX:MaxPermSize=128M");
                }
                final String serverName = options.valueOf("servername").toString();
                System.out.println("Starting server (" + serverName + "), please wait...");

                // register all packet classes.
                RegisterPackets.init();

                // TODO: load "magic values"
                // never remove this line (Material.getByID()), it's needed even if it don't do anything for you.
                // it will force load all material classes, loading class of one material before "Material" is loaded will throw error.
                System.out.println("Registered " + Material.getByID().size() + " vanilla minecraft blocks and items.");
                new ServerImpl(serverName, Proxy.NO_PROXY, options).start(options);
            } catch (final Throwable t)
            {
                t.printStackTrace();
            }
        }
        else
        {
            try
            {
                parser.printHelpOn(System.out);
            } catch (final IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    public static void debug(final Object obj)
    {
        if (enabledDebug)
        {
            System.out.println("[DEBUG] " + obj);
        }
    }

    /**
     * This comment is here just to write some to-do stuff, and other notes
     * TODO: remember about signs that can run commands -> player can inject commands here, aka force op
     *
     * You can make sky with weird colors by GameStateChange, id 7, values from 0 to 60
     * And rain without rains, id 7, values from -10 to 0.
     */
}
