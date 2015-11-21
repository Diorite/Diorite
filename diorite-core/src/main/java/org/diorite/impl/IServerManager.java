package org.diorite.impl;

import org.diorite.impl.inventory.recipe.IRecipeManager;
import org.diorite.ServerManager;

public interface IServerManager extends ServerManager
{
    void setRecipeManager(IRecipeManager recipeManager);

    @Override
    IRecipeManager getRecipeManager();
}
