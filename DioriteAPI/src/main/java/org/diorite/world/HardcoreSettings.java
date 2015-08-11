package org.diorite.world;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class HardcoreSettings
{
    private boolean        enabled;
    private HardcoreAction onDeath;

    public HardcoreSettings(final boolean enabled)
    {
        this.enabled = enabled;
        this.onDeath = HardcoreAction.BAN_PLAYER;
    }

    public HardcoreSettings(final boolean enabled, final HardcoreAction onDeath)
    {
        this.enabled = enabled;
        this.onDeath = (onDeath == null) ? HardcoreAction.BAN_PLAYER : onDeath;
    }

    public boolean isEnabled()
    {
        return this.enabled;
    }

    public void setEnabled(final boolean enabled)
    {
        this.enabled = enabled;
    }

    public HardcoreAction getOnDeath()
    {
        return this.onDeath;
    }

    public void setOnDeath(final HardcoreAction onDeath)
    {
        this.onDeath = onDeath;
    }

    public enum HardcoreAction
    {
        BAN_PLAYER,
        DELETE_PLAYER,
        BAN_ALL_PLAYERS,
        DELETE_ALL_PLAYERS,
        DELETE_WORLD
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("enabled", this.enabled).append("onDeath", this.onDeath).toString();
    }
}
