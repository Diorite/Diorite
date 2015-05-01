package org.diorite.material.blocks.tools;

import java.util.Map;

import org.diorite.BlockFace;
import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.material.BlockMaterialData;
import org.diorite.material.blocks.Activatable;
import org.diorite.material.blocks.Directional;
import org.diorite.utils.collections.SimpleStringHashMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing block "TripwireHook" and all its subtypes.
 */
public class TripwireHook extends BlockMaterialData implements Activatable, Directional
{
    /**
     * Bit flag defining if tripwire is ready to trip. ("middle" position)
     * If bit is set to 0, then it isn't ready
     */
    public static final byte  READY_FLAG       = 0x4;
    /**
     * Bit flag defining if tripwire is active. ("down" position)
     * If bit is set to 0, then it isn't active
     */
    public static final byte  ACTIVE_FLAG      = 0x8;
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final byte  USED_DATA_VALUES = 16;
    /**
     * Blast resistance of block, can be changed only before server start.
     * Final copy of blast resistance from {@link MagicNumbers} class.
     */
    public static final float BLAST_RESISTANCE = MagicNumbers.MATERIAL__TRIPWIRE_HOOK__BLAST_RESISTANCE;
    /**
     * Hardness of block, can be changed only before server start.
     * Final copy of hardness from {@link MagicNumbers} class.
     */
    public static final float HARDNESS         = MagicNumbers.MATERIAL__TRIPWIRE_HOOK__HARDNESS;

    public static final TripwireHook TRIPWIRE_HOOK_SOUTH = new TripwireHook();
    public static final TripwireHook TRIPWIRE_HOOK_WEST  = new TripwireHook(BlockFace.WEST, false, false);
    public static final TripwireHook TRIPWIRE_HOOK_NORTH = new TripwireHook(BlockFace.NORTH, false, false);
    public static final TripwireHook TRIPWIRE_HOOK_EAST  = new TripwireHook(BlockFace.EAST, false, false);

    public static final TripwireHook TRIPWIRE_HOOK_SOUTH_READY = new TripwireHook(BlockFace.SOUTH, true, false);
    public static final TripwireHook TRIPWIRE_HOOK_WEST_READY  = new TripwireHook(BlockFace.WEST, true, false);
    public static final TripwireHook TRIPWIRE_HOOK_NORTH_READY = new TripwireHook(BlockFace.NORTH, true, false);
    public static final TripwireHook TRIPWIRE_HOOK_EAST_READY  = new TripwireHook(BlockFace.EAST, true, false);

    public static final TripwireHook TRIPWIRE_HOOK_SOUTH_ACTIVE = new TripwireHook(BlockFace.SOUTH, false, true);
    public static final TripwireHook TRIPWIRE_HOOK_WEST_ACTIVE  = new TripwireHook(BlockFace.WEST, false, true);
    public static final TripwireHook TRIPWIRE_HOOK_NORTH_ACTIVE = new TripwireHook(BlockFace.NORTH, false, true);
    public static final TripwireHook TRIPWIRE_HOOK_EAST_ACTIVE  = new TripwireHook(BlockFace.EAST, false, true);

    public static final TripwireHook TRIPWIRE_HOOK_SOUTH_READY_ACTIVE = new TripwireHook(BlockFace.SOUTH, true, true);
    public static final TripwireHook TRIPWIRE_HOOK_WEST_READY_ACTIVE  = new TripwireHook(BlockFace.WEST, true, true);
    public static final TripwireHook TRIPWIRE_HOOK_NORTH_READY_ACTIVE = new TripwireHook(BlockFace.NORTH, true, true);
    public static final TripwireHook TRIPWIRE_HOOK_EAST_READY_ACTIVE  = new TripwireHook(BlockFace.EAST, true, true);

    private static final Map<String, TripwireHook>    byName = new SimpleStringHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<TripwireHook> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    protected final BlockFace face;
    protected final boolean   ready;
    protected final boolean   activated;

    @SuppressWarnings("MagicNumber")
    protected TripwireHook()
    {
        super("TRIPWIRE_HOOK", 131, "minecraft:tripwire_hook", "SOUTH", (byte) 0x00);
        this.face = BlockFace.SOUTH;
        this.ready = false;
        this.activated = false;
    }

