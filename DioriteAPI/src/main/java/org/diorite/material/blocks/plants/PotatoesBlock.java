package org.diorite.material.blocks.plants;

import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.utils.collections.SimpleStringHashMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing block "PotatoesBlock" and all its subtypes.
 */
public class PotatoesBlock extends Crops
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final byte  USED_DATA_VALUES = 8;
    /**
     * Blast resistance of block, can be changed only before server start.
     * Final copy of blast resistance from {@link MagicNumbers} class.
     */
    public static final float BLAST_RESISTANCE = MagicNumbers.MATERIAL__POTATOES_BLOCK__BLAST_RESISTANCE;
    /**
     * Hardness of block, can be changed only before server start.
     * Final copy of hardness from {@link MagicNumbers} class.
     */
    public static final float HARDNESS         = MagicNumbers.MATERIAL__POTATOES_BLOCK__HARDNESS;

    public static final PotatoesBlock POTATOES_BLOCK_0    = new PotatoesBlock();
    public static final PotatoesBlock POTATOES_BLOCK_1    = new PotatoesBlock("1", 0x1);
    public static final PotatoesBlock POTATOES_BLOCK_2    = new PotatoesBlock("2", 0x2);
    public static final PotatoesBlock POTATOES_BLOCK_3    = new PotatoesBlock("3", 0x3);
    public static final PotatoesBlock POTATOES_BLOCK_4    = new PotatoesBlock("4", 0x4);
    public static final PotatoesBlock POTATOES_BLOCK_5    = new PotatoesBlock("5", 0x5);
    public static final PotatoesBlock POTATOES_BLOCK_6    = new PotatoesBlock("6", 0x6);
    public static final PotatoesBlock POTATOES_BLOCK_RIPE = new PotatoesBlock("RIPE", 0x7);

    private static final Map<String, PotatoesBlock>    byName = new SimpleStringHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<PotatoesBlock> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    protected final int age;

    @SuppressWarnings("MagicNumber")
    protected PotatoesBlock()
    {
        super("POTATOES_BLOCK", 142, "minecraft:potatoes", "0", (byte) 0x00);
        this.age = 0;
    }

    public PotatoesBlock(final String enumName, final int age)
    {
        super(POTATOES_BLOCK_0.name(), POTATOES_BLOCK_0.getId(), POTATOES_BLOCK_0.getMinecraftId(), enumName, (byte) age);
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
    public PotatoesBlock getAge(final int age)
    {
        return getByID(age);
    }

    @Override
    public PotatoesBlock getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public PotatoesBlock getType(final int id)
    {
        return getByID(id);
    }

    /**
     * Returns one of PotatoesBlock sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of PotatoesBlock or null
     */
    public static PotatoesBlock getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of PotatoesBlock sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of PotatoesBlock or null
     */
    public static PotatoesBlock getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Returns one of PotatoesBlock sub-type based on age.
     * It will never return null.
     *
     * @param age age of PotatoesBlock.
     *
     * @return sub-type of PotatoesBlock
     */
    public static PotatoesBlock getPotatoesBlock(final int age)
    {
        final PotatoesBlock potatoesBlock = getByID(age);
        if (potatoesBlock == null)
        {
            return POTATOES_BLOCK_0;
        }
        return potatoesBlock;
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final PotatoesBlock element)
    {
        byID.put(element.getType(), element);
        byName.put(element.name(), element);
    }

    static
    {
        PotatoesBlock.register(POTATOES_BLOCK_0);
        PotatoesBlock.register(POTATOES_BLOCK_1);
        PotatoesBlock.register(POTATOES_BLOCK_2);
        PotatoesBlock.register(POTATOES_BLOCK_3);
        PotatoesBlock.register(POTATOES_BLOCK_4);
        PotatoesBlock.register(POTATOES_BLOCK_5);
        PotatoesBlock.register(POTATOES_BLOCK_6);
        PotatoesBlock.register(POTATOES_BLOCK_RIPE);
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("age", this.age).toString();
    }
}
