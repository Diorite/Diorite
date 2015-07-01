package org.diorite.material.blocks.others;

import java.util.Map;

import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.material.BlockMaterialData;
import org.diorite.utils.collections.maps.CaseInsensitiveMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing block "FlowerPot" and all its subtypes.
 */
@SuppressWarnings({"MagicNumber", "deprecation"})
public class FlowerPotMat extends BlockMaterialData
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

    public static final FlowerPotMat FLOWER_POT_EMPTY                   = new FlowerPotMat();
    @Deprecated
    public static final FlowerPotMat FLOWER_POT_LEGACY_POPPY            = new FlowerPotMat("LEGACY_POPPY", 0x1);
    @Deprecated
    public static final FlowerPotMat FLOWER_POT_LEGACY_DANDELION        = new FlowerPotMat("LEGACY_DANDELION", 0x2);
    @Deprecated
    public static final FlowerPotMat FLOWER_POT_LEGACY_OAK_SAPLING      = new FlowerPotMat("LEGACY_OAK_SAPLING", 0x3);
    @Deprecated
    public static final FlowerPotMat FLOWER_POT_LEGACY_SPRUCE_SAPLING   = new FlowerPotMat("LEGACY_SPRUCE_SAPLING", 0x4);
    @Deprecated
    public static final FlowerPotMat FLOWER_POT_LEGACY_BIRCH_SAPLING    = new FlowerPotMat("LEGACY_BIRCH_SAPLING", 0x5);
    @Deprecated
    public static final FlowerPotMat FLOWER_POT_LEGACY_JUNGLE_SAPLING   = new FlowerPotMat("LEGACY_JUNGLE_SAPLING", 0x6);
    @Deprecated
    public static final FlowerPotMat FLOWER_POT_LEGACY_RED_MUSHROOM     = new FlowerPotMat("LEGACY_RED_MUSHROOM", 0x7);
    @Deprecated
    public static final FlowerPotMat FLOWER_POT_LEGACY_BROWN_MUSHROOM   = new FlowerPotMat("LEGACY_BROWN_MUSHROOM", 0x8);
    @Deprecated
    public static final FlowerPotMat FLOWER_POT_LEGACY_CACTUS           = new FlowerPotMat("LEGACY_CACTUS", 0x9);
    @Deprecated
    public static final FlowerPotMat FLOWER_POT_LEGACY_DEAD_BUSH        = new FlowerPotMat("LEGACY_DEAD_BUSH", 0xA);
    @Deprecated
    public static final FlowerPotMat FLOWER_POT_LEGACY_FERN             = new FlowerPotMat("LEGACY_FERN", 0xB);
    @Deprecated
    public static final FlowerPotMat FLOWER_POT_LEGACY_ACACIA_SAPLING   = new FlowerPotMat("LEGACY_ACACIA_SAPLING", 0xC);
    @Deprecated
    public static final FlowerPotMat FLOWER_POT_LEGACY_DARK_OAK_SAPLING = new FlowerPotMat("LEGACY_DARK_OAK_SAPLING", 0xD);

    private static final Map<String, FlowerPotMat>    byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<FlowerPotMat> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    @SuppressWarnings("MagicNumber")
    protected FlowerPotMat()
    {
        super("FLOWER_POT", 140, "minecraft:flower_pot", "EMPTY", (byte) 0x00);
    }

    protected FlowerPotMat(final String enumName, final int type)
    {
        super(FLOWER_POT_EMPTY.name(), FLOWER_POT_EMPTY.ordinal(), FLOWER_POT_EMPTY.getMinecraftId(), enumName, (byte) type);
    }

    protected FlowerPotMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type);
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
    public FlowerPotMat getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public FlowerPotMat getType(final int id)
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
    public static FlowerPotMat getByID(final int id)
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
    public static FlowerPotMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final FlowerPotMat element)
    {
        byID.put((byte) element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    @Override
    public FlowerPotMat[] types()
    {
        return FlowerPotMat.flowerPotTypes();
    }

    /**
     * @return array that contains all sub-types of this block.
     */
    public static FlowerPotMat[] flowerPotTypes()
    {
        return byID.values(new FlowerPotMat[byID.size()]);
    }

    static
    {
        FlowerPotMat.register(FLOWER_POT_EMPTY);
        FlowerPotMat.register(FLOWER_POT_LEGACY_POPPY);
        FlowerPotMat.register(FLOWER_POT_LEGACY_DANDELION);
        FlowerPotMat.register(FLOWER_POT_LEGACY_OAK_SAPLING);
        FlowerPotMat.register(FLOWER_POT_LEGACY_SPRUCE_SAPLING);
        FlowerPotMat.register(FLOWER_POT_LEGACY_BIRCH_SAPLING);
        FlowerPotMat.register(FLOWER_POT_LEGACY_JUNGLE_SAPLING);
        FlowerPotMat.register(FLOWER_POT_LEGACY_RED_MUSHROOM);
        FlowerPotMat.register(FLOWER_POT_LEGACY_BROWN_MUSHROOM);
        FlowerPotMat.register(FLOWER_POT_LEGACY_CACTUS);
        FlowerPotMat.register(FLOWER_POT_LEGACY_DEAD_BUSH);
        FlowerPotMat.register(FLOWER_POT_LEGACY_FERN);
        FlowerPotMat.register(FLOWER_POT_LEGACY_ACACIA_SAPLING);
        FlowerPotMat.register(FLOWER_POT_LEGACY_DARK_OAK_SAPLING);
    }
}
