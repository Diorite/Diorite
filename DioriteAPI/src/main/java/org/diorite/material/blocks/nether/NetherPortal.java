package org.diorite.material.blocks.nether;

import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.material.blocks.Portal;
import org.diorite.material.blocks.Rotatable;
import org.diorite.material.blocks.RotateAxis;
import org.diorite.utils.collections.SimpleStringHashMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing block "NetherPortal" and all its subtypes.
 */
public class NetherPortal extends Portal implements Rotatable
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final byte  USED_DATA_VALUES = 2;
    /**
     * Blast resistance of block, can be changed only before server start.
     * Final copy of blast resistance from {@link MagicNumbers} class.
     */
    public static final float BLAST_RESISTANCE = MagicNumbers.MATERIAL__NETHER_PORTAL__BLAST_RESISTANCE;
    /**
     * Hardness of block, can be changed only before server start.
     * Final copy of hardness from {@link MagicNumbers} class.
     */
    public static final float HARDNESS         = MagicNumbers.MATERIAL__NETHER_PORTAL__HARDNESS;

    public static final NetherPortal NETHER_PORTAL_EAST_WEST   = new NetherPortal();
    public static final NetherPortal NETHER_PORTAL_NORTH_SOUTH = new NetherPortal("NORTH_SOUTH", RotateAxis.NORTH_SOUTH);

    private static final Map<String, NetherPortal>    byName = new SimpleStringHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<NetherPortal> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    protected final RotateAxis rotateAxis;

    @SuppressWarnings("MagicNumber")
    protected NetherPortal()
    {
        super("NETHER_PORTAL", 90, "minecraft:portal", "EAST_WEST", (byte) 0x01);
        this.rotateAxis = RotateAxis.EAST_WEST;
    }

    public NetherPortal(final String enumName, final RotateAxis rotateAxis)
    {
        super(NETHER_PORTAL_EAST_WEST.name(), NETHER_PORTAL_EAST_WEST.getId(), NETHER_PORTAL_EAST_WEST.getMinecraftId(), enumName, combine(rotateAxis));
        this.rotateAxis = rotateAxis;
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
    public NetherPortal getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public NetherPortal getType(final int id)
    {
        return getByID(id);
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("rotateAxis", this.rotateAxis).toString();
    }

    @Override
    public RotateAxis getRotateAxis()
    {
        return RotateAxis.EAST_WEST;
    }

    @Override
    public NetherPortal getRotated(final RotateAxis axis)
    {
        return getByID(combine(axis));
    }

    private static byte combine(final RotateAxis rotateAxis)
    {
        switch (rotateAxis)
        {
            case NORTH_SOUTH:
                return 0x02;
            default:
                return 0x01;
        }
    }

    /**
     * Returns one of NetherPortal sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of NetherPortal or null
     */
    public static NetherPortal getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of NetherPortal sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of NetherPortal or null
     */
    public static NetherPortal getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Returns one of NetherPortal sub-type based on {@link RotateAxis}.
     * It will never return null;
     *
     * @param axis rotate axis of block, unsupported axis will cause using axis from default type.
     *
     * @return sub-type of NetherPortal
     */
    public static NetherPortal getNetherPortal(final RotateAxis axis)
    {
        return getByID(combine(axis));
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final NetherPortal element)
    {
        byID.put(element.getType(), element);
        byName.put(element.name(), element);
    }

    static
    {
        NetherPortal.register(NETHER_PORTAL_EAST_WEST);
        NetherPortal.register(NETHER_PORTAL_NORTH_SOUTH);
    }
}
