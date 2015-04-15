package org.diorite.material.blocks.stony;

import java.util.Map;

import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.material.BlockMaterialData;
import org.diorite.material.blocks.Stairs;
import org.diorite.utils.collections.SimpleStringHashMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing block "CobblestoneStairs" and all its subtypes.
 */
public class CobblestoneStairs extends BlockMaterialData implements Stairs
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
    public static final float BLAST_RESISTANCE = MagicNumbers.MATERIAL__COBBLESTONE_STAIRS__BLAST_RESISTANCE;	
    /**
     * Hardness of block, can be changed only before server start.
     * Final copy of hardness from {@link MagicNumbers} class.
     */
    public static final float HARDNESS         = MagicNumbers.MATERIAL__COBBLESTONE_STAIRS__HARDNESS;

    public static final CobblestoneStairs COBBLESTONE_STAIRS = new CobblestoneStairs();

    private static final Map<String, CobblestoneStairs>    byName = new SimpleStringHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<CobblestoneStairs> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    @SuppressWarnings("MagicNumber")
    protected CobblestoneStairs()
    {
        super("COBBLESTONE_STAIRS", 67, "minecraft:stone_stairs", "COBBLESTONE_STAIRS", (byte) 0x00);
    }

    public CobblestoneStairs(final String enumName, final int type)
    {
        super(COBBLESTONE_STAIRS.name(), COBBLESTONE_STAIRS.getId(), COBBLESTONE_STAIRS.getMinecraftId(), enumName, (byte) type);
    }

    public CobblestoneStairs(final int maxStack, final String typeName, final byte type)
    {
        super(COBBLESTONE_STAIRS.name(), COBBLESTONE_STAIRS.getId(), COBBLESTONE_STAIRS.getMinecraftId(), maxStack, typeName, type);
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
    public CobblestoneStairs getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public CobblestoneStairs getType(final int id)
    {
        return getByID(id);
    }

    /**
     * Returns one of CobblestoneStairs sub-type based on sub-id, may return null
     * @param id sub-type id
     * @return sub-type of CobblestoneStairs or null
     */
    public static CobblestoneStairs getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of CobblestoneStairs sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     * @param name name of sub-type
     * @return sub-type of CobblestoneStairs or null
     */
    public static CobblestoneStairs getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     * @param element sub-type to register
     */
    public static void register(final CobblestoneStairs element)
    {
        byID.put(element.getType(), element);
        byName.put(element.name(), element);
    }

    static
    {
        CobblestoneStairs.register(COBBLESTONE_STAIRS);
    }
}
