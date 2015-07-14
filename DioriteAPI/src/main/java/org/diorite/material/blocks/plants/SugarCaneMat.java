package org.diorite.material.blocks.plants;

import java.util.Map;

import org.diorite.material.blocks.AgeableBlockMat;
import org.diorite.utils.collections.maps.CaseInsensitiveMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing block "SugarCane" and all its subtypes.
 */
@SuppressWarnings("MagicNumber")
public class SugarCaneMat extends PlantMat implements AgeableBlockMat
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final byte  USED_DATA_VALUES = 16;

    public static final SugarCaneMat SUGAR_CANE_0  = new SugarCaneMat();
    public static final SugarCaneMat SUGAR_CANE_1  = new SugarCaneMat(0x1);
    public static final SugarCaneMat SUGAR_CANE_2  = new SugarCaneMat(0x2);
    public static final SugarCaneMat SUGAR_CANE_3  = new SugarCaneMat(0x3);
    public static final SugarCaneMat SUGAR_CANE_4  = new SugarCaneMat(0x4);
    public static final SugarCaneMat SUGAR_CANE_5  = new SugarCaneMat(0x5);
    public static final SugarCaneMat SUGAR_CANE_6  = new SugarCaneMat(0x6);
    public static final SugarCaneMat SUGAR_CANE_7  = new SugarCaneMat(0x7);
    public static final SugarCaneMat SUGAR_CANE_8  = new SugarCaneMat(0x8);
    public static final SugarCaneMat SUGAR_CANE_9  = new SugarCaneMat(0x9);
    public static final SugarCaneMat SUGAR_CANE_10 = new SugarCaneMat(0xA);
    public static final SugarCaneMat SUGAR_CANE_11 = new SugarCaneMat(0xB);
    public static final SugarCaneMat SUGAR_CANE_12 = new SugarCaneMat(0xC);
    public static final SugarCaneMat SUGAR_CANE_13 = new SugarCaneMat(0xD);
    public static final SugarCaneMat SUGAR_CANE_14 = new SugarCaneMat(0xE);
    public static final SugarCaneMat SUGAR_CANE_15 = new SugarCaneMat(0xF);

    private static final Map<String, SugarCaneMat>    byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<SugarCaneMat> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    @SuppressWarnings("MagicNumber")
    protected SugarCaneMat()
    {
        super("SUGAR_CANE", 83, "minecraft:reeds", "0", (byte) 0x00, 0, 0);
    }

    protected SugarCaneMat(final int age)
    {
        super(SUGAR_CANE_0.name(), SUGAR_CANE_0.ordinal(), SUGAR_CANE_0.getMinecraftId(), Integer.toString(age), (byte) age, SUGAR_CANE_0.getHardness(), SUGAR_CANE_0.getBlastResistance());
    }

    protected SugarCaneMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type, final float hardness, final float blastResistance)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, hardness, blastResistance);
    }

    @Override
    public SugarCaneMat getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public SugarCaneMat getType(final int id)
    {
        return getByID(id);
    }

    @Override
    public int getAge()
    {
        return this.type;
    }

    @Override
    public SugarCaneMat getAge(final int age)
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
    public static SugarCaneMat getByID(final int id)
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
    public static SugarCaneMat getByEnumName(final String name)
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
    public static SugarCaneMat getSugarCane(final int age)
    {
        final SugarCaneMat sugarCane = getByID(age);
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
    public static void register(final SugarCaneMat element)
    {
        byID.put((byte) element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    @Override
    public SugarCaneMat[] types()
    {
        return SugarCaneMat.sugarCaneTypes();
    }

    /**
     * @return array that contains all sub-types of this block.
     */
    public static SugarCaneMat[] sugarCaneTypes()
    {
        return byID.values(new SugarCaneMat[byID.size()]);
    }

    static
    {
        SugarCaneMat.register(SUGAR_CANE_0);
        SugarCaneMat.register(SUGAR_CANE_1);
        SugarCaneMat.register(SUGAR_CANE_2);
        SugarCaneMat.register(SUGAR_CANE_3);
        SugarCaneMat.register(SUGAR_CANE_4);
        SugarCaneMat.register(SUGAR_CANE_5);
        SugarCaneMat.register(SUGAR_CANE_6);
        SugarCaneMat.register(SUGAR_CANE_7);
        SugarCaneMat.register(SUGAR_CANE_8);
        SugarCaneMat.register(SUGAR_CANE_9);
        SugarCaneMat.register(SUGAR_CANE_10);
        SugarCaneMat.register(SUGAR_CANE_11);
        SugarCaneMat.register(SUGAR_CANE_12);
        SugarCaneMat.register(SUGAR_CANE_13);
        SugarCaneMat.register(SUGAR_CANE_14);
        SugarCaneMat.register(SUGAR_CANE_15);
    }
}
