package org.diorite.material.blocks.plants;

import java.util.Map;

import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.material.BlockMaterialData;
import org.diorite.material.blocks.Powerable;
import org.diorite.utils.collections.SimpleStringHashMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

public class PumpkinLantern extends BlockMaterialData implements Powerable
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
    public static final float BLAST_RESISTANCE = MagicNumbers.MATERIAL__PUMPKIN_LANTERN__BLAST_RESISTANCE;	
    /**
     * Hardness of block, can be changed only before server start.
     * Final copy of hardness from {@link MagicNumbers} class.
     */
    public static final float HARDNESS         = MagicNumbers.MATERIAL__PUMPKIN_LANTERN__HARDNESS;

    public static final PumpkinLantern PUMPKIN_LANTERN = new PumpkinLantern();

    private static final Map<String, PumpkinLantern>    byName = new SimpleStringHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<PumpkinLantern> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    @SuppressWarnings("MagicNumber")
    protected PumpkinLantern()
    {
        super("PUMPKIN_LANTERN", 91, "minecraft:lit_pumpkin", "PUMPKIN_LANTERN", (byte) 0x00);
    }

    public PumpkinLantern(final String enumName, final int type)
    {
        super(PUMPKIN_LANTERN.name(), PUMPKIN_LANTERN.getId(), PUMPKIN_LANTERN.getMinecraftId(), enumName, (byte) type);
    }

    public PumpkinLantern(final int maxStack, final String typeName, final byte type)
    {
        super(PUMPKIN_LANTERN.name(), PUMPKIN_LANTERN.getId(), PUMPKIN_LANTERN.getMinecraftId(), maxStack, typeName, type);
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
    public PumpkinLantern getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public PumpkinLantern getType(final int id)
    {
        return getByID(id);
    }

    @Override
    public boolean isPowered()
    {
        return true;
    }

    @Override
    public BlockMaterialData getPowered(final boolean powered)
    {
        return null; // TODO: implement
    }

    public static PumpkinLantern getByID(final int id)
    {
        return byID.get((byte) id);
    }

    public static PumpkinLantern getByEnumName(final String name)
    {
        return byName.get(name);
    }

    public static void register(final PumpkinLantern element)
    {
        byID.put(element.getType(), element);
        byName.put(element.name(), element);
    }

    static
    {
        PumpkinLantern.register(PUMPKIN_LANTERN);
    }
}
