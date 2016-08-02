package org.diorite.impl.tileentity;

import java.util.Set;

import org.diorite.block.Block;
import org.diorite.block.BlockLocation;
import org.diorite.entity.Item;
import org.diorite.inventory.item.ItemStack;
import org.diorite.nbt.NbtTagCompound;
import org.diorite.tileentity.TileEntityFurnace;
import org.diorite.utils.math.DioriteRandom;

public class TileEntityFurnaceImpl extends TileEntityImpl implements TileEntityFurnace
{
    private final Block block;
    private       short       burnTime;
    private       short       cookTime;
    private       short       fuelTime; //AS TICKS
    private       ItemStack[] items    = new ItemStack[3];

    public TileEntityFurnaceImpl(final Block block)
    {
        super(block.getLocation());
        this.block = block;
    }

    @Override
    public void doTick(final int tps)
    {
        //TODO
    }

    @Override
    public Block getBlock()
    {
        return block;
    }

    @Override
    public void simulateDrop(final DioriteRandom rand, final Set<ItemStack> drops)
    {
        //TODO
    }

    //TODO
    public static int getFuelTime(ItemStack fuel)
    {
        if(fuel == null)
        {
            return 0;
        }

        return 0;
    }

    public static boolean isFuel(ItemStack fuel)
    {
        return getFuelTime(fuel) > 0;
    }

    @Override
    public void loadFromNbt(final NbtTagCompound nbtTileEntity)
    {
        super.loadFromNbt(nbtTileEntity);

        //TODO: LOAD ITEMS

        burnTime = nbtTileEntity.getShort("BurnTime");
        cookTime = nbtTileEntity.getShort("CookTime");
    }

    @Override
    public void saveToNbt(final NbtTagCompound nbtTileEntity)
    {
        super.saveToNbt(nbtTileEntity);

        //TODO: SAVE ITEMS

        nbtTileEntity.setShort("BurnTime", this.burnTime);
        nbtTileEntity.setShort("CookTime", this.cookTime);
    }
}
