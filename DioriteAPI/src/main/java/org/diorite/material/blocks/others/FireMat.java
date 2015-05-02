package org.diorite.material.blocks.others;

import java.util.Map;

import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.material.BlockMaterialData;
import org.diorite.material.blocks.AgeableBlockMat;
import org.diorite.utils.collections.SimpleStringHashMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing block "Fire" and all its subtypes.
 */
@SuppressWarnings("MagicNumber")
public class FireMat extends BlockMaterialData implements AgeableBlockMat
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final byte  USED_DATA_VALUES = 16;
    /**
     * Blast resistance of block, can be changed only before server start.
     * Final copy of blast resistance from {@link MagicNumbers} class.
     */
    public static final float BLAST_RESISTANCE = MagicNumbers.MATERIAL__FIRE__BLAST_RESISTANCE;
    /**
     * Hardness of block, can be changed only before server start.
     * Final copy of hardness from {@link MagicNumbers} class.
     */
    public static final float HARDNESS         = MagicNumbers.MATERIAL__FIRE__HARDNESS;

    public static final FireMat FIRE_0  = new FireMat();
    public static final FireMat FIRE_1  = new FireMat(0x1);
    public static final FireMat FIRE_2  = new FireMat(0x2);
    public static final FireMat FIRE_3  = new FireMat(0x3);
    public static final FireMat FIRE_4  = new FireMat(0x4);
    public static final FireMat FIRE_5  = new FireMat(0x5);
    public static final FireMat FIRE_6  = new FireMat(0x6);
    public static final FireMat FIRE_7  = new FireMat(0x7);
    public static final FireMat FIRE_8  = new FireMat(0x8);
    public static final FireMat FIRE_9  = new FireMat(0x9);
    public static final FireMat FIRE_10 = new FireMat(0xA);
    public static final FireMat FIRE_11 = new FireMat(0xB);
    public static final FireMat FIRE_12 = new FireMat(0xC);
    public static final FireMat FIRE_13 = new FireMat(0xD);
    public static final FireMat FIRE_14 = new FireMat(0xE);
    public static final FireMat FIRE_15 = new FireMat(0xF);

    private static final Map<String, FireMat>    byName = new SimpleStringHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<FireMat> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    @SuppressWarnings("MagicNumber")
    protected FireMat()
    {
        super("FIRE", 51, "minecraft:fire", "0", (byte) 0x0);
    }

    protected FireMat(final int age)
    {
        super(FIRE_0.name(), FIRE_0.getId(), FIRE_0.getMinecraftId(), Integer.toString(age), (byte) age);
    }

    protected FireMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type)
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
    public FireMat getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public FireMat getType(final int id)
    {
        return getByID(id);
    }

    @Override
    public int getAge()
    {
        return this.type;
    }

    @Override
    public FireMat getAge(final int age)
    {
        return getByID(age);
    }

    /**
     * Returns one of Fire sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of Fire or null
     */
    public static FireMat getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of Fire sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of Fire or null
     */
    public static FireMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Returns one of Fire sub-type based on age.
     * It will never return null.
     *
     * @param age age of fire.
     *
     * @return sub-type of Fire
     */
    public static FireMat getFire(final int age)
    {
        final FireMat fire = getByID(age);
        if (fire == null)
        {
            return FIRE_0;
        }
        return fire;
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final FireMat element)
    {
        byID.put(element.getType(), element);
        byName.put(element.name(), element);
    }

    static
    {
        FireMat.register(FIRE_0);
        FireMat.register(FIRE_1);
        FireMat.register(FIRE_2);
        FireMat.register(FIRE_3);
        FireMat.register(FIRE_4);
        FireMat.register(FIRE_5);
        FireMat.register(FIRE_6);
        FireMat.register(FIRE_7);
        FireMat.register(FIRE_8);
        FireMat.register(FIRE_9);
        FireMat.register(FIRE_10);
        FireMat.register(FIRE_11);
        FireMat.register(FIRE_12);
        FireMat.register(FIRE_13);
        FireMat.register(FIRE_14);
        FireMat.register(FIRE_15);
    }
}
