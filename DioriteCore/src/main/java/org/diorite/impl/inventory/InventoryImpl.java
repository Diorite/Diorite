package org.diorite.impl.inventory;

import java.util.UUID;

import org.diorite.impl.GameObjectImpl;
import org.diorite.inventory.Inventory;

public abstract class InventoryImpl extends GameObjectImpl implements Inventory
{
    public InventoryImpl(final UUID uuid)
    {
        super(uuid);
    }

    public InventoryImpl()
    {
        super(UUID.randomUUID());
    }
}
