package org.diorite.material.blocks.tools;

import java.util.Map;

import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.material.BlockMaterialData;
import org.diorite.utils.collections.SimpleStringHashMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing block "Barrier" and all its subtypes.
 */
public class Barrier extends BlockMaterialData
{	
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final byte  USED_DATA_VALUES = 1;	
    /**
     * Blast resistance of block, can be changed only before server start.
     * Final copy of blast resistance from {@link MagicNumbers} class.
     */
    public static final float BLAST_RESISTANCE = MagicNumbers.MATERIAL__BARRIER__BLAST_RESISTANCE;	
    /**
     * Hardness of block, can be changed only before server start.
     * Final copy of hardness from {@link MagicNumbers} class.
     */
    public static final float HARDNESS         = MagicNumbers.MATERIAL__BARRIER__HARDNESS;

    public static final Barrier BARRIER = new Barrier();

    private static final Map<String, Barrier>    byName = new SimpleStringHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<Barrier> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    @SuppressWarnings("MagicNumber")
    protected Barrier()
    {
        super("BARRIER", 166, "minecraft:barrier", "BARRIER", (byte) 0x00);
    }

    public Barrier(final String enumName, final int type)
    {
        super(BARRIER.name(), BARRIER.getId(), BARRIER.getMinecraftId(), enumName, (byte) type);
    }

    public Barrier(final int maxStack, final String typeName, final byte type)
    {
        super(BARRIER.name(), BARRIER.getId(), BARRIER.getMinecraftId(), maxStack, typeName, type);
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
    public Barrier getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public Barrier getType(final int id)
    {
        return getByID(id);
    }

    /**
     * Returns one of Barrier sub-type based on sub-id, may return null
     * @param id sub-type id
     * @return sub-type of Barrier or null
     */
    public static Barrier getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of Barrier sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     * @param name name of sub-type
     * @return sub-type of Barrier or null
     */
    public static Barrier getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     * @param element sub-type to register
     */
    public static void register(final Barrier element)
    {
        byID.put(element.getType(), element);
        byName.put(element.name(), element);
    }

    static
    {
        Barrier.register(BARRIER);
    }
}
