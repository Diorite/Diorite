package org.diorite.material.blocks.nether;

import java.util.Map;

import org.diorite.material.BlockMaterialData;
import org.diorite.utils.collections.maps.CaseInsensitiveMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing block "Glowstone" and all its subtypes.
 */
public class GlowstoneMat extends BlockMaterialData
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final int USED_DATA_VALUES = 1;

    public static final GlowstoneMat GLOWSTONE = new GlowstoneMat();

    private static final Map<String, GlowstoneMat>    byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<GlowstoneMat> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    @SuppressWarnings("MagicNumber")
    protected GlowstoneMat()
    {
        super("GLOWSTONE", 89, "minecraft:glowstone", "GLOWSTONE", (byte) 0x00, 0.3f, 1.5f);
    }

    protected GlowstoneMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type, final float hardness, final float blastResistance)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, hardness, blastResistance);
    }

    @Override
    public GlowstoneMat getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public GlowstoneMat getType(final int id)
    {
        return getByID(id);
    }

    /**
     * Returns one of Glowstone sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of Glowstone or null
     */
    public static GlowstoneMat getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of Glowstone sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of Glowstone or null
     */
    public static GlowstoneMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final GlowstoneMat element)
    {
        byID.put((byte) element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    @Override
    public GlowstoneMat[] types()
    {
        return GlowstoneMat.glowstoneTypes();
    }

    /**
     * @return array that contains all sub-types of this block.
     */
    public static GlowstoneMat[] glowstoneTypes()
    {
        return byID.values(new GlowstoneMat[byID.size()]);
    }

    static
    {
        GlowstoneMat.register(GLOWSTONE);
    }
}
