package org.diorite.material.blocks;

public interface Rotatable
{
    RotateAxis getRotateAxis();

    Rotatable getRotated(RotateAxis axis);
}
