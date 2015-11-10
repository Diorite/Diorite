/*
 * Copyright 2011-2013 Tyler Blair. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are
 * permitted provided that the following conditions are met:
 *
 *    1. Redistributions of source code must retain the above copyright notice, this list of
 *       conditions and the following disclaimer.
 *
 *    2. Redistributions in binary form must reproduce the above copyright notice, this list
 *       of conditions and the following disclaimer in the documentation and/or other materials
 *       provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE AUTHOR ''AS IS'' AND ANY EXPRESS OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 * FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE AUTHOR OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 * ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 * The views and conclusions contained in the software and documentation are those of the
 * authors and contributors and should not be interpreted as representing official policies,
 * either expressed or implied, of anybody else.
 */
package org.diorite.impl.metrics;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.zip.GZIPOutputStream;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.impl.CoreMain;
import org.diorite.impl.DioriteCore;
import org.diorite.cfg.DioriteConfig.OnlineMode;
import org.diorite.entity.EntityType;
import org.diorite.utils.SpammyError;

import gnu.trove.iterator.TShortIntIterator;
import gnu.trove.map.TShortIntMap;
import gnu.trove.map.hash.TShortIntHashMap;

public class Metrics
{
    /**
     * Separator used by metric dount graphs.
     */
    public static final String GRAPH_SEPARATOR = "~=~";

    /**
     * The current revision number
     */
    private static final int REVISION = 7;

    /**
     * The base url of the metrics domain
     */
    private static final String BASE_URL = "http://report.mcstats.org/plugin/Diorite";

    /**
     * Interval of time to ping (in minutes)
     */
    private static final int PING_INTERVAL = 15;

    /**
     * All of the custom graphs to submit to metrics
     */
    private final Set<DynamicMetricsGraph> graphs = Collections.synchronizedSet(new HashSet<>(5));

    /**
     * Server instance
     */
    protected final DioriteCore core;

    /**
     * The thread submission is running on
     */
    private Thread thread = null;

    public static Metrics start(final DioriteCore core)
    {
        /**
         * Not all graphs will be visible on metrics, and some of them may be removed in future
         */

        final Metrics m = new Metrics(core);

        {
            final MetricsGraph graph = m.createGraph("RealAuthMode");
            switch (core.getOnlineMode())
            {
                case TRUE:
                    graph.addPlotter(new BooleanMetricsPlotter("Online"));
                    break;
                case FALSE:
                    graph.addPlotter(new BooleanMetricsPlotter("Offline"));
                    break;
                case AUTO:
                    graph.addPlotter(new BooleanMetricsPlotter("Auto"));
                    break;
                default:
                    graph.addPlotter(new BooleanMetricsPlotter("Unknown"));
            }
        }
        {
            final MetricsGraph graph = m.createGraph("UsedPlugins");
            graph.addPlotter(new MetricsPlotter("Plugins")
            {
                @Override
                public int getValue()
                {
                    return core.getPluginManager().getPlugins().size();
                }
            });
        }
        {
            final MetricsGraph graph = m.createGraph("EntitiesV1");
            graph.addPlotter(new MetricsPlotter("Entities")
            {
                @Override
                public int getValue()
                {
                    return core.getWorldsManager().getWorlds().stream().mapToInt(w -> w.getEntityTrackers().size()).sum();
                }
            });
        }
        {
            final DynamicMetricsGraph graph = new DynamicMetricsGraph("EntitiesV2")
            {
                @Override
                public Set<MetricsPlotter> getPlotters()
                {
                    final Set<MetricsPlotter> result = new HashSet<>(20);
                    //noinspection MagicNumber
                    final TShortIntMap map = new TShortIntHashMap(20, .1f, (short) 0, 0);
                    core.getWorldsManager().getWorlds().forEach(w -> w.getEntityTrackers().getStats().forEachEntry((id, amount) -> {
                        map.put(id, map.get(id) + amount);
                        return true;
                    }));
                    for (final TShortIntIterator it = map.iterator(); it.hasNext(); )
                    {
                        it.advance();
                        final EntityType type = EntityType.getByMinecraftId(it.key());
                        if (type != null)
                        {
                            result.add(new SimpleMetricsPlotter(type.getMcName(), it.value()));
                        }
                    }
                    return result;
                }
            };
            m.addGraph(graph);
        }
        /**
         * Testing, how that will look, may be removed in the future.
         * Seems to be stupid idea, but, whatever.
         */
        {
            m.addGraph(new PluginsMetricsGraph("UsedPluginsV2", core, false));
            m.addGraph(new PluginsMetricsGraph("UsedPluginsV3", core, true));
        }

        m.start();
        return m;
    }

    Metrics(final DioriteCore core)
    {
        if (core == null)
        {
            throw new IllegalArgumentException("Server cannot be null");
        }

        this.core = core;
    }

    /**
     * Get the full server version
     *
     * @return full server version
     */
    public String getFullServerVersion()
    {
        return DioriteCore.getInstance().getServerModName();
    }

    /**
     * Get the amount of players online
     *
     * @return amount of players online
     */
    public int getPlayersOnline()
    {
        return this.core.getPlayersManager().getRawPlayers().size();
    }

