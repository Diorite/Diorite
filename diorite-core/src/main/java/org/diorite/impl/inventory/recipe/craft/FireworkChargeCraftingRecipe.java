package org.diorite.impl.inventory.recipe.craft;

import java.util.Collections;
import java.util.List;

import org.diorite.inventory.GridInventory;
import org.diorite.inventory.item.BaseItemStack;
import org.diorite.inventory.item.ItemStack;
import org.diorite.inventory.recipe.craft.CraftingRecipe;
import org.diorite.inventory.recipe.craft.CraftingRecipeCheckResult;
import org.diorite.material.Material;

public class FireworkChargeCraftingRecipe implements CraftingRecipe
{
    protected final long priority;
    protected final List<ItemStack> result = Collections.singletonList(new BaseItemStack(Material.FIREWORK_CHARGE));

    public FireworkChargeCraftingRecipe(final long priority)
    {
        this.priority = priority;
    }

    @Override
    public CraftingRecipeCheckResult isMatching(final GridInventory inventory)
    {
        return null;
    }

    @Override
    public List<ItemStack> getResult()
    {
        return this.result;
    }

    @Override
    public long getPriority()
    {
        return this.priority;
    }


    @Override
    public boolean isVanilla()
    {
        return true;
    }
}
