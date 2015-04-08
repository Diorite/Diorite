package org.diorite.material.blocks.stony;

import java.util.Map;

import org.diorite.DyeColor;
import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.material.BlockMaterialData;
import org.diorite.material.Colorable;
import org.diorite.material.Material;
import org.diorite.utils.collections.SimpleStringHashMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

public class StainedHardenedClay extends BlockMaterialData implements Colorable
{
    // TODO: auto-generated class, implement other types (sub-ids).
    public static final byte  USED_DATA_VALUES = 1;
    public static final float BLAST_RESISTANCE = MagicNumbers.MATERIAL__STAINED_HARDENED_CLAY__BLAST_RESISTANCE;
    public static final float HARDNESS         = MagicNumbers.MATERIAL__STAINED_HARDENED_CLAY__HARDNESS;

    public static final StainedHardenedClay STAINED_HARDENED_CLAY = new StainedHardenedClay();

    private static final Map<String, StainedHardenedClay>    byName = new SimpleStringHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<StainedHardenedClay> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    @SuppressWarnings("MagicNumber")
    protected StainedHardenedClay()
    {
        super("STAINED_HARDENED_CLAY", 159, "minecraft:stained_hardened_clay", "STAINED_HARDENED_CLAY", (byte) 0x00);
    }

    public StainedHardenedClay(final String enumName, final int type)
    {
        super(STAINED_HARDENED_CLAY.name(), STAINED_HARDENED_CLAY.getId(), STAINED_HARDENED_CLAY.getMinecraftId(), enumName, (byte) type);
    }

    public StainedHardenedClay(final int maxStack, final String typeName, final byte type)
    {
        super(STAINED_HARDENED_CLAY.name(), STAINED_HARDENED_CLAY.getId(), STAINED_HARDENED_CLAY.getMinecraftId(), maxStack, typeName, type);
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
    public StainedHardenedClay getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public StainedHardenedClay getType(final int id)
    {
        return getByID(id);
    }

    @Override
    public DyeColor getColor()
    {
        return null; // TODO: implement
    }

    @Override
    public Material getColor(final DyeColor color)
    {
        return null; // TODO: implement
    }

    public static StainedHardenedClay getByID(final int id)
    {
        return byID.get((byte) id);
    }

    public static StainedHardenedClay getByEnumName(final String name)
    {
        return byName.get(name);
    }

    public static void register(final StainedHardenedClay element)
    {
        byID.put(element.getType(), element);
        byName.put(element.name(), element);
    }

    static
    {
        StainedHardenedClay.register(STAINED_HARDENED_CLAY);
    }
}