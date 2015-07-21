package org.diorite.material.blocks.plants;

import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.material.Material;
import org.diorite.utils.collections.maps.CaseInsensitiveMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing block "PotatoesBlock" and all its subtypes.
 * <p>
 * NOTE: Will crash game when in inventory.
 */
public class PotatoesBlockMat extends CropsMat
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final int USED_DATA_VALUES = 8;

    public static final PotatoesBlockMat POTATOES_BLOCK_0    = new PotatoesBlockMat();
    public static final PotatoesBlockMat POTATOES_BLOCK_1    = new PotatoesBlockMat("1", 0x1);
    public static final PotatoesBlockMat POTATOES_BLOCK_2    = new PotatoesBlockMat("2", 0x2);
    public static final PotatoesBlockMat POTATOES_BLOCK_3    = new PotatoesBlockMat("3", 0x3);
    public static final PotatoesBlockMat POTATOES_BLOCK_4    = new PotatoesBlockMat("4", 0x4);
    public static final PotatoesBlockMat POTATOES_BLOCK_5    = new PotatoesBlockMat("5", 0x5);
    public static final PotatoesBlockMat POTATOES_BLOCK_6    = new PotatoesBlockMat("6", 0x6);
    public static final PotatoesBlockMat POTATOES_BLOCK_RIPE = new PotatoesBlockMat("RIPE", 0x7);

    private static final Map<String, PotatoesBlockMat>    byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<PotatoesBlockMat> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    protected final int age;

    @SuppressWarnings("MagicNumber")
    protected PotatoesBlockMat()
    {
        super("POTATOES_BLOCK", 142, "minecraft:potatoes", "0", (byte) 0x00, 0, 0);
        this.age = 0;
    }

    protected PotatoesBlockMat(final String enumName, final int age)
    {
        super(POTATOES_BLOCK_0.name(), POTATOES_BLOCK_0.ordinal(), POTATOES_BLOCK_0.getMinecraftId(), enumName, (byte) age, POTATOES_BLOCK_0.getHardness(), POTATOES_BLOCK_0.getBlastResistance());
        this.age = age;
    }

    protected PotatoesBlockMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type, final int age, final float hardness, final float blastResistance)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, hardness, blastResistance);
        this.age = age;
    }

    @Override
    public Material ensureValidInventoryItem()
    {
        return Material.POTATO;
    }

    @Override
    public int getAge()
    {
        return this.age;
    }

    @Override
    public PotatoesBlockMat getAge(final int age)
    {
        return getByID(age);
    }

    @Override
    public PotatoesBlockMat getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public PotatoesBlockMat getType(final int id)
    {
        return getByID(id);
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("age", this.age).toString();
    }

    /**
     * Returns one of PotatoesBlock sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of PotatoesBlock or null
     */
    public static PotatoesBlockMat getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of PotatoesBlock sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of PotatoesBlock or null
     */
    public static PotatoesBlockMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Returns one of PotatoesBlock sub-type based on age.
     * It will never return null.
     *
     * @param age age of PotatoesBlock.
     *
     * @return sub-type of PotatoesBlock
     */
    public static PotatoesBlockMat getPotatoesBlock(final int age)
    {
        final PotatoesBlockMat potatoesBlock = getByID(age);
        if (potatoesBlock == null)
        {
            return POTATOES_BLOCK_0;
        }
        return potatoesBlock;
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final PotatoesBlockMat element)
    {
        byID.put((byte) element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    @Override
    public PotatoesBlockMat[] types()
    {
        return PotatoesBlockMat.potatoesBlockTypes();
    }

    /**
     * @return array that contains all sub-types of this block.
     */
    public static PotatoesBlockMat[] potatoesBlockTypes()
    {
        return byID.values(new PotatoesBlockMat[byID.size()]);
    }

    static
    {
        PotatoesBlockMat.register(POTATOES_BLOCK_0);
        PotatoesBlockMat.register(POTATOES_BLOCK_1);
        PotatoesBlockMat.register(POTATOES_BLOCK_2);
        PotatoesBlockMat.register(POTATOES_BLOCK_3);
        PotatoesBlockMat.register(POTATOES_BLOCK_4);
        PotatoesBlockMat.register(POTATOES_BLOCK_5);
        PotatoesBlockMat.register(POTATOES_BLOCK_6);
        PotatoesBlockMat.register(POTATOES_BLOCK_RIPE);
    }
}
