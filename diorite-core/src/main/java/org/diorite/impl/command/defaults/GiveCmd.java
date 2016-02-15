/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2016. Diorite (by Bartłomiej Mazur (aka GotoFinal))
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

import java.util.regex.Pattern;

import org.diorite.impl.command.SystemCommandImpl;
import org.diorite.cfg.messages.DioriteMessages;
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
        this.setDescription("Give item.");
        this.setCommandExecutor((sender, command, label, matchedPattern, args) -> {
            int param = 0; // counter for current param.
            parseGiveCommand((args.has(param) ? args.asPlayer(param++) : null), sender, args, param);
        });
    }

    static void parseGiveCommand(final Player target, final CommandSender sender, final Arguments args, int param)
    {
        if (target == null)
        {
            DioriteMessages.sendMessage(DioriteMessages.MSG_CMD_NO_TARGET, sender);
            return;
        }
        final Material mat;
        if (! args.has(param) || ((((mat = Material.matchValidInventoryMaterial(args.asString(param++), true)))) == null))
        {
            DioriteMessages.sendMessage(DioriteMessages.MSG_CMD_NO_MATERIAL, sender);
            return;
        }
        Integer amount = 1;
        if (args.has(param) && (((amount = args.asInt(param))) == null))
        {
            DioriteMessages.sendMessage(DioriteMessages.MSG_CMD_NO_NUMBER, sender);
            return;
        }
        if (amount <= 0)
        {
            // TODO: block placking blocks with item stacks <= 0
            DioriteMessages.sendMessage(DioriteMessages.MSG_CMD_NUMBER_NOT_POSITIVE, sender);
        }
        // TODO: nbt, or other shit
        final ItemStack[] notAdded = target.getInventory().add(new BaseItemStack(mat, amount));
        final int notAddedAmount = (notAdded.length == 0) ? 0 : notAdded[0].getAmount();
        sender.sendSimpleColoredMessage("Added &9" + (amount - notAddedAmount) + "x&r of &3" + mat.getMinecraftId() + "&9:&3" + mat.getType()); // TODO: change message and add it to config.
    }
}