package org.diorite.impl.inventory.block;

import org.diorite.impl.connection.packets.play.clientbound.PacketPlayClientboundWindowItems;
import org.diorite.impl.entity.IPlayer;
import org.diorite.impl.inventory.InventoryImpl;
import org.diorite.impl.inventory.item.ItemStackImplArray;
import org.diorite.block.BrewingStand;
import org.diorite.entity.Player;
import org.diorite.inventory.InventoryType;
import org.diorite.inventory.block.BrewingStandInventory;
import org.diorite.inventory.item.ItemStack;
import org.diorite.inventory.slot.Slot;

public class BrewingStandInventoryImpl extends InventoryImpl<BrewingStand> implements BrewingStandInventory
{
    private final ItemStackImplArray content = ItemStackImplArray.create(InventoryType.BREWING.getSize());
    private final Slot[]             slots   = new Slot[InventoryType.BREWING.getSize()];

    public BrewingStandInventoryImpl(final BrewingStand holder)
    {
        super(holder);

        for (int i = 0; i < this.slots.length; i++)
        {
            this.slots[i] = Slot.BASE_CONTAINER_SLOT;
        }
    }

    @Override
    public ItemStack getLeftInput()
    {
        return this.getItem(0);
    }

    @Override
    public void setLeftInput(final ItemStack input)
    {
        this.setItem(0, input);
    }

    @Override
    public ItemStack getMiddleInput()
    {
        return this.getItem(1);
    }

    @Override
    public void setMiddleInput(final ItemStack input)
    {
        this.setItem(1, input);
    }

    @Override
    public ItemStack getRightInput()
    {
        return this.getItem(2);
    }

    @Override
    public void setRightInput(final ItemStack input)
    {
        this.setItem(2, input);
    }

    @Override
    public ItemStack getIngridient()
    {
        return this.getItem(3);
    }

    @Override
    public void setIngridient(final ItemStack ingridient)
    {
        this.setItem(3, ingridient);
    }

    @Override
    public ItemStack getFuel()
    {
        return this.getItem(4);
    }

    @Override
    public void setFuel(final ItemStack fuel)
    {
        this.setItem(4, fuel);
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
        return InventoryType.BREWING;
    }

    @Override
    public ItemStackImplArray getArray()
    {
        return this.content;
    }
}
