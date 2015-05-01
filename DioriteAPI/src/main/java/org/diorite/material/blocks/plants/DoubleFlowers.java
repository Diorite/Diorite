package org.diorite.material.blocks.plants;

import java.util.Map;

import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.utils.collections.SimpleStringHashMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing block "DoubleFlowers" and all its subtypes.
 */
public class DoubleFlowers extends Plant
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final byte  USED_DATA_VALUES = 7;
    /**
     * Blast resistance of block, can be changed only before server start.
     * Final copy of blast resistance from {@link MagicNumbers} class.
     */
    public static final float BLAST_RESISTANCE = MagicNumbers.MATERIAL__DOUBLE_FLOWERS__BLAST_RESISTANCE;
    /**
     * Hardness of block, can be changed only before server start.
     * Final copy of hardness from {@link MagicNumbers} class.
     */
    public static final float HARDNESS         = MagicNumbers.MATERIAL__DOUBLE_FLOWERS__HARDNESS;

    public static final DoubleFlowers DOUBLE_FLOWERS_SUNFLOWER = new DoubleFlowers();
    public static final DoubleFlowers DOUBLE_FLOWERS_LILAC     = new DoubleFlowers("LILAC", 0x1);
    public static final DoubleFlowers DOUBLE_FLOWERS_TALLGRASS = new DoubleFlowers("TALLGRASS", 0x2);
    public static final DoubleFlowers DOUBLE_FLOWERS_FERN      = new DoubleFlowers("FERN", 0x3);
    public static final DoubleFlowers DOUBLE_FLOWERS_ROSE_BUSH = new DoubleFlowers("ROSE_BUSH", 0x4);
    public static final DoubleFlowers DOUBLE_FLOWERS_PEONY     = new DoubleFlowers("PEONY", 0x5);
    public static final DoubleFlowers DOUBLE_FLOWERS_TOP_ALL   = new DoubleFlowers("TOP_ALL", 0x8);

    private static final Map<String, DoubleFlowers>    byName = new SimpleStringHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<DoubleFlowers> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    @SuppressWarnings("MagicNumber")
    protected DoubleFlowers()
    {
        super("DOUBLE_FLOWERS", 175, "minecraft:double_plant", "SUNFLOWER", (byte) 0x00);
    }

    public DoubleFlowers(final String enumName, final int type)
    {
        super(DOUBLE_FLOWERS_SUNFLOWER.name(), DOUBLE_FLOWERS_SUNFLOWER.getId(), DOUBLE_FLOWERS_SUNFLOWER.getMinecraftId(), enumName, (byte) type);
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
    public DoubleFlowers getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public DoubleFlowers getType(final int id)
    {
        return getByID(id);
    }

    /**
     * Returns one of DoubleFlowers sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of DoubleFlowers or null
     */
    public static DoubleFlowers getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of DoubleFlowers sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of DoubleFlowers or null
     */
    public static DoubleFlowers getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final DoubleFlowers element)
    {
        byID.put(element.getType(), element);
        byName.put(element.name(), element);
    }

    static
    {
        DoubleFlowers.register(DOUBLE_FLOWERS_SUNFLOWER);
        DoubleFlowers.register(DOUBLE_FLOWERS_LILAC);
        DoubleFlowers.register(DOUBLE_FLOWERS_TALLGRASS);
        DoubleFlowers.register(DOUBLE_FLOWERS_FERN);
        DoubleFlowers.register(DOUBLE_FLOWERS_ROSE_BUSH);
        DoubleFlowers.register(DOUBLE_FLOWERS_PEONY);
        DoubleFlowers.register(DOUBLE_FLOWERS_TOP_ALL);
    }
}
