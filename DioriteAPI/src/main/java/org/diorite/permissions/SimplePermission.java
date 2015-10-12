package org.diorite.permissions;

import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.permissions.pattern.PermissionPattern;
import org.diorite.permissions.pattern.SimplePermissionPattern;

public class SimplePermission extends BasicPermission
{
    private final SimplePermissionPattern pattern;

    public SimplePermission(final PermissionLevel defaultLevel, final SimplePermissionPattern pattern)
    {
        super(defaultLevel);
        Validate.notNull(pattern, "Pattern can't be null.");
        this.pattern = pattern;
    }

    public SimplePermission(final SimplePermissionPattern pattern)
    {
        this(PermissionLevel.OP, pattern);
    }

    public SimplePermission(final PermissionLevel defaultLevel, final String permission)
    {
        this(defaultLevel, new SimplePermissionPattern(permission));
    }

    public SimplePermission(final String permission)
    {
        this(PermissionLevel.OP, new SimplePermissionPattern(permission));
    }

    @Override
    public PermissionPattern getPattern()
    {
        return this.pattern;
    }

    @Override
    public String getValue()
    {
        return this.pattern.getValue();
    }

    @Override
    public boolean equals(final Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (! (o instanceof SimplePermission))
        {
            return false;
        }

        final SimplePermission that = (SimplePermission) o;

        return this.pattern.equals(that.pattern);
    }

    @Override
    public int hashCode()
    {
        return this.pattern.hashCode();
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("pattern", this.pattern).toString();
    }
}
