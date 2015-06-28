package org.diorite.impl.command.defaults;

import java.util.Objects;
import java.util.regex.Pattern;

import org.diorite.impl.command.SystemCommandImpl;
import org.diorite.chat.ChatPosition;
import org.diorite.command.CommandPriority;

public class BroadcastCmd extends SystemCommandImpl
{
    public BroadcastCmd()
    {
        super("broadcast", Pattern.compile("(b(road|)c(ast|))(:(?<type>[a-z0-9]+)|)", Pattern.CASE_INSENSITIVE), CommandPriority.LOW);
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
            if (Objects.equals(chatPosition, ChatPosition.ACTION))
            {
                sender.getServer().sendConsoleSimpleColoredMessage(args.asText());
            }
            sender.getServer().broadcastSimpleColoredMessage(chatPosition, args.asText());
        });
    }
}
