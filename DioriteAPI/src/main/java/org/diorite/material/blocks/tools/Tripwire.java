package org.diorite.material.blocks.tools;

import java.util.Map;

import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.material.BlockMaterialData;
import org.diorite.material.blocks.Activatable;
import org.diorite.utils.collections.SimpleStringHashMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing block "Tripwire" and all its subtypes.
 */
public class Tripwire extends BlockMaterialData implements Activatable
{
    /**
     * Bit flag defining if tripwire is active. (an entity is intersecting its collision mask).
     * If bit is set to 0, then it isn't active
     */
    public static final byte  ACTIVE_FLAG      = 0x1;
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

    public static final Tripwire TRIPWIRE                              = new Tripwire();
    public static final Tripwire TRIPWIRE_ACTIVE                       = new Tripwire(true, false, false, false);
    public static final Tripwire TRIPWIRE_IN_AIR                       = new Tripwire(false, true, false, false);
    public static final Tripwire TRIPWIRE_ACTIVE_IN_AIR                = new Tripwire(true, true, false, false);
    public static final Tripwire TRIPWIRE_VALID                        = new Tripwire(false, false, true, false);
    public static final Tripwire TRIPWIRE_ACTIVE_VALID                 = new Tripwire(true, false, true, false);
    public static final Tripwire TRIPWIRE_IN_AIR_VALID                 = new Tripwire(false, true, true, false);
    public static final Tripwire TRIPWIRE_ACTIVE_IN_AIR_VALID          = new Tripwire(true, true, true, false);
    public static final Tripwire TRIPWIRE_DISARMED                     = new Tripwire(false, false, false, true);
    public static final Tripwire TRIPWIRE_ACTIVE_DISARMED              = new Tripwire(true, false, false, true);
    public static final Tripwire TRIPWIRE_IN_AIR_DISARMED              = new Tripwire(false, true, false, true);
    public static final Tripwire TRIPWIRE_ACTIVE_IN_AIR_DISARMED       = new Tripwire(true, true, false, true);
    public static final Tripwire TRIPWIRE_VALID_DISARMED               = new Tripwire(false, false, true, true);
    public static final Tripwire TRIPWIRE_ACTIVE_VALID_DISARMED        = new Tripwire(true, false, true, true);
    public static final Tripwire TRIPWIRE_IN_AIR_VALID_DISARMED        = new Tripwire(false, true, true, true);
    public static final Tripwire TRIPWIRE_ACTIVE_IN_AIR_VALID_DISARMED = new Tripwire(true, true, true, true);

    private static final Map<String, Tripwire>    byName = new SimpleStringHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<Tripwire> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    protected boolean activated;
    protected boolean inAir;
    protected boolean valid;
    protected boolean disarmed;

    @SuppressWarnings("MagicNumber")
    protected Tripwire()
    {
        super("TRIPWIRE", 132, "minecraft:tripwire", "RAW", (byte) 0x00);
    }

    public Tripwire(final boolean activated, final boolean inAir, final boolean valid, final boolean disarmed)
    {
        super(TRIPWIRE.name(), TRIPWIRE.getId(), TRIPWIRE.getMinecraftId(), combineName(activated, inAir, valid, disarmed), combine(activated, inAir, valid, disarmed));
    }

    private static String combineName(final boolean activated, final boolean inAir, final boolean valid, final boolean disarmed)
    {
        boolean first = true;
        final StringBuilder str = new StringBuilder(64);
        if (activated | inAir | valid | disarmed)
        {
            if (activated)
            {
                str.append("ACTIVE");
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

    private static byte combine(final boolean activated, final boolean inAir, final boolean valid, final boolean disarmed)
    {
        byte result = activated ? ACTIVE_FLAG : 0x0;
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
    public Tripwire getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public Tripwire getType(final int id)
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
    public Tripwire getInAir(final boolean inAir)
    {
        return getByID(combine(this.activated, inAir, this.valid, this.disarmed));
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
    public Tripwire getValid(final boolean valid)
    {
        return getByID(combine(this.activated, this.inAir, valid, this.disarmed));
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
    public Tripwire getDisarmed(final boolean disarmed)
    {
        return getByID(combine(this.activated, this.inAir, this.valid, disarmed));
    }

    @Override
    public boolean isActivated()
    {
        return this.activated;
    }

    @Override
    public Tripwire getActivated(final boolean activated)
    {
        return getByID(combine(activated, this.inAir, this.valid, this.disarmed));
    }

    /**
     * Returns sub-type of Tripwire based on activate, inAir, valid and disarmed state.
     * It will never return null.
     *
     * @param activated if Tripwire should be activated.
     * @param inAir     if Tripwire should be in air.
     * @param valid     if Tripwire should be valid.
     * @param disarmed  if Tripwire should be disarmed.
     *
     * @return sub-type of Tripwire
     */
    public Tripwire getType(final boolean activated, final boolean inAir, final boolean valid, final boolean disarmed)
    {
        return getByID(combine(activated, inAir, valid, disarmed));
    }

    /**
     * Returns one of Tripwire sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of Tripwire or null
     */
    public static Tripwire getByID(final int id)
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
    public static Tripwire getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Returns sub-type of Tripwire based on activate, inAir, valid and disarmed state.
     * It will never return null.
     *
     * @param activated if Tripwire should be activated.
     * @param inAir     if Tripwire should be in air.
     * @param valid     if Tripwire should be valid.
     * @param disarmed  if Tripwire should be disarmed.
     *
     * @return sub-type of Tripwire
     */
    public static Tripwire getTripwire(final boolean activated, final boolean inAir, final boolean valid, final boolean disarmed)
    {
        return getByID(combine(activated, inAir, valid, disarmed));
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final Tripwire element)
    {
        byID.put(element.getType(), element);
        byName.put(element.name(), element);
    }

    static
    {
        Tripwire.register(TRIPWIRE);
        Tripwire.register(TRIPWIRE_ACTIVE);
        Tripwire.register(TRIPWIRE_IN_AIR);
        Tripwire.register(TRIPWIRE_ACTIVE_IN_AIR);
        Tripwire.register(TRIPWIRE_VALID);
        Tripwire.register(TRIPWIRE_ACTIVE_VALID);
        Tripwire.register(TRIPWIRE_IN_AIR_VALID);
        Tripwire.register(TRIPWIRE_ACTIVE_IN_AIR_VALID);
        Tripwire.register(TRIPWIRE_DISARMED);
        Tripwire.register(TRIPWIRE_ACTIVE_DISARMED);
        Tripwire.register(TRIPWIRE_IN_AIR_DISARMED);
        Tripwire.register(TRIPWIRE_ACTIVE_IN_AIR_DISARMED);
        Tripwire.register(TRIPWIRE_VALID_DISARMED);
        Tripwire.register(TRIPWIRE_ACTIVE_VALID_DISARMED);
        Tripwire.register(TRIPWIRE_IN_AIR_VALID_DISARMED);
        Tripwire.register(TRIPWIRE_ACTIVE_IN_AIR_VALID_DISARMED);
    }
}
