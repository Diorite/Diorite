package org.diorite.material.blocks.redstone;

import java.util.Map;

import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.utils.collections.SimpleStringHashMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing block "RedstoneTorchOn" and all its subtypes.
 */
public class RedstoneTorchOn extends RedstoneTorch
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
    public static final float BLAST_RESISTANCE = MagicNumbers.MATERIAL__REDSTONE_TORCH_ON__BLAST_RESISTANCE;	
    /**
     * Hardness of block, can be changed only before server start.
     * Final copy of hardness from {@link MagicNumbers} class.
     */
    public static final float HARDNESS         = MagicNumbers.MATERIAL__REDSTONE_TORCH_ON__HARDNESS;

    public static final RedstoneTorchOn REDSTONE_TORCH_ON = new RedstoneTorchOn();

    private static final Map<String, RedstoneTorchOn>    byName = new SimpleStringHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<RedstoneTorchOn> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    @SuppressWarnings("MagicNumber")
    protected RedstoneTorchOn()
    {
        super("REDSTONE_TORCH_ON", 76, "minecraft:redstone_torch", "REDSTONE_TORCH_ON", (byte) 0x00);
    }

    public RedstoneTorchOn(final String enumName, final int type)
    {
        super(REDSTONE_TORCH_ON.name(), REDSTONE_TORCH_ON.getId(), REDSTONE_TORCH_ON.getMinecraftId(), enumName, (byte) type);
    }

    public RedstoneTorchOn(final int maxStack, final String typeName, final byte type)
    {
        super(REDSTONE_TORCH_ON.name(), REDSTONE_TORCH_ON.getId(), REDSTONE_TORCH_ON.getMinecraftId(), maxStack, typeName, type);
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
    public RedstoneTorchOn getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public RedstoneTorchOn getType(final int id)
    {
        return getByID(id);
    }

    @Override
    public boolean isActivated()
    {
        return true;
    }

    /**
     * Returns one of RedstoneTorchOn sub-type based on sub-id, may return null
     * @param id sub-type id
     * @return sub-type of RedstoneTorchOn or null
     */
    public static RedstoneTorchOn getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of RedstoneTorchOn sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     * @param name name of sub-type
     * @return sub-type of RedstoneTorchOn or null
     */
    public static RedstoneTorchOn getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     * @param element sub-type to register
     */
    public static void register(final RedstoneTorchOn element)
    {
        byID.put(element.getType(), element);
        byName.put(element.name(), element);
    }

    static
    {
        RedstoneTorchOn.register(REDSTONE_TORCH_ON);
    }
}
