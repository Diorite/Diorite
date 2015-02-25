package diorite.impl;

import java.io.PrintStream;
import java.net.InetAddress;
import java.net.Proxy;
import java.net.UnknownHostException;
import java.security.KeyPair;
import java.util.Arrays;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Handler;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import org.apache.logging.log4j.core.appender.ConsoleAppender;

import com.mojang.authlib.GameProfileRepository;
import com.mojang.authlib.minecraft.MinecraftSessionService;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;

import diorite.Server;
import diorite.impl.command.ColoredConsoleCommandSenderImpl;
import diorite.impl.command.CommandMapImpl;
import diorite.impl.command.ConsoleCommandSenderImpl;
import diorite.impl.command.PluginCommandBuilderImpl;
import diorite.impl.command.defaults.RegisterDefaultCommands;
import diorite.impl.connection.MinecraftEncryption;
import diorite.impl.connection.ServerConnection;
import diorite.impl.console.ThreadConsoleReader;
import diorite.impl.log.ForwardLogHandler;
import diorite.impl.log.LoggerOutputStream;
import diorite.impl.log.TerminalConsoleWriterThread;
import diorite.plugin.Plugin;
import diorite.utils.collections.ConcurrentSimpleStringHashMap;
import jline.console.ConsoleReader;
import joptsimple.OptionSet;

public class ServerImpl implements Server, Runnable
{

    private static final Map<String, ServerImpl> instances = new ConcurrentSimpleStringHashMap<>(2, 0.1f);

    private final String serverName;

    private final CommandMapImpl commandMap = new CommandMapImpl();

    private ConsoleCommandSenderImpl consoleCommandSender; //new ConsoleCommandSenderImpl(this);

    private       ConsoleReader    reader;
    private final Thread           mainServerThread;
    private final ServerConnection serverConnection;

    private final YggdrasilAuthenticationService authenticationService;
    private final MinecraftSessionService        sessionService;
    private final GameProfileRepository          gameProfileRepository;

    private long currentTick;
    private int    tps                = DEFAULT_TPS;
    private int    waitTime           = DEFAULT_WAIT_TIME;
    private int    connectionThrottle = 1000;
    private double mutli              = 1; // it can be used with TPS, like make 10 TPS but change this to 2, so server will scale to new TPS.
    private final String  hostname;
    private final int     port;
    private       boolean onlineMode;

    private final double[] recentTps = new double[3];

    private           KeyPair keyPair   = MinecraftEncryption.generateKeyPair();
    private transient boolean isRunning = true;

    public ServerImpl(final String serverName, final Proxy proxy, final OptionSet options)
    {
        this.hostname = options.has("hostname") ? options.valueOf("hostname").toString() : "localhost";
        this.port = options.has("port") ? (Integer) options.valueOf("port") : DEFAULT_PORT;
        this.onlineMode = ! options.has("online") || (Boolean) options.valueOf("online");

        this.serverName = serverName;
        this.mainServerThread = new Thread(this);

        if (instances.get(this.serverName) != null)
        {
            throw new IllegalArgumentException("Server is started yet.");
        }
        instances.put(this.serverName, this);
        final ThreadConsoleReader consoleReader = new ThreadConsoleReader(this, System.in);
        consoleReader.setDaemon(true);
        consoleReader.start();

        try
        {
            this.reader = new ConsoleReader(System.in, System.out);
            this.reader.setExpandEvents(false);
        } catch (final Throwable e)
        {
            this.consoleCommandSender = new ConsoleCommandSenderImpl(this);
            e.printStackTrace();
        }
        this.consoleCommandSender = ColoredConsoleCommandSenderImpl.getInstance(this);

        this.authenticationService = new YggdrasilAuthenticationService(proxy, UUID.randomUUID().toString());
        this.sessionService = this.authenticationService.createMinecraftSessionService();
        this.gameProfileRepository = this.authenticationService.createProfileRepository();

        Runtime.getRuntime().addShutdownHook(new ServerShutdownThread(this));

        RegisterDefaultCommands.init(this.commandMap);

        this.serverConnection = new ServerConnection(this);
    }

    public KeyPair getKeyPair()
    {
        return this.keyPair;
    }

    public void setKeyPair(final KeyPair keyPair)
    {
        this.keyPair = keyPair;
    }

    public int getConnectionThrottle()
    {
        return this.connectionThrottle;
    }

    public void setConnectionThrottle(final int connectionThrottle)
    {
        this.connectionThrottle = connectionThrottle;
    }

    public ServerConnection getServerConnection()
    {
        return this.serverConnection;
    }

    public void setConsoleCommandSender(final ConsoleCommandSenderImpl consoleCommandSender)
    {
        this.consoleCommandSender = consoleCommandSender;
    }

    public boolean isOnlineMode()
    {
        return this.onlineMode;
    }

    public void setOnlineMode(final boolean onlineMode)
    {
        this.onlineMode = onlineMode;
    }

