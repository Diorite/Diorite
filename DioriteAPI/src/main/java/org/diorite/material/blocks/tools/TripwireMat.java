package org.diorite.material.blocks.tools;

import java.util.Map;

import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.material.BlockMaterialData;
import org.diorite.material.blocks.PowerableMat;
import org.diorite.utils.collections.maps.CaseInsensitiveMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing block "Tripwire" and all its subtypes.
 */
public class TripwireMat extends BlockMaterialData implements PowerableMat
{
    /**
     * Bit flag defining if tripwire is powered. (an entity is intersecting its collision mask).
     * If bit is set to 0, then it isn't powered
     */
    public static final byte  POWERED_FLAG     = 0x1;
    /**
     * Bit flag defining if tripwire is suspended in the air. (not above a solid block).
     * If bit is set to 0, then it isn't in air
     */
    public static final byte  IN_AIR_FLAG      = 0x2;
    /**
     * Bit flag defining if tripwire is attached to a valid tripwire circuit.
     * If bit is set to 0, then it isn't attached to a valid tripwire circuit
     */
    public static final byte  VALID_FLAG       = 0x4;
    /**
     * Bit flag defining if tripwire is disarmed.
     * If bit is set to 0, then it isn't disarmed
     */
    public static final byte  DISARMED_FLAG    = 0x8;
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final byte  USED_DATA_VALUES = 16;
    /**
     * Blast resistance of block, can be changed only before server start.
     * Final copy of blast resistance from {@link MagicNumbers} class.
     */
    public static final float BLAST_RESISTANCE = MagicNumbers.MATERIAL__TRIPWIRE__BLAST_RESISTANCE;
    /**
     * Hardness of block, can be changed only before server start.
     * Final copy of hardness from {@link MagicNumbers} class.
     */
    public static final float HARDNESS         = MagicNumbers.MATERIAL__TRIPWIRE__HARDNESS;

    public static final TripwireMat TRIPWIRE                               = new TripwireMat();
    public static final TripwireMat TRIPWIRE_POWERED                       = new TripwireMat(true, false, false, false);
    public static final TripwireMat TRIPWIRE_IN_AIR                        = new TripwireMat(false, true, false, false);
    public static final TripwireMat TRIPWIRE_POWERED_IN_AIR                = new TripwireMat(true, true, false, false);
    public static final TripwireMat TRIPWIRE_VALID                         = new TripwireMat(false, false, true, false);
    public static final TripwireMat TRIPWIRE_POWERED_VALID                 = new TripwireMat(true, false, true, false);
    public static final TripwireMat TRIPWIRE_IN_AIR_VALID                  = new TripwireMat(false, true, true, false);
    public static final TripwireMat TRIPWIRE_POWERED_IN_AIR_VALID          = new TripwireMat(true, true, true, false);
    public static final TripwireMat TRIPWIRE_DISARMED                      = new TripwireMat(false, false, false, true);
    public static final TripwireMat TRIPWIRE_POWERED_DISARMED              = new TripwireMat(true, false, false, true);
    public static final TripwireMat TRIPWIRE_IN_AIR_DISARMED               = new TripwireMat(false, true, false, true);
    public static final TripwireMat TRIPWIRE_POWERED_IN_AIR_DISARMED       = new TripwireMat(true, true, false, true);
    public static final TripwireMat TRIPWIRE_VALID_DISARMED                = new TripwireMat(false, false, true, true);
    public static final TripwireMat TRIPWIRE_POWERED_VALID_DISARMED        = new TripwireMat(true, false, true, true);
    public static final TripwireMat TRIPWIRE_IN_AIR_VALID_DISARMED         = new TripwireMat(false, true, true, true);
    public static final TripwireMat TRIPWIRE_POWERED_IN_AIR_VALID_DISARMED = new TripwireMat(true, true, true, true);

    private static final Map<String, TripwireMat>    byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<TripwireMat> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    protected final boolean powered;
    protected final boolean inAir;
    protected final boolean valid;
    protected final boolean disarmed;

    @SuppressWarnings("MagicNumber")
    protected TripwireMat()
    {
        super("TRIPWIRE", 132, "minecraft:tripwire", "RAW", (byte) 0x00);
        this.powered = false;
        this.inAir = false;
        this.valid = false;
        this.disarmed = false;
    }

    protected TripwireMat(final boolean powered, final boolean inAir, final boolean valid, final boolean disarmed)
    {
        super(TRIPWIRE.name(), TRIPWIRE.ordinal(), TRIPWIRE.getMinecraftId(), combineName(powered, inAir, valid, disarmed), combine(powered, inAir, valid, disarmed));
        this.powered = powered;
        this.inAir = inAir;
        this.valid = valid;
        this.disarmed = disarmed;
    }

