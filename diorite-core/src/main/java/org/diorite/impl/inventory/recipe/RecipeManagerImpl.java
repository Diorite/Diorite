package org.diorite.impl.inventory.recipe;

import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Predicate;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.impl.inventory.recipe.craft.CraftingRecipeBuilderImpl;
import org.diorite.chat.ChatColor;
import org.diorite.inventory.GridInventory;
import org.diorite.inventory.item.BaseItemStack;
import org.diorite.inventory.item.ItemStack;
import org.diorite.inventory.item.builder.ItemBuilder;
import org.diorite.inventory.item.meta.BookMeta;
import org.diorite.inventory.item.meta.BookMeta.GenerationEnum;
import org.diorite.inventory.item.meta.LeatherArmorMeta;
import org.diorite.inventory.item.meta.MapMeta;
import org.diorite.inventory.recipe.craft.CraftingRecipe;
import org.diorite.inventory.recipe.craft.CraftingRecipeBuilder;
import org.diorite.inventory.recipe.craft.CraftingRecipeBuilder.GroupCraftingRecipeBuilder;
import org.diorite.inventory.recipe.craft.CraftingRecipeBuilder.ShapedCraftingRecipeBuilder;
import org.diorite.inventory.recipe.craft.CraftingRecipeBuilder.ShapelessCraftingRecipeBuilder;
import org.diorite.inventory.recipe.craft.CraftingRecipeCheckResult;
import org.diorite.inventory.recipe.craft.CraftingRecipeGroup;
import org.diorite.inventory.recipe.craft.CraftingRecipeItemBuilder;
import org.diorite.inventory.recipe.craft.CraftingRecipeItemBuilder.ShapelessCraftingRecipeItemBuilder;
import org.diorite.inventory.recipe.craft.ShapelessCraftingRecipe;
import org.diorite.material.ArmorMaterial;
import org.diorite.material.BreakableItemMat;
import org.diorite.material.ColorableMat;
import org.diorite.material.ItemMaterialData;
import org.diorite.material.Material;
import org.diorite.material.blocks.CarpetMat;
import org.diorite.material.blocks.CobblestoneWallMat;
import org.diorite.material.blocks.DirtMat;
import org.diorite.material.blocks.DoubleFlowersMat;
import org.diorite.material.blocks.FlowerMat;
import org.diorite.material.blocks.FlowersMat;
import org.diorite.material.blocks.LogMat;
import org.diorite.material.blocks.PlanksMat;
import org.diorite.material.blocks.PrismarineMat;
import org.diorite.material.blocks.QuartzBlockMat;
import org.diorite.material.blocks.RedSandstoneMat;
import org.diorite.material.blocks.RedstoneTorchOnMat;
import org.diorite.material.blocks.SandMat;
import org.diorite.material.blocks.SandstoneMat;
import org.diorite.material.blocks.StainedGlassMat;
import org.diorite.material.blocks.StainedGlassPaneMat;
import org.diorite.material.blocks.StainedHardenedClayMat;
import org.diorite.material.blocks.StoneBrickMat;
import org.diorite.material.blocks.StoneMat;
import org.diorite.material.blocks.StoneSlabMat;
import org.diorite.material.blocks.TorchMat;
import org.diorite.material.blocks.WoodenSlabMat;
import org.diorite.material.blocks.WoolMat;
import org.diorite.material.items.ArmorMat;
import org.diorite.material.items.BannerMat;
import org.diorite.material.items.DyeMat;
import org.diorite.material.items.GoldenAppleMat;
import org.diorite.utils.Color;

public class RecipeManagerImpl implements IRecipeManager
{
    private final Set<CraftingRecipe> recipes = new TreeSet<>(RecipeComparator.INSTANCE);

    @Override
    public CraftingRecipeBuilder craftingBuilder()
    {
        return new CraftingRecipeBuilderImpl();
    }

    @Override
    public boolean add(final CraftingRecipe recipe)
    {
        return this.recipes.add(recipe);
    }

    @Override
    public boolean remove(final CraftingRecipe recipe)
    {
        return this.recipes.remove(recipe);
    }

    @Override
    public Iterator<? extends CraftingRecipe> recipeIterator()
    {
        return this.recipes.iterator();
    }

    @Override
    public void resetCraftingRecipes()
    {
        this.clearCraftingRecipes();
        this.addDefaultCraftingRecipes();
    }

    @Override
    public void clearCraftingRecipes()
    {
        this.recipes.clear();
    }

    @Override
    public Iterator<CraftingRecipe> craftingRecipeIterator()
    {
        final Iterator<CraftingRecipe> it = this.recipes.iterator();
        return new CraftingRecipeIterator(it);
    }

    @Override
    public CraftingRecipeCheckResult matchCraftingRecipe(final GridInventory grid)
    {
        long s = System.nanoTime();
        try
        {
            for (final CraftingRecipe recipe : this.recipes)
            {
                final CraftingRecipeCheckResult result = recipe.isMatching(grid);
                if (result != null)
                {
                    return result;
                }
            }
            return null;
        } finally
        {
            long e = System.nanoTime();
            System.out.println((e - s));
        }
    }

    private static volatile int i = 0;

    @Override
    public void reset()
    {
        this.clear();
        this.addDefaultRecipes();
    }

    @Override
    public void clear()
    {
        this.clearCraftingRecipes();
    }

    @Override
    public CraftingRecipeCheckResult isMatching(final GridInventory inventory)
    {
        throw new IllegalStateException("isMatching method used on root recipe manager.");
    }

    @Override
    public List<ItemStack> getResult()
    {
        throw new IllegalStateException("getResult method used on root recipe manager.");
    }

    @Override
    public long getPriority()
    {
        throw new IllegalStateException("getPriority method used on root recipe manager.");
    }

    @Override
    public boolean isVanilla()
    {
        throw new IllegalStateException("isVanilla method used on root recipe manager.");
    }

    private static long getNextPriority()
    {
        return CraftingRecipe.DIORITE_START + ((2 * (i++)) * CraftingRecipe.DIORITE_SPACE);
    }

