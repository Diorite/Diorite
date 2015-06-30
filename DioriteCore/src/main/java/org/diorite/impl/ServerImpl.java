package org.diorite.impl;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.Proxy;
import java.net.UnknownHostException;
import java.security.KeyPair;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.ConcurrentLinkedQueue;
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
import org.diorite.impl.cfg.DioriteConfigImpl;
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
import org.diorite.impl.input.ConsoleReaderThread;
import org.diorite.impl.input.InputThread;
import org.diorite.impl.log.ForwardLogHandler;
import org.diorite.impl.log.LoggerOutputStream;
import org.diorite.impl.log.TerminalConsoleWriterThread;
import org.diorite.impl.pipelines.event.chunk.ChunkGeneratePipelineImpl;
import org.diorite.impl.pipelines.event.chunk.ChunkLoadPipelineImpl;
import org.diorite.impl.pipelines.event.chunk.ChunkPopulatePipelineImpl;
import org.diorite.impl.pipelines.event.chunk.ChunkUnloadPipelineImpl;
import org.diorite.impl.pipelines.event.input.CommandPipelineImpl;
import org.diorite.impl.pipelines.event.input.TabCompletePipelineImpl;
import org.diorite.impl.pipelines.event.player.BlockDestroyPipelineImpl;
import org.diorite.impl.pipelines.event.player.BlockPlacePipelineImpl;
import org.diorite.impl.pipelines.event.player.ChatPipelineImpl;
import org.diorite.impl.pipelines.event.player.InventoryClickPipelineImpl;
import org.diorite.impl.plugin.JarPluginLoader;
import org.diorite.impl.plugin.PluginManagerImpl;
import org.diorite.impl.scheduler.SchedulerImpl;
import org.diorite.impl.world.WorldsManagerImpl;
import org.diorite.impl.world.generator.FlatWorldGeneratorImpl;
import org.diorite.impl.world.generator.TestWorldGeneratorImpl;
import org.diorite.impl.world.generator.VoidWorldGeneratorImpl;
import org.diorite.impl.world.tick.TickGroups;
import org.diorite.Diorite;
import org.diorite.Server;
import org.diorite.cfg.DioriteConfig.OnlineMode;
import org.diorite.cfg.system.Template;
import org.diorite.cfg.system.TemplateCreator;
import org.diorite.chat.ChatPosition;
import org.diorite.chat.component.BaseComponent;
import org.diorite.entity.Player;
import org.diorite.event.EventType;
import org.diorite.event.chunk.ChunkGenerateEvent;
import org.diorite.event.chunk.ChunkLoadEvent;
import org.diorite.event.chunk.ChunkPopulateEvent;
import org.diorite.event.chunk.ChunkUnloadEvent;
import org.diorite.event.input.SenderCommandEvent;
import org.diorite.event.input.SenderTabCompleteEvent;
import org.diorite.event.pipelines.event.chunk.ChunkGeneratePipeline;
import org.diorite.event.pipelines.event.chunk.ChunkLoadPipeline;
import org.diorite.event.pipelines.event.chunk.ChunkPopulatePipeline;
import org.diorite.event.pipelines.event.chunk.ChunkUnloadPipeline;
import org.diorite.event.pipelines.event.input.CommandPipeline;
import org.diorite.event.pipelines.event.input.TabCompletePipeline;
import org.diorite.event.pipelines.event.player.BlockDestroyPipeline;
import org.diorite.event.pipelines.event.player.BlockPlacePipeline;
import org.diorite.event.pipelines.event.player.ChatPipeline;
import org.diorite.event.pipelines.event.player.InventoryClickPipeline;
import org.diorite.event.player.PlayerBlockDestroyEvent;
import org.diorite.event.player.PlayerBlockPlaceEvent;
import org.diorite.event.player.PlayerChatEvent;
import org.diorite.event.player.PlayerInventoryClickEvent;
import org.diorite.plugin.PluginMainClass;
import org.diorite.plugin.PluginManager;
import org.diorite.scheduler.Scheduler;
import org.diorite.scheduler.Synchronizable;
import org.diorite.utils.DioriteUtils;
import org.diorite.world.World;
import org.diorite.world.WorldsManager;
import org.diorite.world.generator.WorldGenerators;

