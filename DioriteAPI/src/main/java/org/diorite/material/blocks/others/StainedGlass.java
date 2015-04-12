package org.diorite.material.blocks.others;

import java.util.Map;

import org.diorite.DyeColor;
import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.material.BlockMaterialData;
import org.diorite.material.Colorable;
import org.diorite.material.Material;
import org.diorite.utils.collections.SimpleStringHashMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

public class StainedGlass extends BlockMaterialData implements Colorable
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
    public static final float BLAST_RESISTANCE = MagicNumbers.MATERIAL__STAINED_GLASS__BLAST_RESISTANCE;	
    /**
     * Hardness of block, can be changed only before server start.
     * Final copy of hardness from {@link MagicNumbers} class.
     */
    public static final float HARDNESS         = MagicNumbers.MATERIAL__STAINED_GLASS__HARDNESS;

    public static final StainedGlass STAINED_GLASS = new StainedGlass();

    private static final Map<String, StainedGlass>    byName = new SimpleStringHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<StainedGlass> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    @SuppressWarnings("MagicNumber")
    protected StainedGlass()
    {
        super("STAINED_GLASS", 95, "minecraft:stained_glass", "STAINED_GLASS", (byte) 0x00);
    }

    public StainedGlass(final String enumName, final int type)
    {
        super(STAINED_GLASS.name(), STAINED_GLASS.getId(), STAINED_GLASS.getMinecraftId(), enumName, (byte) type);
    }

    public StainedGlass(final int maxStack, final String typeName, final byte type)
    {
        super(STAINED_GLASS.name(), STAINED_GLASS.getId(), STAINED_GLASS.getMinecraftId(), maxStack, typeName, type);
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
    public StainedGlass getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public StainedGlass getType(final int id)
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

    public static StainedGlass getByID(final int id)
    {
        return byID.get((byte) id);
    }

    public static StainedGlass getByEnumName(final String name)
    {
        return byName.get(name);
    }

    public static void register(final StainedGlass element)
    {
        byID.put(element.getType(), element);
        byName.put(element.name(), element);
    }

    static
    {
        StainedGlass.register(STAINED_GLASS);
    }
}
