package org.diorite.impl.command.defaults;

import java.util.regex.Pattern;

import org.diorite.impl.command.SystemCommandImpl;
import org.diorite.command.Arguments;
import org.diorite.command.CommandPriority;
import org.diorite.entity.Player;
import org.diorite.utils.math.DioriteMathUtils;

public class FlyCmd extends SystemCommandImpl
{
    public FlyCmd()
    {
        super("fly", Pattern.compile("(fly)(:(?<speed>((\\d+|)(\\.\\d+|)))|)", Pattern.CASE_INSENSITIVE), CommandPriority.LOW);
        this.setCommandExecutor((sender, command, label, matchedPattern, args) -> {
            final String speedArg = matchedPattern.group("speed");
            final double speed;
            final Player target;
            Boolean on = null;

            if (args.length() >= 2)
            {
                target = args.asPlayer(0);
                on = args.asBoolean(1);
            }
            else
            {
                target = args.has(0) ? args.asPlayer(0) : ((sender instanceof Player) ? (Player) sender : null);
            }

            if (target == null)
            {
                sender.sendSimpleColoredMessage("&4No target...");
                return;
            }

            if (speedArg == null)
            {
                speed = Player.FLY_SPEED;
            }
            else if (speedArg.isEmpty())
            {
                speed = target.getFlySpeed();
            }
            else
            {
                speed = (Arguments.asDouble(speedArg) / 100);
            }
            if (speed > 1)
            {
                sender.sendSimpleColoredMessage("&4Speed can't by larger than 100");
                return;
            }

            if (on == null)
            {
                on = (speed == target.getFlySpeed()) ? ! target.canFly() : target.canFly();
            }
            target.setCanFly(on, speed);

            //noinspection ObjectEquality
            if (target == sender) // this should be this same object
            {
                sender.sendSimpleColoredMessage("&7Fly &3" + (target.canFly() ? "enabled" : "disabled") + "&7! (Speed: &3" + DioriteMathUtils.formatSimpleDecimal(target.getFlySpeed() * 100) + "&7)");
            }
            else
            {
                sender.sendSimpleColoredMessage("&7Fly &3" + (target.canFly() ? "enabled" : "disabled") + "&7 for &3" + target.getName() + "&7! (Speed: &3" + DioriteMathUtils.formatSimpleDecimal(target.getFlySpeed() * 100) + "&7)");
            }
        });
    }
}
