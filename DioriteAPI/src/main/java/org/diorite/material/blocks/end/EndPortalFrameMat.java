package org.diorite.material.blocks.end;

import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.BlockFace;
import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.material.BlockMaterialData;
import org.diorite.material.blocks.DirectionalMat;
import org.diorite.utils.collections.maps.CaseInsensitiveMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing block "EndPortalFrame" and all its subtypes.
 */
public class EndPortalFrameMat extends BlockMaterialData implements DirectionalMat
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final byte  USED_DATA_VALUES = 8;
    /**
     * Flag that determine if this is activated sub-type
     */
    public static final byte  ACTIVATE_FLAG    = 0x4;
    /**
     * Blast resistance of block, can be changed only before server start.
     * Final copy of blast resistance from {@link MagicNumbers} class.
     */
    public static final float BLAST_RESISTANCE = MagicNumbers.MATERIAL__END_PORTAL_FRAME__BLAST_RESISTANCE;
    /**
     * Hardness of block, can be changed only before server start.
     * Final copy of hardness from {@link MagicNumbers} class.
     */
    public static final float HARDNESS         = MagicNumbers.MATERIAL__END_PORTAL_FRAME__HARDNESS;

    public static final EndPortalFrameMat END_PORTAL_FRAME_SOUTH     = new EndPortalFrameMat();
    public static final EndPortalFrameMat END_PORTAL_FRAME_WEST      = new EndPortalFrameMat(BlockFace.WEST, false);
    public static final EndPortalFrameMat END_PORTAL_FRAME_NORTH     = new EndPortalFrameMat(BlockFace.NORTH, false);
    public static final EndPortalFrameMat END_PORTAL_FRAME_EAST      = new EndPortalFrameMat(BlockFace.EAST, false);
    public static final EndPortalFrameMat END_PORTAL_FRAME_SOUTH_EYE = new EndPortalFrameMat(BlockFace.SOUTH, true);
    public static final EndPortalFrameMat END_PORTAL_FRAME_WEST_EYE  = new EndPortalFrameMat(BlockFace.WEST, true);
    public static final EndPortalFrameMat END_PORTAL_FRAME_NORTH_EYE = new EndPortalFrameMat(BlockFace.NORTH, true);
    public static final EndPortalFrameMat END_PORTAL_FRAME_EAST_EYE  = new EndPortalFrameMat(BlockFace.EAST, true);

    private static final Map<String, EndPortalFrameMat>    byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<EndPortalFrameMat> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    private final BlockFace face;
    private final boolean   activated;

    @SuppressWarnings("MagicNumber")
    protected EndPortalFrameMat()
    {
        super("END_PORTAL_FRAME", 120, "minecraft:end_portal_frame", "SOUTH", (byte) 0x00);
        this.face = BlockFace.SOUTH;
        this.activated = false;
    }

    protected EndPortalFrameMat(final BlockFace face, final boolean activated)
    {
        super(END_PORTAL_FRAME_SOUTH.name(), END_PORTAL_FRAME_SOUTH.getId(), END_PORTAL_FRAME_SOUTH.getMinecraftId(), face.name() + (activated ? "_EYE" : ""), combine(face, activated));
        this.face = face;
        this.activated = activated;
    }

    protected EndPortalFrameMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type, final BlockFace face, final boolean activated)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type);
        this.face = face;
        this.activated = activated;
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
    public EndPortalFrameMat getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public EndPortalFrameMat getType(final int id)
    {
        return getByID(id);
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("face", this.face).append("activated", this.activated).toString();
    }

    @Override
    public BlockFace getBlockFacing()
    {
        return this.face;
    }

    @Override
    public EndPortalFrameMat getBlockFacing(final BlockFace face)
    {
        return getByID(combine(face, this.activated));
    }

    /**
     * @return true if frame have eye of ender in it.
     */
    public boolean isActivated()
    {
        return this.activated;
    }


    /**
     * Returns one of EndPortalFrame sub-type based on activated status.
     *
     * @param activated if it have eye of ender in it.
     *
     * @return sub-type of EndPortalFrame
     */
    public EndPortalFrameMat getActivated(final boolean activated)
    {
        return getByID(combine(this.face, activated));
    }

    /**
     * Returns one of EndPortalFrame sub-type based on {@link BlockFace} and activated status.
     * It will never return null;
     *
     * @param face      face of block, unsupported face will cause using face from default type.
     * @param activated if it have eye of ender in it.
     *
     * @return sub-type of EndPortalFrame
     */
    public EndPortalFrameMat getType(final BlockFace face, final boolean activated)
    {
        return getByID(combine(face, activated));
    }

    protected static byte combine(final BlockFace face, final boolean activated)
    {
        byte value = activated ? ACTIVATE_FLAG : 0x0;
        switch (face)
        {
            case WEST:
                value += 0x1;
                break;
            case NORTH:
                value += 0x2;
                break;
            case EAST:
                value += 0x3;
                break;
            default:
                break;
        }
        return value;
    }

    /**
     * Returns one of EndPortalFrame sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of EndPortalFrame or null
     */
    public static EndPortalFrameMat getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of EndPortalFrame sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of EndPortalFrame or null
     */
    public static EndPortalFrameMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Returns one of EndPortalFrame sub-type based on {@link BlockFace} and activated status.
     * It will never return null;
     *
     * @param face      face of block, unsupported face will cause using face from default type.
     * @param activated if it have eye of ender in it.
     *
     * @return sub-type of EndPortalFrame
     */
    public static EndPortalFrameMat getEndPortalFrame(final BlockFace face, final boolean activated)
    {
        return getByID(combine(face, activated));
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final EndPortalFrameMat element)
    {
        byID.put((byte) element.getType(), element);
        byName.put(element.name(), element);
    }

    static
    {
        EndPortalFrameMat.register(END_PORTAL_FRAME_SOUTH);
        EndPortalFrameMat.register(END_PORTAL_FRAME_WEST);
        EndPortalFrameMat.register(END_PORTAL_FRAME_NORTH);
        EndPortalFrameMat.register(END_PORTAL_FRAME_EAST);
        EndPortalFrameMat.register(END_PORTAL_FRAME_SOUTH_EYE);
        EndPortalFrameMat.register(END_PORTAL_FRAME_WEST_EYE);
        EndPortalFrameMat.register(END_PORTAL_FRAME_NORTH_EYE);
        EndPortalFrameMat.register(END_PORTAL_FRAME_EAST_EYE);
    }
}
