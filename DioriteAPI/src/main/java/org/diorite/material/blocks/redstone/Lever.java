package org.diorite.material.blocks.redstone;

import java.util.Map;

import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.material.BlockMaterialData;
import org.diorite.material.blocks.Activatable;
import org.diorite.utils.collections.SimpleStringHashMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing block "Lever" and all its subtypes.
 */
public class Lever extends BlockMaterialData implements Activatable
{
    // TODO: auto-generated class, implement other types (sub-ids).	
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final byte  USED_DATA_VALUES = 1;	
    /**
     * Blast resistance of block, can be changed only before server start.
     * Final copy of blast resistance from {@link MagicNumbers} class.
     */
    public static final float BLAST_RESISTANCE = MagicNumbers.MATERIAL__LEVER__BLAST_RESISTANCE;	
    /**
     * Hardness of block, can be changed only before server start.
     * Final copy of hardness from {@link MagicNumbers} class.
     */
    public static final float HARDNESS         = MagicNumbers.MATERIAL__LEVER__HARDNESS;

    public static final Lever LEVER = new Lever();

    private static final Map<String, Lever>    byName = new SimpleStringHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<Lever> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    @SuppressWarnings("MagicNumber")
    protected Lever()
    {
        super("LEVER", 69, "minecraft:lever", "LEVER", (byte) 0x00);
    }

    public Lever(final String enumName, final int type)
    {
        super(LEVER.name(), LEVER.getId(), LEVER.getMinecraftId(), enumName, (byte) type);
    }

    public Lever(final int maxStack, final String typeName, final byte type)
    {
        super(LEVER.name(), LEVER.getId(), LEVER.getMinecraftId(), maxStack, typeName, type);
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
    public Lever getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public Lever getType(final int id)
    {
        return getByID(id);
    }

    @Override
    public boolean isActivated()
    {
        return false; // TODO implement
    }

    @Override
    public BlockMaterialData getActivated(final boolean activate)
    {
        return null; // TODO implement
    }

    /**
     * Returns one of Lever sub-type based on sub-id, may return null
     * @param id sub-type id
     * @return sub-type of Lever or null
     */
    public static Lever getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of Lever sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     * @param name name of sub-type
     * @return sub-type of Lever or null
     */
    public static Lever getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     * @param element sub-type to register
     */
    public static void register(final Lever element)
    {
        byID.put(element.getType(), element);
        byName.put(element.name(), element);
    }

    static
    {
        Lever.register(LEVER);
    }
}
