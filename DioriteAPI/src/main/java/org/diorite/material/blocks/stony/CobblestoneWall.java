package org.diorite.material.blocks.stony;

import java.util.Map;

import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.material.BlockMaterialData;
import org.diorite.utils.collections.SimpleStringHashMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

public class CobblestoneWall extends BlockMaterialData
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
    public static final float BLAST_RESISTANCE = MagicNumbers.MATERIAL__COBBLESTONE_WALL__BLAST_RESISTANCE;	
    /**
     * Hardness of block, can be changed only before server start.
     * Final copy of hardness from {@link MagicNumbers} class.
     */
    public static final float HARDNESS         = MagicNumbers.MATERIAL__COBBLESTONE_WALL__HARDNESS;

    public static final CobblestoneWall COBBLESTONE_WALL = new CobblestoneWall();

    private static final Map<String, CobblestoneWall>    byName = new SimpleStringHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<CobblestoneWall> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    @SuppressWarnings("MagicNumber")
    protected CobblestoneWall()
    {
        super("COBBLESTONE_WALL", 139, "minecraft:cobblestone_wall", "COBBLESTONE_WALL", (byte) 0x00);
    }

    public CobblestoneWall(final String enumName, final int type)
    {
        super(COBBLESTONE_WALL.name(), COBBLESTONE_WALL.getId(), COBBLESTONE_WALL.getMinecraftId(), enumName, (byte) type);
    }

    public CobblestoneWall(final int maxStack, final String typeName, final byte type)
    {
        super(COBBLESTONE_WALL.name(), COBBLESTONE_WALL.getId(), COBBLESTONE_WALL.getMinecraftId(), maxStack, typeName, type);
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
    public CobblestoneWall getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public CobblestoneWall getType(final int id)
    {
        return getByID(id);
    }

    public static CobblestoneWall getByID(final int id)
    {
        return byID.get((byte) id);
    }

    public static CobblestoneWall getByEnumName(final String name)
    {
        return byName.get(name);
    }

    public static void register(final CobblestoneWall element)
    {
        byID.put(element.getType(), element);
        byName.put(element.name(), element);
    }

    static
    {
        CobblestoneWall.register(COBBLESTONE_WALL);
    }
}
