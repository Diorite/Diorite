package org.diorite.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.Proxy;
import java.net.UnknownHostException;
import java.security.KeyPair;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.function.Predicate;
import java.util.logging.Handler;
import java.util.stream.Collectors;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import org.apache.logging.log4j.core.appender.ConsoleAppender;

import org.diorite.impl.auth.SessionService;
import org.diorite.impl.auth.yggdrasil.YggdrasilSessionService;
import org.diorite.impl.command.ColoredConsoleCommandSenderImpl;
import org.diorite.impl.command.CommandMapImpl;
import org.diorite.impl.command.ConsoleCommandSenderImpl;
import org.diorite.impl.command.PluginCommandBuilderImpl;
import org.diorite.impl.command.defaults.RegisterDefaultCommands;
import org.diorite.impl.connection.MinecraftEncryption;
import org.diorite.impl.connection.NetworkManager;
import org.diorite.impl.connection.ServerConnection;
import org.diorite.impl.connection.packets.play.out.PacketPlayOutChat;
import org.diorite.impl.connection.packets.play.out.PacketPlayOutPlayerListHeaderFooter;
import org.diorite.impl.connection.packets.play.out.PacketPlayOutTitle;
import org.diorite.impl.entity.PlayerImpl;
import org.diorite.impl.log.ForwardLogHandler;
import org.diorite.impl.log.LoggerOutputStream;
import org.diorite.impl.log.TerminalConsoleWriterThread;
import org.diorite.impl.multithreading.input.CommandsThread;
import org.diorite.impl.multithreading.input.ConsoleReaderThread;
import org.diorite.impl.multithreading.input.TabCompleteThread;
import org.diorite.impl.multithreading.map.ChunkMultithreadedHandler;
import org.diorite.impl.multithreading.map.ChunkUnloaderThread;
import org.diorite.impl.pipelines.ChatPipelineImpl;
import org.diorite.impl.pipelines.ChunkGeneratePipelineImpl;
import org.diorite.impl.pipelines.ChunkLoadPipelineImpl;
import org.diorite.impl.pipelines.ChunkPopulatePipelineImpl;
import org.diorite.impl.pipelines.CommandPipelineImpl;
import org.diorite.impl.pipelines.TabCompletePipelineImpl;
import org.diorite.impl.world.WorldsManagerImpl;
import org.diorite.impl.world.generator.FlatWorldGeneratorImpl;
import org.diorite.impl.world.generator.TestWorldGeneratorImpl;
import org.diorite.impl.world.generator.VoidWorldGeneratorImpl;
import org.diorite.Diorite;
import org.diorite.Server;
import org.diorite.cfg.DioriteConfig;
import org.diorite.cfg.DioriteConfig.OnlineMode;
import org.diorite.cfg.system.Template;
import org.diorite.cfg.system.TemplateCreator;
import org.diorite.cfg.yaml.DioriteYaml;
import org.diorite.chat.ChatPosition;
import org.diorite.chat.component.BaseComponent;
import org.diorite.entity.Player;
import org.diorite.event.EventType;
import org.diorite.event.chunk.ChunkGenerateEvent;
import org.diorite.event.chunk.ChunkLoadEvent;
import org.diorite.event.chunk.ChunkPopulateEvent;
import org.diorite.event.others.SenderCommandEvent;
import org.diorite.event.others.SenderTabCompleteEvent;
import org.diorite.event.pipelines.ChatPipeline;
import org.diorite.event.pipelines.ChunkGeneratePipeline;
import org.diorite.event.pipelines.ChunkLoadPipeline;
import org.diorite.event.pipelines.ChunkPopulatePipeline;
import org.diorite.event.pipelines.CommandPipeline;
import org.diorite.event.pipelines.TabCompletePipeline;
import org.diorite.event.player.PlayerChatEvent;
import org.diorite.plugin.Plugin;
import org.diorite.utils.DioriteUtils;
import org.diorite.world.World;
import org.diorite.world.WorldsManager;
import org.diorite.world.generator.WorldGenerators;

import jline.console.ConsoleReader;
import joptsimple.OptionSet;

public class ServerImpl implements Server, Runnable
{
    private static ServerImpl instance;

