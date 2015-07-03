package org.diorite.material.blocks.redstone;

import java.util.Map;

import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.material.BlockMaterialData;
import org.diorite.material.blocks.ChangeablePowerElementMat;
import org.diorite.utils.collections.maps.CaseInsensitiveMap;
import org.diorite.utils.math.ByteRange;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

@SuppressWarnings("MagicNumber")
/**
 * Class representing block "RedstoneWire" and all its subtypes.
 */ public class RedstoneWireMat extends BlockMaterialData implements ChangeablePowerElementMat
{
    /**
     * Range of valid power strength, from 0 to 15
     */
    public static final ByteRange POWER_RANGE      = new ByteRange(0, 15);
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final byte      USED_DATA_VALUES = 16;
    /**
     * Blast resistance of block, can be changed only before server start.
     * Final copy of blast resistance from {@link MagicNumbers} class.
     */
    public static final float     BLAST_RESISTANCE = MagicNumbers.MATERIAL__REDSTONE_WIRE__BLAST_RESISTANCE;
    /**
     * Hardness of block, can be changed only before server start.
     * Final copy of hardness from {@link MagicNumbers} class.
     */
    public static final float     HARDNESS         = MagicNumbers.MATERIAL__REDSTONE_WIRE__HARDNESS;

    public static final RedstoneWireMat REDSTONE_WIRE_OFF   = new RedstoneWireMat();
    public static final RedstoneWireMat REDSTONE_WIRE_ON_1  = new RedstoneWireMat(0x1);
    public static final RedstoneWireMat REDSTONE_WIRE_ON_2  = new RedstoneWireMat(0x2);
    public static final RedstoneWireMat REDSTONE_WIRE_ON_3  = new RedstoneWireMat(0x3);
    public static final RedstoneWireMat REDSTONE_WIRE_ON_4  = new RedstoneWireMat(0x4);
    public static final RedstoneWireMat REDSTONE_WIRE_ON_5  = new RedstoneWireMat(0x5);
    public static final RedstoneWireMat REDSTONE_WIRE_ON_6  = new RedstoneWireMat(0x6);
    public static final RedstoneWireMat REDSTONE_WIRE_ON_7  = new RedstoneWireMat(0x7);
    public static final RedstoneWireMat REDSTONE_WIRE_ON_8  = new RedstoneWireMat(0x8);
    public static final RedstoneWireMat REDSTONE_WIRE_ON_9  = new RedstoneWireMat(0x9);
    public static final RedstoneWireMat REDSTONE_WIRE_ON_10 = new RedstoneWireMat(0xA);
    public static final RedstoneWireMat REDSTONE_WIRE_ON_11 = new RedstoneWireMat(0xB);
    public static final RedstoneWireMat REDSTONE_WIRE_ON_12 = new RedstoneWireMat(0xC);
    public static final RedstoneWireMat REDSTONE_WIRE_ON_13 = new RedstoneWireMat(0xD);
    public static final RedstoneWireMat REDSTONE_WIRE_ON_14 = new RedstoneWireMat(0xE);
    public static final RedstoneWireMat REDSTONE_WIRE_ON_15 = new RedstoneWireMat(0xF);

    private static final Map<String, RedstoneWireMat>    byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<RedstoneWireMat> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    @SuppressWarnings("MagicNumber")
    protected RedstoneWireMat()
    {
        super("REDSTONE_WIRE", 55, "minecraft:redstone_wire", "OFF", (byte) 0x00);
    }

    protected RedstoneWireMat(final int type)
    {
        super(REDSTONE_WIRE_OFF.name(), REDSTONE_WIRE_OFF.ordinal(), REDSTONE_WIRE_OFF.getMinecraftId(), ((type == 0) ? "OFF" : ("ON_" + type)), (byte) type);
    }

    protected RedstoneWireMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type);
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
    public RedstoneWireMat getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public RedstoneWireMat getType(final int id)
    {
        return getByID(id);
    }

    @Override
    public int getPowerStrength()
    {
        return this.type;
    }

    @Override
    public RedstoneWireMat getPowerStrength(final int strength)
    {
        return getByID(POWER_RANGE.getIn(strength));
    }

    @Override
    public RedstoneWireMat getPowered(final boolean powered)
    {
        return getByID(powered ? 15 : 0);
    }

    @Override
    public boolean isPowered()
    {
        return this.type != 0;
    }

    /**
     * Returns one of RedstoneWire sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of RedstoneWire or null
     */
    public static RedstoneWireMat getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of RedstoneWire sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of RedstoneWire or null
     */
    public static RedstoneWireMat getByEnumName(final String name)
    {
        return byName.get(name);
    }


    /**
     * Returns sub-type of RedstoneWire, based on power strenght.
     *
     * @param power power in block.
     *
     * @return sub-type of RedstoneWire
     */
    public static RedstoneWireMat getRedstoneWire(final int power)
    {
        return getByID(power);
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final RedstoneWireMat element)
    {
        byID.put((byte) element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    @Override
    public RedstoneWireMat[] types()
    {
        return RedstoneWireMat.redstoneWireTypes();
    }

    /**
     * @return array that contains all sub-types of this block.
     */
    public static RedstoneWireMat[] redstoneWireTypes()
    {
        return byID.values(new RedstoneWireMat[byID.size()]);
    }

    static
    {
        RedstoneWireMat.register(REDSTONE_WIRE_OFF);
        RedstoneWireMat.register(REDSTONE_WIRE_ON_1);
        RedstoneWireMat.register(REDSTONE_WIRE_ON_2);
        RedstoneWireMat.register(REDSTONE_WIRE_ON_3);
        RedstoneWireMat.register(REDSTONE_WIRE_ON_4);
        RedstoneWireMat.register(REDSTONE_WIRE_ON_5);
        RedstoneWireMat.register(REDSTONE_WIRE_ON_6);
        RedstoneWireMat.register(REDSTONE_WIRE_ON_7);
        RedstoneWireMat.register(REDSTONE_WIRE_ON_8);
        RedstoneWireMat.register(REDSTONE_WIRE_ON_9);
        RedstoneWireMat.register(REDSTONE_WIRE_ON_10);
        RedstoneWireMat.register(REDSTONE_WIRE_ON_11);
        RedstoneWireMat.register(REDSTONE_WIRE_ON_12);
        RedstoneWireMat.register(REDSTONE_WIRE_ON_13);
        RedstoneWireMat.register(REDSTONE_WIRE_ON_14);
        RedstoneWireMat.register(REDSTONE_WIRE_ON_15);
    }
}
