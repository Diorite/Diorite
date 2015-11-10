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

import org.diorite.material.ToolMaterial;
import org.diorite.material.ToolType;
import org.diorite.utils.collections.maps.CaseInsensitiveMap;
import org.diorite.utils.lazy.LazyValue;
import org.diorite.utils.math.DioriteMathUtils;

import gnu.trove.map.TShortObjectMap;
import gnu.trove.map.hash.TShortObjectHashMap;

/**
 * Class representing 'Diamond Pickaxe' item material in minecraft. <br>
 * ID of material: 278 <br>
 * String ID of material: minecraft:diamond_Pickaxe <br>
 * Max item stack size: 1
 */
@SuppressWarnings({"JavaDoc", "ClassHasNoToStringMethod"})
public class DiamondPickaxeMat extends PickaxeMat
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final int USED_DATA_VALUES = 1;

    public static final DiamondPickaxeMat DIAMOND_PICKAXE = new DiamondPickaxeMat();

    private static final Map<String, DiamondPickaxeMat>     byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TShortObjectMap<DiamondPickaxeMat> byID   = new TShortObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR, Short.MIN_VALUE);

    protected final LazyValue<DiamondPickaxeMat> next = new LazyValue<>(() -> (this.haveValidDurability()) ? getByDurability(this.getDurability() + 1) : null);
    protected final LazyValue<DiamondPickaxeMat> prev = new LazyValue<>(() -> (this.haveValidDurability()) ? getByDurability(this.getDurability() - 1) : null);

    @SuppressWarnings("MagicNumber")
    protected DiamondPickaxeMat()
    {
        super("DIAMOND_PICKAXE", 278, "minecraft:diamond_Pickaxe", "NEW", (short) 0, ToolMaterial.DIAMOND);
    }

    protected DiamondPickaxeMat(final int durability)
    {
        super(DIAMOND_PICKAXE.name(), DIAMOND_PICKAXE.getId(), DIAMOND_PICKAXE.getMinecraftId(), Integer.toString(durability), (short) durability, ToolMaterial.DIAMOND);
    }

    protected DiamondPickaxeMat(final String enumName, final int id, final String minecraftId, final String typeName, final short type, final ToolMaterial toolMaterial, final ToolType toolType)
    {
        super(enumName, id, minecraftId, typeName, type, toolMaterial, toolType);
    }

    protected DiamondPickaxeMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final short type, final ToolMaterial toolMaterial, final ToolType toolType)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, toolMaterial, toolType);
    }

    @Override
    public DiamondPickaxeMat[] types()
    {
        return new DiamondPickaxeMat[]{DIAMOND_PICKAXE};
    }

    @Override
    public DiamondPickaxeMat getType(final String type)
    {
        return getByEnumName(type);
    }

    @Override
    public DiamondPickaxeMat getType(final int type)
    {
        return getByID(type);
    }

    @Override
    public DiamondPickaxeMat increaseDurability()
    {
        return this.next.get();
    }

    @Override
    public DiamondPickaxeMat decreaseDurability()
    {
        return this.prev.get();
    }

    @Override
    public DiamondPickaxeMat setDurability(final int durability)
    {
        return this.getType(durability);
    }

    /**
     * Returns one of DiamondPickaxe sub-type based on sub-id.
     *
     * @param id sub-type id
     *
     * @return sub-type of DiamondPickaxe.
     */
    public static DiamondPickaxeMat getByID(final int id)
    {
        DiamondPickaxeMat mat = byID.get((short) id);
        if (mat == null)
        {
            mat = new DiamondPickaxeMat(id);
            if ((id > 0) && (id < DIAMOND_PICKAXE.getBaseDurability()))
            {
                DiamondPickaxeMat.register(mat);
            }
        }
        return mat;
    }

    /**
     * Returns one of DiamondPickaxe sub-type based on durability.
     *
     * @param id durability of type.
     *
     * @return sub-type of DiamondPickaxe.
     */
    public static DiamondPickaxeMat getByDurability(final int id)
    {
        return getByID(id);
    }

    /**
     * Returns one of DiamondPickaxe-type based on name (selected by diorite team).
     * If item contains only one type, sub-name of it will be this same as name of material.<br>
     * Returns null if name can't be parsed to int and it isn't "NEW" one.
     *
     * @param name name of sub-type
     *
     * @return sub-type of DiamondPickaxe.
     */
    public static DiamondPickaxeMat getByEnumName(final String name)
    {
        final DiamondPickaxeMat mat = byName.get(name);
        if (mat == null)
        {
            final Integer idType = DioriteMathUtils.asInt(name);
            if (idType == null)
            {
                return null;
            }
            return getByID(idType);
        }
        return mat;
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final DiamondPickaxeMat element)
    {
        byID.put(element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    /**
     * @return array that contains all sub-types of this item.
     */
    public static DiamondPickaxeMat[] diamondPickaxeTypes()
    {
        return new DiamondPickaxeMat[]{DIAMOND_PICKAXE};
    }

    static
    {
        DiamondPickaxeMat.register(DIAMOND_PICKAXE);
    }
}
