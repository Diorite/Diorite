package org.diorite.material.blocks.tools;

import java.util.Map;

import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.material.BlockMaterialData;
import org.diorite.material.blocks.Activatable;
import org.diorite.utils.collections.SimpleStringHashMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing block "Tripwire" and all its subtypes.
 */
public class Tripwire extends BlockMaterialData implements Activatable
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
    public static final float BLAST_RESISTANCE = MagicNumbers.MATERIAL__TRIPWIRE__BLAST_RESISTANCE;	
    /**
     * Hardness of block, can be changed only before server start.
     * Final copy of hardness from {@link MagicNumbers} class.
     */
    public static final float HARDNESS         = MagicNumbers.MATERIAL__TRIPWIRE__HARDNESS;

    public static final Tripwire TRIPWIRE = new Tripwire();

    private static final Map<String, Tripwire>    byName = new SimpleStringHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<Tripwire> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    @SuppressWarnings("MagicNumber")
    protected Tripwire()
    {
        super("TRIPWIRE", 132, "minecraft:tripwire", "TRIPWIRE", (byte) 0x00);
    }

    public Tripwire(final String enumName, final int type)
    {
        super(TRIPWIRE.name(), TRIPWIRE.getId(), TRIPWIRE.getMinecraftId(), enumName, (byte) type);
    }

    public Tripwire(final int maxStack, final String typeName, final byte type)
    {
        super(TRIPWIRE.name(), TRIPWIRE.getId(), TRIPWIRE.getMinecraftId(), maxStack, typeName, type);
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
    public Tripwire getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public Tripwire getType(final int id)
    {
        return getByID(id);
    }

    @Override
    public boolean isActivated()
    {
        return false; // TODO: implement
    }

    @Override
    public BlockMaterialData getActivated(final boolean activate)
    {
        return null; // TODO: implement
    }

    public static Tripwire getByID(final int id)
    {
        return byID.get((byte) id);
    }

    public static Tripwire getByEnumName(final String name)
    {
        return byName.get(name);
    }

    public static void register(final Tripwire element)
    {
        byID.put(element.getType(), element);
        byName.put(element.name(), element);
    }

    static
    {
        Tripwire.register(TRIPWIRE);
    }
}
