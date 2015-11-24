package org.diorite.impl.inventory.recipe;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.impl.inventory.recipe.craft.CharMapRecipePatternImpl;
import org.diorite.impl.inventory.recipe.craft.ShapedRecipeImpl;
import org.diorite.impl.inventory.recipe.craft.ShapelessRecipeImpl;
import org.diorite.impl.inventory.recipe.craft.ShapelessSingleRecipeImpl;
import org.diorite.entity.Player;
import org.diorite.inventory.item.ItemStack;
import org.diorite.inventory.recipe.RecipeBuilder;
import org.diorite.inventory.recipe.RecipeItem;
import org.diorite.inventory.recipe.RecipeItemBuilder;
import org.diorite.inventory.recipe.craft.Recipe;
import org.diorite.inventory.recipe.craft.RecipePattern;
import org.diorite.inventory.recipe.craft.ShapedRecipe;
import org.diorite.inventory.recipe.craft.ShapelessRecipe;

import gnu.trove.map.TCharObjectMap;
import gnu.trove.map.hash.TCharObjectHashMap;

public class RecipeBuilderImpl implements RecipeBuilder
{
    private ItemStack result;
    private BiFunction<Player, ItemStack[][], ItemStack> func     = null;
    private boolean                                    vanilla  = false;
    private Long                                       priority = null;

    @Override
    public RecipeBuilder result(final ItemStack itemStack)
    {
        this.result = itemStack;
        return this;
    }

    @Override
    public RecipeBuilder result(final BiFunction<Player, ItemStack[][], ItemStack> itemStack)
    {
        this.func = itemStack;
        return this;
    }

    @Override
    public RecipeBuilder vanilla(final boolean vanilla)
    {
        this.vanilla = vanilla;
        return this;
    }

    @Override
    public RecipeBuilder priority(final long priority)
    {
        this.priority = priority;
        return this;
    }

    @Override
    public ShapelessRecipeBuilder shapeless()
    {
        return new ShapelessRecipeBuilderImpl(this);
    }

    @Override
    public ShapedRecipeBuilder shaped()
    {
        return new ShapedRecipeBuilderImpl(this);
    }

    @Override
    public Recipe build() throws RuntimeException
    {
        throw new RuntimeException("Unknown type of recipe.");
    }

    @Override
    public Recipe buildAndAdd() throws RuntimeException
    {
        throw new RuntimeException("Unknown type of recipe.");
    }

    private static class ShapelessRecipeBuilderImpl implements ShapelessRecipeBuilder
    {
        private final RecipeBuilderImpl oldBuilder;
        private final List<RecipeItem> ingredients = new ArrayList<>(10);

        ShapelessRecipeBuilderImpl(final RecipeBuilderImpl recipeBuilder)
        {
            this.oldBuilder = recipeBuilder;
        }

        @Override
        public ShapelessRecipeBuilder result(final ItemStack itemStack)
        {
            this.oldBuilder.result(itemStack);
            return this;
        }

        @Override
        public ShapelessRecipeBuilder result(final BiFunction<Player, ItemStack[][], ItemStack> itemStack)
        {
            this.oldBuilder.result(itemStack);
            return this;
        }

        @Override
        public ShapelessRecipeBuilder vanilla(final boolean vanilla)
        {
            this.oldBuilder.vanilla(vanilla);
            return this;
        }

        @Override
        public ShapelessRecipeBuilder priority(final long priority)
        {
            this.oldBuilder.priority(priority);
            return this;
        }

        @Override
        public ShapelessRecipe build()
        {
            Validate.notEmpty(this.ingredients, "ingredients list can't be empty.");
            Validate.notNull(this.oldBuilder.result, "result item can't be null.");
            if (this.oldBuilder.priority == null)
            {
                this.priority(Recipe.DEFAULT_SHAPELESS_RECIPE_PRIORITY);
            }
            if (this.ingredients.size() == 1)
            {
                if (this.oldBuilder.func == null)
                {
                    return new ShapelessSingleRecipeImpl(this.ingredients.iterator().next(), this.oldBuilder.result, this.oldBuilder.priority, this.oldBuilder.vanilla);
                }
                else
                {
                    return new ShapelessSingleRecipeImpl(this.ingredients.iterator().next(), this.oldBuilder.result, this.oldBuilder.priority, this.oldBuilder.vanilla, this.oldBuilder.func);
                }
            }
            if (this.oldBuilder.func == null)
            {
                return new ShapelessRecipeImpl(this.ingredients, this.oldBuilder.result, this.oldBuilder.priority, this.oldBuilder.vanilla);
            }
            else
            {
                return new ShapelessRecipeImpl(this.ingredients, this.oldBuilder.result, this.oldBuilder.priority, this.oldBuilder.vanilla, this.oldBuilder.func);
            }
        }

        @Override
        public ShapelessRecipeItemBuilder addIngredient()
        {
            return new ShapelessRecipeItemBuilder(this);
        }

        @Override
        public ShapelessRecipeBuilder addIngredient(final RecipeItem item)
        {
            this.ingredients.add(item);
            return this;
        }

