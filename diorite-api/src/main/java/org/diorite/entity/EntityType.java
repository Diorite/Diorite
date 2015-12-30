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

package org.diorite.entity;

import org.diorite.utils.SimpleEnum.ASimpleEnum;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;

@SuppressWarnings({"ClassHasNoToStringMethod"})
public class EntityType extends ASimpleEnum<EntityType>
{
    static
    {
        init(EntityType.class, 3);
    }

    public static final EntityType PLAYER         = new EntityType("PLAYER", Player.class, - 1, "Player");
    public static final EntityType CREEPER        = new EntityType("CREEPER", Creeper.class, 1, "Creeper");
    public static final EntityType SKELETON       = new EntityType("SKELETON", Skeleton.class, 51, "Skeleton");
    public static final EntityType SPIDER         = new EntityType("SPIDER", Spider.class, 52, "Spider");
    public static final EntityType GIANT          = new EntityType("GIANT", Giant.class, 53, "Giant");
    public static final EntityType ZOMBIE         = new EntityType("ZOMBIE", Zombie.class, 54, "Zombie");
    public static final EntityType SLIME          = new EntityType("SLIME", Slime.class, 55, "Slime");
    public static final EntityType GHAST          = new EntityType("GHAST", Ghast.class, 56, "Ghast");
    public static final EntityType ZOMBIE_PIGMAN  = new EntityType("PIG_ZOMBIE", PigZombie.class, 57, "PigZombie");
    public static final EntityType ENDERMAN       = new EntityType("ENDERMAN", Enderman.class, 58, "Enderman");
    public static final EntityType CAVE_SPIDER    = new EntityType("CAVE_SPIDER", CaveSpider.class, 59, "CaveSpider");
    public static final EntityType SILVERFISH     = new EntityType("SILVERFISH", Silverfish.class, 60, "Silverfish");
    public static final EntityType BLAZE          = new EntityType("BLAZE", Blaze.class, 61, "Blaze");
    public static final EntityType LAVA_SLIME     = new EntityType("LAVA_SLIME", LaveSlime.class, 62, "LavaSlime");
    public static final EntityType ENDER_DRAGON   = new EntityType("ENDER_DRAGON", EnderDragon.class, 63, "EnderDragon");
    public static final EntityType WITHER         = new EntityType("WITHER", Wither.class, 64, "WitherBoss");
    public static final EntityType BAT            = new EntityType("BAT", Bat.class, 65, "Bat");
    public static final EntityType WITCH          = new EntityType("WITCH", Witch.class, 66, "Witch");
    public static final EntityType ENDERMITE      = new EntityType("ENDERMITE", Endermite.class, 67, "Endermite");
    public static final EntityType GUARDIAN       = new EntityType("GUARDIAN", Guardian.class, 68, "Guardian");
    public static final EntityType SHULKER        = new EntityType("SHULKER", Shulker.class, 69, "Shulker");
    public static final EntityType PIG            = new EntityType("PIG", Pig.class, 90, "Pig");
    public static final EntityType SHEEP          = new EntityType("SHEEP", Sheep.class, 91, "Sheep");
    public static final EntityType COW            = new EntityType("COW", Cow.class, 92, "Cow");
    public static final EntityType CHICKEN        = new EntityType("CHICKEN", Chicken.class, 93, "Chicken");
    public static final EntityType SQUID          = new EntityType("SQUID", Squid.class, 94, "Squid");
    public static final EntityType WOLF           = new EntityType("WOLF", Wolf.class, 95, "Wolf");
    public static final EntityType MOOSHROOM_COW  = new EntityType("MOOSHROOM_COW", MooshroomCow.class, 96, "MooshroomCow");
    public static final EntityType SNOWMAN        = new EntityType("SNOWMAN", Snowman.class, 97, "SnowMan");
    public static final EntityType OCELOT         = new EntityType("OCELOT", Ocelot.class, 98, "Ozelot");
    public static final EntityType VILLAGER_GOLEM = new EntityType("VILLAGER_GOLEM", VillagerGolem.class, 99, "VillagerGolem");
    public static final EntityType HORSE          = new EntityType("HORSE", Horse.class, 100, "EntityHorse");
    public static final EntityType RABBIT         = new EntityType("RABBIT", Rabbit.class, 101, "Rabbit");
    public static final EntityType VILLAGER       = new EntityType("VILLAGER", Villager.class, 120, "Villager");

