package org.diorite.command;

import org.diorite.Server;
import org.diorite.chat.BaseComponent;

public interface CommandSender
{
    String getName();

    boolean isConsole();

    boolean isPlayer();

    boolean isCommandBlock();

    void sendMessage(String str);

    default void sendMessage(final String[] strs)
    {
        if (strs != null)
        {
            for (final String str : strs)
            {
                this.sendMessage(str);
            }
        }
    }

    void sendMessage(BaseComponent component);

    default void sendMessage(final BaseComponent[] components)
    {
        if (components != null)
        {
            for (final BaseComponent component : components)
            {
                this.sendMessage(component);
            }
        }
    }

    Server getServer();
}
