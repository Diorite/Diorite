/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2015 Diorite (by Bartłomiej Mazur (aka GotoFinal))
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
import java.io.PrintStream;
import java.net.Proxy;
import java.security.KeyPair;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.stream.Collectors;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import org.apache.logging.log4j.core.appender.ConsoleAppender;
import org.fusesource.jansi.AnsiConsole;
import org.reflections.Reflections;
import org.slf4j.LoggerFactory;

import org.diorite.impl.auth.SessionService;
import org.diorite.impl.auth.yggdrasil.YggdrasilSessionService;
import org.diorite.impl.cfg.DioriteConfigImpl;
import org.diorite.impl.command.ColoredConsoleCommandSenderImpl;
import org.diorite.impl.command.CommandMapImpl;
import org.diorite.impl.command.ConsoleCommandSenderImpl;
import org.diorite.impl.command.PluginCommandBuilderImpl;
import org.diorite.impl.command.defaults.RegisterDefaultCommands;
import org.diorite.impl.connection.ConnectionHandler;
import org.diorite.impl.connection.CoreNetworkManager;
import org.diorite.impl.connection.MinecraftEncryption;
import org.diorite.impl.connection.packets.play.server.PacketPlayServerChat;
import org.diorite.impl.connection.packets.play.server.PacketPlayServerPlayerListHeaderFooter;
import org.diorite.impl.connection.packets.play.server.PacketPlayServerTitle;
import org.diorite.impl.entity.PlayerImpl;
import org.diorite.impl.input.ConsoleReaderThread;
import org.diorite.impl.input.InputThread;
import org.diorite.impl.log.ForwardLogHandler;
import org.diorite.impl.log.LoggerOutputStream;
import org.diorite.impl.log.TerminalConsoleWriterThread;
import org.diorite.impl.metrics.Metrics;
import org.diorite.impl.pipelines.event.chunk.ChunkGeneratePipelineImpl;
import org.diorite.impl.pipelines.event.chunk.ChunkLoadPipelineImpl;
import org.diorite.impl.pipelines.event.chunk.ChunkPopulatePipelineImpl;
import org.diorite.impl.pipelines.event.chunk.ChunkUnloadPipelineImpl;
import org.diorite.impl.pipelines.event.input.CommandPipelineImpl;
import org.diorite.impl.pipelines.event.input.TabCompletePipelineImpl;
import org.diorite.impl.pipelines.event.player.BlockDestroyPipelineImpl;
import org.diorite.impl.pipelines.event.player.BlockPlacePipelineImpl;
import org.diorite.impl.pipelines.event.player.ChatPipelineImpl;
import org.diorite.impl.pipelines.event.player.InteractPipelineImpl;
import org.diorite.impl.pipelines.event.player.InventoryClickPipelineImpl;
import org.diorite.impl.pipelines.event.player.JoinPipelineImpl;
import org.diorite.impl.pipelines.event.player.QuitPipelineImpl;
import org.diorite.impl.pipelines.system.CoreInitPipeline;
import org.diorite.impl.pipelines.system.CoreInitPipeline.InitData;
import org.diorite.impl.pipelines.system.CoreStartPipeline;
import org.diorite.impl.plugin.CoreJarPluginLoader;
import org.diorite.impl.plugin.FakePluginLoader;
import org.diorite.impl.plugin.JarPluginLoader;
import org.diorite.impl.plugin.PluginManagerImpl;
import org.diorite.impl.scheduler.SchedulerImpl;
import org.diorite.impl.world.WorldsManagerImpl;
import org.diorite.impl.world.tick.TickGroups;
import org.diorite.Core;
import org.diorite.Diorite;
import org.diorite.ItemFactory;
import org.diorite.ServerManager;
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
import org.diorite.event.pipelines.event.player.InteractPipeline;
import org.diorite.event.pipelines.event.player.InventoryClickPipeline;
import org.diorite.event.pipelines.event.player.JoinPipeline;
import org.diorite.event.pipelines.event.player.QuitPipeline;
import org.diorite.event.player.PlayerBlockDestroyEvent;
import org.diorite.event.player.PlayerBlockPlaceEvent;
import org.diorite.event.player.PlayerChatEvent;
import org.diorite.event.player.PlayerInteractEvent;
import org.diorite.event.player.PlayerInventoryClickEvent;
import org.diorite.event.player.PlayerJoinEvent;
import org.diorite.event.player.PlayerQuitEvent;
import org.diorite.plugin.DioritePlugin;
import org.diorite.plugin.PluginException;
import org.diorite.plugin.PluginManager;
import org.diorite.scheduler.Scheduler;
import org.diorite.scheduler.Synchronizable;
import org.diorite.utils.DioriteUtils;
import org.diorite.utils.SpammyError;
import org.diorite.utils.timings.TimingsManager;

