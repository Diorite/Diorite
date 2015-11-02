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

package org.diorite.enchantments;

import java.util.Map;
import java.util.function.Predicate;

import org.diorite.inventory.item.ItemStack;
import org.diorite.utils.SimpleEnum;
import org.diorite.utils.SimpleEnum.ASimpleEnum;
import org.diorite.utils.collections.maps.CaseInsensitiveMap;

import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.TShortObjectMap;
import gnu.trove.map.hash.TShortObjectHashMap;

/**
 * Enum of all possible enchantment types in vanilla minecraft.
 */
@SuppressWarnings("ClassHasNoToStringMethod")
public class EnchantmentType extends ASimpleEnum<EnchantmentType>
{
    static
    {
        //noinspection MagicNumber
        init(EnchantmentType.class, 27);
    }

    /**
     * Reduces most damage. <br>
     * Exceptions: doesn't reduce damage from the Void, the /kill command, and hunger damage.
     */
    public static final EnchantmentType PROTECTION            = new EnchantmentType("PROTECTION", "protection", 0, t -> false);
    /**
     * Reduces fire damage. <br>
     * Also reduces burn time when set on fire. Every Level will reduce the initiated Burn time by 15%. <br>
     * <br>
     * At Level VII for example you can't be lit on fire through Fire Aspect Enchantments. The only way to burn is staying in Lava or a Firesource. <br>
     * The initiated Fireticks (Lava=300, Fire=150) will be canceled, and only the remaining time in the firesource will be added to the Fireticktimer. However, this Level can not be achieved without editing.
     */
    public static final EnchantmentType FIRE_PROTECTION       = new EnchantmentType("FIRE_PROTECTION", "fire_protection", 1, t -> false);
    /**
     * Reduces fall damage. <br>
     * Also reduces the damage from ender pearl teleportations.
     */
    public static final EnchantmentType FEATHER_FALLING       = new EnchantmentType("FEATHER_FALLING", "feather_falling", 2, t -> false);
    /**
     * Reduces explosion damage.
     * Also reduces explosion knockback.
     */
    public static final EnchantmentType BLAST_PROTECTION      = new EnchantmentType("BLAST_PROTECTION", "blast_protection", 3, t -> false);
    /**
     * Reduces projectile damage. <br>
     * Includes damage from arrows, ghast/blaze fire charges, etc.
     */
    public static final EnchantmentType PROJECTILE_PROTECTION = new EnchantmentType("PROJECTILE_PROTECTION", "projectile_protection", 4, t -> false);
    /**
     * Extends underwater breathing time. <br>
     * Increases underwater breathing time by +15 seconds per level. <br>
     * Increases time between suffocation damage by +1 second per level. <br>
     * Improves underwater vision. <br>
     * Levels above III (which require editing to achieve) don't work, as they increase visibility over 100% which resembles swimming in ice slush.
     */
    public static final EnchantmentType RESPIRATION           = new EnchantmentType("RESPIRATION", "respiration", 5, t -> false);
    /**
     * Increases underwater mining rate. <br>
     * Breaking blocks underwater is allowed at regular speed, though the player cannot be floating to get the full effect. Different levels (through editing) are no different from lvl 1.
     */
    public static final EnchantmentType AQUA_AFFINITY         = new EnchantmentType("AQUA_AFFINITY", "aqua_affinity", 6, t -> false);
    /**
     * Damages attackers. <br>
     * (Level * 15)% chance of inflicting 1-4 hp (or Level - 10 if Level {@literal > 10}) damage on anyone who attacks wearer. <br>
     * In addition to the normal durability reduction for being hit, reduces durability by 3 points when inflicting damage and 1 point otherwise. <br>
     * If present on multiple pieces of armor, only the "bottommost" piece of armor counts. <br>
     * If present on multiple pieces of armor one will be chosen at random.
     */
    public static final EnchantmentType THORNS                = new EnchantmentType("THORNS", "thorns", 7, t -> false);
    /**
     * Increases underwater movement speed. <br>
     * Every level reduces the amount water slows you by 1/3. <br>
     * Level 3 will make you swim as fast as you walk on land. (Any level beyond that will have no effect on speed.) <br>
     * Speed potions will affect your swimming the same way as your walking at level 3. <br>
     * Does not increase vertical speed. <br>
     * If editing is used to put it on other pieces of armor (such as a helmet) it works normally. <br>
     * Not currently in console versions.
     */
    public static final EnchantmentType DEPTH_STRIDER         = new EnchantmentType("DEPTH_STRIDER", "depth_strider", 8, t -> false);
    /**
     * Creates frost blocks when walking over water. <br>
     * If standing on a solid block (and not in a solid block), any still water blocks within a radius of 2 + Level around the block being stood on will be turned into frosted ice.
     */
    public static final EnchantmentType FROST_WALKER          = new EnchantmentType("FROST_WALKER", "frost_walker", 9, t -> false);
    /**
     * Increases damage. <br>
     * Adds 1 extra damage for the first level, and 0.5 for each additional level.
     */
    public static final EnchantmentType SHARPNESS             = new EnchantmentType("SHARPNESS", "sharpness", 16, t -> false);
    /**
     * Increases damage to "undead" mobs (skeletons, zombies, withers, wither skeletons, and zombie pigmen) <br>
     * Each level separately adds 2.5 extra damage to each hit, to "undead" mobs only
     */
    public static final EnchantmentType SMITE                 = new EnchantmentType("SMITE", "smite", 17, t -> false);
    /**
     * Increases damage to "arthropod" mobs (spiders, cave spiders, silverfish and endermites) <br>
     * Each level separately adds 2.5 extra damage to each hit, to "arthropods" only. The enchantment will also cause "arthropods" to have the Slowness IV effect when hit. <br>
     * The duration of the effect is a random value between 1 and 1.5 seconds at level I, increasing the max duration by 0.5 seconds each level, up to 3.5s with Bane of Arthropods V. <br>
     * <br>
     * Notably, the slowness effect applies not only to "arthropod" kills with the sword itself, but to any "arthropod" kills while simply holding the sword.
     */
    public static final EnchantmentType BANE_OF_ARTHROPODS    = new EnchantmentType("BANE_OF_ARTHROPODS", "bane_of_arthropods", 18, t -> false);
    /**
     * Increases knockback. <br>
     * Increases knockback by 3 blocks at level I, and +3 blocks with every additional level. Does combine slightly with knockback caused by attacking while sprinting.
     */
    public static final EnchantmentType KNOCKBACK             = new EnchantmentType("KNOCKBACK", "knockback", 19, t -> false);
    /**
     * Sets the target on fire. <br>
     * Fire Aspect adds 80 Fireticks (4 seconds of burning) per Level to the target. <br>
     * Because the first hit is caused by the item with this enchantment, the 1st second of Fire damage will not be recognized. <br>
     * <br>
     * When possible, dropped meat will be cooked if the mob is killed while on fire. <br>
     * XP will only be dropped if non-fire damage was dealt by the player within 5 seconds of the death.
     */
    public static final EnchantmentType FIRE_ASPECT           = new EnchantmentType("FIRE_ASPECT", "fire_aspect", 20, t -> false);
    /**
     * Mobs can drop more loot. <br>
     * Increases maximum loot drop by +1 per level. <br>
     * Increases chance of rare drops by +1 percentage points per level (i.e., 3.5% at level I, 4.5% at level II, and 5.5% at level III). <br>
     * Notably, this applies not only to kills with the sword but to any kills while holding the sword. <br>
     * For example, a player can fire an arrow at a target and then switch to a looting sword before the arrow hits to get the looting effect.<br>
     * The exact drop chances depend on the type of drop.<br>
     */
    public static final EnchantmentType LOOTING               = new EnchantmentType("LOOTING", "looting", 21, t -> false);
    /**
     * Increases mining speed. <br>
     * Increases mining speed +30% over the previous level: I=130%, II=169%, III=220%, IV=286%, V=371%. <br>
     * One must use the proper tool for a block in order to receive the speed. Does not matter if you mine it with the incorrect tier. <br>
     * The speed increase applies to all blocks that when mined, will drop an item.
     */
    public static final EnchantmentType EFFICIENCY            = new EnchantmentType("EFFICIENCY", "efficiency", 32, t -> false);
    /**
     * Mined blocks drop themselves instead of the usual items. <br>
     * Allows collection of blocks that are normally unobtainable.
     */
    public static final EnchantmentType SILK_TOUCH            = new EnchantmentType("SILK_TOUCH", "silk_touch", 33, t -> false);
    /**
     * Increases durability. <br>
     * For most items, (100/(Level+1))% chance a use reduces durability. On average, lifetime is (Level+1) times as long. <br>
     * For armor, (60 + (40/(Level+1)))% chance a use reduces durability. (In other words, each durability hit against “unbreaking” armor has a 20%/27%/30% chance of being ignored.) <br>
     * Thus, on average, armor lasts 25%/36%/43% longer. <br>
     * <br>
     * When durability is reduced by multiple points (e.g. hooking a mob with a fishing rods or Thorns armor). <br>
     * Unbreaking is applied for each point of reduction. <br>
     */
    public static final EnchantmentType UNBREAKING            = new EnchantmentType("UNBREAKING", "unbreaking", 34, t -> false);
    /**
     * Increases block drops. <br>
     * For coal, diamond, emerald, nether quartz, and lapis lazuli, <br>
     * level I gives a 33% chance to multiply drops by 2 (averaging 33% increase), <br>
     * level II gives a chance to multiply drops by 2 or 3 (25% chance each, averaging 75% increase), <br>
     * and level III gives a chance to multiply drops by 2, 3, or 4 (20% chance each, averaging 120% increase). <br>
     * 1 drop has a weight of 2, and each number of extra drops has a weight of 1. <br>
     * <br>
     * For redstone, carrots, glowstone, sea lanterns, melons, nether wart, potatoes, and wheat (seeds only), each level increases the drop maximum by +1 (maximum 4 for glowstone, 5 for sea lanterns, and 9 for melons).  <br>
     * For tall grass, each level increases the drop maximum by +2. <br>
     * <br>
     * Fortune increases the probability of flint dropping from gravel, and saplings dropping from leaves, and apples dropping from oak and dark oak leaves.
     */
    public static final EnchantmentType FORTUNE               = new EnchantmentType("FORTUNE", "fortune", 35, t -> false);
    /**
     * Increases damage. <br>
     * Increases arrow damage by 25% * (level + 1), rounded up to nearest half-heart. <br>
     * <br>
     * Arrow entities have an NBT tag damage, which for an unenchanted bow is 2, and increments by 0.5 per enchantment level.
     */
    public static final EnchantmentType POWER                 = new EnchantmentType("POWER", "power", 48, t -> false);
    /**
     * Increases knockback. <br>
     * Mobs and players are knocked back further.
     */
    public static final EnchantmentType PUNCH                 = new EnchantmentType("PUNCH", "punch", 49, t -> false);
    /**
     * Flaming arrows. <br>
     * Arrows are on fire when shot and deal 5 hp fire damage over 5 seconds. <br>
     * Unlike flint and steel, flaming arrows only affect players, mobs, and TNT. <br>
     * No other blocks catch fire, and they do not produce light. <br>
     * Fire damage applies after initial damage, similar to Fire Aspect. <br>
     * Creatures killed by fire only drop XP if a player dealt non-fire damage to it within the past 5 seconds.
     */
    public static final EnchantmentType FLAME                 = new EnchantmentType("FLAME", "flame", 50, t -> false);
    /**
     * Shooting consumes no arrows. <br>
     * Allows user to fire infinite arrows as long as they have 1 arrow in their inventory. <br>
     * Fired arrows cannot be retrieved except in creative mode (where they are erased instead of added to your arrows).
     */
    public static final EnchantmentType INFINITY              = new EnchantmentType("INFINITY", "infinity", 51, t -> false);
    /**
     * Decreases odds of catching worthless junk. <br>
     * Lowers chance of "junk" catches by 2.5% per level, and increases chance of "treasure" catches by 1% per level.
     */
    public static final EnchantmentType LUCK_OF_THE_SEA       = new EnchantmentType("LUCK_OF_THE_SEA", "luck_of_the_sea", 61, t -> false);
    /**
     * Increases rate of fish biting your hook. <br>
     * Decreases wait time until a catch by 5 seconds per level. Also decreases chances of both "junk" and "treasure" catches by 1% per level.  <br>
     * At Level VIII, the fish-catching particle effects start almost instantly. <br>
     * At Level IX, you are not able to catch anything.
     */
    public static final EnchantmentType LURE                  = new EnchantmentType("LURE", "lure", 62, t -> false);
    /**
     * Uses XP to increase durability. <br>
     * When an item with the enchantment is held and needs repair,
     * XP orbs collected will repair the item at a rate of 2 durability per XP instead of adding the XP to the player's total. <br>
     * If multiple items have the enchantment, one will be chosen at random for each XP orb collected.
     */
    public static final EnchantmentType MENDING               = new EnchantmentType("MENDING", "mending", 70, t -> false);

