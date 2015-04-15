package org.diorite.material.blocks.others;

import java.util.Map;

import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.material.BlockMaterialData;
import org.diorite.utils.collections.SimpleStringHashMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing block "IronBars" and all its subtypes.
 */
public class IronBars extends BlockMaterialData
{	
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final byte  USED_DATA_VALUES = 1;	
    /**
     * Blast resistance of block, can be changed only before server start.
     * Final copy of blast resistance from {@link MagicNumbers} class.
     */
    public static final float BLAST_RESISTANCE = MagicNumbers.MATERIAL__IRON_BARS__BLAST_RESISTANCE;	
    /**
     * Hardness of block, can be changed only before server start.
     * Final copy of hardness from {@link MagicNumbers} class.
     */
    public static final float HARDNESS         = MagicNumbers.MATERIAL__IRON_BARS__HARDNESS;

    public static final IronBars IRON_BARS = new IronBars();

    private static final Map<String, IronBars>    byName = new SimpleStringHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<IronBars> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    @SuppressWarnings("MagicNumber")
    protected IronBars()
    {
        super("IRON_BARS", 101, "minecraft:iron_bars", "IRON_BARS", (byte) 0x00);
    }

    public IronBars(final String enumName, final int type)
    {
        super(IRON_BARS.name(), IRON_BARS.getId(), IRON_BARS.getMinecraftId(), enumName, (byte) type);
    }

    public IronBars(final int maxStack, final String typeName, final byte type)
    {
        super(IRON_BARS.name(), IRON_BARS.getId(), IRON_BARS.getMinecraftId(), maxStack, typeName, type);
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
    public IronBars getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public IronBars getType(final int id)
    {
        return getByID(id);
    }

    /**
     * Returns one of IronBars sub-type based on sub-id, may return null
     * @param id sub-type id
     * @return sub-type of IronBars or null
     */
    public static IronBars getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of IronBars sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     * @param name name of sub-type
     * @return sub-type of IronBars or null
     */
    public static IronBars getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     * @param element sub-type to register
     */
    public static void register(final IronBars element)
    {
        byID.put(element.getType(), element);
        byName.put(element.name(), element);
    }

    static
    {
        IronBars.register(IRON_BARS);
    }
}