    protected final CommandMapImpl commandMap = new CommandMapImpl();
    protected final String serverName;
    protected final Thread mainServerThread;
    protected final String hostname;
    protected final int    port;
    protected int    tps                = DEFAULT_TPS;
    protected int    waitTime           = DEFAULT_WAIT_TIME;
    protected int    connectionThrottle = 1000;
    protected double mutli              = 1; // it can be used with TPS, like make 10 TPS but change this to 2, so server will scale to new TPS.
    protected       int                      compressionThreshold; // -1 -> off
    protected final YggdrasilSessionService  sessionService;
    protected final ServerConnection         serverConnection;
    protected final EntityManagerImpl        entityManager;
    protected final PlayersManagerImpl       playersManager;
    protected final WorldsManagerImpl        worldsManager;
    protected       ConsoleCommandSenderImpl consoleCommandSender; //new ConsoleCommandSenderImpl(this);
    protected       ConsoleReader            reader;
    protected       long                     currentTick;
    protected       OnlineMode               onlineMode;
    protected       byte                     renderDistance;
    protected final int                      playerTimeout;
    protected final int                      keepAliveTimer;
    protected       DioriteConfig            config;
    private           KeyPair keyPair   = MinecraftEncryption.generateKeyPair();
    private transient boolean isRunning = true;

    public int getCompressionThreshold()
    {
        return this.compressionThreshold;
    }

    public void setCompressionThreshold(final int compressionThreshold)
    {
        this.compressionThreshold = compressionThreshold;
    }

    private void loadConfigFile(final File f)
    {
        final Template<DioriteConfig> cfgTemp = TemplateCreator.getTemplate(DioriteConfig.class);
        boolean needWrite = true;
        if (f.exists())
        {
            final DioriteYaml dy = new DioriteYaml();
            try
            {
                this.config = dy.loadAs(new FileInputStream(f), DioriteConfig.class);
                if (this.config == null)
                {
                    this.config = new DioriteConfig();
                }
                else
                {
                    needWrite = false;
                }
            } catch (final FileNotFoundException ignored) // should be never thrown.
            {
                throw new AssertionError("Config file not found...", ignored);
            }
        }
        else
        {
            this.config = new DioriteConfig();
            try
            {
                DioriteUtils.createFile(f);
            } catch (final IOException e)
            {
                throw new RuntimeException("Can't create configuration file!", e);
            }
        }
        if (needWrite)
        {
            try
            {
                cfgTemp.dump(f, this.config);
            } catch (final IOException e)
            {
                throw new RuntimeException("Can't dump configuration file!", e);
            }
        }
    }

    private void registerEvents()
    {
        EventType.register(SenderCommandEvent.class, CommandPipeline.class, new CommandPipelineImpl());
        EventType.register(SenderTabCompleteEvent.class, TabCompletePipeline.class, new TabCompletePipelineImpl());

        EventType.register(PlayerChatEvent.class, ChatPipeline.class, new ChatPipelineImpl());

        EventType.register(ChunkLoadEvent.class, ChunkLoadPipeline.class, new ChunkLoadPipelineImpl());
        EventType.register(ChunkGenerateEvent.class, ChunkGeneratePipeline.class, new ChunkGeneratePipelineImpl());
        EventType.register(ChunkPopulateEvent.class, ChunkPopulatePipeline.class, new ChunkPopulatePipelineImpl());
    }

    @Override
    public List<String> getOnlinePlayersNames(final String prefix)
    {
        return this.playersManager.getOnlinePlayersNames(prefix);
    }

    @Override
    public List<String> getOnlinePlayersNames()
    {
        return this.playersManager.getOnlinePlayersNames();
    }

