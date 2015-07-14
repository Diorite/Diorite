package org.diorite.material.blocks.plants;

import java.util.Map;

import org.diorite.BlockFace;
import org.diorite.utils.collections.maps.CaseInsensitiveMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing block "Pumpkin" and all its subtypes.
 */
public class PumpkinMat extends AbstractPumpkinMat
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final byte  USED_DATA_VALUES = 5;

    public static final PumpkinMat PUMPKIN_SOUTH = new PumpkinMat();
    public static final PumpkinMat PUMPKIN_WEST  = new PumpkinMat(BlockFace.WEST);
    public static final PumpkinMat PUMPKIN_NORTH = new PumpkinMat(BlockFace.NORTH);
    public static final PumpkinMat PUMPKIN_EAST  = new PumpkinMat(BlockFace.EAST);
    public static final PumpkinMat PUMPKIN_SELF  = new PumpkinMat(BlockFace.SELF);

    private static final Map<String, PumpkinMat>    byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<PumpkinMat> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    @SuppressWarnings("MagicNumber")
    protected PumpkinMat()
    {
        super("PUMPKIN", 86, "minecraft:pumpkin", BlockFace.SOUTH, 1, 5);
    }

    protected PumpkinMat(final BlockFace face)
    {
        super(PUMPKIN_SOUTH.name(), PUMPKIN_SOUTH.ordinal(), PUMPKIN_SOUTH.getMinecraftId(), face, PUMPKIN_SOUTH.getHardness(), PUMPKIN_SOUTH.getBlastResistance());
    }

    protected PumpkinMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type, final BlockFace face, final float hardness, final float blastResistance)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, face, hardness, blastResistance);
    }

    @Override
    public PumpkinMat getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public PumpkinMat getType(final int id)
    {
        return getByID(id);
    }

    @Override
    public PumpkinMat getBlockFacing(final BlockFace face)
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
    public static PumpkinMat getByID(final int id)
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
    public static PumpkinMat getByEnumName(final String name)
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
    public static PumpkinMat getPumpkin(final BlockFace face)
    {
        return getByID(combine(face));
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final PumpkinMat element)
    {
        byID.put((byte) element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    @Override
    public PumpkinMat[] types()
    {
        return PumpkinMat.pumpkinTypes();
    }

    /**
     * @return array that contains all sub-types of this block.
     */
    public static PumpkinMat[] pumpkinTypes()
    {
        return byID.values(new PumpkinMat[byID.size()]);
    }

    static
    {
        PumpkinMat.register(PUMPKIN_SOUTH);
        PumpkinMat.register(PUMPKIN_WEST);
        PumpkinMat.register(PUMPKIN_NORTH);
        PumpkinMat.register(PUMPKIN_EAST);
        PumpkinMat.register(PUMPKIN_SELF);
    }
}
