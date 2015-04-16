package org.diorite.material.blocks.tools;

import java.util.Map;

import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.material.BlockMaterialData;
import org.diorite.material.blocks.ContainerBlock;
import org.diorite.utils.collections.SimpleStringHashMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing block "TrappedChest" and all its subtypes.
 */
public class TrappedChest extends BlockMaterialData implements ContainerBlock
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
    public static final float BLAST_RESISTANCE = MagicNumbers.MATERIAL__TRAPPED_CHEST__BLAST_RESISTANCE;
    /**
     * Hardness of block, can be changed only before server start.
     * Final copy of hardness from {@link MagicNumbers} class.
     */
    public static final float HARDNESS         = MagicNumbers.MATERIAL__TRAPPED_CHEST__HARDNESS;

    public static final TrappedChest TRAPPED_CHEST = new TrappedChest();

    private static final Map<String, TrappedChest>    byName = new SimpleStringHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<TrappedChest> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    @SuppressWarnings("MagicNumber")
    protected TrappedChest()
    {
        super("TRAPPED_CHEST", 146, "minecraft:trapped_chest", "TRAPPED_CHEST", (byte) 0x00);
    }

    public TrappedChest(final String enumName, final int type)
    {
        super(TRAPPED_CHEST.name(), TRAPPED_CHEST.getId(), TRAPPED_CHEST.getMinecraftId(), enumName, (byte) type);
    }

    public TrappedChest(final int maxStack, final String typeName, final byte type)
    {
        super(TRAPPED_CHEST.name(), TRAPPED_CHEST.getId(), TRAPPED_CHEST.getMinecraftId(), maxStack, typeName, type);
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
    public TrappedChest getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public TrappedChest getType(final int id)
    {
        return getByID(id);
    }

    /**
     * Returns one of TrappedChest sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of TrappedChest or null
     */
    public static TrappedChest getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of TrappedChest sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of TrappedChest or null
     */
    public static TrappedChest getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final TrappedChest element)
    {
        byID.put(element.getType(), element);
        byName.put(element.name(), element);
    }

    static
    {
        TrappedChest.register(TRAPPED_CHEST);
    }
}
