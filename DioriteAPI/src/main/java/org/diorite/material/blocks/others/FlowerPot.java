package org.diorite.material.blocks.others;

import java.util.Map;

import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.material.BlockMaterialData;
import org.diorite.utils.collections.SimpleStringHashMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing block "FlowerPot" and all its subtypes.
 */
@SuppressWarnings({"MagicNumber", "deprecation"})
public class FlowerPot extends BlockMaterialData
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final byte  USED_DATA_VALUES = 14;
    /**
     * Blast resistance of block, can be changed only before server start.
     * Final copy of blast resistance from {@link MagicNumbers} class.
     */
    public static final float BLAST_RESISTANCE = MagicNumbers.MATERIAL__FLOWER_POT__BLAST_RESISTANCE;
    /**
     * Hardness of block, can be changed only before server start.
     * Final copy of hardness from {@link MagicNumbers} class.
     */
    public static final float HARDNESS         = MagicNumbers.MATERIAL__FLOWER_POT__HARDNESS;

    public static final FlowerPot FLOWER_POT_EMPTY                   = new FlowerPot();
    @Deprecated
    public static final FlowerPot FLOWER_POT_LEGACY_POPPY            = new FlowerPot("LEGACY_POPPY", 0x1);
    @Deprecated
    public static final FlowerPot FLOWER_POT_LEGACY_DANDELION        = new FlowerPot("LEGACY_DANDELION", 0x2);
    @Deprecated
    public static final FlowerPot FLOWER_POT_LEGACY_OAK_SAPLING      = new FlowerPot("LEGACY_OAK_SAPLING", 0x3);
    @Deprecated
    public static final FlowerPot FLOWER_POT_LEGACY_SPRUCE_SAPLING   = new FlowerPot("LEGACY_SPRUCE_SAPLING", 0x4);
    @Deprecated
    public static final FlowerPot FLOWER_POT_LEGACY_BIRCH_SAPLING    = new FlowerPot("LEGACY_BIRCH_SAPLING", 0x5);
    @Deprecated
    public static final FlowerPot FLOWER_POT_LEGACY_JUNGLE_SAPLING   = new FlowerPot("LEGACY_JUNGLE_SAPLING", 0x6);
    @Deprecated
    public static final FlowerPot FLOWER_POT_LEGACY_RED_MUSHROOM     = new FlowerPot("LEGACY_RED_MUSHROOM", 0x7);
    @Deprecated
    public static final FlowerPot FLOWER_POT_LEGACY_BROWN_MUSHROOM   = new FlowerPot("LEGACY_BROWN_MUSHROOM", 0x8);
    @Deprecated
    public static final FlowerPot FLOWER_POT_LEGACY_CACTUS           = new FlowerPot("LEGACY_CACTUS", 0x9);
    @Deprecated
    public static final FlowerPot FLOWER_POT_LEGACY_DEAD_BUSH        = new FlowerPot("LEGACY_DEAD_BUSH", 0xA);
    @Deprecated
    public static final FlowerPot FLOWER_POT_LEGACY_FERN             = new FlowerPot("LEGACY_FERN", 0xB);
    @Deprecated
    public static final FlowerPot FLOWER_POT_LEGACY_ACACIA_SAPLING   = new FlowerPot("LEGACY_ACACIA_SAPLING", 0xC);
    @Deprecated
    public static final FlowerPot FLOWER_POT_LEGACY_DARK_OAK_SAPLING = new FlowerPot("LEGACY_DARK_OAK_SAPLING", 0xD);

    private static final Map<String, FlowerPot>    byName = new SimpleStringHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<FlowerPot> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    @SuppressWarnings("MagicNumber")
    protected FlowerPot()
    {
        super("FLOWER_POT", 140, "minecraft:flower_pot", "EMPTY", (byte) 0x00);
    }

    public FlowerPot(final String enumName, final int type)
    {
        super(FLOWER_POT_EMPTY.name(), FLOWER_POT_EMPTY.getId(), FLOWER_POT_EMPTY.getMinecraftId(), enumName, (byte) type);
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
    public FlowerPot getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public FlowerPot getType(final int id)
    {
        return getByID(id);
    }

    /**
     * Returns one of FlowerPot sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of FlowerPot or null
     */
    public static FlowerPot getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of FlowerPot sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of FlowerPot or null
     */
    public static FlowerPot getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final FlowerPot element)
    {
        byID.put(element.getType(), element);
        byName.put(element.name(), element);
    }

    static
    {
        FlowerPot.register(FLOWER_POT_EMPTY);
        FlowerPot.register(FLOWER_POT_LEGACY_POPPY);
        FlowerPot.register(FLOWER_POT_LEGACY_DANDELION);
        FlowerPot.register(FLOWER_POT_LEGACY_OAK_SAPLING);
        FlowerPot.register(FLOWER_POT_LEGACY_SPRUCE_SAPLING);
        FlowerPot.register(FLOWER_POT_LEGACY_BIRCH_SAPLING);
        FlowerPot.register(FLOWER_POT_LEGACY_JUNGLE_SAPLING);
        FlowerPot.register(FLOWER_POT_LEGACY_RED_MUSHROOM);
        FlowerPot.register(FLOWER_POT_LEGACY_BROWN_MUSHROOM);
        FlowerPot.register(FLOWER_POT_LEGACY_CACTUS);
        FlowerPot.register(FLOWER_POT_LEGACY_DEAD_BUSH);
        FlowerPot.register(FLOWER_POT_LEGACY_FERN);
        FlowerPot.register(FLOWER_POT_LEGACY_ACACIA_SAPLING);
        FlowerPot.register(FLOWER_POT_LEGACY_DARK_OAK_SAPLING);
    }
}
