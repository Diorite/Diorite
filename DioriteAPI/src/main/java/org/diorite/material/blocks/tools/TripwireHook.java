package org.diorite.material.blocks.tools;

import java.util.Map;

import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.material.BlockMaterialData;
import org.diorite.material.blocks.Activatable;
import org.diorite.utils.collections.SimpleStringHashMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

public class TripwireHook extends BlockMaterialData implements Activatable
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
    public static final float BLAST_RESISTANCE = MagicNumbers.MATERIAL__TRIPWIRE_HOOK__BLAST_RESISTANCE;	
    /**
     * Hardness of block, can be changed only before server start.
     * Final copy of hardness from {@link MagicNumbers} class.
     */
    public static final float HARDNESS         = MagicNumbers.MATERIAL__TRIPWIRE_HOOK__HARDNESS;

    public static final TripwireHook TRIPWIRE_HOOK = new TripwireHook();

    private static final Map<String, TripwireHook>    byName = new SimpleStringHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<TripwireHook> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    @SuppressWarnings("MagicNumber")
    protected TripwireHook()
    {
        super("TRIPWIRE_HOOK", 131, "minecraft:tripwire_hook", "TRIPWIRE_HOOK", (byte) 0x00);
    }

    public TripwireHook(final String enumName, final int type)
    {
        super(TRIPWIRE_HOOK.name(), TRIPWIRE_HOOK.getId(), TRIPWIRE_HOOK.getMinecraftId(), enumName, (byte) type);
    }

    public TripwireHook(final int maxStack, final String typeName, final byte type)
    {
        super(TRIPWIRE_HOOK.name(), TRIPWIRE_HOOK.getId(), TRIPWIRE_HOOK.getMinecraftId(), maxStack, typeName, type);
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
    public TripwireHook getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public TripwireHook getType(final int id)
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

    public static TripwireHook getByID(final int id)
    {
        return byID.get((byte) id);
    }

    public static TripwireHook getByEnumName(final String name)
    {
        return byName.get(name);
    }

    public static void register(final TripwireHook element)
    {
        byID.put(element.getType(), element);
        byName.put(element.name(), element);
    }

    static
    {
        TripwireHook.register(TRIPWIRE_HOOK);
    }
}
