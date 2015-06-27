package org.diorite.impl.command.defaults;

import java.util.UUID;
import java.util.regex.Pattern;

import org.diorite.impl.command.SystemCommandImpl;
import org.diorite.impl.connection.packets.play.out.PacketPlayOut;
import org.diorite.impl.connection.packets.play.out.PacketPlayOutBlockChange;
import org.diorite.impl.connection.packets.play.out.PacketPlayOutEntityMetadata;
import org.diorite.impl.connection.packets.play.out.PacketPlayOutGameStateChange;
import org.diorite.impl.entity.ItemImpl;
import org.diorite.impl.entity.PlayerImpl;
import org.diorite.command.CommandPriority;
import org.diorite.inventory.item.ItemStack;
import org.diorite.material.Material;

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
            System.out.println(p);
            ItemImpl item = new ItemImpl(UUID.randomUUID(), p.getServer(), 44, p.getLocation().addY(2));
            item.setItemStack(new ItemStack(Material.TNT));
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
                case "en":
                {
//                    PacketPlayOutSpawnEntity packet = new PacketPlayOutSpawnEntity();
//                    packet.setEntityId(10);
//                    packet.setEntityTypeId((byte) 2);
//                    packet.setX((int) p.getX() << 5);
//                    packet.setY((int)p.getY() << 5);
//                    packet.setZ((int)p.getZ() << 5);
//                    packet.setObjectData(1);
                    PacketPlayOut packet = item.getSpawnPacket();
                    p.getNetworkManager().sendPacket(packet);
                    sender.sendSimpleColoredMessage(packet.toString());
                    System.out.println(packet);
                    break;
                }
                case "en2":
                {
                    PacketPlayOut packet = new PacketPlayOutEntityMetadata(44, item.getMetadata().getEntries());
                    p.getNetworkManager().sendPacket(packet);
                    sender.sendSimpleColoredMessage(packet.toString());
                    System.out.println(packet);
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
