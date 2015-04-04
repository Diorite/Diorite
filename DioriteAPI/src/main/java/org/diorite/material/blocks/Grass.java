package org.diorite.material.blocks;

import java.util.Map;

import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.material.BlockMaterialData;
import org.diorite.utils.collections.SimpleStringHashMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

public class Grass extends BlockMaterialData
{
    public static final byte  USED_DATA_VALUES = 1;
    public static final float BLAST_RESISTANCE = MagicNumbers.MATERIAL__GRASS__BLAST_RESISTANCE;
    public static final float HARDNESS         = MagicNumbers.MATERIAL__GRASS__HARDNESS;

    public static final Grass GRASS = new Grass();

    private static final Map<String, Grass>    byName = new SimpleStringHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<Grass> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    protected Grass()
    {
        super("GRASS", 2, "minecraft:grass", "GRASS", (byte) 0x00);
    }

    public Grass(final String enumName, final int type)
    {
        super(GRASS.name(), GRASS.getId(), GRASS.getMinecraftId(), enumName, (byte) type);
    }

    public Grass(final int maxStack, final String typeName, final byte type)
    {
        super(GRASS.name(), GRASS.getId(), GRASS.getMinecraftId(), maxStack, typeName, type);
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
    public Grass getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public Grass getType(final int id)
    {
        return getByID(id);
    }

    public static Grass getByID(final int id)
    {
        return byID.get((byte) id);
    }

    public static Grass getByEnumName(final String name)
    {
        return byName.get(name);
    }

    public static void register(final Grass element)
    {
        byID.put(element.getType(), element);
        byName.put(element.name(), element);
    }

    static
    {
        Grass.register(GRASS);
    }
}
