package org.diorite.material.blocks.nether;

import java.util.Map;

import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.material.BlockMaterialData;
import org.diorite.utils.collections.SimpleStringHashMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing block "SoulSand" and all its subtypes.
 */
public class SoulSand extends BlockMaterialData
{	
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final byte  USED_DATA_VALUES = 1;	
    /**
     * Blast resistance of block, can be changed only before server start.
     * Final copy of blast resistance from {@link MagicNumbers} class.
     */
    public static final float BLAST_RESISTANCE = MagicNumbers.MATERIAL__SOUL_SAND__BLAST_RESISTANCE;	
    /**
     * Hardness of block, can be changed only before server start.
     * Final copy of hardness from {@link MagicNumbers} class.
     */
    public static final float HARDNESS         = MagicNumbers.MATERIAL__SOUL_SAND__HARDNESS;

    public static final SoulSand SOUL_SAND = new SoulSand();

    private static final Map<String, SoulSand>    byName = new SimpleStringHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<SoulSand> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    @SuppressWarnings("MagicNumber")
    protected SoulSand()
    {
        super("SOUL_SAND", 88, "minecraft:soul_sand", "SOUL_SAND", (byte) 0x00);
    }

    public SoulSand(final String enumName, final int type)
    {
        super(SOUL_SAND.name(), SOUL_SAND.getId(), SOUL_SAND.getMinecraftId(), enumName, (byte) type);
    }

    public SoulSand(final int maxStack, final String typeName, final byte type)
    {
        super(SOUL_SAND.name(), SOUL_SAND.getId(), SOUL_SAND.getMinecraftId(), maxStack, typeName, type);
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
    public SoulSand getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public SoulSand getType(final int id)
    {
        return getByID(id);
    }

    public static SoulSand getByID(final int id)
    {
        return byID.get((byte) id);
    }

    public static SoulSand getByEnumName(final String name)
    {
        return byName.get(name);
    }

    public static void register(final SoulSand element)
    {
        byID.put(element.getType(), element);
        byName.put(element.name(), element);
    }

    static
    {
        SoulSand.register(SOUL_SAND);
    }
}
