package org.diorite.material.blocks.others;

import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.material.BlockMaterialData;
import org.diorite.material.Material;
import org.diorite.material.blocks.others.MushroomBlockMat.Type;
import org.diorite.utils.collections.maps.CaseInsensitiveMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing block "Cake" and all its subtypes.
 * <br>
 * NOTE: Will crash game when in inventory.
 */
public class CakeBlockMat extends BlockMaterialData
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final int USED_DATA_VALUES = 6;

    public static final CakeBlockMat CAKE_BLOCK_0 = new CakeBlockMat();
    public static final CakeBlockMat CAKE_BLOCK_1 = new CakeBlockMat(0x1);
    public static final CakeBlockMat CAKE_BLOCK_2 = new CakeBlockMat(0x2);
    public static final CakeBlockMat CAKE_BLOCK_3 = new CakeBlockMat(0x3);
    public static final CakeBlockMat CAKE_BLOCK_4 = new CakeBlockMat(0x4);
    public static final CakeBlockMat CAKE_BLOCK_5 = new CakeBlockMat(0x5);
    public static final CakeBlockMat CAKE_BLOCK_6 = new CakeBlockMat(0x6);

    private static final Map<String, CakeBlockMat>    byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<CakeBlockMat> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR, Byte.MIN_VALUE);

    protected final byte piecesEaten;

    @SuppressWarnings("MagicNumber")
    protected CakeBlockMat()
    {
        super("CAKE_BLOCK", 92, "minecraft:cake", 1, "0", (byte) 0x00, 0.5f, 2.5f);
        this.piecesEaten = 0x0;
    }

    protected CakeBlockMat(final int piecesEaten)
    {
        super(CAKE_BLOCK_0.name(), CAKE_BLOCK_0.ordinal(), CAKE_BLOCK_0.getMinecraftId(), CAKE_BLOCK_0.getMaxStack(), Integer.toString(piecesEaten), (byte) piecesEaten, CAKE_BLOCK_0.getHardness(), CAKE_BLOCK_0.getBlastResistance());
        this.piecesEaten = (byte) piecesEaten;
    }

    protected CakeBlockMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type, final byte piecesEaten, final float hardness, final float blastResistance)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, hardness, blastResistance);
        this.piecesEaten = piecesEaten;
    }

    @Override
    public Material ensureValidInventoryItem()
    {
        return Material.CAKE;
    }

    @Override
    public CakeBlockMat getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public CakeBlockMat getType(final int id)
    {
        return getByID(id);
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("piecesEaten", this.piecesEaten).toString();
    }

    /**
     * For vanilla cake blocks should return values from 0 to 6.
     *
     * @return amount of eated pieces of cake {@literal <3}
     */
    public byte getPiecesEaten()
    {
        return this.piecesEaten;
    }

    /**
     * Return cake with selected amount of eaten pieces.
     * Vanilla server will return null for all values above 6.
     *
     * @param piecesEaten amount of eated pieces of cake.
     *
     * @return cake with selected amount of eaten pieces or null.
     */
    public CakeBlockMat getPiecesEaten(final byte piecesEaten)
    {
        return getByID(piecesEaten);
    }

    /**
     * Returns one of Cake sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of Cake or null
     */
    public static CakeBlockMat getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of Cake sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of Cake or null
     */
    public static CakeBlockMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Returns one of Cake sub-type based on amount of eaten pieces.
     * It will never return null. (full cake if number is out of range)
     *
     * @param type amount of eaten pieces.
     *
     * @return sub-type of Cake
     */
    public static CakeBlockMat getCake(final Type type)
    {
        final CakeBlockMat cake = getByID(type.getFlag());
        if (cake == null)
        {
            return CAKE_BLOCK_0;
        }
        return cake;
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final CakeBlockMat element)
    {
        byID.put((byte) element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    @Override
    public CakeBlockMat[] types()
    {
        return CakeBlockMat.cakeTypes();
    }

    /**
     * @return array that contains all sub-types of this block.
     */
    public static CakeBlockMat[] cakeTypes()
    {
        return byID.values(new CakeBlockMat[byID.size()]);
    }

    static
    {
        CakeBlockMat.register(CAKE_BLOCK_0);
        CakeBlockMat.register(CAKE_BLOCK_1);
        CakeBlockMat.register(CAKE_BLOCK_2);
        CakeBlockMat.register(CAKE_BLOCK_3);
        CakeBlockMat.register(CAKE_BLOCK_4);
        CakeBlockMat.register(CAKE_BLOCK_5);
        CakeBlockMat.register(CAKE_BLOCK_6);
    }
}
