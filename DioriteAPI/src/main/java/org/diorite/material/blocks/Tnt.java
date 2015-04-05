package org.diorite.material.blocks;

import java.util.Map;

import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.material.BlockMaterialData;
import org.diorite.utils.collections.SimpleStringHashMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

public class Tnt extends BlockMaterialData
{
    public static final byte  USED_DATA_VALUES = 1;
    public static final float BLAST_RESISTANCE = MagicNumbers.MATERIAL__TNT__BLAST_RESISTANCE;
    public static final float HARDNESS         = MagicNumbers.MATERIAL__TNT__HARDNESS;

    public static final Tnt TNT = new Tnt();

    private static final Map<String, Tnt>    byName = new SimpleStringHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<Tnt> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    @SuppressWarnings("MagicNumber")
    protected Tnt()
    {
        super("TNT", 46, "minecraft:tnt", "TNT", (byte) 0x00);
    }

    public Tnt(final String enumName, final int type)
    {
        super(TNT.name(), TNT.getId(), TNT.getMinecraftId(), enumName, (byte) type);
    }

    public Tnt(final int maxStack, final String typeName, final byte type)
    {
        super(TNT.name(), TNT.getId(), TNT.getMinecraftId(), maxStack, typeName, type);
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
    public Tnt getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public Tnt getType(final int id)
    {
        return getByID(id);
    }

    public static Tnt getByID(final int id)
    {
        return byID.get((byte) id);
    }

    public static Tnt getByEnumName(final String name)
    {
        return byName.get(name);
    }

    public static void register(final Tnt element)
    {
        byID.put(element.getType(), element);
        byName.put(element.name(), element);
    }

    static
    {
        Tnt.register(TNT);
    }
}
