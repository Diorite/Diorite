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
 * Class representing 'Command Block' block material in minecraft. <br>
 * ID of block: 137 <br>
 * String ID of block: minecraft:command_block <br>
 * Hardness: -1 <br>
 * Blast Resistance 18000000 <br>
 * <br>
 * Subtypes: <br>
 * <ol>
 * <li>
 * TRIGGERED:
 * Type name: 'Triggered' <br>
 * SubID: 1 <br>
 * Hardness: -1 <br>
 * Blast Resistance 18000000 <br>
 * </li>
 * <li>
 * COMMAND_BLOCK:
 * Type name: 'Command Block' <br>
 * SubID: 0 <br>
 * Hardness: -1 <br>
 * Blast Resistance 18000000 <br>
 * </li>
 * </ol>
 */
@SuppressWarnings("JavaDoc")
public class CommandBlockMat extends BlockMaterialData
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final int USED_DATA_VALUES = 2;

    public static final CommandBlockMat COMMAND_BLOCK           = new CommandBlockMat();
    public static final CommandBlockMat COMMAND_BLOCK_TRIGGERED = new CommandBlockMat(true);

    private static final Map<String, CommandBlockMat>    byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<CommandBlockMat> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR, Byte.MIN_VALUE);

    protected final boolean triggered;

    @SuppressWarnings("MagicNumber")
    protected CommandBlockMat()
    {
        super("COMMAND_BLOCK", 137, "minecraft:command_block", "COMMAND_BLOCK", (byte) 0x00, - 1, 18_000_000);
        this.triggered = false;
    }

    protected CommandBlockMat(final boolean triggered)
    {
        super(COMMAND_BLOCK.name(), COMMAND_BLOCK.ordinal(), COMMAND_BLOCK.getMinecraftId(), triggered ? "TRIGGERED" : "COMMAND_BLOCK", (byte) (triggered ? 1 : 0), COMMAND_BLOCK.getHardness(), COMMAND_BLOCK.getBlastResistance());
        this.triggered = triggered;
    }

    protected CommandBlockMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type, final boolean triggered, final float hardness, final float blastResistance)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, hardness, blastResistance);
        this.triggered = triggered;
    }

    @Override
    public CommandBlockMat getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public CommandBlockMat getType(final int id)
    {
        return getByID(id);
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("triggered", this.triggered).toString();
    }

    /**
     * @return true if CommandBlock was triggered
     */
    public boolean isTriggered()
    {
        return this.triggered;
    }

    /**
     * Returns one of CommandBlock sub-type, based on triggered state.
     *
     * @param triggered if CommandBlock should be triggered.
     *
     * @return sub-type of CommandBlock
     */
    public CommandBlockMat getTriggered(final boolean triggered)
    {
        return getByID(triggered ? 1 : 0);
    }

    /**
     * Returns one of CommandBlock sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of CommandBlock or null
     */
    public static CommandBlockMat getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of CommandBlock sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of CommandBlock or null
     */
    public static CommandBlockMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Returns one of CommandBlock sub-type, based on triggered state.
     * It will never return null.
     *
     * @param triggered if CommandBlock should be triggered.
     *
     * @return sub-type of CommandBlock
     */
    public static CommandBlockMat getCommandBlock(final boolean triggered)
    {
        return getByID(triggered ? 1 : 0);
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final CommandBlockMat element)
    {
        byID.put((byte) element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    @Override
    public CommandBlockMat[] types()
    {
        return CommandBlockMat.commandBlockTypes();
    }

    /**
     * @return array that contains all sub-types of this block.
     */
    public static CommandBlockMat[] commandBlockTypes()
    {
        return byID.values(new CommandBlockMat[byID.size()]);
    }

    static
    {
        CommandBlockMat.register(COMMAND_BLOCK);
        CommandBlockMat.register(COMMAND_BLOCK_TRIGGERED);
    }
}
