package org.diorite.event.entity;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.entity.Entity;

public class EntityDamageEvent extends EntityEvent
{
    protected       double      damage;
    protected final DamageCause cause;

    public EntityDamageEvent(final Entity entity, final DamageCause cause)
    {
        super(entity);
        this.cause = cause;
    }

    public double getDamage()
    {
        return this.damage;
    }

    public void setDamage(final double damage)
    {
        this.damage = damage;
    }

    public DamageCause getCause()
    {
        return this.cause;
    }

    public enum DamageCause
    {
        // TODO
        ENTITY_ATTACK
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("damage", this.damage).append("cause", this.cause).toString();
    }
}
