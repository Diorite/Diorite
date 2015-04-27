package org.diorite.material.blocks.plants;

import java.util.Arrays;
import java.util.Map;

import org.diorite.BlockFace;
import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.utils.collections.SimpleStringHashMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing block "Vine" and all its subtypes.
 */
public class Vine extends Plant
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final byte  USED_DATA_VALUES = 1;
    /**
     * Blast resistance of block, can be changed only before server start.
     * Final copy of blast resistance from {@link MagicNumbers} class.
     */
    public static final float BLAST_RESISTANCE = MagicNumbers.MATERIAL__VINE__BLAST_RESISTANCE;
    /**
     * Hardness of block, can be changed only before server start.
     * Final copy of hardness from {@link MagicNumbers} class.
     */
    public static final float HARDNESS         = MagicNumbers.MATERIAL__VINE__HARDNESS;
    public static final Vine VINE                       = new Vine();
    public static final Vine VINE_SOUTH                 = new Vine(BlockFace.SOUTH);
    public static final Vine VINE_WEST                  = new Vine(BlockFace.WEST);
    public static final Vine VINE_SOUTH_WEST            = new Vine(BlockFace.SOUTH, BlockFace.WEST);//
    public static final Vine VINE_NORTH                 = new Vine(BlockFace.NORTH);
    public static final Vine VINE_NORTH_SOUTH           = new Vine(BlockFace.NORTH, BlockFace.SOUTH);
    public static final Vine VINE_NORTH_WEST            = new Vine(BlockFace.NORTH, BlockFace.WEST);
    public static final Vine VINE_NORTH_WEST_SOUTH      = new Vine(BlockFace.NORTH, BlockFace.WEST, BlockFace.SOUTH);
    public static final Vine VINE_EAST                  = new Vine(BlockFace.EAST);
    public static final Vine VINE_EAST_SOUTH            = new Vine(BlockFace.EAST, BlockFace.SOUTH);
    public static final Vine VINE_EAST_WEST             = new Vine(BlockFace.EAST, BlockFace.WEST);
    public static final Vine VINE_EAST_SOUTH_WEST       = new Vine(BlockFace.EAST, BlockFace.WEST, BlockFace.SOUTH);
    public static final Vine VINE_EAST_NORTH            = new Vine(BlockFace.EAST, BlockFace.NORTH);
    public static final Vine VINE_EAST_NORTH_SOUTH      = new Vine(BlockFace.EAST, BlockFace.NORTH, BlockFace.SOUTH);
    public static final Vine VINE_EAST_NORTH_WEST       = new Vine(BlockFace.EAST, BlockFace.NORTH, BlockFace.WEST);
    public static final Vine VINE_EAST_NORTH_WEST_SOUTH = new Vine(BlockFace.EAST, BlockFace.NORTH, BlockFace.WEST, BlockFace.SOUTH);
    private static final Map<String, Vine>    byName = new SimpleStringHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<Vine> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    /**
     * Bit flag defining if vines are attachment to south face of block.
     * If bit is set to 0, then it's not attachment to south face of block.
     */
    public static byte SOUTH_FLAG = 0x1;
    /**
     * Bit flag defining if vines are attachment to west face of block.
     * If bit is set to 0, then it's not attachment to west face of block.
     */
    public static byte WEST_FLAG  = 0x2;
    /**
     * Bit flag defining if vines are attachment to north face of block.
     * If bit is set to 0, then it's not attachment to north face of block.
     */
    public static byte NORTH_FLAG = 0x4;
    /**
     * Bit flag defining if vines are attachment to east face of block.
     * If bit is set to 0, then it's not attachment to east face of block.
     */
    public static byte EAST_FLAG  = 0x8;
    protected final BlockFace[] faces;

    @SuppressWarnings("MagicNumber")
    protected Vine()
    {
        super("VINE", 106, "minecraft:vine", "NONE", (byte) 0x00);
        this.faces = BlockFace.EMPTY;
    }

    public Vine(final BlockFace... faces)
    {
        super(VINE.name(), VINE.getId(), VINE.getMinecraftId(), Arrays.stream(faces).map(Enum::name).reduce((a, b) -> a + "_" + b).orElse("NONE"), combine(faces));
        this.faces = faces.clone();
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

    /**
     * @return copy of array of attachment faces.
     */
    public BlockFace[] getBlockFaces()
    {
        return this.faces.clone();
    }

    /**
     * Returns one of Vine sub-type based on {@link BlockFace[]}.
     *
     * @param faces array of attachment faces.
     *
     * @return sib-type of Vine
     */
    public Vine getBlockFaces(final BlockFace... faces)
    {
        return getByID(combine(faces));
    }

    @Override
    public Vine getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public Vine getType(final int id)
    {
        return getByID(id);
    }

    private static byte combine(final BlockFace... faces)
    {
        byte result = 0x0;
        for (final BlockFace face : faces)
        {
            switch (face)
            {
                case SOUTH:
                    result ^= (1 << SOUTH_FLAG);
                    break;
                case WEST:
                    result ^= (1 << WEST_FLAG);
                    break;
                case NORTH:
                    result ^= (1 << NORTH_FLAG);
                    break;
                case EAST:
                    result ^= (1 << EAST_FLAG);
                    break;
                default:
                    break;
            }
        }
        return result;
    }

    /**
     * Returns one of Vine sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of Vine or null
     */
    public static Vine getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of Vine sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of Vine or null
     */
    public static Vine getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Returns one of Vine sub-type based on {@link BlockFace[]}.
     * It will never return null;
     *
     * @param faces array of attachment faces.
     *
     * @return sub-type of Vine
     */
    public static Vine getVine(final BlockFace... faces)
    {
        return getByID(combine(faces));
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final Vine element)
    {
        byID.put(element.getType(), element);
        byName.put(element.name(), element);
    }

    static
    {
        Vine.register(VINE);
        Vine.register(VINE_SOUTH);
        Vine.register(VINE_WEST);
        Vine.register(VINE_SOUTH_WEST);
        Vine.register(VINE_NORTH);
        Vine.register(VINE_NORTH_SOUTH);
        Vine.register(VINE_NORTH_WEST);
        Vine.register(VINE_NORTH_WEST_SOUTH);
        Vine.register(VINE_EAST);
        Vine.register(VINE_EAST_SOUTH);
        Vine.register(VINE_EAST_WEST);
        Vine.register(VINE_EAST_SOUTH_WEST);
        Vine.register(VINE_EAST_NORTH);
        Vine.register(VINE_EAST_NORTH_SOUTH);
        Vine.register(VINE_EAST_NORTH_WEST);
        Vine.register(VINE_EAST_NORTH_WEST_SOUTH);
    }
}
