package org.diorite.material.blocks.stony.oreblocks;

import java.util.Map;

import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.material.Material;
import org.diorite.material.blocks.stony.ore.OreMat;
import org.diorite.utils.collections.maps.CaseInsensitiveMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing block "EmeraldBlock" and all its subtypes.
 */
public class EmeraldBlockMat extends OreBlockMat
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final byte  USED_DATA_VALUES = 1;

    public static final EmeraldBlockMat EMERALD_BLOCK = new EmeraldBlockMat();

    private static final Map<String, EmeraldBlockMat>    byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<EmeraldBlockMat> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    @SuppressWarnings("MagicNumber")
    protected EmeraldBlockMat()
    {
        super("EMERALD_BLOCK", 133, "minecraft:emerald_block", "EMERALD_BLOCK", (byte) 0x00, Material.EMERALD_ORE, 5, 30);
    }

    protected EmeraldBlockMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type, final OreMat ore, final float hardness, final float blastResistance)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, ore, hardness, blastResistance);
    }

    @Override
    public EmeraldBlockMat getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public EmeraldBlockMat getType(final int id)
    {
        return getByID(id);
    }

    /**
     * Returns one of EmeraldBlock sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of EmeraldBlock or null
     */
    public static EmeraldBlockMat getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of EmeraldBlock sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of EmeraldBlock or null
     */
    public static EmeraldBlockMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final EmeraldBlockMat element)
    {
        byID.put((byte) element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    @Override
    public EmeraldBlockMat[] types()
    {
        return EmeraldBlockMat.emeraldBlockTypes();
    }

    /**
     * @return array that contains all sub-types of this block.
     */
    public static EmeraldBlockMat[] emeraldBlockTypes()
    {
        return byID.values(new EmeraldBlockMat[byID.size()]);
    }

    static
    {
        EmeraldBlockMat.register(EMERALD_BLOCK);
    }
}