import javassist.ClassPool;
import javassist.LoaderClassPath;
import jline.UnsupportedTerminal;
import jline.console.ConsoleReader;
import joptsimple.OptionSet;

public class DioriteCore implements Core
{

    private static final CoreInitPipeline  initPipeline;
    private static final CoreStartPipeline startPipeline;
    private static       DioriteCore       instance;

    private static final org.slf4j.Logger coreLogger = LoggerFactory.getLogger("");

    private static class SimpleSyncTask
    {
        private final Synchronizable sync;
        private final Runnable       runnable;

        private SimpleSyncTask(final Synchronizable sync, final Runnable runnable)
        {
            this.sync = sync;
            this.runnable = runnable;
        }

        public Synchronizable getSynchronizable()
        {
            return this.sync;
        }

        public void run()
        {
            this.runnable.run();
        }

        @Override
        public String toString()
        {
            return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("sync", this.sync).append("runnable", this.runnable).toString();
        }
    }

    static
    {
        ClassPool.getDefault().appendClassPath(new LoaderClassPath(DioriteCore.class.getClassLoader()));
        LoggerInit.init();
    }

    private static final class LoggerInit
    {
        private static ConsoleReader reader;
        private static Boolean       coloredConsole;

        private static void init()
        {
            if (System.console() == null)
            {
                System.setProperty("jline.terminal", "jline.UnsupportedTerminal");
                CoreMain.useJline = false;
            }
            try
            {
                reader = new ConsoleReader(System.in, System.out);
                reader.setExpandEvents(false);
            } catch (final Throwable t)
            {
                t.printStackTrace();
                try
                {
                    System.setProperty("jline.terminal", "jline.UnsupportedTerminal");
                    System.setProperty("user.language", "en");
                    CoreMain.useJline = false;
                    reader = new ConsoleReader(System.in, System.out);
                    reader.setExpandEvents(false);
                } catch (final IOException e)
                {
                    coloredConsole = false;
                    e.printStackTrace();
                }
            }
            if (coloredConsole == null)
            {
                coloredConsole = true;
            }


            final String jline_UnsupportedTerminal = new String(new char[]{'j', 'l', 'i', 'n', 'e', '.', 'U', 'n', 's', 'u', 'p', 'p', 'o', 'r', 't', 'e', 'd', 'T', 'e', 'r', 'm', 'i', 'n', 'a', 'l'});
            final String jline_terminal = new String(new char[]{'j', 'l', 'i', 'n', 'e', '.', 't', 'e', 'r', 'm', 'i', 'n', 'a', 'l'});
            CoreMain.useJline = ! jline_UnsupportedTerminal.equals(System.getProperty(jline_terminal));
            if (CoreMain.useJline)
            {
                AnsiConsole.systemInstall();
            }
            else
            {
                System.setProperty("jline.terminal", UnsupportedTerminal.class.getName());
            }


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

            System.setOut(new PrintStream(new LoggerOutputStream(coreLogger, Level.INFO), true));
            System.setErr(new PrintStream(new LoggerOutputStream(coreLogger, Level.WARNING), true));
        }
    }

    {
        this.serverVersion = DioriteCore.class.getPackage().getImplementationVersion();
        if (LoggerInit.coloredConsole == null)
        {
            LoggerInit.init();
        }
        this.reader = LoggerInit.reader;
        this.consoleCommandSender = LoggerInit.coloredConsole ? ColoredConsoleCommandSenderImpl.getInstance(this) : new ConsoleCommandSenderImpl(this);
        coreLogger.info("Starting Diorite v" + this.getVersion() + " core...");
    }

