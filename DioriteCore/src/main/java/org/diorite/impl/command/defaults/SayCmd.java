package org.diorite.impl.command.defaults;

import java.util.regex.Pattern;

import org.diorite.impl.command.SystemCommandImpl;
import org.diorite.chat.ChatPosition;
import org.diorite.command.CommandPriority;

public class SayCmd extends SystemCommandImpl
{
    public SayCmd()
    {
        super("say", Pattern.compile("(say)(:(?<type>[a-z0-9]+)|)", Pattern.CASE_INSENSITIVE), CommandPriority.LOW);
        this.setCommandExecutor((sender, command, label, matchedPattern, args) -> {
            ChatPosition chatPosition;
            try
            {
                chatPosition = ChatPosition.getByEnumName(matchedPattern.group("type"));
                if (chatPosition == null)
                {
                    chatPosition = ChatPosition.CHAT;
                }
            } catch (IllegalStateException | IllegalArgumentException e)
            {
                chatPosition = ChatPosition.CHAT;
            }
            sender.getServer().broadcastSimpleColoredMessage(chatPosition, "&7[&3Diorite&7] &r" + args.asText());
        });
    }
}
