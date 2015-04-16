package org.diorite.material.blocks.stony.ore;

import java.util.Map;

import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.utils.collections.SimpleStringHashMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing block "DiamondOre" and all its subtypes.
 */
public class DiamondOre extends Ore
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final byte  USED_DATA_VALUES = 1;
    /**
     * Blast resistance of block, can be changed only before server start.
     * Final copy of blast resistance from {@link MagicNumbers} class.
     */
    public static final float BLAST_RESISTANCE = MagicNumbers.MATERIAL__DIAMOND_ORE__BLAST_RESISTANCE;
    /**
     * Hardness of block, can be changed only before server start.
     * Final copy of hardness from {@link MagicNumbers} class.
     */
    public static final float HARDNESS         = MagicNumbers.MATERIAL__DIAMOND_ORE__HARDNESS;

    public static final DiamondOre DIAMOND_ORE = new DiamondOre();

    private static final Map<String, DiamondOre>    byName = new SimpleStringHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<DiamondOre> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    @SuppressWarnings("MagicNumber")
    protected DiamondOre()
    {
        super("DIAMOND_ORE", 56, "minecraft:diamond_ore", "DIAMOND_ORE", (byte) 0x00);
    }

    public DiamondOre(final String enumName, final int type)
    {
        super(DIAMOND_ORE.name(), DIAMOND_ORE.getId(), DIAMOND_ORE.getMinecraftId(), enumName, (byte) type);
    }

    public DiamondOre(final int maxStack, final String typeName, final byte type)
    {
        super(DIAMOND_ORE.name(), DIAMOND_ORE.getId(), DIAMOND_ORE.getMinecraftId(), maxStack, typeName, type);
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
    public DiamondOre getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public DiamondOre getType(final int id)
    {
        return getByID(id);
    }

    /**
     * Returns one of DiamondOre sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of DiamondOre or null
     */
    public static DiamondOre getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of DiamondOre sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of DiamondOre or null
     */
    public static DiamondOre getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final DiamondOre element)
    {
        byID.put(element.getType(), element);
        byName.put(element.name(), element);
    }

    static
    {
        DiamondOre.register(DIAMOND_ORE);
    }
}
