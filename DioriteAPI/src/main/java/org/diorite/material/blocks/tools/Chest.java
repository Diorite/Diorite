package org.diorite.material.blocks.tools;

import java.util.Map;

import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.material.BlockMaterialData;
import org.diorite.material.blocks.ContainerBlock;
import org.diorite.utils.collections.SimpleStringHashMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

public class Chest extends BlockMaterialData implements ContainerBlock
{
    // TODO: auto-generated class, implement other types (sub-ids).
    public static final byte  USED_DATA_VALUES = 1;
    public static final float BLAST_RESISTANCE = MagicNumbers.MATERIAL__CHEST__BLAST_RESISTANCE;
    public static final float HARDNESS         = MagicNumbers.MATERIAL__CHEST__HARDNESS;

    public static final Chest CHEST = new Chest();

    private static final Map<String, Chest>    byName = new SimpleStringHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<Chest> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    @SuppressWarnings("MagicNumber")
    protected Chest()
    {
        super("CHEST", 54, "minecraft:chest", "CHEST", (byte) 0x00);
    }

    public Chest(final String enumName, final int type)
    {
        super(CHEST.name(), CHEST.getId(), CHEST.getMinecraftId(), enumName, (byte) type);
    }

    public Chest(final int maxStack, final String typeName, final byte type)
    {
        super(CHEST.name(), CHEST.getId(), CHEST.getMinecraftId(), maxStack, typeName, type);
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
    public Chest getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public Chest getType(final int id)
    {
        return getByID(id);
    }

    public static Chest getByID(final int id)
    {
        return byID.get((byte) id);
    }

    public static Chest getByEnumName(final String name)
    {
        return byName.get(name);
    }

    public static void register(final Chest element)
    {
        byID.put(element.getType(), element);
        byName.put(element.name(), element);
    }

    static
    {
        Chest.register(CHEST);
    }
}