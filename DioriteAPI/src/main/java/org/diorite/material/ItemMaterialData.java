package org.diorite.material;

public abstract class ItemMaterialData extends Material
{
    protected ItemMaterialData(final String enumName, final int id, final String minecraftId, final String typeName, final short type)
    {
        super(enumName, id, minecraftId, typeName, type);
    }

    protected ItemMaterialData(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final short type)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type);
    }

    /**
     * Check if item is tool-based item, tool-based item is an item that
     * have some durability limit. <br>
     * Most of them are sublcasses of {@link org.diorite.material.items.ToolMat}
     *
     * @return true if item is tool-based.
     */
    public boolean isTool()
    {
        return false;
    }

    /**
     * Check if item can be placed, so it will change to the block. Like doors. <br>
     * Item like that should implements {@link org.diorite.material.items.PlaceableItemMat}
     * @return if item is placeable.
     */
    public boolean canBePlaced()
    {
        return false;
    }

    /**
     * Check if item can be eaten by player. <br>
     * Item like that should implements {@link org.diorite.material.items.EdibleItemMat}
     * @return if item can be eaten by player
     */
    public boolean isEdible()
    {
        return false;
    }

    @Override
    public boolean isBlock()
    {
        return false;
    }

    @Override
    public abstract ItemMaterialData[] types();
}