    @Override
    public void addDefaultCraftingRecipes()
    {
//        this.builder().priority(getPriority()).shapeless().addIngredient().item(Material.STONE, true).build().result(Material.STONE_BUTTON).buildAndAdd();
//        this.builder().priority(getPriority()).shaped().pattern("s ", "S ").addIngredient('s').item(Material.STONE).addIngredient('S').item(Material.STICK).build().result(Material.STONE_SWORD).buildAndAdd();
//        this.builder().priority(getPriority()).shaped().pattern("s").addIngredient('s').item(Material.STICK).build().result(WoolMat.WOOL_BLUE).buildAndAdd();
//        this.builder().priority(getPriority()).shapeless().addIngredient().item(Material.STICK).addIngredient().item(Material.STONE).build().result(Material.STONE_AXE).buildAndAdd();
//        this.builder().priority(getPriority()).shapeless().addIngredient().item(DyeMat.DYE_LAPIS_LAZULI).addIngredient().item(WoolMat.WOOL_WHITE, false).build().result(WoolMat.WOOL_BLUE).buildAndAdd();
        this.craftingBuilder().priority(getNextPriority()).shapeless().addIngredient().item(Material.APPLE, 2, false).replacement(Material.DIRT, 16).simpleValidator(item -> ChatColor.translateAlternateColorCodesInString("&3Diorite").equals(item.getItemMeta().getDisplayName())).build().result(Material.GOLDEN_APPLE).buildAndAdd();

        // any whole crafting recipe [xxx,xxx,xxx]
        // @formatter:off
        this.grouped(this.craftingBuilder().shaped().pattern("xxx", "xxx", "xxx").addIngredient('x').any())
            .addGroupedRecipe(g -> g.validator(this.shaped_(Material.STAINED_GLASS, 8, "ggg", "gdg", "ggg").addIngredient('g').item(Material.GLASS, false).addIngredient('d').item(Material.DYE, true))
                .addShapedRecipe_(b -> b.result(StainedGlassMat.STAINED_GLASS_BLACK     , 8).pattern("ggg", "gdg", "ggg").addIngredient('g').item(Material.GLASS, false).addIngredient('d').item(DyeMat.DYE_INK_SAC     , false))
                .addShapedRecipe_(b -> b.result(StainedGlassMat.STAINED_GLASS_RED       , 8).pattern("ggg", "gdg", "ggg").addIngredient('g').item(Material.GLASS, false).addIngredient('d').item(DyeMat.DYE_RED         , false))
                .addShapedRecipe_(b -> b.result(StainedGlassMat.STAINED_GLASS_GREEN     , 8).pattern("ggg", "gdg", "ggg").addIngredient('g').item(Material.GLASS, false).addIngredient('d').item(DyeMat.DYE_GREEN       , false))
                .addShapedRecipe_(b -> b.result(StainedGlassMat.STAINED_GLASS_BROWN     , 8).pattern("ggg", "gdg", "ggg").addIngredient('g').item(Material.GLASS, false).addIngredient('d').item(DyeMat.DYE_COCOA_BEANS , false))
                .addShapedRecipe_(b -> b.result(StainedGlassMat.STAINED_GLASS_BLUE      , 8).pattern("ggg", "gdg", "ggg").addIngredient('g').item(Material.GLASS, false).addIngredient('d').item(DyeMat.DYE_LAPIS_LAZULI, false))
                .addShapedRecipe_(b -> b.result(StainedGlassMat.STAINED_GLASS_PURPLE    , 8).pattern("ggg", "gdg", "ggg").addIngredient('g').item(Material.GLASS, false).addIngredient('d').item(DyeMat.DYE_PURPLE      , false))
                .addShapedRecipe_(b -> b.result(StainedGlassMat.STAINED_GLASS_CYAN      , 8).pattern("ggg", "gdg", "ggg").addIngredient('g').item(Material.GLASS, false).addIngredient('d').item(DyeMat.DYE_CYAN        , false))
                .addShapedRecipe_(b -> b.result(StainedGlassMat.STAINED_GLASS_LIGHT_GRAY, 8).pattern("ggg", "gdg", "ggg").addIngredient('g').item(Material.GLASS, false).addIngredient('d').item(DyeMat.DYE_LIGHT_GRAY  , false))
                .addShapedRecipe_(b -> b.result(StainedGlassMat.STAINED_GLASS_GRAY      , 8).pattern("ggg", "gdg", "ggg").addIngredient('g').item(Material.GLASS, false).addIngredient('d').item(DyeMat.DYE_GRAY        , false))
                .addShapedRecipe_(b -> b.result(StainedGlassMat.STAINED_GLASS_PINK      , 8).pattern("ggg", "gdg", "ggg").addIngredient('g').item(Material.GLASS, false).addIngredient('d').item(DyeMat.DYE_PINK        , false))
                .addShapedRecipe_(b -> b.result(StainedGlassMat.STAINED_GLASS_LIME      , 8).pattern("ggg", "gdg", "ggg").addIngredient('g').item(Material.GLASS, false).addIngredient('d').item(DyeMat.DYE_LIME        , false))
                .addShapedRecipe_(b -> b.result(StainedGlassMat.STAINED_GLASS_YELLOW    , 8).pattern("ggg", "gdg", "ggg").addIngredient('g').item(Material.GLASS, false).addIngredient('d').item(DyeMat.DYE_YELLOW      , false))
                .addShapedRecipe_(b -> b.result(StainedGlassMat.STAINED_GLASS_LIGHT_BLUE, 8).pattern("ggg", "gdg", "ggg").addIngredient('g').item(Material.GLASS, false).addIngredient('d').item(DyeMat.DYE_LIGHT_BLUE  , false))
                .addShapedRecipe_(b -> b.result(StainedGlassMat.STAINED_GLASS_MAGENTA   , 8).pattern("ggg", "gdg", "ggg").addIngredient('g').item(Material.GLASS, false).addIngredient('d').item(DyeMat.DYE_MAGENTA     , false))
                .addShapedRecipe_(b -> b.result(StainedGlassMat.STAINED_GLASS_ORANGE    , 8).pattern("ggg", "gdg", "ggg").addIngredient('g').item(Material.GLASS, false).addIngredient('d').item(DyeMat.DYE_ORANGE      , false))
                .addShapedRecipe_(b -> b.result(StainedGlassMat.STAINED_GLASS_WHITE     , 8).pattern("ggg", "gdg", "ggg").addIngredient('g').item(Material.GLASS, false).addIngredient('d').item(DyeMat.DYE_BONE_MEAL   , false)))
            .addGroupedRecipe(g -> g.validator(this.shaped_(Material.STAINED_HARDENED_CLAY, 8, "hhh", "hdh", "hhh").addIngredient('h').item(Material.HARDENED_CLAY, false).addIngredient('d').item(Material.DYE, true))
                .addShapedRecipe_(b -> b.result(StainedHardenedClayMat.STAINED_HARDENED_CLAY_BLACK     , 8).pattern("hhh", "hdh", "hhh").addIngredient('h').item(Material.HARDENED_CLAY, true).addIngredient('w').item(DyeMat.DYE_INK_SAC     , false))
                .addShapedRecipe_(b -> b.result(StainedHardenedClayMat.STAINED_HARDENED_CLAY_RED       , 8).pattern("hhh", "hdh", "hhh").addIngredient('h').item(Material.HARDENED_CLAY, true).addIngredient('w').item(DyeMat.DYE_RED         , false))
                .addShapedRecipe_(b -> b.result(StainedHardenedClayMat.STAINED_HARDENED_CLAY_GREEN     , 8).pattern("hhh", "hdh", "hhh").addIngredient('h').item(Material.HARDENED_CLAY, true).addIngredient('w').item(DyeMat.DYE_GREEN       , false))
                .addShapedRecipe_(b -> b.result(StainedHardenedClayMat.STAINED_HARDENED_CLAY_BROWN     , 8).pattern("hhh", "hdh", "hhh").addIngredient('h').item(Material.HARDENED_CLAY, true).addIngredient('w').item(DyeMat.DYE_COCOA_BEANS , false))
                .addShapedRecipe_(b -> b.result(StainedHardenedClayMat.STAINED_HARDENED_CLAY_BLUE      , 8).pattern("hhh", "hdh", "hhh").addIngredient('h').item(Material.HARDENED_CLAY, true).addIngredient('w').item(DyeMat.DYE_LAPIS_LAZULI, false))
                .addShapedRecipe_(b -> b.result(StainedHardenedClayMat.STAINED_HARDENED_CLAY_PURPLE    , 8).pattern("hhh", "hdh", "hhh").addIngredient('h').item(Material.HARDENED_CLAY, true).addIngredient('w').item(DyeMat.DYE_PURPLE      , false))
                .addShapedRecipe_(b -> b.result(StainedHardenedClayMat.STAINED_HARDENED_CLAY_CYAN      , 8).pattern("hhh", "hdh", "hhh").addIngredient('h').item(Material.HARDENED_CLAY, true).addIngredient('w').item(DyeMat.DYE_CYAN        , false))
                .addShapedRecipe_(b -> b.result(StainedHardenedClayMat.STAINED_HARDENED_CLAY_LIGHT_GRAY, 8).pattern("hhh", "hdh", "hhh").addIngredient('h').item(Material.HARDENED_CLAY, true).addIngredient('w').item(DyeMat.DYE_LIGHT_GRAY  , false))
                .addShapedRecipe_(b -> b.result(StainedHardenedClayMat.STAINED_HARDENED_CLAY_GRAY      , 8).pattern("hhh", "hdh", "hhh").addIngredient('h').item(Material.HARDENED_CLAY, true).addIngredient('w').item(DyeMat.DYE_GRAY        , false))
                .addShapedRecipe_(b -> b.result(StainedHardenedClayMat.STAINED_HARDENED_CLAY_PINK      , 8).pattern("hhh", "hdh", "hhh").addIngredient('h').item(Material.HARDENED_CLAY, true).addIngredient('w').item(DyeMat.DYE_PINK        , false))
                .addShapedRecipe_(b -> b.result(StainedHardenedClayMat.STAINED_HARDENED_CLAY_LIME      , 8).pattern("hhh", "hdh", "hhh").addIngredient('h').item(Material.HARDENED_CLAY, true).addIngredient('w').item(DyeMat.DYE_LIME        , false))
                .addShapedRecipe_(b -> b.result(StainedHardenedClayMat.STAINED_HARDENED_CLAY_YELLOW    , 8).pattern("hhh", "hdh", "hhh").addIngredient('h').item(Material.HARDENED_CLAY, true).addIngredient('w').item(DyeMat.DYE_YELLOW      , false))
                .addShapedRecipe_(b -> b.result(StainedHardenedClayMat.STAINED_HARDENED_CLAY_LIGHT_BLUE, 8).pattern("hhh", "hdh", "hhh").addIngredient('h').item(Material.HARDENED_CLAY, true).addIngredient('w').item(DyeMat.DYE_LIGHT_BLUE  , false))
                .addShapedRecipe_(b -> b.result(StainedHardenedClayMat.STAINED_HARDENED_CLAY_MAGENTA   , 8).pattern("hhh", "hdh", "hhh").addIngredient('h').item(Material.HARDENED_CLAY, true).addIngredient('w').item(DyeMat.DYE_MAGENTA     , false))
                .addShapedRecipe_(b -> b.result(StainedHardenedClayMat.STAINED_HARDENED_CLAY_ORANGE    , 8).pattern("hhh", "hdh", "hhh").addIngredient('h').item(Material.HARDENED_CLAY, true).addIngredient('w').item(DyeMat.DYE_ORANGE      , false))
                .addShapedRecipe_(b -> b.result(StainedHardenedClayMat.STAINED_HARDENED_CLAY_WHITE     , 8).pattern("hhh", "hdh", "hhh").addIngredient('h').item(Material.HARDENED_CLAY, true).addIngredient('w').item(DyeMat.DYE_BONE_MEAL   , false)))
            .addShapedRecipe_(b -> b.result(Material.COAL_BLOCK            , 1).pattern("ccc", "ccc", "ccc").addIngredient('c').item(Material.COAL            , false))
            .addShapedRecipe_(b -> b.result(Material.IRON_BLOCK            , 1).pattern("iii", "iii", "iii").addIngredient('i').item(Material.IRON_INGOT      , false))
            .addShapedRecipe_(b -> b.result(Material.GOLD_BLOCK            , 1).pattern("ggg", "ggg", "ggg").addIngredient('g').item(Material.GOLD_INGOT      , false))
            .addShapedRecipe_(b -> b.result(Material.DIAMOND_BLOCK         , 1).pattern("ddd", "ddd", "ddd").addIngredient('d').item(Material.DIAMOND         , false))
            .addShapedRecipe_(b -> b.result(Material.EMERALD_BLOCK         , 1).pattern("eee", "eee", "eee").addIngredient('e').item(Material.EMERALD         , false))
            .addShapedRecipe_(b -> b.result(Material.LAPIS_BLOCK           , 1).pattern("iii", "iii", "iii").addIngredient('i').item(DyeMat.DYE_LAPIS_LAZULI  , false))
            .addShapedRecipe_(b -> b.result(Material.REDSTONE_BLOCK        , 1).pattern("rrr", "rrr", "rrr").addIngredient('r').item(Material.REDSTONE        , false))
            .addShapedRecipe_(b -> b.result(Material.SLIME_BLOCK           , 1).pattern("sss", "sss", "sss").addIngredient('s').item(Material.SLIMEBALL       , false))
            .addShapedRecipe_(b -> b.result(Material.MELON_BLOCK           , 1).pattern("mmm", "mmm", "mmm").addIngredient('m').item(Material.MELON           , false))
            .addShapedRecipe_(b -> b.result(Material.HAY_BLOCK             , 1).pattern("www", "www", "www").addIngredient('w').item(Material.WHEAT           , false))
            .addShapedRecipe_(b -> b.result(Material.GOLD_INGOT            , 1).pattern("ggg", "ggg", "ggg").addIngredient('g').item(Material.GOLD_NUGGET     , false))
            .addShapedRecipe_(b -> b.result(PrismarineMat.PRISMARINE_BRICKS, 1).pattern("ppp", "ppp", "ppp").addIngredient('p').item(Material.PRISMARINE_SHARD, false))

            .addShapedRecipe_(b -> b.result(Material.GOLDEN_CARROT               , 1).pattern("ggg", "gcg", "ggg").addIngredient('g').item(Material.GOLD_NUGGET, false).addIngredient('c').item(Material.CARROT          , false))
            .addShapedRecipe_(b -> b.result(Material.SPECKLED_MELON              , 1).pattern("ggg", "gmg", "ggg").addIngredient('m').item(Material.MELON      , false).addIngredient('g').item(Material.GOLD_NUGGET     , false))
            .addShapedRecipe_(b -> b.result(Material.MAP                         , 1).pattern("ppp", "pcp", "ppp").addIngredient('p').item(Material.PAPER      , false).addIngredient('c').item(Material.COMPASS         , false))
            .addShapedRecipe_(b -> b.result(Material.PAINTING                    , 1).pattern("sss", "sws", "sss").addIngredient('s').item(Material.STICK      , false).addIngredient('w').item(Material.WOOL            , true ))
            .addShapedRecipe_(b -> b.result(Material.ITEM_FRAME                  , 1).pattern("sss", "sls", "sss").addIngredient('s').item(Material.STICK      , false).addIngredient('l').item(Material.LEATHER         , false))
            .addShapedRecipe_(b -> b.result(Material.GOLDEN_APPLE                , 1).pattern("ggg", "gag", "ggg").addIngredient('a').item(Material.APPLE      , false).addIngredient('g').item(Material.GOLD_INGOT      , false))
            .addShapedRecipe_(b -> b.result(GoldenAppleMat.GOLDEN_ENCHANTED_APPLE, 1).pattern("ggg", "gag", "ggg").addIngredient('a').item(Material.APPLE      , false).addIngredient('g').item(Material.GOLD_BLOCK      , true ))
            .addShapedRecipe_(b -> b.result(Material.NOTEBLOCK                   , 1).pattern("www", "wrw", "www").addIngredient('r').item(Material.REDSTONE   , false).addIngredient('w').item(Material.PLANKS          , true ))
            .addShapedRecipe_(b -> b.result(PrismarineMat.PRISMARINE_DARK        , 1).pattern("ppp", "pip", "ppp").addIngredient('i').item(Material.DYE        , false).addIngredient('p').item(Material.PRISMARINE_SHARD, false))
            .addShapedRecipe_(b -> b.result(Material.ENDER_CHEST                 , 1).pattern("ooo", "oeo", "ooo").addIngredient('e').item(Material.ENDER_EYE  , false).addIngredient('o').item(Material.OBSIDIAN        , true ))

            .addShapedRecipe_(b -> b.result(Material.PISTON     , 1).pattern("www", "cic", "crc").addIngredient('i').item(Material.IRON_INGOT         , false)                             .addIngredient('r').item(Material.REDSTONE        , false).addIngredient('w').item(Material.PLANKS     , true ).addIngredient('c').item(Material.COBBLESTONE, true ))
            .addShapedRecipe_(b -> b.result(Material.CAKE       , 1).pattern("mmm", "ses", "www").addIngredient('m').item(Material.MILK_BUCKET        , false).replacement(Material.BUCKET).addIngredient('s').item(Material.SUGAR           , false).addIngredient('w').item(Material.WHEAT      , false).addIngredient('e').item(Material.EGG        , false))
            .addShapedRecipe_(b -> b.result(Material.DISPENSER  , 1).pattern("ccc", "cbc", "crc").addIngredient('b').item(Material.BOW                , false)                             .addIngredient('r').item(Material.REDSTONE        , false).addIngredient('c').item(Material.COBBLESTONE, true ))
            .addShapedRecipe_(b -> b.result(Material.BEACON     , 1).pattern("ggg", "gng", "ooo").addIngredient('n').item(Material.NETHER_STAR        , false)                             .addIngredient('g').item(Material.GLASS           , true ).addIngredient('o').item(Material.OBSIDIAN   , true ))
            .addShapedRecipe_(b -> b.result(Material.JUKEBOX    , 1).pattern("www", "wdw", "www").addIngredient('w').item(Material.PLANKS             , true )                             .addIngredient('d').item(Material.DIAMOND         , false))
            .addShapedRecipe_(b -> b.result(Material.SEA_LANTERN, 1).pattern("pPp", "PPP", "pPp").addIngredient('P').item(Material.PRISMARINE_CRYSTALS, false)                             .addIngredient('p').item(Material.PRISMARINE_SHARD, false))

            .buildAndAdd();

        // any 2 lines crafting recipe [xxx,xxx]
        this.grouped(this.craftingBuilder().shaped().pattern("xxx", "xxx").addIngredient('x').any())
            .addGroupedRecipe(g -> g.validator(this.shaped_(Material.STAINED_GLASS_PANE, 16, "sss", "sss").addIngredient('s').item(Material.STAINED_GLASS, true))
                .addShapedRecipe_(b -> b.result(StainedGlassPaneMat.STAINED_GLASS_PANE_WHITE     , 16).pattern("sss", "sss").addIngredient('s').item(StainedGlassMat.STAINED_GLASS_WHITE     , false))
                .addShapedRecipe_(b -> b.result(StainedGlassPaneMat.STAINED_GLASS_PANE_ORANGE    , 16).pattern("sss", "sss").addIngredient('s').item(StainedGlassMat.STAINED_GLASS_ORANGE    , false))
                .addShapedRecipe_(b -> b.result(StainedGlassPaneMat.STAINED_GLASS_PANE_MAGENTA   , 16).pattern("sss", "sss").addIngredient('s').item(StainedGlassMat.STAINED_GLASS_MAGENTA   , false))
                .addShapedRecipe_(b -> b.result(StainedGlassPaneMat.STAINED_GLASS_PANE_LIGHT_BLUE, 16).pattern("sss", "sss").addIngredient('s').item(StainedGlassMat.STAINED_GLASS_LIGHT_BLUE, false))
                .addShapedRecipe_(b -> b.result(StainedGlassPaneMat.STAINED_GLASS_PANE_YELLOW    , 16).pattern("sss", "sss").addIngredient('s').item(StainedGlassMat.STAINED_GLASS_YELLOW    , false))
                .addShapedRecipe_(b -> b.result(StainedGlassPaneMat.STAINED_GLASS_PANE_LIME      , 16).pattern("sss", "sss").addIngredient('s').item(StainedGlassMat.STAINED_GLASS_LIME      , false))
                .addShapedRecipe_(b -> b.result(StainedGlassPaneMat.STAINED_GLASS_PANE_PINK      , 16).pattern("sss", "sss").addIngredient('s').item(StainedGlassMat.STAINED_GLASS_PINK      , false))
                .addShapedRecipe_(b -> b.result(StainedGlassPaneMat.STAINED_GLASS_PANE_GRAY      , 16).pattern("sss", "sss").addIngredient('s').item(StainedGlassMat.STAINED_GLASS_GRAY      , false))
                .addShapedRecipe_(b -> b.result(StainedGlassPaneMat.STAINED_GLASS_PANE_LIGHT_GRAY, 16).pattern("sss", "sss").addIngredient('s').item(StainedGlassMat.STAINED_GLASS_LIGHT_GRAY, false))
                .addShapedRecipe_(b -> b.result(StainedGlassPaneMat.STAINED_GLASS_PANE_CYAN      , 16).pattern("sss", "sss").addIngredient('s').item(StainedGlassMat.STAINED_GLASS_CYAN      , false))
                .addShapedRecipe_(b -> b.result(StainedGlassPaneMat.STAINED_GLASS_PANE_PURPLE    , 16).pattern("sss", "sss").addIngredient('s').item(StainedGlassMat.STAINED_GLASS_PURPLE    , false))
                .addShapedRecipe_(b -> b.result(StainedGlassPaneMat.STAINED_GLASS_PANE_BLUE      , 16).pattern("sss", "sss").addIngredient('s').item(StainedGlassMat.STAINED_GLASS_BLUE      , false))
                .addShapedRecipe_(b -> b.result(StainedGlassPaneMat.STAINED_GLASS_PANE_BROWN     , 16).pattern("sss", "sss").addIngredient('s').item(StainedGlassMat.STAINED_GLASS_BROWN     , false))
                .addShapedRecipe_(b -> b.result(StainedGlassPaneMat.STAINED_GLASS_PANE_GREEN     , 16).pattern("sss", "sss").addIngredient('s').item(StainedGlassMat.STAINED_GLASS_GREEN     , false))
                .addShapedRecipe_(b -> b.result(StainedGlassPaneMat.STAINED_GLASS_PANE_RED       , 16).pattern("sss", "sss").addIngredient('s').item(StainedGlassMat.STAINED_GLASS_RED       , false))
                .addShapedRecipe_(b -> b.result(StainedGlassPaneMat.STAINED_GLASS_PANE_BLACK     , 16).pattern("sss", "sss").addIngredient('s').item(StainedGlassMat.STAINED_GLASS_BLACK     , false)))
            .addGroupedRecipe(g -> g.validator(this.shaped_(Material.OAK_FENCE, 3, "psp", "psp").addIngredient('s').item(Material.STICK, false).addIngredient('p').item(Material.PLANKS, true))
                .addShapedRecipe_(b -> b.result(Material.OAK_FENCE     , 3).pattern("psp", "psp").addIngredient('s').item(Material.STICK, false).addIngredient('p').item(PlanksMat.PLANKS_OAK     , false))
                .addShapedRecipe_(b -> b.result(Material.BIRCH_FENCE   , 3).pattern("psp", "psp").addIngredient('s').item(Material.STICK, false).addIngredient('p').item(PlanksMat.PLANKS_BIRCH   , false))
                .addShapedRecipe_(b -> b.result(Material.SPRUCE_FENCE  , 3).pattern("psp", "psp").addIngredient('s').item(Material.STICK, false).addIngredient('p').item(PlanksMat.PLANKS_SPRUCE  , false))
                .addShapedRecipe_(b -> b.result(Material.JUNGLE_FENCE  , 3).pattern("psp", "psp").addIngredient('s').item(Material.STICK, false).addIngredient('p').item(PlanksMat.PLANKS_JUNGLE  , false))
                .addShapedRecipe_(b -> b.result(Material.ACACIA_FENCE  , 3).pattern("psp", "psp").addIngredient('s').item(Material.STICK, false).addIngredient('p').item(PlanksMat.PLANKS_ACACIA  , false))
                .addShapedRecipe_(b -> b.result(Material.DARK_OAK_FENCE, 3).pattern("psp", "psp").addIngredient('s').item(Material.STICK, false).addIngredient('p').item(PlanksMat.PLANKS_DARK_OAK, false)))
            .addGroupedRecipe(g -> g.validator(this.shaped_(Material.OAK_FENCE_GATE, 1, "sps", "sps").addIngredient('s').item(Material.STICK, false).addIngredient('p').item(Material.PLANKS, true))
                .addShapedRecipe_(b -> b.result(Material.OAK_FENCE_GATE     , 1).pattern("sps", "sps").addIngredient('s').item(Material.STICK, false).addIngredient('p').item(PlanksMat.PLANKS_OAK     , false))
                .addShapedRecipe_(b -> b.result(Material.BIRCH_FENCE_GATE   , 1).pattern("sps", "sps").addIngredient('s').item(Material.STICK, false).addIngredient('p').item(PlanksMat.PLANKS_BIRCH   , false))
                .addShapedRecipe_(b -> b.result(Material.SPRUCE_FENCE_GATE  , 1).pattern("sps", "sps").addIngredient('s').item(Material.STICK, false).addIngredient('p').item(PlanksMat.PLANKS_SPRUCE  , false))
                .addShapedRecipe_(b -> b.result(Material.JUNGLE_FENCE_GATE  , 1).pattern("sps", "sps").addIngredient('s').item(Material.STICK, false).addIngredient('p').item(PlanksMat.PLANKS_JUNGLE  , false))
                .addShapedRecipe_(b -> b.result(Material.ACACIA_FENCE_GATE  , 1).pattern("sps", "sps").addIngredient('s').item(Material.STICK, false).addIngredient('p').item(PlanksMat.PLANKS_ACACIA  , false))
                .addShapedRecipe_(b -> b.result(Material.DARK_OAK_FENCE_GATE, 1).pattern("sps", "sps").addIngredient('s').item(Material.STICK, false).addIngredient('p').item(PlanksMat.PLANKS_DARK_OAK, false)))
            .addGroupedRecipe(g -> g.validator(this.shaped_(Material.OAK_BOAT, 1, "psp", "ppp").addIngredient('s').item(Material.WOODEN_SHOVEL, false).addIngredient('p').item(Material.PLANKS, true))
                .addShapedRecipe_(b -> b.result(Material.OAK_BOAT     , 1).pattern("psp", "ppp").addIngredient('s').item(Material.WOODEN_SHOVEL, false).addIngredient('p').item(PlanksMat.PLANKS_OAK     , false))
                .addShapedRecipe_(b -> b.result(Material.BIRCH_BOAT   , 1).pattern("psp", "ppp").addIngredient('s').item(Material.WOODEN_SHOVEL, false).addIngredient('p').item(PlanksMat.PLANKS_BIRCH   , false))
                .addShapedRecipe_(b -> b.result(Material.SPRUCE_BOAT  , 1).pattern("psp", "ppp").addIngredient('s').item(Material.WOODEN_SHOVEL, false).addIngredient('p').item(PlanksMat.PLANKS_SPRUCE  , false))
                .addShapedRecipe_(b -> b.result(Material.JUNGLE_BOAT  , 1).pattern("psp", "ppp").addIngredient('s').item(Material.WOODEN_SHOVEL, false).addIngredient('p').item(PlanksMat.PLANKS_JUNGLE  , false))
                .addShapedRecipe_(b -> b.result(Material.ACACIA_BOAT  , 1).pattern("psp", "ppp").addIngredient('s').item(Material.WOODEN_SHOVEL, false).addIngredient('p').item(PlanksMat.PLANKS_ACACIA  , false))
                .addShapedRecipe_(b -> b.result(Material.DARK_OAK_BOAT, 1).pattern("psp", "ppp").addIngredient('s').item(Material.WOODEN_SHOVEL, false).addIngredient('p').item(PlanksMat.PLANKS_DARK_OAK, false)))
            .addShapedRecipe_(b -> b.result(Material.REDSTONE_REPEATER_ITEM          , 1 ).pattern("rRr", "sss").addIngredient('s').item(Material.STONE            , false).addIngredient('r').item(RedstoneTorchOnMat.REDSTONE_TORCH_ON_ITEM, true).addIngredient('R').item(Material.REDSTONE, false))
            .addShapedRecipe_(b -> b.result(Material.BED                             , 1 ).pattern("www", "WWW").addIngredient('W').item(Material.PLANKS           , true ).addIngredient('w').item(Material.WOOL                            , true))
            .addShapedRecipe_(b -> b.result(Material.IRON_BARS                       , 16).pattern("iii", "iii").addIngredient('i').item(Material.IRON_INGOT       , false))
            .addShapedRecipe_(b -> b.result(Material.GLASS_PANE                      , 16).pattern("ggg", "ggg").addIngredient('g').item(Material.GLASS            , true ))
            .addShapedRecipe_(b -> b.result(Material.COBBLESTONE_WALL                , 6 ).pattern("ccc", "ccc").addIngredient('c').item(Material.COBBLESTONE      , true ))
            .addShapedRecipe_(b -> b.result(CobblestoneWallMat.COBBLESTONE_WALL_MOSSY, 6 ).pattern("mmm", "mmm").addIngredient('m').item(Material.MOSSY_COBBLESTONE, true ))
            .addShapedRecipe_(b -> b.result(Material.NETHER_BRICK_FENCE              , 6 ).pattern("nnn", "nnn").addIngredient('n').item(Material.NETHER_BRICK     , true ))
            .addShapedRecipe_(b -> b.result(Material.WOODEN_TRAPDOOR                 , 2 ).pattern("www", "www").addIngredient('w').item(Material.PLANKS           , true ))
            .buildAndAdd();

        // any 1 line crafting recipe [xxx]
        this.grouped(this.craftingBuilder().shaped().pattern("xxx").addIngredient('x').any())
            .addGroupedRecipe(mg -> mg.validator(this.shaped_(Material.STONE_SLAB, 6, "xxx").addIngredient('x').any())
                .addShapedRecipe_(b -> b.result(StoneSlabMat.STONE_SLAB              , 6).pattern("sss").addIngredient('s').item(Material.STONE        , false))
                .addShapedRecipe_(b -> b.result(StoneSlabMat.STONE_SLAB_COBBLESTONE  , 6).pattern("ccc").addIngredient('c').item(Material.COBBLESTONE  , false))
                .addShapedRecipe_(b -> b.result(StoneSlabMat.STONE_SLAB_SANDSTONE    , 6).pattern("sss").addIngredient('s').item(Material.SANDSTONE    , false))
                .addShapedRecipe_(b -> b.result(StoneSlabMat.STONE_SLAB_BRICKS       , 6).pattern("bbb").addIngredient('b').item(Material.BRICK_BLOCK  , false))
                .addShapedRecipe_(b -> b.result(StoneSlabMat.STONE_SLAB_STONE_BRICKS , 6).pattern("sss").addIngredient('s').item(Material.STONE_BRICK  , false))
                .addShapedRecipe_(b -> b.result(StoneSlabMat.STONE_SLAB_NETHER_BRICKS, 6).pattern("nnn").addIngredient('n').item(Material.NETHER_BRICK , false))
                .addShapedRecipe_(b -> b.result(StoneSlabMat.STONE_SLAB_QUARTZ       , 6).pattern("qqq").addIngredient('q').item(Material.QUARTZ_BLOCK , false))
                .addShapedRecipe_(b -> b.result(StoneSlabMat.STONE_SLAB_RED_SANDSTONE, 6).pattern("rrr").addIngredient('r').item(Material.RED_SANDSTONE, false))
                .addGroupedRecipe(g -> g.validator(this.shaped_(Material.WOODEN_SLAB, 6, "ppp").addIngredient('p').item(Material.PLANKS, true))
                    .addShapedRecipe_(b -> b.result(WoodenSlabMat.WOODEN_SLAB_OAK     , 6).pattern("ppp").addIngredient('p').item(PlanksMat.PLANKS_OAK     , false))
                    .addShapedRecipe_(b -> b.result(WoodenSlabMat.WOODEN_SLAB_BIRCH   , 6).pattern("ppp").addIngredient('p').item(PlanksMat.PLANKS_BIRCH   , false))
                    .addShapedRecipe_(b -> b.result(WoodenSlabMat.WOODEN_SLAB_SPRUCE  , 6).pattern("ppp").addIngredient('p').item(PlanksMat.PLANKS_SPRUCE  , false))
                    .addShapedRecipe_(b -> b.result(WoodenSlabMat.WOODEN_SLAB_JUNGLE  , 6).pattern("ppp").addIngredient('p').item(PlanksMat.PLANKS_JUNGLE  , false))
                    .addShapedRecipe_(b -> b.result(WoodenSlabMat.WOODEN_SLAB_ACACIA  , 6).pattern("ppp").addIngredient('p').item(PlanksMat.PLANKS_ACACIA  , false))
                    .addShapedRecipe_(b -> b.result(WoodenSlabMat.WOODEN_SLAB_DARK_OAK, 6).pattern("ppp").addIngredient('p').item(PlanksMat.PLANKS_DARK_OAK, false))))
            .addShapedRecipe_(b -> b.result(Material.COOKIE    , 8).pattern("wiw").addIngredient('i').item(DyeMat.DYE_COCOA_BEANS, false).addIngredient('w').item(Material.WHEAT, false))
            .addShapedRecipe_(b -> b.result(Material.BREAD     , 1).pattern("www").addIngredient('w').item(Material.WHEAT        , false))
            .addShapedRecipe_(b -> b.result(Material.PAPER     , 3).pattern("sss").addIngredient('s').item(Material.REEDS        , false))
            .addShapedRecipe_(b -> b.result(Material.SNOW_LAYER, 6).pattern("sss").addIngredient('s').item(Material.SNOW_BLOCK   , true ))
            .buildAndAdd();

        // any 1x3 horizontal line crafting recipe [x,x,x]
        this.grouped(this.craftingBuilder().shaped().pattern("x", "x", "x").addIngredient('x').any())
            .addShapedRecipe_(b -> b.result(Material.TRIPWIRE_HOOK , 2).pattern("i", "s", "w").addIngredient('s').item(Material.STICK, false).addIngredient('w').item(Material.PLANKS     , true ).addIngredient('i').item(Material.IRON_INGOT, false))
            .addShapedRecipe_(b -> b.result(Material.ARROW         , 4).pattern("f", "s", "F").addIngredient('s').item(Material.STICK, false).addIngredient('f').item(Material.FLINT      , false).addIngredient('F').item(Material.FEATHER   , false))
            .addShapedRecipe_(b -> b.result(Material.WOODEN_SHOVEL , 1).pattern("w", "s", "s").addIngredient('s').item(Material.STICK, false).addIngredient('w').item(Material.PLANKS     , true ))
            .addShapedRecipe_(b -> b.result(Material.STONE_SHOVEL  , 1).pattern("c", "s", "s").addIngredient('s').item(Material.STICK, false).addIngredient('c').item(Material.COBBLESTONE, true ))
            .addShapedRecipe_(b -> b.result(Material.IRON_SHOVEL   , 1).pattern("i", "s", "s").addIngredient('s').item(Material.STICK, false).addIngredient('i').item(Material.IRON_INGOT , false))
            .addShapedRecipe_(b -> b.result(Material.GOLDEN_SHOVEL , 1).pattern("g", "s", "s").addIngredient('s').item(Material.STICK, false).addIngredient('g').item(Material.GOLD_INGOT , false))
            .addShapedRecipe_(b -> b.result(Material.DIAMOND_SHOVEL, 1).pattern("d", "s", "s").addIngredient('s').item(Material.STICK, false).addIngredient('d').item(Material.DIAMOND    , false))
            .addShapedRecipe_(b -> b.result(Material.WOODEN_SWORD  , 1).pattern("w", "w", "s").addIngredient('s').item(Material.STICK, false).addIngredient('w').item(Material.PLANKS     , true ))
            .addShapedRecipe_(b -> b.result(Material.STONE_SWORD   , 1).pattern("c", "c", "s").addIngredient('s').item(Material.STICK, false).addIngredient('c').item(Material.COBBLESTONE, true ))
            .addShapedRecipe_(b -> b.result(Material.IRON_SWORD    , 1).pattern("i", "i", "s").addIngredient('s').item(Material.STICK, false).addIngredient('i').item(Material.IRON_INGOT , false))
            .addShapedRecipe_(b -> b.result(Material.GOLDEN_SWORD  , 1).pattern("g", "g", "s").addIngredient('s').item(Material.STICK, false).addIngredient('g').item(Material.GOLD_INGOT , false))
            .addShapedRecipe_(b -> b.result(Material.DIAMOND_SWORD , 1).pattern("d", "d", "s").addIngredient('s').item(Material.STICK, false).addIngredient('d').item(Material.DIAMOND    , false))
            .buildAndAdd();

        // any 1x2 horizontal line crafting recipe [x,x]
        this.grouped(this.craftingBuilder().shaped().pattern("x", "x").addIngredient('x').any())
            .addShapedRecipe_(b -> b.result(TorchMat.TORCH_ITEM                        , 4).pattern("c", "s").addIngredient('s').item(Material.STICK                      , false).addIngredient('c').item(Material.COAL       , true ))
            .addShapedRecipe_(b -> b.result(Material.PUMPKIN_LANTERN                   , 1).pattern("p", "t").addIngredient('t').item(TorchMat.TORCH_ITEM                 , true ).addIngredient('p').item(Material.PUMPKIN    , true ))
            .addShapedRecipe_(b -> b.result(Material.CHEST_MINECART                    , 1).pattern("c", "m").addIngredient('m').item(Material.MINECART                   , false).addIngredient('c').item(Material.CHEST      , true ))
            .addShapedRecipe_(b -> b.result(Material.FURNACE_MINECART                  , 1).pattern("f", "m").addIngredient('m').item(Material.MINECART                   , false).addIngredient('f').item(Material.FURNACE    , true ))
            .addShapedRecipe_(b -> b.result(Material.TNT_MINECART                      , 1).pattern("t", "m").addIngredient('m').item(Material.MINECART                   , false).addIngredient('t').item(Material.TNT        , true ))
            .addShapedRecipe_(b -> b.result(Material.HOPPER_MINECART                   , 1).pattern("h", "m").addIngredient('m').item(Material.MINECART                   , false).addIngredient('h').item(Material.HOPPER     , true ))
            .addShapedRecipe_(b -> b.result(Material.LEVER                             , 1).pattern("s", "c").addIngredient('s').item(Material.STICK                      , false).addIngredient('c').item(Material.COBBLESTONE, true ))
            .addShapedRecipe_(b -> b.result(RedstoneTorchOnMat.REDSTONE_TORCH_ON_ITEM  , 1).pattern("r", "s").addIngredient('s').item(Material.STICK                      , false).addIngredient('r').item(Material.REDSTONE   , false))
            .addShapedRecipe_(b -> b.result(Material.PISTON_STICKY                     , 1).pattern("s", "p").addIngredient('s').item(Material.SLIMEBALL                  , false).addIngredient('p').item(Material.PISTON     , true ))
            .addShapedRecipe_(b -> b.result(SandstoneMat.SANDSTONE_CHISELED            , 1).pattern("s", "s").addIngredient('s').item(StoneSlabMat.STONE_SLAB_SANDSTONE   , false))
            .addShapedRecipe_(b -> b.result(RedSandstoneMat.RED_SANDSTONE_CHISELED     , 1).pattern("s", "s").addIngredient('s').item(StoneSlabMat.STONE_SLAB_STONE       , false))
            .addShapedRecipe_(b -> b.result(QuartzBlockMat.QUARTZ_BLOCK_CHISELED       , 1).pattern("s", "s").addIngredient('s').item(StoneSlabMat.STONE_SLAB_QUARTZ      , false))
            .addShapedRecipe_(b -> b.result(QuartzBlockMat.QUARTZ_BLOCK_PILLAR_VERTICAL, 2).pattern("q", "q").addIngredient('q').item(Material.QUARTZ_BLOCK               , false))
            .addShapedRecipe_(b -> b.result(StoneBrickMat.STONE_BRICK_CHISELED         , 1).pattern("s", "s").addIngredient('s').item(StoneSlabMat.STONE_SLAB_STONE_BRICKS, false))
            .addShapedRecipe_(b -> b.result(Material.STICK                             , 4).pattern("w", "w").addIngredient('w').item(Material.PLANKS                     , true ))
            .buildAndAdd();

        // any 2x2 box crafting recipe [xx,xx]
        this.grouped(this.craftingBuilder().shaped().pattern("xx", "xx").addIngredient('x').any())
                .addShapedRecipe_(b -> b.result(StoneMat.STONE_DIORITE              , 2).pattern("cq", "qc").addIngredient('q').item(Material.QUARTZ           , false).addIngredient('c').item(Material.COBBLESTONE, true ))
                .addShapedRecipe_(b -> b.result(DirtMat.DIRT_COARSE                 , 4).pattern("dg", "gd").addIngredient('g').item(Material.GRAVEL           , true ).addIngredient('d').item(Material.DIRT       , false))
                .addShapedRecipe_(b -> b.result(Material.CRAFTING_TABLE             , 1).pattern("ww", "ww").addIngredient('w').item(Material.PLANKS           , true ))
                .addShapedRecipe_(b -> b.result(Material.SANDSTONE                  , 1).pattern("ss", "ss").addIngredient('s').item(Material.SAND             , false))
                .addShapedRecipe_(b -> b.result(Material.RED_SANDSTONE              , 1).pattern("ss", "ss").addIngredient('s').item(SandMat.SAND_RED          , false))
                .addShapedRecipe_(b -> b.result(SandstoneMat.SANDSTONE_SMOOTH       , 4).pattern("ss", "ss").addIngredient('s').item(Material.SANDSTONE        , false))
                .addShapedRecipe_(b -> b.result(RedSandstoneMat.RED_SANDSTONE_SMOOTH, 4).pattern("rr", "rr").addIngredient('r').item(Material.RED_SANDSTONE    , false))
                .addShapedRecipe_(b -> b.result(Material.STONE_BRICK                , 4).pattern("ss", "ss").addIngredient('s').item(Material.STONE            , false))
                .addShapedRecipe_(b -> b.result(Material.NETHER_BRICK               , 1).pattern("nn", "nn").addIngredient('n').item(Material.NETHER_BRICK_ITEM, false))
                .addShapedRecipe_(b -> b.result(StoneMat.STONE_POLISHED_DIORITE     , 4).pattern("ss", "ss").addIngredient('s').item(StoneMat.STONE_DIORITE    , false))
                .addShapedRecipe_(b -> b.result(StoneMat.STONE_POLISHED_GRANITE     , 4).pattern("ss", "ss").addIngredient('s').item(StoneMat.STONE_GRANITE    , false))
                .addShapedRecipe_(b -> b.result(StoneMat.STONE_POLISHED_ANDESITE    , 4).pattern("ss", "ss").addIngredient('s').item(StoneMat.STONE_ANDESITE   , false))
                .addShapedRecipe_(b -> b.result(Material.PRISMARINE                 , 1).pattern("pp", "pp").addIngredient('p').item(Material.PRISMARINE_SHARD , false))
                .addShapedRecipe_(b -> b.result(Material.SNOW_BLOCK                 , 1).pattern("ss", "ss").addIngredient('s').item(Material.SNOWBALL         , false))
                .addShapedRecipe_(b -> b.result(Material.CLAY_BLOCK                 , 1).pattern("cc", "cc").addIngredient('c').item(Material.CLAY_BALL        , false))
                .addShapedRecipe_(b -> b.result(Material.BRICK_BLOCK                , 1).pattern("cc", "cc").addIngredient('c').item(Material.BRICK            , false))
                .addShapedRecipe_(b -> b.result(Material.GLOWSTONE                  , 1).pattern("gg", "gg").addIngredient('g').item(Material.GLOWSTONE_DUST   , false))
                .addShapedRecipe_(b -> b.result(Material.QUARTZ_BLOCK               , 1).pattern("qq", "qq").addIngredient('q').item(Material.QUARTZ           , false))
                .addShapedRecipe_(b -> b.result(Material.WOOL                       , 1).pattern("ss", "ss").addIngredient('s').item(Material.STRING           , false))
                .addShapedRecipe_(b -> b.result(Material.IRON_TRAPDOOR              , 1).pattern("ii", "ii").addIngredient('i').item(Material.IRON_INGOT       , false))
                .addShapedRecipe_(b -> b.result(Material.LEATHER                    , 1).pattern("rr", "rr").addIngredient('r').item(Material.RABBIT_HIDE      , false))
            .buildAndAdd();

        // any 2x1 box crafting recipe [xx]
        this.grouped(this.craftingBuilder().shaped().pattern("xx").addIngredient('x').any())
                .addShapedRecipe_(b -> b.result(Material.TRAPPED_CHEST        , 1).pattern("ct").addIngredient('t').item(Material.TRIPWIRE_HOOK, true).addIngredient('c').item(Material.CHEST, true))
                .addShapedRecipe_(b -> b.result(Material.STONE_PRESSURE_PLATE , 1).pattern("ss").addIngredient('s').item(Material.STONE        , false))
                .addShapedRecipe_(b -> b.result(Material.WOODEN_PRESSURE_PLATE, 1).pattern("ww").addIngredient('w').item(Material.PLANKS       , true ))
                .addShapedRecipe_(b -> b.result(Material.IRON_PRESSURE_PLATE  , 1).pattern("ii").addIngredient('i').item(Material.IRON_INGOT   , false))
                .addShapedRecipe_(b -> b.result(Material.GOLDEN_PRESSURE_PLATE, 1).pattern("gg").addIngredient('g').item(Material.GOLD_INGOT   , false))
            .addGroupedRecipe(g -> g.validator(this.craftingBuilder().shaped().pattern("ww").addIngredient('w').item(Material.WOOL, true))
                .addShapedRecipe_(b -> b.result(CarpetMat.CARPET_WHITE     , 3).pattern("ww").addIngredient('w').item(WoolMat.WOOL_WHITE     , false))
                .addShapedRecipe_(b -> b.result(CarpetMat.CARPET_ORANGE    , 3).pattern("ww").addIngredient('w').item(WoolMat.WOOL_ORANGE    , false))
                .addShapedRecipe_(b -> b.result(CarpetMat.CARPET_MAGENTA   , 3).pattern("ww").addIngredient('w').item(WoolMat.WOOL_MAGENTA   , false))
                .addShapedRecipe_(b -> b.result(CarpetMat.CARPET_LIGHT_BLUE, 3).pattern("ww").addIngredient('w').item(WoolMat.WOOL_LIGHT_BLUE, false))
                .addShapedRecipe_(b -> b.result(CarpetMat.CARPET_YELLOW    , 3).pattern("ww").addIngredient('w').item(WoolMat.WOOL_YELLOW    , false))
                .addShapedRecipe_(b -> b.result(CarpetMat.CARPET_LIME      , 3).pattern("ww").addIngredient('w').item(WoolMat.WOOL_LIME      , false))
                .addShapedRecipe_(b -> b.result(CarpetMat.CARPET_PINK      , 3).pattern("ww").addIngredient('w').item(WoolMat.WOOL_PINK      , false))
                .addShapedRecipe_(b -> b.result(CarpetMat.CARPET_GRAY      , 3).pattern("ww").addIngredient('w').item(WoolMat.WOOL_GRAY      , false))
                .addShapedRecipe_(b -> b.result(CarpetMat.CARPET_LIGHT_GRAY, 3).pattern("ww").addIngredient('w').item(WoolMat.WOOL_LIGHT_GRAY, false))
                .addShapedRecipe_(b -> b.result(CarpetMat.CARPET_CYAN      , 3).pattern("ww").addIngredient('w').item(WoolMat.WOOL_CYAN      , false))
                .addShapedRecipe_(b -> b.result(CarpetMat.CARPET_PURPLE    , 3).pattern("ww").addIngredient('w').item(WoolMat.WOOL_PURPLE    , false))
                .addShapedRecipe_(b -> b.result(CarpetMat.CARPET_BLUE      , 3).pattern("ww").addIngredient('w').item(WoolMat.WOOL_BLUE      , false))
                .addShapedRecipe_(b -> b.result(CarpetMat.CARPET_BROWN     , 3).pattern("ww").addIngredient('w').item(WoolMat.WOOL_BROWN     , false))
                .addShapedRecipe_(b -> b.result(CarpetMat.CARPET_GREEN     , 3).pattern("ww").addIngredient('w').item(WoolMat.WOOL_GREEN     , false))
                .addShapedRecipe_(b -> b.result(CarpetMat.CARPET_RED       , 3).pattern("ww").addIngredient('w').item(WoolMat.WOOL_RED       , false))
                .addShapedRecipe_(b -> b.result(CarpetMat.CARPET_BLACK     , 3).pattern("ww").addIngredient('w').item(WoolMat.WOOL_BLACK     , false)))
            .buildAndAdd();

        // any bowl shaped crafting recipe [x_x, _x_]
        this.grouped(this.craftingBuilder().shaped().pattern("x_x", "_x_").addIngredient('x').any())
                .addShapedRecipe_(b -> b.result(Material.BUCKET      , 1).pattern("i_i", "_i_").addIngredient('i').item(Material.IRON_INGOT, false))
                .addShapedRecipe_(b -> b.result(Material.FLOWER_POT  , 1).pattern("c_c", "_c_").addIngredient('c').item(Material.BRICK     , false))
                .addShapedRecipe_(b -> b.result(Material.GLASS_BOTTLE, 3).pattern("g_g", "_g_").addIngredient('g').item(Material.GLASS     , true ))
                .addShapedRecipe_(b -> b.result(Material.BOWL        , 4).pattern("w_w", "_w_").addIngredient('w').item(Material.PLANKS    , true ))
            .buildAndAdd();

        // any cross shaped crafting recipe [_x_, xxx, _x_]
        this.grouped(this.craftingBuilder().shaped().pattern("x_x", "_x_").addIngredient('x').any())
                .addShapedRecipe_(b -> b.result(Material.RABBIT_STEW      , 1).pattern("_r_", "cpm", "_b_").addIngredient('p').item(Material.BAKED_POTATO, false).addIngredient('b').item(Material.BOWL    , false).addIngredient('c').item(Material.CARROT, false).addIngredient('r').item(Material.COOKED_RABBIT, false).addIngredient('m').item(Material.BROWN_MUSHROOM, true).item(Material.RED_MUSHROOM, true))
                .addShapedRecipe_(b -> b.result(Material.CLOCK            , 1).pattern("_g_", "grg", "_g_").addIngredient('g').item(Material.GOLD_INGOT  , false).addIngredient('r').item(Material.REDSTONE, false))
                .addShapedRecipe_(b -> b.result(Material.COMPASS          , 1).pattern("_i_", "iri", "_i_").addIngredient('i').item(Material.IRON_INGOT  , false).addIngredient('r').item(Material.REDSTONE, false))
                .addShapedRecipe_(b -> b.result(Material.REDSTONE_LAMP_OFF, 1).pattern("_r_", "rgr", "_r_").addIngredient('g').item(Material.GLOWSTONE   , true ).addIngredient('r').item(Material.REDSTONE, false))
            .buildAndAdd();

        // any 2x3 box crafting recipe [xx,xx,xx]
        this.grouped(this.craftingBuilder().shaped().pattern("xx", "xx", "xx").addIngredient('x').any())
            .addShapedRecipe_(b -> b.result(Material.IRON_DOOR_ITEM, 3).pattern("ii", "ii", "ii").addIngredient('i').item(Material.IRON_INGOT, false))
            .addGroupedRecipe(g -> g.validator(this.craftingBuilder().shaped().pattern("pp", "pp", "pp").addIngredient('p').item(Material.PLANKS, true))
                .addShapedRecipe_(b -> b.result(Material.OAK_DOOR_ITEM     , 3).pattern("pp", "pp", "pp").addIngredient('p').item(PlanksMat.PLANKS_OAK     , false))
                .addShapedRecipe_(b -> b.result(Material.BIRCH_DOOR_ITEM   , 3).pattern("pp", "pp", "pp").addIngredient('p').item(PlanksMat.PLANKS_BIRCH   , false))
                .addShapedRecipe_(b -> b.result(Material.SPRUCE_DOOR_ITEM  , 3).pattern("pp", "pp", "pp").addIngredient('p').item(PlanksMat.PLANKS_SPRUCE  , false))
                .addShapedRecipe_(b -> b.result(Material.JUNGLE_DOOR_ITEM  , 3).pattern("pp", "pp", "pp").addIngredient('p').item(PlanksMat.PLANKS_JUNGLE  , false))
                .addShapedRecipe_(b -> b.result(Material.ACACIA_DOOR_ITEM  , 3).pattern("pp", "pp", "pp").addIngredient('p').item(PlanksMat.PLANKS_ACACIA  , false))
                .addShapedRecipe_(b -> b.result(Material.DARK_OAK_DOOR_ITEM, 3).pattern("pp", "pp", "pp").addIngredient('p').item(PlanksMat.PLANKS_DARK_OAK, false)))
            .buildAndAdd();

        // sign-like recipes
        this.grouped(this.craftingBuilder().shaped().pattern("xxx", "xxx", "_x_").addIngredient('x').any())
            .addShapedRecipe_(b -> b.result(Material.SIGN, 3).pattern("www", "www", "_s_").addIngredient('s').item(Material.STICK, false).addIngredient('w').item(Material.PLANKS, true))
            .addGroupedRecipe(g -> g.validator(this.craftingBuilder().shaped().pattern("www", "www", "_s_").addIngredient('s').item(Material.STICK, true).addIngredient('w').item(Material.WOOL, true))
                .addShapedRecipe_(b -> b.result(BannerMat.BANNER_BLACK     , 1).pattern("www", "www", "_s_").addIngredient('s').item(Material.STICK, true).addIngredient('w').item(WoolMat.WOOL_BLACK     , false))
                .addShapedRecipe_(b -> b.result(BannerMat.BANNER_RED       , 1).pattern("www", "www", "_s_").addIngredient('s').item(Material.STICK, true).addIngredient('w').item(WoolMat.WOOL_RED       , false))
                .addShapedRecipe_(b -> b.result(BannerMat.BANNER_GREEN     , 1).pattern("www", "www", "_s_").addIngredient('s').item(Material.STICK, true).addIngredient('w').item(WoolMat.WOOL_GREEN     , false))
                .addShapedRecipe_(b -> b.result(BannerMat.BANNER_BROWN     , 1).pattern("www", "www", "_s_").addIngredient('s').item(Material.STICK, true).addIngredient('w').item(WoolMat.WOOL_BROWN     , false))
                .addShapedRecipe_(b -> b.result(BannerMat.BANNER_BLUE      , 1).pattern("www", "www", "_s_").addIngredient('s').item(Material.STICK, true).addIngredient('w').item(WoolMat.WOOL_BLUE      , false))
                .addShapedRecipe_(b -> b.result(BannerMat.BANNER_PURPLE    , 1).pattern("www", "www", "_s_").addIngredient('s').item(Material.STICK, true).addIngredient('w').item(WoolMat.WOOL_PURPLE    , false))
                .addShapedRecipe_(b -> b.result(BannerMat.BANNER_CYAN      , 1).pattern("www", "www", "_s_").addIngredient('s').item(Material.STICK, true).addIngredient('w').item(WoolMat.WOOL_CYAN      , false))
                .addShapedRecipe_(b -> b.result(BannerMat.BANNER_LIGHT_GRAY, 1).pattern("www", "www", "_s_").addIngredient('s').item(Material.STICK, true).addIngredient('w').item(WoolMat.WOOL_LIGHT_GRAY, false))
                .addShapedRecipe_(b -> b.result(BannerMat.BANNER_GRAY      , 1).pattern("www", "www", "_s_").addIngredient('s').item(Material.STICK, true).addIngredient('w').item(WoolMat.WOOL_GRAY      , false))
                .addShapedRecipe_(b -> b.result(BannerMat.BANNER_PINK      , 1).pattern("www", "www", "_s_").addIngredient('s').item(Material.STICK, true).addIngredient('w').item(WoolMat.WOOL_PINK      , false))
                .addShapedRecipe_(b -> b.result(BannerMat.BANNER_LIME      , 1).pattern("www", "www", "_s_").addIngredient('s').item(Material.STICK, true).addIngredient('w').item(WoolMat.WOOL_LIME      , false))
                .addShapedRecipe_(b -> b.result(BannerMat.BANNER_YELLOW    , 1).pattern("www", "www", "_s_").addIngredient('s').item(Material.STICK, true).addIngredient('w').item(WoolMat.WOOL_YELLOW    , false))
                .addShapedRecipe_(b -> b.result(BannerMat.BANNER_LIGHT_BLUE, 1).pattern("www", "www", "_s_").addIngredient('s').item(Material.STICK, true).addIngredient('w').item(WoolMat.WOOL_LIGHT_BLUE, false))
                .addShapedRecipe_(b -> b.result(BannerMat.BANNER_MAGENTA   , 1).pattern("www", "www", "_s_").addIngredient('s').item(Material.STICK, true).addIngredient('w').item(WoolMat.WOOL_MAGENTA   , false))
                .addShapedRecipe_(b -> b.result(BannerMat.BANNER_ORANGE    , 1).pattern("www", "www", "_s_").addIngredient('s').item(Material.STICK, true).addIngredient('w').item(WoolMat.WOOL_ORANGE    , false))
                .addShapedRecipe_(b -> b.result(BannerMat.BANNER_WHITE     , 1).pattern("www", "www", "_s_").addIngredient('s').item(Material.STICK, true).addIngredient('w').item(WoolMat.WOOL_WHITE     , false)))
            .buildAndAdd();

        this.grouped(this.craftingBuilder().shaped().pattern("x__", "xx_", "xxx").addIngredient('x').any())
            .addShapedRecipe_(b -> b.result(Material.COBBLESTONE_STAIRS     , 4).pattern("c__", "cc_", "ccc").addIngredient('c').item(PlanksMat.COBBLESTONE     , false))
            .addShapedRecipe_(b -> b.result(Material.BRICK_STAIRS   , 4).pattern("b__", "bb_", "bbb").addIngredient('b').item(PlanksMat.BRICK_BLOCK   , false))
            .addShapedRecipe_(b -> b.result(Material.STONE_BRICK_STAIRS  , 4).pattern("s__", "ss_", "sss").addIngredient('s').item(PlanksMat.STONE_BRICK  , false))
            .addShapedRecipe_(b -> b.result(Material.NETHER_BRICK_STAIRS  , 4).pattern("n__", "nn_", "nnn").addIngredient('n').item(PlanksMat.NETHER_BRICK  , false))
            .addShapedRecipe_(b -> b.result(Material.SANDSTONE_STAIRS  , 4).pattern("s__", "ss_", "sss").addIngredient('s').item(PlanksMat.SANDSTONE  , false))
            .addShapedRecipe_(b -> b.result(Material.RED_SANDSTONE_STAIRS, 4).pattern("r__", "rr_", "rrr").addIngredient('r').item(PlanksMat.RED_SANDSTONE, false))
            .addShapedRecipe_(b -> b.result(Material.QUARTZ_STAIRS, 4).pattern("q__", "qq_", "qqq").addIngredient('q').item(PlanksMat.QUARTZ_BLOCK, false))
            .addGroupedRecipe(g -> g.validator(this.shaped_(Material.OAK_STAIRS, 4,  "p__", "pp_", "ppp").addIngredient('p').item(Material.PLANKS, true))
                .addShapedRecipe_(b -> b.result(Material.OAK_STAIRS     , 3).pattern("p__", "pp_", "ppp").addIngredient('p').item(PlanksMat.PLANKS_OAK     , false))
                .addShapedRecipe_(b -> b.result(Material.BIRCH_STAIRS   , 3).pattern("p__", "pp_", "ppp").addIngredient('p').item(PlanksMat.PLANKS_BIRCH   , false))
                .addShapedRecipe_(b -> b.result(Material.SPRUCE_STAIRS  , 3).pattern("p__", "pp_", "ppp").addIngredient('p').item(PlanksMat.PLANKS_SPRUCE  , false))
                .addShapedRecipe_(b -> b.result(Material.JUNGLE_STAIRS  , 3).pattern("p__", "pp_", "ppp").addIngredient('p').item(PlanksMat.PLANKS_JUNGLE  , false))
                .addShapedRecipe_(b -> b.result(Material.ACACIA_STAIRS  , 3).pattern("p__", "pp_", "ppp").addIngredient('p').item(PlanksMat.PLANKS_ACACIA  , false))
                .addShapedRecipe_(b -> b.result(Material.DARK_OAK_STAIRS, 3).pattern("p__", "pp_", "ppp").addIngredient('p').item(PlanksMat.PLANKS_DARK_OAK, false)))
            .buildAndAdd();
        this.grouped(this.craftingBuilder().shaped().pattern("__x", "_xx", "xxx").addIngredient('x').any())
            .addShapedRecipe_(b -> b.result(Material.COBBLESTONE_STAIRS     , 4).pattern("__c", "_cc", "ccc").addIngredient('c').item(PlanksMat.COBBLESTONE     , false))
            .addShapedRecipe_(b -> b.result(Material.BRICK_STAIRS   , 4).pattern("__b", "_bb", "bbb").addIngredient('b').item(PlanksMat.BRICK_BLOCK   , false))
            .addShapedRecipe_(b -> b.result(Material.STONE_BRICK_STAIRS  , 4).pattern("__s", "_ss", "sss").addIngredient('s').item(PlanksMat.STONE_BRICK  , false))
            .addShapedRecipe_(b -> b.result(Material.NETHER_BRICK_STAIRS  , 4).pattern("__n", "_nn", "nnn").addIngredient('n').item(PlanksMat.NETHER_BRICK  , false))
            .addShapedRecipe_(b -> b.result(Material.SANDSTONE_STAIRS  , 4).pattern("__s", "_ss", "sss").addIngredient('s').item(PlanksMat.SANDSTONE  , false))
            .addShapedRecipe_(b -> b.result(Material.RED_SANDSTONE_STAIRS, 4).pattern("__r", "_rr", "rrr").addIngredient('r').item(PlanksMat.RED_SANDSTONE, false))
            .addShapedRecipe_(b -> b.result(Material.QUARTZ_STAIRS, 4).pattern("__q", "_qq", "qqq").addIngredient('q').item(PlanksMat.QUARTZ_BLOCK, false))
            .addGroupedRecipe(g -> g.validator(this.shaped_(Material.OAK_STAIRS, 4,  "__p", "_pp", "ppp").addIngredient('p').item(Material.PLANKS, true))
                .addShapedRecipe_(b -> b.result(Material.OAK_STAIRS     , 3).pattern("__p", "_pp", "ppp").addIngredient('p').item(PlanksMat.PLANKS_OAK     , false))
                .addShapedRecipe_(b -> b.result(Material.BIRCH_STAIRS   , 3).pattern("__p", "_pp", "ppp").addIngredient('p').item(PlanksMat.PLANKS_BIRCH   , false))
                .addShapedRecipe_(b -> b.result(Material.SPRUCE_STAIRS  , 3).pattern("__p", "_pp", "ppp").addIngredient('p').item(PlanksMat.PLANKS_SPRUCE  , false))
                .addShapedRecipe_(b -> b.result(Material.JUNGLE_STAIRS  , 3).pattern("__p", "_pp", "ppp").addIngredient('p').item(PlanksMat.PLANKS_JUNGLE  , false))
                .addShapedRecipe_(b -> b.result(Material.ACACIA_STAIRS  , 3).pattern("__p", "_pp", "ppp").addIngredient('p').item(PlanksMat.PLANKS_ACACIA  , false))
                .addShapedRecipe_(b -> b.result(Material.DARK_OAK_STAIRS, 3).pattern("__p", "_pp", "ppp").addIngredient('p').item(PlanksMat.PLANKS_DARK_OAK, false)))
            .buildAndAdd();

        // axe
        this.grouped(this.craftingBuilder().shaped().pattern("xx", "xx", "_x").addIngredient('x').any())
            .addShapedRecipe_(b -> b.result(Material.WOODEN_AXE , 1).pattern("ww", "ws", "_s").addIngredient('s').item(Material.STICK, false).addIngredient('w').item(Material.PLANKS     , true ))
            .addShapedRecipe_(b -> b.result(Material.STONE_AXE  , 1).pattern("cc", "cs", "_s").addIngredient('s').item(Material.STICK, false).addIngredient('c').item(Material.COBBLESTONE, true ))
            .addShapedRecipe_(b -> b.result(Material.IRON_AXE   , 1).pattern("ii", "is", "_s").addIngredient('s').item(Material.STICK, false).addIngredient('i').item(Material.IRON_INGOT , false))
            .addShapedRecipe_(b -> b.result(Material.GOLDEN_AXE , 1).pattern("gg", "gs", "_s").addIngredient('s').item(Material.STICK, false).addIngredient('g').item(Material.GOLD_INGOT , false))
            .addShapedRecipe_(b -> b.result(Material.DIAMOND_AXE, 1).pattern("dd", "ds", "_s").addIngredient('s').item(Material.STICK, false).addIngredient('d').item(Material.DIAMOND    , false))
            .buildAndAdd();
        this.grouped(this.craftingBuilder().shaped().pattern("xx", "xx", "x_").addIngredient('x').any())
            .addShapedRecipe_(b -> b.result(Material.WOODEN_AXE , 1).pattern("ww", "sw", "s_").addIngredient('s').item(Material.STICK, false).addIngredient('w').item(Material.PLANKS     , true ))
            .addShapedRecipe_(b -> b.result(Material.STONE_AXE  , 1).pattern("cc", "sc", "s_").addIngredient('s').item(Material.STICK, false).addIngredient('c').item(Material.COBBLESTONE, true ))
            .addShapedRecipe_(b -> b.result(Material.IRON_AXE   , 1).pattern("ii", "si", "s_").addIngredient('s').item(Material.STICK, false).addIngredient('i').item(Material.IRON_INGOT , false))
            .addShapedRecipe_(b -> b.result(Material.GOLDEN_AXE , 1).pattern("gg", "sg", "s_").addIngredient('s').item(Material.STICK, false).addIngredient('g').item(Material.GOLD_INGOT , false))
            .addShapedRecipe_(b -> b.result(Material.DIAMOND_AXE, 1).pattern("dd", "sd", "s_").addIngredient('s').item(Material.STICK, false).addIngredient('d').item(Material.DIAMOND    , false))
            .buildAndAdd();

        // hoe
        this.grouped(this.craftingBuilder().shaped().pattern("xx", "_x", "_x").addIngredient('x').any())
            .addShapedRecipe_(b -> b.result(Material.WOODEN_HOE , 1).pattern("ww", "_s", "_s").addIngredient('s').item(Material.STICK, false).addIngredient('w').item(Material.PLANKS     , true ))
            .addShapedRecipe_(b -> b.result(Material.STONE_HOE  , 1).pattern("cc", "_s", "_s").addIngredient('s').item(Material.STICK, false).addIngredient('c').item(Material.COBBLESTONE, true ))
            .addShapedRecipe_(b -> b.result(Material.IRON_HOE   , 1).pattern("ii", "_s", "_s").addIngredient('s').item(Material.STICK, false).addIngredient('i').item(Material.IRON_INGOT , false))
            .addShapedRecipe_(b -> b.result(Material.GOLDEN_HOE , 1).pattern("gg", "_s", "_s").addIngredient('s').item(Material.STICK, false).addIngredient('g').item(Material.GOLD_INGOT , false))
            .addShapedRecipe_(b -> b.result(Material.DIAMOND_HOE, 1).pattern("dd", "_s", "_s").addIngredient('s').item(Material.STICK, false).addIngredient('d').item(Material.DIAMOND    , false))
            .buildAndAdd();
        this.grouped(this.craftingBuilder().shaped().pattern("xx", "x_", "x_").addIngredient('x').any())
            .addShapedRecipe_(b -> b.result(Material.WOODEN_HOE , 1).pattern("ww", "s_", "s_").addIngredient('s').item(Material.STICK, false).addIngredient('w').item(Material.PLANKS     , true ))
            .addShapedRecipe_(b -> b.result(Material.STONE_HOE  , 1).pattern("cc", "s_", "s_").addIngredient('s').item(Material.STICK, false).addIngredient('c').item(Material.COBBLESTONE, true ))
            .addShapedRecipe_(b -> b.result(Material.IRON_HOE   , 1).pattern("ii", "s_", "s_").addIngredient('s').item(Material.STICK, false).addIngredient('i').item(Material.IRON_INGOT , false))
            .addShapedRecipe_(b -> b.result(Material.GOLDEN_HOE , 1).pattern("gg", "s_", "s_").addIngredient('s').item(Material.STICK, false).addIngredient('g').item(Material.GOLD_INGOT , false))
            .addShapedRecipe_(b -> b.result(Material.DIAMOND_HOE, 1).pattern("dd", "s_", "s_").addIngredient('s').item(Material.STICK, false).addIngredient('d').item(Material.DIAMOND    , false))
            .buildAndAdd();

        // pickaxe
        this.grouped(this.craftingBuilder().shaped().pattern("xx", "_x", "_x").addIngredient('x').any())
            .addShapedRecipe_(b -> b.result(Material.WOODEN_PICKAXE , 1).pattern("www", "_s_", "_s_").addIngredient('s').item(Material.STICK, false).addIngredient('w').item(Material.PLANKS     , true ))
            .addShapedRecipe_(b -> b.result(Material.STONE_PICKAXE  , 1).pattern("ccc", "_s_", "_s_").addIngredient('s').item(Material.STICK, false).addIngredient('c').item(Material.COBBLESTONE, true ))
            .addShapedRecipe_(b -> b.result(Material.IRON_PICKAXE   , 1).pattern("iii", "_s_", "_s_").addIngredient('s').item(Material.STICK, false).addIngredient('i').item(Material.IRON_INGOT , false))
            .addShapedRecipe_(b -> b.result(Material.GOLDEN_PICKAXE , 1).pattern("ggg", "_s_", "_s_").addIngredient('s').item(Material.STICK, false).addIngredient('g').item(Material.GOLD_INGOT , false))
            .addShapedRecipe_(b -> b.result(Material.DIAMOND_PICKAXE, 1).pattern("ddd", "_s_", "_s_").addIngredient('s').item(Material.STICK, false).addIngredient('d').item(Material.DIAMOND    , false))
            .buildAndAdd();

        // helmet
        this.grouped(this.craftingBuilder().shaped().pattern("xxx", "x_x").addIngredient('x').any())
            .addShapedRecipe_(b -> b.result(Material.LEATHER_HELMET, 1).pattern("lll", "l_l").addIngredient('l').item(Material.LEATHER   , false))
            .addShapedRecipe_(b -> b.result(Material.IRON_HELMET   , 1).pattern("iii", "i_i").addIngredient('i').item(Material.IRON_INGOT, false))
            .addShapedRecipe_(b -> b.result(Material.GOLD_HELMET   , 1).pattern("ggg", "g_g").addIngredient('g').item(Material.GOLD_INGOT, false))
            .addShapedRecipe_(b -> b.result(Material.DIAMOND_HELMET, 1).pattern("ddd", "d_d").addIngredient('d').item(Material.DIAMOND   , false))
            .buildAndAdd();

        // chestplate
        this.grouped(this.craftingBuilder().shaped().pattern("x_x", "xxx", "xxx").addIngredient('x').any())
            .addShapedRecipe_(b -> b.result(Material.LEATHER_CHESTPLATE, 1).pattern("l_l", "lll", "lll").addIngredient('l').item(Material.LEATHER   , false))
            .addShapedRecipe_(b -> b.result(Material.IRON_CHESTPLATE   , 1).pattern("i_i", "iii", "iii").addIngredient('i').item(Material.IRON_INGOT, false))
            .addShapedRecipe_(b -> b.result(Material.GOLD_CHESTPLATE   , 1).pattern("g_g", "ggg", "ggg").addIngredient('g').item(Material.GOLD_INGOT, false))
            .addShapedRecipe_(b -> b.result(Material.DIAMOND_CHESTPLATE, 1).pattern("d_d", "ddd", "ddd").addIngredient('d').item(Material.DIAMOND   , false))
            .buildAndAdd();

        // leggings
        this.grouped(this.craftingBuilder().shaped().pattern("xxx", "x_x", "x_x").addIngredient('x').any())
            .addShapedRecipe_(b -> b.result(Material.LEATHER_LEGGINGS, 1).pattern("lll", "l_l", "l_l").addIngredient('l').item(Material.LEATHER   , false))
            .addShapedRecipe_(b -> b.result(Material.IRON_LEGGINGS   , 1).pattern("iii", "i_i", "i_i").addIngredient('i').item(Material.IRON_INGOT, false))
            .addShapedRecipe_(b -> b.result(Material.GOLD_LEGGINGS   , 1).pattern("ggg", "g_g", "g_g").addIngredient('g').item(Material.GOLD_INGOT, false))
            .addShapedRecipe_(b -> b.result(Material.DIAMOND_LEGGINGS, 1).pattern("ddd", "d_d", "d_d").addIngredient('d').item(Material.DIAMOND   , false))
            .buildAndAdd();

        // boots
        this.grouped(this.craftingBuilder().shaped().pattern("x_x", "x_x").addIngredient('x').any())
            .addShapedRecipe_(b -> b.result(Material.LEATHER_BOOTS, 1).pattern("l_l", "l_l").addIngredient('l').item(Material.LEATHER   , false))
            .addShapedRecipe_(b -> b.result(Material.IRON_BOOTS   , 1).pattern("i_i", "i_i").addIngredient('i').item(Material.IRON_INGOT, false))
            .addShapedRecipe_(b -> b.result(Material.GOLD_BOOTS   , 1).pattern("g_g", "g_g").addIngredient('g').item(Material.GOLD_INGOT, false))
            .addShapedRecipe_(b -> b.result(Material.DIAMOND_BOOTS, 1).pattern("d_d", "d_d").addIngredient('d').item(Material.DIAMOND   , false))
            .buildAndAdd();

        // 3 ingredients
        this.grouped(this.craftingBuilder().shapeless().addIngredient().any().addClone().addClone())
            .addShapelessRecipe_(b -> b.result(DyeMat.DYE_LIGHT_GRAY        , 3).addIngredient().item(Material.DYE           , false).addIngredient().item(DyeMat.DYE_BONE_MEAL   , false).addIngredient().item(DyeMat.DYE_BONE_MEAL, false))
            .addShapelessRecipe_(b -> b.result(DyeMat.DYE_MAGENTA           , 3).addIngredient().item(DyeMat.DYE_LAPIS_LAZULI, false).addIngredient().item(DyeMat.DYE_RED         , false).addIngredient().item(DyeMat.DYE_PINK     , false))
            .addShapelessRecipe_(b -> b.result(Material.MUSHROOM_STEW       , 1).addIngredient().item(Material.BROWN_MUSHROOM, false).addIngredient().item(Material.RED_MUSHROOM  , false).addIngredient().item(Material.BOWL       , false))
            .addShapelessRecipe_(b -> b.result(Material.PUMPKIN_PIE         , 1).addIngredient().item(Material.PUMPKIN       , false).addIngredient().item(Material.SUGAR         , false).addIngredient().item(Material.EGG        , false))
            .addShapelessRecipe_(b -> b.result(Material.FERMENTED_SPIDER_EYE, 1).addIngredient().item(Material.SPIDER_EYE    , false).addIngredient().item(Material.BROWN_MUSHROOM, false).addIngredient().item(Material.SUGAR      , false))
            .addShapelessRecipe_(b -> b.result(Material.WRITABLE_BOOK       , 1).addIngredient().item(Material.BOOK          , false).addIngredient().item(Material.DYE           , false).addIngredient().item(Material.FEATHER    , false))
            .addShapelessRecipe_(b -> b.result(Material.FIRE_CHARGE         , 3).addIngredient().item(Material.GUNPOWDER     , false).addIngredient().item(Material.BLAZE_POWDER  , false).addIngredient().item(Material.COAL       , true ))
        .buildAndAdd();

        // 2 ingredients
        this.grouped(this.craftingBuilder().shapeless().addIngredient().any().addClone())
            .addGroupedRecipe(g -> g.validator(this.craftingBuilder().shapeless().addIngredient().item(Material.WOOL, false).addIngredient().item(Material.DYE, true))
                .addShapelessRecipe_(b -> b.result(WoolMat.WOOL_WHITE     , 1).addIngredient().item(Material.WOOL, false).addIngredient().item(DyeMat.DYE_BONE_MEAL   , false))
                .addShapelessRecipe_(b -> b.result(WoolMat.WOOL_ORANGE    , 1).addIngredient().item(Material.WOOL, false).addIngredient().item(DyeMat.DYE_ORANGE      , false))
                .addShapelessRecipe_(b -> b.result(WoolMat.WOOL_MAGENTA   , 1).addIngredient().item(Material.WOOL, false).addIngredient().item(DyeMat.DYE_MAGENTA     , false))
                .addShapelessRecipe_(b -> b.result(WoolMat.WOOL_LIGHT_BLUE, 1).addIngredient().item(Material.WOOL, false).addIngredient().item(DyeMat.DYE_LIGHT_BLUE  , false))
                .addShapelessRecipe_(b -> b.result(WoolMat.WOOL_YELLOW    , 1).addIngredient().item(Material.WOOL, false).addIngredient().item(DyeMat.DYE_YELLOW      , false))
                .addShapelessRecipe_(b -> b.result(WoolMat.WOOL_LIME      , 1).addIngredient().item(Material.WOOL, false).addIngredient().item(DyeMat.DYE_LIME        , false))
                .addShapelessRecipe_(b -> b.result(WoolMat.WOOL_PINK      , 1).addIngredient().item(Material.WOOL, false).addIngredient().item(DyeMat.DYE_PINK        , false))
                .addShapelessRecipe_(b -> b.result(WoolMat.WOOL_GRAY      , 1).addIngredient().item(Material.WOOL, false).addIngredient().item(DyeMat.DYE_GRAY        , false))
                .addShapelessRecipe_(b -> b.result(WoolMat.WOOL_LIGHT_GRAY, 1).addIngredient().item(Material.WOOL, false).addIngredient().item(DyeMat.DYE_LIGHT_GRAY  , false))
                .addShapelessRecipe_(b -> b.result(WoolMat.WOOL_CYAN      , 1).addIngredient().item(Material.WOOL, false).addIngredient().item(DyeMat.DYE_CYAN        , false))
                .addShapelessRecipe_(b -> b.result(WoolMat.WOOL_PURPLE    , 1).addIngredient().item(Material.WOOL, false).addIngredient().item(DyeMat.DYE_PURPLE      , false))
                .addShapelessRecipe_(b -> b.result(WoolMat.WOOL_BLUE      , 1).addIngredient().item(Material.WOOL, false).addIngredient().item(DyeMat.DYE_LAPIS_LAZULI, false))
                .addShapelessRecipe_(b -> b.result(WoolMat.WOOL_BROWN     , 1).addIngredient().item(Material.WOOL, false).addIngredient().item(DyeMat.DYE_COCOA_BEANS , false))
                .addShapelessRecipe_(b -> b.result(WoolMat.WOOL_GREEN     , 1).addIngredient().item(Material.WOOL, false).addIngredient().item(DyeMat.DYE_GREEN       , false))
                .addShapelessRecipe_(b -> b.result(WoolMat.WOOL_RED       , 1).addIngredient().item(Material.WOOL, false).addIngredient().item(DyeMat.DYE_RED         , false))
                .addShapelessRecipe_(b -> b.result(WoolMat.WOOL_BLACK     , 1).addIngredient().item(Material.WOOL, false).addIngredient().item(DyeMat.DYE_INK_SAC     , false)))
            .addShapelessRecipe_(b -> b.result(Material.MAGMA_CREAM           , 1).addIngredient().item(Material.BLAZE_POWDER , false).addIngredient().item(Material.SLIMEBALL   , false))
            .addShapelessRecipe_(b -> b.result(StoneBrickMat.STONE_BRICK_MOSSY, 1).addIngredient().item(Material.STONE_BRICK  , false).addIngredient().item(Material.VINE        , false))
            .addShapelessRecipe_(b -> b.result(Material.MOSSY_COBBLESTONE     , 1).addIngredient().item(Material.COBBLESTONE  , false).addIngredient().item(Material.VINE        , false))
            .addShapelessRecipe_(b -> b.result(StoneMat.STONE_GRANITE         , 1).addIngredient().item(StoneMat.STONE_DIORITE, false).addIngredient().item(Material.QUARTZ      , false))
            .addShapelessRecipe_(b -> b.result(StoneMat.STONE_ANDESITE        , 2).addIngredient().item(StoneMat.STONE_DIORITE, false).addIngredient().item(Material.COBBLESTONE , false))
            .addShapelessRecipe_(b -> b.result(Material.FLINT_AND_STEEL       , 1).addIngredient().item(Material.IRON_INGOT   , false).addIngredient().item(Material.FLINT       , false))
            .addShapelessRecipe_(b -> b.result(Material.ENDER_EYE             , 1).addIngredient().item(Material.ENDER_PEARL  , false).addIngredient().item(Material.BLAZE_POWDER, false))

            .addShapelessRecipe_(b -> b.result(DyeMat.DYE_PINK, 2).addIngredient().item(DyeMat.DYE_RED, false).addIngredient().item(DyeMat.DYE_BONE_MEAL, false))
            .addShapelessRecipe_(b -> b.result(DyeMat.DYE_ORANGE, 2).addIngredient().item(DyeMat.DYE_RED, false).addIngredient().item(DyeMat.DYE_YELLOW, false))
            .addShapelessRecipe_(b -> b.result(DyeMat.DYE_LIME, 2).addIngredient().item(DyeMat.DYE_GREEN, false).addIngredient().item(DyeMat.DYE_BONE_MEAL, false))
            .addShapelessRecipe_(b -> b.result(DyeMat.DYE_GRAY, 2).addIngredient().item(Material.DYE, false).addIngredient().item(DyeMat.DYE_BONE_MEAL, false))
            .addShapelessRecipe_(b -> b.result(DyeMat.DYE_LIGHT_GRAY, 2).addIngredient().item(DyeMat.DYE_GRAY, false).addIngredient().item(DyeMat.DYE_BONE_MEAL, false))
            .addShapelessRecipe_(b -> b.result(DyeMat.DYE_LIGHT_BLUE, 2).addIngredient().item(DyeMat.DYE_LAPIS_LAZULI, false).addIngredient().item(DyeMat.DYE_BONE_MEAL, false))
            .addShapelessRecipe_(b -> b.result(DyeMat.DYE_CYAN, 2).addIngredient().item(DyeMat.DYE_LAPIS_LAZULI, false).addIngredient().item(DyeMat.DYE_GREEN, false))
            .addShapelessRecipe_(b -> b.result(DyeMat.DYE_PURPLE, 2).addIngredient().item(DyeMat.DYE_LAPIS_LAZULI, false).addIngredient().item(DyeMat.DYE_RED, false))
            .addShapelessRecipe_(b -> b.result(DyeMat.DYE_MAGENTA, 2).addIngredient().item(DyeMat.DYE_PURPLE, false).addIngredient().item(DyeMat.DYE_PINK, false))
            .buildAndAdd();

        // 1 ingredients
        this.grouped(this.craftingBuilder().shapeless().addIngredient().any().addClone())
            .addGroupedRecipe(g -> g.validator(this.craftingBuilder().shapeless().addIngredient().item(Material.LOG, true).item(Material.LOG2))
                .addShapelessRecipe_(b -> b.result(PlanksMat.PLANKS_OAK     , 4).addIngredient().item(LogMat.LOG_OAK     , false))
                .addShapelessRecipe_(b -> b.result(PlanksMat.PLANKS_SPRUCE  , 4).addIngredient().item(LogMat.LOG_SPRUCE  , false))
                .addShapelessRecipe_(b -> b.result(PlanksMat.PLANKS_BIRCH   , 4).addIngredient().item(LogMat.LOG_BIRCH   , false))
                .addShapelessRecipe_(b -> b.result(PlanksMat.PLANKS_JUNGLE  , 4).addIngredient().item(LogMat.LOG_JUNGLE  , false))
                .addShapelessRecipe_(b -> b.result(PlanksMat.PLANKS_ACACIA  , 4).addIngredient().item(LogMat.LOG_ACACIA  , false))
                .addShapelessRecipe_(b -> b.result(PlanksMat.PLANKS_DARK_OAK, 4).addIngredient().item(LogMat.LOG_DARK_OAK, false)))
            .addShapelessRecipe_(b -> b.result(Material.BLAZE_POWDER  , 2).addIngredient().item(Material.BLAZE_ROD     , false))
            .addShapelessRecipe_(b -> b.result(Material.STONE_BUTTON  , 1).addIngredient().item(Material.STONE         , false))
            .addShapelessRecipe_(b -> b.result(Material.WOODEN_BUTTON , 1).addIngredient().item(Material.PLANKS        , true ))
            .addShapelessRecipe_(b -> b.result(Material.GOLD_INGOT    , 9).addIngredient().item(Material.GOLD_BLOCK    , true ))
            .addShapelessRecipe_(b -> b.result(Material.IRON_INGOT    , 9).addIngredient().item(Material.IRON_BLOCK    , true ))
            .addShapelessRecipe_(b -> b.result(Material.DIAMOND       , 9).addIngredient().item(Material.DIAMOND_BLOCK , true ))
            .addShapelessRecipe_(b -> b.result(Material.EMERALD       , 9).addIngredient().item(Material.EMERALD_BLOCK , true ))
            .addShapelessRecipe_(b -> b.result(DyeMat.DYE_LAPIS_LAZULI, 9).addIngredient().item(Material.LAPIS_BLOCK   , true ))
            .addShapelessRecipe_(b -> b.result(Material.REDSTONE      , 9).addIngredient().item(Material.REDSTONE_BLOCK, true ))
            .addShapelessRecipe_(b -> b.result(Material.COAL          , 9).addIngredient().item(Material.COAL_BLOCK    , true ))
            .addShapelessRecipe_(b -> b.result(Material.WHEAT         , 9).addIngredient().item(Material.HAY_BLOCK     , true ))
            .addShapelessRecipe_(b -> b.result(Material.SLIMEBALL     , 9).addIngredient().item(Material.SLIME_BLOCK   , true ))
            .addShapelessRecipe_(b -> b.result(Material.GOLD_NUGGET   , 9).addIngredient().item(Material.GOLD_INGOT    , false))
            .addShapelessRecipe_(b -> b.result(Material.MELON_SEEDS   , 1).addIngredient().item(Material.MELON         , false))
            .addShapelessRecipe_(b -> b.result(Material.PUMPKIN_SEEDS , 4).addIngredient().item(Material.PUMPKIN       , true ))
            .addShapelessRecipe_(b -> b.result(Material.SUGAR         , 1).addIngredient().item(Material.REEDS         , false))
            .addShapelessRecipe_(b -> b.result(DyeMat.DYE_BONE_MEAL   , 3).addIngredient().item(Material.BONE          , false))
            .addGroupedRecipe(g -> g.validator(this.craftingBuilder().shapeless().addIngredient().any().simpleValidator(i -> i.getMaterial() instanceof FlowerMat))
                .addShapelessRecipe_(b -> b.result(DyeMat.DYE_RED       , 1).addIngredient().item(Material.FLOWERS                         , false))
                .addShapelessRecipe_(b -> b.result(DyeMat.DYE_LIGHT_BLUE, 1).addIngredient().item(FlowersMat.FLOWERS_BLUE_ORCHID           , false))
                .addShapelessRecipe_(b -> b.result(DyeMat.DYE_MAGENTA   , 1).addIngredient().item(FlowersMat.FLOWERS_ALLIUM                , false))
                .addShapelessRecipe_(b -> b.result(DyeMat.DYE_LIGHT_GRAY, 1).addIngredient().item(FlowersMat.FLOWERS_AZURE_BLUET           , false))
                .addShapelessRecipe_(b -> b.result(DyeMat.DYE_RED       , 1).addIngredient().item(FlowersMat.FLOWERS_RED_TULIP             , false))
                .addShapelessRecipe_(b -> b.result(DyeMat.DYE_ORANGE    , 1).addIngredient().item(FlowersMat.FLOWERS_ORANGE_TULIP          , false))
                .addShapelessRecipe_(b -> b.result(DyeMat.DYE_LIGHT_GRAY, 1).addIngredient().item(FlowersMat.FLOWERS_WHITE_TULIP           , false))
                .addShapelessRecipe_(b -> b.result(DyeMat.DYE_PINK      , 1).addIngredient().item(FlowersMat.FLOWERS_PINK_TULIP            , false))
                .addShapelessRecipe_(b -> b.result(DyeMat.DYE_LIGHT_GRAY, 1).addIngredient().item(FlowersMat.FLOWERS_OXEYE_DAISY           , false))
                .addShapelessRecipe_(b -> b.result(DyeMat.DYE_YELLOW    , 2).addIngredient().item(Material.DOUBLE_FLOWERS                  , false))
                .addShapelessRecipe_(b -> b.result(DyeMat.DYE_MAGENTA   , 2).addIngredient().item(DoubleFlowersMat.DOUBLE_FLOWERS_LILAC    , false))
                .addShapelessRecipe_(b -> b.result(DyeMat.DYE_RED       , 2).addIngredient().item(DoubleFlowersMat.DOUBLE_FLOWERS_ROSE_BUSH, false))
                .addShapelessRecipe_(b -> b.result(DyeMat.DYE_PINK      , 2).addIngredient().item(DoubleFlowersMat.DOUBLE_FLOWERS_PEONY    , false))
                .addShapelessRecipe_(b -> b.result(DyeMat.DYE_YELLOW    , 1).addIngredient().item(Material.DANDELION                       , false)))
            .buildAndAdd();
        // @formatter:on

        // @formatter:off
        this.repair(Material.WOODEN_PICKAXE, Material.WOODEN_AXE, Material.WOODEN_SHOVEL, Material.WOODEN_HOE, Material.WOODEN_SWORD,
                Material.STONE_PICKAXE, Material.STONE_AXE, Material.STONE_SHOVEL, Material.STONE_HOE, Material.STONE_SWORD,
                Material.IRON_PICKAXE, Material.IRON_AXE, Material.IRON_SHOVEL, Material.IRON_HOE, Material.IRON_SWORD,
                Material.GOLDEN_PICKAXE, Material.GOLDEN_AXE, Material.GOLDEN_SHOVEL, Material.GOLDEN_HOE, Material.GOLDEN_SWORD,
                Material.DIAMOND_PICKAXE, Material.DIAMOND_AXE, Material.DIAMOND_SHOVEL, Material.DIAMOND_HOE, Material.DIAMOND_SWORD,
                Material.LEATHER_HELMET, Material.LEATHER_CHESTPLATE, Material.LEATHER_LEGGINGS, Material.LEATHER_BOOTS,
                Material.IRON_HELMET, Material.IRON_CHESTPLATE, Material.IRON_LEGGINGS, Material.IRON_BOOTS,
                Material.CHAINMAIL_HELMET, Material.CHAINMAIL_CHESTPLATE, Material.CHAINMAIL_LEGGINGS, Material.CHAINMAIL_BOOTS,
                Material.GOLD_HELMET, Material.GOLD_CHESTPLATE, Material.GOLD_LEGGINGS, Material.GOLD_BOOTS, Material.DIAMOND_HELMET,
                Material.DIAMOND_CHESTPLATE, Material.DIAMOND_LEGGINGS, Material.DIAMOND_BOOTS);
        // @formatter:on
        // no groups

        this.shapeless(DyeMat.DYE_MAGENTA, 4).addIngredient().item(DyeMat.DYE_LAPIS_LAZULI, false).addIngredient().item(DyeMat.DYE_RED, false).addIngredient().item(DyeMat.DYE_RED, false).addIngredient().item(DyeMat.DYE_BONE_MEAL, false).build().buildAndAdd();
        this.shapeless(Material.BOOK, 1).addIngredient().item(Material.PAPER, false).addIngredient().item(Material.PAPER, false).addIngredient().item(Material.PAPER, false).addIngredient().item(Material.LEATHER, false).build().buildAndAdd();
        this.shapeless(Material.BOOK, 1).addIngredient().item(Material.PAPER, false).addIngredient().item(Material.PAPER, false).addIngredient().item(Material.PAPER, false).addIngredient().item(Material.LEATHER, false).build().buildAndAdd();

        this.shaped(Material.SHEARS, 1, "_i", "i_").addIngredient('i').item(Material.IRON_INGOT, false).build().buildAndAdd();
        this.shaped(Material.MINECART, 1, "i_i", "iii").addIngredient('i').item(Material.IRON_INGOT, false).build().buildAndAdd();
        this.shaped(Material.LADDER, 3, "s_s", "sss", "s_s").addIngredient('s').item(Material.STICK, false).build().buildAndAdd();
        this.shaped(Material.CHEST, 1, "www", "w_w", "www").addIngredient('w').item(Material.PLANKS, true).build().buildAndAdd();
        this.shaped(Material.FURNACE, 1, "ccc", "c_c", "ccc").addIngredient('c').item(Material.COBBLESTONE, true).build().buildAndAdd();
        this.shaped(Material.CAULDRON, 1, "i_i", "i_i", "iii").addIngredient('i').item(Material.IRON_INGOT, false).build().buildAndAdd();
        this.shaped(Material.FISHING_ROD, 1, "__s", "_sS", "s_S").addIngredient('s').item(Material.STICK, false).addIngredient('S').item(Material.STRING, false).build().buildAndAdd();
        this.shaped(Material.BREWING_STAND, 1, "_b_", "ccc").addIngredient('b').item(Material.BLAZE_ROD, false).addIngredient('c').item(Material.COBBLESTONE, true).build().buildAndAdd();
        this.shaped(Material.CARROT_ON_A_STICK, 1, "f_", "_c").addIngredient('f').item(Material.FISHING_ROD, false).addIngredient('c').item(Material.CARROT, false).build().buildAndAdd();
        this.shaped(Material.REDSTONE_COMPARATOR_ITEM, 1, "_r_", "rqr", "sss").addIngredient('s').item(Material.STONE, false).addIngredient('r').item(RedstoneTorchOnMat.REDSTONE_TORCH_ON_ITEM, true).addIngredient('q').item(Material.QUARTZ, false).build().buildAndAdd();
        this.shaped(Material.DROPPER, 1, "ccc", "c_c", "crc").addIngredient('r').item(Material.REDSTONE, false).addIngredient('c').item(Material.COBBLESTONE, true).build().buildAndAdd();
        this.shaped(Material.ENCHANTING_TABLE, 1, "_b_", "dod", "ooo").addIngredient('b').item(Material.BOOK, false).addIngredient('d').item(Material.DIAMOND, false).addIngredient('o').item(Material.OBSIDIAN, true).build().buildAndAdd();
        this.shaped(Material.ANVIL, 1, "iii", "_I_", "III").addIngredient('i').item(Material.IRON_BLOCK, true).addIngredient('I').item(Material.IRON_INGOT, false).build().buildAndAdd();
        this.shaped(Material.HOPPER, 1, "i_i", "ici", "_i_").addIngredient('i').item(Material.IRON_INGOT, false).addIngredient('c').item(Material.CHEST, true).build().buildAndAdd();
        this.shaped(Material.ARMOR_STAND, 1, "sss", "_s_", "sSs").addIngredient('s').item(Material.STICK, false).addIngredient('S').item(Material.STONE_SLAB, false).build().buildAndAdd();
        this.shaped(Material.LEAD, 2, "ss_", "sS_", "__s").addIngredient('s').item(Material.STRING, false).addIngredient('S').item(Material.SLIMEBALL, false).build().buildAndAdd();
        this.shaped(Material.RAIL, 16, "i_i", "isi", "i_i").addIngredient('i').item(Material.IRON_INGOT, false).addIngredient('s').item(Material.STICK, false).build().buildAndAdd();
        this.shaped(Material.POWERED_RAIL, 6, "g_g", "gsg", "grg").addIngredient('s').item(Material.STICK, false).addIngredient('g').item(Material.GOLD_INGOT, false).addIngredient('r').item(Material.REDSTONE, false).build().buildAndAdd();
        this.shaped(Material.DETECTOR_RAIL, 6, "i_i", "isi", "iri").addIngredient('i').item(Material.IRON_INGOT, false).addIngredient('s').item(Material.STONE_PRESSURE_PLATE, true).addIngredient('r').item(Material.REDSTONE, false).build().buildAndAdd();
        this.shaped(Material.BOW, 1, "_sS", "s_S", "_sS").addIngredient('s').item(Material.STICK, false).addIngredient('S').item(Material.STRING, false).build().buildAndAdd();

        this.grouped(this.craftingBuilder().shapeless().addIngredient().item(Material.DYE, true).repeatable().addIngredient().item(Material.LEATHER_HELMET).any().simpleValidator(i -> (i.getMaterial() instanceof ArmorMat) && ((ArmorMat) i.getMaterial()).getArmorMaterial().equals(ArmorMaterial.LEATHER))).addRecipe(this.color(Material.LEATHER_HELMET)).addRecipe(this.color(Material.LEATHER_CHESTPLATE)).addRecipe(this.color(Material.LEATHER_LEGGINGS)).addRecipe(this.color(Material.LEATHER_BOOTS)).buildAndAdd();
        this.bookCopy();
        this.mapZoom();
        this.mapCopy();

        this.shapeless(Material.FIREWORKS, 0).addIngredient().item(Material.GUNPOWDER, false).addIngredient().item(Material.PAPER, false).build().buildAndAdd(); // TODO
        this.shapeless(Material.BANNER, 0).addIngredient().item(Material.BANNER, false).build().buildAndAdd(); // TODO
        this.shapeless(Material.BANNER, 0).addIngredient().item(DyeMat.DYE_PURPLE, false).build().buildAndAdd(); // TODO
    }

