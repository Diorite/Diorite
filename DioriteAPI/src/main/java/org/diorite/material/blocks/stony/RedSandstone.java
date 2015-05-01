package org.diorite.material.blocks.stony;

import java.util.Map;

import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.material.BlockMaterialData;
import org.diorite.utils.collections.SimpleStringHashMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing block "RedSandstone" and all its subtypes.
 */
public class RedSandstone extends BlockMaterialData
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final byte  USED_DATA_VALUES = 3;
    /**
     * Blast resistance of block, can be changed only before server start.
     * Final copy of blast resistance from {@link MagicNumbers} class.
     */
    public static final float BLAST_RESISTANCE = MagicNumbers.MATERIAL__RED_SANDSTONE__BLAST_RESISTANCE;
    /**
     * Hardness of block, can be changed only before server start.
     * Final copy of hardness from {@link MagicNumbers} class.
     */
    public static final float HARDNESS         = MagicNumbers.MATERIAL__RED_SANDSTONE__HARDNESS;

    public static final RedSandstone RED_SANDSTONE          = new RedSandstone();
    public static final RedSandstone RED_SANDSTONE_CHISELED = new RedSandstone("CHISELED", 0x1);
    public static final RedSandstone RED_SANDSTONE_SMOOTH   = new RedSandstone("SMOOTH", 0x2);

    private static final Map<String, RedSandstone>    byName = new SimpleStringHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<RedSandstone> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    @SuppressWarnings("MagicNumber")
    protected RedSandstone()
    {
        super("RED_SANDSTONE", 179, "minecraft:red_sandstone", "RED_SANDSTONE", (byte) 0x00);
    }

    public RedSandstone(final String enumName, final int type)
    {
        super(RED_SANDSTONE.name(), RED_SANDSTONE.getId(), RED_SANDSTONE.getMinecraftId(), enumName, (byte) type);
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
    public RedSandstone getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public RedSandstone getType(final int id)
    {
        return getByID(id);
    }

    /**
     * Returns one of RedSandstone sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of RedSandstone or null
     */
    public static RedSandstone getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of RedSandstone sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of RedSandstone or null
     */
    public static RedSandstone getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final RedSandstone element)
    {
        byID.put(element.getType(), element);
        byName.put(element.name(), element);
    }

    static
    {
        RedSandstone.register(RED_SANDSTONE);
        RedSandstone.register(RED_SANDSTONE_CHISELED);
        RedSandstone.register(RED_SANDSTONE_SMOOTH);
    }
}
