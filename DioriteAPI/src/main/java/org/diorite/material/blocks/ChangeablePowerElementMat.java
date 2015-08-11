package org.diorite.material.blocks;

/**
 * Representing block that contains power of given strength
 */
public interface ChangeablePowerElementMat extends PowerableMat
{
    /**
     * @return Power Strength of current block
     */
    int getPowerStrength();

    /**
     * Returns sub-type of ChangeablePowerElement, based on power strenght.
     *
     * @param strength power in block.
     *
     * @return sub-type of ChangeablePowerElement
     */
    ChangeablePowerElementMat getPowerStrength(int strength);

    @Override
    ChangeablePowerElementMat getPowered(boolean powered);

    @Override
    default boolean isPowered()
    {
        return this.getPowerStrength() != 0;
    }
}
