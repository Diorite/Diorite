package org.diorite.material.blocks.redstone;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.material.BlockMaterialData;
import org.diorite.material.blocks.ChangeablePowerElementMat;
import org.diorite.utils.math.ByteRange;

/**
 * Abstract class for all DaylightDetector-based blocks
 */
@SuppressWarnings("JavaDoc")
public abstract class AbstractDaylightDetectorMat extends BlockMaterialData implements ChangeablePowerElementMat
{
    /**
     * Power range of day light detector, from 0 to 15.
     */
    @SuppressWarnings("MagicNumber")
    public static final ByteRange POWER_RANGE = new ByteRange(0, 15);

    /**
     * redstone power strength gived by this detector.
     */
    protected final int power;

    protected AbstractDaylightDetectorMat(final String enumName, final int id, final String minecraftId, final int power, final float hardness, final float blastResistance)
    {
        super(enumName, id, minecraftId, (power == 0) ? "OFF" : Integer.toString(power), (byte) power, hardness, blastResistance);
        this.power = this.type;
    }

    protected AbstractDaylightDetectorMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type, final int power, final float hardness, final float blastResistance)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, hardness, blastResistance);
        this.power = power;
    }

    @Override
    public int getPowerStrength()
    {
        return this.power;
    }

    /**
     * Returns inverted version of current Daylight Detector
     *
     * @return inverted version of current Daylight Detector
     */
    public abstract AbstractDaylightDetectorMat getInverted();

    @Override
    public abstract AbstractDaylightDetectorMat getType(final int type);

    @Override
    public abstract AbstractDaylightDetectorMat getType(final String type);

    @Override
    public abstract AbstractDaylightDetectorMat[] types();

    @Override
    public abstract AbstractDaylightDetectorMat getPowerStrength(final int strength);

    @Override
    public abstract AbstractDaylightDetectorMat getPowered(final boolean powered);

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("power", this.power).toString();
    }
}
