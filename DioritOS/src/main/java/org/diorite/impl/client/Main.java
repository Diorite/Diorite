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

package org.diorite.impl.client;

import static org.lwjgl.glfw.Callbacks.errorCallbackPrint;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.NULL;


import java.net.InetAddress;
import java.net.Proxy;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.util.Collections;

import org.lwjgl.Sys;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWWindowSizeCallback;
import org.lwjgl.glfw.GLFWvidmode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GLContext;

import org.diorite.impl.CoreMain;
import org.diorite.impl.DioriteCore;
import org.diorite.impl.client.connection.ClientConnection;
import org.diorite.utils.math.DioriteRandomUtils;

import joptsimple.OptionSet;

@SuppressWarnings({"MagicNumber", "ClassHasNoToStringMethod"})
public class Main
{
    static
    {
        SharedLibraryLoader.load();
        System.out.println("Loaded LWJGL library.");
    }

    // We need to strongly reference callback instances.
    private          GLFWErrorCallback      errorCallback;
    private          GLFWKeyCallback        keyCallback;
    private          GLFWWindowSizeCallback resizeCallback;
    private volatile int                    width;
    private volatile int                    height;
    private volatile boolean stop = false;

    // The window handle
    private long window;

    public void run(final OptionSet options)
    {
        System.out.println("Hello LWJGL " + Sys.getVersion() + "!");

        this.width = (int) options.valueOf("width");
        this.height = (int) options.valueOf("height");
        final Thread thread = new Thread(() -> {
            try
            {
                this.init();
                this.loop();
                // Release window and window callbacks
                glfwDestroyWindow(this.window);
                DioriteCore.getInstance().stop();
            } finally
            {
                this.cleanup();
            }
        }, "GL");
        thread.setDaemon(false);
        thread.start();
        try
        {
            new DioriteClient(Proxy.NO_PROXY, options).start(options);
        } catch (final Throwable e)
        {
            e.printStackTrace();
        } finally
        {
            this.stop = true;
        }
    }

    private void cleanup()
    {
        this.keyCallback.release();
        glfwTerminate();
        this.errorCallback.release();
        this.resizeCallback.release();
    }

    private void init()
    {
        // Setup an error callback. The default implementation
        // will print the error message in System.err.
        glfwSetErrorCallback(this.errorCallback = errorCallbackPrint(System.err));

        // Initialize GLFW. Most GLFW functions will not work before doing this.
        if (glfwInit() != GL11.GL_TRUE)
        {
            throw new IllegalStateException("Unable to initialize GLFW");
        }

        // Configure our window
        glfwDefaultWindowHints(); // optional, the current window hints are already the default
        glfwWindowHint(GLFW_VISIBLE, GL_FALSE); // the window will stay hidden after creation
        glfwWindowHint(GLFW_RESIZABLE, GL_TRUE); // the window will be resizable

        // Create the window
        this.window = glfwCreateWindow(this.width, this.height, "DioritOS", NULL, NULL);
        if (this.window == NULL)
        {
            throw new RuntimeException("Failed to create the GLFW window");
        }
        this.resizeCallback = GLFWWindowSizeCallback(this::handleResize);

        // Setup a key callback. It will be called every time a key is pressed, repeated or released.
        glfwSetKeyCallback(this.window, this.keyCallback = new MyGLFWResizeKeyCallback());

        // Get the resolution of the primary monitor
        final ByteBuffer vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
        // Center our window
        glfwSetWindowPos(this.window, (GLFWvidmode.width(vidmode) - this.width) / 2, (GLFWvidmode.height(vidmode) - this.height) / 2);

        // Make the OpenGL context current
        glfwMakeContextCurrent(this.window);
        // Enable v-sync
        glfwSwapInterval(1);

        // Make the window visible
        glfwShowWindow(this.window);

        glfwSetWindowSizeCallback(this.window, this.resizeCallback);
    }

    private void handleResize(final long window, final int width, final int height)
    {
        if ((width != this.width) || (height != this.height))
        {
            Main.this.width = width;
            Main.this.height = height;

            // Get the resolution of the primary monitor
            final ByteBuffer vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
            // Center our window
            glfwSetWindowPos(this.window, (GLFWvidmode.width(vidmode) - this.width) / 2, (GLFWvidmode.height(vidmode) - this.height) / 2);

            glViewport(0, 0, width, height);
        }
    }

