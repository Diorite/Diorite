package org.diorite.material.blocks.plants;

import java.util.Map;

import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.material.blocks.AgeableBlock;
import org.diorite.utils.collections.SimpleStringHashMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing block "Cactus" and all its subtypes.
 */
@SuppressWarnings("MagicNumber")
public class Cactus extends Plant implements AgeableBlock
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final byte  USED_DATA_VALUES = 16;
    /**
     * Blast resistance of block, can be changed only before server start.
     * Final copy of blast resistance from {@link MagicNumbers} class.
     */
    public static final float BLAST_RESISTANCE = MagicNumbers.MATERIAL__CACTUS__BLAST_RESISTANCE;
    /**
     * Hardness of block, can be changed only before server start.
     * Final copy of hardness from {@link MagicNumbers} class.
     */
    public static final float HARDNESS         = MagicNumbers.MATERIAL__CACTUS__HARDNESS;

    public static final Cactus CACTUS_0  = new Cactus();
    public static final Cactus CACTUS_1  = new Cactus(0x1);
    public static final Cactus CACTUS_2  = new Cactus(0x2);
    public static final Cactus CACTUS_3  = new Cactus(0x3);
    public static final Cactus CACTUS_4  = new Cactus(0x4);
    public static final Cactus CACTUS_5  = new Cactus(0x5);
    public static final Cactus CACTUS_6  = new Cactus(0x6);
    public static final Cactus CACTUS_7  = new Cactus(0x7);
    public static final Cactus CACTUS_8  = new Cactus(0x8);
    public static final Cactus CACTUS_9  = new Cactus(0x9);
    public static final Cactus CACTUS_10 = new Cactus(0xA);
    public static final Cactus CACTUS_11 = new Cactus(0xB);
    public static final Cactus CACTUS_12 = new Cactus(0xC);
    public static final Cactus CACTUS_13 = new Cactus(0xD);
    public static final Cactus CACTUS_14 = new Cactus(0xE);
    public static final Cactus CACTUS_15 = new Cactus(0xF);

    private static final Map<String, Cactus>    byName = new SimpleStringHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<Cactus> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    @SuppressWarnings("MagicNumber")
    protected Cactus()
    {
        super("CACTUS", 81, "minecraft:cactus", "0", (byte) 0x00);
    }

    public Cactus(final int age)
    {
        super(CACTUS_0.name(), CACTUS_0.getId(), CACTUS_0.getMinecraftId(), Integer.toString(age), (byte) age);
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
    public Cactus getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public Cactus getType(final int id)
    {
        return getByID(id);
    }

    @Override
    public int getAge()
    {
        return this.type;
    }

    @Override
    public Cactus getAge(final int age)
    {
        return getByID(age);
    }

    /**
     * Returns one of Cactus sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of Cactus or null
     */
    public static Cactus getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of Cactus sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of Cactus or null
     */
    public static Cactus getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Returns one of Cactus sub-type based on age.
     * It will never return null.
     *
     * @param age age of Cactus.
     *
     * @return sub-type of Cactus
     */
    public static Cactus getCactus(final int age)
    {
        final Cactus cactus = getByID(age);
        if (cactus == null)
        {
            return CACTUS_0;
        }
        return cactus;
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final Cactus element)
    {
        byID.put(element.getType(), element);
        byName.put(element.name(), element);
    }

    static
    {
        Cactus.register(CACTUS_0 );
        Cactus.register(CACTUS_1 );
        Cactus.register(CACTUS_2 );
        Cactus.register(CACTUS_3 );
        Cactus.register(CACTUS_4 );
        Cactus.register(CACTUS_5 );
        Cactus.register(CACTUS_6 );
        Cactus.register(CACTUS_7 );
        Cactus.register(CACTUS_8 );
        Cactus.register(CACTUS_9 );
        Cactus.register(CACTUS_10);
        Cactus.register(CACTUS_11);
        Cactus.register(CACTUS_12);
        Cactus.register(CACTUS_13);
        Cactus.register(CACTUS_14);
        Cactus.register(CACTUS_15);
    }
}
