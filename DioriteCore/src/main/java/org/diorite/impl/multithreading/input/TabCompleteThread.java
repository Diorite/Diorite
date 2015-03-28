package org.diorite.impl.multithreading.input;

import java.util.Collection;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.impl.ServerImpl;
import org.diorite.impl.connection.packets.play.out.PacketPlayOutTabComplete;
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
                final Collection<String> strs = this.server.getCommandMap().tabComplete(sender, action.getMsg().substring(1));
                if (! (sender instanceof PlayerImpl))
                {
                    sender.sendSimpleColoredMessage("&7" + StringUtils.join(strs, "&r, &7"));
                }
                else
                {
                    ((PlayerImpl) sender).getNetworkManager().sendPacket(new PacketPlayOutTabComplete(strs));
                }
            }
            else
            {
                final String name = action.getMsg();
                final Collection<String> strs = ((name == null) || name.trim().isEmpty()) ? this.server.getPlayersManager().getOnlinePlayersNames() : this.server.getPlayersManager().getOnlinePlayersNames(name);
                if (! (sender instanceof PlayerImpl))
                {
                    sender.sendSimpleColoredMessage("&7" + StringUtils.join(strs, "&r, &7"));
                }
                else
                {
                    ((PlayerImpl) sender).getNetworkManager().sendPacket(new PacketPlayOutTabComplete(strs));
                }
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
