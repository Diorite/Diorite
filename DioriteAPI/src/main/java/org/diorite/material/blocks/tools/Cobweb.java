package org.diorite.material.blocks.tools;

import java.util.Map;

import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.material.BlockMaterialData;
import org.diorite.utils.collections.SimpleStringHashMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

public class Cobweb extends BlockMaterialData
{	
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final byte  USED_DATA_VALUES = 1;	
    /**
     * Blast resistance of block, can be changed only before server start.
     * Final copy of blast resistance from {@link MagicNumbers} class.
     */
    public static final float BLAST_RESISTANCE = MagicNumbers.MATERIAL__COBWEB__BLAST_RESISTANCE;	
    /**
     * Hardness of block, can be changed only before server start.
     * Final copy of hardness from {@link MagicNumbers} class.
     */
    public static final float HARDNESS         = MagicNumbers.MATERIAL__COBWEB__HARDNESS;

    public static final Cobweb COBWEB = new Cobweb();

    private static final Map<String, Cobweb>    byName = new SimpleStringHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<Cobweb> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    @SuppressWarnings("MagicNumber")
    protected Cobweb()
    {
        super("COBWEB", 30, "minecraft:web", "COBWEB", (byte) 0x00);
    }

    public Cobweb(final String enumName, final int type)
    {
        super(COBWEB.name(), COBWEB.getId(), COBWEB.getMinecraftId(), enumName, (byte) type);
    }

    public Cobweb(final int maxStack, final String typeName, final byte type)
    {
        super(COBWEB.name(), COBWEB.getId(), COBWEB.getMinecraftId(), maxStack, typeName, type);
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
    public Cobweb getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public Cobweb getType(final int id)
    {
        return getByID(id);
    }

    public static Cobweb getByID(final int id)
    {
        return byID.get((byte) id);
    }

    public static Cobweb getByEnumName(final String name)
    {
        return byName.get(name);
    }

    public static void register(final Cobweb element)
    {
        byID.put(element.getType(), element);
        byName.put(element.name(), element);
    }

    static
    {
        Cobweb.register(COBWEB);
    }
}
