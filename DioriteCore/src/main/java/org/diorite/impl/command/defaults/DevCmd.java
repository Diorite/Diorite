package org.diorite.impl.command.defaults;

import java.util.regex.Pattern;

import org.diorite.impl.command.SystemCommandImpl;
import org.diorite.impl.connection.packets.play.out.PacketPlayOutBlockChange;
import org.diorite.impl.connection.packets.play.out.PacketPlayOutGameStateChange;
import org.diorite.impl.entity.PlayerImpl;
import org.diorite.command.CommandPriority;

public class DevCmd extends SystemCommandImpl
{
    public DevCmd()
    { // TODO: remove
        super("dev", Pattern.compile("(dev)(:(?<action>([a-z0-9_]*))|)", Pattern.CASE_INSENSITIVE), CommandPriority.LOW);
        this.setCommandExecutor((sender, command, label, matchedPattern, args) -> {
            final String action = matchedPattern.group("action");
            PlayerImpl p = (PlayerImpl) sender;
            if (action == null)
            {
                p.getNetworkManager().sendPacket(new PacketPlayOutBlockChange(args.readCoordinates(0, p.getLocation().toBlockLocation()), args.asInt(3), args.asInt(4).byteValue()));
                return;
            }
            switch (action.toLowerCase())
            {
                case "gs":
                {
                    p.getNetworkManager().sendPacket(new PacketPlayOutGameStateChange(args.asInt(0), args.asFloat(1)));
                    sender.sendSimpleColoredMessage("&3Done.");
                    break;
                }
                case "cb":
                {
                    sender.sendSimpleColoredMessage(p.getLocation().toBlockLocation().getBlock().toString());
                    break;
                }
                default:
                {
                    sender.sendSimpleColoredMessage("&4No action...");
                    break;
                }
            }
        });
    }
}