    /**
     * Construct and create a Graph that can be used to separate specific plotters to their own graphs on the metrics
     * website. Plotters can be added to the graph object returned.
     *
     * @param name The name of the graph
     *
     * @return Graph object created. Will never return NULL under normal circumstances unless bad parameters are given
     */
    public MetricsGraph createGraph(final String name)
    {
        if (name == null)
        {
            throw new IllegalArgumentException("Graph name cannot be null");
        }

        // Construct the graph object
        final MetricsGraph graph = new MetricsGraph(name);

        // Now we can add our graph
        this.graphs.add(graph);

        // and return back
        return graph;
    }

    /**
     * Add a Graph object to SpoutMetrics that represents data for the plugin that should be sent to the backend
     *
     * @param graph The name of the graph
     */
    public void addGraph(final DynamicMetricsGraph graph)
    {
        if (graph == null)
        {
            throw new IllegalArgumentException("Graph cannot be null");
        }

        this.graphs.add(graph);
    }

    public void stop()
    {
        final Thread t = this.thread;
        this.thread = null;
        t.interrupt();
    }

    /**
     * Start measuring statistics. This will immediately create an async repeating task as the plugin and send the
     * initial data to the metrics backend, and then after that it will post in increments of PING_INTERVAL * 1200
     * ticks.
     *
     * @return True if statistics measuring is running, otherwise false.
     */
    public boolean start()
    {
        // Did we opt out?
        if (this.isOptOut())
        {
            return false;
        }

        // Is metrics already running?
        if (this.thread != null)
        {
            return true;
        }

        this.thread = new Thread(new Runnable()
        {

            private boolean firstPost = true;

            private long nextPost = 0L;

            @Override
            public void run()
            {
                while (Metrics.this.core.isRunning() && (Metrics.this.thread != null))
                {
                    if ((this.nextPost == 0L) || (System.currentTimeMillis() > this.nextPost))
                    {
                        try
                        {
                            // We use the inverse of firstPost because if it is the first time we are posting,
                            // it is not a interval ping, so it evaluates to FALSE
                            // Each time thereafter it will evaluate to TRUE, i.e PING!
                            Metrics.this.postPlugin(! this.firstPost);

                            // After the first post we set firstPost to false
                            // Each post thereafter will be a ping
                            this.firstPost = false;
                            this.nextPost = System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(PING_INTERVAL);
                        } catch (final IOException e)
                        {
                            if (CoreMain.isEnabledDebug())
                            {
                                SpammyError.out("[Metrics] " + e.getMessage(), (int) TimeUnit.MINUTES.toSeconds(5), this.hashCode() + 2);
                            }
                        }
                    }

                    try
                    {
                        Thread.sleep(Math.min(Math.max(this.nextPost - System.currentTimeMillis(), 1), TimeUnit.MINUTES.toMillis(PING_INTERVAL)));
                    } catch (final InterruptedException e)
                    {
                        if (Metrics.this.thread != null)
                        {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }, "MCStats / Plugin Metrics");
        this.thread.start();

        return true;
    }

    /**
     * Has the server owner denied plugin metrics?
     *
     * @return true if metrics should be opted out of it
     */
    public boolean isOptOut()
    {
        return this.getUUID().isEmpty();
    }

    public String getUUID()
    {
        return this.core.getConfig().getMetricsUuid();
    }

    /**
     * Generic method that posts a plugin to the metrics website
     */
    private void postPlugin(final boolean isPing) throws IOException
    {
        final String serverVersion = this.getFullServerVersion();
        final int playersOnline = this.getPlayersOnline();

        // END server software specific section -- all code below does not use any code outside of this class / Java

        // Construct the post data
        final StringBuilder json = new StringBuilder(1024);
        json.append('{');

        // The plugin's description file containg all of the plugin data such as name, version, author, etc
        appendJSONPair(json, "guid", this.getUUID());
        appendJSONPair(json, "plugin_version", this.core.getVersion());
        appendJSONPair(json, "server_version", serverVersion);
        appendJSONPair(json, "players_online", Integer.toString(playersOnline));

        // New data as of R6
        final String osname = System.getProperty("os.name");
        String osarch = System.getProperty("os.arch");
        final String osversion = System.getProperty("os.version");
        final String java_version = System.getProperty("java.version");
        final int coreCount = Runtime.getRuntime().availableProcessors();

        // normalize os arch .. amd64 -> x86_64
        if (osarch.equals("amd64"))
        {
            osarch = "x86_64";
        }

        appendJSONPair(json, "osname", osname);
        appendJSONPair(json, "osarch", osarch);
        appendJSONPair(json, "osversion", osversion);
        appendJSONPair(json, "cores", Integer.toString(coreCount));
        appendJSONPair(json, "auth_mode", (this.core.getOnlineMode() == OnlineMode.FALSE) ? "0" : "1");
        appendJSONPair(json, "java_version", java_version);

        // If we're pinging, append it
        if (isPing)
        {
            appendJSONPair(json, "ping", "1");
        }

        if (! this.graphs.isEmpty())
        {
            synchronized (this.graphs)
            {
                json.append(',');
                json.append('"');
                json.append("graphs");
                json.append('"');
                json.append(':');
                json.append('{');

                boolean firstGraph = true;

                for (final DynamicMetricsGraph graph : this.graphs)
                {
                    final StringBuilder graphJson = new StringBuilder();
                    graphJson.append('{');

                    for (final MetricsPlotter plotter : graph.getPlotters())
                    {
                        appendJSONPair(graphJson, plotter.getColumnName(), Integer.toString(plotter.getValue()));
                    }

                    graphJson.append('}');

                    if (! firstGraph)
                    {
                        json.append(',');
                    }

                    json.append(escapeJSON(graph.getName()));
                    json.append(':');
                    json.append(graphJson);

                    firstGraph = false;
                }

                json.append('}');
            }
        }

        // close json
        json.append('}');

        // Create the url
        final URL url = new URL(BASE_URL);

        // Connect to the website
        final URLConnection connection;

        connection = url.openConnection();


        final byte[] uncompressed = json.toString().getBytes();
        final byte[] compressed = gzip(json.toString());

        // Headers
        connection.addRequestProperty("User-Agent", "MCStats/" + REVISION);
        connection.addRequestProperty("Content-Type", "application/json");
        connection.addRequestProperty("Content-Encoding", "gzip");
        connection.addRequestProperty("Content-Length", Integer.toString(compressed.length));
        connection.addRequestProperty("Accept", "application/json");
        connection.addRequestProperty("Connection", "close");

        connection.setDoOutput(true);

        if (CoreMain.isEnabledDebug())
        {
            SpammyError.out("[Metrics] Prepared request for Diorite uncompressed=" + uncompressed.length + " compressed=" + compressed.length, (int) TimeUnit.MINUTES.toSeconds(5), this.hashCode() + 1);
        }

        // Write the data
        try (final OutputStream os = connection.getOutputStream())
        {
            os.write(compressed);
            os.flush();
        }
        String response;
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream())))
        {
            response = reader.readLine();
        }

        if ((response == null) || response.startsWith("ERR") || response.startsWith("7"))
        {
            if (response == null)
            {
                response = "null";
            }
            else if (response.startsWith("7"))
            {
                response = response.substring(response.startsWith("7,") ? 2 : 1);
            }

            throw new IOException(response);
        }
        else
        {
            // Is this the first update this hour?
            if (response.equals("1") || response.contains("This is your first update this hour"))
            {
                synchronized (this.graphs)
                {
                    this.graphs.forEach(DynamicMetricsGraph::resetPlotters);
                }
            }
        }
    }

    /**
     * GZip compress a string of bytes.
     *
     * @param input string to compress.
     *
     * @return compressed a string of bytes.
     */
    public static byte[] gzip(final String input)
    {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream())
        {
            try (GZIPOutputStream gzos = new GZIPOutputStream(baos))
            {
                gzos.write(input.getBytes("UTF-8"));
            } catch (final IOException e)
            {
                e.printStackTrace();
            }
            return baos.toByteArray();
        } catch (final IOException e)
        {
            throw new RuntimeException(e);
        }
    }

