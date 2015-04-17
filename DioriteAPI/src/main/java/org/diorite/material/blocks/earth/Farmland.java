package org.diorite.material.blocks.earth;

import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.utils.collections.SimpleStringHashMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing block "Farmland" and all its subtypes.
 */
public class Farmland extends Earth
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final byte  USED_DATA_VALUES = 2;
    /**
     * Blast resistance of block, can be changed only before server start.
     * Final copy of blast resistance from {@link MagicNumbers} class.
     */
    public static final float BLAST_RESISTANCE = MagicNumbers.MATERIAL__FARMLAND__BLAST_RESISTANCE;
    /**
     * Hardness of block, can be changed only before server start.
     * Final copy of hardness from {@link MagicNumbers} class.
     */
    public static final float HARDNESS         = MagicNumbers.MATERIAL__FARMLAND__HARDNESS;

    public static final Farmland FARMLAND          = new Farmland();
    public static final Farmland FARMLAND_HYDRATED = new Farmland("HYDRATED", 0x01, true);

    private static final Map<String, Farmland>    byName = new SimpleStringHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<Farmland> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    private final boolean hydrated;

    @SuppressWarnings("MagicNumber")
    protected Farmland()
    {
        super("FARMLAND", 60, "minecraft:farmland", "UNHYDRATED", (byte) 0x00);
        this.hydrated = false;
    }

    public Farmland(final String enumName, final int type, final boolean hydrated)
    {
        super(FARMLAND.name(), FARMLAND.getId(), FARMLAND.getMinecraftId(), enumName, (byte) type);
        this.hydrated = hydrated;
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
    public Farmland getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public Farmland getType(final int id)
    {
        return getByID(id);
    }

    /**
     * @return true if block is hydrated sub-type
     */
    public boolean isHydrated()
    {
        return this.hydrated;
    }

    /**
     * @param hydrated if sub-type should be hydrated sub-type.
     *
     * @return selected sub-type of Farmland
     */
    public Farmland getHydrated(final boolean hydrated)
    {
        return getFarmland(hydrated);
    }

    /**
     * Returns one of Farmland sub-type based on sub-id, may return null
     * {@link #getFarmland}
     *
     * @param id sub-type id
     *
     * @return sub-type of Farmland or null
     */
    public static Farmland getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of Farmland sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of Farmland or null
     */
    public static Farmland getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * @param hydrated if sub-type should be hydrated sub-type.
     *
     * @return selected sub-type of Farmland
     */
    public static Farmland getFarmland(final boolean hydrated)
    {
        if (hydrated)
        {
            return FARMLAND_HYDRATED;
        }
        return FARMLAND;
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final Farmland element)
    {
        byID.put(element.getType(), element);
        byName.put(element.name(), element);
    }

    static
    {
        Farmland.register(FARMLAND);
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("hydrated", this.hydrated).toString();
    }
}