    private void mapZoom()
    {
        this.shaped(Material.FILLED_MAP, 1, "ppp", "pmp", "ppp").result(grid -> {
            ItemStack map = null;
            ItemStack[] items = grid.getItems();
            if (items.length == 9)
            {
                map = items[4].clone();
            }
            else
            {
                for (final ItemStack item : items)
                {
                    if (item.getMaterial().isThisSameID(Material.FILLED_MAP))
                    {
                        map = item.clone();
                        break;
                    }
                }
            }
            assert map != null;
            final MapMeta meta = (MapMeta) map.getItemMeta();
            meta.setScaling((meta.getScaling() + 1));
            return map;
        }).addIngredient('m').item(Material.FILLED_MAP, true).simpleValidator(item -> ((MapMeta) item.getItemMeta()).getScaling() < 4).addIngredient('p').item(Material.PAPER, false).build().buildAndAdd();
    }

    private void mapCopy()
    {
        final ShapelessCraftingRecipeBuilder builder = this.shapeless(Material.FILLED_MAP, 1).addIngredient().item(Material.FILLED_MAP, true).addIngredient().item(Material.MAP, true).repeatable((result, items) -> ItemBuilder.start(result).amount(items.size() + 1).build()).build();
        builder.result(grid -> {
            for (final ItemStack itemStack : grid.getItems())
            {
                if (itemStack == null)
                {
                    continue;
                }
                if (itemStack.getMaterial().simpleEquals(Material.FILLED_MAP))
                {
                    return itemStack.clone();
                }
            }
            throw new AssertionError("Copy map crafting grid without map to copy.");
        });
        builder.buildAndAdd();
    }

