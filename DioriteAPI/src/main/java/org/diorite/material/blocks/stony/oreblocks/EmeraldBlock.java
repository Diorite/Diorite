package org.diorite.material.blocks.stony.oreblocks;

import java.util.Map;

import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.material.Material;
import org.diorite.material.blocks.stony.ore.Ore;
import org.diorite.utils.collections.SimpleStringHashMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing block "EmeraldBlock" and all its subtypes.
 */
public class EmeraldBlock extends OreBlock
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final byte  USED_DATA_VALUES = 1;
    /**
     * Blast resistance of block, can be changed only before server start.
     * Final copy of blast resistance from {@link MagicNumbers} class.
     */
    public static final float BLAST_RESISTANCE = MagicNumbers.MATERIAL__EMERALD_BLOCK__BLAST_RESISTANCE;
    /**
     * Hardness of block, can be changed only before server start.
     * Final copy of hardness from {@link MagicNumbers} class.
     */
    public static final float HARDNESS         = MagicNumbers.MATERIAL__EMERALD_BLOCK__HARDNESS;

    public static final EmeraldBlock EMERALD_BLOCK = new EmeraldBlock(Material.EMERALD_ORE);

    private static final Map<String, EmeraldBlock>    byName = new SimpleStringHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<EmeraldBlock> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    @SuppressWarnings("MagicNumber")
    protected EmeraldBlock(final Ore ore)
    {
        super("EMERALD_BLOCK", 133, "minecraft:emerald_block", "EMERALD_BLOCK", (byte) 0x00, ore);
    }

    public EmeraldBlock(final String enumName, final int type, final Ore ore)
    {
        super(EMERALD_BLOCK.name(), EMERALD_BLOCK.getId(), EMERALD_BLOCK.getMinecraftId(), enumName, (byte) type, ore);
    }

    public EmeraldBlock(final int maxStack, final String typeName, final byte type, final Ore ore)
    {
        super(EMERALD_BLOCK.name(), EMERALD_BLOCK.getId(), EMERALD_BLOCK.getMinecraftId(), maxStack, typeName, type, ore);
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
    public EmeraldBlock getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public EmeraldBlock getType(final int id)
    {
        return getByID(id);
    }

    /**
     * Returns one of EmeraldBlock sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of EmeraldBlock or null
     */
    public static EmeraldBlock getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of EmeraldBlock sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of EmeraldBlock or null
     */
    public static EmeraldBlock getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final EmeraldBlock element)
    {
        byID.put(element.getType(), element);
        byName.put(element.name(), element);
    }

    static
    {
        EmeraldBlock.register(EMERALD_BLOCK);
    }
}
