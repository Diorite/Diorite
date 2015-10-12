package org.diorite.permissions;

import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public abstract class BasicPermission implements Permission
{
    protected PermissionLevel defaultLevel;

    protected BasicPermission(final PermissionLevel defaultLevel)
    {
        this.defaultLevel = defaultLevel;
    }

    @Override
    public PermissionLevel getDefaultLevel()
    {
        return this.defaultLevel;
    }

    @Override
    public void setDefaultLevel(final PermissionLevel defaultLevel)
    {
        Validate.notNull(defaultLevel, "Level can't be null.");
        this.defaultLevel = defaultLevel;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("defaultLevel", this.defaultLevel).toString();
    }
}
