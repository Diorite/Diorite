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

package org.diorite.material.blocks;

import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.material.BlockMaterialData;
import org.diorite.utils.collections.maps.CaseInsensitiveMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing 'Monster Egg Trap' block material in minecraft. <br>
 * ID of block: 97 <br>
 * String ID of block: minecraft:monster_egg <br>
 * Hardness: 0,75 <br>
 * Blast Resistance 3,75 <br>
 * <br>
 * Subtypes: <br>
 * <ol>
 * <li>
 * STONE_BRICK_CHISELED:
 * Type name: 'Stone Brick Chiseled' <br>
 * SubID: 5 <br>
 * Hardness: 0,75 <br>
 * Blast Resistance 3,75 <br>
 * </li>
 * <li>
 * STONE_BRICK_CRACKED:
 * Type name: 'Stone Brick Cracked' <br>
 * SubID: 4 <br>
 * Hardness: 0,75 <br>
 * Blast Resistance 3,75 <br>
 * </li>
 * <li>
 * STONE_BRICK_MOSSY:
 * Type name: 'Stone Brick Mossy' <br>
 * SubID: 3 <br>
 * Hardness: 0,75 <br>
 * Blast Resistance 3,75 <br>
 * </li>
 * <li>
 * STONE_BRICK:
 * Type name: 'Stone Brick' <br>
 * SubID: 2 <br>
 * Hardness: 0,75 <br>
 * Blast Resistance 3,75 <br>
 * </li>
 * <li>
 * COBBLESTONE:
 * Type name: 'Cobblestone' <br>
 * SubID: 1 <br>
 * Hardness: 0,75 <br>
 * Blast Resistance 3,75 <br>
 * </li>
 * <li>
 * STONE:
 * Type name: 'Stone' <br>
 * SubID: 0 <br>
 * Hardness: 0,75 <br>
 * Blast Resistance 3,75 <br>
 * </li>
 * </ol>
 */
@SuppressWarnings("JavaDoc")
public class MonsterEggTrapMat extends StonyMat
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final int USED_DATA_VALUES = 6;

    public static final MonsterEggTrapMat MONSTER_EGG_TRAP_STONE                = new MonsterEggTrapMat();
    public static final MonsterEggTrapMat MONSTER_EGG_TRAP_COBBLESTONE          = new MonsterEggTrapMat("COBBLESTONE", 0x1, CobblestoneMat.COBBLESTONE);
    public static final MonsterEggTrapMat MONSTER_EGG_TRAP_STONE_BRICK          = new MonsterEggTrapMat("STONE_BRICK", 0x2, StoneBrickMat.STONE_BRICK);
    public static final MonsterEggTrapMat MONSTER_EGG_TRAP_STONE_BRICK_MOSSY    = new MonsterEggTrapMat("STONE_BRICK_MOSSY", 0x3, StoneBrickMat.STONE_BRICK_MOSSY);
    public static final MonsterEggTrapMat MONSTER_EGG_TRAP_STONE_BRICK_CRACKED  = new MonsterEggTrapMat("STONE_BRICK_CRACKED", 0x4, StoneBrickMat.STONE_BRICK_CRACKED);
    public static final MonsterEggTrapMat MONSTER_EGG_TRAP_STONE_BRICK_CHISELED = new MonsterEggTrapMat("STONE_BRICK_CHISELED", 0x5, StoneBrickMat.STONE_BRICK_CHISELED);

    private static final Map<String, MonsterEggTrapMat>    byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<MonsterEggTrapMat> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR, Byte.MIN_VALUE);

    protected final BlockMaterialData block;

    @SuppressWarnings("MagicNumber")
    protected MonsterEggTrapMat()
    {
        super("MONSTER_EGG_TRAP", 97, "minecraft:monster_egg", "STONE", (byte) 0x00, 0.75f, 3.75f);
        this.block = StoneMat.STONE;
    }

    protected MonsterEggTrapMat(final String enumName, final int type, final BlockMaterialData block)
    {
        super(MONSTER_EGG_TRAP_STONE.name(), MONSTER_EGG_TRAP_STONE.ordinal(), MONSTER_EGG_TRAP_STONE.getMinecraftId(), enumName, (byte) type, MONSTER_EGG_TRAP_STONE.getHardness(), MONSTER_EGG_TRAP_STONE.getBlastResistance());
        this.block = block;
    }

    protected MonsterEggTrapMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type, final BlockMaterialData block, final float hardness, final float blastResistance)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, hardness, blastResistance);
        this.block = block;
    }

    /**
     * @return type of fake-block. (used textures)
     */
    public BlockMaterialData getBlock()
    {
        return this.block;
    }

    /**
     * Returns sub-type of MonsterEggTrap based on type of fake-block
     *
     * @param block type of fake block.
     *
     * @return sub-type of MonsterEggTrap
     */
    public MonsterEggTrapMat getBlock(final BlockMaterialData block)
    {
        for (final MonsterEggTrapMat v : byName.values())
        {
            if (v.block.equals(block))
            {
                return v;
            }
        }
        return null;
    }

    @Override
    public MonsterEggTrapMat getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public MonsterEggTrapMat getType(final int id)
    {
        return getByID(id);
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("block", this.block).toString();
    }

    /**
     * Returns one of MonsterEggTrap sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of MonsterEggTrap or null
     */
    public static MonsterEggTrapMat getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of MonsterEggTrap sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of MonsterEggTrap or null
     */
    public static MonsterEggTrapMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Returns sub-type of MonsterEggTrap based on type of fake-block
     *
     * @param block type of fake block.
     *
     * @return sub-type of MonsterEggTrap
     */
    public static MonsterEggTrapMat getMonsterEggTrap(final BlockMaterialData block)
    {
        for (final MonsterEggTrapMat v : byName.values())
        {
            if (v.block.equals(block))
            {
                return v;
            }
        }
        return null;
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final MonsterEggTrapMat element)
    {
        byID.put((byte) element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    @Override
    public MonsterEggTrapMat[] types()
    {
        return MonsterEggTrapMat.monsterEggTrapTypes();
    }

    /**
     * @return array that contains all sub-types of this block.
     */
    public static MonsterEggTrapMat[] monsterEggTrapTypes()
    {
        return byID.values(new MonsterEggTrapMat[byID.size()]);
    }

    static
    {
        MonsterEggTrapMat.register(MONSTER_EGG_TRAP_STONE);
        MonsterEggTrapMat.register(MONSTER_EGG_TRAP_COBBLESTONE);
        MonsterEggTrapMat.register(MONSTER_EGG_TRAP_STONE_BRICK);
        MonsterEggTrapMat.register(MONSTER_EGG_TRAP_STONE_BRICK_MOSSY);
        MonsterEggTrapMat.register(MONSTER_EGG_TRAP_STONE_BRICK_CRACKED);
        MonsterEggTrapMat.register(MONSTER_EGG_TRAP_STONE_BRICK_CHISELED);
    }
}
