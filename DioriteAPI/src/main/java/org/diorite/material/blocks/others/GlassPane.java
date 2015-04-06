package org.diorite.material.blocks.others;

import java.util.Map;

import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.material.BlockMaterialData;
import org.diorite.utils.collections.SimpleStringHashMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

public class GlassPane extends BlockMaterialData
{
    public static final byte  USED_DATA_VALUES = 1;
    public static final float BLAST_RESISTANCE = MagicNumbers.MATERIAL__GLASS_PANE__BLAST_RESISTANCE;
    public static final float HARDNESS         = MagicNumbers.MATERIAL__GLASS_PANE__HARDNESS;

    public static final GlassPane GLASS_PANE = new GlassPane();

    private static final Map<String, GlassPane>    byName = new SimpleStringHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<GlassPane> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    @SuppressWarnings("MagicNumber")
    protected GlassPane()
    {
        super("GLASS_PANE", 102, "minecraft:glass_pane", "GLASS_PANE", (byte) 0x00);
    }

    public GlassPane(final String enumName, final int type)
    {
        super(GLASS_PANE.name(), GLASS_PANE.getId(), GLASS_PANE.getMinecraftId(), enumName, (byte) type);
    }

    public GlassPane(final int maxStack, final String typeName, final byte type)
    {
        super(GLASS_PANE.name(), GLASS_PANE.getId(), GLASS_PANE.getMinecraftId(), maxStack, typeName, type);
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
    public GlassPane getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public GlassPane getType(final int id)
    {
        return getByID(id);
    }

    public static GlassPane getByID(final int id)
    {
        return byID.get((byte) id);
    }

    public static GlassPane getByEnumName(final String name)
    {
        return byName.get(name);
    }

    public static void register(final GlassPane element)
    {
        byID.put(element.getType(), element);
        byName.put(element.name(), element);
    }

    static
    {
        GlassPane.register(GLASS_PANE);
    }
}