    private static final Int2ObjectMap<EntityType> byMcId = new Int2ObjectOpenHashMap<>(3, .1f);

    private final Class<? extends Entity> dioriteEntityClass;
    private final boolean                 living;
    private final int                     mcId;
    private final String                  mcName;

    public EntityType(final String enumName, final int enumId, final Class<? extends Entity> dioriteEntityClass, final boolean living, final int mcId, final String mcName)
    {
        super(enumName, enumId);
        this.dioriteEntityClass = dioriteEntityClass;
        this.living = living;
        this.mcId = mcId;
        this.mcName = mcName;
    }

    public EntityType(final String enumName, final Class<? extends Entity> dioriteEntityClass, final int mcId, final String mcName)
    {
        super(enumName);
        this.dioriteEntityClass = dioriteEntityClass;
        this.living = LivingEntity.class.isAssignableFrom(dioriteEntityClass);
        this.mcId = mcId;
        this.mcName = mcName;
    }

    public String getMcName()
    {
        return this.mcName;
    }

    public int getMinecraftId()
    {
        return this.mcId;
    }

    public boolean isLiving()
    {
        return this.living;
    }

    public Class<? extends Entity> getDioriteEntityClass()
    {
        return this.dioriteEntityClass;
    }

    /**
     * Register new {@link EntityType} entry in this enum.
     *
     * @param element new element to register.
     */
    public static void register(final EntityType element)
    {
        ASimpleEnum.register(EntityType.class, element);
        byMcId.put(element.getMinecraftId(), element);
    }

    /**
     * Get one of {@link EntityType} entry by its ordinal id.
     *
     * @param ordinal ordinal id of entry.
     *
     * @return one of entry or null.
     */
    public static EntityType getByEnumOrdinal(final int ordinal)
    {
        return getByEnumOrdinal(EntityType.class, ordinal);
    }

    /**
     * Get one of {@link EntityType} entry by its mc id.
     *
     * @param id mc id of entry.
     *
     * @return one of entry or null.
     */
    public static EntityType getByMinecraftId(final int id)
    {
        return byMcId.get(id);
    }

    /**
     * Get one of {@link EntityType} entry by its name.
     *
     * @param name name of entry.
     *
     * @return one of entry or null.
     */
    public static EntityType getByEnumName(final String name)
    {
        return getByEnumName(EntityType.class, name);
    }

    /**
     * @return all values in array.
     */
    public static EntityType[] values()
    {
        final Int2ObjectMap<EntityType> map = getByEnumOrdinal(EntityType.class);
        return map.values().toArray(new EntityType[map.size()]);
    }

    static
    {
        EntityType.register(PLAYER);
        EntityType.register(CREEPER);
        EntityType.register(SKELETON);
        EntityType.register(SPIDER);
        EntityType.register(GIANT);
        EntityType.register(ZOMBIE);
        EntityType.register(SLIME);
        EntityType.register(GHAST);
        EntityType.register(ZOMBIE_PIGMAN);
        EntityType.register(ENDERMAN);
        EntityType.register(CAVE_SPIDER);
        EntityType.register(SILVERFISH);
        EntityType.register(BLAZE);
        EntityType.register(LAVA_SLIME);
        EntityType.register(ENDER_DRAGON);
        EntityType.register(WITHER);
        EntityType.register(BAT);
        EntityType.register(WITCH);
        EntityType.register(ENDERMITE);
        EntityType.register(GUARDIAN);
        EntityType.register(SHULKER);
        EntityType.register(PIG);
        EntityType.register(SHEEP);
        EntityType.register(COW);
        EntityType.register(CHICKEN);
        EntityType.register(SQUID);
        EntityType.register(WOLF);
        EntityType.register(MOOSHROOM_COW);
        EntityType.register(SNOWMAN);
        EntityType.register(OCELOT);
        EntityType.register(VILLAGER_GOLEM);
        EntityType.register(HORSE);
        EntityType.register(RABBIT);
        EntityType.register(VILLAGER);
    }
}
