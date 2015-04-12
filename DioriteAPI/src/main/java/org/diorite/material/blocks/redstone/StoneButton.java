package org.diorite.material.blocks.redstone;

import java.util.Map;

import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.utils.collections.SimpleStringHashMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

public class StoneButton extends Button
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
    public static final float BLAST_RESISTANCE = MagicNumbers.MATERIAL__STONE_BUTTON__BLAST_RESISTANCE;	
    /**
     * Hardness of block, can be changed only before server start.
     * Final copy of hardness from {@link MagicNumbers} class.
     */
    public static final float HARDNESS         = MagicNumbers.MATERIAL__STONE_BUTTON__HARDNESS;

    public static final StoneButton STONE_BUTTON = new StoneButton();

    private static final Map<String, StoneButton>    byName = new SimpleStringHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<StoneButton> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    @SuppressWarnings("MagicNumber")
    protected StoneButton()
    {
        super("STONE_BUTTON", 77, "minecraft:stone_button", "STONE_BUTTON", (byte) 0x00);
    }

    public StoneButton(final String enumName, final int type)
    {
        super(STONE_BUTTON.name(), STONE_BUTTON.getId(), STONE_BUTTON.getMinecraftId(), enumName, (byte) type);
    }

    public StoneButton(final int maxStack, final String typeName, final byte type)
    {
        super(STONE_BUTTON.name(), STONE_BUTTON.getId(), STONE_BUTTON.getMinecraftId(), maxStack, typeName, type);
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
    public StoneButton getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public StoneButton getType(final int id)
    {
        return getByID(id);
    }

    @Override
    public boolean isActivated()
    {
        return false; // TODO: implement
    }

    public static StoneButton getByID(final int id)
    {
        return byID.get((byte) id);
    }

    public static StoneButton getByEnumName(final String name)
    {
        return byName.get(name);
    }

    public static void register(final StoneButton element)
    {
        byID.put(element.getType(), element);
        byName.put(element.name(), element);
    }

    static
    {
        StoneButton.register(STONE_BUTTON);
    }
}
