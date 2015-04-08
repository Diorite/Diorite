package org.diorite.material.blocks.tools;

import java.util.Map;

import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.material.BlockMaterialData;
import org.diorite.material.blocks.ContainerBlock;
import org.diorite.utils.collections.SimpleStringHashMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

public class Hopper extends BlockMaterialData implements ContainerBlock
{
    // TODO: auto-generated class, implement other types (sub-ids).
    public static final byte  USED_DATA_VALUES = 1;
    public static final float BLAST_RESISTANCE = MagicNumbers.MATERIAL__HOPPER__BLAST_RESISTANCE;
    public static final float HARDNESS         = MagicNumbers.MATERIAL__HOPPER__HARDNESS;

    public static final Hopper HOPPER = new Hopper();

    private static final Map<String, Hopper>    byName = new SimpleStringHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<Hopper> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    @SuppressWarnings("MagicNumber")
    protected Hopper()
    {
        super("HOPPER", 154, "minecraft:hopper", "HOPPER", (byte) 0x00);
    }

    public Hopper(final String enumName, final int type)
    {
        super(HOPPER.name(), HOPPER.getId(), HOPPER.getMinecraftId(), enumName, (byte) type);
    }

    public Hopper(final int maxStack, final String typeName, final byte type)
    {
        super(HOPPER.name(), HOPPER.getId(), HOPPER.getMinecraftId(), maxStack, typeName, type);
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
    public Hopper getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public Hopper getType(final int id)
    {
        return getByID(id);
    }

    public static Hopper getByID(final int id)
    {
        return byID.get((byte) id);
    }

    public static Hopper getByEnumName(final String name)
    {
        return byName.get(name);
    }

    public static void register(final Hopper element)
    {
        byID.put(element.getType(), element);
        byName.put(element.name(), element);
    }

    static
    {
        Hopper.register(HOPPER);
    }
}