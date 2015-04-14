package org.diorite.material.blocks.others;

import java.util.Map;

import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.material.BlockMaterialData;
import org.diorite.utils.collections.SimpleStringHashMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing block "FlowerPot" and all its subtypes.
 */
public class FlowerPot extends BlockMaterialData
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
    public static final float BLAST_RESISTANCE = MagicNumbers.MATERIAL__FLOWER_POT__BLAST_RESISTANCE;	
    /**
     * Hardness of block, can be changed only before server start.
     * Final copy of hardness from {@link MagicNumbers} class.
     */
    public static final float HARDNESS         = MagicNumbers.MATERIAL__FLOWER_POT__HARDNESS;

    public static final FlowerPot FLOWER_POT = new FlowerPot();

    private static final Map<String, FlowerPot>    byName = new SimpleStringHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<FlowerPot> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    @SuppressWarnings("MagicNumber")
    protected FlowerPot()
    {
        super("FLOWER_POT", 140, "minecraft:flower_pot", "FLOWER_POT", (byte) 0x00);
    }

    public FlowerPot(final String enumName, final int type)
    {
        super(FLOWER_POT.name(), FLOWER_POT.getId(), FLOWER_POT.getMinecraftId(), enumName, (byte) type);
    }

    public FlowerPot(final int maxStack, final String typeName, final byte type)
    {
        super(FLOWER_POT.name(), FLOWER_POT.getId(), FLOWER_POT.getMinecraftId(), maxStack, typeName, type);
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
    public FlowerPot getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public FlowerPot getType(final int id)
    {
        return getByID(id);
    }

    public static FlowerPot getByID(final int id)
    {
        return byID.get((byte) id);
    }

    public static FlowerPot getByEnumName(final String name)
    {
        return byName.get(name);
    }

    public static void register(final FlowerPot element)
    {
        byID.put(element.getType(), element);
        byName.put(element.name(), element);
    }

    static
    {
        FlowerPot.register(FLOWER_POT);
    }
}
