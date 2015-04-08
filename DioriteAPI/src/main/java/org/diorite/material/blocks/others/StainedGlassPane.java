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

public class StainedGlassPane extends BlockMaterialData implements Colorable
{
    // TODO: auto-generated class, implement other types (sub-ids).
    public static final byte  USED_DATA_VALUES = 1;
    public static final float BLAST_RESISTANCE = MagicNumbers.MATERIAL__STAINED_GLASS_PANE__BLAST_RESISTANCE;
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

    public static StainedGlassPane getByID(final int id)
    {
        return byID.get((byte) id);
    }

    public static StainedGlassPane getByEnumName(final String name)
    {
        return byName.get(name);
    }

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