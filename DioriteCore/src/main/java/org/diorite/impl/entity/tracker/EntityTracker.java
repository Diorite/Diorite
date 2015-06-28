package org.diorite.impl.entity.tracker;

import org.diorite.impl.entity.EntityImpl;

@SuppressWarnings({"ObjectEquality", "MagicNumber"})
public class EntityTracker extends BaseTracker<EntityImpl>
{
    public EntityTracker(final EntityImpl entity)
    {
        super(entity);
    }
}
