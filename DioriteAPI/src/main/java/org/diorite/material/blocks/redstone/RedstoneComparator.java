package org.diorite.material.blocks.redstone;

import java.util.Map;

import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.material.BlockMaterialData;
import org.diorite.material.blocks.Activatable;
import org.diorite.utils.collections.SimpleStringHashMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

public class RedstoneComparator extends BlockMaterialData implements Activatable
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
    public static final float BLAST_RESISTANCE = MagicNumbers.MATERIAL__REDSTONE_COMPARATOR__BLAST_RESISTANCE;	
    /**
     * Hardness of block, can be changed only before server start.
     * Final copy of hardness from {@link MagicNumbers} class.
     */
    public static final float HARDNESS         = MagicNumbers.MATERIAL__REDSTONE_COMPARATOR__HARDNESS;

    public static final RedstoneComparator REDSTONE_COMPARATOR = new RedstoneComparator();

    private static final Map<String, RedstoneComparator>    byName = new SimpleStringHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<RedstoneComparator> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    @SuppressWarnings("MagicNumber")
    protected RedstoneComparator()
    {
        super("REDSTONE_COMPARATOR", 149, "minecraft:unpowered_comparator", "REDSTONE_COMPARATOR", (byte) 0x00);
    }

    public RedstoneComparator(final String enumName, final int type)
    {
        super(REDSTONE_COMPARATOR.name(), REDSTONE_COMPARATOR.getId(), REDSTONE_COMPARATOR.getMinecraftId(), enumName, (byte) type);
    }

    public RedstoneComparator(final int maxStack, final String typeName, final byte type)
    {
        super(REDSTONE_COMPARATOR.name(), REDSTONE_COMPARATOR.getId(), REDSTONE_COMPARATOR.getMinecraftId(), maxStack, typeName, type);
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
    public RedstoneComparator getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public RedstoneComparator getType(final int id)
    {
        return getByID(id);
    }

    @Override
    public boolean isActivated()
    {
        return false; // TODO implement
    }

    @Override
    public BlockMaterialData getActivated(final boolean activate)
    {
        return null; // TODO implement
    }

    public static RedstoneComparator getByID(final int id)
    {
        return byID.get((byte) id);
    }

    public static RedstoneComparator getByEnumName(final String name)
    {
        return byName.get(name);
    }

    public static void register(final RedstoneComparator element)
    {
        byID.put(element.getType(), element);
        byName.put(element.name(), element);
    }

    static
    {
        RedstoneComparator.register(REDSTONE_COMPARATOR);
    }
}
