package org.diorite.impl.block;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.impl.inventory.block.BeaconInventoryImpl;
import org.diorite.block.Beacon;
import org.diorite.block.Block;
import org.diorite.inventory.Inventory;
import org.diorite.inventory.block.BeaconInventory;
import org.diorite.inventory.item.meta.PotionMeta.PotionTypes;
import org.diorite.tileentity.TileEntityBeacon;

public class BeaconImpl extends BlockStateImpl implements Beacon
{
    private final TileEntityBeacon tileEntity;
    private final BeaconInventory  inventory;

    private BeaconImpl(final Block block)
    {
        super(block);

        this.tileEntity = (TileEntityBeacon) block.getWorld().getTileEntity(block.getLocation());
        this.inventory = new BeaconInventoryImpl(this);
    }

    @Override
    public Inventory getInventory()
    {
        return this.inventory;
    }

    @Override
    public int getLevel()
    {
        return this.tileEntity.getLevel();
    }

    @Override
    public void setLevel(final int level)
    {
        this.tileEntity.setLevel(level);
    }

    @Override
    public PotionTypes getPrimaryEffect()
    {
        return this.tileEntity.getPrimaryEffect();
    }

    @Override
    public void setPrimaryEffect(final PotionTypes effect)
    {
        this.tileEntity.setPrimaryEffect(effect);
    }

    @Override
    public PotionTypes getSecondaryEffect()
    {
        return this.tileEntity.getSecondaryEffect();
    }

    @Override
    public void setSecondaryEffect(final PotionTypes effect)
    {
        this.tileEntity.setSecondaryEffect(effect);
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("tileEntity", this.tileEntity).append("inventory", this.inventory).toString();
    }
}
