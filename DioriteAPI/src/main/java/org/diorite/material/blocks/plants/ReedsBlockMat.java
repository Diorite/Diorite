package org.diorite.material.blocks.plants;

import java.util.Map;

import org.diorite.material.Material;
import org.diorite.material.blocks.AgeableBlockMat;
import org.diorite.utils.collections.maps.CaseInsensitiveMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing block "Reeds" and all its subtypes.
 * <p>
 * NOTE: Will crash game when in inventory.
 */
@SuppressWarnings("MagicNumber")
public class ReedsBlockMat extends PlantMat implements AgeableBlockMat
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final byte USED_DATA_VALUES = 16;

    public static final ReedsBlockMat REEDS_BLOCK_0  = new ReedsBlockMat();
    public static final ReedsBlockMat REEDS_BLOCK_1  = new ReedsBlockMat(0x1);
    public static final ReedsBlockMat REEDS_BLOCK_2  = new ReedsBlockMat(0x2);
    public static final ReedsBlockMat REEDS_BLOCK_3  = new ReedsBlockMat(0x3);
    public static final ReedsBlockMat REEDS_BLOCK_4  = new ReedsBlockMat(0x4);
    public static final ReedsBlockMat REEDS_BLOCK_5  = new ReedsBlockMat(0x5);
    public static final ReedsBlockMat REEDS_BLOCK_6  = new ReedsBlockMat(0x6);
    public static final ReedsBlockMat REEDS_BLOCK_7  = new ReedsBlockMat(0x7);
    public static final ReedsBlockMat REEDS_BLOCK_8  = new ReedsBlockMat(0x8);
    public static final ReedsBlockMat REEDS_BLOCK_9  = new ReedsBlockMat(0x9);
    public static final ReedsBlockMat REEDS_BLOCK_10 = new ReedsBlockMat(0xA);
    public static final ReedsBlockMat REEDS_BLOCK_11 = new ReedsBlockMat(0xB);
    public static final ReedsBlockMat REEDS_BLOCK_12 = new ReedsBlockMat(0xC);
    public static final ReedsBlockMat REEDS_BLOCK_13 = new ReedsBlockMat(0xD);
    public static final ReedsBlockMat REEDS_BLOCK_14 = new ReedsBlockMat(0xE);
    public static final ReedsBlockMat REEDS_BLOCK_15 = new ReedsBlockMat(0xF);

    private static final Map<String, ReedsBlockMat>    byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<ReedsBlockMat> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    @SuppressWarnings("MagicNumber")
    protected ReedsBlockMat()
    {
        super("REEDS_BLOCK", 83, "minecraft:reeds", "0", (byte) 0x00, 0, 0);
    }

    protected ReedsBlockMat(final int age)
    {
        super(REEDS_BLOCK_0.name(), REEDS_BLOCK_0.ordinal(), REEDS_BLOCK_0.getMinecraftId(), Integer.toString(age), (byte) age, REEDS_BLOCK_0.getHardness(), REEDS_BLOCK_0.getBlastResistance());
    }

    protected ReedsBlockMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type, final float hardness, final float blastResistance)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, hardness, blastResistance);
    }

    @Override
    public Material ensureValidInventoryItem()
    {
        return Material.REEDS;
    }

    @Override
    public ReedsBlockMat getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public ReedsBlockMat getType(final int id)
    {
        return getByID(id);
    }

    @Override
    public int getAge()
    {
        return this.type;
    }

    @Override
    public ReedsBlockMat getAge(final int age)
    {
        return getByID(age);
    }

    /**
     * Returns one of Reeds sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of Reeds or null
     */
    public static ReedsBlockMat getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of Reeds sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of Reeds or null
     */
    public static ReedsBlockMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Returns one of Reeds sub-type based on age.
     * It will never return null.
     *
     * @param age age of Reeds.
     *
     * @return sub-type of Reeds
     */
    public static ReedsBlockMat getReeds(final int age)
    {
        final ReedsBlockMat reedsMat = getByID(age);
        if (reedsMat == null)
        {
            return REEDS_BLOCK_0;
        }
        return reedsMat;
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final ReedsBlockMat element)
    {
        byID.put((byte) element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    @Override
    public ReedsBlockMat[] types()
    {
        return ReedsBlockMat.reedsTypes();
    }

    /**
     * @return array that contains all sub-types of this block.
     */
    public static ReedsBlockMat[] reedsTypes()
    {
        return byID.values(new ReedsBlockMat[byID.size()]);
    }

    static
    {
        ReedsBlockMat.register(REEDS_BLOCK_0);
        ReedsBlockMat.register(REEDS_BLOCK_1);
        ReedsBlockMat.register(REEDS_BLOCK_2);
        ReedsBlockMat.register(REEDS_BLOCK_3);
        ReedsBlockMat.register(REEDS_BLOCK_4);
        ReedsBlockMat.register(REEDS_BLOCK_5);
        ReedsBlockMat.register(REEDS_BLOCK_6);
        ReedsBlockMat.register(REEDS_BLOCK_7);
        ReedsBlockMat.register(REEDS_BLOCK_8);
        ReedsBlockMat.register(REEDS_BLOCK_9);
        ReedsBlockMat.register(REEDS_BLOCK_10);
        ReedsBlockMat.register(REEDS_BLOCK_11);
        ReedsBlockMat.register(REEDS_BLOCK_12);
        ReedsBlockMat.register(REEDS_BLOCK_13);
        ReedsBlockMat.register(REEDS_BLOCK_14);
        ReedsBlockMat.register(REEDS_BLOCK_15);
    }
}
