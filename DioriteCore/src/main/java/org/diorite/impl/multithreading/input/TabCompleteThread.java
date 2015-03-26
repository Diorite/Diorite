package org.diorite.impl.multithreading.input;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.impl.ServerImpl;
import org.diorite.impl.entity.PlayerImpl;
import org.diorite.impl.multithreading.ChatAction;
import org.diorite.command.sender.CommandSender;

public class TabCompleteThread extends Thread
{
    private static final Queue<ChatAction> actions = new ConcurrentLinkedQueue<>();
    private final ServerImpl server;

    public TabCompleteThread(final ServerImpl server)
    {
        super("{Diorite-" + server.getServerName() + "|TabCpl}");
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
        if ((action.getMsg() == null) || action.getMsg().isEmpty())
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
            // TODO: tab complete event
            final CommandSender sender = (action.getSender() == null) ? this.server.getConsoleSender() : action.getSender();
            //noinspection HardcodedFileSeparator
            if ((action.getMsg() != null) && action.getMsg().startsWith("/"))
            {
                sender.sendSimpleColoredMessage("&7" + StringUtils.join(this.server.getCommandMap().tabComplete(sender, action.getMsg()), "&r, &7"));
            }
            else
            {
                sender.sendSimpleColoredMessage("&7" + StringUtils.join(this.server.getPlayersManager().getRawPlayers().values().parallelStream().map(PlayerImpl::getName).collect(Collectors.toList()), "&r, &7"));
            }
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
