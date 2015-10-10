package org.diorite.material.blocks.others;

import java.util.Map;

import org.diorite.material.BlockMaterialData;
import org.diorite.utils.collections.maps.CaseInsensitiveMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing 'Glass' block material in minecraft. <br>
 * ID of block: 20 <br>
 * String ID of block: minecraft:glass <br>
 * Hardness: 0,3 <br>
 * Blast Resistance 1,5
 */
@SuppressWarnings("JavaDoc")
public class GlassMat extends BlockMaterialData
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final int USED_DATA_VALUES = 1;

    public static final GlassMat GLASS = new GlassMat();

    private static final Map<String, GlassMat>    byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<GlassMat> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR, Byte.MIN_VALUE);

    @SuppressWarnings("MagicNumber")
    protected GlassMat()
    {
        super("GLASS", 20, "minecraft:glass", "GLASS", (byte) 0x00, 0.3f, 1.5f);
    }

    protected GlassMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type, final float hardness, final float blastResistance)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, hardness, blastResistance);
    }

    @Override
    public GlassMat getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public GlassMat getType(final int id)
    {
        return getByID(id);
    }

    /**
     * Returns one of Glass sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of Glass or null
     */
    public static GlassMat getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of Glass sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of Glass or null
     */
    public static GlassMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final GlassMat element)
    {
        byID.put((byte) element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    @Override
    public GlassMat[] types()
    {
        return GlassMat.glassTypes();
    }

    /**
     * @return array that contains all sub-types of this block.
     */
    public static GlassMat[] glassTypes()
    {
        return byID.values(new GlassMat[byID.size()]);
    }

    static
    {
        GlassMat.register(GLASS);
    }
}
