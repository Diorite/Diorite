package org.diorite.material.blocks.plants;

import java.util.Map;

import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.material.BlockMaterialData;
import org.diorite.material.blocks.Powerable;
import org.diorite.utils.collections.SimpleStringHashMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing block "Pumpkin" and all its subtypes.
 */
public class Pumpkin extends BlockMaterialData implements Powerable
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
    public static final float BLAST_RESISTANCE = MagicNumbers.MATERIAL__PUMPKIN__BLAST_RESISTANCE;	
    /**
     * Hardness of block, can be changed only before server start.
     * Final copy of hardness from {@link MagicNumbers} class.
     */
    public static final float HARDNESS         = MagicNumbers.MATERIAL__PUMPKIN__HARDNESS;

    public static final Pumpkin PUMPKIN = new Pumpkin();

    private static final Map<String, Pumpkin>    byName = new SimpleStringHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<Pumpkin> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    @SuppressWarnings("MagicNumber")
    protected Pumpkin()
    {
        super("PUMPKIN", 86, "minecraft:pumpkin", "PUMPKIN", (byte) 0x00);
    }

    public Pumpkin(final String enumName, final int type)
    {
        super(PUMPKIN.name(), PUMPKIN.getId(), PUMPKIN.getMinecraftId(), enumName, (byte) type);
    }

    public Pumpkin(final int maxStack, final String typeName, final byte type)
    {
        super(PUMPKIN.name(), PUMPKIN.getId(), PUMPKIN.getMinecraftId(), maxStack, typeName, type);
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
    public Pumpkin getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public Pumpkin getType(final int id)
    {
        return getByID(id);
    }

    @Override
    public boolean isPowered()
    {
        return false;
    }

    @Override
    public BlockMaterialData getPowered(final boolean powered)
    {
        return null; // TODO: implement
    }

    public static Pumpkin getByID(final int id)
    {
        return byID.get((byte) id);
    }

    public static Pumpkin getByEnumName(final String name)
    {
        return byName.get(name);
    }

    public static void register(final Pumpkin element)
    {
        byID.put(element.getType(), element);
        byName.put(element.name(), element);
    }

    static
    {
        Pumpkin.register(PUMPKIN);
    }
}
