package org.diorite.tileentity;

public interface TileEntityFurnace extends TileEntity
{
    short getBurnTime();

    void setBurnTime(short burnTime);

    short getCookTime();

    void setCookTime(short cookTime);
}
