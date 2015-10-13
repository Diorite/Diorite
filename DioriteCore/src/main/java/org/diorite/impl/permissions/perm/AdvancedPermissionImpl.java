package org.diorite.impl.permissions.perm;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.permissions.AdvancedPermission;
import org.diorite.permissions.BasicPermission;
import org.diorite.permissions.PermissionLevel;
import org.diorite.permissions.pattern.ExtendedPermissionPattern;

/**
 * Represents advanced version of permission object.
 */
public class AdvancedPermissionImpl extends BasicPermission implements AdvancedPermission
{
    /**
     * Pattern of permission.
     */
    protected final ExtendedPermissionPattern pattern;
    /**
     * String representation of permission.
     */
    protected final String                    value;
    /**
     * Data from permission.
     */
    protected final Object[]                  data;

    public AdvancedPermissionImpl(final ExtendedPermissionPattern pattern, final PermissionLevel defaultLevel, final String permission)
    {
        super(defaultLevel);
        this.pattern = pattern;
        this.value = permission;
        this.data = pattern.getPatternData(permission);
    }

    @Override
    public Object[] getData()
    {
        return this.data;
    }

    @Override
    public ExtendedPermissionPattern getPattern()
    {
        return this.pattern;
    }

    @Override
    public String getValue()
    {
        return this.value;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("pattern", this.pattern).append("value", this.value).append("data", this.data).toString();
    }
}
