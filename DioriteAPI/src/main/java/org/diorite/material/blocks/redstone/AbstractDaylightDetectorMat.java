package org.diorite.material.blocks.redstone;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.material.BlockMaterialData;
import org.diorite.material.blocks.ChangeablePowerElementMat;
import org.diorite.utils.math.ByteRange;

/**
 * Abstract class for all DaylightDetector-based blocks
 */
public abstract class AbstractDaylightDetectorMat extends BlockMaterialData implements ChangeablePowerElementMat
{
    /**
     * Power range of day light detector, from 0 to 15.
     */
    @SuppressWarnings("MagicNumber")
    public static final ByteRange POWER_RANGE = new ByteRange(0, 15);

    protected final int power;

    protected AbstractDaylightDetectorMat(final String enumName, final int id, final String minecraftId, final int power)
    {
        super(enumName, id, minecraftId, (power == 0) ? "OFF" : Integer.toString(power), (byte) power);
        this.power = this.type;
    }

    protected AbstractDaylightDetectorMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type, final int power)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type);
        this.power = power;
    }

    @Override
    public int getPowerStrength()
    {
        return this.power;
    }

    /**
     * @return inverted version of current Daylight Detector
     */
    public abstract AbstractDaylightDetectorMat getInverted();

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("power", this.power).toString();
    }
}
