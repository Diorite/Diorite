package org.diorite.impl.inventory.block;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.impl.connection.packets.play.clientbound.PacketPlayClientboundWindowItems;
import org.diorite.impl.entity.IPlayer;
import org.diorite.impl.inventory.InventoryImpl;
import org.diorite.impl.inventory.item.ItemStackImplArray;
import org.diorite.block.Beacon;
import org.diorite.entity.Player;
import org.diorite.inventory.InventoryType;
import org.diorite.inventory.block.BeaconInventory;
import org.diorite.inventory.item.ItemStack;
import org.diorite.inventory.slot.Slot;
import org.diorite.inventory.slot.SlotType;
import org.diorite.material.items.DiamondMat;
import org.diorite.material.items.EmeraldMat;
import org.diorite.material.items.GoldIngotMat;
import org.diorite.material.items.IronIngotMat;

public class BeaconInventoryImpl extends InventoryImpl<Beacon> implements BeaconInventory
{
    private final ItemStackImplArray content = ItemStackImplArray.create(InventoryType.BEACON.getSize());
    private final Slot[]             slots   = new Slot[InventoryType.BEACON.getSize()];

    public BeaconInventoryImpl(final Beacon holder)
    {
        super(holder);

        for (int i = 0; i < this.slots.length; i++)
        {
            this.slots[i] = Slot.BASE_CONTAINER_SLOT;
        }

        this.slots[0] = new BeaconSlot();
    }

    @Override
    public ItemStackImplArray getArray()
    {
        return this.content;
    }

    @Override
    public Slot getSlot(final int slot)
    {
        return this.slots[slot];
    }

    @Override
    public void update(final Player player) throws IllegalArgumentException
    {
        if (! this.viewers.contains(player))
        {
            throw new IllegalArgumentException("Player must be a viewer of inventory.");
        }

        ((IPlayer) player).getNetworkManager().sendPacket(new PacketPlayClientboundWindowItems(this.windowId, this.content));
    }

    @Override
    public void update()
    {
        this.viewers.stream().filter(h -> h instanceof Player).map(h -> (Player) h).forEach(this::update);
    }

    @Override
    public InventoryType getType()
    {
        return InventoryType.BEACON;
    }

    private static class BeaconSlot extends Slot
    {
        public BeaconSlot()
        {
            super(SlotType.CRAFTING);
        }

        @Override
        public ItemStack canHoldItem(final ItemStack item)
        {
            return ((item.getMaterial() instanceof EmeraldMat) || (item.getMaterial() instanceof DiamondMat) || (item.getMaterial() instanceof GoldIngotMat) || (item.getMaterial() instanceof IronIngotMat)) ? item : null;
        }
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("content", this.content).append("slots", this.slots).toString();
    }
}
