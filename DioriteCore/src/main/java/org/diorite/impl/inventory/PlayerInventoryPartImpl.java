package org.diorite.impl.inventory;

import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.entity.Player;
import org.diorite.inventory.PlayerInventory;
import org.diorite.inventory.item.ItemStackArray;

public abstract class PlayerInventoryPartImpl extends InventoryImpl<Player> implements org.diorite.inventory.PlayerInventoryPart
{
    protected final PlayerInventory playerInventory;
    protected final ItemStackArray  content;

    protected PlayerInventoryPartImpl(final PlayerInventory playerInventory, final ItemStackArray content)
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
    public ItemStackArray getContents()
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
}
