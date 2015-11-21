package org.diorite.impl.inventory.recipe;

import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.inventory.GridInventory;
import org.diorite.inventory.recipe.RecipeBuilder;
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
    public RecipeBuilder builder()
    {
        return new RecipeBuilderImpl();
    }

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
        this.builder().priority(getPriority(i++)).shapeless().addIngredient().item(Material.STONE, true).build().result(Material.STONE_BUTTON).buildAndAdd();
        this.builder().priority(getPriority(i++)).shaped().pattern("s ", "S ").addIngredient('s').item(Material.STONE).addIngredient('S').item(Material.STICK).build().result(Material.STONE_SWORD).buildAndAdd();
        this.builder().priority(getPriority(i++)).shaped().pattern("s").addIngredient('s').item(Material.STICK).build().result(WoolMat.WOOL_BLUE).buildAndAdd();
        this.builder().priority(getPriority(i++)).shapeless().addIngredient().item(Material.STICK).addIngredient().item(Material.STONE).build().result(Material.STONE_AXE).buildAndAdd();
        this.builder().priority(getPriority(i++)).shapeless().addIngredient().item(DyeMat.DYE_LAPIS_LAZULI).addIngredient().item(WoolMat.WOOL_WHITE, false).build().result(WoolMat.WOOL_BLUE).buildAndAdd();
        this.builder().priority(getPriority(i++)).shapeless().addIngredient().item(Material.APPLE, false).replacement(Material.DIRT, 16).simpleValidator(item -> "Diorite".equals(item.getItemMeta().getDisplayName())).build().result(Material.GOLDEN_APPLE).buildAndAdd();
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("recipes", this.recipes).toString();
    }
}