import jline.console.ConsoleReader;
import joptsimple.OptionSet;

public class ServerImpl implements Server
{
    private static ServerImpl instance;

    protected final CommandMapImpl                        commandMap = new CommandMapImpl();
    protected final TickGroups                            ticker     = new TickGroups(this);
    protected final SchedulerImpl                         scheduler  = new SchedulerImpl();
    protected final ConcurrentLinkedQueue<SimpleSyncTask> syncQueue  = new ConcurrentLinkedQueue<>();
    protected final Thread      mainThread;
    protected final InputThread inputThread;
    protected final String      hostname;
    protected final int         port;
    protected int    tps                = DEFAULT_TPS;
    protected int    waitTime           = DEFAULT_WAIT_TIME;
    protected int    connectionThrottle = 1000;
    protected double mutli              = 1; // it can be used with TPS, like make 10 TPS but change this to 2, so server will scale to new TPS.
    protected final YggdrasilSessionService  sessionService;
    protected final ServerConnection         serverConnection;
    protected final PlayersManagerImpl       playersManager;
    protected final WorldsManagerImpl        worldsManager;
    protected       ConsoleCommandSenderImpl consoleCommandSender; //new ConsoleCommandSenderImpl(this);
    protected       ConsoleReader            reader;
    protected       long                     currentTick;
    protected final int                      keepAliveTimer;
    protected       DioriteConfigImpl        config;
    protected       PluginManager            pluginManager;
    protected       File                     pluginsDirectory = new File("plugins"); // TODO Allow to change this
    private final              double[] recentTps  = new double[3];
    private                    KeyPair  keyPair    = MinecraftEncryption.generateKeyPair();
    private transient volatile boolean  isRunning  = true;
    private transient volatile boolean  hasStopped = false;

    public int getCompressionThreshold()
    {
        return this.config.getNetworkCompressionThreshold();
    }

    public void setCompressionThreshold(final int compressionThreshold)
    {
        this.config.setNetworkCompressionThreshold(compressionThreshold);
    }

    private interface SimpleSyncTask extends Runnable
    {
        Synchronizable getSynchronizable();
    }

    public void sync(final Runnable runnable, final Synchronizable sync)
    {
        //noinspection ObjectEquality
        if (Thread.currentThread() == sync.getLastTickThread())
        {
            runnable.run();
            return;
        }
        this.syncQueue.add(new SimpleSyncTask()
        {
            @Override
            public Synchronizable getSynchronizable()
            {
                return sync;
            }

            @Override
            public void run()
            {
                runnable.run();
            }
        });
    }

    public void sync(final Runnable runnable)
    {
        //noinspection ObjectEquality
        if (Thread.currentThread() == this.mainThread)
        {
            runnable.run();
            return;
        }
        this.sync(runnable, this);
    }

    public void addSync(final Runnable runnable, final Synchronizable sync)
    {
        this.syncQueue.add(new SimpleSyncTask()
        {
            @Override
            public Synchronizable getSynchronizable()
            {
                return sync;
            }

            @Override
            public void run()
            {
                runnable.run();
            }
        });
    }

    public void addSync(final Runnable runnable)
    {
        this.addSync(runnable, this);
    }

    public void runSync()
    {
        for (final Iterator<SimpleSyncTask> it = this.syncQueue.iterator(); it.hasNext(); )
        {
            final SimpleSyncTask st = it.next();
            //noinspection ObjectEquality
            if (st.getSynchronizable().getLastTickThread() == Thread.currentThread())
            {
                st.run();
                it.remove();
            }
        }
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

    private void loadConfigFile(final File f)
    {
        final Template<DioriteConfigImpl> cfgTemp = TemplateCreator.getTemplate(DioriteConfigImpl.class);
        boolean needWrite = true;
        if (f.exists())
        {
            try
            {
                this.config = cfgTemp.load(f);
                if (this.config == null)
                {
                    this.config = cfgTemp.fillDefaults(new DioriteConfigImpl());
                }
                else
                {
                    needWrite = false;
                }
            } catch (final IOException e)
            {
                throw new RuntimeException("IO exception when loading config file: " + f, e);
            }
        }
        else
        {
            this.config = cfgTemp.fillDefaults(new DioriteConfigImpl());
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
                cfgTemp.dump(f, this.config, false);
            } catch (final IOException e)
            {
                throw new RuntimeException("Can't dump configuration file!", e);
            }
        }
    }

