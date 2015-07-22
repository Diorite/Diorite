package org.diorite.material.blocks.plants;

import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.material.Material;
import org.diorite.utils.collections.maps.CaseInsensitiveMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing block "WheatBlock" and all its subtypes.
 * <p>
 * NOTE: Will crash game when in inventory.
 */
public class WheatBlockMat extends CropsMat
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final int USED_DATA_VALUES = 8;

    public static final WheatBlockMat WHEAT_BLOCK_0    = new WheatBlockMat();
    public static final WheatBlockMat WHEAT_BLOCK_1    = new WheatBlockMat("1", 0x1);
    public static final WheatBlockMat WHEAT_BLOCK_2    = new WheatBlockMat("2", 0x2);
    public static final WheatBlockMat WHEAT_BLOCK_3    = new WheatBlockMat("3", 0x3);
    public static final WheatBlockMat WHEAT_BLOCK_4    = new WheatBlockMat("4", 0x4);
    public static final WheatBlockMat WHEAT_BLOCK_5    = new WheatBlockMat("5", 0x5);
    public static final WheatBlockMat WHEAT_BLOCK_6    = new WheatBlockMat("6", 0x6);
    public static final WheatBlockMat WHEAT_BLOCK_RIPE = new WheatBlockMat("RIPE", 0x7);

    private static final Map<String, WheatBlockMat>    byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<WheatBlockMat> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR, Byte.MIN_VALUE);

    protected final int age;

    @SuppressWarnings("MagicNumber")
    protected WheatBlockMat()
    {
        super("WHEAT_BLOCK", 59, "minecraft:wheat", "0", (byte) 0x00, 0, 0);
        this.age = 0;
    }

    protected WheatBlockMat(final String enumName, final int age)
    {
        super(WHEAT_BLOCK_0.name(), WHEAT_BLOCK_0.ordinal(), WHEAT_BLOCK_0.getMinecraftId(), enumName, (byte) age, WHEAT_BLOCK_0.getHardness(), WHEAT_BLOCK_0.getBlastResistance());
        this.age = age;
    }

    protected WheatBlockMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type, final int age, final float hardness, final float blastResistance)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, hardness, blastResistance);
        this.age = age;
    }

    @Override
    public Material ensureValidInventoryItem()
    {
        return Material.WHEAT_SEEDS;
    }

    @Override
    public int getAge()
    {
        return this.age;
    }

    @Override
    public WheatBlockMat getAge(final int age)
    {
        return getByID(age);
    }

    @Override
    public WheatBlockMat getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public WheatBlockMat getType(final int id)
    {
        return getByID(id);
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("age", this.age).toString();
    }

    /**
     * Returns one of WheatBlock sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of WheatBlock or null
     */
    public static WheatBlockMat getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of WheatBlock sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of WheatBlock or null
     */
    public static WheatBlockMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Returns one of WheatBlock sub-type based on age.
     * It will never return null.
     *
     * @param age age of WheatBlock.
     *
     * @return sub-type of WheatBlock
     */
    public static WheatBlockMat getWheatBlock(final int age)
    {
        final WheatBlockMat wheatBlock = getByID(age);
        if (wheatBlock == null)
        {
            return WHEAT_BLOCK_0;
        }
        return wheatBlock;
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final WheatBlockMat element)
    {
        byID.put((byte) element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    @Override
    public WheatBlockMat[] types()
    {
        return WheatBlockMat.wheatBlockTypes();
    }

    /**
     * @return array that contains all sub-types of this block.
     */
    public static WheatBlockMat[] wheatBlockTypes()
    {
        return byID.values(new WheatBlockMat[byID.size()]);
    }

    static
    {
        WheatBlockMat.register(WHEAT_BLOCK_0);
        WheatBlockMat.register(WHEAT_BLOCK_1);
        WheatBlockMat.register(WHEAT_BLOCK_2);
        WheatBlockMat.register(WHEAT_BLOCK_3);
        WheatBlockMat.register(WHEAT_BLOCK_4);
        WheatBlockMat.register(WHEAT_BLOCK_5);
        WheatBlockMat.register(WHEAT_BLOCK_6);
        WheatBlockMat.register(WHEAT_BLOCK_RIPE);
    }
}
