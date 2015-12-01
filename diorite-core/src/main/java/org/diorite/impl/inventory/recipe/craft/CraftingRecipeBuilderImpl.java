package org.diorite.impl.inventory.recipe.craft;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.entity.Player;
import org.diorite.inventory.item.ItemStack;
import org.diorite.inventory.recipe.craft.CraftingRecipeBuilder;
import org.diorite.inventory.recipe.craft.CraftingRecipeItem;
import org.diorite.inventory.recipe.craft.CraftingRecipeItemBuilder;
import org.diorite.inventory.recipe.craft.CraftingGrid;
import org.diorite.inventory.recipe.craft.CraftingRecipe;
import org.diorite.inventory.recipe.craft.CraftingRecipePattern;
import org.diorite.inventory.recipe.craft.ShapedCraftingRecipe;
import org.diorite.inventory.recipe.craft.ShapelessCraftingRecipe;

import it.unimi.dsi.fastutil.chars.Char2ObjectMap;
import it.unimi.dsi.fastutil.chars.Char2ObjectOpenHashMap;

public class CraftingRecipeBuilderImpl implements CraftingRecipeBuilder
{
    private ItemStack result;
    private BiFunction<Player, CraftingGrid, ItemStack> func     = null;
    private boolean                                     vanilla  = false;
    private Long                                        priority = null;

    @Override
    public CraftingRecipeBuilder result(final ItemStack itemStack)
    {
        this.result = itemStack;
        return this;
    }

    @Override
    public CraftingRecipeBuilder result(final BiFunction<Player, CraftingGrid, ItemStack> itemStack)
    {
        this.func = itemStack;
        return this;
    }

    @Override
    public CraftingRecipeBuilder vanilla(final boolean vanilla)
    {
        this.vanilla = vanilla;
        return this;
    }

    @Override
    public CraftingRecipeBuilder priority(final long priority)
    {
        this.priority = priority;
        return this;
    }

    @Override
    public ShapelessCraftingRecipeBuilder shapeless()
    {
        return new ShapelessCraftingRecipeBuilderImpl(this);
    }

    @Override
    public ShapedCraftingRecipeBuilder shaped()
    {
        return new ShapedCraftingRecipeBuilderImpl(this);
    }

    @Override
    public CraftingRecipe build() throws RuntimeException
    {
        throw new RuntimeException("Unknown type of recipe.");
    }

    @Override
    public CraftingRecipe buildAndAdd() throws RuntimeException
    {
        throw new RuntimeException("Unknown type of recipe.");
    }

    private static class ShapelessCraftingRecipeBuilderImpl implements ShapelessCraftingRecipeBuilder
    {
        private final CraftingRecipeBuilderImpl oldBuilder;
        private final List<CraftingRecipeItem> ingredients = new ArrayList<>(10);

        ShapelessCraftingRecipeBuilderImpl(final CraftingRecipeBuilderImpl recipeBuilder)
        {
            this.oldBuilder = recipeBuilder;
        }

        @Override
        public ShapelessCraftingRecipeBuilder result(final ItemStack itemStack)
        {
            this.oldBuilder.result(itemStack);
            return this;
        }

        @Override
        public ShapelessCraftingRecipeBuilder result(final BiFunction<Player, CraftingGrid, ItemStack> itemStack)
        {
            this.oldBuilder.result(itemStack);
            return this;
        }

        @Override
        public ShapelessCraftingRecipeBuilder vanilla(final boolean vanilla)
        {
            this.oldBuilder.vanilla(vanilla);
            return this;
        }

        @Override
        public ShapelessCraftingRecipeBuilder priority(final long priority)
        {
            this.oldBuilder.priority(priority);
            return this;
        }

        @Override
        public ShapelessCraftingRecipe build()
        {
            Validate.notEmpty(this.ingredients, "ingredients list can't be empty.");
            Validate.notNull(this.oldBuilder.result, "result item can't be null.");
            if (this.oldBuilder.priority == null)
            {
                this.priority(CraftingRecipe.DEFAULT_SHAPELESS_RECIPE_PRIORITY);
            }
            if (this.ingredients.size() == 1)
            {
                if (this.oldBuilder.func == null)
                {
                    return new ShapelessSingleCraftingRecipeImpl(this.ingredients.iterator().next(), this.oldBuilder.result, this.oldBuilder.priority, this.oldBuilder.vanilla, null);
                }
                else
                {
                    return new ShapelessSingleCraftingRecipeImpl(this.ingredients.iterator().next(), this.oldBuilder.result, this.oldBuilder.priority, this.oldBuilder.vanilla, this.oldBuilder.func);
                }
            }
            if (this.oldBuilder.func == null)
            {
                return new ShapelessCraftingRecipeImpl(this.ingredients, this.oldBuilder.result, this.oldBuilder.priority, this.oldBuilder.vanilla, null);
            }
            else
            {
                return new ShapelessCraftingRecipeImpl(this.ingredients, this.oldBuilder.result, this.oldBuilder.priority, this.oldBuilder.vanilla, this.oldBuilder.func);
            }
        }

        @Override
        public ShapelessCraftingRecipeItemBuilder addIngredient()
        {
            return new ShapelessCraftingRecipeItemBuilder(this);
        }

