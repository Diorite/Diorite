package org.diorite.material.blocks.redstone;

import java.util.Map;

import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.utils.collections.SimpleStringHashMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing block "RedstoneTorchOff" and all its subtypes.
 */
public class RedstoneTorchOff extends RedstoneTorch
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
    public static final float BLAST_RESISTANCE = MagicNumbers.MATERIAL__REDSTONE_TORCH_OFF__BLAST_RESISTANCE;	
    /**
     * Hardness of block, can be changed only before server start.
     * Final copy of hardness from {@link MagicNumbers} class.
     */
    public static final float HARDNESS         = MagicNumbers.MATERIAL__REDSTONE_TORCH_OFF__HARDNESS;

    public static final RedstoneTorchOff REDSTONE_TORCH_OFF = new RedstoneTorchOff();

    private static final Map<String, RedstoneTorchOff>    byName = new SimpleStringHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<RedstoneTorchOff> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    @SuppressWarnings("MagicNumber")
    protected RedstoneTorchOff()
    {
        super("REDSTONE_TORCH_OFF", 75, "minecraft:unlit_redstone_torch", "REDSTONE_TORCH_OFF", (byte) 0x00);
    }

    public RedstoneTorchOff(final String enumName, final int type)
    {
        super(REDSTONE_TORCH_OFF.name(), REDSTONE_TORCH_OFF.getId(), REDSTONE_TORCH_OFF.getMinecraftId(), enumName, (byte) type);
    }

    public RedstoneTorchOff(final int maxStack, final String typeName, final byte type)
    {
        super(REDSTONE_TORCH_OFF.name(), REDSTONE_TORCH_OFF.getId(), REDSTONE_TORCH_OFF.getMinecraftId(), maxStack, typeName, type);
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
    public RedstoneTorchOff getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public RedstoneTorchOff getType(final int id)
    {
        return getByID(id);
    }

    @Override
    public boolean isActivated()
    {
        return false;
    }

    /**
     * Returns one of RedstoneTorchOff sub-type based on sub-id, may return null
     * @param id sub-type id
     * @return sub-type of RedstoneTorchOff or null
     */
    public static RedstoneTorchOff getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of RedstoneTorchOff sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     * @param name name of sub-type
     * @return sub-type of RedstoneTorchOff or null
     */
    public static RedstoneTorchOff getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     * @param element sub-type to register
     */
    public static void register(final RedstoneTorchOff element)
    {
        byID.put(element.getType(), element);
        byName.put(element.name(), element);
    }

    static
    {
        RedstoneTorchOff.register(REDSTONE_TORCH_OFF);
    }
}
