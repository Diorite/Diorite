package org.diorite.material.blocks.others;

import java.util.Map;

import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.material.BlockMaterialData;
import org.diorite.utils.collections.SimpleStringHashMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing block "Fire" and all its subtypes.
 */
@SuppressWarnings("MagicNumber")
public class Fire extends BlockMaterialData
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

    public static final Fire FIRE_0  = new Fire();
    public static final Fire FIRE_1  = new Fire(0x1);
    public static final Fire FIRE_2  = new Fire(0x2);
    public static final Fire FIRE_3  = new Fire(0x3);
    public static final Fire FIRE_4  = new Fire(0x4);
    public static final Fire FIRE_5  = new Fire(0x5);
    public static final Fire FIRE_6  = new Fire(0x6);
    public static final Fire FIRE_7  = new Fire(0x7);
    public static final Fire FIRE_8  = new Fire(0x8);
    public static final Fire FIRE_9  = new Fire(0x9);
    public static final Fire FIRE_10 = new Fire(0xA);
    public static final Fire FIRE_11 = new Fire(0xB);
    public static final Fire FIRE_12 = new Fire(0xC);
    public static final Fire FIRE_13 = new Fire(0xD);
    public static final Fire FIRE_14 = new Fire(0xE);
    public static final Fire FIRE_15 = new Fire(0xF);

    private static final Map<String, Fire>    byName = new SimpleStringHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<Fire> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    @SuppressWarnings("MagicNumber")
    protected Fire()
    {
        super("FIRE", 51, "minecraft:fire", "0", (byte) 0x0);
    }

    public Fire(final int age)
    {
        super(FIRE_0.name(), FIRE_0.getId(), FIRE_0.getMinecraftId(), Integer.toString(age), (byte) age);
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
    public Fire getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public Fire getType(final int id)
    {
        return getByID(id);
    }

    /**
     * Returns one of Fire sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of Fire or null
     */
    public static Fire getByID(final int id)
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
    public static Fire getByEnumName(final String name)
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
    public static Fire getFire(final int age)
    {
        return getByID(age);
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final Fire element)
    {
        byID.put(element.getType(), element);
        byName.put(element.name(), element);
    }

    static
    {
        Fire.register(FIRE_0 );
        Fire.register(FIRE_1 );
        Fire.register(FIRE_2 );
        Fire.register(FIRE_3 );
        Fire.register(FIRE_4 );
        Fire.register(FIRE_5 );
        Fire.register(FIRE_6 );
        Fire.register(FIRE_7 );
        Fire.register(FIRE_8 );
        Fire.register(FIRE_9 );
        Fire.register(FIRE_10);
        Fire.register(FIRE_11);
        Fire.register(FIRE_12);
        Fire.register(FIRE_13);
        Fire.register(FIRE_14);
        Fire.register(FIRE_15);
    }
}
