package org.diorite.material.blocks.plants;

import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.utils.collections.SimpleStringHashMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing block "NetherWartBlock" and all its subtypes.
 */
public class NetherWartBlock extends Crops
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

    public static final NetherWartBlock NETHER_WART_BLOCK_0    = new NetherWartBlock();
    public static final NetherWartBlock NETHER_WART_BLOCK_1    = new NetherWartBlock("1", 0x1);
    public static final NetherWartBlock NETHER_WART_BLOCK_2    = new NetherWartBlock("2", 0x2);
    public static final NetherWartBlock NETHER_WART_BLOCK_RIPE = new NetherWartBlock("RIPE", 0x3);

    private static final Map<String, NetherWartBlock>    byName = new SimpleStringHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<NetherWartBlock> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    protected final int age;

    @SuppressWarnings("MagicNumber")
    protected NetherWartBlock()
    {
        super("NETHER_WART_BLOCK", 115, "minecraft:nether_wart", "0", (byte) 0x00);
        this.age = 0;
    }

    public NetherWartBlock(final String enumName, final int age)
    {
        super(NETHER_WART_BLOCK_0.name(), NETHER_WART_BLOCK_0.getId(), NETHER_WART_BLOCK_0.getMinecraftId(), enumName, (byte) age);
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
    public NetherWartBlock getAge(final int age)
    {
        return getByID(age);
    }

    @Override
    public NetherWartBlock getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public NetherWartBlock getType(final int id)
    {
        return getByID(id);
    }

    /**
     * Returns one of NetherWartBlock sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of NetherWartBlock or null
     */
    public static NetherWartBlock getByID(final int id)
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
    public static NetherWartBlock getByEnumName(final String name)
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
    public static NetherWartBlock getNetherWartBlock(final int age)
    {
        final NetherWartBlock netherWartBlock = getByID(age);
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
    public static void register(final NetherWartBlock element)
    {
        byID.put(element.getType(), element);
        byName.put(element.name(), element);
    }

    static
    {
        NetherWartBlock.register(NETHER_WART_BLOCK_0);
        NetherWartBlock.register(NETHER_WART_BLOCK_1);
        NetherWartBlock.register(NETHER_WART_BLOCK_2);
        NetherWartBlock.register(NETHER_WART_BLOCK_RIPE);
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("age", this.age).toString();
    }
}
