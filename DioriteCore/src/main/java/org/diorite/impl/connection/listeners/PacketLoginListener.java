package org.diorite.impl.connection.listeners;

import java.util.UUID;

import org.diorite.impl.auth.GameProfile;
import org.diorite.impl.connection.CoreNetworkManager;
import org.diorite.impl.connection.packets.PacketListener;
import org.diorite.cfg.DioriteConfig.OnlineMode;

public interface PacketLoginListener extends PacketListener
{
    GameProfile getGameProfile();

    OnlineMode getOnlineMode();

    void setUUID(UUID uuid);

    void setGameProfile(GameProfile newProfile);

    void setProtocolState(ProtocolState state);

    CoreNetworkManager getNetworkManager();

    enum ProtocolState
    {
        HELLO,
        KEY,
        AUTHENTICATING,
        READY_TO_ACCEPT,
        ACCEPTED
    }
}
