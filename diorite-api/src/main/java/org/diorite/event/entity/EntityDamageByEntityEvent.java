package org.diorite.event.entity;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.entity.Entity;

public class EntityDamageByEntityEvent extends EntityDamageEvent
{
    private final Entity damager;

    public EntityDamageByEntityEvent(final Entity entity, final Entity damager, final DamageCause cause)
    {
        super(entity, cause);
        this.damager = damager;
    }

    public Entity getDamager()
    {
        return this.damager;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("damager", this.damager).toString();
    }
}
