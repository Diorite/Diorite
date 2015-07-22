package org.diorite.material.blocks.earth;

import java.util.Map;

import org.diorite.inventory.item.BaseItemStack;
import org.diorite.material.data.drops.PossibleDrops;
import org.diorite.material.data.drops.PossibleFixedDrop;
import org.diorite.utils.collections.maps.CaseInsensitiveMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing block "Grass" and all its subtypes.
 */
public class GrassMat extends EarthMat
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final int USED_DATA_VALUES = 1;

    public static final GrassMat GRASS = new GrassMat();

    private static final Map<String, GrassMat>    byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<GrassMat> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR, Byte.MIN_VALUE);

    protected GrassMat()
    {
        super("GRASS", 2, "minecraft:grass", "GRASS", (byte) 0x00, 0.6f, 3);
    }

    protected GrassMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type, final float hardness, final float blastResistance)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, hardness, blastResistance);
    }

    @Override
    public GrassMat getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public GrassMat getType(final int id)
    {
        return getByID(id);
    }

    @Override
    protected PossibleDrops initPossibleDrops()
    {
        return new PossibleDrops(new PossibleFixedDrop(new BaseItemStack(DIRT)));
    }

    /**
     * Returns one of Grass sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of Grass or null
     */
    public static GrassMat getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of Grass sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of Grass or null
     */
    public static GrassMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final GrassMat element)
    {
        byID.put((byte) element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    @Override
    public GrassMat[] types()
    {
        return GrassMat.grassTypes();
    }

    /**
     * @return array that contains all sub-types of this block.
     */
    public static GrassMat[] grassTypes()
    {
        return byID.values(new GrassMat[byID.size()]);
    }

    static
    {
        GrassMat.register(GRASS);
    }
}
