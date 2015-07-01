package org.diorite.material.blocks.others;

import java.util.Map;

import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.material.BlockMaterialData;
import org.diorite.utils.collections.maps.CaseInsensitiveMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing block "Prismarine" and all its subtypes.
 */
public class PrismarineMat extends BlockMaterialData
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final byte  USED_DATA_VALUES = 3;
    /**
     * Blast resistance of block, can be changed only before server start.
     * Final copy of blast resistance from {@link MagicNumbers} class.
     */
    public static final float BLAST_RESISTANCE = MagicNumbers.MATERIAL__PRISMARINE__BLAST_RESISTANCE;
    /**
     * Hardness of block, can be changed only before server start.
     * Final copy of hardness from {@link MagicNumbers} class.
     */
    public static final float HARDNESS         = MagicNumbers.MATERIAL__PRISMARINE__HARDNESS;

    public static final PrismarineMat PRISMARINE        = new PrismarineMat();
    public static final PrismarineMat PRISMARINE_BRICKS = new PrismarineMat("BRICKS", 0x1);
    public static final PrismarineMat PRISMARINE_DARK   = new PrismarineMat("DARK", 0x2);

    private static final Map<String, PrismarineMat>    byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<PrismarineMat> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    @SuppressWarnings("MagicNumber")
    protected PrismarineMat()
    {
        super("PRISMARINE", 168, "minecraft:prismarine", "PRISMARINE", (byte) 0x00);
    }

    protected PrismarineMat(final String enumName, final int type)
    {
        super(PRISMARINE.name(), PRISMARINE.ordinal(), PRISMARINE.getMinecraftId(), enumName, (byte) type);
    }

    protected PrismarineMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type)
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
    public PrismarineMat getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public PrismarineMat getType(final int id)
    {
        return getByID(id);
    }

    /**
     * Returns one of Prismarine sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of Prismarine or null
     */
    public static PrismarineMat getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of Prismarine sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of Prismarine or null
     */
    public static PrismarineMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final PrismarineMat element)
    {
        byID.put((byte) element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    @Override
    public PrismarineMat[] types()
    {
        return PrismarineMat.prismarineTypes();
    }

    /**
     * @return array that contains all sub-types of this block.
     */
    public static PrismarineMat[] prismarineTypes()
    {
        return byID.values(new PrismarineMat[byID.size()]);
    }

    static
    {
        PrismarineMat.register(PRISMARINE);
        PrismarineMat.register(PRISMARINE_BRICKS);
        PrismarineMat.register(PRISMARINE_DARK);
    }
}
