package org.diorite.impl.command.defaults;

import java.util.UUID;
import java.util.regex.Pattern;

import org.diorite.impl.command.SystemCommandImpl;
import org.diorite.impl.connection.packets.play.server.PacketPlayServerBlockChange;
import org.diorite.impl.connection.packets.play.server.PacketPlayServerGameStateChange;
import org.diorite.impl.entity.EntityImpl;
import org.diorite.impl.entity.ItemImpl;
import org.diorite.impl.entity.PlayerImpl;
import org.diorite.Diorite;
import org.diorite.cfg.messages.DioriteMesssges;
import org.diorite.cfg.messages.Message.MessageData;
import org.diorite.chat.ChatColor;
import org.diorite.chat.component.ClickEvent;
import org.diorite.chat.component.ClickEvent.Action;
import org.diorite.chat.component.ComponentBuilder;
import org.diorite.chat.component.TextComponent;
import org.diorite.chat.component.serialize.ComponentSerializer;
import org.diorite.command.CommandPriority;
import org.diorite.inventory.InventoryHolder;
import org.diorite.inventory.item.BaseItemStack;
import org.diorite.material.Material;
import org.diorite.permissions.PermissionLevel;
import org.diorite.permissions.PermissionsGroup;
import org.diorite.permissions.PermissionsManager;

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
            final PermissionsManager mag = Diorite.getServerManager().getPermissionsManager();
            switch (action.toLowerCase())
            {
                case "pex":
                {
                    if (sender.hasPermission(args.asString(0)))
                    {
                        sender.sendMessage("You have " + args.asString(0) + " permission!");
                    }
                    else
                    {
                        sender.sendMessage("You don't have " + args.asString(0) + " permission!");
                    }
                    break;
                }
                case "pexaddg":
                {
                    PermissionsGroup group = mag.getGroup(args.asString(0));
                    if (group == null)
                    {
                        sender.sendMessage("No group, " + args.asString(0));
                        return;
                    }
                    mag.setPermission(group, args.asString(1), args.asString(2).equalsIgnoreCase("null") ? null : PermissionLevel.valueOf(args.asString(2).toUpperCase()));
                    sender.sendMessage("Set permission " + args.asString(1) + ":" + (args.asString(2).equalsIgnoreCase("null") ? null : PermissionLevel.valueOf(args.asString(2).toUpperCase())) + " to group " + group.getName());
                    break;
                }
                case "pexadd":
                {
                    mag.setPermission(sender, args.asString(0), args.asString(1).equalsIgnoreCase("null") ? null : PermissionLevel.valueOf(args.asString(1).toUpperCase()));
                    sender.sendMessage("Set permission " + args.asString(0) + ":" + (args.asString(1).equalsIgnoreCase("null") ? null : PermissionLevel.valueOf(args.asString(1).toUpperCase())) + " to you");
                    break;
                }
                case "pexaddu":
                {
                    boolean added = mag.addPermissibleToGroup(sender, args.asString(0), args.asInt(1));
                    sender.sendMessage("Added you to " + args.asString(0) + " group: " + added);
                    break;
                }
                case "pexreg":
                {
                    mag.registerPermission(mag.createPermission(args.asString(0), args.asString(0), PermissionLevel.valueOf(args.asString(1).toUpperCase())));
                    sender.sendMessage("Register permission " + args.asString(0) + ":" + PermissionLevel.valueOf(args.asString(1).toUpperCase()) + " to manager");
                    break;
                }
                case "op":
                {
                    if (p.isOp())
                    {
                        p.setOp(false);
                        sender.sendMessage("You are not op anymore...");
                    }
                    else
                    {
                        p.setOp(true);
                        sender.sendMessage("You are now op!");
                    }
                    break;
                }
                case "pexrg":
                {
                    PermissionsGroup group = mag.removeGroup(args.asString(0));
                    sender.sendMessage("Removed group: " + group);
                    break;
                }
                case "pexcg":
                {
                    PermissionsGroup group = mag.createGroup(args.asString(0));
                    sender.sendMessage("Created group: " + group);
                    break;
                }
                case "msgr":
                {
                    DioriteMesssges.reload();
                    System.out.println("Done");
                    break;
                }
                case "msg":
                {
                    DioriteMesssges.broadcastMessage(args.asText(), MessageData.e("player", p));
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
                    ((InventoryHolder) sender).getInventory().update();
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
