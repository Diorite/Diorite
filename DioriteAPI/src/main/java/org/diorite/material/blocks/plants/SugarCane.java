package org.diorite.material.blocks.plants;

import java.util.Map;

import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.material.blocks.AgeableBlock;
import org.diorite.utils.collections.SimpleStringHashMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing block "SugarCane" and all its subtypes.
 */
@SuppressWarnings("MagicNumber")
public class SugarCane extends Plant implements AgeableBlock
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final byte  USED_DATA_VALUES = 16;
    /**
     * Blast resistance of block, can be changed only before server start.
     * Final copy of blast resistance from {@link MagicNumbers} class.
     */
    public static final float BLAST_RESISTANCE = MagicNumbers.MATERIAL__SUGAR_CANE__BLAST_RESISTANCE;
    /**
     * Hardness of block, can be changed only before server start.
     * Final copy of hardness from {@link MagicNumbers} class.
     */
    public static final float HARDNESS         = MagicNumbers.MATERIAL__SUGAR_CANE__HARDNESS;

    public static final SugarCane SUGAR_CANE_0  = new SugarCane();
    public static final SugarCane SUGAR_CANE_1  = new SugarCane(0x1);
    public static final SugarCane SUGAR_CANE_2  = new SugarCane(0x2);
    public static final SugarCane SUGAR_CANE_3  = new SugarCane(0x3);
    public static final SugarCane SUGAR_CANE_4  = new SugarCane(0x4);
    public static final SugarCane SUGAR_CANE_5  = new SugarCane(0x5);
    public static final SugarCane SUGAR_CANE_6  = new SugarCane(0x6);
    public static final SugarCane SUGAR_CANE_7  = new SugarCane(0x7);
    public static final SugarCane SUGAR_CANE_8  = new SugarCane(0x8);
    public static final SugarCane SUGAR_CANE_9  = new SugarCane(0x9);
    public static final SugarCane SUGAR_CANE_10 = new SugarCane(0xA);
    public static final SugarCane SUGAR_CANE_11 = new SugarCane(0xB);
    public static final SugarCane SUGAR_CANE_12 = new SugarCane(0xC);
    public static final SugarCane SUGAR_CANE_13 = new SugarCane(0xD);
    public static final SugarCane SUGAR_CANE_14 = new SugarCane(0xE);
    public static final SugarCane SUGAR_CANE_15 = new SugarCane(0xF);

    private static final Map<String, SugarCane>    byName = new SimpleStringHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<SugarCane> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    @SuppressWarnings("MagicNumber")
    protected SugarCane()
    {
        super("SUGAR_CANE", 83, "minecraft:reeds", "0", (byte) 0x00);
    }

    public SugarCane(final int age)
    {
        super(SUGAR_CANE_0.name(), SUGAR_CANE_0.getId(), SUGAR_CANE_0.getMinecraftId(), Integer.toString(age), (byte) age);
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
    public SugarCane getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public SugarCane getType(final int id)
    {
        return getByID(id);
    }

    @Override
    public int getAge()
    {
        return this.type;
    }

    @Override
    public SugarCane getAge(final int age)
    {
        return getByID(age);
    }

    /**
     * Returns one of SugarCane sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of SugarCane or null
     */
    public static SugarCane getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of SugarCane sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of SugarCane or null
     */
    public static SugarCane getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Returns one of SugarCane sub-type based on age.
     * It will never return null.
     *
     * @param age age of SugarCane.
     *
     * @return sub-type of SugarCane
     */
    public static SugarCane getSugarCane(final int age)
    {
        final SugarCane sugarCane = getByID(age);
        if (sugarCane == null)
        {
            return SUGAR_CANE_0;
        }
        return sugarCane;
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final SugarCane element)
    {
        byID.put(element.getType(), element);
        byName.put(element.name(), element);
    }

    static
    {
        SugarCane.register(SUGAR_CANE_0);
        SugarCane.register(SUGAR_CANE_1);
        SugarCane.register(SUGAR_CANE_2);
        SugarCane.register(SUGAR_CANE_3);
        SugarCane.register(SUGAR_CANE_4);
        SugarCane.register(SUGAR_CANE_5);
        SugarCane.register(SUGAR_CANE_6);
        SugarCane.register(SUGAR_CANE_7);
        SugarCane.register(SUGAR_CANE_8);
        SugarCane.register(SUGAR_CANE_9);
        SugarCane.register(SUGAR_CANE_10);
        SugarCane.register(SUGAR_CANE_11);
        SugarCane.register(SUGAR_CANE_12);
        SugarCane.register(SUGAR_CANE_13);
        SugarCane.register(SUGAR_CANE_14);
        SugarCane.register(SUGAR_CANE_15);
    }
}
