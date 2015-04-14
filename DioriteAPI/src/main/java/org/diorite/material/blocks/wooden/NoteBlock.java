package org.diorite.material.blocks.wooden;

import java.util.Map;

import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.material.blocks.ContainerBlock;
import org.diorite.utils.collections.SimpleStringHashMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing block "NoteBlock" and all its subtypes.
 */
public class NoteBlock extends Wooden implements ContainerBlock
{	
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final byte  USED_DATA_VALUES = 1;	
    /**
     * Blast resistance of block, can be changed only before server start.
     * Final copy of blast resistance from {@link MagicNumbers} class.
     */
    public static final float BLAST_RESISTANCE = MagicNumbers.MATERIAL__NOTEBLOCK__BLAST_RESISTANCE;	
    /**
     * Hardness of block, can be changed only before server start.
     * Final copy of hardness from {@link MagicNumbers} class.
     */
    public static final float HARDNESS         = MagicNumbers.MATERIAL__NOTEBLOCK__HARDNESS;

    public static final NoteBlock NOTEBLOCK = new NoteBlock();

    private static final Map<String, NoteBlock>    byName = new SimpleStringHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<NoteBlock> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    @SuppressWarnings("MagicNumber")
    protected NoteBlock()
    {
        super("NOTEBLOCK", 25, "minecraft:noteblock", "NOTEBLOCK", (byte) 0x00);
    }

    public NoteBlock(final String enumName, final int type)
    {
        super(NOTEBLOCK.name(), NOTEBLOCK.getId(), NOTEBLOCK.getMinecraftId(), enumName, (byte) type);
    }

    public NoteBlock(final int maxStack, final String typeName, final byte type)
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
    public NoteBlock getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public NoteBlock getType(final int id)
    {
        return getByID(id);
    }

    public static NoteBlock getByID(final int id)
    {
        return byID.get((byte) id);
    }

    public static NoteBlock getByEnumName(final String name)
    {
        return byName.get(name);
    }

    public static void register(final NoteBlock element)
    {
        byID.put(element.getType(), element);
        byName.put(element.name(), element);
    }

    static
    {
        NoteBlock.register(NOTEBLOCK);
    }
}
