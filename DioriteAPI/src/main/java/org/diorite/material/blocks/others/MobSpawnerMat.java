package org.diorite.material.blocks.others;

import java.util.Map;

import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.material.BlockMaterialData;
import org.diorite.utils.collections.maps.CaseInsensitiveMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing block "MobSpawner" and all its subtypes.
 */
public class MobSpawnerMat extends BlockMaterialData
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final byte  USED_DATA_VALUES = 1;

    public static final MobSpawnerMat MOB_SPAWNER = new MobSpawnerMat();

    private static final Map<String, MobSpawnerMat>    byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<MobSpawnerMat> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    @SuppressWarnings("MagicNumber")
    protected MobSpawnerMat()
    {
        super("MOB_SPAWNER", 52, "minecraft:mob_spawner", "MOB_SPAWNER", (byte) 0x00, 5, 25);
    }

    protected MobSpawnerMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type, final float hardness, final float blastResistance)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, hardness, blastResistance);
    }

    @Override
    public MobSpawnerMat getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public MobSpawnerMat getType(final int id)
    {
        return getByID(id);
    }

    /**
     * Returns one of MobSpawner sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of MobSpawner or null
     */
    public static MobSpawnerMat getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of MobSpawner sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of MobSpawner or null
     */
    public static MobSpawnerMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final MobSpawnerMat element)
    {
        byID.put((byte) element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    @Override
    public MobSpawnerMat[] types()
    {
        return MobSpawnerMat.mobSpawnerTypes();
    }

    /**
     * @return array that contains all sub-types of this block.
     */
    public static MobSpawnerMat[] mobSpawnerTypes()
    {
        return byID.values(new MobSpawnerMat[byID.size()]);
    }

    static
    {
        MobSpawnerMat.register(MOB_SPAWNER);
    }
}