    private void bookCopy()
    {
        final ShapelessCraftingRecipeBuilder builder = this.shapeless(Material.WRITTEN_BOOK, 1).addIngredient().item(Material.WRITTEN_BOOK, true).replacement(Material.WRITTEN_BOOK, grid -> {
            for (final ItemStack itemStack : grid.getItems())
            {
                if ((itemStack != null) && itemStack.getMaterial().simpleEquals(Material.WRITTEN_BOOK))
                {
                    return itemStack;
                }
            }
            throw new AssertionError("Copy book crafting grid without book to copy.");
        }).simpleValidator(item -> ((BookMeta) item.getItemMeta()).getGeneration() < GenerationEnum.COPY_OF_COPY.getGeneration()).build();
        builder.addIngredient().item(Material.WRITABLE_BOOK, true).repeatable((result, items) -> ItemBuilder.start(result).amount(items.size()).build()).build();
        builder.result(grid -> {
            for (final ItemStack itemStack : grid.getItems())
            {
                if (itemStack == null)
                {
                    continue;
                }
                if (itemStack.getMaterial().simpleEquals(Material.WRITTEN_BOOK))
                {
                    final ItemStack it = itemStack.clone();
                    final BookMeta meta = (BookMeta) it.getItemMeta();
                    meta.setGeneration(meta.getGeneration() + 1);
                    return it;
                }
            }
            throw new AssertionError("Copy book crafting grid without book to copy.");
        });
        builder.buildAndAdd();
    }

