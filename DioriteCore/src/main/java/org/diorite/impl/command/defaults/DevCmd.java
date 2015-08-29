package org.diorite.impl.command.defaults;

import java.util.UUID;
import java.util.regex.Pattern;

import org.diorite.impl.command.SystemCommandImpl;
import org.diorite.impl.connection.packets.play.server.PacketPlayServerBlockChange;
import org.diorite.impl.connection.packets.play.server.PacketPlayServerGameStateChange;
import org.diorite.impl.entity.EntityImpl;
import org.diorite.impl.entity.ItemImpl;
import org.diorite.impl.entity.PlayerImpl;
import org.diorite.cfg.messages.Message.MessageData;
import org.diorite.cfg.messages.MessageLoader;
import org.diorite.chat.ChatColor;
import org.diorite.chat.component.ClickEvent;
import org.diorite.chat.component.ClickEvent.Action;
import org.diorite.chat.component.ComponentBuilder;
import org.diorite.chat.component.TextComponent;
import org.diorite.chat.component.serialize.ComponentSerializer;
import org.diorite.command.CommandPriority;
import org.diorite.inventory.item.BaseItemStack;
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
                p.getNetworkManager().sendPacket(new PacketPlayServerBlockChange(args.readCoordinates(0, p.getLocation().toBlockLocation()), args.asInt(3), args.asInt(4).byteValue()));
                return;
            }
            switch (action.toLowerCase())
            {
                case "msgr":
                {
                    MessageLoader.reloadDioriteMessages();
                    System.out.println("Done");
                    break;
                }
                case "msg":
                {
                    MessageLoader.getMasterNode().getMessage("player", "join").broadcastMessage(MessageData.e("player", p));
                    break;
                }
                case "tc":
                {
                    TextComponent tc = ComponentBuilder.start("test <r> ing").color(ChatColor.RED).event(new ClickEvent(Action.OPEN_URL, "www.diorite.org:<port>")).appendLegacy("§2 costam costam §8<r> dbdjs fdd").create();
                    sender.sendMessage(tc);
                    System.out.println(tc.toLegacyText());
                    System.out.println(ComponentSerializer.toString(tc));
                    sender.sendMessage(tc.duplicate());
                    System.out.println(tc.duplicate().toLegacyText());
                    System.out.println(ComponentSerializer.toString(tc.duplicate()));
                    break;
                }
                case "rep":
                {
                    TextComponent tc = ComponentBuilder.start("test <r> ing").color(ChatColor.RED).event(new ClickEvent(Action.OPEN_URL, "www.diorite.org:<port>")).appendLegacy("§2 costam costam §8<r> dbdjs fdd").create();
                    sender.sendMessage(tc.duplicate());
                    tc.replace("<r>", ComponentBuilder.start("Replaced clickable text").event(new ClickEvent(Action.SUGGEST_COMMAND, "YeY")).create());
                    tc.replace("<port>", new TextComponent("8081"));
                    sender.sendMessage(tc);
                    break;
                }
                case "inv":
                {
                    ((PlayerImpl) sender).getInventory().update();
                    sender.sendMessage("Inventory updated!");
                    break;
                }
                case "gs":
                {
                    p.getNetworkManager().sendPacket(new PacketPlayServerGameStateChange(args.asInt(0), args.asFloat(1)));
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
                    ItemImpl item = new ItemImpl(UUID.randomUUID(), p.getCore(), EntityImpl.getNextEntityID(), p.getLocation().addX(3).addY(1));
                    item.setItemStack(new BaseItemStack(Material.TNT));
                    p.getWorld().addEntity(item);
                    break;
                }
                case "ep":
                {
                    for (EntityImpl e : p.getNearbyEntities(args.asDouble(0), args.asDouble(0), args.asDouble(0)))
                    {
                        sender.sendSimpleColoredMessage("[" + e.getId() + "] " + e.getType() + ": " + e.getLocation());
                    }
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
