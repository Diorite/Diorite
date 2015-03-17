package org.diorite.impl;

import java.io.File;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.net.Proxy;
import java.util.Arrays;
import java.util.regex.Pattern;

import org.apache.commons.lang3.ArrayUtils;
import org.diorite.Server;
import org.diorite.impl.connection.packets.RegisterPackets;
import org.fusesource.jansi.AnsiConsole;

import io.netty.util.ResourceLeakDetector;
import jline.UnsupportedTerminal;
import joptsimple.OptionParser;
import joptsimple.OptionSet;

public final class Main
{
    private static final Pattern PERM_GEN_PAT   = Pattern.compile("[^\\d]");
    public static final  float   JAVA_8         = 52.0f;
    public static final  int     MB_128         = 131072; // 1024KB * 128
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
                this.acceptsAll(Arrays.asList("?"), "Print help");
                this.acceptsAll(Arrays.asList("debug"), "Enable debug mode");
                this.acceptsAll(Arrays.asList("ResourceLeakDetector", "rld"), "ResourceLeakDetector level, disabled by default").withRequiredArg().ofType(String.class).describedAs("rld").defaultsTo(ResourceLeakDetector.Level.DISABLED.name());
                this.acceptsAll(Arrays.asList("p", "port", "server-port"), "Port to listen on").withRequiredArg().ofType(Integer.class).describedAs("port").defaultsTo(Server.DEFAULT_PORT);
                this.acceptsAll(Arrays.asList("hostname", "h"), "hostname to listen on").withRequiredArg().ofType(String.class).describedAs("hostname").defaultsTo("localhost");
                this.acceptsAll(Arrays.asList("online-mode", "online", "o"), "hostname to listen on").withRequiredArg().ofType(Boolean.class).describedAs("online").defaultsTo(true);
                this.acceptsAll(Arrays.asList("render-distance", "render", "rd"), "chunk render distance").withRequiredArg().ofType(Byte.class).describedAs("render").defaultsTo(Server.DEFAULT_RENDER_DISTANCE);
                this.acceptsAll(Arrays.asList("nojline"), "Disables jline and emulates the vanilla console");
                this.acceptsAll(Arrays.asList("noconsole"), "Disables the console");
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
                System.out.println("Starting server, please wait...");
                RegisterPackets.init();
                new ServerImpl(ServerImpl.DEFAULT_SERVER, Proxy.NO_PROXY, options).start(options);
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
            System.out.println(obj);
        }
    }
}
