package org.diorite.impl;

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

import org.diorite.Server;
import org.diorite.impl.command.ColoredConsoleCommandSenderImpl;
import org.diorite.impl.command.CommandMapImpl;
import org.diorite.impl.command.ConsoleCommandSenderImpl;
import org.diorite.impl.command.PluginCommandBuilderImpl;
import org.diorite.impl.command.defaults.RegisterDefaultCommands;
import org.diorite.impl.connection.MinecraftEncryption;
import org.diorite.impl.connection.ServerConnection;
import org.diorite.impl.log.ForwardLogHandler;
import org.diorite.impl.log.LoggerOutputStream;
import org.diorite.impl.log.TerminalConsoleWriterThread;
import org.diorite.impl.multithreading.input.ChatThread;
import org.diorite.impl.multithreading.input.CommandsThread;
import org.diorite.impl.multithreading.input.ConsoleReaderThread;
import org.diorite.plugin.Plugin;
import org.diorite.utils.collections.ConcurrentSimpleStringHashMap;
import jline.console.ConsoleReader;
import joptsimple.OptionSet;

public class ServerImpl implements Server, Runnable
{

    protected static final Map<String, ServerImpl> instances  = new ConcurrentSimpleStringHashMap<>(2, 0.1f);
    protected final        CommandMapImpl          commandMap = new CommandMapImpl();
    protected final String                         serverName;
    protected final Thread                         mainServerThread;
    protected final YggdrasilAuthenticationService authenticationService;
    protected final MinecraftSessionService        sessionService;
    protected final GameProfileRepository          gameProfileRepository;
    protected final String                         hostname;
    protected final int                            port;
    protected final ChatThread                     chatThread;
    protected final CommandsThread                 commandsThread;
    private final double[] recentTps          = new double[3];
    protected     int      tps                = DEFAULT_TPS;
    protected     int      waitTime           = DEFAULT_WAIT_TIME;
    protected     int      connectionThrottle = 1000;
    protected     double   mutli              = 1; // it can be used with TPS, like make 10 TPS but change this to 2, so server will scale to new TPS.
    protected ServerConnection         serverConnection;
    protected EntityManagerImpl        entityManager;
    protected PlayersManagerImpl       playersManager;
    protected ConsoleCommandSenderImpl consoleCommandSender; //new ConsoleCommandSenderImpl(this);
    protected ConsoleReader            reader;
    protected long                     currentTick;
    protected boolean                  onlineMode;
    protected byte                     renderDistance;
    private           KeyPair keyPair   = MinecraftEncryption.generateKeyPair();
    private transient boolean isRunning = true;

    public ServerImpl(final String serverName, final Proxy proxy, final OptionSet options)
    {
        this.hostname = options.valueOf("hostname").toString();
        this.port = (Integer) options.valueOf("port");
        this.onlineMode = (Boolean) options.valueOf("online");
        this.renderDistance = (Byte) options.valueOf("render");

        this.serverName = serverName;
        this.mainServerThread = new Thread(this);

        if (instances.get(this.serverName) != null)
        {
            throw new IllegalArgumentException("Server is started yet.");
        }
        instances.put(this.serverName, this);
        final ConsoleReaderThread consoleReader = new ConsoleReaderThread(this, System.in);
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

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try
            {
                this.isRunning = false;
                this.stop();
            } catch (final Exception e)
            {
                e.printStackTrace();
            }
        }));

        RegisterDefaultCommands.init(this.commandMap);

        this.chatThread = ChatThread.start(this);
        this.commandsThread = CommandsThread.start(this);

        this.entityManager = new EntityManagerImpl(this);
        this.playersManager = new PlayersManagerImpl(this);
        this.serverConnection = new ServerConnection(this);

    }

    public ChatThread getChatThread()
    {
        return this.chatThread;
    }

    public CommandsThread getCommandsThread()
    {
        return this.commandsThread;
    }

    @Override
    public byte getRenderDistance()
    {
        return this.renderDistance;
    }

    @Override
    public void setRenderDistance(final byte renderDistance)
    {
        this.renderDistance = renderDistance;
    }

    @Override
    public ConsoleCommandSenderImpl getConsoleSender()
    {
        return this.consoleCommandSender;
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

    public EntityManagerImpl getEntityManager()
    {
        return this.entityManager;
    }

    public PlayersManagerImpl getPlayersManager()
    {
        return this.playersManager;
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

    public ConsoleReader getReader()
    {
        return this.reader;
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

                    this.playersManager.keepAlive();
                }
            }
        } catch (final Throwable e)
        {
            e.printStackTrace();
            this.stop();
        }
    }

    private static double calcTps(final double avg, final double exp, final double tps)
    {
        return (avg * exp) + (tps * (1.0D - exp));
    }
}