        @Override
        public ShapelessCraftingRecipeBuilder addIngredient(final CraftingRecipeItem item)
        {
            this.ingredients.add(item);
            return this;
        }

        private static class ShapelessCraftingRecipeItemBuilder extends BaseCraftingRecipeItemBuilderImpl<ShapelessCraftingRecipeBuilder, CraftingRecipeItemBuilder.ShapelessCraftingRecipeItemBuilder> implements CraftingRecipeItemBuilder.ShapelessCraftingRecipeItemBuilder
        {
            protected ShapelessCraftingRecipeItemBuilder(final ShapelessCraftingRecipeBuilder builder)
            {
                super(builder);
            }

            @Override
            protected void addItem(final CraftingRecipeItem item)
            {
                this.builder.addIngredient(item);
            }

            @Override
            public ShapelessCraftingRecipeItemBuilder addIngredient()
            {
                return this.build().addIngredient();
            }

            @Override
            public ShapelessCraftingRecipeItemBuilder repeatable()
            {
                return null; // TODO
            }

            @Override
            public ShapelessCraftingRecipeItemBuilder repeatable(final BiFunction<ItemStack, List<ItemStack>, ItemStack> transformFunc)
            {
                return null;
            }
        }

        @Override
        public String toString()
        {
            return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("oldBuilder", this.oldBuilder).append("ingredients", this.ingredients).toString();
        }
    }

    private static class ShapedCraftingRecipeBuilderImpl implements ShapedCraftingRecipeBuilder
    {
        private final CraftingRecipeBuilderImpl oldBuilder;
        protected     String[]                  pattern;
        protected     CraftingRecipePattern     alternatePattern;
        protected final Char2ObjectMap<CraftingRecipeItem> items = new Char2ObjectOpenHashMap<>();

        ShapedCraftingRecipeBuilderImpl(final CraftingRecipeBuilderImpl recipeBuilder)
        {
            this.oldBuilder = recipeBuilder;
        }

        @Override
        public ShapedCraftingRecipeBuilder result(final ItemStack itemStack)
        {
            this.oldBuilder.result(itemStack);
            return this;
        }

        @Override
        public ShapedCraftingRecipeBuilder result(final BiFunction<Player, CraftingGrid, ItemStack> itemStack)
        {
            this.oldBuilder.result(itemStack);
            return this;
        }

        @Override
        public ShapedCraftingRecipeBuilder vanilla(final boolean vanilla)
        {
            this.oldBuilder.vanilla(vanilla);
            return this;
        }

        @Override
        public ShapedCraftingRecipeBuilder priority(final long priority)
        {
            this.oldBuilder.priority(priority);
            return this;
        }

        @Override
        public ShapedCraftingRecipe build()
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
                this.priority(CraftingRecipe.DEFAULT_SHAPED_RECIPE_PRIORITY);
            }
            final CraftingRecipePattern pat = (this.alternatePattern == null) ? new CharMapCraftingRecipePatternImpl(this.items, this.pattern) : this.alternatePattern;
            if (this.oldBuilder.func == null)
            {
                return new ShapedCraftingRecipeImpl(pat, this.oldBuilder.result, this.oldBuilder.priority, this.oldBuilder.vanilla, null);
            }
            else
            {
                return new ShapedCraftingRecipeImpl(pat, this.oldBuilder.result, this.oldBuilder.priority, this.oldBuilder.vanilla, this.oldBuilder.func);
            }
        }

        @Override
        public ShapedCraftingRecipeBuilder pattern(final String... pattern)
        {
            if (this.alternatePattern != null)
            {
                throw new IllegalArgumentException("Can't set string array pattern if other pattern already exist.");
            }
            this.pattern = pattern;
            return this;
        }

        @Override
        public ShapedCraftingRecipeBuilder pattern(final CraftingRecipePattern pattern)
        {
            if (this.pattern != null)
            {
                throw new IllegalArgumentException("Can't set pattern if other string array pattern already exist.");
            }
            this.alternatePattern = pattern;
            return this;
        }

        @Override
        public ShapedCraftingRecipeItemBuilder addIngredient(final char c)
        {
            return new ShapedCraftingRecipeItemBuilder(c, this);
        }

        @Override
        public ShapedCraftingRecipeBuilder addIngredient(final char c, final CraftingRecipeItem item)
        {
            if (this.alternatePattern != null)
            {
                throw new IllegalArgumentException("Can't add ingredient when using pattern object.");
            }
            this.items.put(c, item);
            return this;
        }

        private static class ShapedCraftingRecipeItemBuilder extends BaseCraftingRecipeItemBuilderImpl<ShapedCraftingRecipeBuilder, CraftingRecipeItemBuilder.ShapedCraftingRecipeItemBuilder> implements CraftingRecipeItemBuilder.ShapedCraftingRecipeItemBuilder
        {
            protected final char c;

            protected ShapedCraftingRecipeItemBuilder(final char c, final ShapedCraftingRecipeBuilder builder)
            {
                super(builder);
                this.c = c;
            }

            @Override
            protected void addItem(final CraftingRecipeItem item)
            {
                this.builder.addIngredient(this.c, item);
            }

            @Override
            public String toString()
            {
                return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("c", this.c).toString();
            }

            @Override
            public ShapedCraftingRecipeItemBuilder addIngredient(final char c)
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
