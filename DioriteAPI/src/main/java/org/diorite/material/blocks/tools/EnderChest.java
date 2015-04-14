package org.diorite.material.blocks.tools;

import java.util.Map;

import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.material.BlockMaterialData;
import org.diorite.material.blocks.ContainerBlock;
import org.diorite.utils.collections.SimpleStringHashMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing block "EnderChest" and all its subtypes.
 */
public class EnderChest extends BlockMaterialData implements ContainerBlock
{
    // TODO: auto-generated class, implement other types (sub-ids).	
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final byte  USED_DATA_VALUES = 1;	
    /**
     * Blast resistance of block, can be changed only before server start.
     * Final copy of blast resistance from {@link MagicNumbers} class.
     */
    public static final float BLAST_RESISTANCE = MagicNumbers.MATERIAL__ENDER_CHEST__BLAST_RESISTANCE;	
    /**
     * Hardness of block, can be changed only before server start.
     * Final copy of hardness from {@link MagicNumbers} class.
     */
    public static final float HARDNESS         = MagicNumbers.MATERIAL__ENDER_CHEST__HARDNESS;

    public static final EnderChest ENDER_CHEST = new EnderChest();

    private static final Map<String, EnderChest>    byName = new SimpleStringHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<EnderChest> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    @SuppressWarnings("MagicNumber")
    protected EnderChest()
    {
        super("ENDER_CHEST", 130, "minecraft:ender_chest", "ENDER_CHEST", (byte) 0x00);
    }

    public EnderChest(final String enumName, final int type)
    {
        super(ENDER_CHEST.name(), ENDER_CHEST.getId(), ENDER_CHEST.getMinecraftId(), enumName, (byte) type);
    }

    public EnderChest(final int maxStack, final String typeName, final byte type)
    {
        super(ENDER_CHEST.name(), ENDER_CHEST.getId(), ENDER_CHEST.getMinecraftId(), maxStack, typeName, type);
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
    public EnderChest getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public EnderChest getType(final int id)
    {
        return getByID(id);
    }

    public static EnderChest getByID(final int id)
    {
        return byID.get((byte) id);
    }

    public static EnderChest getByEnumName(final String name)
    {
        return byName.get(name);
    }

    public static void register(final EnderChest element)
    {
        byID.put(element.getType(), element);
        byName.put(element.name(), element);
    }

    static
    {
        EnderChest.register(ENDER_CHEST);
    }
}
