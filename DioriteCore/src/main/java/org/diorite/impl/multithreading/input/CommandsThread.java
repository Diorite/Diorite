package org.diorite.impl.multithreading.input;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.command.CommandSender;
import org.diorite.impl.ServerImpl;
import org.diorite.impl.multithreading.ChatAction;

public class CommandsThread extends Thread
{
    private final Queue<ChatAction> actions = new ConcurrentLinkedQueue<>();
    private final ServerImpl server;

    public CommandsThread(final ServerImpl server)
    {
        super("{Diorite-" + server.getServerName() + "|Cmds}");
        this.server = server;
        this.setDaemon(true);
        this.setPriority(Thread.MIN_PRIORITY);
    }

    public void add(final ChatAction action)
    {
        if ((action.getMsg() == null) || action.getMsg().isEmpty())
        {
            return;
        }
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
            // TODO: cmd event
            final CommandSender sender = (action.getSender() == null) ? this.server.getConsoleSender() : action.getSender();
            this.server.getCommandMap().dispatch(sender, action.getMsg());
        }
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("server", this.server).append("actionsSize", this.actions.size()).toString();
    }

    public static CommandsThread start(final ServerImpl server)
    {
        final CommandsThread thread = new CommandsThread(server);
        thread.start();
        return thread;
    }
}
