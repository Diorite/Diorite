package org.diorite.material.blocks.plants;

import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.utils.collections.maps.CaseInsensitiveMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing block "NetherWartBlock" and all its subtypes.
 */
public class NetherWartBlockMat extends CropsMat
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final byte  USED_DATA_VALUES = 4;
    /**
     * Blast resistance of block, can be changed only before server start.
     * Final copy of blast resistance from {@link MagicNumbers} class.
     */
    public static final float BLAST_RESISTANCE = MagicNumbers.MATERIAL__NETHER_WART_BLOCK__BLAST_RESISTANCE;
    /**
     * Hardness of block, can be changed only before server start.
     * Final copy of hardness from {@link MagicNumbers} class.
     */
    public static final float HARDNESS         = MagicNumbers.MATERIAL__NETHER_WART_BLOCK__HARDNESS;

    public static final NetherWartBlockMat NETHER_WART_BLOCK_0    = new NetherWartBlockMat();
    public static final NetherWartBlockMat NETHER_WART_BLOCK_1    = new NetherWartBlockMat("1", 0x1);
    public static final NetherWartBlockMat NETHER_WART_BLOCK_2    = new NetherWartBlockMat("2", 0x2);
    public static final NetherWartBlockMat NETHER_WART_BLOCK_RIPE = new NetherWartBlockMat("RIPE", 0x3);

    private static final Map<String, NetherWartBlockMat>    byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<NetherWartBlockMat> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    protected final int age;

    @SuppressWarnings("MagicNumber")
    protected NetherWartBlockMat()
    {
        super("NETHER_WART_BLOCK", 115, "minecraft:nether_wart", "0", (byte) 0x00);
        this.age = 0;
    }

    protected NetherWartBlockMat(final String enumName, final int age)
    {
        super(NETHER_WART_BLOCK_0.name(), NETHER_WART_BLOCK_0.getId(), NETHER_WART_BLOCK_0.getMinecraftId(), enumName, (byte) age);
        this.age = age;
    }

    protected NetherWartBlockMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type, final int age)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type);
        this.age = age;
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
    public int getAge()
    {
        return this.age;
    }

    @Override
    public NetherWartBlockMat getAge(final int age)
    {
        return getByID(age);
    }

    @Override
    public NetherWartBlockMat getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public NetherWartBlockMat getType(final int id)
    {
        return getByID(id);
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("age", this.age).toString();
    }

    /**
     * Returns one of NetherWartBlock sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of NetherWartBlock or null
     */
    public static NetherWartBlockMat getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of NetherWartBlock sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of NetherWartBlock or null
     */
    public static NetherWartBlockMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Returns one of NetherWartBlock sub-type based on age.
     * It will never return null.
     *
     * @param age age of NetherWartBlock.
     *
     * @return sub-type of NetherWartBlock
     */
    public static NetherWartBlockMat getNetherWartBlock(final int age)
    {
        final NetherWartBlockMat netherWartBlock = getByID(age);
        if (netherWartBlock == null)
        {
            return NETHER_WART_BLOCK_0;
        }
        return netherWartBlock;
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final NetherWartBlockMat element)
    {
        byID.put((byte) element.getType(), element);
        byName.put(element.name(), element);
    }

    @Override
    public NetherWartBlockMat[] types()
    {
        return NetherWartBlockMat.netherWartBlockTypes();
    }

    /**
     * @return array that contains all sub-types of this block.
     */
    public static NetherWartBlockMat[] netherWartBlockTypes()
    {
        return byID.values(new NetherWartBlockMat[byID.size()]);
    }

    static
    {
        NetherWartBlockMat.register(NETHER_WART_BLOCK_0);
        NetherWartBlockMat.register(NETHER_WART_BLOCK_1);
        NetherWartBlockMat.register(NETHER_WART_BLOCK_2);
        NetherWartBlockMat.register(NETHER_WART_BLOCK_RIPE);
    }
}
