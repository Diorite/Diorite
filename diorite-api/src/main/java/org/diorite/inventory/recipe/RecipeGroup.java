/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2016. Diorite (by Bartłomiej Mazur (aka GotoFinal))
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

package org.diorite.inventory.recipe;

import java.util.Iterator;

/**
 * RecipeGroup, it is some kind of fake recipe that act like RecipeManager too. <br>
 * It allow grouping related recipes to speed up lookup for valid one. <br>
 * Like we have 6 recipes to change log to planks for every type of wood,
 * if we put some other items to crafting (not a log) without group diorite need check
 * 6 times near this same recipe, a log of some type. <br>
 * With group we first validate group recipe, and check if this is any type of log,
 * so it will be only one simple check to say that recipe is invalid instead of 6.
 */
public interface RecipeGroup extends Recipe
{
    /**
     * Removes all recipes and add only vanilla recipes again.
     */
    void reset();

    /**
     * Removes ALL recipes.
     */
    void clear();

    /**
     * Returns iterator of recipes, may be used to remove recipes.
     *
     * @return iterator of recipes, may be used to remove recipes.
     */
    Iterator<? extends Recipe> recipeIterator();
}
