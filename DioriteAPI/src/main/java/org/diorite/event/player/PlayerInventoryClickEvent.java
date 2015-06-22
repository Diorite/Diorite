package org.diorite.event.player;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.entity.Player;
import org.diorite.inventory.ClickType;
import org.diorite.inventory.item.ItemStack;

public class PlayerInventoryClickEvent extends PlayerEvent
{
    private final int windowId;
    private final int clickedSlot;
    private final short actionNumber;
    private final ClickType clickType;

    /**
     * Construct new player event.
     *
     * @param player player related to event, can't be null.
     * @param actionNumber
     * @param windowId
     * @param clickedSlot
     * @param clickType
     */
    public PlayerInventoryClickEvent(final Player player, final short actionNumber, final int windowId, final int clickedSlot, final ClickType clickType)
    {
        super(player);
        this.windowId = windowId;
        this.clickedSlot = clickedSlot;
        this.actionNumber = actionNumber;
        this.clickType = clickType;
    }

    public int getWindowId()
    {
        return this.windowId;
    }

    public int getClickedSlot()
    {
        return this.clickedSlot;
    }

    public ItemStack getClickedItem()
    {
        return this.getPlayer().getInventory().getItem(this.clickedSlot);
    }

    public short getActionNumber()
    {
        return this.actionNumber;
    }

    public ClickType getClickType()
    {
        return this.clickType;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("windowId", this.windowId).append("clickedSlot", this.clickedSlot).append("actionNumber", this.actionNumber).append("clickType", this.clickType).toString();
    }
}
