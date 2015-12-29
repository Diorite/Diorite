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
public class ObjectType extends ASimpleEnum<ObjectType>
{
    static
    {
        init(ObjectType.class, 3);
    }

    public static final ObjectType BOAT              = new ObjectType("BOAT", Boat.class, 1, "Boat");
    public static final ObjectType ITEM              = new ObjectType("ITEM", Item.class, 2, "Item");
    public static final ObjectType AREA_EFFECT_CLOUD = new ObjectType("AREA_EFFECT_CLOUD", AreaEffectCloud.class, 3, "Area Effect Cloud");
    public static final ObjectType MINECART          = new ObjectType("MINECART", Minecart.class, 10, "Minecart");
    public static final ObjectType ZOMBIE            = new ObjectType("ZOMBIE", Zombie.class, 54, "Zombie");
    public static final ObjectType ZOMBIE            = new ObjectType("ZOMBIE", Zombie.class, 54, "Zombie");
    public static final ObjectType ZOMBIE            = new ObjectType("ZOMBIE", Zombie.class, 54, "Zombie");
    public static final ObjectType ZOMBIE            = new ObjectType("ZOMBIE", Zombie.class, 54, "Zombie");
    public static final ObjectType ZOMBIE            = new ObjectType("ZOMBIE", Zombie.class, 54, "Zombie");
    public static final ObjectType ZOMBIE            = new ObjectType("ZOMBIE", Zombie.class, 54, "Zombie");
    public static final ObjectType ZOMBIE            = new ObjectType("ZOMBIE", Zombie.class, 54, "Zombie");
    public static final ObjectType ZOMBIE            = new ObjectType("ZOMBIE", Zombie.class, 54, "Zombie");
    public static final ObjectType ZOMBIE            = new ObjectType("ZOMBIE", Zombie.class, 54, "Zombie");
    public static final ObjectType ZOMBIE            = new ObjectType("ZOMBIE", Zombie.class, 54, "Zombie");
    public static final ObjectType ZOMBIE            = new ObjectType("ZOMBIE", Zombie.class, 54, "Zombie");
    public static final ObjectType ZOMBIE            = new ObjectType("ZOMBIE", Zombie.class, 54, "Zombie");
    public static final ObjectType ZOMBIE            = new ObjectType("ZOMBIE", Zombie.class, 54, "Zombie");
    public static final ObjectType ZOMBIE            = new ObjectType("ZOMBIE", Zombie.class, 54, "Zombie");
    public static final ObjectType ZOMBIE            = new ObjectType("ZOMBIE", Zombie.class, 54, "Zombie");
    public static final ObjectType ZOMBIE            = new ObjectType("ZOMBIE", Zombie.class, 54, "Zombie");
    public static final ObjectType ZOMBIE            = new ObjectType("ZOMBIE", Zombie.class, 54, "Zombie");
    public static final ObjectType ZOMBIE            = new ObjectType("ZOMBIE", Zombie.class, 54, "Zombie");
    public static final ObjectType ZOMBIE            = new ObjectType("ZOMBIE", Zombie.class, 54, "Zombie");
    public static final ObjectType ZOMBIE            = new ObjectType("ZOMBIE", Zombie.class, 54, "Zombie");
    public static final ObjectType ZOMBIE            = new ObjectType("ZOMBIE", Zombie.class, 54, "Zombie");
    public static final ObjectType ZOMBIE            = new ObjectType("ZOMBIE", Zombie.class, 54, "Zombie");
    public static final ObjectType ZOMBIE            = new ObjectType("ZOMBIE", Zombie.class, 54, "Zombie");
    public static final ObjectType ZOMBIE            = new ObjectType("ZOMBIE", Zombie.class, 54, "Zombie");
    public static final ObjectType ZOMBIE            = new ObjectType("ZOMBIE", Zombie.class, 54, "Zombie");
    public static final ObjectType ZOMBIE            = new ObjectType("ZOMBIE", Zombie.class, 54, "Zombie");
    public static final ObjectType ZOMBIE            = new ObjectType("ZOMBIE", Zombie.class, 54, "Zombie");
    public static final ObjectType ZOMBIE            = new ObjectType("ZOMBIE", Zombie.class, 54, "Zombie");
    public static final ObjectType ZOMBIE            = new ObjectType("ZOMBIE", Zombie.class, 54, "Zombie");
    public static final ObjectType ZOMBIE            = new ObjectType("ZOMBIE", Zombie.class, 54, "Zombie");
    public static final ObjectType ZOMBIE            = new ObjectType("ZOMBIE", Zombie.class, 54, "Zombie");
    public static final ObjectType ZOMBIE            = new ObjectType("ZOMBIE", Zombie.class, 54, "Zombie");
    public static final ObjectType ZOMBIE            = new ObjectType("ZOMBIE", Zombie.class, 54, "Zombie");
    public static final ObjectType ZOMBIE            = new ObjectType("ZOMBIE", Zombie.class, 54, "Zombie");
    public static final ObjectType ZOMBIE            = new ObjectType("ZOMBIE", Zombie.class, 54, "Zombie");
    public static final ObjectType ZOMBIE            = new ObjectType("ZOMBIE", Zombie.class, 54, "Zombie");
    public static final ObjectType ZOMBIE            = new ObjectType("ZOMBIE", Zombie.class, 54, "Zombie");
    public static final ObjectType ZOMBIE            = new ObjectType("ZOMBIE", Zombie.class, 54, "Zombie");
    public static final ObjectType ZOMBIE            = new ObjectType("ZOMBIE", Zombie.class, 54, "Zombie");
    public static final ObjectType ZOMBIE            = new ObjectType("ZOMBIE", Zombie.class, 54, "Zombie");
    public static final ObjectType ZOMBIE            = new ObjectType("ZOMBIE", Zombie.class, 54, "Zombie");
    public static final ObjectType ZOMBIE            = new ObjectType("ZOMBIE", Zombie.class, 54, "Zombie");
    public static final ObjectType ZOMBIE            = new ObjectType("ZOMBIE", Zombie.class, 54, "Zombie");
    public static final ObjectType ZOMBIE            = new ObjectType("ZOMBIE", Zombie.class, 54, "Zombie");
    public static final ObjectType ZOMBIE            = new ObjectType("ZOMBIE", Zombie.class, 54, "Zombie");
    public static final ObjectType ZOMBIE            = new ObjectType("ZOMBIE", Zombie.class, 54, "Zombie");
    public static final ObjectType ZOMBIE            = new ObjectType("ZOMBIE", Zombie.class, 54, "Zombie");
    // TODO

