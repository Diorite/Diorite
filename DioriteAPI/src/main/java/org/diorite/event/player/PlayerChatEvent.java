package org.diorite.event.player;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.chat.component.TextComponent;
import org.diorite.entity.Player;

/**
 * When player type something on chat, and it should be now displayed to whole server.
 */
public class PlayerChatEvent extends PlayerEvent
{
    protected TextComponent message;

    /**
     * Construct new chat event with given player and message.
     *
     * @param player  player that typed message.
     * @param message message that player typed.
     */
    public PlayerChatEvent(final Player player, final TextComponent message)
    {
        super(player);
        this.message = message;
    }

    /**
     * @return message typed by player, may be already changed by other event handlers
     */
    public TextComponent getMessage()
    {
        return this.message;
    }

    /**
     * Change message typed by player.
     *
     * @param message new message.
     */
    public void setMessage(final TextComponent message)
    {
        this.message = message;
    }

    @Override
    public boolean equals(final Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (! (o instanceof PlayerChatEvent))
        {
            return false;
        }
        if (! super.equals(o))
        {
            return false;
        }

        final PlayerChatEvent that = (PlayerChatEvent) o;
        return ! ((this.message != null) ? ! this.message.equals(that.message) : (that.message != null));

    }

    @Override
    public int hashCode()
    {
        int result = super.hashCode();
        result = (31 * result) + ((this.message != null) ? this.message.hashCode() : 0);
        return result;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("message", (this.message == null) ? null : this.message.toLegacyText()).toString();
    }
}
