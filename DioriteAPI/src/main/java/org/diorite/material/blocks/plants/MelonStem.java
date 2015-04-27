package org.diorite.material.blocks.plants;

import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.utils.collections.SimpleStringHashMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing block "MelonStem" and all its subtypes.
 */
public class MelonStem extends PlantStem
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final byte  USED_DATA_VALUES = 8;
    /**
     * Blast resistance of block, can be changed only before server start.
     * Final copy of blast resistance from {@link MagicNumbers} class.
     */
    public static final float BLAST_RESISTANCE = MagicNumbers.MATERIAL__MELON_STEM__BLAST_RESISTANCE;
    /**
     * Hardness of block, can be changed only before server start.
     * Final copy of hardness from {@link MagicNumbers} class.
     */
    public static final float HARDNESS         = MagicNumbers.MATERIAL__MELON_STEM__HARDNESS;

    public static final MelonStem MELON_STEM_0     = new MelonStem();
    public static final MelonStem MELON_BLOCK_1    = new MelonStem("1", 0x1);
    public static final MelonStem MELON_BLOCK_2    = new MelonStem("2", 0x2);
    public static final MelonStem MELON_BLOCK_3    = new MelonStem("3", 0x3);
    public static final MelonStem MELON_BLOCK_4    = new MelonStem("4", 0x4);
    public static final MelonStem MELON_BLOCK_5    = new MelonStem("5", 0x5);
    public static final MelonStem MELON_BLOCK_6    = new MelonStem("6", 0x6);
    public static final MelonStem MELON_BLOCK_RIPE = new MelonStem("RIPE", 0x7);

    private static final Map<String, MelonStem>    byName = new SimpleStringHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<MelonStem> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    protected final int age;

    @SuppressWarnings("MagicNumber")
    protected MelonStem()
    {
        super("MELON_STEM", 105, "minecraft:melon_stem", "0", (byte) 0x00);
        this.age = 0;
    }

    public MelonStem(final String enumName, final int age)
    {
        super(MELON_STEM_0.name(), MELON_STEM_0.getId(), MELON_STEM_0.getMinecraftId(), enumName, (byte) age);
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
    public MelonStem getAge(final int age)
    {
        return getByID(age);
    }

    @Override
    public MelonStem getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public MelonStem getType(final int id)
    {
        return getByID(id);
    }

    /**
     * Returns one of MelonStem sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of MelonStem or null
     */
    public static MelonStem getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of MelonStem sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of MelonStem or null
     */
    public static MelonStem getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Returns one of MelonStem sub-type based on age.
     * It will never return null.
     *
     * @param age age of MelonStem.
     *
     * @return sub-type of MelonStem
     */
    public static MelonStem getMelonStem(final int age)
    {
        final MelonStem melonStem = getByID(age);
        if (melonStem == null)
        {
            return MELON_STEM_0;
        }
        return melonStem;
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final MelonStem element)
    {
        byID.put(element.getType(), element);
        byName.put(element.name(), element);
    }

    static
    {
        MelonStem.register(MELON_STEM_0);
        MelonStem.register(MELON_BLOCK_1);
        MelonStem.register(MELON_BLOCK_2);
        MelonStem.register(MELON_BLOCK_3);
        MelonStem.register(MELON_BLOCK_4);
        MelonStem.register(MELON_BLOCK_5);
        MelonStem.register(MELON_BLOCK_6);
        MelonStem.register(MELON_BLOCK_RIPE);
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("age", this.age).toString();
    }
}
