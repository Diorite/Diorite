package org.diorite.material.blocks.others;

import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.material.BlockMaterialData;
import org.diorite.utils.collections.SimpleStringHashMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing block "Jukebox" and all its subtypes.
 */
public class JukeboxMat extends BlockMaterialData
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final byte  USED_DATA_VALUES = 2;
    /**
     * Blast resistance of block, can be changed only before server start.
     * Final copy of blast resistance from {@link MagicNumbers} class.
     */
    public static final float BLAST_RESISTANCE = MagicNumbers.MATERIAL__JUKEBOX__BLAST_RESISTANCE;
    /**
     * Hardness of block, can be changed only before server start.
     * Final copy of hardness from {@link MagicNumbers} class.
     */
    public static final float HARDNESS         = MagicNumbers.MATERIAL__JUKEBOX__HARDNESS;

    public static final JukeboxMat JUKEBOX           = new JukeboxMat();
    public static final JukeboxMat JUKEBOX_WITH_DISC = new JukeboxMat("WITH_DISC", 0x1, true);

    private static final Map<String, JukeboxMat>    byName = new SimpleStringHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<JukeboxMat> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    protected boolean withDisc;

    @SuppressWarnings("MagicNumber")
    protected JukeboxMat()
    {
        super("JUKEBOX", 84, "minecraft:jukebox", "EMPTY", (byte) 0x00);
        this.withDisc = false;
    }

    protected JukeboxMat(final String enumName, final int type, final boolean withDisc)
    {
        super(JUKEBOX.name(), JUKEBOX.getId(), JUKEBOX.getMinecraftId(), enumName, (byte) type);
        this.withDisc = withDisc;
    }

    protected JukeboxMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type, final boolean withDisc)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type);
        this.withDisc = withDisc;
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

    public boolean isWithDisc()
    {
        return this.withDisc;
    }

    public JukeboxMat getWithDisc(final boolean withDisc)
    {
        return getByID(withDisc ? 1 : 0);
    }

    @Override
    public JukeboxMat getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public JukeboxMat getType(final int id)
    {
        return getByID(id);
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("withDisc", this.withDisc).toString();
    }

    /**
     * Returns one of Jukebox sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of Jukebox or null
     */
    public static JukeboxMat getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of Jukebox sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of Jukebox or null
     */
    public static JukeboxMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Returns one of Jukebox sub-type with or without disc.
     * It will never return null.
     *
     * @param withDisc if it should contains disc.
     *
     * @return sub-type of Jukebox
     */
    public static JukeboxMat getJukebox(final boolean withDisc)
    {
        return getByID(withDisc ? 1 : 0);
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final JukeboxMat element)
    {
        byID.put(element.getType(), element);
        byName.put(element.name(), element);
    }

    static
    {
        JukeboxMat.register(JUKEBOX);
        JukeboxMat.register(JUKEBOX_WITH_DISC);
    }
}
