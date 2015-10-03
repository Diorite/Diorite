package org.diorite.permissions.pattern.group;

import org.diorite.utils.math.DioriteMathUtils;

interface SpecialNumberGroup extends SpecialGroup<Long>
{
    @Override
    default Long parseData(final String data)
    {
        if (data == null)
        {
            return null;
        }
        return DioriteMathUtils.asLong(data);
    }
}
