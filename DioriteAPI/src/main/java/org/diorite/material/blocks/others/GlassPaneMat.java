package org.diorite.material.blocks.others;

import java.util.Map;

import org.diorite.material.BlockMaterialData;
import org.diorite.material.blocks.FenceMat;
import org.diorite.utils.collections.maps.CaseInsensitiveMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing block "GlassPane" and all its subtypes.
 */
public class GlassPaneMat extends BlockMaterialData implements FenceMat
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final int USED_DATA_VALUES = 1;

    public static final GlassPaneMat GLASS_PANE = new GlassPaneMat();

    private static final Map<String, GlassPaneMat>    byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<GlassPaneMat> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR, Byte.MIN_VALUE);

    @SuppressWarnings("MagicNumber")
    protected GlassPaneMat()
    {
        super("GLASS_PANE", 102, "minecraft:glass_pane", "GLASS_PANE", (byte) 0x00, 0.3f, 1.5f);
    }

    protected GlassPaneMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type, final float hardness, final float blastResistance)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, hardness, blastResistance);
    }

    @Override
    public GlassPaneMat getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public GlassPaneMat getType(final int id)
    {
        return getByID(id);
    }

    /**
     * Returns one of GlassPane sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of GlassPane or null
     */
    public static GlassPaneMat getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of GlassPane sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of GlassPane or null
     */
    public static GlassPaneMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final GlassPaneMat element)
    {
        byID.put((byte) element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    @Override
    public GlassPaneMat[] types()
    {
        return GlassPaneMat.glassPaneTypes();
    }

    /**
     * @return array that contains all sub-types of this block.
     */
    public static GlassPaneMat[] glassPaneTypes()
    {
        return byID.values(new GlassPaneMat[byID.size()]);
    }

    static
    {
        GlassPaneMat.register(GLASS_PANE);
    }
}
