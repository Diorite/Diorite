package org.diorite.material.blocks.stony;

import java.util.Map;

import org.diorite.utils.collections.maps.CaseInsensitiveMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing 'Obsidian' block material in minecraft. <br>
 * ID of block: 49 <br>
 * String ID of block: minecraft:obsidian <br>
 * Hardness: 50 <br>
 * Blast Resistance 6000
 */
@SuppressWarnings("JavaDoc")
public class ObsidianMat extends StonyMat
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final int USED_DATA_VALUES = 1;

    public static final ObsidianMat OBSIDIAN = new ObsidianMat();

    private static final Map<String, ObsidianMat>    byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<ObsidianMat> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR, Byte.MIN_VALUE);

    @SuppressWarnings("MagicNumber")
    protected ObsidianMat()
    {
        super("OBSIDIAN", 49, "minecraft:obsidian", "OBSIDIAN", (byte) 0x00, 50, 6000);
    }

    protected ObsidianMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type, final float hardness, final float blastResistance)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, hardness, blastResistance);
    }

    @Override
    public ObsidianMat getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public ObsidianMat getType(final int id)
    {
        return getByID(id);
    }

    /**
     * Returns one of Obsidian sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of Obsidian or null
     */
    public static ObsidianMat getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of Obsidian sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of Obsidian or null
     */
    public static ObsidianMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final ObsidianMat element)
    {
        byID.put((byte) element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    @Override
    public ObsidianMat[] types()
    {
        return ObsidianMat.obsidianTypes();
    }

    /**
     * @return array that contains all sub-types of this block.
     */
    public static ObsidianMat[] obsidianTypes()
    {
        return byID.values(new ObsidianMat[byID.size()]);
    }

    static
    {
        ObsidianMat.register(OBSIDIAN);
    }
}