    private static final Map<String, EnchantmentType>     byStringID    = new CaseInsensitiveMap<>(27, SMALL_LOAD_FACTOR);
    private static final TShortObjectMap<EnchantmentType> byNumericType = new TShortObjectHashMap<>(27, SMALL_LOAD_FACTOR);

    /**
     * String ID of this enchantment.
     */
    protected final String                     stringID;
    /**
     * ID of this enchantment.
     */
    protected final short                      numericID;
    /**
     * Predictate for checking conflicts with other enchantment types.
     */
    protected final Predicate<EnchantmentType> isConflicting;

    /**
     * Construct new EnchantmentType.
     *
     * @param enumName      enum name of type.
     * @param enumId        enum id of type.
     * @param stringID      id of this type.
     * @param numericID     id of this type.
     * @param isConflicting predictate for checking conflicts with other enchantment types.
     */
    public EnchantmentType(final String enumName, final int enumId, final String stringID, final int numericID, final Predicate<EnchantmentType> isConflicting)
    {
        super(enumName, enumId);
        this.stringID = stringID;
        this.numericID = (short) numericID;
        this.isConflicting = isConflicting;
    }

    /**
     * Construct new EnchantmentType.
     *
     * @param enumName      enum name of type.
     * @param stringID      id of this type.
     * @param numericID     id of this type.
     * @param isConflicting predictate for checking conflicts with other enchantment types.
     */
    public EnchantmentType(final String enumName, final String stringID, final int numericID, final Predicate<EnchantmentType> isConflicting)
    {
        super(enumName);
        this.stringID = stringID;
        this.numericID = (short) numericID;
        this.isConflicting = isConflicting;
    }

