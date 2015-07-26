package org.diorite.material;

import org.diorite.Diorite;

/**
 * Represents item or block that can be used as fuel in furnance.
 */
public interface FuelMat
{
    /**
     * Time in centisecond for which the item is able to power the furnance.
     *
     * @return fuel "power" in centiseconds.
     */
    int getFuelPower();

    /**
     * Time in ticks for which the item is able to power the furnance.
     * This value with change if TPS settings will be changed.
     *
     * @return fuel "power" in current ticks.
     */
    default int getTickFuelPower()
    {
        final int fp = this.getFuelPower();
        final int tps = Diorite.getTps();
        return ((fp % tps) == 0) ? (fp / tps) : ((fp / tps) + 1);
    }
}
