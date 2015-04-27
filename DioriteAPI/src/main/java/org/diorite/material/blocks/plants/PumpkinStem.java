package org.diorite.material.blocks.plants;

import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.utils.collections.SimpleStringHashMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing block "PumpkinStem" and all its subtypes.
 */
public class PumpkinStem extends PlantStem
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final byte  USED_DATA_VALUES = 8;
    /**
     * Blast resistance of block, can be changed only before server start.
     * Final copy of blast resistance from {@link MagicNumbers} class.
     */
    public static final float BLAST_RESISTANCE = MagicNumbers.MATERIAL__PUMPKIN_STEM__BLAST_RESISTANCE;
    /**
     * Hardness of block, can be changed only before server start.
     * Final copy of hardness from {@link MagicNumbers} class.
     */
    public static final float HARDNESS         = MagicNumbers.MATERIAL__PUMPKIN_STEM__HARDNESS;

    public static final PumpkinStem PUMPKIN_STEM_0     = new PumpkinStem();
    public static final PumpkinStem PUMPKIN_BLOCK_1    = new PumpkinStem("1", 0x1);
    public static final PumpkinStem PUMPKIN_BLOCK_2    = new PumpkinStem("2", 0x2);
    public static final PumpkinStem PUMPKIN_BLOCK_3    = new PumpkinStem("3", 0x3);
    public static final PumpkinStem PUMPKIN_BLOCK_4    = new PumpkinStem("4", 0x4);
    public static final PumpkinStem PUMPKIN_BLOCK_5    = new PumpkinStem("5", 0x5);
    public static final PumpkinStem PUMPKIN_BLOCK_6    = new PumpkinStem("6", 0x6);
    public static final PumpkinStem PUMPKIN_BLOCK_RIPE = new PumpkinStem("RIPE", 0x7);

    private static final Map<String, PumpkinStem>    byName = new SimpleStringHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<PumpkinStem> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    protected final int age;

    @SuppressWarnings("MagicNumber")
    protected PumpkinStem()
    {
        super("PUMPKIN_STEM", 104, "minecraft:pumpkin_stem", "0", (byte) 0x00);
        this.age = 0;
    }

    public PumpkinStem(final String enumName, final int age)
    {
        super(PUMPKIN_STEM_0.name(), PUMPKIN_STEM_0.getId(), PUMPKIN_STEM_0.getMinecraftId(), enumName, (byte) age);
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
    public PumpkinStem getAge(final int age)
    {
        return getByID(age);
    }

    @Override
    public PumpkinStem getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public PumpkinStem getType(final int id)
    {
        return getByID(id);
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("age", this.age).toString();
    }

    /**
     * Returns one of PumpkinStem sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of PumpkinStem or null
     */
    public static PumpkinStem getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of PumpkinStem sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of PumpkinStem or null
     */
    public static PumpkinStem getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Returns one of PumpkinStem sub-type based on age.
     * It will never return null.
     *
     * @param age age of PumpkinStem.
     *
     * @return sub-type of PumpkinStem
     */
    public static PumpkinStem getPumpkinStem(final int age)
    {
        final PumpkinStem pumpkinStem = getByID(age);
        if (pumpkinStem == null)
        {
            return PUMPKIN_STEM_0;
        }
        return pumpkinStem;
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final PumpkinStem element)
    {
        byID.put(element.getType(), element);
        byName.put(element.name(), element);
    }

    static
    {
        PumpkinStem.register(PUMPKIN_STEM_0);
        PumpkinStem.register(PUMPKIN_BLOCK_1);
        PumpkinStem.register(PUMPKIN_BLOCK_2);
        PumpkinStem.register(PUMPKIN_BLOCK_3);
        PumpkinStem.register(PUMPKIN_BLOCK_4);
        PumpkinStem.register(PUMPKIN_BLOCK_5);
        PumpkinStem.register(PUMPKIN_BLOCK_6);
        PumpkinStem.register(PUMPKIN_BLOCK_RIPE);
    }
}
