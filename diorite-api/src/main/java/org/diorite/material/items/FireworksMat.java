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

import org.diorite.inventory.item.meta.FireworkMeta;
import org.diorite.material.ItemMaterialData;
import org.diorite.material.PlaceableEntityMat;
import org.diorite.utils.collections.maps.CaseInsensitiveMap;

import it.unimi.dsi.fastutil.shorts.Short2ObjectMap;
import it.unimi.dsi.fastutil.shorts.Short2ObjectOpenHashMap;

/**
 * Class representing 'Fireworks' item material in minecraft. <br>
 * ID of material: 401 <br>
 * String ID of material: minecraft:fireworks <br>
 * Max item stack size: 64
 */
@SuppressWarnings("JavaDoc")
public class FireworksMat extends ItemMaterialData implements PlaceableEntityMat
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final int USED_DATA_VALUES = 1;

    public static final FireworksMat FIREWORKS = new FireworksMat();

    private static final Map<String, FireworksMat>     byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final Short2ObjectMap<FireworksMat> byID   = new Short2ObjectOpenHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    @SuppressWarnings("MagicNumber")
    protected FireworksMat()
    {
        super("FIREWORKS", 401, "minecraft:fireworks", "FIREWORKS", (short) 0x00);
    }

    protected FireworksMat(final String enumName, final int id, final String minecraftId, final String typeName, final short type)
    {
        super(enumName, id, minecraftId, typeName, type);
    }

    protected FireworksMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final short type)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type);
    }

    {
        this.metaType = FireworkMeta.class;
    }

    @Override
    public FireworksMat getType(final int type)
    {
        return getByID(type);
    }

    @Override
    public FireworksMat getType(final String type)
    {
        return getByEnumName(type);
    }

    /**
     * Returns one of Fireworks sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of Fireworks or null
     */
    public static FireworksMat getByID(final int id)
    {
        return byID.get((short) id);
    }

    /**
     * Returns one of Fireworks sub-type based on name (selected by diorite team), may return null
     * If item contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of Fireworks or null
     */
    public static FireworksMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final FireworksMat element)
    {
        byID.put((short) element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    @Override
    public FireworksMat[] types()
    {
        return FireworksMat.fireworksTypes();
    }

    /**
     * @return array that contains all sub-types of this item.
     */
    public static FireworksMat[] fireworksTypes()
    {
        return byID.values().toArray(new FireworksMat[byID.size()]);
    }

    static
    {
        FireworksMat.register(FIREWORKS);
    }
}

