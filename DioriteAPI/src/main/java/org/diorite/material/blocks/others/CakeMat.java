package org.diorite.material.blocks.others;

import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.material.BlockMaterialData;
import org.diorite.material.blocks.others.MushroomBlockMat.Type;
import org.diorite.utils.collections.maps.CaseInsensitiveMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing block "Cake" and all its subtypes.
 */
public class CakeMat extends BlockMaterialData
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final byte USED_DATA_VALUES = 6;

    public static final CakeMat CAKE_0 = new CakeMat();
    public static final CakeMat CAKE_1 = new CakeMat(0x1);
    public static final CakeMat CAKE_2 = new CakeMat(0x2);
    public static final CakeMat CAKE_3 = new CakeMat(0x3);
    public static final CakeMat CAKE_4 = new CakeMat(0x4);
    public static final CakeMat CAKE_5 = new CakeMat(0x5);
    public static final CakeMat CAKE_6 = new CakeMat(0x6);

    private static final Map<String, CakeMat>    byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<CakeMat> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    protected final byte piecesEaten;

    @SuppressWarnings("MagicNumber")
    protected CakeMat()
    {
        super("CAKE", 92, "minecraft:cake", 1, "0", (byte) 0x00, 0.5f, 2.5f);
        this.piecesEaten = 0x0;
    }

    protected CakeMat(final int piecesEaten)
    {
        super(CAKE_0.name(), CAKE_0.ordinal(), CAKE_0.getMinecraftId(), Integer.toString(piecesEaten), (byte) piecesEaten, CAKE_0.getHardness(), CAKE_0.getBlastResistance());
        this.piecesEaten = (byte) piecesEaten;
    }

    protected CakeMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type, final byte piecesEaten, final float hardness, final float blastResistance)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, hardness, blastResistance);
        this.piecesEaten = piecesEaten;
    }

    @Override
    public CakeMat getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public CakeMat getType(final int id)
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
    public CakeMat getPiecesEaten(final byte piecesEaten)
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
    public static CakeMat getByID(final int id)
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
    public static CakeMat getByEnumName(final String name)
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
    public static CakeMat getCake(final Type type)
    {
        final CakeMat cake = getByID(type.getFlag());
        if (cake == null)
        {
            return CAKE_0;
        }
        return cake;
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final CakeMat element)
    {
        byID.put((byte) element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    @Override
    public CakeMat[] types()
    {
        return CakeMat.cakeTypes();
    }

    /**
     * @return array that contains all sub-types of this block.
     */
    public static CakeMat[] cakeTypes()
    {
        return byID.values(new CakeMat[byID.size()]);
    }

    static
    {
        CakeMat.register(CAKE_0);
        CakeMat.register(CAKE_1);
        CakeMat.register(CAKE_2);
        CakeMat.register(CAKE_3);
        CakeMat.register(CAKE_4);
        CakeMat.register(CAKE_5);
        CakeMat.register(CAKE_6);
    }
}
