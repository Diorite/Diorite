package org.diorite.material.blocks.plants;

import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.BlockFace;
import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.material.blocks.Directional;
import org.diorite.utils.collections.SimpleStringHashMap;
import org.diorite.utils.math.ByteRange;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing block "Cocoa" and all its subtypes.
 */
public class Cocoa extends Crops implements Directional
{
    /**
     * Age range of Cocoa, from 0 to 2.
     */
    public static final ByteRange AGE_RANGE        = new ByteRange(0, 2);
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final byte      USED_DATA_VALUES = 12;
    /**
     * Blast resistance of block, can be changed only before server start.
     * Final copy of blast resistance from {@link MagicNumbers} class.
     */
    public static final float     BLAST_RESISTANCE = MagicNumbers.MATERIAL__COCOA__BLAST_RESISTANCE;
    /**
     * Hardness of block, can be changed only before server start.
     * Final copy of hardness from {@link MagicNumbers} class.
     */
    public static final float     HARDNESS         = MagicNumbers.MATERIAL__COCOA__HARDNESS;

    public static final Cocoa COCOA_NORTH_0 = new Cocoa();
    public static final Cocoa COCOA_EAST_0  = new Cocoa("EAST_0", BlockFace.EAST, 0);
    public static final Cocoa COCOA_SOUTH_0 = new Cocoa("SOUTH_0", BlockFace.SOUTH, 0);
    public static final Cocoa COCOA_WEST_0  = new Cocoa("WEST_0", BlockFace.WEST, 0);

    public static final Cocoa COCOA_NORTH_1 = new Cocoa("NORTH_1", BlockFace.NORTH, 1);
    public static final Cocoa COCOA_EAST_1  = new Cocoa("EAST_1", BlockFace.EAST, 1);
    public static final Cocoa COCOA_SOUTH_1 = new Cocoa("SOUTH_1", BlockFace.SOUTH, 1);
    public static final Cocoa COCOA_WEST_1  = new Cocoa("WEST_1", BlockFace.WEST, 1);

    public static final Cocoa COCOA_NORTH_RIPE = new Cocoa("NORTH_RIPE", BlockFace.NORTH, 2);
    public static final Cocoa COCOA_EAST_RIPE  = new Cocoa("EAST_RIPE", BlockFace.EAST, 2);
    public static final Cocoa COCOA_SOUTH_RIPE = new Cocoa("SOUTH_RIPE", BlockFace.SOUTH, 2);
    public static final Cocoa COCOA_WEST_RIPE  = new Cocoa("WEST_RIPE", BlockFace.WEST, 2);

    private static final Map<String, Cocoa>    byName = new SimpleStringHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<Cocoa> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    protected final BlockFace face;
    protected final int       age;

    @SuppressWarnings("MagicNumber")
    protected Cocoa()
    {
        super("COCOA_NORTH_0", 127, "minecraft:cocoa", "NORTH_0", (byte) 0x00);
        this.face = BlockFace.NORTH;
        this.age = 0;
    }

    public Cocoa(final String enumName, final BlockFace face, final int age)
    {
        super(COCOA_NORTH_0.name(), COCOA_NORTH_0.getId(), COCOA_NORTH_0.getMinecraftId(), enumName, combine(face, age));
        this.face = face;
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
    public Cocoa getAge(final int age)
    {
        return getByID(combine(this.face, age));
    }

    @Override
    public BlockFace getBlockFacing()
    {
        return this.face;
    }

    @Override
    public Cocoa getBlockFacing(final BlockFace face)
    {
        return getByID(combine(face, this.age));
    }

    @Override
    public Cocoa getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public Cocoa getType(final int id)
    {
        return getByID(id);
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("face", this.face).append("age", this.age).toString();
    }

    /**
     * Returns one of Cocoa sub-type based on {@link BlockFace} and age.
     * It will never return null.
     *
     * @param face facing direction of Cocoa.
     * @param age  age of Cocoa.
     *
     * @return sub-type of Cocoa
     */
    public Cocoa getType(final BlockFace face, final int age)
    {
        return getByID(combine(face, this.age));
    }

    private static byte combine(final BlockFace face, final int age)
    {
        byte result;
        switch (face)
        {
            case EAST:
                result = 0x1;
                break;
            case SOUTH:
                result = 0x2;
                break;
            case WEST:
                result = 0x3;
                break;
            default:
                result = 0x0;
                break;
        }
        result |= (AGE_RANGE.getIn(age) << 2);
        return result;
    }

    /**
     * Returns one of Cocoa sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of Cocoa or null
     */
    public static Cocoa getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of Cocoa sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of Cocoa or null
     */
    public static Cocoa getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Returns one of Cocoa sub-type based on {@link BlockFace} and age.
     * It will never return null.
     *
     * @param face facing direction of Cocoa.
     * @param age  age of Cocoa.
     *
     * @return sub-type of Cocoa
     */
    public static Cocoa getCocoa(final BlockFace face, final int age)
    {
        return getByID(combine(face, age));
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final Cocoa element)
    {
        byID.put(element.getType(), element);
        byName.put(element.name(), element);
    }

    static
    {
        Cocoa.register(COCOA_NORTH_0);
        Cocoa.register(COCOA_EAST_0);
        Cocoa.register(COCOA_SOUTH_0);
        Cocoa.register(COCOA_WEST_0);
        Cocoa.register(COCOA_NORTH_1);
        Cocoa.register(COCOA_EAST_1);
        Cocoa.register(COCOA_SOUTH_1);
        Cocoa.register(COCOA_WEST_1);
        Cocoa.register(COCOA_NORTH_RIPE);
        Cocoa.register(COCOA_EAST_RIPE);
        Cocoa.register(COCOA_SOUTH_RIPE);
        Cocoa.register(COCOA_WEST_RIPE);
    }
}