    public TripwireHook(final BlockFace face, final boolean ready, final boolean activated)
    {
        super(TRIPWIRE_HOOK_SOUTH.name(), TRIPWIRE_HOOK_SOUTH.getId(), TRIPWIRE_HOOK_SOUTH.getMinecraftId(), face.name() + (ready ? "_READY" : "") + (activated ? "_ACTIVE" : ""), combine(face, ready, activated));
        this.face = face;
        this.ready = ready;
        this.activated = activated;
    }

    private static byte combine(final BlockFace face, final boolean ready, final boolean active)
    {
        byte result = active ? ACTIVE_FLAG : 0x0;
        switch (face)
        {
            case WEST:
                result |= 0x1;
                break;
            case NORTH:
                result |= 0x2;
                break;
            case EAST:
                result |= 0x3;
                break;
            default:
                break;
        }
        if (ready)
        {
            result |= READY_FLAG;
        }
        return result;
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
    public TripwireHook getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public TripwireHook getType(final int id)
    {
        return getByID(id);
    }

    @Override
    public boolean isActivated()
    {
        return this.activated;
    }

    @Override
    public TripwireHook getActivated(final boolean activated)
    {
        return getByID(combine(this.face, this.ready, activated));
    }

    @Override
    public BlockFace getBlockFacing()
    {
        return this.face;
    }

    /**
     * @return true if tripwire is ready to trip. ("middle" position)
     */
    public boolean isReady()
    {
        return this.ready;
    }

    /**
     * Returns sub-type of TripwireHook based on ready state.
     *
     * @param ready if tripwire should be in ready to trip position. ("middle" position)
     *
     * @return sub-type of TripwireHook
     */
    public TripwireHook getReady(final boolean ready)
    {
        return getByID(combine(this.face, ready, this.activated));
    }

    @Override
    public TripwireHook getBlockFacing(final BlockFace face)
    {
        return getByID(combine(face, this.ready, this.activated));
    }

    /**
     * Returns sub-type of TripwireHook based on {@link BlockFace}, ready and activate state.
     * It will never return null.
     *
     * @param face      facing direction of TripwireHook
     * @param ready     if TripwireHook should be ready to trip. ("middle" position)
     * @param activated if TripwireHook should be activated. ("down" position)
     *
     * @return sub-type of TripwireHook
     */
    public TripwireHook getType(final BlockFace face, final boolean ready, final boolean activated)
    {
        return getByID(combine(face, ready, activated));
    }

    /**
     * Returns one of TripwireHook sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of TripwireHook or null
     */
    public static TripwireHook getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of TripwireHook sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of TripwireHook or null
     */
    public static TripwireHook getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Returns sub-type of TripwireHook based on {@link BlockFace}, ready and activate state.
     * It will never return null.
     *
     * @param face      facing direction of TripwireHook
     * @param ready     if TripwireHook should be ready to trip. ("middle" position)
     * @param activated if TripwireHook should be activated. ("down" position)
     *
     * @return sub-type of TripwireHook
     */
    public static TripwireHook getTripwireHook(final BlockFace face, final boolean ready, final boolean activated)
    {
        return getByID(combine(face, ready, activated));
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final TripwireHook element)
    {
        byID.put(element.getType(), element);
        byName.put(element.name(), element);
    }

    static
    {
        TripwireHook.register(TRIPWIRE_HOOK_SOUTH);
        TripwireHook.register(TRIPWIRE_HOOK_WEST);
        TripwireHook.register(TRIPWIRE_HOOK_NORTH);
        TripwireHook.register(TRIPWIRE_HOOK_EAST);
        TripwireHook.register(TRIPWIRE_HOOK_SOUTH_READY);
        TripwireHook.register(TRIPWIRE_HOOK_WEST_READY);
        TripwireHook.register(TRIPWIRE_HOOK_NORTH_READY);
        TripwireHook.register(TRIPWIRE_HOOK_EAST_READY);
        TripwireHook.register(TRIPWIRE_HOOK_SOUTH_ACTIVE);
        TripwireHook.register(TRIPWIRE_HOOK_WEST_ACTIVE);
        TripwireHook.register(TRIPWIRE_HOOK_NORTH_ACTIVE);
        TripwireHook.register(TRIPWIRE_HOOK_EAST_ACTIVE);
        TripwireHook.register(TRIPWIRE_HOOK_SOUTH_READY_ACTIVE);
        TripwireHook.register(TRIPWIRE_HOOK_WEST_READY_ACTIVE);
        TripwireHook.register(TRIPWIRE_HOOK_NORTH_READY_ACTIVE);
        TripwireHook.register(TRIPWIRE_HOOK_EAST_READY_ACTIVE);
    }
}
