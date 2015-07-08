package org.diorite.inventory;

import java.util.Collection;

public interface DragController
{
    boolean start(boolean right);
    boolean addSlot(boolean right, int slot);
    Collection<Integer> end(boolean right);
}
