package org.diorite.impl.command.defaults;

import java.util.Collections;

import org.diorite.impl.command.SystemCommandImpl;
import org.diorite.GameMode;
import org.diorite.command.CommandPriority;
import org.diorite.entity.Player;

public class GamemodeCmd extends SystemCommandImpl
{
    public GamemodeCmd()
    {
        super("gamemode", Collections.singletonList("gm"), CommandPriority.LOW);
        this.setCommandExecutor((sender, command, label, matchedPattern, args) -> {
            final Player target = args.has(1) ? args.asPlayer(1) : ((sender instanceof Player) ? (Player) sender : null);
            if (target == null)
            {
                sender.sendSimpleColoredMessage("&4No target...");
                return;
            }
            GameMode gameMode = args.has(0) ? args.asSimpleEnumValue(GameMode.class, 0) : (target.getGameMode().equals(GameMode.CREATIVE) ? GameMode.SURVIVAL : GameMode.CREATIVE);
            if (gameMode == null)
            {
                sender.sendSimpleColoredMessage("&4No gamemode named: &c" + args.asString(0));
                return;
            }
            target.setGameMode(gameMode);

            //noinspection ObjectEquality
            if (target == sender) // this should be this same object
            {
                sender.sendSimpleColoredMessage("&7Changed gamemode to &3" + gameMode.getName());
            }
            else
            {
                sender.sendSimpleColoredMessage("&7Changed gamemode of &3" + target.getName() + "&7 to &3" + gameMode.getName());
                target.sendSimpleColoredMessage("&7Changed gamemode to &3" + gameMode.getName());
            }
        });
    }
}
