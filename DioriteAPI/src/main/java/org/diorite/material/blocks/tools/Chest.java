package org.diorite.material.blocks.tools;

import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.BlockFace;
import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.material.BlockMaterialData;
import org.diorite.material.blocks.Directional;
import org.diorite.utils.collections.SimpleStringHashMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing block "Chest" and all its subtypes.
 */
public class Chest extends BlockMaterialData implements Directional
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final byte  USED_DATA_VALUES = 4;
    /**
     * Blast resistance of block, can be changed only before server start.
     * Final copy of blast resistance from {@link MagicNumbers} class.
     */
    public static final float BLAST_RESISTANCE = MagicNumbers.MATERIAL__CHEST__BLAST_RESISTANCE;
    /**
     * Hardness of block, can be changed only before server start.
     * Final copy of hardness from {@link MagicNumbers} class.
     */
    public static final float HARDNESS         = MagicNumbers.MATERIAL__CHEST__HARDNESS;

    public static final Chest CHEST_NORTH = new Chest();
    public static final Chest CHEST_SOUTH = new Chest(BlockFace.SOUTH);
    public static final Chest CHEST_WEST  = new Chest(BlockFace.WEST);
    public static final Chest CHEST_EAST  = new Chest(BlockFace.EAST);

    private static final Map<String, Chest>    byName = new SimpleStringHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<Chest> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    protected final BlockFace face;

    @SuppressWarnings("MagicNumber")
    protected Chest()
    {
        super("CHEST", 54, "minecraft:chest", "NORTH", (byte) 0x00);
        this.face = BlockFace.NORTH;
    }

    public Chest(final BlockFace face)
    {
        super(CHEST_NORTH.name(), CHEST_NORTH.getId(), CHEST_NORTH.getMinecraftId(), face.name(), combine(face));
        this.face = face;
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
    public Chest getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public Chest getType(final int id)
    {
        return getByID(id);
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("face", this.face).toString();
    }

    @Override
    public BlockFace getBlockFacing()
    {
        return this.face;
    }

    @Override
    public Chest getBlockFacing(final BlockFace face)
    {
        return getByID(combine(face));
    }

    private static byte combine(final BlockFace face)
    {
        switch (face)
        {
            case SOUTH:
                return 0x3;
            case WEST:
                return 0x4;
            case EAST:
                return 0x5;
            default:
                return 0x2;
        }
    }

    /**
     * Returns one of Chest sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of Chest or null
     */
    public static Chest getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of Chest sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of Chest or null
     */
    public static Chest getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Returns one of Chest sub-type based on {@link BlockFace}
     * It will never return null.
     *
     * @param face facing of Chest.
     *
     * @return sub-type of Chest
     */
    public static Chest getChest(final BlockFace face)
    {
        return getByID(combine(face));
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final Chest element)
    {
        byID.put(element.getType(), element);
        byName.put(element.name(), element);
    }

    static
    {
        Chest.register(CHEST_NORTH);
        Chest.register(CHEST_SOUTH);
        Chest.register(CHEST_WEST);
        Chest.register(CHEST_EAST);
    }
}
