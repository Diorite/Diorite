package org.diorite.material.blocks.tools;

import java.util.Map;

import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.material.BlockMaterialData;
import org.diorite.material.blocks.ContainerBlock;
import org.diorite.utils.collections.SimpleStringHashMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing block "CraftingTable" and all its subtypes.
 */
public class CraftingTable extends BlockMaterialData implements ContainerBlock
{	
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final byte  USED_DATA_VALUES = 1;	
    /**
     * Blast resistance of block, can be changed only before server start.
     * Final copy of blast resistance from {@link MagicNumbers} class.
     */
    public static final float BLAST_RESISTANCE = MagicNumbers.MATERIAL__CRAFTING_TABLE__BLAST_RESISTANCE;	
    /**
     * Hardness of block, can be changed only before server start.
     * Final copy of hardness from {@link MagicNumbers} class.
     */
    public static final float HARDNESS         = MagicNumbers.MATERIAL__CRAFTING_TABLE__HARDNESS;

    public static final CraftingTable CRAFTING_TABLE = new CraftingTable();

    private static final Map<String, CraftingTable>    byName = new SimpleStringHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<CraftingTable> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    @SuppressWarnings("MagicNumber")
    protected CraftingTable()
    {
        super("CRAFTING_TABLE", 58, "minecraft:crafting_table", "CRAFTING_TABLE", (byte) 0x00);
    }

    public CraftingTable(final String enumName, final int type)
    {
        super(CRAFTING_TABLE.name(), CRAFTING_TABLE.getId(), CRAFTING_TABLE.getMinecraftId(), enumName, (byte) type);
    }

    public CraftingTable(final int maxStack, final String typeName, final byte type)
    {
        super(CRAFTING_TABLE.name(), CRAFTING_TABLE.getId(), CRAFTING_TABLE.getMinecraftId(), maxStack, typeName, type);
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
    public CraftingTable getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public CraftingTable getType(final int id)
    {
        return getByID(id);
    }

    public static CraftingTable getByID(final int id)
    {
        return byID.get((byte) id);
    }

    public static CraftingTable getByEnumName(final String name)
    {
        return byName.get(name);
    }

    public static void register(final CraftingTable element)
    {
        byID.put(element.getType(), element);
        byName.put(element.name(), element);
    }

    static
    {
        CraftingTable.register(CRAFTING_TABLE);
    }
}
