package org.diorite.material.blocks.others;

import java.util.Map;

import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.utils.collections.SimpleStringHashMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing block "WallBanner" and all its subtypes.
 */
public class WallBanner extends BannerBlock
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
    public static final float BLAST_RESISTANCE = MagicNumbers.MATERIAL__WALL_BANNER__BLAST_RESISTANCE;
    /**
     * Hardness of block, can be changed only before server start.
     * Final copy of hardness from {@link MagicNumbers} class.
     */
    public static final float HARDNESS         = MagicNumbers.MATERIAL__WALL_BANNER__HARDNESS;

    public static final WallBanner WALL_BANNER = new WallBanner();

    private static final Map<String, WallBanner>    byName = new SimpleStringHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<WallBanner> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    @SuppressWarnings("MagicNumber")
    protected WallBanner()
    {
        super("WALL_BANNER", 177, "minecraft:wall_banner", "WALL_BANNER", (byte) 0x00);
    }

    public WallBanner(final String enumName, final int type)
    {
        super(WALL_BANNER.name(), WALL_BANNER.getId(), WALL_BANNER.getMinecraftId(), enumName, (byte) type);
    }

    public WallBanner(final int maxStack, final String typeName, final byte type)
    {
        super(WALL_BANNER.name(), WALL_BANNER.getId(), WALL_BANNER.getMinecraftId(), maxStack, typeName, type);
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
    public WallBanner getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public WallBanner getType(final int id)
    {
        return getByID(id);
    }

    /**
     * Returns one of WallBanner sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of WallBanner or null
     */
    public static WallBanner getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of WallBanner sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of WallBanner or null
     */
    public static WallBanner getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final WallBanner element)
    {
        byID.put(element.getType(), element);
        byName.put(element.name(), element);
    }

    static
    {
        WallBanner.register(WALL_BANNER);
    }
}
