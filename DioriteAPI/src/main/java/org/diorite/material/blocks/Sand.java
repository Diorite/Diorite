package org.diorite.material.blocks;

import java.util.Map;

import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.material.BlockMaterialData;
import org.diorite.utils.collections.SimpleStringHashMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

public class Sand extends BlockMaterialData
{
    public static final byte  USED_DATA_VALUES = 2;
    public static final float BLAST_RESISTANCE = MagicNumbers.MATERIAL__SAND__BLAST_RESISTANCE;
    public static final float HARDNESS         = MagicNumbers.MATERIAL__SAND__HARDNESS;

    public static final Sand SAND     = new Sand();
    public static final Sand SAND_RED = new Sand("RED", 0x01);

    private static final Map<String, Sand>    byName = new SimpleStringHashMap<>(USED_DATA_VALUES, SLOW_GROW);
    private static final TByteObjectMap<Sand> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SLOW_GROW);

    @SuppressWarnings("MagicNumber")
    protected Sand()
    {
        super("SPONGE", 12, "minecraft:sand", "SPONGE", (byte) 0x00);
    }

    public Sand(final String enumName, final int type)
    {
        super(SAND.name(), SAND.getId(), SAND.getMinecraftId(), enumName, (byte) type);
    }

    public Sand(final int maxStack, final String typeName, final byte type)
    {
        super(SAND.name(), SAND.getId(), SAND.getMinecraftId(), maxStack, typeName, type);
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
    public Sand getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public Sand getType(final int id)
    {
        return getByID(id);
    }

    public static Sand getByID(final int id)
    {
        return byID.get((byte) id);
    }

    public static Sand getByEnumName(final String name)
    {
        return byName.get(name);
    }

    public static void register(final Sand element)
    {
        byID.put(element.getType(), element);
        byName.put(element.name(), element);
    }

    static
    {
        Sand.register(SAND);
        Sand.register(SAND_RED);
    }
}
