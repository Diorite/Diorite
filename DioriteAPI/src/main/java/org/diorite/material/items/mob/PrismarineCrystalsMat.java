package org.diorite.material.items.mob;

import java.util.Map;

import org.diorite.material.ItemMaterialData;
import org.diorite.utils.collections.maps.CaseInsensitiveMap;

import gnu.trove.map.TShortObjectMap;
import gnu.trove.map.hash.TShortObjectHashMap;

/**
 * Class representing 'Prismarine Crystals' item material in minecraft. <br>
 * ID of material: 410 <br>
 * String ID of material: minecraft:prismarine_crystals <br>
 * Max item stack size: 64
 */
@SuppressWarnings("JavaDoc")
public class PrismarineCrystalsMat extends ItemMaterialData
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final int USED_DATA_VALUES = 1;

    public static final PrismarineCrystalsMat PRISMARINE_CRYSTALS = new PrismarineCrystalsMat();

    private static final Map<String, PrismarineCrystalsMat>     byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TShortObjectMap<PrismarineCrystalsMat> byID   = new TShortObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR, Short.MIN_VALUE);

    @SuppressWarnings("MagicNumber")
    protected PrismarineCrystalsMat()
    {
        super("PRISMARINE_CRYSTALS", 410, "minecraft:prismarine_crystals", "PRISMARINE_CRYSTALS", (short) 0x00);
    }

    protected PrismarineCrystalsMat(final String enumName, final int id, final String minecraftId, final String typeName, final short type)
    {
        super(enumName, id, minecraftId, typeName, type);
    }

    protected PrismarineCrystalsMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final short type)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type);
    }

    @Override
    public PrismarineCrystalsMat getType(final int type)
    {
        return getByID(type);
    }

    @Override
    public PrismarineCrystalsMat getType(final String type)
    {
        return getByEnumName(type);
    }

    /**
     * Returns one of PrismarineCrystals sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of PrismarineCrystals or null
     */
    public static PrismarineCrystalsMat getByID(final int id)
    {
        return byID.get((short) id);
    }

    /**
     * Returns one of PrismarineCrystals sub-type based on name (selected by diorite team), may return null
     * If item contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of PrismarineCrystals or null
     */
    public static PrismarineCrystalsMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final PrismarineCrystalsMat element)
    {
        byID.put(element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    @Override
    public PrismarineCrystalsMat[] types()
    {
        return PrismarineCrystalsMat.prismarineCrystalsTypes();
    }

    /**
     * @return array that contains all sub-types of this item.
     */
    public static PrismarineCrystalsMat[] prismarineCrystalsTypes()
    {
        return byID.values(new PrismarineCrystalsMat[byID.size()]);
    }

    static
    {
        PrismarineCrystalsMat.register(PRISMARINE_CRYSTALS);
    }
}

