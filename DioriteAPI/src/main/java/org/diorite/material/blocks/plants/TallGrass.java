package org.diorite.material.blocks.plants;

import java.util.Map;

import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.utils.collections.SimpleStringHashMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing block "TallGrass" and all its subtypes.
 */
public class TallGrass extends Plant
{	
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final byte  USED_DATA_VALUES = 3;	
    /**
     * Blast resistance of block, can be changed only before server start.
     * Final copy of blast resistance from {@link MagicNumbers} class.
     */
    public static final float BLAST_RESISTANCE = MagicNumbers.MATERIAL__TALL_GRASS__BLAST_RESISTANCE;	
    /**
     * Hardness of block, can be changed only before server start.
     * Final copy of hardness from {@link MagicNumbers} class.
     */
    public static final float HARDNESS         = MagicNumbers.MATERIAL__TALL_GRASS__HARDNESS;

    public static final TallGrass TALL_GRASS_SHRUB = new TallGrass();
    public static final TallGrass TALL_GRASS_LONG  = new TallGrass("LONG", 0x01);
    public static final TallGrass TALL_GRASS_FERN  = new TallGrass("FERN", 0x02);

    private static final Map<String, TallGrass>    byName = new SimpleStringHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<TallGrass> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    @SuppressWarnings("MagicNumber")
    protected TallGrass()
    {
        super("TALL_GRASS", 31, "minecraft:tallgrass", "SHRUB", (byte) 0x00);
    }

    public TallGrass(final String enumName, final int type)
    {
        super(TALL_GRASS_SHRUB.name(), TALL_GRASS_SHRUB.getId(), TALL_GRASS_SHRUB.getMinecraftId(), enumName, (byte) type);
    }

    public TallGrass(final int maxStack, final String typeName, final byte type)
    {
        super(TALL_GRASS_SHRUB.name(), TALL_GRASS_SHRUB.getId(), TALL_GRASS_SHRUB.getMinecraftId(), maxStack, typeName, type);
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
    public TallGrass getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public TallGrass getType(final int id)
    {
        return getByID(id);
    }

    public static TallGrass getByID(final int id)
    {
        return byID.get((byte) id);
    }

    public static TallGrass getByEnumName(final String name)
    {
        return byName.get(name);
    }

    public static void register(final TallGrass element)
    {
        byID.put(element.getType(), element);
        byName.put(element.name(), element);
    }

    static
    {
        TallGrass.register(TALL_GRASS_SHRUB);
        TallGrass.register(TALL_GRASS_LONG);
        TallGrass.register(TALL_GRASS_FERN);
    }
}
