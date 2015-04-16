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
 * Class representing block "Carpet" and all its subtypes.
 */
public class Carpet extends BlockMaterialData implements Colorable
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
    public static final float BLAST_RESISTANCE = MagicNumbers.MATERIAL__CARPET__BLAST_RESISTANCE;
    /**
     * Hardness of block, can be changed only before server start.
     * Final copy of hardness from {@link MagicNumbers} class.
     */
    public static final float HARDNESS         = MagicNumbers.MATERIAL__CARPET__HARDNESS;

    public static final Carpet CARPET = new Carpet();

    private static final Map<String, Carpet>    byName = new SimpleStringHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<Carpet> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    @SuppressWarnings("MagicNumber")
    protected Carpet()
    {
        super("CARPET", 171, "minecraft:carpet", "CARPET", (byte) 0x00);
    }

    public Carpet(final String enumName, final int type)
    {
        super(CARPET.name(), CARPET.getId(), CARPET.getMinecraftId(), enumName, (byte) type);
    }

    public Carpet(final int maxStack, final String typeName, final byte type)
    {
        super(CARPET.name(), CARPET.getId(), CARPET.getMinecraftId(), maxStack, typeName, type);
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
    public Carpet getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public Carpet getType(final int id)
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
     * Returns one of Carpet sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of Carpet or null
     */
    public static Carpet getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of Carpet sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of Carpet or null
     */
    public static Carpet getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final Carpet element)
    {
        byID.put(element.getType(), element);
        byName.put(element.name(), element);
    }

    static
    {
        Carpet.register(CARPET);
    }
}