    protected final boolean isClient;
    protected final CommandMapImpl                        commandMap  = new CommandMapImpl();
    protected final TickGroups                            ticker      = new TickGroups(this);
    protected final SchedulerImpl                         scheduler   = new SchedulerImpl();
    protected final ConcurrentLinkedQueue<SimpleSyncTask> syncQueue   = new ConcurrentLinkedQueue<>();
    protected final ItemFactory                           itemFactory = new ItemFactoryImpl();
    protected final Thread mainThread;
    protected final double[] recentTps = new double[3];
    private final String      serverVersion;
    protected     InputThread inputThread;
    protected String hostname           = "127.0.0.1";
    protected int    port               = - 1;
    protected int    tps                = DEFAULT_TPS;
    protected int    waitTime           = DEFAULT_WAIT_TIME;
    protected int    connectionThrottle = 1000;
    protected double mutli              = 1; // it can be used with TPS, like make 10 TPS but change this to 2, so server will scale to new TPS.
    protected YggdrasilSessionService  sessionService;
    protected ConnectionHandler        connectionHandler;
    protected PlayersManagerImpl       playersManager;
    protected WorldsManagerImpl        worldsManager;
    protected ConsoleCommandSenderImpl consoleCommandSender; //new ConsoleCommandSenderImpl(this);
    protected ConsoleReader            reader;
    protected long                     currentTick;
    protected int                      keepAliveTimer;
    protected DioriteConfigImpl        config;
    protected PluginManager            pluginManager;
    protected TimingsManager           timings;
    protected ServerManager            serverManager;
    protected                    KeyPair keyPair     = MinecraftEncryption.generateKeyPair();
    protected transient volatile boolean isRunning   = true;
    protected transient volatile boolean hasStopped  = false;
    protected transient volatile boolean startedCore = false;
    private Metrics metrics;

    public DioriteCore(final Proxy proxy, final OptionSet options, final boolean isClient)
    {
        this.isClient = isClient;
        instance = this;
        this.mainThread = Thread.currentThread();
        Diorite.setCore(this);

        this.loadConfigFile((File) options.valueOf("config"));
        if (this.config == null)
        {
            throw new AssertionError("Configuration instance is null after creating!");
        }
        this.loadCoreMods();
        this.startedCore = true;

        initPipeline.run(this, new InitData(options, proxy, isClient));
    }

    @Override
    public org.slf4j.Logger getLogger()
    {
        return coreLogger;
    }

    public boolean isStartedCore()
    {
        return this.startedCore;
    }

    public int getCompressionThreshold()
    {
        return this.config.getNetworkCompressionThreshold();
    }

    public void setCompressionThreshold(final int compressionThreshold)
    {
        this.config.setNetworkCompressionThreshold(compressionThreshold);
    }

