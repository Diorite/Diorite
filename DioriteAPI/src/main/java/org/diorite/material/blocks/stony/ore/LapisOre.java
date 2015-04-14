package org.diorite.material.blocks.stony.ore;

import java.util.Map;

import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.utils.collections.SimpleStringHashMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing block "LapisOre" and all its subtypes.
 */
public class LapisOre extends Ore
{	
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final byte  USED_DATA_VALUES = 1;	
    /**
     * Blast resistance of block, can be changed only before server start.
     * Final copy of blast resistance from {@link MagicNumbers} class.
     */
    public static final float BLAST_RESISTANCE = MagicNumbers.MATERIAL__LAPIS_ORE__BLAST_RESISTANCE;	
    /**
     * Hardness of block, can be changed only before server start.
     * Final copy of hardness from {@link MagicNumbers} class.
     */
    public static final float HARDNESS         = MagicNumbers.MATERIAL__LAPIS_ORE__HARDNESS;

    public static final LapisOre LAPIS_ORE = new LapisOre();

    private static final Map<String, LapisOre>    byName = new SimpleStringHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<LapisOre> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    @SuppressWarnings("MagicNumber")
    protected LapisOre()
    {
        super("LAPIS_ORE", 21, "minecraft:lapis_ore", "LAPIS_ORE", (byte) 0x00);
    }

    public LapisOre(final String enumName, final int type)
    {
        super(LAPIS_ORE.name(), LAPIS_ORE.getId(), LAPIS_ORE.getMinecraftId(), enumName, (byte) type);
    }

    public LapisOre(final int maxStack, final String typeName, final byte type)
    {
        super(LAPIS_ORE.name(), LAPIS_ORE.getId(), LAPIS_ORE.getMinecraftId(), maxStack, typeName, type);
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
    public LapisOre getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public LapisOre getType(final int id)
    {
        return getByID(id);
    }

    public static LapisOre getByID(final int id)
    {
        return byID.get((byte) id);
    }

    public static LapisOre getByEnumName(final String name)
    {
        return byName.get(name);
    }

    public static void register(final LapisOre element)
    {
        byID.put(element.getType(), element);
        byName.put(element.name(), element);
    }

    static
    {
        LapisOre.register(LAPIS_ORE);
    }
}
