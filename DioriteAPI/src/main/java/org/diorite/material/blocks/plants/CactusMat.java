package org.diorite.material.blocks.plants;

import java.util.Map;

import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.material.blocks.AgeableBlockMat;
import org.diorite.utils.collections.maps.CaseInsensitiveMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing block "Cactus" and all its subtypes.
 */
@SuppressWarnings("MagicNumber")
public class CactusMat extends PlantMat implements AgeableBlockMat
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

    public static final CactusMat CACTUS_0  = new CactusMat();
    public static final CactusMat CACTUS_1  = new CactusMat(0x1);
    public static final CactusMat CACTUS_2  = new CactusMat(0x2);
    public static final CactusMat CACTUS_3  = new CactusMat(0x3);
    public static final CactusMat CACTUS_4  = new CactusMat(0x4);
    public static final CactusMat CACTUS_5  = new CactusMat(0x5);
    public static final CactusMat CACTUS_6  = new CactusMat(0x6);
    public static final CactusMat CACTUS_7  = new CactusMat(0x7);
    public static final CactusMat CACTUS_8  = new CactusMat(0x8);
    public static final CactusMat CACTUS_9  = new CactusMat(0x9);
    public static final CactusMat CACTUS_10 = new CactusMat(0xA);
    public static final CactusMat CACTUS_11 = new CactusMat(0xB);
    public static final CactusMat CACTUS_12 = new CactusMat(0xC);
    public static final CactusMat CACTUS_13 = new CactusMat(0xD);
    public static final CactusMat CACTUS_14 = new CactusMat(0xE);
    public static final CactusMat CACTUS_15 = new CactusMat(0xF);

    private static final Map<String, CactusMat>    byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<CactusMat> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    @SuppressWarnings("MagicNumber")
    protected CactusMat()
    {
        super("CACTUS", 81, "minecraft:cactus", "0", (byte) 0x00);
    }

    protected CactusMat(final int age)
    {
        super(CACTUS_0.name(), CACTUS_0.getId(), CACTUS_0.getMinecraftId(), Integer.toString(age), (byte) age);
    }

    protected CactusMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type);
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
    public CactusMat getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public CactusMat getType(final int id)
    {
        return getByID(id);
    }

    @Override
    public int getAge()
    {
        return this.type;
    }

    @Override
    public CactusMat getAge(final int age)
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
    public static CactusMat getByID(final int id)
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
    public static CactusMat getByEnumName(final String name)
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
    public static CactusMat getCactus(final int age)
    {
        final CactusMat cactus = getByID(age);
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
    public static void register(final CactusMat element)
    {
        byID.put((byte) element.getType(), element);
        byName.put(element.name(), element);
    }

    static
    {
        CactusMat.register(CACTUS_0);
        CactusMat.register(CACTUS_1);
        CactusMat.register(CACTUS_2);
        CactusMat.register(CACTUS_3);
        CactusMat.register(CACTUS_4);
        CactusMat.register(CACTUS_5);
        CactusMat.register(CACTUS_6);
        CactusMat.register(CACTUS_7);
        CactusMat.register(CACTUS_8);
        CactusMat.register(CACTUS_9);
        CactusMat.register(CACTUS_10);
        CactusMat.register(CACTUS_11);
        CactusMat.register(CACTUS_12);
        CactusMat.register(CACTUS_13);
        CactusMat.register(CACTUS_14);
        CactusMat.register(CACTUS_15);
    }
}
