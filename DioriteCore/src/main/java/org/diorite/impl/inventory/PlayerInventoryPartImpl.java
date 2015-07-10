package org.diorite.impl.inventory;

import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.impl.inventory.item.ItemStackImplArray;
import org.diorite.entity.Player;
import org.diorite.inventory.PlayerInventory;
import org.diorite.inventory.PlayerInventoryPart;

public abstract class PlayerInventoryPartImpl extends InventoryImpl<Player> implements PlayerInventoryPart
{
    protected final PlayerInventoryImpl playerInventory;
    protected final ItemStackImplArray  content;

    protected PlayerInventoryPartImpl(final PlayerInventoryImpl playerInventory, final ItemStackImplArray content)
    {
        super(playerInventory.getHolder());
        this.content = content;
        Validate.notNull(playerInventory, "Base player inventory can't be null!");
        this.playerInventory = playerInventory;
    }

    @Override
    public Player getHolder()
    {
        return this.playerInventory.getHolder();
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("playerInventory", this.playerInventory).toString();
    }

    @Override
    public PlayerInventory getPlayerInventory()
    {
        return this.playerInventory;
    }

    @Override
    public ItemStackImplArray getArray()
    {
        return this.content;
    }

    @Override
    public void update(final Player player) throws IllegalArgumentException
    {
        this.playerInventory.update(player);
    }

    @Override
    public void update()
    {
        this.playerInventory.update();
    }

    @Override
    public void softUpdate()
    {
        throw new UnsupportedOperationException("soft update should be called only for root EQ.");
    }
}
