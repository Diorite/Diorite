package org.diorite.impl.multithreading.input;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.impl.ServerImpl;
import org.diorite.impl.multithreading.ChatAction;
import org.diorite.event.EventType;
import org.diorite.event.others.SenderTabCompleteEvent;

public class TabCompleteThread extends Thread
{
    private static final Queue<ChatAction> actions = new ConcurrentLinkedQueue<>();
    private final ServerImpl server;

    public TabCompleteThread(final ServerImpl server)
    {
        super("{Diorite|TabCpl}");
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
        if ((action.getSender() == null) || (action.getMsg() == null))
        {
            return;
        }
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
            EventType.callEvent(new SenderTabCompleteEvent(action.getSender(), action.getMsg()));
        }
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("server", this.server).append("actionsSize", actions.size()).toString();
    }

    public static TabCompleteThread start(final ServerImpl server)
    {
        final TabCompleteThread thread = new TabCompleteThread(server);
        thread.start();
        return thread;
    }
}
