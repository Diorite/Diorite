package org.diorite.material.blocks.tools;

import java.util.Map;

import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.material.BlockMaterialData;
import org.diorite.utils.collections.maps.CaseInsensitiveMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing block "EnchantingTable" and all its subtypes.
 */
public class EnchantingTableMat extends BlockMaterialData
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final byte  USED_DATA_VALUES = 1;
    /**
     * Blast resistance of block, can be changed only before server start.
     * Final copy of blast resistance from {@link MagicNumbers} class.
     */
    public static final float BLAST_RESISTANCE = MagicNumbers.MATERIAL__ENCHANTING_TABLE__BLAST_RESISTANCE;
    /**
     * Hardness of block, can be changed only before server start.
     * Final copy of hardness from {@link MagicNumbers} class.
     */
    public static final float HARDNESS         = MagicNumbers.MATERIAL__ENCHANTING_TABLE__HARDNESS;

    public static final EnchantingTableMat ENCHANTING_TABLE = new EnchantingTableMat();

    private static final Map<String, EnchantingTableMat>    byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<EnchantingTableMat> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    @SuppressWarnings("MagicNumber")
    protected EnchantingTableMat()
    {
        super("ENCHANTING_TABLE", 116, "minecraft:enchanting_table", "ENCHANTING_TABLE", (byte) 0x00);
    }

    protected EnchantingTableMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type);
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
    public EnchantingTableMat getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public EnchantingTableMat getType(final int id)
    {
        return getByID(id);
    }

    /**
     * Returns one of EnchantingTable sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of EnchantingTable or null
     */
    public static EnchantingTableMat getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of EnchantingTable sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of EnchantingTable or null
     */
    public static EnchantingTableMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final EnchantingTableMat element)
    {
        byID.put((byte) element.getType(), element);
        byName.put(element.name(), element);
    }

    @Override
    public EnchantingTableMat[] types()
    {
        return EnchantingTableMat.enchantingTableTypes();
    }

    /**
     * @return array that contains all sub-types of this block.
     */
    public static EnchantingTableMat[] enchantingTableTypes()
    {
        return byID.values(new EnchantingTableMat[byID.size()]);
    }

    static
    {
        EnchantingTableMat.register(ENCHANTING_TABLE);
    }
}
