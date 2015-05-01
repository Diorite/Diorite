package org.diorite.material.blocks.end;

import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.BlockFace;
import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.material.BlockMaterialData;
import org.diorite.material.blocks.Activatable;
import org.diorite.material.blocks.Directional;
import org.diorite.utils.collections.SimpleStringHashMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing block "EndPortalFrame" and all its subtypes.
 */
public class EndPortalFrame extends BlockMaterialData implements Directional, Activatable
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

    public static final EndPortalFrame END_PORTAL_FRAME_SOUTH     = new EndPortalFrame();
    public static final EndPortalFrame END_PORTAL_FRAME_WEST      = new EndPortalFrame("WEST", BlockFace.WEST, false);
    public static final EndPortalFrame END_PORTAL_FRAME_NORTH     = new EndPortalFrame("NORTH", BlockFace.NORTH, false);
    public static final EndPortalFrame END_PORTAL_FRAME_EAST      = new EndPortalFrame("EAST", BlockFace.EAST, false);
    public static final EndPortalFrame END_PORTAL_FRAME_SOUTH_EYE = new EndPortalFrame("SOUTH_EYE", BlockFace.SOUTH, true);
    public static final EndPortalFrame END_PORTAL_FRAME_WEST_EYE  = new EndPortalFrame("WEST_EYE", BlockFace.WEST, true);
    public static final EndPortalFrame END_PORTAL_FRAME_NORTH_EYE = new EndPortalFrame("NORTH_EYE", BlockFace.NORTH, true);
    public static final EndPortalFrame END_PORTAL_FRAME_EAST_EYE  = new EndPortalFrame("EAST_EYE", BlockFace.EAST, true);

    private static final Map<String, EndPortalFrame>    byName = new SimpleStringHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<EndPortalFrame> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    private final BlockFace face;
    private final boolean   activated;

    @SuppressWarnings("MagicNumber")
    protected EndPortalFrame()
    {
        super("END_PORTAL_FRAME", 120, "minecraft:end_portal_frame", "SOUTH", (byte) 0x00);
        this.face = BlockFace.SOUTH;
        this.activated = false;
    }

    public EndPortalFrame(final String enumName, final BlockFace face, final boolean activated)
    {
        super(END_PORTAL_FRAME_SOUTH.name(), END_PORTAL_FRAME_SOUTH.getId(), END_PORTAL_FRAME_SOUTH.getMinecraftId(), enumName, combine(face, activated));
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
    public EndPortalFrame getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public EndPortalFrame getType(final int id)
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
    public EndPortalFrame getBlockFacing(final BlockFace face)
    {
        return getByID(combine(face, this.activated));
    }

    @Override
    public boolean isActivated()
    {
        return this.activated;
    }

    @Override
    public EndPortalFrame getActivated(final boolean activated)
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
    public EndPortalFrame getType(final BlockFace face, final boolean activated)
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
    public static EndPortalFrame getByID(final int id)
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
    public static EndPortalFrame getByEnumName(final String name)
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
    public static EndPortalFrame getEndPortalFrame(final BlockFace face, final boolean activated)
    {
        return getByID(combine(face, activated));
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final EndPortalFrame element)
    {
        byID.put(element.getType(), element);
        byName.put(element.name(), element);
    }

    static
    {
        EndPortalFrame.register(END_PORTAL_FRAME_SOUTH);
        EndPortalFrame.register(END_PORTAL_FRAME_WEST);
        EndPortalFrame.register(END_PORTAL_FRAME_NORTH);
        EndPortalFrame.register(END_PORTAL_FRAME_EAST);
        EndPortalFrame.register(END_PORTAL_FRAME_SOUTH_EYE);
        EndPortalFrame.register(END_PORTAL_FRAME_WEST_EYE);
        EndPortalFrame.register(END_PORTAL_FRAME_NORTH_EYE);
        EndPortalFrame.register(END_PORTAL_FRAME_EAST_EYE);
    }
}
