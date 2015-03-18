package org.diorite.material.blocks;

import java.util.Map;

import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.material.BlockMaterialData;
import org.diorite.utils.collections.SimpleStringHashMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

public class Planks extends BlockMaterialData
{
    public static final byte USED_DATA_VALUES = 6;

    public static final Planks PLANKS_QAK      = new Planks();
    public static final Planks PLANKS_SPRUCE   = new Planks("SPRUCE", 0x01);
    public static final Planks PLANKS_BIRCH    = new Planks("BIRCH", 0x02);
    public static final Planks PLANKS_JUNGLE   = new Planks("JUNGLE", 0x03);
    public static final Planks PLANKS_ACACIA   = new Planks("ACACIA", 0x04);
    public static final Planks PLANKS_DARK_OAK = new Planks("DARK_OAK", 0x05);

    private static final Map<String, Planks>    byName = new SimpleStringHashMap<>(USED_DATA_VALUES, SLOW_GROW);
    private static final TByteObjectMap<Planks> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SLOW_GROW);

    protected Planks()
    {
        super("PLANKS", 5, "QAK", (byte) 0x00);
    }

    public Planks(final String enumName, final int type)
    {
        super(PLANKS_QAK.name(), PLANKS_QAK.getId(), enumName, (byte) type);
    }

    public Planks(final int maxStack, final String typeName, final byte type)
    {
        super(PLANKS_QAK.name(), PLANKS_QAK.getId(), maxStack, typeName, type);
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
    public float getBlastResistance()
    {
        return MagicNumbers.MATERIAL__PLANKS__BLAST_RESISTANCE;
    }

    @Override
    public float getHardness()
    {
        return MagicNumbers.MATERIAL__PLANKS__HARDNESS;
    }

    public static Planks getByID(final int id)
    {
        return byID.get((byte) id);
    }

    public static Planks getByEnumName(final String name)
    {
        return byName.get(name);
    }

    public static void register(final Planks element)
    {
        byID.put((byte) element.getId(), element);
        byName.put(element.name(), element);
    }

    static
    {
        Planks.register(PLANKS_QAK);
        Planks.register(PLANKS_SPRUCE);
        Planks.register(PLANKS_BIRCH);
        Planks.register(PLANKS_JUNGLE);
        Planks.register(PLANKS_ACACIA);
        Planks.register(PLANKS_DARK_OAK);
    }
}
