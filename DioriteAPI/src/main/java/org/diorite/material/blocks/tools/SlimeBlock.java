package org.diorite.material.blocks.tools;

import java.util.Map;

import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.material.BlockMaterialData;
import org.diorite.utils.collections.SimpleStringHashMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing block "SlimeBlock" and all its subtypes.
 */
public class SlimeBlock extends BlockMaterialData
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final byte  USED_DATA_VALUES = 1;
    /**
     * Blast resistance of block, can be changed only before server start.
     * Final copy of blast resistance from {@link MagicNumbers} class.
     */
    public static final float BLAST_RESISTANCE = MagicNumbers.MATERIAL__SLIME_BLOCK__BLAST_RESISTANCE;
    /**
     * Hardness of block, can be changed only before server start.
     * Final copy of hardness from {@link MagicNumbers} class.
     */
    public static final float HARDNESS         = MagicNumbers.MATERIAL__SLIME_BLOCK__HARDNESS;

    public static final SlimeBlock SLIME_BLOCK = new SlimeBlock();

    private static final Map<String, SlimeBlock>    byName = new SimpleStringHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<SlimeBlock> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    @SuppressWarnings("MagicNumber")
    protected SlimeBlock()
    {
        super("SLIME_BLOCK", 165, "minecraft:slime", "SLIME_BLOCK", (byte) 0x00);
    }

    public SlimeBlock(final String enumName, final int type)
    {
        super(SLIME_BLOCK.name(), SLIME_BLOCK.getId(), SLIME_BLOCK.getMinecraftId(), enumName, (byte) type);
    }

    public SlimeBlock(final int maxStack, final String typeName, final byte type)
    {
        super(SLIME_BLOCK.name(), SLIME_BLOCK.getId(), SLIME_BLOCK.getMinecraftId(), maxStack, typeName, type);
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
    public SlimeBlock getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public SlimeBlock getType(final int id)
    {
        return getByID(id);
    }

    /**
     * Returns one of SlimeBlock sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of SlimeBlock or null
     */
    public static SlimeBlock getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of SlimeBlock sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of SlimeBlock or null
     */
    public static SlimeBlock getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final SlimeBlock element)
    {
        byID.put(element.getType(), element);
        byName.put(element.name(), element);
    }

    static
    {
        SlimeBlock.register(SLIME_BLOCK);
    }
}
