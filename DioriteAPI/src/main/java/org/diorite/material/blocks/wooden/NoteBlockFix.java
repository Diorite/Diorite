package org.diorite.material.blocks.wooden;

import java.util.Map;

import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.material.blocks.Container;
import org.diorite.utils.collections.SimpleStringHashMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

public class NoteBlockFix extends Wooden implements Container
{
    public static final byte  USED_DATA_VALUES = 1;
    public static final float BLAST_RESISTANCE = MagicNumbers.MATERIAL__NOTEBLOCK__BLAST_RESISTANCE;
    public static final float HARDNESS         = MagicNumbers.MATERIAL__NOTEBLOCK__HARDNESS;

    public static final NoteBlockFix NOTEBLOCK = new NoteBlockFix();

    private static final Map<String, NoteBlockFix>    byName = new SimpleStringHashMap<>(USED_DATA_VALUES, SLOW_GROW);
    private static final TByteObjectMap<NoteBlockFix> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SLOW_GROW);

    @SuppressWarnings("MagicNumber")
    protected NoteBlockFix()
    {
        super("NOTEBLOCK", 25, "minecraft:noteblock", "NOTEBLOCK", (byte) 0x00);
    }

    public NoteBlockFix(final String enumName, final int type)
    {
        super(NOTEBLOCK.name(), NOTEBLOCK.getId(), NOTEBLOCK.getMinecraftId(), enumName, (byte) type);
    }

    public NoteBlockFix(final int maxStack, final String typeName, final byte type)
    {
        super(NOTEBLOCK.name(), NOTEBLOCK.getId(), NOTEBLOCK.getMinecraftId(), maxStack, typeName, type);
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
    public NoteBlockFix getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public NoteBlockFix getType(final int id)
    {
        return getByID(id);
    }

    public static NoteBlockFix getByID(final int id)
    {
        return byID.get((byte) id);
    }

    public static NoteBlockFix getByEnumName(final String name)
    {
        return byName.get(name);
    }

    public static void register(final NoteBlockFix element)
    {
        byID.put(element.getType(), element);
        byName.put(element.name(), element);
    }

    static
    {
        NoteBlockFix.register(NOTEBLOCK);
    }
}
