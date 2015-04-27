package org.diorite.material.blocks.plants;

import java.util.Map;

import org.diorite.BlockFace;
import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.utils.collections.SimpleStringHashMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing block "Pumpkin" and all its subtypes.
 */
public class Pumpkin extends AbstractPumpkin
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final byte  USED_DATA_VALUES = 5;
    /**
     * Blast resistance of block, can be changed only before server start.
     * Final copy of blast resistance from {@link MagicNumbers} class.
     */
    public static final float BLAST_RESISTANCE = MagicNumbers.MATERIAL__PUMPKIN__BLAST_RESISTANCE;
    /**
     * Hardness of block, can be changed only before server start.
     * Final copy of hardness from {@link MagicNumbers} class.
     */
    public static final float HARDNESS         = MagicNumbers.MATERIAL__PUMPKIN__HARDNESS;

    public static final Pumpkin PUMPKIN_SOUTH = new Pumpkin();
    public static final Pumpkin PUMPKIN_WEST  = new Pumpkin(BlockFace.WEST);
    public static final Pumpkin PUMPKIN_NORTH = new Pumpkin(BlockFace.NORTH);
    public static final Pumpkin PUMPKIN_EAST  = new Pumpkin(BlockFace.EAST);
    public static final Pumpkin PUMPKIN_SELF  = new Pumpkin(BlockFace.SELF);

    private static final Map<String, Pumpkin>    byName = new SimpleStringHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<Pumpkin> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    @SuppressWarnings("MagicNumber")
    protected Pumpkin()
    {
        super("PUMPKIN", 86, "minecraft:pumpkin", BlockFace.SOUTH);
    }

    public Pumpkin(final BlockFace face)
    {
        super(PUMPKIN_SOUTH.name(), PUMPKIN_SOUTH.getId(), PUMPKIN_SOUTH.getMinecraftId(), face);
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
    public Pumpkin getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public Pumpkin getType(final int id)
    {
        return getByID(id);
    }

    @Override
    public boolean isPowered()
    {
        return false;
    }

    @Override
    public Pumpkin getBlockFacing(final BlockFace face)
    {
        return getByID(combine(face));
    }

    /**
     * Returns one of Pumpkin sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of Pumpkin or null
     */
    public static Pumpkin getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of Pumpkin sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of Pumpkin or null
     */
    public static Pumpkin getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Returns one of Pumpkin sub-type based on {@link BlockFace}.
     * It will never return null;
     *
     * @param face facing of Pumpkin
     *
     * @return sub-type of Pumpkin
     */
    public static Pumpkin getPumpkin(final BlockFace face)
    {
        return getByID(combine(face));
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final Pumpkin element)
    {
        byID.put(element.getType(), element);
        byName.put(element.name(), element);
    }

    static
    {
        Pumpkin.register(PUMPKIN_SOUTH);
        Pumpkin.register(PUMPKIN_WEST);
        Pumpkin.register(PUMPKIN_NORTH);
        Pumpkin.register(PUMPKIN_EAST);
        Pumpkin.register(PUMPKIN_SELF);
    }
}
