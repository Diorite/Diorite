package org.diorite.material.blocks.tools;

import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.BlockFace;
import org.diorite.material.BlockMaterialData;
import org.diorite.material.Material;
import org.diorite.material.blocks.AttachableMat;
import org.diorite.material.blocks.PowerableMat;
import org.diorite.utils.collections.maps.CaseInsensitiveMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing block "TripwireHook" and all its subtypes.
 */
public class TripwireHookMat extends BlockMaterialData implements PowerableMat, AttachableMat
{
    /**
     * Bit flag defining if tripwire is ready to trip. ("middle" position)
     * If bit is set to 0, then it isn't ready
     */
    public static final byte READY_FLAG       = 0x4;
    /**
     * Bit flag defining if tripwire is powered. ("down" position)
     * If bit is set to 0, then it isn't powered
     */
    public static final byte POWERED_FLAG     = 0x8;
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final int  USED_DATA_VALUES = 16;

    public static final TripwireHookMat TRIPWIRE_HOOK_SOUTH = new TripwireHookMat();
    public static final TripwireHookMat TRIPWIRE_HOOK_WEST  = new TripwireHookMat(BlockFace.WEST, false, false);
    public static final TripwireHookMat TRIPWIRE_HOOK_NORTH = new TripwireHookMat(BlockFace.NORTH, false, false);
    public static final TripwireHookMat TRIPWIRE_HOOK_EAST  = new TripwireHookMat(BlockFace.EAST, false, false);

    public static final TripwireHookMat TRIPWIRE_HOOK_SOUTH_READY = new TripwireHookMat(BlockFace.SOUTH, true, false);
    public static final TripwireHookMat TRIPWIRE_HOOK_WEST_READY  = new TripwireHookMat(BlockFace.WEST, true, false);
    public static final TripwireHookMat TRIPWIRE_HOOK_NORTH_READY = new TripwireHookMat(BlockFace.NORTH, true, false);
    public static final TripwireHookMat TRIPWIRE_HOOK_EAST_READY  = new TripwireHookMat(BlockFace.EAST, true, false);

    public static final TripwireHookMat TRIPWIRE_HOOK_SOUTH_POWERED = new TripwireHookMat(BlockFace.SOUTH, false, true);
    public static final TripwireHookMat TRIPWIRE_HOOK_WEST_POWERED  = new TripwireHookMat(BlockFace.WEST, false, true);
    public static final TripwireHookMat TRIPWIRE_HOOK_NORTH_POWERED = new TripwireHookMat(BlockFace.NORTH, false, true);
    public static final TripwireHookMat TRIPWIRE_HOOK_EAST_POWERED  = new TripwireHookMat(BlockFace.EAST, false, true);

    public static final TripwireHookMat TRIPWIRE_HOOK_SOUTH_READY_POWERED = new TripwireHookMat(BlockFace.SOUTH, true, true);
    public static final TripwireHookMat TRIPWIRE_HOOK_WEST_READY_POWERED  = new TripwireHookMat(BlockFace.WEST, true, true);
    public static final TripwireHookMat TRIPWIRE_HOOK_NORTH_READY_POWERED = new TripwireHookMat(BlockFace.NORTH, true, true);
    public static final TripwireHookMat TRIPWIRE_HOOK_EAST_READY_POWERED  = new TripwireHookMat(BlockFace.EAST, true, true);

    private static final Map<String, TripwireHookMat>    byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<TripwireHookMat> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    protected final BlockFace face;
    protected final boolean   ready;
    protected final boolean   powered;

    @SuppressWarnings("MagicNumber")
    protected TripwireHookMat()
    {
        super("TRIPWIRE_HOOK", 131, "minecraft:tripwire_hook", "SOUTH", (byte) 0x00, 0, 0);
        this.face = BlockFace.SOUTH;
        this.ready = false;
        this.powered = false;
    }

    protected TripwireHookMat(final BlockFace face, final boolean ready, final boolean powered)
    {
        super(TRIPWIRE_HOOK_SOUTH.name(), TRIPWIRE_HOOK_SOUTH.ordinal(), TRIPWIRE_HOOK_SOUTH.getMinecraftId(), face.name() + (ready ? "_READY" : "") + (powered ? "_POWERED" : ""), combine(face, ready, powered), TRIPWIRE_HOOK_SOUTH.getHardness(), TRIPWIRE_HOOK_SOUTH.getBlastResistance());
        this.face = face;
        this.ready = ready;
        this.powered = powered;
    }

