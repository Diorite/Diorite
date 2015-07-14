package org.diorite.material.blocks.tools;

import java.util.Map;

import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.material.BlockMaterialData;
import org.diorite.utils.collections.maps.CaseInsensitiveMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing block "DragonEgg" and all its subtypes.
 */
public class DragonEggMat extends BlockMaterialData
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final byte  USED_DATA_VALUES = 1;

    public static final DragonEggMat DRAGON_EGG = new DragonEggMat();

    private static final Map<String, DragonEggMat>    byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<DragonEggMat> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    @SuppressWarnings("MagicNumber")
    protected DragonEggMat()
    {
        super("DRAGON_EGG", 122, "minecraft:dragon_egg", "DRAGON_EGG", (byte) 0x00, 3, 45);
    }

    protected DragonEggMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type, final float hardness, final float blastResistance)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, hardness, blastResistance);
    }

    @Override
    public DragonEggMat getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public DragonEggMat getType(final int id)
    {
        return getByID(id);
    }

    /**
     * Returns one of DragonEgg sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of DragonEgg or null
     */
    public static DragonEggMat getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of DragonEgg sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of DragonEgg or null
     */
    public static DragonEggMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final DragonEggMat element)
    {
        byID.put((byte) element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    @Override
    public DragonEggMat[] types()
    {
        return DragonEggMat.dragonEggTypes();
    }

    /**
     * @return array that contains all sub-types of this block.
     */
    public static DragonEggMat[] dragonEggTypes()
    {
        return byID.values(new DragonEggMat[byID.size()]);
    }

    static
    {
        DragonEggMat.register(DRAGON_EGG);
    }
}
