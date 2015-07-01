package org.diorite.material.blocks.stony.oreblocks;

import java.util.Map;

import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.material.Material;
import org.diorite.material.blocks.stony.ore.OreMat;
import org.diorite.utils.collections.maps.CaseInsensitiveMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing block "DiamondBlock" and all its subtypes.
 */
public class DiamondBlockMat extends OreBlockMat
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final byte  USED_DATA_VALUES = 1;
    /**
     * Blast resistance of block, can be changed only before server start.
     * Final copy of blast resistance from {@link MagicNumbers} class.
     */
    public static final float BLAST_RESISTANCE = MagicNumbers.MATERIAL__DIAMOND_BLOCK__BLAST_RESISTANCE;
    /**
     * Hardness of block, can be changed only before server start.
     * Final copy of hardness from {@link MagicNumbers} class.
     */
    public static final float HARDNESS         = MagicNumbers.MATERIAL__DIAMOND_BLOCK__HARDNESS;

    public static final DiamondBlockMat DIAMOND_BLOCK = new DiamondBlockMat();

    private static final Map<String, DiamondBlockMat>    byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<DiamondBlockMat> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    @SuppressWarnings("MagicNumber")
    protected DiamondBlockMat()
    {
        super("DIAMOND_BLOCK", 57, "minecraft:diamond_block", "DIAMOND_BLOCK", (byte) 0x00, Material.DIAMOND_ORE);
    }

    protected DiamondBlockMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type, final OreMat ore)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, ore);
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
    public DiamondBlockMat getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public DiamondBlockMat getType(final int id)
    {
        return getByID(id);
    }

    /**
     * Returns one of DiamondBlock sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of DiamondBlock or null
     */
    public static DiamondBlockMat getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of DiamondBlock sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of DiamondBlock or null
     */
    public static DiamondBlockMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final DiamondBlockMat element)
    {
        byID.put((byte) element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    @Override
    public DiamondBlockMat[] types()
    {
        return DiamondBlockMat.diamondBlockTypes();
    }

    /**
     * @return array that contains all sub-types of this block.
     */
    public static DiamondBlockMat[] diamondBlockTypes()
    {
        return byID.values(new DiamondBlockMat[byID.size()]);
    }

    static
    {
        DiamondBlockMat.register(DIAMOND_BLOCK);
    }
}
