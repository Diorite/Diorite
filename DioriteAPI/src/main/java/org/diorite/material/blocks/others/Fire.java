package org.diorite.material.blocks.others;

import java.util.Map;

import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.material.BlockMaterialData;
import org.diorite.utils.collections.SimpleStringHashMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing block "Fire" and all its subtypes.
 */
public class Fire extends BlockMaterialData
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
    public static final float BLAST_RESISTANCE = MagicNumbers.MATERIAL__FIRE__BLAST_RESISTANCE;	
    /**
     * Hardness of block, can be changed only before server start.
     * Final copy of hardness from {@link MagicNumbers} class.
     */
    public static final float HARDNESS         = MagicNumbers.MATERIAL__FIRE__HARDNESS;

    public static final Fire FIRE = new Fire();

    private static final Map<String, Fire>    byName = new SimpleStringHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<Fire> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    @SuppressWarnings("MagicNumber")
    protected Fire()
    {
        super("FIRE", 51, "minecraft:fire", "FIRE", (byte) 0x00);
    }

    public Fire(final String enumName, final int type)
    {
        super(FIRE.name(), FIRE.getId(), FIRE.getMinecraftId(), enumName, (byte) type);
    }

    public Fire(final int maxStack, final String typeName, final byte type)
    {
        super(FIRE.name(), FIRE.getId(), FIRE.getMinecraftId(), maxStack, typeName, type);
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
    public Fire getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public Fire getType(final int id)
    {
        return getByID(id);
    }

    public static Fire getByID(final int id)
    {
        return byID.get((byte) id);
    }

    public static Fire getByEnumName(final String name)
    {
        return byName.get(name);
    }

    public static void register(final Fire element)
    {
        byID.put(element.getType(), element);
        byName.put(element.name(), element);
    }

    static
    {
        Fire.register(FIRE);
    }
}