    private void loop()
    {
        // This line is critical for LWJGL's interoperation with GLFW's
        // OpenGL context, or any context that is managed externally.
        // LWJGL detects the context that is current in the current thread,
        // creates the ContextCapabilities instance and makes the OpenGL
        // bindings available for use.
        GLContext.createFromCurrent();

        // Set the clear color
        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);

        // Run the rendering loop until the user has attempted to close
        // the window or has pressed the ESCAPE key.
        while (glfwWindowShouldClose(this.window) == GL_FALSE)
        {
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer

            // useless code, deal with it.
            for (int i = 0, k = DioriteRandomUtils.nextInt(50); i < k; i++)
            {
                rect(DioriteRandomUtils.getRandDouble(- 1, 1), DioriteRandomUtils.getRandDouble(- 1, 1), DioriteRandomUtils.getRandDouble(- 1, 1), DioriteRandomUtils.getRandDouble(- 1, 1), DioriteRandomUtils.getRandDouble(0, 5));
            }
            glfwSwapBuffers(this.window); // swap the color buffers

            // Poll for window events. The key callback above will only be
            // invoked during this call.
            glfwPollEvents();
            if (this.stop)
            {
                glfwSetWindowShouldClose(this.window, GL_TRUE);
                break;
            }
            try
            {
                Thread.sleep(300);
            } catch (final InterruptedException e)
            {
                e.printStackTrace();
            }

        }
    }

    public static void rect(final double x, final double y, final double width, final double height, final double r)
    {
        glPushMatrix();
        glTranslated(x, y, 0);
        glRotated(r, 0, 0, 1);
        glColor4f(DioriteRandomUtils.nextFloat(), DioriteRandomUtils.nextFloat(), DioriteRandomUtils.nextFloat(), DioriteRandomUtils.nextFloat());
        glBegin(GL_QUADS);
        glVertex2d(0, 0);
        glVertex2d(0, height);
        glVertex2d(width, height);
        glVertex2d(width, 0);
        glEnd();
        glPopMatrix();
    }


    public static void main(final String[] args)
    {
        DioriteCore.getInitPipeline().addLast("Diorite|initConnection", (s, p, data) -> {
            s.setHostname(data.options.has("hostname") ? data.options.valueOf("hostname").toString() : s.getConfig().getHostname());
            s.setPort(data.options.has("port") ? (int) data.options.valueOf("port") : s.getConfig().getPort());
            s.setConnectionHandler(new ClientConnection(s));
            s.getConnectionHandler().start();
        });
        DioriteCore.getStartPipeline().addLast("DioriteCore|Run", (s, p, options) -> {
            System.out.println("Started Diorite v" + s.getVersion() + " core!");
            s.run();
        });
//
//        // TODO: remove that, client should be able to select server.
        DioriteCore.getStartPipeline().addBefore("DioriteCore|Run", "Diorite|bindConnection", (s, p, options) -> {
            try
            {
                System.setProperty("io.netty.eventLoopThreads", options.has("netty") ? options.valueOf("netty").toString() : Integer.toString(s.getConfig().getNettyThreads()));
                System.out.println("Starting connecting on " + s.getHostname() + ":" + s.getPort());
                s.getConnectionHandler().init(InetAddress.getByName(s.getHostname()), s.getPort(), s.getConfig().isUseNativeTransport());

                System.out.println("Connected to " + s.getHostname() + ":" + s.getPort());
            } catch (final UnknownHostException e)
            {
                e.printStackTrace();
            }
        });

        final OptionSet options = CoreMain.main(args, false, p -> {
            p.acceptsAll(Collections.singletonList("width"), "width of screen").withRequiredArg().ofType(int.class).describedAs("width").defaultsTo(854);
            p.acceptsAll(Collections.singletonList("height"), "height of screen").withRequiredArg().ofType(int.class).describedAs("width").defaultsTo(480);
        });
        new Main().run(options);
    }

    private static class MyGLFWResizeKeyCallback extends GLFWKeyCallback
    {
        @Override
        public void invoke(final long window, final int key, final int scancode, final int action, final int mods)
        {
            if ((key == GLFW_KEY_ESCAPE) && (action == GLFW_RELEASE))
            {
                glfwSetWindowShouldClose(window, GL_TRUE); // We will detect this in our rendering loop
            }
        }
    }
}