    private ShapelessCraftingRecipe color(final ArmorMat mat)
    {
        final ShapelessCraftingRecipeItemBuilder builder = this.shapeless(mat, 1).addIngredient().item(mat, true).addIngredient().item(Material.DYE, true);
        return builder.repeatableAdv((result, grid) -> {
            int colors = 0;
            int tr = 0, tg = 0, tb = 0;
            double totalMax = 0;
            int dur = 0;
            final List<ItemStack> items = grid.getItemsList();
            for (final ItemStack item : items)
            {
                if (item.getMaterial().isThisSameID(result.getMaterial()))
                {
                    dur = item.getMaterial().getType();
                    final LeatherArmorMeta meta = ((LeatherArmorMeta) item.getItemMeta());
                    final Color color = meta.getColor();
                    if (color != null)
                    {
                        final int r = color.getRed();
                        final int g = color.getGreen();
                        final int b = color.getBlue();
                        tr += r;
                        tg += g;
                        tb += b;
                        totalMax += Math.max(r, Math.max(g, b));
                        colors++;
                    }
                    continue;
                }
                final Color color = ((ColorableMat) item.getMaterial()).getColor().getColor();
                final int r = color.getRed();
                final int g = color.getGreen();
                final int b = color.getBlue();
                tr += r;
                tg += g;
                tb += b;
                totalMax += Math.max(r, Math.max(g, b));
                colors++;
            }
            final double avgRed = tr / colors;
            final double avgGreen = tg / colors;
            final double avgBlue = tb / colors;
            final double gainFactor = (totalMax / colors) / Math.max(avgRed, Math.max(avgGreen, avgBlue));
            final Color resultColor = Color.fromRGB((int) (avgRed * gainFactor), (int) (avgGreen * gainFactor), (int) (avgBlue * gainFactor));

            final LeatherArmorMeta meta = ((LeatherArmorMeta) result.getItemMeta());
            meta.setColor(resultColor);
            result.setMaterial(result.getMaterial().getType(dur));
            return result;
        }).build().build();
    }