    public void sync(final Runnable runnable, final Synchronizable sync)
    {
        //noinspection ObjectEquality
        if (Thread.currentThread() == sync.getLastTickThread())
        {
            runnable.run();
            return;
        }
        this.syncQueue.add(new SimpleSyncTask(sync, runnable));
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
        this.syncQueue.add(new SimpleSyncTask(sync, runnable));
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
    public String getVersion()
    {
        if (this.serverVersion == null)
        {
            SpammyError.err("Missing server version!", (int) TimeUnit.HOURS.toSeconds(1), "serverVersion");
            return "Unknown" + " (MC: " + Core.getMinecraftVersion() + ")";
        }
        return this.serverVersion + " (MC: " + Core.getMinecraftVersion() + ")";
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
    public ItemFactory getItemFactory()
    {
        return this.itemFactory;
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

    @Override
    public DioriteConfigImpl getConfig()
    {
        return this.config;
    }

    public void setConfig(final DioriteConfigImpl config)
    {
        this.config = config;
    }

    @Override
    public WorldsManagerImpl getWorldsManager()
    {
        return this.worldsManager;
    }

    public void setWorldsManager(final WorldsManagerImpl worldsManager)
    {
        this.worldsManager = worldsManager;
    }

    @Override
    public Scheduler getScheduler()
    {
        return this.scheduler;
    }

    @Override
    public TimingsManager getTimings()
    {
        return this.timings;
    }

    @Override
    public void broadcastMessage(final ChatPosition position, final BaseComponent component)
    {
        this.playersManager.forEach(new PacketPlayServerChat(component, position));
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
    public double getSpeedMutli()
    {
        return this.mutli;
    }

    @Override
    public void setSpeedMutli(final double mutli)
    {
        this.mutli = mutli;
    }

    @Override
    public int getTps()
    {
        return this.tps;
    }

    @Override
    public void resetRecentTps()
    {
        Arrays.fill(this.recentTps, this.tps);
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

    @Override
    public void updatePlayerListHeaderAndFooter(final BaseComponent header, final BaseComponent footer)
    {
        this.getOnlinePlayers().forEach((player) -> this.updatePlayerListHeaderAndFooter(header, footer, player));
    }

    @Override
    public void updatePlayerListHeaderAndFooter(final BaseComponent header, final BaseComponent footer, final Player player)
    {
        ((PlayerImpl) player).getNetworkManager().sendPacket(new PacketPlayServerPlayerListHeaderFooter(header, footer));
    }

    @Override
    public void broadcastTitle(final BaseComponent title, final BaseComponent subtitle, final int fadeIn, final int stay, final int fadeOut)
    {
        this.playersManager.forEach((player) -> this.sendTitle(title, subtitle, fadeIn, stay, fadeOut, player));
    }

    @Override
    public void sendTitle(final BaseComponent title, final BaseComponent subtitle, final int fadeIn, final int stay, final int fadeOut, final Player player)
    {
        final CoreNetworkManager n = ((PlayerImpl) player).getNetworkManager();

        if (title != null)
        {
            n.sendPacket(new PacketPlayServerTitle(PacketPlayServerTitle.TitleAction.SET_TITLE, title));
        }

        if (subtitle != null)
        {
            n.sendPacket(new PacketPlayServerTitle(PacketPlayServerTitle.TitleAction.SET_SUBTITLE, subtitle));
        }

        n.sendPacket(new PacketPlayServerTitle(PacketPlayServerTitle.TitleAction.SET_TIMES, fadeIn, stay, fadeOut));
    }

    @Override
    public void removeTitle(final Player player)
    {
        ((PlayerImpl) player).getNetworkManager().sendPacket(new PacketPlayServerTitle(PacketPlayServerTitle.TitleAction.RESET));
    }

    @Override
    public void removeAllTitles()
    {
        this.playersManager.forEach(new PacketPlayServerTitle(PacketPlayServerTitle.TitleAction.RESET));
    }

    @Override
    public PluginManager getPluginManager()
    {
        return this.pluginManager;
    }

    public void setPluginManager(final PluginManager pluginManager)
    {
        this.pluginManager = pluginManager;
    }

    @Override
    public CommandMapImpl getCommandMap()
    {
        return this.commandMap;
    }

    @Override
    public PluginCommandBuilderImpl createCommand(final DioritePlugin dioritePlugin, final String name)
    {
        return PluginCommandBuilderImpl.start(dioritePlugin, name);
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

    protected void loadConfigFile(final File f)
    {
        final Template<DioriteConfigImpl> cfgTemp = TemplateCreator.getTemplate(DioriteConfigImpl.class);
        if (f.exists())
        {
            try
            {
                this.config = cfgTemp.load(f);
                if (this.config == null)
                {
                    this.config = cfgTemp.fillDefaults(new DioriteConfigImpl());
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
        try
        {
            cfgTemp.dump(f, this.config, false);
        } catch (final IOException e)
        {
            throw new RuntimeException("Can't dump configuration file!", e);
        }
    }

    private void checkPluginManager()
    {
        if (this.playersManager == null)
        {
            this.pluginManager = new PluginManagerImpl(this.config.getPluginsDirectory());
        }
        this.pluginManager.registerPluginLoader(new FakePluginLoader());
        this.pluginManager.registerPluginLoader(new JarPluginLoader());
        this.pluginManager.registerPluginLoader(new CoreJarPluginLoader());

    }

    private void loadPlugins()
    {
        this.checkPluginManager();
        final File dir = this.pluginManager.getDirectory();
        if (dir.exists() && ! dir.isDirectory())
        {
            throw new RuntimeException("Plugin directory must be a folder!");
        }
        CoreMain.debug("Plugins directory is: " + dir.getAbsolutePath());
        if (! dir.exists())
        {
            dir.mkdirs();
            CoreMain.debug("Created plugins directory...");
        }

        final File[] files = dir.listFiles();
        if (files == null)
        {
            throw new RuntimeException("Plugin directory must be a folder!");
        }
        for (final File file : files)
        {
            if (file.isDirectory())
            {
                continue;
            }

            try
            {
                this.pluginManager.loadPlugin(file);
            } catch (final PluginException e)
            {
                e.printStackTrace();
            }
        }

        System.out.println("Loaded " + this.pluginManager.getPlugins().size() + " plugins and core mods!");
    }

    private void loadCoreMods()
    {
        this.checkPluginManager();

        final File dir = this.pluginManager.getDirectory();
        if (dir.exists() && ! dir.isDirectory())
        {
            throw new RuntimeException("Plugin directory must be a folder!");
        }
        CoreMain.debug("Plugins directory is: " + dir.getAbsolutePath());
        if (! dir.exists())
        {
            dir.mkdirs();
            CoreMain.debug("Created plugins directory...");
        }

        final File[] files = dir.listFiles();
        if (files == null)
        {
            throw new RuntimeException("Plugin directory must be a folder!");
        }
        for (final File file : files)
        {
            if (file.isDirectory() || ! file.getName().toLowerCase().endsWith(CoreJarPluginLoader.CORE_JAR_SUFFIX.toLowerCase()))
            {
                continue;
            }

            try
            {
                this.pluginManager.loadPlugin(file);
            } catch (final PluginException e)
            {
                e.printStackTrace();
            }
        }

        System.out.println("Loaded " + this.pluginManager.getPlugins().size() + " core mods!");
    }

    private void registerEvents()
    {
        EventType.register(SenderCommandEvent.class, CommandPipeline.class, new CommandPipelineImpl());
        EventType.register(SenderTabCompleteEvent.class, TabCompletePipeline.class, new TabCompletePipelineImpl());

        EventType.register(PlayerChatEvent.class, ChatPipeline.class, new ChatPipelineImpl());

        EventType.register(PlayerBlockDestroyEvent.class, BlockDestroyPipeline.class, new BlockDestroyPipelineImpl());
        EventType.register(PlayerBlockPlaceEvent.class, BlockPlacePipeline.class, new BlockPlacePipelineImpl());
        EventType.register(PlayerInventoryClickEvent.class, InventoryClickPipeline.class, new InventoryClickPipelineImpl());
        EventType.register(PlayerJoinEvent.class, JoinPipeline.class, new JoinPipelineImpl());
        EventType.register(PlayerQuitEvent.class, QuitPipeline.class, new QuitPipelineImpl());
        EventType.register(PlayerInteractEvent.class, InteractPipeline.class, new InteractPipelineImpl());

        EventType.register(ChunkLoadEvent.class, ChunkLoadPipeline.class, new ChunkLoadPipelineImpl());
        EventType.register(ChunkUnloadEvent.class, ChunkUnloadPipeline.class, new ChunkUnloadPipelineImpl());
        EventType.register(ChunkGenerateEvent.class, ChunkGeneratePipeline.class, new ChunkGeneratePipelineImpl());
        EventType.register(ChunkPopulateEvent.class, ChunkPopulatePipeline.class, new ChunkPopulatePipelineImpl());
    }

    public InputThread getInputThread()
    {
        return this.inputThread;
    }

    public void setInputThread(final InputThread inputThread)
    {
        this.inputThread = inputThread;
    }

    public int getKeepAliveTimer()
    {
        return this.keepAliveTimer;
    }

    public int getPlayerTimeout()
    {
        return this.config.getPlayerIdleTimeout();
    }

    public boolean isClient()
    {
        return this.isClient;
    }

    public String getServerVersion()
    {
        return this.serverVersion;
    }

    public Thread getMainThread()
    {
        return this.mainThread;
    }

    public long getCurrentTick()
    {
        return this.currentTick;
    }

    public double getMutli()
    {
        return this.mutli;
    }

    public synchronized void onStop()
    {
        if (this.hasStopped)
        {
            return;
        }
        PluginManagerImpl.saveCache();
        if (this.metrics != null)
        {
            this.metrics.stop();
        }
        if (this.pluginManager != null)
        {
            this.pluginManager.disablePlugins();
        }
        this.hasStopped = true;
        this.isRunning = false;
        if (this.playersManager != null)
        {
            this.playersManager.forEach(p -> p.kick("§4Server closed!"));
        }
        if (this.connectionHandler != null)
        {
            this.connectionHandler.close();
        }
        if (this.worldsManager != null)
        {
            this.worldsManager.getWorlds().stream().forEach(w -> {
                w.save(true);
                w.getChunkManager().getService().await(i -> coreLogger.info("[" + w.getName() + "] Queue: " + i + " left."));
            });
            CoreMain.debug("done?");
        }
        System.out.println("Goodbye <3");
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

    public ConnectionHandler getConnectionHandler()
    {
        return this.connectionHandler;
    }

    public void setConnectionHandler(final ConnectionHandler connectionHandler)
    {
        this.connectionHandler = connectionHandler;
    }

    public void setConsoleCommandSender(final ConsoleCommandSenderImpl consoleCommandSender)
    {
        this.consoleCommandSender = consoleCommandSender;
    }

    public PlayersManagerImpl getPlayersManager()
    {
        return this.playersManager;
    }

    public void setPlayersManager(final PlayersManagerImpl playersManager)
    {
        this.playersManager = playersManager;
    }

    @Override
    public ServerManager getServerManager()
    {
        return this.serverManager;
    }

    public void setServerManager(final ServerManager serverManager)
    {
        this.serverManager = serverManager;
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

    public void setPort(final int port)
    {
        this.port = port;
    }

    public String getHostname()
    {
        return this.hostname;
    }

    public void setHostname(final String hostname)
    {
        this.hostname = hostname;
    }

    public Thread getMainServerThread()
    {
        return this.mainThread;
    }

    public String getServerModName()
    {
        return NANE + " v" + this.getVersion();
    }

    public ConsoleReader getReader()
    {
        return this.reader;
    }

    public void setReader(final ConsoleReader reader)
    {
        this.reader = reader;
    }

    public SessionService getSessionService()
    {
        return this.sessionService;
    }

    public void setSessionService(final YggdrasilSessionService sessionService)
    {
        this.sessionService = sessionService;
    }

    protected void start(final OptionSet options)
    {
        startPipeline.run(this, options);
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
        PluginManagerImpl.saveCache();
        this.metrics = Metrics.start(this);
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
        CoreMain.debug("Main loop finished, stopping server. (" + this.hasStopped + ")");
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

    public static CoreInitPipeline getInitPipeline()
    {
        return initPipeline;
    }

    public static CoreStartPipeline getStartPipeline()
    {
        return startPipeline;
    }

    private static double calcTps(final double avg, final double exp, final double tps)
    {
        return (avg * exp) + (tps * (1.0D - exp));
    }

    public static DioriteCore getInstance()
    {
        return instance;
    }

    static
    {
        Reflections.log = null;
        initPipeline = new CoreInitPipeline();
        initPipeline.addLast("DioriteCore|LoadBasicSettings", (s, p, d) -> {
            s.keepAliveTimer = (int) d.options.valueOf("keepalivetimer");

            if (d.options.has("online"))
            {
                final OnlineMode mode = OnlineMode.valueOf(d.options.valueOf("online").toString().toUpperCase());
                if (mode != null)
                {
                    s.config.setOnlineMode(mode);
                }
            }
        });
        initPipeline.addLast("DioriteCore|registerEvents", (s, p, d) -> s.registerEvents());
        initPipeline.addLast("DioriteCore|initSessionService", (s, p, d) -> s.sessionService = new YggdrasilSessionService(d.proxy, UUID.randomUUID().toString()));
        initPipeline.addLast("DioriteCore|addShutdownHook", (s, p, d) -> Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try
            {
                s.onStop();
            } catch (final Exception e)
            {
                e.printStackTrace();
            }
        })));
        initPipeline.addLast("DioriteCore|RegisterDefaultCommands", (s, p, d) -> RegisterDefaultCommands.init(s.commandMap));
        initPipeline.addLast("DioriteCore|initInputThread", (s, p, d) -> s.inputThread = InputThread.start(s.config.getInputThreadPoolSize()));
        initPipeline.addLast("DioriteCore|initGame", (s, p, d) -> {
            s.serverManager = new ServerManagerImpl(s);
            s.playersManager = new PlayersManagerImpl(s);
            s.worldsManager = new WorldsManagerImpl();
        });
        initPipeline.addLast("DioriteCore|initTimings", (s, p, d) -> s.timings = new TimingsManagerImpl());

        startPipeline = new CoreStartPipeline();
        startPipeline.addLast("DioriteCore|EnableMods", (s, pipeline, options) -> s.getPluginManager().getPlugins().stream().filter(p -> ! p.isEnabled() && p.isCoreMod()).forEach(p -> p.setEnabled(true)));
        startPipeline.addLast("DioriteCore|LoadPlugins", (s, p, options) -> s.loadPlugins());

        startPipeline.addLast("DioriteCore|EnablePlugins", (s, pipeline, options) -> s.pluginManager.getPlugins().stream().filter(p -> ! p.isEnabled()).forEach(p -> p.setEnabled(true)));
        // TODO configuration and other shit.
        startPipeline.addLast("DioriteCore|ConsoleReaderThreadStart", (s, p, options) -> ConsoleReaderThread.start(s));
    }
}
