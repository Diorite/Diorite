package org.diorite.material.blocks;

/**
 * Representing block that contains power of given strength
 */
public interface ACPowerSource
{
    /**
     * @return Power Strength of current block
     */
    int getPowerStrength();

    /**
     * Returns sub-type of ACPowerSource, based on power strenght.
     *
     * @param strength power in block.
     *
     * @return sub-type of ACPowerSource
     */
    ACPowerSource getPowerStrength(int strength);
}