    @SuppressWarnings("unchecked")
    private <T extends ItemMaterialData & BreakableItemMat> void repair(final T... mat)
    {
        final GroupCraftingRecipeBuilder builder = this.grouped(this.shapeless_(Material.WOODEN_PICKAXE, 1).addIngredient().any().simpleValidator(i -> i.getMaterial() instanceof BreakableItemMat).addClone());
        for (final T t : mat)
        {
            builder.addRecipe(this.repair(t).build());
        }
        builder.buildAndAdd();
    }

    @SuppressWarnings("unchecked")
    private <T extends ItemMaterialData & BreakableItemMat> ShapelessCraftingRecipeBuilder repair(final T mat)
    {
        return this.shapeless(mat, 1).addIngredient().item(mat, true).addIngredient().item(mat, true).build().result(grid -> {
            final List<ItemStack> items = grid.getItemsList();
            if (items.size() != 2)
            {
                throw new IllegalStateException("Unexcepted items size in repair recipe! " + items);
            }
            final Material temp = items.get(0).getMaterial();
            final Material temp2 = items.get(1).getMaterial();
            if (! (temp instanceof ItemMaterialData) || ! (temp instanceof BreakableItemMat) || ! (temp2 instanceof ItemMaterialData) || ! (temp2 instanceof BreakableItemMat))
            {
                throw new IllegalStateException("Repair recipe with unrepairable items.");
            }
            final T itemMat = (T) temp;
            final T itemMat2 = (T) temp2;
            // min( Item A uses + Item B uses + floor(Max uses / 20), Max uses)
            // TODO maybe add way to configure that too (at least from API side without overriding whole recipe)
            final int newDurability = Math.min(itemMat.getLeftUses() + itemMat2.getLeftUses() + (itemMat.getBaseDurability() / 20), itemMat.getBaseDurability());
            return new BaseItemStack((Material) itemMat.setLeftUses(newDurability), 1);
        });
    }

