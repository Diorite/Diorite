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

package org.diorite.material.items;

import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.entity.EntityType;
import org.diorite.material.ItemMaterialData;
import org.diorite.utils.collections.maps.CaseInsensitiveMap;
import org.diorite.utils.collections.maps.SimpleEnumMap;

import gnu.trove.map.TShortObjectMap;
import gnu.trove.map.hash.TShortObjectHashMap;

/**
 * Class representing 'Spawn Egg Creeper' item material in minecraft. <br>
 * ID of material: 383 <br>
 * String ID of material: minecraft:spawn_egg <br>
 * Max item stack size: 64
 */
@SuppressWarnings("JavaDoc")
public class SpawnEggMat extends ItemMaterialData
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final int USED_DATA_VALUES = 1;

    public static final SpawnEggMat SPAWN_EGG_CREEPER = new SpawnEggMat();
    // TODO: add more

    private static final Map<String, SpawnEggMat>     byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TShortObjectMap<SpawnEggMat> byID   = new TShortObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR, Short.MIN_VALUE);


    private static final SimpleEnumMap<EntityType, SkullMat> byType = new SimpleEnumMap<>(5);

    protected final EntityType entityType;

    @SuppressWarnings("MagicNumber")
    protected SpawnEggMat()
    {
        super("SPAWN_EGG_CREEPER", 383, "minecraft:spawn_egg", "CREEPER", (short) 0x50);
        this.entityType = EntityType.CREEPER;
    }

    protected SpawnEggMat(final EntityType type)
    {
        super(SPAWN_EGG_CREEPER.name(), SPAWN_EGG_CREEPER.getId(), SPAWN_EGG_CREEPER.getMinecraftId(), type.name(), (short) type.getMinecraftId());
        this.entityType = type;
    }

    protected SpawnEggMat(final String enumName, final int id, final String minecraftId, final String typeName, final short type, final EntityType entityType)
    {
        super(enumName, id, minecraftId, typeName, type);
        this.entityType = entityType;
    }

    protected SpawnEggMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final short type, final EntityType entityType)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type);
        this.entityType = entityType;
    }

    @Override
    public SpawnEggMat getType(final int type)
    {
        return getByID(type);
    }

    @Override
    public SpawnEggMat getType(final String type)
    {
        return getByEnumName(type);
    }

    /**
     * Returns one of SpawnEgg sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of SpawnEgg or null
     */
    public static SpawnEggMat getByID(final int id)
    {
        return byID.get((short) id);
    }

    /**
     * Returns one of SpawnEgg sub-type based on name (selected by diorite team), may return null
     * If item contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of SpawnEgg or null
     */
    public static SpawnEggMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final SpawnEggMat element)
    {
        byID.put(element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    @Override
    public SpawnEggMat[] types()
    {
        return SpawnEggMat.spawnEggTypes();
    }

    /**
     * @return array that contains all sub-types of this item.
     */
    public static SpawnEggMat[] spawnEggTypes()
    {
        return byID.values(new SpawnEggMat[byID.size()]);
    }

    static
    {
        SpawnEggMat.register(SPAWN_EGG_CREEPER);
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("entityType", this.entityType).toString();
    }
}

