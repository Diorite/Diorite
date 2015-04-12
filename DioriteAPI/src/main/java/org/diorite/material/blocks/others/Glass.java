package org.diorite.material.blocks.others;

import java.util.Map;

import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.material.BlockMaterialData;
import org.diorite.utils.collections.SimpleStringHashMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

public class Glass extends BlockMaterialData
{	
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final byte  USED_DATA_VALUES = 1;	
    /**
     * Blast resistance of block, can be changed only before server start.
     * Final copy of blast resistance from {@link MagicNumbers} class.
     */
    public static final float BLAST_RESISTANCE = MagicNumbers.MATERIAL__GLASS__BLAST_RESISTANCE;	
    /**
     * Hardness of block, can be changed only before server start.
     * Final copy of hardness from {@link MagicNumbers} class.
     */
    public static final float HARDNESS         = MagicNumbers.MATERIAL__GLASS__HARDNESS;

    public static final Glass GLASS = new Glass();

    private static final Map<String, Glass>    byName = new SimpleStringHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<Glass> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    @SuppressWarnings("MagicNumber")
    protected Glass()
    {
        super("GLASS", 20, "minecraft:glass", "GLASS", (byte) 0x00);
    }

    public Glass(final String enumName, final int type)
    {
        super(GLASS.name(), GLASS.getId(), GLASS.getMinecraftId(), enumName, (byte) type);
    }

    public Glass(final int maxStack, final String typeName, final byte type)
    {
        super(GLASS.name(), GLASS.getId(), GLASS.getMinecraftId(), maxStack, typeName, type);
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
    public Glass getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public Glass getType(final int id)
    {
        return getByID(id);
    }

    public static Glass getByID(final int id)
    {
        return byID.get((byte) id);
    }

    public static Glass getByEnumName(final String name)
    {
        return byName.get(name);
    }

    public static void register(final Glass element)
    {
        byID.put(element.getType(), element);
        byName.put(element.name(), element);
    }

    static
    {
        Glass.register(GLASS);
    }
}