    private ShapedCraftingRecipeBuilder shaped(final Material resultMat, final int resultAmount, final String... pattern)
    {
        return this.craftingBuilder().priority(getNextPriority()).vanilla(true).result(resultMat, resultAmount).shaped().pattern(pattern);
    }

    private ShapedCraftingRecipeBuilder shaped_(final Material resultMat, final int resultAmount, final String... pattern)
    {
        return this.craftingBuilder().vanilla(true).result(resultMat, resultAmount).shaped().pattern(pattern);
    }

//    private GroupCraftingRecipeBuilder grouped(final Material resultMat, final int resultAmount, final Predicate<GridInventory> predicate)
//    {
//        return this.craftingBuilder().priority(getNextPriority()).vanilla(true).result(resultMat, resultAmount).grouped().validator(predicate);
//    }
//
//    private GroupCraftingRecipeBuilder grouped(final CraftingRecipe predicate)
//    {
//        if (predicate.getResult().isEmpty())
//        {
//            return this.craftingBuilder().priority(getNextPriority()).vanilla(true).result((ItemStack) null).grouped().validator(predicate);
//        }
//        final ItemStack is = predicate.getResult().get(0).clone();
//        return this.craftingBuilder().priority(getNextPriority()).vanilla(true).result(is).grouped().validator(predicate);
//    }
//
//    private GroupCraftingRecipeBuilder grouped(final CraftingRecipeBuilder predicate)
//    {
//        return this.grouped(predicate.build());
//    }
//
//    private GroupCraftingRecipeBuilder grouped(final CraftingRecipeItemBuilder<?, ?> predicate)
//    {
//        return this.grouped(predicate.build().build());
//    }

