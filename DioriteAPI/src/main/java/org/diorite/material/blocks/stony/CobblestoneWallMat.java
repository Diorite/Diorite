package org.diorite.material.blocks.stony;

import java.util.Map;

import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.material.BlockMaterialData;
import org.diorite.utils.collections.maps.CaseInsensitiveMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing block "CobblestoneWall" and all its subtypes.
 */
public class CobblestoneWallMat extends BlockMaterialData
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final byte  USED_DATA_VALUES = 2;
    /**
     * Blast resistance of block, can be changed only before server start.
     * Final copy of blast resistance from {@link MagicNumbers} class.
     */
    public static final float BLAST_RESISTANCE = MagicNumbers.MATERIAL__COBBLESTONE_WALL__BLAST_RESISTANCE;
    /**
     * Hardness of block, can be changed only before server start.
     * Final copy of hardness from {@link MagicNumbers} class.
     */
    public static final float HARDNESS         = MagicNumbers.MATERIAL__COBBLESTONE_WALL__HARDNESS;

    public static final CobblestoneWallMat COBBLESTONE_WALL       = new CobblestoneWallMat();
    public static final CobblestoneWallMat COBBLESTONE_WALL_MOSSY = new CobblestoneWallMat("MOSSY", 0x1);

    private static final Map<String, CobblestoneWallMat>    byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<CobblestoneWallMat> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    @SuppressWarnings("MagicNumber")
    protected CobblestoneWallMat()
    {
        super("COBBLESTONE_WALL", 139, "minecraft:cobblestone_wall", "COBBLESTONE_WALL", (byte) 0x00);
    }

    protected CobblestoneWallMat(final String enumName, final int type)
    {
        super(COBBLESTONE_WALL.name(), COBBLESTONE_WALL.ordinal(), COBBLESTONE_WALL.getMinecraftId(), enumName, (byte) type);
    }

    protected CobblestoneWallMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type);
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
    public CobblestoneWallMat getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public CobblestoneWallMat getType(final int id)
    {
        return getByID(id);
    }

    /**
     * Returns one of CobblestoneWall sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of CobblestoneWall or null
     */
    public static CobblestoneWallMat getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of CobblestoneWall sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of CobblestoneWall or null
     */
    public static CobblestoneWallMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final CobblestoneWallMat element)
    {
        byID.put((byte) element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    @Override
    public CobblestoneWallMat[] types()
    {
        return CobblestoneWallMat.cobblestoneWallTypes();
    }

    /**
     * @return array that contains all sub-types of this block.
     */
    public static CobblestoneWallMat[] cobblestoneWallTypes()
    {
        return byID.values(new CobblestoneWallMat[byID.size()]);
    }

    static
    {
        CobblestoneWallMat.register(COBBLESTONE_WALL);
        CobblestoneWallMat.register(COBBLESTONE_WALL_MOSSY);
    }
}
