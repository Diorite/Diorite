package diorite.material.blocks;

import java.util.Map;

import diorite.cfg.magic.MagicNumbers;
import diorite.material.BlockMaterialData;
import diorite.utils.collections.SimpleStringHashMap;
import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

public class Grass extends BlockMaterialData
{
    public static final byte USED_DATA_VALUES = 1;

    public static final Grass GRASS = new Grass();

    private static final Map<String, Grass>    byName = new SimpleStringHashMap<>(USED_DATA_VALUES, SLOW_GROW);
    private static final TByteObjectMap<Grass> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SLOW_GROW);

    protected Grass()
    {
        super("GRASS", 2, "GRASS", (byte) 0x00);
    }

    public Grass(final String enumName, final int type)
    {
        super(GRASS.name(), GRASS.getId(), enumName, (byte) type);
    }

    public Grass(final int maxStack, final String typeName, final byte type)
    {
        super(GRASS.name(), GRASS.getId(), maxStack, typeName, type);
    }

    @Override
    public float getBlastResistance()
    {
        return MagicNumbers.MATERIAL__GRASS__BLAST_RESISTANCE;
    }

    @Override
    public float getHardness()
    {
        return MagicNumbers.MATERIAL__GRASS__HARDNESS;
    }

    @Override
    public BlockMaterialData getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public BlockMaterialData getType(final int id)
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
        byID.put((byte) element.getId(), element);
        byName.put(element.name(), element);
    }

    static
    {
        Grass.register(GRASS);
    }
}
