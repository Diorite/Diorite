package org.diorite.material.blocks;

public interface Activatable
{
    boolean isActivated();

    Activatable getActivated(boolean activate);
}
