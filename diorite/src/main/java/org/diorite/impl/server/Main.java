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

package org.diorite.impl.server;

import java.net.InetAddress;
import java.net.Proxy;
import java.net.UnknownHostException;

import org.diorite.impl.CoreMain;
import org.diorite.impl.DioriteCore;
import org.diorite.impl.server.connection.ServerConnection;
import org.diorite.impl.world.generator.FlatWorldGeneratorImpl;
import org.diorite.impl.world.generator.TestWorldGeneratorImpl;
import org.diorite.impl.world.generator.VoidWorldGeneratorImpl;
import org.diorite.world.generator.WorldGenerators;

import joptsimple.OptionSet;

public final class Main
{
    private Main()
    {
    }

    public static void main(final String[] args)
    {
        DioriteCore.getInitPipeline().addLast("Diorite|initConnection", (s, p, data) -> {
            s.setHostname(data.options.has("hostname") ? data.options.valueOf("hostname").toString() : s.getConfig().getHostname());
            s.setPort(data.options.has("port") ? (int) data.options.valueOf("port") : s.getConfig().getPort());
            s.setConnectionHandler(new ServerConnection(s));
            s.getConnectionHandler().start();
        });
        DioriteCore.getStartPipeline().addLast("DioriteCore|Run", (s, p, options) -> {
            System.out.println("Started Diorite v" + s.getVersion() + " core!");
            s.run();
        });
        DioriteCore.getStartPipeline().addBefore("DioriteCore|Run", "Diorite|bindConnection", (s, p, options) -> {
            try
            {
                System.setProperty("io.netty.eventLoopThreads", options.has("netty") ? options.valueOf("netty").toString() : Integer.toString(s.getConfig().getNettyThreads()));
                System.out.println("Starting listening on " + s.getHostname() + ":" + s.getPort());
                s.getConnectionHandler().init(InetAddress.getByName(s.getHostname()), s.getPort(), s.getConfig().isUseNativeTransport());

                System.out.println("Binded to " + s.getHostname() + ":" + s.getPort());
            } catch (final UnknownHostException e)
            {
                e.printStackTrace();
            }
        });
        DioriteCore.getStartPipeline().addAfter("DioriteCore|LoadPlugins", "DioriteCore|DefaultGenerators", (s, p, options) -> {
            WorldGenerators.registerGenerator(FlatWorldGeneratorImpl.createInitializer());
            WorldGenerators.registerGenerator(VoidWorldGeneratorImpl.createInitializer());
            WorldGenerators.registerGenerator(TestWorldGeneratorImpl.createInitializer());
        });
        DioriteCore.getStartPipeline().addAfter("DioriteCore|DefaultGenerators", "DioriteCore|LoadWorlds", (s, p, options) -> {
            System.out.println("Loading worlds...");
            s.getWorldsManager().init(s.getConfig(), s.getConfig().getWorlds().getWorldsDir());
            System.out.println("Worlds loaded.");
        });

        final OptionSet options = CoreMain.main(args, false, null);
        if (options != null)
        {
            new DioriteServer(Proxy.NO_PROXY, options).start(options);
        }
    }
}
