package org.diorite.material.blocks.plants;

import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.utils.collections.SimpleStringHashMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing block "CarrotsBlock" and all its subtypes.
 */
public class CarrotsBlockMat extends CropsMat
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final byte  USED_DATA_VALUES = 8;
    /**
     * Blast resistance of block, can be changed only before server start.
     * Final copy of blast resistance from {@link MagicNumbers} class.
     */
    public static final float BLAST_RESISTANCE = MagicNumbers.MATERIAL__CARROTS_BLOCK__BLAST_RESISTANCE;
    /**
     * Hardness of block, can be changed only before server start.
     * Final copy of hardness from {@link MagicNumbers} class.
     */
    public static final float HARDNESS         = MagicNumbers.MATERIAL__CARROTS_BLOCK__HARDNESS;

    public static final CarrotsBlockMat CARROTS_BLOCK_0    = new CarrotsBlockMat();
    public static final CarrotsBlockMat CARROTS_BLOCK_1    = new CarrotsBlockMat("1", 0x1);
    public static final CarrotsBlockMat CARROTS_BLOCK_2    = new CarrotsBlockMat("2", 0x2);
    public static final CarrotsBlockMat CARROTS_BLOCK_3    = new CarrotsBlockMat("3", 0x3);
    public static final CarrotsBlockMat CARROTS_BLOCK_4    = new CarrotsBlockMat("4", 0x4);
    public static final CarrotsBlockMat CARROTS_BLOCK_5    = new CarrotsBlockMat("5", 0x5);
    public static final CarrotsBlockMat CARROTS_BLOCK_6    = new CarrotsBlockMat("6", 0x6);
    public static final CarrotsBlockMat CARROTS_BLOCK_RIPE = new CarrotsBlockMat("RIPE", 0x7);

    private static final Map<String, CarrotsBlockMat>    byName = new SimpleStringHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<CarrotsBlockMat> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    protected final int age;

    @SuppressWarnings("MagicNumber")
    protected CarrotsBlockMat()
    {
        super("CARROTS_BLOCK", 141, "minecraft:carrots", "0", (byte) 0x00);
        this.age = 0;
    }

    protected CarrotsBlockMat(final String enumName, final int age)
    {
        super(CARROTS_BLOCK_0.name(), CARROTS_BLOCK_0.getId(), CARROTS_BLOCK_0.getMinecraftId(), enumName, (byte) age);
        this.age = age;
    }

    protected CarrotsBlockMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type, final int age)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type);
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
    public CarrotsBlockMat getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public CarrotsBlockMat getType(final int id)
    {
        return getByID(id);
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("age", this.age).toString();
    }

    @Override
    public int getAge()
    {
        return this.age;
    }

    @Override
    public CarrotsBlockMat getAge(final int age)
    {
        return getByID(age);
    }

    /**
     * Returns one of CarrotsBlock sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of CarrotsBlock or null
     */
    public static CarrotsBlockMat getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of CarrotsBlock sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of CarrotsBlock or null
     */
    public static CarrotsBlockMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Returns one of CarrotsBlock sub-type based on age.
     * It will never return null.
     *
     * @param age age of CarrotsBlock.
     *
     * @return sub-type of CarrotsBlock
     */
    public static CarrotsBlockMat getCarrotsBlock(final int age)
    {
        final CarrotsBlockMat carrotsBlock = getByID(age);
        if (carrotsBlock == null)
        {
            return CARROTS_BLOCK_0;
        }
        return carrotsBlock;
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final CarrotsBlockMat element)
    {
        byID.put((byte) element.getType(), element);
        byName.put(element.name(), element);
    }

    static
    {
        CarrotsBlockMat.register(CARROTS_BLOCK_0);
        CarrotsBlockMat.register(CARROTS_BLOCK_1);
        CarrotsBlockMat.register(CARROTS_BLOCK_2);
        CarrotsBlockMat.register(CARROTS_BLOCK_3);
        CarrotsBlockMat.register(CARROTS_BLOCK_4);
        CarrotsBlockMat.register(CARROTS_BLOCK_5);
        CarrotsBlockMat.register(CARROTS_BLOCK_6);
        CarrotsBlockMat.register(CARROTS_BLOCK_RIPE);
    }
}