    /**
     * Returns string id of this enchantment type.
     *
     * @return string id of this enchantment type.
     */
    public String getStringID()
    {
        return this.stringID;
    }

    /**
     * Returns numeric id of this enchantment type.
     *
     * @return numeric id of this enchantment type.
     */
    public int getNumericID()
    {
        return this.numericID;
    }

    /**
     * Returns the maximum level that this Enchantment may become.
     *
     * @param itemStack      item stack to check, may be null.
     * @param onEnchantTable if level should be checked for enchant table enchantments.
     *
     * @return the maximum level that this Enchantment may become.
     */
    public int getMaxLevel(final ItemStack itemStack, final boolean onEnchantTable)
    {
        return 3; // TODO
    }

    /**
     * Check if this enchantment conflicts with given one.
     *
     * @param enchantment enchantment to be checked.
     *
     * @return true if this enchantment conflicts with given one.
     */
    public boolean conflictsWith(final EnchantmentType enchantment)
    {
        return this.isConflicting.test(enchantment); // TODO
    }

    /**
     * Register new {@link EnchantmentType} entry in this enum.
     *
     * @param element new element to register.
     */
    public static void register(final EnchantmentType element)
    {
        ASimpleEnum.register(EnchantmentType.class, element);
        byNumericType.put((short) element.getNumericID(), element);
        byStringID.put(element.getStringID(), element);
    }