    protected TripwireHookMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type, final BlockFace face, final boolean ready, final boolean powered, final float hardness, final float blastResistance)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, hardness, blastResistance);
        this.face = face;
        this.ready = ready;
        this.powered = powered;
    }

    @Override
    public Material ensureValidInventoryItem()
    {
        return Material.TRIPWIRE_HOOK;
    }

    @Override
    public TripwireHookMat getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public TripwireHookMat getType(final int id)
    {
        return getByID(id);
    }

    @Override
    public boolean isPowered()
    {
        return this.powered;
    }

    @Override
    public TripwireHookMat getPowered(final boolean powered)
    {
        return getByID(combine(this.face, this.ready, powered));
    }

    @Override
    public BlockFace getBlockFacing()
    {
        return this.face;
    }

    @Override
    public TripwireHookMat getBlockFacing(final BlockFace face)
    {
        return getByID(combine(face, this.ready, this.powered));
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
    public TripwireHookMat getReady(final boolean ready)
    {
        return getByID(combine(this.face, ready, this.powered));
    }

    /**
     * Returns sub-type of TripwireHook based on {@link BlockFace}, ready and powered state.
     * It will never return null.
     *
     * @param face    facing direction of TripwireHook
     * @param ready   if TripwireHook should be ready to trip. ("middle" position)
     * @param powered if TripwireHook should be powered. ("down" position)
     *
     * @return sub-type of TripwireHook
     */
    public TripwireHookMat getType(final BlockFace face, final boolean ready, final boolean powered)
    {
        return getByID(combine(face, ready, powered));
    }

    @Override
    public TripwireHookMat getAttachedFace(final BlockFace face)
    {
        return getByID(combine(face.getOppositeFace(), this.ready, this.powered));
    }

    private static byte combine(final BlockFace face, final boolean ready, final boolean powered)
    {
        byte result = powered ? POWERED_FLAG : 0x0;
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

    /**
     * Returns one of TripwireHook sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of TripwireHook or null
     */
    public static TripwireHookMat getByID(final int id)
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
    public static TripwireHookMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Returns sub-type of TripwireHook based on {@link BlockFace}, ready and powered state.
     * It will never return null.
     *
     * @param face    facing direction of TripwireHook
     * @param ready   if TripwireHook should be ready to trip. ("middle" position)
     * @param powered if TripwireHook should be powered. ("down" position)
     *
     * @return sub-type of TripwireHook
     */
    public static TripwireHookMat getTripwireHook(final BlockFace face, final boolean ready, final boolean powered)
    {
        return getByID(combine(face, ready, powered));
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final TripwireHookMat element)
    {
        byID.put((byte) element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    @Override
    public TripwireHookMat[] types()
    {
        return TripwireHookMat.tripwireHookTypes();
    }

    /**
     * @return array that contains all sub-types of this block.
     */
    public static TripwireHookMat[] tripwireHookTypes()
    {
        return byID.values(new TripwireHookMat[byID.size()]);
    }

    static
    {
        TripwireHookMat.register(TRIPWIRE_HOOK_SOUTH);
        TripwireHookMat.register(TRIPWIRE_HOOK_WEST);
        TripwireHookMat.register(TRIPWIRE_HOOK_NORTH);
        TripwireHookMat.register(TRIPWIRE_HOOK_EAST);
        TripwireHookMat.register(TRIPWIRE_HOOK_SOUTH_READY);
        TripwireHookMat.register(TRIPWIRE_HOOK_WEST_READY);
        TripwireHookMat.register(TRIPWIRE_HOOK_NORTH_READY);
        TripwireHookMat.register(TRIPWIRE_HOOK_EAST_READY);
        TripwireHookMat.register(TRIPWIRE_HOOK_SOUTH_POWERED);
        TripwireHookMat.register(TRIPWIRE_HOOK_WEST_POWERED);
        TripwireHookMat.register(TRIPWIRE_HOOK_NORTH_POWERED);
        TripwireHookMat.register(TRIPWIRE_HOOK_EAST_POWERED);
        TripwireHookMat.register(TRIPWIRE_HOOK_SOUTH_READY_POWERED);
        TripwireHookMat.register(TRIPWIRE_HOOK_WEST_READY_POWERED);
        TripwireHookMat.register(TRIPWIRE_HOOK_NORTH_READY_POWERED);
        TripwireHookMat.register(TRIPWIRE_HOOK_EAST_READY_POWERED);
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("face", this.face).append("ready", this.ready).append("powered", this.powered).toString();
    }
}