    private static final Int2ObjectMap<ObjectType> byMcId = new Int2ObjectOpenHashMap<>(3, .1f);

    private final Class<? extends Entity> dioriteEntityClass;
    private final boolean                 living;
    private final int                     mcId;
    private final String                  mcName;

    public ObjectType(final String enumName, final int enumId, final Class<? extends Entity> dioriteEntityClass, final boolean living, final int mcId, final String mcName)
    {
        super(enumName, enumId);
        this.dioriteEntityClass = dioriteEntityClass;
        this.living = living;
        this.mcId = mcId;
        this.mcName = mcName;
    }

    public ObjectType(final String enumName, final Class<? extends Entity> dioriteEntityClass, final int mcId, final String mcName)
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
     * Register new {@link ObjectType} entry in this enum.
     *
     * @param element new element to register.
     */
    public static void register(final ObjectType element)
    {
        ASimpleEnum.register(ObjectType.class, element);
        byMcId.put(element.getMinecraftId(), element);
    }

    /**
     * Get one of {@link ObjectType} entry by its ordinal id.
     *
     * @param ordinal ordinal id of entry.
     *
     * @return one of entry or null.
     */
    public static ObjectType getByEnumOrdinal(final int ordinal)
    {
        return getByEnumOrdinal(ObjectType.class, ordinal);
    }

    /**
     * Get one of {@link ObjectType} entry by its mc id.
     *
     * @param id mc id of entry.
     *
     * @return one of entry or null.
     */
    public static ObjectType getByMinecraftId(final int id)
    {
        return byMcId.get(id);
    }

    /**
     * Get one of {@link ObjectType} entry by its name.
     *
     * @param name name of entry.
     *
     * @return one of entry or null.
     */
    public static ObjectType getByEnumName(final String name)
    {
        return getByEnumName(ObjectType.class, name);
    }

    /**
     * @return all values in array.
     */
    public static ObjectType[] values()
    {
        final Int2ObjectMap<ObjectType> map = getByEnumOrdinal(ObjectType.class);
        return map.values().toArray(new ObjectType[map.size()]);
    }

    static
    {
        ObjectType.register(PLAYER);
        ObjectType.register(ITEM);
        ObjectType.register(CREEPER);
    }
}
