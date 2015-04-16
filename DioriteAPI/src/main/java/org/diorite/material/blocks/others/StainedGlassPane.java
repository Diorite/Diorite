package org.diorite.material.blocks.others;

import java.util.Map;

import org.diorite.DyeColor;
import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.material.BlockMaterialData;
import org.diorite.material.Colorable;
import org.diorite.material.Material;
import org.diorite.utils.collections.SimpleStringHashMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing block "StainedGlassPane" and all its subtypes.
 */
public class StainedGlassPane extends BlockMaterialData implements Colorable
{
    // TODO: auto-generated class, implement other types (sub-ids).	
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final byte  USED_DATA_VALUES = 1;
    /**
     * Blast resistance of block, can be changed only before server start.
     * Final copy of blast resistance from {@link MagicNumbers} class.
     */
    public static final float BLAST_RESISTANCE = MagicNumbers.MATERIAL__STAINED_GLASS_PANE__BLAST_RESISTANCE;
    /**
     * Hardness of block, can be changed only before server start.
     * Final copy of hardness from {@link MagicNumbers} class.
     */
    public static final float HARDNESS         = MagicNumbers.MATERIAL__STAINED_GLASS_PANE__HARDNESS;

    public static final StainedGlassPane STAINED_GLASS_PANE = new StainedGlassPane();

    private static final Map<String, StainedGlassPane>    byName = new SimpleStringHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<StainedGlassPane> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    @SuppressWarnings("MagicNumber")
    protected StainedGlassPane()
    {
        super("STAINED_GLASS_PANE", 160, "minecraft:stained_glass_pane", "STAINED_GLASS_PANE", (byte) 0x00);
    }

    public StainedGlassPane(final String enumName, final int type)
    {
        super(STAINED_GLASS_PANE.name(), STAINED_GLASS_PANE.getId(), STAINED_GLASS_PANE.getMinecraftId(), enumName, (byte) type);
    }

    public StainedGlassPane(final int maxStack, final String typeName, final byte type)
    {
        super(STAINED_GLASS_PANE.name(), STAINED_GLASS_PANE.getId(), STAINED_GLASS_PANE.getMinecraftId(), maxStack, typeName, type);
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
    public StainedGlassPane getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public StainedGlassPane getType(final int id)
    {
        return getByID(id);
    }

    @Override
    public DyeColor getColor()
    {
        return null; // TODO: implement
    }

    @Override
    public Material getColor(final DyeColor color)
    {
        return null; // TODO: implement
    }

    /**
     * Returns one of StainedGlassPane sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of StainedGlassPane or null
     */
    public static StainedGlassPane getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of StainedGlassPane sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of StainedGlassPane or null
     */
    public static StainedGlassPane getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final StainedGlassPane element)
    {
        byID.put(element.getType(), element);
        byName.put(element.name(), element);
    }

    static
    {
        StainedGlassPane.register(STAINED_GLASS_PANE);
    }
}
