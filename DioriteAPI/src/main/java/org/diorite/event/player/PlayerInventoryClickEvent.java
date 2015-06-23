package org.diorite.event.player;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.entity.Player;
import org.diorite.inventory.ClickType;
import org.diorite.inventory.item.ItemStack;

public class PlayerInventoryClickEvent extends PlayerEvent
{
    protected final int       windowId;
    protected final int       clickedSlot;
    protected final short     actionNumber;
    protected final ClickType clickType;

    protected final ItemStack clickedItem;
    protected final ItemStack cursorItem;

    /**
     * Construct new player event.
     *
     * @param player       player related to event, can't be null.
     * @param actionNumber action number send by client.
     * @param windowId     id of inventory window.
     * @param clickedSlot  clicked slot number.
     * @param clickType    type of click.
     */
    public PlayerInventoryClickEvent(final Player player, final short actionNumber, final int windowId, final int clickedSlot, final ClickType clickType)
    {
        super(player);
        this.windowId = windowId;
        this.clickedSlot = clickedSlot;
        this.actionNumber = actionNumber;
        this.clickType = clickType;

        this.clickedItem = player.getInventory().getItem(clickedSlot);
        this.cursorItem = player.getInventory().getCursorItem();
    }

    public int getWindowId()
    {
        return this.windowId;
    }

    public int getClickedSlot()
    {
        return this.clickedSlot;
    }

    public short getActionNumber()
    {
        return this.actionNumber;
    }

    public ClickType getClickType()
    {
        return this.clickType;
    }

    public ItemStack getClickedItem()
    {
        return this.clickedItem;
    }

    public ItemStack getCursorItem()
    {
        return this.cursorItem;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("windowId", this.windowId).append("clickedSlot", this.clickedSlot).append("actionNumber", this.actionNumber).append("clickType", this.clickType).toString();
    }
}
