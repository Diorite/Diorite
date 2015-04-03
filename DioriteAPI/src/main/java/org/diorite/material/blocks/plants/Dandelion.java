package org.diorite.material.blocks.plants;

import java.util.Map;

import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.utils.collections.SimpleStringHashMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

public class Dandelion extends Plant
{
    public static final byte  USED_DATA_VALUES = 1;
    public static final float BLAST_RESISTANCE = MagicNumbers.MATERIAL__DANDELION__BLAST_RESISTANCE;
    public static final float HARDNESS         = MagicNumbers.MATERIAL__DANDELION__HARDNESS;

    public static final Dandelion DANDELION = new Dandelion();

    private static final Map<String, Dandelion>    byName = new SimpleStringHashMap<>(USED_DATA_VALUES, SLOW_GROW);
    private static final TByteObjectMap<Dandelion> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SLOW_GROW);

    @SuppressWarnings("MagicNumber")
    protected Dandelion()
    {
        super("DANDELION", 37, "minecraft:yellow_flower", "DANDELION", (byte) 0x00);
    }

    public Dandelion(final String enumName, final int type)
    {
        super(DANDELION.name(), DANDELION.getId(), DANDELION.getMinecraftId(), enumName, (byte) type);
    }

    public Dandelion(final int maxStack, final String typeName, final byte type)
    {
        super(DANDELION.name(), DANDELION.getId(), DANDELION.getMinecraftId(), maxStack, typeName, type);
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
    public Dandelion getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public Dandelion getType(final int id)
    {
        return getByID(id);
    }

    public static Dandelion getByID(final int id)
    {
        return byID.get((byte) id);
    }

    public static Dandelion getByEnumName(final String name)
    {
        return byName.get(name);
    }

    public static void register(final Dandelion element)
    {
        byID.put(element.getType(), element);
        byName.put(element.name(), element);
    }

    static
    {
        Dandelion.register(DANDELION);
    }
}
