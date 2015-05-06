package org.diorite.material.blocks.stony.oreblocks;

import java.util.Map;

import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.material.Material;
import org.diorite.material.blocks.stony.ore.OreMat;
import org.diorite.utils.collections.SimpleStringHashMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing block "RedstoneBlock" and all its subtypes.
 */
public class RedstoneBlockMat extends OreBlockMat
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final byte  USED_DATA_VALUES = 1;
    /**
     * Blast resistance of block, can be changed only before server start.
     * Final copy of blast resistance from {@link MagicNumbers} class.
     */
    public static final float BLAST_RESISTANCE = MagicNumbers.MATERIAL__REDSTONE_BLOCK__BLAST_RESISTANCE;
    /**
     * Hardness of block, can be changed only before server start.
     * Final copy of hardness from {@link MagicNumbers} class.
     */
    public static final float HARDNESS         = MagicNumbers.MATERIAL__REDSTONE_BLOCK__HARDNESS;

    public static final RedstoneBlockMat REDSTONE_BLOCK = new RedstoneBlockMat();

    private static final Map<String, RedstoneBlockMat>    byName = new SimpleStringHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<RedstoneBlockMat> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    @SuppressWarnings("MagicNumber")
    protected RedstoneBlockMat()
    {
        super("REDSTONE_BLOCK", 152, "minecraft:redstone_block", "REDSTONE_BLOCK", (byte) 0x00, Material.REDSTONE_ORE);
    }

    protected RedstoneBlockMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type, final OreMat ore)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, ore);
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
    public RedstoneBlockMat getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public RedstoneBlockMat getType(final int id)
    {
        return getByID(id);
    }

    /**
     * Returns one of RedstoneBlock sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of RedstoneBlock or null
     */
    public static RedstoneBlockMat getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of RedstoneBlock sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of RedstoneBlock or null
     */
    public static RedstoneBlockMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final RedstoneBlockMat element)
    {
        byID.put((byte) element.getType(), element);
        byName.put(element.name(), element);
    }

    static
    {
        RedstoneBlockMat.register(REDSTONE_BLOCK);
    }
}
