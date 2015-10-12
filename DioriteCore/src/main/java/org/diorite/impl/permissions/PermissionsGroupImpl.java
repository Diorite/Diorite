package org.diorite.impl.permissions;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.Diorite;
import org.diorite.permissions.PermissionsContainer;
import org.diorite.permissions.PermissionsGroup;

public class PermissionsGroupImpl implements PermissionsGroup
{
    protected final String name;
    protected final PermissionsContainer permissionsContainer = Diorite.getServerManager().getPermissionsManager().createContainer(this);

    public PermissionsGroupImpl(final String name)
    {
        this.name = name.intern();
    }

    @Override
    public String getName()
    {
        return this.name;
    }

    @Override
    public PermissionsContainer getPermissionsContainer()
    {
        return this.permissionsContainer;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("name", this.name).append("permissionsContainer", this.permissionsContainer).toString();
    }
}
