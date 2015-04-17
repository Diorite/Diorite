package org.diorite.material.blocks.cold;

import java.util.Map;

import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.material.BlockMaterialData;
import org.diorite.utils.collections.SimpleStringHashMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing block "SnowLayer" and all its subtypes.
 */
public class SnowLayer extends BlockMaterialData
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final byte  USED_DATA_VALUES = 8;
    /**
     * Blast resistance of block, can be changed only before server start.
     * Final copy of blast resistance from {@link MagicNumbers} class.
     */
    public static final float BLAST_RESISTANCE = MagicNumbers.MATERIAL__SNOW_LAYER__BLAST_RESISTANCE;
    /**
     * Hardness of block, can be changed only before server start.
     * Final copy of hardness from {@link MagicNumbers} class.
     */
    public static final float HARDNESS         = MagicNumbers.MATERIAL__SNOW_LAYER__HARDNESS;

    public static final SnowLayer SNOW_LAYER_1 = new SnowLayer();
    public static final SnowLayer SNOW_LAYER_2 = new SnowLayer(0x01);
    public static final SnowLayer SNOW_LAYER_3 = new SnowLayer(0x02);
    public static final SnowLayer SNOW_LAYER_4 = new SnowLayer(0x03);
    public static final SnowLayer SNOW_LAYER_5 = new SnowLayer(0x04);
    public static final SnowLayer SNOW_LAYER_6 = new SnowLayer(0x05);
    public static final SnowLayer SNOW_LAYER_7 = new SnowLayer(0x06);
    public static final SnowLayer SNOW_LAYER_8 = new SnowLayer(0x07);

    private static final Map<String, SnowLayer>    byName = new SimpleStringHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<SnowLayer> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    @SuppressWarnings("MagicNumber")
    protected SnowLayer()
    {
        super("SNOW_LAYER_1", 78, "minecraft:snow_layer", "1", (byte) 0x00);
    }

    public SnowLayer(final int type)
    {
        super(SNOW_LAYER_1.name(), SNOW_LAYER_1.getId(), SNOW_LAYER_1.getMinecraftId(), Integer.toString(type + 1), (byte) type);
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

    /**
     * Returns one of SnowLayer sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of SnowLayer or null
     */
    public static SnowLayer getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of SnowLayer sub-type based on how many layers it should have.
     * From 1 to 8.
     *
     * @param id sub-type id
     *
     * @return sub-type of SnowLayer or null
     */
    public static SnowLayer getSnowLayer(final int layers)
    {
        return getByID(layers + 1);
    }

    /**
     * Returns one of SnowLayer sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of SnowLayer or null
     */
    public static SnowLayer getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final SnowLayer element)
    {
        byID.put(element.getType(), element);
        byName.put(element.name(), element);
    }

    static
    {
        SnowLayer.register(SNOW_LAYER_1);
        SnowLayer.register(SNOW_LAYER_2);
        SnowLayer.register(SNOW_LAYER_3);
        SnowLayer.register(SNOW_LAYER_4);
        SnowLayer.register(SNOW_LAYER_5);
        SnowLayer.register(SNOW_LAYER_6);
        SnowLayer.register(SNOW_LAYER_7);
        SnowLayer.register(SNOW_LAYER_8);
    }
}
