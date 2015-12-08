package org.diorite.impl.inventory.recipe;

import java.io.Serializable;
import java.util.Comparator;

import org.diorite.inventory.recipe.Recipe;

public class RecipeComparator implements Comparator<Recipe>, Serializable
{
    private static final long serialVersionUID = 0;

    public static final RecipeComparator INSTANCE = new RecipeComparator();

    @Override
    public int compare(final Recipe r1, final Recipe r2)
    {
        if (r1.equals(r2))
        {
            return 0;
        }
        final int i = Long.compare(r1.getPriority(), r2.getPriority());
        if (i != 0)
        {
            return i;
        }
        return 1;
    }
}
