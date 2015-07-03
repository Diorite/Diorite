package org.diorite.impl.command.defaults;

import java.util.regex.Pattern;

import org.diorite.impl.command.SystemCommandImpl;
import org.diorite.chat.component.TextComponent;
import org.diorite.command.CommandPriority;
import org.diorite.entity.Player;

public class KickCmd extends SystemCommandImpl
{
    public KickCmd()
    {
        super("kick", (Pattern) null, CommandPriority.LOW);
        this.setCommandExecutor((sender, command, label, matchedPattern, args) -> {
            if (! args.has(1))
            {
                sender.sendMessage("§4Invalid usage. Use: /kick <nick> <reason>");
                return;
            }

            final Player target = args.asPlayer(0);

            if (target == null)
            {
                sender.sendSimpleColoredMessage("&4Given player isn't online!");
                return;
            }

            final String reason = args.asText(1);

            target.kick(TextComponent.fromLegacyText(reason));

            sender.getServer().broadcastMessage(target.getName() + " has been kicked by " + sender.getName()); //TODO: Send only to ops
        });
    }
}
