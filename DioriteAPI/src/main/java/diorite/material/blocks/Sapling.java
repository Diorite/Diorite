package diorite.material.blocks;

import java.util.Map;

import diorite.cfg.magic.MagicNumbers;
import diorite.material.BlockMaterialData;
import diorite.utils.collections.SimpleStringHashMap;
import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

public class Sapling extends BlockMaterialData
{
    public static final byte USED_DATA_VALUES = 12;
    public static final byte OLD_FLAG         = 0x08;

    public static final Sapling SAPLING_OAK            = new Sapling();
    public static final Sapling SAPLING_SPRUCE         = new Sapling("SPRUCE", 0x01);
    public static final Sapling SAPLING_BIRCH          = new Sapling("BIRCH", 0x02);
    public static final Sapling SAPLING_JUNGLE         = new Sapling("JUNGLE", 0x03);
    public static final Sapling SAPLING_ACACIA         = new Sapling("ACACIA", 0x04);
    public static final Sapling SAPLING_DARK_OAK       = new Sapling("DARK_OAK", 0x05);
    public static final Sapling SAPLING_QAK_OLDER      = new Sapling("QAK_OLDER", SAPLING_OAK.getType() | OLD_FLAG);
    public static final Sapling SAPLING_SPRUCE_OLDER   = new Sapling("SPRUCE_OLDER", SAPLING_SPRUCE.getType() | OLD_FLAG);
    public static final Sapling SAPLING_BIRCH_OLDER    = new Sapling("BIRCH_OLDER", SAPLING_BIRCH.getType() | OLD_FLAG);
    public static final Sapling SAPLING_JUNGLE_OLDER   = new Sapling("JUNGLE_OLDER", SAPLING_JUNGLE.getType() | OLD_FLAG);
    public static final Sapling SAPLING_ACACIA_OLDER   = new Sapling("ACACIA_OLDER", SAPLING_ACACIA.getType() | OLD_FLAG);
    public static final Sapling SAPLING_DARK_OAK_OLDER = new Sapling("DARK_OAK_OLDER", SAPLING_DARK_OAK.getType() | OLD_FLAG);

    private static final Map<String, Sapling>    byName = new SimpleStringHashMap<>(USED_DATA_VALUES, SLOW_GROW);
    private static final TByteObjectMap<Sapling> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SLOW_GROW);

    protected Sapling()
    {
        super("SAPLING", 5, "OAK", (byte) 0x00);
    }

    public Sapling(final String enumName, final int type)
    {
        super(SAPLING_OAK.name(), SAPLING_OAK.getId(), enumName, (byte) type);
    }

    public Sapling(final int maxStack, final String typeName, final byte type)
    {
        super(SAPLING_OAK.name(), SAPLING_OAK.getId(), maxStack, typeName, type);
    }

    public Sapling getOtherStage()
    {
        return Sapling.getByID(this.getId() ^ (1 << OLD_FLAG));
    }

    public Sapling normalize() // make sure that sappling is from first stage (NEW)
    {
        if (this.getStage() == SaplingStage.NEW)
        {
            return this;
        }
        return this.getOtherStage();
    }

    public SaplingStage getStage()
    {
        if ((this.type & OLD_FLAG) != 0)
        {
            return SaplingStage.OLDER;
        }
        return SaplingStage.NEW;
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

    @Override
    public boolean isSolid()
    {
        return false;
    }

    @Override
    public boolean isTransparent()
    {
        return true;
    }

    @Override
    public boolean isFlammable()
    {
        return true;
    }

    @Override
    public boolean isBurnable()
    {
        return true;
    }

    @Override
    public boolean isOccluding()
    {
        return false;
    }

    @Override
    public float getBlastResistance()
    {
        return MagicNumbers.MATERIAL__SAPLING__BLAST_RESISTANCE;
    }

    @Override
    public float getHardness()
    {
        return MagicNumbers.MATERIAL__SAPLING__HARDNESS;
    }

    public enum SaplingStage
    {
        NEW,
        OLDER
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