    public int getPort()
    {
        return this.port;
    }

    public String getHostname()
    {
        return this.hostname;
    }

    public Thread getMainServerThread()
    {
        return this.mainServerThread;
    }

    public String getServerModName()
    {
        return NANE + " v" + VERSION;
    }

    @Override
    public ConsoleCommandSenderImpl getConsoleSender()
    {
        return this.consoleCommandSender;
    }

    public ConsoleReader getReader()
    {
        return this.reader;
    }

    @Override
    public double[] getRecentTps()
    {
        final double[] result = new double[this.recentTps.length];
        for (int i = 0; i < this.recentTps.length; i++)
        {
            result[i] = (this.recentTps[i] > this.tps) ? this.tps : this.recentTps[i];
        }
        return result;
    }

    @Override
    public boolean isRunning()
    {
        return this.isRunning;
    }

    @Override
    public double getMutli()
    {
        return this.mutli;
    }

    @Override
    public void setMutli(final double mutli)
    {
        this.mutli = mutli;
    }

    @Override
    public int getTps()
    {
        return this.tps;
    }

    @Override
    public void setTps(final int tps)
    {
        this.waitTime = NANOS_IN_SECOND / tps;
        this.tps = tps;
    }

    public GameProfileRepository getGameProfileRepository()
    {
        return this.gameProfileRepository;
    }

    public MinecraftSessionService getSessionService()
    {
        return this.sessionService;
    }

    public YggdrasilAuthenticationService getAuthenticationService()
    {
        return this.authenticationService;
    }

    @Override
    public void stop()
    {
        this.isRunning = false;
        // TODO
    }

    @Override
    public CommandMapImpl getCommandMap()
    {
        return this.commandMap;
    }

    @Override
    public PluginCommandBuilderImpl createCommand(final Plugin plugin, final String name)
    {
        return PluginCommandBuilderImpl.start(plugin, name);
    }

    @Override
    public String getServerName()
    {
        return this.serverName;
    }

    void start(final OptionSet options)
    {
        {
            final java.util.logging.Logger global = java.util.logging.Logger.getLogger("");
            global.setUseParentHandlers(false);
            for (final Handler handler : global.getHandlers())
            {
                global.removeHandler(handler);
            }
            global.addHandler(new ForwardLogHandler());

            final Logger logger = (Logger) LogManager.getRootLogger();
            logger.getAppenders().values().stream().filter(appender -> (appender instanceof ConsoleAppender)).forEach(logger::removeAppender);
            new Thread(new TerminalConsoleWriterThread(System.out)).start();

            System.setOut(new PrintStream(new LoggerOutputStream(logger, Level.INFO), true));
            System.setErr(new PrintStream(new LoggerOutputStream(logger, Level.WARN), true));
        }
        System.out.println("Starting Diorite v" + VERSION + " server...");

        try
        {
            System.out.println("Starting listening on " + this.hostname + ":" + this.port);
            this.serverConnection.init(InetAddress.getByName(this.hostname), this.port);
            System.out.println("Binded to " + this.hostname + ":" + this.port);
        } catch (final UnknownHostException e)
        {
            e.printStackTrace();
        }

        this.mainServerThread.start();

        // TODO configuration and other shit.

        System.out.println("Started Diorite v" + VERSION + " server!");
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("serverName", this.serverName).toString();
    }

    private static double calcTps(final double avg, final double exp, final double tps)
    {
        return (avg * exp) + (tps * (1.0D - exp));
    }

    @Override
    public void run()
    {
        try
        {

            Arrays.fill(this.recentTps, (double) DEFAULT_TPS);
            long lastTick = System.nanoTime();
            long catchupTime = 0L;
            long tickSection = lastTick;
            while (this.isRunning)
            {
                final long curTime = System.nanoTime();
                final long wait = this.waitTime - (curTime - lastTick) - catchupTime;
                if (wait > 0L)
                {
                    Thread.sleep(wait / NANOS_IN_MILLI);
                    catchupTime = 0L;
                }
                else
                {
                    catchupTime = Math.min(NANOS_IN_SECOND, Math.abs(wait));
                    if ((this.currentTick++ % 100) == 0)
                    {
                        final double currentTps = (((double) NANOS_IN_SECOND) / (curTime - tickSection)) * 100;
                        //noinspection MagicNumber
                        this.recentTps[0] = calcTps(this.recentTps[0], 0.92D, currentTps);
                        //noinspection MagicNumber
                        this.recentTps[1] = calcTps(this.recentTps[1], 0.9835D, currentTps);
                        //noinspection MagicNumber
                        this.recentTps[2] = calcTps(this.recentTps[2], 0.9945000000000001D, currentTps);
                        tickSection = curTime;
                    }
                    lastTick = curTime;


                }
            }
        } catch (final Throwable e)
        {
            e.printStackTrace();
            this.stop();
        }
    }
}
