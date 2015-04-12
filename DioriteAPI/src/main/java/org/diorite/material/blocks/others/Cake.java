package org.diorite.material.blocks.others;

import java.util.Map;

import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.material.BlockMaterialData;
import org.diorite.utils.collections.SimpleStringHashMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

public class Cake extends BlockMaterialData
{
    // TODO: auto-generated class, implement other types (sub-ids).	
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final byte  USED_DATA_VALUES = 1;	
    /**
     * Blast resistance of block, can be changed only before server start.
     * Final copy of blast resistance from {@link MagicNumbers} class.
     */
    public static final float BLAST_RESISTANCE = MagicNumbers.MATERIAL__CAKE__BLAST_RESISTANCE;	
    /**
     * Hardness of block, can be changed only before server start.
     * Final copy of hardness from {@link MagicNumbers} class.
     */
    public static final float HARDNESS         = MagicNumbers.MATERIAL__CAKE__HARDNESS;

    public static final Cake CAKE = new Cake();

    private static final Map<String, Cake>    byName = new SimpleStringHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<Cake> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    @SuppressWarnings("MagicNumber")
    protected Cake()
    {
        super("CAKE", 92, "minecraft:cake", "CAKE", (byte) 0x00);
    }

    public Cake(final String enumName, final int type)
    {
        super(CAKE.name(), CAKE.getId(), CAKE.getMinecraftId(), enumName, (byte) type);
    }

    public Cake(final int maxStack, final String typeName, final byte type)
    {
        super(CAKE.name(), CAKE.getId(), CAKE.getMinecraftId(), maxStack, typeName, type);
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
    public Cake getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public Cake getType(final int id)
    {
        return getByID(id);
    }

    public static Cake getByID(final int id)
    {
        return byID.get((byte) id);
    }

    public static Cake getByEnumName(final String name)
    {
        return byName.get(name);
    }

    public static void register(final Cake element)
    {
        byID.put(element.getType(), element);
        byName.put(element.name(), element);
    }

    static
    {
        Cake.register(CAKE);
    }
}
