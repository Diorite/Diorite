package org.diorite.impl.permissions.perm;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.permissions.BasicPermission;
import org.diorite.permissions.PermissionLevel;
import org.diorite.permissions.pattern.ExtendedPermissionPattern;

public class CheckExtendedPermission extends BasicPermission
{
    private final ExtendedPermissionPattern pattern;
    private final String                    value;
    private final Object[]                  data;

    public CheckExtendedPermission(final PermissionLevel defaultLevel, final ExtendedPermissionPattern pattern, final String value)
    {
        super(defaultLevel);
        this.pattern = pattern;
        this.value = value;
        this.data = pattern.getValueData(value);
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

    public Object[] getData()
    {
        return this.data;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("pattern", this.pattern).append("value", this.value).append("data", this.data).toString();
    }
}
