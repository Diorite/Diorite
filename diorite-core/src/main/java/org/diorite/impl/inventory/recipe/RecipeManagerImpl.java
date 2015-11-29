package org.diorite.impl.inventory.recipe;

import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.chat.ChatColor;
import org.diorite.inventory.GridInventory;
import org.diorite.inventory.item.BaseItemStack;
import org.diorite.inventory.item.ItemStack;
import org.diorite.inventory.item.meta.BookMeta;
import org.diorite.inventory.item.meta.BookMeta.GenerationEnum;
import org.diorite.inventory.item.meta.LeatherArmorMeta;
import org.diorite.inventory.recipe.RecipeBuilder;
import org.diorite.inventory.recipe.RecipeBuilder.ShapedRecipeBuilder;
import org.diorite.inventory.recipe.RecipeBuilder.ShapelessRecipeBuilder;
import org.diorite.inventory.recipe.craft.Recipe;
import org.diorite.inventory.recipe.craft.RecipeCheckResult;
import org.diorite.material.BreakableItemMat;
import org.diorite.material.ColorableMat;
import org.diorite.material.ItemMaterialData;
import org.diorite.material.Material;
import org.diorite.material.blocks.CarpetMat;
import org.diorite.material.blocks.CobblestoneWallMat;
import org.diorite.material.blocks.DirtMat;
import org.diorite.material.blocks.DoubleFlowersMat;
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
import org.diorite.material.items.CoalMat;
import org.diorite.material.items.DyeMat;
import org.diorite.material.items.GoldenAppleMat;
import org.diorite.utils.Color;

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

    private static volatile int i = 0;

    private static long getPriority()
    {
        return Recipe.DIORITE_START + ((2 * (i++)) * Recipe.DIORITE_SPACE);
    }

    @Override
    public void addDefaultRecipes()
    {
//        this.builder().priority(getPriority()).shapeless().addIngredient().item(Material.STONE, true).build().result(Material.STONE_BUTTON).buildAndAdd();
//        this.builder().priority(getPriority()).shaped().pattern("s ", "S ").addIngredient('s').item(Material.STONE).addIngredient('S').item(Material.STICK).build().result(Material.STONE_SWORD).buildAndAdd();
//        this.builder().priority(getPriority()).shaped().pattern("s").addIngredient('s').item(Material.STICK).build().result(WoolMat.WOOL_BLUE).buildAndAdd();
//        this.builder().priority(getPriority()).shapeless().addIngredient().item(Material.STICK).addIngredient().item(Material.STONE).build().result(Material.STONE_AXE).buildAndAdd();
//        this.builder().priority(getPriority()).shapeless().addIngredient().item(DyeMat.DYE_LAPIS_LAZULI).addIngredient().item(WoolMat.WOOL_WHITE, false).build().result(WoolMat.WOOL_BLUE).buildAndAdd();
        this.builder().priority(getPriority()).shapeless().addIngredient().item(Material.APPLE, 2, false).replacement(Material.DIRT, 16).simpleValidator(item -> ChatColor.translateAlternateColorCodesInString("&3Diorite").equals(item.getItemMeta().getDisplayName())).build().result(Material.GOLDEN_APPLE).buildAndAdd();

        this.shaped(Material.WOODEN_PICKAXE, 1, "www", "_s_", "_s_").addIngredient('s').item(Material.STICK, false).addIngredient('w').item(Material.PLANKS, true).build().buildAndAdd();
        this.shaped(Material.STONE_PICKAXE, 1, "ccc", "_s_", "_s_").addIngredient('s').item(Material.STICK, false).addIngredient('c').item(Material.COBBLESTONE, true).build().buildAndAdd();
        this.shaped(Material.IRON_PICKAXE, 1, "iii", "_s_", "_s_").addIngredient('i').item(Material.IRON_INGOT, false).addIngredient('s').item(Material.STICK, false).build().buildAndAdd();
        this.shaped(Material.DIAMOND_PICKAXE, 1, "ddd", "_s_", "_s_").addIngredient('s').item(Material.STICK, false).addIngredient('d').item(Material.DIAMOND, false).build().buildAndAdd();
        this.shaped(Material.GOLDEN_PICKAXE, 1, "ggg", "_s_", "_s_").addIngredient('s').item(Material.STICK, false).addIngredient('g').item(Material.GOLD_INGOT, false).build().buildAndAdd();
        this.shaped(Material.BOW, 1, "_sS", "s_S", "_sS").addIngredient('s').item(Material.STICK, false).addIngredient('S').item(Material.STRING, false).build().buildAndAdd();
        this.shaped(Material.GOLD_BLOCK, 1, "ggg", "ggg", "ggg").addIngredient('g').item(Material.GOLD_INGOT, false).build().buildAndAdd();
        this.shaped(Material.IRON_BLOCK, 1, "iii", "iii", "iii").addIngredient('i').item(Material.IRON_INGOT, false).build().buildAndAdd();
        this.shaped(Material.DIAMOND_BLOCK, 1, "ddd", "ddd", "ddd").addIngredient('d').item(Material.DIAMOND, false).build().buildAndAdd();
        this.shaped(Material.EMERALD_BLOCK, 1, "eee", "eee", "eee").addIngredient('e').item(Material.EMERALD, false).build().buildAndAdd();
        this.shaped(Material.LAPIS_BLOCK, 1, "iii", "iii", "iii").addIngredient('i').item(DyeMat.DYE_LAPIS_LAZULI, false).build().buildAndAdd();
        this.shaped(Material.REDSTONE_BLOCK, 1, "rrr", "rrr", "rrr").addIngredient('r').item(Material.REDSTONE, false).build().buildAndAdd();
        this.shaped(Material.COAL_BLOCK, 1, "ccc", "ccc", "ccc").addIngredient('c').item(Material.COAL, false).build().buildAndAdd();
        this.shaped(Material.HAY_BLOCK, 1, "www", "www", "www").addIngredient('w').item(Material.WHEAT, false).build().buildAndAdd();
        this.shaped(Material.SLIME_BLOCK, 1, "sss", "sss", "sss").addIngredient('s').item(Material.SLIMEBALL, false).build().buildAndAdd();
        this.shaped(Material.GOLD_INGOT, 1, "ggg", "ggg", "ggg").addIngredient('g').item(Material.GOLD_NUGGET, false).build().buildAndAdd();
        this.shaped(Material.RABBIT_STEW, 1, "_c_", "CbB", "_a_").addIngredient('b').item(Material.BAKED_POTATO, false).addIngredient('a').item(Material.BOWL, false).addIngredient('C').item(Material.CARROT, false).addIngredient('B').item(Material.BROWN_MUSHROOM, true).addIngredient('c').item(Material.COOKED_RABBIT, false).build().buildAndAdd();
        this.shaped(Material.RABBIT_STEW, 1, "_c_", "Cbr", "_B_").addIngredient('b').item(Material.BAKED_POTATO, false).addIngredient('C').item(Material.CARROT, false).addIngredient('r').item(Material.RED_MUSHROOM, true).addIngredient('B').item(Material.BOWL, false).addIngredient('c').item(Material.COOKED_RABBIT, false).build().buildAndAdd();
        this.shaped(Material.MELON_BLOCK, 1, "mmm", "mmm", "mmm").addIngredient('m').item(Material.MELON, false).build().buildAndAdd();
        this.shaped(Material.CHEST, 1, "www", "w_w", "www").addIngredient('w').item(Material.PLANKS, true).build().buildAndAdd();
        this.shaped(Material.ENDER_CHEST, 1, "ooo", "oeo", "ooo").addIngredient('e').item(Material.ENDER_EYE, false).addIngredient('o').item(Material.OBSIDIAN, true).build().buildAndAdd();
        this.shaped(Material.FURNACE, 1, "ccc", "c_c", "ccc").addIngredient('c').item(Material.COBBLESTONE, true).build().buildAndAdd();
        this.shaped(Material.REDSTONE_LAMP_OFF, 1, "_r_", "rgr", "_r_").addIngredient('g').item(Material.GLOWSTONE, true).addIngredient('r').item(Material.REDSTONE, false).build().buildAndAdd();
        this.shaped(Material.BEACON, 1, "ggg", "gng", "ooo").addIngredient('n').item(Material.NETHER_STAR, false).addIngredient('g').item(Material.GLASS, true).addIngredient('o').item(Material.OBSIDIAN, true).build().buildAndAdd();
        this.shaped(PrismarineMat.PRISMARINE_BRICKS, 1, "ppp", "ppp", "ppp").addIngredient('p').item(Material.PRISMARINE_SHARD, false).build().buildAndAdd();
        this.shaped(PrismarineMat.PRISMARINE_DARK, 1, "ppp", "pip", "ppp").addIngredient('i').item(Material.DYE, false).addIngredient('p').item(Material.PRISMARINE_SHARD, false).build().buildAndAdd();
        this.shaped(Material.SEA_LANTERN, 1, "pPp", "PPP", "pPp").addIngredient('P').item(Material.PRISMARINE_CRYSTALS, false).addIngredient('p').item(Material.PRISMARINE_SHARD, false).build().buildAndAdd();
        this.shaped(Material.LEATHER_CHESTPLATE, 1, "l_l", "lll", "lll").addIngredient('l').item(Material.LEATHER, false).build().buildAndAdd();
        this.shaped(Material.LEATHER_LEGGINGS, 1, "lll", "l_l", "l_l").addIngredient('l').item(Material.LEATHER, false).build().buildAndAdd();
        this.shaped(Material.IRON_CHESTPLATE, 1, "i_i", "iii", "iii").addIngredient('i').item(Material.IRON_INGOT, false).build().buildAndAdd();
        this.shaped(Material.IRON_LEGGINGS, 1, "iii", "i_i", "i_i").addIngredient('i').item(Material.IRON_INGOT, false).build().buildAndAdd();
        this.shaped(Material.DIAMOND_CHESTPLATE, 1, "d_d", "ddd", "ddd").addIngredient('d').item(Material.DIAMOND, false).build().buildAndAdd();
        this.shaped(Material.DIAMOND_LEGGINGS, 1, "ddd", "d_d", "d_d").addIngredient('d').item(Material.DIAMOND, false).build().buildAndAdd();
        this.shaped(Material.GOLD_CHESTPLATE, 1, "g_g", "ggg", "ggg").addIngredient('g').item(Material.GOLD_INGOT, false).build().buildAndAdd();
        this.shaped(Material.GOLD_LEGGINGS, 1, "ggg", "g_g", "g_g").addIngredient('g').item(Material.GOLD_INGOT, false).build().buildAndAdd();
        this.shaped(StainedHardenedClayMat.STAINED_HARDENED_CLAY_BLACK, 8, "hhh", "hih", "hhh").addIngredient('i').item(Material.DYE, false).addIngredient('h').item(Material.HARDENED_CLAY, false).build().buildAndAdd();
        this.shaped(StainedGlassMat.STAINED_GLASS_BLACK, 8, "ggg", "gig", "ggg").addIngredient('i').item(Material.DYE, false).addIngredient('g').item(Material.GLASS, false).build().buildAndAdd();
        this.shaped(StainedHardenedClayMat.STAINED_HARDENED_CLAY_RED, 8, "hhh", "hih", "hhh").addIngredient('i').item(DyeMat.DYE_RED, false).addIngredient('h').item(Material.HARDENED_CLAY, false).build().buildAndAdd();
        this.shaped(StainedGlassMat.STAINED_GLASS_RED, 8, "ggg", "gig", "ggg").addIngredient('i').item(DyeMat.DYE_RED, false).addIngredient('g').item(Material.GLASS, false).build().buildAndAdd();
        this.shaped(StainedHardenedClayMat.STAINED_HARDENED_CLAY_GREEN, 8, "hhh", "hih", "hhh").addIngredient('i').item(DyeMat.DYE_GREEN, false).addIngredient('h').item(Material.HARDENED_CLAY, false).build().buildAndAdd();
        this.shaped(StainedGlassMat.STAINED_GLASS_GREEN, 8, "ggg", "gig", "ggg").addIngredient('i').item(DyeMat.DYE_GREEN, false).addIngredient('g').item(Material.GLASS, false).build().buildAndAdd();
        this.shaped(StainedHardenedClayMat.STAINED_HARDENED_CLAY_BROWN, 8, "hhh", "hih", "hhh").addIngredient('i').item(DyeMat.DYE_COCOA_BEANS, false).addIngredient('h').item(Material.HARDENED_CLAY, false).build().buildAndAdd();
        this.shaped(StainedGlassMat.STAINED_GLASS_BROWN, 8, "ggg", "gig", "ggg").addIngredient('i').item(DyeMat.DYE_COCOA_BEANS, false).addIngredient('g').item(Material.GLASS, false).build().buildAndAdd();
        this.shaped(StainedHardenedClayMat.STAINED_HARDENED_CLAY_BLUE, 8, "hhh", "hih", "hhh").addIngredient('i').item(DyeMat.DYE_LAPIS_LAZULI, false).addIngredient('h').item(Material.HARDENED_CLAY, false).build().buildAndAdd();
        this.shaped(StainedGlassMat.STAINED_GLASS_BLUE, 8, "ggg", "gig", "ggg").addIngredient('i').item(DyeMat.DYE_LAPIS_LAZULI, false).addIngredient('g').item(Material.GLASS, false).build().buildAndAdd();
        this.shaped(StainedHardenedClayMat.STAINED_HARDENED_CLAY_PURPLE, 8, "hhh", "hih", "hhh").addIngredient('i').item(DyeMat.DYE_PURPLE, false).addIngredient('h').item(Material.HARDENED_CLAY, false).build().buildAndAdd();
        this.shaped(StainedGlassMat.STAINED_GLASS_PURPLE, 8, "ggg", "gig", "ggg").addIngredient('i').item(DyeMat.DYE_PURPLE, false).addIngredient('g').item(Material.GLASS, false).build().buildAndAdd();
        this.shaped(StainedHardenedClayMat.STAINED_HARDENED_CLAY_CYAN, 8, "hhh", "hih", "hhh").addIngredient('i').item(DyeMat.DYE_CYAN, false).addIngredient('h').item(Material.HARDENED_CLAY, false).build().buildAndAdd();
        this.shaped(StainedGlassMat.STAINED_GLASS_CYAN, 8, "ggg", "gig", "ggg").addIngredient('i').item(DyeMat.DYE_CYAN, false).addIngredient('g').item(Material.GLASS, false).build().buildAndAdd();
        this.shaped(StainedHardenedClayMat.STAINED_HARDENED_CLAY_LIGHT_GRAY, 8, "hhh", "hih", "hhh").addIngredient('i').item(DyeMat.DYE_LIGHT_GRAY, false).addIngredient('h').item(Material.HARDENED_CLAY, false).build().buildAndAdd();
        this.shaped(StainedGlassMat.STAINED_GLASS_LIGHT_GRAY, 8, "ggg", "gig", "ggg").addIngredient('i').item(DyeMat.DYE_LIGHT_GRAY, false).addIngredient('g').item(Material.GLASS, false).build().buildAndAdd();
        this.shaped(StainedHardenedClayMat.STAINED_HARDENED_CLAY_GRAY, 8, "hhh", "hih", "hhh").addIngredient('i').item(DyeMat.DYE_GRAY, false).addIngredient('h').item(Material.HARDENED_CLAY, false).build().buildAndAdd();
        this.shaped(StainedGlassMat.STAINED_GLASS_GRAY, 8, "ggg", "gig", "ggg").addIngredient('i').item(DyeMat.DYE_GRAY, false).addIngredient('g').item(Material.GLASS, false).build().buildAndAdd();
        this.shaped(StainedHardenedClayMat.STAINED_HARDENED_CLAY_PINK, 8, "hhh", "hih", "hhh").addIngredient('i').item(DyeMat.DYE_PINK, false).addIngredient('h').item(Material.HARDENED_CLAY, false).build().buildAndAdd();
        this.shaped(StainedGlassMat.STAINED_GLASS_PINK, 8, "ggg", "gig", "ggg").addIngredient('i').item(DyeMat.DYE_PINK, false).addIngredient('g').item(Material.GLASS, false).build().buildAndAdd();
        this.shaped(StainedHardenedClayMat.STAINED_HARDENED_CLAY_LIME, 8, "hhh", "hih", "hhh").addIngredient('i').item(DyeMat.DYE_LIME, false).addIngredient('h').item(Material.HARDENED_CLAY, false).build().buildAndAdd();
        this.shaped(StainedGlassMat.STAINED_GLASS_LIME, 8, "ggg", "gig", "ggg").addIngredient('i').item(DyeMat.DYE_LIME, false).addIngredient('g').item(Material.GLASS, false).build().buildAndAdd();
        this.shaped(StainedHardenedClayMat.STAINED_HARDENED_CLAY_YELLOW, 8, "hhh", "hih", "hhh").addIngredient('i').item(DyeMat.DYE_YELLOW, false).addIngredient('h').item(Material.HARDENED_CLAY, false).build().buildAndAdd();
        this.shaped(StainedGlassMat.STAINED_GLASS_YELLOW, 8, "ggg", "gig", "ggg").addIngredient('i').item(DyeMat.DYE_YELLOW, false).addIngredient('g').item(Material.GLASS, false).build().buildAndAdd();
        this.shaped(StainedHardenedClayMat.STAINED_HARDENED_CLAY_LIGHT_BLUE, 8, "hhh", "hih", "hhh").addIngredient('i').item(DyeMat.DYE_LIGHT_BLUE, false).addIngredient('h').item(Material.HARDENED_CLAY, false).build().buildAndAdd();
        this.shaped(StainedGlassMat.STAINED_GLASS_LIGHT_BLUE, 8, "ggg", "gig", "ggg").addIngredient('i').item(DyeMat.DYE_LIGHT_BLUE, false).addIngredient('g').item(Material.GLASS, false).build().buildAndAdd();
        this.shaped(StainedHardenedClayMat.STAINED_HARDENED_CLAY_MAGENTA, 8, "hhh", "hih", "hhh").addIngredient('i').item(DyeMat.DYE_MAGENTA, false).addIngredient('h').item(Material.HARDENED_CLAY, false).build().buildAndAdd();
        this.shaped(StainedGlassMat.STAINED_GLASS_MAGENTA, 8, "ggg", "gig", "ggg").addIngredient('i').item(DyeMat.DYE_MAGENTA, false).addIngredient('g').item(Material.GLASS, false).build().buildAndAdd();
        this.shaped(StainedHardenedClayMat.STAINED_HARDENED_CLAY_ORANGE, 8, "hhh", "hih", "hhh").addIngredient('i').item(DyeMat.DYE_ORANGE, false).addIngredient('h').item(Material.HARDENED_CLAY, false).build().buildAndAdd();
        this.shaped(StainedGlassMat.STAINED_GLASS_ORANGE, 8, "ggg", "gig", "ggg").addIngredient('i').item(DyeMat.DYE_ORANGE, false).addIngredient('g').item(Material.GLASS, false).build().buildAndAdd();
        this.shaped(Material.STAINED_HARDENED_CLAY, 8, "hhh", "hih", "hhh").addIngredient('i').item(DyeMat.DYE_BONE_MEAL, false).addIngredient('h').item(Material.HARDENED_CLAY, false).build().buildAndAdd();
        this.shaped(Material.STAINED_GLASS, 8, "ggg", "gig", "ggg").addIngredient('i').item(DyeMat.DYE_BONE_MEAL, false).addIngredient('g').item(Material.GLASS, false).build().buildAndAdd();
        this.shaped(Material.MAP, 1, "ppp", "pmp", "ppp").addIngredient('m').item(Material.FILLED_MAP, true).addIngredient('p').item(Material.PAPER, false).build().buildAndAdd(); // TODO
        this.shaped(BannerMat.BANNER_WHITE, 1, "www", "www", "_s_").addIngredient('s').item(Material.STICK, false).addIngredient('w').item(Material.WOOL, false).build().buildAndAdd();
        this.shaped(BannerMat.BANNER_ORANGE, 1, "www", "www", "_s_").addIngredient('s').item(Material.STICK, false).addIngredient('w').item(WoolMat.WOOL_ORANGE, false).build().buildAndAdd();
        this.shaped(BannerMat.BANNER_MAGENTA, 1, "www", "www", "_s_").addIngredient('s').item(Material.STICK, false).addIngredient('w').item(WoolMat.WOOL_MAGENTA, false).build().buildAndAdd();
        this.shaped(BannerMat.BANNER_LIGHT_BLUE, 1, "www", "www", "_s_").addIngredient('s').item(Material.STICK, false).addIngredient('w').item(WoolMat.WOOL_LIGHT_BLUE, false).build().buildAndAdd();
        this.shaped(BannerMat.BANNER_YELLOW, 1, "www", "www", "_s_").addIngredient('s').item(Material.STICK, false).addIngredient('w').item(WoolMat.WOOL_YELLOW, false).build().buildAndAdd();
        this.shaped(BannerMat.BANNER_LIME, 1, "www", "www", "_s_").addIngredient('s').item(Material.STICK, false).addIngredient('w').item(WoolMat.WOOL_LIME, false).build().buildAndAdd();
        this.shaped(BannerMat.BANNER_PINK, 1, "www", "www", "_s_").addIngredient('s').item(Material.STICK, false).addIngredient('w').item(WoolMat.WOOL_PINK, false).build().buildAndAdd();
        this.shaped(BannerMat.BANNER_GRAY, 1, "www", "www", "_s_").addIngredient('s').item(Material.STICK, false).addIngredient('w').item(WoolMat.WOOL_GRAY, false).build().buildAndAdd();
        this.shaped(BannerMat.BANNER_LIGHT_GRAY, 1, "www", "www", "_s_").addIngredient('s').item(Material.STICK, false).addIngredient('w').item(WoolMat.WOOL_LIGHT_GRAY, false).build().buildAndAdd();
        this.shaped(BannerMat.BANNER_CYAN, 1, "www", "www", "_s_").addIngredient('s').item(Material.STICK, false).addIngredient('w').item(WoolMat.WOOL_CYAN, false).build().buildAndAdd();
        this.shaped(BannerMat.BANNER_PURPLE, 1, "www", "www", "_s_").addIngredient('s').item(Material.STICK, false).addIngredient('w').item(WoolMat.WOOL_PURPLE, false).build().buildAndAdd();
        this.shaped(BannerMat.BANNER_BLUE, 1, "www", "www", "_s_").addIngredient('s').item(Material.STICK, false).addIngredient('w').item(WoolMat.WOOL_BLUE, false).build().buildAndAdd();
        this.shaped(BannerMat.BANNER_BROWN, 1, "www", "www", "_s_").addIngredient('s').item(Material.STICK, false).addIngredient('w').item(WoolMat.WOOL_BROWN, false).build().buildAndAdd();
        this.shaped(BannerMat.BANNER_GREEN, 1, "www", "www", "_s_").addIngredient('s').item(Material.STICK, false).addIngredient('w').item(WoolMat.WOOL_GREEN, false).build().buildAndAdd();
        this.shaped(BannerMat.BANNER_RED, 1, "www", "www", "_s_").addIngredient('s').item(Material.STICK, false).addIngredient('w').item(WoolMat.WOOL_RED, false).build().buildAndAdd();
        this.shaped(Material.BANNER, 1, "www", "www", "_s_").addIngredient('s').item(Material.STICK, false).addIngredient('w').item(WoolMat.WOOL_BLACK, false).build().buildAndAdd();
        this.shaped(Material.JUKEBOX, 1, "www", "wdw", "www").addIngredient('w').item(Material.PLANKS, true).addIngredient('d').item(Material.DIAMOND, false).build().buildAndAdd();
        this.shaped(Material.LEAD, 2, "ss_", "sS_", "__s").addIngredient('s').item(Material.STRING, false).addIngredient('S').item(Material.SLIMEBALL, false).build().buildAndAdd();
        this.shaped(Material.NOTEBLOCK, 1, "www", "wrw", "www").addIngredient('r').item(Material.REDSTONE, false).addIngredient('w').item(Material.PLANKS, true).build().buildAndAdd();
        this.shaped(Material.BOOKSHELF, 1, "www", "bbb", "www").addIngredient('b').item(Material.BOOK, false).addIngredient('w').item(Material.PLANKS, true).build().buildAndAdd();
        this.shaped(Material.TNT, 1, "sSs", "SsS", "sSs").addIngredient('s').item(Material.GUNPOWDER, false).addIngredient('S').item(Material.SAND, true).build().buildAndAdd();
        this.shaped(Material.LADDER, 3, "s_s", "sss", "s_s").addIngredient('s').item(Material.STICK, false).build().buildAndAdd();
        this.shaped(Material.SIGN, 3, "www", "www", "_s_").addIngredient('s').item(Material.STICK, false).addIngredient('w').item(Material.PLANKS, true).build().buildAndAdd();
        this.shaped(Material.CAKE, 1, "mmm", "ses", "www").addIngredient('m').item(Material.MILK_BUCKET, false).replacement(Material.BUCKET).addIngredient('s').item(Material.SUGAR, false).addIngredient('w').item(Material.WHEAT, false).addIngredient('e').item(Material.EGG, false).build().buildAndAdd();
        this.shaped(Material.RAIL, 16, "i_i", "isi", "i_i").addIngredient('i').item(Material.IRON_INGOT, false).addIngredient('s').item(Material.STICK, false).build().buildAndAdd();
        this.shaped(Material.POWERED_RAIL, 6, "g_g", "gsg", "grg").addIngredient('s').item(Material.STICK, false).addIngredient('g').item(Material.GOLD_INGOT, false).addIngredient('r').item(Material.REDSTONE, false).build().buildAndAdd();
        this.shaped(Material.ACTIVATOR_RAIL, 6, "isi", "iri", "isi").addIngredient('i').item(Material.IRON_INGOT, false).addIngredient('s').item(Material.STICK, false).addIngredient('r').item(RedstoneTorchOnMat.REDSTONE_TORCH_ON_ITEM, true).build().buildAndAdd();
        this.shaped(Material.DETECTOR_RAIL, 6, "i_i", "isi", "iri").addIngredient('i').item(Material.IRON_INGOT, false).addIngredient('s').item(Material.STONE_PRESSURE_PLATE, true).addIngredient('r').item(Material.REDSTONE, false).build().buildAndAdd();
        this.shaped(Material.CAULDRON, 1, "i_i", "i_i", "iii").addIngredient('i').item(Material.IRON_INGOT, false).build().buildAndAdd();
        this.shaped(Material.OAK_STAIRS, 4, "w__", "ww_", "www").addIngredient('w').item(Material.PLANKS, false).build().buildAndAdd();
        this.shaped(Material.BIRCH_STAIRS, 4, "w__", "ww_", "www").addIngredient('w').item(PlanksMat.PLANKS_BIRCH, false).build().buildAndAdd();
        this.shaped(Material.SPRUCE_STAIRS, 4, "w__", "ww_", "www").addIngredient('w').item(PlanksMat.PLANKS_SPRUCE, false).build().buildAndAdd();
        this.shaped(Material.JUNGLE_STAIRS, 4, "w__", "ww_", "www").addIngredient('w').item(PlanksMat.PLANKS_JUNGLE, false).build().buildAndAdd();
        this.shaped(Material.ACACIA_STAIRS, 4, "w__", "ww_", "www").addIngredient('w').item(PlanksMat.PLANKS_ACACIA, false).build().buildAndAdd();
        this.shaped(Material.DARK_OAK_STAIRS, 4, "w__", "ww_", "www").addIngredient('w').item(PlanksMat.PLANKS_DARK_OAK, false).build().buildAndAdd();
        this.shaped(Material.FISHING_ROD, 1, "__s", "_sS", "s_S").addIngredient('s').item(Material.STICK, false).addIngredient('S').item(Material.STRING, false).build().buildAndAdd();
        this.shaped(Material.COBBLESTONE_STAIRS, 4, "c__", "cc_", "ccc").addIngredient('c').item(Material.COBBLESTONE, true).build().buildAndAdd();
        this.shaped(Material.BRICK_STAIRS, 4, "b__", "bb_", "bbb").addIngredient('b').item(Material.BRICK_BLOCK, true).build().buildAndAdd();
        this.shaped(Material.STONE_BRICK_STAIRS, 4, "s__", "ss_", "sss").addIngredient('s').item(Material.STONE_BRICK, true).build().buildAndAdd();
        this.shaped(Material.NETHER_BRICK_STAIRS, 4, "n__", "nn_", "nnn").addIngredient('n').item(Material.NETHER_BRICK, true).build().buildAndAdd();
        this.shaped(Material.SANDSTONE_STAIRS, 4, "s__", "ss_", "sss").addIngredient('s').item(Material.SANDSTONE, true).build().buildAndAdd();
        this.shaped(Material.RED_SANDSTONE_STAIRS, 4, "r__", "rr_", "rrr").addIngredient('r').item(Material.RED_SANDSTONE, true).build().buildAndAdd();
        this.shaped(Material.QUARTZ_STAIRS, 4, "q__", "qq_", "qqq").addIngredient('q').item(Material.QUARTZ_BLOCK, true).build().buildAndAdd();
        this.shaped(Material.PAINTING, 1, "sss", "sws", "sss").addIngredient('s').item(Material.STICK, false).addIngredient('w').item(Material.WOOL, true).build().buildAndAdd();
        this.shaped(Material.ITEM_FRAME, 1, "sss", "sls", "sss").addIngredient('s').item(Material.STICK, false).addIngredient('l').item(Material.LEATHER, false).build().buildAndAdd();
        this.shaped(Material.GOLDEN_APPLE, 1, "ggg", "gag", "ggg").addIngredient('a').item(Material.APPLE, false).addIngredient('g').item(Material.GOLD_INGOT, false).build().buildAndAdd();
        this.shaped(GoldenAppleMat.GOLDEN_ENCHANTED_APPLE, 1, "ggg", "gag", "ggg").addIngredient('a').item(Material.APPLE, false).addIngredient('g').item(Material.GOLD_BLOCK, true).build().buildAndAdd();
        this.shaped(Material.GOLDEN_CARROT, 1, "ggg", "gcg", "ggg").addIngredient('g').item(Material.GOLD_NUGGET, false).addIngredient('c').item(Material.CARROT, false).build().buildAndAdd();
        this.shaped(Material.SPECKLED_MELON, 1, "ggg", "gmg", "ggg").addIngredient('m').item(Material.MELON, false).addIngredient('g').item(Material.GOLD_NUGGET, false).build().buildAndAdd();
        this.shaped(Material.REDSTONE_COMPARATOR_ITEM, 1, "_r_", "rqr", "sss").addIngredient('s').item(Material.STONE, false).addIngredient('r').item(RedstoneTorchOnMat.REDSTONE_TORCH_ON_ITEM, true).addIngredient('q').item(Material.QUARTZ, false).build().buildAndAdd();
        this.shaped(Material.CLOCK, 1, "_g_", "grg", "_g_").addIngredient('g').item(Material.GOLD_INGOT, false).addIngredient('r').item(Material.REDSTONE, false).build().buildAndAdd();
        this.shaped(Material.COMPASS, 1, "_i_", "iri", "_i_").addIngredient('i').item(Material.IRON_INGOT, false).addIngredient('r').item(Material.REDSTONE, false).build().buildAndAdd();
        this.shaped(Material.MAP, 1, "ppp", "pcp", "ppp").addIngredient('p').item(Material.PAPER, false).addIngredient('c').item(Material.COMPASS, false).build().buildAndAdd();
        this.shaped(Material.DISPENSER, 1, "ccc", "cbc", "crc").addIngredient('b').item(Material.BOW, false).addIngredient('r').item(Material.REDSTONE, false).addIngredient('c').item(Material.COBBLESTONE, true).build().buildAndAdd();
        this.shaped(Material.DROPPER, 1, "ccc", "c_c", "crc").addIngredient('r').item(Material.REDSTONE, false).addIngredient('c').item(Material.COBBLESTONE, true).build().buildAndAdd();
        this.shaped(Material.PISTON, 1, "www", "cic", "crc").addIngredient('i').item(Material.IRON_INGOT, false).addIngredient('r').item(Material.REDSTONE, false).addIngredient('w').item(Material.PLANKS, true).addIngredient('c').item(Material.COBBLESTONE, true).build().buildAndAdd();
        this.shaped(Material.ENCHANTING_TABLE, 1, "_b_", "dod", "ooo").addIngredient('b').item(Material.BOOK, false).addIngredient('d').item(Material.DIAMOND, false).addIngredient('o').item(Material.OBSIDIAN, true).build().buildAndAdd();
        this.shaped(Material.ANVIL, 1, "iii", "_I_", "III").addIngredient('i').item(Material.IRON_BLOCK, true).addIngredient('I').item(Material.IRON_INGOT, false).build().buildAndAdd();
        this.shaped(Material.DAYLIGHT_DETECTOR, 1, "ggg", "qqq", "www").addIngredient('g').item(Material.GLASS, true).addIngredient('w').item(Material.WOODEN_SLAB, true).addIngredient('q').item(Material.QUARTZ, false).build().buildAndAdd();
        this.shaped(Material.HOPPER, 1, "i_i", "ici", "_i_").addIngredient('i').item(Material.IRON_INGOT, false).addIngredient('c').item(Material.CHEST, true).build().buildAndAdd();
        this.shaped(Material.ARMOR_STAND, 1, "sss", "_s_", "sSs").addIngredient('s').item(Material.STICK, false).addIngredient('S').item(Material.STONE_SLAB, false).build().buildAndAdd();
        this.shaped(Material.WOODEN_AXE, 1, "ww", "ws", "_s").addIngredient('s').item(Material.STICK, false).addIngredient('w').item(Material.PLANKS, true).build().buildAndAdd();
        this.shaped(Material.WOODEN_HOE, 1, "ww", "_s", "_s").addIngredient('s').item(Material.STICK, false).addIngredient('w').item(Material.PLANKS, true).build().buildAndAdd();
        this.shaped(Material.STONE_AXE, 1, "cc", "cs", "_s").addIngredient('s').item(Material.STICK, false).addIngredient('c').item(Material.COBBLESTONE, true).build().buildAndAdd();
        this.shaped(Material.STONE_HOE, 1, "cc", "_s", "_s").addIngredient('s').item(Material.STICK, false).addIngredient('c').item(Material.COBBLESTONE, true).build().buildAndAdd();
        this.shaped(Material.IRON_AXE, 1, "ii", "is", "_s").addIngredient('i').item(Material.IRON_INGOT, false).addIngredient('s').item(Material.STICK, false).build().buildAndAdd();
        this.shaped(Material.IRON_HOE, 1, "ii", "_s", "_s").addIngredient('i').item(Material.IRON_INGOT, false).addIngredient('s').item(Material.STICK, false).build().buildAndAdd();
        this.shaped(Material.DIAMOND_AXE, 1, "dd", "ds", "_s").addIngredient('s').item(Material.STICK, false).addIngredient('d').item(Material.DIAMOND, false).build().buildAndAdd();
        this.shaped(Material.DIAMOND_HOE, 1, "dd", "_s", "_s").addIngredient('s').item(Material.STICK, false).addIngredient('d').item(Material.DIAMOND, false).build().buildAndAdd();
        this.shaped(Material.GOLDEN_AXE, 1, "gg", "gs", "_s").addIngredient('s').item(Material.STICK, false).addIngredient('g').item(Material.GOLD_INGOT, false).build().buildAndAdd();
        this.shaped(Material.GOLDEN_HOE, 1, "gg", "_s", "_s").addIngredient('s').item(Material.STICK, false).addIngredient('g').item(Material.GOLD_INGOT, false).build().buildAndAdd();
        this.shaped(Material.IRON_BARS, 16, "iii", "iii").addIngredient('i').item(Material.IRON_INGOT, false).build().buildAndAdd();
        this.shaped(Material.GLASS_PANE, 16, "ggg", "ggg").addIngredient('g').item(Material.GLASS, true).build().buildAndAdd();
        this.shaped(Material.LEATHER_HELMET, 1, "lll", "l_l").addIngredient('l').item(Material.LEATHER, false).build().buildAndAdd();
        this.shaped(Material.LEATHER_BOOTS, 1, "l_l", "l_l").addIngredient('l').item(Material.LEATHER, false).build().buildAndAdd();
        this.shaped(Material.IRON_HELMET, 1, "iii", "i_i").addIngredient('i').item(Material.IRON_INGOT, false).build().buildAndAdd();
        this.shaped(Material.IRON_BOOTS, 1, "i_i", "i_i").addIngredient('i').item(Material.IRON_INGOT, false).build().buildAndAdd();
        this.shaped(Material.DIAMOND_HELMET, 1, "ddd", "d_d").addIngredient('d').item(Material.DIAMOND, false).build().buildAndAdd();
        this.shaped(Material.DIAMOND_BOOTS, 1, "d_d", "d_d").addIngredient('d').item(Material.DIAMOND, false).build().buildAndAdd();
        this.shaped(Material.GOLD_HELMET, 1, "ggg", "g_g").addIngredient('g').item(Material.GOLD_INGOT, false).build().buildAndAdd();
        this.shaped(Material.GOLD_BOOTS, 1, "g_g", "g_g").addIngredient('g').item(Material.GOLD_INGOT, false).build().buildAndAdd();
        this.shaped(Material.STAINED_GLASS_PANE, 16, "sss", "sss").addIngredient('s').item(Material.STAINED_GLASS, false).build().buildAndAdd();
        this.shaped(StainedGlassPaneMat.STAINED_GLASS_PANE_ORANGE, 16, "sss", "sss").addIngredient('s').item(StainedGlassMat.STAINED_GLASS_ORANGE, false).build().buildAndAdd();
        this.shaped(StainedGlassPaneMat.STAINED_GLASS_PANE_MAGENTA, 16, "sss", "sss").addIngredient('s').item(StainedGlassMat.STAINED_GLASS_MAGENTA, false).build().buildAndAdd();
        this.shaped(StainedGlassPaneMat.STAINED_GLASS_PANE_LIGHT_BLUE, 16, "sss", "sss").addIngredient('s').item(StainedGlassMat.STAINED_GLASS_LIGHT_BLUE, false).build().buildAndAdd();
        this.shaped(StainedGlassPaneMat.STAINED_GLASS_PANE_YELLOW, 16, "sss", "sss").addIngredient('s').item(StainedGlassMat.STAINED_GLASS_YELLOW, false).build().buildAndAdd();
        this.shaped(StainedGlassPaneMat.STAINED_GLASS_PANE_LIME, 16, "sss", "sss").addIngredient('s').item(StainedGlassMat.STAINED_GLASS_LIME, false).build().buildAndAdd();
        this.shaped(StainedGlassPaneMat.STAINED_GLASS_PANE_PINK, 16, "sss", "sss").addIngredient('s').item(StainedGlassMat.STAINED_GLASS_PINK, false).build().buildAndAdd();
        this.shaped(StainedGlassPaneMat.STAINED_GLASS_PANE_GRAY, 16, "sss", "sss").addIngredient('s').item(StainedGlassMat.STAINED_GLASS_GRAY, false).build().buildAndAdd();
        this.shaped(StainedGlassPaneMat.STAINED_GLASS_PANE_LIGHT_GRAY, 16, "sss", "sss").addIngredient('s').item(StainedGlassMat.STAINED_GLASS_LIGHT_GRAY, false).build().buildAndAdd();
        this.shaped(StainedGlassPaneMat.STAINED_GLASS_PANE_CYAN, 16, "sss", "sss").addIngredient('s').item(StainedGlassMat.STAINED_GLASS_CYAN, false).build().buildAndAdd();
        this.shaped(StainedGlassPaneMat.STAINED_GLASS_PANE_PURPLE, 16, "sss", "sss").addIngredient('s').item(StainedGlassMat.STAINED_GLASS_PURPLE, false).build().buildAndAdd();
        this.shaped(StainedGlassPaneMat.STAINED_GLASS_PANE_BLUE, 16, "sss", "sss").addIngredient('s').item(StainedGlassMat.STAINED_GLASS_BLUE, false).build().buildAndAdd();
        this.shaped(StainedGlassPaneMat.STAINED_GLASS_PANE_BROWN, 16, "sss", "sss").addIngredient('s').item(StainedGlassMat.STAINED_GLASS_BROWN, false).build().buildAndAdd();
        this.shaped(StainedGlassPaneMat.STAINED_GLASS_PANE_GREEN, 16, "sss", "sss").addIngredient('s').item(StainedGlassMat.STAINED_GLASS_GREEN, false).build().buildAndAdd();
        this.shaped(StainedGlassPaneMat.STAINED_GLASS_PANE_RED, 16, "sss", "sss").addIngredient('s').item(StainedGlassMat.STAINED_GLASS_RED, false).build().buildAndAdd();
        this.shaped(StainedGlassPaneMat.STAINED_GLASS_PANE_BLACK, 16, "sss", "sss").addIngredient('s').item(StainedGlassMat.STAINED_GLASS_BLACK, false).build().buildAndAdd();
        this.shaped(Material.OAK_FENCE, 3, "wsw", "wsw").addIngredient('s').item(Material.STICK, false).addIngredient('w').item(Material.PLANKS, false).build().buildAndAdd();
        this.shaped(Material.BIRCH_FENCE, 3, "wsw", "wsw").addIngredient('s').item(Material.STICK, false).addIngredient('w').item(PlanksMat.PLANKS_BIRCH, false).build().buildAndAdd();
        this.shaped(Material.SPRUCE_FENCE, 3, "wsw", "wsw").addIngredient('s').item(Material.STICK, false).addIngredient('w').item(PlanksMat.PLANKS_SPRUCE, false).build().buildAndAdd();
        this.shaped(Material.JUNGLE_FENCE, 3, "wsw", "wsw").addIngredient('s').item(Material.STICK, false).addIngredient('w').item(PlanksMat.PLANKS_JUNGLE, false).build().buildAndAdd();
        this.shaped(Material.ACACIA_FENCE, 3, "wsw", "wsw").addIngredient('s').item(Material.STICK, false).addIngredient('w').item(PlanksMat.PLANKS_ACACIA, false).build().buildAndAdd();
        this.shaped(Material.DARK_OAK_FENCE, 3, "wsw", "wsw").addIngredient('s').item(Material.STICK, false).addIngredient('w').item(PlanksMat.PLANKS_DARK_OAK, false).build().buildAndAdd();
        this.shaped(Material.COBBLESTONE_WALL, 6, "ccc", "ccc").addIngredient('c').item(Material.COBBLESTONE, true).build().buildAndAdd();
        this.shaped(CobblestoneWallMat.COBBLESTONE_WALL_MOSSY, 6, "mmm", "mmm").addIngredient('m').item(Material.MOSSY_COBBLESTONE, true).build().buildAndAdd();
        this.shaped(Material.NETHER_BRICK_FENCE, 6, "nnn", "nnn").addIngredient('n').item(Material.NETHER_BRICK, true).build().buildAndAdd();
        this.shaped(Material.OAK_FENCE_GATE, 1, "sws", "sws").addIngredient('s').item(Material.STICK, false).addIngredient('w').item(Material.PLANKS, false).build().buildAndAdd();
        this.shaped(Material.BIRCH_FENCE_GATE, 1, "sws", "sws").addIngredient('s').item(Material.STICK, false).addIngredient('w').item(PlanksMat.PLANKS_BIRCH, false).build().buildAndAdd();
        this.shaped(Material.SPRUCE_FENCE_GATE, 1, "sws", "sws").addIngredient('s').item(Material.STICK, false).addIngredient('w').item(PlanksMat.PLANKS_SPRUCE, false).build().buildAndAdd();
        this.shaped(Material.JUNGLE_FENCE_GATE, 1, "sws", "sws").addIngredient('s').item(Material.STICK, false).addIngredient('w').item(PlanksMat.PLANKS_JUNGLE, false).build().buildAndAdd();
        this.shaped(Material.ACACIA_FENCE_GATE, 1, "sws", "sws").addIngredient('s').item(Material.STICK, false).addIngredient('w').item(PlanksMat.PLANKS_ACACIA, false).build().buildAndAdd();
        this.shaped(Material.DARK_OAK_FENCE_GATE, 1, "sws", "sws").addIngredient('s').item(Material.STICK, false).addIngredient('w').item(PlanksMat.PLANKS_DARK_OAK, false).build().buildAndAdd();
        this.shaped(Material.OAK_DOOR_ITEM, 3, "ww", "ww", "ww").addIngredient('w').item(Material.PLANKS, false).build().buildAndAdd();
        this.shaped(Material.SPRUCE_DOOR_ITEM, 3, "ww", "ww", "ww").addIngredient('w').item(PlanksMat.PLANKS_SPRUCE, false).build().buildAndAdd();
        this.shaped(Material.BIRCH_DOOR_ITEM, 3, "ww", "ww", "ww").addIngredient('w').item(PlanksMat.PLANKS_BIRCH, false).build().buildAndAdd();
        this.shaped(Material.JUNGLE_DOOR_ITEM, 3, "ww", "ww", "ww").addIngredient('w').item(PlanksMat.PLANKS_JUNGLE, false).build().buildAndAdd();
        this.shaped(Material.ACACIA_DOOR_ITEM, 3, "ww", "ww", "ww").addIngredient('w').item(PlanksMat.PLANKS_ACACIA, false).build().buildAndAdd();
        this.shaped(Material.DARK_OAK_DOOR_ITEM, 3, "ww", "ww", "ww").addIngredient('w').item(PlanksMat.PLANKS_DARK_OAK, false).build().buildAndAdd();
        this.shaped(Material.WOODEN_TRAPDOOR, 2, "www", "www").addIngredient('w').item(Material.PLANKS, true).build().buildAndAdd();
        this.shaped(Material.IRON_DOOR_ITEM, 3, "ii", "ii", "ii").addIngredient('i').item(Material.IRON_INGOT, false).build().buildAndAdd();
        this.shaped(Material.BOWL, 4, "w_w", "_w_").addIngredient('w').item(Material.PLANKS, true).build().buildAndAdd();
        this.shaped(Material.GLASS_BOTTLE, 3, "g_g", "_g_").addIngredient('g').item(Material.GLASS, true).build().buildAndAdd();
        this.shaped(Material.MINECART, 1, "i_i", "iii").addIngredient('i').item(Material.IRON_INGOT, false).build().buildAndAdd();
        this.shaped(Material.BREWING_STAND, 1, "_b_", "ccc").addIngredient('b').item(Material.BLAZE_ROD, false).addIngredient('c').item(Material.COBBLESTONE, true).build().buildAndAdd();
        this.shaped(Material.BOAT, 1, "w_w", "www").addIngredient('w').item(Material.PLANKS, true).build().buildAndAdd();
        this.shaped(Material.BUCKET, 1, "i_i", "_i_").addIngredient('i').item(Material.IRON_INGOT, false).build().buildAndAdd();
        this.shaped(Material.FLOWER_POT, 1, "c_c", "_c_").addIngredient('c').item(Material.BRICK, false).build().buildAndAdd();
        this.shaped(Material.REDSTONE_REPEATER_ITEM, 1, "rRr", "sss").addIngredient('s').item(Material.STONE, false).addIngredient('r').item(RedstoneTorchOnMat.REDSTONE_TORCH_ON_ITEM, true).addIngredient('R').item(Material.REDSTONE, false).build().buildAndAdd();
        this.shaped(Material.BED, 1, "www", "WWW").addIngredient('W').item(Material.PLANKS, true).addIngredient('w').item(Material.WOOL, true).build().buildAndAdd();
        this.shaped(Material.SHEARS, 1, "_i", "i_").addIngredient('i').item(Material.IRON_INGOT, false).build().buildAndAdd();
        this.shaped(Material.CRAFTING_TABLE, 1, "ww", "ww").addIngredient('w').item(Material.PLANKS, true).build().buildAndAdd();
        this.shaped(Material.SANDSTONE, 1, "ss", "ss").addIngredient('s').item(Material.SAND, false).build().buildAndAdd();
        this.shaped(Material.RED_SANDSTONE, 1, "ss", "ss").addIngredient('s').item(SandMat.SAND_RED, false).build().buildAndAdd();
        this.shaped(SandstoneMat.SANDSTONE_SMOOTH, 4, "ss", "ss").addIngredient('s').item(Material.SANDSTONE, false).build().buildAndAdd();
        this.shaped(RedSandstoneMat.RED_SANDSTONE_SMOOTH, 4, "rr", "rr").addIngredient('r').item(Material.RED_SANDSTONE, false).build().buildAndAdd();
        this.shaped(Material.STONE_BRICK, 4, "ss", "ss").addIngredient('s').item(Material.STONE, false).build().buildAndAdd();
        this.shaped(Material.NETHER_BRICK, 1, "nn", "nn").addIngredient('n').item(Material.NETHER_BRICK_ITEM, false).build().buildAndAdd();
        this.shaped(StoneMat.STONE_DIORITE, 2, "cq", "qc").addIngredient('q').item(Material.QUARTZ, false).addIngredient('c').item(Material.COBBLESTONE, true).build().buildAndAdd();
        this.shaped(DirtMat.DIRT_COARSE, 4, "dg", "gd").addIngredient('g').item(Material.GRAVEL, true).addIngredient('d').item(Material.DIRT, false).build().buildAndAdd();
        this.shaped(StoneMat.STONE_POLISHED_DIORITE, 4, "ss", "ss").addIngredient('s').item(StoneMat.STONE_DIORITE, false).build().buildAndAdd();
        this.shaped(StoneMat.STONE_POLISHED_GRANITE, 4, "ss", "ss").addIngredient('s').item(StoneMat.STONE_GRANITE, false).build().buildAndAdd();
        this.shaped(StoneMat.STONE_POLISHED_ANDESITE, 4, "ss", "ss").addIngredient('s').item(StoneMat.STONE_ANDESITE, false).build().buildAndAdd();
        this.shaped(Material.PRISMARINE, 1, "pp", "pp").addIngredient('p').item(Material.PRISMARINE_SHARD, false).build().buildAndAdd();
        this.shaped(Material.SNOW_BLOCK, 1, "ss", "ss").addIngredient('s').item(Material.SNOWBALL, false).build().buildAndAdd();
        this.shaped(Material.CLAY_BLOCK, 1, "cc", "cc").addIngredient('c').item(Material.CLAY_BALL, false).build().buildAndAdd();
        this.shaped(Material.BRICK_BLOCK, 1, "cc", "cc").addIngredient('c').item(Material.BRICK, false).build().buildAndAdd();
        this.shaped(Material.GLOWSTONE, 1, "gg", "gg").addIngredient('g').item(Material.GLOWSTONE_DUST, false).build().buildAndAdd();
        this.shaped(Material.QUARTZ_BLOCK, 1, "qq", "qq").addIngredient('q').item(Material.QUARTZ, false).build().buildAndAdd();
        this.shaped(Material.WOOL, 1, "ss", "ss").addIngredient('s').item(Material.STRING, false).build().buildAndAdd();
        this.shaped(Material.IRON_TRAPDOOR, 1, "ii", "ii").addIngredient('i').item(Material.IRON_INGOT, false).build().buildAndAdd();
        this.shaped(Material.CARROT_ON_A_STICK, 1, "f_", "_c").addIngredient('f').item(Material.FISHING_ROD, false).addIngredient('c').item(Material.CARROT, false).build().buildAndAdd();
        this.shaped(Material.LEATHER, 1, "rr", "rr").addIngredient('r').item(Material.RABBIT_HIDE, false).build().buildAndAdd();
        this.shaped(Material.WOODEN_SHOVEL, 1, "w", "s", "s").addIngredient('s').item(Material.STICK, false).addIngredient('w').item(Material.PLANKS, true).build().buildAndAdd();
        this.shaped(Material.STONE_SHOVEL, 1, "c", "s", "s").addIngredient('s').item(Material.STICK, false).addIngredient('c').item(Material.COBBLESTONE, true).build().buildAndAdd();
        this.shaped(Material.IRON_SHOVEL, 1, "i", "s", "s").addIngredient('i').item(Material.IRON_INGOT, false).addIngredient('s').item(Material.STICK, false).build().buildAndAdd();
        this.shaped(Material.DIAMOND_SHOVEL, 1, "d", "s", "s").addIngredient('s').item(Material.STICK, false).addIngredient('d').item(Material.DIAMOND, false).build().buildAndAdd();
        this.shaped(Material.GOLDEN_SHOVEL, 1, "g", "s", "s").addIngredient('s').item(Material.STICK, false).addIngredient('g').item(Material.GOLD_INGOT, false).build().buildAndAdd();
        this.shaped(Material.WOODEN_SWORD, 1, "w", "w", "s").addIngredient('s').item(Material.STICK, false).addIngredient('w').item(Material.PLANKS, true).build().buildAndAdd();
        this.shaped(Material.STONE_SWORD, 1, "c", "c", "s").addIngredient('s').item(Material.STICK, false).addIngredient('c').item(Material.COBBLESTONE, true).build().buildAndAdd();
        this.shaped(Material.IRON_SWORD, 1, "i", "i", "s").addIngredient('i').item(Material.IRON_INGOT, false).addIngredient('s').item(Material.STICK, false).build().buildAndAdd();
        this.shaped(Material.DIAMOND_SWORD, 1, "d", "d", "s").addIngredient('s').item(Material.STICK, false).addIngredient('d').item(Material.DIAMOND, false).build().buildAndAdd();
        this.shaped(Material.GOLDEN_SWORD, 1, "g", "g", "s").addIngredient('s').item(Material.STICK, false).addIngredient('g').item(Material.GOLD_INGOT, false).build().buildAndAdd();
        this.shaped(Material.ARROW, 4, "f", "s", "F").addIngredient('F').item(Material.FEATHER, false).addIngredient('s').item(Material.STICK, false).addIngredient('f').item(Material.FLINT, false).build().buildAndAdd();
        this.shaped(Material.COOKIE, 8, "wiw").addIngredient('i').item(DyeMat.DYE_COCOA_BEANS, false).addIngredient('w').item(Material.WHEAT, false).build().buildAndAdd();
        this.shaped(Material.PAPER, 3, "sss").addIngredient('s').item(Material.REEDS, false).build().buildAndAdd();
        this.shaped(Material.SNOW_LAYER, 6, "sss").addIngredient('s').item(Material.SNOW_BLOCK, true).build().buildAndAdd();
        this.shaped(StoneSlabMat.STONE_SLAB_COBBLESTONE, 6, "ccc").addIngredient('c').item(Material.COBBLESTONE, true).build().buildAndAdd();
        this.shaped(Material.STONE_SLAB, 6, "sss").addIngredient('s').item(Material.STONE, false).build().buildAndAdd();
        this.shaped(StoneSlabMat.STONE_SLAB_SANDSTONE, 6, "sss").addIngredient('s').item(Material.SANDSTONE, true).build().buildAndAdd();
        this.shaped(StoneSlabMat.STONE_SLAB_BRICKS, 6, "bbb").addIngredient('b').item(Material.BRICK_BLOCK, true).build().buildAndAdd();
        this.shaped(StoneSlabMat.STONE_SLAB_STONE_BRICKS, 6, "sss").addIngredient('s').item(Material.STONE_BRICK, true).build().buildAndAdd();
        this.shaped(StoneSlabMat.STONE_SLAB_NETHER_BRICKS, 6, "nnn").addIngredient('n').item(Material.NETHER_BRICK, true).build().buildAndAdd();
        this.shaped(StoneSlabMat.STONE_SLAB_QUARTZ, 6, "qqq").addIngredient('q').item(Material.QUARTZ_BLOCK, true).build().buildAndAdd();
        this.shaped(StoneSlabMat.STONE_SLAB_STONE, 6, "rrr").addIngredient('r').item(Material.RED_SANDSTONE, true).build().buildAndAdd();
        this.shaped(Material.WOODEN_SLAB, 6, "www").addIngredient('w').item(Material.PLANKS, false).build().buildAndAdd();
        this.shaped(WoodenSlabMat.WOODEN_SLAB_BIRCH, 6, "www").addIngredient('w').item(PlanksMat.PLANKS_BIRCH, false).build().buildAndAdd();
        this.shaped(WoodenSlabMat.WOODEN_SLAB_SPRUCE, 6, "www").addIngredient('w').item(PlanksMat.PLANKS_SPRUCE, false).build().buildAndAdd();
        this.shaped(WoodenSlabMat.WOODEN_SLAB_JUNGLE, 6, "www").addIngredient('w').item(PlanksMat.PLANKS_JUNGLE, false).build().buildAndAdd();
        this.shaped(WoodenSlabMat.WOODEN_SLAB_ACACIA, 6, "www").addIngredient('w').item(PlanksMat.PLANKS_ACACIA, false).build().buildAndAdd();
        this.shaped(WoodenSlabMat.WOODEN_SLAB_DARK_OAK, 6, "www").addIngredient('w').item(PlanksMat.PLANKS_DARK_OAK, false).build().buildAndAdd();
        this.shaped(Material.BREAD, 1, "www").addIngredient('w').item(Material.WHEAT, false).build().buildAndAdd();
        this.shaped(Material.TRIPWIRE_HOOK, 2, "i", "s", "w").addIngredient('i').item(Material.IRON_INGOT, false).addIngredient('s').item(Material.STICK, false).addIngredient('w').item(Material.PLANKS, true).build().buildAndAdd();
        this.shaped(Material.TRAPPED_CHEST, 1, "ct").addIngredient('t').item(Material.TRIPWIRE_HOOK, true).addIngredient('c').item(Material.CHEST, true).build().buildAndAdd();
        this.shaped(SandstoneMat.SANDSTONE_CHISELED, 1, "s", "s").addIngredient('s').item(StoneSlabMat.STONE_SLAB_SANDSTONE, false).build().buildAndAdd();
        this.shaped(RedSandstoneMat.RED_SANDSTONE_CHISELED, 1, "s", "s").addIngredient('s').item(StoneSlabMat.STONE_SLAB_STONE, false).build().buildAndAdd();
        this.shaped(QuartzBlockMat.QUARTZ_BLOCK_CHISELED, 1, "s", "s").addIngredient('s').item(StoneSlabMat.STONE_SLAB_QUARTZ, false).build().buildAndAdd();
        this.shaped(QuartzBlockMat.QUARTZ_BLOCK_PILLAR_VERTICAL, 2, "q", "q").addIngredient('q').item(Material.QUARTZ_BLOCK, false).build().buildAndAdd();
        this.shaped(StoneBrickMat.STONE_BRICK_CHISELED, 1, "s", "s").addIngredient('s').item(StoneSlabMat.STONE_SLAB_STONE_BRICKS, false).build().buildAndAdd();
        this.shaped(Material.CARPET, 3, "ww").addIngredient('w').item(Material.WOOL, false).build().buildAndAdd();
        this.shaped(CarpetMat.CARPET_ORANGE, 3, "ww").addIngredient('w').item(WoolMat.WOOL_ORANGE, false).build().buildAndAdd();
        this.shaped(CarpetMat.CARPET_MAGENTA, 3, "ww").addIngredient('w').item(WoolMat.WOOL_MAGENTA, false).build().buildAndAdd();
        this.shaped(CarpetMat.CARPET_LIGHT_BLUE, 3, "ww").addIngredient('w').item(WoolMat.WOOL_LIGHT_BLUE, false).build().buildAndAdd();
        this.shaped(CarpetMat.CARPET_YELLOW, 3, "ww").addIngredient('w').item(WoolMat.WOOL_YELLOW, false).build().buildAndAdd();
        this.shaped(CarpetMat.CARPET_LIME, 3, "ww").addIngredient('w').item(WoolMat.WOOL_LIME, false).build().buildAndAdd();
        this.shaped(CarpetMat.CARPET_PINK, 3, "ww").addIngredient('w').item(WoolMat.WOOL_PINK, false).build().buildAndAdd();
        this.shaped(CarpetMat.CARPET_GRAY, 3, "ww").addIngredient('w').item(WoolMat.WOOL_GRAY, false).build().buildAndAdd();
        this.shaped(CarpetMat.CARPET_LIGHT_GRAY, 3, "ww").addIngredient('w').item(WoolMat.WOOL_LIGHT_GRAY, false).build().buildAndAdd();
        this.shaped(CarpetMat.CARPET_CYAN, 3, "ww").addIngredient('w').item(WoolMat.WOOL_CYAN, false).build().buildAndAdd();
        this.shaped(CarpetMat.CARPET_PURPLE, 3, "ww").addIngredient('w').item(WoolMat.WOOL_PURPLE, false).build().buildAndAdd();
        this.shaped(CarpetMat.CARPET_BLUE, 3, "ww").addIngredient('w').item(WoolMat.WOOL_BLUE, false).build().buildAndAdd();
        this.shaped(CarpetMat.CARPET_BROWN, 3, "ww").addIngredient('w').item(WoolMat.WOOL_BROWN, false).build().buildAndAdd();
        this.shaped(CarpetMat.CARPET_GREEN, 3, "ww").addIngredient('w').item(WoolMat.WOOL_GREEN, false).build().buildAndAdd();
        this.shaped(CarpetMat.CARPET_RED, 3, "ww").addIngredient('w').item(WoolMat.WOOL_RED, false).build().buildAndAdd();
        this.shaped(CarpetMat.CARPET_BLACK, 3, "ww").addIngredient('w').item(WoolMat.WOOL_BLACK, false).build().buildAndAdd();
        this.shaped(Material.STICK, 4, "w", "w").addIngredient('w').item(Material.PLANKS, true).build().buildAndAdd();
        this.shaped(TorchMat.TORCH_ITEM, 4, "c", "s").addIngredient('s').item(Material.STICK, false).addIngredient('c').item(Material.COAL, false).build().buildAndAdd();
        this.shaped(TorchMat.TORCH_ITEM, 4, "c", "s").addIngredient('s').item(Material.STICK, false).addIngredient('c').item(CoalMat.CHARCOAL, false).build().buildAndAdd();
        this.shaped(Material.PUMPKIN_LANTERN, 1, "p", "t").addIngredient('t').item(TorchMat.TORCH_ITEM, true).addIngredient('p').item(Material.PUMPKIN, true).build().buildAndAdd();
        this.shaped(Material.CHEST_MINECART, 1, "c", "m").addIngredient('m').item(Material.MINECART, false).addIngredient('c').item(Material.CHEST, true).build().buildAndAdd();
        this.shaped(Material.FURNACE_MINECART, 1, "f", "m").addIngredient('m').item(Material.MINECART, false).addIngredient('f').item(Material.FURNACE, true).build().buildAndAdd();
        this.shaped(Material.TNT_MINECART, 1, "t", "m").addIngredient('t').item(Material.TNT, true).addIngredient('m').item(Material.MINECART, false).build().buildAndAdd();
        this.shaped(Material.HOPPER_MINECART, 1, "h", "m").addIngredient('h').item(Material.HOPPER, true).addIngredient('m').item(Material.MINECART, false).build().buildAndAdd();
        this.shaped(Material.LEVER, 1, "s", "c").addIngredient('s').item(Material.STICK, false).addIngredient('c').item(Material.COBBLESTONE, true).build().buildAndAdd();
        this.shaped(RedstoneTorchOnMat.REDSTONE_TORCH_ON_ITEM, 1, "r", "s").addIngredient('s').item(Material.STICK, false).addIngredient('r').item(Material.REDSTONE, false).build().buildAndAdd();
        this.shaped(Material.STONE_PRESSURE_PLATE, 1, "ss").addIngredient('s').item(Material.STONE, false).build().buildAndAdd();
        this.shaped(Material.WOODEN_PRESSURE_PLATE, 1, "ww").addIngredient('w').item(Material.PLANKS, true).build().buildAndAdd();
        this.shaped(Material.IRON_PRESSURE_PLATE, 1, "ii").addIngredient('i').item(Material.IRON_INGOT, false).build().buildAndAdd();
        this.shaped(Material.GOLDEN_PRESSURE_PLATE, 1, "gg").addIngredient('g').item(Material.GOLD_INGOT, false).build().buildAndAdd();
        this.shaped(Material.PISTON_STICKY, 1, "s", "p").addIngredient('s').item(Material.SLIMEBALL, false).addIngredient('p').item(Material.PISTON, true).build().buildAndAdd();
        this.shaped(Material.GOLD_INGOT, 9, "g").addIngredient('g').item(Material.GOLD_BLOCK, true).build().buildAndAdd();
        this.shaped(Material.IRON_INGOT, 9, "i").addIngredient('i').item(Material.IRON_BLOCK, true).build().buildAndAdd();
        this.shaped(Material.DIAMOND, 9, "d").addIngredient('d').item(Material.DIAMOND_BLOCK, true).build().buildAndAdd();
        this.shaped(Material.EMERALD, 9, "e").addIngredient('e').item(Material.EMERALD_BLOCK, true).build().buildAndAdd();
        this.shaped(DyeMat.DYE_LAPIS_LAZULI, 9, "l").addIngredient('l').item(Material.LAPIS_BLOCK, true).build().buildAndAdd();
        this.shaped(Material.REDSTONE, 9, "r").addIngredient('r').item(Material.REDSTONE_BLOCK, true).build().buildAndAdd();
        this.shaped(Material.COAL, 9, "c").addIngredient('c').item(Material.COAL_BLOCK, true).build().buildAndAdd();
        this.shaped(Material.WHEAT, 9, "h").addIngredient('h').item(Material.HAY_BLOCK, true).build().buildAndAdd();
        this.shaped(Material.SLIMEBALL, 9, "s").addIngredient('s').item(Material.SLIME_BLOCK, true).build().buildAndAdd();
        this.shaped(Material.GOLD_NUGGET, 9, "g").addIngredient('g').item(Material.GOLD_INGOT, false).build().buildAndAdd();
        this.shaped(Material.MELON_SEEDS, 1, "m").addIngredient('m').item(Material.MELON, false).build().buildAndAdd();
        this.shaped(Material.PUMPKIN_SEEDS, 4, "p").addIngredient('p').item(Material.PUMPKIN, true).build().buildAndAdd();
        this.shaped(Material.SUGAR, 1, "s").addIngredient('s').item(Material.REEDS, false).build().buildAndAdd();
        this.shaped(Material.PLANKS, 4, "l").addIngredient('l').item(Material.LOG, false).build().buildAndAdd();
        this.shaped(PlanksMat.PLANKS_SPRUCE, 4, "l").addIngredient('l').item(LogMat.LOG_SPRUCE, false).build().buildAndAdd();
        this.shaped(PlanksMat.PLANKS_BIRCH, 4, "l").addIngredient('l').item(LogMat.LOG_BIRCH, false).build().buildAndAdd();
        this.shaped(PlanksMat.PLANKS_JUNGLE, 4, "l").addIngredient('l').item(LogMat.LOG_JUNGLE, false).build().buildAndAdd();
        this.shaped(PlanksMat.PLANKS_ACACIA, 4, "l").addIngredient('l').item(LogMat.LOG_ACACIA, false).build().buildAndAdd();
        this.shaped(PlanksMat.PLANKS_DARK_OAK, 4, "l").addIngredient('l').item(LogMat.LOG_DARK_OAK, false).build().buildAndAdd();
        this.shaped(Material.STONE_BUTTON, 1, "s").addIngredient('s').item(Material.STONE, false).build().buildAndAdd();
        this.shaped(Material.WOODEN_BUTTON, 1, "w").addIngredient('w').item(Material.PLANKS, true).build().buildAndAdd();

//        this.shapeless(Material.FIREWORKS, 0).addIngredient().item(Material.GUNPOWDER, false).addIngredient().item(Material.PAPER, false).build().buildAndAdd(); // TODO
//        this.shapeless(Material.BANNER, 0).addIngredient().item(Material.BANNER, false).build().buildAndAdd(); // TODO
//        this.shapeless(Material.MAP, 0).addIngredient().item(Material.MAP, false).build().buildAndAdd(); // TODO
//        this.shapeless(Material.BANNER, 0).addIngredient().item(DyeMat.DYE_PURPLE, false).build().buildAndAdd(); // TODO
        this.shapeless(DyeMat.DYE_MAGENTA, 4).addIngredient().item(DyeMat.DYE_LAPIS_LAZULI, false).addIngredient().item(DyeMat.DYE_RED, false).addIngredient().item(DyeMat.DYE_RED, false).addIngredient().item(DyeMat.DYE_BONE_MEAL, false).build().buildAndAdd();
        this.shapeless(Material.BOOK, 1).addIngredient().item(Material.PAPER, false).addIngredient().item(Material.PAPER, false).addIngredient().item(Material.PAPER, false).addIngredient().item(Material.LEATHER, false).build().buildAndAdd();
        this.shapeless(Material.MUSHROOM_STEW, 1).addIngredient().item(Material.BROWN_MUSHROOM, false).addIngredient().item(Material.RED_MUSHROOM, false).addIngredient().item(Material.BOWL, false).build().buildAndAdd();
        this.shapeless(Material.PUMPKIN_PIE, 1).addIngredient().item(Material.PUMPKIN, false).addIngredient().item(Material.SUGAR, false).addIngredient().item(Material.EGG, false).build().buildAndAdd();
        this.shapeless(Material.FERMENTED_SPIDER_EYE, 1).addIngredient().item(Material.SPIDER_EYE, false).addIngredient().item(Material.BROWN_MUSHROOM, false).addIngredient().item(Material.SUGAR, false).build().buildAndAdd();
        this.shapeless(DyeMat.DYE_LIGHT_GRAY, 3).addIngredient().item(Material.DYE, false).addIngredient().item(DyeMat.DYE_BONE_MEAL, false).addIngredient().item(DyeMat.DYE_BONE_MEAL, false).build().buildAndAdd();
        this.shapeless(DyeMat.DYE_MAGENTA, 3).addIngredient().item(DyeMat.DYE_LAPIS_LAZULI, false).addIngredient().item(DyeMat.DYE_RED, false).addIngredient().item(DyeMat.DYE_PINK, false).build().buildAndAdd();
        this.shapeless(Material.WRITABLE_BOOK, 1).addIngredient().item(Material.BOOK, false).addIngredient().item(Material.DYE, false).addIngredient().item(Material.FEATHER, false).build().buildAndAdd();
        this.shapeless(Material.FIRE_CHARGE, 3).addIngredient().item(Material.GUNPOWDER, false).addIngredient().item(Material.BLAZE_POWDER, false).addIngredient().item(Material.COAL, false).build().buildAndAdd();
        this.shapeless(Material.FIRE_CHARGE, 3).addIngredient().item(Material.GUNPOWDER, false).addIngredient().item(Material.BLAZE_POWDER, false).addIngredient().item(CoalMat.CHARCOAL, false).build().buildAndAdd();
        this.shapeless(Material.MAGMA_CREAM, 1).addIngredient().item(Material.BLAZE_POWDER, false).addIngredient().item(Material.SLIMEBALL, false).build().buildAndAdd();
        this.shapeless(StoneBrickMat.STONE_BRICK_MOSSY, 1).addIngredient().item(Material.STONE_BRICK, false).addIngredient().item(Material.VINE, false).build().buildAndAdd();
        this.shapeless(Material.MOSSY_COBBLESTONE, 1).addIngredient().item(Material.COBBLESTONE, false).addIngredient().item(Material.VINE, false).build().buildAndAdd();
        this.shapeless(StoneMat.STONE_GRANITE, 1).addIngredient().item(StoneMat.STONE_DIORITE, false).addIngredient().item(Material.QUARTZ, false).build().buildAndAdd();
        this.shapeless(StoneMat.STONE_ANDESITE, 2).addIngredient().item(StoneMat.STONE_DIORITE, false).addIngredient().item(Material.COBBLESTONE, false).build().buildAndAdd();
        this.shapeless(Material.WOOL, 1).addIngredient().item(DyeMat.DYE_BONE_MEAL, false).addIngredient().item(Material.WOOL, false).build().buildAndAdd();
        this.shapeless(WoolMat.WOOL_ORANGE, 1).addIngredient().item(DyeMat.DYE_ORANGE, false).addIngredient().item(Material.WOOL, false).build().buildAndAdd();
        this.shapeless(WoolMat.WOOL_MAGENTA, 1).addIngredient().item(DyeMat.DYE_MAGENTA, false).addIngredient().item(Material.WOOL, false).build().buildAndAdd();
        this.shapeless(WoolMat.WOOL_LIGHT_BLUE, 1).addIngredient().item(DyeMat.DYE_LIGHT_BLUE, false).addIngredient().item(Material.WOOL, false).build().buildAndAdd();
        this.shapeless(WoolMat.WOOL_YELLOW, 1).addIngredient().item(DyeMat.DYE_YELLOW, false).addIngredient().item(Material.WOOL, false).build().buildAndAdd();
        this.shapeless(WoolMat.WOOL_LIME, 1).addIngredient().item(DyeMat.DYE_LIME, false).addIngredient().item(Material.WOOL, false).build().buildAndAdd();
        this.shapeless(WoolMat.WOOL_PINK, 1).addIngredient().item(DyeMat.DYE_PINK, false).addIngredient().item(Material.WOOL, false).build().buildAndAdd();
        this.shapeless(WoolMat.WOOL_GRAY, 1).addIngredient().item(DyeMat.DYE_GRAY, false).addIngredient().item(Material.WOOL, false).build().buildAndAdd();
        this.shapeless(WoolMat.WOOL_LIGHT_GRAY, 1).addIngredient().item(DyeMat.DYE_LIGHT_GRAY, false).addIngredient().item(Material.WOOL, false).build().buildAndAdd();
        this.shapeless(WoolMat.WOOL_CYAN, 1).addIngredient().item(DyeMat.DYE_CYAN, false).addIngredient().item(Material.WOOL, false).build().buildAndAdd();
        this.shapeless(WoolMat.WOOL_PURPLE, 1).addIngredient().item(DyeMat.DYE_PURPLE, false).addIngredient().item(Material.WOOL, false).build().buildAndAdd();
        this.shapeless(WoolMat.WOOL_BLUE, 1).addIngredient().item(DyeMat.DYE_LAPIS_LAZULI, false).addIngredient().item(Material.WOOL, false).build().buildAndAdd();
        this.shapeless(WoolMat.WOOL_BROWN, 1).addIngredient().item(DyeMat.DYE_COCOA_BEANS, false).addIngredient().item(Material.WOOL, false).build().buildAndAdd();
        this.shapeless(WoolMat.WOOL_GREEN, 1).addIngredient().item(DyeMat.DYE_GREEN, false).addIngredient().item(Material.WOOL, false).build().buildAndAdd();
        this.shapeless(WoolMat.WOOL_RED, 1).addIngredient().item(DyeMat.DYE_RED, false).addIngredient().item(Material.WOOL, false).build().buildAndAdd();
        this.shapeless(WoolMat.WOOL_BLACK, 1).addIngredient().item(Material.DYE, false).addIngredient().item(Material.WOOL, false).build().buildAndAdd();
        this.shapeless(DyeMat.DYE_PINK, 2).addIngredient().item(DyeMat.DYE_RED, false).addIngredient().item(DyeMat.DYE_BONE_MEAL, false).build().buildAndAdd();
        this.shapeless(DyeMat.DYE_ORANGE, 2).addIngredient().item(DyeMat.DYE_RED, false).addIngredient().item(DyeMat.DYE_YELLOW, false).build().buildAndAdd();
        this.shapeless(DyeMat.DYE_LIME, 2).addIngredient().item(DyeMat.DYE_GREEN, false).addIngredient().item(DyeMat.DYE_BONE_MEAL, false).build().buildAndAdd();
        this.shapeless(DyeMat.DYE_GRAY, 2).addIngredient().item(Material.DYE, false).addIngredient().item(DyeMat.DYE_BONE_MEAL, false).build().buildAndAdd();
        this.shapeless(DyeMat.DYE_LIGHT_GRAY, 2).addIngredient().item(DyeMat.DYE_GRAY, false).addIngredient().item(DyeMat.DYE_BONE_MEAL, false).build().buildAndAdd();
        this.shapeless(DyeMat.DYE_LIGHT_BLUE, 2).addIngredient().item(DyeMat.DYE_LAPIS_LAZULI, false).addIngredient().item(DyeMat.DYE_BONE_MEAL, false).build().buildAndAdd();
        this.shapeless(DyeMat.DYE_CYAN, 2).addIngredient().item(DyeMat.DYE_LAPIS_LAZULI, false).addIngredient().item(DyeMat.DYE_GREEN, false).build().buildAndAdd();
        this.shapeless(DyeMat.DYE_PURPLE, 2).addIngredient().item(DyeMat.DYE_LAPIS_LAZULI, false).addIngredient().item(DyeMat.DYE_RED, false).build().buildAndAdd();
        this.shapeless(DyeMat.DYE_MAGENTA, 2).addIngredient().item(DyeMat.DYE_PURPLE, false).addIngredient().item(DyeMat.DYE_PINK, false).build().buildAndAdd();
        this.shapeless(Material.FLINT_AND_STEEL, 1).addIngredient().item(Material.IRON_INGOT, false).addIngredient().item(Material.FLINT, false).build().buildAndAdd();
        this.shapeless(Material.ENDER_EYE, 1).addIngredient().item(Material.ENDER_PEARL, false).addIngredient().item(Material.BLAZE_POWDER, false).build().buildAndAdd();
        this.shapeless(Material.BLAZE_POWDER, 2).addIngredient().item(Material.BLAZE_ROD, false).build().buildAndAdd();
        this.shapeless(DyeMat.DYE_YELLOW, 1).addIngredient().item(Material.DANDELION, false).build().buildAndAdd();
        this.shapeless(DyeMat.DYE_RED, 1).addIngredient().item(Material.FLOWERS, false).build().buildAndAdd();
        this.shapeless(DyeMat.DYE_BONE_MEAL, 3).addIngredient().item(Material.BONE, false).build().buildAndAdd();
        this.shapeless(DyeMat.DYE_LIGHT_BLUE, 1).addIngredient().item(FlowersMat.FLOWERS_BLUE_ORCHID, false).build().buildAndAdd();
        this.shapeless(DyeMat.DYE_MAGENTA, 1).addIngredient().item(FlowersMat.FLOWERS_ALLIUM, false).build().buildAndAdd();
        this.shapeless(DyeMat.DYE_LIGHT_GRAY, 1).addIngredient().item(FlowersMat.FLOWERS_AZURE_BLUET, false).build().buildAndAdd();
        this.shapeless(DyeMat.DYE_RED, 1).addIngredient().item(FlowersMat.FLOWERS_RED_TULIP, false).build().buildAndAdd();
        this.shapeless(DyeMat.DYE_ORANGE, 1).addIngredient().item(FlowersMat.FLOWERS_ORANGE_TULIP, false).build().buildAndAdd();
        this.shapeless(DyeMat.DYE_LIGHT_GRAY, 1).addIngredient().item(FlowersMat.FLOWERS_WHITE_TULIP, false).build().buildAndAdd();
        this.shapeless(DyeMat.DYE_PINK, 1).addIngredient().item(FlowersMat.FLOWERS_PINK_TULIP, false).build().buildAndAdd();
        this.shapeless(DyeMat.DYE_LIGHT_GRAY, 1).addIngredient().item(FlowersMat.FLOWERS_OXEYE_DAISY, false).build().buildAndAdd();
        this.shapeless(DyeMat.DYE_YELLOW, 2).addIngredient().item(Material.DOUBLE_FLOWERS, false).build().buildAndAdd();
        this.shapeless(DyeMat.DYE_MAGENTA, 2).addIngredient().item(DoubleFlowersMat.DOUBLE_FLOWERS_LILAC, false).build().buildAndAdd();
        this.shapeless(DyeMat.DYE_RED, 2).addIngredient().item(DoubleFlowersMat.DOUBLE_FLOWERS_ROSE_BUSH, false).build().buildAndAdd();
        this.shapeless(DyeMat.DYE_PINK, 2).addIngredient().item(DoubleFlowersMat.DOUBLE_FLOWERS_PEONY, false).build().buildAndAdd();

        this.repair(Material.WOODEN_PICKAXE, Material.WOODEN_AXE, Material.WOODEN_SHOVEL, Material.WOODEN_HOE, Material.WOODEN_SWORD);
        this.repair(Material.STONE_PICKAXE, Material.STONE_AXE, Material.STONE_SHOVEL, Material.STONE_HOE, Material.STONE_SWORD);
        this.repair(Material.IRON_PICKAXE, Material.IRON_AXE, Material.IRON_SHOVEL, Material.IRON_HOE, Material.IRON_SWORD);
        this.repair(Material.GOLDEN_PICKAXE, Material.GOLDEN_AXE, Material.GOLDEN_SHOVEL, Material.GOLDEN_HOE, Material.GOLDEN_SWORD);
        this.repair(Material.DIAMOND_PICKAXE, Material.DIAMOND_AXE, Material.DIAMOND_SHOVEL, Material.DIAMOND_HOE, Material.DIAMOND_SWORD);

        this.repair(Material.LEATHER_HELMET, Material.LEATHER_CHESTPLATE, Material.LEATHER_LEGGINGS, Material.LEATHER_BOOTS);
        this.repair(Material.IRON_HELMET, Material.IRON_CHESTPLATE, Material.IRON_LEGGINGS, Material.IRON_BOOTS);
        this.repair(Material.CHAINMAIL_HELMET, Material.CHAINMAIL_CHESTPLATE, Material.CHAINMAIL_LEGGINGS, Material.CHAINMAIL_BOOTS);
        this.repair(Material.GOLD_HELMET, Material.GOLD_CHESTPLATE, Material.GOLD_LEGGINGS, Material.GOLD_BOOTS);
        this.repair(Material.DIAMOND_HELMET, Material.DIAMOND_CHESTPLATE, Material.DIAMOND_LEGGINGS, Material.DIAMOND_BOOTS);

        this.color(Material.LEATHER_HELMET);
        this.color(Material.LEATHER_CHESTPLATE);
        this.color(Material.LEATHER_LEGGINGS);
        this.color(Material.LEATHER_BOOTS);

        this.bookCopy();
    }

    private void bookCopy()
    {
        final ShapelessRecipeBuilder builder = this.shapeless(Material.WRITTEN_BOOK, 1).addIngredient().item(Material.WRITTEN_BOOK, true).replacement(Material.WRITTEN_BOOK, grid -> {
            for (final ItemStack itemStack : grid.getItems())
            {
                if ((itemStack != null) && itemStack.getMaterial().simpleEquals(Material.WRITTEN_BOOK))
                {
                    return itemStack;
                }
            }
            throw new AssertionError("Copy book crafting grid without book to copy.");
        }).simpleValidator(item -> ((BookMeta) item.getItemMeta()).getGeneration() < GenerationEnum.COPY_OF_COPY.getGeneration()).build();
        for (int i = 1; i < 9; i++)
        {
            builder.addIngredient().item(Material.WRITABLE_BOOK, true).build();
            builder.result(grid -> {
                int copys = 0;
                ItemStack orginal = null;
                for (final ItemStack itemStack : grid.getItems())
                {
                    if (itemStack == null)
                    {
                        continue;
                    }
                    if (itemStack.getMaterial().simpleEquals(Material.WRITTEN_BOOK))
                    {
                        orginal = itemStack.clone();
                        continue;
                    }
                    copys++;
                }
                assert orginal != null;
                orginal.setAmount(copys);
                final BookMeta meta = (BookMeta) orginal.getItemMeta();
                meta.setGeneration(meta.getGeneration() + 1);
                return orginal;
            });
            builder.buildAndAdd();
        }

    }

    private void color(final ArmorMat mat)
    {
        final ShapelessRecipeBuilder builder = this.shapeless(mat, 1).addIngredient().item(mat, true).build();
        for (int i = 1; i < 9; i++)
        {
            builder.addIngredient().item(Material.DYE, true).build();
            builder.result(grid -> {
                int colors = 0;
                int tr = 0, tg = 0, tb = 0;
                double totalMax = 0;
                final List<ItemStack> items = grid.getItemsList();
                for (final ItemStack item : items)
                {
                    if (item.getMaterial().equals(mat))
                    {
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

                final ItemStack item = new BaseItemStack(mat);
                final LeatherArmorMeta meta = ((LeatherArmorMeta) item.getItemMeta());
                meta.setColor(resultColor);
                return item;
            });
            builder.buildAndAdd();
        }
    }

    @SuppressWarnings("unchecked")
    private <T extends ItemMaterialData & BreakableItemMat> void repair(final T... mat)
    {
        for (final T t : mat)
        {
            this.repair(t).buildAndAdd();
        }
    }

    @SuppressWarnings("unchecked")
    private <T extends ItemMaterialData & BreakableItemMat> ShapelessRecipeBuilder repair(final T mat)
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

    private ShapedRecipeBuilder shaped(final Material resultMat, final int resultAmount, final String... pattern)
    {
        return this.builder().priority(getPriority()).vanilla(true).result(resultMat, resultAmount).shaped().pattern(pattern);
    }

    private ShapelessRecipeBuilder shapeless(final Material resultMat, final int resultAmount)
    {
        return this.builder().priority(getPriority()).vanilla(true).result(resultMat, resultAmount).shapeless();
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("recipes", this.recipes).toString();
    }
}
