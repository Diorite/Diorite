package org.diorite.material.blocks.tools;

import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.material.BlockMaterialData;
import org.diorite.utils.collections.maps.CaseInsensitiveMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing block "BrewingStandBlock" and all its subtypes.
 */
public class BrewingStandBlockMat extends BlockMaterialData
{
    /**
     * Bit flag defining if slot 0 of brewing stand have potion in it
     * If bit is set to 0, then slot is empty.
     */
    public static final byte  SLOT_0_FLAG      = 0x1;
    /**
     * Bit flag defining if slot 1 of brewing stand have potion in it
     * If bit is set to 0, then slot is empty.
     */
    public static final byte  SLOT_1_FLAG      = 0x2;
    /**
     * Bit flag defining if slot 2 of brewing stand have potion in it
     * If bit is set to 0, then slot is empty.
     */
    public static final byte  SLOT_2_FLAG      = 0x4;
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final byte  USED_DATA_VALUES = 8;
    /**
     * Blast resistance of block, can be changed only before server start.
     * Final copy of blast resistance from {@link MagicNumbers} class.
     */
    public static final float BLAST_RESISTANCE = MagicNumbers.MATERIAL__BREWING_STAND_BLOCK__BLAST_RESISTANCE;
    /**
     * Hardness of block, can be changed only before server start.
     * Final copy of hardness from {@link MagicNumbers} class.
     */
    public static final float HARDNESS         = MagicNumbers.MATERIAL__BREWING_STAND_BLOCK__HARDNESS;

    public static final BrewingStandBlockMat BREWING_STAND_BLOCK_EMPTY = new BrewingStandBlockMat();
    public static final BrewingStandBlockMat BREWING_STAND_BLOCK_F_E_E = new BrewingStandBlockMat("F_E_E", true, false, false);
    public static final BrewingStandBlockMat BREWING_STAND_BLOCK_E_F_E = new BrewingStandBlockMat("E_F_E", false, true, false);
    public static final BrewingStandBlockMat BREWING_STAND_BLOCK_F_F_E = new BrewingStandBlockMat("F_F_E", true, true, false);
    public static final BrewingStandBlockMat BREWING_STAND_BLOCK_E_E_F = new BrewingStandBlockMat("E_E_F", false, false, true);
    public static final BrewingStandBlockMat BREWING_STAND_BLOCK_F_E_F = new BrewingStandBlockMat("F_E_F", true, false, true);
    public static final BrewingStandBlockMat BREWING_STAND_BLOCK_E_F_F = new BrewingStandBlockMat("E_F_F", false, true, true);
    public static final BrewingStandBlockMat BREWING_STAND_BLOCK_FULL  = new BrewingStandBlockMat("FULL", true, true, true);

    private static final Map<String, BrewingStandBlockMat>    byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<BrewingStandBlockMat> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    protected final boolean[] hasBottle;

    @SuppressWarnings("MagicNumber")
    protected BrewingStandBlockMat()
    {
        super("BREWING_STAND_BLOCK", 117, "minecraft:brewing_stand", "EMPTY", (byte) 0x00);
        this.hasBottle = new boolean[3];
    }

    protected BrewingStandBlockMat(final String enumName, final boolean slot0, final boolean slot1, final boolean slot2)
    {
        super(BREWING_STAND_BLOCK_EMPTY.name(), BREWING_STAND_BLOCK_EMPTY.ordinal(), BREWING_STAND_BLOCK_EMPTY.getMinecraftId(), enumName, combine(slot0, slot1, slot2));
        this.hasBottle = new boolean[]{slot0, slot1, slot2};
    }

    protected BrewingStandBlockMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type, final boolean[] hasBottle)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type);
        this.hasBottle = hasBottle;
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

    /**
     * @param slot number of slot, couted from 0.
     *
     * @return true if selected slot have item in it.
     *
     * @throws ArrayIndexOutOfBoundsException if slot is {@literal >} 2 or {@literal <} 0
     */
    public boolean isSlotFull(final int slot) throws ArrayIndexOutOfBoundsException
    {
        return this.hasBottle[slot];
    }

    /**
     * Returns one of BrewingStandBlock sub-type based on used slots.
     * It will never return null.
     *
     * @param isFull0 if brewing stand should have item in slot 0
     * @param isFull1 if brewing stand should have item in slot 1
     * @param isFull2 if brewing stand should have item in slot 2
     *
     * @return sub-type of BrewingStandBlock
     */
    public BrewingStandBlockMat getType(final boolean isFull0, final boolean isFull1, final boolean isFull2)
    {
        return getByID(combine(isFull0, isFull1, isFull2));
    }

    @Override
    public BrewingStandBlockMat getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public BrewingStandBlockMat getType(final int id)
    {
        return getByID(id);
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("hasBottle", this.hasBottle).toString();
    }

    private static byte combine(final boolean slot0, final boolean slot1, final boolean slot2)
    {
        byte result = slot0 ? SLOT_0_FLAG : 0;
        if (slot1)
        {
            result |= SLOT_1_FLAG;
        }
        if (slot2)
        {
            result |= SLOT_2_FLAG;
        }
        return result;
    }

    /**
     * Returns one of BrewingStandBlock sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of BrewingStandBlock or null
     */
    public static BrewingStandBlockMat getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of BrewingStandBlock sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of BrewingStandBlock or null
     */
    public static BrewingStandBlockMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Returns one of BrewingStandBlock sub-type based on used slots.
     * It will never return null.
     *
     * @param isFull0 if brewing stand should have item in slot 0
     * @param isFull1 if brewing stand should have item in slot 1
     * @param isFull2 if brewing stand should have item in slot 2
     *
     * @return sub-type of BrewingStandBlock
     */
    public static BrewingStandBlockMat getBrewingStandBlock(final boolean isFull0, final boolean isFull1, final boolean isFull2)
    {
        return getByID(combine(isFull0, isFull1, isFull2));
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final BrewingStandBlockMat element)
    {
        byID.put((byte) element.getType(), element);
        byName.put(element.name(), element);
    }

    @Override
    public BrewingStandBlockMat[] types()
    {
        return BrewingStandBlockMat.brewingStandBlockTypes();
    }

    /**
     * @return array that contains all sub-types of this block.
     */
    public static BrewingStandBlockMat[] brewingStandBlockTypes()
    {
        return byID.values(new BrewingStandBlockMat[byID.size()]);
    }

    static
    {
        BrewingStandBlockMat.register(BREWING_STAND_BLOCK_EMPTY);
        BrewingStandBlockMat.register(BREWING_STAND_BLOCK_F_E_E);
        BrewingStandBlockMat.register(BREWING_STAND_BLOCK_E_F_E);
        BrewingStandBlockMat.register(BREWING_STAND_BLOCK_F_F_E);
        BrewingStandBlockMat.register(BREWING_STAND_BLOCK_E_E_F);
        BrewingStandBlockMat.register(BREWING_STAND_BLOCK_F_E_F);
        BrewingStandBlockMat.register(BREWING_STAND_BLOCK_E_F_F);
        BrewingStandBlockMat.register(BREWING_STAND_BLOCK_FULL);
    }
}
