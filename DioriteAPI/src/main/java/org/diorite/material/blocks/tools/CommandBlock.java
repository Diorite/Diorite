package org.diorite.material.blocks.tools;

import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.material.BlockMaterialData;
import org.diorite.utils.collections.SimpleStringHashMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing block "CommandBlock" and all its subtypes.
 */
public class CommandBlock extends BlockMaterialData
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final byte  USED_DATA_VALUES = 2;
    /**
     * Blast resistance of block, can be changed only before server start.
     * Final copy of blast resistance from {@link MagicNumbers} class.
     */
    public static final float BLAST_RESISTANCE = MagicNumbers.MATERIAL__COMMAND_BLOCK__BLAST_RESISTANCE;
    /**
     * Hardness of block, can be changed only before server start.
     * Final copy of hardness from {@link MagicNumbers} class.
     */
    public static final float HARDNESS         = MagicNumbers.MATERIAL__COMMAND_BLOCK__HARDNESS;

    public static final CommandBlock COMMAND_BLOCK           = new CommandBlock();
    public static final CommandBlock COMMAND_BLOCK_TRIGGERED = new CommandBlock("TRIGGERED", 0x1, true);

    private static final Map<String, CommandBlock>    byName = new SimpleStringHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<CommandBlock> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    protected final boolean triggered;

    @SuppressWarnings("MagicNumber")
    protected CommandBlock()
    {
        super("COMMAND_BLOCK", 137, "minecraft:command_block", "COMMAND_BLOCK", (byte) 0x00);
        this.triggered = false;
    }

    public CommandBlock(final String enumName, final int type, final boolean triggered)
    {
        super(COMMAND_BLOCK.name(), COMMAND_BLOCK.getId(), COMMAND_BLOCK.getMinecraftId(), enumName, (byte) type);
        this.triggered = triggered;
    }

    @Override
    public float getBlastResistance()
    {
        return BLAST_RESISTANCE;
    }

    @Override
    public float getHardness()
    {
        return HARDNESS;
    }

    @Override
    public CommandBlock getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public CommandBlock getType(final int id)
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
    public CommandBlock getTriggered(final boolean triggered)
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
    public static CommandBlock getByID(final int id)
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
    public static CommandBlock getByEnumName(final String name)
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
    public static CommandBlock getCommandBlock(final boolean triggered)
    {
        return getByID(triggered ? 1 : 0);
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final CommandBlock element)
    {
        byID.put(element.getType(), element);
        byName.put(element.name(), element);
    }

    static
    {
        CommandBlock.register(COMMAND_BLOCK);
        CommandBlock.register(COMMAND_BLOCK_TRIGGERED);
    }
}
