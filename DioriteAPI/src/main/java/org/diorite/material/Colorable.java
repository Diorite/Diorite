package org.diorite.material;

import org.diorite.DyeColor;

public interface Colorable
{
    DyeColor getColor();

    Material getColor(DyeColor color);
}
