package org.diorite.material.blocks.nether;

import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.BlockFace;
import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.material.BlockMaterialData;
import org.diorite.material.blocks.PortalMat;
import org.diorite.material.blocks.RotatableMat;
import org.diorite.material.blocks.RotateAxisMat;
import org.diorite.utils.collections.SimpleStringHashMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing block "NetherPortal" and all its subtypes.
 */
public class NetherPortalMat extends BlockMaterialData implements RotatableMat, PortalMat
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

    public static final NetherPortalMat NETHER_PORTAL_EAST_WEST   = new NetherPortalMat();
    public static final NetherPortalMat NETHER_PORTAL_NORTH_SOUTH = new NetherPortalMat("NORTH_SOUTH", RotateAxisMat.NORTH_SOUTH);

    private static final Map<String, NetherPortalMat>    byName = new SimpleStringHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<NetherPortalMat> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    protected final RotateAxisMat rotateAxis;

    @SuppressWarnings("MagicNumber")
    protected NetherPortalMat()
    {
        super("NETHER_PORTAL", 90, "minecraft:portal", "EAST_WEST", (byte) 0x01);
        this.rotateAxis = RotateAxisMat.EAST_WEST;
    }

    protected NetherPortalMat(final String enumName, final RotateAxisMat rotateAxis)
    {
        super(NETHER_PORTAL_EAST_WEST.name(), NETHER_PORTAL_EAST_WEST.getId(), NETHER_PORTAL_EAST_WEST.getMinecraftId(), enumName, combine(rotateAxis));
        this.rotateAxis = rotateAxis;
    }

    protected NetherPortalMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type, final RotateAxisMat rotateAxis)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type);
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
    public NetherPortalMat getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public NetherPortalMat getType(final int id)
    {
        return getByID(id);
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("rotateAxis", this.rotateAxis).toString();
    }

    @Override
    public RotateAxisMat getRotateAxis()
    {
        return this.rotateAxis;
    }

    @Override
    public NetherPortalMat getRotated(final RotateAxisMat axis)
    {
        return getByID(combine(axis));
    }

    @Override
    public NetherPortalMat getBlockFacing(final BlockFace face)
    {
        switch (face)
        {
            case NORTH:
            case SOUTH:
                return getByID(combine(RotateAxisMat.NORTH_SOUTH));
            case EAST:
            case WEST:
                return getByID(combine(RotateAxisMat.EAST_WEST));
            case UP:
            case DOWN:
                return getByID(combine(RotateAxisMat.UP_DOWN));
            case SELF:
                return getByID(combine(RotateAxisMat.NONE));
            default:
                return getByID(combine(RotateAxisMat.EAST_WEST));
        }
    }

    private static byte combine(final RotateAxisMat rotateAxis)
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
    public static NetherPortalMat getByID(final int id)
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
    public static NetherPortalMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Returns one of NetherPortal sub-type based on {@link RotateAxisMat}.
     * It will never return null;
     *
     * @param axis rotate axis of block, unsupported axis will cause using axis from default type.
     *
     * @return sub-type of NetherPortal
     */
    public static NetherPortalMat getNetherPortal(final RotateAxisMat axis)
    {
        return getByID(combine(axis));
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final NetherPortalMat element)
    {
        byID.put((byte) element.getType(), element);
        byName.put(element.name(), element);
    }

    static
    {
        NetherPortalMat.register(NETHER_PORTAL_EAST_WEST);
        NetherPortalMat.register(NETHER_PORTAL_NORTH_SOUTH);
    }
}
