package org.diorite.material.blocks.stony.oreblocks;

import java.util.Map;

import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.material.Material;
import org.diorite.material.blocks.stony.ore.OreMat;
import org.diorite.utils.collections.maps.CaseInsensitiveMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing block "CoalBlock" and all its subtypes.
 */
public class CoalBlockMat extends OreBlockMat
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final byte  USED_DATA_VALUES = 1;

    public static final CoalBlockMat COAL_BLOCK = new CoalBlockMat();

    private static final Map<String, CoalBlockMat>    byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<CoalBlockMat> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    @SuppressWarnings("MagicNumber")
    protected CoalBlockMat()
    {
        super("COAL_BLOCK", 173, "minecraft:coal_block", "COAL_BLOCK", (byte) 0x00, Material.COAL_ORE, 5, 30);
    }

    protected CoalBlockMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type, final OreMat ore, final float hardness, final float blastResistance)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, ore, hardness, blastResistance);
    }

    @Override
    public CoalBlockMat getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public CoalBlockMat getType(final int id)
    {
        return getByID(id);
    }

    /**
     * Returns one of CoalBlock sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of CoalBlock or null
     */
    public static CoalBlockMat getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of CoalBlock sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of CoalBlock or null
     */
    public static CoalBlockMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final CoalBlockMat element)
    {
        byID.put((byte) element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    @Override
    public CoalBlockMat[] types()
    {
        return CoalBlockMat.coalBlockTypes();
    }

    /**
     * @return array that contains all sub-types of this block.
     */
    public static CoalBlockMat[] coalBlockTypes()
    {
        return byID.values(new CoalBlockMat[byID.size()]);
    }

    static
    {
        CoalBlockMat.register(COAL_BLOCK);
    }
}
