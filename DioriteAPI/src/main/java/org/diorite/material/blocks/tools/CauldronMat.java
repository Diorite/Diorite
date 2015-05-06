package org.diorite.material.blocks.tools;

import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.material.BlockMaterialData;
import org.diorite.utils.collections.SimpleStringHashMap;
import org.diorite.utils.math.DioriteMathUtils;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing block "Cauldron" and all its subtypes.
 */
public class CauldronMat extends BlockMaterialData
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final byte  USED_DATA_VALUES = 4;
    /**
     * Blast resistance of block, can be changed only before server start.
     * Final copy of blast resistance from {@link MagicNumbers} class.
     */
    public static final float BLAST_RESISTANCE = MagicNumbers.MATERIAL__CAULDRON__BLAST_RESISTANCE;
    /**
     * Hardness of block, can be changed only before server start.
     * Final copy of hardness from {@link MagicNumbers} class.
     */
    public static final float HARDNESS         = MagicNumbers.MATERIAL__CAULDRON__HARDNESS;

    public static final CauldronMat CAULDRON_EMPTY = new CauldronMat();
    public static final CauldronMat CAULDRON_1     = new CauldronMat("1", 0x1, 1);
    public static final CauldronMat CAULDRON_2     = new CauldronMat("2", 0x2, 2);
    public static final CauldronMat CAULDRON_FULL  = new CauldronMat("FULL", 0x3, 3);

    private static final Map<String, CauldronMat>    byName = new SimpleStringHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<CauldronMat> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    protected final int waterLevel;

    @SuppressWarnings("MagicNumber")
    protected CauldronMat()
    {
        super("CAULDRON", 118, "minecraft:cauldron", "EMPTY", (byte) 0x00);
        this.waterLevel = 0;
    }

    protected CauldronMat(final String enumName, final int type, final int waterLevel)
    {
        super(CAULDRON_EMPTY.name(), CAULDRON_EMPTY.getId(), CAULDRON_EMPTY.getMinecraftId(), enumName, (byte) type);
        this.waterLevel = waterLevel;
    }

    protected CauldronMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type, final int waterLevel)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type);
        this.waterLevel = waterLevel;
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

    /**
     * Returns water level of cauldron.
     * Every level can be used to fill one water bottle.
     *
     * @return water level.
     */
    public int getWaterLevel()
    {
        return this.waterLevel;
    }

    /**
     * Returns one of Cauldron sub-type based on water-level.
     * It will never return null.
     *
     * @param level water level.
     *
     * @return sub-type of Cauldron
     */
    public CauldronMat getWaterLevel(final int level)
    {
        return getByID(DioriteMathUtils.getInRange(level, 0, 3));
    }

    @Override
    public CauldronMat getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public CauldronMat getType(final int id)
    {
        return getByID(id);
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("waterLevel", this.waterLevel).toString();
    }

    /**
     * Returns one of Cauldron sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of Cauldron or null
     */
    public static CauldronMat getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of Cauldron sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of Cauldron or null
     */
    public static CauldronMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Returns one of Cauldron sub-type based on water-level.
     * It will never return null.
     *
     * @param level water level.
     *
     * @return sub-type of Cauldron
     */
    public static CauldronMat getCauldron(final int level)
    {
        return getByID(DioriteMathUtils.getInRange(level, 0, 3));
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final CauldronMat element)
    {
        byID.put((byte) element.getType(), element);
        byName.put(element.name(), element);
    }

    static
    {
        CauldronMat.register(CAULDRON_EMPTY);
        CauldronMat.register(CAULDRON_1);
        CauldronMat.register(CAULDRON_2);
        CauldronMat.register(CAULDRON_FULL);
    }
}
