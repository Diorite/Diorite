package org.diorite.impl;

import java.io.File;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.util.Arrays;
import java.util.Collections;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import org.apache.commons.lang3.ArrayUtils;

import org.diorite.impl.connection.packets.RegisterPackets;
import org.diorite.Core;
import org.diorite.material.BlockMaterialData;
import org.diorite.material.ItemMaterialData;
import org.diorite.material.Material;
import org.diorite.utils.math.DioriteMathUtils;

import io.netty.util.ResourceLeakDetector;
import joptsimple.OptionParser;
import joptsimple.OptionSet;

public final class CoreMain
{
    public static final  float   JAVA_8         = 52.0f;
    public static final  int     MB_128         = 131072; // 1024KB * 128
    private static final Pattern PERM_GEN_PAT   = Pattern.compile("[^\\d]");
    static               boolean consoleEnabled = true;
    static               boolean useJline       = true;
    static               boolean enabledDebug   = false;
    static               boolean client         = false;

    private CoreMain()
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

    public static boolean isClient()
    {
        return consoleEnabled;
    }

    public static boolean init(final OptionSet options, final boolean client)
    {
        CoreMain.client = client;
        if (! options.has("?"))
        {
            final String path = new File(".").getAbsolutePath();
            if (path.contains("!") || path.contains("+"))
            {
                System.err.println("Cannot run server in a directory with ! or + in the pathname. Please rename the affected folders and try again.");
                return true;
            }
            try
            {
                CoreMain.enabledDebug = options.has("debug");
                CoreMain.debug("===> Debug is enabled! <===");
                try
                {
                    final String lvl = options.valueOf("rld").toString();
                    if (lvl.length() == 1)
                    {
                        ResourceLeakDetector.setLevel(ResourceLeakDetector.Level.values()[DioriteMathUtils.asInt(lvl, 0)]);
                    }
                    else
                    {
                        ResourceLeakDetector.setLevel(ResourceLeakDetector.Level.valueOf(lvl));
                    }
                } catch (final Exception e)
                {
                    e.printStackTrace();
                }
                if (options.has("noconsole"))
                {
                    CoreMain.consoleEnabled = false;
                }
                int maxPermGen = 0;
                for (final String s : ManagementFactory.getRuntimeMXBean().getInputArguments())
                {
                    if (s.startsWith("-XX:MaxPermSize"))
                    {
                        maxPermGen = DioriteMathUtils.asInt(PERM_GEN_PAT.matcher(s).replaceAll(""), 0);
                        maxPermGen <<= 10 * "kmg".indexOf(Character.toLowerCase(s.charAt(s.length() - 1)));
                    }
                }
                if ((Float.parseFloat(System.getProperty("java.class.version")) < JAVA_8) && (maxPermGen < MB_128))
                {
                    System.out.println("Warning, your max perm gen size is not set or less than 128mb. It is recommended you restart Java with the following argument: -XX:MaxPermSize=128M");
                }
                System.out.println("Starting server, please wait...");

                // register all packet classes.
                RegisterPackets.init();

                // TODO: load "magic values"
                // never remove this line (Material.getByID()), it's needed even if it don't do anything for you.
                // it will force load all material classes, loading class of one material before "Material" is loaded will throw error.
                System.out.println("Registered " + Material.values().length + (enabledDebug ? (" (" + Stream.of(Material.values()).map(Material::types).mapToInt(t -> t.length).sum() + " with sub-types)") : "") + " vanilla minecraft blocks and items.");
                if (enabledDebug)
                {
                    System.out.println("Registered " + Stream.of(Material.values()).filter(m -> m instanceof BlockMaterialData).count() + " (" + Stream.of(Material.values()).filter(m -> m instanceof BlockMaterialData).map(Material::types).mapToInt(t -> t.length).sum() + ") vanilla minecraft blocks.");
                    System.out.println("Registered " + Stream.of(Material.values()).filter(m -> m instanceof ItemMaterialData).count() + " (" + Stream.of(Material.values()).filter(m -> m instanceof ItemMaterialData).map(Material::types).mapToInt(t -> t.length).sum() + ") vanilla minecraft items.");
                }
//                new DioriteCore(Proxy.NO_PROXY, options, client).start(options);
            } catch (final Throwable t)
            {
                t.printStackTrace();
            }
        }
        else
        {
            return false;
        }
        return true;
    }

    public static OptionSet main(final String[] args, final boolean client)
    {
        final OptionParser parser = new OptionParser()
        {
            {
                this.acceptsAll(Collections.singletonList("?"), "Print help");
                this.acceptsAll(Collections.singletonList("debug"), "Enable debug mode");
                this.acceptsAll(Arrays.asList("resourceleakdetector", "rld"), "ResourceLeakDetector level, disabled by default").withRequiredArg().ofType(String.class).describedAs("rld").defaultsTo(ResourceLeakDetector.Level.DISABLED.name());
                this.acceptsAll(Arrays.asList("p", "port", "server-port"), "Port to listen on").withRequiredArg().ofType(Integer.class).describedAs("port").defaultsTo(Core.DEFAULT_PORT);
                this.acceptsAll(Arrays.asList("hostname", "h"), "hostname to listen on").withRequiredArg().ofType(String.class).describedAs("hostname").defaultsTo("localhost");
                this.acceptsAll(Arrays.asList("online-mode", "online", "o"), "is server should be in online-mode").withRequiredArg().ofType(Boolean.class).describedAs("online").defaultsTo(true);
                this.acceptsAll(Collections.singletonList("config"), "Configuration file to use.").withRequiredArg().ofType(File.class).describedAs("config").defaultsTo(new File("diorite.yml"));
                this.acceptsAll(Arrays.asList("keepalivetimer", "keep-alive-timer", "kat"), "Each x seconds server will send keep alive packet to players").withRequiredArg().ofType(Integer.class).describedAs("keepalivetimer").defaultsTo(10);
                this.acceptsAll(Arrays.asList("netty", "netty-threads"), "Amount of netty event loop threads.").withRequiredArg().ofType(Integer.class).describedAs("netty").defaultsTo(4);
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
        if (! init(options, client))
        {
            try
            {
                parser.printHelpOn(System.out);
            } catch (final IOException e)
            {
                e.printStackTrace();
            }
            return null;
        }
        return options;
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