    public ServerImpl(final String serverName, final Proxy proxy, final OptionSet options)
    {
        instance = this;
        Diorite.setServer(this);
        this.loadConfigFile((File) options.valueOf("config"));
        if (this.config == null)
        {
            throw new AssertionError("Configuration instance is null after creating!");
        }

        this.keepAliveTimer = (int) options.valueOf("keepalivetimer");

        if (options.has("keepalivetimer"))
        {
            this.playerTimeout = (int) options.valueOf("timeout");
        }
        else
        {
            this.playerTimeout = this.config.getPlayerIdleTimeout();
        }
        if (options.has("compressionthreshold"))
        {
            this.compressionThreshold = (int) options.valueOf("compressionthreshold");
        }
        else
        {
            this.compressionThreshold = this.config.getNetworkCompressionThreshold();
        }

        if (options.has("hostname"))
        {
            this.hostname = options.valueOf("hostname").toString();
        }
        else
        {
            this.hostname = this.config.getHostname();
        }

        if (options.has("port"))
        {
            this.port = (int) options.valueOf("port");
        }
        else
        {
            this.port = this.config.getPort();
        }

        if (options.has("online"))
        {
            final OnlineMode mode = OnlineMode.valueOf(options.valueOf("online").toString().toUpperCase());
            this.onlineMode = (mode == null) ? OnlineMode.TRUE : mode;
        }
        else
        {
            this.onlineMode = this.config.getOnlineMode();
        }

        if (options.has("render"))
        {
            this.renderDistance = (byte) options.valueOf("render");
        }
        else
        {
            this.renderDistance = (byte) this.config.getViewDistance();
        }


        this.serverName = serverName;
        this.mainServerThread = new Thread(this, "{Diorite|Main}");
        this.registerEvents();

        if (System.console() == null)
        {
            System.setProperty("jline.terminal", "jline.UnsupportedTerminal");
            Main.useJline = false;
        }
        try
        {

            this.reader = new ConsoleReader(System.in, System.out);
            this.reader.setExpandEvents(false);
        } catch (final Throwable t)
        {
            t.printStackTrace();
            try
            {
                System.setProperty("jline.terminal", "jline.UnsupportedTerminal");
                System.setProperty("user.language", "en");
                Main.useJline = false;
                this.reader = new ConsoleReader(System.in, System.out);
                this.reader.setExpandEvents(false);
            } catch (final IOException e)
            {
                this.consoleCommandSender = new ConsoleCommandSenderImpl(this);
                e.printStackTrace();
            }
        }
        this.consoleCommandSender = ColoredConsoleCommandSenderImpl.getInstance(this);

        ConsoleReaderThread.start(this);

        this.sessionService = new YggdrasilSessionService(proxy, UUID.randomUUID().toString());

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try
            {
                this.stop();
            } catch (final Exception e)
            {
                e.printStackTrace();
            }
        }));

        RegisterDefaultCommands.init(this.commandMap);

        org.diorite.impl.multithreading.input.ChatThread.start(this);
        CommandsThread.start(this);
        TabCompleteThread.start(this);
        ChunkUnloaderThread.start(this);
        new ChunkMultithreadedHandler(this).start();

        this.entityManager = new EntityManagerImpl(this);
        this.playersManager = new PlayersManagerImpl(this);
        this.worldsManager = new WorldsManagerImpl();

        this.serverConnection = new ServerConnection(this);
        this.serverConnection.start();
    }

    @Override
    public DioriteConfig getConfig()
    {
        return this.config;
    }

    public int getKeepAliveTimer()
    {
        return this.keepAliveTimer;
    }

    public int getPlayerTimeout()
    {
        return this.playerTimeout;
    }

    @Override
    public WorldsManager getWorldsManager()
    {
        return this.worldsManager;
    }

    @Override
    public void broadcastMessage(final ChatPosition position, final BaseComponent component)
    {
        this.playersManager.forEach(new PacketPlayOutChat(component, position));
    }

    @Override
    public void sendConsoleMessage(final String str)
    {
        this.consoleCommandSender.sendMessage(str);
    }

    @Override
    public void sendConsoleMessage(final BaseComponent component)
    {
        this.consoleCommandSender.sendMessage(component);
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
    public synchronized void stop()
    {
        if (! this.isRunning)
        {
            return; // TODO This shouldn't never happen. Maybe IllegalStateException?
        }
        this.isRunning = false;
        if (this.playersManager != null)
        {
            this.playersManager.forEach(p -> p.kick("§4Server closed!"));
        }
        if (this.worldsManager != null)
        {
            this.worldsManager.getWorlds().stream().forEach(World::save);
        }
        if (this.serverConnection != null)
        {
            this.serverConnection.close();
        }
        System.out.println("Goodbye <3");
        // TODO
    }

    @Override
    public void updatePlayerListHeaderAndFooter(final BaseComponent header, final BaseComponent footer)
    {
        this.getOnlinePlayers().forEach((player) -> this.updatePlayerListHeaderAndFooter(header, footer, player));
    }

    @Override
    public void updatePlayerListHeaderAndFooter(final BaseComponent header, final BaseComponent footer, final Player player)
    {
        ((PlayerImpl) player).getNetworkManager().sendPacket(new PacketPlayOutPlayerListHeaderFooter(header, footer));
    }

    @Override
    public void broadcastTitle(final BaseComponent title, final BaseComponent subtitle, final int fadeIn, final int stay, final int fadeOut)
    {
        this.playersManager.forEach((player) -> this.sendTitle(title, subtitle, fadeIn, stay, fadeOut, player));
    }

    @Override
    public void sendTitle(final BaseComponent title, final BaseComponent subtitle, final int fadeIn, final int stay, final int fadeOut, final Player player)
    {
        final NetworkManager n = ((PlayerImpl) player).getNetworkManager();

        if (title != null)
        {
            n.sendPacket(new PacketPlayOutTitle(PacketPlayOutTitle.TitleAction.SET_TITLE, title));
        }

        if (subtitle != null)
        {
            n.sendPacket(new PacketPlayOutTitle(PacketPlayOutTitle.TitleAction.SET_SUBTITLE, subtitle));
        }

        n.sendPacket(new PacketPlayOutTitle(PacketPlayOutTitle.TitleAction.SET_TIMES, fadeIn, stay, fadeOut));
    }

    @Override
    public void removeTitle(final Player player)
    {
        ((PlayerImpl) player).getNetworkManager().sendPacket(new PacketPlayOutTitle(PacketPlayOutTitle.TitleAction.RESET));
    }

    @Override
    public void removeAllTitles()
    {
        this.playersManager.forEach(new PacketPlayOutTitle(PacketPlayOutTitle.TitleAction.RESET));
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

    @Override
    public Collection<Player> getOnlinePlayers()
    {
        return new CopyOnWriteArraySet<>(this.playersManager.getRawPlayers().values());
    }

    @Override
    public Collection<Player> getOnlinePlayers(final Predicate<Player> predicate)
    {
        return this.playersManager.getRawPlayers().values().stream().filter(predicate).collect(Collectors.toSet());
    }

    @Override
    public Player getPlayer(final UUID uuid)
    {
        return this.playersManager.getRawPlayers().get(uuid);
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

    public OnlineMode getOnlineMode()
    {
        return this.onlineMode;
    }

    public void setOnlineMode(final OnlineMode onlineMode)
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

    public SessionService getSessionService()
    {
        return this.sessionService;
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

            final Thread writer = new Thread(new TerminalConsoleWriterThread(System.out));
            writer.setDaemon(true);
            writer.start();

            System.setOut(new PrintStream(new LoggerOutputStream(logger, Level.INFO), true));
            System.setErr(new PrintStream(new LoggerOutputStream(logger, Level.WARN), true));
        }
        System.out.println("Starting Diorite v" + VERSION + " server...");

        { // register default generators
            WorldGenerators.registerGenerator(FlatWorldGeneratorImpl.createInitializer());
            WorldGenerators.registerGenerator(VoidWorldGeneratorImpl.createInitializer());
            WorldGenerators.registerGenerator(TestWorldGeneratorImpl.createInitializer());
        }

        System.out.println("Loading worlds...");
        this.worldsManager.init(new File(options.valueOf("worldsdir").toString()));
        System.out.println("Worlds loaded.");

        try
        {
            System.setProperty("io.netty.eventLoopThreads", options.valueOf("netty").toString());
            System.out.println("Starting listening on " + this.hostname + ":" + this.port);
            this.serverConnection.init(InetAddress.getByName(this.hostname), this.port, true); // TODO: epoll

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
            long lastTick = System.nanoTime();
            long catchupTime = 0L;
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
                    lastTick = curTime;

                    this.playersManager.keepAlive();
                    this.worldsManager.doTick();
                }
            }
        } catch (final Throwable e)
        {
            e.printStackTrace();
            this.stop();
        }
    }

    public static ServerImpl getInstance()
    {
        return instance;
    }
}
