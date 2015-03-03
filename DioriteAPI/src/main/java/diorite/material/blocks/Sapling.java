package diorite.material.blocks;

import java.util.Map;

import diorite.material.BlockMaterialData;
import diorite.utils.collections.SimpleStringHashMap;
import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

public class Sapling extends BlockMaterialData
{
    public static final Sapling SAPLING_OAK      = new Sapling();
    public static final Sapling SAPLING_SPRUCE   = new Sapling("SPRUCE", 0x01);
    public static final Sapling SAPLING_BIRCH    = new Sapling("BIRCH", 0x02);
    public static final Sapling SAPLING_JUNGLE   = new Sapling("JUNGLE", 0x03);
    public static final Sapling SAPLING_ACACIA   = new Sapling("ACACIA", 0x04);
    public static final Sapling SAPLING_DARK_OAK = new Sapling("DARK_OAK", 0x05);
    // TODO: how to implement other sapling types? (Stage 1)

    private static final Map<String, Sapling>    byName = new SimpleStringHashMap<>(6, .1f);
    private static final TByteObjectMap<Sapling> byID   = new TByteObjectHashMap<>(6, .1f);

    protected Sapling()
    {
        super("SAPLING", 5, "OAK", (byte) 0x00);
    }

    public Sapling(final String enumName, final int type)
    {
        super(SAPLING_OAK.name(), SAPLING_OAK.getId(), enumName, (byte) type);
    }

    public Sapling(final int maxStack, final int durability, final String typeName, final byte type)
    {
        super(SAPLING_OAK.name(), SAPLING_OAK.getId(), maxStack, durability, typeName, type);
    }

    @Override
    public BlockMaterialData getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public BlockMaterialData getType(final int id)
    {
        return getByID(id);
    }

    public static Sapling getByID(final int id)
    {
        return byID.get((byte) id);
    }

    public static Sapling getByEnumName(final String name)
    {
        return byName.get(name);
    }

    public static void register(final Sapling element)
    {
        byID.put((byte) element.getId(), element);
        byName.put(element.name(), element);
    }

    static
    {
        Sapling.register(SAPLING_OAK);
        Sapling.register(SAPLING_SPRUCE);
        Sapling.register(SAPLING_BIRCH);
        Sapling.register(SAPLING_JUNGLE);
        Sapling.register(SAPLING_ACACIA);
        Sapling.register(SAPLING_DARK_OAK);
    }
}
