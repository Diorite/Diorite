package org.diorite.material.blocks.tools;

import java.util.Map;

import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.material.BlockMaterialData;
import org.diorite.utils.collections.SimpleStringHashMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

public class Cauldron extends BlockMaterialData
{
    // TODO: auto-generated class, implement other types (sub-ids).
    public static final byte  USED_DATA_VALUES = 1;
    public static final float BLAST_RESISTANCE = MagicNumbers.MATERIAL__CAULDRON__BLAST_RESISTANCE;
    public static final float HARDNESS         = MagicNumbers.MATERIAL__CAULDRON__HARDNESS;

    public static final Cauldron CAULDRON = new Cauldron();

    private static final Map<String, Cauldron>    byName = new SimpleStringHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<Cauldron> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    @SuppressWarnings("MagicNumber")
    protected Cauldron()
    {
        super("CAULDRON", 118, "minecraft:cauldron", "CAULDRON", (byte) 0x00);
    }

    public Cauldron(final String enumName, final int type)
    {
        super(CAULDRON.name(), CAULDRON.getId(), CAULDRON.getMinecraftId(), enumName, (byte) type);
    }

    public Cauldron(final int maxStack, final String typeName, final byte type)
    {
        super(CAULDRON.name(), CAULDRON.getId(), CAULDRON.getMinecraftId(), maxStack, typeName, type);
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
    public Cauldron getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public Cauldron getType(final int id)
    {
        return getByID(id);
    }

    public static Cauldron getByID(final int id)
    {
        return byID.get((byte) id);
    }

    public static Cauldron getByEnumName(final String name)
    {
        return byName.get(name);
    }

    public static void register(final Cauldron element)
    {
        byID.put(element.getType(), element);
        byName.put(element.name(), element);
    }

    static
    {
        Cauldron.register(CAULDRON);
    }
}