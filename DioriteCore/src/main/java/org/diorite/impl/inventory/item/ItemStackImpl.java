package org.diorite.impl.inventory.item;

import java.util.Optional;
import java.util.UUID;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.impl.GameObjectImpl;
import org.diorite.inventory.Inventory;
import org.diorite.inventory.item.ItemStack;
import org.diorite.material.Material;

public class ItemStackImpl extends GameObjectImpl implements ItemStack
{
    // TODO: lore, name and other stuff
    private Material     material;
    private int          amount;
    private short        durability;
    private Inventory    location;
    private ItemMetaImpl itemMeta;

    public ItemStackImpl(final UUID uuid, final Material material, final int amount, final int durability)
    {
        super(uuid);
        this.material = material;
        this.amount = amount;
        this.durability = (short) durability;
    }

    public ItemStackImpl(final UUID uuid, final Material material)
    {
        this(uuid, material, 1);
    }

    public ItemStackImpl(final UUID uuid, final Material material, final int amount)
    {
        this(uuid, material, amount, 0);
    }

    public ItemStackImpl(final Material material)
    {
        this(UUID.randomUUID(), material, 1);
    }

    public ItemStackImpl(final Material material, final int amount)
    {
        this(UUID.randomUUID(), material, amount, 0);
    }

    public ItemStackImpl(final Material material, final int amount, final int durability)
    {
        this(UUID.randomUUID(), material, amount, durability);
    }

    @Override
    public Material getMaterial()
    {
        return this.material;
    }

    public void setMaterial(final Material material)
    {
        this.material = material;
    }

    @Override
    public int getAmount()
    {
        return this.amount;
    }

    public void setAmount(final int amount)
    {
        this.amount = amount;
    }

    @Override
    public short getDurability()
    {
        return this.durability;
    }

    public void setDurability(final short durability)
    {
        this.durability = durability;
    }

    public Optional<Inventory> getLocation()
    {
        return Optional.ofNullable(this.location);
    }

    public void setLocation(final Inventory inventory)
    {
        if (! inventory.contains(this))
        {
            inventory.add(this);
        }
        this.location = inventory;
    }

    @Override
    public ItemMetaImpl getItemMeta()
    {
        // TODO: never return null, and always return valid type
        if (this.itemMeta == null)
        {
            this.itemMeta = new SimpleItemMetaImpl(null);
        }
        return this.itemMeta;
    }

    public void setItemMeta(final ItemMetaImpl itemMeta)
    {
        // TODO: add type check
        this.itemMeta = itemMeta;
    }

    public void update()
    {
        // TODO
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("material", this.material).append("amount", this.amount).append("durability", this.durability).append("location", this.location).append("itemMeta", this.itemMeta).toString();
    }
}
