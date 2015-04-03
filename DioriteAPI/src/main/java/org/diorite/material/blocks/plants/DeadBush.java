package org.diorite.material.blocks.plants;

import java.util.Map;

import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.utils.collections.SimpleStringHashMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

public class DeadBush extends Plant
{
    public static final byte  USED_DATA_VALUES = 1;
    public static final float BLAST_RESISTANCE = MagicNumbers.MATERIAL__DEAD_BUSH__BLAST_RESISTANCE;
    public static final float HARDNESS         = MagicNumbers.MATERIAL__DEAD_BUSH__HARDNESS;

    public static final DeadBush DEAD_BUSH = new DeadBush();

    private static final Map<String, DeadBush>    byName = new SimpleStringHashMap<>(USED_DATA_VALUES, SLOW_GROW);
    private static final TByteObjectMap<DeadBush> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SLOW_GROW);

    @SuppressWarnings("MagicNumber")
    protected DeadBush()
    {
        super("DEAD_BUSH", 32, "minecraft:deadbush", "DEAD_BUSH", (byte) 0x00);
    }

    public DeadBush(final String enumName, final int type)
    {
        super(DEAD_BUSH.name(), DEAD_BUSH.getId(), DEAD_BUSH.getMinecraftId(), enumName, (byte) type);
    }

    public DeadBush(final int maxStack, final String typeName, final byte type)
    {
        super(DEAD_BUSH.name(), DEAD_BUSH.getId(), DEAD_BUSH.getMinecraftId(), maxStack, typeName, type);
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
    public DeadBush getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public DeadBush getType(final int id)
    {
        return getByID(id);
    }

    public static DeadBush getByID(final int id)
    {
        return byID.get((byte) id);
    }

    public static DeadBush getByEnumName(final String name)
    {
        return byName.get(name);
    }

    public static void register(final DeadBush element)
    {
        byID.put(element.getType(), element);
        byName.put(element.name(), element);
    }

    static
    {
        DeadBush.register(DEAD_BUSH);
    }
}
