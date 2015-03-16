package org.diorite.impl.multithreading.input;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.ChatColor;
import org.diorite.chat.TextComponent;
import org.diorite.impl.ServerImpl;
import org.diorite.impl.connection.packets.play.out.PacketPlayOutChat;
import org.diorite.impl.multithreading.ChatAction;

public class ChatThread extends Thread
{
    private final Queue<ChatAction> actions = new ConcurrentLinkedQueue<>();
    private final ServerImpl server;

    public ChatThread(final ServerImpl server)
    {
        super("{Diorite-" + server.getServerName() + "|Chat}");
        this.server = server;
        this.setDaemon(true);
        this.setPriority(Thread.MIN_PRIORITY);
    }

    public void add(final ChatAction action)
    {
        this.actions.add(action);
    }

    @Override
    public void run()
    {
        while (this.server.isRunning())
        {
            final ChatAction action = this.actions.poll();
            if (action == null)
            {
                try
                {
                    Thread.sleep(100);
                } catch (final InterruptedException ignored)
                {
                }
                continue;
            }
            // TODO: chat event
            final String msg = (action.getSender() == null ? "" : (action.getSender().getName() + ChatColor.AQUA + ": " + ChatColor.GRAY.toString())) + (action.getMsg() == null ? "" : action.getMsg());
            this.server.getPlayersManager().forEach(new PacketPlayOutChat(new TextComponent(msg)));
        }
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("server", this.server).append("actionsSize", this.actions.size()).toString();
    }

    public static ChatThread start(final ServerImpl server)
    {
        final ChatThread thread = new ChatThread(server);
        thread.start();
        return thread;
    }
}
