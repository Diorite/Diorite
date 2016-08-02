package org.diorite.impl.inventory.block;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.impl.connection.packets.play.clientbound.PacketPlayClientboundWindowItems;
import org.diorite.impl.entity.IPlayer;
import org.diorite.impl.inventory.InventoryImpl;
import org.diorite.impl.inventory.item.ItemStackImplArray;
import org.diorite.block.Furnace;
import org.diorite.entity.Player;
import org.diorite.inventory.InventoryType;
import org.diorite.inventory.block.FurnaceInventory;
import org.diorite.inventory.item.ItemStack;
import org.diorite.inventory.slot.Slot;

public class FurnaceInventoryImpl extends InventoryImpl<Furnace> implements FurnaceInventory
{
    private final int windowId;
    private final ItemStackImplArray content = ItemStackImplArray.create(InventoryType.FURNACE.getSize());
    private final Slot[]             slots   = new Slot[InventoryType.FURNACE.getSize()];

    {
        this.slots[0] = Slot.BASE_CONTAINER_SLOT;
        this.slots[1] = Slot.BASE_FUEL_SLOT;
        this.slots[2] = Slot.BASE_RESULT_SLOT;
    }

    public FurnaceInventoryImpl(final Furnace holder, final int windowId)
    {
        super(holder);
        this.windowId = windowId;
    }

    @Override
    public ItemStack getFuel()
    {
        return this.getItem(1);
    }

    @Override
    public void setFuel(final ItemStack fuel)
    {
        this.setItem(1, fuel);
    }

    @Override
    public ItemStack getSmelting()
    {
        return this.getItem(0);
    }

    @Override
    public void setSmelting(final ItemStack smelting)
    {
        this.setItem(0, smelting);
    }

    @Override
    public ItemStack getResult()
    {
        return this.getItem(2);
    }

    @Override
    public void setResult(final ItemStack result)
    {
        this.setItem(2, result);
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
        return InventoryType.FURNACE;
    }

    @Override
    public Furnace getHolder()
    {
        return super.getHolder();
    }

    @Override
    public int getWindowId()
    {
        return this.windowId;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("windowId", this.windowId).append("content", this.content).append("slots", this.slots).toString();
    }
}
