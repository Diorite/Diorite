package org.diorite.material.blocks.tools;

import java.util.Map;

import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.material.BlockMaterialData;
import org.diorite.material.blocks.ContainerBlock;
import org.diorite.utils.collections.SimpleStringHashMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing block "Anvil" and all its subtypes.
 */
public class Anvil extends BlockMaterialData implements ContainerBlock
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
    public static final float BLAST_RESISTANCE = MagicNumbers.MATERIAL__ANVIL__BLAST_RESISTANCE;
    /**
     * Hardness of block, can be changed only before server start.
     * Final copy of hardness from {@link MagicNumbers} class.
     */
    public static final float HARDNESS         = MagicNumbers.MATERIAL__ANVIL__HARDNESS;

    public static final Anvil ANVIL = new Anvil();

    private static final Map<String, Anvil>    byName = new SimpleStringHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<Anvil> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    @SuppressWarnings("MagicNumber")
    protected Anvil()
    {
        super("ANVIL", 145, "minecraft:anvil", "ANVIL", (byte) 0x00);
    }

    public Anvil(final String enumName, final int type)
    {
        super(ANVIL.name(), ANVIL.getId(), ANVIL.getMinecraftId(), enumName, (byte) type);
    }

    public Anvil(final int maxStack, final String typeName, final byte type)
    {
        super(ANVIL.name(), ANVIL.getId(), ANVIL.getMinecraftId(), maxStack, typeName, type);
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
    public Anvil getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public Anvil getType(final int id)
    {
        return getByID(id);
    }

    /**
     * Returns one of Anvil sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of Anvil or null
     */
    public static Anvil getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of Anvil sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of Anvil or null
     */
    public static Anvil getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final Anvil element)
    {
        byID.put(element.getType(), element);
        byName.put(element.name(), element);
    }

    static
    {
        Anvil.register(ANVIL);
    }
}
