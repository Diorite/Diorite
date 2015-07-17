package org.diorite.material.blocks.others;

import java.util.Map;

import org.diorite.material.BlockMaterialData;
import org.diorite.utils.collections.maps.CaseInsensitiveMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing block "FlowerPot" and all its subtypes.
 */
@SuppressWarnings({"MagicNumber", "deprecation"})
public class FlowerPotBlockMat extends BlockMaterialData
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final byte USED_DATA_VALUES = 14;

    public static final FlowerPotBlockMat FLOWER_POT_BLOCK_EMPTY             = new FlowerPotBlockMat();
    @Deprecated
    public static final FlowerPotBlockMat FLOWER_POT_BLOCK_LEGACY_POPPY            = new FlowerPotBlockMat("LEGACY_POPPY", 0x1);
    @Deprecated
    public static final FlowerPotBlockMat FLOWER_POT_BLOCK_LEGACY_DANDELION        = new FlowerPotBlockMat("LEGACY_DANDELION", 0x2);
    @Deprecated
    public static final FlowerPotBlockMat FLOWER_POT_BLOCK_LEGACY_OAK_SAPLING      = new FlowerPotBlockMat("LEGACY_OAK_SAPLING", 0x3);
    @Deprecated
    public static final FlowerPotBlockMat FLOWER_POT_BLOCK_LEGACY_SPRUCE_SAPLING   = new FlowerPotBlockMat("LEGACY_SPRUCE_SAPLING", 0x4);
    @Deprecated
    public static final FlowerPotBlockMat FLOWER_POT_BLOCK_LEGACY_BIRCH_SAPLING    = new FlowerPotBlockMat("LEGACY_BIRCH_SAPLING", 0x5);
    @Deprecated
    public static final FlowerPotBlockMat FLOWER_POT_BLOCK_LEGACY_JUNGLE_SAPLING   = new FlowerPotBlockMat("LEGACY_JUNGLE_SAPLING", 0x6);
    @Deprecated
    public static final FlowerPotBlockMat FLOWER_POT_BLOCK_LEGACY_RED_MUSHROOM     = new FlowerPotBlockMat("LEGACY_RED_MUSHROOM", 0x7);
    @Deprecated
    public static final FlowerPotBlockMat FLOWER_POT_BLOCK_LEGACY_BROWN_MUSHROOM   = new FlowerPotBlockMat("LEGACY_BROWN_MUSHROOM", 0x8);
    @Deprecated
    public static final FlowerPotBlockMat FLOWER_POT_BLOCK_LEGACY_CACTUS           = new FlowerPotBlockMat("LEGACY_CACTUS", 0x9);
    @Deprecated
    public static final FlowerPotBlockMat FLOWER_POT_BLOCK_LEGACY_DEAD_BUSH        = new FlowerPotBlockMat("LEGACY_DEAD_BUSH", 0xA);
    @Deprecated
    public static final FlowerPotBlockMat FLOWER_POT_BLOCK_LEGACY_FERN             = new FlowerPotBlockMat("LEGACY_FERN", 0xB);
    @Deprecated
    public static final FlowerPotBlockMat FLOWER_POT_BLOCK_LEGACY_ACACIA_SAPLING   = new FlowerPotBlockMat("LEGACY_ACACIA_SAPLING", 0xC);
    @Deprecated
    public static final FlowerPotBlockMat FLOWER_POT_BLOCK_LEGACY_DARK_OAK_SAPLING = new FlowerPotBlockMat("LEGACY_DARK_OAK_SAPLING", 0xD);

    private static final Map<String, FlowerPotBlockMat>    byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<FlowerPotBlockMat> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    @SuppressWarnings("MagicNumber")
    protected FlowerPotBlockMat()
    {
        super("FLOWER_POT_BLOCK", 140, "minecraft:flower_pot", "EMPTY", (byte) 0x00, 0, 0);
    }

    protected FlowerPotBlockMat(final String enumName, final int type)
    {
        super(FLOWER_POT_BLOCK_EMPTY.name(), FLOWER_POT_BLOCK_EMPTY.ordinal(), FLOWER_POT_BLOCK_EMPTY.getMinecraftId(), enumName, (byte) type, FLOWER_POT_BLOCK_EMPTY.getHardness(), FLOWER_POT_BLOCK_EMPTY.getBlastResistance());
    }

    protected FlowerPotBlockMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type, final float hardness, final float blastResistance)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, hardness, blastResistance);
    }

    @Override
    public FlowerPotBlockMat getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public FlowerPotBlockMat getType(final int id)
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
    public static FlowerPotBlockMat getByID(final int id)
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
    public static FlowerPotBlockMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final FlowerPotBlockMat element)
    {
        byID.put((byte) element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    @Override
    public FlowerPotBlockMat[] types()
    {
        return FlowerPotBlockMat.flowerPotTypes();
    }

    /**
     * @return array that contains all sub-types of this block.
     */
    public static FlowerPotBlockMat[] flowerPotTypes()
    {
        return byID.values(new FlowerPotBlockMat[byID.size()]);
    }

    static
    {
        FlowerPotBlockMat.register(FLOWER_POT_BLOCK_EMPTY);
        FlowerPotBlockMat.register(FLOWER_POT_BLOCK_LEGACY_POPPY);
        FlowerPotBlockMat.register(FLOWER_POT_BLOCK_LEGACY_DANDELION);
        FlowerPotBlockMat.register(FLOWER_POT_BLOCK_LEGACY_OAK_SAPLING);
        FlowerPotBlockMat.register(FLOWER_POT_BLOCK_LEGACY_SPRUCE_SAPLING);
        FlowerPotBlockMat.register(FLOWER_POT_BLOCK_LEGACY_BIRCH_SAPLING);
        FlowerPotBlockMat.register(FLOWER_POT_BLOCK_LEGACY_JUNGLE_SAPLING);
        FlowerPotBlockMat.register(FLOWER_POT_BLOCK_LEGACY_RED_MUSHROOM);
        FlowerPotBlockMat.register(FLOWER_POT_BLOCK_LEGACY_BROWN_MUSHROOM);
        FlowerPotBlockMat.register(FLOWER_POT_BLOCK_LEGACY_CACTUS);
        FlowerPotBlockMat.register(FLOWER_POT_BLOCK_LEGACY_DEAD_BUSH);
        FlowerPotBlockMat.register(FLOWER_POT_BLOCK_LEGACY_FERN);
        FlowerPotBlockMat.register(FLOWER_POT_BLOCK_LEGACY_ACACIA_SAPLING);
        FlowerPotBlockMat.register(FLOWER_POT_BLOCK_LEGACY_DARK_OAK_SAPLING);
    }
}
