package org.diorite.material.blocks.others;

import java.util.Map;

import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.material.BlockMaterialData;
import org.diorite.utils.collections.SimpleStringHashMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

public class SeaLantren extends BlockMaterialData
{	
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final byte  USED_DATA_VALUES = 1;	
    /**
     * Blast resistance of block, can be changed only before server start.
     * Final copy of blast resistance from {@link MagicNumbers} class.
     */
    public static final float BLAST_RESISTANCE = MagicNumbers.MATERIAL__SEA_LANTREN__BLAST_RESISTANCE;	
    /**
     * Hardness of block, can be changed only before server start.
     * Final copy of hardness from {@link MagicNumbers} class.
     */
    public static final float HARDNESS         = MagicNumbers.MATERIAL__SEA_LANTREN__HARDNESS;

    public static final SeaLantren SEA_LANTREN = new SeaLantren();

    private static final Map<String, SeaLantren>    byName = new SimpleStringHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<SeaLantren> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    @SuppressWarnings("MagicNumber")
    protected SeaLantren()
    {
        super("SEA_LANTREN", 169, "minecraft:sea_lantren", "SEA_LANTREN", (byte) 0x00);
    }

    public SeaLantren(final String enumName, final int type)
    {
        super(SEA_LANTREN.name(), SEA_LANTREN.getId(), SEA_LANTREN.getMinecraftId(), enumName, (byte) type);
    }

    public SeaLantren(final int maxStack, final String typeName, final byte type)
    {
        super(SEA_LANTREN.name(), SEA_LANTREN.getId(), SEA_LANTREN.getMinecraftId(), maxStack, typeName, type);
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
    public SeaLantren getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public SeaLantren getType(final int id)
    {
        return getByID(id);
    }

    public static SeaLantren getByID(final int id)
    {
        return byID.get((byte) id);
    }

    public static SeaLantren getByEnumName(final String name)
    {
        return byName.get(name);
    }

    public static void register(final SeaLantren element)
    {
        byID.put(element.getType(), element);
        byName.put(element.name(), element);
    }

    static
    {
        SeaLantren.register(SEA_LANTREN);
    }
}
