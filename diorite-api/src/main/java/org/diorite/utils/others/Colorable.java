package org.diorite.utils.others;

import org.diorite.utils.Color;

/**
 * Represent something that can be colored using RGB.
 */
public interface Colorable
{
    /**
     * Returns color of this object.
     *
     * @return color of this object.
     */
    Color getColor();

    /**
     * Sets new color of this object.
     *
     * @param color new color to be used.
     */
    void setColor(Color color);
}