    /**
     * Get one of {@link EnchantmentType} entry by its ordinal id.
     *
     * @param ordinal ordinal id of entry.
     *
     * @return one of entry or null.
     */
    public static EnchantmentType getByEnumOrdinal(final int ordinal)
    {
        return getByEnumOrdinal(EnchantmentType.class, ordinal);
    }

    /**
     * Get one of EnchantmentType entry by its name.
     *
     * @param name name of entry.
     *
     * @return one of entry or null.
     */
    public static EnchantmentType getByEnumName(final String name)
    {
        return getByEnumName(EnchantmentType.class, name);
    }

    /**
     * Get one of EnchantmentType entry by its numeric id.
     *
     * @param id id of entry.
     *
     * @return one of entry or null.
     */
    public static EnchantmentType getByNumericID(final int id)
    {
        return byNumericType.get((short) id);
    }

    /**
     * Get one of EnchantmentType entry by its string id.
     *
     * @param id id of entry.
     *
     * @return one of entry or null.
     */
    public static EnchantmentType getByStringID(final String id)
    {
        return byStringID.get(id);
    }

    /**
     * @return all values in array.
     */
    public static EnchantmentType[] values()
    {
        final TIntObjectMap<SimpleEnum<?>> map = getByEnumOrdinal(EnchantmentType.class);
        return (EnchantmentType[]) map.values(new EnchantmentType[map.size()]);
    }

    static
    {
        EnchantmentType.register(PROTECTION);
        EnchantmentType.register(FIRE_PROTECTION);
        EnchantmentType.register(FEATHER_FALLING);
        EnchantmentType.register(BLAST_PROTECTION);
        EnchantmentType.register(PROJECTILE_PROTECTION);
        EnchantmentType.register(RESPIRATION);
        EnchantmentType.register(AQUA_AFFINITY);
        EnchantmentType.register(THORNS);
        EnchantmentType.register(DEPTH_STRIDER);
        EnchantmentType.register(FROST_WALKER);
        EnchantmentType.register(SHARPNESS);
        EnchantmentType.register(SMITE);
        EnchantmentType.register(BANE_OF_ARTHROPODS);
        EnchantmentType.register(KNOCKBACK);
        EnchantmentType.register(FIRE_ASPECT);
        EnchantmentType.register(LOOTING);
        EnchantmentType.register(EFFICIENCY);
        EnchantmentType.register(SILK_TOUCH);
        EnchantmentType.register(UNBREAKING);
        EnchantmentType.register(FORTUNE);
        EnchantmentType.register(POWER);
        EnchantmentType.register(PUNCH);
        EnchantmentType.register(FLAME);
        EnchantmentType.register(INFINITY);
        EnchantmentType.register(LUCK_OF_THE_SEA);
        EnchantmentType.register(LURE);
        EnchantmentType.register(MENDING);
    }
}