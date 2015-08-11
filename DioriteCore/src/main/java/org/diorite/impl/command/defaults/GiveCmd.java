package org.diorite.impl.command.defaults;

import java.util.regex.Pattern;

import org.diorite.impl.command.SystemCommandImpl;
import org.diorite.command.Arguments;
import org.diorite.command.CommandPriority;
import org.diorite.command.sender.CommandSender;
import org.diorite.entity.Player;
import org.diorite.inventory.item.BaseItemStack;
import org.diorite.inventory.item.ItemStack;
import org.diorite.material.Material;

public class GiveCmd extends SystemCommandImpl
{
    public GiveCmd()
    {
        super("give", (Pattern) null, CommandPriority.LOW);
        this.setCommandExecutor((sender, command, label, matchedPattern, args) -> {
            int param = 0; // counter for current param.
            parseGiveCommand((args.has(param) ? args.asPlayer(param++) : null), sender, args, param);
        });
    }

    static void parseGiveCommand(final Player target, final CommandSender sender, final Arguments args, int param)
    {
        if (target == null)
        {
            sender.sendSimpleColoredMessage("&4No player."); // TODO: change message and add it to config.
            return;
        }
        final Material mat;
        if (! args.has(param) || ((((mat = Material.matchValidInventoryMaterial(args.asString(param++), true)))) == null))
        {
            sender.sendSimpleColoredMessage("&4No material"); // TODO: change message and add it to config.
            return;
        }
        Integer amount = 1;
        if (args.has(param) && (((amount = args.asInt(param))) == null))
        {
            sender.sendSimpleColoredMessage("&4Amount must be a number."); // TODO: change message and add it to config.
            return;
        }
        // TODO: nbt, or other shit
        final ItemStack[] notAdded = target.getInventory().add(new BaseItemStack(mat, amount));
        final int notAddedAmount = (notAdded.length == 0) ? 0 : notAdded[0].getAmount();
        sender.sendSimpleColoredMessage("Added &9" + (amount - notAddedAmount) + "x&r of &3" + mat.getMinecraftId() + "&9:&3" + mat.getType()); // TODO: change message and add it to config.
    }
}