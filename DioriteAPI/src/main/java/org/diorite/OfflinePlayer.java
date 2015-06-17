package org.diorite;

import java.util.UUID;

public interface OfflinePlayer
{
    String getName();

    boolean isOnline();

    UUID getUniqueID();
}
