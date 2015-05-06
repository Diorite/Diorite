package org.diorite.material.blocks.plants;

import java.util.Map;

import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.utils.collections.SimpleStringHashMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing block "DeadBush" and all its subtypes.
 */
public class DeadBushMat extends FlowerMat
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final byte  USED_DATA_VALUES = 1;
    /**
     * Blast resistance of block, can be changed only before server start.
     * Final copy of blast resistance from {@link MagicNumbers} class.
     */
    public static final float BLAST_RESISTANCE = MagicNumbers.MATERIAL__DEAD_BUSH__BLAST_RESISTANCE;
    /**
     * Hardness of block, can be changed only before server start.
     * Final copy of hardness from {@link MagicNumbers} class.
     */
    public static final float HARDNESS         = MagicNumbers.MATERIAL__DEAD_BUSH__HARDNESS;

    public static final DeadBushMat DEAD_BUSH = new DeadBushMat();

    private static final Map<String, DeadBushMat>    byName = new SimpleStringHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<DeadBushMat> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    @SuppressWarnings("MagicNumber")
    protected DeadBushMat()
    {
        super("DEAD_BUSH", 32, "minecraft:deadbush", (byte) 0x00, FlowerTypeMat.DEAD_BUSH);
    }

    protected DeadBushMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type, final FlowerTypeMat flowerType)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, flowerType);
    }

    @Override
    public DeadBushMat getFlowerType(final FlowerTypeMat flowerType)
    {
        return getDeadBush(flowerType);
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
    public DeadBushMat getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public DeadBushMat getType(final int id)
    {
        return getByID(id);
    }

    /**
     * Returns one of DeadBush sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of DeadBush or null
     */
    public static DeadBushMat getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of DeadBush sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of DeadBush or null
     */
    public static DeadBushMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Returns one of DeadBush sub-type based on {@link FlowerTypeMat}
     * If this flower don't supprot given type, it will return default one.
     *
     * @param flowerType type of flower
     *
     * @return sub-type of DeadBush
     */
    public static DeadBushMat getDeadBush(final FlowerTypeMat flowerType)
    {
        for (final DeadBushMat mat : byName.values())
        {
            if (mat.flowerType == flowerType)
            {
                return mat;
            }
        }
        return DEAD_BUSH;
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final DeadBushMat element)
    {
        byID.put((byte) element.getType(), element);
        byName.put(element.name(), element);
    }

    static
    {
        DeadBushMat.register(DEAD_BUSH);
    }
}
