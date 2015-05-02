package org.diorite.material.blocks.loose;

import java.util.Map;

import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.utils.collections.SimpleStringHashMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing block "Sand" and all its subtypes.
 */
public class SandMat extends LooseMat
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final byte  USED_DATA_VALUES = 2;
    /**
     * Blast resistance of block, can be changed only before server start.
     * Final copy of blast resistance from {@link MagicNumbers} class.
     */
    public static final float BLAST_RESISTANCE = MagicNumbers.MATERIAL__SAND__BLAST_RESISTANCE;
    /**
     * Hardness of block, can be changed only before server start.
     * Final copy of hardness from {@link MagicNumbers} class.
     */
    public static final float HARDNESS         = MagicNumbers.MATERIAL__SAND__HARDNESS;

    public static final SandMat SAND     = new SandMat();
    public static final SandMat SAND_RED = new SandMat("RED", 0x01);

    private static final Map<String, SandMat>    byName = new SimpleStringHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<SandMat> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    @SuppressWarnings("MagicNumber")
    protected SandMat()
    {
        super("SPONGE", 12, "minecraft:sand", "SPONGE", (byte) 0x00);
    }

    protected SandMat(final String enumName, final int type)
    {
        super(SAND.name(), SAND.getId(), SAND.getMinecraftId(), enumName, (byte) type);
    }

    protected SandMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type)
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
    public SandMat getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public SandMat getType(final int id)
    {
        return getByID(id);
    }

    /**
     * Returns one of Sand sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of Sand or null
     */
    public static SandMat getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of Sand sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of Sand or null
     */
    public static SandMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final SandMat element)
    {
        byID.put(element.getType(), element);
        byName.put(element.name(), element);
    }

    static
    {
        SandMat.register(SAND);
        SandMat.register(SAND_RED);
    }
}
