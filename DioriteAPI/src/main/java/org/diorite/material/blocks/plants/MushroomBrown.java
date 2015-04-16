package org.diorite.material.blocks.plants;

import java.util.Map;

import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.utils.collections.SimpleStringHashMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing block "MushroomBrown" and all its subtypes.
 */
public class MushroomBrown extends Mushroom
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final byte  USED_DATA_VALUES = 1;
    /**
     * Blast resistance of block, can be changed only before server start.
     * Final copy of blast resistance from {@link MagicNumbers} class.
     */
    public static final float BLAST_RESISTANCE = MagicNumbers.MATERIAL__BROWN_MUSHROOM__BLAST_RESISTANCE;
    /**
     * Hardness of block, can be changed only before server start.
     * Final copy of hardness from {@link MagicNumbers} class.
     */
    public static final float HARDNESS         = MagicNumbers.MATERIAL__BROWN_MUSHROOM__HARDNESS;

    public static final MushroomBrown BROWN_MUSHROOM = new MushroomBrown();

    private static final Map<String, MushroomBrown>    byName = new SimpleStringHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<MushroomBrown> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    @SuppressWarnings("MagicNumber")
    protected MushroomBrown()
    {
        super("BROWN_MUSHROOM", 39, "minecraft:brown_mushroom", "BROWN_MUSHROOM", (byte) 0x00);
    }

    public MushroomBrown(final String enumName, final int type)
    {
        super(BROWN_MUSHROOM.name(), BROWN_MUSHROOM.getId(), BROWN_MUSHROOM.getMinecraftId(), enumName, (byte) type);
    }

    public MushroomBrown(final int maxStack, final String typeName, final byte type)
    {
        super(BROWN_MUSHROOM.name(), BROWN_MUSHROOM.getId(), BROWN_MUSHROOM.getMinecraftId(), maxStack, typeName, type);
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
    public MushroomBrown getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public MushroomBrown getType(final int id)
    {
        return getByID(id);
    }

    /**
     * Returns one of MushroomBrown sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of MushroomBrown or null
     */
    public static MushroomBrown getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of MushroomBrown sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of MushroomBrown or null
     */
    public static MushroomBrown getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final MushroomBrown element)
    {
        byID.put(element.getType(), element);
        byName.put(element.name(), element);
    }

    static
    {
        MushroomBrown.register(BROWN_MUSHROOM);
    }
}