    private GroupCraftingRecipeBuilder grouped(final Predicate<GridInventory> predicate)
    {
        return this.craftingBuilder().priority(getNextPriority()).vanilla(true).grouped().validator(predicate);
    }

    private GroupCraftingRecipeBuilder grouped(final CraftingRecipe predicate)
    {
        return this.craftingBuilder().priority(getNextPriority()).vanilla(true).result((ItemStack) null).grouped().validator(predicate);
    }

    private GroupCraftingRecipeBuilder grouped(final CraftingRecipeBuilder predicate)
    {
        return this.grouped(predicate.build());
    }

    private GroupCraftingRecipeBuilder grouped(final CraftingRecipeItemBuilder<?, ?> predicate)
    {
        return this.grouped(predicate.build().build());
    }

    private ShapelessCraftingRecipeBuilder shapeless(final Material resultMat, final int resultAmount)
    {
        return this.craftingBuilder().priority(getNextPriority()).vanilla(true).result(resultMat, resultAmount).shapeless();
    }

    private ShapelessCraftingRecipeBuilder shapeless_(final Material resultMat, final int resultAmount)
    {
        return this.craftingBuilder().vanilla(true).result(resultMat, resultAmount).shapeless();
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("recipes", this.recipes).toString();
    }

    private static class CraftingRecipeIterator implements Iterator<CraftingRecipe>
    {
        private final Iterator<? extends CraftingRecipe> it;
        private       CraftingRecipeIterator             it2;
        private int nextWithoutRemove = - 1;

        private CraftingRecipeIterator(final Iterator<? extends CraftingRecipe> it)
        {
            this.it = it;
        }

        @Override
        public boolean hasNext()
        {
            if (this.it2 != null)
            {
                if (this.it2.hasNext())
                {
                    return true;
                }
                this.nextWithoutRemove = - 1;
                this.it2 = null;
            }
            return this.it.hasNext();
        }

        @Override
        public CraftingRecipe next()
        {
            if (this.it2 != null)
            {
                if (this.it2.hasNext())
                {
                    this.nextWithoutRemove++;
                    return this.it2.next();
                }
                this.nextWithoutRemove = - 1;
                this.it2 = null;
            }
            final CraftingRecipe recipe = this.it.next();
            if (recipe instanceof CraftingRecipeGroup)
            {
                this.it2 = new CraftingRecipeIterator(((CraftingRecipeGroup) recipe).recipeIterator());
                return this.next();
            }
            return recipe;
        }

        @Override
        public void remove()
        {
            if (this.it2 != null)
            {
                this.it2.remove();
                final boolean rem = this.nextWithoutRemove == 0;
                if (rem)
                {
                    this.nextWithoutRemove = - 1;
                }
                if (! this.it2.hasNext())
                {
                    this.it2 = null;
                    this.nextWithoutRemove = - 1;
                    if (rem)
                    {
                        this.it.remove();
                    }
                }
                return;
            }
            this.it.remove();
        }

        @Override
        public String toString()
        {
            return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("it", this.it).toString();
        }
    }
}
