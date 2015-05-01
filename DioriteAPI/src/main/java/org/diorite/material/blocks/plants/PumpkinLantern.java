package org.diorite.material.blocks.plants;

import java.util.Map;

import org.diorite.BlockFace;
import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.utils.collections.SimpleStringHashMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing block "PumpkinLantern" and all its subtypes.
 */
public class PumpkinLantern extends AbstractPumpkin
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final byte  USED_DATA_VALUES = 5;
    /**
     * Blast resistance of block, can be changed only before server start.
     * Final copy of blast resistance from {@link MagicNumbers} class.
     */
    public static final float BLAST_RESISTANCE = MagicNumbers.MATERIAL__PUMPKIN_LANTERN__BLAST_RESISTANCE;
    /**
     * Hardness of block, can be changed only before server start.
     * Final copy of hardness from {@link MagicNumbers} class.
     */
    public static final float HARDNESS         = MagicNumbers.MATERIAL__PUMPKIN_LANTERN__HARDNESS;

    public static final PumpkinLantern PUMPKIN_LANTERN_SOUTH = new PumpkinLantern();
    public static final PumpkinLantern PUMPKIN_LANTERN_WEST  = new PumpkinLantern(BlockFace.WEST);
    public static final PumpkinLantern PUMPKIN_LANTERN_NORTH = new PumpkinLantern(BlockFace.NORTH);
    public static final PumpkinLantern PUMPKIN_LANTERN_EAST  = new PumpkinLantern(BlockFace.EAST);
    public static final PumpkinLantern PUMPKIN_LANTERN_SELF  = new PumpkinLantern(BlockFace.SELF);

    private static final Map<String, PumpkinLantern>    byName = new SimpleStringHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<PumpkinLantern> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    @SuppressWarnings("MagicNumber")
    protected PumpkinLantern()
    {
        super("PUMPKIN_LANTERN", 91, "minecraft:lit_pumpkin", BlockFace.SOUTH);
    }

    public PumpkinLantern(final BlockFace face)
    {
        super(PUMPKIN_LANTERN_SOUTH.name(), PUMPKIN_LANTERN_SOUTH.getId(), PUMPKIN_LANTERN_SOUTH.getMinecraftId(), face);
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
    public PumpkinLantern getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public PumpkinLantern getType(final int id)
    {
        return getByID(id);
    }

    @Override
    public boolean isPowered()
    {
        return true;
    }

    @Override
    public PumpkinLantern getBlockFacing(final BlockFace face)
    {
        return getByID(combine(face));
    }

    /**
     * Returns one of PumpkinLantern sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of PumpkinLantern or null
     */
    public static PumpkinLantern getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of PumpkinLantern sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of PumpkinLantern or null
     */
    public static PumpkinLantern getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Returns one of PumpkinLantern sub-type based on {@link BlockFace}.
     * It will never return null;
     *
     * @param face facing of PumpkinLantern
     *
     * @return sub-type of PumpkinLantern
     */
    public static PumpkinLantern getPumpkinLantern(final BlockFace face)
    {
        return getByID(combine(face));
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final PumpkinLantern element)
    {
        byID.put(element.getType(), element);
        byName.put(element.name(), element);
    }

    static
    {
        PumpkinLantern.register(PUMPKIN_LANTERN_SOUTH);
        PumpkinLantern.register(PUMPKIN_LANTERN_WEST);
        PumpkinLantern.register(PUMPKIN_LANTERN_NORTH);
        PumpkinLantern.register(PUMPKIN_LANTERN_EAST);
        PumpkinLantern.register(PUMPKIN_LANTERN_SELF);
    }
}