    private static void appendJSONPair(final StringBuilder json, final String key, final String value)
    {
        boolean isValueNumeric = false;

        try
        {
            if (value.equals("0") || ! value.endsWith("0"))
            {
                Double.parseDouble(value);
                isValueNumeric = true;
            }
        } catch (final NumberFormatException e)
        {
            isValueNumeric = false;
        }

        if (json.charAt(json.length() - 1) != '{')
        {
            json.append(',');
        }

        json.append(escapeJSON(key));
        json.append(':');

        if (isValueNumeric)
        {
            json.append(value);
        }
        else
        {
            json.append(escapeJSON(value));
        }
    }

    /**
     * Escape a string to create a valid JSON string
     *
     * @param text
     *
     * @return
     */
    private static String escapeJSON(final String text)
    {
        final StringBuilder builder = new StringBuilder();

        builder.append('"');
        for (int index = 0; index < text.length(); index++)
        {
            final char chr = text.charAt(index);

            switch (chr)
            {
                case '"':
                case '\\':
                    builder.append('\\');
                    builder.append(chr);
                    break;
                case '\b':
                    builder.append("\\b");
                    break;
                case '\t':
                    builder.append("\\t");
                    break;
                case '\n':
                    builder.append("\\n");
                    break;
                case '\r':
                    builder.append("\\r");
                    break;
                default:
                    if (chr < ' ')
                    {
                        final String t = "000" + Integer.toHexString(chr);
                        builder.append("\\u").append(t.substring(t.length() - 4));
                    }
                    else
                    {
                        builder.append(chr);
                    }
                    break;
            }
        }
        builder.append('"');

        return builder.toString();
    }

    /**
     * Encode text as UTF-8
     *
     * @param text the text to encode
     *
     * @return the encoded text, as UTF-8
     */
    private static String urlEncode(final String text) throws UnsupportedEncodingException
    {
        return URLEncoder.encode(text, "UTF-8");
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("graphs", this.graphs).append("server", this.core).append("thread", this.thread).toString();
    }

}