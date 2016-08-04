package org.diorite.impl.inventory.block;

import org.diorite.impl.connection.packets.play.clientbound.PacketPlayClientboundWindowItems;
import org.diorite.impl.entity.IPlayer;
import org.diorite.impl.inventory.InventoryImpl;
import org.diorite.impl.inventory.item.ItemStackImplArray;
import org.diorite.block.DoubleChest;
import org.diorite.entity.Player;
import org.diorite.inventory.Inventory;
import org.diorite.inventory.InventoryType;
import org.diorite.inventory.block.DoubleChestInventory;
import org.diorite.inventory.slot.Slot;

public class DoubleChestInventoryImpl extends InventoryImpl<DoubleChest> implements DoubleChestInventory
{
    public DoubleChestInventoryImpl(final DoubleChest holder)
    {
        super(holder);
    }

    @Override
    // TODO
    public ItemStackImplArray getArray()
    {
        return null;
    }

    @Override
    public Inventory getLeftSide()
    {
        return this.holder.getLeftSide().getInventory();
    }

    @Override
    public Inventory getRightSide()
    {
        return this.holder.getRightSide().getInventory();
    }

    @Override
    public Slot getSlot(final int slot)
    {
        final int rightSize = this.holder.getInventory().size();
        if (slot < rightSize)
        {
            return this.holder.getRightSide().getInventory().getSlot(slot);
        }
        else
        {
            return this.holder.getLeftSide().getInventory().getSlot(slot - rightSize);
        }
    }

    @Override
    public void update(final Player player) throws IllegalArgumentException
    {
        if (! this.viewers.contains(player))
        {
            throw new IllegalArgumentException("Player must be a viewer of inventory.");
        }

        ((IPlayer) player).getNetworkManager().sendPacket(new PacketPlayClientboundWindowItems(this.windowId, this.getArray()));
    }

    @Override
    public void update()
    {
        this.viewers.stream().filter(h -> h instanceof Player).map(h -> (Player) h).forEach(this::update);
    }

    @Override
    public InventoryType getType()
    {
        return InventoryType.LARGE_CHEST;
    }
}
