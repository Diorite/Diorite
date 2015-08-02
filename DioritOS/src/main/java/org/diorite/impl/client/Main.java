package org.diorite.impl.client;

import static org.lwjgl.glfw.Callbacks.errorCallbackPrint;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.opengl.GL11.GL_TRUE;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.system.MemoryUtil.NULL;


import java.io.File;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Collections;

import org.apache.commons.lang3.ArrayUtils;
import org.lwjgl.Sys;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWWindowSizeCallback;
import org.lwjgl.glfw.GLFWvidmode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GLContext;

import org.diorite.impl.ServerImpl;

import io.netty.util.ResourceLeakDetector;
import joptsimple.OptionParser;
import joptsimple.OptionSet;

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
                ServerImpl.getInstance().stop();
            } finally
            {
                this.cleanup();
            }
        }, "GL");
        thread.setDaemon(false);
        thread.start();
        try
        {
            org.diorite.impl.Main.init(options, true);
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
        glfwSetWindowSizeCallback(this.window, this.resizeCallback);

        // Setup a key callback. It will be called every time a key is pressed, repeated or released.
        glfwSetKeyCallback(this.window, this.keyCallback = new GLFWKeyCallback()
        {
            @Override
            public void invoke(final long window, final int key, final int scancode, final int action, final int mods)
            {
                if ((key == GLFW_KEY_ESCAPE) && (action == GLFW_RELEASE))
                {
                    glfwSetWindowShouldClose(window, GL_TRUE); // We will detect this in our rendering loop
                }
            }
        });

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
    }

    private void handleResize(final long window, final int width, final int height)
    {
        Main.this.width = width;
        Main.this.height = height;
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
        glClearColor(1.0f, 0.0f, 0.0f, 0.0f);

        // Run the rendering loop until the user has attempted to close
        // the window or has pressed the ESCAPE key.
        while (glfwWindowShouldClose(this.window) == GL_FALSE)
        {
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer

            glfwSwapBuffers(this.window); // swap the color buffers

            // Poll for window events. The key callback above will only be
            // invoked during this call.
            glfwPollEvents();
            if (this.stop)
            {
                glfwSetWindowShouldClose(this.window, GL_TRUE);
                break;
            }
        }
    }

    public static void main(final String[] args)
    {
        final OptionParser parser = new OptionParser()
        {
            {
                this.acceptsAll(Collections.singletonList("?"), "Print help");
                this.acceptsAll(Collections.singletonList("debug"), "Enable debug mode");
                this.acceptsAll(Arrays.asList("resourceleakdetector", "rld"), "ResourceLeakDetector level, disabled by default").withRequiredArg().ofType(String.class).describedAs("rld").defaultsTo(ResourceLeakDetector.Level.DISABLED.name());
                this.acceptsAll(Arrays.asList("online-mode", "online", "o"), "if player should be auth with Mojang").withRequiredArg().ofType(Boolean.class).describedAs("online").defaultsTo(true);
                this.acceptsAll(Collections.singletonList("config"), "Configuration file to use.").withRequiredArg().ofType(File.class).describedAs("config").defaultsTo(new File("dioritOS.yml"));
                this.acceptsAll(Arrays.asList("keepalivetimer", "keep-alive-timer", "kat"), "Each x seconds client will send keep alive packet to server").withRequiredArg().ofType(Integer.class).describedAs("keepalivetimer").defaultsTo(10);
                this.acceptsAll(Collections.singletonList("width"), "width of screen").withRequiredArg().ofType(int.class).describedAs("width").defaultsTo(854);
                this.acceptsAll(Collections.singletonList("height"), "height of screen").withRequiredArg().ofType(int.class).describedAs("width").defaultsTo(480);
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
        new Main().run(options);
    }

}