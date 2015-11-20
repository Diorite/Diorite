/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2015 Diorite (by Bartłomiej Mazur (aka GotoFinal))
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package org.diorite.impl;

import org.diorite.impl.inventory.recipe.IRecipeManager;
import org.diorite.impl.inventory.recipe.RecipeManagerImpl;
import org.diorite.impl.permissions.DioritePermissionsManager;
import org.diorite.permissions.PermissionsManager;

/**
 * Class used to manage all event pipelines, other pipelines, plugins and more.
 */
public class ServerManagerImpl implements IServerManager
{
    private final DioriteCore core;
    private PermissionsManager permissionsManager = new DioritePermissionsManager();
    private IRecipeManager     recipeManager      = new RecipeManagerImpl();

    public ServerManagerImpl(final DioriteCore core)
    {
        this.core = core;
    }

    @Override
    public PermissionsManager getPermissionsManager()
    {
        return this.permissionsManager;
    }

    @Override
    public void setPermissionsManager(final PermissionsManager permissionsManager)
    {
        this.permissionsManager = permissionsManager;
    }

    @Override
    public IRecipeManager getRecipeManager()
    {
        return this.recipeManager;
    }

    @Override
    public void setRecipeManager(final IRecipeManager recipeManager)
    {
        this.recipeManager = recipeManager;
    }
}
