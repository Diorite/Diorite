package org.diorite.material.blocks;

import java.util.Map;

import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.material.BlockMaterialData;
import org.diorite.utils.collections.SimpleStringHashMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

public class Air extends BlockMaterialData
{
    public static final byte USED_DATA_VALUES = 1;

    public static final Air AIR = new Air();

    private static final Map<String, Air>    byName = new SimpleStringHashMap<>(USED_DATA_VALUES, SLOW_GROW);
    private static final TByteObjectMap<Air> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SLOW_GROW);

    protected Air()
    {
        super("AIR", 0, "minecraft:air", 0, "AIR", (byte) 0x00);
    }

    public Air(final String enumName, final int type)
    {
        super(AIR.name(), AIR.getId(), AIR.getMinecraftId(), AIR.getMaxStack(), enumName, (byte) type);
    }

    public Air(final int maxStack, final String typeName, final byte type)
    {
        super(AIR.name(), AIR.getId(), AIR.getMinecraftId(), maxStack, typeName, type);
    }

    @Override
    public Air getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public Air getType(final int id)
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
    public boolean isOccluding()
    {
        return false;
    }

    @Override
    public boolean isReplaceable()
    {
        return true;
    }

    @Override
    public float getBlastResistance()
    {
        return MagicNumbers.MATERIAL__AIR__BLAST_RESISTANCE;
    }

    @Override
    public float getHardness()
    {
        return MagicNumbers.MATERIAL__AIR__HARDNESS;
    }

    public static Air getByID(final int id)
    {
        return byID.get((byte) id);
    }

    public static Air getByEnumName(final String name)
    {
        return byName.get(name);
    }

    public static void register(final Air element)
    {
        byID.put(element.getType(), element);
        byName.put(element.name(), element);
    }

    static
    {
        Air.register(AIR);
    }
}
