package org.diorite.material.blocks.plants;

import java.util.Map;

import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.utils.collections.SimpleStringHashMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

public class NetherWartBlock extends Plant
{
    // TODO: auto-generated class, implement other types (sub-ids).	
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final byte  USED_DATA_VALUES = 1;	
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

    public static final NetherWartBlock NETHER_WART_BLOCK = new NetherWartBlock();

    private static final Map<String, NetherWartBlock>    byName = new SimpleStringHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<NetherWartBlock> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    @SuppressWarnings("MagicNumber")
    protected NetherWartBlock()
    {
        super("NETHER_WART_BLOCK", 115, "minecraft:nether_wart", "NETHER_WART_BLOCK", (byte) 0x00);
    }

    public NetherWartBlock(final String enumName, final int type)
    {
        super(NETHER_WART_BLOCK.name(), NETHER_WART_BLOCK.getId(), NETHER_WART_BLOCK.getMinecraftId(), enumName, (byte) type);
    }

    public NetherWartBlock(final int maxStack, final String typeName, final byte type)
    {
        super(NETHER_WART_BLOCK.name(), NETHER_WART_BLOCK.getId(), NETHER_WART_BLOCK.getMinecraftId(), maxStack, typeName, type);
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
    public NetherWartBlock getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public NetherWartBlock getType(final int id)
    {
        return getByID(id);
    }

    public static NetherWartBlock getByID(final int id)
    {
        return byID.get((byte) id);
    }

    public static NetherWartBlock getByEnumName(final String name)
    {
        return byName.get(name);
    }

    public static void register(final NetherWartBlock element)
    {
        byID.put(element.getType(), element);
        byName.put(element.name(), element);
    }

    static
    {
        NetherWartBlock.register(NETHER_WART_BLOCK);
    }
}
