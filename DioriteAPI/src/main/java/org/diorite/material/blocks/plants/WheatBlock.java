package org.diorite.material.blocks.plants;

import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.utils.collections.SimpleStringHashMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing block "WheatBlock" and all its subtypes.
 */
public class WheatBlock extends Crops
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final byte  USED_DATA_VALUES = 8;
    /**
     * Blast resistance of block, can be changed only before server start.
     * Final copy of blast resistance from {@link MagicNumbers} class.
     */
    public static final float BLAST_RESISTANCE = MagicNumbers.MATERIAL__WHEAT_BLOCK__BLAST_RESISTANCE;
    /**
     * Hardness of block, can be changed only before server start.
     * Final copy of hardness from {@link MagicNumbers} class.
     */
    public static final float HARDNESS         = MagicNumbers.MATERIAL__WHEAT_BLOCK__HARDNESS;

    public static final WheatBlock WHEAT_BLOCK_0    = new WheatBlock();
    public static final WheatBlock WHEAT_BLOCK_1    = new WheatBlock("1", 0x1);
    public static final WheatBlock WHEAT_BLOCK_2    = new WheatBlock("2", 0x2);
    public static final WheatBlock WHEAT_BLOCK_3    = new WheatBlock("3", 0x3);
    public static final WheatBlock WHEAT_BLOCK_4    = new WheatBlock("4", 0x4);
    public static final WheatBlock WHEAT_BLOCK_5    = new WheatBlock("5", 0x5);
    public static final WheatBlock WHEAT_BLOCK_6    = new WheatBlock("6", 0x6);
    public static final WheatBlock WHEAT_BLOCK_RIPE = new WheatBlock("RIPE", 0x7);

    private static final Map<String, WheatBlock>    byName = new SimpleStringHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<WheatBlock> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    protected final int age;

    @SuppressWarnings("MagicNumber")
    protected WheatBlock()
    {
        super("WHEAT_BLOCK", 59, "minecraft:wheat", "0", (byte) 0x00);
        this.age = 0;
    }

    public WheatBlock(final String enumName, final int age)
    {
        super(WHEAT_BLOCK_0.name(), WHEAT_BLOCK_0.getId(), WHEAT_BLOCK_0.getMinecraftId(), enumName, (byte) age);
        this.age = age;
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
    public int getAge()
    {
        return this.age;
    }

    @Override
    public WheatBlock getAge(final int age)
    {
        return getByID(age);
    }

    @Override
    public WheatBlock getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public WheatBlock getType(final int id)
    {
        return getByID(id);
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("age", this.age).toString();
    }

    /**
     * Returns one of WheatBlock sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of WheatBlock or null
     */
    public static WheatBlock getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of WheatBlock sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of WheatBlock or null
     */
    public static WheatBlock getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Returns one of WheatBlock sub-type based on age.
     * It will never return null.
     *
     * @param age age of WheatBlock.
     *
     * @return sub-type of WheatBlock
     */
    public static WheatBlock getWheatBlock(final int age)
    {
        final WheatBlock wheatBlock = getByID(age);
        if (wheatBlock == null)
        {
            return WHEAT_BLOCK_0;
        }
        return wheatBlock;
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final WheatBlock element)
    {
        byID.put(element.getType(), element);
        byName.put(element.name(), element);
    }

    static
    {
        WheatBlock.register(WHEAT_BLOCK_0);
        WheatBlock.register(WHEAT_BLOCK_1);
        WheatBlock.register(WHEAT_BLOCK_2);
        WheatBlock.register(WHEAT_BLOCK_3);
        WheatBlock.register(WHEAT_BLOCK_4);
        WheatBlock.register(WHEAT_BLOCK_5);
        WheatBlock.register(WHEAT_BLOCK_6);
        WheatBlock.register(WHEAT_BLOCK_RIPE);
    }
}
