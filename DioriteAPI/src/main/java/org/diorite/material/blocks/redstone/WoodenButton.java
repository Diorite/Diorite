package org.diorite.material.blocks.redstone;

import java.util.Map;

import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.utils.collections.SimpleStringHashMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing block "WoodenButton" and all its subtypes.
 */
public class WoodenButton extends Button
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
    public static final float BLAST_RESISTANCE = MagicNumbers.MATERIAL__WOODEN_BUTTON__BLAST_RESISTANCE;	
    /**
     * Hardness of block, can be changed only before server start.
     * Final copy of hardness from {@link MagicNumbers} class.
     */
    public static final float HARDNESS         = MagicNumbers.MATERIAL__WOODEN_BUTTON__HARDNESS;

    public static final WoodenButton WOODEN_BUTTON = new WoodenButton();

    private static final Map<String, WoodenButton>    byName = new SimpleStringHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<WoodenButton> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    @SuppressWarnings("MagicNumber")
    protected WoodenButton()
    {
        super("WOODEN_BUTTON", 143, "minecraft:wooden_button", "WOODEN_BUTTON", (byte) 0x00);
    }

    public WoodenButton(final String enumName, final int type)
    {
        super(WOODEN_BUTTON.name(), WOODEN_BUTTON.getId(), WOODEN_BUTTON.getMinecraftId(), enumName, (byte) type);
    }

    public WoodenButton(final int maxStack, final String typeName, final byte type)
    {
        super(WOODEN_BUTTON.name(), WOODEN_BUTTON.getId(), WOODEN_BUTTON.getMinecraftId(), maxStack, typeName, type);
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
    public WoodenButton getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public WoodenButton getType(final int id)
    {
        return getByID(id);
    }

    @Override
    public boolean isActivated()
    {
        return false; // TODO: implement
    }

    /**
     * Returns one of WoodenButton sub-type based on sub-id, may return null
     * @param id sub-type id
     * @return sub-type of WoodenButton or null
     */
    public static WoodenButton getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of WoodenButton sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     * @param name name of sub-type
     * @return sub-type of WoodenButton or null
     */
    public static WoodenButton getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     * @param element sub-type to register
     */
    public static void register(final WoodenButton element)
    {
        byID.put(element.getType(), element);
        byName.put(element.name(), element);
    }

    static
    {
        WoodenButton.register(WOODEN_BUTTON);
    }
}
