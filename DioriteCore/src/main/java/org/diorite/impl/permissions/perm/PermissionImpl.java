package org.diorite.impl.permissions.perm;

import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.impl.permissions.pattern.PermissionPatternImpl;
import org.diorite.permissions.BasicPermission;
import org.diorite.permissions.PermissionLevel;
import org.diorite.permissions.pattern.PermissionPattern;

public class PermissionImpl extends BasicPermission
{
    private final PermissionPattern pattern;

    public PermissionImpl(final PermissionLevel defaultLevel, final PermissionPattern pattern)
    {
        super(defaultLevel);
        Validate.notNull(pattern, "Pattern can't be null.");
        this.pattern = pattern;
    }

    public PermissionImpl(final PermissionPattern pattern)
    {
        this(PermissionLevel.OP, pattern);
    }

    public PermissionImpl(final PermissionLevel defaultLevel, final String permission)
    {
        this(defaultLevel, new PermissionPatternImpl(permission));
    }

    public PermissionImpl(final String permission)
    {
        this(PermissionLevel.OP, new PermissionPatternImpl(permission));
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
        if (! (o instanceof PermissionImpl))
        {
            return false;
        }

        final PermissionImpl that = (PermissionImpl) o;

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
