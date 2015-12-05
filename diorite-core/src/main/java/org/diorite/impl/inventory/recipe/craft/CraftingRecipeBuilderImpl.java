package org.diorite.impl.inventory.recipe.craft;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.Diorite;
import org.diorite.entity.Player;
import org.diorite.inventory.GridInventory;
import org.diorite.inventory.item.ItemStack;
import org.diorite.inventory.recipe.craft.BasicRepeatableCraftingRecipeItem;
import org.diorite.inventory.recipe.craft.CraftingGrid;
import org.diorite.inventory.recipe.craft.CraftingRecipe;
import org.diorite.inventory.recipe.craft.CraftingRecipeBuilder;
import org.diorite.inventory.recipe.craft.CraftingRecipeGroup;
import org.diorite.inventory.recipe.craft.CraftingRecipeItem;
import org.diorite.inventory.recipe.craft.CraftingRecipeItemBuilder;
import org.diorite.inventory.recipe.craft.CraftingRecipePattern;
import org.diorite.inventory.recipe.craft.CraftingRepeatableRecipeItem;
import org.diorite.inventory.recipe.craft.SemiShapedCraftingRecipe;
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
    private final CraftingRecipeGroup parent;

    public CraftingRecipeBuilderImpl()
    {
        this.parent = Diorite.getServerManager().getRecipeManager();
    }

    public CraftingRecipeBuilderImpl(final CraftingRecipeGroup parent)
    {
        this.parent = parent;
    }

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
    public CraftingRecipeGroup getParent()
    {
        return this.parent;
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
    public GroupCraftingRecipeBuilder grouped()
    {
        return new GroupCraftingRecipeBuilderImpl(this);
    }

    @Override
    public SemiShapedCraftingRecipeBuilder semiShaped()
    {
        return new SemiShapedCraftingRecipeBuilderImpl(this);
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

    private static class GroupCraftingRecipeBuilderImpl implements GroupCraftingRecipeBuilder
    {
        private final CraftingRecipeBuilderImpl oldBuilder;
        private       Predicate<GridInventory>  validator;
        private final Collection<CraftingRecipe>        recipes         = new ArrayList<>(10);
        private final Collection<CraftingRecipeBuilder> recipesBuilders = new ArrayList<>(10);
        private       int                               lastPriority    = 0;

        GroupCraftingRecipeBuilderImpl(final CraftingRecipeBuilderImpl recipeBuilder)

        {
            this.oldBuilder = recipeBuilder;
        }

        @Override
        public GroupCraftingRecipeBuilder result(final ItemStack itemStack)
        {
            this.oldBuilder.result(itemStack);
            return this;
        }

        @Override
        public GroupCraftingRecipeBuilder result(final BiFunction<Player, CraftingGrid, ItemStack> itemStack)
        {
            this.oldBuilder.result(itemStack);
            return this;
        }

        @Override
        public GroupCraftingRecipeBuilder vanilla(final boolean vanilla)
        {
            this.oldBuilder.vanilla(vanilla);
            return this;
        }

        @Override
        public GroupCraftingRecipeBuilder priority(final long priority)
        {
            this.oldBuilder.priority(priority);
            return this;
        }

        @Override
        public CraftingRecipeGroup getParent()
        {
            return this.oldBuilder.parent;
        }

        @Override
        public CraftingRecipeGroup build()
        {
            Validate.notNull(this.oldBuilder.result, "result item can't be null.");
            Validate.notNull(this.validator, "validator can't be null.");
            if (this.oldBuilder.priority == null)
            {
                this.priority(CraftingRecipe.DEFAULT_RECIPE_PRIORITY);
            }
            return new CraftingRecipeGroupImpl(this.validator, this.recipes, this.oldBuilder.result, this.oldBuilder.priority, this.oldBuilder.vanilla);
        }

        @Override
        public GroupCraftingRecipeBuilder validator(final Predicate<GridInventory> predicate)
        {
            this.validator = predicate;
            return this;
        }

        @Override
        public GroupCraftingRecipeBuilder addRecipe(final CraftingRecipe recipe)
        {
            this.recipes.add(recipe);
            return this;
        }

        @Override
        public GroupCraftingRecipeBuilder addRecipe(final UnaryOperator<CraftingRecipeBuilder> recipe)
        {
            return this.addRecipe(recipe.apply(new CraftingRecipeBuilderImpl().vanilla(this.oldBuilder.vanilla).priority(this.lastPriority++)).build());
        }

        @Override
        public GroupCraftingRecipeBuilder addShapedRecipe(final UnaryOperator<ShapedCraftingRecipeBuilder> recipe)
        {
            return this.addRecipe(recipe.apply(new CraftingRecipeBuilderImpl().shaped().vanilla(this.oldBuilder.vanilla).priority(this.lastPriority++)).build());
        }

        @Override
        public GroupCraftingRecipeBuilder addShapelessRecipe(final UnaryOperator<ShapelessCraftingRecipeBuilder> recipe)
        {
            return this.addRecipe(recipe.apply(new CraftingRecipeBuilderImpl().shapeless().vanilla(this.oldBuilder.vanilla).priority(this.lastPriority++)).build());
        }

        @Override
        public GroupCraftingRecipeBuilder addGroupedRecipe(final UnaryOperator<GroupCraftingRecipeBuilder> recipe)
        {
            return this.addRecipe(recipe.apply(new CraftingRecipeBuilderImpl().grouped().vanilla(this.oldBuilder.vanilla).priority(this.lastPriority++)).build());
        }

        @Override
        public GroupCraftingRecipeBuilder addSemiShapedRecipe(final UnaryOperator<SemiShapedCraftingRecipeBuilder> recipe)
        {
            return this.addRecipe(recipe.apply(new CraftingRecipeBuilderImpl().semiShaped().vanilla(this.oldBuilder.vanilla).priority(this.lastPriority++)).build());
        }

        @Override
        public GroupCraftingRecipeBuilder addShapedRecipe_(final Function<ShapedCraftingRecipeBuilder, CraftingRecipeItemBuilder.ShapedCraftingRecipeItemBuilder> recipe)
        {
            return this.addRecipe(recipe.apply(new CraftingRecipeBuilderImpl().shaped().vanilla(this.oldBuilder.vanilla).priority(this.lastPriority++)).build().build());
        }

        @Override
        public GroupCraftingRecipeBuilder addShapelessRecipe_(final Function<ShapelessCraftingRecipeBuilder, CraftingRecipeItemBuilder.ShapelessCraftingRecipeItemBuilder> recipe)
        {
            return this.addRecipe(recipe.apply(new CraftingRecipeBuilderImpl().shapeless().vanilla(this.oldBuilder.vanilla).priority(this.lastPriority++)).build().build());
        }

        @Override
        public String toString()
        {
            return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("oldBuilder", this.oldBuilder).append("validator", this.validator).append("recipes", this.recipes).toString();
        }
    }

    private static class SemiShapedCraftingRecipeBuilderImpl implements SemiShapedCraftingRecipeBuilder
    {
        private final CraftingRecipeBuilderImpl oldBuilder;

        private String[]              pattern;
        private CraftingRecipePattern alternatePattern;
        private final Char2ObjectMap<CraftingRecipeItem> items = new Char2ObjectOpenHashMap<>();

        private final List<CraftingRecipeItem>           ingredients           = new ArrayList<>(10);
        private final List<CraftingRepeatableRecipeItem> repeatableIngredients = new ArrayList<>(10);

        SemiShapedCraftingRecipeBuilderImpl(final CraftingRecipeBuilderImpl recipeBuilder)
        {
            this.oldBuilder = recipeBuilder;
        }

        @Override
        public SemiShapedCraftingRecipeBuilderImpl result(final ItemStack itemStack)
        {
            this.oldBuilder.result(itemStack);
            return this;
        }

        @Override
        public SemiShapedCraftingRecipeBuilderImpl result(final BiFunction<Player, CraftingGrid, ItemStack> itemStack)
        {
            this.oldBuilder.result(itemStack);
            return this;
        }

        @Override
        public SemiShapedCraftingRecipeBuilderImpl vanilla(final boolean vanilla)
        {
            this.oldBuilder.vanilla(vanilla);
            return this;
        }

        @Override
        public SemiShapedCraftingRecipeBuilderImpl priority(final long priority)
        {
            this.oldBuilder.priority(priority);
            return this;
        }

        @Override
        public CraftingRecipeGroup getParent()
        {
            return this.oldBuilder.parent;
        }

        @Override
        public SemiShapedCraftingRecipe build()
        {
            if (this.repeatableIngredients.isEmpty())
            {
                Validate.notEmpty(this.ingredients, "ingredients list can't be empty.");
            }
            else if (this.ingredients.isEmpty())
            {
                Validate.notEmpty(this.repeatableIngredients, "ingredients list can't be empty.");
            }
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
                this.priority(CraftingRecipe.DEFAULT_RECIPE_PRIORITY);
            }
            return new SemiShapedCraftingRecipeImpl((this.alternatePattern == null) ? new CharMapCraftingRecipePatternImpl(this.items, this.pattern) : this.alternatePattern, this.ingredients, this.repeatableIngredients, this.oldBuilder.result, this.oldBuilder.priority, this.oldBuilder.vanilla, this.oldBuilder.func);
        }

        @Override
        public SemiShapedCraftingRecipeBuilderImpl pattern(final String... pattern)
        {
            if (this.alternatePattern != null)
            {
                throw new IllegalArgumentException("Can't set string array pattern if other pattern already exist.");
            }
            this.pattern = pattern;
            return this;
        }

        @Override
        public SemiShapedCraftingRecipeBuilderImpl pattern(final CraftingRecipePattern pattern)
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
        public SemiShapedCraftingRecipeBuilderImpl addIngredient(final char c, final CraftingRecipeItem item)
        {
            if (this.alternatePattern != null)
            {
                throw new IllegalArgumentException("Can't add ingredient when using pattern object.");
            }
            this.items.put(c, item);
            return this;
        }

        @Override
        public ShapelessCraftingRecipeItemBuilder addIngredient()
        {
            return new ShapelessCraftingRecipeItemBuilder(this);
        }

        @Override
        public SemiShapedCraftingRecipeBuilderImpl addIngredient(final CraftingRecipeItem item)
        {
            if (item instanceof CraftingRepeatableRecipeItem)
            {
                this.repeatableIngredients.add((CraftingRepeatableRecipeItem) item);
            }
            else
            {
                this.ingredients.add(item);
            }
            return this;
        }

        @Override
        public String toString()
        {
            return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("oldBuilder", this.oldBuilder).append("pattern", this.pattern).append("alternatePattern", this.alternatePattern).append("items", this.items).append("ingredients", this.ingredients).append("repeatableIngredients", this.repeatableIngredients).toString();
        }
    }

    private static class ShapelessCraftingRecipeBuilderImpl implements ShapelessCraftingRecipeBuilder
    {
        private final CraftingRecipeBuilderImpl oldBuilder;
        private final List<CraftingRecipeItem>           ingredients           = new ArrayList<>(10);
        private final List<CraftingRepeatableRecipeItem> repeatableIngredients = new ArrayList<>(10);

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
        public CraftingRecipeGroup getParent()
        {
            return this.oldBuilder.parent;
        }

        @Override
        public ShapelessCraftingRecipe build()
        {
            if (this.repeatableIngredients.isEmpty())
            {
                Validate.notEmpty(this.ingredients, "ingredients list can't be empty.");
            }
            else if (this.ingredients.isEmpty())
            {
                Validate.notEmpty(this.repeatableIngredients, "ingredients list can't be empty.");
            }
            Validate.notNull(this.oldBuilder.result, "result item can't be null.");
            if (this.oldBuilder.priority == null)
            {
                this.priority(CraftingRecipe.DEFAULT_SHAPELESS_RECIPE_PRIORITY);
            }
            if ((this.ingredients.size() == 1) && this.repeatableIngredients.isEmpty())
            {
                return new ShapelessSingleCraftingRecipeImpl(this.ingredients.iterator().next(), this.oldBuilder.result, this.oldBuilder.priority, this.oldBuilder.vanilla, this.oldBuilder.func);
            }
            if ((this.repeatableIngredients.size() == 1) && this.ingredients.isEmpty())
            {
                return new ShapelessSingleCraftingRecipeImpl(this.repeatableIngredients.iterator().next(), this.oldBuilder.result, this.oldBuilder.priority, this.oldBuilder.vanilla, this.oldBuilder.func);
            }
            return new ShapelessCraftingRecipeImpl(this.ingredients, this.repeatableIngredients, this.oldBuilder.result, this.oldBuilder.priority, this.oldBuilder.vanilla, this.oldBuilder.func);
        }

        @Override
        public ShapelessCraftingRecipeItemBuilder addIngredient()
        {
            return new ShapelessCraftingRecipeItemBuilder(this);
        }

        @Override
        public ShapelessCraftingRecipeBuilder addIngredient(final CraftingRecipeItem item)
        {
            if (item instanceof CraftingRepeatableRecipeItem)
            {
                this.repeatableIngredients.add((CraftingRepeatableRecipeItem) item);
            }
            else
            {
                this.ingredients.add(item);
            }
            return this;
        }

        @Override
        public String toString()
        {
            return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("oldBuilder", this.oldBuilder).append("ingredients", this.ingredients).toString();
        }
    }

    private static class ShapelessCraftingRecipeItemBuilder extends BaseCraftingRecipeItemBuilderImpl<ShapelessCraftingRecipeBuilder, CraftingRecipeItemBuilder.ShapelessCraftingRecipeItemBuilder> implements CraftingRecipeItemBuilder.ShapelessCraftingRecipeItemBuilder
    {
        private boolean                                           isRepeatable;
        private BiFunction<ItemStack, List<ItemStack>, ItemStack> repeatableFunc;

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
        protected CraftingRecipeItem createItem()
        {
            if (this.isRepeatable)
            {
                return new BasicRepeatableCraftingRecipeItem(super.createItem(), this.repeatableFunc);
            }
            return super.createItem();
        }

        @Override
        public ShapelessCraftingRecipeItemBuilder addIngredient()
        {
            return this.build().addIngredient();
        }

        @Override
        public ShapelessCraftingRecipeItemBuilder repeatable()
        {
            this.isRepeatable = true;
            return this;
        }

        @Override
        public ShapelessCraftingRecipeItemBuilder repeatable(final BiFunction<ItemStack, List<ItemStack>, ItemStack> transformFunc)
        {
            this.isRepeatable = true;
            this.repeatableFunc = transformFunc;
            return this;
        }

        @Override
        public String toString()
        {
            return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("isRepeatable", this.isRepeatable).append("repeatableFunc", this.repeatableFunc).toString();
        }
    }

    private static class ShapedCraftingRecipeBuilderImpl implements ShapedCraftingRecipeBuilder
    {
        private final CraftingRecipeBuilderImpl oldBuilder;
        private       String[]                  pattern;
        private       CraftingRecipePattern     alternatePattern;
        private final Char2ObjectMap<CraftingRecipeItem> items = new Char2ObjectOpenHashMap<>();

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
        public CraftingRecipeGroup getParent()
        {
            return this.oldBuilder.parent;
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
            return new ShapedCraftingRecipeImpl((this.alternatePattern == null) ? new CharMapCraftingRecipePatternImpl(this.items, this.pattern) : this.alternatePattern, this.oldBuilder.result, this.oldBuilder.priority, this.oldBuilder.vanilla, this.oldBuilder.func);
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

        @Override
        public String toString()
        {
            return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("oldBuilder", this.oldBuilder).toString();
        }
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
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("result", this.result).append("func", this.func).append("vanilla", this.vanilla).append("priority", this.priority).toString();
    }
}
