package org.diorite.impl.permissions;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.entity.Player;
import org.diorite.permissions.PlayerPermissionsContainer;

public class PlayerPermissionsContainerImpl extends GroupablePermissionsContainerImpl implements PlayerPermissionsContainer
{
    protected boolean op;

    protected PlayerPermissionsContainerImpl(final boolean op, final Player player)
    {
        super(player);
        this.op = op;
    }

    @Override
    public boolean isOp()
    {
        return this.op;
    }

    @Override
    public boolean setOp(final boolean op)
    {
        // TODO: event/pipeline
        this.op = op;
        return true;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("op", this.op).toString();
    }
}
