package org.diorite.impl.inventory;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.entity.ArmoredEntity;
import org.diorite.inventory.EntityEquipment;
import org.diorite.inventory.item.ItemStackArray;

public class EntityEquipmentImpl implements EntityEquipment
{
    protected final ItemStackArray content = ItemStackArray.create(4);
    protected final ArmoredEntity entity;

    public EntityEquipmentImpl(final ArmoredEntity entity)
    {
        this.entity = entity;
    }

    @Override
    public ItemStackArray getContents()
    {
        return this.content;
    }

    @Override
    public ArmoredEntity getEquipmentHolder()
    {
        return this.entity;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("content", this.content).append("entity", this.entity.getId()).toString();
    }
}