    private void loadPlugins()
    {
        this.pluginManager = new PluginManagerImpl();
        this.pluginManager.registerPluginLoader(new JarPluginLoader());

        Main.debug("Plugins directory is: " + this.pluginsDirectory.getAbsolutePath());
        if (! this.pluginsDirectory.exists())
        {
            this.pluginsDirectory.mkdir();
            Main.debug("Created plugins directory...");
        }

        for (final File file : this.pluginsDirectory.listFiles())
        {
            if (file.isDirectory())
            {
                continue;
            }

            this.pluginManager.loadPlugin(file);
        }

        Main.debug("Loaded " + this.pluginManager.getPlugins().size() + " plugins!");
    }

    private void registerEvents()
    {
        EventType.register(SenderCommandEvent.class, CommandPipeline.class, new CommandPipelineImpl());
        EventType.register(SenderTabCompleteEvent.class, TabCompletePipeline.class, new TabCompletePipelineImpl());

        EventType.register(PlayerChatEvent.class, ChatPipeline.class, new ChatPipelineImpl());

        EventType.register(PlayerBlockDestroyEvent.class, BlockDestroyPipeline.class, new BlockDestroyPipelineImpl());
        EventType.register(PlayerBlockPlaceEvent.class, BlockPlacePipeline.class, new BlockPlacePipelineImpl());
        EventType.register(PlayerInventoryClickEvent.class, InventoryClickPipeline.class, new InventoryClickPipelineImpl());

        EventType.register(ChunkLoadEvent.class, ChunkLoadPipeline.class, new ChunkLoadPipelineImpl());
        EventType.register(ChunkUnloadEvent.class, ChunkUnloadPipeline.class, new ChunkUnloadPipelineImpl());
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

    public ServerImpl(final Proxy proxy, final OptionSet options)
    {
        instance = this;
        this.mainThread = Thread.currentThread();
        Diorite.setServer(this);
        this.loadConfigFile((File) options.valueOf("config"));
        if (this.config == null)
        {
            throw new AssertionError("Configuration instance is null after creating!");
        }
        this.keepAliveTimer = (int) options.valueOf("keepalivetimer");

        this.hostname = options.has("hostname") ? options.valueOf("hostname").toString() : this.config.getHostname();
        this.port = options.has("port") ? (int) options.valueOf("port") : this.config.getPort();

        if (options.has("online"))
        {
            final OnlineMode mode = OnlineMode.valueOf(options.valueOf("online").toString().toUpperCase());
            if (mode != null)
            {
                this.config.setOnlineMode(mode);
            }
        }
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
                this.onStop();
            } catch (final Exception e)
            {
                e.printStackTrace();
            }
        }));

        RegisterDefaultCommands.init(this.commandMap);

        this.inputThread = InputThread.start(this.config.getInputThreadPoolSize());

        this.playersManager = new PlayersManagerImpl(this);
        this.worldsManager = new WorldsManagerImpl();

        this.serverConnection = new ServerConnection(this);
        this.serverConnection.start();

        this.loadPlugins();
    }

    public InputThread getInputThread()
    {
        return this.inputThread;
    }

    @Override
    public DioriteConfigImpl getConfig()
    {
        return this.config;
    }

    public int getKeepAliveTimer()
    {
        return this.keepAliveTimer;
    }

    public int getPlayerTimeout()
    {
        return this.config.getPlayerIdleTimeout();
    }

    @Override
    public WorldsManager getWorldsManager()
    {
        return this.worldsManager;
    }

    @Override
    public Scheduler getScheduler()
    {
        return this.scheduler;
    }

    @Override
    public void broadcastMessage(final ChatPosition position, final BaseComponent component)
    {
        this.playersManager.forEach(new PacketPlayOutChat(component, position));
        if (! Objects.equals(position, ChatPosition.ACTION))
        {
            this.sendConsoleMessage(component);
        }
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
        return (byte) this.config.getViewDistance();
    }

    @Override
    public void setRenderDistance(final byte renderDistance)
    {
        this.config.setViewDistance(renderDistance);
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
            return;
        }
        this.isRunning = false;
        // TODO
    }

    public synchronized void onStop()
    {
        if (this.hasStopped)
        {
            return;
        }
        this.hasStopped = true;
        this.isRunning = false;
        if (this.playersManager != null)
        {
            this.playersManager.forEach(p -> p.kick("§4Server closed!"));
        }
        if (this.worldsManager != null)
        {
            this.worldsManager.getWorlds().stream().forEach(World::save);
            Main.debug("done?");
        }
        if (this.serverConnection != null)
        {
            this.serverConnection.close();
        }
        System.out.println("Goodbye <3");
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
    public PluginManager getPluginManager()
    {
        return null;
    }

    @Override
    public CommandMapImpl getCommandMap()
    {
        return this.commandMap;
    }

    @Override
    public PluginCommandBuilderImpl createCommand(final PluginMainClass pluginMainClass, final String name)
    {
        return PluginCommandBuilderImpl.start(pluginMainClass, name);
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

    public PlayersManagerImpl getPlayersManager()
    {
        return this.playersManager;
    }

    public OnlineMode getOnlineMode()
    {
        return this.config.getOnlineMode();
    }

    public void setOnlineMode(final OnlineMode onlineMode)
    {
        this.config.setOnlineMode(onlineMode);
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
        return this.mainThread;
    }

    public String getServerModName()
    {
        return NANE + " v" + Server.getVersion();
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
        System.out.println("Starting Diorite v" + Server.getVersion() + " server...");

        { // register default generators
            WorldGenerators.registerGenerator(FlatWorldGeneratorImpl.createInitializer());
            WorldGenerators.registerGenerator(VoidWorldGeneratorImpl.createInitializer());
            WorldGenerators.registerGenerator(TestWorldGeneratorImpl.createInitializer());
        }

        System.out.println("Loading worlds...");
        this.worldsManager.init(this.config, this.config.getWorlds().getWorldsDir());
        System.out.println("Worlds loaded.");

        try
        {
            System.setProperty("io.netty.eventLoopThreads", options.has("netty") ? options.valueOf("netty").toString() : Integer.toString(this.config.getNettyThreads()));
            System.out.println("Starting listening on " + this.hostname + ":" + this.port);
            this.serverConnection.init(InetAddress.getByName(this.hostname), this.port, this.config.isUseNativeTransport());

            System.out.println("Binded to " + this.hostname + ":" + this.port);
        } catch (final UnknownHostException e)
        {
            e.printStackTrace();
        }

        // TODO configuration and other shit.

        System.out.println("Started Diorite v" + Server.getVersion() + " server!");
        this.run();
    }

    public TickGroups getTicker()
    {
        return this.ticker;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).toString();
    }

    public void runScheduler(final boolean withAsync)
    {
        this.scheduler.tick(this.currentTick, withAsync);
    }

    public void run()
    {
        Arrays.fill(this.recentTps, (double) DEFAULT_TPS);

        try
        {
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

                    this.runScheduler(true);

                    this.runSync();

                    this.playersManager.doTick(this.tps);
                    this.ticker.doTick(this.tps);
                }
            }
        } catch (final Throwable e)
        {
            e.printStackTrace();
            this.onStop();
            return;
        }
        Main.debug("Main loop finished, stopping server. (" + this.hasStopped + ")");
        if (! this.hasStopped)
        {
            this.onStop();
        }
    }

    @Override
    public Thread getLastTickThread()
    {
        return this.mainThread;
    }

    @Override
    public boolean isValidSynchronizable()
    {
        return this.isRunning;
    }

    private static double calcTps(final double avg, final double exp, final double tps)
    {
        return (avg * exp) + (tps * (1.0D - exp));
    }

    public static ServerImpl getInstance()
    {
        return instance;
    }
}
