package org.diorite.material.blocks.redstone;

import org.diorite.material.BlockMaterialData;

/**
 * Abstract class for all DaylightDetector-based blocks
 */
public abstract class AbstractDaylightDetector extends BlockMaterialData
{
    public AbstractDaylightDetector(final String enumName, final int id, final String minecraftId, final String typeName, final byte type)
    {
        super(enumName, id, minecraftId, typeName, type);
    }

    /**
     * @return inverted version of current Daylight Detector
     */
    public abstract AbstractDaylightDetector getInverted();
}
