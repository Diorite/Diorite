package org.diorite.impl.multithreading.input;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.impl.ServerImpl;
import org.diorite.impl.multithreading.ChatAction;
import org.diorite.chat.ChatColor;
import org.diorite.chat.DioriteMarkdownParser;
import org.diorite.chat.component.TextComponent;

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

    public int getActionsSize()
    {
        return this.actions.size();
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

            // TODO: implement some needed permissions (Yeach, I need create permissions system too) to use markdown options.
            final TextComponent base = new TextComponent(((action.getSender() == null) ? "" : (action.getSender().getName() + ChatColor.AQUA + ": " + ChatColor.GRAY.toString())));
            base.addExtra((action.getMsg() == null) ? new TextComponent("") : DioriteMarkdownParser.parse(action.getMsg(), ChatColor.GRAY));
            this.server.getPlayersManager().forEach(p -> p.sendMessage(base));
            this.server.getConsoleSender().sendMessage(base);
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
