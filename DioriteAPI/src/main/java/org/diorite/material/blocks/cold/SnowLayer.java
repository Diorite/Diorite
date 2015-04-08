package org.diorite.material.blocks.cold;

import java.util.Map;

import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.material.BlockMaterialData;
import org.diorite.utils.collections.SimpleStringHashMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

public class SnowLayer extends BlockMaterialData
{
    // TODO: auto-generated class, implement other types (sub-ids).
    public static final byte  USED_DATA_VALUES = 1;
    public static final float BLAST_RESISTANCE = MagicNumbers.MATERIAL__SNOW_LAYER__BLAST_RESISTANCE;
    public static final float HARDNESS         = MagicNumbers.MATERIAL__SNOW_LAYER__HARDNESS;

    public static final SnowLayer SNOW_LAYER = new SnowLayer();

    private static final Map<String, SnowLayer>    byName = new SimpleStringHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<SnowLayer> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    @SuppressWarnings("MagicNumber")
    protected SnowLayer()
    {
        super("SNOW_LAYER", 78, "minecraft:snow_layer", "SNOW_LAYER", (byte) 0x00);
    }

    public SnowLayer(final String enumName, final int type)
    {
        super(SNOW_LAYER.name(), SNOW_LAYER.getId(), SNOW_LAYER.getMinecraftId(), enumName, (byte) type);
    }

    public SnowLayer(final int maxStack, final String typeName, final byte type)
    {
        super(SNOW_LAYER.name(), SNOW_LAYER.getId(), SNOW_LAYER.getMinecraftId(), maxStack, typeName, type);
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
    public SnowLayer getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public SnowLayer getType(final int id)
    {
        return getByID(id);
    }

    public static SnowLayer getByID(final int id)
    {
        return byID.get((byte) id);
    }

    public static SnowLayer getByEnumName(final String name)
    {
        return byName.get(name);
    }

    public static void register(final SnowLayer element)
    {
        byID.put(element.getType(), element);
        byName.put(element.name(), element);
    }

    static
    {
        SnowLayer.register(SNOW_LAYER);
    }
}