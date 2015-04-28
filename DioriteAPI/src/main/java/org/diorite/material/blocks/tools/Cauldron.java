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
public class Cauldron extends BlockMaterialData
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

    public static final Cauldron CAULDRON_EMPTY = new Cauldron();
    public static final Cauldron CAULDRON_1     = new Cauldron("1", 0x1, 1);
    public static final Cauldron CAULDRON_2     = new Cauldron("2", 0x2, 2);
    public static final Cauldron CAULDRON_FULL  = new Cauldron("FULL", 0x3, 3);

    private static final Map<String, Cauldron>    byName = new SimpleStringHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<Cauldron> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    protected final int waterLevel;

    @SuppressWarnings("MagicNumber")
    protected Cauldron()
    {
        super("CAULDRON", 118, "minecraft:cauldron", "EMPTY", (byte) 0x00);
        this.waterLevel = 0;
    }

    public Cauldron(final String enumName, final int type, final int waterLevel)
    {
        super(CAULDRON_EMPTY.name(), CAULDRON_EMPTY.getId(), CAULDRON_EMPTY.getMinecraftId(), enumName, (byte) type);
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
    public Cauldron getWaterLevel(final int level)
    {
        return getByID(DioriteMathUtils.getInRange(level, 0, 3));
    }

    @Override
    public Cauldron getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public Cauldron getType(final int id)
    {
        return getByID(id);
    }

    /**
     * Returns one of Cauldron sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of Cauldron or null
     */
    public static Cauldron getByID(final int id)
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
    public static Cauldron getByEnumName(final String name)
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
    public static Cauldron getCauldron(final int level)
    {
        return getByID(DioriteMathUtils.getInRange(level, 0, 3));
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final Cauldron element)
    {
        byID.put(element.getType(), element);
        byName.put(element.name(), element);
    }

    static
    {
        Cauldron.register(CAULDRON_EMPTY);
        Cauldron.register(CAULDRON_1);
        Cauldron.register(CAULDRON_2);
        Cauldron.register(CAULDRON_FULL);
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("waterLevel", this.waterLevel).toString();
    }
}
