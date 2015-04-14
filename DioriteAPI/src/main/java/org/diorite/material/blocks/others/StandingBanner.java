package org.diorite.material.blocks.others;

import java.util.Map;

import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.utils.collections.SimpleStringHashMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing block "StandingBanner" and all its subtypes.
 */
public class StandingBanner extends BannerBlock
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
    public static final float BLAST_RESISTANCE = MagicNumbers.MATERIAL__STANDING_BANNER__BLAST_RESISTANCE;	
    /**
     * Hardness of block, can be changed only before server start.
     * Final copy of hardness from {@link MagicNumbers} class.
     */
    public static final float HARDNESS         = MagicNumbers.MATERIAL__STANDING_BANNER__HARDNESS;

    public static final StandingBanner STANDING_BANNER = new StandingBanner();

    private static final Map<String, StandingBanner>    byName = new SimpleStringHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<StandingBanner> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    @SuppressWarnings("MagicNumber")
    protected StandingBanner()
    {
        super("STANDING_BANNER", 176, "minecraft:standing_banner", "STANDING_BANNER", (byte) 0x00);
    }

    public StandingBanner(final String enumName, final int type)
    {
        super(STANDING_BANNER.name(), STANDING_BANNER.getId(), STANDING_BANNER.getMinecraftId(), enumName, (byte) type);
    }

    public StandingBanner(final int maxStack, final String typeName, final byte type)
    {
        super(STANDING_BANNER.name(), STANDING_BANNER.getId(), STANDING_BANNER.getMinecraftId(), maxStack, typeName, type);
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
    public StandingBanner getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public StandingBanner getType(final int id)
    {
        return getByID(id);
    }

    public static StandingBanner getByID(final int id)
    {
        return byID.get((byte) id);
    }

    public static StandingBanner getByEnumName(final String name)
    {
        return byName.get(name);
    }

    public static void register(final StandingBanner element)
    {
        byID.put(element.getType(), element);
        byName.put(element.name(), element);
    }

    static
    {
        StandingBanner.register(STANDING_BANNER);
    }
}