        private static class ShapelessRecipeItemBuilder extends BaseRecipeItemBuilderImpl<ShapelessRecipeBuilder, RecipeItemBuilder.ShapelessRecipeItemBuilder> implements RecipeItemBuilder.ShapelessRecipeItemBuilder
        {
            protected ShapelessRecipeItemBuilder(final ShapelessRecipeBuilder builder)
            {
                super(builder);
            }

            @Override
            protected void addItem(final RecipeItem item)
            {
                this.builder.addIngredient(item);
            }

            @Override
            public ShapelessRecipeItemBuilder addIngredient()
            {
                return this.build().addIngredient();
            }
        }

        @Override
        public String toString()
        {
            return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("oldBuilder", this.oldBuilder).append("ingredients", this.ingredients).toString();
        }
    }

    private static class ShapedRecipeBuilderImpl implements ShapedRecipeBuilder
    {
        private final RecipeBuilderImpl oldBuilder;
        protected     String[]          pattern;
        protected     RecipePattern     alternatePattern;
        protected final TCharObjectMap<RecipeItem> items = new TCharObjectHashMap<>();

        ShapedRecipeBuilderImpl(final RecipeBuilderImpl recipeBuilder)
        {
            this.oldBuilder = recipeBuilder;
        }

        @Override
        public ShapedRecipeBuilder result(final ItemStack itemStack)
        {
            this.oldBuilder.result(itemStack);
            return this;
        }

        @Override
        public ShapedRecipeBuilder result(final BiFunction<Player, ItemStack[][], ItemStack> itemStack)
        {
            this.oldBuilder.result(itemStack);
            return this;
        }

        @Override
        public ShapedRecipeBuilder vanilla(final boolean vanilla)
        {
            this.oldBuilder.vanilla(vanilla);
            return this;
        }

        @Override
        public ShapedRecipeBuilder priority(final long priority)
        {
            this.oldBuilder.priority(priority);
            return this;
        }

        @Override
        public ShapedRecipe build()
        {
            if ((this.pattern == null) && (this.alternatePattern == null))
            {
                throw new IllegalArgumentException("Pattern can't be null.");
            }
            if ((this.pattern != null) && (this.alternatePattern != null))
            {
                throw new IllegalArgumentException("Can't use both patterns at once.");
            }
            Validate.notNull(this.oldBuilder.result, "result item can't be null.");
            if (this.oldBuilder.priority == null)
            {
                this.priority(Recipe.DEFAULT_SHAPED_RECIPE_PRIORITY);
            }
            final RecipePattern pat = (this.alternatePattern == null) ? new CharMapRecipePatternImpl(this.items, this.pattern) : this.alternatePattern;
            if (this.oldBuilder.func == null)
            {
                return new ShapedRecipeImpl(pat, this.oldBuilder.result, this.oldBuilder.priority, this.oldBuilder.vanilla);
            }
            else
            {
                return new ShapedRecipeImpl(pat, this.oldBuilder.result, this.oldBuilder.priority, this.oldBuilder.vanilla, this.oldBuilder.func);
            }
        }

        @Override
        public ShapedRecipeBuilder pattern(final String... pattern)
        {
            if (this.alternatePattern != null)
            {
                throw new IllegalArgumentException("Can't set string array pattern if other pattern already exist.");
            }
            this.pattern = pattern;
            return this;
        }

        @Override
        public ShapedRecipeBuilder pattern(final RecipePattern pattern)
        {
            if (this.pattern != null)
            {
                throw new IllegalArgumentException("Can't set pattern if other string array pattern already exist.");
            }
            this.alternatePattern = pattern;
            return this;
        }

        @Override
        public ShapedRecipeItemBuilder addIngredient(final char c)
        {
            return new ShapedRecipeItemBuilder(c, this);
        }

        @Override
        public ShapedRecipeBuilder addIngredient(final char c, final RecipeItem item)
        {
            if (this.alternatePattern != null)
            {
                throw new IllegalArgumentException("Can't add ingredient when using pattern object.");
            }
            this.items.put(c, item);
            return this;
        }

        private static class ShapedRecipeItemBuilder extends BaseRecipeItemBuilderImpl<ShapedRecipeBuilder, RecipeItemBuilder.ShapedRecipeItemBuilder> implements RecipeItemBuilder.ShapedRecipeItemBuilder
        {
            protected final char c;

            protected ShapedRecipeItemBuilder(final char c, final ShapedRecipeBuilder builder)
            {
                super(builder);
                this.c = c;
            }

            @Override
            protected void addItem(final RecipeItem item)
            {
                this.builder.addIngredient(this.c, item);
            }

            @Override
            public String toString()
            {
                return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("c", this.c).toString();
            }

            @Override
            public RecipeItemBuilder.ShapedRecipeItemBuilder addIngredient(final char c)
            {
                return this.build().addIngredient(c);
            }
        }

        @Override
        public String toString()
        {
            return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("oldBuilder", this.oldBuilder).toString();
        }
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("result", this.result).append("func", this.func).append("vanilla", this.vanilla).append("priority", this.priority).toString();
    }
}
