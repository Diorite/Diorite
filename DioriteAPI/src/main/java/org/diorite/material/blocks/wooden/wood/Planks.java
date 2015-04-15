package org.diorite.material.blocks.wooden.wood;

import java.util.Map;

import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.material.blocks.wooden.WoodType;
import org.diorite.utils.collections.SimpleStringHashMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing block "Planks" and all its subtypes.
 */
public class Planks extends Wood
{	
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final byte  USED_DATA_VALUES = 6;	
    /**
     * Blast resistance of block, can be changed only before server start.
     * Final copy of blast resistance from {@link MagicNumbers} class.
     */
    public static final float BLAST_RESISTANCE = MagicNumbers.MATERIAL__PLANKS__BLAST_RESISTANCE;	
    /**
     * Hardness of block, can be changed only before server start.
     * Final copy of hardness from {@link MagicNumbers} class.
     */
    public static final float HARDNESS         = MagicNumbers.MATERIAL__PLANKS__HARDNESS;

    public static final Planks PLANKS_OAK      = new Planks();
    public static final Planks PLANKS_SPRUCE   = new Planks("SPRUCE", WoodType.SPRUCE);
    public static final Planks PLANKS_BIRCH    = new Planks("BIRCH", WoodType.BIRCH);
    public static final Planks PLANKS_JUNGLE   = new Planks("JUNGLE", WoodType.JUNGLE);
    public static final Planks PLANKS_ACACIA   = new Planks("ACACIA", WoodType.ACACIA);
    public static final Planks PLANKS_DARK_OAK = new Planks("DARK_OAK", WoodType.DARK_OAK);

    private static final Map<String, Planks>    byName = new SimpleStringHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<Planks> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    protected Planks()
    {
        super("PLANKS", 5, "minecraft:planks", "QAK", WoodType.OAK.getPlanksMeta(), WoodType.OAK);
    }

    public Planks(final String enumName, final WoodType woodType)
    {
        super(PLANKS_OAK.name(), PLANKS_OAK.getId(), PLANKS_OAK.getMinecraftId(), enumName, woodType.getPlanksMeta(), woodType);
    }

    public Planks(final int maxStack, final String typeName, final WoodType woodType)
    {
        super(PLANKS_OAK.name(), PLANKS_OAK.getId(), PLANKS_OAK.getMinecraftId(), maxStack, typeName, woodType.getPlanksMeta(), woodType);
    }

    @Override
    public Planks getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public Planks getType(final int id)
    {
        return getByID(id);
    }

    @Override
    public boolean isFlammable()
    {
        return true;
    }

    @Override
    public boolean isBurnable()
    {
        return true;
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
    public Planks getWoodType(final WoodType woodType)
    {
        return getPlanks(woodType);
    }

    /**
     * Returns one of Planks sub-type based on sub-id, may return null
     * @param id sub-type id
     * @return sub-type of Planks or null
     */
    public static Planks getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of Planks sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     * @param name name of sub-type
     * @return sub-type of Planks or null
     */
    public static Planks getByEnumName(final String name)
    {
        return byName.get(name);
    }

    public static Planks getPlanks(final WoodType type)
    {
        switch (type)
        {
            case OAK:
                return PLANKS_OAK;
            case SPRUCE:
                return PLANKS_SPRUCE;
            case BIRCH:
                return PLANKS_BIRCH;
            case JUNGLE:
                return PLANKS_JUNGLE;
            case ACACIA:
                return PLANKS_ACACIA;
            case DARK_OAK:
                return PLANKS_DARK_OAK;
            default:
                return PLANKS_OAK;
        }
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     * @param element sub-type to register
     */
    public static void register(final Planks element)
    {
        byID.put(element.getType(), element);
        byName.put(element.name(), element);
    }

    static
    {
        Planks.register(PLANKS_OAK);
        Planks.register(PLANKS_SPRUCE);
        Planks.register(PLANKS_BIRCH);
        Planks.register(PLANKS_JUNGLE);
        Planks.register(PLANKS_ACACIA);
        Planks.register(PLANKS_DARK_OAK);
    }
}
