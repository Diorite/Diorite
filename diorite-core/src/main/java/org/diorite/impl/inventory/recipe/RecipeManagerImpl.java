package org.diorite.impl.inventory.recipe;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.diorite.impl.inventory.recipe.craft.ArrayRecipePattern;
import org.diorite.impl.inventory.recipe.craft.SimpleShapedRecipeImpl;
import org.diorite.impl.inventory.recipe.craft.SimpleShapelessRecipeImpl;
import org.diorite.impl.inventory.recipe.craft.SimpleShapelessSingleRecipeImpl;
import org.diorite.inventory.GridInventory;
import org.diorite.inventory.item.BaseItemStack;
import org.diorite.inventory.recipe.RecipeItem;
import org.diorite.inventory.recipe.SimpleRecipeItem;
import org.diorite.inventory.recipe.craft.Recipe;
import org.diorite.inventory.recipe.craft.RecipeCheckResult;
import org.diorite.material.Material;
import org.diorite.material.blocks.WoolMat;
import org.diorite.material.items.DyeMat;

public class RecipeManagerImpl implements IRecipeManager
{
    final Set<Recipe> recipes = new TreeSet<>((r1, r2) -> {
        if (r1.equals(r2))
        {
            return 0;
        }
        int i = Long.compare(r1.getPriority(), r2.getPriority());
        if (i != 0)
        {
            return i;
        }
        return 1;
    });

    @Override
    public boolean add(final Recipe recipe)
    {
        return this.recipes.add(recipe);
    }

    @Override
    public boolean remove(final Recipe recipe)
    {
        return this.recipes.remove(recipe);
    }

    @Override
    public void reset()
    {
        this.clear();
        this.addDefaultRecipes();
    }

    @Override
    public void clear()
    {
        this.recipes.clear();
    }

    @Override
    public Iterator<Recipe> recipeIterator()
    {
        return this.recipes.iterator();
    }

    @Override
    public RecipeCheckResult matchRecipe(final GridInventory grid)
    {
        for (final Recipe recipe : this.recipes)
        {
            final RecipeCheckResult result = recipe.isMatching(grid);
            if (result != null)
            {
                return result;
            }
        }
        return null;
    }

    private static long getPriority(final int i)
    {
        return Recipe.DIORITE_START + ((2 * i) * Recipe.DIORITE_SPACE);
    }

    @Override
    public void addDefaultRecipes()
    {
        int i = 0;
        this.recipes.add(new SimpleShapelessSingleRecipeImpl(new SimpleRecipeItem(Material.STONE, true), new BaseItemStack(Material.STONE_BUTTON), getPriority(i++)));
        this.recipes.add(new SimpleShapedRecipeImpl(new ArrayRecipePattern(new RecipeItem[][]{{new SimpleRecipeItem(Material.STONE), null}, {null, new SimpleRecipeItem(Material.STICK)}}), new BaseItemStack(Material.STONE_SWORD), getPriority(i++)));
        this.recipes.add(new SimpleShapedRecipeImpl(new ArrayRecipePattern(new RecipeItem[][]{{new SimpleRecipeItem(Material.STICK)}}), new BaseItemStack(WoolMat.WOOL_BLUE), getPriority(i++)));
        {
            final List<RecipeItem> recipeItems = new ArrayList<>(2);
            recipeItems.add(new SimpleRecipeItem(Material.STICK));
            recipeItems.add(new SimpleRecipeItem(Material.STONE));
            this.recipes.add(new SimpleShapelessRecipeImpl(recipeItems, new BaseItemStack(Material.STONE_AXE), getPriority(i++)));
        }
        {
            final List<RecipeItem> recipeItems = new ArrayList<>(2);
            recipeItems.add(new SimpleRecipeItem(DyeMat.DYE_LAPIS_LAZULI));
            recipeItems.add(new SimpleRecipeItem(WoolMat.WOOL_WHITE));
            this.recipes.add(new SimpleShapelessRecipeImpl(recipeItems, new BaseItemStack(WoolMat.WOOL_BLUE), getPriority(i++)));
        }
        // TODO
    }

    {
        this.addDefaultRecipes();
    }
}
