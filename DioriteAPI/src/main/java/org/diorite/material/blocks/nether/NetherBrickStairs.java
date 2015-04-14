package org.diorite.material.blocks.nether;

import java.util.Map;

import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.material.BlockMaterialData;
import org.diorite.material.blocks.Stairs;
import org.diorite.utils.collections.SimpleStringHashMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing block "NetherBrickStairs" and all its subtypes.
 */
public class NetherBrickStairs extends BlockMaterialData implements Stairs
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
    public static final float BLAST_RESISTANCE = MagicNumbers.MATERIAL__NETHER_BRICK_STAIRS__BLAST_RESISTANCE;	
    /**
     * Hardness of block, can be changed only before server start.
     * Final copy of hardness from {@link MagicNumbers} class.
     */
    public static final float HARDNESS         = MagicNumbers.MATERIAL__NETHER_BRICK_STAIRS__HARDNESS;

    public static final NetherBrickStairs NETHER_BRICK_STAIRS = new NetherBrickStairs();

    private static final Map<String, NetherBrickStairs>    byName = new SimpleStringHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<NetherBrickStairs> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    @SuppressWarnings("MagicNumber")
    protected NetherBrickStairs()
    {
        super("NETHER_BRICK_STAIRS", 114, "minecraft:nether_brick_stairs", "NETHER_BRICK_STAIRS", (byte) 0x00);
    }

    public NetherBrickStairs(final String enumName, final int type)
    {
        super(NETHER_BRICK_STAIRS.name(), NETHER_BRICK_STAIRS.getId(), NETHER_BRICK_STAIRS.getMinecraftId(), enumName, (byte) type);
    }

    public NetherBrickStairs(final int maxStack, final String typeName, final byte type)
    {
        super(NETHER_BRICK_STAIRS.name(), NETHER_BRICK_STAIRS.getId(), NETHER_BRICK_STAIRS.getMinecraftId(), maxStack, typeName, type);
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
    public NetherBrickStairs getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public NetherBrickStairs getType(final int id)
    {
        return getByID(id);
    }

    public static NetherBrickStairs getByID(final int id)
    {
        return byID.get((byte) id);
    }

    public static NetherBrickStairs getByEnumName(final String name)
    {
        return byName.get(name);
    }

    public static void register(final NetherBrickStairs element)
    {
        byID.put(element.getType(), element);
        byName.put(element.name(), element);
    }

    static
    {
        NetherBrickStairs.register(NETHER_BRICK_STAIRS);
    }
}
