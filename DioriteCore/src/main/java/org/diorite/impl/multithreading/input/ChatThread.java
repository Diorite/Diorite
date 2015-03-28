package org.diorite.impl.multithreading.input;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.impl.ServerImpl;
import org.diorite.impl.multithreading.ChatAction;
import org.diorite.chat.ChatColor;
import org.diorite.chat.component.TextComponent;

public class ChatThread extends Thread
{
    private static final Queue<ChatAction> actions = new ConcurrentLinkedQueue<>();
    private final ServerImpl server;

    public ChatThread(final ServerImpl server)
    {
        super("{Diorite-" + server.getServerName() + "|Chat}");
        this.server = server;
        this.setDaemon(true);
        this.setPriority(Thread.MIN_PRIORITY);
    }

    public static int getActionsSize()
    {
        return actions.size();
    }

    public static void add(final ChatAction action)
    {
        actions.add(action);
        synchronized (actions)
        {
            actions.notifyAll();
        }
    }

    @Override
    public void run()
    {
        while (this.server.isRunning())
        {
            final ChatAction action = actions.poll();
            if (action == null)
            {
                try
                {
                    synchronized (actions)
                    {
                        actions.wait();
                    }
                } catch (final InterruptedException e)
                {
                    e.printStackTrace();
                }
                continue;
            }
            // TODO: chat event

            // TODO: implement some needed permissions (Yeach, I need create permissions system too) to use markdown options.
            final TextComponent base = new TextComponent(((action.getSender() == null) ? "" : (action.getSender().getName() + ChatColor.AQUA + ": " + ChatColor.GRAY.toString() + (action.getMsg() == null ? "" : action.getMsg()))));
            // base.addExtra((action.getMsg() == null) ? new TextComponent("") : DioriteMarkdownParser.parse(action.getMsg(), ChatColor.GRAY)); TODO: fix
            this.server.getPlayersManager().forEach(p -> p.sendMessage(base));
            this.server.getConsoleSender().sendMessage(base);
        }
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("server", this.server).append("actionsSize", actions.size()).toString();
    }

    public static ChatThread start(final ServerImpl server)
    {
        final ChatThread thread = new ChatThread(server);
        thread.start();
        return thread;
    }
}
