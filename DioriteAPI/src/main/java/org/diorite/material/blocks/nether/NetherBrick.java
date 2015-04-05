package org.diorite.material.blocks.nether;

import java.util.Map;

import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.material.BlockMaterialData;
import org.diorite.utils.collections.SimpleStringHashMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

public class NetherBrick extends BlockMaterialData
{
    public static final byte  USED_DATA_VALUES = 1;
    public static final float BLAST_RESISTANCE = MagicNumbers.MATERIAL__NETHER_BRICK__BLAST_RESISTANCE;
    public static final float HARDNESS         = MagicNumbers.MATERIAL__NETHER_BRICK__HARDNESS;

    public static final NetherBrick NETHER_BRICK = new NetherBrick();

    private static final Map<String, NetherBrick>    byName = new SimpleStringHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<NetherBrick> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    @SuppressWarnings("MagicNumber")
    protected NetherBrick()
    {
        super("NETHER_BRICK", 112, "minecraft:nether_brick", "NETHER_BRICK", (byte) 0x00);
    }

    public NetherBrick(final String enumName, final int type)
    {
        super(NETHER_BRICK.name(), NETHER_BRICK.getId(), NETHER_BRICK.getMinecraftId(), enumName, (byte) type);
    }

    public NetherBrick(final int maxStack, final String typeName, final byte type)
    {
        super(NETHER_BRICK.name(), NETHER_BRICK.getId(), NETHER_BRICK.getMinecraftId(), maxStack, typeName, type);
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
    public NetherBrick getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public NetherBrick getType(final int id)
    {
        return getByID(id);
    }

    public static NetherBrick getByID(final int id)
    {
        return byID.get((byte) id);
    }

    public static NetherBrick getByEnumName(final String name)
    {
        return byName.get(name);
    }

    public static void register(final NetherBrick element)
    {
        byID.put(element.getType(), element);
        byName.put(element.name(), element);
    }

    static
    {
        NetherBrick.register(NETHER_BRICK);
    }
}