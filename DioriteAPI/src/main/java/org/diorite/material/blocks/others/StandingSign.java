package org.diorite.material.blocks.others;

import java.util.Map;

import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.utils.collections.SimpleStringHashMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing block "StandingSign" and all its subtypes.
 */
public class StandingSign extends SignBlock
{
    // TODO: auto-generated class, implement other types (sub-ids).	
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final byte  USED_DATA_VALUES = 1;
    /**
     * Blast resistance of block, can be changed only before server start.
     * Final copy of blast resistance from {@link MagicNumbers} class.
     */
    public static final float BLAST_RESISTANCE = MagicNumbers.MATERIAL__STANDING_SIGN__BLAST_RESISTANCE;
    /**
     * Hardness of block, can be changed only before server start.
     * Final copy of hardness from {@link MagicNumbers} class.
     */
    public static final float HARDNESS         = MagicNumbers.MATERIAL__STANDING_SIGN__HARDNESS;

    public static final StandingSign STANDING_SIGN = new StandingSign();

    private static final Map<String, StandingSign>    byName = new SimpleStringHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<StandingSign> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    @SuppressWarnings("MagicNumber")
    protected StandingSign()
    {
        super("STANDING_SIGN", 63, "minecraft:standing_sign", "STANDING_SIGN", (byte) 0x00);
    }

    public StandingSign(final String enumName, final int type)
    {
        super(STANDING_SIGN.name(), STANDING_SIGN.getId(), STANDING_SIGN.getMinecraftId(), enumName, (byte) type);
    }

    public StandingSign(final int maxStack, final String typeName, final byte type)
    {
        super(STANDING_SIGN.name(), STANDING_SIGN.getId(), STANDING_SIGN.getMinecraftId(), maxStack, typeName, type);
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
    public StandingSign getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public StandingSign getType(final int id)
    {
        return getByID(id);
    }

    /**
     * Returns one of StandingSign sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of StandingSign or null
     */
    public static StandingSign getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of StandingSign sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of StandingSign or null
     */
    public static StandingSign getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final StandingSign element)
    {
        byID.put(element.getType(), element);
        byName.put(element.name(), element);
    }

    static
    {
        StandingSign.register(STANDING_SIGN);
    }
}
