package org.diorite.event.player;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.entity.Player;
import org.diorite.inventory.ClickType;
import org.diorite.inventory.item.ItemStack;

public class PlayerInventoryClickEvent extends PlayerEvent
{
    private int windowId;
    private int clickedSlot;
    private short actionNumber;
    private ClickType clickType;

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

    public void setWindowId(final int windowId)
    {
        this.windowId = windowId;
    }

    public int getClickedSlot()
    {
        return this.clickedSlot;
    }

    public void setClickedSlot(final int clickedSlot)
    {
        this.clickedSlot = clickedSlot;
    }

    public short getActionNumber()
    {
        return this.actionNumber;
    }

    public void setActionNumber(final short actionNumber)
    {
        this.actionNumber = actionNumber;
    }

    public ClickType getClickType()
    {
        return this.clickType;
    }

    public void setClickType(final ClickType clickType)
    {
        this.clickType = clickType;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("windowId", this.windowId).append("clickedSlot", this.clickedSlot).append("actionNumber", this.actionNumber).append("clickType", this.clickType).toString();
    }
}