    public TripwireMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type, final boolean powered, final boolean inAir, final boolean valid, final boolean disarmed)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type);
        this.powered = powered;
        this.inAir = inAir;
        this.valid = valid;
        this.disarmed = disarmed;
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
    public TripwireMat getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public TripwireMat getType(final int id)
    {
        return getByID(id);
    }

    /**
     * @return if Tripwire is in air.
     */
    public boolean isInAir()
    {
        return this.inAir;
    }

    /**
     * Returns sub-type of Tripwire based on inAir state.
     * It will never return null.
     *
     * @param inAir if Tripwire should be in air.
     *
     * @return sub-type of Tripwire
     */
    public TripwireMat getInAir(final boolean inAir)
    {
        return getByID(combine(this.powered, inAir, this.valid, this.disarmed));
    }

    /**
     * @return if Tripwire is in valid.
     */
    public boolean isValid()
    {
        return this.valid;
    }

    /**
     * Returns sub-type of Tripwire based on valid state.
     * It will never return null.
     *
     * @param valid if Tripwire should be valid.
     *
     * @return sub-type of Tripwire
     */
    public TripwireMat getValid(final boolean valid)
    {
        return getByID(combine(this.powered, this.inAir, valid, this.disarmed));
    }

    /**
     * @return if Tripwire is in disarmed.
     */
    public boolean isDisarmed()
    {
        return this.disarmed;
    }

    /**
     * Returns sub-type of Tripwire based on disarmed state.
     * It will never return null.
     *
     * @param disarmed if Tripwire should be disarmed.
     *
     * @return sub-type of Tripwire
     */
    public TripwireMat getDisarmed(final boolean disarmed)
    {
        return getByID(combine(this.powered, this.inAir, this.valid, disarmed));
    }

    @Override
    public boolean isPowered()
    {
        return this.powered;
    }

    @Override
    public TripwireMat getPowered(final boolean powered)
    {
        return getByID(combine(powered, this.inAir, this.valid, this.disarmed));
    }

    /**
     * Returns sub-type of Tripwire based on powered, inAir, valid and disarmed state.
     * It will never return null.
     *
     * @param powered  if Tripwire should be powered.
     * @param inAir    if Tripwire should be in air.
     * @param valid    if Tripwire should be valid.
     * @param disarmed if Tripwire should be disarmed.
     *
     * @return sub-type of Tripwire
     */
    public TripwireMat getType(final boolean powered, final boolean inAir, final boolean valid, final boolean disarmed)
    {
        return getByID(combine(powered, inAir, valid, disarmed));
    }

    private static String combineName(final boolean powered, final boolean inAir, final boolean valid, final boolean disarmed)
    {
        boolean first = true;
        final StringBuilder str = new StringBuilder(64);
        if (powered | inAir | valid | disarmed)
        {
            if (powered)
            {
                str.append("POWERED");
                first = false;
            }
            if (inAir)
            {
                if (! first)
                {
                    str.append("_");
                }
                else
                {
                    str.append("IN_AIR");
                }
                first = false;
            }
            if (valid)
            {
                if (! first)
                {
                    str.append("_");
                }
                else
                {
                    str.append("VALID");
                }
                first = false;
            }
            if (disarmed)
            {
                if (! first)
                {
                    str.append("_");
                }
                else
                {
                    str.append("DISARMED");
                }
            }
            return str.toString();
        }
        return "RAW";
    }

    private static byte combine(final boolean powered, final boolean inAir, final boolean valid, final boolean disarmed)
    {
        byte result = powered ? POWERED_FLAG : 0x0;
        if (inAir)
        {
            result |= IN_AIR_FLAG;
        }
        if (valid)
        {
            result |= VALID_FLAG;
        }
        if (disarmed)
        {
            result |= DISARMED_FLAG;
        }
        return result;
    }

    /**
     * Returns one of Tripwire sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of Tripwire or null
     */
    public static TripwireMat getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of Tripwire sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of Tripwire or null
     */
    public static TripwireMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Returns sub-type of Tripwire based on powered, inAir, valid and disarmed state.
     * It will never return null.
     *
     * @param powered  if Tripwire should be powered.
     * @param inAir    if Tripwire should be in air.
     * @param valid    if Tripwire should be valid.
     * @param disarmed if Tripwire should be disarmed.
     *
     * @return sub-type of Tripwire
     */
    public static TripwireMat getTripwire(final boolean powered, final boolean inAir, final boolean valid, final boolean disarmed)
    {
        return getByID(combine(powered, inAir, valid, disarmed));
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final TripwireMat element)
    {
        byID.put((byte) element.getType(), element);
        byName.put(element.name(), element);
    }

    @Override
    public TripwireMat[] types()
    {
        return TripwireMat.tripwireTypes();
    }

    /**
     * @return array that contains all sub-types of this block.
     */
    public static TripwireMat[] tripwireTypes()
    {
        return byID.values(new TripwireMat[byID.size()]);
    }

    static
    {
        TripwireMat.register(TRIPWIRE);
        TripwireMat.register(TRIPWIRE_POWERED);
        TripwireMat.register(TRIPWIRE_IN_AIR);
        TripwireMat.register(TRIPWIRE_POWERED_IN_AIR);
        TripwireMat.register(TRIPWIRE_VALID);
        TripwireMat.register(TRIPWIRE_POWERED_VALID);
        TripwireMat.register(TRIPWIRE_IN_AIR_VALID);
        TripwireMat.register(TRIPWIRE_POWERED_IN_AIR_VALID);
        TripwireMat.register(TRIPWIRE_DISARMED);
        TripwireMat.register(TRIPWIRE_POWERED_DISARMED);
        TripwireMat.register(TRIPWIRE_IN_AIR_DISARMED);
        TripwireMat.register(TRIPWIRE_POWERED_IN_AIR_DISARMED);
        TripwireMat.register(TRIPWIRE_VALID_DISARMED);
        TripwireMat.register(TRIPWIRE_POWERED_VALID_DISARMED);
        TripwireMat.register(TRIPWIRE_IN_AIR_VALID_DISARMED);
        TripwireMat.register(TRIPWIRE_POWERED_IN_AIR_VALID_DISARMED);
    }
}
