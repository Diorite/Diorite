package org.diorite.material.blocks;

public interface Powerable
{
    boolean isPowered();

    Powerable getPowered(boolean powered);
}
