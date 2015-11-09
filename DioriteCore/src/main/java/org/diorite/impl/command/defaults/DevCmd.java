/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2015 Diorite (by Bartłomiej Mazur (aka GotoFinal))
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package org.diorite.impl.command.defaults;

import java.util.Arrays;
import java.util.UUID;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.apache.commons.lang3.RandomStringUtils;

import org.diorite.impl.command.SystemCommandImpl;
import org.diorite.impl.connection.packets.play.server.PacketPlayServerBlockChange;
import org.diorite.impl.connection.packets.play.server.PacketPlayServerGameStateChange;
import org.diorite.impl.entity.EntityImpl;
import org.diorite.impl.entity.ItemImpl;
import org.diorite.impl.entity.PlayerImpl;
import org.diorite.impl.inventory.item.meta.ItemMetaImpl;
import org.diorite.impl.inventory.item.meta.PotionMetaImpl;
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
import org.diorite.effect.StatusEffect;
import org.diorite.effect.StatusEffectType;
import org.diorite.enchantments.EnchantmentType;
import org.diorite.entity.attrib.AttributeModifier;
import org.diorite.entity.attrib.AttributeType;
import org.diorite.inventory.InventoryHolder;
import org.diorite.inventory.item.BaseItemStack;
import org.diorite.inventory.item.ItemStack;
import org.diorite.inventory.item.meta.ItemMeta;
import org.diorite.inventory.item.meta.PotionMeta;
import org.diorite.inventory.item.meta.SkullMeta;
import org.diorite.material.Material;
import org.diorite.material.items.PotionMat;
import org.diorite.material.items.SkullMat;
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
                case "potionmeta":
                {
                    final ItemStack item = new BaseItemStack(new PotionMat("POTION", 373, "minecraft:potion", 1, "POTION", (short) 8193, 5, 6){});
                    final PotionMeta meta = new PotionMetaImpl(null);
                    meta.addCustomEffect(new StatusEffect(StatusEffectType.INVISIBILITY, 3,300,false, true), false);
                    meta.addCustomEffect(new StatusEffect(StatusEffectType.INVISIBILITY, 3,30000,false, true), false);
                    item.setItemMeta(meta);
                    p.getInventory().add(item);
                    break;
                }
                case "m2":
                {
                    final ItemMeta meta = p.getInventory().getItemInHand().getItemMeta();
                    meta.setDisplayName(RandomStringUtils.randomAlphanumeric(16));
                    break;
                }
                case "itemid":
                {
                    final ItemMetaImpl meta = (ItemMetaImpl) p.getInventory().getItemInHand().getItemMeta();
                    System.out.println("Meta: " + System.identityHashCode(meta) + ", tag: " + (meta.getRawData() == null ? "NULL" : System.identityHashCode(meta.getRawData()) + "") + ", item: " + (! meta.getItemStack().isPresent() ? "NULL" : System.identityHashCode(meta.getItemStack().get()) + ""));
                    break;
                }
                case "skullmeta":
                {
                    final ItemStack item = new BaseItemStack(SkullMat.SKULL_PLAYER);
                    System.out.println(item.getItemMeta());
                    final SkullMeta meta = (SkullMeta) item.getItemMeta();
                    meta.setOwner(args.asString(0));
                    System.out.println(meta);
                    p.getInventory().add(item);
                    break;
                }
                case "itemmeta":
                {
                    final ItemStack item = new BaseItemStack(Material.STONE);
                    final ItemMeta meta = item.getItemMeta();
                    meta.setDisplayName("Custom name!");
                    meta.setLore(Arrays.asList("North to", "gupi nup"));
                    meta.addEnchant(EnchantmentType.AQUA_AFFINITY, 3, true);
                    meta.addAttributeModifier(AttributeModifier.builder().setUuid(new UUID(0, 0)).setName("t").setValue(2.25D).setType(AttributeType.GENERIC_ATTACK_DAMAGE).build());
                    meta.addAttributeModifier(AttributeModifier.builder().setUuid(new UUID(0, 1)).setName("t").setValue(2.25D).setType(AttributeType.GENERIC_MAX_HEALTH).build());
                    p.getInventory().add(item);
                    break;
                }
                case "pextest":
                {
                    sender.setOp(false);
                    mag.getPermissibleGroups(sender).forEach(g -> mag.removePermissibleFromGroup(sender, g));
                    sender.sendMessage("Testing permissions: ");
                    sender.sendMessage("foo.bar: " + sender.hasPermission("foo.bar")); // false
                    sender.sendMessage("Your groups: " + mag.getPermissibleGroups(sender).stream().map(f -> f.getGroup().getName()).collect(Collectors.toList())); // empty
                    PermissionsGroup group = mag.createGroup("test");
                    sender.sendMessage("Adding to group (" + group.getName() + "): " + mag.addPermissibleToGroup(sender, group, 1)); // true
                    sender.sendMessage("Your groups: " + mag.getPermissibleGroups(sender).stream().map(f -> f.getGroup().getName()).collect(Collectors.toList())); // test
                    mag.setPermission(group, "foo.bar", PermissionLevel.TRUE);
                    sender.sendMessage("foo.bar: " + sender.hasPermission("foo.bar")); // true
                    mag.removePermissibleFromGroup(sender, "test");
                    sender.sendMessage("Your groups: " + mag.getPermissibleGroups(sender).stream().map(f -> f.getGroup().getName()).collect(Collectors.toList())); // empty
                    sender.sendMessage("foo.bar: " + sender.hasPermission("foo.bar")); // false
                    sender.sendMessage("Adding to group (" + group.getName() + "): " + mag.addPermissibleToGroup(sender, group, 1)); // true
                    mag.setPermission(group, "foo.bar", PermissionLevel.OP);
                    sender.sendMessage("foo.bar: " + sender.hasPermission("foo.bar")); // false
                    sender.setOp(true);
                    sender.sendMessage("foo.bar: " + sender.hasPermission("foo.bar")); // true
                    sender.setOp(false);
                    mag.setPermission(group, "foo.{$-$}", "foo.[-100--10,25-30]", PermissionLevel.TRUE); // from -100 to -10
                    sender.sendMessage("foo.5: " + sender.hasPermission("foo.5")); // false
                    sender.sendMessage("foo.-10: " + sender.hasPermission("foo.-10")); // true
                    sender.sendMessage("foo.-25: " + sender.hasPermission("foo.-25")); // true
                    sender.sendMessage("foo.25: " + sender.hasPermission("foo.25")); // true
                    mag.removePermissibleFromGroup(sender, "test");
                    sender.sendMessage("Your groups: " + mag.getPermissibleGroups(sender).stream().map(f -> f.getGroup().getName()).collect(Collectors.toList())); // empty
                    sender.sendMessage("foo.5: " + sender.hasPermission("foo.5")); // false
                    sender.sendMessage("foo.-10: " + sender.hasPermission("foo.-10")); // false
                    sender.sendMessage("foo.-25: " + sender.hasPermission("foo.-25")); // false
                    sender.sendMessage("foo.25: " + sender.hasPermission("foo.25")); // false
                    sender.setOp(true);
                    sender.sendMessage("foo.5: " + sender.hasPermission("foo.5")); // true
                    sender.sendMessage("foo.-10: " + sender.hasPermission("foo.-10")); // true
                    sender.sendMessage("foo.-25: " + sender.hasPermission("foo.-25")); // true
                    sender.sendMessage("foo.25: " + sender.hasPermission("foo.25")); // true

                    mag.addPermissibleToGroup(sender, mag.createGroup("test"), 1);
                    mag.addPermissibleToGroup(sender, mag.createGroup("a1"), 2);
                    mag.addPermissibleToGroup(sender, mag.createGroup("a2"), 3);
                    mag.addPermissibleToGroup(sender, mag.createGroup("a3"), 4);
                    sender.sendMessage("Your groups: " + mag.getPermissibleGroups(sender).stream().map(f -> f.getGroup().getName()).collect(Collectors.toList())); // test, a1, a2, a3
                    mag.setPermission(mag.getGroup("test"), "test.test", PermissionLevel.FALSE);
                    mag.setPermission(mag.getGroup("a3"), "test.test", PermissionLevel.TRUE);
                    sender.sendMessage("test.test: " + sender.hasPermission("test.test")); // true
                    mag.setPermission(sender, "test.test", PermissionLevel.NOT_OP);
                    sender.sendMessage("test.test: " + sender.hasPermission("test.test")); // false
                    mag.setPermission(mag.getGroup("a2"), "test.test2", PermissionLevel.FALSE);
                    mag.setPermission(mag.getGroup("a1"), "test.test2", PermissionLevel.TRUE);
                    sender.sendMessage("test.test2: " + sender.hasPermission("test.test2")); // false
                    break;
                }
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
